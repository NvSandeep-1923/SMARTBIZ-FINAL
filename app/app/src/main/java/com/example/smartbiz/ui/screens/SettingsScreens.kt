package com.example.smartbiz.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.data.api.SessionManager
import kotlinx.coroutines.launch

// ─── SCREEN 1: BusinessProfileScreen ─────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessProfileScreen(
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var businessName by remember { mutableStateOf(SessionManager.currentBusinessName.ifBlank { "" }) }
    var category by remember { mutableStateOf("Retail/Kirana") }
    var customCategory by remember { mutableStateOf("") }   // shown when "Others" is picked
    var gstin by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf(SessionManager.currentUserName.ifBlank { "" }) }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }
    val categories = listOf("Retail/Kirana", "Wholesale", "Textiles & Fabrics", "Electronics", "Food & Beverage", "Pharmacy", "Others")
    val fieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Color(0xFFF5F6FA),
        focusedContainerColor = Color(0xFFF5F6FA)
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Business Profile")) },
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
            modifier = Modifier.padding(padding).fillMaxSize()
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Business Details card
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(t("Business Details"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A237E))
                    OutlinedTextField(value = businessName, onValueChange = { businessName = it },
                        label = { Text(t("Business Name")) }, modifier = Modifier.fillMaxWidth(),
                        colors = fieldColors, shape = RoundedCornerShape(10.dp))
                    ExposedDropdownMenuBox(expanded = categoryExpanded, onExpandedChange = { categoryExpanded = it }) {
                        OutlinedTextField(value = category, onValueChange = {}, readOnly = true,
                            label = { Text(t("Business Category")) }, modifier = Modifier.fillMaxWidth().menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                            colors = fieldColors, shape = RoundedCornerShape(10.dp))
                        ExposedDropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }) {
                            categories.forEach { c ->
                                DropdownMenuItem(text = { Text(t(c)) }, onClick = { category = c; categoryExpanded = false })
                            }
                        }
                    }
                    // Custom category input — only visible when "Others" is selected
                    if (category == "Others") {
                        OutlinedTextField(
                            value = customCategory,
                            onValueChange = { customCategory = it },
                            label = { Text(t("Specify your business type")) },
                            placeholder = { Text(t("e.g. Bakery, Salon, Medical Store...")) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = fieldColors,
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                    }
                    OutlinedTextField(value = gstin, onValueChange = { gstin = it },
                        label = { Text(t("GSTIN (Optional)")) }, modifier = Modifier.fillMaxWidth(),
                        colors = fieldColors, shape = RoundedCornerShape(10.dp))
                }
            }

            // Contact Information card
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(t("Contact Information"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A237E))
                    OutlinedTextField(value = phone, onValueChange = { phone = it },
                        label = { Text(t("Phone Number")) }, modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = fieldColors, shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = email, onValueChange = { email = it },
                        label = { Text(t("Email Address")) }, modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = fieldColors, shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = address, onValueChange = { address = it },
                        label = { Text(t("Address")) }, modifier = Modifier.fillMaxWidth(),
                        minLines = 3, maxLines = 3,
                        colors = fieldColors, shape = RoundedCornerShape(10.dp))
                }
            }

            Button(
                onClick = {
                    if (businessName.isNotBlank()) {
                        SessionManager.currentBusinessName = businessName
                    }
                    // If Others, validate custom category is filled
                    if (category == "Others" && customCategory.isBlank()) {
                        scope.launch { snackbarHostState.showSnackbar(translationViewModel.translate("Please specify your business type")) }
                        return@Button
                    }
                    scope.launch { snackbarHostState.showSnackbar(translationViewModel.translate("Profile updated successfully")) }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
            ) { Text(t("Update Profile"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White) }
        }
    }
}

