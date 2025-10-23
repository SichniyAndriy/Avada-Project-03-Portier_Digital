AI Assistant: README

Project-03-Portier_Digital

Overview
Portier Digital is a Spring Boot (Java 17) web application for a client-facing digital services portal with an admin area and public pages. It follows a layered architecture: Spring MVC (controllers/views), service layer, Spring Data JPA (Hibernate) with PostgreSQL, WebSocket for real-time events, and server-side rendering via Thymeleaf. The project includes ERD/class diagrams, modular tests, structured logging, Dockerization, and npm-managed frontend assets.

Features
- Domain CRUD with transactions and validation.
- Admin dashboard and public pages (SSR via Thymeleaf).
- Real-time notifications/status updates via WebSocket.
- DTO mapping with MapStruct to decouple web and persistence layers.
- Structured logging with Logback.
- Unit and integration testing (JUnit 5, Mockito).
- Dockerized runtime; Docker Compose stack with PostgreSQL.
- Frontend assets managed via npm (jQuery, axios) with ESLint.

Architecture
- Backend: Java 17, Spring Boot 3.x, Spring MVC, Spring Data JPA (Hibernate), Jakarta Persistence/Validation, WebSocket, embedded Tomcat.
- Database: PostgreSQL, HikariCP connection pooling.
- Views: Thymeleaf templates (SSR).
- Utilities: Lombok (boilerplate reduction), MapStruct (DTO mapping).
- Build/Dev: Maven, Spring Boot DevTools, Qodana (static analysis).
- Logging/Telemetry: Logback; Micrometer libraries present for observation.
- Testing: JUnit 5, Mockito.

Source layout
- s_lab.sichniy_andriy.portier_digital
  - config: Spring configuration (e.g., WebSocket setup, app configs).
  - controller: MVC/REST controllers.
  - service: business logic and transactional workflows.
  - repository: Spring Data JPA repositories.
  - model: JPA entities and related domain models.
  - websocket: WebSocket handlers for real-time features.
  - aspect: AOP aspects (cross-cutting concerns, if used).
  - InitUtils, ServiceUtils: initialization and helper utilities.
  - PortierDigitalApplication, ServletInitializer: application entry points/bootstrap.
- resources
  - templates: Thymeleaf pages.
  - static: static assets (CSS/JS/images).
  - package.json / package-lock.json / jsconfig.json: frontend dependencies and JS config.
  - node_modules: installed frontend packages.
  - application.yml: application configuration and profiles.
  - logback.xml: logging configuration.

Prerequisites
- Java 17 (JDK)
- Maven 3.9+
- Node.js LTS + npm
- Docker and Docker Compose (optional but recommended)
- PostgreSQL (optional for local without Docker)

Local setup
1) Clone and install backend dependencies
- mvn clean install
2) Install frontend dependencies
- cd src/main/resources
- npm ci
3) Configure database
- Option A: Local PostgreSQL — create database and user; set credentials via environment variables or application.yml.
- Option B: Docker Compose — bring up the stack (database and app) with appropriate environment variables for DB name, user, and password.
4) Run in development
- mvn spring-boot:run
- App runs on embedded Tomcat (default 8080 unless overridden by profile).
5) Build artifact
- mvn package
- Produces runnable artifact suitable for containerization or direct run.

Configuration
- application.yml: database URL/credentials, HikariCP pool settings, profile-specific overrides, server port, CORS/origins for WebSocket, etc.
- Environment variables can override YAML (e.g., SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD, SPRING_PROFILES_ACTIVE).
- logback.xml: logging levels, appenders, patterns, rolling policies.

Database
- JPA/Hibernate for ORM; schema handling is profile-dependent.
- Transaction management via @Transactional at the service layer.
- Repositories use Spring Data for CRUD and query derivation.

Web and real-time
- Server-side views with Thymeleaf, integrating static assets from resources.
- WebSocket endpoints provide push notifications and real-time status updates; allowed origins configured at the WebSocket config level.

Frontend
- Assets managed by npm under src/main/resources; linted with ESLint.
- jQuery and axios are used for client-side interactions with backend endpoints and real-time UI updates.

Testing
- Unit tests with JUnit 5 and Mockito (services, controllers with mocks).
- Integration tests using Spring Boot test support (context load, repositories, web layer).
- Run tests with mvn test.

Quality and CI
- Qodana configured for static code analysis.
- Maven Surefire for test execution in pipelines.
- GitHub configuration present for CI/CD workflows.

Docker
- Containerized application image suitable for deployment.
- Docker Compose stack includes PostgreSQL service and the application service on a bridged network; ports and volumes configurable.
- Typical usage:
  - Build application JAR via Maven.
  - Build container image.
  - Start services with Docker Compose; confirm DB readiness and app startup.

Environments
- dev: verbose logging, hot reload via DevTools, relaxed settings.
- prod: tuned Hibernate and HikariCP, production logging policies, hardened origins and CORS as needed.

Security notes
- Use TLS termination (e.g., reverse proxy) in production.
- Provide secrets (DB passwords, tokens) via environment variables or a secrets manager.
- Restrict WebSocket allowed origins appropriately for production.

Troubleshooting
- Database connectivity: verify connection string, credentials, container health, and exposed ports.
- Schema/DDL: confirm Hibernate DDL strategy or apply migrations if managed externally.
- WebSocket issues: check endpoint path and allowed origins; ensure reverse proxy supports WebSocket upgrades.
- Static assets: re-run npm ci in src/main/resources; verify asset paths in templates.
- Port conflicts: change server.port via profile or environment variable.

Run cookbook
- Quick dev run:
  - npm ci (in src/main/resources)
  - mvn spring-boot:run
  - Open http://localhost:8080 (or configured port)
- Quick container run:
  - Build app with Maven
  - Build image, then use Docker Compose to start DB and app
  - Access app at the mapped host port

Roadmap ideas
- Database migrations (e.g., Flyway/Liquibase).
- Role-based access control and authentication.
- API documentation (e.g., SpringDoc OpenAPI).
- Caching and performance tuning.
- Observability enhancements (Micrometer metrics, tracing).

License
- Specify project license if needed.

Contact
- Maintainer: add name and contact for support or contributions.
