# User Management & Role Assignment System

## Overview
This project is a Spring Boot application implementing a User Management System following Robert C. Martin's Clean Architecture principles. It provides functionality to create users and roles, assign and remove roles from users, and retrieve user details with their assigned roles, including pagination for user lists. The application uses an H2 in-memory database, DTOs for request/response, validation, error handling, and unit tests for the service layer.

## Features
- **Create User**: Add a new user with a name and email.
- **Create Role**: Add a new role with a role name.
- **Assign Role to User**: Assign an existing role to an existing user.
- **Remove Role from User**: Remove an assigned role from a user.
- **Fetch User Details**: Retrieve user information along with their assigned roles.
- **List Users with Pagination**: Retrieve a paginated list of users.
- **DTOs**: Use Data Transfer Objects for request and response to avoid exposing domain entities.
- **Validation**: Ensures proper email format and non-blank names/role names.
- **Error Handling**: Returns meaningful HTTP status codes and messages.
- **H2 Database**: In-memory database with console access at `/h2-console`.
- **Unit Tests**: Tests for the service layer using Mockito.

## Prerequisites
- Java 17 or higher
- Maven 3.8.x or higher
- Git (optional, for cloning the repository)

## Setup Instructions
1. **Clone the Repository** (if applicable):
   ```bash
   git clone <repository-url>
   cd usermanagement
   ```

2. **Build the Project**:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

4. **Access H2 Console**:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (leave blank)

## API Endpoints
| Method | Endpoint                        | Request Body                                   | Response                             |
|--------|---------------------------------|------------------------------------------------|--------------------------------------|
| POST   | `/users`                        | `{ "name": "John Doe", "email": "john@example.com" }` | Created User ID                     |
| POST   | `/roles`                        | `{ "roleName": "ADMIN" }`                      | Created Role ID                     |
| POST   | `/users/{userId}/assign-role/{roleId}` | (Empty body)                                   | Success Message                     |
| DELETE | `/users/{userId}/remove-role/{roleId}` | (Empty body)                                   | Success Message                     |
| GET    | `/users/{id}`                   | -                                              | User Details with Assigned Roles    |
| GET    | `/users?page=0&size=10`         | -                                              | Paginated List of Users             |

## Testing
**Run Unit Tests**:
```bash
mvn test
```
This executes unit tests for the service layer, mocking repository interfaces using Mockito.

## Project Structure
The project adheres to Clean Architecture with the following layers:
- **Domain**: Contains pure business entities (`User`, `Role`) without Spring/JPA annotations.
- **Application**: Contains use cases (`UserService`, `RoleService`) and repository interfaces (`UserRepository`, `RoleRepository`).
- **Infrastructure**: Contains adapters for persistence (`UserJpaRepository`, `RoleJpaRepository`), controllers (`UserController`, `RoleController`), and DTOs.
- **Config**: Contains Spring configuration (`BeanConfig`).

```
src/main/java/com/example/usermanagement/
├── domain/
│   ├── User.java
│   └── Role.java
├── application/
│   ├── UserService.java
│   ├── RoleService.java
│   └── interfaces/
│       ├── UserRepository.java
│       └── RoleRepository.java
├── infrastructure/
│   ├── controller/
│   │   ├── dto/
│   │   │   ├── UserDto.java
│   │   │   ├── RoleDto.java
│   │   │   ├── CreateUserRequest.java
│   │   │   └── CreateRoleRequest.java
│   │   ├── UserController.java
│   │   └── RoleController.java
│   └── persistence/
│       ├── UserJpaEntity.java
│       ├── RoleJpaEntity.java
│       ├── UserJpaRepository.java
│       └── RoleJpaRepository.java
└── config/
    └── BeanConfig.java
```

## Dependencies
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Hibernate Validator
- JUnit 5 (for testing)
- Mockito (for mocking in tests)

## Notes
- The domain layer is independent of frameworks, ensuring portability.
- Repository interfaces in the application layer act as ports, implemented by JPA adapters in the infrastructure layer.
- Controllers handle REST API requests and map them to service layer calls using DTOs.
- DTOs are placed in the `infrastructure/controller/dto` package, as they are part of the controller layer's responsibility.
- Validation is implemented using `@Valid`, `@NotBlank`, and `@Email` annotations.
- Error handling uses `@ControllerAdvice` for consistent HTTP status codes and messages.
- UUIDs are generated using `UUID.randomUUID()` for entity IDs.
- Pagination is implemented for the user list API using Spring Data's `Pageable`.
- DTOs are used to encapsulate request and response data, preventing direct exposure of domain entities.

## Bonus Features (Not Implemented)
The following optional features were not included but can be added:
- Audit fields (createdDate, updatedDate)
- Swagger/OpenAPI documentation
- Integration tests

## Troubleshooting
- **H2 Console Not Accessible**: Ensure the URL is correct and the application is running.
- **Validation Errors**: Check request bodies for valid email formats and non-blank fields.
- **404 Errors**: Verify that user/role IDs exist before assigning/removing roles or fetching details.
- **Pagination Issues**: Ensure `page` and `size` parameters are valid non-negative integers.

For further assistance, refer to the Spring Boot logs or contact the project maintainer.