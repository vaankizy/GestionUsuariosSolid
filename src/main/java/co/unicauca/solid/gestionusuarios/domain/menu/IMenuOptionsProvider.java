package co.unicauca.solid.gestionusuarios.domain.menu;

import java.util.List;

/**
 * Abstracción del proveedor de opciones de menú/tablero para un rol.
 * <p>
 * Principio Abierto/Cerrado (OCP): para agregar un nuevo rol con su propio
 * menú, se crea una nueva clase que implemente esta interfaz y se registra
 * en MenuProviderFactory; NO es necesario modificar las clases de menú ya
 * existentes ni el código de la interfaz gráfica que las consume.
 *
 * @author Grupo Taller 2 - SOLID
 */
public interface IMenuOptionsProvider {

    /**
     * @return título de bienvenida / cabecera del tablero para este rol.
     */
    String getTitulo();

    /**
     * @return lista de opciones de menú disponibles para este rol.
     */
    List<String> getOpciones();
}
