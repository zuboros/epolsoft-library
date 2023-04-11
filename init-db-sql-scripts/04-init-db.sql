CREATE TYPE bookStatus AS ENUM ('CREATED', 'WAIT_APPROVING', 'ACTIVED', 'BLOCKED', 'ARCHIVED');

ALTER TABLE public.book
    ADD COLUMN IF NOT EXISTS status bookStatus NOT NULL DEFAULT 'CREATED';

ALTER TABLE public.users
    ADD COLUMN IF NOT EXISTS password_updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE public.users
    DROP COLUMN IF EXISTS avatar_name;

CREATE TABLE IF NOT EXISTS public.policy
(
    id bigint NOT NULL,
    reg_validation VARCHAR(255) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    password_expiration_time INTERVAL DAY TO MINUTE NOT NULL DEFAULT '5 minute'
);

INSERT INTO public.policy (id, reg_validation, password_expiration_time)
    VALUES (1, '^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$', '5 minute');

DELETE FROM public.role WHERE id = 3;
INSERT INTO public.role (id, name) VALUES (3, 'MODERATOR');

CREATE OR REPLACE FUNCTION isPasswordExpired(_passwordUpdatedAt TIMESTAMP) RETURNS BOOLEAN AS
$$
    DECLARE
        _password_expiration_time INTERVAL DAY TO MINUTE;
    BEGIN
        SELECT password_expiration_time INTO _password_expiration_time FROM policy WHERE id = 1;
        RETURN (CAST(_passwordUpdatedAt AS TIMESTAMP  WITHOUT TIME ZONE) + _password_expiration_time)
                   < CAST(now() AS TIMESTAMP  WITHOUT TIME ZONE);
    END
$$ LANGUAGE plpgsql;