/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.deskapp.listener;

import com.cinves.deskapp.DeskApp;
import com.cinves.whiit.Update;
import com.cinves.whiit.LastLocation;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

/**
 *
 * @author Bto Conchas
 */
public class Listener implements Runnable {

    private final LastLocation yo;
    private final InetAddress grupo;
    private final MulticastSocket socket;

    public Listener(int port) throws IOException {

        yo = new LastLocation("yo ", new Date(), "biblioteca");
        grupo = InetAddress.getByName("228.5.6.7");
        socket = new MulticastSocket(port);
    }

    @Override
    public void run() {
        try {
            while (true) {

                // Se une al grupo
                socket.joinGroup(grupo);

                byte[] bufer = new byte[1000];
                DatagramPacket mensajeEntrada = new DatagramPacket(bufer, bufer.length);
                socket.receive(mensajeEntrada);
                Object obj = DeskApp.deserialize(mensajeEntrada.getData());

                if (obj instanceof Update) {
                    System.out.println("Update recibido por: " + mensajeEntrada.getAddress());
                    Update auth = (Update) obj;
                    if (!auth.getName().equals(yo.getName())) {
                        
                        yo.setUpdate(new Date());
                        byte[] m = DeskApp.serialize(yo);
                        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, grupo, 6000);
                        socket.send(mensajeSalida);
                    }
                }
            }
    }
    catch (IOException | ClassNotFoundException ex

    
        ) {
            System.out.println("Error al generar socket " + ex);
    }
}



}
