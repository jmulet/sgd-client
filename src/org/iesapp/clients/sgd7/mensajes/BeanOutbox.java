/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import java.util.ArrayList;

/**
 *
 * @author Josep
 */
public class BeanOutbox implements java.io.Serializable {

    private int id;
    private String destinatarios;
    protected int destinatariosCount;
    private String texto;
    protected String richText;
    private java.util.Date fechaEnviado;
    protected ArrayList<BeanMensajesAttachment> attachments = new  ArrayList<BeanMensajesAttachment>();
  
    
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
     * @return the fechaEnviado
     */
    public java.util.Date getFechaEnviado() {
        return fechaEnviado;
    }

    /**
     * @param fechaEnviado the fechaEnviado to set
     */
    public void setFechaEnviado(java.util.Date fechaEnviado) {
        this.fechaEnviado = fechaEnviado;
    }

    /**
     * @return the destinatarios
     */
    public String getDestinatarios() {
        return destinatarios;
    }

    /**
     * @param destinatarios the destinatarios to set
     */
    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
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

    public ArrayList<BeanMensajesAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<BeanMensajesAttachment> attachments) {
        this.attachments = attachments;
    }

    public int getDestinatariosCount() {
        return destinatariosCount;
    }

    public void setDestinatariosCount(int destinatariosCount) {
        this.destinatariosCount = destinatariosCount;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }
 
 
}
