-- Storage buckets and policies

INSERT INTO storage.buckets (id, name, public, file_size_limit, allowed_mime_types)
VALUES
    ('avatars', 'avatars', true, 5242880, ARRAY['image/jpeg', 'image/png', 'image/webp']),
    ('profile-images', 'profile-images', true, 5242880, ARRAY['image/jpeg', 'image/png', 'image/webp']),
    ('documents', 'documents', false, 10485760, ARRAY['image/jpeg', 'image/png', 'application/pdf']),
    ('attachments', 'attachments', false, 10485760, ARRAY['image/jpeg', 'image/png', 'application/pdf']),
    ('complaints', 'complaints', false, 10485760, ARRAY['image/jpeg', 'image/png', 'application/pdf'])
ON CONFLICT (id) DO NOTHING;

-- Avatars: users upload to own folder
CREATE POLICY "Users upload own avatar"
    ON storage.objects FOR INSERT
    TO authenticated
    WITH CHECK (bucket_id = 'avatars' AND (storage.foldername(name))[1] = auth.uid()::text);

CREATE POLICY "Users update own avatar"
    ON storage.objects FOR UPDATE
    TO authenticated
    USING (bucket_id = 'avatars' AND (storage.foldername(name))[1] = auth.uid()::text);

CREATE POLICY "Public read avatars"
    ON storage.objects FOR SELECT
    TO public
    USING (bucket_id = 'avatars');

CREATE POLICY "Users delete own avatar"
    ON storage.objects FOR DELETE
    TO authenticated
    USING (bucket_id = 'avatars' AND (storage.foldername(name))[1] = auth.uid()::text);

-- Profile Images: users upload to own folder
CREATE POLICY "Users upload own profile-images"
    ON storage.objects FOR INSERT
    TO authenticated
    WITH CHECK (bucket_id = 'profile-images' AND (storage.foldername(name))[1] = auth.uid()::text);

CREATE POLICY "Users update own profile-images"
    ON storage.objects FOR UPDATE
    TO authenticated
    USING (bucket_id = 'profile-images' AND (storage.foldername(name))[1] = auth.uid()::text);

CREATE POLICY "Public read profile-images"
    ON storage.objects FOR SELECT
    TO public
    USING (bucket_id = 'profile-images');

CREATE POLICY "Users delete own profile-images"
    ON storage.objects FOR DELETE
    TO authenticated
    USING (bucket_id = 'profile-images' AND (storage.foldername(name))[1] = auth.uid()::text);

-- Documents: private per user
CREATE POLICY "Users manage own documents"
    ON storage.objects FOR ALL
    TO authenticated
    USING (bucket_id = 'documents' AND (storage.foldername(name))[1] = auth.uid()::text)
    WITH CHECK (bucket_id = 'documents' AND (storage.foldername(name))[1] = auth.uid()::text);

-- Attachments: private per user
CREATE POLICY "Users manage own attachments storage"
    ON storage.objects FOR ALL
    TO authenticated
    USING (bucket_id = 'attachments' AND (storage.foldername(name))[1] = auth.uid()::text)
    WITH CHECK (bucket_id = 'attachments' AND (storage.foldername(name))[1] = auth.uid()::text);

-- Complaints storage: private per user
CREATE POLICY "Users manage own complaints storage"
    ON storage.objects FOR ALL
    TO authenticated
    USING (bucket_id = 'complaints' AND (storage.foldername(name))[1] = auth.uid()::text)
    WITH CHECK (bucket_id = 'complaints' AND (storage.foldername(name))[1] = auth.uid()::text);

CREATE POLICY "Admins read all storage"
    ON storage.objects FOR SELECT
    TO authenticated
    USING (
        public.is_admin()
    );
