# Movie Management API

A robust Spring Boot backend application for managing an inventory of movies. It provides a full set of CRUD operations (Create, Read, Update, Delete) to easily manage movie data.

## Features

- **Full CRUD operations** for Movie entities.
- **RESTful API Architecture** adhering to best practices.
- **In-Memory Database** (H2 Database) configured for lightweight development & setup without dependencies.
- **Automatic database initialization** and schema generation.
- **Interactive API Documentation** via OpenAPI (Swagger UI).
- Built-in validation constraints for structured model data.
- **Pagination support** for easy retrieval of massive datasets.
- Fully tested using Unit and Integration Testing layers.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.4** (Framework context/auto-configuration)
- **Spring Data JPA & Hibernate** (Data persistence)
- **H2 Database** (In-memory SQL Database)
- **Spring Web** (RESTful endpoints controller)
- **Lombok** (Boilerplate code reduction)
- **Jakarta Validation** (Input constraints)
- **Springdoc OpenAPI / Swagger** (Documentation & UI)
- **JUnit 5 / Mockito / AssertJ** (Testing framework)
- **Maven** (Dependency & build management)

## How to Run the Project

1. Ensure **Java 17** and **Maven** are installed on your machine.
2. Open a terminal or IDE in the root folder (`c:\Springdevtools\TaskAPI`).
3. Run the following command to start the application:

   ```bash
   mvn spring-boot:run
   ```

   Alternatively, if you're executing an existing built `.jar`:
   ```bash
   mvn clean package
   java -jar target/movie-api-0.0.1-SNAPSHOT.jar
   ```

4. The application will start locally on `http://localhost:8080`.

## Database Details (H2)

The application utilizes an H2 In-Memory database, meaning data is stored locally in RAM while the server is active, and resets on restart.

You can view the H2 Console by visiting:
- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:moviedb`
- **Username:** `sa`
- **Password:** `password`

## Interactive Swagger Documentation

Once the application is running, Swagger UI is automatically generated directly from the codebase logic. You can use it to visually interact with all properties, tests, and paths.

- **Swagger UI Link:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Raw OpenAPI JSON Spec Link:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Core Endpoints Preview

| HTTP Method | API Path | Action | Description |
|---|---|---|---|
| `GET` | `/movies` | Get All Movies | Fetches movies paginated (`?page=0&size=10`). |
| `GET` | `/movies/{id}` | Get Movie | Fetch single movie details using an ID. |
| `POST` | `/movies` | Create Movie | Registers a new movie to the system. |
| `PUT` | `/movies/{id}` | Update Movie | Modifies an existing movie definition. |
| `DELETE` | `/movies/{id}` | Delete Movie | Removes a movie from the database. |


## How to Run Tests

This project includes both `Controller`, `Service`, and full `Integration tests` verifying application logic and controller flow.

To run all background tests through Maven, execute:

```bash
mvn clean test
```

*Status output will be printed to your current terminal.*
