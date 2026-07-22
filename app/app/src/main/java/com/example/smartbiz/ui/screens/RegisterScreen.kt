package com.example.smartbiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartbiz.data.models.RegisterRequest

@Composable
fun RegisterScreen(
    onRegisterClick: (RegisterRequest) -> Unit,
    onLoginClick: () -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    // Local guard — prevents double-tap firing multiple requests
    var submitted by remember { mutableStateOf(false) }

    // Reset submitted flag when loading finishes or error appears
    LaunchedEffect(isLoading) {
        if (!isLoading) submitted = false
    }

    val canSubmit = name.isNotBlank() && email.isNotBlank() && phone.isNotBlank()
            && businessName.isNotBlank() && pin.length >= 4 && !isLoading && !submitted

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(translationViewModel.t("Create Account"), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
        Text(
            translationViewModel.t("Join SmartBiz to grow your business"),
            fontSize = 14.sp, color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Error banner
        if (errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text(
                    errorMessage,
                    color = Color(0xFFC62828),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 14.sp
                )
            }
        }

        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text(translationViewModel.t("Full Name")) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text(translationViewModel.t("Email Address")) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true, enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone, onValueChange = { phone = it },
            label = { Text(translationViewModel.t("Phone Number")) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true, enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = businessName, onValueChange = { businessName = it },
            label = { Text(translationViewModel.t("Business Name")) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = pin, onValueChange = { if (it.length <= 6) pin = it },
            label = { Text(translationViewModel.t("Security PIN (4-6 digits)")) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            singleLine = true, enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                submitted = true
                onRegisterClick(RegisterRequest(name, email, phone, businessName, pin))
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
            enabled = canSubmit
        ) {
            if (isLoading || submitted) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text(translationViewModel.t("Register Now"), fontWeight = FontWeight.Bold)
            }
        }

        TextButton(onClick = onLoginClick, enabled = !isLoading) {
            Text(translationViewModel.t("Already have an account? "), color = Color.Gray)
            Text(translationViewModel.t("Login"), color = Color(0xFF1A237E), fontWeight = FontWeight.Bold)
        }
    }
}
