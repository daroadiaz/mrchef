FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN mvn dependency-check:check || true

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/mrchef-0.0.1-SNAPSHOT.jar app.jar
RUN if [ -f /app/target/dependency-check-report.html ]; then \
	cp /app/target/dependency-check-report.html /reports/frontend-vulnerability-report.html; \
fi
EXPOSE 8081
# Añadir más opciones de debug
ENTRYPOINT ["java", "-Xmx512m", "-jar", "-Dspring.profiles.active=docker", "-Dlogging.level.root=INFO", "-Dlogging.level.com.example=DEBUG", "-Dlogging.level.org.springframework=DEBUG", "app.jar"]