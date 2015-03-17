/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.deskapp.listener;

import com.cinves.deskapp.DeskApp;
import com.cinves.whitt.LastLocation;
import com.cinves.whitt.Update;
import static com.sun.medialib.mlib.Image.Log;
import static com.sun.medialib.mlib.Image.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bto Conchas
 */
public class Listener implements Runnable {

    private final LastLocation yo;
    private final InetAddress grupo;
    private final MulticastSocket socket;
    //private final DatagramSocket socket2;

    public Listener(int port) throws IOException {

        yo = new LastLocation(getLocalAddress(), new Date(), "biblioteca");
        grupo = InetAddress.getByName("228.5.6.7");
        socket = new MulticastSocket(port);
        //socket2 = new DatagramSocket();
    }

    @Override
    public void run() {
        try {
            socket.joinGroup(grupo);
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true) {
            try {
                // Se une al grupo
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
                        //DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, mensajeEntrada.getAddress(), 6000);
                        //socket2.send(mensajeSalida);
                        System.out.println("Aki se supone que se contesta el mensaje");
                    }
                }

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error al generar socket " + ex);
            }
        }
    }
    
    private String getLocalAddress() {
            String localAddress = "";
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                            localAddress += inetAddress.getHostAddress() + ",";
                        }

                    }
                }
            } catch (SocketException ex) {
                System.out.println(ex.getMessage());
            }
            String[] cadenas = localAddress.split(",");
            if (cadenas.length == 1) {
                return cadenas[0];
            } else return "Sin ip";
        }

    @Override
    protected void finalize() throws Throwable {
        if(socket!=null)socket.close();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
