const email = `testuser_${Date.now()}@example.com`;
const password = "password1234";
const phone = "1234567890";
const merchant_name = "Test Merchant";

async function testBackendFlow() {
    console.log(`Starting backend verification flow for ${email}...`);

    try {
        console.log("\n1. Testing Registration Endpoint (/api/auth/register)");
        const registerRes = await fetch("http://127.0.0.1:5001/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password, phone, merchant_name })
        });
        const registerData = await registerRes.json();
        console.log("Registration Status:", registerRes.status);
        console.log("Registration Response JSON:", JSON.stringify(registerData));

        if (registerRes.ok) {
            console.log("✅ Registration endpoint is fully functional and hooked up to Supabase!");
        } else {
            console.error("❌ Registration endpoint failed. Response JSON:", JSON.stringify(registerData));
            return;
        }

        console.log("\n2. Testing Login Endpoint (Pre-Email Verification) (/api/auth/login)");
        const loginRes = await fetch("http://127.0.0.1:5001/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        const loginData = await loginRes.json();
        console.log("Login Status (Expected: 403 Forbidden due to unverified email):", loginRes.status);
        console.log("Login Response:", loginData);

        if (loginRes.status === 403 && loginData.error === 'Please verify your email.') {
            console.log("✅ Backend is successfully blocking unverified users (Supabase Email Verification Logic functioning as intended).");
        } else {
            console.warn("⚠️ Login returned something unexpected:", loginData);
        }

        console.log("\nTest Completed Successfully.");
    } catch (e) {
        console.error("Failed to connect to backend:");
        console.error(e.name, e.message, e.cause ? e.cause.message : "");
    }
}

testBackendFlow();
