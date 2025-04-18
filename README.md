# MrChef Application

## Overview

MrChef is a web application that consists of two main components:
- Backend API (Java Spring Boot)
- Frontend UI (Java Spring Boot with Thymeleaf)

This application allows users to create, view, and manage cooking recipes.

## System Requirements

- Java 17
- MySQL 8.0
- Maven (optional if using the provided wrapper or Docker)

## Ports

- **Backend**: 8080
- **Frontend**: 8081
- **Database**: 3306 (Local MySQL installation)

## Independent Deployment

The application is designed to be deployable with or without Docker. Here are the instructions for both scenarios:

### Standard Deployment (without Docker)

1. **Database Setup**:
   - Ensure MySQL is running on port 3306
   - Create a database named `mrchef`
   - Use default credentials or update the application.properties files

2. **Backend Deployment**:
   ```bash
   cd MrChef-Backend
   ./mvnw clean package
   java -jar target/mrchefbackend-0.0.1-SNAPSHOT.jar
   ```

3. **Frontend Deployment**:
   ```bash
   cd MrChef-FrontEnd
   ./mvnw clean package
   java -jar target/mrchef-0.0.1-SNAPSHOT.jar
   ```

4. **Access the Application**:
   - Frontend UI: http://localhost:8081
   - Backend API: http://localhost:8080

## Vulnerability Scanning with Docker

We provide Docker configurations specifically for vulnerability scanning purposes. This allows you to analyze dependencies without affecting your regular deployment.

### Running the Vulnerability Scan

1. **Build and run the containers for scanning**:
   ```bash
   docker-compose build
   docker-compose up -d
   ```

2. **Extract the vulnerability reports**:
   ```bash
   docker cp mrchef-backend:/reports/backend-vulnerability-report.html ./backend-vulnerabilities.html
   docker cp mrchef-frontend:/reports/frontend-vulnerability-report.html ./frontend-vulnerabilities.html
   ```

3. **View the reports**:
   - Open the HTML files in any web browser
   - The reports provide detailed information about vulnerabilities in dependencies

4. **Stop the containers after scanning**:
   ```bash
   docker-compose down
   ```

### Configuration Details

The Docker setup utilizes OWASP Dependency-Check to scan for known vulnerabilities in project dependencies. This provides:

- Comprehensive vulnerability assessment
- CVE identification and severity ratings
- Recommendations for remediating security issues
- Detailed reports in HTML and JSON formats

## Important Security Considerations

- The application uses JWT for authentication
- Always ensure your MySQL installation is properly secured
- Update dependencies regularly to address security vulnerabilities
- Review the vulnerability reports and address critical issues

## Architecture

```
Client → Frontend (8081) → Backend (8080) → Database (3306)
```

## Development Notes

- Backend uses Spring Security and JWT for authentication
- Frontend uses Thymeleaf templates and Bootstrap for UI
- Both components are secured with Spring Security
- OWASP Dependency-Check is integrated for security vulnerability analysis

## Troubleshooting

If you encounter issues with database connectivity:
- Ensure MySQL is running and accessible
- Verify database credentials in application.properties
- Check that the mrchef database exists

For Docker-related issues:
- Ensure Docker and Docker Compose are properly installed
- Check if ports 8080 and 8081 are available
- Use `docker logs` to investigate container startup problems