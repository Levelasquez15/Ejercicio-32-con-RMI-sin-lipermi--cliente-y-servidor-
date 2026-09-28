package com.edad.rmi.vistas;

import java.awt.Color;
import java.awt.Font;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.edad.rmi.lib.DatosEdad;
import com.edad.rmi.lib.IRemotaCalculoEdad;

/**
 * Formulario Swing para la aplicación Cliente RMI nativo (Estándar de Java, sin librerías externas).
 * Utiliza LocateRegistry.getRegistry(host, port).lookup(...) para obtener el stub remoto.
 */
public class VentanaPrincipal extends JFrame {

    private static final String NOMBRE_SERVICIO = "CalculoEdadRemoto";

    private String ipServidor = "localhost";
    private int puerto = 9007;
    private IRemotaCalculoEdad calculoEdadRemoto;

    // Componentes de la interfaz
    private JLabel lblTitulo;
    private JTabbedPane jTabbedPane1;

    // Pestaña 1: Conexión
    private JPanel panelConexion;
    private JLabel lblIP;
    private JTextField campoIPServidor;
    private JLabel lblPuerto;
    private JTextField campoPuertoServidor;
    private JLabel lblEstado;
    private JLabel txtEstado;
    private JButton btnIniciar;

    // Pestaña 2: Cálculo de Edad
    private JPanel panelCalculo;
    private JLabel lblEdad;
    private JTextField campoEdad;
    private JButton btnCalcular;
    private JLabel lblResultado;
    private JLabel txtResultado;
    private JLabel txtMensaje;

