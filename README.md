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

Perfect! So now both **User Service** and **Order Service** are containerized and tested locally — that’s a huge milestone! 🥳🔥

Let’s update your README with today’s accomplishments (Day 2). Here's the updated section to add:

---

## 📅 Day 2 - April 21, 2025

Today we **containerized** both the `UserService` and `OrderService` and tested them locally using Docker.

### ✅ What We Did:

#### 🔧 Build & Package the Services
- Ran the Maven build:
  ```bash
  ./mvnw clean install
  ```

#### 🐳 Created Dockerfiles
- Added `Dockerfile` to both services with the following structure:
  ```dockerfile

# Use a specific OpenJDK 17 base image
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Specify the JAR file path using ARG and build-time variable
ARG JAR_FILE=target/user-service-0.0.1-SNAPSHOT.jar

# Copy the jar file into the container
COPY ${JAR_FILE} /app/app.jar

# Expose the port the app will run on
EXPOSE 8080

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

  ```

#### 📦 Built Docker Images
- Built images locally:
  ```bash
  docker build -t userservice:v1 .
  docker build -t orderservice:v1 .
  ```

#### 🚀 Ran Containers Locally
- Ran the services in **detached mode**:
  ```bash
  docker run -d -p 8080:8080 userservice:v1
  docker run -d -p 8081:8080 orderservice:v1
  ```

#### 📟 Tested with `curl`:
```bash
# UserService
curl http://localhost:8080/users/ #check service is up or not
for add user do 
curl -X POST http://localhost:8080/users \
    -H "Content-Type: application/json" \
    -d '{"name": "John Doe", "email": "john.doe@example.com"}'


# OrderService
curl http://localhost:8081/orders/ #check service is up or not

for place order do
curl -X POST http://localhost:8081/orders \
    -H "Content-Type: application/json" \
    -d '{"orderId": 123, "product": "Laptop", "quantity": 1}'
```

## 📅 Day 3 - April 24, 2025

push the images to the artifactory 
1. create a seperate GCP project # in my case i used java-application-host-on-gke
2. search for the artifacts registry and enable to api
3. create a repository and give it a name
4. copy the path

Tag your existing images
docker tag userservice:v1 us-east1-docker.pkg.dev/java-application-host-on-gke/jdk-app-images/userservice:v1
docker tag orderservice:v1 us-east1-docker.pkg.dev/java-application-host-on-gke/jdk-app-images/orderservice:v1

docker push orderservice:v1 us-east1-docker.pkg.dev/java-application-host-on-gke/jdk-app-images/orderservice:v1
docker push userservice:v1 us-east1-docker.pkg.dev/java-application-host-on-gke/jdk-app-images/userservice:v1

validate if the images are pushed