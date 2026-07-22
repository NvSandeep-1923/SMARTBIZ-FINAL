-- Row Level Security Policies

ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.customers ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.ledger ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.inventory ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.invoices ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.invoice_items ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.expenses ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.translations ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.notifications ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.attachments ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.complaints ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.activity_logs ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.feedback ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.settings ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.dashboard_stats ENABLE ROW LEVEL SECURITY;

-- Helper: is admin
CREATE OR REPLACE FUNCTION public.is_admin()
RETURNS BOOLEAN
LANGUAGE sql
STABLE
SECURITY DEFINER
SET search_path = public
AS $$
    SELECT EXISTS (
        SELECT 1 FROM public.profiles
        WHERE id = auth.uid() AND role = 'Admin'
    );
$$;

-- Helper: is officer or admin
CREATE OR REPLACE FUNCTION public.is_officer_or_admin()
RETURNS BOOLEAN
LANGUAGE sql
STABLE
SECURITY DEFINER
SET search_path = public
AS $$
    SELECT EXISTS (
        SELECT 1 FROM public.profiles
        WHERE id = auth.uid() AND role IN ('Officer', 'Admin')
    );
$$;

-- ─── PROFILES ────────────────────────────────────────────────────────────────
CREATE POLICY "Users read own profile"
    ON public.profiles FOR SELECT
    USING (id = auth.uid() OR public.is_admin());

CREATE POLICY "Users update own profile"
    ON public.profiles FOR UPDATE
    USING (id = auth.uid())
    WITH CHECK (id = auth.uid() AND role = (SELECT role FROM public.profiles WHERE id = auth.uid()));

CREATE POLICY "Admins read all profiles"
    ON public.profiles FOR SELECT
    USING (public.is_admin());

-- ─── CUSTOMERS ───────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own customers"
    ON public.customers FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers read all customers"
    ON public.customers FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── LEDGER ──────────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own ledger"
    ON public.ledger FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers read all ledger"
    ON public.ledger FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── INVENTORY ───────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own inventory"
    ON public.inventory FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers read all inventory"
    ON public.inventory FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── INVOICES ────────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own invoices"
    ON public.invoices FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers read all invoices"
    ON public.invoices FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── INVOICE ITEMS (via invoice ownership) ───────────────────────────────────
CREATE POLICY "Users manage own invoice items"
    ON public.invoice_items FOR ALL
    USING (
        EXISTS (
            SELECT 1 FROM public.invoices i
            WHERE i.id = invoice_id AND (i.user_id = auth.uid() OR public.is_admin())
        )
    )
    WITH CHECK (
        EXISTS (
            SELECT 1 FROM public.invoices i
            WHERE i.id = invoice_id AND (i.user_id = auth.uid() OR public.is_admin())
        )
    );

-- ─── EXPENSES ────────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own expenses"
    ON public.expenses FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers read all expenses"
    ON public.expenses FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── TRANSLATIONS (public read) ──────────────────────────────────────────────
CREATE POLICY "Anyone authenticated can read translations"
    ON public.translations FOR SELECT
    TO authenticated
    USING (true);

CREATE POLICY "Admins manage translations"
    ON public.translations FOR ALL
    USING (public.is_admin())
    WITH CHECK (public.is_admin());

-- ─── NOTIFICATIONS ───────────────────────────────────────────────────────────
CREATE POLICY "Users manage own notifications"
    ON public.notifications FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

-- ─── ATTACHMENTS ─────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own attachments"
    ON public.attachments FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

-- ─── COMPLAINTS ───────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own complaints"
    ON public.complaints FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers view all complaints"
    ON public.complaints FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── ACTIVITY LOGS ────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own activity logs"
    ON public.activity_logs FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

-- ─── FEEDBACK ─────────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own feedback"
    ON public.feedback FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

CREATE POLICY "Officers view all feedback"
    ON public.feedback FOR SELECT
    USING (public.is_officer_or_admin());

-- ─── SETTINGS ─────────────────────────────────────────────────────────────────
CREATE POLICY "Users manage own settings"
    ON public.settings FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

-- ─── DASHBOARD STATS ──────────────────────────────────────────────────────────
CREATE POLICY "Users manage own dashboard stats"
    ON public.dashboard_stats FOR ALL
    USING (user_id = auth.uid() OR public.is_admin())
    WITH CHECK (user_id = auth.uid() OR public.is_admin());

-- Realtime publication
ALTER PUBLICATION supabase_realtime ADD TABLE public.notifications;
ALTER PUBLICATION supabase_realtime ADD TABLE public.ledger;
ALTER PUBLICATION supabase_realtime ADD TABLE public.invoices;
ALTER PUBLICATION supabase_realtime ADD TABLE public.complaints;
ALTER PUBLICATION supabase_realtime ADD TABLE public.dashboard_stats;
