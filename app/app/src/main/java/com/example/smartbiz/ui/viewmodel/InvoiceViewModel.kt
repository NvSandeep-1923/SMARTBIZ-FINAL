package com.example.smartbiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.models.Invoice
import com.example.smartbiz.data.models.InvoiceItem
import com.example.smartbiz.data.repository.SmartBizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InvoiceViewModel(private val repository: SmartBizRepository = SmartBizRepository()) : ViewModel() {

    private val _selectedItems = MutableStateFlow<List<InvoiceItem>>(emptyList())
    val selectedItems: StateFlow<List<InvoiceItem>> = _selectedItems

    fun addItem(item: InvoiceItem) {
        _selectedItems.value += item
    }

    fun removeItem(item: InvoiceItem) {
        _selectedItems.value -= item
    }

    fun clearItems() {
        _selectedItems.value = emptyList()
    }

    fun createInvoice(invoice: Invoice, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.createInvoice(invoice)
                if (response.isSuccessful) {
                    _selectedItems.value = emptyList()
                    onSuccess()
                }
            } catch (e: Exception) { }
        }
    }
}
