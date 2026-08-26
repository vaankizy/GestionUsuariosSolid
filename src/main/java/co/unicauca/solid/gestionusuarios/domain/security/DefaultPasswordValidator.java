package co.unicauca.solid.gestionusuarios.domain.security;

/**
 * Validador por defecto de contraseñas, con las reglas exigidas por la
 * guía del Taller 2:
 * - Mínimo 6 caracteres
 * - Al menos un dígito
 * - Al menos un carácter especial
 * - Al menos una mayúscula
 *
 * @author Grupo Taller 2 - SOLID
 */
public class DefaultPasswordValidator implements IPasswordValidator {

    private static final int MIN_LENGTH = 6;
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:'\",.<>/?`~\\";

    @Override
    public PasswordValidationResult validate(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            return PasswordValidationResult.invalid("La contraseña no puede estar vacía.");
        }

        if (plainPassword.length() < MIN_LENGTH) {
            return PasswordValidationResult.invalid(
                    "La contraseña debe tener al menos " + MIN_LENGTH + " caracteres.");
        }

        if (!containsDigit(plainPassword)) {
            return PasswordValidationResult.invalid("La contraseña debe incluir al menos un dígito.");
        }

        if (!containsUpperCase(plainPassword)) {
            return PasswordValidationResult.invalid("La contraseña debe incluir al menos una letra mayúscula.");
        }

        if (!containsSpecialChar(plainPassword)) {
            return PasswordValidationResult.invalid("La contraseña debe incluir al menos un carácter especial.");
        }

        return PasswordValidationResult.ok();
    }

    private boolean containsDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsUpperCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialChar(String password) {
        for (char c : password.toCharArray()) {
            if (SPECIAL_CHARS.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }
}
