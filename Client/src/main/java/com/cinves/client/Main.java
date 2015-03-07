/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Bto Conchas
 */
public class Main {
    private static final int PORT =5000;
    private static final String HOST="localhost";
    private static Socket so;
    private static PrintStream output;
    private static String input;
    
    public static void main(String[] args) throws IOException {
        so = new Socket(HOST,PORT);
        output = new PrintStream(so.getOutputStream());
        output.println("que ondas!");

        so.close();
    }
            
}
