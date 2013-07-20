/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import java.util.Date;

/**
 *
 * @author Josep
 */
public class BeanActividadBasic implements java.io.Serializable {

    protected String idProfesores = "";
    protected int idEvaluacionesDetalle;
    protected String descripcion = "";
    protected Date fecha;
    protected boolean publicarWEB = true;
    protected boolean seguimiento;
    protected boolean evaluable;
    protected int ordre;
    protected int peso = 100;
    protected int idConceptosEvaluables;
    protected BeanConceptos concepto;
    protected int idSistemasNotas = 1;
    protected int idPeso = 0;
    public static final int ACT_ALTRES = 0;
    public static final int ACT_DEURES = 1;
    public static final int ACT_EXAMEN = 2;
    public static final int ACT_QUADERN = 3;

    public BeanActividadBasic() {
        concepto = new BeanConceptos();
    }

    public boolean isEvaluable() {
        return evaluable;
    }

    public void setEvaluable(boolean evaluable) {
        this.evaluable = evaluable;
    }

    public boolean isSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(boolean seguimiento) {
        this.seguimiento = seguimiento;
    }

    public boolean isPublicarWEB() {
        return publicarWEB;
    }

    public void setPublicarWEB(boolean publicarWEB) {
        this.publicarWEB = publicarWEB;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEvaluacionesDetalle() {
        return idEvaluacionesDetalle;
    }

    public void setIdEvaluacionesDetalle(int idEvaluacionesDetalle) {
        this.idEvaluacionesDetalle = idEvaluacionesDetalle;
    }

    /**
     * @return the ordre
     */
    public int getOrdre() {
        return ordre;
    }

    /**
     * @param ordre the ordre to set
     */
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /*
     * @return the idConceptosEvaluables
     */
    public int getIdConceptosEvaluables() {
        return idConceptosEvaluables;
    }

    /**
     * @param idConceptosEvaluables the idConceptosEvaluables to set
     */
    public void setIdConceptosEvaluables(int idConceptosEvaluables) {
        this.idConceptosEvaluables = idConceptosEvaluables;
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
     * @return the concepto
     */
    public BeanConceptos getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(BeanConceptos concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the idSistemasNotas
     */
    public int getIdSistemasNotas() {
        return idSistemasNotas;
    }

    /**
     * @param idSistemasNotas the idSistemasNotas to set
     */
    public void setIdSistemasNotas(int idSistemasNotas) {
        this.idSistemasNotas = idSistemasNotas;
    }

    /**
     * @return the idPeso
     */
    public int getIdPeso() {
        return idPeso;
    }

    /**
     * @param idPeso the idPeso to set
     */
    public void setIdPeso(int idPeso) {
        this.idPeso = idPeso;
    }
}
