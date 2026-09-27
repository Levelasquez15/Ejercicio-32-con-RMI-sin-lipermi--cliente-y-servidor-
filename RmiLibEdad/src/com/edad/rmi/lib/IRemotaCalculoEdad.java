package com.edad.rmi.lib;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota estándar de Java RMI (sin librerías externas).
 * Define el contrato de métodos remotos para el cálculo de días vividos (Ejercicio 32).
 * 
 * Reglas fundamentales de RMI nativo:
 * 1. Debe extender obligatoriamente de java.rmi.Remote.
 * 2. Todos los métodos remotos deben declarar 'throws RemoteException'.
 */
public interface IRemotaCalculoEdad extends Remote {

    public DatosEdad calcularDias(DatosEdad datos) throws RemoteException;

}