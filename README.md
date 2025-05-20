# Aurela Web 

Aurela Web is a Spring Boot-based RESTful API powering the Aurela e-commerce platform. It handles user registration, product and order management, image uploads. Built with scalability and security in mind, it integrates seamlessly with third-party services Cloudinary.

---

## 🚀 Features

- ✅ User registration, login, and account activation (email/code & Google OAuth2)
- 🛒 Product & category management
- 💳 Stripe-powered payment integration
- 🧾 Order creation and order history
- ☁️ Image upload with Cloudinary
- 🔐 JWT-based authentication and route protection
- 📦 RESTful API architecture

---

## 🛠️ Technologies Used

### 🧠 Core Frameworks & Language
- **Java 17**
- **Spring Boot** – for application configuration and dependency injection
- **Spring Web** – for building RESTful APIs
- **Spring Security** – for authentication and route protection
- **Spring Data JPA** – for ORM and database access
- **ModelMapper** – DTO <-> Entity transformation
- **Java Mail Sender** – for sending emails
- **OpenAPI & Swagger UI** -for API documentation and testing

### 💾 Database & Persistence
- **PostgreSQL** – primary relational database
- **Hibernate** – JPA provider

### ☁️ Cloud & Third-party Integration
- **Cloudinary** – for uploading and serving product images

### 🔐 Authentication
- **JWT (JSON Web Tokens)** – stateless authentication
- **HttpOnly Cookie Token**

### 📦 Build & Dependency Management
- **Maven** – for dependency and build management
- `.mvn/wrapper` – ensures consistent builds across environments

### 🐳 Deployment & Containerization
- **Docker** – for containerizing the application
- **Docker Compose** – for orchestrating containers in local/dev

### 🔧 Miscellaneous
- **Lombok** – to reduce boilerplate in models/services
- **Validation API** – for request validation (e.g., `@NotBlank`, `@Email`, etc.)
---
### 📄 License
- MIT
### 👤 Author
- Developed by Karima

