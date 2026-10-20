CREATE TABLE usuarios (
    id          BIGSERIAL PRIMARY KEY,
    nome        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    senha       VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  NOT NULL DEFAULT 'SECRETARIA',
    ativo       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE clientes (
    id             BIGSERIAL PRIMARY KEY,
    nome           VARCHAR(255) NOT NULL,
    telefone       VARCHAR(20),
    email          VARCHAR(255),
    tipo           VARCHAR(20)  NOT NULL DEFAULT 'ATIVO',
    observacoes    TEXT,
    ultima_visita  TIMESTAMP,
    created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE procedimentos (
    id          BIGSERIAL PRIMARY KEY,
    nome        VARCHAR(255) NOT NULL,
    duracao_min INT          NOT NULL,
    valor       DECIMAL(10,2) NOT NULL,
    ativo       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agendamentos (
    id               BIGSERIAL PRIMARY KEY,
    data             DATE         NOT NULL,
    hora             TIME         NOT NULL,
    cliente_id       BIGINT       NOT NULL REFERENCES clientes(id),
    procedimento_id  BIGINT       NOT NULL REFERENCES procedimentos(id),
    duracao_min      INT,
    valor            DECIMAL(10,2),
    status           VARCHAR(20)  NOT NULL DEFAULT 'CONFIRMADO',
    observacoes      TEXT,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE receitas (
    id               BIGSERIAL PRIMARY KEY,
    data             DATE         NOT NULL,
    cliente_id       BIGINT       REFERENCES clientes(id),
    procedimento_id  BIGINT       REFERENCES procedimentos(id),
    valor            DECIMAL(10,2) NOT NULL,
    forma_pagamento  VARCHAR(20)  NOT NULL,
    status           VARCHAR(20)  NOT NULL DEFAULT 'PAGO',
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE despesas (
    id               BIGSERIAL PRIMARY KEY,
    data             DATE         NOT NULL,
    categoria        VARCHAR(30)  NOT NULL,
    descricao        VARCHAR(500),
    valor            DECIMAL(10,2) NOT NULL,
    forma_pagamento  VARCHAR(20)  NOT NULL,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produtos (
    id              BIGSERIAL PRIMARY KEY,
    nome            VARCHAR(255) NOT NULL,
    quantidade      INT          NOT NULL DEFAULT 0,
    estoque_minimo  INT          NOT NULL DEFAULT 0,
    preco_custo     DECIMAL(10,2),
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE movimentacoes_estoque (
    id          BIGSERIAL PRIMARY KEY,
    produto_id  BIGINT      NOT NULL REFERENCES produtos(id),
    tipo        VARCHAR(10) NOT NULL,
    quantidade  INT         NOT NULL,
    observacoes TEXT,
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE historicos_cliente (
    id               BIGSERIAL PRIMARY KEY,
    cliente_id       BIGINT       NOT NULL REFERENCES clientes(id),
    procedimento_id  BIGINT       NOT NULL REFERENCES procedimentos(id),
    data             DATE         NOT NULL,
    valor            DECIMAL(10,2),
    observacoes      TEXT,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_agendamentos_data ON agendamentos(data);
CREATE INDEX idx_agendamentos_cliente ON agendamentos(cliente_id);
CREATE INDEX idx_receitas_data ON receitas(data);
CREATE INDEX idx_despesas_data ON despesas(data);
CREATE INDEX idx_historicos_cliente ON historicos_cliente(cliente_id);
CREATE INDEX idx_produtos_quantidade ON produtos(quantidade);
