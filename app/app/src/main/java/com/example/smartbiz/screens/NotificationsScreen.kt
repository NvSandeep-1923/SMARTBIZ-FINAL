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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme

@Composable
fun NotificationsScreen() {
    Scaffold(
        topBar = { NotificationsTopBar() },
        bottomBar = { DashboardBottomNav() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            NotificationsTabs()
            
            NotificationCard(
                title = "AI Credit Risk Alert",
                badge = "High Risk",
                badgeColor = Color(0xFFC62828),
                badgeBg = Color(0xFFFFEBEE),
                icon = Icons.Default.Warning,
                iconColor = Color(0xFFF44336),
                iconBg = Color(0xFFFFEBEE),
                time = "2m ago",
                unread = true,
                borderLeftColor = Color(0xFFF44336),
                content = buildAnnotatedString {
                    append("Customer ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("\"Everest Trading\"") }
                    append(" has exceeded credit limit by 25%. AI suggests halting further credit sales.")
                },
                actions = {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Indigo900), shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), modifier = Modifier.height(34.dp)) {
                        Text("Review Account", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )

            NotificationCard(
                title = "Payment Due Reminder",
                icon = Icons.Default.AccountBalanceWallet,
                iconColor = Color(0xFF4CAF50),
                iconBg = Color(0xFFE8F5E9),
                time = "1h ago",
                unread = true,
                borderLeftColor = Color(0xFF4CAF50),
                content = buildAnnotatedString {
                    append("Invoice ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("#8842") }
                    append(" for ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("₹45,200") }
                    append(" from ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("\"City Wholesale\"") }
                    append(" is due in 24 hours.")
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), modifier = Modifier.height(34.dp)) {
                            Text("Record Payment", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(onClick = {}, border = BorderStroke(1.5.dp, Color(0xFFD0D4E8)), shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), modifier = Modifier.height(34.dp)) {
                            Text("View Invoice", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Indigo900)
                        }
                    }
                }
            )

            NotificationCard(
                title = "Low Stock: Basmati Rice",
                icon = Icons.Default.Inventory,
                iconColor = Color(0xFFFF9800),
                iconBg = Color(0xFFFFF3E0),
                time = "4h ago",
                unread = true,
                borderLeftColor = Color(0xFFFF9800),
                content = buildAnnotatedString {
                    append("Stock level for ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("\"Basmati Premium 5kg\"") }
                    append(" is below reorder point ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("(5 units remaining)") }
                    append(".")
                },
                actions = {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), modifier = Modifier.height(34.dp)) {
                        Text("Create Purchase Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )

            NotificationCard(
                title = "Payment Received",
                icon = Icons.Default.CheckCircle,
                iconColor = Color(0xFFAAAAAA),
                iconBg = Color(0xFFF5F5F5),
                time = "Yesterday",
                unread = false,
                content = buildAnnotatedString {
                    append("Successful payment of ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("₹12,000") }
                    append(" received from ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) { append("\"Malik General Store\"") }
                    append(".")
                }
            )
            
            Text(text = "Notification Insights", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900, modifier = Modifier.padding(top = 16.dp, bottom = 10.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                InsightCard(title = "Unresolved Alerts", value = "03", sub = "↑ +2 from yesterday", color = Indigo900, modifier = Modifier.weight(1f))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    InsightCard(title = "EFFICIENCY SCORE", value = "98%", color = Color(0xFFE8F5E9), textColor = Color(0xFF2E7D32))
                    InsightCard(title = "RESPONSE TIME", value = "12m", color = Color(0xFFFFF3E0), textColor = Color(0xFFE65100))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun NotificationsTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null, tint = Indigo900, modifier = Modifier.size(22.dp))
        Text(text = "Notifications", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Text(text = "Mark all as read", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Indigo900)
    }
}

@Composable
fun NotificationsTabs() {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        val tabs = listOf("All", "AI Alerts", "Reminders")
        tabs.forEach { tab ->
            val isActive = tab == "All"
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = tab, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isActive) Indigo900 else Color(0xFFAAAAAA), modifier = Modifier.padding(vertical = 10.dp))
                if (isActive) Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Indigo900, RoundedCornerShape(2.dp)))
            }
        }
    }
}

@Composable
fun NotificationCard(
    title: String,
    content: androidx.compose.ui.text.AnnotatedString,
    icon: ImageVector,
    iconColor: Color,
    iconBg: Color,
    time: String,
    unread: Boolean,
    badge: String? = null,
    badgeColor: Color = Color.Transparent,
    badgeBg: Color = Color.Transparent,
    borderLeftColor: Color = Color.Transparent,
    actions: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .alpha(if (unread) 1f else 0.7f),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box {
            if (unread) Box(modifier = Modifier.align(Alignment.CenterStart).width(4.dp).height(80.dp).background(borderLeftColor))
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.size(44.dp).background(iconBg, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF222222))
                            if (badge != null) {
                                Surface(color = badgeBg, shape = RoundedCornerShape(6.dp)) {
                                    Text(text = badge, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = badgeColor, modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp))
                                }
                            }
                        }
                        Text(text = content, fontSize = 13.sp, color = Color(0xFF666666), lineHeight = 18.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                    Text(text = time, fontSize = 11.sp, color = Color(0xFFAAAAAA), fontWeight = FontWeight.Bold)
                }
                if (actions != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    actions()
                }
            }
        }
    }
}

@Composable
fun InsightCard(title: String, value: String, sub: String? = null, color: Color, textColor: Color = Color.White, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor.copy(alpha = 0.7f))
            Text(text = value, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = textColor, modifier = Modifier.padding(vertical = 2.dp))
            if (sub != null) {
                Text(text = sub, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor.copy(alpha = 0.6f))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun NotificationsScreenPreview() {
    SmartBizTheme {
        NotificationsScreen()
    }
}
