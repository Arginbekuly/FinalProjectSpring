CREATE TABLE IF NOT EXISTS evidence
(
    id                UUID         NOT NULL PRIMARY KEY,
    theory_id         UUID         NOT NULL REFERENCES theory (id),
    user_id           UUID         NOT NULL REFERENCES users (id),
    type              VARCHAR(50)  NOT NULL CHECK (type IN ('TEXT', 'LINK', 'IMAGE_REFERENCE', 'VIDEO_REFERENCE', 'DOCUMENT_REFERENCE')),
    title             VARCHAR(255) NOT NULL,
    content           VARCHAR(255) NOT NULL,
    source_url        VARCHAR(255) NOT NULL,
    source_name       VARCHAR(255) NOT NULL,
    reliability_score INTEGER      NOT NULL,
    created_at        TIMESTAMP    NOT NULL,
    updated_at        TIMESTAMP    NOT NULL
);