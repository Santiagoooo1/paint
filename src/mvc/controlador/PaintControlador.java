package mvc.controlador;

import mvc.modelo.*;
import mvc.vista.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.Color;

public class PaintControlador {

    private VistaPaint vista;
    private Dibujo dibujoActual;
    private FiguraDAO figuraDAO;
    private List<int[]> puntosTemporales = new ArrayList<>();

    public PaintControlador(VistaPaint vista) {
        this.vista = vista;
        this.figuraDAO = new FiguraDAO();
        this.dibujoActual = new Dibujo(0, "Dibujo temporal", new Date());
        vista.lienzo.setFiguras(dibujoActual.getFiguras());
        configurarEventos();
    }

    private void configurarEventos() {
        vista.lienzo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    cerrarPoligonoIrregular();
                } else {
                    manejarClick(e.getX(), e.getY());
                }
            }
        });

        vista.btnGuardar.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(vista, "Nombre del dibujo:");
            if (nombre == null || nombre.trim().isEmpty()) {
                return;
            }

            dibujoActual.setNombre(nombre);
            dibujoActual.setFecha(new Date());

            DibujoDAO dibujoDAO = new DibujoDAO();
            int idDibujo = dibujoDAO.guardarDibujo(dibujoActual);
            if (idDibujo > 0) {
                for (Figura figura : dibujoActual.getFiguras()) {
                    figuraDAO.guardarFigura(figura, idDibujo);
                }
                JOptionPane.showMessageDialog(vista, "Dibujo y figuras guardados correctamente.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar el dibujo.");
            }
        });

        vista.btnCargar.addActionListener(e -> {
            DibujoDAO dibujoDAO = new DibujoDAO();
            List<Dibujo> dibujos = dibujoDAO.obtenerTodos();
            if (dibujos.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No hay dibujos guardados.");
                return;
            }

            String[] nombres = dibujos.stream()
                    .map(d -> "ID " + d.getId() + ": " + d.getNombre() + " (" + d.getFecha() + ")")
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    vista, "Selecciona un dibujo para cargar:",
                    "Cargar dibujo", JOptionPane.PLAIN_MESSAGE, null,
                    nombres, nombres[0]);

            if (seleccion != null) {
                int idSeleccionado = Integer.parseInt(seleccion.split(" ")[1].replace(":", ""));
                Dibujo dibujoSeleccionado = dibujoDAO.obtenerDibujo(idSeleccionado);
                List<Figura> figuras = figuraDAO.obtenerFigurasDeDibujo(idSeleccionado);
                dibujoSeleccionado.getFiguras().addAll(figuras);
                dibujoActual = dibujoSeleccionado;
                vista.lienzo.setFiguras(dibujoActual.getFiguras());
                vista.lienzo.repaint();
            }
        });

        vista.btnBorrar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(vista, "¿Quieres borrar todas las figuras del lienzo?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DibujoDAO dibujoDAO = new DibujoDAO();
                dibujoDAO.eliminarDibujo(dibujoActual.getId());
                dibujoActual = new Dibujo(0, "Dibujo temporal", new Date());
                vista.lienzo.setFiguras(dibujoActual.getFiguras());
                puntosTemporales.clear();
                vista.lienzo.repaint();
            }
        });

        vista.btnNuevoLienzo.addActionListener(e -> {
            dibujoActual = new Dibujo(0, "Dibujo temporal", new Date());
            dibujoActual.getFiguras().clear();
            puntosTemporales.clear();
            vista.lienzo.setFiguras(dibujoActual.getFiguras());
            vista.lienzo.repaint();
        });

        vista.btnColorBorde.addActionListener(e -> {
            Color nuevoColor = JColorChooser.showDialog(vista, "Seleccionar color de borde", vista.colorActualBorde);
            if (nuevoColor != null) {
                vista.colorActualBorde = nuevoColor;
                vista.btnColorBorde.setBackground(nuevoColor);
            }
        });

        vista.btnColorRelleno.addActionListener(e -> {
            Color nuevoColor = JColorChooser.showDialog(vista, "Seleccionar color de relleno", vista.colorActualRelleno);
            if (nuevoColor != null) {
                vista.colorActualRelleno = nuevoColor;
                vista.btnColorRelleno.setBackground(nuevoColor);
            }
        });

        vista.btnExportar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar como SVG");
            fileChooser.setSelectedFile(new java.io.File("dibujo.svg"));

            int resultado = fileChooser.showSaveDialog(vista);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                java.io.File archivo = fileChooser.getSelectedFile();

                if (!archivo.getName().toLowerCase().endsWith(".svg")) {
                    archivo = new java.io.File(archivo.getAbsolutePath() + ".svg");
                }

                SVGExporter.exportarComoArchivoSVG(dibujoActual, archivo.getAbsolutePath());
                JOptionPane.showMessageDialog(vista, "Archivo SVG guardado en:\n" + archivo.getAbsolutePath());
            }
        });

    }

    private void manejarClick(int x, int y) {
        String tipo = (String) vista.comboFiguras.getSelectedItem();
        String colorBorde = "#" + Integer.toHexString(vista.colorActualBorde.getRGB()).substring(2);
        String colorRelleno = "#" + Integer.toHexString(vista.colorActualRelleno.getRGB()).substring(2);

        puntosTemporales.add(new int[]{x, y});
        vista.lienzo.setPuntosTemporales(puntosTemporales);

        Figura figura = null;
        int orden = dibujoActual.getFiguras().size();

        switch (tipo) {
            case "Punto" -> {
                figura = new Punto(x, y, colorBorde, orden);
                puntosTemporales.clear();
            }
            case "Linea" -> {
                if (puntosTemporales.size() == 2) {
                    int[] p1 = puntosTemporales.get(0);
                    int[] p2 = puntosTemporales.get(1);
                    figura = new Linea(p1[0], p1[1], p2[0], p2[1], colorBorde, orden);
                    puntosTemporales.clear();
                }
            }
            case "Circulo" -> {
                if (puntosTemporales.size() == 2) {
                    int[] c = puntosTemporales.get(0);
                    int[] r = puntosTemporales.get(1);
                    int radio = (int) Math.hypot(r[0] - c[0], r[1] - c[1]);
                    figura = new Circulo(c[0], c[1], radio, colorBorde, colorRelleno, orden);
                    puntosTemporales.clear();
                }
            }
            case "PoligonoRegular" -> {
                if (puntosTemporales.size() == 2) {
                    int[] c = puntosTemporales.get(0);
                    int[] v = puntosTemporales.get(1);
                    int lados = vista.sliderLados.getValue();
                    figura = new PoligonoRegular(c[0], c[1], v[0], v[1], lados, colorBorde, colorRelleno, orden);
                    puntosTemporales.clear();
                }
            }
        }

        if (figura != null) {
            dibujoActual.agregarFigura(figura);
            vista.lienzo.setFiguras(dibujoActual.getFiguras());
        }
    }

    private void cerrarPoligonoIrregular() {
        if (puntosTemporales.size() < 3) {
            return;
        }

        int n = puntosTemporales.size();
        for (int i = 0; i < n - 1; i++) {
            int[] a1 = puntosTemporales.get(i);
            int[] a2 = puntosTemporales.get(i + 1);
            for (int j = i + 2; j < n - 1; j++) {
                int[] b1 = puntosTemporales.get(j);
                int[] b2 = puntosTemporales.get(j + 1);
                if (segmentosSeCruzan(a1[0], a1[1], a2[0], a2[1], b1[0], b1[1], b2[0], b2[1])) {
                    JOptionPane.showMessageDialog(vista, "¡El trazo se cruza! No se puede cerrar.");
                    return;
                }
            }
        }

        int[] a = puntosTemporales.get(n - 1);
        int[] b = puntosTemporales.get(0);
        for (int i = 1; i < n - 2; i++) {
            int[] c = puntosTemporales.get(i);
            int[] d = puntosTemporales.get(i + 1);
            if (segmentosSeCruzan(a[0], a[1], b[0], b[1], c[0], c[1], d[0], d[1])) {
                JOptionPane.showMessageDialog(vista, "¡La línea de cierre cruza otras líneas!");
                return;
            }
        }

        String colorBorde = "#" + Integer.toHexString(vista.colorActualBorde.getRGB()).substring(2);
        String colorRelleno = "#" + Integer.toHexString(vista.colorActualRelleno.getRGB()).substring(2);
        int orden = dibujoActual.getFiguras().size();

        PoligonoIrregular pol = new PoligonoIrregular();
        for (int[] p : puntosTemporales) {
            pol.agregarPunto(p[0], p[1]);
        }
        pol.setColorBorde(colorBorde);
        pol.setColorRelleno(colorRelleno);
        pol.setOrdenDibujo(orden);

        dibujoActual.agregarFigura(pol);
        puntosTemporales.clear();
        vista.lienzo.setPuntosTemporales(puntosTemporales);
        vista.lienzo.setFiguras(dibujoActual.getFiguras());
        vista.lienzo.repaint();
    }

    private boolean segmentosSeCruzan(int x1, int y1, int x2, int y2,
            int x3, int y3, int x4, int y4) {
        double d1 = direccion(x3, y3, x4, y4, x1, y1);
        double d2 = direccion(x3, y3, x4, y4, x2, y2);
        double d3 = direccion(x1, y1, x2, y2, x3, y3);
        double d4 = direccion(x1, y1, x2, y2, x4, y4);

        return (d1 * d2 < 0) && (d3 * d4 < 0);
    }

    private double direccion(int xi, int yi, int xj, int yj, int xk, int yk) {
        return (xk - xi) * (yj - yi) - (xj - xi) * (yk - yi);
    }
}
