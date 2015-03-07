/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Bto Conchas
 */

public class Listener implements Runnable{
private final ServerSocket sSocket;
private Socket socket;
private String message;
private BufferedReader reader;
private PrintStream output;

    public Listener(int port ) throws IOException{
        sSocket = new ServerSocket(port);
        socket =new Socket();
    }
    
    @Override
    public void run() {
    try {
        while(true){
        System.out.println("esperando conexion");
        socket = sSocket.accept();
        
        System.out.println("se han conectado");
        
        
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output= new PrintStream( socket.getOutputStream()); 
        
        message =  reader.readLine();
        System.out.println("mensaje: ");
        System.out.println(message);
         output.println("mensaje recibido");  
         
         reader.close();
         output.close();
        
        }
    } catch (IOException ex) {
        System.out.println("Error al generar socket "+ex);
    }
    }
    
}
