package co.unicauca.solid.gestionusuarios.domain.menu;

import java.util.List;

/**
 * @author Grupo Taller 2 - SOLID
 */
public class AutorPreguntasMenuProvider implements IMenuOptionsProvider {

    @Override
    public String getTitulo() {
        return "Panel de Autor de Preguntas";
    }

    @Override
    public List<String> getOpciones() {
        return List.of(
                "Crear nueva pregunta",
                "Editar mis preguntas",
                "Ver historial de preguntas enviadas"
        );
    }
}
