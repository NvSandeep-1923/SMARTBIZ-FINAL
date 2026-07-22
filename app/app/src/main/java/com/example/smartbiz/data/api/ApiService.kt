package com.example.smartbiz.data.api

import com.example.smartbiz.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- AUTH ---
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterApiRequest): Response<AuthResponse>

    // --- DASHBOARD ---
    @GET("/api/dashboard/stats")
    suspend fun getDashboardStats(): Response<DashboardStats>

    // --- INVENTORY ---
    @GET("/api/inventory")
    suspend fun getInventory(): Response<List<InventoryItem>>

    @POST("/api/inventory")
    suspend fun addItem(@Body item: InventoryItem): Response<IdResponse>

    @DELETE("/api/inventory/{id}")
    suspend fun deleteItem(@Path("id") id: Int): Response<SuccessResponse>

    // --- CUSTOMERS ---
    @GET("/api/customers")
    suspend fun getCustomers(): Response<List<Customer>>

    @POST("/api/customers")
    suspend fun addCustomer(@Body customer: Customer): Response<IdResponse>

    // --- LEDGER ---
    @GET("/api/ledger/{customerId}")
    suspend fun getLedger(@Path("customerId") customerId: Int): Response<List<LedgerTransaction>>

    @POST("/api/ledger")
    suspend fun addLedgerEntry(@Body entry: LedgerTransaction): Response<SuccessResponse>

    // --- INVOICES ---
    @GET("/api/invoices")
    suspend fun getInvoices(): Response<List<Invoice>>

    @POST("/api/invoices")
    suspend fun createInvoice(@Body invoice: Invoice): Response<IdResponse>

    // --- EXPENSES ---
    @GET("/api/expenses")
    suspend fun getExpenses(): Response<List<Expense>>

    @POST("/api/expenses")
    suspend fun addExpense(@Body expense: Expense): Response<SuccessResponse>

    // --- AI INSIGHTS ---
    @GET("/api/ai/insights")
    suspend fun getAiInsights(): Response<AiInsights>

    // --- TRANSLATIONS ---
    @GET("/api/translations/{lang}")
    suspend fun getTranslations(@Path("lang") lang: String): Response<Map<String, String>>
}
