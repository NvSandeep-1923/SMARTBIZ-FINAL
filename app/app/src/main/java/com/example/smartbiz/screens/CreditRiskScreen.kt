package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

data class RiskItem(
    val name: String,
    val lastPayment: String,
    val amount: String,
    val riskLevel: String,
    val riskColor: Color,
    val riskBg: Color,
    val avatarBg: Color,
    val avatarChar: String,
    val aiInsight: String? = null
)

@Composable
fun CreditRiskScreen() {
    val riskItems = listOf(
        RiskItem("Rajesh Groceries", "Last payment: 45 days ago", "₹ 42,850.00", "High Risk", Color(0xFFC62828), Color(0xFFFFEBEE), Color(0xFFEF9A9A), "R", "AI Insight: Payment delay predicted based on history. Customer cash flow shows volatility patterns typical of seasonal downturn."),
        RiskItem("Priya Textiles", "Last payment: 12 days ago", "₹ 15,200.00", "Medium Risk", Color(0xFFE65100), Color(0xFFFFF3E0), Color(0xFFFFCC80), "P", "AI Insight: Moderate credit limit recommended. Ensure follow-up via WhatsApp within 48 hours to maintain current cycle."),
        RiskItem("Modern Electronics", "Last payment: 2 days ago", "₹ 5,400.00", "Low Risk", Color(0xFF2E7D32), Color(0xFFE8F5E9), Color(0xFFA5D6A7), "M"),
        RiskItem("Super Traders", "Last payment: 62 days ago", "₹ 1,28,400.00", "High Risk", Color(0xFFC62828), Color(0xFFFFEBEE), Color(0xFFEF9A9A), "S", "AI Insight: High probability of default. Action recommended: Suspend credit orders and initiate formal recovery protocol immediately.")
    )

    Scaffold(
        topBar = { CreditRiskTopBar() },
        bottomBar = { DashboardBottomNav() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(text = "Customer Credit Risk Analysis", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Text(text = "AI-powered financial health monitoring and predictive insights.", fontSize = 13.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 4.dp, bottom = 14.dp))
            
            RiskLegendCard()
            HealthCard()
            RiskPrioritizationRow()
            
            riskItems.forEach { item ->
                RiskItemCard(item)
            }
            
            MarketTrendsCard()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CreditRiskTopBar() {
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
fun RiskLegendCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = Color(0xFF888888), modifier = Modifier.size(16.dp))
                Text(text = "Risk Legend", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF444444))
            }
            LegendRow(Color(0xFFF44336), "High Risk", "Critically Overdue")
            LegendRow(Color(0xFFFF9800), "Medium Risk", "Unstable History")
            LegendRow(Color(0xFF4CAF50), "Low Risk", "Timely Payments")
        }
    }
}

@Composable
fun LegendRow(color: Color, title: String, sub: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Text(text = sub, fontSize = 12.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun HealthCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "84%", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Text(text = "Overall Portfolio Health", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), letterSpacing = 0.8.sp)
        }
    }
}

@Composable
fun RiskPrioritizationRow() {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Risk Prioritization", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Surface(color = Indigo900, shape = RoundedCornerShape(20.dp)) {
                Text(text = "All", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp))
            }
            Surface(color = Color.White, border = BorderStroke(1.5.dp, Color(0xFFD0D4E8)), shape = RoundedCornerShape(20.dp)) {
                Text(text = "High Risk Only", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp))
            }
        }
    }
}

@Composable
fun RiskItemCard(item: RiskItem) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                Box(modifier = Modifier.size(44.dp).background(item.avatarBg, CircleShape), contentAlignment = Alignment.Center) {
                    Text(text = item.avatarChar, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                    Text(text = item.lastPayment, fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = item.amount, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                    Surface(color = item.riskBg, shape = RoundedCornerShape(20.dp), modifier = Modifier.padding(top = 4.dp)) {
                        Text(text = item.riskLevel, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = item.riskColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                    }
                }
            }
            if (item.aiInsight != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (item.riskLevel == "High Risk") Color(0xFFFFF5F5) else Color(0xFFFFF8F0),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(modifier = Modifier.padding(10.dp, 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = if (item.riskLevel == "High Risk") Color(0xFFC62828) else Color(0xFFE65100), modifier = Modifier.size(14.dp).padding(top = 1.dp))
                        Text(text = item.aiInsight, fontSize = 12.sp, color = Color(0xFF888888), lineHeight = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun MarketTrendsCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(text = "AI Market Trends", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF90CAF9))
            Text(text = "Local retail credit cycles are lengthening.", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
            Text(text = "Average of 4.2 days this month. SmartBiz AI suggests increasing your credit buffer for preferred low-risk customers.", fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f), lineHeight = 18.sp)
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun CreditRiskScreenPreview() {
    SmartBizTheme {
        CreditRiskScreen()
    }
}
