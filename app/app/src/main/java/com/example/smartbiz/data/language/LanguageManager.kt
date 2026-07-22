package com.example.smartbiz.data.language

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/**
 * Manages app-wide language switching using Android's official AppCompatDelegate.
 * Persists language selection in SharedPreferences.
 * Works on all API levels (minSdk 24+).
 * NO downloads required — all strings are bundled in the APK.
 */
object LanguageManager {

    private const val PREFS_NAME  = "smartbiz_language"
    private const val KEY_LANG    = "selected_language"
    const val DEFAULT_LANG        = "en"

    /** Map of display name → BCP-47 language tag */
    val languages = linkedMapOf(
        "English"  to "en",
        "Hindi"    to "hi",
        "Telugu"   to "te",
        "Marathi"  to "mr",
        "Gujarati" to "gu",
        "Tamil"    to "ta"
    )

    /** Retrieve the persisted language code (e.g. "hi") */
    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANG, DEFAULT_LANG) ?: DEFAULT_LANG
    }

    /** Get display name from a BCP-47 code */
    fun getDisplayName(code: String): String =
        languages.entries.firstOrNull { it.value == code }?.key ?: "English"

    /**
     * Apply language globally using AppCompatDelegate.
     * This persists across app restarts automatically.
     * Call this when user selects a language.
     */
    fun applyLanguage(context: Context, languageCode: String) {
        // Persist
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_LANG, languageCode).apply()

        // Apply via AppCompatDelegate (recommended approach)
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    /**
     * Apply the saved language on app start.
     * Call this in Application.onCreate() or MainActivity.onCreate() before setContent.
     */
    fun applyOnStart(context: Context) {
        val code = getSavedLanguage(context)
        val localeList = LocaleListCompat.forLanguageTags(code)
        AppCompatDelegate.setApplicationLocales(localeList)
    }
}
