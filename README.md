# Best Travel API

## 📋 Description

**Best Travel** is a Spring Boot backend application that provides a complete REST API for travel services management. The application allows managing flights, hotels, reservations, tickets, and tours, with advanced features such as currency conversion, email notifications, and report generation.

## 🏗️ Architecture and Technologies

### Technology Stack
- **Framework**: Spring Boot 3.0.5 - **Java**: JDK 17
- **Database**: PostgreSQL (main data) + MongoDB (users)
- **Cache**: Redis
- **Security**: Spring Security + OAuth2 + JWT
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven
- **Containers**: Docker & Docker Compose

### Architectural Pattern
- **Layered Architecture**: Controllers → Services → Repositories → Entities
- **Separation of Concerns**: DTOs, Entities, Services
- **Dependency Injection**: Spring IoC Container
- **Aspects**: AOP for logging and notifications

## 🚀 Main Features

### 1. Flight Management (`/fly`)
- ✅ List flights with pagination and sorting
- ✅ Search by price (less than, between ranges)
- ✅ Search by origin and destination
- ✅ Redis cache for optimization

### 2. Hotel Management (`/hotel`)
- ✅ List hotels with pagination and sorting
- ✅ Search by price (less than, between ranges)
- ✅ Search by rating
- ✅ Redis cache for optimization

### 3. Ticket Management (`/ticket`)
- ✅ Create, read, update and delete tickets
- ✅ Automatic currency conversion (USD, EUR, etc.)
- ✅ Price calculation with additional charges (25%)
- ✅ Email notifications

### 4. Reservation Management (`/reservation`)
- ✅ Create, read, update and delete reservations
- ✅ Automatic currency conversion
- ✅ Price calculation with additional charges (20%)
- ✅ Email notifications

### 5. Tour Management (`/tour`)
- ✅ Create tours combining flights and hotels
- ✅ Add/remove tickets and reservations from tours
- ✅ Complete travel package management
- ✅ Email notifications

### 6. User Management (`/user`)
- ✅ Enable/disable users
- ✅ Role management (USER, ADMIN)
- ✅ OAuth2 authentication with JWT

### 7. Reports (`/report`)
- ✅ Excel report generation
- ✅ Customer and sales statistics
- ✅ Automatic file download

### 8. Additional Features
- 🔄 **Currency Conversion**: External API integration
- 📧 **Notifications**: Automatic email sending
- 🚫 **Blacklist**: Blocked customer control
- 📊 **Logging**: Aspects for auditing
- 🔒 **Security**: OAuth2 + JWT + Roles
- ⚡ **Cache**: Redis for performance optimization

## 🗄️ Data Model

### Main Entities (PostgreSQL)
- **Customer**: Customers with personal information and statistics
- **Fly**: Flights with origin, destination, price and airline
- **Hotel**: Hotels with location, rating and price
- **Ticket**: Flight tickets with dates and prices
- **Reservation**: Hotel reservations with dates and prices
- **Tour**: Packages combining flights and hotels

### Documents (MongoDB)
- **AppUser**: System users with roles and permissions

## 🔧 Configuration and Installation

### Prerequisites
- Java 17+
- Docker and Docker Compose
- Maven 3.6. Clone Repository
```bash
git clone <repository-url>
cd best_travel
```

### 2. Configure Environment Variables
Create `.env` file in project root:
```env
# Database
POSTGRES_DB=best_travel
POSTGRES_USER=alejandro
POSTGRES_PASSWORD=debuggeandoideas

# MongoDB
MONGO_USERNAME=master
MONGO_PASSWORD=debuggeandoideas

# Redis
REDIS_PASSWORD=debuggeandoideas

# Email
EMAIL_USERNAME=my_email@gmail.com
EMAIL_PASSWORD=my_password

# External API
API_CURRENCY_KEY=My_API
```

### 3. Start Infrastructure
```bash
docker-compose up -d
```

### 4. Run Application
```bash
mvn spring-boot:run
```

### 5. Verify Installation
- **API**: http://localhost:8080best_travel
- **Swagger**: http://localhost:880est_travel/swagger-ui/index.html
- **PostgreSQL**: localhost:5432**MongoDB**: localhost:27017- **Redis**: localhost:6379

## 🔐 Authentication and Authorization

### OAuth2 Flow1*Get Token**: POST `/oauth2/token`2**Authorize**: GET `/oauth2uthorize`
3. **Use API**: Include Bearer token in headers

### Roles and Permissions
- **PUBLIC**: `/fly/**`, `/hotel/**`, `/swagger-ui/**`, `/report/**`
- **USER**: `/tour/**`, `/ticket/**`, `/reservation/**`
- **ADMIN**: `/user/**`

