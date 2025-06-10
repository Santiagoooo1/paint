package mvc.modelo;

import java.awt.Color;
import java.awt.Graphics2D;

public class Circulo extends Figura {
    private int cx, cy, radio;

    public Circulo() {}

    public Circulo(int cx, int cy, int radio, String colorBorde, String colorRelleno, int ordenDibujo) {
        super(colorBorde, colorRelleno, ordenDibujo);
        this.cx = cx;
        this.cy = cy;
        this.radio = radio;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.decode(colorRelleno));
        g.fillOval(cx - radio, cy - radio, 2 * radio, 2 * radio);
        g.setColor(Color.decode(colorBorde));
        g.drawOval(cx - radio, cy - radio, 2 * radio, 2 * radio);
    }

    @Override
    public String toSVG() {
        return String.format(
            "<circle cx=\"%d\" cy=\"%d\" r=\"%d\" stroke=\"%s\" fill=\"%s\" />",
            cx, cy, radio, colorBorde, colorRelleno
        );
    }

    @Override
    public String toDatos() {
        return cx + "," + cy + "," + radio;
    }

    @Override
    public void cargarDesdeDatos(String datos) {
        String[] partes = datos.split(",");
        cx = Integer.parseInt(partes[0]);
        cy = Integer.parseInt(partes[1]);
        radio = Integer.parseInt(partes[2]);
    }
}



