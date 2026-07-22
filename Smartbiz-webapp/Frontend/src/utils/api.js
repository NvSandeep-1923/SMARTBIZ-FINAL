const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:5001/api';

// Helper for making API calls
async function apiClient(endpoint, { method = 'GET', body, ...customConfig } = {}) {
  const headers = { 'Content-Type': 'application/json' };
  
  // Note: we can add authentication tokens here later if needed
  
  const config = {
    method,
    headers,
    ...customConfig,
  };

  if (body) {
    config.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, config);
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.error || 'API request failed');
    }
    return data;
  } catch (error) {
    console.error(`API Error on ${endpoint}:`, error);
    throw error;
  }
}

export const api = {
  // Auth
  login: (credentials) => apiClient('/auth/login', { method: 'POST', body: credentials }),
  register: (userDetails) => apiClient('/auth/register', { method: 'POST', body: userDetails }),

  // Dashboard
  getDashboardStats: () => apiClient('/dashboard/stats'),

  // Inventory
  getInventory: () => apiClient('/inventory'),
  addInventoryItem: (item) => apiClient('/inventory', { method: 'POST', body: item }),
  deleteInventoryItem: (id) => apiClient(`/inventory/${id}`, { method: 'DELETE' }),

  // Customers
  getCustomers: () => apiClient('/customers'),
  addCustomer: (customer) => apiClient('/customers', { method: 'POST', body: customer }),

  // Ledger
  getCustomerLedger: (customerId) => apiClient(`/ledger/${customerId}`),
  addLedgerEntry: (entry) => apiClient('/ledger', { method: 'POST', body: entry }),

  // Invoices
  getInvoices: () => apiClient('/invoices'),
  createInvoice: (invoiceData) => apiClient('/invoices', { method: 'POST', body: invoiceData }),

  // Expenses
  getExpenses: () => apiClient('/expenses'),
  addExpense: (expense) => apiClient('/expenses', { method: 'POST', body: expense }),
};
