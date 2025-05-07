CREATE TABLE clientes (
    id INT PRIMARY KEY,
    empresa VARCHAR(100),
    FOREIGN KEY (id) REFERENCES usuario(id)
);
