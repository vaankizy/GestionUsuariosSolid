package co.unicauca.solid.gestionusuarios.domain.menu;

import java.util.List;

/**
 * @author Grupo Taller 2 - SOLID
 */
public class DocenteMenuProvider implements IMenuOptionsProvider {

    @Override
    public String getTitulo() {
        return "Panel de Docente";
    }

    @Override
    public List<String> getOpciones() {
        return List.of(
                "Crear evaluación",
                "Ver resultados de estudiantes",
                "Gestionar mis cursos"
        );
    }
}
