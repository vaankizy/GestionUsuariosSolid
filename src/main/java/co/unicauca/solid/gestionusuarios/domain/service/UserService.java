package co.unicauca.solid.gestionusuarios.domain.service;

import co.unicauca.solid.gestionusuarios.domain.EstadoUsuario;
import co.unicauca.solid.gestionusuarios.domain.Role;
import co.unicauca.solid.gestionusuarios.domain.User;
import co.unicauca.solid.gestionusuarios.domain.access.IUserRepository;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordHasher;
import co.unicauca.solid.gestionusuarios.domain.security.IPasswordValidator;
import co.unicauca.solid.gestionusuarios.domain.security.PasswordValidationResult;
import java.util.List;
import java.util.Optional;

/**
 * Lógica de negocio de la gestión de usuarios.
 * <p>
 * Principio de Inversión de Dependencias (DIP): UserService NO conoce
 * implementaciones concretas. Solo depende de las abstracciones
 * IUserRepository, IPasswordHasher e IPasswordValidator, que se inyectan
 * por constructor (igual que en el ejemplo 5: Service(IProductRepository)).
 * <p>
 * Esto permite, por ejemplo, hacer pruebas unitarias usando un repositorio
 * falso en memoria (InMemoryUserRepository) sin tocar SQLite.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class UserService implements IUserService {

    private final IUserRepository repository;
    private final IPasswordHasher passwordHasher;
    private final IPasswordValidator passwordValidator;

    public UserService(IUserRepository repository,
                        IPasswordHasher passwordHasher,
                        IPasswordValidator passwordValidator) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public RegisterResult register(String username, String fullName, Role role,
                                    EstadoUsuario estado, String plainPassword) {

        if (username == null || username.isBlank()) {
            return RegisterResult.failure("El nombre de usuario (login) es obligatorio.");
        }
        if (fullName == null || fullName.isBlank()) {
            return RegisterResult.failure("El nombre completo es obligatorio.");
        }
        if (role == null) {
            return RegisterResult.failure("Debe seleccionar un rol.");
        }
        if (estado == null) {
            return RegisterResult.failure("Debe seleccionar un estado.");
        }
        if (repository.existsByUsername(username)) {
            return RegisterResult.failure("Ya existe un usuario registrado con ese nombre de usuario.");
        }

        PasswordValidationResult passwordCheck = passwordValidator.validate(plainPassword);
        if (!passwordCheck.isValid()) {
            return RegisterResult.failure(passwordCheck.getMessage());
        }

        String hashedPassword = passwordHasher.hash(plainPassword);
        User newUser = new User(username, fullName, role, estado, hashedPassword);

        boolean saved = repository.save(newUser);
        if (!saved) {
            return RegisterResult.failure("No fue posible guardar el usuario en la base de datos.");
        }

        return RegisterResult.success();
    }

    @Override
    public AuthResult login(String username, String plainPassword) {
        if (username == null || username.isBlank() || plainPassword == null || plainPassword.isBlank()) {
            return AuthResult.failure("Debe ingresar usuario y contraseña.");
        }

        Optional<User> maybeUser = repository.findByUsername(username);
        if (maybeUser.isEmpty()) {
            return AuthResult.failure("El usuario no existe.");
        }

        User user = maybeUser.get();

        if (!user.isActivo()) {
            return AuthResult.failure("El usuario se encuentra inactivo. Contacte al administrador.");
        }

        boolean matches = passwordHasher.verify(plainPassword, user.getPasswordHash());
        if (!matches) {
            return AuthResult.failure("La contraseña es incorrecta.");
        }

        return AuthResult.success(user);
    }

    @Override
    public List<User> listUsers() {
        return repository.list();
    }
}
