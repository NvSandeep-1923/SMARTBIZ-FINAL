"""
Test Suite 07: Billing Page
===========================
Tests invoicing, items selection, subtotal calculation, and checkout.
"""

import time
import pytest
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestBilling:
    """Billing page test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        navigate_to(self.driver, base_url, "#billing")
        time.sleep(2)
        log_event("INFO", "Navigated to Billing page")

    def test_billing_page_loads(self):
        """Billing page should load."""
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-BIL-001 PASSED: Billing page loaded")
