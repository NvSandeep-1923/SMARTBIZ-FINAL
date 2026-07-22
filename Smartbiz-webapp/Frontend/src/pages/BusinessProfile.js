import { Header, Navigation } from '../components/Navigation';

export function BusinessProfilePage() {
  return `
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
      ${Navigation('settings')}
    </div>
  `;
}

BusinessProfilePage.init = () => {
  const form = document.querySelector('.profile-form');
  if (form) {
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      alert('Profile updated successfully!');
      window.history.back();
    });
  }
};
