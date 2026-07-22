import { Header, Navigation } from '../components/Navigation';
import { api } from '../utils/api';

export function CustomerLedgerPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">Customer Ledger</h1>
      </header>
      
      <main class="content-area">
        <div class="ledger-summary card mb-lg">
          <div class="flex items-center gap-md mb-md">
            <div class="avatar-sm"><i class="ph ph-user"></i></div>
            <div>
              <div class="font-bold" id="c-ledger-name">Loading...</div>
              <div class="text-xs text-secondary">Ledger Details</div>
            </div>
          </div>
          <div class="flex justify-between items-end">
            <div>
              <div class="text-xs text-secondary">NET BALANCE</div>
              <div class="text-xl font-bold" id="c-ledger-balance">₹ 0</div>
            </div>
            <div class="risk-badge low-risk">Low Risk</div>
          </div>
        </div>
        
        <div class="transaction-history">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title">Transactions</h3>
            <button class="btn-text">Filter</button>
          </div>
          
          <div class="ledger-list" id="ledger-list-container">
             <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading transactions...</div>
          </div>
        </div>
        
        <div class="action-footer fixed-bottom" style="bottom: 64px;">
          <button class="btn btn-error flex-1" id="btn-ledger-gave">YOU GAVE</button>
          <button class="btn btn-success flex-1" id="btn-ledger-got">YOU GOT</button>
        </div>
      </main>
      ${Navigation('customers')}
    </div>
  `;
}

CustomerLedgerPage.init = async () => {
  const customerId = localStorage.getItem('selected_customer_id');
  const customerName = localStorage.getItem('selected_customer_name') || 'Customer';
  
  if(!customerId) {
    alert('No customer selected');
    window.location.hash = '#customers';
    return;
  }

  document.getElementById('c-ledger-name').textContent = customerName;
  const listContainer = document.getElementById('ledger-list-container');
  const balanceEl = document.getElementById('c-ledger-balance');

  const loadLedger = async () => {
    try {
      const data = await api.getCustomerLedger(customerId);
      if(!data.length) {
        listContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No transactions.</div>';
        balanceEl.textContent = '₹ 0';
        balanceEl.className = 'text-xl font-bold';
        return;
      }

      let balance = 0;
      listContainer.innerHTML = data.map(tx => {
        if(tx.type === 'gave') balance += tx.amount;
        if(tx.type === 'got') balance -= tx.amount;
        
        return `
          <div class="ledger-item card mb-sm">
            <div class="flex justify-between">
              <div>
                <div class="text-xs text-secondary">${new Date(tx.tx_date).toLocaleString()}</div>
                <div class="font-medium mt-xs">${tx.description || 'Transaction'}</div>
              </div>
              <div class="text-right">
                <div class="font-bold ${tx.type === 'gave' ? 'text-error' : 'text-success'}">
                  ${tx.type === 'gave' ? '-' : '+'} ₹ ${tx.amount}
                </div>
                <div class="text-xs text-secondary">${tx.type === 'gave' ? 'You Gave' : 'You Got'}</div>
              </div>
            </div>
          </div>
        `;
      }).join('');

      balanceEl.textContent = `₹ ${balance}`;
      balanceEl.className = `text-xl font-bold ${balance > 0 ? 'text-error' : 'text-success'}`;
    } catch (err) {
      listContainer.innerHTML = '<div style="padding: 1rem; text-align: center; color: var(--text-error);">Failed to load.</div>';
    }
  };

  await loadLedger();

  const handleTransaction = async (type) => {
    let amount = prompt(`Enter amount you ${type === 'gave' ? 'gave to' : 'got from'} ${customerName}:`);
    if (!amount || isNaN(amount)) return;
    let description = prompt('Enter description (optional):') || '';
    
    try {
      await api.addLedgerEntry({
        customer_id: customerId,
        amount: parseFloat(amount),
        type,
        description,
        merchant_id: localStorage.getItem('merchant_id') || 1
      });
      loadLedger();
    } catch (err) {
      alert('Failed: ' + err.message);
    }
  };

  document.getElementById('btn-ledger-gave')?.addEventListener('click', () => handleTransaction('gave'));
  document.getElementById('btn-ledger-got')?.addEventListener('click', () => handleTransaction('got'));
};
