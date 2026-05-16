CREATE TABLE IF NOT EXISTS tags
(
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL UNIQUE,
    theory_id UUID         NOT NULL REFERENCES theory (id)
);