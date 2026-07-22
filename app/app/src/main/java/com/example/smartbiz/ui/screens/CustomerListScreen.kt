package com.example.smartbiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
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
import com.example.smartbiz.data.models.Customer
import com.example.smartbiz.ui.viewmodel.CustomersState
import com.example.smartbiz.ui.viewmodel.CustomersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerListScreen(
    viewModel: CustomersViewModel = viewModel(),
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToLedger: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    // Add customer dialog
    if (showAddDialog) {
        AddCustomerDialog(
            onDismiss = { showAddDialog = false },
            onSave = { customer ->
                viewModel.addCustomer(customer) { showAddDialog = false }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Customers")) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = translationViewModel.t("Back"))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF1A237E)
            ) {
                Icon(Icons.Default.Add, contentDescription = translationViewModel.t("Add Customer"), tint = Color.White)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {
                is CustomersState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is CustomersState.Success -> {
                    if (state.customers.isEmpty()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(translationViewModel.t("No customers yet"), color = Color.Gray, fontSize = 16.sp)
                            Text(translationViewModel.t("Tap + to add your first customer"), color = Color.LightGray, fontSize = 13.sp)
                        }
                    } else {
                        CustomerList(state.customers, onNavigateToLedger, translationViewModel)
                    }
                }
                is CustomersState.Error -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.message, color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadCustomers() }) { Text(translationViewModel.t("Retry")) }
                }
            }
        }
    }
}

@Composable
fun AddCustomerDialog(
    onDismiss: () -> Unit,
    onSave: (Customer) -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(translationViewModel.t("Add Customer"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    label = { Text(translationViewModel.t("Customer Name *")) },
                    modifier = Modifier.fillMaxWidth(), singleLine = true
                )
                OutlinedTextField(
                    value = phone, onValueChange = { phone = it },
                    label = { Text(translationViewModel.t("Phone Number")) },
                    modifier = Modifier.fillMaxWidth(), singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text(translationViewModel.t("Email (optional)")) },
                    modifier = Modifier.fillMaxWidth(), singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        isSaving = true
                        onSave(Customer(name = name, phone = phone.ifBlank { null }, email = email.ifBlank { null }))
                    }
                },
                enabled = name.isNotBlank() && !isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
            ) {
                if (isSaving) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color.White)
                else Text(translationViewModel.t("SAVE"))
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(translationViewModel.t("Cancel")) } }
    )
}

@Composable
fun CustomerList(
    customers: List<Customer>,
    onNavigateToLedger: (Int) -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(customers) { customer ->
            CustomerCard(customer, onClick = { customer.id?.let { onNavigateToLedger(it) } }, translationViewModel = translationViewModel)
        }
    }
}

@Composable
fun CustomerCard(
    customer: Customer,
    onClick: () -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(46.dp).background(Color(0xFFE8EAF6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = customer.name.first().uppercaseChar().toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A237E)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(customer.name, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF1A237E))
                Text(customer.phone ?: customer.email ?: translationViewModel.t("No contact info"), fontSize = 13.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                val balance = customer.balance
                Text(
                    text = "₹${Math.abs(balance).toLong()}",
                    fontWeight = FontWeight.Black, fontSize = 16.sp,
                    color = if (balance > 0) Color(0xFF2E7D32) else if (balance < 0) Color(0xFFC62828) else Color.Gray
                )
                Text(
                    text = when {
                        balance > 0 -> translationViewModel.t("ADVANCE")
                        balance < 0 -> translationViewModel.t("DUE")
                        else -> translationViewModel.t("SETTLED")
                    },
                    fontSize = 10.sp, fontWeight = FontWeight.Bold,
                    color = if (balance > 0) Color(0xFF2E7D32) else if (balance < 0) Color(0xFFC62828) else Color.Gray
                )
            }
        }
    }
}
