/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.incidencias;

/**
 *
 * @author Josep
 */
public class BeanIncidencias {
    protected int id=-1;
    protected int idAlumnos=0;
    protected String idProfesores="";
    protected int idTipoIncidencias=0;
    protected int idHorasCentro=0;
    protected String horaCentro = "";
    protected int idGrupAsig=0;
    protected String asignatura="";
    protected int idTipoObservaciones=0;
    protected java.sql.Date dia;
    protected String hora="";
    protected String comentarios="";
    protected java.sql.Timestamp fechaModificado;
    protected String simbolo="";
    
    protected String descripcion="";
    protected String observaciones="";
    protected boolean editable=true;
    
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
     * @return the idAlumnos
     */
    public int getIdAlumnos() {
        return idAlumnos;
    }

    /**
     * @param idAlumnos the idAlumnos to set
     */
    public void setIdAlumnos(int idAlumnos) {
        this.idAlumnos = idAlumnos;
    }

     
    /**
     * @return the idTipoIncidencias
     */
    public int getIdTipoIncidencias() {
        return idTipoIncidencias;
    }

    /**
     * @param idTipoIncidencias the idTipoIncidencias to set
     */
    public void setIdTipoIncidencias(int idTipoIncidencias) {
        this.idTipoIncidencias = idTipoIncidencias;
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

    /**
     * @return the idTipoObservaciones
     */
    public int getIdTipoObservaciones() {
        return idTipoObservaciones;
    }

    /**
     * @param idTipoObservaciones the idTipoObservaciones to set
     */
    public void setIdTipoObservaciones(int idTipoObservaciones) {
        this.idTipoObservaciones = idTipoObservaciones;
    }

    /**
     * @return the dia
     */
    public java.sql.Date getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(java.sql.Date dia) {
        this.dia = dia;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the fechaModificado
     */
    public java.sql.Timestamp getFechaModificado() {
        return fechaModificado;
    }

    /**
     * @param fechaModificado the fechaModificado to set
     */
    public void setFechaModificado(java.sql.Timestamp fechaModificado) {
        this.fechaModificado = fechaModificado;
    }

    /**
     * @return the simbolo
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @param simbolo the simbolo to set
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
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
     * @return the asignatura
     */
    public String getAsignatura() {
        return asignatura;
    }

    /**
     * @param asignatura the asignatura to set
     */
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * @return the horaCentro
     */
    public String getHoraCentro() {
        return horaCentro;
    }

    /**
     * @param horaCentro the horaCentro to set
     */
    public void setHoraCentro(String horaCentro) {
        this.horaCentro = horaCentro;
    }
 
}
