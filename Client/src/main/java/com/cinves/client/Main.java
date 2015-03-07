/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.client;

import com.cinves.whiit.LastLocation;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Bto Conchas
 */
public class Main {
    private static final int PORT =5000;
    private static final String HOST="localhost";
    private static Socket so;
    private static ObjectOutputStream output;
    
    public static void main(String[] args) throws IOException {
        so = new Socket(HOST,PORT);
        output = new ObjectOutputStream(so.getOutputStream());
        
        output.writeObject(new LastLocation("yo ",new Date(),"biblioteca"));

        so.close();
    }
            
}
