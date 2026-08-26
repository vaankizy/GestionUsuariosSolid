package co.unicauca.solid.gestionusuarios.domain.service;

import co.unicauca.solid.gestionusuarios.domain.EstadoUsuario;
import co.unicauca.solid.gestionusuarios.domain.Role;
import co.unicauca.solid.gestionusuarios.domain.User;
import java.util.List;

/**
 * Abstracción de los casos de uso de gestión de usuarios.
 * La capa de interfaz gráfica (UI) depende de esta interfaz, no de
 * UserService directamente (DIP + ISP: la UI solo ve lo que necesita).
 *
 * @author Grupo Taller 2 - SOLID
 */
public interface IUserService {

    RegisterResult register(String username, String fullName, Role role,
                             EstadoUsuario estado, String plainPassword);

    AuthResult login(String username, String plainPassword);

    List<User> listUsers();
}
