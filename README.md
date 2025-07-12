## Translation Service
---

This is a test application built with **Java 21** and **Spring Boot 3.5.x**, submitted for the Remote Java Developer position at **DigitalTolk**.

---

## 🚀 Features

- ✅ Full CRUD APIs using Spring Boot
- ✅ Spring Data JPA for entity relationships
- ✅ Spring Security with JWT-based authentication
- ✅ Swagger UI for API documentation
- ✅ Dockerized stack: App, PostgreSQL, and pgAdmin
- ✅ Bean validation for input data
- ✅ Pre-loaded test data via `CommandLineRunner`

---

## 🐳 Dockerized Setup

To run the application stack using Docker:

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

