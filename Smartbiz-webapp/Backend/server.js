const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const db = require('./database');

const app = express();
const PORT = process.env.PORT || 5001;

app.use(cors());
app.use(bodyParser.json());

// Request logger
app.use((req, res, next) => {
    console.log(`[${new Date().toISOString()}] ${req.method} ${req.url} from ${req.ip}`);
    next();
});

// Helper for DB queries
const runQuery = (sql, params) => new Promise((resolve, reject) => {
    db.run(sql, params, function(err) {
        if (err) reject(err);
        else resolve(this);
    });
});

const getQuery = (sql, params) => new Promise((resolve, reject) => {
    db.get(sql, params, (err, row) => {
        if (err) reject(err);
        else resolve(row);
    });
});

const allQuery = (sql, params) => new Promise((resolve, reject) => {
    db.all(sql, params, (err, rows) => {
        if (err) reject(err);
        else resolve(rows);
    });
});

// --- AUTH ---
app.post('/api/auth/login', async (req, res) => {
    const { phone, email, password } = req.body;
    try {
        const user = await getQuery('SELECT * FROM users WHERE (phone = ? OR email = ?) AND password = ?', [phone || email, phone || email, password]);
        if (!user) return res.status(401).json({ error: 'Invalid credentials' });
        res.json({ user, message: 'Login successful' });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

app.post('/api/auth/register', async (req, res) => {
    const { phone, email, password, merchant_name } = req.body;
    try {
        const result = await runQuery('INSERT INTO users (phone, email, password, merchant_name) VALUES (?, ?, ?, ?)', [phone, email, password, merchant_name]);
        res.status(201).json({ id: result.lastID, message: 'Registered' });
    } catch (err) { res.status(400).json({ error: 'Already exists' }); }
});

// --- INVENTORY ---
app.get('/api/inventory', async (req, res) => {
    try { res.json(await allQuery('SELECT * FROM inventory ORDER BY id DESC', [])); }
    catch (err) { res.status(500).json({ error: err.message }); }
});

app.post('/api/inventory', async (req, res) => {
    const { item_name, category, sku, hsn, sale_price, cost_price, stock_level, unit, gst_rate, merchant_id } = req.body;
    try {
        const result = await runQuery('INSERT INTO inventory (item_name, category, sku, hsn, sale_price, cost_price, stock_level, unit, gst_rate, merchant_id) VALUES (?,?,?,?,?,?,?,?,?,?)', 
            [item_name, category, sku, hsn, sale_price, cost_price, stock_level, unit, gst_rate, merchant_id]);
        res.status(201).json({ id: result.lastID });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

app.delete('/api/inventory/:id', async (req, res) => {
    try { await runQuery('DELETE FROM inventory WHERE id = ?', [req.params.id]); res.json({ success: true }); }
    catch (err) { res.status(500).json({ error: err.message }); }
});

// --- CUSTOMERS ---
app.get('/api/customers', async (req, res) => {
    try { res.json(await allQuery('SELECT * FROM customers ORDER BY name ASC', [])); }
    catch (err) { res.status(500).json({ error: err.message }); }
});

app.post('/api/customers', async (req, res) => {
    const { name, phone, email, gstin, address, merchant_id } = req.body;
    try {
        const result = await runQuery('INSERT INTO customers (name, phone, email, gstin, address, merchant_id) VALUES (?,?,?,?,?,?)', [name, phone, email, gstin, address, merchant_id]);
        res.status(201).json({ id: result.lastID });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

// --- LEDGER ---
app.get('/api/ledger/:customerId', async (req, res) => {
    try { res.json(await allQuery('SELECT * FROM ledger WHERE customer_id = ? ORDER BY tx_date DESC', [req.params.customerId])); }
    catch (err) { res.status(500).json({ error: err.message }); }
});

app.post('/api/ledger', async (req, res) => {
    const { customer_id, amount, type, description, merchant_id } = req.body;
    try {
        await runQuery('INSERT INTO ledger (customer_id, amount, type, description, merchant_id) VALUES (?,?,?,?,?)', [customer_id, amount, type, description, merchant_id]);
        res.status(201).json({ success: true });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

// --- INVOICES ---
app.get('/api/invoices', async (req, res) => {
    try { res.json(await allQuery('SELECT * FROM invoices ORDER BY id DESC', [])); }
    catch (err) { res.status(500).json({ error: err.message }); }
});

app.post('/api/invoices', async (req, res) => {
    const { invoice_number, customer_id, subtotal, total_gst, grand_total, items, merchant_id } = req.body;
    try {
        const inv = await runQuery('INSERT INTO invoices (invoice_number, customer_id, subtotal, total_gst, grand_total, merchant_id) VALUES (?,?,?,?,?,?)', 
            [invoice_number, customer_id, subtotal, total_gst, grand_total, merchant_id]);
        for (let item of items) {
            await runQuery('INSERT INTO invoice_items (invoice_id, item_id, item_name, qty, price, gst_rate, subtotal) VALUES (?,?,?,?,?,?,?)',
                [inv.lastID, item.id, item.item_name, item.qty, item.price, item.gst_rate, item.subtotal]);
            // Decrease stock
            await runQuery('UPDATE inventory SET stock_level = stock_level - ? WHERE id = ?', [item.qty, item.id]);
        }
        res.status(201).json({ id: inv.lastID });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

// --- EXPENSES ---
app.get('/api/expenses', async (req, res) => {
    try { res.json(await allQuery('SELECT * FROM expenses ORDER BY expense_date DESC', [])); }
    catch (err) { res.status(500).json({ error: err.message }); }
});

app.post('/api/expenses', async (req, res) => {
    const { description, amount, category, payment_mode, expense_date, merchant_id } = req.body;
    try {
        await runQuery('INSERT INTO expenses (description, amount, category, payment_mode, expense_date, merchant_id) VALUES (?,?,?,?,?,?)', 
            [description, amount, category, payment_mode, expense_date, merchant_id]);
        res.status(201).json({ success: true });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

// --- DASHBOARD STATS ---
app.get('/api/dashboard/stats', async (req, res) => {
    const today = new Date().toISOString().split('T')[0];
    try {
        const salesToday = await getQuery('SELECT SUM(grand_total) as total FROM invoices WHERE DATE(created_at) = ?', [today]);
        const lowStock = await getQuery('SELECT COUNT(*) as count FROM inventory WHERE stock_level < 10', []);
        const totalUdhar = await getQuery("SELECT SUM(amount) as total FROM ledger WHERE type = 'gave'", []);
        const totalGot = await getQuery("SELECT SUM(amount) as total FROM ledger WHERE type = 'got'", []);
        
        res.json({
            salesToday: salesToday.total || 0,
            lowStockCount: lowStock.count || 0,
            udharTotal: (totalUdhar.total || 0) - (totalGot.total || 0),
            recentTransactions: await allQuery('SELECT * FROM ledger ORDER BY tx_date DESC LIMIT 5', [])
        });
    } catch (err) { res.status(500).json({ error: err.message }); }
});

app.listen(PORT, () => console.log(`SmartBiz Backend running on port ${PORT}`));
