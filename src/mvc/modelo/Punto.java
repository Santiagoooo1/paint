package mvc.modelo;

import java.awt.Color;
import java.awt.Graphics2D;

public class Punto extends Figura {
    private int x;
    private int y;

    public Punto() {
        super();
    }

    public Punto(int x, int y, String colorBorde, int ordenDibujo) {
        super(colorBorde, "none", ordenDibujo);
        this.x = x;
        this.y = y;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.decode(colorBorde));
        g.fillOval(x - 2, y - 2, 4, 4);
    }

    @Override
    public String toSVG() {
        return String.format(
            "<circle cx=\"%d\" cy=\"%d\" r=\"2\" fill=\"%s\" />",
            x, y, colorBorde
        );
    }

    @Override
    public String toDatos() {
        return x + "," + y;
    }

    @Override
    public void cargarDesdeDatos(String datos) {
        String[] partes = datos.split(",");
        this.x = Integer.parseInt(partes[0]);
        this.y = Integer.parseInt(partes[1]);
    }

    // Getters y Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

