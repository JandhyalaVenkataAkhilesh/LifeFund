# ===============================
# Stage 1 : Build Application
# ===============================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ===============================
# Stage 2 : Run Application
# ===============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy generated jar
COPY --from=builder /app/target/*.jar app.jar

# Give permissions
RUN chown -R appuser:appgroup /app

# Run as non-root user
USER appuser

# Expose application port
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8080} -jar app.jar"]