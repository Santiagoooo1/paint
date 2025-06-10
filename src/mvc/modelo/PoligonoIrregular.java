package mvc.modelo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class PoligonoIrregular extends Figura {
    private List<Integer> puntosX = new ArrayList<>();
    private List<Integer> puntosY = new ArrayList<>();

    public PoligonoIrregular() {}

    public void agregarPunto(int x, int y) {
        puntosX.add(x);
        puntosY.add(y);
    }

    @Override
    public void dibujar(Graphics2D g) {
        if (puntosX.size() < 3) return;

        int[] xs = puntosX.stream().mapToInt(Integer::intValue).toArray();
        int[] ys = puntosY.stream().mapToInt(Integer::intValue).toArray();

        g.setColor(Color.decode(colorRelleno));
        g.fillPolygon(xs, ys, puntosX.size());
        g.setColor(Color.decode(colorBorde));
        g.drawPolygon(xs, ys, puntosX.size());
    }

    @Override
    public String toSVG() {
        StringBuilder puntos = new StringBuilder();
        for (int i = 0; i < puntosX.size(); i++) {
            puntos.append(puntosX.get(i)).append(",").append(puntosY.get(i)).append(" ");
        }

        return String.format(
            "<polygon points=\"%s\" stroke=\"%s\" fill=\"%s\" />",
            puntos.toString().trim(), colorBorde, colorRelleno
        );
    }

    @Override
    public String toDatos() {
        StringBuilder datos = new StringBuilder();
        for (int i = 0; i < puntosX.size(); i++) {
            datos.append(puntosX.get(i)).append(",").append(puntosY.get(i)).append(";");
        }
        return datos.toString();
    }

    @Override
    public void cargarDesdeDatos(String datos) {
        puntosX.clear();
        puntosY.clear();
        String[] pares = datos.split(";");
        for (String par : pares) {
            if (!par.isEmpty()) {
                String[] coords = par.split(",");
                puntosX.add(Integer.parseInt(coords[0]));
                puntosY.add(Integer.parseInt(coords[1]));
            }
        }
    }
}

