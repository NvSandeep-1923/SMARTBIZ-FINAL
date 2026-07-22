package com.example.smartbiz.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartbiz.data.models.*
import com.example.smartbiz.ui.viewmodel.AiInsightsState
import com.example.smartbiz.ui.viewmodel.AiInsightsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiInsightsScreen(
    viewModel: AiInsightsViewModel = viewModel(),
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(22.dp))
                        Text(translationViewModel.t("AI Business Insights"), fontWeight = FontWeight.ExtraBold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.load() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFF5F6FA))) {
            when (val state = uiState) {
                is AiInsightsState.Loading -> AiLoadingScreen()
                is AiInsightsState.Error   -> AiErrorScreen(state.message, translationViewModel) { viewModel.load() }
                is AiInsightsState.Success -> AiInsightsContent(state.insights, translationViewModel)
            }
        }
    }
}

// ─── Loading / Error ─────────────────────────────────────────────────────────
@Composable
fun AiLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF1A237E), modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Analysing your business data...", color = Color.Gray, fontSize = 15.sp)
        Text("This may take a moment", color = Color.LightGray, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun AiErrorScreen(
    message: String,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.WifiOff, contentDescription = null, modifier = Modifier.size(56.dp), tint = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(translationViewModel.t("Could not load insights"), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF444444))
        Text(message, color = Color.Gray, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(translationViewModel.t("Retry"))
        }
    }
}

// ─── Main Content ─────────────────────────────────────────────────────────────
@Composable
fun AiInsightsContent(
    insights: AiInsights,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel
) {
    @Composable
    fun t(key: String): String = translationViewModel.t(key)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Generated at timestamp
        item {
            Text(
                "Last analysed: ${insights.generatedAt.take(16).replace("T", " ")} UTC",
                fontSize = 11.sp, color = Color.LightGray,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End
            )
        }

        // AI Suggestions
        if (insights.suggestions.isNotEmpty()) {
            item { SectionHeader(t("AI Suggestions"), Icons.Default.AutoAwesome, Color(0xFF1A237E)) }
            items(insights.suggestions) { suggestion -> SuggestionCard(suggestion) }
        }

        // Sales Forecast
        item { SectionHeader(t("Sales Forecast"), Icons.Default.TrendingUp, Color(0xFF1A237E)) }
        item { ForecastCard(insights.forecast) }

        // Credit Risk Summary
        item { SectionHeader(t("Customer Credit Risk"), Icons.Default.Shield, Color(0xFF1A237E)) }
        item { CreditRiskSummaryCard(insights.creditRisk.summary) }
        items(insights.creditRisk.customers) { customer -> CustomerRiskCard(customer) }

        // Empty state for no customers
        if (insights.creditRisk.customers.isEmpty()) {
            item {
                EmptyCard(t("No customers yet. Add customers and ledger entries to see credit risk analysis."))
            }
        }

        // Inventory Alerts
        if (insights.inventory.restockAlerts.isNotEmpty()) {
            item { SectionHeader(t("Restock Alerts"), Icons.Default.Inventory, Color(0xFFE65100)) }
            items(insights.inventory.restockAlerts) { alert -> RestockAlertCard(alert) }
        }

        // Top Items
        if (insights.inventory.topItems.isNotEmpty()) {
            item { SectionHeader(t("Top Selling Items"), Icons.Default.Star, Color(0xFF1A237E)) }
            items(insights.inventory.topItems) { item -> TopItemCard(item) }
        }

        // Expense Analysis
        item { SectionHeader(t("Expense Breakdown (30d)"), Icons.Default.PieChart, Color(0xFF1A237E)) }
        item { ExpenseAnalysisCard(insights.expenses) }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

// ─── Section Header ───────────────────────────────────────────────────────────
@Composable
fun SectionHeader(title: String, icon: ImageVector, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, color = color)
    }
}

@Composable
fun EmptyCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
            Text(message, color = Color.Gray, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
    }
}

// ─── Suggestion Card ──────────────────────────────────────────────────────────
@Composable
fun SuggestionCard(s: AiSuggestion) {
    val (bg, border, iconTint) = when (s.type) {
        "POSITIVE" -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), Color(0xFF2E7D32))
        "WARNING"  -> Triple(Color(0xFFFFF3E0), Color(0xFFE65100), Color(0xFFE65100))
        "ALERT"    -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), Color(0xFFC62828))
        "CRITICAL" -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), Color(0xFFC62828))
        else       -> Triple(Color(0xFFE8EAF6), Color(0xFF1A237E), Color(0xFF1A237E))
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        border = androidx.compose.foundation.BorderStroke(1.dp, border)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = when (s.type) {
                    "POSITIVE" -> Icons.Default.CheckCircle
                    "WARNING"  -> Icons.Default.Info
                    "CRITICAL" -> Icons.Default.Error
                    else       -> Icons.Default.Warning
                },
                contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp)
            )
            Text(s.message, fontSize = 14.sp, color = iconTint, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
        }
    }
}

