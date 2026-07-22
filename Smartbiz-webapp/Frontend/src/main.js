import './styles/base.css';
import './styles/components.css';
import './styles/auth.css';
import './styles/dashboard.css';
import './styles/inventory.css';
import './styles/billing.css';
import './styles/customers.css';
import './styles/reports.css';
import './styles/settings.css';
import './styles/scanner.css';
import './styles/results.css';
import './styles/onboarding.css';

import { Navigation } from './components/Navigation';
import { LoginPage, RegisterPage } from './pages/Auth';
import { OTPPage } from './pages/OTP';
import { DashboardPage } from './pages/Dashboard';
import { InventoryPage } from './pages/Inventory';
import { AddProductPage } from './pages/AddProduct';
import { BillingPage } from './pages/Billing';
import { CustomersPage } from './pages/Customers';
import { ReportsPage } from './pages/Reports';
import { SettingsPage } from './pages/Settings';
import { BusinessProfilePage } from './pages/BusinessProfile';
import { PrinterSettingsPage } from './pages/PrinterSettings';
import { StaffManagementPage } from './pages/StaffManagement';
import { ScannerPage } from './pages/Scanner';
import { PaymentSuccessPage } from './pages/PaymentSuccess';
import { CustomerLedgerPage } from './pages/CustomerLedger';
import { OnboardingPage } from './pages/Onboarding';
import { SalesForecastPage } from './pages/SalesForecast';
import { LanguageSettingsPage } from './pages/LanguageSettings';
import { DaybookPage } from './pages/Daybook';
import { PnLReportPage } from './pages/PnLReport';
import { TaxReportPage } from './pages/TaxReport';
import { NotificationsPage } from './pages/Notifications';
import { HelpSupportPage } from './pages/HelpSupport';

// Mock other pages for now
const MockPage = (title) => `
  <div class="page-container">
    <header class="app-header">
      <div class="flex items-center gap-sm">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">${title}</h1>
      </div>
    </header>
    <main class="content-area">
      <div class="card mt-lg text-center p-xl">
        <i class="ph ph-hammer" style="font-size: 48px; color: var(--primary)"></i>
        <h2 class="mt-md">${title}</h2>
        <p class="text-secondary mt-sm">This feature is currently under active development. Check back soon!</p>
      </div>
    </main>
    ${Navigation('')}
  </div>
`;

const routes = {
  '': OnboardingPage,
  '#onboarding': OnboardingPage,
  '#login': LoginPage,
  '#register': RegisterPage,
  '#otp': OTPPage,
  '#dashboard': DashboardPage,
  '#customers': CustomersPage,
  '#billing': BillingPage,
  '#inventory': InventoryPage,
  '#inventory/add': AddProductPage,
  '#reports': ReportsPage,
  '#reports/daybook': DaybookPage,
  '#reports/pnl': PnLReportPage,
  '#reports/tax': TaxReportPage,
  '#reports/forecast': SalesForecastPage,
  '#settings': SettingsPage,
  '#settings/business_profile': BusinessProfilePage,
  '#settings/printer': PrinterSettingsPage,
  '#settings/staff': StaffManagementPage,
  '#settings/notifications': NotificationsPage,
  '#settings/help': HelpSupportPage,
  '#scanner': ScannerPage,
  '#payment-success': PaymentSuccessPage,
  '#customers/ledger': CustomerLedgerPage,
  '#settings/language': LanguageSettingsPage,
};

function router() {
  const app = document.querySelector('#app');
  const hash = window.location.hash || '';
  
  console.log('Routing to:', hash);

  let page = routes[hash];
  
  if (!page) {
    const routeKey = Object.keys(routes).find(key => {
      if (key.includes(':') && hash.startsWith(key.split(':')[0])) return true;
      if (key !== '' && hash.startsWith(key)) return true;
      return false;
    });
    page = routes[routeKey] || routes[''];
  }
  
  if (!page) page = routes[''];

  app.innerHTML = page();
  
  if (page.init) {
    page.init();
  }

  // Basic navigation active state handling
  const navItems = document.querySelectorAll('.nav-item');
  navItems.forEach(item => {
    const itemHash = item.getAttribute('href');
    if (itemHash && (hash === itemHash || (hash.startsWith(itemHash) && itemHash !== '#'))) {
      item.classList.add('active');
    } else {
      item.classList.remove('active');
    }
  });
}

window.addEventListener('hashchange', router);
window.addEventListener('load', router);
