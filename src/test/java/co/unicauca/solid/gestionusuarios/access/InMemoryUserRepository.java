package co.unicauca.solid.gestionusuarios.access;

import co.unicauca.solid.gestionusuarios.domain.User;
import co.unicauca.solid.gestionusuarios.domain.access.IUserRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación en memoria de IUserRepository, usada SOLO para pruebas
 * unitarias.
 * <p>
 * Esta clase es la mejor demostración práctica del beneficio del Principio
 * de Inversión de Dependencias (DIP): como UserService solo depende de la
 * interfaz IUserRepository, podemos probar toda la lógica de negocio
 * (UserService) sin necesitar una base de datos SQLite real, sin acceso a
 * disco y sin que las pruebas sean lentas o frágiles.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class InMemoryUserRepository implements IUserRepository {

    private final Map<String, User> storage = new LinkedHashMap<>();

    /** Si es true, save() siempre falla (simula un error de base de datos). */
    private boolean forceSaveFailure = false;

    @Override
    public boolean save(User user) {
        if (forceSaveFailure) {
            return false;
        }
        if (user == null || user.getUsername() == null || user.getUsername().isBlank()) {
            return false;
        }
        storage.put(user.getUsername(), user);
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(storage.get(username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return storage.containsKey(username);
    }

    @Override
    public List<User> list() {
        return new ArrayList<>(storage.values());
    }

    public void setForceSaveFailure(boolean forceSaveFailure) {
        this.forceSaveFailure = forceSaveFailure;
    }

    public int size() {
        return storage.size();
    }
}
