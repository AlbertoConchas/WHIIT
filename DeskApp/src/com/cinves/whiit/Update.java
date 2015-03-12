/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.whiit;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class Update implements Serializable{
    private final int token;
    private final String name;
    public Update(int token,String name){
        
        this.token=token;
        this.name=name;
        
    }
    
   public int getToket(){
       return this.token;
   } 
   public String getName(){
       return this.name;
   } 
}