// ─── SCREEN 2: StaffManagementScreen ─────────────────────────────────────────
private data class StaffMember(val name: String, val role: String, val online: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffManagementScreen(
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val demoStaff = remember {
        mutableStateListOf(
            StaffMember("Amit Patel", "Admin", true),
            StaffMember("Suresh Raina", "Salesperson", false),
            StaffMember("Kavita Singh", "Inventory Manager", true)
        )
    }
    var showAddDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newRole by remember { mutableStateOf("") }
    var newPhone by remember { mutableStateOf("") }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(translationViewModel.t("Add Staff Member"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = newName, onValueChange = { newName = it },
                        label = { Text(translationViewModel.t("Name")) }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = newRole, onValueChange = { newRole = it },
                        label = { Text(translationViewModel.t("Role")) }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = newPhone, onValueChange = { newPhone = it },
                        label = { Text(translationViewModel.t("Phone")) }, modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newName.isNotBlank()) {
                        demoStaff.add(StaffMember(newName, newRole.ifBlank { translationViewModel.translate("Staff") }, true))
                    }
                    showAddDialog = false; newName = ""; newRole = ""; newPhone = ""
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))) { Text(translationViewModel.t("Add")) }
            },
            dismissButton = { TextButton(onClick = { showAddDialog = false }) { Text(translationViewModel.t("Cancel")) } }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Staff Management"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, translationViewModel.t("Back")) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F6FA))
            )
        },
        containerColor = Color(0xFFF5F6FA)
    ) { padding ->
        @Composable fun t(s: String) = translationViewModel.t(s)
        Column(modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(t("Active Staff"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A1A2E))
                    Button(onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                        shape = RoundedCornerShape(8.dp)) { Text("+ " + t("Add Staff"), fontSize = 13.sp) }
                }
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column {
                    demoStaff.forEachIndexed { idx, staff ->
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).background(Color(0xFFEEEEEE), CircleShape), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Person, null, tint = Color(0xFF757575), modifier = Modifier.size(22.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(staff.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text(staff.role, fontSize = 12.sp, color = Color(0xFF888888))
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(if (staff.online) Color(0xFF4CAF50) else Color(0xFF9E9E9E), CircleShape))
                                Spacer(Modifier.width(4.dp))
                                Text(if (staff.online) t("Online") else t("Offline"), fontSize = 12.sp,
                                    color = if (staff.online) Color(0xFF4CAF50) else Color(0xFF9E9E9E))
                            }
                        }
                        if (idx < demoStaff.lastIndex) Divider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(start = 68.dp))
                    }
                }
            }

            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(t("Roles & Permissions"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A237E))
                    Spacer(Modifier.height(4.dp))
                    Text(t("Manage what your staff can see and do in the app."), fontSize = 13.sp, color = Color(0xFF888888), textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = {
                        scope.launch { snackbarHostState.showSnackbar(translationViewModel.translate("Roles configuration coming soon")) }
                    }, shape = RoundedCornerShape(8.dp)) { Text(t("Configure Roles"), color = Color(0xFF1A237E)) }
                }
            }
        }
    }
}

// ─── SCREEN 3: PrinterSettingsScreen ─────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterSettingsScreen(
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var bluetoothEnabled by remember { mutableStateOf(true) }
    var epsonConnected by remember { mutableStateOf(false) }
    var paperSize by remember { mutableStateOf("3 Inch (80mm)") }
    var paperExpanded by remember { mutableStateOf(false) }
    var copies by remember { mutableStateOf("1") }
    var printLogo by remember { mutableStateOf(true) }
    val paperOptions = listOf("2 Inch (58mm)", "3 Inch (80mm)")
    val fieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Color(0xFFF5F6FA), focusedContainerColor = Color(0xFFF5F6FA))

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Printer Settings"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, translationViewModel.t("Back")) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F6FA))
            )
        },
        containerColor = Color(0xFFF5F6FA)
    ) { padding ->
        @Composable fun t(s: String) = translationViewModel.t(s)
        Column(modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            // Bluetooth toggle card
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(t("Bluetooth Printing"), fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                        Text(t("Connect to thermal printer."), fontSize = 12.sp, color = Color(0xFF888888))
                    }
                    Switch(checked = bluetoothEnabled, onCheckedChange = { bluetoothEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF1A237E)))
                }
            }

            // Available Printers card
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(t("Available Printers"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A237E))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Print, null, tint = Color(0xFF1A237E), modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(t("TVS RP-3200 (Connected)"), modifier = Modifier.weight(1f), fontSize = 14.sp)
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFF4CAF50), CircleShape))
                    }
                    Divider(color = Color(0xFFF0F0F0))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Print, null, tint = Color(0xFF757575), modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(t("EPSON TM-T88V"), modifier = Modifier.weight(1f), fontSize = 14.sp)
                        if (epsonConnected) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFF4CAF50), CircleShape))
                                Spacer(Modifier.width(6.dp))
                                Text(t("Connected"), fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.SemiBold)
                            }
                        } else {
                            TextButton(onClick = {
                                epsonConnected = true
                                scope.launch { snackbarHostState.showSnackbar(translationViewModel.translate("EPSON TM-T88V connected!")) }
                            }) { Text(t("Connect"), color = Color(0xFF1A237E)) }
                        }
                    }
                }
            }

            // Print Configuration card
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(t("Print Configuration"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A237E))
                    ExposedDropdownMenuBox(expanded = paperExpanded, onExpandedChange = { paperExpanded = it }) {
                        OutlinedTextField(value = paperSize, onValueChange = {}, readOnly = true,
                            label = { Text(t("Paper Size")) }, modifier = Modifier.fillMaxWidth().menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = paperExpanded) },
                            colors = fieldColors, shape = RoundedCornerShape(10.dp))
                        ExposedDropdownMenu(expanded = paperExpanded, onDismissRequest = { paperExpanded = false }) {
                            paperOptions.forEach { opt ->
                                DropdownMenuItem(text = { Text(opt) }, onClick = { paperSize = opt; paperExpanded = false })
                            }
                        }
                    }
                    OutlinedTextField(value = copies, onValueChange = { copies = it },
                        label = { Text(t("Copies")) }, modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = fieldColors, shape = RoundedCornerShape(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = printLogo, onCheckedChange = { printLogo = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1A237E)))
                        Spacer(Modifier.width(8.dp))
                        Text(t("Print Logo on Invoice"), fontSize = 14.sp)
                    }
                }
            }

            OutlinedButton(
                onClick = { scope.launch { snackbarHostState.showSnackbar(translationViewModel.translate("Test print sent!")) } },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                border = ButtonDefaults.outlinedButtonBorder
            ) { Text("🖨 " + t("Test Print"), fontSize = 15.sp, color = Color(0xFF1A237E)) }
        }
    }
}

