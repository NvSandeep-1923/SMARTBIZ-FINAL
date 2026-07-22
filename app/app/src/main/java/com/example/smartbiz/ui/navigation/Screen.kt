package com.example.smartbiz.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Inventory : Screen("inventory")
    object AddItem : Screen("add_item")
    object Customers : Screen("customers")
    object AddCustomer : Screen("add_customer")
    object CustomerLedger : Screen("ledger/{customerId}") {
        fun createRoute(customerId: Int) = "ledger/$customerId"
    }
    object InvoiceBuilder : Screen("invoice_builder")
    object Expenses : Screen("expenses")
    object AddExpense : Screen("add_expense")
    object Reports : Screen("reports")
    object Profile : Screen("profile")
    object AiInsights : Screen("ai_insights")
    object BusinessProfile : Screen("business_profile")
    object StaffManagement : Screen("staff_management")
    object PrinterSettings : Screen("printer_settings")
    object AppLanguage : Screen("app_language")
    object NotificationsSettings : Screen("notifications_settings")
    object HelpSupport : Screen("help_support")
}
