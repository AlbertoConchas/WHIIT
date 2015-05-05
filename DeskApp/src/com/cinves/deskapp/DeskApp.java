/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.deskapp;

import com.cinves.client.DFrame;
import com.cinves.deskapp.listener.Listener;
import com.cinves.deskapp.listener.WSListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author alberto
 */
public class DeskApp {
    private static final String endpoint="http://localhost:8080/WebS/Whiit?wsdl";
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public static void main(String[] args) throws IOException, DatatypeConfigurationException {
        // TODO code application logic here

        if (args.length>0 && args[0].equals("server")) {
            System.out.println("Ejecutando server");
            (new Thread(new Listener(5000))).start();
            (new Thread(new WSListener(endpoint))).start();
        } else {
            System.out.println("Ejecutando cliente");
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new DFrame(endpoint).setVisible(true);
                    } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Error: " + ex);
                    }
                }
            });
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
