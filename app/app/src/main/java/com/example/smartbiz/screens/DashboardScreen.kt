package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.TextGray

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = { DashboardTopBar() },
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
            HeroCard()
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(label = "Total Udhar", amount = "₹ 12,400", icon = Icons.Default.Description, color = Color(0xFFFFF3E0), iconColor = Color(0xFFE65100), modifier = Modifier.weight(1f))
                StatCard(label = "Stock Alerts", amount = "8 Items Low", icon = Icons.Default.Warning, color = Color(0xFFFFEBEE), iconColor = Color(0xFFC62828), modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            CashCard()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Quick Actions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Indigo900, modifier = Modifier.padding(bottom = 12.dp))
            QuickActionsRow()
            Spacer(modifier = Modifier.height(20.dp))
            WeeklyTrendCard()
            Spacer(modifier = Modifier.height(20.dp))
            TransactionsSection()
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun DashboardScreenPreview() {
    SmartBizTheme {
        DashboardScreen()
    }
}

@Composable
fun DashboardTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBg)
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Indigo900)
        Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900)
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Indigo900),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "SK", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun HeroCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Box(modifier = Modifier.padding(22.dp)) {
            Column {
                Text(text = "Total Sales (Today)", fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
                Text(text = "₹ 42,850", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "↑ +12% from yesterday",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun StatCard(label: String, amount: String, icon: ImageVector, color: Color, iconColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(18.dp, 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = label, fontSize = 12.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold)
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Text(text = amount, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        }
    }
}

@Composable
fun CashCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Row(
            modifier = Modifier.padding(18.dp, 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Cash in Hand", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold)
                Text(text = "₹ 28,120", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            }
            Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun QuickActionsRow() {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickActionBtn(icon = Icons.Default.PersonAdd, label = "Add Customer")
        QuickActionBtn(icon = Icons.Default.Description, label = "New Invoice")
        QuickActionBtn(icon = Icons.Default.AddBox, label = "Add Stock")
        QuickActionBtn(icon = Icons.Default.Assessment, label = "Reports")
    }
}

@Composable
fun QuickActionBtn(icon: ImageVector, label: String) {
    Card(
        modifier = Modifier.width(76.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp, 12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(24.dp))
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF444444), textAlign = TextAlign.Center, lineHeight = 14.sp)
        }
    }
}

@Composable
fun WeeklyTrendCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Weekly Sales Trend", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                Surface(
                    modifier = Modifier.border(1.5.dp, Color(0xFFE0E3EF), RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White
                ) {
                    Row(modifier = Modifier.padding(10.dp, 5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Last 7 Days", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Indigo900, modifier = Modifier.size(14.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth().height(100.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween) {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val heights = listOf(28, 44, 20, 48, 32, 38, 12)
                days.forEachIndexed { index, day ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {
                        Box(modifier = Modifier.fillMaxWidth(0.6f).height(heights[index].dp).background(Indigo900, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)))
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = day, fontSize = 10.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionsSection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Recent Transactions", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
            Text(text = "View All", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Indigo900)
        }
        Spacer(modifier = Modifier.height(12.dp))
        TransactionItem(name = "Sunil Kumar", time = "Today, 2:45 PM", amount = "+ ₹1,200", method = "CASH", icon = Icons.Default.Receipt, iconBg = Color(0xFFE8F5E9), iconTint = Color(0xFF2E7D32), amountColor = Color(0xFF2E7D32))
        TransactionItem(name = "Rakesh General Store", time = "Yesterday, 5:20 PM", amount = "- ₹4,500", method = "UDHAR", icon = Icons.Default.CreditCard, iconBg = Color(0xFFFFF3E0), iconTint = Color(0xFFE65100), amountColor = Color(0xFFC62828))
        TransactionItem(name = "Inventory Restock", time = "22 Oct, 11:30 AM", amount = "- ₹15,000", method = "BANK", icon = Icons.Default.LocalShipping, iconBg = Color(0xFFE8EAF6), iconTint = Indigo900, amountColor = Color(0xFFC62828))
    }
}

@Composable
fun TransactionItem(name: String, time: String, amount: String, method: String, icon: ImageVector, iconBg: Color, iconTint: Color, amountColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(40.dp).background(iconBg, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(text = time, fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = amountColor)
                Text(text = method, fontSize = 10.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}
