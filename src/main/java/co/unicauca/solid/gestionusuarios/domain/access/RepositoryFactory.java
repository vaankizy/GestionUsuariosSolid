package co.unicauca.solid.gestionusuarios.domain.access;

/**
 * Fábrica (Singleton) que se encarga de instanciar la implementación
 * concreta de IUserRepository que se deba usar.
 * <p>
 * Sigue exactamente el mismo patrón del ejemplo 5 de Inversión de
 * Dependencias visto en la teoría (Factory.getRepository(String type)).
 * Si en el futuro se necesita otra tecnología de persistencia
 * (por ejemplo PostgreSQL), basta con agregar un nuevo "case" y una
 * nueva clase que implemente IUserRepository, sin tocar el resto del
 * sistema (Open/Closed Principle).
 *
 * @author Grupo Taller 2 - SOLID
 */
public class RepositoryFactory {

    private static RepositoryFactory instance;

    /** Ruta por defecto del archivo físico de la base de datos. */
    private static final String DEFAULT_DB_FILE = "gestionusuarios.db";

    private RepositoryFactory() {
    }

    public static RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new RepositoryFactory();
        }
        return instance;
    }

    /**
     * Crea una instancia concreta de la jerarquía IUserRepository.
     *
     * @param type "sqlite-file" para persistencia en archivo físico,
     *             "sqlite-memory" para persistencia en memoria (útil en pruebas)
     * @return una clase hija de la abstracción IUserRepository
     */
    public IUserRepository getRepository(String type) {
        IUserRepository result = null;

        switch (type) {
            case "sqlite-file":
                result = new SQLiteUserRepository("jdbc:sqlite:" + DEFAULT_DB_FILE);
                break;
            case "sqlite-memory":
                result = new SQLiteUserRepository("jdbc:sqlite::memory:");
                break;
            default:
                throw new IllegalArgumentException("Tipo de repositorio no soportado: " + type);
        }

        return result;
    }
}
