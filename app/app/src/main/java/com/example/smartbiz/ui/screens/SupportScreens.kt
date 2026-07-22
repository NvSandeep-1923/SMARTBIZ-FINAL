package com.example.smartbiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartbiz.data.api.SessionManager
import com.example.smartbiz.ui.viewmodel.DashboardState
import com.example.smartbiz.ui.viewmodel.DashboardViewModel

// ─── REPORTS SCREEN ────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: DashboardViewModel = viewModel(),
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Business Reports")) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = translationViewModel.t("Back"))
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadDashboard() }) {
                        Icon(Icons.Default.Refresh, contentDescription = translationViewModel.t("Refresh"))
                    }
                }
            )
        }
    ) { padding ->
        @Composable fun t(s: String) = translationViewModel.t(s)
        when (val state = uiState) {
            is DashboardState.Loading -> Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            is DashboardState.Error -> Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.message, color = Color.Red)
                    Button(onClick = { viewModel.loadDashboard() }, modifier = Modifier.padding(top = 12.dp)) { Text(t("Retry")) }
                }
            }
            is DashboardState.Success -> {
                val stats = state.stats
                Column(
                    modifier = Modifier.padding(padding).fillMaxSize()
                        .verticalScroll(rememberScrollState()).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(t("Summary"), fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF1A237E))

                    ReportCard(
                        title = t("Today's Sales"),
                        value = "₹${stats.salesToday.toLong()}",
                        icon = Icons.Default.TrendingUp,
                        color = Color(0xFF1A237E)
                    )
                    ReportCard(
                        title = t("Total Udhar"),
                        value = "₹${stats.udharTotal.toLong()}",
                        icon = Icons.Default.AccountBalanceWallet,
                        color = Color(0xFFC62828)
                    )
                    ReportCard(
                        title = t("Items Low on Stock"),
                        value = "${stats.lowStockCount}",
                        icon = Icons.Default.Inventory,
                        color = Color(0xFFE65100)
                    )
                    ReportCard(
                        title = t("Recent Transactions"),
                        value = "${stats.recentTransactions.size}",
                        icon = Icons.Default.Receipt,
                        color = Color(0xFF2E7D32)
                    )

                    if (stats.recentTransactions.isNotEmpty()) {
                        Text(t("Recent Transactions"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF444444))
                        stats.recentTransactions.forEach { tx ->
                            TransactionItem(tx, translationViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReportCard(title: String, value: String, icon: ImageVector, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(color.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontSize = 13.sp, color = color.copy(alpha = 0.7f))
                Text(value, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = color)
            }
        }
    }
}

// ─── PROFILE / SETTINGS SCREEN ────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    val businessName = SessionManager.currentBusinessName.ifBlank { "SmartBiz Store" }
    val userName     = SessionManager.currentUserName.ifBlank { "Owner" }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Settings"), fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = translationViewModel.t("Back"))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F6FA))
            )
        },
        containerColor = Color(0xFFF5F6FA)
    ) { padding ->
        @Composable fun t(s: String) = translationViewModel.t(s)
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // ── User Card ────────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar circle
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFFEEEEEE), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFF9E9E9E),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Column {
                        Text(
                            userName,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color(0xFF1A1A2E)
                        )
                        Text(
                            t("SmartBiz Pro Member"),
                            fontSize = 13.sp,
                            color = Color(0xFF888888)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Settings Menu ────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column {
                    SettingsRow(
                        icon = Icons.Default.Store,
                        iconBg = Color(0xFFE3F2FD),
                        iconTint = Color(0xFF1565C0),
                        title = t("Business Profile"),
                        subtitle = t("Details, Logo, GSTIN"),
                        onClick = { onNavigate("business_profile") }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Default.Group,
                        iconBg = Color(0xFFE8F5E9),
                        iconTint = Color(0xFF2E7D32),
                        title = t("Staff Management"),
                        subtitle = t("Roles, Permissions"),
                        onClick = { onNavigate("staff_management") }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Default.Print,
                        iconBg = Color(0xFFF3E5F5),
                        iconTint = Color(0xFF7B1FA2),
                        title = t("Printer Settings"),
                        subtitle = t("Bluetooth, Thermal Printer"),
                        onClick = { onNavigate("printer_settings") }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Default.Language,
                        iconBg = Color(0xFFE3F2FD),
                        iconTint = Color(0xFF0288D1),
                        title = t("App Language"),
                        subtitle = t("English, Hindi, Telugu, etc."),
                        onClick = { onNavigate("app_language") }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Default.Notifications,
                        iconBg = Color(0xFFFFF8E1),
                        iconTint = Color(0xFFF57F17),
                        title = t("Notifications"),
                        subtitle = t("Alerts, Reminders"),
                        onClick = { onNavigate("notifications_settings") }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Default.HelpOutline,
                        iconBg = Color(0xFFE8EAF6),
                        iconTint = Color(0xFF3949AB),
                        title = t("Help & Support"),
                        subtitle = t("FAQs, Contact Us"),
                        onClick = { onNavigate("help_support") },
                        isLast = true
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Logout ───────────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                TextButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(
                        t("Logout Account"),
                        color = Color(0xFFC62828),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // ── Version Footer ───────────────────────────────────────────
            Text(
                t("SmartBiz v2.4.0 (Stable)"),
                fontSize = 12.sp,
                color = Color(0xFFAAAAAA),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBg, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF888888))
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFCCCCCC),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SettingsDivider() {
    Divider(
        color = Color(0xFFF0F0F0),
        modifier = Modifier.padding(start = 76.dp)
    )
}

@Composable
fun InfoDialog(title: String, message: String, onDismiss: () -> Unit, translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(translationViewModel.t(title), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E)) },
        text = { Text(translationViewModel.t(message), fontSize = 14.sp, color = Color(0xFF444444)) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(translationViewModel.t("OK"), color = Color(0xFF1A237E), fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontSize = 11.sp, color = Color.Gray)
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF222222))
        }
    }
}
