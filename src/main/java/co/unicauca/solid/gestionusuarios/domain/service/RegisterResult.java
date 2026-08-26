package co.unicauca.solid.gestionusuarios.domain.service;

/**
 * Resultado inmutable de intentar registrar un usuario.
 *
 * @author Grupo Taller 2 - SOLID
 */
public final class RegisterResult {

    private final boolean success;
    private final String message;

    private RegisterResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static RegisterResult success() {
        return new RegisterResult(true, "Usuario registrado correctamente.");
    }

    public static RegisterResult failure(String reason) {
        return new RegisterResult(false, reason);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
