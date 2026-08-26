package co.unicauca.solid.gestionusuarios.domain.access;

import co.unicauca.solid.gestionusuarios.domain.EstadoUsuario;
import co.unicauca.solid.gestionusuarios.domain.Role;
import co.unicauca.solid.gestionusuarios.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación concreta de IUserRepository usando SQLite (JDBC).
 * <p>
 * Es libre de decidir CÓMO se persiste (archivo físico o en memoria),
 * el resto de la aplicación solo conoce la abstracción IUserRepository
 * (Inversión de Dependencias), tal como en el ejemplo 5 de la teoría
 * (ProductRepository implements IProductRepository).
 *
 * @author Grupo Taller 2 - SOLID
 */
public class SQLiteUserRepository implements IUserRepository {

    private static final Logger LOGGER = Logger.getLogger(SQLiteUserRepository.class.getName());

    private final String jdbcUrl;
    private Connection conn;

    /**
     * @param jdbcUrl cadena de conexión JDBC de SQLite. Ejemplos:
     *                "jdbc:sqlite:gestionusuarios.db" (archivo físico)
     *                "jdbc:sqlite::memory:" (en memoria)
     */
    public SQLiteUserRepository(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        connect();
        initDatabase();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "No fue posible conectar a la base de datos SQLite", ex);
        }
    }

    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (\n"
                + "  Username TEXT PRIMARY KEY,\n"
                + "  NombreCompleto TEXT NOT NULL,\n"
                + "  Rol TEXT NOT NULL,\n"
                + "  Estado TEXT NOT NULL,\n"
                + "  PasswordHash TEXT NOT NULL\n"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "No fue posible crear la tabla Usuario", ex);
        }
    }

    @Override
    public boolean save(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isBlank()) {
            return false;
        }
        String sql = "INSERT INTO Usuario (Username, NombreCompleto, Rol, Estado, PasswordHash) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getRole().name());
            pstmt.setString(4, user.getEstado().name());
            pstmt.setString(5, user.getPasswordHash());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "No fue posible guardar el usuario", ex);
            return false;
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT Username, NombreCompleto, Rol, Estado, PasswordHash "
                + "FROM Usuario WHERE Username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "No fue posible buscar el usuario", ex);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public List<User> list() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT Username, NombreCompleto, Rol, Estado, PasswordHash FROM Usuario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "No fue posible listar los usuarios", ex);
        }
        return users;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("Username"));
        user.setFullName(rs.getString("NombreCompleto"));
        user.setRole(Role.fromNombre(rs.getString("Rol")));
        user.setEstado(EstadoUsuario.fromNombre(rs.getString("Estado")));
        user.setPasswordHash(rs.getString("PasswordHash"));
        return user;
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Error cerrando la conexión", ex);
        }
    }
}
