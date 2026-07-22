import { Header, Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function ReportsPage() {
  return `
    <div class="page-container">
      ${Header('Insights & Reports')}
      
      <main class="content-area">
        <section class="ai-insights card mb-lg" style="background: linear-gradient(135deg, var(--primary), #303f9f); color: white;">
          <div class="flex items-center gap-sm mb-sm">
            <span class="ai-icon"><i class="ph ph-sparkle"></i></span>
            <h3 class="section-title">Data Insights</h3>
          </div>
          <p class="text-sm opacity-90 mb-md" id="ai-insight-text">Loading insights from server...</p>
          <button class="btn btn-secondary w-full" id="btn-reports-action">Create Order</button>
        </section>
        
        <section class="business-reports">
          <h3 class="section-title mb-md">Business Reports</h3>
          <div class="reports-grid">
            <a href="#reports/daybook" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-primary"><i class="ph ph-calendar-blank" style="font-size: 24px;"></i></span>
              <span class="report-name">Daybook</span>
            </a>
            <a href="#reports/pnl" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-success"><i class="ph ph-trend-up" style="font-size: 24px;"></i></span>
              <span class="report-name">P&L Statement</span>
            </a>
            <a href="#reports/tax" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-warning"><i class="ph ph-receipt" style="font-size: 24px;"></i></span>
              <span class="report-name">Tax & GST</span>
            </a>
            <a href="#reports/forecast" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-error"><i class="ph ph-trend-down" style="font-size: 24px;"></i></span>
              <span class="report-name">Sales Forecast</span>
            </a>
          </div>
        </section>
        
        <section class="credit-risk mt-lg">
          <div class="card">
            <h3 class="section-title mb-sm">Customer Credit Risk</h3>
            <div class="risk-summary flex items-center gap-md">
              <div class="risk-gauge">84%</div>
              <div class="text-xs text-secondary">OVERALL PORTFOLIO HEALTH</div>
            </div>
            <div class="mt-md" id="reports-customers">
              <div class="text-xs text-secondary">Loading portfolio health...</div>
            </div>
          </div>
        </section>
      </main>
      
      ${Navigation('reports')}
    </div>
  `;
}

ReportsPage.init = async () => {
  document.getElementById('btn-reports-action')?.addEventListener('click', () => {
    window.location.hash = '#inventory/add';
  });

  try {
    const invoices = await api.getInvoices();
    const insightText = document.getElementById('ai-insight-text');
    if (insightText && invoices && invoices.length) {
      const latest = invoices[0];
      insightText.textContent = `You have ${invoices.length} total invoices on record. Latest invoice amount: ₹${latest.grand_total}.`;
    }

    const customers = await api.getCustomers();
    const customersContainer = document.getElementById('reports-customers');
    if (customersContainer && customers && customers.length) {
       customersContainer.innerHTML = customers.slice(0,3).map(c => `
         <div class="flex justify-between items-center mb-sm">
           <span class="text-sm">${c.name}</span>
           <span class="risk-badge low-risk">Low Risk</span>
         </div>
       `).join('');
    } else if (customersContainer) {
       customersContainer.innerHTML = '<div class="text-xs text-secondary">No customers found</div>';
    }
  } catch(error) {
    console.error('Failed to load reports data', error);
  }
};
