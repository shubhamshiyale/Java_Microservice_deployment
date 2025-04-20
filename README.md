Here’s the updated README with today's steps added at the bottom:

---

# Java Microservices Deployment - Local to GKE (DevOps Practice)

## 📅 Day 1 - April 7, 2025

This is a hands-on learning project where we simulate a microservice-based architecture locally and gradually migrate it to GKE using console and eventually Terraform. The end goal is to understand every step of the DevOps lifecycle using a simplified setup that mimics a real-world scenario with APIGEE, Cloud Armor, Load Balancer, and ASM (Anthos Service Mesh).

We start by creating a basic Java Spring Boot application that represents **User Management Service** — one of the microservices in our setup. It connects to an H2 in-memory SQL database locally and exposes REST endpoints to manage users.

---

## 🧠 What We Did Today:

### ✅ Initial Setup:
- Installed all necessary software using Chocolatey (Java, Maven, Git, Spring Boot CLI).

### ✅ Local Setup

- OS: Windows 11
- Installed tools:
  - [x] Java 17 (`choco install openjdk`)
  - [x] Maven (`choco install maven`)
  - [x] Git (`choco install git`)
  - [x] VS Code
  - [x] Postman or `curl` (for API testing)

- Set up VS Code workspace and created a new GitHub repo: [Java_Microservice_deployment](https://github.com/shubhamshiyale/Java_Microservice_deployment.git).
- Cloned the repo locally.

### ✅ Project Generation:
- Used [Spring Initializr](https://start.spring.io/) with the following settings:
  - **Spring Boot Version:** 3.3.10
  - **Project:** Maven
  - **Language:** Java
  - **Dependencies:** Spring Web, Spring Data JPA, H2 Database, Lombok
  - **Group:** com.shubham
  - **Artifact:** userservice
  - **Name:** userservice
  - **Package Name:** com.shubham.userservice
  - **Packaging:** Jar
  - **Java Version:** 17

### ✅ Local Setup:
```bash
# Clone the repo
$ git clone https://github.com/shubhamshiyale/Java_Microservice_deployment.git
$ cd Java_Microservice_deployment

# Unzip and place the Spring Boot project inside this folder if not generated via curl.

# Run the application
$ ./mvnw spring-boot:run
```

### ✅ API Testing:
```bash
# POST - Create user
$ curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Shubham","email":"shubham@example.com"}'

# GET - List users
$ curl http://localhost:8080/users
```

You can also visit `http://localhost:8080/users` in the browser and see the JSON list.

---

## 📦 Service Details (UserService)

This microservice is responsible for handling user data. It performs basic CRUD operations and stores data in an in-memory database (H2). Here's what it includes:

- A **User** entity with `id`, `name`, and `email`
- A **UserRepository** that extends `JpaRepository`
- A **UserController** with two endpoints:
  - `GET /users`: Returns all users
  - `POST /users`: Creates a new user

This is the first of the two microservices we will build. The second microservice might handle tasks like Order Processing or Inventory, and we'll connect them later.

---

## 🐞 Troubleshooting:
- `Whitelabel Error Page` - This is shown for unmapped root endpoints like `/`. Define a `/` or ignore.
- H2 SQL error with `user` - Avoid using reserved keywords like `user`. Use `app_user` or backticks if needed.

---

## 📌 Next Steps
- Create the second microservice (e.g., ProductService or OrderService).
- Connect both using REST calls.
- Add Dockerfile and docker-compose for local orchestration.
- Migrate to GKE via GCP Console.
- Final migration and deployment via Terraform.

---

## 📅 Day 2 - April 8, 2025

### ✅ OrderService Setup:

- Created and tested the **OrderService** microservice.
- This service handles order data and accepts order details via a `POST` API at `http://localhost:8081/orders`.

  Example for placing an order:
  
  ```bash
  # POST - Place order
  $ curl -X POST http://localhost:8081/orders \
    -H "Content-Type: application/json" \
    -d '{"id": "101", "userId": "u123", "product": "Laptop", "quantity": 1}'
  
  # Response: "Order placed successfully!"
  ```

  The `OrderService` simulates placing an order and returns a success message.

### ✅ OrderService API Testing:

```bash
# POST - Create order
$ curl -X POST http://localhost:8081/orders \
  -H "Content-Type: application/json" \
  -d '{"id": "101", "userId": "u123", "product": "Laptop", "quantity": 1}'

# GET - List orders
$ curl http://localhost:8081/orders
```

### ✅ Service Integration:

- Integrated **UserService** and **OrderService**.
- Both services are working independently, with **UserService** on port `8080` and **OrderService** on port `8081`.

---

Feel free to copy the entire updated README now! Let me know if you need any further changes or adjustments.