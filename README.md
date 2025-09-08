Notes API.

API for managing users and notes with a microservices architecture, built using Spring Boot, PostgreSQL as the database, and Docker for containerization.
Authentication and authorization are handled using Spring Security with JWT tokens.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

Technologies

Spring Boot – Java framework for REST microservices.

Spring Security – Authentication and authorization.

JWT (JSON Web Tokens) – Token-based authentication.

PostgreSQL – Relational database.

Docker – Containerization of services.

Maven – Dependency management.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

Features:

Users:

-Register a new user.

-Login with username and password and JWT bearer token on header.


Notes:

Create, update, and delete notes.


Microservices:

User Service separated from Notes Service.

Communication via REST (Not yet).


Security:

JWT authentication using Spring Security.

Secured endpoints require Authorization: Bearer <token> header.


Docker:

-docker-compose file makes easier to run all once. (docker compose up --build)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

Important Configurations:

-Add a secret key for JWT on application.properties and delete the enviroment path form docker-compose.
