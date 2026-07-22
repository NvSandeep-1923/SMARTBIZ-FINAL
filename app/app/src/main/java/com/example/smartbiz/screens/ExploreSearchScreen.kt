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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun ExploreSearchScreen() {
    Scaffold(
        topBar = { ExploreTopBar() },
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
            ExploreSearchBar()
            ExploreFilterChips()
            RecentSearchesCard()
            ProTipBanner()
            
            ExploreSectionLabel("Customers")
            CustomerResultCard("V", "Venkatesh Traders", "Kormangala, Bangalore", "₹45,200", "Balance Due", Color(0xFF2E7D32))
            CustomerResultCard("A", "Aman General Stores", "Indiranagar, Bangalore", "₹0", "Settled", Color(0xFF999999))
            
            Spacer(modifier = Modifier.height(10.dp))
            ExploreSectionLabel("Items")
            ItemResultCard("Organic Rice (5kg)", "Stock: 124 units", "₹450.00", "IN STOCK", Color(0xFF2E7D32), Color(0xFFE8F5E9))
            ItemResultCard("Mustard Oil (1L)", "Stock: 8 units", "₹185.00", "LOW STOCK", Color(0xFFE65100), Color(0xFFFFF3E0))
            
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                ExploreSectionLabel("Invoices")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "View All", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
                }
            }
            InvoiceResultCard("INV-2024-0092", "12 Oct 2023 • Aman Stores", "₹12,850", "UNPAID", Color(0xFFC62828), Color(0xFFFFEBEE))
            InvoiceResultCard("INV-2024-0088", "08 Oct 2023 • Cash Sale", "₹3,400", "PAID", Color(0xFF2E7D32), Color(0xFFE8F5E9))
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ExploreTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Indigo900),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "SB", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ExploreSearchBar() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(2.dp, Indigo900),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFF888888), modifier = Modifier.size(18.dp))
            Text(text = "Search customers, items, or invo...", color = Color(0xFFBBBBBB), fontSize = 14.sp, modifier = Modifier.weight(1f))
            Surface(color = LightBg, shape = RoundedCornerShape(6.dp), border = BorderStroke(1.dp, Color(0xFFD0D4E8))) {
                Text(text = "⌘ K", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp))
            }
        }
    }
}

@Composable
fun ExploreFilterChips() {
    val chips = listOf("All" to Icons.Default.Category, "Customers" to Icons.Default.Person, "Items" to Icons.Default.Inventory, "Invoices" to Icons.Default.Description)
    Row(
        modifier = Modifier.padding(bottom = 16.dp).horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chips.forEach { (label, icon) ->
            val isActive = label == "All"
            Surface(
                color = if (isActive) Indigo900 else Color.White,
                shape = RoundedCornerShape(20.dp),
                border = if (isActive) null else BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
                modifier = Modifier.clickable { /* TODO */ }
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = icon, contentDescription = null, tint = if (isActive) Color.White else Color(0xFF888888), modifier = Modifier.size(14.dp))
                    Text(text = label, color = if (isActive) Color.White else Color(0xFF888888), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RecentSearchesCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "RECENT SEARCHES", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF888888), letterSpacing = 0.7.sp)
                Text(text = "Clear", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Indigo900)
            }
            RecentSearchItem("Organic Rice Bulk")
            RecentSearchItem("Venkatesh Traders")
            RecentSearchItem("INV-2024-0092")
        }
    }
}

@Composable
fun RecentSearchItem(text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(imageVector = Icons.Default.History, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(16.dp))
        Text(text = text, fontSize = 14.sp, color = Color(0xFF444444), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ProTipBanner() {
    Card(
        modifier = Modifier.fillMaxWidth().height(110.dp).padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Bottom) {
            Text(text = "PRO TIP", fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = Color.White.copy(alpha = 0.7f), letterSpacing = 0.8.sp)
            Text(text = "Use shortcuts to search faster", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun ExploreSectionLabel(text: String) {
    Text(text = text.uppercase(), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF888888), letterSpacing = 0.7.sp, modifier = Modifier.padding(bottom = 10.dp))
}

@Composable
fun CustomerResultCard(iconChar: String, name: String, sub: String, amount: String, tag: String, amountColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(42.dp).background(if (iconChar == "V") Color(0xFF2E7D32) else Color(0xFFE65100), CircleShape), contentAlignment = Alignment.Center) {
                Text(text = iconChar, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                Text(text = sub, fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = amountColor)
                Text(text = tag, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Composable
fun ItemResultCard(name: String, stock: String, price: String, badge: String, badgeColor: Color, badgeBg: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(40.dp).background(Color(0xFFE8EAF6), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.Inventory, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(text = stock, fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = price, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                Surface(color = badgeBg, shape = RoundedCornerShape(20.dp)) {
                    Text(text = badge, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = badgeColor, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))
                }
            }
        }
    }
}

@Composable
fun InvoiceResultCard(num: String, meta: String, amount: String, status: String, statusColor: Color, statusBg: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(40.dp).background(Color(0xFFE8EAF6), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = num, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(text = meta, fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                Surface(color = statusBg, shape = RoundedCornerShape(6.dp), modifier = Modifier.padding(top = 3.dp)) {
                    Text(text = status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = statusColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
            }
            Box(modifier = Modifier.size(28.dp).background(LightBg, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = Color(0xFF888888), modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ExploreSearchScreenPreview() {
    SmartBizTheme {
        ExploreSearchScreen()
    }
}
