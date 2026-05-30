package vista;

import dao.TicketDAO;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.ReporteTicket;

public class ReportePanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ReportePanel() {
        setLayout(new BorderLayout());
        construirInterfaz();
        cargarReporte();
    }

    private void construirInterfaz() {
        JPanel superior = new JPanel(new BorderLayout());
        JButton btnRefrescar = new JButton("Invocar VIEW / Refrescar reporte");
        superior.add(btnRefrescar, BorderLayout.EAST);

        modelo = new DefaultTableModel(new Object[]{"ID", "Titulo", "Descripcion", "Categoria", "Prioridad", "Estado", "Cliente", "Agente", "Fecha creacion", "Fecha cierre", "Comentarios"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(260);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(8).setPreferredWidth(135);
        tabla.getColumnModel().getColumn(9).setPreferredWidth(135);
        tabla.getColumnModel().getColumn(10).setPreferredWidth(90);

        add(superior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cargarReporte();
            }
        });
    }

    private void cargarReporte() {
        try {
            TicketDAO dao = new TicketDAO();
            List<ReporteTicket> lista = dao.listarReporteGeneral();
            modelo.setRowCount(0);
            for (ReporteTicket r : lista) {
                modelo.addRow(new Object[]{r.getIdTicket(), r.getTitulo(), r.getDescripcion(), r.getCategoria(),
                    r.getPrioridad(), r.getEstado(), r.getCliente(), r.getAgente(), r.getFechaCreacion(),
                    r.getFechaCierre(), r.getTotalComentarios()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error invocando la vista VW_REPORTE_TICKETS: " + e.getMessage());
        }
    }
}
