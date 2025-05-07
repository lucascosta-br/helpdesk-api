CREATE TABLE tecnicos (
    id INT PRIMARY KEY,
    setor VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES usuario(id)
);
