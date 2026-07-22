-- SmartBiz Supabase Migration: Initial Schema
-- Run in Supabase SQL Editor or via supabase db push

-- ─── ENUMS ───────────────────────────────────────────────────────────────────
CREATE TYPE public.user_role AS ENUM ('Citizen', 'Officer', 'Admin');

-- ─── PROFILES (linked to auth.users) ─────────────────────────────────────────
CREATE TABLE public.profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    email TEXT NOT NULL,
    full_name TEXT,
    phone TEXT,
    role public.user_role NOT NULL DEFAULT 'Citizen',
    business_name TEXT,
    business_type TEXT,
    gstin TEXT,
    address TEXT,
    avatar TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_profiles_role ON public.profiles(role);
CREATE INDEX idx_profiles_email ON public.profiles(email);

-- Auto-create profile on signup (Citizen only — role cannot be set by user)
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER
LANGUAGE plpgsql
SECURITY DEFINER
SET search_path = public
AS $$
BEGIN
    INSERT INTO public.profiles (id, email, full_name, phone, role, business_name)
    VALUES (
        NEW.id,
        NEW.email,
        COALESCE(NEW.raw_user_meta_data->>'full_name', ''),
        COALESCE(NEW.raw_user_meta_data->>'phone', ''),
        'Citizen',
        COALESCE(NEW.raw_user_meta_data->>'business_name', '')
    );
    RETURN NEW;
END;
$$;

CREATE TRIGGER on_auth_user_created
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();

-- ─── CUSTOMERS ───────────────────────────────────────────────────────────────
CREATE TABLE public.customers (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    phone TEXT,
    email TEXT,
    gstin TEXT,
    address TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_customers_user_id ON public.customers(user_id);

-- ─── LEDGER ──────────────────────────────────────────────────────────────────
CREATE TABLE public.ledger (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    customer_id BIGINT NOT NULL REFERENCES public.customers(id) ON DELETE CASCADE,
    amount NUMERIC(12,2) NOT NULL,
    type TEXT NOT NULL CHECK (type IN ('gave', 'got')),
    description TEXT,
    tx_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ledger_user_id ON public.ledger(user_id);
CREATE INDEX idx_ledger_customer_id ON public.ledger(customer_id);

-- ─── INVENTORY ───────────────────────────────────────────────────────────────
CREATE TABLE public.inventory (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    item_name TEXT NOT NULL,
    category TEXT,
    sku TEXT,
    hsn TEXT,
    sale_price NUMERIC(12,2) DEFAULT 0,
    cost_price NUMERIC(12,2) DEFAULT 0,
    stock_level INTEGER DEFAULT 0,
    unit TEXT,
    gst_rate NUMERIC(5,2) DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_inventory_user_id ON public.inventory(user_id);

-- ─── INVOICES ────────────────────────────────────────────────────────────────
CREATE TABLE public.invoices (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    invoice_number TEXT NOT NULL,
    customer_id BIGINT REFERENCES public.customers(id) ON DELETE SET NULL,
    subtotal NUMERIC(12,2),
    total_gst NUMERIC(12,2),
    grand_total NUMERIC(12,2),
    status TEXT DEFAULT 'final' CHECK (status IN ('draft', 'final')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(user_id, invoice_number)
);

CREATE INDEX idx_invoices_user_id ON public.invoices(user_id);

-- ─── INVOICE ITEMS ───────────────────────────────────────────────────────────
CREATE TABLE public.invoice_items (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT NOT NULL REFERENCES public.invoices(id) ON DELETE CASCADE,
    item_id BIGINT REFERENCES public.inventory(id) ON DELETE SET NULL,
    item_name TEXT NOT NULL,
    qty NUMERIC(12,2) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    gst_rate NUMERIC(5,2) DEFAULT 0,
    subtotal NUMERIC(12,2) NOT NULL
);

CREATE INDEX idx_invoice_items_invoice_id ON public.invoice_items(invoice_id);

-- ─── EXPENSES ────────────────────────────────────────────────────────────────
CREATE TABLE public.expenses (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    description TEXT,
    amount NUMERIC(12,2) NOT NULL,
    category TEXT,
    payment_mode TEXT DEFAULT 'Cash',
    expense_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_expenses_user_id ON public.expenses(user_id);

-- ─── TRANSLATIONS ────────────────────────────────────────────────────────────
CREATE TABLE public.translations (
    id BIGSERIAL PRIMARY KEY,
    lang TEXT NOT NULL,
    key TEXT NOT NULL,
    value TEXT NOT NULL,
    UNIQUE(lang, key)
);

-- ─── NOTIFICATIONS (Realtime) ────────────────────────────────────────────────
CREATE TABLE public.notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    message TEXT NOT NULL,
    type TEXT DEFAULT 'info',
    read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_notifications_user_id ON public.notifications(user_id);

-- ─── COMPLAINTS ───────────────────────────────────────────────────────────────
CREATE TABLE public.complaints (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    status TEXT NOT NULL DEFAULT 'open',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_complaints_user_id ON public.complaints(user_id);

-- ─── ACTIVITY LOGS ────────────────────────────────────────────────────────────
CREATE TABLE public.activity_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    action TEXT NOT NULL,
    details TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_activity_logs_user_id ON public.activity_logs(user_id);

-- ─── FEEDBACK ─────────────────────────────────────────────────────────────────
CREATE TABLE public.feedback (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comments TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_feedback_user_id ON public.feedback(user_id);

-- ─── SETTINGS ─────────────────────────────────────────────────────────────────
CREATE TABLE public.settings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    key TEXT NOT NULL,
    value TEXT,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(user_id, key)
);

CREATE INDEX idx_settings_user_id ON public.settings(user_id);

-- ─── DASHBOARD STATS ──────────────────────────────────────────────────────────
CREATE TABLE public.dashboard_stats (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    stat_type TEXT NOT NULL,
    value JSONB NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(user_id, stat_type)
);

CREATE INDEX idx_dashboard_stats_user_id ON public.dashboard_stats(user_id);

-- ─── ATTACHMENTS (Storage metadata) ──────────────────────────────────────────
CREATE TABLE public.attachments (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    bucket TEXT NOT NULL,
    path TEXT NOT NULL,
    file_name TEXT,
    mime_type TEXT,
    entity_type TEXT,
    entity_id UUID, -- Updated to UUID to reference either complaint id or profile id etc
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_attachments_user_id ON public.attachments(user_id);

-- ─── UPDATED_AT TRIGGER ──────────────────────────────────────────────────────
CREATE OR REPLACE FUNCTION public.set_updated_at()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$;

CREATE TRIGGER profiles_updated_at
    BEFORE UPDATE ON public.profiles
    FOR EACH ROW EXECUTE FUNCTION public.set_updated_at();

CREATE TRIGGER settings_updated_at
    BEFORE UPDATE ON public.settings
    FOR EACH ROW EXECUTE FUNCTION public.set_updated_at();

CREATE TRIGGER dashboard_stats_updated_at
    BEFORE UPDATE ON public.dashboard_stats
    FOR EACH ROW EXECUTE FUNCTION public.set_updated_at();
