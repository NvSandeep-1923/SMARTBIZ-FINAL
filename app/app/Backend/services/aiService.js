function linearTrend(values) {
    const n = values.length;
    if (n < 2) return 0;
    const sumX = (n * (n - 1)) / 2;
    const sumX2 = (n * (n - 1) * (2 * n - 1)) / 6;
    const sumY = values.reduce((a, b) => a + b, 0);
    const sumXY = values.reduce((acc, v, i) => acc + i * v, 0);
    return (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
}

function classifyCreditRisk(totalGave, totalGot, txCount, avgDaysBetweenPayments, maxBalance) {
    const repayRate = totalGave > 0 ? totalGot / totalGave : 1;
    let score = 0;
    if (repayRate >= 0.9) score += 40;
    else if (repayRate >= 0.6) score += 25;
    else if (repayRate >= 0.3) score += 10;
    if (txCount >= 10) score += 20;
    else if (txCount >= 5) score += 12;
    else if (txCount >= 2) score += 5;
    if (avgDaysBetweenPayments <= 7) score += 20;
    else if (avgDaysBetweenPayments <= 15) score += 12;
    else if (avgDaysBetweenPayments <= 30) score += 5;
    const balanceRatio = maxBalance > 0 ? (totalGave - totalGot) / maxBalance : 0;
    if (balanceRatio <= 0.1) score += 20;
    else if (balanceRatio <= 0.4) score += 10;
    else if (balanceRatio <= 0.7) score += 5;
    if (score >= 65) return { level: 'LOW', score, color: '#2E7D32', label: 'Low Risk' };
    if (score >= 35) return { level: 'MEDIUM', score, color: '#E65100', label: 'Medium Risk' };
    return { level: 'HIGH', score, color: '#C62828', label: 'High Risk' };
}

async function getAiInsights(supabase, userId) {
    const now = new Date();
    const thirtyDaysAgo = new Date(Date.now() - 30 * 86400000).toISOString();

    const { data: invoices30d } = await supabase
        .from('invoices')
        .select('grand_total, created_at')
        .eq('user_id', userId)
        .gte('created_at', thirtyDaysAgo)
        .order('created_at', { ascending: true });

    const dailyMap = {};
    (invoices30d || []).forEach(inv => {
        const day = inv.created_at.split('T')[0];
        dailyMap[day] = (dailyMap[day] || 0) + Number(inv.grand_total || 0);
    });
    const salesValues = Object.values(dailyMap);
    const avgDaily = salesValues.length > 0 ? salesValues.reduce((a, b) => a + b, 0) / salesValues.length : 0;
    const slope = linearTrend(salesValues);
    const trendDirection = slope > 50 ? 'UP' : slope < -50 ? 'DOWN' : 'STABLE';
    const trendPercent = avgDaily > 0 ? Math.round((slope / avgDaily) * 100) : 0;

    const { data: customers } = await supabase.from('customers').select('*').eq('user_id', userId);
    const customerRisks = [];

    for (const customer of customers || []) {
        const [{ data: ledger }, { data: custInvoices }] = await Promise.all([
            supabase.from('ledger').select('*').eq('customer_id', customer.id).order('tx_date', { ascending: true }),
            supabase.from('invoices').select('*').eq('customer_id', customer.id)
        ]);

        const totalGave = (ledger || []).filter(t => t.type === 'gave').reduce((s, t) => s + Number(t.amount), 0);
        const totalGot = (ledger || []).filter(t => t.type === 'got').reduce((s, t) => s + Number(t.amount), 0);
        const outstanding = totalGave - totalGot;
        const gotTxs = (ledger || []).filter(t => t.type === 'got');
        let avgDaysBetween = 30;
        if (gotTxs.length >= 2) {
            const spans = [];
            for (let i = 1; i < gotTxs.length; i++) {
                const d1 = new Date(gotTxs[i - 1].tx_date);
                const d2 = new Date(gotTxs[i].tx_date);
                if (!isNaN(d1) && !isNaN(d2)) spans.push(Math.abs((d2 - d1) / 86400000));
            }
            if (spans.length > 0) avgDaysBetween = spans.reduce((a, b) => a + b, 0) / spans.length;
        }
        const risk = classifyCreditRisk(totalGave, totalGot, (ledger || []).length, avgDaysBetween, Math.max(totalGave, 1));
        const lastTx = ledger?.[ledger.length - 1];
        const lastActivityDays = lastTx ? Math.floor((now - new Date(lastTx.tx_date)) / 86400000) : 999;

        customerRisks.push({
            customerId: customer.id,
            customerName: customer.name,
            phone: customer.phone,
            riskLevel: risk.level,
            riskScore: risk.score,
            riskColor: risk.color,
            riskLabel: risk.label,
            outstanding: Math.round(outstanding),
            totalGave: Math.round(totalGave),
            totalGot: Math.round(totalGot),
            txCount: (ledger || []).length,
            avgPaymentDays: Math.round(avgDaysBetween),
            lastActivityDays,
            invoiceCount: (custInvoices || []).length
        });
    }

    customerRisks.sort((a, b) => {
        const order = { HIGH: 0, MEDIUM: 1, LOW: 2 };
        return order[a.riskLevel] - order[b.riskLevel];
    });

    const { data: inventory } = await supabase.from('inventory').select('*').eq('user_id', userId).order('stock_level', { ascending: true });
    const { data: expenseRows } = await supabase.from('expenses').select('category, amount').eq('user_id', userId).gte('expense_date', thirtyDaysAgo.split('T')[0]);

    const expenseByCategory = {};
    (expenseRows || []).forEach(e => {
        if (!expenseByCategory[e.category]) expenseByCategory[e.category] = { category: e.category, total: 0, count: 0 };
        expenseByCategory[e.category].total += Number(e.amount);
        expenseByCategory[e.category].count += 1;
    });

    const totalExpenses30d = Object.values(expenseByCategory).reduce((s, e) => s + e.total, 0);
    const totalRevenue30d = (invoices30d || []).reduce((s, i) => s + Number(i.grand_total || 0), 0);
    const profitMargin30d = totalRevenue30d > 0 ? Math.round(((totalRevenue30d - totalExpenses30d) / totalRevenue30d) * 100) : 0;

    const suggestions = [];
    if (trendDirection === 'DOWN') suggestions.push({ type: 'WARNING', icon: 'TrendingDown', message: `Sales declining ${Math.abs(trendPercent)}% — consider promotions.` });
    if (trendDirection === 'UP') suggestions.push({ type: 'POSITIVE', icon: 'TrendingUp', message: `Sales growing ${trendPercent}% — great momentum!` });
    const highRiskCount = customerRisks.filter(c => c.riskLevel === 'HIGH').length;
    if (highRiskCount > 0) suggestions.push({ type: 'ALERT', icon: 'Warning', message: `${highRiskCount} customer(s) flagged HIGH credit risk.` });

    return {
        generatedAt: now.toISOString(),
        forecast: {
            avgDailySales: Math.round(avgDaily),
            next7Days: Math.max(0, Math.round((avgDaily + slope * 3) * 7)),
            next30Days: Math.max(0, Math.round((avgDaily + slope * 15) * 30)),
            trendDirection,
            trendPercent,
            dataPoints: salesValues.length
        },
        creditRisk: {
            summary: {
                high: customerRisks.filter(c => c.riskLevel === 'HIGH').length,
                medium: customerRisks.filter(c => c.riskLevel === 'MEDIUM').length,
                low: customerRisks.filter(c => c.riskLevel === 'LOW').length,
                total: customerRisks.length
            },
            customers: customerRisks
        },
        inventory: { topItems: [], restockAlerts: (inventory || []).filter(i => i.stock_level < 10).map(i => ({ ...i, urgency: i.stock_level < 3 ? 'CRITICAL' : 'HIGH' })) },
        expenses: {
            byCategory: Object.values(expenseByCategory),
            totalLast30d: Math.round(totalExpenses30d),
            revenueLast30d: Math.round(totalRevenue30d),
            profitMargin: profitMargin30d
        },
        suggestions
    };
}

module.exports = { getAiInsights };
