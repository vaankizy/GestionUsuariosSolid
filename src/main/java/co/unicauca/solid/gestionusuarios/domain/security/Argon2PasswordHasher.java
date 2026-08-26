package co.unicauca.solid.gestionusuarios.domain.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Implementación de IPasswordHasher usando el algoritmo Argon2
 * (librería argon2-jvm, ver referencia "Hashing with Argon2 in Java" de la
 * guía del taller).
 * <p>
 * Argon2 es un algoritmo de hashing diseñado específicamente para
 * contraseñas: es lento a propósito y añade "salt" automáticamente,
 * por lo que dos contraseñas iguales generan hashes distintos.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class Argon2PasswordHasher implements IPasswordHasher {

    private static final int ITERATIONS = 4;
    private static final int MEMORY_KB = 65536; // 64 MB
    private static final int PARALLELISM = 2;

    private final Argon2 argon2;

    public Argon2PasswordHasher() {
        this.argon2 = Argon2Factory.create();
    }

    @Override
    public String hash(String plainPassword) {
        char[] passwordChars = plainPassword.toCharArray();
        try {
            return argon2.hash(ITERATIONS, MEMORY_KB, PARALLELISM, passwordChars);
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }

    @Override
    public boolean verify(String plainPassword, String hash) {
        char[] passwordChars = plainPassword.toCharArray();
        try {
            return argon2.verify(hash, passwordChars);
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }
}
