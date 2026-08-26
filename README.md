# Taller 2 – Principios SOLID: Gestión de Usuarios del Sistema

**Programa de Ingeniería de Sistemas – Laboratorio de Ingeniería del Software II – Periodo 2‑2026**
**Universidad del Cauca**

Aplicación de escritorio monolítica en Java (Swing) que implementa el requerimiento
*"Gestión de usuarios del sistema"* del proyecto de curso, aplicando los principios **SOLID**
y siguiendo la misma estructura del **Ejemplo 5 de Inversión de Dependencias (DIP)** visto en
la clase teórica.

---

## 1. Integrantes del grupo

> ✏️ Completar antes de entregar:
- Nombre 1 – código
- Nombre 2 – código

---

## 2. Requisitos funcionales cubiertos

- **Registro de usuarios**: username (login), nombre completo, rol (Administrador, Autor de
  preguntas, Revisor, Docente, Estudiante), estado (Activo/Inactivo) y contraseña.
- **Validación de contraseña**: mínimo 6 caracteres, al menos un dígito, un carácter especial
  y una mayúscula.
- **Almacenamiento cifrado** de la contraseña (Argon2) en la base de datos.
- **Inicio de sesión** con validación de credenciales.
- **Tablero/menú según el rol** del usuario autenticado.
- **Persistencia en SQLite** (archivo físico `gestionusuarios.db` por defecto).

---

## 3. Arquitectura y aplicación de los principios SOLID

El diseño replica exactamente el patrón del **Ejemplo 5 (DIP)**:

```
Factory (singleton) ──crea──▶ IRepositorio (abstracción) ◀──implementa── RepositorioConcreto
                                        ▲
                                        │ (inyección por constructor)
                                     Service
                                        ▲
                                        │ (inyección por constructor)
                                    UI (Swing)
```

### Estructura de paquetes

```
co.unicauca.solid.gestionusuarios
 ├── domain
 │    ├── User.java, Role.java, EstadoUsuario.java        (modelo)
 │    ├── access
 │    │    ├── IUserRepository.java                        (abstracción - DIP)
 │    │    ├── SQLiteUserRepository.java                   (implementación concreta)
 │    │    └── RepositoryFactory.java                      (Factory / Singleton)
 │    ├── security
 │    │    ├── IPasswordHasher.java / Argon2PasswordHasher.java
 │    │    └── IPasswordValidator.java / DefaultPasswordValidator.java
 │    ├── service
 │    │    ├── IUserService.java / UserService.java        (lógica de negocio)
 │    │    └── AuthResult.java / RegisterResult.java
 │    └── menu
 │         ├── IMenuOptionsProvider.java                   (Strategy por rol - OCP)
 │         ├── AdministradorMenuProvider.java, ...
 │         └── MenuProviderFactory.java
 ├── ui
 │    ├── LoginFrame.java, RegisterFrame.java, DashboardFrame.java
 └── MainApp.java                                          (composition root)
```

### ¿Cómo se aplica cada principio?

| Principio | Dónde se aplica |
|---|---|
| **S – Single Responsibility** | `User` solo modela el dato. `DefaultPasswordValidator` solo valida formato. `Argon2PasswordHasher` solo cifra/verifica. `SQLiteUserRepository` solo persiste. `UserService` solo orquesta la lógica de negocio. Cada clase tiene una única razón de cambio. |
| **O – Open/Closed** | Para agregar un nuevo rol con su propio menú (`domain.menu`) solo se crea una nueva clase que implemente `IMenuOptionsProvider` y se registra en `MenuProviderFactory`; no se modifica `LoginFrame`, `DashboardFrame` ni las clases de menú existentes. Lo mismo aplica si se quisiera agregar otro tipo de repositorio (ej. PostgreSQL) en `RepositoryFactory`. |
| **L – Liskov Substitution** | Cualquier implementación de `IUserRepository` (SQLite, en memoria para pruebas) puede sustituir a otra sin romper el comportamiento esperado por `UserService`. Lo mismo con `IPasswordHasher`/`IPasswordValidator`. |
| **I – Interface Segregation** | Las interfaces son pequeñas y específicas (`IPasswordHasher` solo tiene hash/verify, `IPasswordValidator` solo validate, `IMenuOptionsProvider` solo getTitulo/getOpciones) en vez de una única interfaz gigante. |
| **D – Dependency Inversion** | `UserService` depende únicamente de las abstracciones `IUserRepository`, `IPasswordHasher` e `IPasswordValidator` (inyectadas por constructor), tal como `Service` depende de `IProductRepository` en el ejemplo 5. La UI depende de `IUserService`, no de `UserService` directamente. Las implementaciones concretas solo se conocen en `MainApp` (composition root). |

