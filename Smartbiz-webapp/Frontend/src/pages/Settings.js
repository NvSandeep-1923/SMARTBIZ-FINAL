import { Header, Navigation } from '../components/Navigation';
import { t } from '../utils/i18n';

export function SettingsPage() {
  const settingsItems = [
    { id: 'business_profile', label: 'Business Profile', icon: '<i class="ph ph-buildings"></i>', sub: 'Details, Logo, GSTIN' },
    { id: 'staff', label: 'Staff Management', icon: '<i class="ph ph-users"></i>', sub: 'Roles, Permissions' },
    { id: 'printer', label: 'Printer Settings', icon: '<i class="ph ph-printer"></i>', sub: 'Bluetooth, Thermal Printer' },
    { id: 'language', label: t('app_language'), icon: '<i class="ph ph-globe"></i>', sub: 'English, Hindi, Telugu, etc.' },
    { id: 'notifications', label: 'Notifications', icon: '<i class="ph ph-bell"></i>', sub: 'Alerts, Reminders' },
    { id: 'help', label: 'Help & Support', icon: '<i class="ph ph-question"></i>', sub: 'FAQs, Contact Us' },
  ];

  return `
    <div class="page-container">
      ${Header('Settings')}
      
      <main class="content-area">
        <div class="profile-summary card mb-lg">
          <div class="flex items-center gap-md">
            <div class="avatar-lg"><i class="ph ph-user" style="font-size: 24px;"></i></div>
            <div>
              <div class="font-bold text-lg">Rajesh Kumar</div>
              <div class="text-sm text-secondary">SmartBiz Pro Member</div>
            </div>
          </div>
        </div>
        
        <div class="settings-list">
          ${settingsItems.map(item => `
            <a href="#settings/${item.id}" class="settings-item card mb-sm">
              <div class="flex items-center gap-md">
                <div class="settings-icon-bg">${item.icon}</div>
                <div class="flex-1">
                  <div class="font-medium">${item.label}</div>
                  <div class="text-xs text-secondary">${item.sub}</div>
                </div>
                <div class="text-secondary"><i class="ph ph-caret-right"></i></div>
              </div>
            </a>
          `).join('')}
        </div>
        
        <div class="mt-xl text-center">
          <button class="btn btn-outline text-error w-full">${t('logout')}</button>
          <div class="text-xs text-secondary mt-md">SmartBiz v2.4.0 (Stable)</div>
        </div>
      </main>
      
      ${Navigation('settings')}
    </div>
  `;
}

SettingsPage.init = () => {
  const logoutBtn = document.querySelector('.btn-outline.text-error');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      window.location.hash = '#login';
    });
  }
};
