package com.example.smartbiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartbiz.data.models.LedgerTransaction
import com.example.smartbiz.ui.viewmodel.CustomersViewModel
import com.example.smartbiz.ui.viewmodel.LedgerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerLedgerScreen(
    customerId: Int,
    viewModel: CustomersViewModel = viewModel(),
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val ledgerState by viewModel.ledgerState.collectAsState()

    // Dialog state
    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("gave") } // "gave" or "got"
    var dialogAmount by remember { mutableStateOf("") }
    var dialogNote by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(customerId) {
        viewModel.loadLedger(customerId)
    }

    // Transaction dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    if (dialogType == "gave") translationViewModel.t("You Gave (Credit)") else translationViewModel.t("You Got (Payment)"),
                    fontWeight = FontWeight.Bold,
                    color = if (dialogType == "gave") Color(0xFFC62828) else Color(0xFF2E7D32)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = dialogAmount,
                        onValueChange = { dialogAmount = it },
                        label = { Text(translationViewModel.t("Amount (₹)")) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = dialogNote,
                        onValueChange = { dialogNote = it },
                        label = { Text(translationViewModel.t("Note (optional)")) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = dialogAmount.toDoubleOrNull() ?: return@Button
                        isSaving = true
                        val entry = LedgerTransaction(
                            customer_id = customerId,
                            amount = amount,
                            type = dialogType,
                            description = dialogNote.ifBlank { null }
                        )
                        viewModel.addLedgerEntry(entry) {
                            isSaving = false
                            showDialog = false
                            dialogAmount = ""
                            dialogNote = ""
                        }
                    },
                    enabled = dialogAmount.isNotBlank() && !isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (dialogType == "gave") Color(0xFFC62828) else Color(0xFF2E7D32)
                    )
                ) {
                    if (isSaving) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                    else Text(translationViewModel.t("SAVE"))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text(translationViewModel.t("Cancel")) }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Customer Ledger")) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = translationViewModel.t("Back"))
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (val state = ledgerState) {
                    is LedgerState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    is LedgerState.Success -> {
                        if (state.transactions.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(translationViewModel.t("No transactions yet"), color = Color.Gray)
                            }
                        } else {
                            LedgerList(state.transactions, translationViewModel)
                        }
                    }
                    is LedgerState.Error -> Text(
                        state.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    else -> {}
                }
            }

            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        dialogType = "gave"
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) {
                    Text(translationViewModel.t("YOU GAVE ₹"), fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        dialogType = "got"
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text(translationViewModel.t("YOU GOT ₹"), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun LedgerList(
    transactions: List<LedgerTransaction>,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(transactions) { tx ->
            LedgerItem(tx, translationViewModel)
        }
    }
}

@Composable
fun LedgerItem(
    tx: LedgerTransaction,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    val isGave = tx.type == "gave"
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    tx.description ?: if (isGave) translationViewModel.t("Credit Given") else translationViewModel.t("Payment Received"),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(tx.tx_date ?: translationViewModel.t("Just now"), fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (isGave) "-" else "+"} ₹${tx.amount}",
                    color = if (isGave) Color(0xFFC62828) else Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = if (isGave) translationViewModel.t("GAVE") else translationViewModel.t("GOT"),
                    color = if (isGave) Color(0xFFC62828) else Color(0xFF2E7D32),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
