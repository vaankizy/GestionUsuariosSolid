package co.unicauca.solid.gestionusuarios.domain;

/**
 * Roles disponibles para un usuario del sistema.
 *
 * @author Grupo Taller 2 - SOLID
 */
public enum Role {

    ADMINISTRADOR("Administrador"),
    AUTOR_PREGUNTAS("Autor de preguntas"),
    REVISOR("Revisor"),
    DOCENTE("Docente"),
    ESTUDIANTE("Estudiante");

    private final String etiqueta;

    Role(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Permite obtener el enum a partir del texto guardado en la base de datos.
     *
     * @param nombre nombre del enum (name())
     * @return el Role correspondiente
     */
    public static Role fromNombre(String nombre) {
        return Role.valueOf(nombre);
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
