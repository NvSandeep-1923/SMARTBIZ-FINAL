package com.example.smartbiz.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun LoginV2Screen() {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFFEEF0FB), Color(0xFFF5F6FF))))
            .padding(horizontal = 28.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Shop Icon
        Box(
            modifier = Modifier
                .size(76.dp)
                .background(Indigo900, RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier.size(38.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Login to your Business", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900, textAlign = TextAlign.Center)
        Text(text = "Enter your details to manage your store", fontSize = 14.sp, color = Color(0xFF888888), textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 32.dp))

        // Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 28.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(28.dp)) {
                Text(text = "Phone Number", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 8.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, Color(0xFFE0E3EF), RoundedCornerShape(12.dp))
                        .padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "+91",
                        modifier = Modifier
                            .background(Color(0xFFF0F2F8))
                            .padding(14.dp, 16.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Indigo900
                    )
                    Box(modifier = Modifier.width(1.5.dp).height(48.dp).background(Color(0xFFE0E3EF)))
                    BasicTextField(
                        value = phoneNumber,
                        onValueChange = { if (it.length <= 10) phoneNumber = it },
                        modifier = Modifier.weight(1f).padding(14.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        decorationBox = { innerTextField ->
                            if (phoneNumber.isEmpty()) {
                                Text(text = "00000 00000", color = Color(0xFFBBBBBB), fontSize = 15.sp)
                            }
                            innerTextField()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Get OTP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color(0xFFE8EAF0))
                    Text(text = "OR LOGIN WITH EMAIL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFAAAAAA), letterSpacing = 0.8.sp)
                    Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color(0xFFE8EAF0))
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    border = BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Indigo900, modifier = Modifier.size(18.dp))
                        Text(text = "Continue with Email", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(
                        modifier = Modifier.size(18.dp),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
                        color = Color.White
                    ) {}
                    Text(
                        text = buildAnnotatedString {
                            append("I agree to the ")
                            withStyle(style = SpanStyle(color = Indigo900, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                                append("Terms and Conditions")
                            }
                            append(" and the ")
                            withStyle(style = SpanStyle(color = Indigo900, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                                append("Privacy Policy")
                            }
                            append(" of SmartBiz.")
                        },
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TrustCardV2(icon = Icons.Default.Shield, label = "Secure SSL Encryption", modifier = Modifier.weight(1f))
            TrustCardV2(icon = Icons.Default.VerifiedUser, label = "Trusted by 10k+ SMBs", modifier = Modifier.weight(1f))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "SmartBiz", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Text(text = "• v2.4.0 (Stable)", fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 4.dp))
            Text(text = "Powered by SecureCommerce Infrastructure", fontSize = 11.sp, color = Color(0xFFBBBBBB), modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun TrustCardV2(icon: ImageVector, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp, 14.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(24.dp))
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF555555), textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginV2ScreenPreview() {
    SmartBizTheme {
        LoginV2Screen()
    }
}
