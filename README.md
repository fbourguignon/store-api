# store-api
Projeto de estudo de uma API REST com Spring Boot 2.2.1 , Spring Data, Spring Security, JWT e Lombok


### Banco de dados

O banco utilizado foi o postgresSQL com uma database chamada store

As configurações de persistência apontando para o banco rodando no container e na mesma network estão no arquivo do profile container.

```console
spring.datasource.url=jdbc:postgresql://db:5432/store?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Build do Projeto

Para buildar somente o artefato.
```console
mvn clean install
```

### Rodando o projeto

Para subir o ambiente com a API em Springboot e o banco de dados.

```console
docker-compose up -d --build
```

### Swagger

Com o ambiente rodando, para acessar o swagger

http://localhost:8080/store/swagger-ui.html


### Autenticação

```json
POST localhost:8080/store/auth/login
{
    "username": "jhon@gmail.com",
    "password": "123456"
}
```
