import { Header, Navigation } from '../components/Navigation';

export function PrinterSettingsPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">Printer Settings</h1>
      </header>
      
      <main class="content-area">
        <section class="card mb-md">
          <div class="flex justify-between items-center">
            <div class="flex items-center gap-md">
              <div class="icon-circle bg-blue">🛜</div>
              <div>
                <div class="font-bold">Bluetooth Printing</div>
                <div class="text-xs text-secondary">Connect to thermal printer</div>
              </div>
            </div>
            <label class="switch">
              <input type="checkbox" checked>
              <span class="slider round"></span>
            </label>
          </div>
        </section>
        
        <section class="card mb-md">
          <h3 class="section-title mb-sm">Available Printers</h3>
          <div class="device-list">
            <div class="device-item flex justify-between items-center py-sm border-b">
              <div class="flex items-center gap-md">
                <span>🖨️</span>
                <div class="text-sm">TVS RP-3200 (Connected)</div>
              </div>
              <div class="text-success">●</div>
            </div>
            <div class="device-item flex justify-between items-center py-sm">
              <div class="flex items-center gap-md">
                <span>🖨️</span>
                <div class="text-sm">EPSON TM-T88V</div>
              </div>
              <button class="btn-text">Connect</button>
            </div>
          </div>
        </section>
        
        <section class="card mb-lg">
          <h3 class="section-title mb-sm">Print Configuration</h3>
          <div class="form-group mb-md">
            <label>Paper Size</label>
            <select class="input-full">
              <option>2 Inch (58mm)</option>
              <option>3 Inch (80mm)</option>
              <option>A4 Size</option>
            </select>
          </div>
          <div class="form-group mb-md">
            <label>Copies</label>
            <input type="number" value="1" class="input-full">
          </div>
          <label class="checkbox-container">
            <input type="checkbox" checked>
            <span class="text-sm">Print Logo on Invoice</span>
          </label>
        </section>
        
        <button class="btn btn-outline w-full mb-md">🖨️ Test Print</button>
      </main>
      ${Navigation('settings')}
    </div>
  `;
}

PrinterSettingsPage.init = () => {
  const testPrintBtn = document.querySelector('.btn-outline.w-full');
  if (testPrintBtn) {
    testPrintBtn.addEventListener('click', () => {
      testPrintBtn.textContent = '⌛ Printing...';
      testPrintBtn.disabled = true;
      
      setTimeout(() => {
        alert('Test print successful! Check your TVS RP-3200 printer.');
        testPrintBtn.textContent = '🖨️ Test Print';
        testPrintBtn.disabled = false;
      }, 1500);
    });
  }
};
