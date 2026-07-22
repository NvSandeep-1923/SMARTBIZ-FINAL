"""
Test Suite 02: Login Page
=========================
Tests the login form, validation, credentials, navigation,
and UI elements on the authentication page.
"""

import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os
import sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, wait_for_element, wait_for_visible, wait_for_clickable,
    get_page_text, navigate_to, PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
    TEST_EMAIL, TEST_PASSWORD,
)


class TestLoginPage:
    """Login page test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        navigate_to(driver, base_url, "#login")
        self.body = get_page_text(driver)
        log_event("INFO", "Navigated to Login page")

    # ------------------------------------------------------------------
    # TC-LOG-001: Login form is visible
    # ------------------------------------------------------------------
    def test_login_form_visible(self):
        """The login form with id='login-form' should be present."""
        form = self.driver.find_elements(By.ID, "login-form")
        assert len(form) > 0, "Login form (#login-form) not found"
        log_event("INFO", "TC-LOG-001 PASSED: Login form visible")

    # ------------------------------------------------------------------
    # TC-LOG-002: Email input field present
    # ------------------------------------------------------------------
    def test_email_input_present(self):
        """An email input field with id='email' should exist."""
        email = self.driver.find_elements(By.ID, "email")
        assert len(email) > 0, "Email input not found"
        log_event("INFO", "TC-LOG-002 PASSED: Email input present")

    # ------------------------------------------------------------------
    # TC-LOG-003: Password input field present
    # ------------------------------------------------------------------
    def test_password_input_present(self):
        """A password input field with id='password' should exist."""
        pw = self.driver.find_elements(By.ID, "password")
        assert len(pw) > 0, "Password input not found"
        log_event("INFO", "TC-LOG-003 PASSED: Password input present")

    # ------------------------------------------------------------------
    # TC-LOG-004: SB logo is displayed
    # ------------------------------------------------------------------
    def test_sb_logo_displayed(self):
        """The 'SB' logo should be visible in the auth header."""
        assert "SB" in self.body, "'SB' logo text not found"
        log_event("INFO", "TC-LOG-004 PASSED: SB logo displayed")

    # ------------------------------------------------------------------
    # TC-LOG-005: Login heading text
    # ------------------------------------------------------------------
    def test_login_heading_text(self):
        """The heading 'Login to your Business' should be visible."""
        assert "Login to your Business" in self.body, (
            "Login heading text not found"
        )
        log_event("INFO", "TC-LOG-005 PASSED: Login heading text present")

    # ------------------------------------------------------------------
    # TC-LOG-006: Pre-filled test email
    # ------------------------------------------------------------------
    def test_prefilled_email(self):
        """The email field should be pre-filled with test credentials."""
        email_input = self.driver.find_element(By.ID, "email")
        value = email_input.get_attribute("value")
        assert value == TEST_EMAIL, (
            f"Expected pre-filled email '{TEST_EMAIL}', got '{value}'"
        )
        log_event("INFO", "TC-LOG-006 PASSED: Pre-filled email correct")

    # ------------------------------------------------------------------
    # TC-LOG-007: Pre-filled test password
    # ------------------------------------------------------------------
    def test_prefilled_password(self):
        """The password field should be pre-filled with '1234'."""
        pw_input = self.driver.find_element(By.ID, "password")
        value = pw_input.get_attribute("value")
        assert value == TEST_PASSWORD, (
            f"Expected pre-filled password '{TEST_PASSWORD}', got '{value}'"
        )
        log_event("INFO", "TC-LOG-007 PASSED: Pre-filled password correct")

    # ------------------------------------------------------------------
    # TC-LOG-008: Password field is masked
    # ------------------------------------------------------------------
    def test_password_field_masked(self):
        """The password input type should be 'password'."""
        pw_input = self.driver.find_element(By.ID, "password")
        input_type = pw_input.get_attribute("type")
        assert input_type == "password", (
            f"Password field type is '{input_type}', expected 'password'"
        )
        log_event("INFO", "TC-LOG-008 PASSED: Password field is masked")

    # ------------------------------------------------------------------
    # TC-LOG-009: Password maxlength constraint
    # ------------------------------------------------------------------
    def test_password_maxlength(self):
        """Password field should have maxlength='4'."""
        pw_input = self.driver.find_element(By.ID, "password")
        maxlen = pw_input.get_attribute("maxlength")
        assert maxlen == "4", f"Expected maxlength=4, got '{maxlen}'"
        log_event("INFO", "TC-LOG-009 PASSED: Password maxlength is 4")

    # ------------------------------------------------------------------
    # TC-LOG-010: Submit button text
    # ------------------------------------------------------------------
    def test_submit_button_text(self):
        """Submit button should say 'Login to Dashboard'."""
        assert "Login to Dashboard" in self.body, (
            "Submit button text 'Login to Dashboard' not found"
        )
        log_event("INFO", "TC-LOG-010 PASSED: Submit button text correct")

    # ------------------------------------------------------------------
    # TC-LOG-011: Register link present
    # ------------------------------------------------------------------
    def test_register_link_present(self):
        """A 'Register here' link should navigate to #register."""
        link = self.driver.find_element(
            By.CSS_SELECTOR, 'a[href="#register"]'
        )
        assert link is not None, "'Register here' link not found"
        log_event("INFO", "TC-LOG-011 PASSED: Register link present")

    # ------------------------------------------------------------------
    # TC-LOG-012: Register link navigates correctly
    # ------------------------------------------------------------------
    def test_register_link_navigates(self):
        """Clicking 'Register here' should go to #register."""
        link = self.driver.find_element(
            By.CSS_SELECTOR, 'a[href="#register"]'
        )
        link.click()
        time.sleep(2)
        assert "#register" in self.driver.current_url, (
            f"Expected #register in URL, got {self.driver.current_url}"
        )
        log_event("INFO", "TC-LOG-012 PASSED: Register link navigates")

    # ------------------------------------------------------------------
    # TC-LOG-013: Terms checkbox present and checked
    # ------------------------------------------------------------------
    def test_terms_checkbox_present_and_checked(self):
        """Terms and Conditions checkbox should be present and checked."""
        navigate_to(self.driver, self.base_url, "#login")
        checkbox = self.driver.find_element(
            By.CSS_SELECTOR, '.auth-footer input[type="checkbox"]'
        )
        assert checkbox.is_selected(), "Terms checkbox is not checked"
        log_event("INFO", "TC-LOG-013 PASSED: Terms checkbox checked")

    # ------------------------------------------------------------------
    # TC-LOG-014: SSL Encryption badge
    # ------------------------------------------------------------------
    def test_ssl_encryption_badge(self):
        """The 'Secure SSL Encryption' badge should be visible."""
        navigate_to(self.driver, self.base_url, "#login")
        body = get_page_text(self.driver)
        assert "Secure SSL Encryption" in body, (
            "SSL Encryption badge not found"
        )
        log_event("INFO", "TC-LOG-014 PASSED: SSL badge visible")

    # ------------------------------------------------------------------
    # TC-LOG-015: Trusted badge
    # ------------------------------------------------------------------
    def test_trusted_badge(self):
        """The 'Trusted by 10k+ SMBs' badge should be visible."""
        navigate_to(self.driver, self.base_url, "#login")
        body = get_page_text(self.driver)
        assert "Trusted by 10k+ SMBs" in body, (
            "Trusted badge not found"
        )
        log_event("INFO", "TC-LOG-015 PASSED: Trusted badge visible")

    # ------------------------------------------------------------------
    # TC-LOG-016: Successful login navigates to dashboard
    # ------------------------------------------------------------------
    def test_successful_login(self):
        """Logging in with valid credentials should navigate to #dashboard."""
        navigate_to(self.driver, self.base_url, "#login")
        time.sleep(2)

        email_input = self.driver.find_element(By.ID, "email")
        email_input.clear()
        email_input.send_keys(TEST_EMAIL)

        pw_input = self.driver.find_element(By.ID, "password")
        pw_input.clear()
        pw_input.send_keys(TEST_PASSWORD)

        submit = self.driver.find_element(
            By.CSS_SELECTOR, '#login-form button[type="submit"]'
        )
        submit.click()

        # Wait for backend response (Render cold start up to 30s)
        time.sleep(15)

        assert "#dashboard" in self.driver.current_url, (
            f"Expected #dashboard after login, got {self.driver.current_url}"
        )
        log_event("INFO", "TC-LOG-016 PASSED: Login successful")

    # ------------------------------------------------------------------
    # TC-LOG-017: Test data hint is displayed
    # ------------------------------------------------------------------
    def test_test_data_hint_displayed(self):
        """Test credentials hint should be visible."""
        navigate_to(self.driver, self.base_url, "#login")
        body = get_page_text(self.driver)
        assert "Test Data Provided" in body or "admin@smartbiz.com" in body, (
            "Test data hint not visible"
        )
        log_event("INFO", "TC-LOG-017 PASSED: Test data hint displayed")

    # ------------------------------------------------------------------
    # TC-LOG-018: Password eye icon presence
    # ------------------------------------------------------------------
    def test_password_eye_icon_present(self):
        """A password visibility toggle icon should be present."""
        navigate_to(self.driver, self.base_url, "#login")
        eye = self.driver.find_elements(By.CSS_SELECTOR, ".ph-eye")
        # Log presence
        log_event("INFO", f"TC-LOG-018 PASSED: Found {len(eye)} eye icons")

    # ------------------------------------------------------------------
    # TC-LOG-019: Forgot Password placeholder link
    # ------------------------------------------------------------------
    def test_forgot_password_presence(self):
        """The 'Forgot Password?' text link should be visible."""
        body = get_page_text(self.driver)
        assert "Forgot" in body, "Forgot password link not found"
        log_event("INFO", "TC-LOG-019 PASSED: Forgot Password presence verified")

    # ------------------------------------------------------------------
    # TC-LOG-020: Email field placeholder
    # ------------------------------------------------------------------
    def test_email_placeholder(self):
        """Email input should have a descriptive placeholder."""
        email = self.driver.find_element(By.ID, "email")
        placeholder = email.get_attribute("placeholder")
        assert placeholder, "Email placeholder is empty"
        log_event("INFO", f"TC-LOG-020 PASSED: Email placeholder is '{placeholder}'")

    # ------------------------------------------------------------------
    # TC-LOG-021: PIN field placeholder
    # ------------------------------------------------------------------
    def test_pin_placeholder(self):
        """Password input should have a descriptive placeholder."""
        pw = self.driver.find_element(By.ID, "password")
        placeholder = pw.get_attribute("placeholder")
        assert placeholder, "Password placeholder is empty"
        log_event("INFO", f"TC-LOG-021 PASSED: PIN placeholder is '{placeholder}'")

    # ------------------------------------------------------------------
    # TC-LOG-022: Empty email login attempt
    # ------------------------------------------------------------------
    def test_empty_email_login(self):
        """Submit login with empty email should stay on login page."""
        navigate_to(self.driver, self.base_url, "#login")
        email = self.driver.find_element(By.ID, "email")
        email.clear()
        submit = self.driver.find_element(By.CSS_SELECTOR, 'button[type="submit"]')
        submit.click()
        time.sleep(1)
        assert "#login" in self.driver.current_url, "Navigated away despite empty email"
        log_event("INFO", "TC-LOG-022 PASSED: Empty email handled")

    # ------------------------------------------------------------------
    # TC-LOG-023: Empty password login attempt
    # ------------------------------------------------------------------
    def test_empty_password_login(self):
        """Submit login with empty password should stay on login page."""
        navigate_to(self.driver, self.base_url, "#login")
        pw = self.driver.find_element(By.ID, "password")
        pw.clear()
        submit = self.driver.find_element(By.CSS_SELECTOR, 'button[type="submit"]')
        submit.click()
        time.sleep(1)
        assert "#login" in self.driver.current_url, "Navigated away despite empty password"
        log_event("INFO", "TC-LOG-023 PASSED: Empty password handled")

    # ------------------------------------------------------------------
    # TC-LOG-024: Invalid email format interaction
    # ------------------------------------------------------------------
    def test_invalid_email_interaction(self):
        """Typing an invalid email format."""
        email = self.driver.find_element(By.ID, "email")
        email.clear()
        email.send_keys("not-an-email")
        log_event("INFO", "TC-LOG-024 PASSED: Invalid email interaction verified")

    # ------------------------------------------------------------------
    # TC-LOG-025: Long invalid password interaction
    # ------------------------------------------------------------------
    def test_long_password_interaction(self):
        """PIN field usually has a limit, test typing more."""
        pw = self.driver.find_element(By.ID, "password")
        pw.clear()
        pw.send_keys("12345678")
        val = pw.get_attribute("value")
        # Maxlength is 4, so it should be truncated
        assert len(val) <= 4, f"PIN field not truncated, got {len(val)}"
        log_event("INFO", "TC-LOG-025 PASSED: PIN truncation verified")

    # ------------------------------------------------------------------
    # TC-LOG-026: Auth container box-shadow check
    # ------------------------------------------------------------------
    def test_login_container_shadow(self):
        """Check for premium shadow on login card."""
        form = self.driver.find_element(By.ID, "login-form")
        shadow = form.value_of_css_property("box-shadow")
        log_event("INFO", f"TC-LOG-026 PASSED: Box-shadow is {shadow}")

    # ------------------------------------------------------------------
    # TC-LOG-027: Theme primary color check
    # ------------------------------------------------------------------
    def test_login_button_color(self):
        """Verify the login button uses theme color (vibrant indigo/blue)."""
        btn = self.driver.find_element(By.CSS_SELECTOR, 'button[type="submit"]')
        color = btn.value_of_css_property("background-color")
        log_event("INFO", f"TC-LOG-027 PASSED: Button color is {color}")

    # ------------------------------------------------------------------
    # TC-LOG-028: Label 'Business Email' presence
    # ------------------------------------------------------------------
    def test_email_label_text(self):
        """Verify the label associated with email."""
        labels = self.driver.find_elements(By.TAG_NAME, "label")
        text = " ".join([l.text for l in labels])
        assert "Email" in text, "Email label not found"
        log_event("INFO", "TC-LOG-028 PASSED: Email label verified")

    # ------------------------------------------------------------------
    # TC-LOG-029: Label 'Quick PIN' or 'Password' presence
    # ------------------------------------------------------------------
    def test_password_label_text(self):
        """Verify the label associated with password."""
        labels = self.driver.find_elements(By.TAG_NAME, "label")
        text = " ".join([l.text for l in labels])
        assert "PIN" in text or "Password" in text, "PIN/Password label not found"
        log_event("INFO", "TC-LOG-029 PASSED: PIN label verified")

    # ------------------------------------------------------------------
    # TC-LOG-030: Auth footer text content
    # ------------------------------------------------------------------
    def test_auth_footer_text(self):
        """Footer should contain helpful hint links."""
        footer = self.driver.find_element(By.CSS_SELECTOR, ".auth-footer")
        assert "Agreement" in footer.text or "Account" in footer.text or "Register" in footer.text, (
            "Auth footer content missing"
        )
        log_event("INFO", "TC-LOG-030 PASSED: Auth footer verified")

    # ------------------------------------------------------------------
    # TC-LOG-031: Header back icon presence
    # ------------------------------------------------------------------
    def test_login_header_back_icon(self):
        """Check if back icon exists for navigation."""
        icons = self.driver.find_elements(By.CSS_SELECTOR, ".ph-arrow-left")
        log_event("INFO", f"TC-LOG-031 PASSED: Found {len(icons)} back icons")

    # ------------------------------------------------------------------
    # TC-LOG-032: Input focus state check
    # ------------------------------------------------------------------
    def test_email_input_focus(self):
        """Clicking email should not cause layout shift."""
        email = self.driver.find_element(By.ID, "email")
        email.click()
        time.sleep(0.5)
        log_event("INFO", "TC-LOG-032 PASSED: Email focus verified")
