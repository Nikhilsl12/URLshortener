# URL Shortener

A simple URL shortener service built with Java, Spring Boot, and H2 in-memory database.

## Features

- Shorten long URLs to short codes
- Redirect short codes to original URLs
- Track access count for each short URL
- RESTful API endpoints

## Tech Stack

- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- H2 Database (in-memory)
- Maven

## Getting Started

### Prerequisites

- Java 21+
- Maven

### Running the Application

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/urlshortener.git
    cd urlshortener
    ```

2. Build and run:
    ```sh
    mvn spring-boot:run
    ```

3. The application will start on [http://localhost:8080](http://localhost:8080).

### API Endpoints

- **Shorten URL**
    - `POST /api/shorten`
    - Request body: `{ "url": "https://example.com" }`
    - Response: `{ "originalUrl": "...", "shortCode": "...", "shortUrl": "/api/..." }`

- **Redirect to Original URL**
    - `GET /api/{shortCode}`
    - Redirects to the original URL if found.

### H2 Console

- Access the H2 database console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:urlshortenerdb`
- Username: `1234`
- Password: *(leave blank)*

## License

This project is licensed under the MIT License.