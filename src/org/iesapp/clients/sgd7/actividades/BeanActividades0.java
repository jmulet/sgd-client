/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class BeanActividades0 implements java.io.Serializable {

    protected int idProfesores=0;
    protected HashMap<Integer, Integer> mapIds;  //mapa entre idActivitat - idGrupAsig
    protected int idClase;
    protected int idEvaluacionesDetalle;
    protected String descripcion = "";
    protected Date fecha;
    protected boolean publicarWEB;
    protected boolean seguimiento;
    protected boolean evaluable;
    protected int ordre;
    protected int totalasig;
    protected int peso;
    protected ArrayList<BeanActividadesAlumno> actividadesAlumno;

    public BeanActividades0() {
        mapIds = new HashMap<Integer, Integer>();
        actividadesAlumno = new ArrayList<BeanActividadesAlumno>();
    }

    public boolean isEvaluable() {
        return evaluable;
    }

    public void setEvaluable(boolean evaluable) {
        this.evaluable = evaluable;
    }

    public boolean isSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(boolean seguimiento) {
        this.seguimiento = seguimiento;
    }

    public boolean isPublicarWEB() {
        return publicarWEB;
    }

    public void setPublicarWEB(boolean publicarWEB) {
        this.publicarWEB = publicarWEB;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEvaluacionesDetalle() {
        return idEvaluacionesDetalle;
    }

    public void setIdEvaluacionesDetalle(int idEvaluacionesDetalle) {
        this.idEvaluacionesDetalle = idEvaluacionesDetalle;
    }

    /**
     * @return the mapIds
     */
    public HashMap<Integer, Integer> getMapIds() {
        return mapIds;
    }

    /**
     * @param mapIds the mapIds to set
     */
    public void setMapIds(HashMap<Integer, Integer> mapIds) {
        this.mapIds = mapIds;
    }

    /**
     * @return the ordre
     */
    public int getOrdre() {
        return ordre;
    }

    /**
     * @param ordre the ordre to set
     */
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }


    /**
     * @return the totalasig
     */
    public int getTotalasig() {
        
//        
//        totalasig = 0;
//        
//        for(int i=0; i<actividadesAlumno.size();i++)
//        {
//            if(actividadesAlumno.get(i).isSelected()) totalasig +=1;
//        }
        
        return totalasig;
    }

    /**
     * @param totalasig the totalasig to set
     */
    public void setTotalasig(int totalasig) {
        this.totalasig = totalasig;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
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
     * @return the idProfesores
     */
    public int getIdProfesores() {
        return idProfesores;
    }

    /**
     * @param idProfesores the idProfesores to set
     */
    public void setIdProfesores(int idProfesores) {
        this.idProfesores = idProfesores;
    }

    /**
     * @return the actividadesAlumno
     */
    public ArrayList<BeanActividadesAlumno> getActividadesAlumno() {
        return actividadesAlumno;
    }

    /**
     * @param actividadesAlumno the actividadesAlumno to set
     */
    public void setActividadesAlumno(ArrayList<BeanActividadesAlumno> actividadesAlumno) {
        this.actividadesAlumno = actividadesAlumno;
    }

 
}
