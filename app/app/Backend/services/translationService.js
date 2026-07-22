const initialTranslations = {
    "Hindi": {
        "Welcome Back": "स्वागत है",
        "Manage your business with precision": "सटीकता के साथ अपने व्यवसाय का प्रबंधन करें",
        "Email Address": "ईमेल पता",
        "Password": "पासवर्ड",
        "LOGIN SECURELY": "सुरक्षित लॉगिन करें",
        "Don't have an account? ": "क्या आपका अकाउंट नहीं है? ",
        "Register Now": "अभी पंजीकरण करें",
        "SmartBiz": "स्मार्टबिज़",
        "Business Overview": "व्यापार अवलोकन",
        "Today's Sales": "आज की बिक्री",
        "Total Udhar": "कुल उधार",
        "Please verify your email.": "कृपया अपना ईमेल सत्यापित करें।",
        "Citizen Dashboard": "नागरिक डैशबोर्ड",
        "Officer Dashboard": "अधिकारी डैशबोर्ड",
        "Admin Dashboard": "व्यवस्थापक डैशबोर्ड"
    },
    "Telugu": {
        "Welcome Back": "స్వాగతం",
        "SmartBiz": "స్మార్ట్‌బిజ్",
        "Business Overview": "వ్యాపార అవలోకనం",
        "Today's Sales": "నేటి అమ్మకాలు",
        "Please verify your email.": "దయచేసి మీ ఈమెయిల్‌ను ధృవీకరించండి."
    }
};

async function seedTranslations(supabaseAdmin) {
    if (!supabaseAdmin) return;
    const { count } = await supabaseAdmin.from('translations').select('*', { count: 'exact', head: true });
    if (count && count > 0) return;

    const rows = [];
    for (const [lang, map] of Object.entries(initialTranslations)) {
        for (const [key, value] of Object.entries(map)) {
            rows.push({ lang, key, value });
        }
    }
    await supabaseAdmin.from('translations').insert(rows);
    console.log('Seeded translations.');
}

async function getTranslations(supabase, lang) {
    const { data, error } = await supabase.from('translations').select('key, value').eq('lang', lang);
    if (error) throw error;
    const map = {};
    (data || []).forEach(r => { map[r.key] = r.value; });
    return map;
}

module.exports = { seedTranslations, getTranslations };
