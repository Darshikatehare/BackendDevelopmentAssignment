# Movie Management API

This project is a simple backend application built using Spring Boot for managing movie records. It provides basic CRUD operations (Create, Read, Update, Delete) so users can add, view, update, and remove movies from the system.
The application follows REST API principles and uses an H2 in-memory database, so it can run easily without setting up an external database.

## Features

1. Create, update, delete, and view movie records
2. RESTful API endpoints for movie operations
3. In-memory database using H2 for easy setup
4. Validation for movie data fields
5. Swagger UI for testing and viewing APIs
6. Pagination support when fetching movies
7. Unit and integration tests for verifying functionality

## Tech Stack

1. Java 17
2. Spring Boot 3.2.4
3. Spring Data JPA / Hibernate
4. H2 Database
5. Spring Web
6. Lombok
7. Jakarta Validation
8. Springdoc OpenAPI (Swagger)
9. JUnit 5, Mockito
10. Maven

## How to Run the Project

1. Ensure **Java 17** and **Maven** are installed on your machine.
2. Open a terminal or IDE in the root folder `C:\Springdevtools\TaskAPI`.
3. Run the following command to start the application:

   mvn spring-boot:run

   Alternatively, if you're executing an existing built `.jar`:

   mvn clean package
   java -jar target/movie-api-0.0.1-SNAPSHOT.jar
   

4. The application will start on `http://localhost:8080`.

## H2 Database

This project uses the H2 in-memory database, so data is stored only while the application is running. When the server restarts, the data will reset.

To view the H2 console, visit:
- *URL:* http://localhost:8080/h2-console

Database configuration:

- *JDBC URL:* `jdbc:h2:mem:moviedb`
- *Username:* `sa`
- *Password:* `password`

## Swagger API Documentation

Swagger UI is available to test all the API endpoints.

- **Swagger UI Link:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON Link:** http://localhost:8080/v3/api-docs

### Core Endpoints Preview

1. `GET /movies` - Returns a list of movies. Supports pagination (`?page=0&size=10`). 
2. `GET /movies/{id}` - Returns details of a specific movie using its ID. 
3. `POST /movies` - Adds a new movie to the database. 
4. `PUT /movies/{id}` - Updates the details of an existing movie. 
5. `DELETE /movies/{id}` - Deletes a movie from the database. 


## How to Run Tests

To run the tests for the project:

mvn clean test

*This will execute unit tests and integration tests and show the results in the terminal.*
