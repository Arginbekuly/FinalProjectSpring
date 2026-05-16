CREATE TABLE IF NOT EXISTS contradictions
(
    id          UUID         NOT NULL PRIMARY KEY,
    theory_id_a UUID         NOT NULL REFERENCES theory (id),
    theory_id_b UUID         NOT NULL REFERENCES theory (id),
    user_id     UUID         NOT NULL REFERENCES users (id),
    reason      VARCHAR(255) NOT NULL,
    severity    INTEGER      NOT NULL,
    status      VARCHAR(50)  NOT NULL CHECK (status IN ('DETECTED', 'CONFIRMED', 'REJECTED')),
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    UNIQUE (theory_id_a, theory_id_b)
);