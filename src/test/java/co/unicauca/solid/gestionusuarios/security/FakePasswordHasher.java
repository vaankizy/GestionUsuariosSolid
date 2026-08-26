package co.unicauca.solid.gestionusuarios.security;

import co.unicauca.solid.gestionusuarios.domain.security.IPasswordHasher;

/**
 * Implementación falsa (fake) de IPasswordHasher usada solo en pruebas
 * unitarias de UserService.
 * <p>
 * No usa ningún algoritmo criptográfico real: simplemente antepone un
 * prefijo. Esto hace que las pruebas de UserService sean rápidas y no
 * dependan de la librería Argon2, cumpliendo el mismo espíritu de DIP:
 * UserService no sabe (ni le importa) qué implementación de IPasswordHasher
 * está usando.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class FakePasswordHasher implements IPasswordHasher {

    private static final String PREFIX = "FAKE-HASH::";

    @Override
    public String hash(String plainPassword) {
        return PREFIX + plainPassword;
    }

    @Override
    public boolean verify(String plainPassword, String hash) {
        return (PREFIX + plainPassword).equals(hash);
    }
}
