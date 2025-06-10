package mvc.modelo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class PoligonoRegular extends Figura {
    private int centroX, centroY, verticeX, verticeY;
    private int lados;

    public PoligonoRegular() {}

    public PoligonoRegular(int centroX, int centroY, int verticeX, int verticeY, int lados, String colorBorde, String colorRelleno, int ordenDibujo) {
        super(colorBorde, colorRelleno, ordenDibujo);
        this.centroX = centroX;
        this.centroY = centroY;
        this.verticeX = verticeX;
        this.verticeY = verticeY;
        this.lados = lados;
    }

    @Override
    public void dibujar(Graphics2D g) {
        double radio = Math.hypot(verticeX - centroX, verticeY - centroY);
        double anguloInicial = Math.atan2(verticeY - centroY, verticeX - centroX);

        Polygon pol = new Polygon();
        for (int i = 0; i < lados; i++) {
            double angulo = anguloInicial + 2 * Math.PI * i / lados;
            int x = (int) (centroX + radio * Math.cos(angulo));
            int y = (int) (centroY + radio * Math.sin(angulo));
            pol.addPoint(x, y);
        }

        g.setColor(Color.decode(colorRelleno));
        g.fillPolygon(pol);
        g.setColor(Color.decode(colorBorde));
        g.drawPolygon(pol);
    }

    @Override
    public String toSVG() {
        StringBuilder puntos = new StringBuilder();
        double radio = Math.hypot(verticeX - centroX, verticeY - centroY);
        double anguloInicial = Math.atan2(verticeY - centroY, verticeX - centroX);

        for (int i = 0; i < lados; i++) {
            double angulo = anguloInicial + 2 * Math.PI * i / lados;
            int x = (int) (centroX + radio * Math.cos(angulo));
            int y = (int) (centroY + radio * Math.sin(angulo));
            puntos.append(x).append(",").append(y).append(" ");
        }

        return String.format(
            "<polygon points=\"%s\" stroke=\"%s\" fill=\"%s\" />",
            puntos.toString().trim(), colorBorde, colorRelleno
        );
    }

    @Override
    public String toDatos() {
        return centroX + "," + centroY + "," + verticeX + "," + verticeY + "," + lados;
    }

    @Override
    public void cargarDesdeDatos(String datos) {
        String[] partes = datos.split(",");
        centroX = Integer.parseInt(partes[0]);
        centroY = Integer.parseInt(partes[1]);
        verticeX = Integer.parseInt(partes[2]);
        verticeY = Integer.parseInt(partes[3]);
        lados = Integer.parseInt(partes[4]);
    }
}


