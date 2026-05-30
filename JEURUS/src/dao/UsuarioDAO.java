package dao;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import util.ItemCombo;

public class UsuarioDAO {

    public Usuario login(String nombreUsuario, String clave) throws Exception {
        String sql = "SELECT u.id_usuario, u.nombre, u.correo, u.usuario, u.clave, "
                + "u.id_rol, r.nombre_rol "
                + "FROM usuario u INNER JOIN rol r ON u.id_rol = r.id_rol "
                + "WHERE UPPER(u.usuario) = UPPER(?) AND u.clave = ?";

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, clave);
            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setNombreRol(rs.getString("nombre_rol"));
                return u;
            }
            return null;
        } finally {
            cerrar(rs, ps, cn);
        }
    }

    public List<Usuario> listarUsuarios() throws Exception {
        String sql = "SELECT u.id_usuario, u.nombre, u.correo, u.usuario, u.clave, "
                + "u.id_rol, r.nombre_rol "
                + "FROM usuario u INNER JOIN rol r ON u.id_rol = r.id_rol "
                + "ORDER BY u.id_usuario";

        List<Usuario> lista = new ArrayList<Usuario>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setNombreRol(rs.getString("nombre_rol"));
                lista.add(u);
            }
            return lista;
        } finally {
            cerrar(rs, ps, cn);
        }
    }

    public void registrarUsuario(Usuario usuario) throws Exception {
        String sql = "INSERT INTO usuario(nombre, correo, usuario, clave, id_rol) "
                + "VALUES(?, ?, ?, ?, ?)";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getUsuario());
            ps.setString(4, usuario.getClave());
            ps.setInt(5, usuario.getIdRol());
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    public List<ItemCombo> listarRoles() throws Exception {
        String sql = "SELECT id_rol, nombre_rol FROM rol ORDER BY id_rol";
        List<ItemCombo> lista = new ArrayList<ItemCombo>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new ItemCombo(rs.getInt("id_rol"), rs.getString("nombre_rol")));
            }
            return lista;
        } finally {
            cerrar(rs, ps, cn);
        }
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
