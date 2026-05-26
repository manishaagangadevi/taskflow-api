# TaskFlow API

A production-grade Task Management REST API built with Spring Boot, JWT Authentication, and Role-Based Access Control.

## Tech Stack
- Java 17 + Spring Boot 4
- Spring Security + JWT
- Spring Data JPA + PostgreSQL
- Docker

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login and get JWT token |

### Tasks (requires Bearer token)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/tasks | Create a task |
| GET | /api/tasks | Get all tasks (paginated) |
| GET | /api/tasks?status=TODO | Filter by status |
| GET | /api/tasks?priority=HIGH | Filter by priority |
| GET | /api/tasks/{id} | Get task by ID |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Delete task |

## Run Locally

```bash
# Start PostgreSQL
docker run --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=taskflowdb -p 5432:5432 -d postgres

# Run the app
./mvnw spring-boot:run
```

## Run with Docker

```bash
docker build -t taskflow-api .
docker run -p 8080:8080 taskflow-api
```

## Sample Request

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Manisha","email":"manisha@test.com","password":"123456"}'

# Create Task (use token from register response)
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"My Task","priority":"HIGH","status":"TODO"}'
```