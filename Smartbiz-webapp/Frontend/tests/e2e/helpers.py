"""
SmartBiz E2E Test Helpers
=========================
Shared utility functions and constants used across all test files.
These are importable (unlike conftest.py which is pytest-internal).
"""

import os
import time
import json
import logging
from datetime import datetime
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# ---------------------------------------------------------------------------
# Constants
# ---------------------------------------------------------------------------
BASE_URL = os.environ.get(
    "SMARTBIZ_BASE_URL",
    "https://thegitguy-56.github.io/Smartbiz-frontend/",
)
API_URL = os.environ.get(
    "SMARTBIZ_API_URL",
    "https://smartbiz-backend-c7ru.onrender.com/api",
)
TEST_EMAIL = "admin@smartbiz.com"
TEST_PASSWORD = "1234"

DEFAULT_TIMEOUT = 30
SHORT_TIMEOUT = 10
PAGE_LOAD_WAIT = 5

# ---------------------------------------------------------------------------
# Logging
# ---------------------------------------------------------------------------
LOG_FILE = os.path.join(os.path.dirname(__file__), "..", "execution_log.jsonl")

logger = logging.getLogger("smartbiz_e2e")
if not logger.handlers:
    logger.setLevel(logging.DEBUG)
    _fh = logging.FileHandler(LOG_FILE, mode="a", encoding="utf-8")
    _fh.setLevel(logging.DEBUG)
    _fh.setFormatter(logging.Formatter("%(message)s"))
    logger.addHandler(_fh)
    _ch = logging.StreamHandler()
    _ch.setLevel(logging.INFO)
    _ch.setFormatter(logging.Formatter("[%(levelname)s] %(message)s"))
    logger.addHandler(_ch)


def log_event(level: str, message: str):
    """Write a structured JSON log line for the Execution Log sheet."""
    entry = {
        "timestamp": datetime.utcnow().isoformat() + "Z",
        "level": level,
        "message": message,
    }
    logger.info(json.dumps(entry))


# ---------------------------------------------------------------------------
# Selenium helpers
# ---------------------------------------------------------------------------

def wait_for_element(driver, by, value, timeout=DEFAULT_TIMEOUT):
    """Wait for an element to be present and return it."""
    return WebDriverWait(driver, timeout).until(
        EC.presence_of_element_located((by, value))
    )


def wait_for_visible(driver, by, value, timeout=DEFAULT_TIMEOUT):
    """Wait for an element to be visible and return it."""
    return WebDriverWait(driver, timeout).until(
        EC.visibility_of_element_located((by, value))
    )


def wait_for_clickable(driver, by, value, timeout=DEFAULT_TIMEOUT):
    """Wait for an element to be clickable and return it."""
    return WebDriverWait(driver, timeout).until(
        EC.element_to_be_clickable((by, value))
    )


def get_page_text(driver):
    """Return the full visible text of the page body."""
    try:
        body = driver.find_element(By.TAG_NAME, "body")
        return body.text
    except Exception:
        return ""


def navigate_to(driver, base_url, hash_path):
    """Navigate to a hash route and wait for content to load."""
    driver.get(base_url + hash_path)
    time.sleep(PAGE_LOAD_WAIT)
