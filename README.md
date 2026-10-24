# product-service
Spring boot app to crud product

## Prerequisites

- JDK 25 or later. The Gradle build uses a Java 25 toolchain.
- Docker with Docker Compose, to run the PostgreSQL database defined in `docker-compose.yml`.

## Database Setup

From the project root, start the PostgreSQL database:

```bash
docker compose up -d postgres-db
```

This starts PostgreSQL on port `5432` and creates the `productdb` database using the settings in `docker-compose.yml`. The application is configured to connect to that local database. Start the application after the database is ready.

Check the database container status with:

```bash
docker compose ps
```

Stop the database when finished with:

```bash
docker compose down
```

## Build, Test, and Run

Run these commands from the project root. The Gradle wrapper downloads and uses the Gradle version configured for this project.

Build the application:

```bash
./gradlew build
```

Run the tests:

```bash
./gradlew test
```

Start the application:

```bash
./gradlew bootRun
```

The service starts on `http://localhost:8080`. Ensure the database is running before starting the application.

## Basic Usage

The product API is available at `http://localhost:8080/products`.

Create a product by sending its name as JSON:

```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Example product"}'
```

List all products:

```bash
curl http://localhost:8080/products
```