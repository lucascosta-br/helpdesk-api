CREATE TABLE anexos (
    id SERIAL PRIMARY KEY,
    nome_arquivo VARCHAR(255) NOT NULL,
    dados BYTEA NOT NULL,

    chamado_id BIGINT NOT NULL,
    CONSTRAINT fk_anexo_chamado
        FOREIGN KEY (chamado_id)
        REFERENCES chamados (id)
        ON DELETE CASCADE
);
