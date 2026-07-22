package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun AIInsightsScreen() {
    Scaffold(
        topBar = { AIInsightsTopBar() },
        bottomBar = { DashboardBottomNav() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Indigo900,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
                Text(text = "AI ANALYSIS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Indigo900, letterSpacing = 0.5.sp)
            }
            Text(text = "Smart Business\nInsights", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF111111), lineHeight = 32.sp)
            Text(text = "Real-time predictive analytics powered by SmartBiz AI.", fontSize = 13.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 6.dp, bottom = 20.dp))
            
            ForecastCard()
            AISuggestionCard()
            OptimizationCard()
            TopSellingProductsCard()
            CustomerCreditRiskCard()
        }
    }
}

@Composable
fun AIInsightsTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Indigo900)
        Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900)
    }
}

@Composable
fun ForecastCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(text = "Sales Forecast", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                    Text(text = "Projected growth for next month", fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "+24.8%", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
                    Text(text = "ESTIMATED\nINCREASE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), textAlign = TextAlign.End)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Simplified Forecast Chart
            Canvas(modifier = Modifier.fillMaxWidth().height(90.dp)) {
                val path = Path().apply {
                    moveTo(0f, size.height * 0.75f)
                    cubicTo(
                        size.width * 0.2f, size.height * 0.7f,
                        size.width * 0.4f, size.height * 0.6f,
                        size.width * 0.6f, size.height * 0.5f
                    )
                    cubicTo(
                        size.width * 0.75f, size.height * 0.4f,
                        size.width * 0.9f, size.height * 0.2f,
                        size.width, size.height * 0.05f
                    )
                }
                drawPath(path, color = Indigo900, style = Stroke(width = 2.5.dp.toPx()))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Week 1", "Week 2", "Week 3", "Forecast").forEach { label ->
                    Text(text = label, fontSize = 11.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun AISuggestionCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFF90CAF9), modifier = Modifier.size(14.dp))
                Text(text = "AI Suggestion", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF90CAF9))
            }
            Text(
                text = buildAnnotatedString {
                    append("Restock ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) { append("Wheat") }
                    append(" — likely to run out in 3 days based on local demand trends.")
                },
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(text = "Create Order", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun OptimizationCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(32.dp).background(Color(0xFFFFF9E6), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = Color(0xFFF9A825), modifier = Modifier.size(16.dp))
            }
            Column {
                Text(text = "Optimization", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(
                    text = buildAnnotatedString {
                        append("Bulk discount available for ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("Dairy") }
                        append(" from Supplier B until tomorrow.")
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF888888),
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

@Composable
fun TopSellingProductsCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Top Selling Products", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = null, tint = Color(0xFFAAAAAA))
            }
            TopProductItem("🌾", "Premium Wheat Flour", "SKU: WF-092", "₹12,450", "↑ 12%", Color(0xFF2E7D32))
            TopProductItem("🛢️", "Refined Cooking Oil", "SKU: CO-441", "₹8,920", "↑ 8%", Color(0xFF2E7D32))
            TopProductItem("🥚", "Organic Brown Eggs", "SKU: EG-102", "₹4,200", "↓ 2%", Color(0xFFC62828))
        }
    }
}

@Composable
fun TopProductItem(icon: String, name: String, sku: String, price: String, change: String, changeColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(modifier = Modifier.size(38.dp).background(Color(0xFFE8EAF6), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
            Text(text = icon, fontSize = 18.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
            Text(text = sku, fontSize = 11.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 1.dp))
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = price, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Text(text = change, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = changeColor, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun CustomerCreditRiskCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Customer Credit Risk", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = Color(0xFFAAAAAA), modifier = Modifier.size(20.dp))
            }
            CreditRiskItem("Rajesh Kumar", "Outstanding: ₹22,000", "LOW RISK", Color(0xFFE8F5E9), Color(0xFF2E7D32))
            CreditRiskItem("Priya Sharma", "Outstanding: ₹45,500", "MEDIUM RISK", Color(0xFFFFF3E0), Color(0xFFE65100))
            CreditRiskItem("Amit Patel", "Outstanding: ₹89,000", "HIGH RISK", Color(0xFFFFEBEE), Color(0xFFC62828))
        }
    }
}

@Composable
fun CreditRiskItem(name: String, outstanding: String, risk: String, riskBg: Color, riskColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(modifier = Modifier.size(40.dp).background(Color(0xFFE0E0E0), CircleShape), contentAlignment = Alignment.Center) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.Gray)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
            Text(text = outstanding, fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
        }
        Surface(color = riskBg, shape = RoundedCornerShape(20.dp)) {
            Text(text = risk, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = riskColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun AIInsightsScreenPreview() {
    SmartBizTheme {
        AIInsightsScreen()
    }
}
