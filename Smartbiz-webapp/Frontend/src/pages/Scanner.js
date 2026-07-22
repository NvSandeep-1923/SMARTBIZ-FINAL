export function ScannerPage() {
  return `
    <div class="scanner-container">
      <header class="app-header-transparent">
        <button class="icon-btn-white" onclick="window.history.back()">✕</button>
        <div class="scanner-title">Scan Barcode</div>
        <button class="icon-btn-white">🔦</button>
      </header>
      
      <div class="scanner-viewport">
        <div class="scanner-frame">
          <div class="scan-line"></div>
        </div>
        <div class="scanner-overlay top"></div>
        <div class="scanner-overlay bottom">
          <p class="scan-instruction">Align barcode within the frame to scan</p>
          <div class="manual-entry mt-xl">
            <button class="btn btn-outline-white">Enter Barcode Manually</button>
          </div>
        </div>
        <div class="scanner-overlay left"></div>
        <div class="scanner-overlay right"></div>
      </div>
      
      <div class="scanner-footer">
        <div class="recent-scans card">
          <div class="flex items-center gap-md">
            <span>📦</span>
            <div>
              <div class="font-bold">Organic Whole Milk 1L</div>
              <div class="text-xs text-secondary">SKU: DAIRY-WH-001</div>
            </div>
            <button class="btn btn-primary btn-sm ml-auto">Add</button>
          </div>
        </div>
      </div>
    </div>
  `;
}

ScannerPage.init = () => {
  const recentScan = document.querySelector('.recent-scans');
  const scanLine = document.querySelector('.scan-line');
  
  if (recentScan) recentScan.style.display = 'none';

  // Simulate scanning after 2 seconds
  setTimeout(() => {
    if (recentScan) {
      recentScan.style.display = 'block';
      recentScan.classList.add('animate-slide-up');
    }
    if (scanLine) scanLine.style.animation = 'none';
  }, 2000);

  const addBtn = document.querySelector('.recent-scans .btn-primary');
  if (addBtn) {
    addBtn.addEventListener('click', () => {
      // Simulate adding to cart/billing
      window.location.hash = '#billing';
    });
  }
};
