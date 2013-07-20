/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Josep
 */
public class BeanClase implements java.io.Serializable{

    protected java.sql.Time inicio;
    protected Time fin;
    protected String grupo="";
    protected String aula="";
    protected int idClase;
    //Aquesta idClase esta formada pels següents agrupaments idGrupAsigs
    protected ArrayList<Integer> idGrupAsigs;
    protected int idHorasCentro;
    private String materia="";
    //Aquest camp permet anotar comentaris sobre la sessio
    private String seguimiento="";

    public BeanClase()
    {
         idGrupAsigs = new ArrayList<Integer>();
    }
    
    public int getIdHorasCentro() {
        return idHorasCentro;
    }

    public void setIdHorasCentro(int idHorasCentro) {
        this.idHorasCentro = idHorasCentro;
    }


    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }


    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }


    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }


    public java.sql.Time getInicio() {
        return inicio;
    }

    public void setInicio(java.sql.Time inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @return the idGrupAsigs
     */
    public ArrayList<Integer> getIdGrupAsigs() {
        return idGrupAsigs;
    }

    /**
     * @param idGrupAsigs the idGrupAsigs to set
     */
    public void setIdGrupAsigs(ArrayList<Integer> idGrupAsigs) {
        this.idGrupAsigs = idGrupAsigs;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

}
