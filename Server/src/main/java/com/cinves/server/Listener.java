/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.server;

import com.cinves.whiit.LastLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Bto Conchas
 */

public class Listener implements Runnable {

    private final ServerSocket sSocket;
    private Socket socket;
    private PrintStream output;

    public Listener(int port) throws IOException {
        sSocket = new ServerSocket(port);
        socket = new Socket();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("esperando conexion");
                socket = sSocket.accept();

                System.out.println("se han conectado");

                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                output = new PrintStream(socket.getOutputStream());

                LastLocation l = (LastLocation) input.readObject();
                System.out.println("mensaje: ");
                System.out.println(l);
                System.out.println("----------------------------------------------------------------------");
                output.println("mensaje recibido");
                
                input.close();
                output.close();

            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error al generar socket " + ex);
        }
    }

}
