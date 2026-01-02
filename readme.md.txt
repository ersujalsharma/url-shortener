# URL Shortener

A scalable URL Shortener built using **Java, Spring Boot, MySQL**, and **Redis**.

## Features
- Short URL generation
- Fast redirection
- MySQL persistence with indexing
- Swagger/OpenAPI support
- Designed for horizontal scalability

## Tech Stack
- Java 17
- Spring Boot
- MySQL
- Redis (planned)
- Swagger / OpenAPI

## Architecture (High Level)
Client → Load Balancer → Application Servers → Redis → MySQL

## Status
Core functionality completed.  
Currently working on scaling (Redis, rate limiting, deployment).
