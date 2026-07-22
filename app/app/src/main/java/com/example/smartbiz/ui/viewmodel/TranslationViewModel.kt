package com.example.smartbiz.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbiz.data.translation.TranslationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TranslationState {
    object Idle : TranslationState()
    object Downloading : TranslationState()
    object Ready : TranslationState()
    data class Error(val message: String) : TranslationState()
}

class TranslationViewModel : ViewModel() {

    private val _state = MutableStateFlow<TranslationState>(TranslationState.Idle)
    val state: StateFlow<TranslationState> = _state

    private val _currentLanguage = MutableStateFlow("English")
    val currentLanguage: StateFlow<String> = _currentLanguage

    // Track which languages have been downloaded
    private val _readyLanguages = MutableStateFlow(mutableSetOf("English"))
    val readyLanguages: StateFlow<Set<String>> = _readyLanguages

    init {
        // Mark languages with fallbacks as ready initially
        val initialReady = mutableSetOf("English", "Hindi", "Telugu")
        _readyLanguages.value = initialReady
    }

    /** Switch language — downloads from backend if needed */
    fun applyLanguage(language: String) {
        if (language == _currentLanguage.value) return

        viewModelScope.launch {
            _state.value = TranslationState.Downloading
            
            val success = TranslationManager.fetchTranslations(language)
            
            if (success || TranslationManager.isModelReady(language)) {
                TranslationManager.setLanguage(language)
                _currentLanguage.value = language
                
                val updatedReady = _readyLanguages.value.toMutableSet()
                updatedReady.add(language)
                _readyLanguages.value = updatedReady
                
                _state.value = TranslationState.Ready
            } else {
                _state.value = TranslationState.Error("Failed to download translations for $language")
            }
        }
    }

    /**
     * Reactive translation. Use this inside Composable functions.
     * Automatically triggers recomposition when language changes.
     */
    @Composable
    fun t(text: String): String {
        val currentLang by currentLanguage.collectAsState()
        return TranslationManager.translate(text)
    }

    /** Non-reactive translation for use in non-composable contexts */
    fun translate(text: String): String = TranslationManager.translate(text)
}
