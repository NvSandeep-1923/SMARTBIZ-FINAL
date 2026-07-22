import { Header, Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function BillingPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">Create Invoice</h1>
        <div class="step-indicator">Step 2 of 3</div>
      </header>
      
      <main class="content-area">
        <section class="customer-selection card mb-lg">
          <div class="form-group">
            <label class="font-bold">Select Customer</label>
            <select id="billing-customer" class="input-full mt-xs">
              <option value="">Loading customers...</option>
            </select>
          </div>
        </section>
        
        <section class="invoice-items">
          <div class="flex gap-sm items-end mb-md">
            <div class="form-group flex-2">
              <label>Select Item to Add</label>
              <select id="billing-inventory" class="input-full mt-xs">
                 <option value="">Loading inventory...</option>
              </select>
            </div>
            <button class="btn btn-primary" id="btn-add-item"><i class="ph ph-plus"></i> Add</button>
          </div>
          
          <div class="items-list" id="invoice-items-list">
            <!-- Dynamic items will be injected here -->
          </div>
        </section>
        
        <section class="invoice-summary mt-lg">
          <div class="summary-card">
            <div class="summary-row">
              <span>Subtotal</span>
              <span id="subtotal">₹ 0.00</span>
            </div>
            <div class="summary-row">
              <span>Total GST</span>
              <span id="total-gst">₹ 0.00</span>
            </div>
            <div class="summary-row total">
              <span>Grand Total</span>
              <span id="grand-total">₹ 0.00</span>
            </div>
          </div>
        </section>
        
        <div class="action-footer">
          <button class="btn btn-outline flex-1">Save Draft</button>
          <button class="btn btn-primary flex-2" id="btn-checkout">Save & Preview</button>
        </div>
      </main>
      
      ${Navigation('billing')}
    </div>
  `;
}

BillingPage.init = async () => {
  let initialItems = [];
  let cartItems = [];
  let availableInventory = [];
  let customersList = [];

  const itemsList = document.getElementById('invoice-items-list');
  const subtotalEl = document.getElementById('subtotal');
  const gstEl = document.getElementById('total-gst');
  const totalEl = document.getElementById('grand-total');
  const customerSelect = document.getElementById('billing-customer');
  const inventorySelect = document.getElementById('billing-inventory');

  try {
    customersList = await api.getCustomers();
    customerSelect.innerHTML = '<option value="">-- Choose Customer --</option>' + customersList.map(c => 
      `<option value="${c.id}">${c.name}</option>`
    ).join('');

    availableInventory = await api.getInventory();
    inventorySelect.innerHTML = '<option value="">-- Choose Item --</option>' + availableInventory.filter(i => i.stock_level > 0).map(i => 
      `<option value="${i.id}">${i.item_name} (Stock: ${i.stock_level}) - ₹${i.sale_price}</option>`
    ).join('');
  } catch(err) {
    console.error('Failed to load data for billing', err);
  }

  function renderItems() {
    itemsList.innerHTML = cartItems.map(item => {
      const subtotal = item.qty * item.price;
      const gstAmount = (subtotal * item.gst) / 100;
      const total = subtotal + gstAmount;
      
      return `
        <div class="item-entry card mb-sm" data-id="${item.id}">
          <div class="flex justify-between">
            <div>
              <div class="font-bold">${item.name}</div>
              <div class="text-xs text-secondary">HSN: ${item.hsn || '0000'}</div>
            </div>
            <button class="icon-btn text-error btn-remove-item" data-id="${item.id}"><i class="ph ph-trash"></i></button>
          </div>
          <div class="item-entry-grid mt-sm">
            <div class="entry-field">
              <span class="label">Qty:</span>
              <div class="qty-control">
                <button class="qty-btn minus" data-id="${item.id}">-</button>
                <span class="value">${item.qty}</span>
                <button class="qty-btn plus" data-id="${item.id}">+</button>
              </div>
            </div>
            <div class="entry-field">
              <span class="label">Price:</span>
              <span class="value">₹ ${item.price.toFixed(2)}</span>
            </div>
            <div class="entry-field">
              <span class="label">GST:</span>
              <span class="value">${item.gst}% (₹ ${gstAmount.toFixed(2)})</span>
            </div>
            <div class="entry-field">
              <span class="label">Total:</span>
              <span class="value font-bold text-primary">₹ ${total.toFixed(2)}</span>
            </div>
          </div>
        </div>
      `;
    }).join('');

    calculateTotals();
    attachListeners();
  }

  function calculateTotals() {
    let subtotal = 0;
    let totalGst = 0;

    cartItems.forEach(item => {
      const lineSubtotal = item.qty * item.price;
      subtotal += lineSubtotal;
      totalGst += (lineSubtotal * item.gst) / 100;
    });

    const grandTotal = subtotal + totalGst;

    subtotalEl.dataset.value = subtotal;
    gstEl.dataset.value = totalGst;
    totalEl.dataset.value = grandTotal;

    subtotalEl.textContent = `₹ ${subtotal.toLocaleString('en-IN', { minimumFractionDigits: 2 })}`;
    gstEl.textContent = `₹ ${totalGst.toLocaleString('en-IN', { minimumFractionDigits: 2 })}`;
    totalEl.textContent = `₹ ${grandTotal.toLocaleString('en-IN', { minimumFractionDigits: 2 })}`;
  }

  function attachListeners() {
    document.querySelectorAll('.btn-remove-item').forEach(btn => {
      btn.addEventListener('click', () => {
        const id = parseInt(btn.dataset.id);
        cartItems = cartItems.filter(i => i.id !== id);
        renderItems();
      });
    });

    document.querySelectorAll('.qty-btn.plus').forEach(btn => {
      btn.addEventListener('click', () => {
        const id = parseInt(btn.dataset.id);
        const item = cartItems.find(i => i.id === id);
        if (item) {
          item.qty++;
          renderItems();
        }
      });
    });

    document.querySelectorAll('.qty-btn.minus').forEach(btn => {
      btn.addEventListener('click', () => {
        const id = parseInt(btn.dataset.id);
        const item = cartItems.find(i => i.id === id);
        if (item && item.qty > 1) {
          item.qty--;
          renderItems();
        }
      });
    });
  }

  document.getElementById('btn-add-item')?.addEventListener('click', () => {
    const invId = parseInt(inventorySelect.value);
    if(isNaN(invId)) return alert('Select an item first');
    
    // Check if already in cart
    const existing = cartItems.find(i => i.id === invId);
    if(existing) {
       existing.qty++;
       renderItems();
       return;
    }

    const invItem = availableInventory.find(i => i.id === invId);
    if(!invItem) return;

    cartItems.push({
      id: invItem.id,
      name: invItem.item_name,
      hsn: invItem.hsn,
      qty: 1,
      price: invItem.sale_price || 0,
      gst: invItem.gst_rate || 0
    });
    renderItems();
  });

  document.getElementById('btn-checkout')?.addEventListener('click', async () => {
    const custId = parseInt(customerSelect.value);
    if(isNaN(custId)) return alert('Select a customer first');
    if(!cartItems.length) return alert('Add items to the invoice');

    const btn = document.getElementById('btn-checkout');
    btn.disabled = true;
    btn.textContent = 'Saving...';
    
    try {
      const invoiceData = {
        invoice_number: 'INV-' + Date.now(),
        customer_id: custId,
        subtotal: parseFloat(subtotalEl.dataset.value) || 0,
        total_gst: parseFloat(gstEl.dataset.value) || 0,
        grand_total: parseFloat(totalEl.dataset.value) || 0,
        merchant_id: localStorage.getItem('merchant_id') || 1,
        items: cartItems.map(i => ({
           id: i.id,
           item_name: i.name,
           qty: i.qty,
           price: i.price,
           gst_rate: i.gst,
           subtotal: i.qty * i.price
        }))
      };

      await api.createInvoice(invoiceData);
      window.location.hash = '#payment-success';
    } catch(err) {
      alert('Failed: ' + err.message);
      btn.disabled = false;
      btn.textContent = 'Save & Preview';
    }
  });

  renderItems();
};
