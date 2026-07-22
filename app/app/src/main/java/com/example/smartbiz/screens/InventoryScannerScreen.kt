package com.example.smartbiz.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.SmartBizTheme
import androidx.compose.foundation.BorderStroke

@Composable
fun InventoryScannerScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Mock Camera Viewport
        Box(modifier = Modifier.fillMaxWidth().height(520.dp).background(Color(0xFF111111))) {
            // Mock background image would go here
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f)))
        }

        // Topbar
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 52.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(38.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.45f)), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Text(text = "Inventory Scanner", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Box(modifier = Modifier.size(38.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.45f)), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.FlashOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }

        // Scan Frame
        val infiniteTransition = rememberInfiniteTransition()
        val scanLineY by infiniteTransition.animateFloat(
            initialValue = 0.1f,
            targetValue = 0.9f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "ScanLine"
        )

        Box(modifier = Modifier.align(Alignment.Center).offset(y = (-40).dp)) {
            Box(modifier = Modifier.size(240.dp, 200.dp)) {
                // Corners
                val cornerSize = 28.dp
                val stroke = 3.dp
                val cornerColor = Color(0xFF4CAF50)
                
                // TL
                Box(modifier = Modifier.size(cornerSize).align(Alignment.TopStart).border(width = stroke, color = cornerColor, shape = RoundedCornerShape(topStart = 4.dp)))
                // TR
                Box(modifier = Modifier.size(cornerSize).align(Alignment.TopEnd).border(width = stroke, color = cornerColor, shape = RoundedCornerShape(topEnd = 4.dp)))
                // BL
                Box(modifier = Modifier.size(cornerSize).align(Alignment.BottomStart).border(width = stroke, color = cornerColor, shape = RoundedCornerShape(bottomStart = 4.dp)))
                // BR
                Box(modifier = Modifier.size(cornerSize).align(Alignment.BottomEnd).border(width = stroke, color = cornerColor, shape = RoundedCornerShape(bottomEnd = 4.dp)))
                
                // Scan Line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = (200 * scanLineY).dp)
                        .background(Brush.horizontalGradient(listOf(Color.Transparent, cornerColor, Color.Transparent)))
                )
                
                // Inner frame
                Box(modifier = Modifier.fillMaxSize().border(1.dp, cornerColor.copy(alpha = 0.25f), RoundedCornerShape(4.dp)))
            }
        }

        // Hint
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 340.dp),
            color = Color.Black.copy(alpha = 0.5f),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "Align barcode within frame to scan",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp)
            )
        }

        // Result Bottom Sheet
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 32.dp)) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally).size(40.dp, 4.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(20.dp)) {
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                            Text(text = "Item Found", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "SKU: 88201933", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888))
                }
                
                Spacer(modifier = Modifier.height(18.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Box(modifier = Modifier.size(80.dp).background(Color(0xFFE8EAF6), RoundedCornerShape(16.dp)).border(1.5.dp, Color(0xFFE0E3EF), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Default.Watch, contentDescription = null, tint = Indigo900, modifier = Modifier.size(40.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Pro-Fit Smart Watch S3", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                        Row(modifier = Modifier.padding(top = 6.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Badge(text = "In Stock", color = Color(0xFF2E7D32), bg = Color(0xFFE8F5E9))
                            Badge(text = "Electronics", color = Indigo900, bg = Color(0xFFE8EAF6))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "₹129.00", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                        Text(text = "Stock: 42 Units", fontSize = 13.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 2.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(18.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.weight(1f).height(56.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
                            Text(text = "Edit Item", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                        }
                    }
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Text(text = "Add to Sale", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Badge(text: String, color: Color, bg: Color) {
    Surface(color = bg, shape = RoundedCornerShape(8.dp)) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun InventoryScannerScreenPreview() {
    SmartBizTheme {
        InventoryScannerScreen()
    }
}
