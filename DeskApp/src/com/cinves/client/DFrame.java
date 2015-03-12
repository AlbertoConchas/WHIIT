/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.client;

import com.cinves.deskapp.DeskApp;
import com.cinves.whiit.LastLocation;
import com.cinves.whiit.Update;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alberto
 */
public final class DFrame extends javax.swing.JFrame {

    private final InetAddress grupo;
    private MulticastSocket socket;

    /**
     * Creates new form DFrame
     *
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public DFrame() throws IOException, ClassNotFoundException {
        initComponents();
        grupo = InetAddress.getByName("228.5.6.7");
        update();

    }

    public void update() throws IOException, ClassNotFoundException {
        socket = new MulticastSocket(6000);
        byte[] m = DeskApp.serialize(new Update(3, "aa"));
        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, grupo, 5000);
        socket.send(mensajeSalida);
        
        (new Thread(new Wait())).start();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textS = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textS.setColumns(20);
        textS.setRows(5);
        jScrollPane1.setViewportView(textS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    class Wait implements Runnable {

        @Override
        public void run() {
            while (true) {

                byte[] bufer = new byte[1000];
                DatagramPacket mensajeEntrada = new DatagramPacket(bufer, bufer.length);
                try {
                    socket.receive(mensajeEntrada);
                    Object obj = DeskApp.deserialize(mensajeEntrada.getData());
                    if (obj instanceof LastLocation) {
                        LastLocation loc = (LastLocation) obj;
                        textS.setText(textS.getText() + loc.toString());
                    }
                } catch (IOException   |ClassNotFoundException ex) {
                    Logger.getLogger(DFrame.class.getName()).log(Level.SEVERE, null, ex);
                } 

            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textS;
    // End of variables declaration//GEN-END:variables
}
