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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun CustomerLedgerScreen() {
    Scaffold(
        topBar = { LedgerTopBar() },
        bottomBar = { LedgerBottomActions() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            LedgerHeroCard()
            SummaryRow()
            LedgerTransactionsSection()
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFCCCCCC)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Payment Visual Placeholder", color = Color(0xFF888888), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun LedgerTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBg)
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.size(36.dp).background(Indigo900, CircleShape), contentAlignment = Alignment.Center) {
                Text(text = "RS", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
            }
            Column {
                Text(text = "Rahul Sharma", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
                Text(text = "Last activity: 2 hours ago", fontSize = 11.sp, color = Color(0xFFAAAAAA))
            }
        }
        Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun LedgerHeroCard() {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp).fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Indigo900)
    ) {
        Column(modifier = Modifier.padding(24.dp, 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "CURRENT BALANCE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f), letterSpacing = 0.8.sp)
            Text(text = "₹5,400", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF4CAF50), modifier = Modifier.padding(vertical = 8.dp))
            Surface(
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "You will get", fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
            Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceAround) {
                LedgerActionIcon(Icons.Default.Call, "Call")
                LedgerActionIcon(Icons.Default.Chat, "Message")
                LedgerActionIcon(Icons.Default.PhoneIphone, "WhatsApp")
            }
        }
    }
}

@Composable
fun LedgerActionIcon(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White.copy(alpha = 0.8f))
    }
}

@Composable
fun SummaryRow() {
    Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SummaryCard("Total Gave", "₹12,850", Color(0xFFE65100), modifier = Modifier.weight(1f))
        SummaryCard("Total Got", "₹7,450", Color(0xFF2E7D32), modifier = Modifier.weight(1f))
    }
}

@Composable
fun SummaryCard(label: String, amount: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 12.sp, color = Color(0xFF888888), fontWeight = FontWeight.SemiBold)
            Text(text = amount, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = color, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun LedgerTransactionsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Transactions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Indigo900)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallIconBtn(Icons.Default.FilterList)
                SmallIconBtn(Icons.Default.FileDownload)
            }
        }
        
        LedgerDateChip("OCTOBER 24, 2023")
        LedgerTxItem("Grocery Items - Bulk", "11:45 AM • Balance: ₹5,400", "₹2,500", "You Gave", Color(0xFFE65100))
        LedgerTxItem("Partial Cash Payment", "10:15 AM • Balance: ₹2,900", "₹1,500", "You Got", Color(0xFF2E7D32))
        
        LedgerDateChip("OCTOBER 22, 2023")
        LedgerTxItem("Rice & Flour 50kg", "04:30 PM • Balance: ₹4,400", "₹4,400", "You Gave", Color(0xFFE65100))
    }
}

@Composable
fun SmallIconBtn(icon: ImageVector) {
    Surface(
        modifier = Modifier.size(34.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun LedgerDateChip(date: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        color = Color(0xFFE8EAF6),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = date,
            modifier = Modifier.padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Indigo900,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun LedgerTxItem(name: String, time: String, amount: String, label: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp, 16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier.size(38.dp).background(color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (label == "You Gave") Icons.Outlined.TrendingUp else Icons.Outlined.TrendingDown,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Text(text = time, fontSize = 11.sp, color = Color(0xFFAAAAAA), modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = color)
                Text(text = label, fontSize = 11.sp, color = color, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Composable
fun LedgerBottomActions() {
    Row(modifier = Modifier.fillMaxWidth().height(56.dp)) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f).fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Text(text = "YOU GAVE", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f).fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Text(text = "YOU GOT", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun CustomerLedgerScreenPreview() {
    SmartBizTheme {
        CustomerLedgerScreen()
    }
}
