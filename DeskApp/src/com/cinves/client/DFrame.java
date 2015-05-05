/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.client;

import com.cinves.deskapp.DeskApp;
import com.cinves.whitt.LastLocation;
import com.cinves.whitt.Update;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author alberto
 */
public final class DFrame extends javax.swing.JFrame {

    private final InetAddress grupo;
    private MulticastSocket socket;
    DefaultListModel resultList;
    DefaultListModel resultWSList;
    private static String endpointURL;
    /**
     * Creates new form DFrame
     *
     * @param endpoint
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public DFrame(String endpoint) throws IOException, ClassNotFoundException {
        initComponents();
        endpointURL = endpoint;
        grupo = InetAddress.getByName("228.5.6.7");
        update();
        updateWs();

    }

    public void update() throws IOException, ClassNotFoundException {
        resultList.clear();
        jButton1.setEnabled(false);
        socket = new MulticastSocket(6000);
        socket.setSoTimeout(2000);
        byte[] m = DeskApp.serialize(new Update(3, "Beto"));
        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, grupo, 5000);
        socket.send(mensajeSalida);
        
        (new Thread(new Wait())).start();

    }
    private void updateWs() {
        resultWSList.clear();
        jButton1.setEnabled(false);
        List<com.cinves.whiit.LastLocation> list = listar();
        for (com.cinves.whiit.LastLocation list1 : list) {
            resultWSList.addElement(list1.toString());
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        usrs = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        usrs1 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resultWSList = new DefaultListModel();
        usrs.setModel(resultWSList);
        jScrollPane2.setViewportView(usrs);

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cinvestav/resource/logo.png"))); // NOI18N

        jLabel2.setText("Sockets");

        resultList = new DefaultListModel();
        usrs1.setModel(resultList);
        jScrollPane3.setViewportView(usrs1);

        jLabel3.setText("Web Service");

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(44, 44, 44)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(533, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jScrollPane3)
                    .addGap(38, 38, 38)))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            updateWs();
        } catch (Exception ex) {
            System.err.println("No se pudo actualizar "+ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList usrs;
    private javax.swing.JList usrs1;
    // End of variables declaration//GEN-END:variables

    private static List<com.cinves.whiit.LastLocation> listar() {
        com.cinves.whiit.Whiit_Service service = new com.cinves.whiit.Whiit_Service();
        com.cinves.whiit.Whiit port = service.getWhiitPort();
        
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
        
        return port.list();
    }
}
