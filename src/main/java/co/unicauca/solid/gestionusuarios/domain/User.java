package co.unicauca.solid.gestionusuarios.domain;

import java.util.Objects;

/**
 * Entidad de dominio que representa a un usuario del sistema.
 * Es una clase POJO simple (Single Responsibility: solo modela el dato).
 *
 * @author Grupo Taller 2 - SOLID
 */
public class User {

    private String username;
    private String fullName;
    private Role role;
    private EstadoUsuario estado;
    private String passwordHash;

    public User() {
    }

    public User(String username, String fullName, Role role, EstadoUsuario estado, String passwordHash) {
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.estado = estado;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActivo() {
        return this.estado == EstadoUsuario.ACTIVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + ", fullName='" + fullName + '\''
                + ", role=" + role
                + ", estado=" + estado
                + '}';
    }
}
