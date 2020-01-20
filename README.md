# store-api

- [x] Spring Boot 2.2.1
- [x] Spring Security (JWT)
- [x] Spring Data
- [x] Lombok
- [x] Flyway
- [x] Swagger
- [x] PostgresSQL

### Database

Postgres local (profile dev)
```console
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Postgres in docker network (profile container)
```console
spring.datasource.url=jdbc:postgresql://db:5432/store?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username=postgres
spring.datasource.password=postgres
```
### Run the Application

```console
docker-compose up -d --build
```

### Swagger

```
http://localhost:8080/store/swagger-ui.html
```


### Authentication

```json
POST localhost:8080/store/auth/login
{
    "username": "jhon@gmail.com",
    "password": "123456"
}
```
