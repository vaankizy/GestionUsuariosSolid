package co.unicauca.solid.gestionusuarios.domain.access;

import co.unicauca.solid.gestionusuarios.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * Abstracción (contrato) de la persistencia de usuarios.
 * <p>
 * Principio de Inversión de Dependencias (DIP): las capas superiores
 * (UserService) dependen de esta interfaz y NO de una implementación
 * concreta como SQLiteUserRepository. Así, la tecnología de persistencia
 * se puede cambiar (SQLite, memoria, otra BD) sin modificar el servicio.
 *
 * @author Grupo Taller 2 - SOLID
 */
public interface IUserRepository {

    /**
     * Guarda un nuevo usuario.
     *
     * @param user usuario a guardar (ya debe traer la contraseña cifrada)
     * @return true si se guardó correctamente
     */
    boolean save(User user);

    /**
     * Busca un usuario por su nombre de usuario (login).
     *
     * @param username login del usuario
     * @return el usuario envuelto en Optional, o Optional.empty() si no existe
     */
    Optional<User> findByUsername(String username);

    /**
     * Indica si ya existe un usuario registrado con ese username.
     *
     * @param username login a verificar
     * @return true si ya existe
     */
    boolean existsByUsername(String username);

    /**
     * Lista todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    List<User> list();
}
