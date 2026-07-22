package com.example.smartbiz.data.repository

import com.example.smartbiz.data.api.RetrofitClient
import com.example.smartbiz.data.api.SessionManager
import com.example.smartbiz.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmartBizRepository {
    private val api = RetrofitClient.apiService

    // Auth — backend uses 'password', not 'pin'
    suspend fun login(email: String, pin: String) = withContext(Dispatchers.IO) {
        api.login(LoginRequest(email = email, password = pin))
    }

    suspend fun register(request: RegisterRequest) = withContext(Dispatchers.IO) {
        api.register(request.toApiRequest())
    }

    // Dashboard
    suspend fun getDashboardStats() = withContext(Dispatchers.IO) {
        api.getDashboardStats()
    }

    // Inventory — always scoped to current merchant
    suspend fun getInventory() = withContext(Dispatchers.IO) {
        api.getInventory()
    }

    suspend fun addItem(item: InventoryItem) = withContext(Dispatchers.IO) {
        api.addItem(item.copy(merchant_id = SessionManager.currentMerchantId))
    }

    suspend fun deleteItem(id: Int) = withContext(Dispatchers.IO) {
        api.deleteItem(id)
    }

    // Customers
    suspend fun getCustomers() = withContext(Dispatchers.IO) {
        api.getCustomers()
    }

    suspend fun addCustomer(customer: Customer) = withContext(Dispatchers.IO) {
        api.addCustomer(customer.copy(merchant_id = SessionManager.currentMerchantId))
    }

    // Ledger
    suspend fun getLedger(customerId: Int) = withContext(Dispatchers.IO) {
        api.getLedger(customerId)
    }

    suspend fun addLedgerEntry(entry: LedgerTransaction) = withContext(Dispatchers.IO) {
        api.addLedgerEntry(entry.copy(merchant_id = SessionManager.currentMerchantId))
    }

    // Invoices
    suspend fun createInvoice(invoice: Invoice) = withContext(Dispatchers.IO) {
        api.createInvoice(invoice.copy(merchant_id = SessionManager.currentMerchantId))
    }

    // Expenses
    suspend fun getExpenses() = withContext(Dispatchers.IO) {
        api.getExpenses()
    }

    suspend fun addExpense(expense: Expense) = withContext(Dispatchers.IO) {
        api.addExpense(expense.copy(merchant_id = SessionManager.currentMerchantId))
    }

    // AI Insights
    suspend fun getAiInsights() = withContext(Dispatchers.IO) {
        api.getAiInsights()
    }

    // Translations
    suspend fun getTranslations(lang: String) = withContext(Dispatchers.IO) {
        api.getTranslations(lang)
    }
}
