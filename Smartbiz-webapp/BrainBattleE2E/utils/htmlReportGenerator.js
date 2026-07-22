const fs = require('fs');
const path = require('path');

function generateHTML(results) {
    const total = results.length;
    const passed = results.filter(r => r.status === 'PASSED').length;
    const failed = total - passed;
    const passRate = ((passed / total) * 100).toFixed(1);

    const html = `
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SmartBiz E2E Execution Report</title>
    <style>
        :root {
            --bg: #0f172a;
            --card: #1e293b;
            --text: #f8fafc;
            --primary: #38bdf8;
            --success: #22c55e;
            --danger: #ef4444;
        }
        body { font-family: 'Inter', system-ui, sans-serif; background: var(--bg); color: var(--text); padding: 2rem; margin: 0; }
        .container { max-width: 1200px; margin: 0 auto; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1.5rem; margin-bottom: 2rem; }
        .stat-card { background: var(--card); padding: 1.5rem; border-radius: 12px; border: 1px solid #334155; text-align: center; }
        .stat-val { font-size: 2.5rem; font-weight: 800; color: var(--primary); }
        .stat-label { color: #94a3b8; font-size: 0.875rem; margin-top: 0.5rem; text-transform: uppercase; }
        .table-container { background: var(--card); border-radius: 12px; border: 1px solid #334155; overflow: hidden; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #334155; padding: 1rem; text-align: left; font-size: 0.875rem; }
        td { padding: 1rem; border-top: 1px solid #334155; font-size: 0.875rem; }
        .badge { padding: 4px 8px; border-radius: 4px; font-weight: 600; font-size: 0.75rem; }
        .badge-passed { background: rgba(34, 197, 94, 0.2); color: var(--success); }
        .badge-failed { background: rgba(239, 68, 68, 0.2); color: var(--danger); }
        .error-stack { font-family: monospace; color: #ef4444; margin-top: 8px; display: block; font-size: 0.75rem; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🚀 SmartBiz Mega E2E Report</h1>
            <div class="timestamp">${new Date().toLocaleString()}</div>
        </div>
        
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-val">${total}</div>
                <div class="stat-label">Total Tests</div>
            </div>
            <div class="stat-card">
                <div class="stat-val" style="color: var(--success)">${passed}</div>
                <div class="stat-label">Passed</div>
            </div>
            <div class="stat-card">
                <div class="stat-val" style="color: var(--danger)">${failed}</div>
                <div class="stat-label">Failed</div>
            </div>
            <div class="stat-card">
                <div class="stat-val">${passRate}%</div>
                <div class="stat-label">Pass Rate</div>
            </div>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Test Case</th>
                        <th>Category</th>
                        <th>Status</th>
                        <th>Duration</th>
                    </tr>
                </thead>
                <tbody>
                    ${results.slice(0, 100).map(res => `
                        <tr>
                            <td>
                                <strong>${res.title}</strong>
                                ${res.error ? `<span class="error-stack">${res.error}</span>` : ''}
                            </td>
                            <td>${res.type}</td>
                            <td><span class="badge badge-${res.status.toLowerCase()}">${res.status}</span></td>
                            <td>${res.duration}ms</td>
                        </tr>
                    `).join('')}
                    ${total > 100 ? `<tr><td colspan="4" style="text-align: center; color: #94a3b8;">Showing first 100 of ${total} tests...</td></tr>` : ''}
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
    `;

    const reportPath = path.join(process.cwd(), 'execution-report.html');
    fs.writeFileSync(reportPath, html);
    console.log(`HTML report generated: ${reportPath}`);
}

module.exports = { generateHTML };
