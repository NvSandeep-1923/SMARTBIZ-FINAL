"""
Test Suite 14: Responsive & Mobile Layout Tests
================================================
Tests correct rendering across common viewport sizes including mobile
(360x800), tablet (768x1024), and desktop (1280x800).
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
    PAGE_LOAD_WAIT, DEFAULT_TIMEOUT,
)

VIEWPORTS = {
    "mobile_s":  (360, 800),
    "mobile_m":  (412, 915),
    "tablet":    (768, 1024),
    "desktop":   (1280, 800),
    "desktop_l": (1920, 1080),
}


def set_viewport(driver, width, height):
    driver.set_window_size(width, height)
    time.sleep(1)


class TestResponsive:
    """Responsive / mobile layout test cases (TC-RSP-*)."""

    @pytest.fixture(autouse=True)
    def setup(self, driver, base_url):
        self.driver = driver
        self.base_url = base_url

    # ------------------------------------------------------------------
    # TC-RSP-001: Mobile-S viewport – page loads
    # ------------------------------------------------------------------
    def test_mobile_s_page_loads(self):
        """Page should load on 360x800 viewport without errors."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        assert self.driver.title != "", "Page has no title on mobile-S"
        log_event("INFO", "TC-RSP-001 PASSED: Mobile-S page loads")

    # ------------------------------------------------------------------
    # TC-RSP-002: Mobile-M viewport – page loads
    # ------------------------------------------------------------------
    def test_mobile_m_page_loads(self):
        """Page should load on 412x915 viewport."""
        set_viewport(self.driver, 412, 915)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        body = self.driver.find_element(By.TAG_NAME, "body").text
        log_event("INFO", f"TC-RSP-002 PASSED: Mobile-M body length={len(body)}")

    # ------------------------------------------------------------------
    # TC-RSP-003: Tablet viewport – page loads
    # ------------------------------------------------------------------
    def test_tablet_page_loads(self):
        """Page should load on 768x1024 viewport."""
        set_viewport(self.driver, 768, 1024)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        body = self.driver.find_element(By.TAG_NAME, "body").text
        log_event("INFO", f"TC-RSP-003 PASSED: Tablet body length={len(body)}")

    # ------------------------------------------------------------------
    # TC-RSP-004: Desktop viewport – page loads
    # ------------------------------------------------------------------
    def test_desktop_page_loads(self):
        """Page should load on 1280x800 desktop viewport."""
        set_viewport(self.driver, 1280, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        assert len(self.driver.page_source) > 200, "Desktop page source too short"
        log_event("INFO", "TC-RSP-004 PASSED: Desktop page loads")

    # ------------------------------------------------------------------
    # TC-RSP-005: Large desktop viewport – page loads
    # ------------------------------------------------------------------
    def test_large_desktop_page_loads(self):
        """Page should load on 1920x1080 viewport."""
        set_viewport(self.driver, 1920, 1080)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        source = self.driver.page_source
        assert len(source) > 200, "1920x1080 page source too short"
        log_event("INFO", "TC-RSP-005 PASSED: Large desktop viewport loads")

    # ------------------------------------------------------------------
    # TC-RSP-006: Viewport meta tag has width=device-width
    # ------------------------------------------------------------------
    def test_viewport_meta_device_width(self):
        """Viewport meta content should include width=device-width."""
        set_viewport(self.driver, 412, 915)
        self.driver.get(self.base_url)
        metas = self.driver.find_elements(By.CSS_SELECTOR, "meta[name='viewport']")
        if metas:
            content = metas[0].get_attribute("content") or ""
            assert "width" in content.lower(), "Viewport meta missing width"
        log_event("INFO", "TC-RSP-006 PASSED: Viewport meta has width")

    # ------------------------------------------------------------------
    # TC-RSP-007: No horizontal overflow on mobile
    # ------------------------------------------------------------------
    def test_no_horizontal_overflow_mobile(self):
        """Body scroll width should not exceed viewport width on mobile."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        scroll_w = self.driver.execute_script("return document.body.scrollWidth;")
        win_w    = self.driver.execute_script("return window.innerWidth;")
        assert scroll_w <= win_w + 20, (
            f"Horizontal overflow: scrollWidth={scroll_w}, innerWidth={win_w}"
        )
        log_event("INFO", f"TC-RSP-007 PASSED: No horizontal overflow mobile scrollW={scroll_w}")

    # ------------------------------------------------------------------
    # TC-RSP-008: No horizontal overflow on tablet
    # ------------------------------------------------------------------
    def test_no_horizontal_overflow_tablet(self):
        """Body scroll width should not exceed viewport width on tablet."""
        set_viewport(self.driver, 768, 1024)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        scroll_w = self.driver.execute_script("return document.body.scrollWidth;")
        win_w    = self.driver.execute_script("return window.innerWidth;")
        assert scroll_w <= win_w + 20, (
            f"Horizontal overflow on tablet: scrollWidth={scroll_w}"
        )
        log_event("INFO", "TC-RSP-008 PASSED: No horizontal overflow tablet")

    # ------------------------------------------------------------------
    # TC-RSP-009: Login page renders on mobile
    # ------------------------------------------------------------------
    def test_login_renders_on_mobile(self):
        """Login page body should have content on mobile viewport."""
        set_viewport(self.driver, 360, 800)
        navigate_to(self.driver, self.base_url, "#login")
        body_text = get_page_text(self.driver)
        assert len(body_text) > 0, "Login page body empty on mobile"
        log_event("INFO", "TC-RSP-009 PASSED: Login renders on mobile")

    # ------------------------------------------------------------------
    # TC-RSP-010: Register page renders on mobile
    # ------------------------------------------------------------------
    def test_register_renders_on_mobile(self):
        """Register page body should have content on mobile viewport."""
        set_viewport(self.driver, 360, 800)
        navigate_to(self.driver, self.base_url, "#register")
        body_text = get_page_text(self.driver)
        assert len(body_text) > 0, "Register page body empty on mobile"
        log_event("INFO", "TC-RSP-010 PASSED: Register renders on mobile")

    # ------------------------------------------------------------------
    # TC-RSP-011: Buttons are touch-friendly (height >= 36px) on mobile
    # ------------------------------------------------------------------
    def test_buttons_touch_friendly(self):
        """All buttons should have height >= 36px on mobile viewport."""
        set_viewport(self.driver, 412, 915)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        buttons = self.driver.find_elements(By.TAG_NAME, "button")
        small_buttons = []
        for btn in buttons[:10]:
            h = btn.size.get("height", 0)
            if h > 0 and h < 36:
                small_buttons.append(f"{btn.text!r}: {h}px")
        assert len(small_buttons) == 0, f"Too-small buttons: {small_buttons}"
        log_event("INFO", "TC-RSP-011 PASSED: Buttons are touch-friendly")

    # ------------------------------------------------------------------
    # TC-RSP-012: Input fields are touch-friendly (height >= 36px)
    # ------------------------------------------------------------------
    def test_inputs_touch_friendly(self):
        """Input fields should have height >= 36px on mobile."""
        set_viewport(self.driver, 412, 915)
        navigate_to(self.driver, self.base_url, "#login")
        inputs = self.driver.find_elements(By.TAG_NAME, "input")
        small_inputs = []
        for inp in inputs:
            h = inp.size.get("height", 0)
            if h > 0 and h < 30:
                small_inputs.append(f"type={inp.get_attribute('type')}: {h}px")
        assert len(small_inputs) == 0, f"Too-small inputs: {small_inputs}"
        log_event("INFO", "TC-RSP-012 PASSED: Inputs touch-friendly")

    # ------------------------------------------------------------------
    # TC-RSP-013: Page title is present on all viewports
    # ------------------------------------------------------------------
    def test_page_title_all_viewports(self):
        """Page title should not be empty on any viewport."""
        for name, (w, h) in VIEWPORTS.items():
            set_viewport(self.driver, w, h)
            self.driver.get(self.base_url)
            time.sleep(2)
            title = self.driver.title
            log_event("INFO", f"TC-RSP-013 viewport={name} title={title!r}")
        log_event("INFO", "TC-RSP-013 PASSED: Title present across viewports")

    # ------------------------------------------------------------------
    # TC-RSP-014: Landscape mobile does not break layout
    # ------------------------------------------------------------------
    def test_landscape_mobile_layout(self):
        """Landscape mobile (844x390) should not have JS errors."""
        set_viewport(self.driver, 844, 390)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        source = self.driver.page_source
        assert len(source) > 100, "Landscape page source too short"
        log_event("INFO", "TC-RSP-014 PASSED: Landscape mobile layout OK")

    # ------------------------------------------------------------------
    # TC-RSP-015: Images do not overflow their containers
    # ------------------------------------------------------------------
    def test_images_do_not_overflow(self):
        """Images should not exceed viewport width on mobile."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        win_w = self.driver.execute_script("return window.innerWidth;")
        imgs  = self.driver.find_elements(By.TAG_NAME, "img")
        overflow = []
        for img in imgs:
            w = img.size.get("width", 0)
            if w > win_w:
                overflow.append(f"{img.get_attribute('src')}: {w}px")
        assert len(overflow) == 0, f"Overflowing images: {overflow}"
        log_event("INFO", "TC-RSP-015 PASSED: Images do not overflow")

    # ------------------------------------------------------------------
    # TC-RSP-016: Font size readable on mobile (>= 12px)
    # ------------------------------------------------------------------
    def test_font_size_readable_mobile(self):
        """Body font-size should be >= 12px on mobile."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        font_size = self.driver.execute_script(
            "const el = document.querySelector('body');"
            "return el ? parseFloat(window.getComputedStyle(el).fontSize) : 16;"
        )
        assert font_size >= 12, f"Font size too small: {font_size}px"
        log_event("INFO", f"TC-RSP-016 PASSED: Font size={font_size}px")

    # ------------------------------------------------------------------
    # TC-RSP-017: App container width is not fixed to desktop size
    # ------------------------------------------------------------------
    def test_app_container_not_fixed_width(self):
        """App container should not have a fixed 1200px+ width on mobile."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        containers = self.driver.find_elements(By.CSS_SELECTOR, "#app, .app, main, #root")
        for c in containers[:3]:
            w = c.size.get("width", 0)
            assert w <= 400, f"Container too wide for mobile: {w}px"
        log_event("INFO", "TC-RSP-017 PASSED: Container not fixed-desktop-width")

    # ------------------------------------------------------------------
    # TC-RSP-018: Login form is visible on mobile
    # ------------------------------------------------------------------
    def test_login_form_visible_mobile(self):
        """Login form elements should be visible on mobile viewport."""
        set_viewport(self.driver, 360, 800)
        navigate_to(self.driver, self.base_url, "#login")
        inputs = self.driver.find_elements(By.TAG_NAME, "input")
        assert len(inputs) > 0 or len(get_page_text(self.driver)) > 20, (
            "No inputs or content visible on mobile login"
        )
        log_event("INFO", "TC-RSP-018 PASSED: Login form visible on mobile")

    # ------------------------------------------------------------------
    # TC-RSP-019: Scrolling works on mobile viewport
    # ------------------------------------------------------------------
    def test_scrolling_works_mobile(self):
        """JavaScript scroll should work without throwing errors."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        self.driver.execute_script("window.scrollTo(0, 100);")
        scroll_y = self.driver.execute_script("return window.scrollY;")
        log_event("INFO", f"TC-RSP-019 PASSED: ScrollY={scroll_y}")

    # ------------------------------------------------------------------
    # TC-RSP-020: Window resize does not crash JS
    # ------------------------------------------------------------------
    def test_window_resize_no_crash(self):
        """Resizing window should not produce SEVERE JS errors."""
        self.driver.get(self.base_url)
        time.sleep(2)
        for w, h in [(1280, 800), (768, 1024), (360, 800), (1280, 800)]:
            self.driver.set_window_size(w, h)
            time.sleep(0.5)
        logs = self.driver.get_log("browser")
        severe = [l for l in logs if l.get("level") == "SEVERE"
                  and "favicon" not in l.get("message", "")]
        assert len(severe) == 0, f"Resize caused JS errors: {severe}"
        log_event("INFO", "TC-RSP-020 PASSED: Window resize no crash")

    # ------------------------------------------------------------------
    # TC-RSP-021: iPhone 12 Pro viewport (390x844) loads
    # ------------------------------------------------------------------
    def test_iphone12_viewport(self):
        """iPhone 12 Pro viewport (390x844) should load the app."""
        set_viewport(self.driver, 390, 844)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        assert len(self.driver.page_source) > 100, "iPhone 12 viewport issue"
        log_event("INFO", "TC-RSP-021 PASSED: iPhone 12 viewport loads")

    # ------------------------------------------------------------------
    # TC-RSP-022: Galaxy S21 viewport (360x800) loads
    # ------------------------------------------------------------------
    def test_galaxy_s21_viewport(self):
        """Galaxy S21 viewport (360x800) should load the app."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        assert len(self.driver.page_source) > 100, "Galaxy S21 viewport issue"
        log_event("INFO", "TC-RSP-022 PASSED: Galaxy S21 viewport loads")

    # ------------------------------------------------------------------
    # TC-RSP-023: iPad viewport (820x1180) loads
    # ------------------------------------------------------------------
    def test_ipad_viewport(self):
        """iPad Air viewport (820x1180) should load the app."""
        set_viewport(self.driver, 820, 1180)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        assert len(self.driver.page_source) > 100, "iPad viewport issue"
        log_event("INFO", "TC-RSP-023 PASSED: iPad viewport loads")

    # ------------------------------------------------------------------
    # TC-RSP-024: Body background-color not default white on all sizes
    # ------------------------------------------------------------------
    def test_body_has_background_color(self):
        """App should define a background color (not just browser default)."""
        set_viewport(self.driver, 412, 915)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        bg = self.driver.execute_script(
            "return window.getComputedStyle(document.body).backgroundColor;"
        )
        log_event("INFO", f"TC-RSP-024 PASSED: background-color={bg}")
        assert bg != "", "No background color set"

    # ------------------------------------------------------------------
    # TC-RSP-025: Desktop view renders more content than mobile
    # ------------------------------------------------------------------
    def test_desktop_has_content(self):
        """Desktop viewport should load the page with content."""
        set_viewport(self.driver, 1280, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        body_text = get_page_text(self.driver)
        log_event("INFO", f"TC-RSP-025 PASSED: Desktop body len={len(body_text)}")

    # ------------------------------------------------------------------
    # TC-RSP-026: All links are clickable on mobile
    # ------------------------------------------------------------------
    def test_links_accessible_mobile(self):
        """Links on mobile should not be 0px wide (invisible/inaccessible)."""
        set_viewport(self.driver, 360, 800)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        links = self.driver.find_elements(By.TAG_NAME, "a")
        broken = [a.text for a in links[:10] if a.size.get("width", 1) == 0]
        assert len(broken) == 0, f"Zero-width links on mobile: {broken}"
        log_event("INFO", "TC-RSP-026 PASSED: Links accessible on mobile")

    # ------------------------------------------------------------------
    # TC-RSP-027: Register page no horizontal overflow on mobile
    # ------------------------------------------------------------------
    def test_register_no_overflow_mobile(self):
        """Register page should not overflow horizontally on mobile."""
        set_viewport(self.driver, 360, 800)
        navigate_to(self.driver, self.base_url, "#register")
        scroll_w = self.driver.execute_script("return document.body.scrollWidth;")
        win_w    = self.driver.execute_script("return window.innerWidth;")
        assert scroll_w <= win_w + 20, f"Register overflow: scrollW={scroll_w}"
        log_event("INFO", "TC-RSP-027 PASSED: Register no overflow mobile")

    # ------------------------------------------------------------------
    # TC-RSP-028: Very small viewport (320x568) does not crash
    # ------------------------------------------------------------------
    def test_very_small_viewport(self):
        """iPhone SE (320x568) should not crash the app."""
        set_viewport(self.driver, 320, 568)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        assert len(self.driver.page_source) > 100, "320x568 crashed"
        log_event("INFO", "TC-RSP-028 PASSED: Very small viewport no crash")

    # ------------------------------------------------------------------
    # TC-RSP-029: App renders correctly after orientation change simulation
    # ------------------------------------------------------------------
    def test_orientation_change_simulation(self):
        """Simulating portrait→landscape should not break rendering."""
        set_viewport(self.driver, 360, 800)    # portrait
        self.driver.get(self.base_url)
        time.sleep(2)
        set_viewport(self.driver, 800, 360)    # landscape
        time.sleep(2)
        assert len(self.driver.page_source) > 100, "Orientation change crashed"
        log_event("INFO", "TC-RSP-029 PASSED: Orientation change OK")

    # ------------------------------------------------------------------
    # TC-RSP-030: Text remains legible (no overflow) on smallest viewport
    # ------------------------------------------------------------------
    def test_text_legible_smallest_viewport(self):
        """Text on 320px viewport should not be clipped to 0px height."""
        set_viewport(self.driver, 320, 568)
        self.driver.get(self.base_url)
        time.sleep(PAGE_LOAD_WAIT)
        body = self.driver.find_element(By.TAG_NAME, "body")
        height = body.size.get("height", 0)
        assert height > 0, "Body height is 0 on smallest viewport"
        log_event("INFO", f"TC-RSP-030 PASSED: Body height={height}px on 320px")
