package co.unicauca.solid.gestionusuarios.ui;

import co.unicauca.solid.gestionusuarios.domain.EstadoUsuario;
import co.unicauca.solid.gestionusuarios.domain.Role;
import co.unicauca.solid.gestionusuarios.domain.service.IUserService;
import co.unicauca.solid.gestionusuarios.domain.service.RegisterResult;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana de registro de un nuevo usuario del sistema.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class RegisterFrame extends JFrame {

    private final IUserService userService;
    private final LoginFrame loginFrame;

    private JTextField txtUsername;
    private JTextField txtFullName;
    private JComboBox<Role> cmbRole;
    private JComboBox<EstadoUsuario> cmbEstado;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;

    public RegisterFrame(IUserService userService, LoginFrame loginFrame) {
        super("Gestión de Usuarios - Registro");
        this.userService = userService;
        this.loginFrame = loginFrame;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        JLabel lblTitle = new JLabel("Registro de usuario", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Usuario (login):"), gbc);
        txtUsername = new JTextField(16);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Nombre completo:"), gbc);
        txtFullName = new JTextField(16);
        gbc.gridx = 1;
        panel.add(txtFullName, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Rol:"), gbc);
        cmbRole = new JComboBox<>(Role.values());
        gbc.gridx = 1;
        panel.add(cmbRole, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Estado:"), gbc);
        cmbEstado = new JComboBox<>(EstadoUsuario.values());
        gbc.gridx = 1;
        panel.add(cmbEstado, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField(16);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Confirmar contraseña:"), gbc);
        txtConfirmPassword = new JPasswordField(16);
        gbc.gridx = 1;
        panel.add(txtConfirmPassword, gbc);
        row++;

        JLabel lblHint = new JLabel("<html><i>Mínimo 6 caracteres, con mayúscula,<br>"
                + "dígito y carácter especial.</i></html>");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(lblHint, gbc);
        row++;
        gbc.gridwidth = 1;

        JButton btnRegister = new JButton("Registrar");
        btnRegister.addActionListener(e -> onRegister());
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(btnRegister, gbc);
        row++;

        JButton btnBack = new JButton("Volver a iniciar sesión");
        btnBack.addActionListener(e -> onBackToLogin());
        gbc.gridy = row;
        panel.add(btnBack, gbc);

        add(panel);
    }

    private void onRegister() {
        String username = txtUsername.getText().trim();
        String fullName = txtFullName.getText().trim();
        Role role = (Role) cmbRole.getSelectedItem();
        EstadoUsuario estado = (EstadoUsuario) cmbEstado.getSelectedItem();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RegisterResult result = userService.register(username, fullName, role, estado, password);

        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(this, result.getMessage(),
                    "No fue posible registrar el usuario", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, result.getMessage(),
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
        onBackToLogin();
    }

    private void onBackToLogin() {
        loginFrame.setVisible(true);
        this.dispose();
    }
}
