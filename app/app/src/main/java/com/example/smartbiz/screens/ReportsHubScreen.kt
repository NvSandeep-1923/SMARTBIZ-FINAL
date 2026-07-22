package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun ReportsHubScreen() {
    Scaffold(
        topBar = { ReportsHubTopBar() },
        bottomBar = { DashboardBottomNav() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Indigo900,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Assessment, contentDescription = null)
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
            ReportsHeroCard()
            AIInsightsBriefSection()
            ReportsTwoColSection()
            FinancialReportsSection()
            InventoryReportsSection()
            PerformanceVisualizerCard()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ReportsHubTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Indigo900)
        Text(text = "Business Reports", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Indigo900)
            Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900)
        }
    }
}

@Composable
fun ReportsHeroCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Total Sales this Month", fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
            Text(text = "₹ 4,82,950", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(vertical = 6.dp))
            Surface(
                color = Color(0xFF4CAF50).copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "↑ 12.5%  vs last month", fontSize = 12.sp, color = Color(0xFFA5D6A7), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
    }
}

@Composable
fun AIInsightsBriefSection() {
    Column(modifier = Modifier.padding(bottom = 14.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Indigo900, modifier = Modifier.size(14.dp))
                Text(text = "AI Insights", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
            }
            Text(text = "View Details", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8F0))
        ) {
            Row(modifier = Modifier.padding(14.dp, 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.size(38.dp).background(Color(0xFFFFE0B2), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.QueryStats, contentDescription = null, tint = Color(0xFFE65100), modifier = Modifier.size(20.dp))
                }
                Column {
                    Text(text = "Sales Forecast", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                    Text(text = "Expect 15% growth in next 7 days", fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
                }
            }
        }
    }
}

@Composable
fun ReportsTwoColSection() {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        ReportsMiniCard(icon = Icons.Default.Group, title = "Customer Analysis", color = Color(0xFFE8EAF6), iconColor = Indigo900, modifier = Modifier.weight(1f))
        ReportsMiniCard(icon = Icons.Default.Groups, title = "Retention Rate", color = Color(0xFFE8F5E9), iconColor = Color(0xFF2E7D32), modifier = Modifier.weight(1f))
    }
}

@Composable
fun ReportsMiniCard(icon: ImageVector, title: String, color: Color, iconColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.size(38.dp).background(color, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
        }
    }
}

@Composable
fun FinancialReportsSection() {
    Column(modifier = Modifier.padding(bottom = 14.dp)) {
        Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Indigo900, modifier = Modifier.size(18.dp))
            Text(text = "Financial", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        }
        ReportsListItem("Sales Report", "Summary of all transactions", Icons.Default.BarChart, Color(0xFFE8EAF6), Indigo900)
        ReportsListItem("P&L Statement", "Profit and loss analysis", Icons.Default.CurrencyRupee, Color(0xFFE8F5E9), Color(0xFF2E7D32))
        ReportsListItem("Daybook", "Daily transaction records", Icons.Default.CalendarToday, Color(0xFFFFEBEE), Color(0xFFC62828))
    }
}

@Composable
fun ReportsListItem(name: String, sub: String, icon: ImageVector, iconBg: Color, iconColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(40.dp).background(iconBg, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(text = sub, fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun InventoryReportsSection() {
    Column(modifier = Modifier.padding(bottom = 14.dp)) {
        Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(imageVector = Icons.Default.Inventory, contentDescription = null, tint = Indigo900, modifier = Modifier.size(18.dp))
            Text(text = "Inventory", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InventoryReportCard("Stock Report", "Full inventory valuation", Icons.Default.Inventory2, Color(0xFFE8EAF6), Indigo900, modifier = Modifier.weight(1f))
            InventoryReportCard("Low Stock", "12 items need reorder", Icons.Default.Warning, Color(0xFFFFCDD2), Color(0xFFC62828), isAlert = true, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun InventoryReportCard(name: String, sub: String, icon: ImageVector, iconBg: Color, iconColor: Color, isAlert: Boolean = false, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isAlert) Color(0xFFFFEBEE) else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.size(34.dp).background(iconBg, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isAlert) Color(0xFFC62828) else Color(0xFF222222))
            Text(text = sub, fontSize = 11.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun PerformanceVisualizerCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(text = "Weekly Performance Visualizer", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
            Text(text = "Open Chart Builder", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Indigo900, modifier = Modifier.padding(top = 4.dp, bottom = 14.dp))
            Row(modifier = Modifier.fillMaxWidth().height(60.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val heights = listOf(35, 50, 28, 58, 44, 38, 30)
                heights.forEachIndexed { index, h ->
                    Box(modifier = Modifier.weight(1f).height(h.dp).background(if (index == 3) Indigo900 else Color(0xFFC5CAE9), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)))
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ReportsHubScreenPreview() {
    SmartBizTheme {
        ReportsHubScreen()
    }
}
