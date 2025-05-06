FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN mvn dependency-check:check || true

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/mrchefbackend-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build /app/target/dependency-check-report.html /reports/
EXPOSE 8080
# Añadir más opciones de debug
ENTRYPOINT ["java", "-Xmx512m", "-jar", "-Dspring.profiles.active=docker", "-Dlogging.level.root=INFO", "-Dlogging.level.com.example=DEBUG", "-Dlogging.level.org.springframework=DEBUG", "app.jar"]