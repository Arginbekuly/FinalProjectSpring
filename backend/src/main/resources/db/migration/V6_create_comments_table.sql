CREATE TABLE IF NOT EXISTS comments
(
    id         UUID      NOT NULL PRIMARY KEY,
    text       TEXT      NOT NULL,
    user_id    UUID      NOT NULL REFERENCES users (id),
    theory_id  UUID      NOT NULL REFERENCES theory (id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
