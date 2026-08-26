package co.unicauca.solid.gestionusuarios.domain.menu;

import java.util.List;

/**
 * @author Grupo Taller 2 - SOLID
 */
public class AdministradorMenuProvider implements IMenuOptionsProvider {

    @Override
    public String getTitulo() {
        return "Panel de Administrador";
    }

    @Override
    public List<String> getOpciones() {
        return List.of(
                "Gestionar usuarios del sistema",
                "Ver reportes generales",
                "Configurar parámetros del sistema"
        );
    }
}
