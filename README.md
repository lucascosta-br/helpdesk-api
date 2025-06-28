# 🚀 Helpdesk API

API RESTful para gestão de chamados, clientes, técnicos e geração de relatórios, desenvolvida com Java 21 + Spring Boot + Spring Security + JWT + Swagger + Docker.

## 🛠️ Tecnologias

- Java 21
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Swagger/OpenAPI
- JasperReports
- Spring Mail
- Flyway
- MapStruct
- Bean Validation
- Docker e DockerHub

## 🎯 Funcionalidades

- ✅ Cadastro e autenticação de usuários (Admin, Técnico, Cliente)
- ✅ Controle de acesso por perfis
- ✅ Gestão de chamados
- ✅ Envio automático de emails (na atualização de chamados)
- ✅ Upload e download de arquivos (anexos dos chamados)
- ✅ Importação e Exportação de clientes e técnicos (CSV, XLSX)
- ✅ Geração de relatórios em PDF
- ✅ Documentação interativa via Swagger
- ✅ Versionamento de banco com Flyway
- ✅ Conversão de entidades para DTO com MapStruct
- ✅ Disponível no DockerHub

## 🔐 Segurança

- Login com JWT
- Controle de acesso baseado em perfil:
  - 🔒 Admin
  - 🛠️ Técnico
  - 👨‍💼 Cliente

## 📧 Envio de Emails
- O sistema envia emails automaticamente:
    - Quando um chamado muda para "Em Andamento"
    - Quando um chamado é "Concluído"
- Configuração SMTP no application.yml com suporte para Gmail, Outlook, etc.

## 📦 Rodando com Docker

```bash
# Build da imagem
docker build -t lucasdsousacosta/helpdesk:latest .

# Rodando a API
docker run -p 8080:8080 lucasdsousacosta/helpdesk
```

## 📚 Documentação
Acesse o Swagger UI:

```bash
http://localhost:8080/swagger-ui.html
```

## 🚀 Endpoints principais

| Método | Endpoint                                              | Descrição                                | Perfis           |
|--------|-------------------------------------------------------|------------------------------------------|------------------|
| POST   | /api/v1/autenticacao/login                            | Login e geração de token JWT             | Público          |
| POST   | /api/v1/autenticacao/registrar                        | Cadastro de usuário                      | Admin            |
| GET    | /api/v1/chamados                                      | Listar chamados                          | Admin, Técnico   |
| POST   | /api/v1/chamados                                      | Criar chamado                            | Cliente          |
| PUT    | /api/v1/chamados/{id}                                 | Atualizar status do chamado              | Técnico          |
| GET    | /api/v1/anexos/por-chamado/{chamadoId}                | Listar anexos de um chamado              | Técnico          |
| GET    | /api/v1/anexos/downloadFile/{nomeArquivo:.+}          | Download de arquivo                      | Técnico          |
| POST   | /api/v1/anexos/upload/{chamadoId}                     | Upload de arquivo                        | Cliente          |
| POST   | /api/v1/anexos/uploadMultiplos/{chamadoId}            | Upload de múltiplos arquivos             | Cliente          |
| GET    | /api/v1/relatorios/chamados                           | Gera relatório geral dos chamados        | Admin            |
| POST   | /api/v1/clientes/criarVarios                          | Importação de clientes via arquivo       | Admin            |
| GET    | /api/v1/clientes/exportarTodos                        | Exportar clientes (CSV/XLSX)             | Admin, Técnico   |
| POST   | /api/v1/tecnicos/criarVarios                          | Importação de técnicos via arquivo       | Admin            |
| GET    | /api/v1/tecnicos/exportarTodos                        | Exportar técnicos (CSV/XLSX)             | Admin, Técnico   |

## 🐳 DockerHub

👉 https://hub.docker.com/repository/docker/lucasdsousacosta/helpdesk

## 👨‍💻 Autor

[Lucas Costa](https://github.com/lucascosta-br)  
[LinkedIn](https://www.linkedin.com/in/lucascosta-br)

