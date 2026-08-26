package co.unicauca.solid.gestionusuarios.domain.menu;

import java.util.List;

/**
 * @author Grupo Taller 2 - SOLID
 */
public class RevisorMenuProvider implements IMenuOptionsProvider {

    @Override
    public String getTitulo() {
        return "Panel de Revisor";
    }

    @Override
    public List<String> getOpciones() {
        return List.of(
                "Revisar preguntas pendientes",
                "Aprobar o rechazar preguntas",
                "Ver historial de revisiones"
        );
    }
}
