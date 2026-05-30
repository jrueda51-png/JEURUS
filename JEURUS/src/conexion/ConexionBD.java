package conexion;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static final String URL_BD = "jdbc:oracle:thin:@192.168.254.215:1521:orcl";
    private static final String USER = "JEURUS";
    private static final String PASSWORD = "JEURUS";

    public static Connection conectar() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(URL_BD, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            return conectarCargandoDriverLocal();
        }
    }

    private static Connection conectarCargandoDriverLocal() throws SQLException, ClassNotFoundException {
        File[] rutas = new File[] {
            new File("ojdbc14.jar"),
            new File("lib/ojdbc14.jar"),
            new File("ojdbc6.jar"),
            new File("lib/ojdbc6.jar"),
            new File("C:/Users/SALA-404/Documents/JEURUS/ojdbc14.jar"),
            new File("C:/Users/SALA-404/Documents/JEURUS/lib/ojdbc14.jar")
        };

        Exception ultimoError = null;

        for (int i = 0; i < rutas.length; i++) {
            File archivo = rutas[i];
            if (archivo.exists()) {
                try {
                    URL url = archivo.toURI().toURL();
                    URLClassLoader loader = new URLClassLoader(new URL[] { url }, ConexionBD.class.getClassLoader());
                    Class claseDriver = Class.forName("oracle.jdbc.OracleDriver", true, loader);
                    Driver driver = (Driver) claseDriver.getDeclaredConstructor().newInstance();

                    Properties propiedades = new Properties();
                    propiedades.setProperty("user", USER);
                    propiedades.setProperty("password", PASSWORD);

                    Connection conexion = driver.connect(URL_BD, propiedades);
                    if (conexion != null) {
                        return conexion;
                    }
                } catch (Exception ex) {
                    ultimoError = ex;
                }
            }
        }

        ClassNotFoundException error = new ClassNotFoundException(
                "No se encontro oracle.jdbc.OracleDriver. Ubique ojdbc14.jar en la raiz del proyecto o en la carpeta lib.");

        if (ultimoError != null) {
            error.initCause(ultimoError);
        }

        throw error;
    }
}
