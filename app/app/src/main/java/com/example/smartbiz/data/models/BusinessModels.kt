package com.example.smartbiz.data.models

// Generic API responses
data class IdResponse(val id: Int? = null, val message: String? = null, val error: String? = null)
data class SuccessResponse(val success: Boolean = false, val message: String? = null, val error: String? = null)

data class InventoryItem(
    val id: Int? = null,
    val item_name: String,
    val sku: String? = null,
    val hsn: String? = null,
    val category: String,
    val cost_price: Double,
    val sale_price: Double,
    val stock_level: Int,
    val unit: String? = null,
    val gst_rate: Int = 0,
    val merchant_id: Int = 0
)

data class Customer(
    val id: Int? = null,
    val name: String,
    val phone: String? = null,
    val email: String? = null,
    val address: String? = null,
    val gstin: String? = null,
    val balance: Double = 0.0,
    val merchant_id: Int = 0
)

data class Invoice(
    val id: Int? = null,
    val invoice_number: String,
    val customer_id: Int,
    val subtotal: Double,
    val total_gst: Double,
    val grand_total: Double,
    val tx_date: String? = null,
    val merchant_id: Int = 0,
    val items: List<InvoiceItem>? = null
)

data class InvoiceItem(
    val id: Int? = null,
    val invoice_id: Int? = null,
    val item_id: Int = 0,
    val item_name: String,
    val qty: Double,
    val sale_price: Double,
    val gst_rate: Int = 0,
    val gst_amount: Double = 0.0,
    val subtotal: Double
)

data class Expense(
    val id: Int? = null,
    val amount: Double,
    val description: String,
    val category: String,
    val payment_mode: String? = "Cash",
    val expense_date: String,
    val merchant_id: Int = 0
)

data class LedgerTransaction(
    val id: Int? = null,
    val customer_id: Int,
    val amount: Double,
    val type: String,          // 'gave' = you gave credit, 'got' = you received payment
    val description: String? = null,
    val tx_date: String? = null,
    val merchant_id: Int = 0
)

data class DashboardStats(
    val salesToday: Double = 0.0,
    val udharTotal: Double = 0.0,
    val lowStockCount: Int = 0,
    val recentTransactions: List<LedgerTransaction> = emptyList()
)
