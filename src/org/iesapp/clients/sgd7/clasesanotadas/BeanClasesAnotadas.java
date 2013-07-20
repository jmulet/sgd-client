/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clasesanotadas;

/**
 *
 * @author Josep
 */
public class BeanClasesAnotadas {
   protected int id=0;
   protected String idProfesores="";
   protected String idProfesoresReal="";
   protected java.sql.Date fecha;
   protected int idHorasCentro=0;
   protected int idGrupAsig=0;

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
     * @return the idProfesores
     */
    public String getIdProfesores() {
        return idProfesores;
    }

    /**
     * @param idProfesores the idProfesores to set
     */
    public void setIdProfesores(String idProfesores) {
        this.idProfesores = idProfesores;
    }

    /**
     * @return the idProfesoresReal
     */
    public String getIdProfesoresReal() {
        return idProfesoresReal;
    }

    /**
     * @param idProfesoresReal the idProfesoresReal to set
     */
    public void setIdProfesoresReal(String idProfesoresReal) {
        this.idProfesoresReal = idProfesoresReal;
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
     * @return the idHorasCentro
     */
    public int getIdHorasCentro() {
        return idHorasCentro;
    }

    /**
     * @param idHorasCentro the idHorasCentro to set
     */
    public void setIdHorasCentro(int idHorasCentro) {
        this.idHorasCentro = idHorasCentro;
    }

    /**
     * @return the idGrupAsig
     */
    public int getIdGrupAsig() {
        return idGrupAsig;
    }

    /**
     * @param idGrupAsig the idGrupAsig to set
     */
    public void setIdGrupAsig(int idGrupAsig) {
        this.idGrupAsig = idGrupAsig;
    }
}
