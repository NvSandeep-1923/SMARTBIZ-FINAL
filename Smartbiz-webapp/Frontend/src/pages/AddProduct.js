import { Header, Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function AddProductPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">Add New Item</h1>
      </header>
      
      <main class="content-area">
        <form class="product-form" id="add-product-form">
          <section class="card mb-md">
            <h3 class="section-title mb-sm">Basic Details</h3>
            <div class="form-group mb-md">
              <label>Item Name</label>
              <input type="text" id="p-name" placeholder="e.g. Premium Basmati Rice" class="input-full" required>
            </div>
            <div class="flex gap-md mb-md">
              <div class="form-group flex-1">
                <label>Category</label>
                <select id="p-cat" class="input-full">
                  <option>Grains & Pulses</option>
                  <option>Dairy</option>
                  <option>Beverages</option>
                </select>
              </div>
              <div class="form-group flex-1">
                <label>Unit</label>
                <select id="p-unit" class="input-full">
                  <option>kg</option>
                  <option>pcs</option>
                  <option>ltr</option>
                </select>
              </div>
            </div>
            <div class="flex gap-md">
              <div class="form-group flex-1">
                <label>HSN Code</label>
                <input type="text" id="p-hsn" placeholder="8-digit code" class="input-full">
              </div>
              <div class="form-group flex-1">
                <label>GST Rate (%)</label>
                <select id="p-gst" class="input-full">
                  <option value="0">0%</option>
                  <option value="5">5%</option>
                  <option value="12">12%</option>
                  <option value="18">18%</option>
                </select>
              </div>
            </div>
          </section>
          
          <section class="card mb-md">
            <h3 class="section-title mb-sm">Pricing Information</h3>
            <div class="flex gap-md">
              <div class="form-group flex-1">
                <label>Purchase Price (₹)</label>
                <input type="number" id="p-cost" placeholder="0.00" class="input-full" required>
              </div>
              <div class="form-group flex-1">
                <label>Sales Price (₹)</label>
                <input type="number" id="p-sale" placeholder="0.00" class="input-full" required>
              </div>
            </div>
          </section>
          
          <section class="card mb-lg">
            <h3 class="section-title mb-sm">Stock Inventory</h3>
            <div class="flex gap-md mb-md">
              <div class="form-group flex-1">
                <label>Opening Stock</label>
                <input type="number" id="p-stock" placeholder="0" class="input-full" required>
              </div>
              <div class="form-group flex-1">
                <label>Low Stock Alert Level</label>
                <input type="number" id="p-alert" placeholder="Notify when below..." class="input-full">
              </div>
            </div>
          </section>
          
          <div class="action-footer">
            <button type="button" class="btn btn-outline flex-1" onclick="window.history.back()">Cancel</button>
            <button type="submit" class="btn btn-primary flex-2">Save Product</button>
          </div>
        </form>
      </main>
      ${Navigation('inventory')}
    </div>
  `;
}

AddProductPage.init = () => {
  const form = document.getElementById('add-product-form');
  if (form) {
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const payload = {
        item_name: document.getElementById('p-name').value,
        category: document.getElementById('p-cat').value,
        sku: 'SKU-' + Math.floor(Math.random() * 1000000),
        hsn: document.getElementById('p-hsn').value,
        sale_price: parseFloat(document.getElementById('p-sale').value) || 0,
        cost_price: parseFloat(document.getElementById('p-cost').value) || 0,
        stock_level: parseInt(document.getElementById('p-stock').value) || 0,
        unit: document.getElementById('p-unit').value,
        gst_rate: parseInt(document.getElementById('p-gst').value) || 0,
        merchant_id: localStorage.getItem('merchant_id') || 1
      };

      const submitBtn = form.querySelector('button[type="submit"]');
      try {
        submitBtn.disabled = true;
        submitBtn.textContent = 'Saving...';
        await api.addInventoryItem(payload);
        alert(`Product "${payload.item_name}" saved successfully!`);
        window.location.hash = '#inventory';
      } catch (err) {
        alert('Failed to save product: ' + err.message);
      } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = 'Save Product';
      }
    });
  }
};
