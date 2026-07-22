-- Rename avatar to avatar_url in profiles table
ALTER TABLE public.profiles RENAME COLUMN avatar TO avatar_url;
