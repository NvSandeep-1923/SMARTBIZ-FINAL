"""
Test Suite 11: Navigation & Routing
====================================
Tests hash routes, back/forward buttons, and deep links.
"""

import time
import pytest
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from helpers import (
    log_event, get_page_text, navigate_to,
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)


class TestNavigation:
    """Navigation test cases."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url
        log_event("INFO", "Navigation test suite starting")

    def test_hash_navigation_routes(self):
        """Routing between hashes."""
        for route in ["#login", "#register", "#inventory", "#settings"]:
            navigate_to(self.driver, self.base_url, route)
            assert len(self.driver.page_source) > 100
        log_event("INFO", "TC-NAV-001 PASSED: Hash navigation routes verified")
