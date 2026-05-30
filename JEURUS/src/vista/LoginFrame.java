package vista;

import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import modelo.Usuario;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private JButton btnSalir;

    public LoginFrame() {
        setTitle("JEURUS - Inicio de sesion");
        setSize(430, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        construirInterfaz();
    }

    private void construirInterfaz() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(41, 57, 80));

        JLabel lblTitulo = new JLabel("JEURUS", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JLabel lblSubtitulo = new JLabel("Sistema de tickets y soporte al cliente", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblClave = new JLabel("Clave:");
        txtUsuario = new JTextField(18);
        txtClave = new JPasswordField(18);
        btnIngresar = new JButton("Iniciar sesion");
        btnSalir = new JButton("Salir");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblClave, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtClave, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(btnIngresar, gbc);
        gbc.gridx = 1;
        panelFormulario.add(btnSalir, gbc);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);

        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresar();
            }
        });

        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });

        getRootPane().setDefaultButton(btnIngresar);
    }

    private void ingresar() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (usuario.length() == 0 || clave.length() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y clave.");
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuarioLogueado = dao.login(usuario, clave);

            if (usuarioLogueado == null) {
                JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos.");
            } else {
                PrincipalFrame principal = new PrincipalFrame(usuarioLogueado);
                principal.setVisible(true);
                dispose();
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "No se encontro el driver de Oracle.\nCopie ojdbc14.jar u ojdbc6.jar en la carpeta lib y agreguelo a NetBeans.",
                    "Driver Oracle", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible conectar con la base de datos.\n" + e.getMessage(),
                    "Conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
}
