package com.example.smartbiz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.smartbiz.data.models.Invoice
import com.example.smartbiz.data.models.InventoryItem
import com.example.smartbiz.data.models.InvoiceItem
import com.example.smartbiz.ui.viewmodel.CustomersState
import com.example.smartbiz.ui.viewmodel.CustomersViewModel
import com.example.smartbiz.ui.viewmodel.InventoryState
import com.example.smartbiz.ui.viewmodel.InventoryViewModel
import com.example.smartbiz.ui.viewmodel.InvoiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceBuilderScreen(
    invoiceViewModel: InvoiceViewModel = viewModel(),
    inventoryViewModel: InventoryViewModel = viewModel(),
    customersViewModel: CustomersViewModel = viewModel(),
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val articles by invoiceViewModel.selectedItems.collectAsState()
    val inventoryState by inventoryViewModel.uiState.collectAsState()
    val customersState by customersViewModel.uiState.collectAsState()

    val subtotal = articles.sumOf { it.qty * it.sale_price }
    val totalGst = articles.sumOf { it.gst_amount }
    val grandTotal = subtotal + totalGst

    var showItemPicker by remember { mutableStateOf(false) }
    var showCustomerPicker by remember { mutableStateOf(false) }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var isCreating by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    // Item picker dialog
    if (showItemPicker) {
        ItemPickerDialog(
            inventoryState = inventoryState,
            onDismiss = { showItemPicker = false },
            onItemSelected = { item, qty ->
                val gstAmt = item.sale_price * qty * (item.gst_rate / 100.0)
                invoiceViewModel.addItem(
                    InvoiceItem(
                        item_id = item.id ?: 0,
                        item_name = item.item_name,
                        qty = qty,
                        sale_price = item.sale_price,
                        gst_rate = item.gst_rate,
                        gst_amount = gstAmt,
                        subtotal = item.sale_price * qty + gstAmt
                    )
                )
                showItemPicker = false
            }
        )
    }

    // Customer picker dialog
    if (showCustomerPicker) {
        CustomerPickerDialog(
            customersState = customersState,
            onDismiss = { showCustomerPicker = false },
            onCustomerSelected = { customer ->
                selectedCustomer = customer
                showCustomerPicker = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(translationViewModel.t("Invoice Builder")) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = translationViewModel.t("Back"))
                    }
                },
                actions = {
                    IconButton(onClick = { invoiceViewModel.clearItems() }) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = translationViewModel.t("Clear"), tint = Color(0xFFC62828))
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // Customer selector
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { showCustomerPicker = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedCustomer != null) Color(0xFFE8EAF6) else Color(0xFFF9FAFC)
                ),
                border = BorderStroke(1.dp, if (selectedCustomer != null) Color(0xFF1A237E) else Color(0xFFE8EAF6))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            if (selectedCustomer != null) selectedCustomer!!.name else translationViewModel.t("Select Customer"),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A237E), fontSize = 15.sp
                        )
                        if (selectedCustomer?.phone != null)
                            Text(selectedCustomer!!.phone!!, fontSize = 12.sp, color = Color.Gray)
                    }
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                }
            }

            // Items header
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(translationViewModel.t("Billing Items") + " (${articles.size})", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1A237E))
                FilledTonalButton(
                    onClick = { showItemPicker = true },
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFFE8EAF6))
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(translationViewModel.t("Add Item"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E), fontSize = 13.sp)
                }
            }

            // Error
            if (errorMsg != null) {
                Text(errorMsg!!, color = Color.Red, modifier = Modifier.padding(horizontal = 16.dp), fontSize = 13.sp)
            }

            // Item list
            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                if (articles.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
                            Text(translationViewModel.t("No items added yet.\nTap 'Add Item' above."), color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                }
                items(articles) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.item_name, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                                Text("${item.qty}× ₹${item.sale_price} + GST ${item.gst_rate}%", fontSize = 12.sp, color = Color.Gray)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("₹${String.format("%.2f", item.subtotal)}", fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
                                IconButton(onClick = { invoiceViewModel.removeItem(item) }, modifier = Modifier.size(24.dp)) {
                                    Icon(Icons.Default.Close, contentDescription = translationViewModel.t("Remove"), tint = Color.Red, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Totals + Finalize
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(translationViewModel.t("Subtotal"), color = Color.Gray)
                        Text("₹${String.format("%.2f", subtotal)}", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(translationViewModel.t("GST"), color = Color.Gray)
                        Text("₹${String.format("%.2f", totalGst)}", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(translationViewModel.t("Grand Total"), fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color(0xFF1A237E))
                        Text("₹${String.format("%.2f", grandTotal)}", fontWeight = FontWeight.Black, fontSize = 20.sp, color = Color(0xFF1A237E))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            when {
                                selectedCustomer == null -> errorMsg = translationViewModel.translate("Please select a customer")
                                articles.isEmpty() -> errorMsg = translationViewModel.translate("Add at least one item")
                                else -> {
                                    errorMsg = null
                                    isCreating = true
                                    val invoice = Invoice(
                                        invoice_number = "INV-${System.currentTimeMillis()}",
                                        customer_id = selectedCustomer!!.id ?: 1,
                                        subtotal = subtotal,
                                        total_gst = totalGst,
                                        grand_total = grandTotal,
                                        items = articles
                                    )
                                    invoiceViewModel.createInvoice(invoice) {
                                        isCreating = false
                                        onNavigateBack()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                        enabled = !isCreating
                    ) {
                        if (isCreating) CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color.White)
                        else Text(translationViewModel.t("FINALIZE & SAVE"), fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemPickerDialog(
    inventoryState: com.example.smartbiz.ui.viewmodel.InventoryState,
    onDismiss: () -> Unit,
    onItemSelected: (InventoryItem, Double) -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    var selectedItem by remember { mutableStateOf<InventoryItem?>(null) }
    var qty by remember { mutableStateOf("1") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(translationViewModel.t("Add Item to Invoice"), fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                when (inventoryState) {
                    is InventoryState.Loading -> CircularProgressIndicator()
                    is InventoryState.Error -> Text(inventoryState.message, color = Color.Red)
                    is InventoryState.Success -> {
                        if (inventoryState.items.isEmpty()) {
                            Text(translationViewModel.t("No inventory items. Add items first."), color = Color.Gray)
                        } else {
                            Text(translationViewModel.t("Select Item:"), fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                            LazyColumn(modifier = Modifier.heightIn(max = 240.dp)) {
                                items(items = inventoryState.items) { item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable { selectedItem = item }
                                            .background(
                                                if (selectedItem?.id == item.id) Color(0xFFE8EAF6)
                                                else Color.Transparent,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(item.item_name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                            Text(translationViewModel.t("Stock") + ": ${item.stock_level} ${item.unit ?: "pcs"}", fontSize = 11.sp, color = Color.Gray)
                                        }
                                        Text("₹${item.sale_price}", fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                                    }
                                }
                            }
                            if (selectedItem != null) {
                                OutlinedTextField(
                                    value = qty, onValueChange = { qty = it },
                                    label = { Text(translationViewModel.t("Quantity")) },
                                    modifier = Modifier.fillMaxWidth(), singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val q = qty.toDoubleOrNull() ?: return@Button
                    selectedItem?.let { onItemSelected(it, q) }
                },
                enabled = selectedItem != null && qty.toDoubleOrNull() != null && (qty.toDoubleOrNull() ?: 0.0) > 0,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
            ) { Text(translationViewModel.t("ADD")) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(translationViewModel.t("Cancel")) } }
    )
}

@Composable
fun CustomerPickerDialog(
    customersState: CustomersState,
    onDismiss: () -> Unit,
    onCustomerSelected: (Customer) -> Unit,
    translationViewModel: com.example.smartbiz.ui.viewmodel.TranslationViewModel = viewModel()
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(translationViewModel.t("Select Customer"), fontWeight = FontWeight.Bold) },
        text = {
            when (customersState) {
                is CustomersState.Loading -> CircularProgressIndicator()
                is CustomersState.Error -> Text(customersState.message, color = Color.Red)
                is CustomersState.Success -> {
                    if (customersState.customers.isEmpty()) {
                        Text(translationViewModel.t("No customers yet. Add a customer first."), color = Color.Gray)
                    } else {
                        LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                            items(customersState.customers) { customer ->
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .clickable { onCustomerSelected(customer) }
                                        .padding(vertical = 12.dp, horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(customer.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                        if (customer.phone != null)
                                            Text(customer.phone, fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                                Divider(color = Color(0xFFF0F0F0))
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text(translationViewModel.t("Cancel")) } }
    )
}
