package com.example.smartbiz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.api.SessionManager
import com.example.smartbiz.data.models.LoginRequest
import com.example.smartbiz.data.models.RegisterRequest
import com.example.smartbiz.data.models.User
import com.example.smartbiz.data.repository.SmartBizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SmartBizRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, pin: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Backend uses 'password' field, not 'pin'
                val response = repository.login(email, pin)
                if (response.isSuccessful) {
                    val body = response.body()
                    val user = body?.user
                    val token = body?.token
                    val rToken = body?.refresh_token
                    if (user != null && token != null) {
                        // Persist session
                        SessionManager.saveSession(getApplication(), token, rToken, user)
                        _authState.value = AuthState.Success(user)
                    } else {
                        _authState.value = AuthState.Error(
                            body?.error ?: body?.message ?: "Invalid credentials"
                        )
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = try {
                        org.json.JSONObject(errorBody).optString("error", "Login failed")
                    } catch (e: Exception) {
                        "Login failed"
                    }
                    _authState.value = AuthState.Error(
                        when (response.code()) {
                            401 -> "Wrong email or PIN. Please try again."
                            403 -> errorMessage.ifBlank { "Please verify your email before continuing." }
                            404 -> "Account not found. Please register first."
                            500 -> "Server error. Please try again later."
                            else -> errorMessage.ifBlank { "Login failed. Please check your details." }
                        }
                    )
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Cannot connect to server. Check your network.")
            }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = repository.register(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.id != null) {
                        // Registration success — auto-login will hit 403 if email unconfirmed,
                        // prompting user to confirm check email.
                        login(request.email, request.pin)
                    } else {
                        // Backend said 200 but no id — try login anyway
                        login(request.email, request.pin)
                    }
                } else {
                    // 400 = already exists → try logging in with same credentials
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = try {
                        org.json.JSONObject(errorBody).optString("error", "Registration failed")
                    } catch (e: Exception) {
                        "Registration failed"
                    }
                    if (response.code() == 400 || errorBody.contains("exists", ignoreCase = true)) {
                        // User already registered — just log them in
                        login(request.email, request.pin)
                    } else {
                        _authState.value = AuthState.Error(errorMessage.ifBlank { "Registration failed (${response.code()})" })
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Cannot connect to server. Check your network.")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
