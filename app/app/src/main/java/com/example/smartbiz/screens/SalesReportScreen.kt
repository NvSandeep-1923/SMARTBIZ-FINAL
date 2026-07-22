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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme
import androidx.compose.foundation.BorderStroke

@Composable
fun SalesReportScreen() {
    Scaffold(
        topBar = { SalesReportTopBar() },
        bottomBar = { DashboardBottomNav() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Indigo900,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
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
            Text(text = "Sales Report", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Text(text = "Detailed business performance insights", fontSize = 13.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp, bottom = 12.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 14.dp)) {
                ReportActionBtn(Icons.Default.Download, "Download PDF")
                ReportActionBtn(Icons.Default.Share, "Share")
            }
            
            PeriodTabsRow()
            
            StatReportCard(title = "Total Sales", amount = "₹4,28,450", badge = "↑ +12.5% vs last month", icon = Icons.Default.TrendingUp, isPrimary = true)
            StatReportCard(title = "Total Profit", amount = "₹84,200", badge = "↑ +8.2% vs last month", icon = Icons.Default.Payments, isPrimary = false)
            StatReportCard(title = "Total Collected", amount = "₹3,90,120", badge = "+2.1% Pending", icon = Icons.Default.ReceiptLong, isPrimary = false, badgeColor = Color(0xFFE65100))
            
            SalesTrendChartCard()
            RecentTransactionsCard()
        }
    }
}

@Composable
fun SalesReportTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Indigo900)
        Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900)
    }
}

@Composable
fun ReportActionBtn(icon: ImageVector, label: String) {
    Surface(
        modifier = Modifier.clickable { /* TODO */ },
        border = BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
        shape = RoundedCornerShape(10.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
            Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
        }
    }
}

@Composable
fun PeriodTabsRow() {
    val tabs = listOf("Daily", "Weekly", "Monthly", "Custom")
    Surface(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            tabs.forEach { tab ->
                val isActive = tab == "Daily"
                Surface(
                    modifier = Modifier.weight(1f).clickable { /* TODO */ },
                    color = if (isActive) Indigo900 else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (tab == "Custom") {
                            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = if (isActive) Color.White else Color(0xFF888888), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(text = tab, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isActive) Color.White else Color(0xFF888888))
                    }
                }
            }
        }
    }
}

@Composable
fun StatReportCard(title: String, amount: String, badge: String, icon: ImageVector, isPrimary: Boolean, badgeColor: Color = if (isPrimary) Color(0xFFA5D6A7) else Color(0xFF2E7D32)) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = if (isPrimary) Indigo900 else Color.White),
        elevation = if (isPrimary) CardDefaults.cardElevation() else CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = if (isPrimary) Color.White.copy(alpha = 0.7f) else Color(0xFF888888))
                Text(text = amount, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = if (isPrimary) Color.White else Indigo900)
                Text(text = badge, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = badgeColor, modifier = Modifier.padding(top = 6.dp))
            }
            Box(
                modifier = Modifier.size(44.dp).background(if (isPrimary) Color.White.copy(alpha = 0.12f) else LightBg, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = if (isPrimary) Color.White else Indigo900, modifier = Modifier.size(22.dp))
            }
        }
    }
}

@Composable
fun SalesTrendChartCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Sales Trend", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    LegendItem("Revenue", Indigo900)
                    LegendItem("Profit", Color(0xFF4CAF50))
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth().height(100.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween) {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val revHeights = listOf(55, 68, 40, 80, 72, 85, 58)
                val profHeights = listOf(18, 22, 14, 26, 24, 28, 20)
                days.forEachIndexed { index, day ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            Box(modifier = Modifier.width(14.dp).height(revHeights[index].dp).background(Indigo900, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)))
                            Box(modifier = Modifier.width(14.dp).height(profHeights[index].dp).background(Color(0xFF4CAF50), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = day, fontSize = 10.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888))
    }
}

@Composable
fun RecentTransactionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Recent Transactions", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                Text(text = "View All", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
            }
            ReportTxItem("INV-2023-084", "Today, 2:45 PM • Cash Payment", "₹12,400", "Completed", Color(0xFFE8F5E9), Color(0xFF2E7D32))
            ReportTxItem("INV-2023-083", "Today, 11:20 AM • UPI Payment", "₹4,200", "Completed", Color(0xFFE3F2FD), Color(0xFF1565C0))
            ReportTxItem("INV-2023-082", "Yesterday, 5:15 PM • Pending Balance", "₹8,900", "Partial", Color(0xFFFFF3E0), Color(0xFFE65100))
        }
    }
}

@Composable
fun ReportTxItem(id: String, meta: String, amount: String, status: String, statusBg: Color, statusColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            modifier = Modifier.size(38.dp).background(statusBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = statusColor, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = id, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
            Text(text = meta, fontSize = 11.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = amount, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Surface(color = statusBg, shape = RoundedCornerShape(6.dp), modifier = Modifier.padding(top = 3.dp)) {
                Text(text = status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = statusColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun SalesReportScreenPreview() {
    SmartBizTheme {
        SalesReportScreen()
    }
}
