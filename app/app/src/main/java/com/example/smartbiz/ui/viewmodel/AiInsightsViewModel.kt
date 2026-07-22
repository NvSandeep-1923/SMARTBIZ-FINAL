package com.example.smartbiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.models.AiInsights
import com.example.smartbiz.data.repository.SmartBizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AiInsightsState {
    object Loading : AiInsightsState()
    data class Success(val insights: AiInsights) : AiInsightsState()
    data class Error(val message: String) : AiInsightsState()
}

class AiInsightsViewModel(
    private val repository: SmartBizRepository = SmartBizRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<AiInsightsState>(AiInsightsState.Loading)
    val uiState: StateFlow<AiInsightsState> = _uiState

    init { load() }

    fun load() {
        viewModelScope.launch {
            _uiState.value = AiInsightsState.Loading
            try {
                val response = repository.getAiInsights()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = AiInsightsState.Success(response.body()!!)
                } else {
                    _uiState.value = AiInsightsState.Error("Failed to load AI insights")
                }
            } catch (e: Exception) {
                _uiState.value = AiInsightsState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