### Test Users
```json
[object Object]
  ragnar777: 3,
 heisenberg: 4sw0d,misterX:misterX123,
 neverMore:4dmIn"
}
```

## 📚 Main Endpoints

### Flights
```http
GET /fly?page=0&size=10sortType=UPPER
GET /fly/less_price?price=100/fly/between_price?min=50ax=20
GET /fly/origin_destiny?origin=Mexico&destiny=Grecia
```

### Hotels
```http
GET /hotel?page=0size=10sortType=LOWER
GET /hotel/less_price?price=100
GET /hotel/between_price?min=50max=200
GET /hotel/rating?rating=4
```

### Tours
```http
POST /tour[object Object]
 customerId: "VIKI771012RG93,
  flights": [object Object]id":1}, {"id": 2],
  hotels": [{"id: 1totalDays:3,email":user@example.com"
}
```

### Tickets
```http
POST /ticket
GET /ticket/{id}
PUT /ticket/{id}
DELETE /ticket/{id}
GET /ticket?flyId=1&currency=EUR
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Coverage
```bash
mvn jacoco:report
```

## 📊 Monitoring and Logs

### Logs
- **Level**: DEBUG for SQL, INFO for operations
- **Format**: Structured JSON
- **Rotation**: Daily with compression

### Metrics
- **Health Checks**: `/actuator/health`
- **Metrics**: `/actuator/metrics`
- **Cache Stats**: Redis INFO

## 🚀 Deployment

### Docker
```bash
docker build -t best-travel .
docker run -p 8080:8080 best-travel
```

### Kubernetes
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: best-travel
spec:
  replicas:3elector:
    matchLabels:
      app: best-travel
```

## 📝 Contributing

1. Fork the project
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 👥 Author

