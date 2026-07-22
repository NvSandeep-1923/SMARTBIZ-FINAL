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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun ExpenseTrackerScreen() {
    Scaffold(
        topBar = { ExpenseTopBar() },
        bottomBar = { DashboardBottomNav() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White,
                shape = CircleShape,
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
            ExpenseHeroCard()
            ExpenseChipsRow()
            
            Text(text = "Today, 24 Oct", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), modifier = Modifier.padding(bottom = 8.dp))
            ExpenseItem("Rent", "Main Office Space - Oct", "₹25,000", "10:30 AM", Icons.Default.Business, Color(0xFFFFF3E0), Color(0xFFE65100))
            ExpenseItem("Utilities", "Electricity & Water Bill", "₹4,200", "09:15 AM", Icons.Default.FlashOn, Color(0xFFE8F5E9), Color(0xFF2E7D32))
            
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Yesterday, 23 Oct", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), modifier = Modifier.padding(bottom = 8.dp))
            ExpenseItem("Salary", "Staff Payment - Rahul K.", "₹12,000", "05:45 PM", Icons.Default.Work, Color(0xFFEDE7F6), Color(0xFF7B1FA2))
            ExpenseItem("Stock", "Wholesale Grains Restock", "₹6,500", "11:00 AM", Icons.Default.Inventory, Color(0xFFFFF3E0), Color(0xFFF57C00))
            ExpenseItem("Misc", "Cleaning Supplies", "₹550", "10:20 AM", Icons.Default.MoreHoriz, Color(0xFFF5F5F5), Color(0xFF888888))
            
            Text(
                text = "Showing expenses for the last 30 days",
                fontSize = 12.sp,
                color = Color(0xFFAAAAAA),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun ExpenseTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Indigo900)
        Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Indigo900)
            Icon(imageVector = Icons.Default.FilterList, contentDescription = null, tint = Indigo900)
            Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900)
        }
    }
}

@Composable
fun ExpenseHeroCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "TOTAL EXPENSES THIS MONTH", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f), letterSpacing = 0.7.sp)
            Text(text = "₹48,250", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(vertical = 6.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFFFFCC80), modifier = Modifier.size(14.dp))
                Text(text = "12% higher than last month", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFCC80))
            }
        }
    }
}

@Composable
fun ExpenseChipsRow() {
    val chips = listOf("All Expenses", "Pending", "Rent", "Salary")
    Row(
        modifier = Modifier.padding(bottom = 14.dp).horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chips.forEach { chip ->
            val isActive = chip == "All Expenses"
            Surface(
                color = if (isActive) Indigo900 else Color.White,
                shape = RoundedCornerShape(20.dp),
                border = if (isActive) null else BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
                modifier = Modifier.clickable { /* TODO */ }
            ) {
                Text(
                    text = chip,
                    color = if (isActive) Color.White else Color(0xFF888888),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp)
                )
            }
        }
    }
}

@Composable
fun ExpenseItem(name: String, sub: String, amount: String, time: String, icon: ImageVector, iconBg: Color, iconTint: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(42.dp).background(iconBg, RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(text = sub, fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFE65100))
                Text(text = time, fontSize = 11.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ExpenseTrackerScreenPreview() {
    SmartBizTheme {
        ExpenseTrackerScreen()
    }
}
