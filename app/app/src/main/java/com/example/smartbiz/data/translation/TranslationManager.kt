package com.example.smartbiz.data.translation

import com.example.smartbiz.data.repository.SmartBizRepository

/**
 * Dynamic translation manager.
 * Initially uses a small static fallback, but fetches full sets from the backend.
 */
object TranslationManager {

    private val repository = SmartBizRepository()

    // Key: Language, Value: (Key: English string, Value: Translated string)
    private val dynamicTranslations = mutableMapOf<String, MutableMap<String, String>>()

    private val fallbackTranslations: Map<String, Map<String, String>> = mapOf(
        "Hindi" to mapOf(
            "SmartBiz" to "स्मार्टबिज़",
            "Business Overview" to "व्यापार अवलोकन",
            "Today's Sales" to "आज की बिक्री",
            "Total Udhar" to "कुल उधार",
            "Items Low on Stock" to "कम स्टॉक वाले आइटम",
            "Tap to reorder" to "पुनः ऑर्डर करें",
            "Quick Actions" to "त्वरित क्रियाएँ",
            "Billing" to "बिलिंग",
            "Customers" to "ग्राहक",
            "Inventory" to "इन्वेंटरी",
            "Expenses" to "खर्च",
            "Recent Transactions" to "हाल के लेनदेन",
            "See All" to "सभी देखें",
            "AI Business Insights" to "AI व्यापार अंतर्दृष्टि",
            "App Language" to "ऐप की भाषा",
            "Settings" to "सेटिंग्स",
            "Apply" to "लागू करें"
        ),
        "Telugu" to mapOf(
            "SmartBiz" to "స్మార్ట్‌బిజ్",
            "Business Overview" to "వ్యాపార అవలోకనం",
            "Today's Sales" to "నేటి అమ్మకాలు",
            "Total Udhar" to "మొత్తం అప్పు",
            "Items Low on Stock" to "స్టాక్ తక్కువగా ఉన్న వస్తువులు",
            "Quick Actions" to "త్వరిత చర్యలు",
            "Billing" to "బిల్లింగ్",
            "Customers" to "కస్టమర్లు",
            "Inventory" to "జాబితా",
            "Expenses" to "ఖర్చులు",
            "Recent Transactions" to "ఇటీవలి లావాదేవీలు",
            "See All" to "అన్నీ చూడండి",
            "AI Business Insights" to "AI వ్యాపార అంతర్దృష్టులు",
            "App Language" to "యాప్ భాష",
            "Settings" to "సెట్టింగ్‌లు",
            "Apply" to "వర్తింపజేయండి"
        )
    )

    private var activeLanguage: String = "English"

    val supportedLanguages = listOf("English", "Hindi", "Telugu", "Marathi", "Gujarati", "Tamil")

    fun setLanguage(language: String) {
        activeLanguage = language
    }

    fun getCurrentLanguage() = activeLanguage

    fun isEnglish() = activeLanguage == "English"

    /**
     * Fetch all translations for a language from the backend.
     * Overwrites local cache on success.
     */
    suspend fun fetchTranslations(language: String): Boolean {
        if (language == "English") return true
        
        return try {
            val response = repository.getTranslations(language)
            if (response.isSuccessful && response.body() != null) {
                dynamicTranslations[language] = response.body()!!.toMutableMap()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    /** Translate a string — tries dynamic map, then fallback, then returns key */
    fun translate(text: String): String {
        if (activeLanguage == "English") return text
        
        // 1. Try dynamic (fetched from backend)
        val dynamic = dynamicTranslations[activeLanguage]?.get(text)
        if (dynamic != null) return dynamic
        
        // 2. Try static fallback (built-in)
        return fallbackTranslations[activeLanguage]?.get(text) ?: text
    }

    /** Ready if we have ANY translations for this language (even fallback) */
    fun isModelReady(language: String): Boolean {
        if (language == "English") return true
        return dynamicTranslations.containsKey(language) || fallbackTranslations.containsKey(language)
    }

    fun isDownloading(language: String): Boolean = false // Handled in ViewModel

    fun close() { /* no-op */ }
}
