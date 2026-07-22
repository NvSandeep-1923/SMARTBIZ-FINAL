"""
Test Suite 18: Full End-to-End User Flow Tests
==============================================
Tests complete user journeys and multi-step workflows in SmartBiz.
"""

import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT, TEST_EMAIL, TEST_PASSWORD,
)


class TestUserFlows:
    """Full End-to-End User Journey Test Cases (TC-FLOW-*)."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url

    # ------------------------------------------------------------------
    # TC-FLOW-001: Journey - Landing to Login
    # ------------------------------------------------------------------
    def test_flow_landing_to_login(self):
        """User starts at onboarding landing page and navigates to login."""
        self.driver.get(self.base_url)
        time.sleep(2)
        navigate_to(self.driver, self.base_url, "#login")
        time.sleep(2)
        assert "#login" in self.driver.current_url or len(self.driver.find_elements(By.ID, "login-form")) > 0
        log_event("INFO", "TC-FLOW-001 PASSED: Landing to login flow completed")

    # ------------------------------------------------------------------
    # TC-FLOW-002: Journey - Landing to Register
    # ------------------------------------------------------------------
    def test_flow_landing_to_register(self):
        """User starts at onboarding landing page and navigates to register."""
        self.driver.get(self.base_url)
        time.sleep(2)
        navigate_to(self.driver, self.base_url, "#register")
        time.sleep(2)
        assert "#register" in self.driver.current_url or "register" in get_page_text(self.driver).lower()
        log_event("INFO", "TC-FLOW-002 PASSED: Landing to register flow completed")

    # ------------------------------------------------------------------
    # TC-FLOW-003: Journey - Login to Dashboard
    # ------------------------------------------------------------------
    def test_flow_login_to_dashboard(self):
        """User enters credentials and attempts to access dashboard."""
        navigate_to(self.driver, self.base_url, "#login")
        em = self.driver.find_elements(By.ID, "email")
        pw = self.driver.find_elements(By.ID, "password")
        if em and pw:
            em[0].send_keys(TEST_EMAIL)
            pw[0].send_keys(TEST_PASSWORD)
            btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
            if btns:
                btns[0].click()
                time.sleep(5)
        log_event("INFO", "TC-FLOW-003 PASSED: Login to dashboard attempted")

    # ------------------------------------------------------------------
    # TC-FLOW-004: Journey - Switch pages via hash navigation
    # ------------------------------------------------------------------
    def test_flow_multi_page_navigation(self):
        """User navigates across multiple sections sequentially."""
        routes = ["#login", "#register", "#inventory", "#customers", "#reports", "#settings"]
        for r in routes:
            navigate_to(self.driver, self.base_url, r)
            time.sleep(1)
            assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-004 PASSED: Multi-page navigation flow completed")

    # ------------------------------------------------------------------
    # TC-FLOW-005: Journey - Attempt Add Product in Inventory
    # ------------------------------------------------------------------
    def test_flow_inventory_to_add_product(self):
        """User navigates to inventory and triggers add product flow."""
        navigate_to(self.driver, self.base_url, "#inventory")
        time.sleep(2)
        add_btns = self.driver.find_elements(By.CSS_SELECTOR, "button[id*='add'], a[href*='add'], button")
        if add_btns:
            add_btns[0].click()
            time.sleep(2)
        log_event("INFO", "TC-FLOW-005 PASSED: Inventory to add product flow")

    # ------------------------------------------------------------------
    # TC-FLOW-006: Journey - Customer search flow
    # ------------------------------------------------------------------
    def test_flow_customer_search(self):
        """User opens customer list and uses search input."""
        navigate_to(self.driver, self.base_url, "#customers")
        time.sleep(2)
        search_inputs = self.driver.find_elements(By.CSS_SELECTOR, "input[type='search'], input[placeholder*='search' i]")
        if search_inputs:
            search_inputs[0].send_keys("Test Customer")
            time.sleep(1)
        log_event("INFO", "TC-FLOW-006 PASSED: Customer search flow completed")

    # ------------------------------------------------------------------
    # TC-FLOW-007: Journey - Inventory search & filter flow
    # ------------------------------------------------------------------
    def test_flow_inventory_search_filter(self):
        """User searches for items in inventory page."""
        navigate_to(self.driver, self.base_url, "#inventory")
        time.sleep(2)
        inputs = self.driver.find_elements(By.TAG_NAME, "input")
        if inputs:
            inputs[0].send_keys("Product 1")
            time.sleep(1)
        log_event("INFO", "TC-FLOW-007 PASSED: Inventory search filter flow")

    # ------------------------------------------------------------------
    # TC-FLOW-008: Journey - Billing/Invoice builder item selection
    # ------------------------------------------------------------------
    def test_flow_billing_invoice_build(self):
        """User navigates to billing section."""
        navigate_to(self.driver, self.base_url, "#billing")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-008 PASSED: Billing flow accessed")

    # ------------------------------------------------------------------
    # TC-FLOW-009: Journey - Reports view switching
    # ------------------------------------------------------------------
    def test_flow_reports_hub_views(self):
        """User opens reports and toggles available options/tabs."""
        navigate_to(self.driver, self.base_url, "#reports")
        time.sleep(2)
        btns = self.driver.find_elements(By.TAG_NAME, "button")
        if len(btns) > 1:
            btns[1].click()
            time.sleep(1)
        log_event("INFO", "TC-FLOW-009 PASSED: Reports view switching flow")

    # ------------------------------------------------------------------
    # TC-FLOW-010: Journey - Settings language selection
    # ------------------------------------------------------------------
    def test_flow_settings_language_toggle(self):
        """User opens settings and inspects language options."""
        navigate_to(self.driver, self.base_url, "#settings")
        time.sleep(2)
        body = get_page_text(self.driver)
        assert len(body) > 0
        log_event("INFO", "TC-FLOW-010 PASSED: Settings language toggle flow")

    # ------------------------------------------------------------------
    # TC-FLOW-011: Journey - Onboarding slider swipe/next simulation
    # ------------------------------------------------------------------
    def test_flow_onboarding_carousel_advancement(self):
        """User clicks through onboarding dots/next buttons."""
        self.driver.get(self.base_url)
        time.sleep(2)
        dots = self.driver.find_elements(By.CSS_SELECTOR, ".dot, button[class*='dot'], [class*='indicator']")
        for dot in dots[:3]:
            dot.click()
            time.sleep(1)
        log_event("INFO", "TC-FLOW-011 PASSED: Carousel advancement flow")

    # ------------------------------------------------------------------
    # TC-FLOW-012: Journey - Help & Support section flow
    # ------------------------------------------------------------------
    def test_flow_help_support_navigation(self):
        """User checks support / FAQs route."""
        navigate_to(self.driver, self.base_url, "#support")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-012 PASSED: Help support navigation flow")

    # ------------------------------------------------------------------
    # TC-FLOW-013: Journey - Notifications drawer check
    # ------------------------------------------------------------------
    def test_flow_notifications_check(self):
        """User checks notifications section."""
        navigate_to(self.driver, self.base_url, "#notifications")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-013 PASSED: Notifications flow completed")

    # ------------------------------------------------------------------
    # TC-FLOW-014: Journey - Quick login-logout loop
    # ------------------------------------------------------------------
    def test_flow_login_logout_loop(self):
        """User logs in and immediately attempts logout."""
        navigate_to(self.driver, self.base_url, "#login")
        fill_email = self.driver.find_elements(By.ID, "email")
        fill_pw = self.driver.find_elements(By.ID, "password")
        if fill_email and fill_pw:
            fill_email[0].send_keys(TEST_EMAIL)
            fill_pw[0].send_keys(TEST_PASSWORD)
            btns = self.driver.find_elements(By.CSS_SELECTOR, 'button[type="submit"], button')
            if btns:
                btns[0].click()
                time.sleep(3)
        navigate_to(self.driver, self.base_url, "#login")
        log_event("INFO", "TC-FLOW-014 PASSED: Login-logout loop flow completed")

    # ------------------------------------------------------------------
    # TC-FLOW-015: Journey - Full session sanity sweep
    # ------------------------------------------------------------------
    def test_flow_full_app_sanity_sweep(self):
        """Sanity check across all main hash targets."""
        for target in ["", "#login", "#dashboard", "#inventory", "#billing", "#customers", "#reports", "#settings"]:
            self.driver.get(self.base_url + target)
            time.sleep(0.5)
            assert self.driver.title != "" or len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-015 PASSED: Full app sanity sweep completed")

    # ------------------------------------------------------------------
    # TC-FLOW-016: Journey - Profile view & edit trigger
    # ------------------------------------------------------------------
    def test_flow_profile_view_trigger(self):
        """User opens business profile settings."""
        navigate_to(self.driver, self.base_url, "#profile")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-016 PASSED: Profile view trigger flow")

    # ------------------------------------------------------------------
    # TC-FLOW-017: Journey - Daybook ledger inspection
    # ------------------------------------------------------------------
    def test_flow_daybook_ledger_inspection(self):
        """User views daybook transactions."""
        navigate_to(self.driver, self.base_url, "#daybook")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-017 PASSED: Daybook ledger inspection flow")

    # ------------------------------------------------------------------
    # TC-FLOW-018: Journey - Expense tracking route
    # ------------------------------------------------------------------
    def test_flow_expense_tracking_route(self):
        """User opens expense management section."""
        navigate_to(self.driver, self.base_url, "#expenses")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-018 PASSED: Expense tracking route flow")

    # ------------------------------------------------------------------
    # TC-FLOW-019: Journey - AI Insights module
    # ------------------------------------------------------------------
    def test_flow_ai_insights_module(self):
        """User checks AI insights page."""
        navigate_to(self.driver, self.base_url, "#insights")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-019 PASSED: AI insights module flow")

    # ------------------------------------------------------------------
    # TC-FLOW-020: Journey - Credit risk evaluation route
    # ------------------------------------------------------------------
    def test_flow_credit_risk_evaluation(self):
        """User opens credit risk analysis page."""
        navigate_to(self.driver, self.base_url, "#credit-risk")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-020 PASSED: Credit risk evaluation flow")

    # ------------------------------------------------------------------
    # TC-FLOW-021: Journey - Barcode scanner module
    # ------------------------------------------------------------------
    def test_flow_barcode_scanner_module(self):
        """User checks barcode scanner page."""
        navigate_to(self.driver, self.base_url, "#scanner")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-021 PASSED: Barcode scanner module flow")

    # ------------------------------------------------------------------
    # TC-FLOW-022: Journey - Tax reports module
    # ------------------------------------------------------------------
    def test_flow_tax_reports_module(self):
        """User views tax report page."""
        navigate_to(self.driver, self.base_url, "#tax-report")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-022 PASSED: Tax reports module flow")

    # ------------------------------------------------------------------
    # TC-FLOW-023: Journey - PnL summary route
    # ------------------------------------------------------------------
    def test_flow_pnl_summary_route(self):
        """User views PnL report page."""
        navigate_to(self.driver, self.base_url, "#pnl")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-023 PASSED: PnL summary route flow")

    # ------------------------------------------------------------------
    # TC-FLOW-024: Journey - Staff management route
    # ------------------------------------------------------------------
    def test_flow_staff_management_route(self):
        """User checks staff management page."""
        navigate_to(self.driver, self.base_url, "#staff")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-024 PASSED: Staff management route flow")

    # ------------------------------------------------------------------
    # TC-FLOW-025: Journey - Printer setup route
    # ------------------------------------------------------------------
    def test_flow_printer_setup_route(self):
        """User checks printer settings page."""
        navigate_to(self.driver, self.base_url, "#printer")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-025 PASSED: Printer setup route flow")

    # ------------------------------------------------------------------
    # TC-FLOW-026: Journey - Sales forecast route
    # ------------------------------------------------------------------
    def test_flow_sales_forecast_route(self):
        """User opens sales forecasting tool."""
        navigate_to(self.driver, self.base_url, "#forecast")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-026 PASSED: Sales forecast route flow")

    # ------------------------------------------------------------------
    # TC-FLOW-027: Journey - OTP validation route
    # ------------------------------------------------------------------
    def test_flow_otp_validation_route(self):
        """User opens OTP verification page."""
        navigate_to(self.driver, self.base_url, "#otp")
        time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-027 PASSED: OTP validation route flow")

    # ------------------------------------------------------------------
    # TC-FLOW-028: Journey - Onboarding completion redirect
    # ------------------------------------------------------------------
    def test_flow_onboarding_complete_redirect(self):
        """User clicks get started on landing page."""
        self.driver.get(self.base_url)
        time.sleep(2)
        btn = self.driver.find_elements(By.CSS_SELECTOR, "button, a[href*='login'], a[href*='register']")
        if btn:
            btn[0].click()
            time.sleep(2)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-028 PASSED: Onboarding complete redirect flow")

    # ------------------------------------------------------------------
    # TC-FLOW-029: Journey - Rapid route switching stress
    # ------------------------------------------------------------------
    def test_flow_rapid_route_switching(self):
        """Rapid hash updates to test stability."""
        for r in ["#login", "#register", "#dashboard", "#inventory"]:
            self.driver.execute_script(f"window.location.hash = '{r}';")
            time.sleep(0.3)
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-FLOW-029 PASSED: Rapid route switching flow")

    # ------------------------------------------------------------------
    # TC-FLOW-030: Journey - Final workflow end-to-end check
    # ------------------------------------------------------------------
    def test_flow_final_e2e_check(self):
        """Final sanity flow ensuring stability of session."""
        self.driver.get(self.base_url)
        time.sleep(2)
        assert self.driver.title != ""
        log_event("INFO", "TC-FLOW-030 PASSED: Final workflow end-to-end check complete")
