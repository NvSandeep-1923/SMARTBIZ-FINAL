package com.example.smartbiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartbiz.data.models.DashboardStats
import com.example.smartbiz.data.models.LedgerTransaction
import com.example.smartbiz.data.api.SessionManager
import com.example.smartbiz.ui.viewmodel.DashboardState
import com.example.smartbiz.ui.viewmodel.DashboardViewModel
import com.example.smartbiz.ui.viewmodel.TranslationViewModel
import com.example.smartbiz.ui.viewmodel.TranslationState

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    translationViewModel: TranslationViewModel = viewModel(),
    onNavigateToBilling: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToCustomers: () -> Unit,
    onNavigateToExpenses: () -> Unit = {},
    onNavigateToReports: () -> Unit = {},
    onNavigateToAiInsights: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val role = SessionManager.currentUserRole
    when (role) {
        "Officer" -> OfficerDashboard(
            translationViewModel = translationViewModel,
            onNavigateToProfile = onNavigateToProfile
        )
        "Admin" -> AdminDashboard(
            translationViewModel = translationViewModel,
            onNavigateToProfile = onNavigateToProfile
        )
        else -> CitizenDashboard(
            viewModel = viewModel,
            translationViewModel = translationViewModel,
            onNavigateToBilling = onNavigateToBilling,
            onNavigateToInventory = onNavigateToInventory,
            onNavigateToCustomers = onNavigateToCustomers,
            onNavigateToExpenses = onNavigateToExpenses,
            onNavigateToReports = onNavigateToReports,
            onNavigateToAiInsights = onNavigateToAiInsights,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficerDashboard(
    translationViewModel: TranslationViewModel,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Officer Dashboard"), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = translationViewModel.t("Profile"))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF9FAFC)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Box(
                    modifier = Modifier.size(72.dp)
                        .background(Color(0xFFE8EAF6), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = Color(0xFF1A237E),
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = translationViewModel.t("Officer Portal"),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF1A237E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = translationViewModel.t("Manage applications, verify merchants and inspect reports here."),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = translationViewModel.t("Assigned Cases"),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = translationViewModel.t("No active reviews allocated. Check again later."),
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    translationViewModel: TranslationViewModel,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Admin Console"), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = translationViewModel.t("Profile"))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF9FAFC)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Box(
                    modifier = Modifier.size(72.dp)
                        .background(Color(0xFFFFF3E0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        tint = Color(0xFFE65100),
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = translationViewModel.t("Administrator Dashboard"),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFE65100)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = translationViewModel.t("Full administrative access. Manage users, databases and configurations."),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = translationViewModel.t("System Health"),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Database Status", fontSize = 13.sp)
                            Text("Online", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenDashboard(
    viewModel: DashboardViewModel = viewModel(),
    translationViewModel: TranslationViewModel = viewModel(),
    onNavigateToBilling: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToCustomers: () -> Unit,
    onNavigateToExpenses: () -> Unit = {},
    onNavigateToReports: () -> Unit = {},
    onNavigateToAiInsights: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val translationState by translationViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("SmartBiz"), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = translationViewModel.t("Profile"))
                    }
                    IconButton(onClick = onNavigateToReports) {
                        Icon(Icons.Default.BarChart, contentDescription = translationViewModel.t("Reports"))
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onDashboard = { viewModel.loadDashboard() },
                onCustomers = onNavigateToCustomers,
                onBilling = onNavigateToBilling,
                onInventory = onNavigateToInventory,
                onExpenses = onNavigateToExpenses,
                labels = listOf(
                    translationViewModel.t("SmartBiz"),
                    translationViewModel.t("Customers"),
                    translationViewModel.t("Billing"),
                    translationViewModel.t("Inventory"),
                    translationViewModel.t("Expenses")
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {
                is DashboardState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                is DashboardState.Success -> DashboardContent(
                    stats = state.stats,
                    translationViewModel = translationViewModel,
                    onNavigateToBilling = onNavigateToBilling,
                    onNavigateToInventory = onNavigateToInventory,
                    onNavigateToCustomers = onNavigateToCustomers,
                    onNavigateToExpenses = onNavigateToExpenses,
                    onNavigateToAiInsights = onNavigateToAiInsights
                )
                is DashboardState.Error -> Column(
                    modifier = Modifier.align(Alignment.Center).padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.WifiOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(state.message, color = Color.Red, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadDashboard() }) { Text(translationViewModel.t("Retry")) }
                }
            }
        }
    }
}

@Composable
fun DashboardContent(
    stats: DashboardStats,
    translationViewModel: TranslationViewModel,
    onNavigateToBilling: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToCustomers: () -> Unit,
    onNavigateToExpenses: () -> Unit,
    onNavigateToAiInsights: () -> Unit = {}
) {
    @Composable
    fun t(key: String): String = translationViewModel.t(key)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFC))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text(
                t("Business Overview"),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color(0xFF1A237E)
            )
        }

        // Stats row
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = t("Today's Sales"),
                    value = "₹${stats.salesToday.toLong()}",
                    icon = Icons.Default.TrendingUp,
                    containerColor = Color(0xFFE8EAF6),
                    iconColor = Color(0xFF1A237E),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = t("Total Udhar"),
                    value = "₹${stats.udharTotal.toLong()}",
                    icon = Icons.Default.AccountBalanceWallet,
                    containerColor = Color(0xFFFFEBEE),
                    iconColor = Color(0xFFC62828),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Low stock alert
        if (stats.lowStockCount > 0) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onNavigateToInventory() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFE65100), modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("${stats.lowStockCount} ${t("Items Low on Stock")}", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                            Text(t("Tap to reorder"), fontSize = 12.sp, color = Color(0xFFE65100).copy(alpha = 0.7f))
                        }
                    }
                }
            }
        }

        // Quick action grid
        item {
            Text(t("Quick Actions"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF444444))
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickActionCard(t("Billing"), Icons.Default.Receipt, Color(0xFF1A237E), Modifier.weight(1f), onNavigateToBilling)
                QuickActionCard(t("Customers"), Icons.Default.Group, Color(0xFF2E7D32), Modifier.weight(1f), onNavigateToCustomers)
                QuickActionCard(t("Inventory"), Icons.Default.Inventory2, Color(0xFFE65100), Modifier.weight(1f), onNavigateToInventory)
                QuickActionCard(t("Expenses"), Icons.Default.MoneyOff, Color(0xFFC62828), Modifier.weight(1f), onNavigateToExpenses)
            }
        }

        // AI Insights banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onNavigateToAiInsights() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(26.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(t("AI Business Insights"), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color.White)
                        Text(t("Credit risk · Sales forecast · Restock alerts"), fontSize = 12.sp, color = Color.White.copy(alpha = 0.65f))
                    }
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                }
            }
        }

        // Recent transactions header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(t("Recent Transactions"), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A237E))
                TextButton(onClick = onNavigateToCustomers) {
                    Text(t("See All"), color = Color(0xFF1A237E), fontWeight = FontWeight.Bold)
                }
            }
        }

        if (stats.recentTransactions.isEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text(t("No transactions yet"), color = Color.Gray)
                    }
                }
            }
        } else {
            items(stats.recentTransactions) { tx -> TransactionItem(tx, translationViewModel) }
        }
    }
}

