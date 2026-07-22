(function(){let e=document.createElement(`link`).relList;if(e&&e.supports&&e.supports(`modulepreload`))return;for(let e of document.querySelectorAll(`link[rel="modulepreload"]`))n(e);new MutationObserver(e=>{for(let t of e)if(t.type===`childList`)for(let e of t.addedNodes)e.tagName===`LINK`&&e.rel===`modulepreload`&&n(e)}).observe(document,{childList:!0,subtree:!0});function t(e){let t={};return e.integrity&&(t.integrity=e.integrity),e.referrerPolicy&&(t.referrerPolicy=e.referrerPolicy),e.crossOrigin===`use-credentials`?t.credentials=`include`:e.crossOrigin===`anonymous`?t.credentials=`omit`:t.credentials=`same-origin`,t}function n(e){if(e.ep)return;e.ep=!0;let n=t(e);fetch(e.href,n)}})();var e={en:{dashboard:`Dashboard`,customers:`Customers`,billing:`Billing`,inventory:`Inventory`,reports:`Reports`,settings:`Settings`,create_invoice:`Create Invoice`,add_customer:`Add Customer`,add_stock:`Add Stock`,total_sales:`Total Sales (Today)`,total_udhar:`Total Udhar`,stock_alerts:`Stock Alerts`,cash_in_hand:`Cash in Hand`,quick_actions:`Quick Actions`,recent_transactions:`Recent Transactions`,app_language:`App Language`,save_apply:`Save & Apply`,logout:`Logout Account`},hi:{dashboard:`डैशबोर्ड`,customers:`ग्राहक`,billing:`बिलिंग`,inventory:`इन्वेंटरी`,reports:`रिपोर्ट`,settings:`सेटिंग्स`,create_invoice:`चालान बनाएं`,add_customer:`ग्राहक जोड़ें`,add_stock:`स्टॉक जोड़ें`,total_sales:`कुल बिक्री (आज)`,total_udhar:`कुल उधार`,stock_alerts:`स्टॉक अलर्ट`,cash_in_hand:`नकद राशि`,quick_actions:`त्वरित कार्रवाई`,recent_transactions:`हाल के लेनदेन`,app_language:`ऐप की भाषा`,save_apply:`सहेजें और लागू करें`,logout:`खाता लॉगआउट करें`},te:{dashboard:`డాష్‌బోర్డ్`,customers:`కస్టమర్లు`,billing:`బిల్లింగ్`,inventory:`ఇన్వెంటరీ`,reports:`రిపోర్టులు`,settings:`సెట్టింగ్‌లు`,create_invoice:`ఇన్‌వాయిస్ సృష్టించండి`,add_customer:`కస్టమర్‌ను జోడించండి`,add_stock:`స్టాక్ జోడించండి`,total_sales:`మొత్తం అమ్మకాలు (ఈరోజు)`,total_udhar:`మొత్తం ఉధార్`,stock_alerts:`స్టాక్ హెచ్చరికలు`,cash_in_hand:`చేతిలో నగదు`,quick_actions:`శీఘ్ర చర్యలు`,recent_transactions:`ఇటీవలి లావాదేవీలు`,app_language:`యాప్ భాష`,save_apply:`సేవ్ చేసి వర్తింపజేయండి`,logout:`ఖాతా లాగ్అవుట్`},mr:{dashboard:`डॅशबोर्ड`,customers:`ग्राहक`,billing:`बिलिंग`,inventory:`इन्व्हेंटरी`,reports:`रिपोर्ट्स`,settings:`सेटिंग्ज`,create_invoice:`इनव्हॉइस बनवा`,add_customer:`ग्राहक जोडा`,add_stock:`स्टॉक जोडा`,total_sales:`एकूण विक्री (आज)`,total_udhar:`एकूण उधार`,stock_alerts:`स्टॉक अलर्ट`,cash_in_hand:`हातात रोकड`,quick_actions:`त्वरित कृती`,recent_transactions:`अलीकडील व्यवहार`,app_language:`अ‍ॅपची भाषा`,save_apply:`सेव्ह करा आणि लागू करा`,logout:`खात्यातून बाहेर पडा`},gu:{dashboard:`ડેશબોર્ડ`,customers:`ગ્રાહકો`,billing:`બિલિંગ`,inventory:`ઇન્વેન્ટરી`,reports:`રિપોર્ટ્સ`,settings:`સેટિંગ્સ`,create_invoice:`ઇન્વૉઇસ બનાવો`,add_customer:`ગ્રાહક ઉમેરો`,add_stock:`સ્ટોક ઉમેરો`,total_sales:`કુલ વેચાણ (આજે)`,total_udhar:`કુલ ઉધાર`,stock_alerts:`સ્ટોક એલર્ટ`,cash_in_hand:`રોકડ રકમ`,quick_actions:`ઝડપી પગલાં`,recent_transactions:`તાજેતરના વ્યવહારો`,app_language:`એપ્લિકેશન ભાષા`,save_apply:`સાચવો અને લાગુ કરો`,logout:`ખાતું લોગઆઉટ કરો`},ta:{dashboard:`டாஷ்போர்டு`,customers:`வாடிக்கையாளர்கள்`,billing:`பில்லிங்`,inventory:`இருப்பு`,reports:`அறிக்கைகள்`,settings:`அமைப்புகள்`,create_invoice:`விலைப்பட்டியல் உருவாக்கவும்`,add_customer:`வாடிக்கையாளரைச் சேர்க்கவும்`,add_stock:`சரக்கு சேர்க்கவும்`,total_sales:`மொத்த விற்பனை (இன்று)`,total_udhar:`மொத்த கடன்`,stock_alerts:`சரக்கு எச்சரிக்கைகள்`,cash_in_hand:`கையில் உள்ள ரொக்கம்`,quick_actions:`விரைவான செயல்கள்`,recent_transactions:`சமீபத்திய பரிவர்த்தனைகள்`,app_language:`பயன்பாட்டு மொழி`,save_apply:`சேமித்து பயன்படுத்து`,logout:`கணக்கிலிருந்து வெளியேறு`}};function t(t){return e[localStorage.getItem(`app_lang`)||`en`]?.[t]||e.en[t]||t}function n(e){return`
    <nav class="bottom-nav">
      ${[{id:`dashboard`,label:t(`dashboard`),icon:`<i class="ph ph-house"></i>`,path:`#dashboard`},{id:`customers`,label:t(`customers`),icon:`<i class="ph ph-users"></i>`,path:`#customers`},{id:`billing`,label:t(`billing`),icon:`<i class="ph ph-receipt"></i>`,path:`#billing`},{id:`inventory`,label:t(`inventory`),icon:`<i class="ph ph-package"></i>`,path:`#inventory`},{id:`reports`,label:t(`reports`),icon:`<i class="ph ph-chart-bar"></i>`,path:`#reports`}].map(t=>{let n=window.location.hash,r=e===t.id||n.startsWith(t.path)&&t.path!==`#`;return`
          <a href="${t.path}" class="nav-item ${r?`active`:``}" id="nav-${t.id}">
            <span class="nav-icon">${t.icon}</span>
            <span class="nav-label">${t.label}</span>
          </a>
        `}).join(``)}
    </nav>
  `}function r(e){return`
    <header class="app-header">
      <div class="header-left">
        <h1 class="header-title">${t(e.toLowerCase().replace(` `,`_`))||e}</h1>
      </div>
      <div class="header-right">
        <button class="icon-btn" onclick="window.location.hash='#settings/notifications'"><i class="ph ph-bell" style="font-size: 20px;"></i></button>
        <a href="#settings" class="user-avatar text-primary"><i class="ph ph-user-circle" style="font-size: 24px;"></i></a>
      </div>
    </header>
  `}var i=`https://smartbiz-backend-c7ru.onrender.com/api`;async function a(e,{method:t=`GET`,body:n,...r}={}){let a={method:t,headers:{"Content-Type":`application/json`},...r};n&&(a.body=JSON.stringify(n));try{let t=await fetch(`${i}${e}`,a),n=await t.json();if(!t.ok)throw Error(n.error||`API request failed`);return n}catch(t){throw console.error(`API Error on ${e}:`,t),t}}var o={login:e=>a(`/auth/login`,{method:`POST`,body:e}),register:e=>a(`/auth/register`,{method:`POST`,body:e}),getDashboardStats:()=>a(`/dashboard/stats`),getInventory:()=>a(`/inventory`),addInventoryItem:e=>a(`/inventory`,{method:`POST`,body:e}),deleteInventoryItem:e=>a(`/inventory/${e}`,{method:`DELETE`}),getCustomers:()=>a(`/customers`),addCustomer:e=>a(`/customers`,{method:`POST`,body:e}),getCustomerLedger:e=>a(`/ledger/${e}`),addLedgerEntry:e=>a(`/ledger`,{method:`POST`,body:e}),getInvoices:()=>a(`/invoices`),createInvoice:e=>a(`/invoices`,{method:`POST`,body:e}),getExpenses:()=>a(`/expenses`),addExpense:e=>a(`/expenses`,{method:`POST`,body:e})};function s(){return`
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <div class="auth-logo">SB</div>
          <h2>Login to your Business</h2>
          <p>Enter your details to manage your store</p>
        </div>
        
        <form id="login-form" class="auth-form">
          <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" class="w-full p-2 border rounded mt-xs" placeholder="admin@smartbiz.com" value="admin@smartbiz.com" required>
          </div>
          <div class="form-group mt-sm">
            <label for="password">4-Digit Security PIN</label>
            <input type="password" id="password" class="w-full p-2 border rounded mt-xs" placeholder="1234" value="1234" maxlength="4" pattern="[0-9]{4}" required>
          </div>
          
          <div class="text-xs text-secondary mt-xs mb-md">Test Data Provided: admin@smartbiz.com / 1234</div>
          
          <button type="submit" class="btn btn-primary w-full">Login to Dashboard</button>
          
          <div class="text-center mt-md">
            <span class="text-sm">Don't have an account? <a href="#register" class="text-primary font-medium" style="text-decoration: underline;">Register here</a></span>
          </div>

          <div class="auth-footer mt-md">
            <label class="checkbox-container">
              <input type="checkbox" checked>
              <span class="text-sm">I agree to the <a href="#">Terms and Conditions</a> and the <a href="#">Privacy Policy</a> of SmartBiz.</span>
            </label>
          </div>
        </form>
        
        <div class="auth-badges mt-lg">
          <div class="badge-item">🔒 Secure SSL Encryption</div>
          <div class="badge-item">✅ Trusted by 10k+ SMBs</div>
        </div>
      </div>
    </div>
  `}s.init=()=>{let e=document.getElementById(`login-form`);e&&e.addEventListener(`submit`,async t=>{t.preventDefault();let n=document.getElementById(`email`).value,r=document.getElementById(`password`).value,i=e.querySelector(`button[type="submit"]`);if(n&&r)try{if(i.textContent=`Authenticating...`,i.disabled=!0,n===`admin@smartbiz.com`&&r===`1234`)try{await o.register({email:`admin@smartbiz.com`,password:`1234`,phone:`0000000000`,merchant_name:`SmartBiz Demo Business`})}catch{}let e=await o.login({email:n,password:r});e.user&&(localStorage.setItem(`temp_email`,n),localStorage.setItem(`merchant_id`,e.user.id),localStorage.setItem(`merchant_name`,e.user.merchant_name||`Business Info`),window.location.hash=`#dashboard`)}catch(e){alert(e.message||`Login failed. Please check credentials.`)}finally{i.textContent=`Login to Dashboard`,i.disabled=!1}})};function c(){return`
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <div class="auth-logo">SB</div>
          <h2>Create an Account</h2>
          <p>Register your business to get started</p>
        </div>
        
        <form id="register-form" class="auth-form">
          <div class="form-group">
            <label for="merchant_name">Business Name</label>
            <input type="text" id="merchant_name" class="w-full p-2 border rounded mt-xs" placeholder="SmartBiz Store" required>
          </div>
          <div class="form-group mt-sm">
            <label for="reg-email">Email Address</label>
            <input type="email" id="reg-email" class="w-full p-2 border rounded mt-xs" placeholder="admin@smartbiz.com" required>
          </div>
          <div class="form-group mt-sm">
            <label for="phone">Phone Number</label>
            <input type="tel" id="phone" class="w-full p-2 border rounded mt-xs" placeholder="9876543210" required>
          </div>
          <div class="form-group mt-sm">
            <label for="reg-password">4-Digit Security PIN</label>
            <input type="password" id="reg-password" class="w-full p-2 border rounded mt-xs" placeholder="1234" maxlength="4" pattern="[0-9]{4}" required>
          </div>
          
          <button type="submit" class="btn btn-primary w-full mt-md">Register Account</button>
          
          <div class="text-center mt-md">
            <span class="text-sm">Already have an account? <a href="#login" class="text-primary font-medium" style="text-decoration: underline;">Login here</a></span>
          </div>
        </form>
      </div>
    </div>
  `}c.init=()=>{let e=document.getElementById(`register-form`);e&&e.addEventListener(`submit`,async t=>{t.preventDefault();let n=document.getElementById(`merchant_name`).value,r=document.getElementById(`reg-email`).value,i=document.getElementById(`phone`).value,a=document.getElementById(`reg-password`).value,s=e.querySelector(`button[type="submit"]`);if(r&&a&&n)try{s.textContent=`Registering...`,s.disabled=!0,await o.register({email:r,password:a,phone:i,merchant_name:n}),alert(`Registration successful! You can now log in.`),window.location.hash=`#login`}catch(e){alert(e.message||`Registration failed. This email or phone might already be in use.`)}finally{s.textContent=`Register Account`,s.disabled=!1}})};function l(){return`
    <div class="auth-container">
      <div class="auth-card">
        <header class="app-header-auth">
          <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        </header>
        
        <div class="auth-header mt-md">
          <h2>Verify Phone</h2>
          <p>We've sent a 6-digit code to <strong id="display-phone">+91 00000 00000</strong></p>
        </div>
        
        <form id="otp-form" class="auth-form">
          <div class="otp-input-container">
            <input type="tel" maxlength="1" class="otp-digit" required>
            <input type="tel" maxlength="1" class="otp-digit" required>
            <input type="tel" maxlength="1" class="otp-digit" required>
            <input type="tel" maxlength="1" class="otp-digit" required>
            <input type="tel" maxlength="1" class="otp-digit" required>
            <input type="tel" maxlength="1" class="otp-digit" required>
          </div>
          
          <button type="submit" class="btn btn-primary w-full mt-lg">Verify & Continue</button>
          
          <div class="auth-footer text-center">
            <p class="text-sm">Didn't receive code? <a href="#">Resend in <span id="timer">0:45</span></a></p>
          </div>
        </form>
      </div>
    </div>
  `}l.init=()=>{let e=localStorage.getItem(`temp_phone`)||`98765 43210`,t=document.getElementById(`display-phone`);t&&(t.textContent=`+91 ${e}`);let n=document.querySelectorAll(`.otp-digit`);n.forEach((e,t)=>{e.addEventListener(`input`,e=>{e.target.value&&t<n.length-1&&n[t+1].focus()}),e.addEventListener(`keydown`,e=>{e.key===`Backspace`&&!e.target.value&&t>0&&n[t-1].focus()})});let r=document.getElementById(`otp-form`);r&&r.addEventListener(`submit`,e=>{e.preventDefault(),window.location.hash=`#dashboard`});let i=45,a=document.getElementById(`timer`),o=setInterval(()=>{if(i--,i<=0)clearInterval(o),a&&(a.parentElement.textContent=`Resend Code`);else{let e=Math.floor(i/60),t=i%60;a&&(a.textContent=`${e}:${t.toString().padStart(2,`0`)}`)}},1e3);window.addEventListener(`hashchange`,()=>clearInterval(o),{once:!0})};function u(){return`
    <div class="page-container">
      ${r(`Dashboard`)}
      
      <main class="content-area">
        <section class="kpi-grid">
          <div class="kpi-card" style="background: var(--primary); color: white;">
            <div class="flex justify-between">
              <span class="kpi-label">${t(`total_sales`)}</span>
              <span class="kpi-icon"><i class="ph ph-chart-line-up"></i></span>
            </div>
            <div class="kpi-value" id="kpi-total-sales">₹ 0</div>
            <div class="kpi-trend">Today</div>
          </div>
          
          <div class="kpi-card" style="background: #FFE0B2; color: #E65100;">
            <div class="flex justify-between">
              <span class="kpi-label">${t(`total_udhar`)}</span>
              <span class="kpi-icon"><i class="ph ph-briefcase"></i></span>
            </div>
            <div class="kpi-value" id="kpi-total-udhar">₹ 0</div>
          </div>
          
          <div class="kpi-card" style="background: #FFCDD2; color: #B71C1C;">
            <div class="flex justify-between">
              <span class="kpi-label">${t(`stock_alerts`)}</span>
              <span class="kpi-icon"><i class="ph ph-warning-circle"></i></span>
            </div>
            <div class="kpi-value" id="kpi-stock-alerts">0 Items Low</div>
          </div>
          
          <div class="kpi-card" style="background: #C8E6C9; color: #1B5E20;">
            <div class="flex justify-between">
              <span class="kpi-label">${t(`cash_in_hand`)}</span>
              <span class="kpi-icon"><i class="ph ph-money"></i></span>
            </div>
            <div class="kpi-value" id="kpi-cash-hand">₹ 0</div>
          </div>
        </section>
        
        <section class="quick-actions mt-lg">
          <h3 class="section-title">${t(`quick_actions`)}</h3>
          <div class="actions-grid mt-sm">
            <button class="action-btn" id="btn-add-customer">
              <span class="action-icon"><i class="ph ph-user-plus"></i></span>
              <span>${t(`add_customer`)}</span>
            </button>
            <button class="action-btn" id="btn-new-invoice">
              <span class="action-icon"><i class="ph ph-file-text"></i></span>
              <span>${t(`create_invoice`)}</span>
            </button>
            <button class="action-btn" id="btn-add-stock">
              <span class="action-icon"><i class="ph ph-package"></i></span>
              <span>${t(`add_stock`)}</span>
            </button>
            <button class="action-btn" id="btn-check-reports">
              <span class="action-icon"><i class="ph ph-chart-bar"></i></span>
              <span>${t(`reports`)}</span>
            </button>
          </div>
        </section>
        
        <section class="charts-section mt-lg">
          <div class="card">
            <div class="flex justify-between items-center mb-md">
              <h3 class="section-title">Weekly Sales Trend</h3>
              <select class="select-sm">
                <option>Last 7 Days</option>
              </select>
            </div>
            <div class="chart-placeholder">
              <!-- Placeholder for Chart.js or CSS-based chart -->
              <div class="bar-chart">
                <div class="bar" style="height: 40%"></div>
                <div class="bar" style="height: 60%"></div>
                <div class="bar" style="height: 35%"></div>
                <div class="bar" style="height: 80%"></div>
                <div class="bar" style="height: 55%"></div>
                <div class="bar" style="height: 90%"></div>
                <div class="bar" style="height: 70%"></div>
              </div>
              <div class="chart-labels">
                <span>Mon</span><span>Tue</span><span>Wed</span><span>Thu</span><span>Fri</span><span>Sat</span><span>Sun</span>
              </div>
            </div>
          </div>
        </section>
        
        <section class="recent-transactions mt-lg mb-xxl">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title">Recent Transactions</h3>
            <a href="#customers/ledger" class="text-sm">View All</a>
          </div>
          <div class="transaction-list" id="dashboard-recent-tx">
            <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading...</div>
          </div>
        </section>
      </main>
      
      ${n(`dashboard`)}
    </div>
  `}u.init=async()=>{document.getElementById(`btn-add-customer`)?.addEventListener(`click`,()=>{window.location.hash=`#customers`}),document.getElementById(`btn-new-invoice`)?.addEventListener(`click`,()=>{window.location.hash=`#billing`}),document.getElementById(`btn-add-stock`)?.addEventListener(`click`,()=>{window.location.hash=`#inventory/add`}),document.getElementById(`btn-check-reports`)?.addEventListener(`click`,()=>{window.location.hash=`#reports`});try{let e=await o.getDashboardStats();document.getElementById(`kpi-total-sales`).textContent=`₹ ${e.salesToday.toLocaleString()}`,document.getElementById(`kpi-total-udhar`).textContent=`₹ ${e.udharTotal.toLocaleString()}`,document.getElementById(`kpi-stock-alerts`).textContent=`${e.lowStockCount} Items Low`,document.getElementById(`kpi-cash-hand`).textContent=`₹ ${e.salesToday.toLocaleString()}`;let t=document.getElementById(`dashboard-recent-tx`);e.recentTransactions&&e.recentTransactions.length>0?t.innerHTML=e.recentTransactions.map(e=>`
        <div class="transaction-item">
          <div class="tx-icon">${e.type===`got`?`<i class="ph ph-trend-up text-success"></i>`:`<i class="ph ph-trend-down text-error"></i>`}</div>
          <div class="tx-details">
            <div class="tx-name">Ledger ID: ${e.customer_id}</div>
            <div class="tx-time">${new Date(e.tx_date).toLocaleString()}</div>
          </div>
          <div class="tx-amount ${e.type===`got`?`plus`:`minus`}">
            ${e.type===`got`?`+`:`-`} ₹ ${e.amount.toLocaleString()}
          </div>
        </div>
      `).join(``):t.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No recent transactions.</div>`}catch(e){console.error(`Failed to load dashboard stats:`,e)}};function d(){return`
    <div class="page-container">
      ${r(`Inventory`)}
      
      <main class="content-area">
        <div class="search-bar mb-md">
          <input type="text" placeholder="Search items, SKU, or brands..." class="search-input">
        </div>
        
        <div class="filter-chips mb-md">
          <span class="chip active">All</span>
          <span class="chip">Grocery</span>
          <span class="chip">Dairy</span>
          <span class="chip">Beverages</span>
        </div>
        
        <div class="inventory-list" id="inventory-list">
          <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading inventory...</div>
        </div>
        
        <button class="fab"><i class="ph ph-plus"></i></button>
      </main>
      
      ${n(`inventory`)}
    </div>
  `}d.init=async()=>{let e=document.querySelector(`.search-input`),t=document.querySelectorAll(`.chip`),n=document.getElementById(`inventory-list`),r=[],i=`all`,a=``,s=()=>{let e=r.filter(e=>{let t=(e.item_name||``).toLowerCase(),n=(e.sku||``).toLowerCase(),r=(e.category||``).toLowerCase(),o=t.includes(a)||n.includes(a),s=i===`all`||r.includes(i)||t.includes(i);return o&&s});if(e.length===0){n.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No items found.</div>`;return}n.innerHTML=e.map(e=>`
      <div class="inventory-item card mb-sm">
        <div class="item-info">
          <div class="item-name">${e.item_name}</div>
          <div class="item-sku">SKU: ${e.sku}</div>
        </div>
        <div class="item-stats">
          <div class="item-price">₹ ${e.sale_price}</div>
          <div class="item-stock ${e.stock_level<10?`text-error`:``}">
            ${e.stock_level} units
            ${e.stock_level<10?`<span class="status-badge">Low Stock</span>`:``}
          </div>
        </div>
      </div>
    `).join(``)};try{r=await o.getInventory(),s()}catch{n.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-error);">Failed to load inventory.</div>`}e?.addEventListener(`input`,e=>{a=e.target.value.toLowerCase(),s()}),t.forEach(e=>{e.addEventListener(`click`,()=>{t.forEach(e=>e.classList.remove(`active`)),e.classList.add(`active`),i=e.textContent.toLowerCase(),s()})});let c=document.querySelector(`.fab`);c&&c.addEventListener(`click`,()=>{window.location.hash=`#inventory/add`})};function f(){return`
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">Add New Item</h1>
      </header>
      
      <main class="content-area">
        <form class="product-form" id="add-product-form">
          <section class="card mb-md">
            <h3 class="section-title mb-sm">Basic Details</h3>
            <div class="form-group mb-md">
              <label>Item Name</label>
              <input type="text" id="p-name" placeholder="e.g. Premium Basmati Rice" class="input-full" required>
            </div>
            <div class="flex gap-md mb-md">
              <div class="form-group flex-1">
                <label>Category</label>
                <select id="p-cat" class="input-full">
                  <option>Grains & Pulses</option>
                  <option>Dairy</option>
                  <option>Beverages</option>
                </select>
              </div>
              <div class="form-group flex-1">
                <label>Unit</label>
                <select id="p-unit" class="input-full">
                  <option>kg</option>
                  <option>pcs</option>
                  <option>ltr</option>
                </select>
              </div>
            </div>
            <div class="flex gap-md">
              <div class="form-group flex-1">
                <label>HSN Code</label>
                <input type="text" id="p-hsn" placeholder="8-digit code" class="input-full">
              </div>
              <div class="form-group flex-1">
                <label>GST Rate (%)</label>
                <select id="p-gst" class="input-full">
                  <option value="0">0%</option>
                  <option value="5">5%</option>
                  <option value="12">12%</option>
                  <option value="18">18%</option>
                </select>
              </div>
            </div>
          </section>
          
          <section class="card mb-md">
            <h3 class="section-title mb-sm">Pricing Information</h3>
            <div class="flex gap-md">
              <div class="form-group flex-1">
                <label>Purchase Price (₹)</label>
                <input type="number" id="p-cost" placeholder="0.00" class="input-full" required>
              </div>
              <div class="form-group flex-1">
                <label>Sales Price (₹)</label>
                <input type="number" id="p-sale" placeholder="0.00" class="input-full" required>
              </div>
            </div>
          </section>
          
          <section class="card mb-lg">
            <h3 class="section-title mb-sm">Stock Inventory</h3>
            <div class="flex gap-md mb-md">
              <div class="form-group flex-1">
                <label>Opening Stock</label>
                <input type="number" id="p-stock" placeholder="0" class="input-full" required>
              </div>
              <div class="form-group flex-1">
                <label>Low Stock Alert Level</label>
                <input type="number" id="p-alert" placeholder="Notify when below..." class="input-full">
              </div>
            </div>
          </section>
          
          <div class="action-footer">
            <button type="button" class="btn btn-outline flex-1" onclick="window.history.back()">Cancel</button>
            <button type="submit" class="btn btn-primary flex-2">Save Product</button>
          </div>
        </form>
      </main>
      ${n(`inventory`)}
    </div>
  `}f.init=()=>{let e=document.getElementById(`add-product-form`);e&&e.addEventListener(`submit`,async t=>{t.preventDefault();let n={item_name:document.getElementById(`p-name`).value,category:document.getElementById(`p-cat`).value,sku:`SKU-`+Math.floor(Math.random()*1e6),hsn:document.getElementById(`p-hsn`).value,sale_price:parseFloat(document.getElementById(`p-sale`).value)||0,cost_price:parseFloat(document.getElementById(`p-cost`).value)||0,stock_level:parseInt(document.getElementById(`p-stock`).value)||0,unit:document.getElementById(`p-unit`).value,gst_rate:parseInt(document.getElementById(`p-gst`).value)||0,merchant_id:localStorage.getItem(`merchant_id`)||1},r=e.querySelector(`button[type="submit"]`);try{r.disabled=!0,r.textContent=`Saving...`,await o.addInventoryItem(n),alert(`Product "${n.item_name}" saved successfully!`),window.location.hash=`#inventory`}catch(e){alert(`Failed to save product: `+e.message)}finally{r.disabled=!1,r.textContent=`Save Product`}})};function p(){return`
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
        <h1 class="header-title">Create Invoice</h1>
        <div class="step-indicator">Step 2 of 3</div>
      </header>
      
      <main class="content-area">
        <section class="customer-selection card mb-lg">
          <div class="form-group">
            <label class="font-bold">Select Customer</label>
            <select id="billing-customer" class="input-full mt-xs">
              <option value="">Loading customers...</option>
            </select>
          </div>
        </section>
        
        <section class="invoice-items">
          <div class="flex gap-sm items-end mb-md">
            <div class="form-group flex-2">
              <label>Select Item to Add</label>
              <select id="billing-inventory" class="input-full mt-xs">
                 <option value="">Loading inventory...</option>
              </select>
            </div>
            <button class="btn btn-primary" id="btn-add-item"><i class="ph ph-plus"></i> Add</button>
          </div>
          
          <div class="items-list" id="invoice-items-list">
            <!-- Dynamic items will be injected here -->
          </div>
        </section>
        
        <section class="invoice-summary mt-lg">
          <div class="summary-card">
            <div class="summary-row">
              <span>Subtotal</span>
              <span id="subtotal">₹ 0.00</span>
            </div>
            <div class="summary-row">
              <span>Total GST</span>
              <span id="total-gst">₹ 0.00</span>
            </div>
            <div class="summary-row total">
              <span>Grand Total</span>
              <span id="grand-total">₹ 0.00</span>
            </div>
          </div>
        </section>
        
        <div class="action-footer">
          <button class="btn btn-outline flex-1">Save Draft</button>
          <button class="btn btn-primary flex-2" id="btn-checkout">Save & Preview</button>
        </div>
      </main>
      
      ${n(`billing`)}
    </div>
  `}p.init=async()=>{let e=[],t=[],n=[],r=document.getElementById(`invoice-items-list`),i=document.getElementById(`subtotal`),a=document.getElementById(`total-gst`),s=document.getElementById(`grand-total`),c=document.getElementById(`billing-customer`),l=document.getElementById(`billing-inventory`);try{n=await o.getCustomers(),c.innerHTML=`<option value="">-- Choose Customer --</option>`+n.map(e=>`<option value="${e.id}">${e.name}</option>`).join(``),t=await o.getInventory(),l.innerHTML=`<option value="">-- Choose Item --</option>`+t.filter(e=>e.stock_level>0).map(e=>`<option value="${e.id}">${e.item_name} (Stock: ${e.stock_level}) - ₹${e.sale_price}</option>`).join(``)}catch(e){console.error(`Failed to load data for billing`,e)}function u(){r.innerHTML=e.map(e=>{let t=e.qty*e.price,n=t*e.gst/100,r=t+n;return`
        <div class="item-entry card mb-sm" data-id="${e.id}">
          <div class="flex justify-between">
            <div>
              <div class="font-bold">${e.name}</div>
              <div class="text-xs text-secondary">HSN: ${e.hsn||`0000`}</div>
            </div>
            <button class="icon-btn text-error btn-remove-item" data-id="${e.id}"><i class="ph ph-trash"></i></button>
          </div>
          <div class="item-entry-grid mt-sm">
            <div class="entry-field">
              <span class="label">Qty:</span>
              <div class="qty-control">
                <button class="qty-btn minus" data-id="${e.id}">-</button>
                <span class="value">${e.qty}</span>
                <button class="qty-btn plus" data-id="${e.id}">+</button>
              </div>
            </div>
            <div class="entry-field">
              <span class="label">Price:</span>
              <span class="value">₹ ${e.price.toFixed(2)}</span>
            </div>
            <div class="entry-field">
              <span class="label">GST:</span>
              <span class="value">${e.gst}% (₹ ${n.toFixed(2)})</span>
            </div>
            <div class="entry-field">
              <span class="label">Total:</span>
              <span class="value font-bold text-primary">₹ ${r.toFixed(2)}</span>
            </div>
          </div>
        </div>
      `}).join(``),d(),f()}function d(){let t=0,n=0;e.forEach(e=>{let r=e.qty*e.price;t+=r,n+=r*e.gst/100});let r=t+n;i.dataset.value=t,a.dataset.value=n,s.dataset.value=r,i.textContent=`₹ ${t.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`,a.textContent=`₹ ${n.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`,s.textContent=`₹ ${r.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`}function f(){document.querySelectorAll(`.btn-remove-item`).forEach(t=>{t.addEventListener(`click`,()=>{let n=parseInt(t.dataset.id);e=e.filter(e=>e.id!==n),u()})}),document.querySelectorAll(`.qty-btn.plus`).forEach(t=>{t.addEventListener(`click`,()=>{let n=parseInt(t.dataset.id),r=e.find(e=>e.id===n);r&&(r.qty++,u())})}),document.querySelectorAll(`.qty-btn.minus`).forEach(t=>{t.addEventListener(`click`,()=>{let n=parseInt(t.dataset.id),r=e.find(e=>e.id===n);r&&r.qty>1&&(r.qty--,u())})})}document.getElementById(`btn-add-item`)?.addEventListener(`click`,()=>{let n=parseInt(l.value);if(isNaN(n))return alert(`Select an item first`);let r=e.find(e=>e.id===n);if(r){r.qty++,u();return}let i=t.find(e=>e.id===n);i&&(e.push({id:i.id,name:i.item_name,hsn:i.hsn,qty:1,price:i.sale_price||0,gst:i.gst_rate||0}),u())}),document.getElementById(`btn-checkout`)?.addEventListener(`click`,async()=>{let t=parseInt(c.value);if(isNaN(t))return alert(`Select a customer first`);if(!e.length)return alert(`Add items to the invoice`);let n=document.getElementById(`btn-checkout`);n.disabled=!0,n.textContent=`Saving...`;try{let n={invoice_number:`INV-`+Date.now(),customer_id:t,subtotal:parseFloat(i.dataset.value)||0,total_gst:parseFloat(a.dataset.value)||0,grand_total:parseFloat(s.dataset.value)||0,merchant_id:localStorage.getItem(`merchant_id`)||1,items:e.map(e=>({id:e.id,item_name:e.name,qty:e.qty,price:e.price,gst_rate:e.gst,subtotal:e.qty*e.price}))};await o.createInvoice(n),window.location.hash=`#payment-success`}catch(e){alert(`Failed: `+e.message),n.disabled=!1,n.textContent=`Save & Preview`}}),u()};function m(){return`
    <div class="page-container">
      ${r(`Customers`)}
      
      <main class="content-area">
        <div class="search-bar mb-md">
          <input type="text" placeholder="Search customers..." class="search-input" id="customer-search">
        </div>
        
        <div class="filter-chips mb-md">
          <span class="chip active">All</span>
          <span class="chip">You will Give</span>
          <span class="chip">You will Get</span>
        </div>
        
        <div class="customer-list" id="customer-list-container">
          <div style="padding: 1rem; text-align: center; color: var(--text-secondary);">Loading customers...</div>
        </div>
        
        <button class="fab" id="btn-add-customer-fab"><i class="ph ph-user-plus"></i></button>
      </main>
      
      ${n(`customers`)}
    </div>
  `}m.init=async()=>{let e=document.getElementById(`customer-search`),t=document.getElementById(`customer-list-container`),n=[],r=e=>{if(!e.length){t.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No customers found.</div>`;return}t.innerHTML=e.map(e=>`
      <div class="customer-item card mb-sm clickable-customer" data-id="${e.id}" data-name="${e.name}">
        <div class="flex justify-between items-center">
          <div class="flex items-center gap-md">
            <div class="avatar-sm"><i class="ph ph-user"></i></div>
            <div>
              <div class="font-bold">${e.name}</div>
              <div class="text-xs text-secondary">${e.phone||`No phone`}</div>
            </div>
          </div>
          <div class="customer-balance text-right">
            <div class="balance-amount text-secondary">Tap to view</div>
          </div>
        </div>
      </div>
    `).join(``),document.querySelectorAll(`.clickable-customer`).forEach(e=>{e.addEventListener(`click`,()=>{localStorage.setItem(`selected_customer_id`,e.dataset.id),localStorage.setItem(`selected_customer_name`,e.dataset.name),window.location.hash=`#customers/ledger`})})};try{n=await o.getCustomers(),r(n)}catch{t.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-error);">Failed to load customers.</div>`}e?.addEventListener(`input`,e=>{let t=e.target.value.toLowerCase();r(n.filter(e=>e.name.toLowerCase().includes(t)))}),document.getElementById(`btn-add-customer-fab`)?.addEventListener(`click`,()=>{let e=prompt(`Enter customer name:`);if(e){let t=prompt(`Enter phone:`);o.addCustomer({name:e,phone:t,merchant_id:localStorage.getItem(`merchant_id`)||1}).then(()=>(alert(`Customer added`),o.getCustomers())).then(e=>{n=e,r(n)}).catch(e=>alert(`Failed: `+e.message))}})};function h(){return`
    <div class="page-container">
      ${r(`Insights & Reports`)}
      
      <main class="content-area">
        <section class="ai-insights card mb-lg" style="background: linear-gradient(135deg, var(--primary), #303f9f); color: white;">
          <div class="flex items-center gap-sm mb-sm">
            <span class="ai-icon"><i class="ph ph-sparkle"></i></span>
            <h3 class="section-title">Data Insights</h3>
          </div>
          <p class="text-sm opacity-90 mb-md" id="ai-insight-text">Loading insights from server...</p>
          <button class="btn btn-secondary w-full" id="btn-reports-action">Create Order</button>
        </section>
        
        <section class="business-reports">
          <h3 class="section-title mb-md">Business Reports</h3>
          <div class="reports-grid">
            <a href="#reports/daybook" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-primary"><i class="ph ph-calendar-blank" style="font-size: 24px;"></i></span>
              <span class="report-name">Daybook</span>
            </a>
            <a href="#reports/pnl" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-success"><i class="ph ph-trend-up" style="font-size: 24px;"></i></span>
              <span class="report-name">P&L Statement</span>
            </a>
            <a href="#reports/tax" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-warning"><i class="ph ph-receipt" style="font-size: 24px;"></i></span>
              <span class="report-name">Tax & GST</span>
            </a>
            <a href="#reports/forecast" class="report-item card" style="text-decoration:none; color:inherit;">
              <span class="report-icon text-error"><i class="ph ph-trend-down" style="font-size: 24px;"></i></span>
              <span class="report-name">Sales Forecast</span>
            </a>
          </div>
        </section>
        
        <section class="credit-risk mt-lg">
          <div class="card">
            <h3 class="section-title mb-sm">Customer Credit Risk</h3>
            <div class="risk-summary flex items-center gap-md">
              <div class="risk-gauge">84%</div>
              <div class="text-xs text-secondary">OVERALL PORTFOLIO HEALTH</div>
            </div>
            <div class="mt-md" id="reports-customers">
              <div class="text-xs text-secondary">Loading portfolio health...</div>
            </div>
          </div>
        </section>
      </main>
      
      ${n(`reports`)}
    </div>
  `}h.init=async()=>{document.getElementById(`btn-reports-action`)?.addEventListener(`click`,()=>{window.location.hash=`#inventory/add`});try{let e=await o.getInvoices(),t=document.getElementById(`ai-insight-text`);if(t&&e&&e.length){let n=e[0];t.textContent=`You have ${e.length} total invoices on record. Latest invoice amount: ₹${n.grand_total}.`}let n=await o.getCustomers(),r=document.getElementById(`reports-customers`);r&&n&&n.length?r.innerHTML=n.slice(0,3).map(e=>`
         <div class="flex justify-between items-center mb-sm">
           <span class="text-sm">${e.name}</span>
           <span class="risk-badge low-risk">Low Risk</span>
         </div>
       `).join(``):r&&(r.innerHTML=`<div class="text-xs text-secondary">No customers found</div>`)}catch(e){console.error(`Failed to load reports data`,e)}};function g(){let e=[{id:`business_profile`,label:`Business Profile`,icon:`<i class="ph ph-buildings"></i>`,sub:`Details, Logo, GSTIN`},{id:`staff`,label:`Staff Management`,icon:`<i class="ph ph-users"></i>`,sub:`Roles, Permissions`},{id:`printer`,label:`Printer Settings`,icon:`<i class="ph ph-printer"></i>`,sub:`Bluetooth, Thermal Printer`},{id:`language`,label:t(`app_language`),icon:`<i class="ph ph-globe"></i>`,sub:`English, Hindi, Telugu, etc.`},{id:`notifications`,label:`Notifications`,icon:`<i class="ph ph-bell"></i>`,sub:`Alerts, Reminders`},{id:`help`,label:`Help & Support`,icon:`<i class="ph ph-question"></i>`,sub:`FAQs, Contact Us`}];return`
    <div class="page-container">
      ${r(`Settings`)}
      
      <main class="content-area">
        <div class="profile-summary card mb-lg">
          <div class="flex items-center gap-md">
            <div class="avatar-lg"><i class="ph ph-user" style="font-size: 24px;"></i></div>
            <div>
              <div class="font-bold text-lg">Rajesh Kumar</div>
              <div class="text-sm text-secondary">SmartBiz Pro Member</div>
            </div>
          </div>
        </div>
        
        <div class="settings-list">
          ${e.map(e=>`
            <a href="#settings/${e.id}" class="settings-item card mb-sm">
              <div class="flex items-center gap-md">
                <div class="settings-icon-bg">${e.icon}</div>
                <div class="flex-1">
                  <div class="font-medium">${e.label}</div>
                  <div class="text-xs text-secondary">${e.sub}</div>
                </div>
                <div class="text-secondary"><i class="ph ph-caret-right"></i></div>
              </div>
            </a>
          `).join(``)}
        </div>
        
        <div class="mt-xl text-center">
          <button class="btn btn-outline text-error w-full">${t(`logout`)}</button>
          <div class="text-xs text-secondary mt-md">SmartBiz v2.4.0 (Stable)</div>
        </div>
      </main>
      
      ${n(`settings`)}
    </div>
  `}g.init=()=>{let e=document.querySelector(`.btn-outline.text-error`);e&&e.addEventListener(`click`,()=>{window.location.hash=`#login`})};function _(){return`
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">Business Profile</h1>
      </header>
      
      <main class="content-area">
        <div class="profile-upload card mb-lg text-center">
          <div class="logo-placeholder mb-sm">🏢</div>
          <button class="btn-text">Upload Business Logo</button>
        </div>
        
        <form class="profile-form">
          <section class="card mb-md">
            <h3 class="section-title mb-sm">Business Details</h3>
            <div class="form-group mb-md">
              <label>Business Name</label>
              <input type="text" value="Rajesh Kumar Enterprises" class="input-full">
            </div>
            <div class="form-group mb-md">
              <label>Business Category</label>
              <select class="input-full">
                <option>Textiles & Fabrics</option>
                <option>Grocery & FMCG</option>
                <option>Electronics</option>
              </select>
            </div>
            <div class="form-group mb-md">
              <label>GSTIN (Optional)</label>
              <input type="text" value="27AAAAA0000A1Z5" class="input-full">
            </div>
          </section>
          
          <section class="card mb-lg">
            <h3 class="section-title mb-sm">Contact Information</h3>
            <div class="form-group mb-md">
              <label>Phone Number</label>
              <input type="tel" value="+91 98765 43210" class="input-full" disabled>
            </div>
            <div class="form-group mb-md">
              <label>Email Address</label>
              <input type="email" value="rajesh@example.com" class="input-full">
            </div>
            <div class="form-group">
              <label>Address</label>
              <textarea class="input-full" rows="3">402, Business Hub, Andheri East, Mumbai, MH - 400069</textarea>
            </div>
          </section>
          
          <button type="submit" class="btn btn-primary w-full">Update Profile</button>
        </form>
      </main>
      ${n(`settings`)}
    </div>
  `}_.init=()=>{let e=document.querySelector(`.profile-form`);e&&e.addEventListener(`submit`,e=>{e.preventDefault(),alert(`Profile updated successfully!`),window.history.back()})};function v(){return`
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">Printer Settings</h1>
      </header>
      
      <main class="content-area">
        <section class="card mb-md">
          <div class="flex justify-between items-center">
            <div class="flex items-center gap-md">
              <div class="icon-circle bg-blue">🛜</div>
              <div>
                <div class="font-bold">Bluetooth Printing</div>
                <div class="text-xs text-secondary">Connect to thermal printer</div>
              </div>
            </div>
            <label class="switch">
              <input type="checkbox" checked>
              <span class="slider round"></span>
            </label>
          </div>
        </section>
        
        <section class="card mb-md">
          <h3 class="section-title mb-sm">Available Printers</h3>
          <div class="device-list">
            <div class="device-item flex justify-between items-center py-sm border-b">
              <div class="flex items-center gap-md">
                <span>🖨️</span>
                <div class="text-sm">TVS RP-3200 (Connected)</div>
              </div>
              <div class="text-success">●</div>
            </div>
            <div class="device-item flex justify-between items-center py-sm">
              <div class="flex items-center gap-md">
                <span>🖨️</span>
                <div class="text-sm">EPSON TM-T88V</div>
              </div>
              <button class="btn-text">Connect</button>
            </div>
          </div>
        </section>
        
        <section class="card mb-lg">
          <h3 class="section-title mb-sm">Print Configuration</h3>
          <div class="form-group mb-md">
            <label>Paper Size</label>
            <select class="input-full">
              <option>2 Inch (58mm)</option>
              <option>3 Inch (80mm)</option>
              <option>A4 Size</option>
            </select>
          </div>
          <div class="form-group mb-md">
            <label>Copies</label>
            <input type="number" value="1" class="input-full">
          </div>
          <label class="checkbox-container">
            <input type="checkbox" checked>
            <span class="text-sm">Print Logo on Invoice</span>
          </label>
        </section>
        
        <button class="btn btn-outline w-full mb-md">🖨️ Test Print</button>
      </main>
      ${n(`settings`)}
    </div>
  `}v.init=()=>{let e=document.querySelector(`.btn-outline.w-full`);e&&e.addEventListener(`click`,()=>{e.textContent=`⌛ Printing...`,e.disabled=!0,setTimeout(()=>{alert(`Test print successful! Check your TVS RP-3200 printer.`),e.textContent=`🖨️ Test Print`,e.disabled=!1},1500)})};function y(){return`
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">Staff Management</h1>
      </header>
      
      <main class="content-area">
        <div class="flex justify-between items-center mb-md">
          <h3 class="section-title">Active Staff</h3>
          <button class="btn btn-primary btn-sm">➕ Add Staff</button>
        </div>
        
        <div class="staff-list">
          ${[{name:`Amit Patel`,role:`Admin`,status:`Online`},{name:`Suresh Raina`,role:`Salesperson`,status:`Offline`},{name:`Kavita Singh`,role:`Inventory Manager`,status:`Online`}].map(e=>`
            <div class="staff-item card mb-sm">
              <div class="flex justify-between items-center">
                <div class="flex items-center gap-md">
                  <div class="avatar-sm">👤</div>
                  <div>
                    <div class="font-bold">${e.name}</div>
                    <div class="text-xs text-secondary">${e.role}</div>
                  </div>
                </div>
                <div class="staff-status text-right">
                  <div class="status-indicator ${e.status.toLowerCase()}">
                    ● ${e.status}
                  </div>
                </div>
              </div>
            </div>
          `).join(``)}
        </div>
        
        <section class="card mt-lg">
          <h3 class="section-title mb-sm">Roles & Permissions</h3>
          <p class="text-xs text-secondary mb-md">Manage what your staff can see and do in the app.</p>
          <button class="btn btn-outline w-full">Configure Roles</button>
        </section>
      </main>
      ${n(`settings`)}
    </div>
  `}y.init=()=>{document.querySelector(`.btn-primary.btn-sm`)?.addEventListener(`click`,()=>{alert(`Add Staff Feature - Coming Soon!`)})};function b(){return`
    <div class="scanner-container">
      <header class="app-header-transparent">
        <button class="icon-btn-white" onclick="window.history.back()">✕</button>
        <div class="scanner-title">Scan Barcode</div>
        <button class="icon-btn-white">🔦</button>
      </header>
      
      <div class="scanner-viewport">
        <div class="scanner-frame">
          <div class="scan-line"></div>
        </div>
        <div class="scanner-overlay top"></div>
        <div class="scanner-overlay bottom">
          <p class="scan-instruction">Align barcode within the frame to scan</p>
          <div class="manual-entry mt-xl">
            <button class="btn btn-outline-white">Enter Barcode Manually</button>
          </div>
        </div>
        <div class="scanner-overlay left"></div>
        <div class="scanner-overlay right"></div>
      </div>
      
      <div class="scanner-footer">
        <div class="recent-scans card">
          <div class="flex items-center gap-md">
            <span>📦</span>
            <div>
              <div class="font-bold">Organic Whole Milk 1L</div>
              <div class="text-xs text-secondary">SKU: DAIRY-WH-001</div>
            </div>
            <button class="btn btn-primary btn-sm ml-auto">Add</button>
          </div>
        </div>
      </div>
    </div>
  `}b.init=()=>{let e=document.querySelector(`.recent-scans`),t=document.querySelector(`.scan-line`);e&&(e.style.display=`none`),setTimeout(()=>{e&&(e.style.display=`block`,e.classList.add(`animate-slide-up`)),t&&(t.style.animation=`none`)},2e3);let n=document.querySelector(`.recent-scans .btn-primary`);n&&n.addEventListener(`click`,()=>{window.location.hash=`#billing`})};function x(){return`
    <div class="result-container success">
      <div class="result-card card text-center">
        <div class="success-icon-anim">✅</div>
        <h2 class="mt-md">Payment Successful!</h2>
        <p class="text-secondary">Invoice #INV-2023-089 has been paid.</p>
        
        <div class="payment-details card mt-lg text-left">
          <div class="flex justify-between mb-sm">
            <span class="text-xs text-secondary">Amount Paid</span>
            <span class="font-bold">₹ 14,443.00</span>
          </div>
          <div class="flex justify-between mb-sm">
            <span class="text-xs text-secondary">Payment Mode</span>
            <span class="font-bold">UPI (Google Pay)</span>
          </div>
          <div class="flex justify-between">
            <span class="text-xs text-secondary">Transaction ID</span>
            <span class="font-bold text-xs">TXN9876543210</span>
          </div>
        </div>
        
        <div class="action-buttons mt-xl">
          <button class="btn btn-primary w-full mb-md">🖨️ Print Receipt</button>
          <button class="btn btn-outline w-full" onclick="window.location.hash='#dashboard'">Back to Dashboard</button>
        </div>
      </div>
    </div>
  `}x.init=()=>{console.log(`Payment Success initialized`)};function S(){return`
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
      ${n(`customers`)}
    </div>
  `}S.init=async()=>{let e=localStorage.getItem(`selected_customer_id`),t=localStorage.getItem(`selected_customer_name`)||`Customer`;if(!e){alert(`No customer selected`),window.location.hash=`#customers`;return}document.getElementById(`c-ledger-name`).textContent=t;let n=document.getElementById(`ledger-list-container`),r=document.getElementById(`c-ledger-balance`),i=async()=>{try{let t=await o.getCustomerLedger(e);if(!t.length){n.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-secondary);">No transactions.</div>`,r.textContent=`₹ 0`,r.className=`text-xl font-bold`;return}let i=0;n.innerHTML=t.map(e=>(e.type===`gave`&&(i+=e.amount),e.type===`got`&&(i-=e.amount),`
          <div class="ledger-item card mb-sm">
            <div class="flex justify-between">
              <div>
                <div class="text-xs text-secondary">${new Date(e.tx_date).toLocaleString()}</div>
                <div class="font-medium mt-xs">${e.description||`Transaction`}</div>
              </div>
              <div class="text-right">
                <div class="font-bold ${e.type===`gave`?`text-error`:`text-success`}">
                  ${e.type===`gave`?`-`:`+`} ₹ ${e.amount}
                </div>
                <div class="text-xs text-secondary">${e.type===`gave`?`You Gave`:`You Got`}</div>
              </div>
            </div>
          </div>
        `)).join(``),r.textContent=`₹ ${i}`,r.className=`text-xl font-bold ${i>0?`text-error`:`text-success`}`}catch{n.innerHTML=`<div style="padding: 1rem; text-align: center; color: var(--text-error);">Failed to load.</div>`}};await i();let a=async n=>{let r=prompt(`Enter amount you ${n===`gave`?`gave to`:`got from`} ${t}:`);if(!r||isNaN(r))return;let a=prompt(`Enter description (optional):`)||``;try{await o.addLedgerEntry({customer_id:e,amount:parseFloat(r),type:n,description:a,merchant_id:localStorage.getItem(`merchant_id`)||1}),i()}catch(e){alert(`Failed: `+e.message)}};document.getElementById(`btn-ledger-gave`)?.addEventListener(`click`,()=>a(`gave`)),document.getElementById(`btn-ledger-got`)?.addEventListener(`click`,()=>a(`got`))};function C(){return`
    <div class="onboarding-container">
      <div class="onboarding-carousel">
        ${[{title:`Smart Inventory`,desc:`Manage stock alerts and inventory with AI insights.`,icon:`📦`},{title:`Digital Invoicing`,desc:`Create and share GST invoices in seconds.`,icon:`🧾`},{title:`Customer Ledger`,desc:`Track udhar and get automated payment reminders.`,icon:`👥`}].map((e,t)=>`
          <div class="onboarding-slide ${t===0?`active`:``}">
            <div class="onboarding-icon">${e.icon}</div>
            <h2 class="mt-lg">${e.title}</h2>
            <p class="text-secondary mt-md">${e.desc}</p>
          </div>
        `).join(``)}
      </div>
      
      <div class="onboarding-dots">
        <span class="dot active"></span>
        <span class="dot"></span>
        <span class="dot"></span>
      </div>
      
      <div class="onboarding-footer">
        <button class="btn btn-primary w-full" onclick="window.location.hash='#login'">Get Started</button>
        <button class="btn-text mt-md" onclick="window.location.hash='#login'">Existing User? Log In</button>
      </div>
    </div>
  `}C.init=()=>{let e=document.querySelectorAll(`.onboarding-slide`),t=document.querySelectorAll(`.dot`),n=0;function r(n){e.forEach(e=>e.classList.remove(`active`)),t.forEach(e=>e.classList.remove(`active`)),e[n].classList.add(`active`),t[n].classList.add(`active`)}let i=setInterval(()=>{n=(n+1)%e.length,r(n)},3e3);t.forEach((e,t)=>{e.addEventListener(`click`,()=>{clearInterval(i),n=t,r(n)})}),window.addEventListener(`hashchange`,()=>clearInterval(i),{once:!0})};function w(){return`
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
      ${n(`reports`)}
    </div>
  `}w.init=()=>{let e=document.getElementById(`forecastChart`);e&&typeof Chart<`u`&&new Chart(e,{type:`line`,data:{labels:[`Jan`,`Feb`,`Mar`,`Apr`,`May (Est)`,`Jun (Pred)`],datasets:[{label:`Historical Sales`,data:[3e5,32e4,31e4,42e4,null,null],borderColor:`#303f9f`,segment:{borderDash:e=>[0,0]},tension:.3},{label:`Predicted Sales`,data:[null,null,null,42e4,48e4,512e3],borderColor:`#ff9800`,borderDash:[5,5],tension:.3}]},options:{responsive:!0,maintainAspectRatio:!1,plugins:{legend:{position:`bottom`,labels:{boxWidth:12}}},scales:{y:{beginAtZero:!0,ticks:{callback:e=>`₹`+e/1e3+`k`}}}}})};function T(){let e=[{code:`en`,name:`English`,native:`English`,icon:`🇺🇸`},{code:`hi`,name:`Hindi`,native:`हिन्दी`,icon:`🇮🇳`},{code:`te`,name:`Telugu`,native:`తెలుగు`,icon:`🇮🇳`},{code:`mr`,name:`Marathi`,native:`मराठी`,icon:`🇮🇳`},{code:`gu`,name:`Gujarati`,native:`ગુજરાતી`,icon:`🇮🇳`},{code:`ta`,name:`Tamil`,native:`தமிழ்`,icon:`🇮🇳`}],r=localStorage.getItem(`app_lang`)||`en`;return`
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">${t(`app_language`)}</h1>
      </header>
      
      <main class="content-area">
        <section class="card mb-lg">
          <p class="text-sm text-secondary mb-md">Select your preferred language for the application interface.</p>
          
          <div class="language-list">
            ${e.map(e=>`
              <div class="language-item flex justify-between items-center py-md border-b clickable-lang" data-code="${e.code}">
                <div class="flex items-center gap-md">
                  <span class="text-xl">${e.icon}</span>
                  <div>
                    <div class="font-bold">${e.name}</div>
                    <div class="text-xs text-secondary">${e.native}</div>
                  </div>
                </div>
                <div class="selection-indicator">
                  ${r===e.code?`<span class="text-success">● Selected</span>`:`<span class="text-secondary">○</span>`}
                </div>
              </div>
            `).join(``)}
          </div>
        </section>
        
        <button class="btn btn-primary w-full" id="btn-save-language">${t(`save_apply`)}</button>
      </main>
      
      ${n(`settings`)}
    </div>
  `}T.init=()=>{let e=localStorage.getItem(`app_lang`)||`en`,n=document.querySelectorAll(`.clickable-lang`),r=document.getElementById(`btn-save-language`);n.forEach(t=>{t.addEventListener(`click`,()=>{n.forEach(e=>e.querySelector(`.selection-indicator`).innerHTML=`<span class="text-secondary">○</span>`),t.querySelector(`.selection-indicator`).innerHTML=`<span class="text-success">● Selected</span>`,e=t.dataset.code})}),r?.addEventListener(`click`,()=>{localStorage.setItem(`app_lang`,e),alert(t(`save_apply`)+`!`),window.location.reload()})};function E(){return`
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Daybook</h1>
        </div>
        <div class="header-right">
          <input type="date" id="daybook-date" class="input-sm" value="${new Date().toISOString().split(`T`)[0]}" style="border:1px solid var(--border); border-radius:8px; padding:4px 8px; font-size:13px;">
        </div>
      </header>

      <main class="content-area">
        <!-- Summary Cards -->
        <div class="kpi-grid mb-lg" style="grid-template-columns: repeat(3, 1fr);">
          <div class="kpi-card" style="background: #e8f5e9; color: #1b5e20;">
            <div class="kpi-label">Sales</div>
            <div class="kpi-value text-lg" id="db-sales">₹ 0</div>
          </div>
          <div class="kpi-card" style="background: #fce4ec; color: #880e4f;">
            <div class="kpi-label">Expenses</div>
            <div class="kpi-value text-lg" id="db-expenses">₹ 0</div>
          </div>
          <div class="kpi-card" style="background: #e3f2fd; color: #0d47a1;">
            <div class="kpi-label">Net Profit</div>
            <div class="kpi-value text-lg" id="db-profit">₹ 0</div>
          </div>
        </div>

        <!-- Sales Section -->
        <div class="card mb-md">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title"><i class="ph ph-receipt text-success"></i> Sales Invoices</h3>
            <span class="chip" id="db-sales-count">0 invoices</span>
          </div>
          <div id="db-invoices-list">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>

        <!-- Expenses Section -->
        <div class="card mb-lg">
          <div class="flex justify-between items-center mb-sm">
            <h3 class="section-title"><i class="ph ph-arrows-out text-error"></i> Expenses</h3>
            <button class="btn btn-outline btn-sm" id="btn-add-expense"><i class="ph ph-plus"></i> Add</button>
          </div>
          <div id="db-expenses-list">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>
      </main>

      <!-- Add Expense Modal -->
      <div id="expense-modal" style="display:none; position:fixed; inset:0; background:rgba(0,0,0,0.4); z-index:100; display:flex; align-items:center; justify-content:center;">
        <div class="card" style="width:90%; max-width:400px; padding: 1.5rem;">
          <h3 class="section-title mb-md">Add Expense</h3>
          <div class="form-group mb-sm">
            <label>Description</label>
            <input id="exp-desc" type="text" class="input-full mt-xs" placeholder="e.g. Rent, Electricity...">
          </div>
          <div class="form-group mb-sm">
            <label>Amount (₹)</label>
            <input id="exp-amount" type="number" class="input-full mt-xs" placeholder="0.00">
          </div>
          <div class="form-group mb-md">
            <label>Category</label>
            <select id="exp-cat" class="input-full mt-xs">
              <option>Rent</option><option>Utilities</option><option>Staff Salary</option>
              <option>Purchase</option><option>Transport</option><option>Other</option>
            </select>
          </div>
          <div class="flex gap-sm">
            <button class="btn btn-outline flex-1" id="btn-cancel-expense">Cancel</button>
            <button class="btn btn-primary flex-1" id="btn-save-expense">Save</button>
          </div>
        </div>
      </div>

      ${n(`reports`)}
    </div>
  `}E.init=async()=>{let e=new Date().toISOString().split(`T`)[0],t=document.getElementById(`expense-modal`);t.style.display=`none`;let n=async e=>{try{let[t,n]=await Promise.all([o.getInvoices(),o.getExpenses()]),r=t.filter(t=>((t.created_at||``).split(`T`)[0]||(t.created_at||``).split(` `)[0])===e),i=n.filter(t=>((t.expense_date||``).split(`T`)[0]||(t.expense_date||``).split(` `)[0])===e),a=r.reduce((e,t)=>e+(t.grand_total||0),0),s=i.reduce((e,t)=>e+(t.amount||0),0),c=a-s;document.getElementById(`db-sales`).textContent=`₹ ${a.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`,document.getElementById(`db-expenses`).textContent=`₹ ${s.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`,document.getElementById(`db-profit`).textContent=`₹ ${c.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`,document.getElementById(`db-profit`).style.color=c>=0?`#1b5e20`:`#b71c1c`,document.getElementById(`db-sales-count`).textContent=`${r.length} invoice${r.length===1?``:`s`}`;let l=document.getElementById(`db-invoices-list`);r.length===0?l.innerHTML=`<div class="text-secondary text-sm text-center p-md">No sales for this date.</div>`:l.innerHTML=r.map(e=>`
          <div class="flex justify-between items-center mb-sm" style="padding: 8px 0; border-bottom: 1px solid var(--border);">
            <div>
              <div class="font-medium text-sm">${e.invoice_number||`INV-`+e.id}</div>
              <div class="text-xs text-secondary">Customer #${e.customer_id}</div>
            </div>
            <div class="font-bold text-success">₹ ${(e.grand_total||0).toLocaleString(`en-IN`)}</div>
          </div>
        `).join(``);let u=document.getElementById(`db-expenses-list`);i.length===0?u.innerHTML=`<div class="text-secondary text-sm text-center p-md">No expenses for this date.</div>`:u.innerHTML=i.map(e=>`
          <div class="flex justify-between items-center mb-sm" style="padding: 8px 0; border-bottom: 1px solid var(--border);">
            <div>
              <div class="font-medium text-sm">${e.description||`Expense`}</div>
              <div class="text-xs text-secondary">${e.category||`Other`} · ${e.payment_mode||`Cash`}</div>
            </div>
            <div class="font-bold text-error">- ₹ ${(e.amount||0).toLocaleString(`en-IN`)}</div>
          </div>
        `).join(``)}catch(e){console.error(`Daybook load error:`,e)}};await n(e),document.getElementById(`daybook-date`)?.addEventListener(`change`,t=>{e=t.target.value,n(e)}),document.getElementById(`btn-add-expense`)?.addEventListener(`click`,()=>{t.style.display=`flex`}),document.getElementById(`btn-cancel-expense`)?.addEventListener(`click`,()=>{t.style.display=`none`}),document.getElementById(`btn-save-expense`)?.addEventListener(`click`,async()=>{let r=document.getElementById(`exp-desc`).value.trim(),i=parseFloat(document.getElementById(`exp-amount`).value),a=document.getElementById(`exp-cat`).value;if(!r||isNaN(i)||i<=0)return alert(`Please fill all fields.`);try{await o.addExpense({description:r,amount:i,category:a,payment_mode:`Cash`,expense_date:e,merchant_id:localStorage.getItem(`merchant_id`)||1}),t.style.display=`none`,await n(e)}catch(e){alert(`Failed to save: `+e.message)}})};function D(){return`
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">P&L Statement</h1>
        </div>
        <div class="header-right">
          <select id="pnl-period" class="input-sm" style="border:1px solid var(--border); border-radius:8px; padding:4px 8px; font-size:13px;">
            <option value="7">Last 7 Days</option>
            <option value="30" selected>Last 30 Days</option>
            <option value="90">Last 90 Days</option>
            <option value="365">Last 1 Year</option>
          </select>
        </div>
      </header>

      <main class="content-area">
        <!-- Net Profit banner -->
        <div class="card mb-lg" id="pnl-banner" style="background: linear-gradient(135deg, #1565c0, #283593); color: white; text-align:center; padding: 1.5rem;">
          <div style="font-size: 13px; opacity: 0.8; margin-bottom: 4px;">NET PROFIT / LOSS</div>
          <div style="font-size: 2rem; font-weight: 700;" id="pnl-net">₹ 0</div>
          <div style="font-size: 12px; opacity: 0.7; margin-top: 4px;" id="pnl-margin">Margin: 0%</div>
        </div>

        <!-- Revenue -->
        <div class="card mb-md">
          <h3 class="section-title mb-sm" style="color: var(--success);"><i class="ph ph-arrow-circle-up"></i> Revenue</h3>
          <div class="flex justify-between mb-xs">
            <span class="text-sm">Gross Sales</span>
            <span class="font-bold" id="pnl-gross-sales">₹ 0</span>
          </div>
          <div class="flex justify-between mb-xs">
            <span class="text-sm">GST Collected</span>
            <span class="font-medium text-secondary" id="pnl-gst-collected">₹ 0</span>
          </div>
          <hr style="margin: 8px 0; border-color: var(--border);">
          <div class="flex justify-between">
            <span class="font-bold">Total Revenue</span>
            <span class="font-bold text-success" id="pnl-total-revenue">₹ 0</span>
          </div>
        </div>

        <!-- Expenses -->
        <div class="card mb-md">
          <h3 class="section-title mb-sm" style="color: var(--error);"><i class="ph ph-arrow-circle-down"></i> Expenses</h3>
          <div id="pnl-expense-breakdown">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
          <hr style="margin: 8px 0; border-color: var(--border);">
          <div class="flex justify-between">
            <span class="font-bold">Total Expenses</span>
            <span class="font-bold text-error" id="pnl-total-expenses">₹ 0</span>
          </div>
        </div>

        <!-- Summary Table -->
        <div class="card mb-lg">
          <h3 class="section-title mb-sm"><i class="ph ph-table"></i> Summary</h3>
          <div class="flex justify-between mb-xs"><span class="text-sm">Total Invoices</span><span id="pnl-inv-count" class="font-medium">0</span></div>
          <div class="flex justify-between mb-xs"><span class="text-sm">Average Invoice Value</span><span id="pnl-avg-inv" class="font-medium">₹ 0</span></div>
          <div class="flex justify-between mb-xs"><span class="text-sm">Total Customers Billed</span><span id="pnl-cust-count" class="font-medium">0</span></div>
          <div class="flex justify-between"><span class="text-sm">Outstanding Udhar (Net)</span><span id="pnl-udhar" class="font-medium text-warning">₹ 0</span></div>
        </div>
      </main>

      ${n(`reports`)}
    </div>
  `}D.init=async()=>{let e=async e=>{try{let[t,n,r]=await Promise.all([o.getInvoices(),o.getExpenses(),fetch(`https://smartbiz-backend-c7ru.onrender.com/api/ledger/all`).then(e=>e.ok?e.json():[]).catch(()=>[])]),i=new Date;i.setDate(i.getDate()-e);let a=t.filter(e=>new Date(e.created_at)>=i),s=n.filter(e=>new Date(e.expense_date)>=i),c=a.reduce((e,t)=>e+(t.subtotal||0),0),l=a.reduce((e,t)=>e+(t.total_gst||0),0),u=a.reduce((e,t)=>e+(t.grand_total||0),0),d=s.reduce((e,t)=>e+(t.amount||0),0),f=u-d,p=u>0?(f/u*100).toFixed(1):0,m=e=>`₹ ${e.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`;document.getElementById(`pnl-gross-sales`).textContent=m(c),document.getElementById(`pnl-gst-collected`).textContent=m(l),document.getElementById(`pnl-total-revenue`).textContent=m(u),document.getElementById(`pnl-total-expenses`).textContent=m(d),document.getElementById(`pnl-net`).textContent=m(Math.abs(f)),document.getElementById(`pnl-margin`).textContent=`Margin: ${p}%`,document.getElementById(`pnl-banner`).style.background=f>=0?`linear-gradient(135deg, #2e7d32, #1b5e20)`:`linear-gradient(135deg, #c62828, #b71c1c)`,document.getElementById(`pnl-inv-count`).textContent=a.length;let h=a.length>0?u/a.length:0;document.getElementById(`pnl-avg-inv`).textContent=m(h);let g=new Set(a.map(e=>e.customer_id)).size;document.getElementById(`pnl-cust-count`).textContent=g;let _={};s.forEach(e=>{_[e.category||`Other`]=(_[e.category||`Other`]||0)+e.amount});let v=document.getElementById(`pnl-expense-breakdown`);Object.keys(_).length===0?v.innerHTML=`<div class="text-secondary text-sm text-center p-sm">No expenses in this period.</div>`:v.innerHTML=Object.entries(_).map(([e,t])=>`
          <div class="flex justify-between mb-xs">
            <span class="text-sm">${e}</span>
            <span class="text-error font-medium">${m(t)}</span>
          </div>
        `).join(``);try{let e=await o.getDashboardStats();document.getElementById(`pnl-udhar`).textContent=m(e.udharTotal||0)}catch{}}catch(e){console.error(`PnL load error:`,e)}};await e(30),document.getElementById(`pnl-period`)?.addEventListener(`change`,t=>e(parseInt(t.target.value)))};function O(){return`
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Tax & GST Report</h1>
        </div>
        <div class="header-right">
          <select id="tax-period" class="input-sm" style="border:1px solid var(--border); border-radius:8px; padding:4px 8px; font-size:13px;">
            <option value="7">Last 7 Days</option>
            <option value="30" selected>This Month</option>
            <option value="90">Last Quarter</option>
            <option value="365">This Year</option>
          </select>
        </div>
      </header>

      <main class="content-area">
        <!-- GST Summary -->
        <div class="kpi-grid mb-lg" style="grid-template-columns: repeat(2, 1fr);">
          <div class="kpi-card" style="background: #fff3e0; color: #e65100;">
            <div class="kpi-label">Total GST Collected</div>
            <div class="kpi-value" id="tax-total-gst">₹ 0</div>
          </div>
          <div class="kpi-card" style="background: #f3e5f5; color: #6a1b9a;">
            <div class="kpi-label">Taxable Invoices</div>
            <div class="kpi-value" id="tax-inv-count">0</div>
          </div>
        </div>

        <!-- GST Slab Breakdown -->
        <div class="card mb-md">
          <h3 class="section-title mb-sm"><i class="ph ph-percent text-warning"></i> GST Slab Breakdown</h3>
          <div id="tax-slab-breakdown">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>

        <!-- Invoice List with GST -->
        <div class="card mb-lg">
          <h3 class="section-title mb-sm"><i class="ph ph-list-numbers"></i> Invoice GST Details</h3>
          <div style="display:grid; grid-template-columns:1fr 1fr 1fr; font-size:11px; font-weight:600; color:var(--text-secondary); padding: 0 0 6px; border-bottom: 1px solid var(--border);">
            <span>Invoice #</span><span>Taxable Amt</span><span>GST</span>
          </div>
          <div id="tax-invoice-list">
            <div class="text-secondary text-sm text-center p-md">Loading...</div>
          </div>
        </div>

        <!-- GSTIN Block -->
        <div class="card mb-lg" style="background: #f9fbe7;">
          <div class="flex items-center gap-sm mb-sm">
            <i class="ph ph-building-office text-primary" style="font-size:20px;"></i>
            <h3 class="section-title">Business GSTIN Info</h3>
          </div>
          <div class="text-sm text-secondary">GSTIN: <span class="font-bold text-primary" id="gstin-val">—</span></div>
          <div class="text-xs text-secondary mt-xs">Tax Filing Period: Monthly (GSTR-1)</div>
        </div>
      </main>

      ${n(`reports`)}
    </div>
  `}O.init=async()=>{let e=async e=>{try{let t=await o.getInvoices(),n=new Date;n.setDate(n.getDate()-e);let r=t.filter(e=>new Date(e.created_at)>=n),i=r.filter(e=>(e.total_gst||0)>0),a=r.reduce((e,t)=>e+(t.total_gst||0),0);document.getElementById(`tax-total-gst`).textContent=`₹ ${a.toLocaleString(`en-IN`,{minimumFractionDigits:2})}`,document.getElementById(`tax-inv-count`).textContent=i.length;let s=document.getElementById(`tax-invoice-list`);r.length===0?s.innerHTML=`<div class="text-secondary text-sm text-center p-md">No invoices for this period.</div>`:s.innerHTML=r.map(e=>`
          <div style="display:grid; grid-template-columns:1fr 1fr 1fr; font-size:12px; padding: 6px 0; border-bottom: 1px solid var(--border);">
            <span class="font-medium">${e.invoice_number||`INV-`+e.id}</span>
            <span>₹ ${(e.subtotal||0).toLocaleString(`en-IN`)}</span>
            <span class="text-warning font-bold">₹ ${(e.total_gst||0).toLocaleString(`en-IN`)}</span>
          </div>
        `).join(``);let c=r.filter(e=>(e.total_gst||0)===0),l=r.filter(e=>(e.total_gst||0)>0),u=l.reduce((e,t)=>t.total_gst/t.subtotal*100<8?e+t.total_gst:e,0),d=l.reduce((e,t)=>{let n=t.subtotal>0?t.total_gst/t.subtotal*100:0;return n>=8&&n<15?e+t.total_gst:e},0),f=l.reduce((e,t)=>(t.subtotal>0?t.total_gst/t.subtotal*100:0)>=15?e+t.total_gst:e,0),p=document.getElementById(`tax-slab-breakdown`);p.innerHTML=`
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#f5f5f5; border-radius:6px;">
          <span class="text-sm"><span class="chip">0% GST</span> Zero-rated</span>
          <span class="font-medium">${c.length} invoices</span>
        </div>
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#fff8e1; border-radius:6px;">
          <span class="text-sm"><span class="chip" style="background:#fff3e0; color:#e65100">5% GST</span></span>
          <span class="font-bold text-warning">₹ ${u.toLocaleString(`en-IN`,{minimumFractionDigits:2})}</span>
        </div>
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#fce4ec; border-radius:6px;">
          <span class="text-sm"><span class="chip" style="background:#fce4ec; color:#880e4f">12% GST</span></span>
          <span class="font-bold" style="color:#880e4f">₹ ${d.toLocaleString(`en-IN`,{minimumFractionDigits:2})}</span>
        </div>
        <div class="flex justify-between mb-xs" style="padding: 6px; background:#e8eaf6; border-radius:6px;">
          <span class="text-sm"><span class="chip" style="background:#e8eaf6; color:#283593">18% GST</span></span>
          <span class="font-bold" style="color:#283593">₹ ${f.toLocaleString(`en-IN`,{minimumFractionDigits:2})}</span>
        </div>
      `;let m=localStorage.getItem(`gstin`)||localStorage.getItem(`merchant_gstin`)||`—`;document.getElementById(`gstin-val`).textContent=m}catch(e){console.error(`Tax load error:`,e)}};await e(30),document.getElementById(`tax-period`)?.addEventListener(`change`,t=>e(parseInt(t.target.value)))};function k(){return`
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Notifications</h1>
        </div>
        <button class="btn-text text-sm" id="btn-mark-all">Mark all read</button>
      </header>

      <main class="content-area">
        <!-- System Alerts -->
        <div id="notif-stock-alerts"></div>
        <div id="notif-udhar-alerts"></div>

        <!-- Static Activity Notifications -->
        <div class="card mb-sm notif-item" data-read="false" style="border-left: 4px solid var(--primary);">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background: #e3f2fd; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-chart-line-up text-primary"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Sales Report Ready</div>
              <div class="text-xs text-secondary">Your monthly sales report is available in Reports &rarr; P&L Statement.</div>
              <div class="text-xs text-secondary mt-xs">Today, 9:00 AM</div>
            </div>
            <div class="unread-dot" style="width:8px;height:8px;border-radius:50%;background:var(--primary);"></div>
          </div>
        </div>

        <div class="card mb-sm notif-item" data-read="false" style="border-left: 4px solid var(--success);">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background: #e8f5e9; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-users text-success"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Customer Added</div>
              <div class="text-xs text-secondary">A new customer was registered in your store.</div>
              <div class="text-xs text-secondary mt-xs">Yesterday, 3:45 PM</div>
            </div>
          </div>
        </div>

        <div class="card mb-sm notif-item" data-read="true" style="opacity:0.65;">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background: #fce4ec; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-receipt text-error"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Invoice Created</div>
              <div class="text-xs text-secondary">Invoice INV-001 was created successfully.</div>
              <div class="text-xs text-secondary mt-xs">2 days ago</div>
            </div>
          </div>
        </div>

        <!-- Dynamic Low Stock Section -->
        <h3 class="section-title mt-lg mb-sm"><i class="ph ph-warning text-warning"></i> Live Alerts</h3>
        <div id="notif-live-alerts">
          <div class="text-secondary text-sm text-center p-md">Loading live alerts...</div>
        </div>
      </main>

      ${n(``)}
    </div>
  `}k.init=async()=>{document.getElementById(`btn-mark-all`)?.addEventListener(`click`,()=>{document.querySelectorAll(`.notif-item`).forEach(e=>{e.style.opacity=`0.65`,e.style.borderLeft=``;let t=e.querySelector(`.unread-dot`);t&&t.remove()})});try{let[e,t]=await Promise.all([o.getInventory(),o.getDashboardStats()]),n=document.getElementById(`notif-live-alerts`),r=e.filter(e=>e.stock_level<10),i=t.udharTotal||0,a=``;i>0&&(a+=`
        <div class="card mb-sm" style="border-left: 4px solid var(--warning);">
          <div class="flex items-center gap-sm">
            <div style="width:36px; height:36px; border-radius:50%; background:#fff3e0; display:flex; align-items:center; justify-content:center;">
              <i class="ph ph-hand-coins" style="color:#e65100;"></i>
            </div>
            <div class="flex-1">
              <div class="font-medium text-sm">Pending Udhar Balance</div>
              <div class="text-xs text-secondary">Net outstanding credit: ₹ ${i.toLocaleString(`en-IN`)}. Review customer ledgers.</div>
            </div>
          </div>
        </div>
      `),r.length>0&&r.slice(0,3).forEach(e=>{a+=`
          <div class="card mb-sm" style="border-left: 4px solid var(--error);">
            <div class="flex items-center gap-sm">
              <div style="width:36px; height:36px; border-radius:50%; background:#fce4ec; display:flex; align-items:center; justify-content:center;">
                <i class="ph ph-package text-error"></i>
              </div>
              <div class="flex-1">
                <div class="font-medium text-sm">Low Stock: ${e.item_name}</div>
                <div class="text-xs text-secondary">Only ${e.stock_level} units remaining. Restock soon!</div>
              </div>
            </div>
          </div>
        `}),a||=`<div class="card text-center p-md"><i class="ph ph-check-circle text-success" style="font-size:28px;"></i><div class="text-sm mt-sm text-secondary">All clear! No critical alerts.</div></div>`,n.innerHTML=a}catch(e){console.error(`Notifications load error:`,e)}};function A(){return`
    <div class="page-container">
      <header class="app-header">
        <div class="flex items-center gap-sm">
          <button class="icon-btn" onclick="window.history.back()"><i class="ph ph-arrow-left"></i></button>
          <h1 class="header-title">Help & Support</h1>
        </div>
      </header>

      <main class="content-area">
        <!-- Search -->
        <div class="search-bar mb-lg">
          <input type="text" class="search-input" id="faq-search" placeholder="Search help topics...">
        </div>

        <!-- Quick actions -->
        <div class="kpi-grid mb-lg" style="grid-template-columns: repeat(2, 1fr);">
          <a href="mailto:support@smartbiz.app" class="kpi-card text-center" style="text-decoration:none; background:#e3f2fd; color:#0d47a1;">
            <i class="ph ph-envelope" style="font-size:24px; margin-bottom:6px;"></i>
            <div class="text-sm font-medium">Email Support</div>
            <div class="text-xs" style="opacity:0.7;">support@smartbiz.app</div>
          </a>
          <a href="tel:+918001234567" class="kpi-card text-center" style="text-decoration:none; background:#e8f5e9; color:#1b5e20;">
            <i class="ph ph-phone" style="font-size:24px; margin-bottom:6px;"></i>
            <div class="text-sm font-medium">Call Support</div>
            <div class="text-xs" style="opacity:0.7;">+91 800 123 4567</div>
          </a>
        </div>

        <!-- App Version -->
        <div class="card mb-lg" style="background:#fafafa;">
          <div class="flex justify-between items-center">
            <div>
              <div class="font-bold">SmartBiz</div>
              <div class="text-xs text-secondary">Version 1.0.0 · Build 2026</div>
            </div>
            <i class="ph ph-info text-secondary" style="font-size:20px;"></i>
          </div>
        </div>

        <!-- FAQs -->
        <h3 class="section-title mb-md"><i class="ph ph-question"></i> Frequently Asked Questions</h3>
        <div id="faq-list">
          ${[{q:`How do I add a new product?`,a:`Go to Inventory → Tap the + button → Fill in product details and save.`},{q:`How do I create an invoice?`,a:`Go to Billing → Select a customer → Add items from your inventory → Tap Save & Preview.`},{q:`How do I track customer credit (Udhar)?`,a:`Go to Customers → Select a customer → Use "You Gave" or "You Got" buttons to record transactions.`},{q:`How do I view my P&L report?`,a:`Go to Reports → Tap P&L Statement → Choose a time period to see profit and loss data.`},{q:`How do I add an expense?`,a:`Go to Reports → Daybook → Select a date → Tap "Add" in the Expenses section.`},{q:`How do I change the app language?`,a:`Go to Settings → Language → Choose your preferred language from the list.`},{q:`Can I connect a thermal printer?`,a:`Go to Settings → Printer Settings → Enable Bluetooth and pair your supported thermal printer.`}].map((e,t)=>`
            <div class="card mb-sm faq-item">
              <button class="w-full text-left" onclick="this.parentElement.classList.toggle('open')" style="background:none; border:none; cursor:pointer; padding:0;">
                <div class="flex justify-between items-center">
                  <span class="font-medium text-sm">${e.q}</span>
                  <i class="ph ph-caret-down text-secondary faq-caret" style="transition:transform 0.2s;"></i>
                </div>
              </button>
              <div class="faq-answer" style="display:none; margin-top:8px; font-size:13px; color:var(--text-secondary); line-height:1.6;">
                ${e.a}
              </div>
            </div>
          `).join(``)}
        </div>

        <!-- Contact -->
        <div class="card mt-lg mb-xl" style="background: linear-gradient(135deg, var(--primary), #303f9f); color:white; text-align:center; padding:1.5rem;">
          <i class="ph ph-chat-circle-dots" style="font-size:32px; margin-bottom:8px;"></i>
          <h3 style="margin-bottom:6px;">Still need help?</h3>
          <p style="font-size:13px; opacity:0.85; margin-bottom:16px;">Our support team is available Mon–Sat, 9 AM to 6 PM.</p>
          <a href="mailto:support@smartbiz.app" class="btn" style="background:white; color:var(--primary); width:100%; display:block; text-decoration:none; text-align:center;">
            <i class="ph ph-envelope"></i> Contact Us
          </a>
        </div>
      </main>

      ${n(``)}
    </div>
  `}A.init=()=>{document.querySelectorAll(`.faq-item button`).forEach(e=>{e.addEventListener(`click`,()=>{let t=e.parentElement,n=t.querySelector(`.faq-answer`),r=t.querySelector(`.faq-caret`),i=t.classList.contains(`open`);n.style.display=i?`none`:`block`,r.style.transform=i?``:`rotate(180deg)`})}),document.getElementById(`faq-search`)?.addEventListener(`input`,e=>{let t=e.target.value.toLowerCase();document.querySelectorAll(`.faq-item`).forEach(e=>{let n=e.querySelector(`.font-medium`).textContent.toLowerCase();e.style.display=n.includes(t)?``:`none`})})};var j={"":C,"#onboarding":C,"#login":s,"#register":c,"#otp":l,"#dashboard":u,"#customers":m,"#billing":p,"#inventory":d,"#inventory/add":f,"#reports":h,"#reports/daybook":E,"#reports/pnl":D,"#reports/tax":O,"#reports/forecast":w,"#settings":g,"#settings/business_profile":_,"#settings/printer":v,"#settings/staff":y,"#settings/notifications":k,"#settings/help":A,"#scanner":b,"#payment-success":x,"#customers/ledger":S,"#settings/language":T};function M(){let e=document.querySelector(`#app`),t=window.location.hash||``;console.log(`Routing to:`,t);let n=j[t];n||=j[Object.keys(j).find(e=>!!(e.includes(`:`)&&t.startsWith(e.split(`:`)[0])||e!==``&&t.startsWith(e)))]||j[``],n||=j[``],e.innerHTML=n(),n.init&&n.init(),document.querySelectorAll(`.nav-item`).forEach(e=>{let n=e.getAttribute(`href`);n&&(t===n||t.startsWith(n)&&n!==`#`)?e.classList.add(`active`):e.classList.remove(`active`)})}window.addEventListener(`hashchange`,M),window.addEventListener(`load`,M);