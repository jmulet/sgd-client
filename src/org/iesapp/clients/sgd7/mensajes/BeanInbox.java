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
public class BeanInbox implements java.io.Serializable {

    private int id;
    protected int idMensaje;
    private String remitente;
    private int idremitente;
    private String texto;
    protected String richText;
    private String textocorto;
    private java.util.Date fechaEnviado;
    private boolean leido;
    private boolean selected;
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
     * @return the remitente
     */
    public String getRemitente() {
        return remitente;
    }

    /**
     * @param remitente the remitente to set
     */
    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    /**
     * @return the idremitente
     */
    public int getIdremitente() {
        return idremitente;
    }

    /**
     * @param idremitente the idremitente to set
     */
    public void setIdremitente(int idremitente) {
        this.idremitente = idremitente;
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
     * @return the textocorto
     */
    public String getTextocorto() {
        return textocorto;
    }

    /**
     * @param textocorto the textocorto to set
     */
    public void setTextocorto(String textocorto) {
        this.textocorto = textocorto;
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
     * @return the leido
     */
    public boolean isLeido() {
        return leido;
    }

    /**
     * @param leido the leido to set
     */
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
           
}
