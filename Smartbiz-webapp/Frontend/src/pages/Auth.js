import { api } from '../utils/api';

export function LoginPage() {
  return `
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
  `;
}

LoginPage.init = () => {
  const form = document.getElementById('login-form');
  if (!form) return;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const submitBtn = form.querySelector('button[type="submit"]');

    if (email && password) {
      try {
        submitBtn.textContent = 'Authenticating...';
        submitBtn.disabled = true;
        
        // Automatically make sure the test user exists
        if (email === 'admin@smartbiz.com' && password === '1234') {
          try {
            await api.register({ email: 'admin@smartbiz.com', password: '1234', phone: '0000000000', merchant_name: 'SmartBiz Demo Business' });
          } catch(err) {
            // Already exists or duplicate entry, completely fine
          }
        }
        
        const res = await api.login({ email, password });
        
        if(res.user) {
          localStorage.setItem('temp_email', email);
          localStorage.setItem('merchant_id', res.user.id);
          localStorage.setItem('merchant_name', res.user.merchant_name || 'Business Info');
          window.location.hash = '#dashboard';
        }
      } catch (err) {
        alert(err.message || 'Login failed. Please check credentials.');
      } finally {
        submitBtn.textContent = 'Login to Dashboard';
        submitBtn.disabled = false;
      }
    }
  });
};

export function RegisterPage() {
  return `
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
  `;
}

RegisterPage.init = () => {
  const form = document.getElementById('register-form');
  if (!form) return;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const merchant_name = document.getElementById('merchant_name').value;
    const email = document.getElementById('reg-email').value;
    const phone = document.getElementById('phone').value;
    const password = document.getElementById('reg-password').value;
    const submitBtn = form.querySelector('button[type="submit"]');

    if (email && password && merchant_name) {
      try {
        submitBtn.textContent = 'Registering...';
        submitBtn.disabled = true;
        
        await api.register({ email, password, phone, merchant_name });
        alert('Registration successful! You can now log in.');
        window.location.hash = '#login';
      } catch (err) {
        alert(err.message || 'Registration failed. This email or phone might already be in use.');
      } finally {
        submitBtn.textContent = 'Register Account';
        submitBtn.disabled = false;
      }
    }
  });
};
