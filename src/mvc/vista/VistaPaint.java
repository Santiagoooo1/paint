package mvc.vista;

import javax.swing.*;
import java.awt.*;

public class VistaPaint extends JFrame {

    public JComboBox<String> comboFiguras;
    public JButton btnGuardar, btnCargar, btnExportar;
    public JSlider sliderLados;
    public JButton btnColorBorde, btnColorRelleno;
    public Color colorActualBorde = Color.BLACK;
    public Color colorActualRelleno = Color.WHITE;

    public Lienzo lienzo;
    public JButton btnBorrar;
    public JButton btnNuevoLienzo;

    public VistaPaint() {
        setTitle("Mini Paint");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar componentes
        comboFiguras = new JComboBox<>(new String[]{
            "Punto", "Linea", "Circulo", "PoligonoRegular", "PoligonoIrregular"
        });

        btnGuardar = new JButton("Guardar");
        btnCargar = new JButton("Cargar");
        btnExportar = new JButton("Exportar SVG");
        btnBorrar = new JButton("Borrar Todo");
        btnNuevoLienzo = new JButton("Nuevo lienzo");
        btnColorBorde = new JButton("Color Borde");
        btnColorRelleno = new JButton("Color Relleno");

        sliderLados = new JSlider(3, 12, 5);
        sliderLados.setPaintLabels(true);
        sliderLados.setPaintTicks(true);
        sliderLados.setMajorTickSpacing(1);
        sliderLados.setMinorTickSpacing(1);

        lienzo = new Lienzo();

        // Panel de controles (superior)
        JPanel panelControles = new JPanel(new GridLayout(2, 1));

        JPanel fila1 = new JPanel();  // primera fila
        fila1.add(new JLabel("Figura:"));
        fila1.add(comboFiguras);
        fila1.add(new JLabel("Lados:"));
        fila1.add(sliderLados);
        fila1.add(btnGuardar);
        fila1.add(btnCargar);
        fila1.add(btnExportar);
        fila1.add(btnBorrar);

// Segunda fila para botones de color
        JPanel fila2 = new JPanel();
        fila2.add(btnColorBorde);
        fila2.add(btnColorRelleno);

        panelControles.add(fila1);
        panelControles.add(fila2);

        // Panel de botones inferior
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevoLienzo);

        // Agregar componentes al JFrame
        add(panelControles, BorderLayout.NORTH);
        add(lienzo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH); // botón "Nuevo lienzo" al sur

        setVisible(true);
    }
}
