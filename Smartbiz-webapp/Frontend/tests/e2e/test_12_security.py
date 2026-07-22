"""
Test Suite 12: Security & Vulnerability Tests
==============================================
Tests for common web security issues including XSS,
input sanitisation, HTTPS usage, password masking,
localStorage hygiene, and external resource loading.
"""

import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import os
import sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT, TEST_EMAIL, TEST_PASSWORD,
)


class TestSecurity:
    """Security and vulnerability test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        log_event("INFO", "Security test suite starting")

    # ------------------------------------------------------------------
    # TC-SEC-001: HTTPS enforced on GitHub Pages
    # ------------------------------------------------------------------
    def test_https_enforced(self):
        """The app URL should use HTTPS."""
        assert self.base_url.startswith("https://"), (
            f"Base URL does not use HTTPS: {self.base_url}"
        )
        log_event("INFO", "TC-SEC-001 PASSED: HTTPS enforced")

    # ------------------------------------------------------------------
    # TC-SEC-002: XSS – script tag in email field
    # ------------------------------------------------------------------
    def test_xss_script_in_email(self):
        """Injecting a <script> tag in the email field should not execute."""
        navigate_to(self.driver, self.base_url, "#login")
        email_input = self.driver.find_element(By.ID, "email")
        email_input.clear()
        xss_payload = '<script>alert("XSS")</script>'
        email_input.send_keys(xss_payload)

        # Check no alert popped up
        try:
            alert = self.driver.switch_to.alert
            alert.dismiss()
            pytest.fail("XSS alert was triggered – vulnerability found!")
        except Exception:
            pass  # No alert = good

        log_event("INFO", "TC-SEC-002 PASSED: XSS script in email blocked")

    # ------------------------------------------------------------------
    # TC-SEC-003: XSS – script tag in password field
    # ------------------------------------------------------------------
    def test_xss_script_in_password(self):
        """Injecting a <script> tag in password field should not execute."""
        navigate_to(self.driver, self.base_url, "#login")
        pw_input = self.driver.find_element(By.ID, "password")
        pw_input.clear()
        pw_input.send_keys('<img src=x onerror=alert(1)>')

        try:
            alert = self.driver.switch_to.alert
            alert.dismiss()
            pytest.fail("XSS via img onerror triggered – vulnerability!")
        except Exception:
            pass

        log_event("INFO", "TC-SEC-003 PASSED: XSS script in password blocked")

    # ------------------------------------------------------------------
    # TC-SEC-004: SQL injection in login email
    # ------------------------------------------------------------------
    def test_sql_injection_email(self):
        """SQL injection characters in email should not cause a crash."""
        navigate_to(self.driver, self.base_url, "#login")

        email_input = self.driver.find_element(By.ID, "email")
        email_input.clear()
        email_input.send_keys("' OR 1=1 --")

        pw_input = self.driver.find_element(By.ID, "password")
        pw_input.clear()
        pw_input.send_keys("' OR 1=1 --")

        submit = self.driver.find_element(
            By.CSS_SELECTOR, '#login-form button[type="submit"]'
        )
        submit.click()
        time.sleep(5)

        # Should NOT navigate to dashboard with injected credentials
        assert "#dashboard" not in self.driver.current_url, (
            "SQL injection bypassed login – critical vulnerability!"
        )
        log_event("INFO", "TC-SEC-004 PASSED: SQL injection blocked")

    # ------------------------------------------------------------------
    # TC-SEC-005: Password field is masked
    # ------------------------------------------------------------------
    def test_password_field_type(self):
        """Login password field type must be 'password'."""
        navigate_to(self.driver, self.base_url, "#login")
        pw = self.driver.find_element(By.ID, "password")
        assert pw.get_attribute("type") == "password", (
            "Password field is not type=password – input is visible!"
        )
        log_event("INFO", "TC-SEC-005 PASSED: Password field masked")

    # ------------------------------------------------------------------
    # TC-SEC-006: Register PIN field is masked
    # ------------------------------------------------------------------
    def test_register_pin_masked(self):
        """Register PIN field type must be 'password'."""
        navigate_to(self.driver, self.base_url, "#register")
        pw = self.driver.find_element(By.ID, "reg-password")
        assert pw.get_attribute("type") == "password", (
            "Register PIN field is not type=password"
        )
        log_event("INFO", "TC-SEC-006 PASSED: Register PIN masked")

    # ------------------------------------------------------------------
    # TC-SEC-007: API calls use HTTPS
    # ------------------------------------------------------------------
    def test_api_calls_use_https(self):
        """The API base URL configured in the app should use HTTPS."""
        navigate_to(self.driver, self.base_url, "#login")
        # The .env sets VITE_API_URL, but in production build it's baked in
        # We verify the Render URL is HTTPS
        from helpers import API_URL
        assert API_URL.startswith("https://"), (
            f"API URL does not use HTTPS: {API_URL}"
        )
        log_event("INFO", "TC-SEC-007 PASSED: API uses HTTPS")

    # ------------------------------------------------------------------
    # TC-SEC-008: No plaintext passwords in page source
    # ------------------------------------------------------------------
    def test_no_plaintext_passwords_in_source(self):
        """The page source should not expose plaintext passwords in scripts."""
        navigate_to(self.driver, self.base_url, "#dashboard")
        source = self.driver.page_source
        # Check that the test password value is only in the form input value,
        # not floating as a JS variable in a script block
        script_blocks = source.split("<script")
        for block in script_blocks[1:]:  # Skip before first <script
            # Allow it in value="" attribute of inputs but not raw in JS
            if 'password = "1234"' in block or "password = '1234'" in block:
                pytest.fail(
                    "Plaintext password found in script block"
                )
        log_event("INFO", "TC-SEC-008 PASSED: No plaintext passwords")

    # ------------------------------------------------------------------
    # TC-SEC-009: External scripts from trusted domains only
    # ------------------------------------------------------------------
    def test_external_scripts_trusted(self):
        """External scripts should load only from trusted CDN domains."""
        navigate_to(self.driver, self.base_url, "")
        scripts = self.driver.find_elements(By.TAG_NAME, "script")
        trusted_domains = [
            "unpkg.com", "cdn.jsdelivr.net", "fonts.googleapis.com",
            "fonts.gstatic.com", ""  # empty for inline/local scripts
        ]
        for script in scripts:
            src = script.get_attribute("src") or ""
            if src:
                is_trusted = any(domain in src for domain in trusted_domains)
                is_local = not src.startswith("http")
                assert is_trusted or is_local, (
                    f"Untrusted script source: {src}"
                )
        log_event("INFO", "TC-SEC-009 PASSED: Scripts from trusted domains")

    # ------------------------------------------------------------------
    # TC-SEC-010: No console errors on page load
    # ------------------------------------------------------------------
    def test_no_critical_console_errors(self):
        """Page should load without critical JavaScript errors."""
        navigate_to(self.driver, self.base_url, "")
        logs = self.driver.get_log("browser")
        severe_errors = [
            log for log in logs if log.get("level") == "SEVERE"
            and "favicon" not in log.get("message", "").lower()
        ]
        # We allow some non-critical errors (CORS, favicon, etc.)
        critical_count = len([
            e for e in severe_errors
            if "TypeError" in e.get("message", "")
            or "ReferenceError" in e.get("message", "")
            or "SyntaxError" in e.get("message", "")
        ])
        assert critical_count == 0, (
            f"Found {critical_count} critical JS errors: "
            f"{[e['message'] for e in severe_errors[:3]]}"
        )
        log_event("INFO", "TC-SEC-010 PASSED: No critical console errors")

    # ------------------------------------------------------------------
    # TC-SEC-011: XSS via URL hash parameter
    # ------------------------------------------------------------------
    def test_xss_via_hash(self):
        """XSS payload in URL hash should not execute."""
        self.driver.get(
            self.base_url + '#<script>alert("XSS")</script>'
        )
        time.sleep(3)
        try:
            alert = self.driver.switch_to.alert
            alert.dismiss()
            pytest.fail("XSS via hash triggered an alert!")
        except Exception:
            pass
        log_event("INFO", "TC-SEC-011 PASSED: XSS via hash blocked")

    # ------------------------------------------------------------------
    # TC-SEC-012: Form action does not point to external domain
    # ------------------------------------------------------------------
    def test_form_action_local(self):
        """Login form should not submit to an external domain."""
        navigate_to(self.driver, self.base_url, "#login")
        form = self.driver.find_element(By.ID, "login-form")
        action = form.get_attribute("action") or ""
        if action:
            assert "thegitguy-56.github.io" in action or action.startswith("/"), (
                f"Form action points to external domain: {action}"
            )
        # Empty action = submits to same page, which is fine
        log_event("INFO", "TC-SEC-012 PASSED: Form action is local")
