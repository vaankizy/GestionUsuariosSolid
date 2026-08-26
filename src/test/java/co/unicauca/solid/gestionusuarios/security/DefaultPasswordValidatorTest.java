package co.unicauca.solid.gestionusuarios.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.unicauca.solid.gestionusuarios.domain.security.DefaultPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.security.PasswordValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias de la clase de dominio DefaultPasswordValidator.
 *
 * @author Grupo Taller 2 - SOLID
 */
class DefaultPasswordValidatorTest {

    private IPasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new DefaultPasswordValidator();
    }

    @Test
    @DisplayName("Contraseña válida con mayúscula, dígito, especial y longitud >= 6")
    void validPasswordShouldPass() {
        PasswordValidationResult result = validator.validate("Clave1!");
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Contraseña muy corta debe ser rechazada")
    void tooShortPasswordShouldFail() {
        PasswordValidationResult result = validator.validate("A1!ab");
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Contraseña sin dígito debe ser rechazada")
    void passwordWithoutDigitShouldFail() {
        PasswordValidationResult result = validator.validate("Clavee!");
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Contraseña sin mayúscula debe ser rechazada")
    void passwordWithoutUpperCaseShouldFail() {
        PasswordValidationResult result = validator.validate("clave1!");
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Contraseña sin carácter especial debe ser rechazada")
    void passwordWithoutSpecialCharShouldFail() {
        PasswordValidationResult result = validator.validate("Clave12");
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Contraseña nula o vacía debe ser rechazada")
    void nullOrBlankPasswordShouldFail() {
        assertFalse(validator.validate(null).isValid());
        assertFalse(validator.validate("   ").isValid());
    }
}
