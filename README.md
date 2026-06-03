# Workshop: Spring Boot API Demo - Progra III

## Datos del estudiante

* **Nombre:** Jorge Mario Cano Cobon
* **Carne:** 0905-24-10433
* **Carrera:** Ingenieria en Sistemas de Informacion
* **Universidad:** Universidad Mariano Galvez de Guatemala (UMG)

---

## Estado del proyecto

* **Build:** `SUCCESS`
* **Tests:** `16/16 Passing`
* **Java Version:** 17
* **Framework:** Spring Boot 3.3.5

## Tecnologias utilizadas

* **Maven:** Manejo de dependencias.
* **Spring Boot Web:** API REST.
* **Jakarta Validation:** Validacion de datos de entrada.
* **SpringDoc / Swagger:** Documentacion interactiva de la API.
* **OpenAPI Generator:** Interfaces de contrato generadas desde `openapi.yaml`.
* **JUnit 5 & MockMvc:** Pruebas de controladores y servicios.
* **Spring Data JPA + PostgreSQL:** Incremento de categorias y productos.

## Funcionalidades implementadas

1. **Endpoint de salud:** `GET /api/v1`
2. **Sistema de saludos:** `GET/POST /api/v1/saludos` con normalizacion de nombres.
3. **Manejo global de excepciones:** respuestas estructuradas para validacion, reglas de negocio y entidades no encontradas.
4. **Simulador de prestamos:** `POST /api/v1/simulaciones/prestamo` con calculo de cuota fija.
5. **Demo de estado:** endpoints de comparacion singleton/manual.
6. **Incremento JPA:** CRUD de categorias y productos.
7. **Contract-first completo:** contrato OpenAPI con workshop, simulaciones, demo de estado, categorias y productos.

---

# Workshop Spring Boot 3.x + JDK 17 (Maven)

Este repositorio es un tutorial paso a paso para clase/laboratorio.
Los estudiantes avanzan descomentando bloques, probando endpoints y completando ejercicios.

## Objetivo del taller

- Practicar arquitectura basica de API REST con Spring Boot.
- Aplicar validaciones con `jakarta.validation`.
- Manejar errores de forma global.
- Documentar la API con Swagger/OpenAPI.
- Implementar un endpoint de negocio mas desafiante.

---

## 0) Requisitos

- Java `17`
- Maven `3.9+`
- PostgreSQL local para los endpoints JPA:
  - Base de datos: `workshop_jpa`
  - Usuario: `postgres`
  - Password: `0000`

Verifica Java:

```bash
java -version
```

Debe mostrar `17.x`.

---

## 1) Comando para generar el proyecto Spring Boot

Este comando usa Spring Initializr y genera un proyecto Maven con Java 17 y Spring Boot 3.x:

```bash
curl https://start.spring.io/starter.zip \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.3.5 \
  -d groupId=com.ejemplo \
  -d artifactId=springboot-api-demo \
  -d name=springboot-api-demo \
  -d packageName=com.ejemplo.demo \
  -d javaVersion=17 \
  -d dependencies=web,validation \
  -o springboot-api-demo.zip
```

```bash
unzip springboot-api-demo.zip -d .
cd springboot-api-demo
```

---

## 2) Paso a paso del taller

### Paso 1 - Ejecutar base del proyecto

```bash
mvn spring-boot:run
```

Probar endpoint base:

```bash
curl http://localhost:8080/api/v1
```

Respuesta esperada:

```json
{
  "estado": "ok",
  "mensaje": "Workshop Spring Boot activo"
}
```

### Paso 2 - Habilitar endpoint GET de saludos

Probar:

```bash
curl "http://localhost:8080/api/v1/saludos?nombre=Ana"
```

### Paso 3 - Habilitar endpoint POST con validacion

Probar caso correcto:

```bash
curl -X POST http://localhost:8080/api/v1/saludos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana"}'
```

Probar caso invalido:

```bash
curl -X POST http://localhost:8080/api/v1/saludos \
  -H "Content-Type: application/json" \
  -d '{"nombre":""}'
```

Debe responder `400` en el caso invalido.

### Paso 4 - Ejercicio de logica de negocio

En `src/main/java/com/ejemplo/demo/domain/service/SaludoService.java`:

- Quitar espacios al inicio/final.
- Convertir primera letra a mayuscula y resto a minuscula.
- Rechazar nombres con numeros mediante regla de negocio.

### Paso 5 - Manejo de errores de negocio

En `src/main/java/com/ejemplo/demo/api/exception/GlobalExceptionHandler.java`:

- Si hay error de negocio, responder `400` con codigo `BUSINESS_RULE_ERROR`.
- Si hay validacion invalida, responder `400` con codigo `VALIDATION_ERROR`.

### Paso 6 - Completar pruebas

Ejecutar:

```bash
mvn test
```

### Paso 7 - Documentar API con Swagger/OpenAPI

Levantar proyecto y abrir:

- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs`

### Paso 8 - Crear endpoint desafiante obligatorio

#### Simulador de prestamo

- Endpoint: `POST /api/v1/simulaciones/prestamo`
- Request:
  - `monto` (BigDecimal, > 0)
  - `tasaAnual` (BigDecimal, > 0)
  - `meses` (int, entre 1 y 360)
- Response:
  - `cuotaMensual`
  - `interesTotal`
  - `totalPagar`

Requisitos implementados:

- Validar datos de entrada con `jakarta.validation`.
- Implementar logica en `PrestamoService`.
- Manejar errores de negocio en `GlobalExceptionHandler`.
- Documentar endpoint en Swagger/OpenAPI.
- Crear pruebas de caso exitoso y caso invalido.

Formula utilizada:

```text
cuota = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
```

Donde:

- `P` = monto
- `r` = tasa mensual (`tasaAnual / 12 / 100`)
- `n` = numero de meses

---

## Checklist final

- [x] Proyecto corre en local
- [x] GET `/api/v1` responde OK
- [x] GET `/api/v1/saludos` habilitado
- [x] POST `/api/v1/saludos` habilitado y validando
- [x] Reglas de negocio implementadas
- [x] Manejo de errores de negocio implementado
- [x] Swagger/OpenAPI habilitado y accesible
- [x] Endpoint nuevo implementado
- [x] Tests del endpoint nuevo en verde
- [x] Pruebas pasando (`mvn test`)
- [x] YAML contract-first completo con todas las operaciones existentes
- [x] Interfaces generadas desde Maven con OpenAPI Generator
- [x] Controladores implementan interfaces generadas sin cambiar rutas ni JSON
- [x] Incremento JPA con 2 entidades relacionadas
- [x] APIs CRUD de categorias y productos implementadas
- [x] Pruebas del incremento JPA en verde
