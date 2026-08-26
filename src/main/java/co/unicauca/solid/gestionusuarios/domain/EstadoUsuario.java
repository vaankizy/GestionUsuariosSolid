package co.unicauca.solid.gestionusuarios.domain;

/**
 * Estado de un usuario dentro del sistema.
 *
 * @author Grupo Taller 2 - SOLID
 */
public enum EstadoUsuario {

    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String etiqueta;

    EstadoUsuario(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static EstadoUsuario fromNombre(String nombre) {
        return EstadoUsuario.valueOf(nombre);
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
