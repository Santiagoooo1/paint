package mvc.modelo;

import java.awt.Color;
import java.awt.Graphics2D;

public class Linea extends Figura {
    private int x1, y1, x2, y2;

    public Linea() {}

    public Linea(int x1, int y1, int x2, int y2, String colorBorde, int ordenDibujo) {
        super(colorBorde, "none", ordenDibujo);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.decode(colorBorde));
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public String toSVG() {
        return String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"%s\" />", x1, y1, x2, y2, colorBorde);
    }

    @Override
    public String toDatos() {
        return x1 + "," + y1 + "," + x2 + "," + y2;
    }

    @Override
    public void cargarDesdeDatos(String datos) {
        String[] partes = datos.split(",");
        x1 = Integer.parseInt(partes[0]);
        y1 = Integer.parseInt(partes[1]);
        x2 = Integer.parseInt(partes[2]);
        y2 = Integer.parseInt(partes[3]);
    }
}


