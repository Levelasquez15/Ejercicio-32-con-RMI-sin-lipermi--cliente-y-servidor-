package com.edad.rmi;

import com.edad.rmi.net.Servidor;

/**
 * Punto de entrada para la ejecución del Servidor RMI estándar.
 */
public class Principal {

    public static void main(String[] args) {
        int puerto = 9007;
        if (args.length > 0) {
            try {
                puerto = Integer.parseInt(args[0].trim());
            } catch (NumberFormatException ex) {
                System.out.println("Puerto inválido, usando por defecto: 9007");
            }
        }

        Servidor servidor = new Servidor(puerto);
        try {
            servidor.iniciar();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n[SERVIDOR] Apagando servicio RMI...");
                servidor.detener();
            }));

            // Mantener hilo principal activo para evitar terminación en NetBeans
            Thread.currentThread().join();
        } catch (Exception ex) {
            System.err.println("[SERVIDOR ERROR] No se pudo iniciar el servidor RMI: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}