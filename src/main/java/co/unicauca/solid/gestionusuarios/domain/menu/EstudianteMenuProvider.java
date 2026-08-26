package co.unicauca.solid.gestionusuarios.domain.menu;

import java.util.List;

/**
 * @author Grupo Taller 2 - SOLID
 */
public class EstudianteMenuProvider implements IMenuOptionsProvider {

    @Override
    public String getTitulo() {
        return "Panel de Estudiante";
    }

    @Override
    public List<String> getOpciones() {
        return List.of(
                "Presentar evaluación",
                "Ver mis resultados",
                "Ver mi perfil"
        );
    }
}
