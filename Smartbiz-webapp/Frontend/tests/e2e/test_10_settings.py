"""
Test Suite 10: Settings Page
============================
Tests business profile, printer settings, language options.
"""

import time
import pytest
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestSettings:
    """Settings page test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        navigate_to(self.driver, base_url, "#settings")
        time.sleep(2)
        log_event("INFO", "Navigated to Settings page")

    def test_settings_page_loads(self):
        """Settings page should load."""
        assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-SET-001 PASSED: Settings page loaded")
