package co.unicauca.solid.gestionusuarios.domain.security;

/**
 * Resultado inmutable de validar una contraseña.
 *
 * @author Grupo Taller 2 - SOLID
 */
public final class PasswordValidationResult {

    private final boolean valid;
    private final String message;

    private PasswordValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static PasswordValidationResult ok() {
        return new PasswordValidationResult(true, "La contraseña es válida.");
    }

    public static PasswordValidationResult invalid(String reason) {
        return new PasswordValidationResult(false, reason);
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }
}
