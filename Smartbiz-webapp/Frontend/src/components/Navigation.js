import { t } from '../utils/i18n';

export function Navigation(activePath) {
  const navItems = [
    { id: 'dashboard', label: t('dashboard'), icon: '<i class="ph ph-house"></i>', path: '#dashboard' },
    { id: 'customers', label: t('customers'), icon: '<i class="ph ph-users"></i>', path: '#customers' },
    { id: 'billing', label: t('billing'), icon: '<i class="ph ph-receipt"></i>', path: '#billing' },
    { id: 'inventory', label: t('inventory'), icon: '<i class="ph ph-package"></i>', path: '#inventory' },
    { id: 'reports', label: t('reports'), icon: '<i class="ph ph-chart-bar"></i>', path: '#reports' },
  ];

  return `
    <nav class="bottom-nav">
      ${navItems.map(item => {
        const hash = window.location.hash;
        const isActive = activePath === item.id || (hash.startsWith(item.path) && item.path !== '#');
        return `
          <a href="${item.path}" class="nav-item ${isActive ? 'active' : ''}" id="nav-${item.id}">
            <span class="nav-icon">${item.icon}</span>
            <span class="nav-label">${item.label}</span>
          </a>
        `;
      }).join('')}
    </nav>
  `;
}

export function Header(title) {
  // Translate common page titles
  const translatedTitle = t(title.toLowerCase().replace(' ', '_')) || title;
  
  return `
    <header class="app-header">
      <div class="header-left">
        <h1 class="header-title">${translatedTitle}</h1>
      </div>
      <div class="header-right">
        <button class="icon-btn" onclick="window.location.hash='#settings/notifications'"><i class="ph ph-bell" style="font-size: 20px;"></i></button>
        <a href="#settings" class="user-avatar text-primary"><i class="ph ph-user-circle" style="font-size: 24px;"></i></a>
      </div>
    </header>
  `;
}
