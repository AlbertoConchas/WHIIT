/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.server;

import java.io.IOException;

/**
 *
 * @author Bto Conchas
 */


public class Main {
private static final int PORT= 5000;    
    
    public static void main(String[] args) throws IOException {
     
        (new Thread(new Listener(PORT))).start();
        
    }
}
