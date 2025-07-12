# Translation Service
<hr>

* This is a test application build on Java 21 and spring-boot 3.5.X for applying to a java developer position. 
* The project is a spring-boot service with CRUD implementations, Spring Data JPA, spring-security, swagger implemented.
* This project has CommandLineRunners setup to add test data in the database at startup.
* Run the following command with **_Java 21_** and **_Gradle 8.14.X_** to start all containers.


    docker compose down -v && ./gradlew clean build && docker compose up --build

* The API's are already secured, in order to register, use the following body to hit **_/api/v1/auth/register_** endpoint to register a user:

        {
          "name": "admin",
          "email": "admin@gmail.com",
          "password": "admin",
          "role": "USER"
        }

* Once user is registered, use the same body to authenticate (**_/api/v1/auth/register_**)
* This will return a JWT which you can add as a Bearer token to access secured API's.
## Design Explanation

* This project is using Spring Data JPA acting as an ORM model to create database relationships.
* There are no performance measures taken as the response times are already low. In case of higher response times, I could have used redis.
* Token based authentication using Spring Security 6.5 and JWTs is implemented.
* Swagger is implemented for API Documentation.
* The complete project is dockerized, the application, postgres-db and pg-admin.
* Validation is implemented using spring-data-validation.

