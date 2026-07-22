import { Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function PnLReportPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">P&L Statement</h1>
        </div>
        <div class="header-right">
          <select id="pnl-period" class="input-sm" style="border:1px solid var(--border); border-radius:8px; padding:4px 8px; font-size:13px;">
            <option value="7">Last 7 Days</option>
            <option value="30" selected>Last 30 Days</option>
            <option value="90">Last 90 Days</option>
            <option value="365">Last 1 Year</option>
          </select>
        </div>
      </header>

      <main class="content-area">
        <!-- Net Profit banner -->
        <div class="card mb-lg" id="pnl-banner" style="background: linear-gradient(135deg, #1565c0, #283593); color: white; text-align:center; padding: 1.5rem;">
          <div style="font-size: 13px; opacity: 0.8; margin-bottom: 4px;">NET PROFIT / LOSS</div>
          <div style="font-size: 2rem; font-weight: 700;" id="pnl-net">₹ 0</div>
          <div style="font-size: 12px; opacity: 0.7; margin-top: 4px;" id="pnl-margin">Margin: 0%</div>
        </div>

        <!-- Revenue -->
        <div class="card mb-md">
          <h3 class="section-title mb-sm" style="color: var(--success);"><i class="ph ph-arrow-circle-up"></i> Revenue</h3>
          <div class="flex justify-between mb-xs">
            <span class="text-sm">Gross Sales</span>
            <span class="font-bold" id="pnl-gross-sales">₹ 0</span>
          </div>
          <div class="flex justify-between mb-xs">
            <span class="text-sm">GST Collected</span>
            <span class="font-medium text-secondary" id="pnl-gst-collected">₹ 0</span>
          </div>
          <hr style="margin: 8px 0; border-color: var(--border);">
          <div class="flex justify-between">
            <span class="font-bold">Total Revenue</span>
            <span class="font-bold text-success" id="pnl-total-revenue">₹ 0</span>
          </div>
        </div>

        <!-- Expenses -->
        <div class="card mb-md">
          <h3 class="section-title mb-sm" style="color: var(--error);"><i class="ph ph-arrow-circle-down"></i> Expenses</h3>
          <div id="pnl-expense-breakdown">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
          <hr style="margin: 8px 0; border-color: var(--border);">
          <div class="flex justify-between">
            <span class="font-bold">Total Expenses</span>
            <span class="font-bold text-error" id="pnl-total-expenses">₹ 0</span>
          </div>
        </div>

        <!-- Summary Table -->
        <div class="card mb-lg">
          <h3 class="section-title mb-sm"><i class="ph ph-table"></i> Summary</h3>
          <div class="flex justify-between mb-xs"><span class="text-sm">Total Invoices</span><span id="pnl-inv-count" class="font-medium">0</span></div>
          <div class="flex justify-between mb-xs"><span class="text-sm">Average Invoice Value</span><span id="pnl-avg-inv" class="font-medium">₹ 0</span></div>
          <div class="flex justify-between mb-xs"><span class="text-sm">Total Customers Billed</span><span id="pnl-cust-count" class="font-medium">0</span></div>
          <div class="flex justify-between"><span class="text-sm">Outstanding Udhar (Net)</span><span id="pnl-udhar" class="font-medium text-warning">₹ 0</span></div>
        </div>
      </main>

      ${Navigation('reports')}
    </div>
  `;
}

PnLReportPage.init = async () => {
  const loadPnL = async (days) => {
    try {
      const [invoices, expenses, ledger] = await Promise.all([
        api.getInvoices(),
        api.getExpenses(),
        fetch(`${import.meta.env.VITE_API_URL || 'http://localhost:5001/api'}/ledger/all`).then(r => r.ok ? r.json() : []).catch(() => [])
      ]);

      const cutoff = new Date();
      cutoff.setDate(cutoff.getDate() - days);

      const filteredInv = invoices.filter(inv => new Date(inv.created_at) >= cutoff);
      const filteredExp = expenses.filter(exp => new Date(exp.expense_date) >= cutoff);

      const grossSales = filteredInv.reduce((s, inv) => s + (inv.subtotal || 0), 0);
      const gstCollected = filteredInv.reduce((s, inv) => s + (inv.total_gst || 0), 0);
      const totalRevenue = filteredInv.reduce((s, inv) => s + (inv.grand_total || 0), 0);
      const totalExpenses = filteredExp.reduce((s, exp) => s + (exp.amount || 0), 0);
      const netProfit = totalRevenue - totalExpenses;
      const margin = totalRevenue > 0 ? ((netProfit / totalRevenue) * 100).toFixed(1) : 0;

      const fmt = (n) => `₹ ${n.toLocaleString('en-IN', {minimumFractionDigits:2})}`;

      document.getElementById('pnl-gross-sales').textContent = fmt(grossSales);
      document.getElementById('pnl-gst-collected').textContent = fmt(gstCollected);
      document.getElementById('pnl-total-revenue').textContent = fmt(totalRevenue);
      document.getElementById('pnl-total-expenses').textContent = fmt(totalExpenses);
      document.getElementById('pnl-net').textContent = fmt(Math.abs(netProfit));
      document.getElementById('pnl-margin').textContent = `Margin: ${margin}%`;
      document.getElementById('pnl-banner').style.background = netProfit >= 0
        ? 'linear-gradient(135deg, #2e7d32, #1b5e20)'
        : 'linear-gradient(135deg, #c62828, #b71c1c)';

      document.getElementById('pnl-inv-count').textContent = filteredInv.length;
      const avgInv = filteredInv.length > 0 ? totalRevenue / filteredInv.length : 0;
      document.getElementById('pnl-avg-inv').textContent = fmt(avgInv);
      const uniqueCust = new Set(filteredInv.map(i => i.customer_id)).size;
      document.getElementById('pnl-cust-count').textContent = uniqueCust;

      // Expense breakdown by category
      const cats = {};
      filteredExp.forEach(exp => {
        cats[exp.category || 'Other'] = (cats[exp.category || 'Other'] || 0) + exp.amount;
      });
      const breakdownEl = document.getElementById('pnl-expense-breakdown');
      if (Object.keys(cats).length === 0) {
        breakdownEl.innerHTML = '<div class="text-secondary text-sm text-center p-sm">No expenses in this period.</div>';
      } else {
        breakdownEl.innerHTML = Object.entries(cats).map(([cat, amount]) => `
          <div class="flex justify-between mb-xs">
            <span class="text-sm">${cat}</span>
            <span class="text-error font-medium">${fmt(amount)}</span>
          </div>
        `).join('');
      }

      // Udhar from dashboard stats
      try {
        const stats = await api.getDashboardStats();
        document.getElementById('pnl-udhar').textContent = fmt(stats.udharTotal || 0);
      } catch(e) {}

    } catch(err) {
      console.error('PnL load error:', err);
    }
  };

  await loadPnL(30);
  document.getElementById('pnl-period')?.addEventListener('change', (e) => loadPnL(parseInt(e.target.value)));
};
