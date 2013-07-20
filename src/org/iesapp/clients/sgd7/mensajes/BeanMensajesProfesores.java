/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

/**
 *
 * @author Josep
 */
public class BeanMensajesProfesores {    
    
    protected int id=-1;
    protected int idMensajes;
    protected int idProfesores;
    protected java.sql.Date fechaEnviado;
    protected java.sql.Date fechaLeido;
    protected boolean borradoUp=false;

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
     * @return the idMensajes
     */
    public int getIdMensajes() {
        return idMensajes;
    }

    /**
     * @param idMensajes the idMensajes to set
     */
    public void setIdMensajes(int idMensajes) {
        this.idMensajes = idMensajes;
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
     * @return the fechaEnviado
     */
    public java.sql.Date getFechaEnviado() {
        return fechaEnviado;
    }

    /**
     * @param fechaEnviado the fechaEnviado to set
     */
    public void setFechaEnviado(java.sql.Date fechaEnviado) {
        this.fechaEnviado = fechaEnviado;
    }

    /**
     * @return the fechaLeido
     */
    public java.sql.Date getFechaLeido() {
        return fechaLeido;
    }

    /**
     * @param fechaLeido the fechaLeido to set
     */
    public void setFechaLeido(java.sql.Date fechaLeido) {
        this.fechaLeido = fechaLeido;
    }

    /**
     * @return the borradoUp
     */
    public boolean isBorradoUp() {
        return borradoUp;
    }

    /**
     * @param borradoUp the borradoUp to set
     */
    public void setBorradoUp(boolean borradoUp) {
        this.borradoUp = borradoUp;
    }

    
}
