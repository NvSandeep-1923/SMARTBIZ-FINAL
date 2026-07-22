"""
Test Suite 11: Navigation & Routing
====================================
Tests the bottom navigation bar, hash-based routing,
active states, and page transitions.
"""

import time
import pytest
from selenium.webdriver.common.by import By
import os
import sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestNavigation:
    """Navigation and routing test cases (requires login)."""

    @pytest.fixture(autouse=True)
    def setup(self, logged_in_driver, base_url):
        self.driver = logged_in_driver
        self.base_url = base_url
        navigate_to(self.driver, base_url, "#dashboard")
        time.sleep(3)
        log_event("INFO", "Navigation tests – starting from Dashboard")

    # ------------------------------------------------------------------
    # TC-NAV-001: Bottom nav has 5 items
    # ------------------------------------------------------------------
    def test_bottom_nav_has_five_items(self):
        """Bottom navigation should have exactly 5 nav items."""
        items = self.driver.find_elements(By.CSS_SELECTOR, ".nav-item")
        assert len(items) == 5, f"Expected 5 nav items, found {len(items)}"
        log_event("INFO", "TC-NAV-001 PASSED: 5 nav items")

    # ------------------------------------------------------------------
    # TC-NAV-002: Nav item – Dashboard
    # ------------------------------------------------------------------
    def test_nav_item_dashboard(self):
        """Dashboard nav item should exist with correct link."""
        item = self.driver.find_elements(By.ID, "nav-dashboard")
        assert len(item) > 0, "Dashboard nav item not found"
        href = item[0].get_attribute("href")
        assert "#dashboard" in href, f"Dashboard href wrong: {href}"
        log_event("INFO", "TC-NAV-002 PASSED: Dashboard nav item")

    # ------------------------------------------------------------------
    # TC-NAV-003: Nav item – Customers
    # ------------------------------------------------------------------
    def test_nav_item_customers(self):
        """Customers nav item should exist."""
        item = self.driver.find_elements(By.ID, "nav-customers")
        assert len(item) > 0, "Customers nav item not found"
        log_event("INFO", "TC-NAV-003 PASSED: Customers nav item")

    # ------------------------------------------------------------------
    # TC-NAV-004: Nav item – Billing
    # ------------------------------------------------------------------
    def test_nav_item_billing(self):
        """Billing nav item should exist."""
        item = self.driver.find_elements(By.ID, "nav-billing")
        assert len(item) > 0, "Billing nav item not found"
        log_event("INFO", "TC-NAV-004 PASSED: Billing nav item")

    # ------------------------------------------------------------------
    # TC-NAV-005: Nav item – Inventory
    # ------------------------------------------------------------------
    def test_nav_item_inventory(self):
        """Inventory nav item should exist."""
        item = self.driver.find_elements(By.ID, "nav-inventory")
        assert len(item) > 0, "Inventory nav item not found"
        log_event("INFO", "TC-NAV-005 PASSED: Inventory nav item")

    # ------------------------------------------------------------------
    # TC-NAV-006: Nav item – Reports
    # ------------------------------------------------------------------
    def test_nav_item_reports(self):
        """Reports nav item should exist."""
        item = self.driver.find_elements(By.ID, "nav-reports")
        assert len(item) > 0, "Reports nav item not found"
        log_event("INFO", "TC-NAV-006 PASSED: Reports nav item")

    # ------------------------------------------------------------------
    # TC-NAV-007: Clicking Customers nav navigates correctly
    # ------------------------------------------------------------------
    def test_nav_customers_navigates(self):
        """Clicking Customers nav should go to #customers."""
        item = self.driver.find_element(By.ID, "nav-customers")
        item.click()
        time.sleep(3)
        assert "#customers" in self.driver.current_url, (
            f"Expected #customers, got {self.driver.current_url}"
        )
        log_event("INFO", "TC-NAV-007 PASSED: Customers nav navigates")

    # ------------------------------------------------------------------
    # TC-NAV-008: Clicking Billing nav navigates correctly
    # ------------------------------------------------------------------
    def test_nav_billing_navigates(self):
        """Clicking Billing nav should go to #billing."""
        navigate_to(self.driver, self.base_url, "#dashboard")
        time.sleep(2)
        item = self.driver.find_element(By.ID, "nav-billing")
        item.click()
        time.sleep(3)
        assert "#billing" in self.driver.current_url, (
            f"Expected #billing, got {self.driver.current_url}"
        )
        log_event("INFO", "TC-NAV-008 PASSED: Billing nav navigates")

    # ------------------------------------------------------------------
    # TC-NAV-009: Clicking Inventory nav navigates correctly
    # ------------------------------------------------------------------
    def test_nav_inventory_navigates(self):
        """Clicking Inventory nav should go to #inventory."""
        navigate_to(self.driver, self.base_url, "#dashboard")
        time.sleep(2)
        item = self.driver.find_element(By.ID, "nav-inventory")
        item.click()
        time.sleep(3)
        assert "#inventory" in self.driver.current_url, (
            f"Expected #inventory, got {self.driver.current_url}"
        )
        log_event("INFO", "TC-NAV-009 PASSED: Inventory nav navigates")

    # ------------------------------------------------------------------
    # TC-NAV-010: Clicking Reports nav navigates correctly
    # ------------------------------------------------------------------
    def test_nav_reports_navigates(self):
        """Clicking Reports nav should go to #reports."""
        navigate_to(self.driver, self.base_url, "#dashboard")
        time.sleep(2)
        item = self.driver.find_element(By.ID, "nav-reports")
        item.click()
        time.sleep(3)
        assert "#reports" in self.driver.current_url, (
            f"Expected #reports, got {self.driver.current_url}"
        )
        log_event("INFO", "TC-NAV-010 PASSED: Reports nav navigates")

    # ------------------------------------------------------------------
    # TC-NAV-011: Direct URL access to #settings
    # ------------------------------------------------------------------
    def test_direct_url_settings(self):
        """Directly navigating to #settings should load settings page."""
        navigate_to(self.driver, self.base_url, "#settings")
        time.sleep(3)
        body = get_page_text(self.driver)
        assert "Settings" in body or "Rajesh Kumar" in body, (
            "Settings page did not load from direct URL"
        )
        log_event("INFO", "TC-NAV-011 PASSED: Direct #settings access")

    # ------------------------------------------------------------------
    # TC-NAV-012: Invalid hash falls back gracefully
    # ------------------------------------------------------------------
    def test_invalid_hash_fallback(self):
        """An invalid hash should fall back to onboarding or login."""
        navigate_to(self.driver, self.base_url, "#nonexistent-page")
        time.sleep(3)
        body = get_page_text(self.driver)
        # Should fall back to onboarding (default route)
        has_onboarding = "Get Started" in body or "Smart Inventory" in body
        has_login = "Login" in body
        assert has_onboarding or has_login, (
            "Invalid hash did not fall back gracefully"
        )
        log_event("INFO", "TC-NAV-012 PASSED: Invalid hash fallback")

    # ------------------------------------------------------------------
    # TC-NAV-013: Nav icons render (Phosphor icons)
    # ------------------------------------------------------------------
    def test_nav_icons_render(self):
        """Each nav item should contain an icon element."""
        navigate_to(self.driver, self.base_url, "#dashboard")
        time.sleep(2)
        icons = self.driver.find_elements(By.CSS_SELECTOR, ".nav-icon")
        assert len(icons) >= 5, f"Expected ≥5 nav icons, found {len(icons)}"
        log_event("INFO", "TC-NAV-013 PASSED: Nav icons rendered")

    # ------------------------------------------------------------------
    # TC-NAV-014: Nav labels are visible
    # ------------------------------------------------------------------
    def test_nav_labels_visible(self):
        """Each nav item should have a visible label."""
        navigate_to(self.driver, self.base_url, "#dashboard")
        time.sleep(2)
        labels = self.driver.find_elements(By.CSS_SELECTOR, ".nav-label")
        assert len(labels) >= 5, f"Expected ≥5 labels, found {len(labels)}"
        label_texts = [l.text.strip() for l in labels if l.text.strip()]
        assert len(label_texts) >= 5, (
            f"Only {len(label_texts)} labels have text"
        )
        log_event("INFO", "TC-NAV-014 PASSED: Nav labels visible")
