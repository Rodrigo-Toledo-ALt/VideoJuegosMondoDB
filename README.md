# API REST de Gestión de Videojuegos con Spring Boot y MongoDB

Este proyecto implementa una API REST completa para la gestión de videojuegos, desarrolladores, géneros y valoraciones de usuarios, utilizando Spring Boot y MongoDB como base de datos.

## Características principales

- **Autenticación JWT**: Sistema de autenticación seguro con tokens JWT
- **Roles y permisos**: Diferentes niveles de acceso (USER y ADMIN)
- **Operaciones CRUD**: Para todas las entidades del sistema
- **Validaciones**: Validaciones de datos en todas las entidades
- **Manejo de excepciones**: Sistema robusto de manejo de errores
- **Búsqueda y filtrado**: Operaciones de búsqueda y filtrado avanzadas
- **Documentación**: Código bien documentado

## Tecnologías utilizadas

- **Spring Boot 3.5.0**: Framework de desarrollo
- **Spring Security**: Seguridad y autenticación
- **Spring Data MongoDB**: Acceso a datos MongoDB
- **JWT (JSON Web Tokens)**: Autenticación stateless
- **Lombok**: Reducción de código boilerplate
- **Bean Validation**: Validación de datos
- **Java 17**: Versión de Java

## Estructura del proyecto

El proyecto sigue una arquitectura en capas:

- **Model**: Entidades MongoDB (documentos)
- **Repository**: Interfaces para acceso a datos
- **Service**: Lógica de negocio
- **Controller**: Endpoints REST
- **DTO**: Objetos de transferencia de datos
- **Config**: Configuración de seguridad y MongoDB
- **Exception**: Manejo de excepciones
- **Util**: Clases de utilidad

## Entidades principales

- **Usuario**: Usuarios del sistema con roles (USER/ADMIN)
- **Videojuego**: Información de videojuegos
- **Género**: Categorías de videojuegos
- **Desarrollador**: Empresas desarrolladoras
- **Valoración**: Puntuaciones y comentarios de usuarios

## Requisitos previos

- Java 17 o superior
- MongoDB 4.4 o superior
- Maven 3.6 o superior

## Configuración e instalación

1. **Clonar el repositorio**:
   ```bash
   git clone <url-del-repositorio>
   cd VideoJuegosMondoDB
   ```

2. **Configurar MongoDB**:
   - Asegúrate de tener MongoDB instalado y ejecutándose en el puerto 27017
   - La base de datos se creará automáticamente al iniciar la aplicación
   - No es necesario crear manualmente las colecciones

3. **Compilar el proyecto**:
   ```bash
   ./mvnw clean package
   ```

4. **Ejecutar la aplicación**:
   ```bash
   ./mvnw spring-boot:run
   ```
   
   o

   ```bash
   java -jar target/VideoJuegosMondoDB-0.0.1-SNAPSHOT.jar
   ```

5. **Acceder a la API**:
   - La API estará disponible en `http://localhost:8080`

## Endpoints principales

### Autenticación

- `POST /auth/login`: Iniciar sesión con email y contraseña
- `POST /auth/register`: Registrar nuevo usuario (rol USER por defecto)

### Usuarios (solo ADMIN)

- `GET /usuarios`: Listar todos los usuarios
- `GET /usuarios/{id}`: Obtener usuario por ID
- `POST /usuarios`: Crear usuario
- `PUT /usuarios/{id}`: Actualizar usuario
- `DELETE /usuarios/{id}`: Eliminar usuario

### Videojuegos

- `GET /videojuegos`: Listar todos (público)
- `GET /videojuegos/{id}`: Obtener por ID (público)
- `GET /videojuegos/buscar?titulo={titulo}`: Buscar por título
- `GET /videojuegos/filtrar?genero={genero}&plataforma={plataforma}&desarrollador={dev}`: Filtrar
- `POST /videojuegos`: Crear (solo ADMIN)
- `PUT /videojuegos/{id}`: Actualizar (solo ADMIN)
- `DELETE /videojuegos/{id}`: Eliminar (solo ADMIN)

### Géneros

- `GET /generos`: Listar todos
- `GET /generos/{id}`: Obtener por ID
- `POST /generos`: Crear (solo ADMIN)
- `PUT /generos/{id}`: Actualizar (solo ADMIN)
- `DELETE /generos/{id}`: Eliminar (solo ADMIN)

### Desarrolladores

- `GET /desarrolladores`: Listar todos
- `GET /desarrolladores/{id}`: Obtener por ID
- `POST /desarrolladores`: Crear (solo ADMIN)
- `PUT /desarrolladores/{id}`: Actualizar (solo ADMIN)
- `DELETE /desarrolladores/{id}`: Eliminar (solo ADMIN)

### Valoraciones

- `POST /valoraciones`: Crear valoración (usuarios autenticados)
- `GET /valoraciones/videojuego/{id}`: Obtener valoraciones por videojuego

## Autenticación

Para acceder a endpoints protegidos, debes incluir el token JWT en el header `Authorization`:

```
Authorization: Bearer <token>
```

Puedes obtener un token mediante el endpoint `/auth/login`.

## Usuarios por defecto

La base de datos ya incluye dos usuarios para pruebas:

- **Admin**: 
  - Email: `admin@videojuegos.com`
  - Contraseña: `admin123`
  - Rol: ADMIN

- **Usuario normal**:
  - Email: `juan@email.com`
  - Contraseña: `password123`
  - Rol: USER

## Ejemplos de uso

### Iniciar sesión (obtener token)

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@videojuegos.com", "password": "admin123"}'
```

### Listar videojuegos (público)

```bash
curl http://localhost:8080/videojuegos
```

### Crear un videojuego (requiere token de ADMIN)

```bash
curl -X POST http://localhost:8080/videojuegos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "titulo": "The Last of Us Part II",
    "desarrolladorId": "60d21b4668b0e754e8786e51",
    "generoId": "60d21b4668b0e754e8786e52",
    "plataforma": "PlayStation 4",
    "fechaLanzamiento": "2020-06-19",
    "calificacionPEGI": "18+"
  }'
```

## Contribuir

Si deseas contribuir a este proyecto, por favor:

1. Haz un fork del repositorio
2. Crea una rama para tu característica (`git checkout -b feature/nueva-caracteristica`)
3. Realiza tus cambios y haz commit (`git commit -m 'Añadir nueva característica'`)
4. Sube los cambios (`git push origin feature/nueva-caracteristica`)
5. Abre un Pull Request

## Licencia

Este proyecto está licenciado bajo [MIT License](LICENSE).