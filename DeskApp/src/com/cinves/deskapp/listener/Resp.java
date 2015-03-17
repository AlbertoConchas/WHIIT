/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.deskapp.listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author alberto
 */
public class Resp implements Runnable {
    private final DatagramSocket socket2;
    private final byte[] mensaje;
    private final InetAddress entrada;

    public Resp(byte[] mensaje,InetAddress entrada) throws SocketException {
        this.socket2 = new DatagramSocket();
        this.entrada=entrada;
        this.mensaje=mensaje;
    }
    
    @Override
    public void run() {
        DatagramPacket mensajeSalida = new DatagramPacket(mensaje, mensaje.length, entrada, 6000);
        try {
            socket2.send(mensajeSalida);
        } catch (IOException ex) {
            System.err.println("No se pudo enviar la localizacion. "+ex);
        }finally{
            socket2.close();
        }
    }
    
}
