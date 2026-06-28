-- BaseDatos.sql
-- Script de inicialización para Docker

CREATE DATABASE IF NOT EXISTS clientes_db;
CREATE DATABASE IF NOT EXISTS cuentas_db;

GRANT ALL PRIVILEGES ON clientes_db.* TO 'micro_user'@'%';
GRANT ALL PRIVILEGES ON cuentas_db.* TO 'micro_user'@'%';
FLUSH PRIVILEGES;

-- --------------------------------------------------------
-- DDL Microservicio Clientes
-- --------------------------------------------------------
USE clientes_db;

CREATE TABLE IF NOT EXISTS persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT PRIMARY KEY,
    cliente_id VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (id) REFERENCES persona(id)
);

-- --------------------------------------------------------
-- DDL Microservicio Cuentas
-- --------------------------------------------------------
USE cuentas_db;

CREATE TABLE IF NOT EXISTS cuenta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(38,2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME(6) NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(38,2) NOT NULL,
    saldo DECIMAL(38,2) NOT NULL,
    saldo_inicial DECIMAL(38,2) NOT NULL,
    numero_cuenta VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL
);
