package mvc.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/paint";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // cambia si tienes contraseña

    private static Connection conexion;

    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conexion;
    }

    public static void crearTablas() throws SQLException {
        Statement stmt = obtenerConexion().createStatement();

        String sqlDibujo = """
            CREATE TABLE IF NOT EXISTS dibujo (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100),
                fecha DATETIME DEFAULT CURRENT_TIMESTAMP
            );
        """;

        String sqlFigura = """
            CREATE TABLE IF NOT EXISTS figura (
                id INT AUTO_INCREMENT PRIMARY KEY,
                tipo VARCHAR(50),
                datos TEXT,
                colorBorde VARCHAR(20),
                colorRelleno VARCHAR(20),
                orden INT,
                id_dibujo INT,
                FOREIGN KEY (id_dibujo) REFERENCES dibujo(id) ON DELETE CASCADE
            );
        """;

        stmt.execute(sqlDibujo);
        stmt.execute(sqlFigura);
    }

    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

