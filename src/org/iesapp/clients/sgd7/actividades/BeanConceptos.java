/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

/**
 *
 * @author Josep
 */
public class BeanConceptos {
    protected int id=-1;
    protected int idClase;
    protected String idProfesores;
    protected String htmlColor="000000";
    protected String nombre="Global";
    protected int porcentaje;
    protected String textoActividad="";
    protected boolean evaluable=false;
    protected boolean web=true;

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
     * @return the htmlColor
     */
    public String getHtmlColor() {
        return htmlColor;
    }

    /**
     * @param htmlColor the htmlColor to set
     */
    public void setHtmlColor(String htmlColor) {
        this.htmlColor = htmlColor;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the porcentaje
     */
    public int getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * @return the textoActividad
     */
    public String getTextoActividad() {
        return textoActividad;
    }

    /**
     * @param textoActividad the textoActividad to set
     */
    public void setTextoActividad(String textoActividad) {
        this.textoActividad = textoActividad;
    }

    /**
     * @return the evaluable
     */
    public boolean isEvaluable() {
        return evaluable;
    }

    /**
     * @param evaluable the evaluable to set
     */
    public void setEvaluable(boolean evaluable) {
        this.evaluable = evaluable;
    }

    /**
     * @return the web
     */
    public boolean isWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(boolean web) {
        this.web = web;
    }

    /**
     * @return the idProfesores
     */
    public String getIdProfesores() {
        return idProfesores;
    }

    /**
     * @param idProfesores the idProfesores to set
     */
    public void setIdProfesores(String idProfesores) {
        this.idProfesores = idProfesores;
    }
 
 
}
