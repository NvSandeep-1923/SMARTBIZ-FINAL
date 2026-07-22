const sqlite3 = require('sqlite3').verbose();
const path = require('path');

const dbPath = path.join(__dirname, 'smartbiz_master.db');
const db = new sqlite3.Database(dbPath, (err) => {
    if (err) {
        console.error('Error opening database:', err.message);
    } else {
        console.log('Connected to SmartBiz Master Database.');
        initializeSchema();
    }
});

function initializeSchema() {
    db.serialize(() => {
        // Migration: add missing columns if they don't exist yet
        db.run(`ALTER TABLE invoices ADD COLUMN grand_total REAL`, () => {});
        db.run(`ALTER TABLE invoices ADD COLUMN total_gst REAL`, () => {});
        db.run(`ALTER TABLE invoices ADD COLUMN subtotal REAL`, () => {});
        db.run(`ALTER TABLE expenses ADD COLUMN payment_mode TEXT`, () => {});
        db.run(`ALTER TABLE inventory ADD COLUMN sku TEXT`, () => {});
        db.run(`ALTER TABLE inventory ADD COLUMN hsn TEXT`, () => {});
        // --- AUTH & PROFILE ---
        db.run(`CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            phone TEXT UNIQUE,
            email TEXT UNIQUE,
            password TEXT,
            merchant_name TEXT,
            business_type TEXT,
            gstin TEXT,
            address TEXT,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )`);

        // --- CUSTOMERS & LEDGER ---
        db.run(`CREATE TABLE IF NOT EXISTS customers (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            phone TEXT,
            email TEXT,
            gstin TEXT,
            address TEXT,
            merchant_id INTEGER,
            FOREIGN KEY(merchant_id) REFERENCES users(id)
        )`);

        db.run(`CREATE TABLE IF NOT EXISTS ledger (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            customer_id INTEGER,
            amount REAL,
            type TEXT, -- 'gave' (credit), 'got' (debit)
            description TEXT,
            tx_date DATETIME DEFAULT CURRENT_TIMESTAMP,
            merchant_id INTEGER,
            FOREIGN KEY(customer_id) REFERENCES customers(id),
            FOREIGN KEY(merchant_id) REFERENCES users(id)
        )`);

        // --- INVENTORY ---
        db.run(`CREATE TABLE IF NOT EXISTS inventory (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            item_name TEXT NOT NULL,
            category TEXT,
            sku TEXT,
            hsn TEXT,
            sale_price REAL DEFAULT 0,
            cost_price REAL DEFAULT 0,
            stock_level INTEGER DEFAULT 0,
            unit TEXT,
            gst_rate REAL DEFAULT 0,
            merchant_id INTEGER,
            FOREIGN KEY(merchant_id) REFERENCES users(id)
        )`);

        // --- INVOICES ---
        db.run(`CREATE TABLE IF NOT EXISTS invoices (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            invoice_number TEXT UNIQUE,
            customer_id INTEGER,
            subtotal REAL,
            total_gst REAL,
            grand_total REAL,
            status TEXT DEFAULT 'final', -- 'draft', 'final'
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            merchant_id INTEGER,
            FOREIGN KEY(customer_id) REFERENCES customers(id),
            FOREIGN KEY(merchant_id) REFERENCES users(id)
        )`);

        db.run(`CREATE TABLE IF NOT EXISTS invoice_items (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            invoice_id INTEGER,
            item_id INTEGER,
            item_name TEXT,
            qty REAL,
            price REAL,
            gst_rate REAL,
            subtotal REAL,
            FOREIGN KEY(invoice_id) REFERENCES invoices(id),
            FOREIGN KEY(item_id) REFERENCES inventory(id)
        )`);

        // --- EXPENSES ---
        db.run(`CREATE TABLE IF NOT EXISTS expenses (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            description TEXT,
            amount REAL,
            category TEXT,
            payment_mode TEXT,
            expense_date DATE,
            merchant_id INTEGER,
            FOREIGN KEY(merchant_id) REFERENCES users(id)
        )`);
    });
}

module.exports = db;
