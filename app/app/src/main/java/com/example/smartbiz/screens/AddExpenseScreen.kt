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
fun AddExpenseScreen() {
    Scaffold(
        topBar = { AddExpenseTopBar() },
        bottomBar = { AddExpenseBottomActions() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth().height(4.dp).background(Color(0xFFE0E3EF), RoundedCornerShape(2.dp))) {
                Box(modifier = Modifier.fillMaxWidth(0.33f).fillMaxHeight().background(Indigo900, RoundedCornerShape(2.dp)))
            }
            
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                AddExpenseSectionCard(title = "Core Details", icon = Icons.Default.Inventory) {
                    ExpenseDropdown(label = "Category", icon = Icons.Default.List, placeholder = "Select Category")
                    ExpenseAmountField(label = "Amount (₹)")
                }
                
                AddExpenseSectionCard(title = "Transaction Log", icon = Icons.Default.History) {
                    ExpenseDropdown(label = "Date", icon = Icons.Default.CalendarToday, placeholder = "mm/dd/yyyy")
                    Text(text = "Payment Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PaymentModeItem(label = "Cash", icon = Icons.Default.AccountBalanceWallet, isActive = true, modifier = Modifier.weight(1f))
                        PaymentModeItem(label = "Bank", icon = Icons.Default.AccountBalance, isActive = false, modifier = Modifier.weight(1f))
                        PaymentModeItem(label = "UPI", icon = Icons.Default.QrCode, isActive = false, modifier = Modifier.weight(1f))
                    }
                }
                
                AddExpenseSectionCard(title = "Documentation", icon = Icons.Default.Description) {
                    Text(text = "Expense Notes", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(90.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF8F9FF),
                        border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
                    ) {
                        BasicTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.padding(13.dp, 14.dp),
                            decorationBox = { innerTextField ->
                                if (true) Text(text = "Add details about this purchase...", color = Color(0xFFBBBBBB), fontSize = 14.sp)
                                innerTextField()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "Attach Bill/Receipt", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFAFBFF),
                        border = BorderStroke(2.dp, Color(0xFFD0D4E8))
                    ) {
                        Column(
                            modifier = Modifier.padding(28.dp, 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(32.dp))
                            Text(text = "Tap to upload image or PDF", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888))
                            Text(text = "Max size: 5MB", fontSize = 11.sp, color = Color(0xFFAAAAAA))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(180.dp))
        }
    }
}

@Composable
fun AddExpenseTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBg)
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
        Text(text = "Add Expense", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun ExpenseDropdown(label: String, icon: ImageVector, placeholder: String) {
    Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FF),
        border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
    ) {
        Row(
            modifier = Modifier.padding(13.dp, 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = placeholder, fontSize = 14.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(18.dp))
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
fun ExpenseAmountField(label: String) {
    Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
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
                .padding(13.dp, 14.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Indigo900
        )
        Box(modifier = Modifier.width(1.5.dp).height(48.dp).background(Color(0xFFE0E3EF)))
        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.weight(1f).padding(13.dp, 14.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                if (true) Text(text = "0.00", color = Color(0xFFCCCCCC), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                innerTextField()
            }
        )
    }
}

@Composable
fun PaymentModeItem(label: String, icon: ImageVector, isActive: Boolean, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.clickable { /* TODO */ },
        shape = RoundedCornerShape(12.dp),
        color = if (isActive) Color(0xFFE8EAF6) else Color.White,
        border = BorderStroke(1.5.dp, if (isActive) Indigo900 else Color(0xFFE0E3EF))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = if (isActive) Indigo900 else Color(0xFFAAAAAA), modifier = Modifier.size(20.dp))
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isActive) Indigo900 else Color(0xFFAAAAAA))
        }
    }
}

@Composable
fun AddExpenseBottomActions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .padding(bottom = 72.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Text(text = "Save Expense", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AddExpenseSectionCard(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
                Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900, letterSpacing = 0.6.sp)
            }
            content()
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun AddExpenseScreenPreview() {
    SmartBizTheme {
        AddExpenseScreen()
    }
}
