package co.unicauca.solid.gestionusuarios;

import co.unicauca.solid.gestionusuarios.domain.access.IUserRepository;
import co.unicauca.solid.gestionusuarios.domain.access.RepositoryFactory;
import co.unicauca.solid.gestionusuarios.domain.security.Argon2PasswordHasher;
import co.unicauca.solid.gestionusuarios.domain.security.DefaultPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordHasher;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.service.IUserService;
import co.unicauca.solid.gestionusuarios.domain.service.UserService;
import co.unicauca.solid.gestionusuarios.ui.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación ("composition root").
 * <p>
 * Aquí, y solo aquí, se conocen y se instancian las implementaciones
 * concretas (SQLiteUserRepository vía Factory, Argon2PasswordHasher,
 * DefaultPasswordValidator). El resto del sistema (UserService, la UI)
 * solo trabaja con abstracciones (IUserRepository, IPasswordHasher,
 * IPasswordValidator, IUserService), cumpliendo el Principio de
 * Inversión de Dependencias.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class MainApp {

    public static void main(String[] args) {

        // 1. Se obtiene la implementación concreta de persistencia a través
        //    de la Factory (igual patrón que el ejemplo 5 de la teoría).
        IUserRepository repository = RepositoryFactory.getInstance().getRepository("sqlite-file");

        // 2. Se eligen las implementaciones concretas de seguridad.
        IPasswordHasher passwordHasher = new Argon2PasswordHasher();
        IPasswordValidator passwordValidator = new DefaultPasswordValidator();

        // 3. Se inyectan todas las abstracciones al servicio (constructor injection).
        IUserService userService = new UserService(repository, passwordHasher, passwordValidator);

        // 4. Se lanza la interfaz gráfica, que solo conoce IUserService.
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(userService);
            loginFrame.setVisible(true);
        });
    }
}
