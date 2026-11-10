# Mini Spring

A lightweight Java framework built from scratch to understand the internal mechanisms behind Spring Boot and Spring MVC.

This project is an educational implementation of some core concepts used by Spring, using plain Java and reflection.

## Features

- HTTP server using Java Socket API
- HTTP request parsing and response handling
- Request dispatching
- Handler Mapping and Handler Adapters
- Custom annotations (`@RestController`, `@GetMapping`, `@Service`)
- Runtime component scanning
- Dependency Injection through constructor injection
- IoC Container and singleton bean management
- Application bootstrap using `@MiniBootApplication`

## Architecture

The framework is organized into several components:

- **Server:** Accepts incoming HTTP connections.
- **HTTP:** Parses requests and builds responses.
- **Dispatcher:** Routes incoming requests to the appropriate handlers.
- **Handler Mapping:** Maps HTTP methods and paths to handlers.
- **Handler Adapters:** Execute different types of handlers.
- **Component Scanner:** Discovers annotated classes at runtime.
- **Application Context:** Manages bean definitions, instances, and dependencies.
- **Component Registrar:** Registers discovered components.

## Technologies

- Java
- Java Reflection API
- Java Socket API
- Java Annotations
- Git

## Getting Started

Clone the repository:

```bash
git clone YOUR_REPOSITORY_URL
```

Open the project in IntelliJ IDEA and run `MyApplication.java`.

The server starts on port `8088`.

Example endpoints:

```http
GET http://localhost:8088/users
GET http://localhost:8088/users/profile
GET http://localhost:8088/products
```

## Project Goals

The goal is to understand how frameworks like Spring Boot work internally by implementing simplified versions of their core mechanisms without relying on Spring libraries.

This is an ongoing learning project, and new features will be added progressively.

## Disclaimer

This project is for educational purposes and is not intended for production use.