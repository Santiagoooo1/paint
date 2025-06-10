package mvc.modelo;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DibujoDAO {

    public int guardarDibujo(Dibujo dibujo) {
        String sql = "INSERT INTO dibujo (nombre, fecha) VALUES (?, ?)";
        int idGenerado = -1;

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dibujo.getNombre());
            stmt.setTimestamp(2, new Timestamp(dibujo.getFecha().getTime()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1); // Recuperamos el ID autogenerado
                dibujo.setId(idGenerado);  // Guardamos el ID en el objeto
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    // Método opcional si necesitas cargar un dibujo desde la BD
    public Dibujo obtenerDibujo(int id) {
        String sql = "SELECT * FROM dibujo WHERE id = ?";
        Dibujo dibujo = null;

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                Timestamp fecha = rs.getTimestamp("fecha");

                dibujo = new Dibujo(id, nombre, new Date(fecha.getTime()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dibujo;
    }

    public List<Dibujo> obtenerTodos() {
        List<Dibujo> dibujos = new ArrayList<>();
        String sql = "SELECT * FROM dibujo ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Timestamp fecha = rs.getTimestamp("fecha");

                Dibujo dibujo = new Dibujo(id, nombre, new Date(fecha.getTime()));
                dibujos.add(dibujo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dibujos;
    }

    public void eliminarDibujo(int id) {
        String sql = "DELETE FROM dibujo WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
