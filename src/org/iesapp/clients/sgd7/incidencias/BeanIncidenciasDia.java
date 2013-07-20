/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.incidencias;

/**
 *
 * @author Josep
 */
public class BeanIncidenciasDia extends BeanIncidencias {
    
 
    protected String nombreProfesor="";
    protected boolean pasoLista=false;
    protected int ordre;

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public boolean isPasoLista() {
        return pasoLista;
    }

    public void setPasoLista(boolean pasoLista) {
        this.pasoLista = pasoLista;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
  
    
}
