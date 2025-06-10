package mvc.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dibujo {
    private int id;
    private String nombre;
    private Date fecha;
    private List<Figura> figuras;

    public Dibujo() {
        figuras = new ArrayList<>();
    }

    public Dibujo(int id, String nombre, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.figuras = new ArrayList<>();
    }

    // Métodos de gestión de figuras
    public void agregarFigura(Figura figura) {
        figuras.add(figura);
    }

    public void eliminarFigura(Figura figura) {
        figuras.remove(figura);
    }

    public List<Figura> getFiguras() {
        return figuras;
    }

    // Exportar todas las figuras a SVG
    public String generarSVG() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n");
        for (Figura figura : figuras) {
            sb.append("  ").append(figura.toSVG()).append("\n");
        }
        sb.append("</svg>");
        return sb.toString();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}


