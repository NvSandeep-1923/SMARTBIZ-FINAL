import { Header, Navigation } from '../components/Navigation';

export function StaffManagementPage() {
  const staff = [
    { name: 'Amit Patel', role: 'Admin', status: 'Online' },
    { name: 'Suresh Raina', role: 'Salesperson', status: 'Offline' },
    { name: 'Kavita Singh', role: 'Inventory Manager', status: 'Online' },
  ];

  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">Staff Management</h1>
      </header>
      
      <main class="content-area">
        <div class="flex justify-between items-center mb-md">
          <h3 class="section-title">Active Staff</h3>
          <button class="btn btn-primary btn-sm">➕ Add Staff</button>
        </div>
        
        <div class="staff-list">
          ${staff.map(member => `
            <div class="staff-item card mb-sm">
              <div class="flex justify-between items-center">
                <div class="flex items-center gap-md">
                  <div class="avatar-sm">👤</div>
                  <div>
                    <div class="font-bold">${member.name}</div>
                    <div class="text-xs text-secondary">${member.role}</div>
                  </div>
                </div>
                <div class="staff-status text-right">
                  <div class="status-indicator ${member.status.toLowerCase()}">
                    ● ${member.status}
                  </div>
                </div>
              </div>
            </div>
          `).join('')}
        </div>
        
        <section class="card mt-lg">
          <h3 class="section-title mb-sm">Roles & Permissions</h3>
          <p class="text-xs text-secondary mb-md">Manage what your staff can see and do in the app.</p>
          <button class="btn btn-outline w-full">Configure Roles</button>
        </section>
      </main>
      ${Navigation('settings')}
    </div>
  `;
}

StaffManagementPage.init = () => {
  document.querySelector('.btn-primary.btn-sm')?.addEventListener('click', () => {
    alert('Add Staff Feature - Coming Soon!');
  });
};
