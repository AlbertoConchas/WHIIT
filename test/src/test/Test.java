/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.cinves.whiit.LastLocation;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author bto
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public static void main(String[] args) throws DatatypeConfigurationException {
        // TODO code application logic here
        
        LastLocation l = new LastLocation();
        
        l.setLocation("here!");
        l.setName("Beto3");
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar() );
        l.setUpdate(date);
        inform(l);
        List<LastLocation> t =list("");
        
        
        for (LastLocation t1 : t) {
            System.out.println(t1);
        }
        //System.out.println(hello("aaa "));
    }

    private static LastLocation consult(java.lang.String name) {
        com.cinves.whiit.Whiit_Service service = new com.cinves.whiit.Whiit_Service();
        com.cinves.whiit.Whiit port = service.getWhiitPort();
        return port.consult(name);
    }

    private static String hello(java.lang.String name) {
        com.cinves.whiit.Whiit_Service service = new com.cinves.whiit.Whiit_Service();
        com.cinves.whiit.Whiit port = service.getWhiitPort();
        return port.hello(name);
    }

    private static void inform(com.cinves.whiit.LastLocation lastLocation) {
        com.cinves.whiit.Whiit_Service service = new com.cinves.whiit.Whiit_Service();
        com.cinves.whiit.Whiit port = service.getWhiitPort();
        port.inform(lastLocation);
    }

    private static List<LastLocation> list(java.lang.String name) {
        com.cinves.whiit.Whiit_Service service = new com.cinves.whiit.Whiit_Service();
        com.cinves.whiit.Whiit port = service.getWhiitPort();
        return port.list();
    }


}
