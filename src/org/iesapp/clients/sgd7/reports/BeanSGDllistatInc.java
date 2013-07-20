/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.sgd7.reports;

import java.util.Date;

/**
 *
 * @author Josep
 */
public class BeanSGDllistatInc {

    private String grupo;
    private String tutorProfesor;
    private String alumno;
    private String expediente;
    private Date fecha;
    private String hora;
    private String incidencia;
    private String asignatura;
    private String tutor;
    private String observacion;
    private String comentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }



    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }


    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }


    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }



    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }


    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }



    public String getTutorProfesor() {
        return tutorProfesor;
    }


    public void setTutorProfesor(String tutorProfesor) {
        this.tutorProfesor = tutorProfesor;
    }


    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

}
