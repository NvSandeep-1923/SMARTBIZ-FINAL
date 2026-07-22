package com.example.smartbiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.models.InventoryItem
import com.example.smartbiz.data.repository.SmartBizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class InventoryState {
    object Loading : InventoryState()
    data class Success(val items: List<InventoryItem>) : InventoryState()
    data class Error(val message: String) : InventoryState()
}

class InventoryViewModel(private val repository: SmartBizRepository = SmartBizRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<InventoryState>(InventoryState.Loading)
    val uiState: StateFlow<InventoryState> = _uiState

    init {
        loadInventory()
    }

    fun loadInventory() {
        viewModelScope.launch {
            _uiState.value = InventoryState.Loading
            try {
                val response = repository.getInventory()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = InventoryState.Success(response.body()!!)
                } else {
                    _uiState.value = InventoryState.Error("Failed to load inventory")
                }
            } catch (e: Exception) {
                _uiState.value = InventoryState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addItem(item: InventoryItem, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.addItem(item)
                if (response.isSuccessful) {
                    loadInventory()
                    onSuccess()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
