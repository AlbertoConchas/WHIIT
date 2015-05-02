/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinves.whiit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author bto
 */
@WebService(serviceName = "Whiit")
public class Whiit {

    List<LastLocation> locs = new ArrayList();

    
    @WebMethod(operationName = "inform")
    public void inform(@WebParam(name = "lastLocation") LastLocation loc) {
        if(locs.isEmpty()){
            locs.add(loc);
        }else{
            for (int i=0;i<locs.size();i++) {
                if(locs.get(i).getName().equals(loc.getName())){
                    locs.set(i, loc);
                    return;
                }
            }
            locs.add(loc);
        }
    }
    @WebMethod(operationName = "consult")
    public LastLocation consult(@WebParam(name = "name") String name) {
        for (LastLocation loc : locs) {
            if(loc.getName().equals(name))return loc;
        }
        return null;
    }
    @WebMethod(operationName = "list")
    public List<LastLocation> list() {
        return new ArrayList(locs);
    }
    
}
