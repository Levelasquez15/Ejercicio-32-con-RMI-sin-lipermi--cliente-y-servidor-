package com.edad.rmi.net;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.edad.rmi.lib.DatosEdad;
import com.edad.rmi.lib.IRemotaCalculoEdad;

/**
 * Implementación del servicio remoto en RMI estándar de Java.
 * Extiende de UnicastRemoteObject para permitir exportar el objeto
 * y habilitar la recepción de llamadas remotas entrantes sobre TCP/IP.
 */
public class CalculoRmiEdadImplem extends UnicastRemoteObject implements IRemotaCalculoEdad {

    private static final long serialVersionUID = 1L;

    public CalculoRmiEdadImplem() throws RemoteException {
        super();
    }

    @Override
    public DatosEdad calcularDias(DatosEdad datos) throws RemoteException {
        if (datos == null) {
            datos = new DatosEdad();
            datos.setResultadoDias(0);
            datos.setMensaje("ERROR: Datos no proporcionados.");
            return datos;
        }

        if (datos.getEdad() <= 0) {
            datos.setResultadoDias(0);
            datos.setMensaje("ERROR: La edad debe ser un entero positivo mayor que cero.");
            System.out.println("[SERVIDOR] Solicitud rechazada: edad inválida (" + datos.getEdad() + ")");
            return datos;
        }

        // Ejercicio 32: Días vividos = edad en años * 365 días
        long dias = (long) datos.getEdad() * 365L;
        datos.setResultadoDias(dias);
        datos.setMensaje("Estimación aproximada considerando 365 días por año");

        System.out.println("[SERVIDOR] Solicitud atendida -> Edad: " + datos.getEdad() + " años | Días calculados: " + dias);
        return datos;
    }
}