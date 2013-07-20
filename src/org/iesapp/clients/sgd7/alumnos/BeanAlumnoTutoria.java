/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.alumnos;

    
/**
 *
 * @author Josep
 */
public class BeanAlumnoTutoria extends BeanAlumno {
    
    public static final byte GREEN_FLAG = 0;
    public static final byte ORANGE_FLAG = 1;
    public static final byte RED_FLAG = 2;
    
    protected int accionsStatus = 0;
    protected String msgPendents = "";
    
    private String entrevistesPendents="";

    public String getMsgPendents() {
        return msgPendents;
    }

    public void setMsgPendents(String msgPendents) {
        this.msgPendents = msgPendents;
    }

    /**
     * @return the accionsStatus
     */
    public int getAccionsStatus() {
        return accionsStatus;
    }

    /**
     * @param accionsStatus the accionsStatus to set
     */
    public void setAccionsStatus(int accionsStatus) {
        this.accionsStatus = accionsStatus;
    }

    public String getEntrevistesPendents() {
        return entrevistesPendents;
    }

    public void setEntrevistesPendents(String entrevistesPendents) {
        this.entrevistesPendents = entrevistesPendents;
    }
    

}
