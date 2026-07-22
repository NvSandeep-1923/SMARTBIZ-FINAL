"""
SmartBiz E2E Test Suite - Pytest Fixtures
==========================================
Provides WebDriver setup, login helpers, and hooks.
Utility functions are in helpers.py (importable by test files).
"""

import os
import sys
import time
import pytest
from datetime import datetime

# Ensure the e2e directory is on the Python path so helpers.py is importable
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException

from helpers import (
    BASE_URL, TEST_EMAIL, TEST_PASSWORD,
    DEFAULT_TIMEOUT, SHORT_TIMEOUT, PAGE_LOAD_WAIT,
    log_event,
)


# ---------------------------------------------------------------------------
# Fixtures
# ---------------------------------------------------------------------------

@pytest.fixture(scope="session")
def base_url():
    """Return the base URL of the SmartBiz frontend."""
    return BASE_URL


@pytest.fixture(scope="function")
def driver():
    """
    Create a fresh headless Chrome WebDriver for each test function.
    """
    chrome_options = Options()
    chrome_options.add_argument("--headless=new")
    chrome_options.add_argument("--no-sandbox")
    chrome_options.add_argument("--disable-dev-shm-usage")
    chrome_options.add_argument("--disable-gpu")
    chrome_options.add_argument("--window-size=412,915")
    chrome_options.add_argument("--disable-extensions")
    chrome_options.add_argument("--disable-infobars")
    chrome_options.add_argument("--ignore-certificate-errors")

    try:
        from webdriver_manager.chrome import ChromeDriverManager
        service = Service(ChromeDriverManager().install())
        drv = webdriver.Chrome(service=service, options=chrome_options)
    except Exception:
        drv = webdriver.Chrome(options=chrome_options)

    drv.set_page_load_timeout(60)
    drv.implicitly_wait(SHORT_TIMEOUT)

    log_event("INFO", "WebDriver session started")
    yield drv

    drv.quit()
    log_event("INFO", "WebDriver session closed")


@pytest.fixture(scope="function")
def logged_in_driver(driver, base_url):
    """
    Provide a WebDriver already logged in to SmartBiz.
    """
    log_event("INFO", "Logging in with test credentials...")

    driver.get(base_url + "#login")
    time.sleep(PAGE_LOAD_WAIT)

    try:
        wait = WebDriverWait(driver, DEFAULT_TIMEOUT)

        email_input = wait.until(
            EC.presence_of_element_located((By.ID, "email"))
        )
        email_input.clear()
        email_input.send_keys(TEST_EMAIL)

        password_input = driver.find_element(By.ID, "password")
        password_input.clear()
        password_input.send_keys(TEST_PASSWORD)

        submit_btn = driver.find_element(
            By.CSS_SELECTOR, '#login-form button[type="submit"]'
        )
        submit_btn.click()

        # Wait for backend (Render cold start can take ~30s)
        time.sleep(10)

        wait.until(
            lambda d: "#dashboard" in d.current_url or "#login" in d.current_url
        )

        if "#dashboard" in driver.current_url:
            log_event("INFO", "Login successful – dashboard loaded")
        else:
            log_event("WARNING", "Login may have failed – still on login page")

    except TimeoutException:
        log_event("ERROR", "Login timed out waiting for page elements")
    except Exception as e:
        log_event("ERROR", f"Login failed: {str(e)}")

    yield driver


# ---------------------------------------------------------------------------
# Hooks – screenshot on failure
# ---------------------------------------------------------------------------

@pytest.hookimpl(tryfirst=True, hookwrapper=True)
def pytest_runtest_makereport(item, call):
    """Capture screenshot when a test fails."""
    outcome = yield
    report = outcome.get_result()

    if report.when == "call" and report.failed:
        drv = item.funcargs.get("driver") or item.funcargs.get("logged_in_driver")
        if drv:
            screenshot_dir = os.path.join(
                os.path.dirname(__file__), "..", "screenshots"
            )
            os.makedirs(screenshot_dir, exist_ok=True)
            filename = f"{item.name}_{datetime.utcnow().strftime('%H%M%S')}.png"
            filepath = os.path.join(screenshot_dir, filename)
            try:
                drv.save_screenshot(filepath)
                log_event("INFO", f"Screenshot saved: {filename}")
            except Exception:
                log_event("WARNING", f"Failed to save screenshot for {item.name}")
