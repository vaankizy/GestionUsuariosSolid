package co.unicauca.solid.gestionusuarios.domain.service;

import co.unicauca.solid.gestionusuarios.domain.User;

/**
 * Resultado inmutable de un intento de inicio de sesión.
 *
 * @author Grupo Taller 2 - SOLID
 */
public final class AuthResult {

    private final boolean success;
    private final String message;
    private final User user;

    private AuthResult(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public static AuthResult success(User user) {
        return new AuthResult(true, "Inicio de sesión exitoso.", user);
    }

    public static AuthResult failure(String reason) {
        return new AuthResult(false, reason, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
