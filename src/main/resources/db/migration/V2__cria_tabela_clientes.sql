CREATE TABLE clientes (
    id INT PRIMARY KEY,
    empresa VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES usuarios(id)
);
