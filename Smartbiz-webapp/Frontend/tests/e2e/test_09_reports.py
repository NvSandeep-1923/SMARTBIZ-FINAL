"""
Test Suite 09: Reports & Analytics Page
=======================================
Tests PnL, sales, tax, and inventory reports.
"""

import time
import pytest
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestReports:
    """Reports page test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        navigate_to(self.driver, base_url, "#reports")
        time.sleep(2)
        log_event("INFO", "Navigated to Reports page")

    def test_reports_page_loads(self):
        """Reports page should load."""
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-REP-001 PASSED: Reports page loaded")