---

## 4. Pruebas unitarias

Ubicadas en `src/test/java`. Se aprovecha el DIP para probar `UserService` sin necesitar
una base de datos real:

- `InMemoryUserRepository`: doble de prueba (test double) de `IUserRepository`.
- `FakePasswordHasher`: doble de prueba de `IPasswordHasher` (pruebas rápidas de `UserService`).
- `DefaultPasswordValidatorTest`: valida las reglas de formato de contraseña.
- `Argon2PasswordHasherTest`: valida el algoritmo real de cifrado (hash/verify, salting).
- `UserServiceTest`: valida registro, duplicados, login correcto/incorrecto, usuario inactivo, etc.

Ejecutar todas las pruebas:

```bash
mvn test
```

---

## 5. Cómo ejecutar el proyecto

### Requisitos
- JDK 17 o superior.
- Maven 3.8+.
- Conexión a internet la primera vez (para descargar `sqlite-jdbc`, `argon2-jvm` y `junit-jupiter`).

### Desde IntelliJ IDEA
1. Abrir el proyecto como proyecto Maven (`File > Open` y seleccionar la carpeta que contiene `pom.xml`).
2. Esperar a que IntelliJ descargue las dependencias.
3. Ejecutar la clase `co.unicauca.solid.gestionusuarios.MainApp`.

### Desde consola
```bash
mvn clean compile
mvn exec:java
```

O generar un jar ejecutable con todas las dependencias incluidas:
```bash
mvn clean package
java -jar target/gestion-usuarios-solid.jar
```

La base de datos SQLite (`gestionusuarios.db`) se crea automáticamente en la raíz del
proyecto la primera vez que se ejecuta la aplicación.

---

## 6. Subir el proyecto a GitHub (comandos de consola)

```bash
# 1. Inicializar el repositorio local
git init

# 2. Agregar todos los archivos
git add .

# 3. Primer commit
git commit -m "Taller 2 - Gestion de usuarios aplicando principios SOLID"

# 4. Crear el repositorio en GitHub (desde la web) y luego enlazarlo
git remote add origin https://github.com/<usuario>/<nombre-repositorio>.git

# 5. Subir el código
git branch -M main
git push -u origin main
```

> Recuerda incluir un `.gitignore` para no subir `target/` ni el archivo de base de datos
> generado (`gestionusuarios.db`). Ya se incluye uno en la raíz del proyecto.

---

## 7. Informe de entrega

Recuerda entregar al docente un informe en PDF que incluya:
1. Nombres de los integrantes del grupo.
2. URL del repositorio de GitHub para clonar el proyecto.
3. Breve explicación de cómo se aplicó cada principio SOLID (puede basarse en la tabla de la
   sección 3 de este README).
4. Capturas de pantalla de la aplicación en ejecución (login, registro, tablero por rol) y de
   las pruebas unitarias pasando.

---

## 8. Referencias usadas (tomadas de la guía del taller)

- SQLite Java en IntelliJ IDEA: https://www.sqlitetutorial.net/sqlite-java/
- Hashing with Argon2 in Java: https://www.baeldung.com/java-argon2-hashing
