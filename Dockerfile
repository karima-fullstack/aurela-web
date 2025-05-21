# Use lightweight JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the entire project into the container
COPY . ./

# Ensure Maven wrapper is executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw -B -DskipTests clean package

# Run the application
CMD ["sh", "-c", "java -jar target/*.jar"]
