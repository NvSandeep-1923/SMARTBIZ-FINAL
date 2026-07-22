import { Header, Navigation } from '../components/Navigation';
import { t } from '../utils/i18n';
import { api } from '../utils/api';

export function DashboardPage() {
  return `
    <div class="page-container">
      ${Header('Dashboard')}
      
      <main class="content-area">
        <section class="kpi-grid">
          <div class="kpi-card" style="background: var(--primary); color: white;">
            <div class="flex justify-between">
              <span class="kpi-label">${t('total_sales')}</span>
              <span class="kpi-icon"><i class="ph ph-chart-line-up"></i></span>
            </div>
            <div class="kpi-value" id="kpi-total-sales">₹ 0</div>
            <div class="kpi-trend">Today</div>
          </div>
          
          <div class="kpi-card" style="background: #FFE0B2; color: #E65100;">
            <div class="flex justify-between">
              <span class="kpi-label">${t('total_udhar')}</span>
              <span class="kpi-icon"><i class="ph ph-briefcase"></i></span>
            </div>
            <div class="kpi-value" id="kpi-total-udhar">₹ 0</div>
          </div>
          
          <div class="kpi-card" style="background: #FFCDD2; color: #B71C1C;">
            <div class="flex justify-between">
              <span class="kpi-label">${t('stock_alerts')}</span>
              <span class="kpi-icon"><i class="ph ph-warning-circle"></i></span>
            </div>
            <div class="kpi-value" id="kpi-stock-alerts">0 Items Low</div>
          </div>
          
          <div class="kpi-card" style="background: #C8E6C9; color: #1B5E20;">
            <div class="flex justify-between">
              <span class="kpi-label">${t('cash_in_hand')}</span>
              <span class="kpi-icon"><i class="ph ph-money"></i></span>
            </div>
            <div class="kpi-value" id="kpi-cash-hand">₹ 0</div>
          </div>
        </section>
        
        <section class="quick-actions mt-lg">
          <h3 class="section-title">${t('quick_actions')}</h3>
          <div class="actions-grid mt-sm">
            <button class="action-btn" id="btn-add-customer">
              <span class="action-icon"><i class="ph ph-user-plus"></i></span>
              <span>${t('add_customer')}</span>
            </button>
            <button class="action-btn" id="btn-new-invoice">
              <span class="action-icon"><i class="ph ph-file-text"></i></span>
              <span>${t('create_invoice')}</span>
            </button>
            <button class="action-btn" id="btn-add-stock">
              <span class="action-icon"><i class="ph ph-package"></i></span>
              <span>${t('add_stock')}</span>
            </button>
            <button class="action-btn" id="btn-check-reports">
              <span class="action-icon"><i class="ph ph-chart-bar"></i></span>
              <span>${t('reports')}</span>
            </button>
          </div>
        </section>
        
        <section class="charts-section mt-lg">
          <div class="card">
            <div class="flex justify-between items-center mb-md">
              <h3 class="section-title">Weekly Sales Trend</h3>
              <select class="select-sm">
                <option>Last 7 Days</option>
              </select>
            </div>
            <div class="chart-placeholder">
              <!-- Placeholder for Chart.js or CSS-based chart -->
              <div class="bar-chart">
                <div class="bar" style="height: 40%"></div>
                <div class="bar" style="height: 60%"></div>
                <div class="bar" style="height: 35%"></div>
                <div class="bar" style="height: 80%"></div>
                <div class="bar" style="height: 55%"></div>
                <div class="bar" style="height: 90%"></div>
                <div class="bar" style="height: 70%"></div>
              </div>
              <div class="chart-labels">
                <span>Mon</span><span>Tue</span><span>Wed</span><span>Thu</span><span>Fri</span><span>Sat</span><span>Sun</span>
              </div>
            </div>
          </div>
        </section>
        
        <section class="recent-transactions mt-lg mb-xxl">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title">Recent Transactions</h3>
            <a href="#customers/ledger" class="text-sm">View All</a>
          </div>
          <div class="transaction-list" id="dashboard-recent-tx">
            <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading...</div>
          </div>
        </section>
      </main>
      
      ${Navigation('dashboard')}
    </div>
  `;
}

DashboardPage.init = async () => {
  document.getElementById('btn-add-customer')?.addEventListener('click', () => {
    window.location.hash = '#customers';
  });
  document.getElementById('btn-new-invoice')?.addEventListener('click', () => {
    window.location.hash = '#billing';
  });
  document.getElementById('btn-add-stock')?.addEventListener('click', () => {
    window.location.hash = '#inventory/add';
  });
  document.getElementById('btn-check-reports')?.addEventListener('click', () => {
    window.location.hash = '#reports';
  });

  try {
    const stats = await api.getDashboardStats();
    
    document.getElementById('kpi-total-sales').textContent = `₹ ${stats.salesToday.toLocaleString()}`;
    document.getElementById('kpi-total-udhar').textContent = `₹ ${stats.udharTotal.toLocaleString()}`;
    document.getElementById('kpi-stock-alerts').textContent = `${stats.lowStockCount} Items Low`;
    // For cash in hand, we could calculate sales - expenses, or similar. We'll set a basic placeholder if not defined
    document.getElementById('kpi-cash-hand').textContent = `₹ ${(stats.salesToday).toLocaleString()}`;

    const txContainer = document.getElementById('dashboard-recent-tx');
    if (stats.recentTransactions && stats.recentTransactions.length > 0) {
      txContainer.innerHTML = stats.recentTransactions.map(tx => `
        <div class="transaction-item">
          <div class="tx-icon">${tx.type === 'got' ? '<i class="ph ph-trend-up text-success"></i>' : '<i class="ph ph-trend-down text-error"></i>'}</div>
          <div class="tx-details">
            <div class="tx-name">Ledger ID: ${tx.customer_id}</div>
            <div class="tx-time">${new Date(tx.tx_date).toLocaleString()}</div>
          </div>
          <div class="tx-amount ${tx.type === 'got' ? 'plus' : 'minus'}">
            ${tx.type === 'got' ? '+' : '-'} ₹ ${tx.amount.toLocaleString()}
          </div>
        </div>
      `).join('');
    } else {
      txContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No recent transactions.</div>';
    }
  } catch (error) {
    console.error('Failed to load dashboard stats:', error);
  }
};
