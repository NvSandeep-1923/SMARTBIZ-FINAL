import { Header, Navigation } from '../components/Navigation';
import { t } from '../utils/i18n';

export function LanguageSettingsPage() {
  const languages = [
    { code: 'en', name: 'English', native: 'English', icon: '🇺🇸' },
    { code: 'hi', name: 'Hindi', native: 'हिन्दी', icon: '🇮🇳' },
    { code: 'te', name: 'Telugu', native: 'తెలుగు', icon: '🇮🇳' },
    { code: 'mr', name: 'Marathi', native: 'मराठी', icon: '🇮🇳' },
    { code: 'gu', name: 'Gujarati', native: 'ગુજરાતી', icon: '🇮🇳' },
    { code: 'ta', name: 'Tamil', native: 'தமிழ்', icon: '🇮🇳' },
  ];

  const currentLang = localStorage.getItem('app_lang') || 'en';

  return `
    <div class="page-container">
      <header class="app-header">
        <button class="icon-btn" onclick="window.history.back()">⬅️</button>
        <h1 class="header-title">${t('app_language')}</h1>
      </header>
      
      <main class="content-area">
        <section class="card mb-lg">
          <p class="text-sm text-secondary mb-md">Select your preferred language for the application interface.</p>
          
          <div class="language-list">
            ${languages.map(lang => `
              <div class="language-item flex justify-between items-center py-md border-b clickable-lang" data-code="${lang.code}">
                <div class="flex items-center gap-md">
                  <span class="text-xl">${lang.icon}</span>
                  <div>
                    <div class="font-bold">${lang.name}</div>
                    <div class="text-xs text-secondary">${lang.native}</div>
                  </div>
                </div>
                <div class="selection-indicator">
                  ${currentLang === lang.code ? '<span class="text-success">● Selected</span>' : '<span class="text-secondary">○</span>'}
                </div>
              </div>
            `).join('')}
          </div>
        </section>
        
        <button class="btn btn-primary w-full" id="btn-save-language">${t('save_apply')}</button>
      </main>
      
      ${Navigation('settings')}
    </div>
  `;
}

LanguageSettingsPage.init = () => {
  let selectedLang = localStorage.getItem('app_lang') || 'en';
  const langItems = document.querySelectorAll('.clickable-lang');
  const saveBtn = document.getElementById('btn-save-language');

  langItems.forEach(item => {
    item.addEventListener('click', () => {
      // Update visual selection
      langItems.forEach(i => i.querySelector('.selection-indicator').innerHTML = '<span class="text-secondary">○</span>');
      item.querySelector('.selection-indicator').innerHTML = '<span class="text-success">● Selected</span>';
      
      selectedLang = item.dataset.code;
    });
  });

  saveBtn?.addEventListener('click', () => {
    localStorage.setItem('app_lang', selectedLang);
    alert(t('save_apply') + '!');
    window.location.reload(); // Refresh to apply changes globally
  });
};
