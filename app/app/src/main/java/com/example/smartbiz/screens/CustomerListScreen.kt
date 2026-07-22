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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

data class Customer(
    val name: String,
    val lastDate: String,
    val balance: String,
    val tag: String,
    val amountColor: Color,
    val avatarBg: Color,
    val avatarChar: String,
    val isOverdue: Boolean = false
)

@Composable
fun CustomerListScreen() {
    val customers = listOf(
        Customer("Rajesh Kumar", "Last: 12 Oct 2023", "₹12,450", "YOU WILL GET", Color(0xFF2E7D32), Color(0xFF2E7D32), "R"),
        Customer("Priya Sharma", "Last: 08 Oct 2023", "₹2,800", "YOU WILL GIVE", Color(0xFFE65100), Color(0xFFE65100), "P"),
        Customer("Amit Verma", "Last: 05 Oct 2023", "Settled", "NO PENDING", Color(0xFFAAAAAA), Color(0xFF607D8B), "A"),
        Customer("Sunita General Store", "Last: Today", "₹45,200", "YOU WILL GET", Color(0xFF2E7D32), Color(0xFF5C6BC0), "S"),
        Customer("Modern Wholesalers", "Last: 30 Sep 2023", "₹18,900", "OVERDUE TO PAY", Color(0xFFC62828), Color(0xFFC62828), "!", isOverdue = true)
    )

    Scaffold(
        topBar = { CustomerTopBar() },
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
                .padding(horizontal = 16.dp)
        ) {
            CustomerFilterChips()
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                items(customers) { customer ->
                    CustomerCard(customer)
                }
            }
        }
    }
}

@Composable
fun CustomerTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(18.dp))
                Text(text = "Search customers...", color = Color(0xFFBBBBBB), fontSize = 14.sp)
            }
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(Indigo900, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.PersonAddAlt1, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
fun CustomerFilterChips() {
    val filters = listOf("All", "You will Give", "You will Get")
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 14.dp)
    ) {
        filters.forEach { filter ->
            val isActive = filter == "All"
            Surface(
                color = if (isActive) Indigo900 else Color.White,
                shape = RoundedCornerShape(20.dp),
                border = if (isActive) null else BorderStroke(1.5.dp, Color(0xFFD0D4E8)),
                modifier = Modifier.clickable { /* TODO */ }
            ) {
                Text(
                    text = filter,
                    color = if (isActive) Color.White else Color(0xFF888888),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp)
                )
            }
        }
    }
}

@Composable
fun CustomerCard(customer: Customer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (customer.isOverdue) 1.5.dp else 0.dp,
                color = if (customer.isOverdue) Color(0xFFFFCDD2) else Color.Transparent,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(bottom = 12.dp)) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(customer.avatarBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = customer.avatarChar, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = customer.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                    Text(text = customer.lastDate, fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
                }
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(16.dp))
            }
            
            Divider(color = LightBg, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Balance", fontSize = 12.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold)
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = customer.balance, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = customer.amountColor)
                    Text(text = customer.tag, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = customer.amountColor, letterSpacing = 0.4.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun CustomerListScreenPreview() {
    SmartBizTheme {
        CustomerListScreen()
    }
}
