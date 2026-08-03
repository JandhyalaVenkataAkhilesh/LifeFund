# ===============================
# Stage 1 : Build Application
# ===============================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy Maven files
COPY pom.xml .

COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# ===============================
# Stage 2 : Run Application
# ===============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy generated JAR
COPY --from=builder /app/target/*.jar app.jar

# Set permissions
RUN chown -R appuser:appgroup /app

# Run as non-root user
USER appuser

# Expose application port
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8080} -jar app.jar"]