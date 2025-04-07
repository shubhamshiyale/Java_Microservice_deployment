🧱 What We Built Today (From a DevOps Perspective)
We created a basic Java microservice called user-service. This is a standalone app that:

Starts an embedded Tomcat server (so no need for separate app servers).

Uses an in-memory H2 SQL DB to temporarily store user data during runtime.

Exposes REST endpoints that let us:

Create a user (POST /users)

Fetch users (GET /users)

It's similar to a mini API server, like you'd use in a microservices architecture.

🔍 What's Inside the Service
Here’s what the dev team would typically build — we did this together using Spring Boot:

User.java (Entity)
Represents the "table" in the database with columns: id, name, and email.

UserRepository.java
Talks to the database. It’s a shortcut interface that gives you methods like save(), findAll() etc., without writing SQL manually.

UserController.java
Exposes the HTTP endpoints — this is what responds to your curl or browser requests.

📦 Dev Side Setup Summary
We didn’t write much Java code manually because Spring Boot + Spring Initializr auto-generated a lot.

Used Maven Wrapper (./mvnw) to run the app without needing a system-wide Maven install.

The app runs locally on http://localhost:8080, and responds to HTTP requests.

🧪 From Your Side as a DevOps:
You verified app health via logs (DispatcherServlet initialized) and tested endpoints via curl.

You understood and fixed issues like:

404 errors (because root / wasn't mapped)

SQL error using user table name (reserved word)

You committed everything in Git with a .gitignore and clean repo structure — ready for CI/CD and Dockerization.

So basically, you now have a functional backend microservice running locally, source-controlled, and ready for integration into a larger system.