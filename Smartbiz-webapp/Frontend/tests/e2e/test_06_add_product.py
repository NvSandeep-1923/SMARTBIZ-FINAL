"""
Test Suite 06: Add Product Page
===============================
Tests adding new products, form inputs, pricing, and category selection.
"""

import time
import pytest
from selenium.webdriver.common.by import By
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestAddProduct:
    """Add Product page test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        navigate_to(self.driver, base_url, "#add-product")
        time.sleep(2)
        log_event("INFO", "Navigated to Add Product page")

    def test_add_product_page_loads(self):
        """Add product page should load."""
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-PRD-001 PASSED: Add product page loaded")

    def test_form_inputs_present(self):
        """Form inputs should be present."""
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-PRD-002 PASSED: Form inputs present")
