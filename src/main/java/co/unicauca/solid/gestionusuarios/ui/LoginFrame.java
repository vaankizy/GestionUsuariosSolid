package co.unicauca.solid.gestionusuarios.ui;

import co.unicauca.solid.gestionusuarios.domain.menu.IMenuOptionsProvider;
import co.unicauca.solid.gestionusuarios.domain.menu.MenuProviderFactory;
import co.unicauca.solid.gestionusuarios.domain.service.AuthResult;
import co.unicauca.solid.gestionusuarios.domain.service.IUserService;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana de inicio de sesión.
 * <p>
 * La UI solo depende de la abstracción IUserService (DIP), nunca de
 * UserService ni de las clases de acceso a datos directamente.
 *
 * @author Grupo Taller 2 - SOLID
 */
public class LoginFrame extends JFrame {

    private final IUserService userService;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame(IUserService userService) {
        super("Gestión de Usuarios - Iniciar sesión");
        this.userService = userService;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Iniciar sesión", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.addActionListener(e -> onLogin());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        JButton btnRegister = new JButton("Crear una cuenta nueva");
        btnRegister.addActionListener(e -> onGoToRegister());
        gbc.gridy = 4;
        panel.add(btnRegister, gbc);

        add(panel);
    }

    private void onLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        AuthResult result = userService.login(username, password);

        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(this, result.getMessage(),
                    "No fue posible iniciar sesión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        IMenuOptionsProvider menuProvider = MenuProviderFactory.getProvider(result.getUser().getRole());
        DashboardFrame dashboard = new DashboardFrame(result.getUser(), menuProvider, userService);
        dashboard.setVisible(true);
        this.dispose();
    }

    private void onGoToRegister() {
        RegisterFrame registerFrame = new RegisterFrame(userService, this);
        registerFrame.setVisible(true);
        this.setVisible(false);
    }
}
