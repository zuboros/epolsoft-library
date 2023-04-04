CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
);

ALTER TABLE public.author
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS isBlocked boolean NOT NULL,
    ADD COLUMN IF NOT EXISTS mail citext NOT NULL UNIQUE,
    ADD COLUMN IF NOT EXISTS avatar VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255) COLLATE pg_catalog."default" NOT NULL;

CREATE TABLE IF NOT EXISTS public.author_role
(
    author_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT author_role_pkey PRIMARY KEY (author_id, role_id),
    CONSTRAINT author_role_to_author_fkey FOREIGN KEY (author_id)
        REFERENCES public.author (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT author_role_to_role_fkey FOREIGN KEY (role_id)
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
    DROP IF EXISTS COLUMN name,
    ADD COLUMN IF NOT EXISTS fileName VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    ADD COLUMN IF NOT EXISTS filePath VARCHAR(255) COLLATE pg_catalog."default" NOT NULL;

INSERT INTO public.role (id, name) VALUES (1, "USER"),
                                          (2, "ADMIN"),
                                          (3, "ANONYMOUS");