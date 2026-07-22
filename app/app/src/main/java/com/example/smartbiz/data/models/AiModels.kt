package com.example.smartbiz.data.models

// ─── FORECAST ────────────────────────────────────────────────────────────────
data class WeeklySale(
    val week: String = "",
    val total: Double = 0.0,
    val orders: Int = 0
)

data class AiForecast(
    val avgDailySales: Double = 0.0,
    val next7Days: Double = 0.0,
    val next30Days: Double = 0.0,
    val trendDirection: String = "STABLE",  // UP / DOWN / STABLE
    val trendPercent: Int = 0,
    val weeklySales: List<WeeklySale> = emptyList(),
    val dataPoints: Int = 0
)

// ─── CREDIT RISK ─────────────────────────────────────────────────────────────
enum class RiskLevel { HIGH, MEDIUM, LOW }

data class CustomerRisk(
    val customerId: Int = 0,
    val customerName: String = "",
    val phone: String? = null,
    val riskLevel: String = "LOW",    // HIGH / MEDIUM / LOW
    val riskScore: Int = 0,           // 0–100
    val riskColor: String = "#2E7D32",
    val riskLabel: String = "Low Risk",
    val outstanding: Double = 0.0,
    val totalGave: Double = 0.0,
    val totalGot: Double = 0.0,
    val txCount: Int = 0,
    val avgPaymentDays: Int = 0,
    val lastActivityDays: Int = 0,
    val behaviourTags: List<String> = emptyList(),
    val invoiceCount: Int = 0
)

data class CreditRiskSummary(
    val high: Int = 0,
    val medium: Int = 0,
    val low: Int = 0,
    val total: Int = 0
)

data class AiCreditRisk(
    val summary: CreditRiskSummary = CreditRiskSummary(),
    val customers: List<CustomerRisk> = emptyList()
)

// ─── INVENTORY INTELLIGENCE ──────────────────────────────────────────────────
data class TopItem(
    val item_name: String = "",
    val total_qty: Double = 0.0,
    val revenue: Double = 0.0,
    val stock_level: Int = 0,
    val sale_price: Double = 0.0
)

data class RestockAlert(
    val id: Int = 0,
    val item_name: String = "",
    val stock_level: Int = 0,
    val unit: String? = null,
    val sold_last_30: Double = 0.0,
    val daysRemaining: Int = 0,
    val urgency: String = "MEDIUM",   // CRITICAL / HIGH / MEDIUM
    val dailyVelocity: Double = 0.0
)

data class AiInventory(
    val topItems: List<TopItem> = emptyList(),
    val restockAlerts: List<RestockAlert> = emptyList()
)

// ─── EXPENSES ────────────────────────────────────────────────────────────────
data class ExpenseCategory(
    val category: String = "",
    val total: Double = 0.0,
    val count: Int = 0
)

data class AiExpenses(
    val byCategory: List<ExpenseCategory> = emptyList(),
    val totalLast30d: Double = 0.0,
    val revenueLast30d: Double = 0.0,
    val profitMargin: Int = 0
)

// ─── SUGGESTIONS ─────────────────────────────────────────────────────────────
data class AiSuggestion(
    val type: String = "INFO",        // POSITIVE / WARNING / ALERT / CRITICAL
    val icon: String = "Info",
    val message: String = ""
)

// ─── FULL INSIGHTS RESPONSE ──────────────────────────────────────────────────
data class AiInsights(
    val generatedAt: String = "",
    val forecast: AiForecast = AiForecast(),
    val creditRisk: AiCreditRisk = AiCreditRisk(),
    val inventory: AiInventory = AiInventory(),
    val expenses: AiExpenses = AiExpenses(),
    val suggestions: List<AiSuggestion> = emptyList()
)
