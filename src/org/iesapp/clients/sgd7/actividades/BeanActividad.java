/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

/**
 *
 * @author Josep
 */
public class BeanActividad extends BeanActividadBasic {

    protected int id=0;
    protected int idGrupAsig;  

  
    public BeanActividad() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idGrupAsig
     */
    public int getIdGrupAsig() {
        return idGrupAsig;
    }

    /**
     * @param idGrupAsig the idGrupAsig to set
     */
    public void setIdGrupAsig(int idGrupAsig) {
        this.idGrupAsig = idGrupAsig;
    }
 

 
}
