package mvc.modelo;

import java.awt.Point;
import java.util.List;

public class Validacion {
    public static boolean tieneLadosCruzados(List<Point> vertices) {
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Point a1 = vertices.get(i);
            Point a2 = vertices.get((i + 1) % n);

            for (int j = i + 1; j < n; j++) {
                Point b1 = vertices.get(j);
                Point b2 = vertices.get((j + 1) % n);

                // Evita comparar segmentos consecutivos
                if (Math.abs(i - j) <= 1 || (i == 0 && j == n - 1)) continue;

                if (segmentosCruzan(a1, a2, b1, b2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Función geométrica para detectar si dos segmentos se cruzan
    private static boolean segmentosCruzan(Point p1, Point p2, Point q1, Point q2) {
        return ccw(p1, q1, q2) != ccw(p2, q1, q2) &&
               ccw(p1, p2, q1) != ccw(p1, p2, q2);
    }

    private static boolean ccw(Point a, Point b, Point c) {
        return (c.y - a.y) * (b.x - a.x) > (b.y - a.y) * (c.x - a.x);
    }
}

