package mvc.modelo;

import mvc.modelo.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SVGExporter {

    public static void exportarComoArchivoSVG(Dibujo dibujo, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("<svg xmlns='http://www.w3.org/2000/svg' width='1000' height='700'>\n");

            for (Figura figura : dibujo.getFiguras()) {
                writer.write(figura.toSVG() + "\n");
            }

            writer.write("</svg>");
            System.out.println("SVG exportado correctamente a: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

