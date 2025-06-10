package mvc.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FiguraDAO {

    public void guardarFigura(Figura figura, int idDibujo) {
        String sql = "INSERT INTO figura (tipo, datos, colorBorde, colorRelleno, orden, id_dibujo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, figura.getClass().getSimpleName()); // Ej: Punto, Linea
            stmt.setString(2, figura.toDatos());
            stmt.setString(3, figura.getColorBorde());
            stmt.setString(4, figura.getColorRelleno());
            stmt.setInt(5, figura.getOrdenDibujo());
            stmt.setInt(6, idDibujo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Figura> obtenerFigurasDeDibujo(int idDibujo) {
        List<Figura> figuras = new ArrayList<>();
        String sql = "SELECT tipo, datos, colorBorde, colorRelleno, orden FROM figura WHERE id_dibujo = ? ORDER BY orden ASC";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, idDibujo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String datos = rs.getString("datos");
                String colorBorde = rs.getString("colorBorde");
                String colorRelleno = rs.getString("colorRelleno");
                int orden = rs.getInt("orden");

                Figura figura = crearFiguraDesdeTipo(tipo);
                if (figura != null) {
                    figura.setColorBorde(colorBorde);
                    figura.setColorRelleno(colorRelleno);
                    figura.setOrdenDibujo(orden);
                    figura.cargarDesdeDatos(datos);
                    figuras.add(figura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return figuras;
    }

    private Figura crearFiguraDesdeTipo(String tipo) {
        return switch (tipo) {
            case "Punto" -> new Punto();
            case "Linea" -> new Linea();
            case "Circulo" -> new Circulo();
            case "PoligonoRegular" -> new PoligonoRegular();
            case "PoligonoIrregular" -> new PoligonoIrregular();
            default -> null;
        };
    }

    public void eliminarFigurasDeDibujo(int idDibujo) {
        String sql = "DELETE FROM figura WHERE id_dibujo = ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, idDibujo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

