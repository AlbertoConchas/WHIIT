
package com.cinves.whiit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para inform complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="inform">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastLocation" type="{http://whiit.cinves.com/}lastLocation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inform", propOrder = {
    "lastLocation"
})
public class Inform {

    protected LastLocation lastLocation;

    /**
     * Obtiene el valor de la propiedad lastLocation.
     * 
     * @return
     *     possible object is
     *     {@link LastLocation }
     *     
     */
    public LastLocation getLastLocation() {
        return lastLocation;
    }

    /**
     * Define el valor de la propiedad lastLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LastLocation }
     *     
     */
    public void setLastLocation(LastLocation value) {
        this.lastLocation = value;
    }

}
