CREATE TABLE IF NOT EXISTS public.author
(
    id bigint NOT NULL,
    name VARCHAR(255) COLLATE pg_catalog."default" NOT NULL UNIQUE,
    CONSTRAINT author_pkey PRIMARY KEY (id)
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
    author_id BIGINT,
    topic_id BIGINT,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT book_topic_fkey FOREIGN KEY (topic_id)
        REFERENCES public.topic (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT book_author_fkey FOREIGN KEY (author_id)
        REFERENCES public.author (id) MATCH SIMPLE
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
       a.name AS author
FROM book b
         JOIN topic t ON b.topic_id = t.id
         JOIN author a ON b.author_id = a.id;