@Composable
fun QuickActionCard(label: String, icon: ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() }.aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun StatCard(
    title: String, value: String, icon: ImageVector,
    containerColor: Color, iconColor: Color, modifier: Modifier = Modifier
) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = containerColor)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(title, fontSize = 12.sp, color = iconColor.copy(alpha = 0.7f))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = iconColor)
        }
    }
}

@Composable
fun TransactionItem(tx: LedgerTransaction, translationViewModel: TranslationViewModel = viewModel()) {
    @Composable
    fun t(key: String): String = translationViewModel.t(key)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(Color(0xFFF5F5F5), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (tx.type == "gave") Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = if (tx.type == "gave") Color(0xFFC62828) else Color(0xFF2E7D32),
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(tx.description?.let { t(it) } ?: t("Customer Transaction"), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(t(tx.tx_date ?: "Just now"), fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = "${if (tx.type == "gave") "-" else "+"} ₹${tx.amount.toLong()}",
                color = if (tx.type == "gave") Color(0xFFC62828) else Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    onDashboard: () -> Unit,
    onCustomers: () -> Unit,
    onBilling: () -> Unit,
    onInventory: () -> Unit,
    onExpenses: () -> Unit,
    labels: List<String> = listOf("Home", "Customers", "Billing", "Inventory", "Expenses")
) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text(labels.getOrElse(0) { "Home" }, fontSize = 11.sp) },
            selected = true, onClick = onDashboard
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Group, contentDescription = null) },
            label = { Text(labels.getOrElse(1) { "Customers" }, fontSize = 11.sp) },
            selected = false, onClick = onCustomers
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Receipt, contentDescription = null) },
            label = { Text(labels.getOrElse(2) { "Billing" }, fontSize = 11.sp) },
            selected = false, onClick = onBilling
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Inventory2, contentDescription = null) },
            label = { Text(labels.getOrElse(3) { "Inventory" }, fontSize = 11.sp) },
            selected = false, onClick = onInventory
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.MoneyOff, contentDescription = null) },
            label = { Text(labels.getOrElse(4) { "Expenses" }, fontSize = 11.sp) },
            selected = false, onClick = onExpenses
        )
    }
}
