import { Header, Navigation } from '../components/Navigation';

export function SalesForecastPage() {
  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">Sales Forecast</h1>
      </header>
      
      <main class="content-area">
        <section class="forecast-overview card mb-lg">
          <div class="text-xs text-secondary">PROJECTED SALES (NEXT MONTH)</div>
          <div class="flex items-center gap-md">
            <div class="text-xl font-bold">₹ 5,12,000</div>
            <div class="text-success text-sm">+24.8% ↑</div>
          </div>
          <p class="text-xs text-secondary mt-sm">Based on AI analysis of historical data and market trends.</p>
        </section>
        
        <section class="forecast-chart card mb-lg">
          <h3 class="section-title mb-md">Sales Trend & Prediction</h3>
          <div class="chart-container" style="position: relative; height:200px; width:100%">
             <canvas id="forecastChart"></canvas>
          </div>
        </section>
        
        <section class="ai-recommendations">
          <h3 class="section-title mb-md">AI Optimization Tips</h3>
          <div class="recommendation-card card mb-sm">
            <div class="flex gap-md">
              <div class="rec-icon text-primary"><i class="ph ph-shopping-cart" style="font-size: 24px;"></i></div>
              <div>
                <div class="font-bold">Stock Up: Dairy</div>
                <p class="text-xs text-secondary mt-xs">Demand for milk and cheese is expected to rise by 15% next weekend due to local festivals.</p>
              </div>
            </div>
          </div>
          <div class="recommendation-card card">
            <div class="flex gap-md">
              <div class="rec-icon text-success"><i class="ph ph-currency-inr" style="font-size: 24px;"></i></div>
              <div>
                <div class="font-bold">Clearance Opportunity</div>
                <p class="text-xs text-secondary mt-xs">Organic Brown Eggs are moving slowly. A 10% discount could clear stock in 2 days.</p>
              </div>
            </div>
          </div>
        </section>
      </main>
      ${Navigation('reports')}
    </div>
  `;
}

SalesForecastPage.init = () => {
  const ctx = document.getElementById('forecastChart');
  if (ctx && typeof Chart !== 'undefined') {
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May (Est)', 'Jun (Pred)'],
        datasets: [
          {
            label: 'Historical Sales',
            data: [300000, 320000, 310000, 420000, null, null],
            borderColor: '#303f9f',
            segment: {
              borderDash: ctx => [0, 0]
            },
            tension: 0.3
          },
          {
            label: 'Predicted Sales',
            data: [null, null, null, 420000, 480000, 512000],
            borderColor: '#ff9800',
            borderDash: [5, 5],
            tension: 0.3
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom',
            labels: { boxWidth: 12 }
          }
        },
        scales: {
          y: { beginAtZero: true, ticks: { callback: (v) => '₹' + v/1000 + 'k' } }
        }
      }
    });
  }
};
