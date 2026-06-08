# ToDo List API

API REST para gerenciamento de tarefas, desenvolvida com Java 21, Spring Boot, SQL Server, Flyway, Swagger/OpenAPI e testes automatizados.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- SQL Server
- Flyway
- Swagger/OpenAPI
- JUnit 5
- Docker Compose
- GitHub Actions

## Pre-requisitos

Antes de iniciar, tenha instalado:

- Java 21
- Docker
- Docker Compose
- Git

No Windows, os comandos Maven podem ser executados com:

```bash
.\mvnw.cmd
```

No Linux ou macOS:

```bash
./mvnw
```

## 1. Subir o SQL Server com Docker Compose

Na raiz do projeto, execute:

```bash
docker compose up -d
```

Esse comando sobe:

- um container SQL Server em `localhost:1433`
- um volume persistente para os dados do banco
- um container de inicializacao que cria o banco `todo_list`

Para verificar se os containers estao rodando:

```bash
docker compose ps
```

## 2. Configuracao do banco

A aplicacao usa as seguintes configuracoes padrao:

```text
SQLSERVER_URL=jdbc:sqlserver://localhost:1433;databaseName=todo_list;encrypt=true;trustServerCertificate=true
SQLSERVER_USERNAME=sa
SQLSERVER_PASSWORD=123Mudar
```

Esses valores ja estao compativeis com o `docker-compose.yml`.

## 3. Migrations

As migrations ficam em:

```text
src/main/resources/db/migration
```

A primeira migration cria a tabela `tasks`:

```text
V1__create_tasks_table.sql
```

Ao iniciar a aplicacao, o Flyway executa automaticamente as migrations pendentes. O Hibernate esta configurado com `ddl-auto: validate`, entao ele apenas valida o schema criado pelo Flyway.

## 4. Rodar a aplicacao

Com o banco no ar, execute:

```bash
.\mvnw.cmd spring-boot:run
```

Ou, em Linux/macOS:

```bash
./mvnw spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

## 5. Acessar o Swagger

Com a aplicacao rodando, acesse:

```text
http://localhost:8080/swagger-ui.html
```

A especificacao OpenAPI em JSON fica em:

```text
http://localhost:8080/v3/api-docs
```

## 6. Endpoints principais

```text
POST   /tasks
GET    /tasks
GET    /tasks/{id}
PUT    /tasks/{id}
PATCH  /tasks/{id}/complete
PATCH  /tasks/{id}/reopen
DELETE /tasks/{id}
```

Exemplo de payload para criar uma tarefa:

```json
{
  "title": "Estudar testes unitarios",
  "description": "Criar testes para dominio, aplicacao e controller."
}
```

## 7. Rodar os testes

Execute:

```bash
.\mvnw.cmd test
```

Ou, em Linux/macOS:

```bash
./mvnw test
```

## 8. Gerar o build

Para gerar o arquivo `.jar`:

```bash
.\mvnw.cmd clean package
```

O artefato sera gerado em:

```text
target/
```

## 9. Parar o ambiente local

Para parar os containers:

```bash
docker compose down
```

Para parar e remover tambem o volume do banco:

```bash
docker compose down -v
```

Use `down -v` apenas quando quiser apagar os dados locais do SQL Server.

## CI/CD

O projeto possui uma esteira em GitHub Actions:

```text
.github/workflows/ci-cd.yml
```

Ela executa build e testes, sobe um SQL Server no pipeline, cria o banco `db_todolist`, empacota a aplicacao e publica o `.jar` como artefato.
