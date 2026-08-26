package co.unicauca.solid.gestionusuarios.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.unicauca.solid.gestionusuarios.domain.security.Argon2PasswordHasher;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias de Argon2PasswordHasher.
 * <p>
 * Estas pruebas usan la implementación REAL de Argon2 (no un fake), por lo
 * que requieren que Maven pueda descargar la dependencia argon2-jvm
 * (ejecutar con acceso a internet: {@code mvn test}).
 *
 * @author Grupo Taller 2 - SOLID
 */
class Argon2PasswordHasherTest {

    private IPasswordHasher hasher;

    @BeforeEach
    void setUp() {
        hasher = new Argon2PasswordHasher();
    }

    @Test
    @DisplayName("El hash generado nunca debe ser igual a la contraseña en texto plano")
    void hashShouldNotEqualPlainPassword() {
        String plain = "Clave1!";
        String hashed = hasher.hash(plain);
        assertNotEquals(plain, hashed);
    }

    @Test
    @DisplayName("verify() debe retornar true cuando la contraseña coincide con el hash")
    void verifyShouldReturnTrueForMatchingPassword() {
        String plain = "Clave1!";
        String hashed = hasher.hash(plain);
        assertTrue(hasher.verify(plain, hashed));
    }

    @Test
    @DisplayName("verify() debe retornar false cuando la contraseña NO coincide con el hash")
    void verifyShouldReturnFalseForWrongPassword() {
        String hashed = hasher.hash("Clave1!");
        assertFalse(hasher.verify("OtraClave2@", hashed));
    }

    @Test
    @DisplayName("Dos hashes de la misma contraseña deben ser distintos (uso de salt aleatorio)")
    void hashingSamePasswordTwiceShouldProduceDifferentHashes() {
        String plain = "Clave1!";
        String hash1 = hasher.hash(plain);
        String hash2 = hasher.hash(plain);
        assertNotEquals(hash1, hash2);
    }
}
