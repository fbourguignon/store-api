# store-api  [![CircleCI](https://circleci.com/gh/fbourguignon/store-api/tree/master.svg?style=svg)](https://circleci.com/gh/fbourguignon/store-api/tree/master)

Template project with

- [x] Spring Boot 2.2.1
- [x] Spring Security (JWT)
- [x] Spring Data
- [x] Lombok
- [x] Flyway
- [x] Swagger
- [x] Docker
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

From project directory, start up the application by running.
Compose pulls and build the images from project, and starts the services.

```console
docker-compose up -d --build
```

### Swagger

```
http://localhost:8080/store/swagger-ui.html
```
Enter http://localhost:8080/store/swagger-ui.html in a browser to see the application running.

### Authentication
   
   ```json
   POST /store/auth/login HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json   
   {
       "username": "jhon@gmail.com",
       "password": "123456"
   }
   ```
   
### Security

The security expects a token named authorization with the generated JWT.
   
   ```json
    GET /store/products HTTP/1.1
    Host: localhost:8080
    Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwZGZkNTZjOS03NzU4LTRjYzAtOTg0Zi01ZjFhOTA2ZjcyOGMiLCJpYXQiOjE1Nzk0ODY2ODcsImV4cCI6MTU4MDA5MTQ4N30.Ewn0A0OTSX9Ik8dDmQDe33UklZkUD62L-5F_I11dYkpCWqHlpjyOfy8FNS6pJAp4g2EGrRXRFquxaizvfJRQzw
   ```

The project uses two types of roles.

```json
ROLE_ADMIN
ROLE_USER   
```
