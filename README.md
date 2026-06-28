# Boot4 API-First Book Management

This project is a modern Spring Boot application that demonstrates the **API-First** development approach. It manages a library of books with polymorphic data structures (E-Books, Audiobooks, and Paperbacks) using an OpenAPI specification as the single source of truth.

## Core Features

* **API-First Approach:** REST API interfaces and DTOs are generated at compile time using the `openapi-generator-maven-plugin`.
* **Polymorphism (No Discriminator):** Unlike the `main` branch, this branch does **not** use the OpenAPI `discriminator`. Subtype-specific fields are modeled on a single flat schema (`BookBase`) and their coherence with `bookType` is validated server-side (see [Modeling Approach](#modeling-approach-no-discriminator)).
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

## Modeling Approach: No Discriminator

> This branch (`feature/nodiscriminator`) intentionally diverges from `main` in how
> polymorphic books are modeled in the OpenAPI contract.

### How `main` works (discriminator-based)

On the `main` branch the spec relies on the OpenAPI `discriminator`:

* A base `BookDto` declares `discriminator: { propertyName: bookType }` with a
  `mapping` to the subtype schemas.
* `PaperbackDto`, `EBookDto`, and `AudioBookDto` extend the base via `allOf`, each
  carrying only its own fields.
* `CreateBookRequest` / `BookResponse` are `oneOf` those three subtype schemas.
* The generator produces three polymorphic DTO classes, and Jackson performs
  polymorphic (de)serialization driven by the `bookType` value. `BookMapper` then
  dispatches by runtime subtype (`instanceof` / `switch`).

### How this branch works (flat model, no discriminator)

This branch removes the `discriminator` entirely and uses a **single flat schema**:

* A single `BookBase` schema declares **all** fields. Every subtype-specific field
  (`pages`, `weightGrams`, `format`, `fileSizeMb`, `narrator`, `durationMinutes`)
  is **optional**, and its relevance depends on `bookType`.
* `CreateBookRequest` and `BookResponse` are simply `allOf: [BookBase]` (kept as
  distinct types so request/response contracts can evolve independently).
* The generator produces one flat DTO; there is **no Jackson polymorphism** and no
  runtime subtype dispatch. `BookMapper` is reduced to a single
  `toBook(CreateBookRequest)` / `toBookResponse(Book)` pair.
* Because the schema no longer enforces which fields belong to which type, the
  coherence between `bookType` and the subtype-specific fields is validated
  **server-side**: `BookService.validateSubtypeFields(...)` rejects an incoherent
  payload (e.g. an `AUDIOBOOK` carrying `pages`) by throwing
  `InvalidBookRequestException`, which is mapped to an **HTTP 400** response using
  Spring's `ProblemDetail`.

### Trade-offs

* **Pros:** simpler generated model and mapper, no Jackson polymorphic
  configuration, and request/response contracts that are easy to evolve.
* **Cons:** field/type coherence is no longer guaranteed by the contract itself and
  must be enforced (and tested) in application code.

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