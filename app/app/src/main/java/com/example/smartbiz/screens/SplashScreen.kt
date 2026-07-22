package com.example.smartbiz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900

import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Indigo900),
        contentAlignment = Alignment.Center
    ) {
        // Background Glow (simplified)
        Box(
            modifier = Modifier
                .size(500.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x733F51B5), Color.Transparent),
                    ),
                    shape = CircleShape
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Wrap
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Color(0x1FFFFFFF), RoundedCornerShape(28.dp))
                    .border(1.5.dp, Color(0x2EFFFFFF), RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BusinessCenter,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "SmartBiz",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = (-1).sp
            )

            Text(
                text = "Your Business, Simplified.",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.65f),
                modifier = Modifier.padding(top = 10.dp, bottom = 64.dp)
            )

            // Pagination Dots
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.size(8.dp).background(Color.White.copy(alpha = 0.35f), CircleShape))
                Box(modifier = Modifier.size(width = 22.dp, height = 8.dp).background(Color.White, RoundedCornerShape(4.dp)))
                Box(modifier = Modifier.size(8.dp).background(Color.White.copy(alpha = 0.35f), CircleShape))
            }
        }

        // Footer
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.White.copy(alpha = 0.55f)
                )
                Text(
                    text = "Made with precision for Indian Merchants",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.55f)
                )
            }
            Text(
                text = "v4.2.0",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.35f)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun SplashScreenPreview() {
    SmartBizTheme {
        SplashScreen()
    }
}
