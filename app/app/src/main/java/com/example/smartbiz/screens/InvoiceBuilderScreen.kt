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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun InvoiceBuilderScreen() {
    Scaffold(
        topBar = { InvoiceTopBar() },
        bottomBar = { InvoiceBottomActions() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            CustomerSelectionCard()
            Spacer(modifier = Modifier.height(16.dp))
            InvoiceItemsSection()
            Spacer(modifier = Modifier.height(16.dp))
            AddNoteCard()
            Spacer(modifier = Modifier.height(16.dp))
            InvoiceTotalsCard()
            Spacer(modifier = Modifier.height(80.dp)) // Padding for bottom actions
        }
    }
}

@Composable
fun InvoiceTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBg)
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
        Text(text = "SmartBiz", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Step 2 of 3", fontSize = 13.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold)
            Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun CustomerSelectionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.size(44.dp).background(Color(0xFFE8EAF6), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Rajesh Kumar Enterprises", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                    Text(text = "GSTIN: 27AAAAA0000A1Z5", fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
                }
                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                    border = BorderStroke(1.5.dp, Indigo900),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Change", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Divider(color = LightBg, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "BILLING ADDRESS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFAAAAAA), letterSpacing = 0.6.sp)
                    Text(text = "402, Business Hub,\nAndheri East, Mumbai,\nMH - 400069", fontSize = 13.sp, color = Color(0xFF333333), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 4.dp))
                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(text = "DATE & INVOICE #", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFAAAAAA), letterSpacing = 0.6.sp)
                    Row(modifier = Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "24 Oct 2023 | ", fontSize = 13.sp, color = Color(0xFF333333), fontWeight = FontWeight.SemiBold)
                        Text(text = "INV-2023-089", fontSize = 13.sp, color = Indigo900, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@Composable
fun InvoiceItemsSection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Invoice Items", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Text(text = "Add Item", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        InvoiceItemCard("Premium Cotton Fabric - Blue", "HSN: 5208", "50 mtrs", "₹240.00", "12% (₹1,440)", "₹13,440.00")
        InvoiceItemCard("Nylon Thread Spool", "HSN: 5401", "10 units", "₹85.00", "18% (₹153)", "₹1,003.00")
    }
}

@Composable
fun InvoiceItemCard(name: String, hsn: String, qty: String, price: String, gst: String, subtotal: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 14.dp)) {
                Box(modifier = Modifier.size(40.dp).background(LightBg, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.Inventory, contentDescription = null, tint = Color(0xFF888888), modifier = Modifier.size(20.dp))
                }
                Column {
                    Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                    Text(text = hsn, fontSize = 12.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                InvoiceItemRow("Qty:", qty)
                InvoiceItemRow("Price:", price)
                InvoiceItemRow("GST:", gst)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Subtotal:", fontSize = 13.sp, color = Color(0xFF888888))
                    Text(text = subtotal, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                }
            }
        }
    }
}

@Composable
fun InvoiceItemRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, fontSize = 13.sp, color = Color(0xFF888888))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF333333))
    }
}

@Composable
fun AddNoteCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(2.dp, Color(0xFFE0E3EF))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.EditNote, contentDescription = null, tint = Color(0xFFBBBBBB), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add internal notes or bank details for this invoice.",
                fontSize = 13.sp,
                color = Color(0xFFAAAAAA),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Add Note", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
        }
    }
}

@Composable
fun InvoiceTotalsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            TotalRow("Subtotal", "₹12,850.00")
            TotalRow("Total GST (CGST+SGST)", "₹1,593.00")
            TotalRow("Round Off", "-₹0.00")
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Grand Total", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text(text = "₹14,443.00", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            }
        }
    }
}

@Composable
fun TotalRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
fun InvoiceBottomActions() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Color.White,
        shadowElevation = 20.dp
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBg),
                shape = RoundedCornerShape(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
                    Text(text = "Save Draft", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                }
            }
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.weight(2f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
                shape = RoundedCornerShape(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Text(text = "Save & Preview", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun InvoiceBuilderScreenPreview() {
    SmartBizTheme {
        InvoiceBuilderScreen()
    }
}
