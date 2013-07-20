/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

import org.iesapp.clients.sgd7.profesores.BeanFaltasProfesores;

 
/**
 *
 * @author Josep
 */
public class BeanClaseGuardia extends BeanClase{

    private int status = 0;      //indica l'status de la clase no signat, signat, falta, guardia, etc.
    private String img = "";  //aplica un nom d'imatge associat a l'estat
    private String idProfesor = "";  //anota la id del professor
    private String nombreProfesor = ""; //el nom del professor
    private BeanFaltasProfesores beanFProf;
    
    //La resta de camps estan derivats de la clase BeanClase

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
 

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BeanFaltasProfesores getBeanFProf() {
        return beanFProf;
    }

    public void setBeanFProf(BeanFaltasProfesores beanFProf) {
        this.beanFProf = beanFProf;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }
    
    
}
