package co.unicauca.solid.gestionusuarios.domain.security;

/**
 * Abstracción para el cifrado/verificación de contraseñas.
 * <p>
 * DIP: UserService depende de esta interfaz, no de un algoritmo concreto.
 * Esto permite cambiar el algoritmo de hashing (Argon2, BCrypt, etc.) sin
 * modificar la lógica de negocio.
 *
 * @author Grupo Taller 2 - SOLID
 */
public interface IPasswordHasher {

    /**
     * Genera el hash cifrado de una contraseña en texto plano.
     *
     * @param plainPassword contraseña en texto plano
     * @return hash cifrado listo para persistir en la base de datos
     */
    String hash(String plainPassword);

    /**
     * Verifica que una contraseña en texto plano corresponda al hash guardado.
     *
     * @param plainPassword contraseña en texto plano ingresada por el usuario
     * @param hash          hash almacenado en la base de datos
     * @return true si coinciden
     */
    boolean verify(String plainPassword, String hash);
}
