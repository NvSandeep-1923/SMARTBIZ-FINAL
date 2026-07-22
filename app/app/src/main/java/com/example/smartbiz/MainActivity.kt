package com.example.smartbiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartbiz.data.api.SessionManager
import com.example.smartbiz.ui.navigation.Screen
import com.example.smartbiz.ui.screens.*
import com.example.smartbiz.ui.theme.SmartBizTheme
import com.example.smartbiz.ui.viewmodel.AuthState
import com.example.smartbiz.ui.viewmodel.AuthViewModel
import com.example.smartbiz.ui.viewmodel.TranslationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize RetrofitClient & SessionManager from SharedPreferences
        com.example.smartbiz.data.api.RetrofitClient.init(applicationContext)
        setContent {
            SmartBizTheme {
                SmartBizApp()
            }
        }
    }
}

@Composable
fun SmartBizApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val translationViewModel: TranslationViewModel = viewModel()  // single instance for whole app
    val authState by authViewModel.authState.collectAsState()

    val isLoading = authState is AuthState.Loading
    val errorMessage = (authState as? AuthState.Error)?.message

    // Single top-level observer — navigates to Dashboard on any auth success
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(0) { inclusive = true }
            }
            authViewModel.resetState()
        }
    }

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        // ── Splash ──────────────────────────────────────────────────────────
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToLogin = {
                val nextRoute = if (SessionManager.authToken != null) Screen.Dashboard.route else Screen.Login.route
                navController.navigate(nextRoute) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        // ── Auth ─────────────────────────────────────────────────────────────
        composable(Screen.Login.route) {
            // Clear any stale error from previous session when screen first appears
            DisposableEffect(Unit) {
                authViewModel.resetState()
                onDispose { }
            }
            LoginScreen(
                translationViewModel = translationViewModel,
                onLoginClick = { email, pin -> authViewModel.login(email, pin) },
                onRegisterClick = {
                    authViewModel.resetState()
                    navController.navigate(Screen.Register.route)
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }

        composable(Screen.Register.route) {
            DisposableEffect(Unit) {
                authViewModel.resetState()
                onDispose { }
            }
            RegisterScreen(
                onRegisterClick = { request -> authViewModel.register(request) },
                onLoginClick = {
                    authViewModel.resetState()
                    navController.popBackStack()
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }

        // ── Dashboard ────────────────────────────────────────────────────────
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                translationViewModel = translationViewModel,
                onNavigateToBilling   = { navController.navigate(Screen.InvoiceBuilder.route) },
                onNavigateToInventory = { navController.navigate(Screen.Inventory.route) },
                onNavigateToCustomers = { navController.navigate(Screen.Customers.route) },
                onNavigateToExpenses  = { navController.navigate(Screen.Expenses.route) },
                onNavigateToReports   = { navController.navigate(Screen.Reports.route) },
                onNavigateToAiInsights = { navController.navigate(Screen.AiInsights.route) },
                onNavigateToProfile   = { navController.navigate(Screen.Profile.route) }
            )
        }

        // ── Inventory ────────────────────────────────────────────────────────
        composable(Screen.Inventory.route) {
            InventoryScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() },
                onAddItem = { navController.navigate(Screen.AddItem.route) }
            )
        }

        composable(Screen.AddItem.route) {
            AddItemScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Customers ────────────────────────────────────────────────────────
        composable(Screen.Customers.route) {
            CustomerListScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLedger = { customerId ->
                    navController.navigate(Screen.CustomerLedger.createRoute(customerId))
                }
            )
        }

        composable(Screen.CustomerLedger.route) { backStackEntry ->
            val customerId = backStackEntry.arguments
                ?.getString("customerId")?.toIntOrNull() ?: 0
            CustomerLedgerScreen(
                customerId = customerId,
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Billing ──────────────────────────────────────────────────────────
        composable(Screen.InvoiceBuilder.route) {
            InvoiceBuilderScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Expenses ─────────────────────────────────────────────────────────
        composable(Screen.Expenses.route) {
            ExpensesScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() },
                onAddExpense = { navController.navigate(Screen.AddExpense.route) }
            )
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Reports ──────────────────────────────────────────────────────────
        composable(Screen.Reports.route) {
            ReportsScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── AI Insights ───────────────────────────────────────────────────────
        composable(Screen.AiInsights.route) {
            AiInsightsScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Profile ──────────────────────────────────────────────────────────
        composable(Screen.Profile.route) {
            ProfileScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    // Clear session and return to login
                    SessionManager.clearSession(context)
                    authViewModel.resetState()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // ── Settings Sub-Screens ──────────────────────────────────────────────
        composable(Screen.BusinessProfile.route) {
            BusinessProfileScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StaffManagement.route) {
            StaffManagementScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.PrinterSettings.route) {
            PrinterSettingsScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AppLanguage.route) {
            AppLanguageScreen(
                onNavigateBack = { navController.popBackStack() },
                translationViewModel = translationViewModel
            )
        }
        composable(Screen.NotificationsSettings.route) {
            NotificationsSettingsScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.HelpSupport.route) {
            HelpSupportScreen(
                translationViewModel = translationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
