# 💼 Job Portal Backend 

## 📌 Overview

A production-style Job Portal Backend application built using Spring Boot and Spring Security. The system supports JWT authentication, role-based authorization, job management, job applications, resume uploads, recruiter dashboards, and application status workflow management.

This project follows a layered architecture and demonstrates real-world backend development practices used in modern recruitment platforms.

---

## 🚀 Features

### 👤 Authentication & Authorization

* User Registration
* User Login
* JWT Token-Based Authentication
* BCrypt Password Encryption
* Role-Based Access Control

#### Roles

* ADMIN
* RECRUITER
* JOB_SEEKER

---

### 💼 Job Management

* Create Jobs
* Update Jobs
* Delete Jobs
* View All Jobs
* View Job Details
* Recruiter Ownership Validation

---

### 📄 Job Applications

* Apply For Jobs
* Resume Upload
* Prevent Duplicate Applications
* View Applied Jobs
* Withdraw Applications
* Recruiter View Applicants
* Recruiter Update Application Status

---

### 📁 Resume Management

* Resume Upload
* Resume Storage
* Resume Download/View

---

### 📊 Recruiter Dashboard

* Total Jobs Posted
* Total Applications Received
* Applications By Status
* Recent Applications

---

## 🔄 Application Status Workflow

The system enforces valid application status transitions.

```text
APPLIED
   ↓
REVIEWED
   ↓
SHORTLISTED
   ↓
HIRED
```

or

```text
APPLIED
   ↓
REVIEWED
   ↓
REJECTED
```

### Allowed Transitions

```text
APPLIED → REVIEWED

REVIEWED → SHORTLISTED
REVIEWED → REJECTED

SHORTLISTED → HIRED
SHORTLISTED → REJECTED
```

### Blocked Transitions

```text
APPLIED → HIRED ❌

REVIEWED → APPLIED ❌

HIRED → REVIEWED ❌

REJECTED → APPLIED ❌
```

Invalid transitions are automatically rejected.

---

## ⚠️ Exception Handling

Global exception handling implemented using:

```java
@RestControllerAdvice
```

Custom Exceptions:

* ResourceNotFoundException
* InvalidStatusTransitionException
* AccessDeniedException

Returns meaningful HTTP responses instead of generic errors.

---

## 🧱 Tech Stack

### Backend

* Java 24
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Maven
* Lombok

### Database

* PostgreSQL

### Security

* JWT Authentication
* BCrypt Password Encoder

---

## 🏗️ Architecture

```text
Controller
    ↓
Service Interface
    ↓
Service Implementation
    ↓
Repository
    ↓
PostgreSQL
```

### Design Principles

* Layered Architecture
* DTO-Based Communication
* Separation of Concerns
* RESTful API Design

---

## 🔐 Security Features

* Stateless Authentication
* JWT Access Tokens
* Role-Based Authorization
* Protected Endpoints
* Password Encryption using BCrypt
* Custom JWT Filter
* Secure API Access

---

## 📡 REST API Endpoints

### Authentication

| Method | Endpoint |
|----------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |

---

### Jobs

| Method | Endpoint |
|----------|----------|
| GET | /api/jobs |
| GET | /api/jobs/{id} |
| POST | /api/recruiter/jobs |
| PUT | /api/recruiter/jobs/{id} |
| DELETE | /api/recruiter/jobs/{id} |

---

### Applications

| Method | Endpoint |
|----------|----------|
| POST | /api/jobseeker/apply/{jobId} |
| GET | /api/jobseeker/applications |
| DELETE | /api/jobseeker/{applicationId} |

---

### Recruiter

| Method | Endpoint |
|----------|----------|
| GET | /api/recruiter/dashboard |
| GET | /api/recruiter/jobs |
| GET | /api/recruiter/jobs/{jobId}/applicants |
| PUT | /api/recruiter/applications/{applicationId}/status |

---

### Resume

| Method | Endpoint |
|----------|----------|
| GET | /api/resumes/{fileName} |

---

## 🎯 Key Learning Outcomes

* Spring Boot REST API Development
* Spring Security Configuration
* JWT Authentication & Authorization
* Role-Based Access Control
* File Upload Handling
* Resume Management
* Database Design with JPA & Hibernate
* Recruiter & Job Seeker Workflow Design
* Exception Handling in Production Applications
* RESTful API Design
* Clean Architecture Practices

---

## 📈 Future Enhancements

* Email Notifications
* Interview Scheduling
* Pagination & Sorting
* Advanced Search Filters
* Recruiter Profiles
* Company Profiles
* Docker Containerization
* CI/CD Pipeline
* Cloud Deployment
* Microservices Architecture

---

## 👨‍💻 Author

**Shivam Sharma**

Java Full Stack Developer

### Skills

* Java
* Spring Boot
* Spring Security
* PostgreSQL
* React
* REST APIs
* JWT Authentication
* Hibernate
* Maven
