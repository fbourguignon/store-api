# store-api
API REST com Spring Boot 2.2.1 , Spring Data, Spring Security, JWT e Lombok


### Banco de dados

O banco utilizado foi o postgresSQL.

### Build do Projeto

```console
mvn clean install
```

### Rodando o projeto

Para buildar somente a imagem da api.

```console
docker build ./ -t store-api
```
Executando a imagem

```console
docker run -d -p 8080:8080 store-api
```
