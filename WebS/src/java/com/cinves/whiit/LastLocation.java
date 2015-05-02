package com.cinves.whiit;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lastLocation", propOrder = {
    "location",
    "name",
    "update"
})
public class LastLocation implements Serializable {

    private String name;
    @XmlSchemaType(name = "dateTime")
    private Date update;
    private String location;

    public LastLocation(String name, Date update, String location) {
        this.location=location;
        this.update=update;
        this.name=name;
    }

    public LastLocation(){
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update=update;
    }

    public String getLocation() {
       return location;
    }

    public void setLocation(String location) {
        this.location=location;
    }
}