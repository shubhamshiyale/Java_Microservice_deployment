
## 🔍 Microservice Details – UserService

**UserService** is the first microservice in our project. It represents a small part of a larger system that might include other services like `ProductService`, `OrderService`, etc.

### 🔧 What It Does:
- Manages **user information** such as name and email.
- Provides a **RESTful API** for creating and retrieving user data.
- Uses an **H2 in-memory database** (temporary database useful for development/testing).
- Runs as a **Spring Boot** standalone web server.

### 📁 Main Components:
| File | Purpose |
|------|---------|
| `User.java` | Represents the data model (a "User" with `id`, `name`, and `email`) |
| `UserRepository.java` | Handles communication with the database (Spring Data JPA) |
| `UserController.java` | Exposes REST APIs to interact with the system (like `/users`) |

### 🌐 API Endpoints:
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/users` | Create a new user |
| GET    | `/users` | List all users |

This service will eventually be connected to others, containerized using Docker, and deployed to GKE with monitoring, logging, and security layers.

Would you like me to add this directly into the README in the canvas?