/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.whiit;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Bto Conchas
 */
public class LastLocation implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    private String name;
    private Date update;
    private String location;
    
    public LastLocation(){
    }

    public LastLocation(String name,Date update, String location){
        this.name = name;
        this.update = update;
        this.location=location;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    @Override
    public String toString(){
        return "Localización: "+location+" Ultimo registro: "+ update.toString() +" Nombre: "+name;
    }
    
}