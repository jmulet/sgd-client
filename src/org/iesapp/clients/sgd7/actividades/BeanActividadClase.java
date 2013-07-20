/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class BeanActividadClase extends BeanActividadBasic {

  
    protected HashMap<Integer, Integer> mapGrupAct;
    protected ArrayList<BeanActividadesAlumno> assignacions;
    protected int idClase;
    protected int totalasig;
  

    public BeanActividadClase() {
        mapGrupAct = new HashMap<Integer, Integer>();
        assignacions = new ArrayList<BeanActividadesAlumno>();
    }

    /**
     * @return the mapGrupAct
     */
    public HashMap<Integer, Integer> getMapGrupAct() {
        return mapGrupAct;
    }

    /**
     * @param mapGrupAct the mapGrupAct to set
     */
    public void setMapGrupAct(HashMap<Integer, Integer> mapGrupAct) {
        this.mapGrupAct = mapGrupAct;
    }

    /**
     * @return the assignacions
     */
    public ArrayList<BeanActividadesAlumno> getAssignacions() {
        return assignacions;
    }

    /**
     * @param assignacions the assignacions to set
     */
    public void setAssignacions(ArrayList<BeanActividadesAlumno> assignacions) {
        this.assignacions = assignacions;
    }

    /**
     * @return the idClase
     */
    public int getIdClase() {
        return idClase;
    }

    /**
     * @param idClase the idClase to set
     */
    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    /**
     * @return the totalasig
     */
    public int getTotalasig() {
        return totalasig;
    }

    /**
     * @param totalasig the totalasig to set
     */
    public void setTotalasig(int totalasig) {
        this.totalasig = totalasig;
    }
 
   
}
