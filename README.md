# 🔧 Employee Management System – REST API (Spring Boot)

This is a RESTful version of the Employee Management System built using **Spring Boot**, exposing **REST APIs** to manage employees, projects, and clients.  
This is a backend-only version (no UI) meant to be consumed by frontend apps or Postman.

---

## 💻 Tech Stack

- Java 8
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Eclipse IDE
- Postman (for testing APIs)

---

## 📌 Project Features

- ✅ Add, update, delete employees
- ✅ Add, update, delete clients and projects
- ✅ Assign and release employees from projects
- ✅ View employees on bench
- ✅ OTP-based login for employees/clients
- ✅ 5-failed attempt lockout with 5-minute ban
- ✅ Auto ID generation: `JTC-001`, `client-001`, etc.

---

## 🌐 API Endpoints (Examples)

| Method | Endpoint                  | Description                   |
|--------|---------------------------|-------------------------------|
| POST   | `/api/employees`          | Add new employee              |
| GET    | `/api/employees/{id}`     | Get employee by ID            |
| PUT    | `/api/employees/{id}`     | Update employee               |
| DELETE | `/api/employees/{id}`     | Delete employee               |
| POST   | `/api/clients`            | Add new client                |
| POST   | `/api/login/send-otp`     | Send OTP to email             |
| POST   | `/api/login/verify-otp`   | Verify OTP                    |

---

## 🧪 How to Run Locally

1. Clone this repo:
   ```bash
   git clone https://github.com/Shiv241197/EmployeeManagement-REST.git


---
---

## 👩‍💻 Developed by

**Shivani Malokar**  
📧 shivanimalokar7@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/shivani-malokar241197)
