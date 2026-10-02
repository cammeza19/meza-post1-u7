# Post-contenido Unidad 7: Patrones Arquitectónicos I

## Descripción del Proyecto
Backend para un sistema de gestión de multas de biblioteca universitario desarrollado en Spring Boot, Spring Data JPA y base de datos H2 en memoria.

## Parte 1: Arquitectura en Capas
Se implementó una arquitectura en capas tradicional con separación estricta de responsabilidades:
- **`controller/`**: Capa de Presentación con endpoints REST y manejo global de excepciones.
- **`service/`**: Capa de Aplicación que orquesta las reglas de negocio y transacciones.
- **`model/`**: Capa de Dominio con la entidad JPA, enumeraciones y reglas puras.
- **`repository/`**: Capa de Infraestructura con consultas derivadas en Spring Data JPA.

## Decisiones de Diseño (Parte 1)

- **Punto de decisión 1 — Cálculo del monto:** La regla `Multa.calcularMonto` vive en la propia entidad `Multa` porque solo depende de los días de atraso y no necesita colaboradores externos (evitando un modelo anémico).

- **Punto de decisión 2 — Conteo de multas pendientes:** Se resolvió con `countByEstudianteIdAndEstado` directamente en la base de datos para no degradar el rendimiento trayendo historiales largos a la memoria de Java.

## Evidencia de Endpoints Probados

- `GET /api/multas` - Listar vacío (`200 OK`)
- `POST /api/multas` - Generar multa calculada (`201 Created`)
- `POST /api/multas` - Error de validación DTO (`400 Bad Request`)
- `POST /api/multas` - Límite de multas pendientes superado (`409 Conflict`)
- `GET /api/multas/{id}` - Multa inexistente (`404 Not Found`)
- `PATCH /api/multas/{id}/pagar` - Pago en ventanilla (`200 OK`)

## Instrucciones de Ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/cammeza19/meza-post1-u7.git
   cd meza-post1-u7/multas-biblioteca-api

2. Ejecutar la aplicación:

   ```bash
   ./mvnw spring-boot:run
   ```

3. La API estará disponible en:

   ```bash
   http://localhost:8080/api/multas
   ```


4. La consola H2 estará disponible en:

   ```bash
   http://localhost:8080/h2-console
   ```

### Estructura de Paquetes
```text
multas-biblioteca-api/
└── src/main/java/com/example/multas/
    ├── controller/
    │   ├── MultaController.java
    │   └── GlobalExceptionHandler.java
    ├── service/
    │   └── MultaService.java
    ├── model/
    │   ├── Multa.java
    │   ├── EstadoMulta.java
    │   ├── MultaNotFoundException.java
    │   ├── LimiteMultasPendientesException.java
    │   └── MultaYaPagadaException.java
    └── repository/
        └── MultaRepository.java
