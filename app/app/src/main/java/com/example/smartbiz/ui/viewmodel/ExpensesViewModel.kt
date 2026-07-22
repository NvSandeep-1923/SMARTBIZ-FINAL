package com.example.smartbiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.models.Expense
import com.example.smartbiz.data.repository.SmartBizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ExpensesState {
    object Loading : ExpensesState()
    data class Success(val expenses: List<Expense>) : ExpensesState()
    data class Error(val message: String) : ExpensesState()
}

class ExpensesViewModel(private val repository: SmartBizRepository = SmartBizRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
    val uiState: StateFlow<ExpensesState> = _uiState

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            _uiState.value = ExpensesState.Loading
            try {
                val response = repository.getExpenses()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = ExpensesState.Success(response.body()!!)
                } else {
                    _uiState.value = ExpensesState.Error("Failed to load expenses")
                }
            } catch (e: Exception) {
                _uiState.value = ExpensesState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addExpense(expense: Expense, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.addExpense(expense)
                if (response.isSuccessful) {
                    loadExpenses()
                    onSuccess()
                }
            } catch (e: Exception) { }
        }
    }
}
