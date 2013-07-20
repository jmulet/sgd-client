/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.profesores;

/**
 *
 * @author Josep
 */
public class BeanProfesor{

    protected String idProfesor = "";
    protected String abrev = "";
    protected int idUnidadesPersonales = 0;
    protected String nombre = "";
    protected String claveUP = "";
    protected boolean enviarSMS = false;
    protected boolean bloqueoMyClass = false;
    protected int idClaseTutoria = -1;
    protected boolean tutor = false;
    protected String systemUser = "";

    public boolean isEnviarSMS() {
        return enviarSMS;
    }

    public void setEnviarSMS(boolean enviarSMS) {
        this.enviarSMS = enviarSMS;
    }

    
    public String getClaveUP() {
        return claveUP;
    }

    public void setClaveUP(String claveUP) {
        this.claveUP = claveUP;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getIdUnidadesPersonales() {
        return idUnidadesPersonales;
    }

    public void setIdUnidadesPersonales(int idUnidadesPersonales) {
        this.idUnidadesPersonales = idUnidadesPersonales;
    }


    

    /**
     * @return the bloqueoMyClass
     */
    public boolean isBloqueoMyClass() {
        return bloqueoMyClass;
    }

    /**
     * @param bloqueoMyClass the bloqueoMyClass to set
     */
    public void setBloqueoMyClass(boolean bloqueoMyClass) {
        this.bloqueoMyClass = bloqueoMyClass;
    }

    /**
     * @return the idProfesor
     */
    public String getIdProfesor() {
        return idProfesor;
    }

    /**
     * @param idProfesor the idProfesor to set
     */
    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    /**
     * @return the idClaseTutoria
     */
    public int getIdClaseTutoria() {
        return idClaseTutoria;
    }

    /**
     * @param idClaseTutoria the idClaseTutoria to set
     */
    public void setIdClaseTutoria(int idClaseTutoria) {
        this.idClaseTutoria = idClaseTutoria;
    }

    /**
     * @return the abrev
     */
    public String getAbrev() {
        return abrev;
    }

    /**
     * @param abrev the abrev to set
     */
    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    /**
     * @return the systemUser
     */
    public String getSystemUser() {
        return systemUser;
    }

    /**
     * @param systemUser the systemUser to set
     */
    public void setSystemUser(String systemUser) {
        this.systemUser = systemUser;
    }

    public boolean isTutor() {
        return tutor;
    }

    public void setTutor(boolean tutor) {
        this.tutor = tutor;
    }

}
