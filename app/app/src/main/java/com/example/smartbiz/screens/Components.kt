package com.example.smartbiz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900

@Composable
fun DashboardBottomNav(selectedIndex: Int = 0) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf("Dashboard", "Customers", "Billing", "Inventory", "Reports")
        val icons = listOf(Icons.Default.Dashboard, Icons.Default.People, Icons.Default.Description, Icons.Default.Inventory, Icons.Default.Assessment)
        
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = { /* TODO */ },
                icon = { Icon(imageVector = icons[index], contentDescription = null) },
                label = { Text(text = item, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Indigo900,
                    selectedTextColor = Indigo900,
                    indicatorColor = Color(0xFFE8F0FE),
                    unselectedIconColor = Color(0xFFBBBBBB),
                    unselectedTextColor = Color(0xFFBBBBBB)
                )
            )
        }
    }
}
