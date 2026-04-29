# BankApp – Java Banking Backend API

Professional backend banking application built with **Java + Spring Boot**.  
Created as portfolio project for Java Internship / Junior Backend roles.

---

# Features

## User Management
- Register new users
- Login with JWT authentication
- BCrypt password hashing

## Bank Accounts
- Create bank account for user
- Unique account numbers
- Check balances

## Money Operations
- Deposit money
- Withdraw money
- Transfer money between accounts

## Transactions
- Transaction history
- Transfer logs
- Deposit / Withdraw records

## Security
- Spring Security
- JWT Authorization
- Protected endpoints
- Swagger JWT support

## Code Quality
- DTO architecture
- ResponseEntity
- Pagination
- Global Exception Handling
- Lombok
- Builder Pattern

## Testing
- Unit Tests (JUnit + Mockito)
- Integration Tests (MockMvc)

## DevOps
- Docker
- Docker Compose
- GitHub Actions CI/CD

---

# Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- Swagger / OpenAPI
- Docker
- GitHub Actions

---

# Run Locally

## Standard

```bash
mvn spring-boot:run
```

# Docker
```bash
docker compose up --build
```

# Swagger UI
After starting application:
```bash
http://localhost:8080/swagger-ui/index.html
```

# Authentication Flow
## Register
```bash
POST /users
```
## Login
```bash
POST /users/login
```
Returns JWT token

## Authorize
Use token in Swagger or header:
```bash
Authorization: Bearer YOUR_TOKEN
```

# Example Endpoints
## Accounts

```bash
GET /accounts?page=0&size=5
POST /accounts
POST /accounts/{id}/deposit
POST /accounts/{id}/withdraw
POST /accounts/transfer
```

## Transactions
```bash
GET /transactions?page=0&size=10
```

# CI/CD
## Every push runs:

- Build
- Tests
- Verifications
--

via GitHub Actions.

# Project Purpose
## This project was created to demonstrate practical backend skills.

- REST API design
- Security
- Database integration
- Testing
- Clean architecture
- DevOps basics
---

# Author:
## Adam Pietkiewicz




