import { Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function DaybookPage() {
  const today = new Date().toISOString().split('T')[0];
  return `
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Daybook</h1>
        </div>
        <div class="header-right">
          <input type="date" id="daybook-date" class="input-sm" value="${today}" style="border:1px solid var(--border); border-radius:8px; padding:4px 8px; font-size:13px;">
        </div>
      </header>

      <main class="content-area">
        <!-- Summary Cards -->
        <div class="kpi-grid mb-lg" style="grid-template-columns: repeat(3, 1fr);">
          <div class="kpi-card" style="background: #e8f5e9; color: #1b5e20;">
            <div class="kpi-label">Sales</div>
            <div class="kpi-value text-lg" id="db-sales">₹ 0</div>
          </div>
          <div class="kpi-card" style="background: #fce4ec; color: #880e4f;">
            <div class="kpi-label">Expenses</div>
            <div class="kpi-value text-lg" id="db-expenses">₹ 0</div>
          </div>
          <div class="kpi-card" style="background: #e3f2fd; color: #0d47a1;">
            <div class="kpi-label">Net Profit</div>
            <div class="kpi-value text-lg" id="db-profit">₹ 0</div>
          </div>
        </div>

        <!-- Sales Section -->
        <div class="card mb-md">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title"><i class="ph ph-receipt text-success"></i> Sales Invoices</h3>
            <span class="chip" id="db-sales-count">0 invoices</span>
          </div>
          <div id="db-invoices-list">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>

        <!-- Expenses Section -->
        <div class="card mb-lg">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title"><i class="ph ph-arrows-out text-error"></i> Expenses</h3>
            <button class="btn btn-outline btn-sm" id="btn-add-expense"><i class="ph ph-plus"></i> Add</button>
          </div>
          <div id="db-expenses-list">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>
      </main>

      <!-- Add Expense Modal -->
      <div id="expense-modal" style="display:none; position:fixed; inset:0; background:rgba(0,0,0,0.4); z-index:100; display:flex; align-items:center; justify-content:center;">
        <div class="card" style="width:90%; max-width:400px; padding: 1.5rem;">
          <h3 class="section-title mb-md">Add Expense</h3>
          <div class="form-group mb-sm">
            <label>Description</label>
            <input id="exp-desc" type="text" class="input-full mt-xs" placeholder="e.g. Rent, Electricity...">
          </div>
          <div class="form-group mb-sm">
            <label>Amount (₹)</label>
            <input id="exp-amount" type="number" class="input-full mt-xs" placeholder="0.00">
          </div>
          <div class="form-group mb-md">
            <label>Category</label>
            <select id="exp-cat" class="input-full mt-xs">
              <option>Rent</option><option>Utilities</option><option>Staff Salary</option>
              <option>Purchase</option><option>Transport</option><option>Other</option>
            </select>
          </div>
          <div class="flex gap-sm">
            <button class="btn btn-outline flex-1" id="btn-cancel-expense">Cancel</button>
            <button class="btn btn-primary flex-1" id="btn-save-expense">Save</button>
          </div>
        </div>
      </div>

      ${Navigation('reports')}
    </div>
  `;
}

DaybookPage.init = async () => {
  let selectedDate = new Date().toISOString().split('T')[0];
  const modal = document.getElementById('expense-modal');
  modal.style.display = 'none';

  const loadData = async (date) => {
    try {
      const [invoices, expenses] = await Promise.all([api.getInvoices(), api.getExpenses()]);

      // Filter for selected date
      const dayInvoices = invoices.filter(inv => {
        const d = (inv.created_at || '').split('T')[0] || (inv.created_at || '').split(' ')[0];
        return d === date;
      });
      const dayExpenses = expenses.filter(exp => {
        const d = (exp.expense_date || '').split('T')[0] || (exp.expense_date || '').split(' ')[0];
        return d === date;
      });

      const totalSales = dayInvoices.reduce((s, inv) => s + (inv.grand_total || 0), 0);
      const totalExp = dayExpenses.reduce((s, exp) => s + (exp.amount || 0), 0);
      const profit = totalSales - totalExp;

      document.getElementById('db-sales').textContent = `₹ ${totalSales.toLocaleString('en-IN', {minimumFractionDigits:2})}`;
      document.getElementById('db-expenses').textContent = `₹ ${totalExp.toLocaleString('en-IN', {minimumFractionDigits:2})}`;
      document.getElementById('db-profit').textContent = `₹ ${profit.toLocaleString('en-IN', {minimumFractionDigits:2})}`;
      document.getElementById('db-profit').style.color = profit >= 0 ? '#1b5e20' : '#b71c1c';
      document.getElementById('db-sales-count').textContent = `${dayInvoices.length} invoice${dayInvoices.length !== 1 ? 's' : ''}`;

      // Render invoices
      const invList = document.getElementById('db-invoices-list');
      if (dayInvoices.length === 0) {
        invList.innerHTML = '<div class="text-secondary text-sm text-center p-md">No sales for this date.</div>';
      } else {
        invList.innerHTML = dayInvoices.map(inv => `
          <div class="flex justify-between items-center mb-sm" style="padding: 8px 0; border-bottom: 1px solid var(--border);">
            <div>
              <div class="font-medium text-sm">${inv.invoice_number || 'INV-' + inv.id}</div>
              <div class="text-xs text-secondary">Customer #${inv.customer_id}</div>
            </div>
            <div class="font-bold text-success">₹ ${(inv.grand_total || 0).toLocaleString('en-IN')}</div>
          </div>
        `).join('');
      }

      // Render expenses
      const expList = document.getElementById('db-expenses-list');
      if (dayExpenses.length === 0) {
        expList.innerHTML = '<div class="text-secondary text-sm text-center p-md">No expenses for this date.</div>';
      } else {
        expList.innerHTML = dayExpenses.map(exp => `
          <div class="flex justify-between items-center mb-sm" style="padding: 8px 0; border-bottom: 1px solid var(--border);">
            <div>
              <div class="font-medium text-sm">${exp.description || 'Expense'}</div>
              <div class="text-xs text-secondary">${exp.category || 'Other'} · ${exp.payment_mode || 'Cash'}</div>
            </div>
            <div class="font-bold text-error">- ₹ ${(exp.amount || 0).toLocaleString('en-IN')}</div>
          </div>
        `).join('');
      }
    } catch (err) {
      console.error('Daybook load error:', err);
    }
  };

  await loadData(selectedDate);

  document.getElementById('daybook-date')?.addEventListener('change', (e) => {
    selectedDate = e.target.value;
    loadData(selectedDate);
  });

  document.getElementById('btn-add-expense')?.addEventListener('click', () => {
    modal.style.display = 'flex';
  });
  document.getElementById('btn-cancel-expense')?.addEventListener('click', () => {
    modal.style.display = 'none';
  });
  document.getElementById('btn-save-expense')?.addEventListener('click', async () => {
    const desc = document.getElementById('exp-desc').value.trim();
    const amount = parseFloat(document.getElementById('exp-amount').value);
    const category = document.getElementById('exp-cat').value;
    if (!desc || isNaN(amount) || amount <= 0) return alert('Please fill all fields.');
    try {
      await api.addExpense({ description: desc, amount, category, payment_mode: 'Cash', expense_date: selectedDate, merchant_id: localStorage.getItem('merchant_id') || 1 });
      modal.style.display = 'none';
      await loadData(selectedDate);
    } catch (err) {
      alert('Failed to save: ' + err.message);
    }
  });
};
