const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
require('dotenv').config();

const { supabase, supabaseAdmin, createUserClient, supabaseUrl, supabaseAnonKey } = require('./config/supabase');
const { requireAuth, requireRole } = require('./middleware/auth');
const { getDashboardStats, getAdminStats, getOfficerStats } = require('./services/dashboardService');
const { getAiInsights } = require('./services/aiService');
const { seedTranslations, getTranslations } = require('./services/translationService');

const app = express();
const PORT = process.env.PORT || 5001;

app.use(cors());
app.use(bodyParser.json());

app.use((req, res, next) => {
    console.log(`[${new Date().toISOString()}] ${req.method} ${req.url}`);
    next();
});

// ─── HEALTH ──────────────────────────────────────────────────────────────────
app.get('/api/health', (req, res) => {
    res.json({ status: 'ok', database: 'supabase' });
});

// Helper to convert UUID to a stable, unique integer for client compatibility
function hashStringToInt(str) {
    if (!str) return 0;
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        hash = (hash << 5) - hash + str.charCodeAt(i);
        hash |= 0;
    }
    return Math.abs(hash);
}

// ─── AUTHENTICATION (Supabase Auth) ──────────────────────────────────────────
app.post('/api/auth/register', async (req, res) => {
    const { email, password, phone, merchant_name } = req.body;
    if (!email || !password) {
        return res.status(400).json({ error: 'Email and password are required' });
    }

    if (supabaseUrl === 'https://your-project-ref.supabase.co') {
        return res.status(201).json({
            id: hashStringToInt("mock-ui-test-user"),
            message: 'Please verify your email.'
        });
    }

    try {
        const { data, error } = await supabase.auth.signUp({
            email,
            password,
            options: {
                data: {
                    full_name: merchant_name || '',
                    phone: phone || '',
                    business_name: merchant_name || '',
                    role: 'Citizen' // Force default role on signup
                }
            }
        });
        if (error) {
            return res.status(400).json({ error: error.message });
        }
        const user = data.user;
        if (!user) {
            return res.status(400).json({ error: 'Failed to create user' });
        }
        res.status(201).json({
            id: hashStringToInt(user.id),
            message: 'Please verify your email.'
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/auth/login', async (req, res) => {
    const { email, password } = req.body;
    if (!email || !password) {
        return res.status(400).json({ error: 'Email and password are required' });
    }

    if (supabaseUrl === 'https://your-project-ref.supabase.co') {
        return res.json({
            user: {
                id: hashStringToInt("mock-ui-test-user"),
                email: email,
                phone: '1234567890',
                merchant_name: 'Test Merchant',
                business_type: 'Retail',
                gstin: '',
                address: '',
                role: 'Citizen',
                avatar_url: ''
            },
            token: "mock-jwt-token.payload.signature",
            refresh_token: "mock-refresh-token",
            message: 'Login successful'
        });
    }

    try {
        const { data, error } = await supabase.auth.signInWithPassword({
            email,
            password
        });
        if (error) {
            return res.status(401).json({ error: error.message });
        }
        const user = data.user;
        if (!user.email_confirmed_at) {
            return res.status(403).json({ error: 'Please verify your email.' });
        }

        const { data: profile, error: profileErr } = await supabaseAdmin
            .from('profiles')
            .select('*')
            .eq('id', user.id)
            .single();

        if (profileErr || !profile) {
            return res.status(403).json({ error: 'Profile not found' });
        }

        res.json({
            user: {
                id: hashStringToInt(user.id),
                email: user.email,
                phone: profile.phone || '',
                merchant_name: profile.business_name || profile.full_name || '',
                business_type: profile.business_type || '',
                gstin: profile.gstin || '',
                address: profile.address || '',
                role: profile.role || 'Citizen',
                avatar_url: profile.avatar_url || ''
            },
            token: data.session.access_token,
            refresh_token: data.session.refresh_token,
            message: 'Login successful'
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


app.post('/api/auth/forgot-password', async (req, res) => {
    const { email } = req.body;
    if (!email) return res.status(400).json({ error: 'Email is required' });
    try {
        const { error } = await supabase.auth.resetPasswordForEmail(email);
        if (error) return res.status(400).json({ error: error.message });
        res.json({ message: 'Password reset link sent to your email.' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/auth/reset-password', async (req, res) => {
    const { password } = req.body;
    const authHeader = req.headers.authorization;
    if (!password) return res.status(400).json({ error: 'New password is required' });
    if (!authHeader) return res.status(401).json({ error: 'Unauthorized' });
    const token = authHeader.split(' ')[1];
    try {
        const client = createUserClient(token);
        const { error } = await client.auth.updateUser({ password });
        if (error) return res.status(400).json({ error: error.message });
        res.json({ message: 'Password updated successfully' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/auth/refresh', async (req, res) => {
    const { refresh_token } = req.body;
    if (!refresh_token) return res.status(400).json({ error: 'Refresh token is required' });
    try {
        const { data, error } = await supabase.auth.refreshSession({ refresh_token });
        if (error) return res.status(400).json({ error: error.message });
        res.json({
            token: data.session.access_token,
            refresh_token: data.session.refresh_token,
            user: {
                id: hashStringToInt(data.user.id),
                email: data.user.email
            },
            message: 'Session refreshed'
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


// ─── AUTH (Supabase Auth — client uses SDK; these are helper endpoints) ───────
app.get('/api/auth/session', requireAuth, (req, res) => {
    res.json({
        user: {
            id: req.profile.id,
            email: req.profile.email,
            full_name: req.profile.full_name,
            phone: req.profile.phone,
            role: req.profile.role,
            business_name: req.profile.business_name,
            avatar_url: req.profile.avatar_url,
            created_at: req.profile.created_at
        },
        message: 'Session valid'
    });
});

app.post('/api/auth/logout', requireAuth, async (req, res) => {
    try {
        await req.supabase.auth.signOut();
        res.json({ message: 'Logged out' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// ─── PROFILE ─────────────────────────────────────────────────────────────────
app.get('/api/profile', requireAuth, (req, res) => {
    res.json(req.profile);
});

app.patch('/api/profile', requireAuth, async (req, res) => {
    const { full_name, phone, business_name, business_type, gstin, address, avatar_url } = req.body;
    const { data, error } = await req.supabase
        .from('profiles')
        .update({ full_name, phone, business_name, business_type, gstin, address, avatar_url })
        .eq('id', req.profile.id)
        .select()
        .single();
    if (error) return res.status(400).json({ error: error.message });
    res.json(data);
});

// ─── DASHBOARD (role-aware) ──────────────────────────────────────────────────
app.get('/api/dashboard/stats', requireAuth, async (req, res) => {
    try {
        if (supabaseUrl === 'https://your-project-ref.supabase.co') {
            return res.json({
                total_inventory: 150,
                total_sales: 5200.00,
                active_customers: 24,
                pending_payments: 3,
                invoices: [],
                low_stock_items: []
            });
        }

        const role = req.profile.role;
        if (role === 'Admin') {
            return res.json(await getAdminStats(supabaseAdmin));
        }
        if (role === 'Officer') {
            return res.json(await getOfficerStats(supabaseAdmin));
        }
        res.json(await getDashboardStats(req.supabase, req.profile.id));
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// ─── INVENTORY ───────────────────────────────────────────────────────────────
app.get('/api/inventory', requireAuth, async (req, res) => {
    const query = req.supabase.from('inventory').select('*').order('id', { ascending: false });
    if (req.profile.role === 'Citizen') query.eq('user_id', req.profile.id);
    const { data, error } = await query;
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

app.post('/api/inventory', requireAuth, async (req, res) => {
    const row = { ...req.body, user_id: req.profile.id };
    delete row.merchant_id;
    const { data, error } = await req.supabase.from('inventory').insert(row).select('id').single();
    if (error) return res.status(400).json({ error: error.message });
    res.status(201).json({ id: data.id });
});

app.delete('/api/inventory/:id', requireAuth, async (req, res) => {
    let q = req.supabase.from('inventory').delete().eq('id', req.params.id);
    if (req.profile.role === 'Citizen') q = q.eq('user_id', req.profile.id);
    const { error } = await q;
    if (error) return res.status(500).json({ error: error.message });
    res.json({ success: true });
});

// ─── CUSTOMERS ─────────────────────────────────────────────────────────────────
app.get('/api/customers', requireAuth, async (req, res) => {
    let q = req.supabase.from('customers').select('*').order('name');
    if (req.profile.role === 'Citizen') q = q.eq('user_id', req.profile.id);
    const { data, error } = await q;
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

app.post('/api/customers', requireAuth, async (req, res) => {
    const row = { name: req.body.name, phone: req.body.phone, email: req.body.email, gstin: req.body.gstin, address: req.body.address, user_id: req.profile.id };
    const { data, error } = await req.supabase.from('customers').insert(row).select('id').single();
    if (error) return res.status(400).json({ error: error.message });
    res.status(201).json({ id: data.id });
});

// ─── LEDGER ──────────────────────────────────────────────────────────────────
app.get('/api/ledger/:customerId', requireAuth, async (req, res) => {
    let q = req.supabase.from('ledger').select('*').eq('customer_id', req.params.customerId).order('tx_date', { ascending: false });
    if (req.profile.role === 'Citizen') q = q.eq('user_id', req.profile.id);
    const { data, error } = await q;
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

app.post('/api/ledger', requireAuth, async (req, res) => {
    const row = { customer_id: req.body.customer_id, amount: req.body.amount, type: req.body.type, description: req.body.description, user_id: req.profile.id };
    const { error } = await req.supabase.from('ledger').insert(row);
    if (error) return res.status(400).json({ error: error.message });
    res.status(201).json({ success: true });
});

// ─── INVOICES ──────────────────────────────────────────────────────────────────
app.get('/api/invoices', requireAuth, async (req, res) => {
    let q = req.supabase.from('invoices').select('*').order('id', { ascending: false });
    if (req.profile.role === 'Citizen') q = q.eq('user_id', req.profile.id);
    const { data, error } = await q;
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

app.post('/api/invoices', requireAuth, async (req, res) => {
    const { invoice_number, customer_id, subtotal, total_gst, grand_total, items } = req.body;
    const { data: inv, error: invErr } = await req.supabase.from('invoices').insert({
        invoice_number, customer_id, subtotal, total_gst, grand_total, user_id: req.profile.id
    }).select('id').single();
    if (invErr) return res.status(400).json({ error: invErr.message });

    for (const item of items || []) {
        await req.supabase.from('invoice_items').insert({
            invoice_id: inv.id, item_id: item.id, item_name: item.item_name,
            qty: item.qty, price: item.price, gst_rate: item.gst_rate, subtotal: item.subtotal
        });
        if (item.id) {
            const { data: stock } = await req.supabase.from('inventory').select('stock_level').eq('id', item.id).single();
            if (stock) {
                await req.supabase.from('inventory').update({ stock_level: stock.stock_level - item.qty }).eq('id', item.id);
            }
        }
    }
    res.status(201).json({ id: inv.id });
});

// ─── EXPENSES ──────────────────────────────────────────────────────────────────
app.get('/api/expenses', requireAuth, async (req, res) => {
    let q = req.supabase.from('expenses').select('*').order('expense_date', { ascending: false });
    if (req.profile.role === 'Citizen') q = q.eq('user_id', req.profile.id);
    const { data, error } = await q;
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

app.post('/api/expenses', requireAuth, async (req, res) => {
    const row = { description: req.body.description, amount: req.body.amount, category: req.body.category, payment_mode: req.body.payment_mode, expense_date: req.body.expense_date, user_id: req.profile.id };
    const { error } = await req.supabase.from('expenses').insert(row);
    if (error) return res.status(400).json({ error: error.message });
    res.status(201).json({ success: true });
});

// ─── AI INSIGHTS ─────────────────────────────────────────────────────────────
app.get('/api/ai/insights', requireAuth, async (req, res) => {
    try {
        res.json(await getAiInsights(req.supabase, req.profile.id));
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// ─── NOTIFICATIONS ───────────────────────────────────────────────────────────
app.get('/api/notifications', requireAuth, async (req, res) => {
    const { data, error } = await req.supabase.from('notifications').select('*').eq('user_id', req.profile.id).order('created_at', { ascending: false }).limit(50);
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

app.patch('/api/notifications/:id/read', requireAuth, async (req, res) => {
    const { error } = await req.supabase.from('notifications').update({ read: true }).eq('id', req.params.id).eq('user_id', req.profile.id);
    if (error) return res.status(400).json({ error: error.message });
    res.json({ success: true });
});

// ─── TRANSLATIONS ────────────────────────────────────────────────────────────
app.get('/api/translations/:lang', async (req, res) => {
    try {
        res.json(await getTranslations(supabase, req.params.lang));
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// ─── STORAGE UPLOAD URL (signed) ─────────────────────────────────────────────
app.post('/api/storage/upload', requireAuth, async (req, res) => {
    const { bucket, path, contentType } = req.body;
    const allowed = ['avatars', 'profile-images', 'documents', 'attachments', 'complaints'];
    if (!allowed.includes(bucket)) return res.status(400).json({ error: 'Invalid bucket' });
    const fullPath = `${req.profile.id}/${path}`;
    const { data, error } = await supabaseAdmin.storage.from(bucket).createSignedUploadUrl(fullPath);
    if (error) return res.status(400).json({ error: error.message });
    res.json({ signedUrl: data.signedUrl, path: fullPath, token: data.token });
});

// ─── ADMIN: list users ───────────────────────────────────────────────────────
app.get('/api/admin/users', requireAuth, requireRole('Admin'), async (req, res) => {
    const { data, error } = await supabaseAdmin.from('profiles').select('id, email, full_name, phone, role, created_at').order('created_at', { ascending: false });
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

// ─── ADMIN: create user ──────────────────────────────────────────────────────
app.post('/api/admin/users', requireAuth, requireRole('Admin'), async (req, res) => {
    const { email, password, full_name, phone, role } = req.body;
    if (!email || !password || !role) {
        return res.status(400).json({ error: 'Email, password, and role are required' });
    }
    if (role !== 'Officer' && role !== 'Citizen') {
        return res.status(400).json({ error: 'Invalid role. Role must be Officer or Citizen' });
    }
    try {
        const { data, error } = await supabaseAdmin.auth.admin.createUser({
            email,
            password,
            email_confirm: true,
            user_metadata: {
                full_name: full_name || '',
                phone: phone || '',
                role: role
            }
        });
        if (error) {
            return res.status(400).json({ error: error.message });
        }
        const user = data.user;
        const { error: updateErr } = await supabaseAdmin
            .from('profiles')
            .update({ role, full_name, phone })
            .eq('id', user.id);

        if (updateErr) {
            return res.status(400).json({ error: updateErr.message });
        }
        res.status(201).json({
            id: hashStringToInt(user.id),
            message: 'User created successfully.'
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// ─── STARTUP ─────────────────────────────────────────────────────────────────
seedTranslations(supabaseAdmin).catch(console.error);

app.listen(PORT, () => {
    console.log(`SmartBiz Backend (Supabase) running on port ${PORT}`);
    console.log(`Supabase URL: ${supabaseUrl ? 'configured' : 'MISSING'}`);
});

module.exports = app;
