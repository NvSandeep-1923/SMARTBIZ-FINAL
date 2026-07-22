package com.example.smartbiz.data.models

data class User(
    val id: Int,
    val phone: String? = null,
    val email: String? = null,
    val merchant_name: String? = null,
    val business_type: String? = null,
    val gstin: String? = null,
    val address: String? = null,
    val role: String? = null,
    val avatar_url: String? = null
)

// Backend responds with: { user: {...}, message: "..." } OR { error: "..." }
data class AuthResponse(
    val user: User? = null,
    val message: String? = null,
    val error: String? = null,
    val id: Int? = null,      // register returns { id, message }
    val token: String? = null,
    val refresh_token: String? = null
) {
    val success: Boolean get() = user != null || id != null
}

// Backend expects: { email, password }
data class LoginRequest(
    val email: String,
    val password: String
)

// Holds UI form values
data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val business_name: String,
    val pin: String
)

// What actually goes to backend: { phone, email, password, merchant_name }
data class RegisterApiRequest(
    val phone: String,
    val email: String,
    val password: String,
    val merchant_name: String
)

fun RegisterRequest.toApiRequest() = RegisterApiRequest(
    phone = phone,
    email = email,
    password = pin,
    merchant_name = business_name
)
