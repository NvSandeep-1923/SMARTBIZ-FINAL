export function OnboardingPage() {
  const slides = [
    { title: 'Smart Inventory', desc: 'Manage stock alerts and inventory with AI insights.', icon: '📦' },
    { title: 'Digital Invoicing', desc: 'Create and share GST invoices in seconds.', icon: '🧾' },
    { title: 'Customer Ledger', desc: 'Track udhar and get automated payment reminders.', icon: '👥' },
  ];

  return `
    <div class="onboarding-container">
      <div class="onboarding-carousel">
        ${slides.map((slide, index) => `
          <div class="onboarding-slide ${index === 0 ? 'active' : ''}">
            <div class="onboarding-icon">${slide.icon}</div>
            <h2 class="mt-lg">${slide.title}</h2>
            <p class="text-secondary mt-md">${slide.desc}</p>
          </div>
        `).join('')}
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
  `;
}

OnboardingPage.init = () => {
  const slides = document.querySelectorAll('.onboarding-slide');
  const dots = document.querySelectorAll('.dot');
  let currentSlide = 0;

  function showSlide(index) {
    slides.forEach(slide => slide.classList.remove('active'));
    dots.forEach(dot => dot.classList.remove('active'));
    
    slides[index].classList.add('active');
    dots[index].classList.add('active');
  }

  // Auto-slide every 3 seconds
  const interval = setInterval(() => {
    currentSlide = (currentSlide + 1) % slides.length;
    showSlide(currentSlide);
  }, 3000);

  // Manual dot navigation
  dots.forEach((dot, index) => {
    dot.addEventListener('click', () => {
      clearInterval(interval);
      currentSlide = index;
      showSlide(currentSlide);
    });
  });

  // Cleanup on hash change (optional but good practice)
  window.addEventListener('hashchange', () => clearInterval(interval), { once: true });
};
