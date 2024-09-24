# Cercli Assessment 

## Case 1

### Employee Data Management System

#### Database Setup

It uses in memory DB setup and require no additional configuration as `application.properties` file is handling
all the configurations

Username: sa
Password: Not required

#### Environment Setup
Requires Java 21 and Maven >3.8.8 to be in your PATH

#### Build & Run

- With every change run `mvn spring-javaformat:apply`
- Run `mvn clean install` to build the application
- Run `mvn spring-boot:run` to run the application

#### Accessing the application

- DB can be accessed via `localhost:9001/h2-console`
- Swagger can be accessed via `localhost:90001/swagger/ui`