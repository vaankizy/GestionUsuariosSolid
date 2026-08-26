package co.unicauca.solid.gestionusuarios.domain.security;

/**
 * Abstracción para validar las reglas de formato de una contraseña.
 * <p>
 * Separar esta responsabilidad en su propia interfaz (SRP) permite que
 * UserService no conozca las reglas concretas, y que dichas reglas se
 * puedan reemplazar o extender sin modificar UserService (OCP + DIP).
 *
 * @author Grupo Taller 2 - SOLID
 */
public interface IPasswordValidator {

    /**
     * Valida el formato de una contraseña en texto plano.
     *
     * @param plainPassword contraseña a validar
     * @return resultado con el detalle de la validación
     */
    PasswordValidationResult validate(String plainPassword);
}
