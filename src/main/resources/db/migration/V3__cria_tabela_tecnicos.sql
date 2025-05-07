CREATE TABLE tecnicos (
    id INT PRIMARY KEY,
    setor VARCHAR(100),
    FOREIGN KEY (id) REFERENCES usuario(id)
);