    public VentanaPrincipal() {
        initComponents();
        setTitle("CLIENTE EDAD - RMI (NATIVO)");
        setSize(460, 310);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        lblTitulo = new JLabel("CLIENTE EDAD (RMI ESTÁNDAR)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(20, 15, 410, 30);
        add(lblTitulo);

        jTabbedPane1 = new JTabbedPane();
        jTabbedPane1.setBounds(20, 55, 410, 205);

        // --- Panel 1: CONEXIÓN ---
        panelConexion = new JPanel();
        panelConexion.setLayout(null);
        panelConexion.setBackground(Color.WHITE);

        lblIP = new JLabel("DIRECCIÓN IP:");
        lblIP.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIP.setBounds(25, 25, 120, 25);
        panelConexion.add(lblIP);

        campoIPServidor = new JTextField("localhost");
        campoIPServidor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        campoIPServidor.setBounds(155, 25, 210, 25);
        panelConexion.add(campoIPServidor);

        lblPuerto = new JLabel("PUERTO DE RED:");
        lblPuerto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPuerto.setBounds(25, 65, 120, 25);
        panelConexion.add(lblPuerto);

        campoPuertoServidor = new JTextField("9007");
        campoPuertoServidor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        campoPuertoServidor.setBounds(155, 65, 210, 25);
        panelConexion.add(campoPuertoServidor);

        lblEstado = new JLabel("ESTADO:");
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEstado.setBounds(25, 105, 120, 25);
        panelConexion.add(lblEstado);

        txtEstado = new JLabel("Desconectado");
        txtEstado.setFont(new Font("Tahoma", Font.BOLD, 13));
        txtEstado.setForeground(new Color(255, 0, 51));
        txtEstado.setBounds(155, 105, 210, 25);
        panelConexion.add(txtEstado);

        btnIniciar = new JButton("Conectar");
        btnIniciar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnIniciar.setForeground(new Color(0, 153, 51));
        btnIniciar.setBounds(155, 140, 130, 30);
        btnIniciar.addActionListener(this::btnIniciarActionPerformed);
        panelConexion.add(btnIniciar);

        jTabbedPane1.addTab("CONEXION", panelConexion);

        // --- Panel 2: CALCULAR EDAD ---
        panelCalculo = new JPanel();
        panelCalculo.setLayout(null);
        panelCalculo.setBackground(Color.WHITE);

        lblEdad = new JLabel("EDAD (AÑOS):");
        lblEdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEdad.setBounds(25, 20, 100, 25);
        panelCalculo.add(lblEdad);

        campoEdad = new JTextField();
        campoEdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        campoEdad.setBounds(125, 20, 120, 25);
        panelCalculo.add(campoEdad);

        btnCalcular = new JButton("CALCULAR");
        btnCalcular.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCalcular.setBounds(260, 20, 120, 25);
        btnCalcular.addActionListener(this::btnCalcularActionPerformed);
        panelCalculo.add(btnCalcular);

        lblResultado = new JLabel("DÍAS VIVIDOS:");
        lblResultado.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblResultado.setBounds(25, 65, 100, 25);
        panelCalculo.add(lblResultado);

        txtResultado = new JLabel("0 días");
        txtResultado.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtResultado.setForeground(new Color(255, 0, 51));
        txtResultado.setBounds(125, 65, 260, 25);
        panelCalculo.add(txtResultado);

        txtMensaje = new JLabel("Ingrese una edad en años y presione Calcular.");
        txtMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtMensaje.setBorder(BorderFactory.createTitledBorder("Información"));
        txtMensaje.setBounds(25, 105, 360, 60);
        panelCalculo.add(txtMensaje);

        jTabbedPane1.addTab("CALCULAR EDAD", panelCalculo);

        add(jTabbedPane1);
    }

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (btnIniciar.getText().equalsIgnoreCase("Conectar")) {
                puerto = Integer.parseInt(campoPuertoServidor.getText().trim());
                ipServidor = campoIPServidor.getText().trim();

                // Conexión RMI nativa mediante LocateRegistry
                Registry registro = LocateRegistry.getRegistry(ipServidor, puerto);
                calculoEdadRemoto = (IRemotaCalculoEdad) registro.lookup(NOMBRE_SERVICIO);

                btnIniciar.setText("Desconectar");
                btnIniciar.setForeground(new Color(255, 0, 51));
                txtEstado.setText("Conectado");
                txtEstado.setForeground(new Color(0, 153, 51));

                campoIPServidor.setEnabled(false);
                campoPuertoServidor.setEnabled(false);
            } else if (btnIniciar.getText().equalsIgnoreCase("Desconectar")) {
                calculoEdadRemoto = null;
                btnIniciar.setText("Conectar");
                btnIniciar.setForeground(new Color(0, 153, 51));
                txtEstado.setText("Desconectado");
                txtEstado.setForeground(new Color(255, 0, 51));

                campoIPServidor.setEnabled(true);
                campoPuertoServidor.setEnabled(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El puerto debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR AL CONECTAR al servidor RMI:\n" + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            System.out.println("ERROR AL CONECTAR");
            ex.printStackTrace();
        }
    }

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {
        if (calculoEdadRemoto == null) {
            JOptionPane.showMessageDialog(this, "Primero debe conectarse al servidor en la pestaña CONEXIÓN.", "Sin Conexión", JOptionPane.WARNING_MESSAGE);
            jTabbedPane1.setSelectedIndex(0);
            return;
        }

        String textoEdad = campoEdad.getText().trim();
        if (textoEdad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la edad en años.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final int edad;
        try {
            edad = Integer.parseInt(textoEdad);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ejecutar llamada remota en hilo secundario
        Thread hilo = new Thread(() -> {
            try {
                System.out.println("Edad: " + edad);
                DatosEdad datos = new DatosEdad();
                datos.setEdad(edad);
                System.out.println("Enviados los datos\nEsperando respuesta");

                datos = calculoEdadRemoto.calcularDias(datos);

                final DatosEdad datosRetorno = datos;
                System.out.println("Días: " + datosRetorno.getResultadoDias() + "\nMensaje: " + datosRetorno.getMensaje());
                SwingUtilities.invokeLater(() -> {
                    txtResultado.setText(datosRetorno.getResultadoDias() + " días");
                    txtMensaje.setText("<html>" + datosRetorno.getMensaje() + "</html>");
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "ERROR con el cliente " + ex.getMessage());
                });
                System.out.println("ERROR con el cliente " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        hilo.start();
    }
}