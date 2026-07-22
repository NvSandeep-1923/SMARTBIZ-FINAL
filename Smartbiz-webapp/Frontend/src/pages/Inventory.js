import { Header, Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function InventoryPage() {
  return `
    <div class="page-container">
      ${Header('Inventory')}
      
      <main class="content-area">
        <div class="search-bar mb-md">
          <input type="text" placeholder="Search items, SKU, or brands..." class="search-input">
        </div>
        
        <div class="filter-chips mb-md">
          <span class="chip active">All</span>
          <span class="chip">Grocery</span>
          <span class="chip">Dairy</span>
          <span class="chip">Beverages</span>
        </div>
        
        <div class="inventory-list" id="inventory-list">
          <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading inventory...</div>
        </div>
        
        <button class="fab"><i class="ph ph-plus"></i></button>
      </main>
      
      ${Navigation('inventory')}
    </div>
  `;
}

InventoryPage.init = async () => {
  const searchInput = document.querySelector('.search-input');
  const chips = document.querySelectorAll('.chip');
  const listContainer = document.getElementById('inventory-list');
  let inventory = [];
  let currentFilter = 'all';
  let searchTerm = '';

  const renderItems = () => {
    let filtered = inventory.filter(item => {
      const name = (item.item_name || '').toLowerCase();
      const sku = (item.sku || '').toLowerCase();
      const category = (item.category || '').toLowerCase();
      
      const matchesSearch = name.includes(searchTerm) || sku.includes(searchTerm);
      const matchesCat = currentFilter === 'all' || category.includes(currentFilter) || name.includes(currentFilter);
      return matchesSearch && matchesCat;
    });

    if(filtered.length === 0) {
      listContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No items found.</div>';
      return;
    }

    listContainer.innerHTML = filtered.map(item => `
      <div class="inventory-item card mb-sm">
        <div class="item-info">
          <div class="item-name">${item.item_name}</div>
          <div class="item-sku">SKU: ${item.sku}</div>
        </div>
        <div class="item-stats">
          <div class="item-price">₹ ${item.sale_price}</div>
          <div class="item-stock ${item.stock_level < 10 ? 'text-error' : ''}">
            ${item.stock_level} units
            ${item.stock_level < 10 ? '<span class="status-badge">Low Stock</span>' : ''}
          </div>
        </div>
      </div>
    `).join('');
  };

  try {
    inventory = await api.getInventory();
    renderItems();
  } catch (error) {
    listContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-error);">Failed to load inventory.</div>';
  }

  searchInput?.addEventListener('input', (e) => {
    searchTerm = e.target.value.toLowerCase();
    renderItems();
  });

  chips.forEach(chip => {
    chip.addEventListener('click', () => {
      chips.forEach(c => c.classList.remove('active'));
      chip.classList.add('active');
      currentFilter = chip.textContent.toLowerCase();
      renderItems();
    });
  });

  const fab = document.querySelector('.fab');
  if (fab) {
    fab.addEventListener('click', () => {
      window.location.hash = '#inventory/add';
    });
  }
};
