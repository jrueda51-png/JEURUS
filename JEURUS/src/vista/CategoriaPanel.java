package vista;

import dao.CatalogoDAO;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import util.ItemCombo;

public class CategoriaPanel extends JPanel {

    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTable tabla;
    private DefaultTableModel modelo;

    public CategoriaPanel() {
        setLayout(new BorderLayout());
        construirInterfaz();
        cargarCategorias();
    }

    private void construirInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar categoria"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField(20);
        txtDescripcion = new JTextArea(3, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JButton btnGuardar = new JButton("Guardar categoria");
        JButton btnRefrescar = new JButton("Refrescar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        gbc.gridx = 2;
        panel.add(btnGuardar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Descripcion:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(txtDescripcion), gbc);
        gbc.gridx = 2;
        panel.add(btnRefrescar, gbc);

        modelo = new DefaultTableModel(new Object[]{"ID", "Categoria"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                guardarCategoria();
            }
        });

        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cargarCategorias();
            }
        });
    }

    private void cargarCategorias() {
        try {
            CatalogoDAO dao = new CatalogoDAO();
            List<ItemCombo> lista = dao.listarCategorias();
            modelo.setRowCount(0);
            for (ItemCombo item : lista) {
                modelo.addRow(new Object[]{item.getId(), item.getNombre()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando categorias: " + e.getMessage());
        }
    }

    private void guardarCategoria() {
        if (txtNombre.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre de la categoria.");
            return;
        }

        try {
            CatalogoDAO dao = new CatalogoDAO();
            dao.registrarCategoria(txtNombre.getText().trim(), txtDescripcion.getText().trim());
            JOptionPane.showMessageDialog(this, "Categoria registrada correctamente.");
            txtNombre.setText("");
            txtDescripcion.setText("");
            cargarCategorias();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar categoria: " + e.getMessage());
        }
    }
}
