package co.unicauca.solid.gestionusuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.unicauca.solid.gestionusuarios.access.InMemoryUserRepository;
import co.unicauca.solid.gestionusuarios.domain.EstadoUsuario;
import co.unicauca.solid.gestionusuarios.domain.Role;
import co.unicauca.solid.gestionusuarios.domain.security.DefaultPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.service.AuthResult;
import co.unicauca.solid.gestionusuarios.domain.service.IUserService;
import co.unicauca.solid.gestionusuarios.domain.service.RegisterResult;
import co.unicauca.solid.gestionusuarios.domain.service.UserService;
import co.unicauca.solid.gestionusuarios.security.FakePasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias de UserService.
 * <p>
 * Gracias al Principio de Inversión de Dependencias (DIP), UserService se
 * puede probar inyectando dobles de prueba (InMemoryUserRepository,
 * FakePasswordHasher) en vez de las implementaciones reales
 * (SQLiteUserRepository, Argon2PasswordHasher). Esto hace que las pruebas
 * sean rápidas, aisladas y no dependan de una base de datos real.
 *
 * @author Grupo Taller 2 - SOLID
 */
class UserServiceTest {

    private InMemoryUserRepository repository;
    private IUserService userService;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
        FakePasswordHasher hasher = new FakePasswordHasher();
        IPasswordValidator validator = new DefaultPasswordValidator();
        userService = new UserService(repository, hasher, validator);
    }

    @Test
    @DisplayName("Registrar un usuario válido debe ser exitoso y guardarlo en el repositorio")
    void registerValidUserShouldSucceed() {
        RegisterResult result = userService.register(
                "jperez", "Juan Pérez", Role.ESTUDIANTE, EstadoUsuario.ACTIVO, "Clave1!");

        assertTrue(result.isSuccess());
        assertEquals(1, repository.size());
        assertTrue(repository.existsByUsername("jperez"));
    }

    @Test
    @DisplayName("La contraseña nunca se debe guardar en texto plano")
    void passwordShouldBeStoredHashedNotInPlainText() {
        userService.register("jperez", "Juan Pérez", Role.ESTUDIANTE, EstadoUsuario.ACTIVO, "Clave1!");

        String storedHash = repository.findByUsername("jperez").get().getPasswordHash();
        assertFalse(storedHash.equals("Clave1!"));
    }

    @Test
    @DisplayName("No se debe permitir registrar dos usuarios con el mismo username")
    void registerDuplicateUsernameShouldFail() {
        userService.register("jperez", "Juan Pérez", Role.ESTUDIANTE, EstadoUsuario.ACTIVO, "Clave1!");
        RegisterResult result = userService.register(
                "jperez", "Otro Nombre", Role.DOCENTE, EstadoUsuario.ACTIVO, "OtraClave2@");

        assertFalse(result.isSuccess());
        assertEquals(1, repository.size());
    }

    @Test
    @DisplayName("Registrar con una contraseña que no cumple el formato debe fallar")
    void registerWithInvalidPasswordShouldFail() {
        RegisterResult result = userService.register(
                "jperez", "Juan Pérez", Role.ESTUDIANTE, EstadoUsuario.ACTIVO, "abc");

        assertFalse(result.isSuccess());
        assertEquals(0, repository.size());
    }

    @Test
    @DisplayName("Registrar sin username debe fallar")
    void registerWithBlankUsernameShouldFail() {
        RegisterResult result = userService.register(
                "", "Juan Pérez", Role.ESTUDIANTE, EstadoUsuario.ACTIVO, "Clave1!");

        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("Iniciar sesión con credenciales correctas debe ser exitoso")
    void loginWithCorrectCredentialsShouldSucceed() {
        userService.register("jperez", "Juan Pérez", Role.DOCENTE, EstadoUsuario.ACTIVO, "Clave1!");

        AuthResult result = userService.login("jperez", "Clave1!");

        assertTrue(result.isSuccess());
        assertEquals(Role.DOCENTE, result.getUser().getRole());
    }

    @Test
    @DisplayName("Iniciar sesión con contraseña incorrecta debe fallar")
    void loginWithWrongPasswordShouldFail() {
        userService.register("jperez", "Juan Pérez", Role.DOCENTE, EstadoUsuario.ACTIVO, "Clave1!");

        AuthResult result = userService.login("jperez", "ClaveIncorrecta9!");

        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("Iniciar sesión con un usuario inexistente debe fallar")
    void loginWithNonExistentUserShouldFail() {
        AuthResult result = userService.login("noexiste", "Clave1!");
        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("Un usuario INACTIVO no debe poder iniciar sesión")
    void loginWithInactiveUserShouldFail() {
        userService.register("jperez", "Juan Pérez", Role.DOCENTE, EstadoUsuario.INACTIVO, "Clave1!");

        AuthResult result = userService.login("jperez", "Clave1!");

        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("listUsers() debe retornar todos los usuarios registrados")
    void listUsersShouldReturnAllRegisteredUsers() {
        userService.register("jperez", "Juan Pérez", Role.DOCENTE, EstadoUsuario.ACTIVO, "Clave1!");
        userService.register("mgomez", "María Gómez", Role.ESTUDIANTE, EstadoUsuario.ACTIVO, "Clave2@");

        assertEquals(2, userService.listUsers().size());
    }
}
