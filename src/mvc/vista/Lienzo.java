package mvc.vista;

import mvc.modelo.Figura;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Lienzo extends JPanel {
    private List<Figura> figuras;
    private List<int[]> puntosTemporales = new ArrayList<>();

    public Lienzo() {
        setBackground(Color.WHITE);
    }

    public void setFiguras(List<Figura> figuras) {
        this.figuras = figuras;
        repaint();
    }

    public void setPuntosTemporales(List<int[]> puntos) {
        this.puntosTemporales = puntos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (figuras != null) {
            Graphics2D g2 = (Graphics2D) g;
            for (Figura figura : figuras) {
                figura.dibujar(g2);
            }
        }

        // Dibujar los puntos temporales con líneas entre ellos
        if (puntosTemporales != null && puntosTemporales.size() >= 2) {
            g.setColor(Color.GRAY);
            for (int i = 0; i < puntosTemporales.size() - 1; i++) {
                int[] p1 = puntosTemporales.get(i);
                int[] p2 = puntosTemporales.get(i + 1);
                g.drawLine(p1[0], p1[1], p2[0], p2[1]);
            }

            // Dibujar línea de cierre
            if (puntosTemporales.size() >= 3) {
                int[] primero = puntosTemporales.get(0);
                int[] ultimo = puntosTemporales.get(puntosTemporales.size() - 1);
                g.drawLine(ultimo[0], ultimo[1], primero[0], primero[1]);
            }
        }
    }
}


