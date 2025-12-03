CREATE TABLE IF NOT EXISTS public.acessorios
(
    id SERIAL PRIMARY KEY,
    descricao VARCHAR NOT NULL,
    nome VARCHAR NOT NULL,
    preco DOUBLE NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS public.documentos
(
    id SERIAL PRIMARY KEY,
    renavam VARCHAR NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS public.proprietarios
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR NOT NULL,
    telefone VARCHAR,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS public.veiculo_acessorio (
    veiculo_id BIGINT NOT NULL,
    acessorio_id BIGINT NOT NULL,
    PRIMARY KEY (veiculo_id, acessorio_id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id) ON DELETE CASCADE,
    FOREIGN KEY (acessorio_id) REFERENCES acessorios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.veiculos
(
    id SERIAL PRIMARY KEY,
    tipo_veiculo VARCHAR(20),
    marca VARCHAR,
    modelo VARCHAR,
    ano INTEGER NOT NULL,
    preco DOUBLE PRECISION NOT NULL,
    capacidade_carga DOUBLE PRECISION,
    capacidade_porta_malas DOUBLE PRECISION,
    cilindradas INTEGER NOT NULL,
    observacao VARCHAR,
    combustivel VARCHAR,
    codigoFipe VARCHAR,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    documento_id BIGINT UNIQUE,
    proprietario_id BIGINT,
    FOREIGN KEY (documento_id) REFERENCES documentos(id),
    FOREIGN KEY (proprietario_id) REFERENCES proprietarios(id)
);