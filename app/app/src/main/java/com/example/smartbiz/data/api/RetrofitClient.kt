package com.example.smartbiz.data.api

import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 10.0.2.2 = emulator loopback to host PC
    // For physical device, use your PC's Wi-Fi IP (same network as phone)
    private const val EMULATOR_URL = "http://10.0.2.2:5001"
    private const val DEVICE_URL   = "http://10.34.41.231:5001"

    // Set to true when running on physical device, false for emulator
    private const val USE_PHYSICAL_DEVICE = true

    val BASE_URL get() = if (USE_PHYSICAL_DEVICE) DEVICE_URL else EMULATOR_URL

    private var appContext: android.content.Context? = null

    fun init(context: android.content.Context) {
        val app = context.applicationContext
        appContext = app
        SessionManager.restoreSession(app)
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            // Pre-emptively refresh token if expired
            val context = appContext
            if (context != null && SessionManager.authToken != null && SessionManager.isSessionExpired() && SessionManager.refreshToken != null) {
                synchronized(this) {
                    if (SessionManager.isSessionExpired()) {
                        refreshAccessToken(context)
                    }
                }
            }

            // Attach current token
            val token = SessionManager.authToken
            val request = if (token != null) {
                chain.request().newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } else {
                chain.request()
            }

            var response = chain.proceed(request)
            if (response.code == 401 && SessionManager.refreshToken != null && context != null) {
                var refreshed = false
                synchronized(this) {
                    if (SessionManager.isSessionExpired() || SessionManager.authToken == token) {
                        refreshed = refreshAccessToken(context)
                    } else {
                        refreshed = true // Already refreshed by another request thread
                    }
                }
                if (refreshed) {
                    response.close()
                    val newRequest = chain.request().newBuilder()
                        .header("Authorization", "Bearer ${SessionManager.authToken}")
                        .build()
                    response = chain.proceed(newRequest)
                }
            }
            response
        }
        .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private fun refreshAccessToken(context: android.content.Context): Boolean {
        try {
            val refreshRequest = okhttp3.Request.Builder()
                .url("$BASE_URL/api/auth/refresh")
                .post("{\"refresh_token\":\"${SessionManager.refreshToken}\"}".toRequestBody("application/json".toMediaTypeOrNull()))
                .build()
            val tempClient = OkHttpClient.Builder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .build()
            val response = tempClient.newCall(refreshRequest).execute()
            if (response.isSuccessful) {
                val bodyStr = response.body?.string() ?: ""
                val json = org.json.JSONObject(bodyStr)
                val newToken = json.optString("token")
                val newRefreshToken = json.optString("refresh_token")
                if (newToken.isNotBlank()) {
                    SessionManager.updateAccessToken(context, newToken, newRefreshToken)
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}

/**
 * Persistent SharedPreferences-based session store.
 * Holds active authentication token, user information, and handles token refresh/expiry.
 */
object SessionManager {
    private const val PREFS_NAME = "smartbiz_session"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_EMAIL = "email"
    private const val KEY_ROLE = "role"
    private const val KEY_USERNAME = "username"
    private const val KEY_BUSINESS_NAME = "business_name"
    private const val KEY_AVATAR_URL = "avatar_url"
    private const val KEY_EXPIRY = "expiry"

    var currentMerchantId: Int = 1
    var authToken: String? = null
    var currentUserName: String = ""
    var currentBusinessName: String = ""
    var currentUserRole: String = "Citizen"

    var refreshToken: String? = null
    var currentUserEmail: String = ""
    var currentUserAvatarUrl: String = ""
    var sessionExpiry: Long = 0L

    fun saveSession(context: android.content.Context, token: String, rToken: String?, user: com.example.smartbiz.data.models.User) {
        authToken = token
        refreshToken = rToken
        currentMerchantId = user.id
        currentUserName = user.merchant_name ?: ""
        currentBusinessName = user.merchant_name ?: ""
        currentUserRole = user.role ?: "Citizen"
        currentUserEmail = user.email ?: ""
        currentUserAvatarUrl = user.avatar_url ?: ""
        sessionExpiry = getExpiryFromToken(token)

        context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_ACCESS_TOKEN, token)
            .putString(KEY_REFRESH_TOKEN, rToken)
            .putInt(KEY_USER_ID, user.id)
            .putString(KEY_EMAIL, user.email)
            .putString(KEY_ROLE, user.role)
            .putString(KEY_USERNAME, user.merchant_name)
            .putString(KEY_BUSINESS_NAME, user.merchant_name)
            .putString(KEY_AVATAR_URL, user.avatar_url)
            .putLong(KEY_EXPIRY, sessionExpiry)
            .apply()
    }

    fun restoreSession(context: android.content.Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
        val token = prefs.getString(KEY_ACCESS_TOKEN, null)
        return if (token != null) {
            authToken = token
            refreshToken = prefs.getString(KEY_REFRESH_TOKEN, null)
            currentMerchantId = prefs.getInt(KEY_USER_ID, 1)
            currentUserName = prefs.getString(KEY_USERNAME, "") ?: ""
            currentBusinessName = prefs.getString(KEY_BUSINESS_NAME, "") ?: ""
            currentUserRole = prefs.getString(KEY_ROLE, "Citizen") ?: "Citizen"
            currentUserEmail = prefs.getString(KEY_EMAIL, "") ?: ""
            currentUserAvatarUrl = prefs.getString(KEY_AVATAR_URL, "") ?: ""
            sessionExpiry = prefs.getLong(KEY_EXPIRY, 0L)
            true
        } else {
            false
        }
    }

    fun updateAccessToken(context: android.content.Context, token: String, rToken: String?) {
        authToken = token
        refreshToken = rToken
        sessionExpiry = getExpiryFromToken(token)

        context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_ACCESS_TOKEN, token)
            .putString(KEY_REFRESH_TOKEN, rToken)
            .putLong(KEY_EXPIRY, sessionExpiry)
            .apply()
    }

    fun clearSession(context: android.content.Context) {
        authToken = null
        refreshToken = null
        currentMerchantId = 1
        currentUserName = ""
        currentBusinessName = ""
        currentUserRole = "Citizen"
        currentUserEmail = ""
        currentUserAvatarUrl = ""
        sessionExpiry = 0L

        context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

    fun isSessionExpired(): Boolean {
        if (authToken == null) return true
        val nowSeconds = System.currentTimeMillis() / 1000
        return sessionExpiry > 0 && nowSeconds >= sessionExpiry
    }

    private fun getExpiryFromToken(token: String): Long {
        try {
            val parts = token.split(".")
            if (parts.size >= 2) {
                val payloadBytes = android.util.Base64.decode(parts[1], android.util.Base64.DEFAULT)
                val payloadString = String(payloadBytes, java.nio.charset.StandardCharsets.UTF_8)
                val json = org.json.JSONObject(payloadString)
                return json.optLong("exp", 0L)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0L
    }
}
