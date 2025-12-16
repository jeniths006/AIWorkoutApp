
DO $$
BEGIN
    IF NOT EXISTS (
       SELECT 1
       FROM pg_constraint
       WHERE conname = 'users_email_unique'
    ) THEN
       ALTER TABLE users
            ADD CONSTRAINT users_email_uniqie UNIQUE (email);
    END IF;
END$$;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS password_hash TEXT;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW();

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW();