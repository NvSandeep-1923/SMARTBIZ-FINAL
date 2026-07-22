import { Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function TaxReportPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Tax & GST Report</h1>
        </div>
        <div class="header-right">
          <select id="tax-period" class="input-sm" style="border:1px solid var(--border); border-radius:8px; padding:4px 8px; font-size:13px;">
            <option value="7">Last 7 Days</option>
            <option value="30" selected>This Month</option>
            <option value="90">Last Quarter</option>
            <option value="365">This Year</option>
          </select>
        </div>
      </header>

      <main class="content-area">
        <!-- GST Summary -->
        <div class="kpi-grid mb-lg" style="grid-template-columns: repeat(2, 1fr);">
          <div class="kpi-card" style="background: #fff3e0; color: #e65100;">
            <div class="kpi-label">Total GST Collected</div>
            <div class="kpi-value" id="tax-total-gst">₹ 0</div>
          </div>
          <div class="kpi-card" style="background: #f3e5f5; color: #6a1b9a;">
            <div class="kpi-label">Taxable Invoices</div>
            <div class="kpi-value" id="tax-inv-count">0</div>
          </div>
        </div>

        <!-- GST Slab Breakdown -->
        <div class="card mb-md">
          <h3 class="section-title mb-sm"><i class="ph ph-percent text-warning"></i> GST Slab Breakdown</h3>
          <div id="tax-slab-breakdown">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>

        <!-- Invoice List with GST -->
        <div class="card mb-lg">
          <h3 class="section-title mb-sm"><i class="ph ph-list-numbers"></i> Invoice GST Details</h3>
          <div style="display:grid; grid-template-columns:1fr 1fr 1fr; font-size:11px; font-weight:600; color:var(--text-secondary); padding: 0 0 6px; border-bottom: 1px solid var(--border);">
            <span>Invoice #</span><span>Taxable Amt</span><span>GST</span>
          </div>
          <div id="tax-invoice-list">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>

        <!-- GSTIN Block -->
        <div class="card mb-lg" style="background: #f9fbe7;">
          <div class="flex items-center gap-sm mb-sm">
            <i class="ph ph-building-office text-primary" style="font-size:20px;"></i>
            <h3 class="section-title">Business GSTIN Info</h3>
          </div>
          <div class="text-sm text-secondary">GSTIN: <span class="font-bold text-primary" id="gstin-val">—</span></div>
          <div class="text-xs text-secondary mt-xs">Tax Filing Period: Monthly (GSTR-1)</div>
        </div>
      </main>

      ${Navigation('reports')}
    </div>
  `;
}

TaxReportPage.init = async () => {
  const loadTax = async (days) => {
    try {
      const invoices = await api.getInvoices();
      const cutoff = new Date();
      cutoff.setDate(cutoff.getDate() - days);

      const filtered = invoices.filter(inv => new Date(inv.created_at) >= cutoff);
      const taxableInvoices = filtered.filter(inv => (inv.total_gst || 0) > 0);
      const totalGST = filtered.reduce((s, inv) => s + (inv.total_gst || 0), 0);

      document.getElementById('tax-total-gst').textContent = `₹ ${totalGST.toLocaleString('en-IN', {minimumFractionDigits:2})}`;
      document.getElementById('tax-inv-count').textContent = taxableInvoices.length;

      // Invoice list
      const listEl = document.getElementById('tax-invoice-list');
      if (filtered.length === 0) {
        listEl.innerHTML = '<div class="text-secondary text-sm text-center p-md">No invoices for this period.</div>';
      } else {
        listEl.innerHTML = filtered.map(inv => `
          <div style="display:grid; grid-template-columns:1fr 1fr 1fr; font-size:12px; padding: 6px 0; border-bottom: 1px solid var(--border);">
            <span class="font-medium">${inv.invoice_number || 'INV-'+inv.id}</span>
            <span>₹ ${(inv.subtotal || 0).toLocaleString('en-IN')}</span>
            <span class="text-warning font-bold">₹ ${(inv.total_gst || 0).toLocaleString('en-IN')}</span>
          </div>
        `).join('');
      }

      // GST slab breakdown (we show by gst rates from invoices)
      // Since we only store total gst per invoice and not per rate in invoices table, 
      // we'll approximate: show 0% (no gst), and gst-bearing invoices
      const noGst = filtered.filter(i => (i.total_gst || 0) === 0);
      const withGst = filtered.filter(i => (i.total_gst || 0) > 0);
      const gstAmt5 = withGst.reduce((s,i) => { const rate = i.total_gst / i.subtotal * 100; return rate < 8 ? s + i.total_gst : s; }, 0);
      const gstAmt12 = withGst.reduce((s,i) => { const rate = i.subtotal>0 ? i.total_gst / i.subtotal * 100 : 0; return rate >= 8 && rate < 15 ? s + i.total_gst : s; }, 0);
      const gstAmt18 = withGst.reduce((s,i) => { const rate = i.subtotal>0 ? i.total_gst / i.subtotal * 100 : 0; return rate >= 15 ? s + i.total_gst : s; }, 0);

      const slabEl = document.getElementById('tax-slab-breakdown');
      slabEl.innerHTML = `
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#f5f5f5; border-radius:6px;">
          <span class="text-sm"><span class="chip">0% GST</span> Zero-rated</span>
          <span class="font-medium">${noGst.length} invoices</span>
        </div>
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#fff8e1; border-radius:6px;">
          <span class="text-sm"><span class="chip" style="background:#fff3e0; color:#e65100">5% GST</span></span>
          <span class="font-bold text-warning">₹ ${gstAmt5.toLocaleString('en-IN', {minimumFractionDigits:2})}</span>
        </div>
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#fce4ec; border-radius:6px;">
          <span class="text-sm"><span class="chip" style="background:#fce4ec; color:#880e4f">12% GST</span></span>
          <span class="font-bold" style="color:#880e4f">₹ ${gstAmt12.toLocaleString('en-IN', {minimumFractionDigits:2})}</span>
        </div>
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#e8eaf6; border-radius:6px;">
          <span class="text-sm"><span class="chip" style="background:#e8eaf6; color:#283593">18% GST</span></span>
          <span class="font-bold" style="color:#283593">₹ ${gstAmt18.toLocaleString('en-IN', {minimumFractionDigits:2})}</span>
        </div>
      `;

      // GSTIN from business profile in localStorage
      const gstin = localStorage.getItem('gstin') || localStorage.getItem('merchant_gstin') || '—';
      document.getElementById('gstin-val').textContent = gstin;
    } catch(err) {
      console.error('Tax load error:', err);
    }
  };

  await loadTax(30);
  document.getElementById('tax-period')?.addEventListener('change', (e) => loadTax(parseInt(e.target.value)));
};
