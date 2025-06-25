# RestAPIs

## Overview
This project is a Spring Boot-based REST API for managing products. It demonstrates CRUD (Create, Read, Update, Delete) operations on a `Product` entity using Spring Data JPA with an in-memory H2 database. API documentation is provided via Springdoc OpenAPI and Swagger UI.

## Prerequisites
- Java 17
- Maven 3.6+
- (Optional) IDE like IntelliJ IDEA or VS Code

## API Documentation

- **Swagger UI**:  
  Browse interactive API docs at:  
  ```
  http://localhost:8090/swagger-ui.html
  ```

- **OpenAPI JSON**:  
  Download the spec from:  
  ```
  http://localhost:8090/v3/api-docs
  ```

## Project Structure

```
RestAPIs
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── nitstech
        │           └── restapis
        │               ├── RestApiApplication.java       # Main Spring Boot application
        │               ├── config
        │               │   └── OpenApiConfig.java         # OpenAPI / Swagger configuration
        │               ├── domain
        │               │   └── Product.java               # JPA entity model
        │               ├── repository
        │               │   └── ProductRepository.java    # Spring Data JPA repository
        │               ├── api
        │               │   └── Products.java             # REST controller for Product endpoints
        │               └── exception
        │                   └── ProductNotFoundException.java  # Custom exception for 404s
        └── resources
            └── application.yaml                         # Application configuration (H2, server port, Swagger)
```

### File Descriptions

- **pom.xml**  
  Defines project dependencies (Spring Boot, Spring Data JPA, H2, Lombok, Springdoc OpenAPI) and build settings.

- **application.yaml**  
  Configures the in-memory H2 database, server port (`8090`), and Springdoc paths.

- **RestApiApplication.java**  
  Bootstraps the Spring Boot application.

- **OpenApiConfig.java**  
  Sets up the OpenAPI `Info` (title, description, version) and groups API paths under `/api/v1/products`.

- **Product.java**  
  A JPA entity with fields: `id`, `name`, `description`, and `price`. Uses Jakarta validation and Lombok.

- **ProductRepository.java**  
  Extends `JpaRepository` to provide standard CRUD operations.

- **Products.java**  
  REST controller exposing endpoints:
  - `GET /api/v1/products`  
  - `POST /api/v1/products`  
  - `PUT /api/v1/products/{id}`  
  - `DELETE /api/v1/products/{id}`

- **ProductNotFoundException.java**  
  Thrown when a requested product ID does not exist (results in HTTP 404).

## Usage Examples

- **Create Product**  
  ```bash
  curl -X POST http://localhost:8090/api/v1/products \
    -H 'Content-Type: application/json' \
    -d '{"name":"New Product","description":"Description","price":100.00}'
  ```

- **List Products**  
  ```bash
  curl http://localhost:8090/api/v1/products
  ```

- **Update Product**  
  ```bash
  curl -X PUT http://localhost:8090/api/v1/products/1 \
    -H 'Content-Type: application/json' \
    -d '{"name":"Updated Name","description":"Updated Desc","price":120.00}'
  ```

- **Delete Product**  
  ```bash
  curl -X DELETE http://localhost:8090/api/v1/products/1
  ```

## License
This project is provided as-is under the MIT License.
