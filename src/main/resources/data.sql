INSERT INTO BOOK (book_type, title, author, publication_date, price, isbn, format, file_size_mb, narrator, duration_minutes, created_at)
VALUES
    -- E-Books (valorizzano format e file_size_mb)
    ('EBOOK', 'Spring Boot 3 API Mastery', 'Vincenzo Racca', '2025-03-01', 50.00, '9365898080', 'PDF', 15.50, NULL, NULL, '2026-06-01T00:00:00Z'),
    ('EBOOK', 'Mastering Java 21', 'Luca Rossi', '2024-11-15', 45.50, '9781234567890', 'EPUB', 8.20, NULL, NULL, '2026-06-01T10:00:00Z'),
    ('EBOOK', 'PostgreSQL for Beginners', 'Giulia Verdi', '2025-01-10', 25.99, '9783456789012', 'PDF', 12.00, NULL, NULL, '2026-06-03T09:15:00Z'),
    ('EBOOK', 'React vs Angular', 'Sara Greco', '2025-04-01', 38.00, '9780123456789', 'EPUB', 5.40, NULL, NULL, '2026-06-10T10:20:00Z'),

    -- AudioBooks (valorizzano narrator e duration_minutes)
    ('AUDIOBOOK', 'Nice book', 'Guglielmo Boccia', '2025-02-01', 30.00, '1295838080', NULL, NULL, 'Francesco Pannofino', 320, '2026-06-01T00:00:00Z'),
    ('AUDIOBOOK', 'Cloud Native Architecture', 'Marco Bianchi', '2023-08-20', 35.00, '9782345678901', NULL, NULL, 'Luca Ward', 450, '2026-06-02T11:30:00Z'),
    ('AUDIOBOOK', 'The Agile Developer', 'Elena Ricci', '2020-07-22', 28.50, '9788901234567', NULL, NULL, 'Pino Insegno', 280, '2026-06-08T15:30:00Z'),
    ('AUDIOBOOK', 'Clean Code Practices', 'Davide Costa', '2023-11-11', 49.99, '9781122334455', NULL, NULL, 'Roberto Pedicini', 510, '2026-06-11T09:00:00Z'),

    -- Paperback / Libri fisici (lasciano a NULL i campi specifici degli altri tipi)
    ('PAPERBACK', 'Advanced Hibernate', 'Francesca Neri', '2022-05-05', 40.00, '9784567890123', NULL, NULL, NULL, NULL, '2026-06-04T14:45:00Z'),
    ('PAPERBACK', 'Effective Microservices', 'Alessandro Bruno', '2024-09-30', 55.00, '9785678901234', NULL, NULL, NULL, NULL, '2026-06-05T08:20:00Z'),
    ('PAPERBACK', 'Design Patterns Explained', 'Chiara Romano', '2021-12-01', 32.90, '9786789012345', NULL, NULL, NULL, NULL, '2026-06-06T16:10:00Z'),
    ('PAPERBACK', 'Docker & Kubernetes', 'Stefano Colombo', '2023-04-18', 48.00, '9787890123456', NULL, NULL, NULL, NULL, '2026-06-07T12:05:00Z'),
    ('PAPERBACK', 'Security in Spring', 'Matteo Marino', '2024-02-14', 42.00, '9789012345678', NULL, NULL, NULL, NULL, '2026-06-09T18:00:00Z');