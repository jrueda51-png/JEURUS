package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import modelo.Usuario;

public class PrincipalFrame extends JFrame {

    private Usuario usuario;

    public PrincipalFrame(Usuario usuario) {
        this.usuario = usuario;
        setTitle("JEURUS - Panel principal");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        construirInterfaz();
    }

    private void construirInterfaz() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(41, 57, 80));

        JLabel lblTitulo = new JLabel("  JEURUS - Sistema de tickets y soporte al cliente");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        encabezado.add(lblTitulo, BorderLayout.WEST);

        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombre() + " | Rol: " + usuario.getNombreRol() + "  ");
        lblUsuario.setForeground(Color.WHITE);
        encabezado.add(lblUsuario, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Tickets", new TicketPanel(usuario));
        tabs.addTab("Reportes", new ReportePanel());

        if ("ADMINISTRADOR".equalsIgnoreCase(usuario.getNombreRol())) {
            tabs.addTab("Usuarios", new UsuarioPanel());
            tabs.addTab("Categorias", new CategoriaPanel());
        }

        setJMenuBar(crearMenu());
        add(encabezado, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSesion = new JMenuItem("Cerrar sesion");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                LoginFrame login = new LoginFrame();
                login.setVisible(true);
                dispose();
            }
        });

        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de JEURUS");
        itemAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JOptionPane.showMessageDialog(PrincipalFrame.this,
                        "JEURUS\nSistema de tickets y soporte al cliente\nJava + Oracle 10g + SQL*Plus",
                        "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuSistema.add(itemSesion);
        menuSistema.add(itemSalir);
        menuAyuda.add(itemAcerca);
        menuBar.add(menuSistema);
        menuBar.add(menuAyuda);
        return menuBar;
    }
}
