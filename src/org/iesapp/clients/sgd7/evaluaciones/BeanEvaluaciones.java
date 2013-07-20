/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.evaluaciones;

import java.sql.Date;

/**
 *
 * @author Josep
 */
public class BeanEvaluaciones implements java.io.Serializable{
    
    
    private int id;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaInicioReal;
    private Date fechaFinReal;
    private String valorExportable="";

    public String getValorExportable() {
        return valorExportable;
    }

    public void setValorExportable(String valorExportable) {
        this.valorExportable = valorExportable;
    }


    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }


    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fechaInicioReal
     */
    public Date getFechaInicioReal() {
        return fechaInicioReal;
    }

    /**
     * @param fechaInicioReal the fechaInicioReal to set
     */
    public void setFechaInicioReal(Date fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }

    /**
     * @return the fechaFinReal
     */
    public Date getFechaFinReal() {
        return fechaFinReal;
    }

    /**
     * @param fechaFinReal the fechaFinReal to set
     */
    public void setFechaFinReal(Date fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

}
