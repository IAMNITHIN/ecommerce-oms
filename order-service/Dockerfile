# ==========================================
# Stage 1: Build Stage
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the entire project context (Requires context: . in docker-compose)
COPY . .

# Compile the specific module. 
# We use -pl to target the specific service and -am to build its dependencies.
RUN mvn clean package -pl order-service -am -DskipTests

# ==========================================
# Stage 2: Runtime Stage
# ==========================================
# Use the Alpine JRE image to drastically reduce container size
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a non-root user for security best practices
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the compiled JAR from Stage 1
COPY --from=build /app/order-service/target/*.jar app.jar

# Run the application
# We do not hardcode JAVA_OPTS here; we inject them via docker-compose.yml
ENTRYPOINT ["java", "-jar", "app.jar"]
