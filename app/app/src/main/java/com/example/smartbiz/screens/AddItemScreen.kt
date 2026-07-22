package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme
import androidx.compose.foundation.BorderStroke

@Composable
fun AddItemScreen() {
    Scaffold(
        topBar = { AddItemTopBar() },
        bottomBar = { AddItemBottomActions() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            BasicDetailsSection()
            PricingInformationSection()
            StockInventorySection()
            Spacer(modifier = Modifier.height(180.dp)) // Space for bottom actions
        }
    }
}

@Composable
fun AddItemTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBg)
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
        Text(text = "Add New Item", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun BasicDetailsSection() {
    AddItemSectionCard(title = "Basic Details", icon = Icons.Default.Description) {
        AddItemField(label = "Item Name", placeholder = "e.g. Premium Basmati Rice")
        AddItemDropdown(label = "Category", options = listOf("Grains & Pulses", "Dairy", "Beverages"))
        AddItemDropdown(label = "Unit", options = listOf("kg", "pcs", "litre", "box"))
        AddItemField(label = "HSN Code", placeholder = "8-digit code")
        AddItemDropdown(label = "GST Rate (%)", options = listOf("0%", "5%", "12%", "18%", "28%"))
    }
}

@Composable
fun PricingInformationSection() {
    AddItemSectionCard(title = "Pricing Information", icon = Icons.Default.AccountBalanceWallet) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AddItemField(label = "Purchase Price (₹)", placeholder = "0.00", isPrice = true)
            }
            Column(modifier = Modifier.weight(1f)) {
                AddItemField(label = "Sales Price (₹)", placeholder = "0.00", isPrice = true)
            }
        }
    }
}

@Composable
fun StockInventorySection() {
    AddItemSectionCard(title = "Stock Inventory", icon = Icons.Default.List) {
        AddItemField(label = "Opening Stock", placeholder = "0", keyboardType = KeyboardType.Number)
        AddItemField(label = "Low Stock Alert level", placeholder = "Notify when below...", keyboardType = KeyboardType.Number)
    }
}

@Composable
private fun AddItemSectionCard(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            }
            content()
        }
    }
}

@Composable
fun AddItemField(label: String, placeholder: String, isPrice: Boolean = false, keyboardType: KeyboardType = KeyboardType.Text) {
    var text by remember { mutableStateOf("") }
    Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
    
    if (isPrice) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F9FF), RoundedCornerShape(12.dp))
                .border(1.5.dp, Color(0xFFE0E3EF), RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "₹",
                modifier = Modifier
                    .background(Color(0xFFF0F2F8))
                    .padding(13.dp, 12.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo900
            )
            Box(modifier = Modifier.width(1.5.dp).height(44.dp).background(Color(0xFFE0E3EF)))
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f).padding(13.dp, 12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) Text(text = placeholder, color = Color(0xFFBBBBBB), fontSize = 14.sp)
                    innerTextField()
                }
            )
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF8F9FF),
            border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.padding(13.dp, 14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) Text(text = placeholder, color = Color(0xFFBBBBBB), fontSize = 14.sp)
                    innerTextField()
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
private fun AddItemDropdown(label: String, options: List<String>) {
    Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FF),
        border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
    ) {
        Row(
            modifier = Modifier.padding(13.dp, 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = options.first(), fontSize = 14.sp, color = Color(0xFF333333))
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF888888), modifier = Modifier.size(16.dp))
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
fun AddItemBottomActions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .padding(bottom = 72.dp) // Height of bottom nav
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Text(text = "Save Product", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        TextButton(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text(text = "Cancel", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun AddItemScreenPreview() {
    SmartBizTheme {
        AddItemScreen()
    }
}
