/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class BeanMensajes {    
    protected int id=-1;
    protected java.sql.Date fecha;
    protected String texto="";
    protected String richText=null;
    protected int idUsuarios=0;
    protected int idProfesores=0;
    protected boolean borradoUp=false;
    protected int idWeb=0;
    protected boolean contestado=false;
    protected HashMap<Integer, Integer> destinatarios;
    protected ArrayList<BeanMensajesAttachment> attachments = new ArrayList<BeanMensajesAttachment>();
  
    

    public BeanMensajes()
    {
        destinatarios = new HashMap<Integer,Integer>();
        
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
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the idUsuarios
     */
    public int getIdUsuarios() {
        return idUsuarios;
    }

    /**
     * @param idUsuarios the idUsuarios to set
     */
    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
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
     * @return the idWeb
     */
    public int getIdWeb() {
        return idWeb;
    }

    /**
     * @param idWeb the idWeb to set
     */
    public void setIdWeb(int idWeb) {
        this.idWeb = idWeb;
    }

    /**
     * @return the contestado
     */
    public boolean isContestado() {
        return contestado;
    }

    /**
     * @param contestado the contestado to set
     */
    public void setContestado(boolean contestado) {
        this.contestado = contestado;
    }

    /**
     * @return the fecha
     */
    public java.sql.Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
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

    /**
     * @return the destinatarios
     */
    public HashMap<Integer, Integer> getDestinatarios() {
        return destinatarios;
    }

    /**
     * @param destinatarios the destinatarios to set
     */
    public void setDestinatarios(HashMap<Integer, Integer> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public ArrayList<BeanMensajesAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<BeanMensajesAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

}
