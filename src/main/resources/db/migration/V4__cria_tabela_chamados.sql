CREATE TABLE chamados (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    status VARCHAR(20) NOT NULL,
    prioridade VARCHAR(20) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_fechamento TIMESTAMP,

    cliente_id BIGINT,
    tecnico_id BIGINT,

    CONSTRAINT fk_chamado_cliente
    FOREIGN KEY (cliente_id)
    REFERENCES clientes (id)
    ON DELETE SET NULL,

    CONSTRAINT fk_chamado_tecnico
    FOREIGN KEY (tecnico_id)
    REFERENCES tecnicos (id)
    ON DELETE SET NULL

);