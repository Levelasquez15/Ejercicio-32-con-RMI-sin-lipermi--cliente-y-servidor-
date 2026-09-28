package com.edad.rmi.net;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import com.edad.rmi.lib.IRemotaCalculoEdad;

/**
 * Gestor del ciclo de vida del servidor RMI nativo / estándar de Java.
 * Utiliza el registro de nombres RMI integrado mediante LocateRegistry.
 */
public class Servidor {

    public static final String NOMBRE_SERVICIO = "CalculoEdadRemoto";

    private int puerto = 9007;
    private Registry registro;
    private CalculoRmiEdadImplem calculoEdad;

    public Servidor() {
        this(9007);
    }

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public void iniciar() throws Exception {
        try {
            // Iniciar o localizar el registro RMI en el puerto configurado
            try {
                registro = LocateRegistry.createRegistry(puerto);
            } catch (RemoteException ex) {
                registro = LocateRegistry.getRegistry(puerto);
            }

            calculoEdad = new CalculoRmiEdadImplem();
            registro.rebind(NOMBRE_SERVICIO, calculoEdad);

            System.out.println("==================================================");
            System.out.println("  SERVIDOR RMI ESTÁNDAR (SIN LIBRERÍA) INICIADO   ");
            System.out.println("  Puerto de escucha: " + puerto);
            System.out.println("  Nombre servicio  : " + NOMBRE_SERVICIO);
            System.out.println("  Interfaz remota  : IRemotaCalculoEdad           ");
            System.out.println("  Esperando peticiones de clientes...            ");
            System.out.println("==================================================");
        } catch (RemoteException ex) {
            throw new Exception("Error al iniciar el servidor RMI en el puerto " + puerto + ": " + ex.getMessage(), ex);
        }
    }

    public void detener() {
        try {
            if (registro != null) {
                registro.unbind(NOMBRE_SERVICIO);
            }
            if (calculoEdad != null) {
                UnicastRemoteObject.unexportObject(calculoEdad, true);
            }
            System.out.println("[SERVIDOR] Servidor RMI detenido correctamente.");
        } catch (Exception ex) {
            System.err.println("[SERVIDOR] Advertencia al detener: " + ex.getMessage());
        }
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}