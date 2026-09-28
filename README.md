# Ejercicio 32: Cálculo de edad en días con Java RMI Estándar (Sin librerías)

---

## ¿De qué trata el proyecto?

Este proyecto corresponde a la solución del **Ejercicio 32** desarrollado únicamente con **Java RMI nativo** (el paquete estándar `java.rmi.*` que viene con el JDK), sin utilizar librerías externas de terceros como LipeRMI.

El objetivo del ejercicio es comunicar un cliente y un servidor para realizar el cálculo de los días vividos por una persona:
1. El cliente ingresa su edad en años (por ejemplo, `20`).
2. Esa edad se envía al servidor mediante una llamada a método remoto.
3. El servidor realiza el cálculo multiplicando los años por 365 días (`días = edad * 365`).
4. El servidor responde al cliente con los días calculados y un mensaje, mostrándolo en la ventana gráfica.

---

## Estructura de los proyectos en NetBeans

Siguiendo la metodología trabajada en clase, el ejercicio se organizó en 3 proyectos de NetBeans (Java with Ant):

* **RmiLibEdad (Librería de Clases):**
  Es el proyecto común que comparten el cliente y el servidor. Contiene:
  * `DatosEdad.java`: Clase que sirve de transporte para los datos (edad, resultado en días y mensaje). Implementa `Serializable` para poder viajar por la red.
  * `IRemotaCalculoEdad.java`: La interfaz remota que define el método `calcularDias(...)`.

* **RmiServidorEdad (Aplicación Servidor):**
  Es el programa de consola que atiende las peticiones. Contiene:
  * `CalculoRmiEdadImplem.java`: La implementación donde se hace la multiplicación `edad * 365`. Hereda de `UnicastRemoteObject`.
  * `Servidor.java`: Se encarga de iniciar el registro RMI en el puerto 9007 (`LocateRegistry.createRegistry`) y publicar el servicio con el nombre `"CalculoEdadRemoto"`.
  * `Principal.java`: Clase con el método `main` para arrancar el servidor y mantenerlo escuchando.

* **RmiClienteEdad (Aplicación Cliente con Interfaz Gráfica):**
  Es la aplicación visual en Swing para el usuario final. Contiene:
  * `VentanaPrincipal.java`: Formulario con pestañas para "CONEXION" y "CALCULAR EDAD". Se conecta al servidor mediante `LocateRegistry.getRegistry(...).lookup(...)` y hace la petición en un hilo secundario (`Thread`) para no congelar la ventana.
  * `Principal.java`: Clase principal que inicia la ventana.

---

## ¿Qué diferencias tiene con la versión de LipeRMI?

Al trabajar con **RMI puro de Java** (sin ninguna librería), el lenguaje nos exige varias cosas que con LipeRMI no hacían falta:

1. **La interfaz remota:** Debe extender obligatoriamente de `java.rmi.Remote`.
2. **Las excepciones:** Cada método de la interfaz remota tiene que declarar obligatoriamente `throws RemoteException` para contemplar posibles fallos en la comunicación.
3. **La clase del servidor:** Debe heredar de `UnicastRemoteObject` (o exportarse manualmente) para poder recibir llamadas remotas sobre TCP.
4. **El registro de nombres:** Se utiliza el registro nativo de Java (`rmiregistry` / `LocateRegistry`) para registrar el servicio con un nombre y para que el cliente lo busque con `lookup`.
5. **Cero dependencias:** No se necesita agregar ningún archivo `.jar` externo en las librerías del proyecto, todo corre directamente con el JDK.

---

## Cómo ejecutar el proyecto en NetBeans

1. Abrir NetBeans y cargar los 3 proyectos (`RmiLibEdad`, `RmiServidorEdad` y `RmiClienteEdad`) usando **File > Open Project...**.
2. Hacer clic derecho sobre **RmiLibEdad** y darle a **Clean and Build** para generar el archivo JAR de la librería.
3. Hacer clic derecho sobre **RmiServidorEdad** y darle a **Run**. En la consola de abajo aparecerá el mensaje indicando que el servidor está iniciado y esperando peticiones en el puerto 9007.
4. Hacer clic derecho sobre **RmiClienteEdad** y darle a **Run**.
5. En la ventana del cliente:
   * En la pestaña **CONEXION**, presionar el botón **Conectar** (el estado cambiará a verde: *Conectado*).
   * Pasar a la pestaña **CALCULAR EDAD**, digitar una edad (por ejemplo: `20`) y hacer clic en **CALCULAR**.
   * Se mostrarán los días calculados (`7300 días`) tanto en pantalla como en la consola.
