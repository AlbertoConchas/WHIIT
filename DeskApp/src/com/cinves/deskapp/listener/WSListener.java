/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.deskapp.listener;

import com.cinves.whiit.LastLocation;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Bto Conchas
 */
public class WSListener implements Runnable {

    private final LastLocation yo;
    private static String endpointURL;

    public WSListener(String endpoint) throws IOException, DatatypeConfigurationException {
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar() );
        yo = new LastLocation();
        yo.setName("Beto");
        yo.setUpdate(date);
        yo.setLocation("Biblioteca");
        endpointURL = endpoint;
    }

    @Override
    public void run() {

        while (true) {
            inform(yo);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                System.err.println("Fallo sleep");
            }
        }
    }

    private static void inform(com.cinves.whiit.LastLocation lastLocation) {
        com.cinves.whiit.Whiit_Service service = new com.cinves.whiit.Whiit_Service();
        
        com.cinves.whiit.Whiit port = service.getWhiitPort();
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
        port.inform(lastLocation);
    }
     

}
