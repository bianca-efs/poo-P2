Nome do projeto: Sistema de Gerenciamento de Papelaria
---
Nome completo dos integrantes:
* Bianca Felipe da Silva
* Victor Souza Perez
---
Tema escolhido:
Sistema de Gerenciamento de Papelaria.

Descrição do problema resolvido:
O sistema foi desenvolvido para auxiliar no gerenciamento de uma papelaria, permitindo o cadastro, consulta, atualização e exclusão de informações essenciais para o funcionamento do negócio. A aplicação centraliza o controle de clientes, fornecedores, funcionários e produtos, facilitando a organização dos dados, reduzindo erros de registros manuais e agilizando os processos administrativos.
---
Lista de entidades implementadas:

* Cliente
* Funcionário
* Fornecedor
* Produto
---
Instruções para execução:

* Instalar o Java JDK 21.
* Instalar e configurar o MySQL Server (MySQL 9.7 Command Line Client).
* Criar o banco de dados denominado "papelaria".
* Configurar o usuário e a senha do banco de dados conforme definidos nas classes DAO do projeto.
* Executar os scripts de criação das tabelas: (

"CREATE DATABASE papelaria;
USE papelaria;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    data_cadastro DATE NOT NULL
);

CREATE TABLE fornecedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    data_contrato DATE NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_contrato DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    setor VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL
);"

)
* Realizar o clone do repositório utilizando o comando git clone.
* Entrar na pasta do projeto via Prompt de Comando (CMD) e executar o comando: gradle run.
* Utilizar o menu principal para acessar as funcionalidades do sistema.
---
Divisão de responsabilidades por integrante:

Bianca Felipe da Silva:

* Modelagem das entidades Funcionario e Cliente.
* Desenvolvimento das telas em JavaFX: FuncionarioBoundary, ClienteBoundary e MenuBoundary.
* Implementação das funcionalidades de cadastro, consulta, atualização e exclusão relacionadas às entidades Funcionario e Cliente.
* Desenvolvimento da camada de persistência (DAO) das entidades Funcionario e Cliente.
* Integração com o banco de dados MySQL das entidades Funcionario e Cliente.
* Implementação das consultas SQL relacionadas às entidades Funcionario e Cliente.
* Testes de interface, validações e correção de erros.

Victor Souza Perez:

* Modelagem das entidades Fornecedor e Produto.
* Desenvolvimento das telas em JavaFX: FornecedorBoundary e ProdutoBoundary.
* Implementação das funcionalidades de cadastro, consulta, atualização e exclusão relacionadas às entidades Fornecedor e Produto.
* Desenvolvimento da camada de persistência (DAO) das entidades Fornecedor e Produto.
* Integração com o banco de dados MySQL das entidades Fornecedor e Produto.
* Implementação das consultas SQL relacionadas às entidades Fornecedor e Produto.
* Testes de interface, validações e correção de erros.

Link para o vídeo no YouTube:
https://youtu.be/lNm-N-EXVsk