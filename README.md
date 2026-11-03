<h1 align="center"> Project Publisher Manager</h1>

You need to install the following technology:

<div align="left">

![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-3069DE?style=for-the-badge&logo=kubernetes&logoColor=white)
![Helm](https://img.shields.io/badge/Helm-0F1689?style=for-the-badge&logo=Helm&labelColor=0F1689)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

</div>

---

## About the Project

This is the second microservice with closed scope. The project consists of a **publisher manager**,
in which the user can register, list, update and delete gaming companies (publishers) such as Nintendo, Sega, Capcom, etc.

In addition to publisher management, this microservice is responsible for **notifying external client applications** (such as [Game Manager](https://github.com/LucasSResende/game-manager)) through an automated callback mechanism, clearing cached data whenever a publisher is created, updated or deleted.

---

## End-Points (Publisher Manager - Port 8080)

### 1. Read all publishers:
`GET http://localhost:8080/publisher`

### 2. Read publisher by ID:
`GET http://localhost:8080/publisher/{id}` (or `?id={id}`)

### 3. Register a new publisher:
`POST http://localhost:8080/publisher`

Payload example:
```json
{
  "id": "capcom",
  "name": "Capcom Co., Ltd."
}
```

### 4. Update publisher name (NEW):
`PUT http://localhost:8080/publisher/{id}`

Payload example:
```json
{
  "name": "Capcom Entertainment"
}
```
Returns `200 OK` with updated publisher, and invokes callback to clear client caches.

### 5. Delete publisher (NEW):
`DELETE http://localhost:8080/publisher/{id}`

Returns `204 No Content` on success, and invokes callback to clear client caches.

### 6. Register consumer application for cache callbacks:
`POST http://localhost:8080/notification`

### 7. Read all registered notification consumers:
`GET http://localhost:8080/notification`
