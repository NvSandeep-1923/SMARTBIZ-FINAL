package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.TextGray

import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbiz.ui.theme.SmartBizTheme

data class InventoryItem(
    val name: String,
    val category: String,
    val sku: String,
    val stock: Int,
    val salePrice: String,
    val costPrice: String,
    val isLowStock: Boolean,
    val icon: String
)

@Composable
fun InventoryScreen() {
    val items = listOf(
        InventoryItem("Organic Whole Milk 1L", "Dairy", "DR-0042", 4, "₹180", "₹124", true, "🥛"),
        InventoryItem("Premium Basmati Rice 5kg", "Grocery", "GR-0118", 42, "₹750", "₹520", false, "🌾"),
        InventoryItem("Fresh Mango Crate", "Grocery", "GR-0882", 15, "₹2,100", "₹1,500", false, "🥭"),
        InventoryItem("Sparkling Water 500ml", "Beverages", "BV-0091", 8, "₹90", "₹62", true, "💧")
    )

    Scaffold(
        topBar = { InventoryTopBar() },
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
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Inventory Management", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900, modifier = Modifier.padding(bottom = 12.dp))
            
            SearchBar()
            CategoryChips()
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                items(items) { item ->
                    InventoryItemCard(item)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun InventoryScreenPreview() {
    SmartBizTheme {
        InventoryScreen()
    }
}

@Composable
fun InventoryTopBar() {
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
fun SearchBar() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(18.dp))
            Text(text = "Search items, SKU, or brands...", color = Color(0xFFBBBBBB), fontSize = 14.sp)
        }
    }
}

@Composable
fun CategoryChips() {
    val categories = listOf("All", "Grocery", "Dairy", "Beverages", "Electronics")
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 14.dp)
    ) {
        items(categories) { category ->
            val isActive = category == "All"
            Surface(
                color = if (isActive) Indigo900 else Color.White,
                shape = RoundedCornerShape(20.dp),
                border = if (isActive) null else BorderStroke(1.5.dp, Color(0xFFE0E3EF)),
                modifier = Modifier.clickable { /* TODO */ }
            ) {
                Text(
                    text = category,
                    color = if (isActive) Color.White else Color(0xFF888888),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun InventoryItemCard(item: InventoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            if (item.isLowStock) {
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).border(1.5.dp, Color(0xFFFFCDD2), RoundedCornerShape(8.dp)),
                    color = Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Color(0xFFC62828), modifier = Modifier.size(12.dp))
                        Text(text = "Low Stock", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                    }
                }
            }
            
            Column {
                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier.size(68.dp).background(LightBg, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item.icon, fontSize = 28.sp)
                    }
                    Column(modifier = Modifier.padding(top = 2.dp)) {
                        Text(text = item.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900, modifier = Modifier.padding(end = 60.dp))
                        Text(text = "${item.category} • SKU: ${item.sku}", fontSize = 12.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.SemiBold)
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = LightBg, thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Column {
                        Text(text = "Stock Level", fontSize = 12.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold)
                        Text(
                            text = "${item.stock} units",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (item.isLowStock) Color(0xFFC62828) else Color(0xFF2E7D32)
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Sale Price", fontSize = 11.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.SemiBold)
                        Text(text = item.salePrice, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                        Text(text = "Cost: ${item.costPrice}", fontSize = 12.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
