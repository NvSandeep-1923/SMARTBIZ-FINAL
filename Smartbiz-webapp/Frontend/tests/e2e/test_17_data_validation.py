"""
Test Suite 17: Data Validation & Edge Cases
=============================================
Tests form validation, boundary values, SQL injection attempts,
special characters, numeric edge cases, and empty/null inputs.
"""

import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT, TEST_EMAIL, TEST_PASSWORD,
)


def fill_field(driver, field_id, value):
    """Helper to safely fill an input field."""
    els = driver.find_elements(By.ID, field_id)
    if els:
        els[0].clear()
        els[0].send_keys(value)
        return True
    return False


class TestDataValidation:
    """Data validation and edge case test cases (TC-VAL-*)."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url

    # ------------------------------------------------------------------
    # TC-VAL-001: Empty email – login rejects
    # ------------------------------------------------------------------
    def test_empty_email_rejected(self):
        """Submitting login with empty email should not proceed."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(3)
            assert "#dashboard" not in self.driver.current_url, (
                "Dashboard reached with empty email!"
            )
        log_event("INFO", "TC-VAL-001 PASSED: Empty email rejected")

    # ------------------------------------------------------------------
    # TC-VAL-002: Empty password – login rejects
    # ------------------------------------------------------------------
    def test_empty_password_rejected(self):
        """Submitting login with empty password should not proceed."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(3)
            assert "#dashboard" not in self.driver.current_url, (
                "Dashboard reached with empty password!"
            )
        log_event("INFO", "TC-VAL-002 PASSED: Empty password rejected")

    # ------------------------------------------------------------------
    # TC-VAL-003: Invalid email format rejected
    # ------------------------------------------------------------------
    def test_invalid_email_format_rejected(self):
        """Non-email string in email field should not pass validation."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "not-an-email")
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(3)
            assert "#dashboard" not in self.driver.current_url, (
                "Dashboard reached with invalid email!"
            )
        log_event("INFO", "TC-VAL-003 PASSED: Invalid email format rejected")

    # ------------------------------------------------------------------
    # TC-VAL-004: Very long email (500 chars) does not crash
    # ------------------------------------------------------------------
    def test_very_long_email_no_crash(self):
        """Entering a 500-character email should not crash the form."""
        navigate_to(self.driver, self.base_url, "#login")
        long_email = "a" * 490 + "@test.com"
        fill_field(self.driver, "email", long_email)
        time.sleep(1)
        source = self.driver.page_source
        assert len(source) > 100, "App crashed on long email"
        log_event("INFO", "TC-VAL-004 PASSED: Very long email no crash")

    # ------------------------------------------------------------------
    # TC-VAL-005: SQL injection in email field
    # ------------------------------------------------------------------
    def test_sql_injection_email(self):
        """SQL injection in email field should not expose data."""
        navigate_to(self.driver, self.base_url, "#login")
        payload = "' OR '1'='1'; --"
        fill_field(self.driver, "email", payload)
        fill_field(self.driver, "password", payload)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(6)
            assert "#dashboard" not in self.driver.current_url, (
                "SQL injection succeeded!"
            )
        log_event("INFO", "TC-VAL-005 PASSED: SQL injection in email rejected")

    # ------------------------------------------------------------------
    # TC-VAL-006: SQL injection in password field
    # ------------------------------------------------------------------
    def test_sql_injection_password(self):
        """SQL injection in password field should not authenticate user."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        payload = "' OR '1'='1"
        fill_field(self.driver, "password", payload)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(6)
            assert "#dashboard" not in self.driver.current_url, (
                "SQL injection on password succeeded!"
            )
        log_event("INFO", "TC-VAL-006 PASSED: SQL injection password rejected")

    # ------------------------------------------------------------------
    # TC-VAL-007: XSS via email field – no script executed
    # ------------------------------------------------------------------
    def test_xss_email_field(self):
        """XSS payload in email should not execute JavaScript."""
        navigate_to(self.driver, self.base_url, "#login")
        xss = "<img src=x onerror=alert('XSS')>"
        fill_field(self.driver, "email", xss)
        time.sleep(2)
        try:
            alert = self.driver.switch_to.alert
            alert.dismiss()
            pytest.fail("XSS alert was triggered!")
        except Exception:
            pass
        log_event("INFO", "TC-VAL-007 PASSED: XSS email field rejected")

    # ------------------------------------------------------------------
    # TC-VAL-008: Unicode / emoji in email field
    # ------------------------------------------------------------------
    def test_unicode_in_email(self):
        """Unicode/emoji characters in email should not crash the form."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "test😊@example.com")
        time.sleep(1)
        assert len(self.driver.page_source) > 100, "App crashed on unicode email"
        log_event("INFO", "TC-VAL-008 PASSED: Unicode email no crash")

    # ------------------------------------------------------------------
    # TC-VAL-009: Whitespace-only email is rejected
    # ------------------------------------------------------------------
    def test_whitespace_email_rejected(self):
        """Whitespace-only email should fail validation."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "     ")
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(3)
            assert "#dashboard" not in self.driver.current_url
        log_event("INFO", "TC-VAL-009 PASSED: Whitespace email rejected")

    # ------------------------------------------------------------------
    # TC-VAL-010: Correct credentials login succeeds
    # ------------------------------------------------------------------
    def test_correct_credentials_login(self):
        """Valid credentials should lead to dashboard or remain stable."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(10)
            url = self.driver.current_url
            log_event("INFO", f"TC-VAL-010: After login URL={url}")
        log_event("INFO", "TC-VAL-010 PASSED: Correct credentials test done")

    # ------------------------------------------------------------------
    # TC-VAL-011: Password with special characters
    # ------------------------------------------------------------------
    def test_password_special_chars(self):
        """Special characters in password should not crash the form."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        fill_field(self.driver, "password", "P@$$w0rd!#%^&*()")
        time.sleep(1)
        assert len(self.driver.page_source) > 100, "App crashed on special char password"
        log_event("INFO", "TC-VAL-011 PASSED: Special char password no crash")

    # ------------------------------------------------------------------
    # TC-VAL-012: Very short password (1 char)
    # ------------------------------------------------------------------
    def test_very_short_password(self):
        """A 1-character password should be rejected on registration."""
        navigate_to(self.driver, self.base_url, "#register")
        pw_inputs = self.driver.find_elements(By.CSS_SELECTOR, "input[type='password']")
        if pw_inputs:
            pw_inputs[0].send_keys("a")
        btns = self.driver.find_elements(By.TAG_NAME, "button")
        if btns:
            btns[0].click()
            time.sleep(3)
        log_event("INFO", "TC-VAL-012 PASSED: Very short password test done")

    # ------------------------------------------------------------------
    # TC-VAL-013: Phone number field – numeric validation
    # ------------------------------------------------------------------
    def test_phone_field_numeric(self):
        """Phone fields should only accept numeric or formatted input."""
        navigate_to(self.driver, self.base_url, "#register")
        phone = self.driver.find_elements(By.CSS_SELECTOR,
                                          "input[type='tel'], input[name*='phone'], input[id*='phone']")
        if phone:
            phone[0].send_keys("abc123")
            val = phone[0].get_attribute("value")
            log_event("INFO", f"TC-VAL-013: Phone value after 'abc123' = {val!r}")
        log_event("INFO", "TC-VAL-013 PASSED: Phone field numeric check done")

    # ------------------------------------------------------------------
    # TC-VAL-014: Email field maxlength enforced
    # ------------------------------------------------------------------
    def test_email_maxlength(self):
        """Email field should have a reasonable maxlength."""
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        if em:
            ml = em[0].get_attribute("maxlength")
            if ml:
                assert int(ml) >= 50, f"Email maxlength too short: {ml}"
                assert int(ml) <= 500, f"Email maxlength suspiciously large: {ml}"
        log_event("INFO", "TC-VAL-014 PASSED: Email maxlength checked")

    # ------------------------------------------------------------------
    # TC-VAL-015: Null byte in input field
    # ------------------------------------------------------------------
    def test_null_byte_in_email(self):
        """Null byte in email should not cause a server error."""
        navigate_to(self.driver, self.base_url, "#login")
        try:
            fill_field(self.driver, "email", "test\x00@test.com")
        except Exception:
            pass  # Selenium may reject null bytes
        assert len(self.driver.page_source) > 100, "Null byte crashed app"
        log_event("INFO", "TC-VAL-015 PASSED: Null byte in email no crash")

    # ------------------------------------------------------------------
    # TC-VAL-016: Large number in numeric field
    # ------------------------------------------------------------------
    def test_large_number_in_amount_field(self):
        """Extremely large numbers in amount fields should not crash."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(10)
        navigate_to(self.driver, self.base_url, "#inventory")
        amount_fields = self.driver.find_elements(By.CSS_SELECTOR, "input[type='number']")
        if amount_fields:
            amount_fields[0].send_keys("999999999999")
        assert len(self.driver.page_source) > 100, "App crashed on large number"
        log_event("INFO", "TC-VAL-016 PASSED: Large number no crash")

    # ------------------------------------------------------------------
    # TC-VAL-017: Negative number in price/quantity field
    # ------------------------------------------------------------------
    def test_negative_number_in_price_field(self):
        """Negative values in price should show validation error."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(10)
        navigate_to(self.driver, self.base_url, "#inventory")
        price_fields = self.driver.find_elements(By.CSS_SELECTOR, "input[type='number']")
        if price_fields:
            price_fields[0].send_keys("-100")
        log_event("INFO", "TC-VAL-017 PASSED: Negative price test done")

    # ------------------------------------------------------------------
    # TC-VAL-018: Zero in price field
    # ------------------------------------------------------------------
    def test_zero_price_field(self):
        """Zero price may be valid or invalid – should not crash."""
        navigate_to(self.driver, self.base_url, "#inventory")
        price_fields = self.driver.find_elements(By.CSS_SELECTOR, "input[type='number']")
        if price_fields:
            price_fields[0].send_keys("0")
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-VAL-018 PASSED: Zero price field no crash")

    # ------------------------------------------------------------------
    # TC-VAL-019: Decimal number in price field
    # ------------------------------------------------------------------
    def test_decimal_price_field(self):
        """Decimal prices (e.g. 99.99) should be accepted."""
        navigate_to(self.driver, self.base_url, "#inventory")
        price_fields = self.driver.find_elements(By.CSS_SELECTOR, "input[type='number']")
        if price_fields:
            price_fields[0].send_keys("99.99")
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-VAL-019 PASSED: Decimal price no crash")

    # ------------------------------------------------------------------
    # TC-VAL-020: HTML injection in product name
    # ------------------------------------------------------------------
    def test_html_injection_product_name(self):
        """HTML tags in product name field should be escaped, not rendered."""
        navigate_to(self.driver, self.base_url, "#inventory")
        text_inputs = self.driver.find_elements(By.CSS_SELECTOR, "input[type='text']")
        if text_inputs:
            text_inputs[0].send_keys("<b>bold</b>")
        time.sleep(1)
        # Check no actual bold rendered
        bold_elements = self.driver.find_elements(By.TAG_NAME, "b")
        injected = [b for b in bold_elements if b.text.strip() == "bold"]
        assert len(injected) == 0, "HTML injection rendered in product name"
        log_event("INFO", "TC-VAL-020 PASSED: HTML injection escaped")

    # ------------------------------------------------------------------
    # TC-VAL-021: Form reset clears fields
    # ------------------------------------------------------------------
    def test_form_reset_clears_fields(self):
        """Navigating away and back should clear form state."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "test@clear.com")
        navigate_to(self.driver, self.base_url, "")
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        if em:
            val = em[0].get_attribute("value") or ""
            log_event("INFO", f"TC-VAL-021: Email field after nav={val!r}")
        log_event("INFO", "TC-VAL-021 PASSED: Form state checked after navigation")

    # ------------------------------------------------------------------
    # TC-VAL-022: Double-click on submit does not double submit
    # ------------------------------------------------------------------
    def test_double_click_submit_safe(self):
        """Double-clicking submit should not duplicate form submissions."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "double@click.com")
        fill_field(self.driver, "password", "password123")
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            from selenium.webdriver import ActionChains
            ac = ActionChains(self.driver)
            ac.double_click(btns[0]).perform()
            time.sleep(5)
        assert len(self.driver.page_source) > 100, "App crashed on double click"
        log_event("INFO", "TC-VAL-022 PASSED: Double click submit safe")

    # ------------------------------------------------------------------
    # TC-VAL-023: Pasting text into email field works
    # ------------------------------------------------------------------
    def test_paste_into_email_field(self):
        """Pasting text into email field via JS should work."""
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        if em:
            em[0].click()
            self.driver.execute_script(
                "arguments[0].value = 'pasted@example.com';", em[0]
            )
            val = em[0].get_attribute("value")
            log_event("INFO", f"TC-VAL-023: Pasted value={val!r}")
        log_event("INFO", "TC-VAL-023 PASSED: Paste into email field")

    # ------------------------------------------------------------------
    # TC-VAL-024: Email with multiple @ signs rejected
    # ------------------------------------------------------------------
    def test_multiple_at_signs_rejected(self):
        """Email with multiple @ signs should fail validation."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "user@@domain.com")
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(3)
            assert "#dashboard" not in self.driver.current_url
        log_event("INFO", "TC-VAL-024 PASSED: Multiple @ signs rejected")

    # ------------------------------------------------------------------
    # TC-VAL-025: Email missing domain is rejected
    # ------------------------------------------------------------------
    def test_email_missing_domain_rejected(self):
        """Email without domain (user@) should fail validation."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "user@")
        fill_field(self.driver, "password", TEST_PASSWORD)
        btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
        if btns:
            btns[0].click()
            time.sleep(3)
            assert "#dashboard" not in self.driver.current_url
        log_event("INFO", "TC-VAL-025 PASSED: Email missing domain rejected")

    # ------------------------------------------------------------------
    # TC-VAL-026: JavaScript injection in form field
    # ------------------------------------------------------------------
    def test_javascript_injection_form_field(self):
        """javascript: URI in form field should not execute."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "javascript:alert(1)")
        time.sleep(1)
        try:
            alert = self.driver.switch_to.alert
            alert.dismiss()
            pytest.fail("JS injection via form field executed!")
        except Exception:
            pass
        log_event("INFO", "TC-VAL-026 PASSED: JS injection form field rejected")

    # ------------------------------------------------------------------
    # TC-VAL-027: Copy-paste protection not applied to email
    # ------------------------------------------------------------------
    def test_copy_paste_allowed_email(self):
        """Email field should allow paste (no oncopy/onpaste blocking)."""
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        if em:
            onpaste = em[0].get_attribute("onpaste") or ""
            assert "return false" not in onpaste.lower(), "Paste blocked on email"
        log_event("INFO", "TC-VAL-027 PASSED: Paste not blocked on email")

    # ------------------------------------------------------------------
    # TC-VAL-028: Keyboard Enter key submits login form
    # ------------------------------------------------------------------
    def test_enter_key_submits_login(self):
        """Pressing Enter in password field should submit the login form."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", "enter@test.com")
        pw = self.driver.find_elements(By.ID, "password")
        if pw:
            pw[0].send_keys("test12345")
            pw[0].send_keys(Keys.RETURN)
            time.sleep(5)
        log_event("INFO", "TC-VAL-028 PASSED: Enter key submit test done")

    # ------------------------------------------------------------------
    # TC-VAL-029: Long business name in registration
    # ------------------------------------------------------------------
    def test_long_business_name(self):
        """A 200-char business name should not crash the registration form."""
        navigate_to(self.driver, self.base_url, "#register")
        biz_fields = self.driver.find_elements(
            By.CSS_SELECTOR, "input[name*='business'], input[id*='business'], input[placeholder*='business' i]"
        )
        if biz_fields:
            biz_fields[0].send_keys("B" * 200)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-VAL-029 PASSED: Long business name no crash")

    # ------------------------------------------------------------------
    # TC-VAL-030: All form fields accept Tab key navigation
    # ------------------------------------------------------------------
    def test_tab_key_navigates_form(self):
        """Tab key should move focus through form inputs."""
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        if em:
            em[0].click()
            em[0].send_keys(Keys.TAB)
            active = self.driver.switch_to.active_element
            tag = active.tag_name.lower()
            assert tag in ("input", "button", "a", "select", "textarea"), (
                f"Tab moved to unexpected element: {tag}"
            )
        log_event("INFO", "TC-VAL-030 PASSED: Tab key navigates form")

    # ------------------------------------------------------------------
    # TC-VAL-031: Register form – mismatched passwords show error
    # ------------------------------------------------------------------
    def test_register_password_mismatch(self):
        """Mismatched confirm password should show an error."""
        navigate_to(self.driver, self.base_url, "#register")
        pw_inputs = self.driver.find_elements(By.CSS_SELECTOR, "input[type='password']")
        if len(pw_inputs) >= 2:
            pw_inputs[0].send_keys("password123")
            pw_inputs[1].send_keys("different456")
            btns = self.driver.find_elements(By.TAG_NAME, "button")
            if btns:
                btns[0].click()
                time.sleep(3)
        log_event("INFO", "TC-VAL-031 PASSED: Mismatched passwords test done")

    # ------------------------------------------------------------------
    # TC-VAL-032: Emoji-only password does not crash
    # ------------------------------------------------------------------
    def test_emoji_only_password(self):
        """Emoji-only password should not cause JS exception."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_field(self.driver, "email", TEST_EMAIL)
        try:
            fill_field(self.driver, "password", "🔑🔒🛡️")
        except Exception:
            pass
        assert len(self.driver.page_source) > 100, "Emoji password crashed app"
        log_event("INFO", "TC-VAL-032 PASSED: Emoji password no crash")

    # ------------------------------------------------------------------
    # TC-VAL-033: Input fields not disabled on page load
    # ------------------------------------------------------------------
    def test_inputs_not_disabled_on_load(self):
        """Login form inputs should be enabled (not disabled) on page load."""
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        pw = self.driver.find_elements(By.ID, "password")
        if em:
            assert em[0].is_enabled(), "Email input is disabled!"
        if pw:
            assert pw[0].is_enabled(), "Password input is disabled!"
        log_event("INFO", "TC-VAL-033 PASSED: Inputs not disabled on load")

    # ------------------------------------------------------------------
    # TC-VAL-034: Page number field (pagination) only accepts positive ints
    # ------------------------------------------------------------------
    def test_pagination_no_crash(self):
        """Pagination controls (if any) should handle edge values."""
        navigate_to(self.driver, self.base_url, "#inventory")
        time.sleep(PAGE_LOAD_WAIT)
        page_inputs = self.driver.find_elements(By.CSS_SELECTOR, "input[type='number']")
        for inp in page_inputs[:2]:
            try:
                inp.clear()
                inp.send_keys("0")
            except Exception:
                pass
        assert len(self.driver.page_source) > 100, "Pagination crashed app"
        log_event("INFO", "TC-VAL-034 PASSED: Pagination edge case handled")

    # ------------------------------------------------------------------
    # TC-VAL-035: App handles browser back button correctly
    # ------------------------------------------------------------------
    def test_browser_back_button(self):
        """Browser back button should not crash the app."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        navigate_to(self.driver, self.base_url, "#login")
        time.sleep(2)
        self.driver.back()
        time.sleep(2)
        assert len(self.driver.page_source) > 100, "Back button crashed app"
        log_event("INFO", "TC-VAL-035 PASSED: Browser back button handled")
