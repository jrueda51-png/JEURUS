package dao;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ItemCombo;

public class CatalogoDAO {

    public List<ItemCombo> listarCategorias() throws Exception {
        return listar("SELECT id_categoria, nombre_categoria FROM categoria ORDER BY nombre_categoria");
    }

    public List<ItemCombo> listarPrioridades() throws Exception {
        return listar("SELECT id_prioridad, nombre_prioridad FROM prioridad ORDER BY id_prioridad");
    }

    public List<ItemCombo> listarEstados() throws Exception {
        return listar("SELECT id_estado, nombre_estado FROM estado ORDER BY id_estado");
    }

    public void registrarCategoria(String nombre, String descripcion) throws Exception {
        String sql = "INSERT INTO categoria(nombre_categoria, descripcion) VALUES(?, ?)";
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        } finally {
            cerrar(null, ps, cn);
        }
    }

    private List<ItemCombo> listar(String sql) throws Exception {
        List<ItemCombo> lista = new ArrayList<ItemCombo>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conectar();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ItemCombo(rs.getInt(1), rs.getString(2)));
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
