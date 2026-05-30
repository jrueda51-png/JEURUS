package dao;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Comentario;
import modelo.ReporteTicket;
import modelo.Ticket;
import modelo.Usuario;

public class TicketDAO {

    public void registrarTicket(Ticket ticket) throws Exception {
        String sql = "INSERT INTO ticket(titulo, descripcion, id_cliente, id_categoria, id_prioridad, id_estado) "
                + "VALUES(?, ?, ?, ?, ?, (SELECT id_estado FROM estado WHERE nombre_estado = 'ABIERTO'))";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setString(1, ticket.getTitulo());
            ps.setString(2, ticket.getDescripcion());
            ps.setInt(3, ticket.getIdCliente());
            ps.setInt(4, ticket.getIdCategoria());
            ps.setInt(5, ticket.getIdPrioridad());
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    public List<ReporteTicket> listarTickets(Usuario usuario) throws Exception {
        String sql = "SELECT v.* FROM vw_reporte_tickets v INNER JOIN ticket t ON v.id_ticket = t.id_ticket ";

        if ("CLIENTE".equalsIgnoreCase(usuario.getNombreRol())) {
            sql += "WHERE t.id_cliente = ? ";
        } else if ("AGENTE".equalsIgnoreCase(usuario.getNombreRol())) {
            sql += "WHERE t.id_agente = ? OR t.id_agente IS NULL ";
        }

        sql += "ORDER BY v.id_ticket DESC";

        List<ReporteTicket> lista = new ArrayList<ReporteTicket>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);

            if ("CLIENTE".equalsIgnoreCase(usuario.getNombreRol())
                    || "AGENTE".equalsIgnoreCase(usuario.getNombreRol())) {
                ps.setInt(1, usuario.getIdUsuario());
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapReporte(rs));
            }
            return lista;
        } finally {
            cerrar(rs, ps, cn);
        }
    }

    public List<ReporteTicket> listarReporteGeneral() throws Exception {
        String sql = "SELECT * FROM vw_reporte_tickets ORDER BY id_ticket DESC";
        List<ReporteTicket> lista = new ArrayList<ReporteTicket>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapReporte(rs));
            }
            return lista;
        } finally {
            cerrar(rs, ps, cn);
        }
    }

    public void asignarTicket(int idTicket, int idAgente) throws Exception {
        String sql = "UPDATE ticket SET id_agente = ?, id_estado = "
                + "(SELECT id_estado FROM estado WHERE nombre_estado = 'EN_PROCESO') "
                + "WHERE id_ticket = ?";
        ejecutarActualizacion(sql, idAgente, idTicket);
    }

    public void actualizarEstado(int idTicket, int idEstado) throws Exception {
        String sql = "UPDATE ticket SET id_estado = ?, fecha_cierre = "
                + "CASE WHEN ? = (SELECT id_estado FROM estado WHERE nombre_estado = 'CERRADO') THEN SYSDATE ELSE fecha_cierre END "
                + "WHERE id_ticket = ?";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idEstado);
            ps.setInt(2, idEstado);
            ps.setInt(3, idTicket);
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    public void cerrarTicket(int idTicket) throws Exception {
        String sql = "UPDATE ticket SET id_estado = (SELECT id_estado FROM estado WHERE nombre_estado = 'CERRADO'), "
                + "fecha_cierre = SYSDATE WHERE id_ticket = ?";
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idTicket);
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    public void agregarComentario(int idTicket, int idUsuario, String mensaje) throws Exception {
        String sql = "INSERT INTO comentario(id_ticket, id_usuario, mensaje) VALUES(?, ?, ?)";
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idTicket);
            ps.setInt(2, idUsuario);
            ps.setString(3, mensaje);
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    public List<Comentario> listarComentarios(int idTicket) throws Exception {
        String sql = "SELECT c.id_comentario, c.id_ticket, c.id_usuario, c.mensaje, "
                + "TO_CHAR(c.fecha_comentario, 'DD/MM/YYYY HH24:MI') AS fecha_comentario, u.nombre "
                + "FROM comentario c INNER JOIN usuario u ON c.id_usuario = u.id_usuario "
                + "WHERE c.id_ticket = ? ORDER BY c.fecha_comentario";

        List<Comentario> lista = new ArrayList<Comentario>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idTicket);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("id_comentario"));
                c.setIdTicket(rs.getInt("id_ticket"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setMensaje(rs.getString("mensaje"));
                c.setFechaComentario(rs.getString("fecha_comentario"));
                c.setNombreUsuario(rs.getString("nombre"));
                lista.add(c);
            }
            return lista;
        } finally {
            cerrar(rs, ps, cn);
        }
    }

    private void ejecutarActualizacion(String sql, int valor1, int valor2) throws Exception {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, valor1);
            ps.setInt(2, valor2);
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    private ReporteTicket mapReporte(ResultSet rs) throws SQLException {
        ReporteTicket r = new ReporteTicket();
        r.setIdTicket(rs.getInt("id_ticket"));
        r.setTitulo(rs.getString("titulo"));
        r.setDescripcion(rs.getString("descripcion"));
        r.setCategoria(rs.getString("categoria"));
        r.setPrioridad(rs.getString("prioridad"));
        r.setEstado(rs.getString("estado"));
        r.setCliente(rs.getString("cliente"));
        r.setAgente(rs.getString("agente"));
        r.setFechaCreacion(rs.getString("fecha_creacion"));
        r.setFechaCierre(rs.getString("fecha_cierre"));
        r.setTotalComentarios(rs.getInt("total_comentarios"));
        return r;
    }

    private void cerrar(ResultSet rs, PreparedStatement ps, Connection cn) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cn != null) {
            cn.close();
        }
    }
}
