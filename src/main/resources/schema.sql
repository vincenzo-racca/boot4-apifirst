create table BOOK (
    id serial primary key,
    book_type varchar(255) not null,
    title varchar(255) not null,
    author varchar(255) not null,
    publication_date date,
    price decimal(10, 2),
    isbn varchar(13) unique,

    -- Colonne specifiche per Paperback (Cartaceo)
    pages integer,                       -- Numero di pagine (NULL per altri tipi)
    weight_grams decimal(10, 2),         -- Peso in grammi (NULL per altri tipi)

    format varchar(50),                  -- Es. EPUB, PDF (NULL per altri tipi)
    file_size_mb decimal(10, 2),         -- Dimensione in MB (NULL per altri tipi)

    narrator varchar(255),               -- Voce narrante (NULL per altri tipi)
    duration_minutes integer,            -- Durata in minuti (NULL per altri tipi)
    created_at  timestamp with time zone,
    updated_at  timestamp with time zone
);