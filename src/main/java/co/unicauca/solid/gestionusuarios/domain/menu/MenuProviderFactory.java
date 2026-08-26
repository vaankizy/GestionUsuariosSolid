package co.unicauca.solid.gestionusuarios.domain.menu;

import co.unicauca.solid.gestionusuarios.domain.Role;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Fábrica que entrega el IMenuOptionsProvider correspondiente a un Role.
 * <p>
 * Se usa un mapa de proveedores (Strategy pattern) en lugar de un switch
 * gigante en la interfaz gráfica: si mañana se agrega un nuevo rol, solo
 * se registra una entrada nueva aquí (Open/Closed Principle), sin tocar
 * LoginFrame ni DashboardFrame.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class MenuProviderFactory {

    private static final Map<Role, Supplier<IMenuOptionsProvider>> PROVIDERS = new EnumMap<>(Role.class);

    static {
        PROVIDERS.put(Role.ADMINISTRADOR, AdministradorMenuProvider::new);
        PROVIDERS.put(Role.AUTOR_PREGUNTAS, AutorPreguntasMenuProvider::new);
        PROVIDERS.put(Role.REVISOR, RevisorMenuProvider::new);
        PROVIDERS.put(Role.DOCENTE, DocenteMenuProvider::new);
        PROVIDERS.put(Role.ESTUDIANTE, EstudianteMenuProvider::new);
    }

    private MenuProviderFactory() {
    }

    public static IMenuOptionsProvider getProvider(Role role) {
        Supplier<IMenuOptionsProvider> supplier = PROVIDERS.get(role);
        if (supplier == null) {
            throw new IllegalArgumentException("No hay un menú configurado para el rol: " + role);
        }
        return supplier.get();
    }
}
