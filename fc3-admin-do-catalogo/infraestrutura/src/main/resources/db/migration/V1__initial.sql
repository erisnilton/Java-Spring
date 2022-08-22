
CREATE TABLE category (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    description VARCHAR(4000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updatad_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
)
