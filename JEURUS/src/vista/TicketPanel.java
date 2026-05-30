package vista;

import dao.CatalogoDAO;
import dao.TicketDAO;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Comentario;
import modelo.ReporteTicket;
import modelo.Ticket;
import modelo.Usuario;
import util.ItemCombo;

public class TicketPanel extends JPanel {

    private Usuario usuario;
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<ItemCombo> cmbCategoria;
    private JComboBox<ItemCombo> cmbPrioridad;
    private JComboBox<ItemCombo> cmbEstado;
    private JTextArea txtComentario;
    private JTable tablaTickets;
    private DefaultTableModel modeloTabla;

    public TicketPanel(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout());
        construirInterfaz();
        cargarCombos();
        cargarTickets();
    }

    private void construirInterfaz() {
        JPanel panelNuevo = new JPanel(new GridBagLayout());
        panelNuevo.setBorder(javax.swing.BorderFactory.createTitledBorder("Crear nuevo ticket"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTitulo = new JTextField(25);
        txtDescripcion = new JTextArea(3, 25);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        cmbCategoria = new JComboBox<ItemCombo>();
        cmbPrioridad = new JComboBox<ItemCombo>();
        JButton btnCrear = new JButton("Crear ticket");
        JButton btnRefrescar = new JButton("Refrescar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelNuevo.add(new JLabel("Titulo:"), gbc);
        gbc.gridx = 1;
        panelNuevo.add(txtTitulo, gbc);

        gbc.gridx = 2;
        panelNuevo.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 3;
        panelNuevo.add(cmbCategoria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelNuevo.add(new JLabel("Descripcion:"), gbc);
        gbc.gridx = 1;
        panelNuevo.add(new JScrollPane(txtDescripcion), gbc);

        gbc.gridx = 2;
        panelNuevo.add(new JLabel("Prioridad:"), gbc);
        gbc.gridx = 3;
        panelNuevo.add(cmbPrioridad, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panelNuevo.add(btnCrear, gbc);
        gbc.gridx = 3;
        panelNuevo.add(btnRefrescar, gbc);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Titulo", "Categoria", "Prioridad", "Estado", "Cliente", "Agente", "Creacion", "Cierre", "Comentarios"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTickets = new JTable(modeloTabla);
        tablaTickets.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaTickets.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaTickets.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaTickets.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaTickets.getColumnModel().getColumn(3).setPreferredWidth(90);
        tablaTickets.getColumnModel().getColumn(4).setPreferredWidth(110);
        tablaTickets.getColumnModel().getColumn(5).setPreferredWidth(150);
        tablaTickets.getColumnModel().getColumn(6).setPreferredWidth(150);
        tablaTickets.getColumnModel().getColumn(7).setPreferredWidth(135);
        tablaTickets.getColumnModel().getColumn(8).setPreferredWidth(135);
        tablaTickets.getColumnModel().getColumn(9).setPreferredWidth(90);

        JPanel panelAcciones = new JPanel(new GridBagLayout());
        panelAcciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestion del ticket seleccionado"));
        cmbEstado = new JComboBox<ItemCombo>();
        txtComentario = new JTextArea(3, 30);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        JButton btnAsignarme = new JButton("Asignarme");
        JButton btnCambiarEstado = new JButton("Cambiar estado");
        JButton btnCerrar = new JButton("Cerrar ticket");
        JButton btnComentar = new JButton("Agregar comentario");
        JButton btnVerComentarios = new JButton("Ver comentarios");

        GridBagConstraints ac = new GridBagConstraints();
        ac.insets = new Insets(5, 5, 5, 5);
        ac.fill = GridBagConstraints.HORIZONTAL;

        ac.gridx = 0;
        ac.gridy = 0;
        panelAcciones.add(new JLabel("Estado:"), ac);
        ac.gridx = 1;
        panelAcciones.add(cmbEstado, ac);
        ac.gridx = 2;
        panelAcciones.add(btnCambiarEstado, ac);
        ac.gridx = 3;
        panelAcciones.add(btnAsignarme, ac);
        ac.gridx = 4;
        panelAcciones.add(btnCerrar, ac);

        ac.gridx = 0;
        ac.gridy = 1;
        panelAcciones.add(new JLabel("Comentario:"), ac);
        ac.gridx = 1;
        ac.gridwidth = 2;
        panelAcciones.add(new JScrollPane(txtComentario), ac);
        ac.gridx = 3;
        ac.gridwidth = 1;
        panelAcciones.add(btnComentar, ac);
        ac.gridx = 4;
        panelAcciones.add(btnVerComentarios, ac);

        add(panelNuevo, BorderLayout.NORTH);
        add(new JScrollPane(tablaTickets), BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        boolean esCliente = "CLIENTE".equalsIgnoreCase(usuario.getNombreRol());
        btnAsignarme.setEnabled(!esCliente);
        btnCambiarEstado.setEnabled(!esCliente);
        btnCerrar.setEnabled(!esCliente);
        cmbEstado.setEnabled(!esCliente);

        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                crearTicket();
            }
        });

        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cargarTickets();
            }
        });

        btnAsignarme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                asignarme();
            }
        });

        btnCambiarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cambiarEstado();
            }
        });

        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cerrarTicket();
            }
        });

        btnComentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                agregarComentario();
            }
        });

        btnVerComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                verComentarios();
            }
        });
    }

    private void cargarCombos() {
        try {
            CatalogoDAO dao = new CatalogoDAO();
            llenarCombo(cmbCategoria, dao.listarCategorias());
            llenarCombo(cmbPrioridad, dao.listarPrioridades());
            llenarCombo(cmbEstado, dao.listarEstados());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando catalogos: " + e.getMessage());
        }
    }

    private void llenarCombo(JComboBox<ItemCombo> combo, List<ItemCombo> datos) {
        combo.removeAllItems();
        for (ItemCombo item : datos) {
            combo.addItem(item);
        }
    }

    private void crearTicket() {
        if (txtTitulo.getText().trim().length() == 0 || txtDescripcion.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese titulo y descripcion.");
            return;
        }

        try {
            ItemCombo categoria = (ItemCombo) cmbCategoria.getSelectedItem();
            ItemCombo prioridad = (ItemCombo) cmbPrioridad.getSelectedItem();

            Ticket ticket = new Ticket();
            ticket.setTitulo(txtTitulo.getText().trim());
            ticket.setDescripcion(txtDescripcion.getText().trim());
            ticket.setIdCliente(usuario.getIdUsuario());
            ticket.setIdCategoria(categoria.getId());
            ticket.setIdPrioridad(prioridad.getId());

            TicketDAO dao = new TicketDAO();
            dao.registrarTicket(ticket);
            JOptionPane.showMessageDialog(this, "Ticket registrado correctamente.");
            txtTitulo.setText("");
            txtDescripcion.setText("");
            cargarTickets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear ticket: " + e.getMessage());
        }
    }

    private void cargarTickets() {
        try {
            TicketDAO dao = new TicketDAO();
            List<ReporteTicket> lista = dao.listarTickets(usuario);
            modeloTabla.setRowCount(0);
            for (ReporteTicket r : lista) {
                modeloTabla.addRow(new Object[]{r.getIdTicket(), r.getTitulo(), r.getCategoria(), r.getPrioridad(),
                    r.getEstado(), r.getCliente(), r.getAgente(), r.getFechaCreacion(), r.getFechaCierre(), r.getTotalComentarios()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tickets: " + e.getMessage());
        }
    }

    private int obtenerTicketSeleccionado() {
        int fila = tablaTickets.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket.");
            return -1;
        }
        return Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
    }

    private void asignarme() {
        int idTicket = obtenerTicketSeleccionado();
        if (idTicket < 0) {
            return;
        }

        try {
            TicketDAO dao = new TicketDAO();
            dao.asignarTicket(idTicket, usuario.getIdUsuario());
            JOptionPane.showMessageDialog(this, "Ticket asignado correctamente.");
            cargarTickets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al asignar ticket: " + e.getMessage());
        }
    }

    private void cambiarEstado() {
        int idTicket = obtenerTicketSeleccionado();
        if (idTicket < 0) {
            return;
        }

        try {
            ItemCombo estado = (ItemCombo) cmbEstado.getSelectedItem();
            TicketDAO dao = new TicketDAO();
            dao.actualizarEstado(idTicket, estado.getId());
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
            cargarTickets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + e.getMessage());
        }
    }

    private void cerrarTicket() {
        int idTicket = obtenerTicketSeleccionado();
        if (idTicket < 0) {
            return;
        }

        try {
            TicketDAO dao = new TicketDAO();
            dao.cerrarTicket(idTicket);
            JOptionPane.showMessageDialog(this, "Ticket cerrado correctamente.");
            cargarTickets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cerrar ticket: " + e.getMessage());
        }
    }

    private void agregarComentario() {
        int idTicket = obtenerTicketSeleccionado();
        if (idTicket < 0) {
            return;
        }
        if (txtComentario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese un comentario.");
            return;
        }

        try {
            TicketDAO dao = new TicketDAO();
            dao.agregarComentario(idTicket, usuario.getIdUsuario(), txtComentario.getText().trim());
            JOptionPane.showMessageDialog(this, "Comentario registrado.");
            txtComentario.setText("");
            cargarTickets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al comentar: " + e.getMessage());
        }
    }

    private void verComentarios() {
        int idTicket = obtenerTicketSeleccionado();
        if (idTicket < 0) {
            return;
        }

        try {
            TicketDAO dao = new TicketDAO();
            List<Comentario> lista = dao.listarComentarios(idTicket);
            StringBuilder sb = new StringBuilder();
            if (lista.isEmpty()) {
                sb.append("Este ticket no tiene comentarios.");
            } else {
                for (Comentario c : lista) {
                    sb.append(c.getFechaComentario()).append(" - ").append(c.getNombreUsuario()).append("\n");
                    sb.append(c.getMensaje()).append("\n\n");
                }
            }
            JTextArea area = new JTextArea(sb.toString(), 15, 50);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Comentarios del ticket " + idTicket, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar comentarios: " + e.getMessage());
        }
    }
}
