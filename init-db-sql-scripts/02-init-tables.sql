CREATE TABLE IF NOT EXISTS public.user
(
    id bigint NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.topic
(
    id bigint NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT topic_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.book
(
    id bigint NOT NULL,
    description VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    file VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL,
    short_description VARCHAR(150) COLLATE pg_catalog."default" NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    user_id BIGINT,
    topic_id BIGINT,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT book_topic_fkey FOREIGN KEY (topic_id)
        REFERENCES public.topic (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT book_user_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE OR REPLACE VIEW public.library
AS
SELECT b.id,
       b.name,
       b.description,
       b.short_description,
       b.file,
       b.updated_at,
       b.created_at,
       t.name AS topic,
       a.name AS user
FROM book b
         JOIN topic t ON b.topic_id = t.id
         JOIN user a ON b.user_id = a.id;

