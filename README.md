# MrChef Application

## Overview

MrChef es una aplicación web que consta de dos componentes principales:

- Backend API (Java Spring Boot)
- Frontend UI (Java Spring Boot con Thymeleaf)

Esta aplicación permite a los usuarios crear, ver y gestionar recetas de cocina.

## Requisitos del Sistema

- Java 17
- MySQL 8.0
- Maven (opcional si se usa el wrapper proporcionado o Docker)

## Puertos

- Backend: 8080
- Frontend: 8081
- Base de datos: 3306 (Instalación local de MySQL)
- SonarQube: 9000 (Para análisis de calidad de código)

## Despliegue Independiente

La aplicación está diseñada para ser desplegada con o sin Docker. Aquí están las instrucciones para ambos escenarios:

### Despliegue Estándar (sin Docker)

#### Configuración de la Base de Datos:

- Asegúrese de que MySQL está ejecutándose en el puerto 3306
- Cree una base de datos llamada `mrchef`
- Use credenciales predeterminadas o actualice los archivos application.properties

#### Despliegue del Backend:
```bash
cd MrChef-Backend
./mvnw clean package
java -jar target/mrchefbackend-0.0.1-SNAPSHOT.jar
```

#### Despliegue del Frontend:
```bash
cd MrChef-FrontEnd
./mvnw clean package
java -jar target/mrchef-0.0.1-SNAPSHOT.jar
```

#### Acceso a la Aplicación:

- Frontend UI: http://localhost:8081
- Backend API: http://localhost:8080

## Escaneo de Vulnerabilidades con Docker

Proporcionamos configuraciones Docker específicamente para propósitos de escaneo de vulnerabilidades. Esto le permite analizar dependencias sin afectar su despliegue regular.

### Ejecutar el Escaneo de Vulnerabilidades

#### Construir y ejecutar los contenedores para escaneo:
```bash
docker-compose build
docker-compose up -d
```

#### Extraer los informes de vulnerabilidades:
```bash
docker cp mrchef-backend:/reports/backend-vulnerability-report.html ./backend-vulnerabilities.html
docker cp mrchef-frontend:/reports/frontend-vulnerability-report.html ./frontend-vulnerabilities.html
```

#### Ver los informes:

- Abra los archivos HTML en cualquier navegador web
- Los informes proporcionan información detallada sobre vulnerabilidades en dependencias

#### Detener los contenedores después del escaneo:
```bash
docker-compose down
```

## Detalles de Configuración

La configuración Docker utiliza OWASP Dependency-Check para escanear vulnerabilidades conocidas en las dependencias del proyecto. Esto proporciona:

- Evaluación integral de vulnerabilidades
- Identificación de CVE y calificaciones de severidad
- Recomendaciones para remediar problemas de seguridad
- Informes detallados en formatos HTML y JSON

## Análisis de Calidad de Código con SonarQube

SonarQube está integrado para analizar la calidad del código y las vulnerabilidades de seguridad en el código fuente.

### Iniciar el contenedor SonarQube:
```bash
docker-compose up -d sonarqube sonarqube-db
```

### Ejecutar análisis SonarQube:
```bash
# Para el backend
docker run --rm --network mrchef_mrchef-network -v "C:/Users/esnup/Desktop/seguri/m1/mrchef/MrChef-Backend:/usr/src/app" -w /usr/src/app maven:3.8-openjdk-17 mvn sonar:sonar "-Dsonar.projectKey=mrchef-backend" "-Dsonar.projectName=MrChef Backend" "-Dsonar.host.url=http://sonarqube:9000" "-Dsonar.login=your_sonar_token"

# Para el frontend
docker run --rm --network mrchef_mrchef-network -v "C:/Users/esnup/Desktop/seguri/m1/mrchef/MrChef-FrontEnd:/usr/src/app" -w /usr/src/app maven:3.8-openjdk-17 mvn sonar:sonar "-Dsonar.projectKey=mrchef-frontend" "-Dsonar.projectName=MrChef Frontend" "-Dsonar.host.url=http://sonarqube:9000" "-Dsonar.login=your_sonar_token"
```

### Acceder a los informes de SonarQube:

- Navegue a http://localhost:9000
- Vea informes detallados sobre calidad de código, vulnerabilidades de seguridad y problemas de mantenibilidad
- Examine ubicaciones específicas de código que requieren atención

## Consideraciones Importantes de Seguridad

- La aplicación utiliza JWT para autenticación
- Asegúrese siempre de que su instalación de MySQL esté correctamente protegida
- Actualice las dependencias regularmente para abordar vulnerabilidades de seguridad
- Revise los informes de vulnerabilidad y aborde los problemas críticos
- Aborde todos los problemas críticos y de alta gravedad encontrados por SonarQube

## Arquitectura

Cliente → Frontend (8081) → Backend (8080) → Base de datos (3306)

## Notas de Desarrollo

- El backend utiliza Spring Security y JWT para autenticación
- El frontend utiliza plantillas Thymeleaf y Bootstrap para la interfaz de usuario
- Ambos componentes están protegidos con Spring Security
- OWASP Dependency-Check está integrado para análisis de vulnerabilidades de seguridad
- SonarQube se utiliza para análisis continuo de calidad de código y seguridad

## Solución de Problemas

### Si encuentra problemas con la conectividad de la base de datos:

- Asegúrese de que MySQL está ejecutándose y es accesible
- Verifique las credenciales de la base de datos en application.properties
- Compruebe que la base de datos mrchef existe

### Para problemas relacionados con Docker:

- Asegúrese de que Docker y Docker Compose estén correctamente instalados
- Compruebe si los puertos 8080, 8081 y 9000 están disponibles
- Utilice docker logs para investigar problemas de inicio de contenedores

### Para problemas de SonarQube:

- Asegúrese de que el contenedor SonarQube esté ejecutándose (docker ps)
- Verifique la conectividad de red entre contenedores
- Compruebe que el token utilizado es válido y tiene los permisos apropiados