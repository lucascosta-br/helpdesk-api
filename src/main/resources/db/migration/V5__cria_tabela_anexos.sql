CREATE TABLE anexos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    caminho VARCHAR(150) NOT NULL,
    tamanho BIGINT,

    chamado_id BIGINT NOT NULL,
    CONSTRAINT fk_anexo_chamado
        FOREIGN KEY (chamado_id)
        REFERENCES chamados (id)
        ON DELETE CASCADE
);
