CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
);

ALTER TABLE public.author
    ADD COLUMN created_at timestamp without time zone,
    ADD COLUMN updated_at timestamp without time zone,
    ADD COLUMN isBlocked boolean NOT NULL,
    ADD COLUMN mail citext NOT NULL UNIQUE,
    ADD COLUMN avatar character varying(255) COLLATE pg_catalog."default" NOT NULL,
    ADD COLUMN password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL;

CREATE TABLE IF NOT EXISTS public.author_role
(
    id bigint NOT NULL,
    author_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT author_role_pkey PRIMARY KEY (id),
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
    ADD COLUMN created_at timestamp without time zone,
    ADD COLUMN updated_at timestamp without time zone,
    ADD COLUMN isActive boolean NOT NULL;

INSERT INTO public.role (id, name) VALUES (1, "USER"),
                                          (2, "ADMIN");
