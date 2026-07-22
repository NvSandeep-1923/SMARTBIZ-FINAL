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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.TextGray

import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun LoginScreen() {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        // Topbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(36.dp)) {
                Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null, tint = Indigo900)
            }
            Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(36.dp)) {
                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = null, tint = Indigo900)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
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

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Welcome back!", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Text(text = "Login to manage your business", fontSize = 14.sp, color = TextGray, modifier = Modifier.padding(bottom = 28.dp))

            // Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = "Phone Number", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 8.dp))
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, Color(0xFFE0E3EF), RoundedCornerShape(12.dp))
                            .padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color(0xFFF8F9FF), RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "🇮🇳", fontSize = 18.sp)
                            Text(text = "+91", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                        }
                        Box(modifier = Modifier.width(1.5.dp).height(48.dp).background(Color(0xFFE0E3EF)))
                        BasicTextField(
                            value = phoneNumber,
                            onValueChange = { if (it.length <= 10) phoneNumber = it },
                            modifier = Modifier.weight(1f).padding(14.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            decorationBox = { innerTextField ->
                                if (phoneNumber.isEmpty()) {
                                    Text(text = "Enter 10 digit number", color = Color(0xFFBBBBBB), fontSize = 15.sp)
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
                        Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color(0xFFE0E3EF))
                        Text(text = "SECURE ACCESS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFAAAAAA), letterSpacing = 0.8.sp)
                        Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color(0xFFE0E3EF))
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
                            Text(text = "Login with Email", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
                        }
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TrustCard(icon = Icons.Default.Shield, label = "Secure Payments", color = Color(0xFF2E7D32), modifier = Modifier.weight(1f))
                TrustCard(icon = Icons.Default.Timeline, label = "Real-time Analytics", color = Indigo900, modifier = Modifier.weight(1f))
            }

            Text(
                text = buildAnnotatedString {
                    append("By continuing, you agree to our ")
                    withStyle(style = SpanStyle(color = Indigo900, fontWeight = FontWeight.Bold)) {
                        append("Terms & Conditions")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(color = Indigo900, fontWeight = FontWeight.Bold)) {
                        append("Privacy Policy")
                    }
                },
                fontSize = 12.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(22.dp))
                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(22.dp))
                Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(22.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenPreview() {
    SmartBizTheme {
        LoginScreen()
    }
}

@Composable
fun TrustCard(icon: ImageVector, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp, 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = color)
        }
    }
}
