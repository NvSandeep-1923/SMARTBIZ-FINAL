async function getDashboardStats(supabase, userId) {
    const today = new Date().toISOString().split('T')[0];

    const { data: invoicesToday, error: invErr } = await supabase
        .from('invoices')
        .select('grand_total')
        .eq('user_id', userId)
        .gte('created_at', `${today}T00:00:00`)
        .lte('created_at', `${today}T23:59:59`);

    if (invErr) throw invErr;

    const salesToday = (invoicesToday || []).reduce((s, i) => s + Number(i.grand_total || 0), 0);

    const { data: lowStock, error: stockErr } = await supabase
        .from('inventory')
        .select('id', { count: 'exact', head: true })
        .eq('user_id', userId)
        .lt('stock_level', 10);

    if (stockErr) throw stockErr;

    const { data: ledgerRows, error: ledgerErr } = await supabase
        .from('ledger')
        .select('amount, type')
        .eq('user_id', userId);

    if (ledgerErr) throw ledgerErr;

    let totalGave = 0, totalGot = 0;
    (ledgerRows || []).forEach(r => {
        if (r.type === 'gave') totalGave += Number(r.amount);
        else if (r.type === 'got') totalGot += Number(r.amount);
    });

    const { data: recentTransactions, error: recentErr } = await supabase
        .from('ledger')
        .select('*')
        .eq('user_id', userId)
        .order('tx_date', { ascending: false })
        .limit(5);

    if (recentErr) throw recentErr;

    return {
        salesToday,
        lowStockCount: lowStock ?? 0,
        udharTotal: totalGave - totalGot,
        recentTransactions: recentTransactions || []
    };
}

async function getAdminStats(supabaseAdmin) {
    if (!supabaseAdmin) throw new Error('Admin client not configured');

    const [profiles, invoices, customers, inventory] = await Promise.all([
        supabaseAdmin.from('profiles').select('id', { count: 'exact', head: true }),
        supabaseAdmin.from('invoices').select('grand_total'),
        supabaseAdmin.from('customers').select('id', { count: 'exact', head: true }),
        supabaseAdmin.from('inventory').select('id', { count: 'exact', head: true })
    ]);

    const totalRevenue = (invoices.data || []).reduce((s, i) => s + Number(i.grand_total || 0), 0);

    return {
        totalUsers: profiles.count || 0,
        totalCustomers: customers.count || 0,
        totalInventoryItems: inventory.count || 0,
        totalRevenue,
        role: 'Admin'
    };
}

async function getOfficerStats(supabaseAdmin) {
    if (!supabaseAdmin) throw new Error('Admin client not configured');

    const [merchants, invoices, ledger] = await Promise.all([
        supabaseAdmin.from('profiles').select('id', { count: 'exact', head: true }).eq('role', 'Citizen'),
        supabaseAdmin.from('invoices').select('grand_total, created_at').gte('created_at', new Date(Date.now() - 30 * 86400000).toISOString()),
        supabaseAdmin.from('ledger').select('amount, type')
    ]);

    const revenue30d = (invoices.data || []).reduce((s, i) => s + Number(i.grand_total || 0), 0);
    let udhar = 0;
    (ledger.data || []).forEach(r => {
        if (r.type === 'gave') udhar += Number(r.amount);
        else if (r.type === 'got') udhar -= Number(r.amount);
    });

    return {
        totalMerchants: merchants.count || 0,
        revenueLast30Days: revenue30d,
        totalOutstandingUdhar: udhar,
        role: 'Officer'
    };
}

module.exports = { getDashboardStats, getAdminStats, getOfficerStats };
