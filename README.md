# 🚗 Vehicle Management System (Spring Boot)

## 📌 Overview

A backend application built using Java Spring Boot to manage vehicles, users, and role-based access. This project demonstrates REST API development, database relationships, and scalable backend design.

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA (Hibernate)
* PostgreSQL
* Gradle
* Lombok
* Redis (planned)
* Kafka (planned)

---

## 📂 Project Structure

```
controller/
service/
repository/
entity/
dto/ (future)
config/ (future)
exception/ (future)
```

---

## 🧩 Features Implemented

### ✅ User Management

* Create users
* Unique email constraint
* One user can have multiple vehicles

### ✅ Vehicle Management

* Create vehicle
* Each vehicle is linked to a user

### ✅ Role-Based Design (RBAC Ready)

* Users can have multiple roles
* Roles stored in separate table
* Mapping via `user_roles`

---

## 🗄️ Database Design

### Tables:

* users
* vehicles
* roles
* user_roles
```
+-------------+        +----------------+        +-------------+
|   users     |        |   vehicles     |        |   roles     |
+-------------+        +----------------+        +-------------+
| id (PK)     |<------ | user_id (FK)   |        | id (PK)     |
| name        |        | id (PK)        |        | role_name   |
| email       |        | model          |        +-------------+
| password    |        | vehicle_number |
+-------------+        | status         |
       |               +----------------+
       |
       | Many-to-Many
       |
+-------------------+
|   user_roles      |
+-------------------+
| user_id (FK)      |
| role_id (FK)      |
+-------------------+
```

---

## 🔗 Entity Relationships

* User → Vehicles → OneToMany
* Vehicle → User → ManyToOne
* User ↔ Roles → ManyToMany

---

## 🚀 API Endpoints

### User APIs (Planned)

```
POST /api/v1/users/register
GET /api/v1/users/{id}
```

### Vehicle APIs

```
POST /api/v1/vehicle/register
GET /api/v1/vehicle/
```

---

## ⚙️ How to Run

### 1. Start PostgreSQL (Docker recommended)

```
docker-compose up -d
```

### 2. Run application

```
./gradlew bootRun
```

---

## 🧪 Testing

Use Postman or curl to test APIs.

---

## 📈 Future Enhancements

* [ ] DTO layer implementation
* [ ] Global exception handling
* [ ] Validation (Bean Validation)
* [ ] JWT Authentication
* [ ] Role-Based Authorization (RBAC)
* [ ] Kafka integration (event-driven)
* [ ] Redis caching
* [ ] Flyway migration (optional)

---



