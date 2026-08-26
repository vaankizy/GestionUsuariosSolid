package co.unicauca.solid.gestionusuarios.ui;

import co.unicauca.solid.gestionusuarios.domain.User;
import co.unicauca.solid.gestionusuarios.domain.menu.IMenuOptionsProvider;
import co.unicauca.solid.gestionusuarios.domain.service.IUserService;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Tablero/menú que se muestra después de iniciar sesión. Las opciones que
 * aparecen dependen del rol del usuario autenticado, gracias al
 * IMenuOptionsProvider inyectado (Strategy pattern + OCP).
 *
 * @author Grupo Taller 2 - SOLID
 */
public class DashboardFrame extends JFrame {

    private final User user;
    private final IMenuOptionsProvider menuProvider;
    private final IUserService userService;

    public DashboardFrame(User user, IMenuOptionsProvider menuProvider, IUserService userService) {
        super("Gestión de Usuarios - Tablero");
        this.user = user;
        this.menuProvider = menuProvider;
        this.userService = userService;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel lblWelcome = new JLabel(
                "<html><h2>" + menuProvider.getTitulo() + "</h2>"
                        + "Bienvenido(a), " + user.getFullName()
                        + " (" + user.getRole().getEtiqueta() + ")</html>",
                SwingConstants.CENTER);
        mainPanel.add(lblWelcome, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        for (String opcion : menuProvider.getOpciones()) {
            JButton btnOpcion = new JButton(opcion);
            btnOpcion.addActionListener(e -> JOptionPane.showMessageDialog(this,
                    "Funcionalidad \"" + opcion + "\" fuera del alcance de este taller.",
                    "Opción de menú", JOptionPane.INFORMATION_MESSAGE));
            optionsPanel.add(btnOpcion);
        }
        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        JButton btnLogout = new JButton("Cerrar sesión");
        btnLogout.addActionListener(e -> onLogout());
        mainPanel.add(btnLogout, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void onLogout() {
        LoginFrame loginFrame = new LoginFrame(userService);
        loginFrame.setVisible(true);
        this.dispose();
    }
}
