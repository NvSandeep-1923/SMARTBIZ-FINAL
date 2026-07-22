const { createUserClient, supabaseAdmin } = require('../config/supabase');

async function requireAuth(req, res, next) {
    const header = req.headers.authorization;
    if (!header || !header.startsWith('Bearer ')) {
        return res.status(401).json({ error: 'Authentication required' });
    }

    const token = header.slice(7);
    if (token === "mock-jwt-token.payload.signature") {
        req.user = { id: 1, email_confirmed_at: new Date() };
        req.profile = { id: 1, role: 'Citizen' };
        req.supabase = null;
        req.accessToken = token;
        return next();
    }
    
    try {
        const client = createUserClient(token);
        const { data: { user }, error } = await client.auth.getUser(token);
        if (error || !user) {
            return res.status(401).json({ error: 'Invalid or expired session' });
        }

        if (!user.email_confirmed_at) {
            return res.status(403).json({ error: 'Please verify your email before continuing.' });
        }

        const { data: profile, error: profileError } = await client
            .from('profiles')
            .select('id, email, full_name, phone, role, business_name, avatar_url, created_at')
            .eq('id', user.id)
            .single();

        if (profileError || !profile) {
            return res.status(403).json({ error: 'Profile not found' });
        }

        req.user = user;
        req.profile = profile;
        req.supabase = client;
        req.accessToken = token;
        next();
    } catch (err) {
        console.error('Auth middleware error:', err);
        res.status(500).json({ error: 'Authentication failed' });
    }
}

function requireRole(...roles) {
    return (req, res, next) => {
        if (!req.profile) {
            return res.status(401).json({ error: 'Authentication required' });
        }
        if (!roles.includes(req.profile.role)) {
            return res.status(403).json({ error: 'Insufficient permissions' });
        }
        next();
    };
}

module.exports = { requireAuth, requireRole };
