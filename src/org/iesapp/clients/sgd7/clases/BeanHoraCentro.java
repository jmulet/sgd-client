/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

import java.sql.Time;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class BeanHoraCentro {
    protected int idHoraClase = 0;
    protected String descripcion="";
    protected java.sql.Time inicio;
    protected java.sql.Time fin;

    
    public BeanHoraCentro(int aInt, Time time0, Time time1) {
        this.idHoraClase = aInt;
        this.inicio = time0;
        this.fin = time1;
        this.descripcion = StringUtils.formatTime(time0)+"-"+StringUtils.formatTime(time1);
    }

    public int getIdHoraClase() {
        return idHoraClase;
    }

    public void setIdHoraClase(int idHoraClase) {
        this.idHoraClase = idHoraClase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public java.sql.Time getInicio() {
        return inicio;
    }

    public void setInicio(java.sql.Time inicio) {
        this.inicio = inicio;
    }

    public java.sql.Time getFin() {
        return fin;
    }

    public void setFin(java.sql.Time fin) {
        this.fin = fin;
    }
}
