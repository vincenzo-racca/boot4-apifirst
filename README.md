# Boot4 API-First Book Management

This project is a modern Spring Boot application that demonstrates the **API-First** development approach. It manages a library of books with polymorphic data structures (E-Books, Audiobooks, and Paperbacks) using an OpenAPI specification as the single source of truth.

## Core Features

* **API-First Approach:** REST API interfaces and DTOs are generated at compile time using the `openapi-generator-maven-plugin`.
* **Polymorphism:** Supports specific fields for different book formats via OpenAPI `discriminator` and Jackson mapping.
* **Spring Data JDBC:** Uses Single Table Inheritance to map polymorphic entities to an in-memory H2 database.
* **Automatic Auditing:** Automatically populates `createdAt` and `updatedAt` timestamps on database records.
* **Global Exception Handling:** Returns standardized error responses using Spring's `ProblemDetail` for 404 (Not Found) and 400 (Duplicate) scenarios.
* **Modern Concurrency:** Leverages Java Virtual Threads for optimized performance.
* **Object Mapping:** Utilizes MapStruct for seamless mapping between the generated API DTOs and internal domain records.

---

## Technology Stack

| Component | Technology / Version |
| --- | --- |
| **Language** | Java 25 |
| **Framework** | Spring Boot 4.1.0 |
| **Database** | H2 (In-Memory) |
| **Persistence** | Spring Data JDBC |
| **API Generation** | OpenAPI Generator Plugin (7.20.0) |
| **Mapping** | MapStruct (1.6.3) |

---

## API Specification

The REST API is defined in `docs/openapi/book-openapi.yml`. During the Maven build process, the plugin reads this file and generates the necessary Java interfaces (e.g., `BooksApi`) and data models inside the `com.vincenzoracca.boot4.api.generated` and `com.vincenzoracca.boot4.model.generated` packages.

### Available Endpoints

* `GET /books`: Retrieve a paginated list of all books.
* `GET /books/{isbn}`: Retrieve details of a specific book by its ISBN.
* `POST /books`: Create a new book (requires a `bookType` of PAPERBACK, EBOOK, or AUDIOBOOK).

---

## Getting Started

### Prerequisites

* JDK 25 installed on your machine.
* Maven (or you can use the included Maven Wrapper).

### Running the Application

1. Clone the repository and navigate to the project root directory.
2. Compile the project to trigger the OpenAPI generation and MapStruct processors:
```bash
./mvnw clean compile

```


3. Start the Spring Boot application:
```bash
./mvnw spring-boot:run

```



The server will start on `http://localhost:8080`.

### Testing the Endpoints

The database is pre-populated with 13 sample records via the `data.sql` file.

You can fetch a specific book using `curl`:

```bash
curl --location 'http://localhost:8080/books?page=0&size=10&sort=title,desc,author,desc'
```

Create new resource:
```bash
curl --location 'http://localhost:8080/books' \
--header 'Content-Type: application/json' \
--data '{
            "bookType": "AUDIOBOOK",
            "isbn": "0788901234567",
            "title": "The Agile Developer",
            "author": "Zazza",
            "durationMinutes": 280,
            "narrator": "Pino Insegno",
            "price": 28.50
        }'
```

You can also use Swagger UI to test the endpoints on https://petstore.swagger.io/?url=https://raw.githubusercontent.com/vincenzo-racca/boot4-apifirst/refs/heads/main/docs/openapi/book-openapi.yml.

### Accessing the Database Console

The H2 database console is enabled by default. You can access it via your browser:

* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:testdb`
* **Username:** `sa`
* **Password:** `password`