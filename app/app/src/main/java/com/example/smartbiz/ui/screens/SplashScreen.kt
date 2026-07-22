package com.example.smartbiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A237E)),
        contentAlignment = Alignment.Center
    ) {
        // Background Glow Effect
        Box(
            modifier = Modifier
                .size(500.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x733F51B5), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset.Unspecified,
                        radius = 1000f
                    )
                )
                .align(Alignment.Center)
                .offset(y = (-50).dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo Wrap
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Color(0x1FFFFFFF), androidx.compose.foundation.shape.RoundedCornerShape(28.dp))
                    .border(1.5.dp, Color(0x2EFFFFFF), androidx.compose.foundation.shape.RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Using the generated logo symbol with forced white tint
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.logo_symbol),
                    contentDescription = "SmartBiz Logo",
                    modifier = Modifier.size(56.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
                )
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            Text(
                text = "SmartBiz",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp
            )
            
            Text(
                text = "Your Business, Simplified.",
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 16.sp,
                letterSpacing = 0.2.sp
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            // Progress Dots
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.size(8.dp).background(Color.White.copy(alpha = 0.35f), CircleShape))
                Box(modifier = Modifier.size(width = 22.dp, height = 8.dp).background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(4.dp)))
                Box(modifier = Modifier.size(8.dp).background(Color.White.copy(alpha = 0.35f), CircleShape))
            }
        }
        
        // Footer
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = Color.White.copy(alpha = 0.55f), modifier = Modifier.size(16.dp))
                Text("Made with precision for Indian Merchants", color = Color.White.copy(alpha = 0.55f), fontSize = 13.sp)
            }
            Text("v4.2.0 (STABLE NATIVE BUILD)", color = Color.White.copy(alpha = 0.35f), fontSize = 12.sp)
        }
    }

    LaunchedEffect(Unit) {
        delay(2500)
        onNavigateToLogin()
    }
}
