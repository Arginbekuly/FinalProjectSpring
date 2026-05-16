CREATE TABLE IF NOT EXISTS votes
(
    id         UUID        NOT NULL PRIMARY KEY,
    user_id    UUID        NOT NULL REFERENCES users (id),
    theory_id  UUID        NOT NULL REFERENCES theory (id),
    type       VARCHAR(50) NOT NULL CHECK (type IN ('UPVOTE', 'DOWNVOTE')),
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    UNIQUE (user_id, theory_id)
);
