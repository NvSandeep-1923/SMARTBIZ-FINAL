package com.example.smartbiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.models.Customer
import com.example.smartbiz.data.models.LedgerTransaction
import com.example.smartbiz.data.repository.SmartBizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CustomersState {
    object Loading : CustomersState()
    data class Success(val customers: List<Customer>) : CustomersState()
    data class Error(val message: String) : CustomersState()
}

sealed class LedgerState {
    object Idle : LedgerState()
    object Loading : LedgerState()
    data class Success(val transactions: List<LedgerTransaction>) : LedgerState()
    data class Error(val message: String) : LedgerState()
}

class CustomersViewModel(private val repository: SmartBizRepository = SmartBizRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<CustomersState>(CustomersState.Loading)
    val uiState: StateFlow<CustomersState> = _uiState

    private val _ledgerState = MutableStateFlow<LedgerState>(LedgerState.Idle)
    val ledgerState: StateFlow<LedgerState> = _ledgerState

    init {
        loadCustomers()
    }

    fun loadCustomers() {
        viewModelScope.launch {
            _uiState.value = CustomersState.Loading
            try {
                val response = repository.getCustomers()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = CustomersState.Success(response.body()!!)
                } else {
                    _uiState.value = CustomersState.Error("Failed to load customers")
                }
            } catch (e: Exception) {
                _uiState.value = CustomersState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadLedger(customerId: Int) {
        viewModelScope.launch {
            _ledgerState.value = LedgerState.Loading
            try {
                val response = repository.getLedger(customerId)
                if (response.isSuccessful && response.body() != null) {
                    _ledgerState.value = LedgerState.Success(response.body()!!)
                } else {
                    _ledgerState.value = LedgerState.Error("Failed to load ledger")
                }
            } catch (e: Exception) {
                _ledgerState.value = LedgerState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addCustomer(customer: Customer, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.addCustomer(customer)
                if (response.isSuccessful) {
                    loadCustomers()
                    onSuccess()
                }
            } catch (e: Exception) { }
        }
    }

    fun addLedgerEntry(entry: LedgerTransaction, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.addLedgerEntry(entry)
                if (response.isSuccessful) {
                    loadLedger(entry.customer_id)
                    loadCustomers() // Balance changed
                    onSuccess()
                }
            } catch (e: Exception) { }
        }
    }
}