- 🌐 [LinkedIn](https://www.linkedin.com/in/abel-vergaray-barrena-software)
- ✉️ Email: avergarayb@gmail.com
- 🌍 Location: Trujillo, Peru

---

---

# Best Travel API

## 📋 Descripción

**Best Travel** es una aplicación backend desarrollada en Spring Boot que proporciona una API REST completa para la gestión de servicios de viajes. La aplicación permite gestionar vuelos, hoteles, reservas, tickets y tours, con funcionalidades avanzadas como conversión de monedas, notificaciones por email, y generación de reportes.

## 🏗️ Arquitectura y Tecnologías

### Stack Tecnológico
- **Framework**: Spring Boot 3.0.5 - **Java**: JDK 17
- **Base de Datos**: PostgreSQL (datos principales) + MongoDB (usuarios)
- **Cache**: Redis
- **Seguridad**: Spring Security + OAuth2 + JWT
- **Documentación**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven
- **Contenedores**: Docker & Docker Compose

### Patrón Arquitectónico
- **Arquitectura en Capas**: Controllers → Services → Repositories → Entities
- **Separación de Responsabilidades**: DTOs, Entidades, Servicios
- **Inyección de Dependencias**: Spring IoC Container
- **Aspectos**: AOP para logging y notificaciones

## 🚀 Funcionalidades Principales

### 1. Gestión de Vuelos (`/fly`)
- ✅ Listar vuelos con paginación y ordenamiento
- ✅ Búsqueda por precio (menor que, entre rangos)
- ✅ Búsqueda por origen y destino
- ✅ Cache con Redis para optimización

### 2. Gestión de Hoteles (`/hotel`)
- ✅ Listar hoteles con paginación y ordenamiento
- ✅ Búsqueda por precio (menor que, entre rangos)
- ✅ Búsqueda por rating
- ✅ Cache con Redis para optimización

### 3. Gestión de Tickets (`/ticket`)
- ✅ Crear, leer, actualizar y eliminar tickets
- ✅ Conversión automática de monedas (USD, EUR, etc.)
- ✅ Cálculo de precios con cargos adicionales (25%)
- ✅ Notificaciones por email

### 4. Gestión de Reservas (`/reservation`)
- ✅ Crear, leer, actualizar y eliminar reservas
- ✅ Conversión automática de monedas
- ✅ Cálculo de precios con cargos adicionales (20%)
- ✅ Notificaciones por email

### 5. Gestión de Tours (`/tour`)
- ✅ Crear tours combinando vuelos y hoteles
- ✅ Agregar/remover tickets y reservas de tours
- ✅ Gestión de paquetes completos de viaje
- ✅ Notificaciones por email

### 6. Gestión de Usuarios (`/user`)
- ✅ Habilitar/deshabilitar usuarios
- ✅ Gestión de roles (USER, ADMIN)
- ✅ Autenticación OAuth2 con JWT

### 7. Reportes (`/report`)
- ✅ Generación de reportes Excel
- ✅ Estadísticas de clientes y ventas
- ✅ Descarga automática de archivos

### 8. Características Adicionales
- 🔄 **Conversión de Monedas**: Integración con API externa
- 📧 **Notificaciones**: Envío automático de emails
- 🚫 **Lista Negra**: Control de clientes bloqueados
- 📊 **Logging**: Aspectos para auditoría
- 🔒 **Seguridad**: OAuth2 + JWT + Roles
- ⚡ **Cache**: Redis para optimización de rendimiento

## 🗄️ Modelo de Datos

### Entidades Principales (PostgreSQL)
- **Customer**: Clientes con información personal y estadísticas
- **Fly**: Vuelos con origen, destino, precio y aerolínea
- **Hotel**: Hoteles con ubicación, rating y precio
- **Ticket**: Tickets de vuelo con fechas y precios
- **Reservation**: Reservas de hotel con fechas y precios
- **Tour**: Paquetes que combinan vuelos y hoteles

### Documentos (MongoDB)
- **AppUser**: Usuarios del sistema con roles y permisos

## 🔧 Configuración e Instalación

### Prerrequisitos
- Java 17+
- Docker y Docker Compose
- Maven30.6

### 1. Clonar el Repositorio
```bash
git clone <repository-url>
cd best_travel
```

### 2. Configurar Variables de Entorno
Crear archivo `.env` en la raíz del proyecto:
```env
# Base de Datos
POSTGRES_DB=best_travel
POSTGRES_USER=alejandro
POSTGRES_PASSWORD=debuggeandoideas

# MongoDB
MONGO_USERNAME=master
MONGO_PASSWORD=debuggeandoideas

# Redis
REDIS_PASSWORD=debuggeandoideas

# Email
EMAIL_USERNAME=my_email@gmail.com
EMAIL_PASSWORD=my_password

# API Externa
API_CURRENCY_KEY=My_API
```

### 3. Iniciar Infraestructura
```bash
docker-compose up -d
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

### 5. Verificar Instalación
- **API**: http://localhost:8080best_travel
- **Swagger**: http://localhost:880est_travel/swagger-ui/index.html
- **PostgreSQL**: localhost:5432**MongoDB**: localhost:27017- **Redis**: localhost:6379# 🔐 Autenticación y Autorización

### OAuth2 Flow
1**Obtener Token**: POST `/oauth2/token`2**Autorizar**: GET `/oauth2authorize`
3. **Usar API**: Incluir Bearer token en headers

### Roles y Permisos
- **PUBLIC**: `/fly/**`, `/hotel/**`, `/swagger-ui/**`, `/report/**`
- **USER**: `/tour/**`, `/ticket/**`, `/reservation/**`
- **ADMIN**: `/user/**`

### Usuarios de Prueba
```json
[object Object]
  ragnar777: 3,
 heisenberg: 4sw0d,misterX:misterX123,
 neverMore:4dmIn
}
```

## 📚 Endpoints Principales

### Vuelos
```http
GET /fly?page=0&size=10sortType=UPPER
GET /fly/less_price?price=100/fly/between_price?min=50ax=20
GET /fly/origin_destiny?origin=Mexico&destiny=Grecia
```

### Hoteles
```http
GET /hotel?page=0size=10sortType=LOWER
GET /hotel/less_price?price=100
GET /hotel/between_price?min=50max=200
GET /hotel/rating?rating=4
```

### Tours
```http
POST /tour[object Object]
 customerId: "VIKI771012RG93,
  flights": [object Object]id":1}, {"id": 2],
  hotels": [{"id: 1totalDays:3,email":user@example.com"
}
```

### Tickets
```http
POST /ticket
GET /ticket/{id}
PUT /ticket/{id}
DELETE /ticket/{id}
GET /ticket?flyId=1&currency=EUR
```

## 🧪 Testing

### Ejecutar Tests
```bash
mvn test
```

### Cobertura
```bash
mvn jacoco:report
```

## 📊 Monitoreo y Logs

### Logs
- **Nivel**: DEBUG para SQL, INFO para operaciones
- **Formato**: JSON estructurado
- **Rotación**: Diaria con compresión

### Métricas
- **Health Checks**: `/actuator/health`
- **Métricas**: `/actuator/metrics`
- **Cache Stats**: Redis INFO

## 🚀 Despliegue

### Docker
```bash
docker build -t best-travel .
docker run -p 8080:8080 best-travel
```

### Kubernetes
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: best-travel
spec:
  replicas:3elector:
    matchLabels:
      app: best-travel
```

## 📝 Contribución

1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request


## 👥 Autor

- 🌐 [LinkedIn](https://www.linkedin.com/in/abel-vergaray-barrena-software)
- ✉️ Email: avergarayb@gmail.com
- 🌍 Location: Trujillo, Peru

---

