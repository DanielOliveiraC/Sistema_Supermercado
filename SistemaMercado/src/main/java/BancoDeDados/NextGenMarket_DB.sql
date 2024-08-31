CREATE DATABASE NextGenMarket;

-- Criação das tabelas ----------------------------------------------

CREATE TABLE produtos(
	id_produto INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nome VARCHAR(100),
	estoque INT,
	preco DECIMAL(10, 2)
	);
	

CREATE TABLE endereco(
	id_endereco INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	logradouro VARCHAR(50),
	numero VARCHAR(10),
	bairro VARCHAR(50),
	cidade VARCHAR(50)
);

CREATE TABLE cliente(
	id_cliente INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nome_cliente VARCHAR(100),
	cpf VARCHAR(11),
	id_endereco INT,
	telefone VARCHAR(15),
	CONSTRAINT id_endereco FOREIGN KEY(id_endereco) 
		REFERENCES endereco(id_endereco)
		ON DELETE SET NULL
);

CREATE TABLE venda(
    id_venda INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_cliente INT,
    data_venda DATETIME,
    total_venda DECIMAL(10, 2),
    CONSTRAINT id_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE itensVenda (
	id_itens_venda INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	id_venda INT,
	id_produto INT, 
	quantidade INT,
	preco_unit DECIMAL(10,2),
	CONSTRAINT id_venda FOREIGN KEY (id_venda) REFERENCES venda(id_venda),
	CONSTRAINT id_produto FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
);
-- ------------------------------------------------------------------