// ─── SCREEN 4: AppLanguageScreen ─────────────────────────────────────────────
private data class LangOption(val flag: String, val locale: String, val name: String, val native: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguageScreen(
    onNavigateBack: () -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val translationState by translationViewModel.state.collectAsState()
    val currentLanguage by translationViewModel.currentLanguage.collectAsState()
    val readyLanguages by translationViewModel.readyLanguages.collectAsState()

    val languages = remember {
        listOf(
            LangOption("🇺🇸", "US", "English",  "English"),
            LangOption("🇮🇳", "IN", "Hindi",    "हिंदी"),
            LangOption("🇮🇳", "IN", "Telugu",   "తెలుగు"),
            LangOption("🇮🇳", "IN", "Marathi",  "मराठी"),
            LangOption("🇮🇳", "IN", "Gujarati", "ગુજરાતી"),
            LangOption("🇮🇳", "IN", "Tamil",    "தமிழ்")
        )
    }
    var selectedLang by remember(currentLanguage) { mutableStateOf(currentLanguage) }
    val isApplyingLang = translationState is com.example.smartbiz.ui.viewmodel.TranslationState.Downloading

    LaunchedEffect(translationState) {
        when (val s = translationState) {
            is com.example.smartbiz.ui.viewmodel.TranslationState.Ready -> {
                if (selectedLang == translationViewModel.currentLanguage.value) {
                    snackbarHostState.showSnackbar(translationViewModel.translate("✓ Language changed to") + " ${translationViewModel.currentLanguage.value}")
                }
            }
            is com.example.smartbiz.ui.viewmodel.TranslationState.Error ->
                snackbarHostState.showSnackbar("⚠ ${s.message}")
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(translationViewModel.t("App Language"), fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A237E), modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, translationViewModel.t("Back")) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F6FA))
            )
        },
        containerColor = Color(0xFFF5F6FA)
    ) { padding ->
        @Composable fun t(s: String) = translationViewModel.t(s)
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(t("Select your preferred language for the application interface."),
                fontSize = 14.sp, color = Color(0xFF888888))

            // Language download status card
            val isReady = readyLanguages.contains(selectedLang)
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isReady) Color(0xFFE8F5E9) else Color(0xFFFFF3E0))
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(if (isReady) Icons.Default.CheckCircle else Icons.Default.CloudDownload, null,
                        tint = if (isReady) Color(0xFF2E7D32) else Color(0xFFE65100), modifier = Modifier.size(18.dp))
                    Column {
                        Text(if (isReady) translationViewModel.t("Ready to use") else translationViewModel.t("Download needed"),
                            fontWeight = FontWeight.SemiBold, fontSize = 13.sp,
                            color = if (isReady) Color(0xFF2E7D32) else Color(0xFFE65100))
                        Text(if (isReady) translationViewModel.t("Translations are cached locally") 
                             else translationViewModel.t("Full language pack will be downloaded from backend"),
                            fontSize = 11.sp, color = (if (isReady) Color(0xFF2E7D32) else Color(0xFFE65100)).copy(alpha = 0.7f))
                    }
                }
            }

            // Language list
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column {
                    languages.forEach { lang ->
                        key(lang.name) {
                            val isSelected   = selectedLang == lang.name
                            val isApplied    = currentLanguage == lang.name

                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .clickable { selectedLang = lang.name }
                                    .background(when {
                                        isApplied  -> Color(0xFFE8F5E9)
                                        isSelected -> Color(0xFFF0F4FF)
                                        else       -> Color.White
                                    })
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${lang.flag} ${lang.locale}", fontSize = 14.sp,
                                    modifier = Modifier.width(56.dp))
                                Spacer(Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(lang.name,
                                        fontWeight = if (isSelected || isApplied) FontWeight.ExtraBold else FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        color = when {
                                            isApplied  -> Color(0xFF2E7D32)
                                            isSelected -> Color(0xFF1A237E)
                                            else       -> Color(0xFF1A1A2E)
                                        })
                                    Text(lang.native, fontSize = 12.sp,
                                        color = when {
                                            isApplied  -> Color(0xFF2E7D32).copy(alpha = 0.7f)
                                            isSelected -> Color(0xFF1A237E).copy(alpha = 0.7f)
                                            else       -> Color(0xFF888888)
                                        })
                                }
                                // Right side status
                                when {
                                    isApplied -> Text("✓ " + translationViewModel.t("Applied"), color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    isApplyingLang && isSelected ->
                                        CircularProgressIndicator(modifier = Modifier.size(18.dp),
                                            strokeWidth = 2.dp, color = Color(0xFF1A237E))
                                    isSelected -> Text("• " + translationViewModel.t("Selected"), color = Color(0xFF4CAF50),
                                        fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    else -> RadioButton(selected = false,
                                        onClick = { selectedLang = lang.name },
                                        colors = RadioButtonDefaults.colors(unselectedColor = Color(0xFFCCCCCC)))
                                }                            }
                            Divider(color = Color(0xFFF0F0F0))
                        }
                    }
                }
            }

            // Info note
            Card(shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Info, null, tint = Color(0xFF1565C0), modifier = Modifier.size(18.dp))
                    Text(translationViewModel.t("Switching allows you to manage your business in your preferred native language. Language packs are fetched once and then available offline."),
                        fontSize = 12.sp, color = Color(0xFF1565C0))
                }
            }

            // Save & Apply button
            Button(
                onClick = {
                    when {
                        selectedLang == currentLanguage ->
                            scope.launch { snackbarHostState.showSnackbar("$selectedLang is already applied") }
                        else -> {
                            translationViewModel.applyLanguage(selectedLang)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                enabled = !isApplyingLang
            ) {
                if (isApplyingLang) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp, color = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text(translationViewModel.t("Applying") + "...", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                } else {
                    val btnLabel = if (selectedLang == currentLanguage) translationViewModel.t("Already Applied")
                                   else translationViewModel.t("Save & Apply")
                    Text(btnLabel, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}

// ─── SCREEN 5: NotificationsSettingsScreen ───────────────────────────────────
private data class NotifItem(
    val id: Int,
    val title: String,
    val body: String,
    val time: String,
    val borderColor: Color,
    val unread: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsSettingsScreen(
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit
) {
    // Use mutableStateListOf so individual read-state changes trigger recompose
    val readIds = remember { mutableStateListOf<Int>() }

    val notifications = remember {
        listOf(
            NotifItem(1, "Sales Report Ready",
                "Your monthly sales report is available in Reports → P&L Statement.",
                "Today, 8:00 AM", Color(0xFF1565C0), unread = true),
            NotifItem(2, "Customer Added",
                "A new customer was registered in your store.",
                "Yesterday, 3:45 PM", Color(0xFF2E7D32), unread = false),
            NotifItem(3, "Invoice Created",
                "Invoice INV-001 was created successfully.",
                "2 days ago", Color(0xFFC62828), unread = false)
        )
    }
    val liveAlerts = remember {
        listOf(
            NotifItem(4, "Pending Udhar Balance",
                "Net outstanding credit: ₹3,980. Review customer ledgers.",
                "", Color(0xFFF9A825)),
            NotifItem(5, "Low Stock: Toor Dal 1kg",
                "Only 7 units remaining. Restock soon!",
                "", Color(0xFFC62828))
        )
    }

    // Notification preference toggles
    var pushEnabled    by remember { mutableStateOf(true) }
    var stockAlerts    by remember { mutableStateOf(true) }
    var paymentAlerts  by remember { mutableStateOf(true) }
    var salesSummary   by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(translationViewModel.t("Notifications"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, translationViewModel.t("Back")) }
                },
                actions = {
                    TextButton(onClick = {
                        // Mark all unread notifications as read
                        notifications.filter { it.unread }.forEach { readIds.add(it.id) }
                    }) {
                        Text(translationViewModel.t("Mark all read"), color = Color(0xFF1A237E), fontSize = 13.sp)
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Notification preference toggles
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    NotifToggleRow(t("Push Notifications"), t("Receive alerts on your device"), pushEnabled) { pushEnabled = it }
                    Divider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(start = 16.dp))
                    NotifToggleRow(t("Low Stock Alerts"), t("Get notified when items are low"), stockAlerts) { stockAlerts = it }
                    Divider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(start = 16.dp))
                    NotifToggleRow(t("Payment Reminders"), t("Alerts for pending payments"), paymentAlerts) { paymentAlerts = it }
                    Divider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(start = 16.dp))
                    NotifToggleRow(t("Daily Sales Summary"), t("Morning report at 8 AM"), salesSummary) { salesSummary = it }
                }
            }

            // Recent notifications
            Text(t("Recent"), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
            notifications.forEach { notif ->
                NotifCard(
                    notif = notif,
                    showDot = notif.unread && !readIds.contains(notif.id),
                    onTap = { readIds.add(notif.id) },
                    translationViewModel = translationViewModel
                )
            }

            // Live alerts
            Text("⚠ " + t("Live Alerts"), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
            liveAlerts.forEach { alert ->
                NotifCard(notif = alert, showDot = false, onTap = {}, translationViewModel = translationViewModel)
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun NotifToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF888888))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF1A237E)
            )
        )
    }
}

