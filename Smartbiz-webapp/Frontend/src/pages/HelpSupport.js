import { Navigation } from '../components/Navigation';

export function HelpSupportPage() {
  const faqs = [
    { q: 'How do I add a new product?', a: 'Go to Inventory → Tap the + button → Fill in product details and save.' },
    { q: 'How do I create an invoice?', a: 'Go to Billing → Select a customer → Add items from your inventory → Tap Save & Preview.' },
    { q: 'How do I track customer credit (Udhar)?', a: 'Go to Customers → Select a customer → Use "You Gave" or "You Got" buttons to record transactions.' },
    { q: 'How do I view my P&L report?', a: 'Go to Reports → Tap P&L Statement → Choose a time period to see profit and loss data.' },
    { q: 'How do I add an expense?', a: 'Go to Reports → Daybook → Select a date → Tap "Add" in the Expenses section.' },
    { q: 'How do I change the app language?', a: 'Go to Settings → Language → Choose your preferred language from the list.' },
    { q: 'Can I connect a thermal printer?', a: 'Go to Settings → Printer Settings → Enable Bluetooth and pair your supported thermal printer.' },
  ];

  return `
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Help & Support</h1>
        </div>
      </header>

      <main class="content-area">
        <!-- Search -->
        <div class="search-bar mb-lg">
          <input type="text" class="search-input" id="faq-search" placeholder="Search help topics...">
        </div>

        <!-- Quick actions -->
        <div class="kpi-grid mb-lg" style="grid-template-columns: repeat(2, 1fr);">
          <a href="mailto:support@smartbiz.app" class="kpi-card text-center" style="text-decoration:none; background:#e3f2fd; color:#0d47a1;">
            <i class="ph ph-envelope" style="font-size:24px; margin-bottom:6px;"></i>
            <div class="text-sm font-medium">Email Support</div>
            <div class="text-xs" style="opacity:0.7;">support@smartbiz.app</div>
          </a>
          <a href="tel:+918001234567" class="kpi-card text-center" style="text-decoration:none; background:#e8f5e9; color:#1b5e20;">
            <i class="ph ph-phone" style="font-size:24px; margin-bottom:6px;"></i>
            <div class="text-sm font-medium">Call Support</div>
            <div class="text-xs" style="opacity:0.7;">+91 800 123 4567</div>
          </a>
        </div>

        <!-- App Version -->
        <div class="card mb-lg" style="background:#fafafa;">
          <div class="flex justify-between items-center">
            <div>
              <div class="font-bold">SmartBiz</div>
              <div class="text-xs text-secondary">Version 1.0.0 · Build 2026</div>
            </div>
            <i class="ph ph-info text-secondary" style="font-size:20px;"></i>
          </div>
        </div>

        <!-- FAQs -->
        <h3 class="section-title mb-md"><i class="ph ph-question"></i> Frequently Asked Questions</h3>
        <div id="faq-list">
          ${faqs.map((faq, i) => `
            <div class="card mb-sm faq-item">
              <button class="w-full text-left" onclick="this.parentElement.classList.toggle('open')" style="background:none; border:none; cursor:pointer; padding:0;">
                <div class="flex justify-between items-center">
                  <span class="font-medium text-sm">${faq.q}</span>
                  <i class="ph ph-caret-down text-secondary faq-caret" style="transition:transform 0.2s;"></i>
                </div>
              </button>
              <div class="faq-answer" style="display:none; margin-top:8px; font-size:13px; color:var(--text-secondary); line-height:1.6;">
                ${faq.a}
              </div>
            </div>
          `).join('')}
        </div>

        <!-- Contact -->
        <div class="card mt-lg mb-xl" style="background: linear-gradient(135deg, var(--primary), #303f9f); color:white; text-align:center; padding:1.5rem;">
          <i class="ph ph-chat-circle-dots" style="font-size:32px; margin-bottom:8px;"></i>
          <h3 style="margin-bottom:6px;">Still need help?</h3>
          <p style="font-size:13px; opacity:0.85; margin-bottom:16px;">Our support team is available Mon–Sat, 9 AM to 6 PM.</p>
          <a href="mailto:support@smartbiz.app" class="btn" style="background:white; color:var(--primary); width:100%; display:block; text-decoration:none; text-align:center;">
            <i class="ph ph-envelope"></i> Contact Us
          </a>
        </div>
      </main>

      ${Navigation('')}
    </div>
  `;
}

HelpSupportPage.init = () => {
  // FAQ accordion
  document.querySelectorAll('.faq-item button').forEach(btn => {
    btn.addEventListener('click', () => {
      const item = btn.parentElement;
      const answer = item.querySelector('.faq-answer');
      const caret = item.querySelector('.faq-caret');
      const isOpen = item.classList.contains('open');
      answer.style.display = isOpen ? 'none' : 'block';
      caret.style.transform = isOpen ? '' : 'rotate(180deg)';
    });
  });

  // FAQ search
  document.getElementById('faq-search')?.addEventListener('input', (e) => {
    const term = e.target.value.toLowerCase();
    document.querySelectorAll('.faq-item').forEach(item => {
      const q = item.querySelector('.font-medium').textContent.toLowerCase();
      item.style.display = q.includes(term) ? '' : 'none';
    });
  });
};
