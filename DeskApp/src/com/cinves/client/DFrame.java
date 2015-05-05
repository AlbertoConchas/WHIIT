/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.client;

import com.cinves.deskapp.DeskApp;
import com.cinves.whitt.LastLocation;
import com.cinves.whitt.Update;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alberto
 */
public final class DFrame extends javax.swing.JFrame {

    private final InetAddress grupo;
    private MulticastSocket socket;
    DefaultListModel resultList;

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
        resultList.clear();
        jButton1.setEnabled(false);
        socket = new MulticastSocket(6000);
        socket.setSoTimeout(10000);
        byte[] m = DeskApp.serialize(new Update(3, "Beto"));
        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, grupo, 5000);
        socket.send(mensajeSalida);
        
        (new Thread(new Wait())).start();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        usrs = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resultList = new DefaultListModel();
        usrs.setModel(resultList);
        jScrollPane2.setViewportView(usrs);

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cinvestav/resource/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 226, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            update();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("No se pudo actualizar "+ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    class Wait implements Runnable {

        @Override
        public void run() {
            System.out.println("Actualizando");
            while (true) {
                
                byte[] bufer = new byte[1000];
                DatagramPacket mensajeEntrada = new DatagramPacket(bufer, bufer.length);
                try {
                    socket.receive(mensajeEntrada);
                    Object obj = DeskApp.deserialize(mensajeEntrada.getData());
                    if (obj instanceof LastLocation) {
                        LastLocation loc = (LastLocation) obj;
                        resultList.addElement(loc.toString());
                    }
                } catch (SocketTimeoutException ex) {
                    System.out.println("Fin actualización Time Out.");        
                    jButton1.setEnabled(true);
                    socket.close();
                    break;
                } catch (IOException   |ClassNotFoundException ex) {
                    System.err.println("fallo al actualizar " +ex);
                    socket.close();
                    break;
                }

            }
            jButton1.setEnabled(true);
        }

    }
 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList usrs;
    // End of variables declaration//GEN-END:variables
}