@Composable
private fun NotifCard(notif: NotifItem, showDot: Boolean, onTap: () -> Unit, translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel) {
    @Composable fun t(s: String) = translationViewModel.t(s)
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (showDot) Color(0xFFF8F9FF) else Color.White
        ),
        modifier = Modifier.fillMaxWidth().clickable { onTap() }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .heightIn(min = 72.dp)
                    .background(notif.borderColor)
            )
            Column(modifier = Modifier.weight(1f).padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        notif.title,
                        fontWeight = if (showDot) FontWeight.ExtraBold else FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (showDot) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFF1565C0), CircleShape)
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(notif.body, fontSize = 12.sp, color = Color(0xFF666666))
                if (notif.time.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(notif.time, fontSize = 11.sp, color = Color(0xFFAAAAAA))
                }
            }
        }
    }
}

// ─── SCREEN 6: HelpSupportScreen ─────────────────────────────────────────────
private data class FaqItem(val question: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current   // ← hoisted here so all blocks can use it
    var searchQuery by remember { mutableStateOf("") }
    val allFaqs = remember {
        listOf(
            FaqItem("How do I add a new product?",
                "Go to Inventory → tap the + button → fill in product details like name, price, and stock quantity → tap Save."),
            FaqItem("How do I create an invoice?",
                "Navigate to Billing → tap New Invoice → select a customer → add items from inventory → tap Finalize & Save."),
            FaqItem("How do I track customer credit (Udhar)?",
                "Open Customers → select a customer → tap the customer card to view the Ledger with all transactions and outstanding balance."),
            FaqItem("How do I view my P&L report?",
                "Go to Reports from the Dashboard top bar → view Sales, Udhar, Low Stock counts and recent transactions."),
            FaqItem("How do I add an expense?",
                "Open Expenses from the bottom navigation bar → tap the + button → enter amount, description, category → tap Save Expense."),
            FaqItem("How do I change the app language?",
                "Go to Settings (Profile icon) → App Language → select your preferred language → tap Save & Apply."),
            FaqItem("Can I connect a thermal printer?",
                "Yes! Go to Settings → Printer Settings → enable Bluetooth Printing → select your printer from the Available Printers list.")
        )
    }
    val filteredFaqs = remember(searchQuery) {
        if (searchQuery.isBlank()) allFaqs
        else allFaqs.filter {
            it.question.contains(searchQuery, ignoreCase = true) ||
            it.answer.contains(searchQuery, ignoreCase = true)
        }
    }
    var expandedFaq by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(searchQuery) { expandedFaq = null }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Help & Support"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, translationViewModel.t("Back")) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F6FA))
            )
        },
        containerColor = Color(0xFFF5F6FA)
    ) { padding ->
        @Composable fun t(s: String) = translationViewModel.t(s)
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(t("Search help articles...")) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            // Support contact cards — only show when not searching
            if (searchQuery.isBlank()) {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Email Support card — tappable, opens email app
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                try {
                                    // ACTION_VIEW with mailto: is the most reliable way to open email
                                    val uri = Uri.parse(
                                        "mailto:support@smartbiz.app" +
                                        "?subject=" + Uri.encode("SmartBiz App Support")
                                    )
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("No email app found. Email: support@smartbiz.app")
                                    }
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Email, null, tint = Color(0xFF1565C0), modifier = Modifier.size(28.dp))
                            Spacer(Modifier.height(6.dp))
                            Text("Email Support", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1565C0))
                            Text("support@smartbiz.app", fontSize = 11.sp, color = Color(0xFF444444), textAlign = TextAlign.Center)
                        }
                    }

                    // Call Support card — tappable, opens phone dialer
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                try {
                                    // ACTION_DIAL opens dialer with number pre-filled, no permission needed
                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+918001234567"))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("No phone app found. Call: +91 800 123 4567")
                                    }
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Phone, null, tint = Color(0xFF2E7D32), modifier = Modifier.size(28.dp))
                            Spacer(Modifier.height(6.dp))
                            Text("Call Support", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
                            Text("+91 800 123 4567", fontSize = 11.sp, color = Color(0xFF444444), textAlign = TextAlign.Center)
                        }
                    }
                }

                // App info card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("SmartBiz", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Version 1.0.0 · Build 2026", fontSize = 12.sp, color = Color(0xFF888888))
                        }
                        Icon(Icons.Default.Info, null, tint = Color(0xFF1A237E), modifier = Modifier.size(24.dp))
                    }
                }
            }

            // FAQ section header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.HelpOutline, null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    if (searchQuery.isBlank()) "Frequently Asked Questions"
                    else "Results for \"$searchQuery\" (${filteredFaqs.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A2E)
                )
            }

            // FAQ accordion — filtered
            if (filteredFaqs.isEmpty()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text("No results found for \"$searchQuery\"", color = Color.Gray, textAlign = TextAlign.Center)
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        filteredFaqs.forEachIndexed { idx, faq ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        expandedFaq = if (expandedFaq == idx) null else idx
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        faq.question,
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = if (expandedFaq == idx) Color(0xFF1A237E) else Color(0xFF1A1A2E)
                                    )
                                    Icon(
                                        if (expandedFaq == idx) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                        null,
                                        tint = if (expandedFaq == idx) Color(0xFF1A237E) else Color(0xFF888888),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                AnimatedVisibility(visible = expandedFaq == idx) {
                                    Text(
                                        faq.answer,
                                        fontSize = 13.sp,
                                        color = Color(0xFF666666),
                                        modifier = Modifier
                                            .background(Color(0xFFF8F9FF))
                                            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp, top = 4.dp)
                                    )
                                }
                            }
                            if (idx < filteredFaqs.lastIndex) Divider(color = Color(0xFFF0F0F0))
                        }
                    }
                }
            }

            // "Still need help?" bottom card — only when not searching
            if (searchQuery.isBlank()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Chat, null, tint = Color.White, modifier = Modifier.size(28.dp))
                        Text("Still need help?", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                        Text(
                            "Our support team is available Mon-Sat, 9 AM to 6 PM.",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        OutlinedButton(
                            onClick = {
                                try {
                                    val uri = Uri.parse(
                                        "mailto:support@smartbiz.app" +
                                        "?subject=" + Uri.encode("SmartBiz App Support")
                                    )
                                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                } catch (e: Exception) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Email: support@smartbiz.app")
                                    }
                                }
                            },
                            border = BorderStroke(1.dp, Color.White),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("✉ Contact Us", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

