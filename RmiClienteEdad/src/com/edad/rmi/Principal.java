package com.edad.rmi;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.edad.rmi.vistas.VentanaPrincipal;

/**
 * Punto de entrada para la aplicación Cliente RMI estándar.
 */
public class Principal {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // Mantener estilo por defecto si falla
        }

        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}