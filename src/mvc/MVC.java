package mvc;

import mvc.controlador.PaintControlador;
import mvc.modelo.ConexionBD;
import mvc.vista.VistaPaint;

import javax.swing.*;

public class MVC {
    public static void main(String[] args) {
        // Lógica para ejecutar en el hilo de interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializar la BD y sus tablas
                ConexionBD.crearTablas();

                // Crear la vista
                VistaPaint vista = new VistaPaint();

                // Crear el controlador que maneja la lógica de la app
                new PaintControlador(vista);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación:\n" + e.getMessage());
            }
        });
    }
}