// ─── Forecast Card ────────────────────────────────────────────────────────────
@Composable
fun ForecastCard(forecast: AiForecast) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Trend badge
            val trendColor = when (forecast.trendDirection) {
                "UP"   -> Color(0xFF81C784)
                "DOWN" -> Color(0xFFEF9A9A)
                else   -> Color(0xFFFFCC80)
            }
            val trendIcon = when (forecast.trendDirection) {
                "UP"   -> Icons.Default.TrendingUp
                "DOWN" -> Icons.Default.TrendingDown
                else   -> Icons.Default.TrendingFlat
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(color = trendColor.copy(alpha = 0.2f), shape = RoundedCornerShape(20.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(trendIcon, contentDescription = null, tint = trendColor, modifier = Modifier.size(14.dp))
                        Text(
                            "${forecast.trendDirection} ${if (forecast.trendPercent != 0) "${Math.abs(forecast.trendPercent)}%" else ""}",
                            fontSize = 12.sp, fontWeight = FontWeight.Bold, color = trendColor
                        )
                    }
                }
                Text("Based on ${forecast.dataPoints} days of data", fontSize = 11.sp, color = Color.White.copy(alpha = 0.5f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ForecastStat("Next 7 Days", "₹${forecast.next7Days.toLong()}", Color.White)
                ForecastStat("Next 30 Days", "₹${forecast.next30Days.toLong()}", Color.White)
                ForecastStat("Avg/Day", "₹${forecast.avgDailySales.toLong()}", Color.White)
            }
            // Mini bar chart
            if (forecast.weeklySales.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Weekly Sales", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 8.dp))
                val maxVal = forecast.weeklySales.maxOfOrNull { it.total } ?: 1.0
                Row(modifier = Modifier.fillMaxWidth().height(60.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    forecast.weeklySales.forEachIndexed { i, week ->
                        val ratio = if (maxVal > 0) (week.total / maxVal).toFloat() else 0f
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .height((ratio * 50).dp.coerceAtLeast(4.dp))
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(if (i == forecast.weeklySales.lastIndex) Color(0xFF81C784) else Color.White.copy(alpha = 0.4f))
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    forecast.weeklySales.forEachIndexed { i, _ ->
                        Text("W${i + 1}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Text(label, fontSize = 11.sp, color = color.copy(alpha = 0.6f))
    }
}

// ─── Credit Risk Summary ──────────────────────────────────────────────────────
@Composable
fun CreditRiskSummaryCard(summary: CreditRiskSummary) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RiskCountBadge(summary.high,   "HIGH",   Color(0xFFC62828), Color(0xFFFFEBEE))
            RiskCountBadge(summary.medium, "MEDIUM", Color(0xFFE65100), Color(0xFFFFF3E0))
            RiskCountBadge(summary.low,    "LOW",    Color(0xFF2E7D32), Color(0xFFE8F5E9))
        }
    }
}

@Composable
fun RiskCountBadge(count: Int, label: String, textColor: Color, bgColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(
            modifier = Modifier.size(52.dp).background(bgColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("$count", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = textColor)
        }
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}

// ─── Customer Risk Card ───────────────────────────────────────────────────────
@Composable
fun CustomerRiskCard(customer: CustomerRisk) {
    val riskColor = when (customer.riskLevel) {
        "HIGH"   -> Color(0xFFC62828)
        "MEDIUM" -> Color(0xFFE65100)
        else     -> Color(0xFF2E7D32)
    }
    val riskBg = when (customer.riskLevel) {
        "HIGH"   -> Color(0xFFFFEBEE)
        "MEDIUM" -> Color(0xFFFFF3E0)
        else     -> Color(0xFFE8F5E9)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header row
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(42.dp).background(Color(0xFFE8EAF6), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        customer.customerName.first().uppercaseChar().toString(),
                        fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF1A237E)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(customer.customerName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A237E))
                    if (customer.phone != null)
                        Text(customer.phone, fontSize = 12.sp, color = Color.Gray)
                }
                // Risk badge
                Surface(color = riskBg, shape = RoundedCornerShape(20.dp)) {
                    Text(
                        customer.riskLabel,
                        fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = riskColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Risk score bar
            Text("Risk Score: ${customer.riskScore}/100", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
            LinearProgressIndicator(
                progress = (customer.riskScore / 100f).coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = riskColor,
                trackColor = riskBg
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Stats row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                RiskStat("Outstanding", "₹${customer.outstanding.toLong()}", riskColor)
                RiskStat("Gave", "₹${customer.totalGave.toLong()}", Color(0xFFC62828))
                RiskStat("Got", "₹${customer.totalGot.toLong()}", Color(0xFF2E7D32))
                RiskStat("Txns", "${customer.txCount}", Color(0xFF1A237E))
            }
            // Behaviour tags
            if (customer.behaviourTags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    customer.behaviourTags.take(4).forEach { tag ->
                        Surface(color = Color(0xFFE8EAF6), shape = RoundedCornerShape(20.dp)) {
                            Text(tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }
                }
            }
            // Last activity
            if (customer.lastActivityDays < 999) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Last activity: ${if (customer.lastActivityDays == 0) "Today" else "${customer.lastActivityDays} days ago"} • Avg payment: ${customer.avgPaymentDays}d",
                    fontSize = 11.sp, color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun RiskStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Text(label, fontSize = 10.sp, color = Color.Gray)
    }
}

// ─── Restock Alert Card ───────────────────────────────────────────────────────
@Composable
fun RestockAlertCard(alert: RestockAlert) {
    val (urgencyColor, urgencyBg) = when (alert.urgency) {
        "CRITICAL" -> Pair(Color(0xFFC62828), Color(0xFFFFEBEE))
        "HIGH"     -> Pair(Color(0xFFE65100), Color(0xFFFFF3E0))
        else       -> Pair(Color(0xFFFF8F00), Color(0xFFFFF8E1))
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, urgencyColor.copy(alpha = 0.3f))
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(40.dp).background(urgencyBg, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Inventory, contentDescription = null, tint = urgencyColor, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(alert.item_name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1A237E))
                Text(
                    "Stock: ${alert.stock_level} ${alert.unit ?: "pcs"} • Velocity: ${alert.dailyVelocity}/day",
                    fontSize = 12.sp, color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Surface(color = urgencyBg, shape = RoundedCornerShape(8.dp)) {
                    Text(alert.urgency, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = urgencyColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                }
                Text(
                    if (alert.daysRemaining < 999) "~${alert.daysRemaining}d left" else "No sales yet",
                    fontSize = 11.sp, color = urgencyColor, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

// ─── Top Item Card ────────────────────────────────────────────────────────────
@Composable
fun TopItemCard(item: TopItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(40.dp).background(Color(0xFFE8EAF6), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(item.item_name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1A237E))
                Text("Sold: ${item.total_qty.toLong()} units", fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("₹${item.revenue.toLong()}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF2E7D32))
                Text("Revenue", fontSize = 10.sp, color = Color.LightGray)
            }
        }
    }
}

// ─── Expense Analysis Card ────────────────────────────────────────────────────
@Composable
fun ExpenseAnalysisCard(expenses: AiExpenses) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Summary row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ExpenseSummaryItem("Revenue", "₹${expenses.revenueLast30d.toLong()}", Color(0xFF2E7D32))
                ExpenseSummaryItem("Expenses", "₹${expenses.totalLast30d.toLong()}", Color(0xFFC62828))
                ExpenseSummaryItem("Profit Margin", "${expenses.profitMargin}%",
                    if (expenses.profitMargin >= 30) Color(0xFF2E7D32)
                    else if (expenses.profitMargin >= 10) Color(0xFFE65100)
                    else Color(0xFFC62828)
                )
            }
            if (expenses.byCategory.isNotEmpty()) {
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))
                Text("By Category", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                val total = expenses.byCategory.sumOf { it.total }.coerceAtLeast(1.0)
                expenses.byCategory.forEach { cat ->
                    val pct = (cat.total / total * 100).toInt()
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(cat.category, modifier = Modifier.width(100.dp), fontSize = 13.sp, color = Color(0xFF333333))
                        LinearProgressIndicator(
                            progress = (cat.total / total).toFloat(),
                            modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFF1A237E),
                            trackColor = Color(0xFFE8EAF6)
                        )
                        Text("  $pct%", fontSize = 12.sp, color = Color(0xFF1A237E), fontWeight = FontWeight.Bold, modifier = Modifier.width(40.dp))
                    }
                }
            } else {
                Text("No expenses recorded in the last 30 days.", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
fun ExpenseSummaryItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Text(label, fontSize = 11.sp, color = Color.Gray)
    }
}
