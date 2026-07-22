export function OTPPage() {
  return `
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
  `;
}

OTPPage.init = () => {
  const phone = localStorage.getItem('temp_phone') || '98765 43210';
  const displayPhone = document.getElementById('display-phone');
  if (displayPhone) displayPhone.textContent = `+91 ${phone}`;

  const digits = document.querySelectorAll('.otp-digit');
  digits.forEach((digit, index) => {
    digit.addEventListener('input', (e) => {
      if (e.target.value && index < digits.length - 1) {
        digits[index + 1].focus();
      }
    });

    digit.addEventListener('keydown', (e) => {
      if (e.key === 'Backspace' && !e.target.value && index > 0) {
        digits[index - 1].focus();
      }
    });
  });

  const form = document.getElementById('otp-form');
  if (form) {
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      window.location.hash = '#dashboard';
    });
  }

  // Simple timer
  let timeLeft = 45;
  const timerDisplay = document.getElementById('timer');
  const timerInterval = setInterval(() => {
    timeLeft--;
    if (timeLeft <= 0) {
      clearInterval(timerInterval);
      if (timerDisplay) timerDisplay.parentElement.textContent = 'Resend Code';
    } else {
      const minutes = Math.floor(timeLeft / 60);
      const seconds = timeLeft % 60;
      if (timerDisplay) timerDisplay.textContent = `${minutes}:${seconds.toString().padStart(2, '0')}`;
    }
  }, 1000);

  window.addEventListener('hashchange', () => clearInterval(timerInterval), { once: true });
};
