CREATE TABLE IF NOT EXISTS analysis_results
(
    id                      UUID         NOT NULL PRIMARY KEY,
    theory_id               UUID         NOT NULL REFERENCES theory (id),
    evidence_score          FLOAT        NOT NULL,
    consistency_score       FLOAT        NOT NULL,
    community_score         FLOAT        NOT NULL,
    final_credibility_score FLOAT        NOT NULL,
    summary                 VARCHAR(255) NOT NULL,
    analyzed_at             TIMESTAMP    NOT NULL
);