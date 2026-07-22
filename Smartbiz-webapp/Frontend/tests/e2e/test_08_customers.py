"""
Test Suite 08: Customer Management Page
=======================================
Tests customer directory, adding customers, and ledger balances.
"""

import time
import pytest
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestCustomers:
    """Customers page test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        navigate_to(self.driver, base_url, "#customers")
        time.sleep(2)
        log_event("INFO", "Navigated to Customers page")

    def test_customers_page_loads(self):
        """Customers page should load."""
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-CUST-001 PASSED: Customers page loaded")
