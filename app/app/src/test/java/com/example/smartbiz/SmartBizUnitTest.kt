package com.example.smartbiz

import org.junit.Test
import org.junit.Assert.*

/**
 * SmartBiz Android App Unit Test Suite
 */
class SmartBizUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun app_name_constant_is_valid() {
        val appName = "SmartBiz"
        assertNotNull(appName)
        assertEquals("SmartBiz", appName)
    }

    @Test
    fun currency_formatter_handles_zero() {
        val amount = 0.0
        val formatted = String.format("₹%.2f", amount)
        assertEquals("₹0.00", formatted)
    }

    @Test
    fun email_validator_accepts_valid_email() {
        val email = "user@smartbiz.com"
        val isValid = email.contains("@") && email.contains(".")
        assertTrue(isValid)
    }

    @Test
    fun email_validator_rejects_invalid_email() {
        val email = "invalid-email"
        val isValid = email.contains("@") && email.contains(".")
        assertFalse(isValid)
    }
}
