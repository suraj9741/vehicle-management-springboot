
# 🚗 Vehicle Management System (Spring Boot)

## 📌 Overview

A **production-ready backend system** built using **Spring Boot** that manages users, vehicles, and real-time vehicle events.

This project demonstrates:

* REST API development
* Role-Based Access Control (RBAC)
* Event-driven architecture using Kafka
* Performance optimization using Redis caching
* Secure authentication using JWT
* Clean architecture with logging & exception handling

---

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3**
* **Spring Security + JWT**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL**
* **Apache Kafka**
* **Redis (Caching)**
* **Gradle**
* **Docker & Docker Compose**
* **Lombok**

---

## 🏗️ Architecture

### 🔹 High-Level Flow

```text
Client → Controller → Service → Cache (Redis) → DB (Postgres)
                                 ↓
                             Kafka Producer
                                 ↓
                           Kafka Topic
                                 ↓
                        Event Consumer Service
                                 ↓
                          Notification Service
```

---

## 📂 Project Structure

```
controller/
service/
service/impl/
repository/
entity/
model/
converter/
config/
exception/
security/
common/ (API wrapper)
```

---

## 🧩 Features Implemented

---

### 👤 User Management

* Register user
* Update user
* Delete user
* Fetch user(s)
* Password encryption
* Default role assignment

---

### 🚗 Vehicle Management

* Register vehicle
* Fetch vehicles (all / by user)
* Delete vehicle
* One user → multiple vehicles

---

### 🔐 Authentication & Authorization

* JWT-based authentication
* Secure endpoints using Spring Security
* Role-based access (ADMIN / USER)

---

### ⚡ Kafka (Event-Driven Architecture)

#### Flow:

```text
Vehicle Data → Kafka (vehicle-data)
             ↓
     Event Evaluator Service
             ↓
Kafka (vehicle-events)
             ↓
Notification Service
```

#### Supported Events:

* Battery Low
* Door Open
* Lights ON when engine OFF
* Service Due

---

### 🔔 Notification System

* Kafka consumer listens to events
* Sends notifications (currently logs)
* Easily extendable to:

    * Email
    * SMS
    * Push notifications

---

### ⚡ Redis Caching (Performance Optimization)

* Cached APIs:

    * `getAllVehicles()`
    * `getVehiclesByUserId()`
    * `getUserById()`

* Features:

    * Cache TTL
    * Cache eviction on update/delete
    * JSON serialization
    * Cache hit/miss logging

---

### 🧾 Logging

* Structured logging using SLF4J
* Logs for:

    * API flow
    * Kafka events
    * Cache behavior
    * Errors

---

### ⚠️ Exception Handling

* Global exception handler (`@RestControllerAdvice`)
* Handles:

    * Validation errors
    * Runtime exceptions
    * Generic errors

---

## 🗄️ Database Design

### Tables:

* `users`
* `vehicles`
* `roles`
* `user_roles`

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

* **User → Vehicle** → OneToMany
* **Vehicle → User** → ManyToOne
* **User ↔ Roles** → ManyToMany

---

## 🚀 API Endpoints

---

### 🔐 Auth APIs

```
POST /api/v1/auth/login
```

---

### 👤 User APIs

```
POST   /api/v1/users
GET    /api/v1/users/{id}
GET    /api/v1/users
PUT    /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

---

### 🚗 Vehicle APIs

```
POST   /api/v1/vehicles
GET    /api/v1/vehicles
GET    /api/v1/vehicles/user/{userId}
DELETE /api/v1/vehicles/{id}
```

---

### ⚙️ Admin APIs

```
GET    /api/v1/admin/users
DELETE /api/v1/admin/users/{id}
PUT    /api/v1/admin/users/{userId}/roles/{roleName}
GET    /api/v1/admin/vehicles
DELETE /api/v1/admin/vehicles/{id}
```

---

### 📡 Kafka Testing APIs

```
POST /api/v1/kafka/publish
GET  /api/v1/kafka/status
```

---

## ⚙️ How to Run

---

### 1️⃣ Start Infrastructure (Docker)

```bash
docker-compose up -d
```

Services:

* PostgreSQL → `5432`
* Kafka → `9092, 9093`
* Kafka UI → `http://localhost:8000`
* Redis → `6379`

---

### 2️⃣ Run Application

```bash
./gradlew bootRun
```

---

### 3️⃣ Test APIs

Use:

* Postman
* Curl

---

## 🧪 Testing Cache

1. Call API → DB hit (log visible)
2. Call again → Cache hit (no DB log)
3. Check Redis:

```bash
docker exec -it redis-vms redis-cli
keys *
```


