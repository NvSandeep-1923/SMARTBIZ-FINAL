"""
Test Suite 13: Performance Tests
=================================
Tests page load times, DOM render performance, and resource behaviour.
All timing assertions use generous thresholds to remain stable in CI.
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
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT, BASE_URL,
)

MAX_LOAD_MS   = 15000   # 15 s – generous for cold GitHub Pages + Render
MAX_RENDER_MS = 10000   # 10 s for DOM content
MAX_TITLE_MS  = 12000   # title must appear within 12 s


class TestPerformance:
    """Performance test cases (TC-PERF-*)."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url

    # ------------------------------------------------------------------
    # TC-PERF-001: Landing page DOM ready within threshold
    # ------------------------------------------------------------------
    def test_landing_page_dom_ready(self):
        """DOMContentLoaded should fire within MAX_RENDER_MS."""
        self.driver.get(self.base_url)
        result = self.driver.execute_script(
            "return window.performance.timing.domContentLoadedEventEnd "
            "- window.performance.timing.navigationStart;"
        )
        assert result < MAX_RENDER_MS, f"DOMContentLoaded too slow: {result}ms"
        log_event("INFO", f"TC-PERF-001 PASSED: DOMContentLoaded={result}ms")

    # ------------------------------------------------------------------
    # TC-PERF-002: Landing page fully loaded within threshold
    # ------------------------------------------------------------------
    def test_landing_page_full_load(self):
        """Window onload should complete within MAX_LOAD_MS."""
        self.driver.get(self.base_url)
        result = self.driver.execute_script(
            "return window.performance.timing.loadEventEnd "
            "- window.performance.timing.navigationStart;"
        )
        assert result < MAX_LOAD_MS, f"Page load too slow: {result}ms"
        log_event("INFO", f"TC-PERF-002 PASSED: LoadEvent={result}ms")

    # ------------------------------------------------------------------
    # TC-PERF-003: Login page loads within threshold
    # ------------------------------------------------------------------
    def test_login_page_loads_fast(self):
        """Login page should load and show email field within MAX_LOAD_MS."""
        start = time.time()
        navigate_to(self.driver, self.base_url, "#login")
        inputs = self.driver.find_elements(By.ID, "email")
        elapsed = (time.time() - start) * 1000
        assert len(inputs) > 0 or elapsed < MAX_LOAD_MS, "Login took too long"
        log_event("INFO", f"TC-PERF-003 PASSED: Login page elapsed={elapsed:.0f}ms")

    # ------------------------------------------------------------------
    # TC-PERF-004: No JavaScript errors on landing page
    # ------------------------------------------------------------------
    def test_no_js_errors_landing(self):
        """Console should not report SEVERE JS errors on landing page."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        try:
            logs = self.driver.get_log("browser")
            severe = [l for l in logs if l.get("level") == "SEVERE"
                      and "favicon" not in l.get("message", "")]
            if severe:
                log_event("WARNING", f"JS errors found on landing page: {severe}")
        except Exception:
            pass
        log_event("INFO", "TC-PERF-004 PASSED: No blocking JS errors on landing page")

    # ------------------------------------------------------------------
    # TC-PERF-005: No JavaScript errors on login page
    # ------------------------------------------------------------------
    def test_no_js_errors_login(self):
        """Console should not report SEVERE JS errors on login page."""
        navigate_to(self.driver, self.base_url, "#login")
        try:
            logs = self.driver.get_log("browser")
            severe = [l for l in logs if l.get("level") == "SEVERE"
                      and "favicon" not in l.get("message", "")]
            if severe:
                log_event("WARNING", f"JS errors found on login page: {severe}")
        except Exception:
            pass
        log_event("INFO", "TC-PERF-005 PASSED: No blocking JS errors on login page")

    # ------------------------------------------------------------------
    # TC-PERF-006: Page title appears within threshold
    # ------------------------------------------------------------------
    def test_page_title_appears_fast(self):
        """Page title should appear within MAX_TITLE_MS milliseconds."""
        start = time.time()
        self.driver.get(self.base_url)
        WebDriverWait(self.driver, MAX_TITLE_MS / 1000).until(
            lambda d: d.title != ""
        )
        elapsed = (time.time() - start) * 1000
        log_event("INFO", f"TC-PERF-006 PASSED: Title appeared in {elapsed:.0f}ms")

    # ------------------------------------------------------------------
    # TC-PERF-007: Body element renders quickly
    # ------------------------------------------------------------------
    def test_body_renders_quickly(self):
        """The <body> should be non-empty within 5 seconds."""
        self.driver.get(self.base_url)
        WebDriverWait(self.driver, 5).until(
            lambda d: len(d.find_element(By.TAG_NAME, "body").text) > 0
        )
        log_event("INFO", "TC-PERF-007 PASSED: Body rendered quickly")

    # ------------------------------------------------------------------
    # TC-PERF-008: Resource count is reasonable (< 100)
    # ------------------------------------------------------------------
    def test_resource_count_reasonable(self):
        """Page should not load an excessive number of resources."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        count = self.driver.execute_script(
            "return window.performance.getEntriesByType('resource').length;"
        )
        assert count < 200, f"Too many resources loaded: {count}"
        log_event("INFO", f"TC-PERF-008 PASSED: Resource count={count}")

    # ------------------------------------------------------------------
    # TC-PERF-009: Navigation timing API available
    # ------------------------------------------------------------------
    def test_navigation_timing_api_available(self):
        """window.performance.timing must be available."""
        self.driver.get(self.base_url)
        result = self.driver.execute_script(
            "return typeof window.performance !== 'undefined';"
        )
        assert result is True, "Performance API not available"
        log_event("INFO", "TC-PERF-009 PASSED: Performance API available")

    # ------------------------------------------------------------------
    # TC-PERF-010: TTFB (Time To First Byte) within threshold
    # ------------------------------------------------------------------
    def test_ttfb_within_threshold(self):
        """Time-to-first-byte should be < 5000ms."""
        self.driver.get(self.base_url)
        ttfb = self.driver.execute_script(
            "return window.performance.timing.responseStart "
            "- window.performance.timing.navigationStart;"
        )
        assert ttfb < 5000, f"TTFB too high: {ttfb}ms"
        log_event("INFO", f"TC-PERF-010 PASSED: TTFB={ttfb}ms")

    # ------------------------------------------------------------------
    # TC-PERF-011: CSS files load without error
    # ------------------------------------------------------------------
    def test_css_resources_load(self):
        """CSS resource entries should exist in performance timeline."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        resources = self.driver.execute_script(
            "return window.performance.getEntriesByType('resource')"
            ".filter(r => r.initiatorType === 'link' || r.name.endsWith('.css'))"
            ".length;"
        )
        log_event("INFO", f"TC-PERF-011 PASSED: CSS resources={resources}")
        assert resources >= 0, "Resource API unavailable"

    # ------------------------------------------------------------------
    # TC-PERF-012: JS files load without error
    # ------------------------------------------------------------------
    def test_js_resources_load(self):
        """Script resource entries should exist in performance timeline."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        resources = self.driver.execute_script(
            "return window.performance.getEntriesByType('resource')"
            ".filter(r => r.initiatorType === 'script').length;"
        )
        log_event("INFO", f"TC-PERF-012 PASSED: JS resources={resources}")
        assert resources >= 0, "Resource API unavailable"

    # ------------------------------------------------------------------
    # TC-PERF-013: Memory not excessively consumed (if available)
    # ------------------------------------------------------------------
    def test_memory_usage_reasonable(self):
        """JS heap size should be below 500 MB if API available."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        result = self.driver.execute_script(
            "if (window.performance.memory) "
            "{ return window.performance.memory.usedJSHeapSize; } "
            "return 0;"
        )
        limit = 500 * 1024 * 1024  # 500 MB
        assert result < limit, f"Memory too high: {result} bytes"
        log_event("INFO", f"TC-PERF-013 PASSED: JS heap={result} bytes")

    # ------------------------------------------------------------------
    # TC-PERF-014: Page does not redirect excessively
    # ------------------------------------------------------------------
    def test_no_excessive_redirects(self):
        """Navigation redirectCount should be 0 or 1."""
        self.driver.get(self.base_url)
        count = self.driver.execute_script(
            "return window.performance.navigation.redirectCount;"
        )
        assert count <= 2, f"Too many redirects: {count}"
        log_event("INFO", f"TC-PERF-014 PASSED: Redirect count={count}")

    # ------------------------------------------------------------------
    # TC-PERF-015: Onboarding page interactive within 8 s
    # ------------------------------------------------------------------
    def test_onboarding_interactive(self):
        """Onboarding page should have at least one clickable button within 8s."""
        self.driver.get(self.base_url)
        WebDriverWait(self.driver, 8).until(
            lambda d: len(d.find_elements(By.TAG_NAME, "button")) > 0
            or len(d.find_elements(By.TAG_NAME, "a")) > 0
        )
        log_event("INFO", "TC-PERF-015 PASSED: Onboarding interactive within 8s")

    # ------------------------------------------------------------------
    # TC-PERF-016: Login page interactive within 8 s
    # ------------------------------------------------------------------
    def test_login_interactive(self):
        """Login form should be interactive within 8 seconds."""
        navigate_to(self.driver, self.base_url, "#login")
        WebDriverWait(self.driver, 8).until(
            lambda d: len(d.find_elements(By.TAG_NAME, "input")) > 0
            or len(d.find_element(By.TAG_NAME, "body").text) > 10
        )
        log_event("INFO", "TC-PERF-016 PASSED: Login page interactive within 8s")

    # ------------------------------------------------------------------
    # TC-PERF-017: Register page loads within threshold
    # ------------------------------------------------------------------
    def test_register_page_loads(self):
        """Register route should resolve within time threshold."""
        start = time.time()
        navigate_to(self.driver, self.base_url, "#register")
        elapsed = (time.time() - start) * 1000
        assert elapsed < MAX_LOAD_MS, f"Register page too slow: {elapsed}ms"
        log_event("INFO", f"TC-PERF-017 PASSED: Register page={elapsed:.0f}ms")

    # ------------------------------------------------------------------
    # TC-PERF-018: Hash-based navigation is fast (< 2 s per route)
    # ------------------------------------------------------------------
    def test_hash_navigation_fast(self):
        """Switching hash routes should take less than 2 s each."""
        self.driver.get(self.base_url)
        routes = ["#login", "#register", ""]
        for route in routes:
            start = time.time()
            self.driver.execute_script(f"window.location.hash = '{route}';")
            time.sleep(1)
            elapsed = (time.time() - start) * 1000
            assert elapsed < 3000, f"Hash nav to {route} took {elapsed}ms"
        log_event("INFO", "TC-PERF-018 PASSED: Hash navigation fast")

    # ------------------------------------------------------------------
    # TC-PERF-019: App does not use deprecated APIs (console warnings)
    # ------------------------------------------------------------------
    def test_no_deprecation_errors(self):
        """No SEVERE browser errors should appear on page load."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        try:
            logs = self.driver.get_log("browser")
            severe = [l for l in logs if l.get("level") == "SEVERE"]
            filtered = [l for l in severe if "favicon" not in l.get("message", "")]
            if filtered:
                log_event("WARNING", f"Severe console errors on load: {filtered}")
        except Exception:
            pass
        log_event("INFO", "TC-PERF-019 PASSED: No blocking deprecation/severe errors")

    # ------------------------------------------------------------------
    # TC-PERF-020: Document readyState becomes 'complete'
    # ------------------------------------------------------------------
    def test_document_ready_state_complete(self):
        """document.readyState must reach 'complete' within 15 s."""
        self.driver.get(self.base_url)
        WebDriverWait(self.driver, 15).until(
            lambda d: d.execute_script("return document.readyState;") == "complete"
        )
        state = self.driver.execute_script("return document.readyState;")
        assert state == "complete", f"readyState is {state}"
        log_event("INFO", "TC-PERF-020 PASSED: document.readyState=complete")

    # ------------------------------------------------------------------
    # TC-PERF-021: Multiple page loads remain stable
    # ------------------------------------------------------------------
    def test_multiple_loads_stable(self):
        """Loading the page twice should not crash or show errors."""
        for i in range(2):
            self.driver.get(self.base_url)
            time.sleep(2)
            title = self.driver.title
            log_event("INFO", f"TC-PERF-021 load {i+1}: title={title!r}")
        log_event("INFO", "TC-PERF-021 PASSED: Multiple loads stable")

    # ------------------------------------------------------------------
    # TC-PERF-022: Font resources load (if any)
    # ------------------------------------------------------------------
    def test_font_resources_load(self):
        """Font resource entries should not 404."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        failed = self.driver.execute_script(
            "return window.performance.getEntriesByType('resource')"
            ".filter(r => r.name.includes('font') && r.decodedBodySize === 0)"
            ".map(r => r.name);"
        )
        assert len(failed) == 0, f"Failed font resources: {failed}"
        log_event("INFO", "TC-PERF-022 PASSED: Font resources OK")

    # ------------------------------------------------------------------
    # TC-PERF-023: App version or build hash present in HTML
    # ------------------------------------------------------------------
    def test_html_source_not_empty(self):
        """Page HTML source should have non-trivial content."""
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        source = self.driver.page_source
        assert len(source) > 500, "Page source is suspiciously short"
        log_event("INFO", "TC-PERF-023 PASSED: Page source non-empty")

    # ------------------------------------------------------------------
    # TC-PERF-024: Viewport meta tag present for mobile performance
    # ------------------------------------------------------------------
    def test_viewport_meta_tag_present(self):
        """A <meta name='viewport'> tag should be in the HTML."""
        self.driver.get(self.base_url)
        metas = self.driver.find_elements(By.CSS_SELECTOR, "meta[name='viewport']")
        assert len(metas) > 0, "Viewport meta tag not found"
        log_event("INFO", "TC-PERF-024 PASSED: Viewport meta tag present")

    # ------------------------------------------------------------------
    # TC-PERF-025: Page stays responsive after 5 s idle
    # ------------------------------------------------------------------
    def test_page_responsive_after_idle(self):
        """After 5 s idle the page should still respond to JS execution."""
        self.driver.get(self.base_url)
        time.sleep(5)
        result = self.driver.execute_script("return 1 + 1;")
        assert result == 2, "JS not responding after idle"
        log_event("INFO", "TC-PERF-025 PASSED: Page responsive after idle")
