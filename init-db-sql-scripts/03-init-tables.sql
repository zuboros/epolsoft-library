CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
);

ALTER TABLE public.user
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS isBlocked boolean NOT NULL,
    ADD COLUMN IF NOT EXISTS mail citext NOT NULL UNIQUE,
    ADD COLUMN IF NOT EXISTS avatar VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255) COLLATE pg_catalog."default" NOT NULL;

CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_role_to_user_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_role_to_role_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.topic
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS isActive BOOLEAN NOT NULL;

INSERT INTO public.role (id, name) VALUES (1, "USER"),
                                          (2, "ADMIN"),
                                          (3, "ANONYMOUS");

ALTER TABLE public.book
    DROP IF EXISTS COLUMN file,
    ADD COLUMN IF NOT EXISTS fileName VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    ADD COLUMN IF NOT EXISTS filePath VARCHAR(255) COLLATE pg_catalog."default" NOT NULL;