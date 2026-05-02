# 🏢 Employee Management System

A full-stack Employee Management System built with Spring Boot, React, and deployed on AWS EC2 with complete DevOps pipeline.

## 🌐 Live Demo
**Application URL:** http://13.235.245.16

**Test Credentials:**
- Username: `sakshi`
- Password: `sakshi123`

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3.x
- Spring Security + JWT Authentication
- Spring Data JPA + Hibernate
- MySQL 8.0
- Lombok

### Frontend
- React.js + Vite
- Tailwind CSS
- Axios
- React Router DOM
- Context API

### DevOps
- Docker + Docker Compose
- GitHub Actions (CI/CD)
- AWS EC2
- Kubernetes (Minikube)
- Nginx

---

## ✨ Features

- 🔐 JWT Authentication (Register, Login, Change Password)
- 👥 Employee Management (CRUD, Search, Pagination)
- 🏢 Department Management (CRUD)
- 📊 Dashboard with real-time counts
- 🔍 Search employees by name/job title
- 📄 Pagination support
- 🛡️ Protected routes
- 🐳 Fully containerized with Docker

---

## 🏗️ Architecture

React Frontend (Nginx)
↓
Spring Boot Backend (REST API)
↓
MySQL Database

All services run in Docker containers orchestrated by Docker Compose.

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- Node.js 20+
- MySQL 8.0
- Docker & Docker Compose

### Run with Docker Compose (Recommended)

```bash
# Clone the repository
git clone https://github.com/Sak2803shi/Employee-management-system.git
cd Employee-management-system

# Start all services
docker-compose up -d

# Access the app
open http://localhost
```

### Run locally

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

---

## 🐳 Docker

```bash
# Build and run all containers
docker-compose up --build -d

# Stop all containers
docker-compose down

# View logs
docker-compose logs -f
```

---

## ☸️ Kubernetes (Minikube)

```bash
# Start Minikube
minikube start --driver=docker

# Apply manifests
kubectl apply -f k8s/mysql-secret.yml
kubectl apply -f k8s/mysql-deployment.yml
kubectl apply -f k8s/backend-deployment.yml
kubectl apply -f k8s/frontend-deployment.yml

# Check pods
kubectl get pods

# Access app
minikube service frontend-service --url
```

---

## 🔄 CI/CD Pipeline

GitHub Actions automatically:
1. Builds Spring Boot JAR
2. Builds Docker images
3. Pushes to Docker Hub

**Docker Hub:** https://hub.docker.com/u/sak0328nov

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register user |
| POST | `/api/auth/login` | Login |
| POST | `/api/auth/change-password` | Change password |

### Employees
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get by ID |
| POST | `/api/employees` | Create employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/search?keyword=` | Search |
| GET | `/api/employees/paginated` | With pagination |

### Departments
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/departments` | Get all departments |
| POST | `/api/departments` | Create department |
| PUT | `/api/departments/{id}` | Update department |
| DELETE | `/api/departments/{id}` | Delete department |

---

## 👩‍💻 Developer

**Sakshi Pensalwar**
- GitHub: [@Sak2803shi](https://github.com/Sak2803shi)

---

## 📝 License
This project is for educational purposes.