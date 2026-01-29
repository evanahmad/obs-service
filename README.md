# OBS Inventory Service

A simple **production-style Spring Boot backend** for managing **Items, Inventory, and Orders**.

This project demonstrates:

* Clean layered architecture (Controller → Service → Repository)
* Proper exception handling with global handler
* Inventory stock management (top-up & withdrawal)
* Order creation with stock validation
* Pagination-ready APIs
* H2 in-memory database for local development

---

## 🧱 Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Data JPA (Hibernate)
* H2 Database (in-memory)
* Lombok
* Maven / Gradle

---

## 📦 Domain Overview

### Item

Represents a sellable product.

### Inventory

Represents stock movement:

* **T (TOP_UP)** → increase stock
* **W (WITHDRAWAL)** → decrease stock

Stock is **derived**, not stored directly.

### Order

Represents a purchase transaction.

* Validates available stock before creation
* Automatically creates an inventory withdrawal

---

## ▶️ Application Startup

Run the application:

```bash
./mvnw spring-boot:run
# or
./gradlew bootRun
```

Application will start at:

```
http://localhost:8080
```

H2 Console:

```
http://localhost:8080/h2-console
```

---

## 🔄 API Flow (IMPORTANT)

You **must follow this order** when testing:

1. Create Item
2. Top-up Inventory (add stock)
3. Create Order (withdraw stock)
4. Query data

---

## 📌 API Endpoints & cURL Examples

### 1️⃣ Create Item

```bash
curl -X POST http://localhost:8080/api/v1/items \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MacBook Pro",
    "price": 25000000
  }'
```

Response:

```json
{
  "id": 1,
  "name": "MacBook Pro",
  "price": 25000000
}
```

---

### 2️⃣ Top-up Inventory (Add Stock)

```bash
curl -X POST "http://localhost:8080/api/v1/inventories?itemId=1&quantity=10&type=T"
```

This increases available stock for Item `id=1`.

---

### 3️⃣ Get Item (with remaining stock)

```bash
curl "http://localhost:8080/api/v1/items/1?includeStock=true"
```

---

### 4️⃣ Create Order (Withdraw Stock)

```bash
curl -X POST "http://localhost:8080/api/v1/orders?itemId=1&quantity=3"
```

✅ If stock is sufficient → order created
❌ If stock is insufficient → `INSUFFICIENT_STOCK` error

---

### 5️⃣ Get Order by ID

```bash
curl http://localhost:8080/api/v1/orders/1
```

---

### 6️⃣ List Orders (Paginated)

```bash
curl "http://localhost:8080/api/v1/orders?page=0&size=10"
```

---

### 7️⃣ Get Inventory Records

```bash
curl "http://localhost:8080/api/v1/inventories?page=0&size=10"
```

---

## ❗ Error Response Format

All errors follow a unified structure:

```json
{
  "errorCode": "INSUFFICIENT_STOCK",
  "errorMessage": "Not enough stock available to fulfill the request",
  "path": "/api/v1/orders",
  "time": "2026-01-30T00:45:12.123+07:00"
}
```

### Common Error Codes

| Code               | Meaning                   |
| ------------------ | ------------------------- |
| GENERAL_ERROR      | Internal server error     |
| RESOURCE_NOT_FOUND | Entity does not exist     |
| INVALID_INPUT      | Request validation failed |
| INSUFFICIENT_STOCK | Stock is not enough       |

---

## 🧪 Notes

* Stock is **calculated dynamically**, not stored
* Orders automatically create inventory withdrawals
* Transactions are managed at service boundaries
* Designed to be easily extended (payment, shipment, auth)

---

## 🚀 Next Possible Improvements

* DTO + Mapper layer
* Optimistic locking for inventory
* Integration tests
* Swagger / OpenAPI
* Authentication & authorization

---

## 👤 Author

Built as a **learning + production-grade backend exercise**.

Feel free to extend, refactor, or plug this into a frontend.

Happy coding ✨
