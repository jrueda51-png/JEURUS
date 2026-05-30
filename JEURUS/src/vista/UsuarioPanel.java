package vista;

import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import util.ItemCombo;

public class UsuarioPanel extends JPanel {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtUsuario;
    private JTextField txtClave;
    private JComboBox<ItemCombo> cmbRol;
    private JTable tabla;
    private DefaultTableModel modelo;

    public UsuarioPanel() {
        setLayout(new BorderLayout());
        construirInterfaz();
        cargarRoles();
        cargarUsuarios();
    }

    private void construirInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar usuario"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField(18);
        txtCorreo = new JTextField(18);
        txtUsuario = new JTextField(18);
        txtClave = new JTextField(18);
        cmbRol = new JComboBox<ItemCombo>();
        JButton btnGuardar = new JButton("Guardar usuario");
        JButton btnRefrescar = new JButton("Refrescar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 3;
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Clave:"), gbc);
        gbc.gridx = 3;
        panel.add(txtClave, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        panel.add(cmbRol, gbc);
        gbc.gridx = 2;
        panel.add(btnGuardar, gbc);
        gbc.gridx = 3;
        panel.add(btnRefrescar, gbc);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Correo", "Usuario", "Rol"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                guardarUsuario();
            }
        });

        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cargarUsuarios();
            }
        });
    }

    private void cargarRoles() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<ItemCombo> lista = dao.listarRoles();
            cmbRol.removeAllItems();
            for (ItemCombo item : lista) {
                cmbRol.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando roles: " + e.getMessage());
        }
    }

    private void cargarUsuarios() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> lista = dao.listarUsuarios();
            modelo.setRowCount(0);
            for (Usuario u : lista) {
                modelo.addRow(new Object[]{u.getIdUsuario(), u.getNombre(), u.getCorreo(), u.getUsuario(), u.getNombreRol()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuario() {
        if (txtNombre.getText().trim().length() == 0 || txtUsuario.getText().trim().length() == 0 || txtClave.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Nombre, usuario y clave son obligatorios.");
            return;
        }

        try {
            ItemCombo rol = (ItemCombo) cmbRol.getSelectedItem();
            Usuario u = new Usuario();
            u.setNombre(txtNombre.getText().trim());
            u.setCorreo(txtCorreo.getText().trim());
            u.setUsuario(txtUsuario.getText().trim());
            u.setClave(txtClave.getText().trim());
            u.setIdRol(rol.getId());

            UsuarioDAO dao = new UsuarioDAO();
            dao.registrarUsuario(u);
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            txtNombre.setText("");
            txtCorreo.setText("");
            txtUsuario.setText("");
            txtClave.setText("");
            cargarUsuarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar usuario: " + e.getMessage());
        }
    }
}
