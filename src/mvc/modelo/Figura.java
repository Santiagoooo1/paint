package mvc.modelo;

import java.awt.Graphics2D;

public abstract class Figura {
    protected String colorBorde;
    protected String colorRelleno;
    protected int ordenDibujo;

    public Figura() {}

    public Figura(String colorBorde, String colorRelleno, int ordenDibujo) {
        this.colorBorde = colorBorde;
        this.colorRelleno = colorRelleno;
        this.ordenDibujo = ordenDibujo;
    }

    // Método para pintar la figura en el lienzo
    public abstract void dibujar(Graphics2D g);

    // Método para generar el SVG de la figura
    public abstract String toSVG();

    // Método para convertir atributos en String (para base de datos)
    public abstract String toDatos();

    // Método para cargar desde los datos guardados
    public abstract void cargarDesdeDatos(String datos);

    // Getters y Setters comunes
    public String getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(String colorBorde) {
        this.colorBorde = colorBorde;
    }

    public String getColorRelleno() {
        return colorRelleno;
    }

    public void setColorRelleno(String colorRelleno) {
        this.colorRelleno = colorRelleno;
    }

    public int getOrdenDibujo() {
        return ordenDibujo;
    }

    public void setOrdenDibujo(int ordenDibujo) {
        this.ordenDibujo = ordenDibujo;
    }
}



