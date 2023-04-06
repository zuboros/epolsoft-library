ALTER TABLE public.author RENAME TO "user";

ALTER TABLE public.user
    RENAME CONSTRAINT author_pkey TO user_pkey;

ALTER TABLE public.book
    RENAME COLUMN author_id TO user_id;

ALTER TABLE public.book
    RENAME CONSTRAINT book_author_fkey TO book_user_fkey;

ALTER TABLE public.user
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS is_blocked BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS mail VARCHAR(255) NOT NULL UNIQUE DEFAULT "",
    ADD COLUMN IF NOT EXISTS avatar_name VARCHAR(255) COLLATE pg_catalog."default",
    ADD COLUMN IF NOT EXISTS avatar_path VARCHAR(255) COLLATE pg_catalog."default",
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255) COLLATE pg_catalog."default" NOT NULL DEFAULT "";

ALTER TABLE public.topic
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN IF NOT EXISTS is_active BOOLEAN NOT NULL;

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

INSERT INTO public.role (id, name) VALUES (1, 'USER'),
                                          (2, 'ADMIN'),
                                          (3, 'ANONYMOUS');

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

DROP VIEW IF EXISTS public.library;

CREATE OR REPLACE VIEW public.library
AS
SELECT b.id,
       b.name,
       b.description,
       b.short_description,
       b.updated_at,
       b.created_at,
       t.name AS topic_name,
       u.name AS user_name
FROM book b
         JOIN public.topic t ON b.topic_id = t.id
         JOIN public.user u ON b.user_id = u.id;

ALTER TABLE public.book
    DROP COLUMN IF EXISTS file,
    ADD COLUMN IF NOT EXISTS file_name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    ADD COLUMN IF NOT EXISTS file_path VARCHAR(255) COLLATE pg_catalog."default" NOT NULL;
