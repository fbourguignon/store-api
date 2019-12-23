# store-api
API REST com Spring Boot 2.2.1 , Spring Data, Spring Security, JWT e Lombok


### Banco de dados

O banco utilizado foi o postgresSQL com uma database chamada store

As configurações de persistência apontando para o banco rodando no container e na mesma network estão no arquivo do profile network.

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