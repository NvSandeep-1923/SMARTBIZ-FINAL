import { Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function NotificationsPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Notifications</h1>
        </div>
        <button class="btn-text text-sm" id="btn-mark-all">Mark all read</button>
      </header>

      <main class="content-area">
        <!-- System Alerts -->
        <div id="notif-stock-alerts"></div>
        <div id="notif-udhar-alerts"></div>

        <!-- Static Activity Notifications -->
        <div class="card mb-sm notif-item" data-read="false" style="border-left: 4px solid var(--primary);">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background: #e3f2fd; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-chart-line-up text-primary"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Sales Report Ready</div>
              <div class="text-xs text-secondary">Your monthly sales report is available in Reports &rarr; P&L Statement.</div>
              <div class="text-xs text-secondary mt-xs">Today, 9:00 AM</div>
            </div>
            <div class="unread-dot" style="width:8px;height:8px;border-radius:50%;background:var(--primary);"></div>
          </div>
        </div>

        <div class="card mb-sm notif-item" data-read="false" style="border-left: 4px solid var(--success);">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background: #e8f5e9; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-users text-success"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Customer Added</div>
              <div class="text-xs text-secondary">A new customer was registered in your store.</div>
              <div class="text-xs text-secondary mt-xs">Yesterday, 3:45 PM</div>
            </div>
          </div>
        </div>

        <div class="card mb-sm notif-item" data-read="true" style="opacity:0.65;">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background: #fce4ec; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-receipt text-error"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Invoice Created</div>
              <div class="text-xs text-secondary">Invoice INV-001 was created successfully.</div>
              <div class="text-xs text-secondary mt-xs">2 days ago</div>
            </div>
          </div>
        </div>

        <!-- Dynamic Low Stock Section -->
        <h3 class="section-title mt-lg mb-sm"><i class="ph ph-warning text-warning"></i> Live Alerts</h3>
        <div id="notif-live-alerts">
          <div class="text-secondary text-sm text-center p-md">Loading live alerts...</div>
        </div>
      </main>

      ${Navigation('')}
    </div>
  `;
}

NotificationsPage.init = async () => {
  document.getElementById('btn-mark-all')?.addEventListener('click', () => {
    document.querySelectorAll('.notif-item').forEach(el => {
      el.style.opacity = '0.65';
      el.style.borderLeft = '';
      const dot = el.querySelector('.unread-dot');
      if (dot) dot.remove();
    });
  });

  try {
    const [inventory, stats] = await Promise.all([
      api.getInventory(),
      api.getDashboardStats()
    ]);

    const liveEl = document.getElementById('notif-live-alerts');
    const lowStock = inventory.filter(i => i.stock_level < 10);
    const udharNet = stats.udharTotal || 0;

    let alertsHTML = '';

    if (udharNet > 0) {
      alertsHTML += `
        <div class="card mb-sm" style="border-left: 4px solid var(--warning);">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background:#fff3e0; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-hand-coins" style="color:#e65100;"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Pending Udhar Balance</div>
              <div class="text-xs text-secondary">Net outstanding credit: ₹ ${udharNet.toLocaleString('en-IN')}. Review customer ledgers.</div>
            </div>
          </div>
        </div>
      `;
    }

    if (lowStock.length > 0) {
      lowStock.slice(0, 3).forEach(item => {
        alertsHTML += `
          <div class="card mb-sm" style="border-left: 4px solid var(--error);">
            <div class="flex items-center gap-sm">
              <div style="width:36px; height:36px; border-radius:50%; background:#fce4ec; display:flex; align-items:center; justify-content:center;">
                <i class="ph ph-package text-error"></i>
              </div>
              <div class="flex-1">
                <div class="font-medium text-sm">Low Stock: ${item.item_name}</div>
                <div class="text-xs text-secondary">Only ${item.stock_level} units remaining. Restock soon!</div>
              </div>
            </div>
          </div>
        `;
      });
    }

    if (!alertsHTML) {
      alertsHTML = '<div class="card text-center p-md"><i class="ph ph-check-circle text-success" style="font-size:28px;"></i><div class="text-sm mt-sm text-secondary">All clear! No critical alerts.</div></div>';
    }

    liveEl.innerHTML = alertsHTML;
  } catch(err) {
    console.error('Notifications load error:', err);
  }
};
