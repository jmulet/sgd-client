/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.profesores;

 
import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class BeanFaltasProfesores {

    protected String idProfesores; //id profesor que falta
    protected HashMap<Integer, Integer> mapIdGrupAsigs;
    protected java.sql.Date fecha;
    protected java.sql.Time hora;
    protected int idHorasCentro;
    protected String idProfesores2; //id del profesor de guardia
    protected int idTipoIncidencias;
    protected String simbolo;
    protected String observaciones;
    protected String descripcion;
   
    public BeanFaltasProfesores()
    {
       mapIdGrupAsigs = new HashMap<Integer, Integer>();
             
    }

    public String getIdProfesores() {
        return idProfesores;
    }

    public void setIdProfesores(String idProfesores) {
        this.idProfesores = idProfesores;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public java.sql.Time getHora() {
        return hora;
    }

    public void setHora(java.sql.Time hora) {
        this.hora = hora;
    }

    public int getIdHorasCentro() {
        return idHorasCentro;
    }

    public void setIdHorasCentro(int idHorasCentro) {
        this.idHorasCentro = idHorasCentro;
    }

    public String getIdProfesores2() {
        return idProfesores2;
    }

    public void setIdProfesores2(String idProfesores2) {
        this.idProfesores2 = idProfesores2;
    }

    public int getIdTipoIncidencias() {
        return idTipoIncidencias;
    }

    public void setIdTipoIncidencias(int idTipoIncidencias) {
        this.idTipoIncidencias = idTipoIncidencias;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public HashMap<Integer, Integer> getMapIdGrupAsigs() {
        return mapIdGrupAsigs;
    }

    public void setMapIdGrupAsigs(HashMap<Integer, Integer> mapIdGrupAsigs) {
        this.mapIdGrupAsigs = mapIdGrupAsigs;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

 
}
