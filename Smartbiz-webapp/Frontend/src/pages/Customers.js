import { Header, Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function CustomersPage() {
  return `
    <div class="page-container">
      ${Header('Customers')}
      
      <main class="content-area">
        <div class="search-bar mb-md">
          <input type="text" placeholder="Search customers..." class="search-input" id="customer-search">
        </div>
        
        <div class="filter-chips mb-md">
          <span class="chip active">All</span>
          <span class="chip">You will Give</span>
          <span class="chip">You will Get</span>
        </div>
        
        <div class="customer-list" id="customer-list-container">
          <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading customers...</div>
        </div>
        
        <button class="fab" id="btn-add-customer-fab"><i class="ph ph-user-plus"></i></button>
      </main>
      
      ${Navigation('customers')}
    </div>
  `;
}

CustomersPage.init = async () => {
  const searchInput = document.getElementById('customer-search');
  const listContainer = document.getElementById('customer-list-container');
  let customersData = [];

  const renderCustomers = (list) => {
    if(!list.length) {
      listContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No customers found.</div>';
      return;
    }
    listContainer.innerHTML = list.map(customer => `
      <div class="customer-item card mb-sm clickable-customer" data-id="${customer.id}" data-name="${customer.name}">
        <div class="flex justify-between items-center">
          <div class="flex items-center gap-md">
            <div class="avatar-sm"><i class="ph ph-user"></i></div>
            <div>
              <div class="font-bold">${customer.name}</div>
              <div class="text-xs text-secondary">${customer.phone || 'No phone'}</div>
            </div>
          </div>
          <div class="customer-balance text-right">
            <div class="balance-amount text-secondary">Tap to view</div>
          </div>
        </div>
      </div>
    `).join('');

    document.querySelectorAll('.clickable-customer').forEach(item => {
      item.addEventListener('click', () => {
        localStorage.setItem('selected_customer_id', item.dataset.id);
        localStorage.setItem('selected_customer_name', item.dataset.name);
        window.location.hash = '#customers/ledger';
      });
    });
  };

  try {
    customersData = await api.getCustomers();
    renderCustomers(customersData);
  } catch (error) {
    listContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-error);">Failed to load customers.</div>';
  }

  searchInput?.addEventListener('input', (e) => {
    const term = e.target.value.toLowerCase();
    const filtered = customersData.filter(c => c.name.toLowerCase().includes(term));
    renderCustomers(filtered);
  });

  document.getElementById('btn-add-customer-fab')?.addEventListener('click', () => {
    const name = prompt('Enter customer name:');
    if(name) {
       const phone = prompt('Enter phone:');
       api.addCustomer({ name, phone, merchant_id: localStorage.getItem('merchant_id') || 1 })
         .then(() => {
            alert('Customer added');
            return api.getCustomers();
         })
         .then(data => {
            customersData = data;
            renderCustomers(customersData);
         })
         .catch(err => alert('Failed: ' + err.message));
    }
  });
};
