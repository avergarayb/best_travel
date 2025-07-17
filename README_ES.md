# Best Travel API

## 📋 Descripción

**Best Travel** es una aplicación backend desarrollada en Spring Boot que proporciona una API REST completa para la gestión de servicios de viajes. La aplicación permite gestionar vuelos, hoteles, reservas, tickets y tours, con funcionalidades avanzadas como conversión de monedas, notificaciones por email, y generación de reportes.

## 🏗️ Arquitectura y Tecnologías

### Stack Tecnológico
- **Framework**: Spring Boot30.5- **Java**: JDK 17
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

### 1 Gestión de Vuelos (`/fly`)
- ✅ Listar vuelos con paginación y ordenamiento
- ✅ Búsqueda por precio (menor que, entre rangos)
- ✅ Búsqueda por origen y destino
- ✅ Cache con Redis para optimización

###2Gestión de Hoteles (`/hotel`)
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

###6estión de Usuarios (`/user`)
- ✅ Habilitar/deshabilitar usuarios
- ✅ Gestión de roles (USER, ADMIN)
- ✅ Autenticación OAuth2 con JWT

### 7. Reportes (`/report`)
- ✅ Generación de reportes Excel
- ✅ Estadísticas de clientes y ventas
- ✅ Descarga automática de archivos

### 8Características Adicionales
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

### 1lonar el Repositorio
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

###3antar Infraestructura
```bash
docker-compose up -d
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

###5. Verificar Instalación
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

## ⚠️ Problemas Identificados y Mejoras

### 🔴 Problemas Críticos de Seguridad1*Credenciales Hardcodeadas**
   - ❌ Contraseñas en `application.properties`
   - ❌ API keys en archivos de configuración
   - ❌ Credenciales de base de datos expuestas

2. **Falta de Validación**
   - ❌ No hay validación de entrada en muchos endpoints
   - ❌ Ausencia de rate limiting
   - ❌ No hay protección contra SQL injection

3 **Gestión de Errores**
   - ❌ Información sensible en logs
   - ❌ Stack traces expuestas al cliente

### 🟡 Problemas de Diseño1 **Arquitectura**
   - ⚠️ Mezcla de responsabilidades en servicios
   - ⚠️ Falta de interfaces para testing
   - ⚠️ Dependencias circulares potenciales

2**Base de Datos**
   - ⚠️ Uso de `FetchType.EAGER` que puede causar N+1 queries
   - ⚠️ Falta de índices optimizados
   - ⚠️ No hay migraciones de base de datos3. **Performance**
   - ⚠️ Cache no configurado correctamente
   - ⚠️ Llamadas síncronas a APIs externas
   - ⚠️ Falta de paginación en algunos endpoints

### 🟢 Mejoras Recomendadas

#### Seguridad
```java
// Usar variables de entorno
@Value(${DB_PASSWORD}")
private String dbPassword;

// Implementar rate limiting
@RateLimiter(name = "api")
public ResponseEntity<?> endpoint() [object Object] Validación robusta
@Valid @RequestBody TourRequest request
```

#### Performance
```java
// Cache asíncrono
@Async
@Cacheable("hotels")
public Set<HotelResponse> getHotels() [object Object]Paginación consistente
public Page<Response> getAll(Pageable pageable) { }

// Lazy loading
@OneToMany(fetch = FetchType.LAZY)
private Set<Entity> entities;
```

#### Arquitectura
```java
// Separar interfaces
public interface TourService {
    TourResponse create(TourRequest request);
}

// DTOs específicos
public class TourCreateDTO { }
public class TourUpdateDTO[object Object]
// Manejo de errores centralizado
@ControllerAdvice
public class GlobalExceptionHandler { }
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
2. Crear feature branch (`git checkout -b feature/AmazingFeature`)3 Commit cambios (`git commit -m 'Add AmazingFeature'`)4 Push al branch (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autor

- 🌐 [LinkedIn](https://www.linkedin.com/in/abel-vergaray-barrena-software)
- ✉️ Email: avergarayb@gmail.com
- 🌍 Location: Trujillo, Peru

---

**Nota**: Este proyecto es una demostración educativa. Para uso en producción, implementar todas las mejoras de seguridad y performance recomendadas. 