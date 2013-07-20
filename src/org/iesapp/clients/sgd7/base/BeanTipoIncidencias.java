/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.base;

/**
 *
 * @author Josep
 */
public class BeanTipoIncidencias {    
    private int id;
    private String simbolo;
    private String descripcion;
    private boolean visibleUp;
    private String tipo;

    /**
     * @return the visibleUp
     */
    public boolean isVisibleUp() {
        return visibleUp;
    }

    /**
     * @param visibleUp the visibleUp to set
     */
    public void setVisibleUp(boolean visibleUp) {
        this.visibleUp = visibleUp;
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
     * @return the simbolo
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @param simbolo the simbolo to set
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
