import fs from 'fs-extra';
import path from 'path';

export default async function generateHtmlReport(results, stats) {
  const categories = [...new Set(results.map(r => r.category))];
  const catData = categories.map(cat => {
    const tests = results.filter(r => r.category === cat);
    return {
      name: cat,
      passed: tests.filter(r => r.status === 'PASS').length,
      failed: tests.filter(r => r.status === 'FAIL').length,
    };
  });

  const htmlContent = `
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SmartBiz E2E Execution Report</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg: #0f172a;
            --card-bg: #1e293b;
            --text-primary: #f8fafc;
            --text-secondary: #94a3b8;
            --success: #22c55e;
            --danger: #ef4444;
            --warning: #f59e0b;
            --accent: #3b82f6;
        }
        body {
            background-color: var(--bg);
            color: var(--text-primary);
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 2rem;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            border-bottom: 1px solid #334155;
            padding-bottom: 1rem;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        .stat-card {
            background: var(--card-bg);
            padding: 1.5rem;
            border-radius: 0.75rem;
            border: 1px solid #334155;
            text-align: center;
        }
        .stat-value {
            font-size: 2.5rem;
            font-weight: 700;
            margin: 0.5rem 0;
        }
        .stat-label {
            color: var(--text-secondary);
            font-size: 0.875rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }
        .charts {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        .chart-card {
            background: var(--card-bg);
            padding: 1.5rem;
            border-radius: 0.75rem;
            border: 1px solid #334155;
        }
        .table-container {
            background: var(--card-bg);
            border-radius: 0.75rem;
            border: 1px solid #334155;
            overflow: hidden;
            margin-bottom: 2rem;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #334155;
        }
        th {
            background: #2d3748;
            color: var(--text-secondary);
            font-weight: 600;
        }
        tr:hover {
            background: #2d3748;
        }
        .badge {
            padding: 0.25rem 0.625rem;
            border-radius: 9999px;
            font-size: 0.75rem;
            font-weight: 600;
        }
        .badge-pass { background: rgba(34, 197, 94, 0.2); color: var(--success); }
        .badge-fail { background: rgba(239, 68, 68, 0.2); color: var(--danger); }
        .error-stack {
            font-family: monospace;
            font-size: 0.75rem;
            background: #000;
            padding: 1rem;
            border-radius: 0.5rem;
            margin-top: 0.5rem;
            color: #ef4444;
            max-height: 200px;
            overflow-y: auto;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>SmartBiz E2E Results</h1>
            <div>
                <span class="badge" style="background: var(--accent)">Build #${process.env.GITHUB_RUN_NUMBER || 'LOCAL'}</span>
                <span class="badge" style="background: #334155">${new Date().toLocaleString()}</span>
            </div>
        </div>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Total Tests</div>
                <div class="stat-value">${stats.tests}</div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Passed</div>
                <div class="stat-value" style="color: var(--success)">${stats.passes}</div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Failed</div>
                <div class="stat-value" style="color: var(--danger)">${stats.failures}</div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Pass Rate</div>
                <div class="stat-value" style="color: var(--accent)">${((stats.passes / stats.tests) * 100).toFixed(1)}%</div>
            </div>
        </div>

        <div class="charts">
            <div class="chart-card">
                <canvas id="passFailChart"></canvas>
            </div>
            <div class="chart-card">
                <canvas id="categoryChart"></canvas>
            </div>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Test Case</th>
                        <th>Status</th>
                        <th>Duration</th>
                    </tr>
                </thead>
                <tbody>
                    ${results.map(r => `
                        <tr>
                            <td>${r.category}</td>
                            <td>
                                <div>${r.title}</div>
                                ${r.error ? '<div class="error-stack">' + r.stack + '</div>' : ''}
                            </td>
                            <td><span class="badge ${r.status === 'PASS' ? 'badge-pass' : 'badge-fail'}">${r.status}</span></td>
                            <td>${r.duration}ms</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    </div>

    <script>
        // Pass/Fail Chart
        new Chart(document.getElementById('passFailChart'), {
            type: 'doughnut',
            data: {
                labels: ['Passed', 'Failed'],
                datasets: [{
                    data: [${stats.passes}, ${stats.failures}],
                    backgroundColor: ['#22c55e', '#ef4444'],
                    borderWidth: 0
                }]
            },
            options: {
                plugins: {
                    title: { display: true, text: 'Overall Status', color: '#f8fafc' },
                    legend: { labels: { color: '#f8fafc' } }
                }
            }
        });

        // Category Chart
        new Chart(document.getElementById('categoryChart'), {
            type: 'bar',
            data: {
                labels: ${JSON.stringify(catData.map(c => c.name))},
                datasets: [
                    {
                        label: 'Passed',
                        data: ${JSON.stringify(catData.map(c => c.passed))},
                        backgroundColor: '#22c55e'
                    },
                    {
                        label: 'Failed',
                        data: ${JSON.stringify(catData.map(c => c.failed))},
                        backgroundColor: '#ef4444'
                    }
                ]
            },
            options: {
                scales: {
                    y: { stacked: true, grid: { color: '#334155' }, ticks: { color: '#f8fafc' } },
                    x: { stacked: true, grid: { color: '#334155' }, ticks: { color: '#f8fafc' } }
                },
                plugins: {
                    title: { display: true, text: 'Results by Category', color: '#f8fafc' },
                    legend: { labels: { color: '#f8fafc' } }
                }
            }
        });
    </script>
</body>
</html>
  `;

  await fs.writeFile('Test_Results/HTML/execution-report.html', htmlContent);
}
