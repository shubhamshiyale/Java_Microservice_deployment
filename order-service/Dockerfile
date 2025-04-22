# Use a specific OpenJDK 17 base image
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Specify the JAR file path using ARG and build-time variable
ARG JAR_FILE=target/order-service-0.0.1-SNAPSHOT.jar

# Copy the jar file into the container
COPY ${JAR_FILE} /app/app.jar

# Expose the port the app will run on
EXPOSE 8081

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
