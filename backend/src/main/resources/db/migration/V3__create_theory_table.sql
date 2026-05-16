CREATE TABLE IF NOT EXISTS theory
(
    id                  UUID         NOT NULL PRIMARY KEY,
    user_id             UUID         NOT NULL REFERENCES users (id),
    title               VARCHAR(255) NOT NULL,
    description         TEXT         NOT NULL,
    summary             TEXT         NOT NULL,
    status              VARCHAR(50)  NOT NULL CHECK (status IN ('DRAFT', 'PENDING_REVIEW', 'PUBLISHED', 'FLAGGED', 'REJECTED', 'ARCHIVE')),
    credibility_score   FLOAT        NOT NULL,
    popularity_score    FLOAT        NOT NULL,
    contradiction_count INTEGER      NOT NULL,
    view_count          INTEGER      NOT NULL,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP    NOT NULL,
    published_at        TIMESTAMP    NOT NULL
);
