package com.example.smartbiz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.TextGray

import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbiz.ui.theme.SmartBizTheme
import androidx.compose.foundation.BorderStroke

@Composable
fun OnboardingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
            .padding(horizontal = 24.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo Box
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SmartBiz",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Indigo900,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Feature Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Manage Ledger, Billing &\nInventory in one app.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureItem(icon = Icons.Default.Book, label = "Ledger", modifier = Modifier.weight(1f))
                    FeatureItem(icon = Icons.Default.Receipt, label = "Billing", modifier = Modifier.weight(1f))
                    FeatureItem(icon = Icons.Default.Inventory, label = "Stocks", modifier = Modifier.weight(1f))
                }
            }
        }

        Text(
            text = "Select Language",
            fontSize = 13.sp,
            color = TextGray,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 6.dp)
        )

        // Language Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(1.5.dp, Color(0xFFE0E3EF), RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = TextGray, modifier = Modifier.size(18.dp))
                Text(text = "English", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
            }
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = TextGray)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Get Started", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = BorderStroke(1.5.dp, Indigo900),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(text = "Existing User? Log In", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = Icons.Default.Computer, contentDescription = null, tint = Color(0xFFAAAAAA), modifier = Modifier.size(14.dp))
            Text(text = "Made with precision for Indian Merchants", fontSize = 12.sp, color = Color(0xFFAAAAAA))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun OnboardingScreenPreview() {
    SmartBizTheme {
        OnboardingScreen()
    }
}

@Composable
fun FeatureItem(icon: ImageVector, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(LightBg, RoundedCornerShape(14.dp))
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(28.dp))
        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
    }
}
