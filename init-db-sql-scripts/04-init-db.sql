CREATE TYPE bookStatus AS ENUM ('CREATED', 'WAIT_APPROVING', 'ACTIVED', 'BLOCKED', 'ARCHIVED');

ALTER TABLE public.book
    ADD COLUMN IF NOT EXISTS status bookStatus NOT NULL DEFAULT 'CREATED';

ALTER TABLE public.users
    ADD COLUMN IF NOT EXISTS password_updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CAST(now() AS TIMESTAMP WITHOUT TIME ZONE);

ALTER TABLE public.users
    DROP COLUMN IF EXISTS avatar_name;

CREATE TABLE IF NOT EXISTS public.policy
(
    id bigint NOT NULL,
    reg_validation VARCHAR(255) COLLATE pg_catalog."default" NOT NULL DEFAULT '^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$',
    password_expiration_time INTERVAL DAY TO MINUTE NOT NULL DEFAULT '7 day',
    outdate_password_notification_time INTERVAL DAY TO MINUTE NOT NULL DEFAULT '20 minute'
);

INSERT INTO public.policy (id)
    VALUES (1);

DELETE FROM public.role WHERE id = 3;
INSERT INTO public.role (id, name) VALUES (3, 'MODERATOR');

INSERT INTO public.users (id, name, mail, password_hash, updated_at, created_at, password_updated_at)
    VALUES (1,
            'BILLY',
            'BILLY.HARINGTON@mail.ru',
            '$2a$04$1TDFTbZr0UZkqf1l3flku.IP3XKG9LSmF8UYmPZl8OT0ah7pWCAP6',
            CAST(now() AS TIMESTAMP WITHOUT TIME ZONE),
            CAST(now() AS TIMESTAMP WITHOUT TIME ZONE),
            CAST(now() + INTERVAL '100 year' AS TIMESTAMP WITHOUT TIME ZONE));

INSERT INTO public.user_role (role_id, user_id)
    VALUES (1, 1),
           (2, 1);

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

CREATE OR REPLACE FUNCTION howManyDaysNotification(_passwordUpdatedAt TIMESTAMP) RETURNS INTEGER AS
$$
    DECLARE
        _password_expiration_time INTERVAL DAY TO MINUTE;
        _outdate_password_notification_time INTERVAL DAY TO MINUTE;
    BEGIN
        SELECT password_expiration_time INTO _password_expiration_time FROM policy WHERE id = 1;
        SELECT outdate_password_notification_time INTO _outdate_password_notification_time FROM policy WHERE id = 1;
        IF (SELECT * FROM isPasswordExpired(CAST(_passwordUpdatedAt AS TIMESTAMP  WITHOUT TIME ZONE))) IS TRUE
            THEN RETURN -1;
        ELSE
            IF (CAST(_passwordUpdatedAt AS TIMESTAMP  WITHOUT TIME ZONE) + _outdate_password_notification_time
                < CAST(now() AS TIMESTAMP  WITHOUT TIME ZONE))
                THEN RETURN (SELECT EXTRACT(DAY FROM (CAST(_passwordUpdatedAt AS TIMESTAMP  WITHOUT TIME ZONE) + _password_expiration_time
                            - CAST(now() AS TIMESTAMP  WITHOUT TIME ZONE))));
            ELSE
                RETURN -1;
            END IF;
        END IF;
    END
$$ LANGUAGE plpgsql;