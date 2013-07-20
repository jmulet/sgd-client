/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.alumnos;

    
/**
 *
 * @author Josep
 */
public class BeanAlumno {
    protected int id=0;
    protected int expediente=0;
    protected String grupo="";
    protected String nombre="";

    protected int ordre=0;
    protected int idGrupoAsig=0;
    
    protected int idGrupAsigTutoria=0;
    protected int idTutor=0;
    protected int idHoraTutoria=0;
    protected int idDiaTutoria=0;
    protected String nomTutor="";
    protected int idGrupo=0;
    
    protected String incidencies="";
    protected int status = 0; //0=normal //1=expulsat //2=dimecres
  
    //Pel mobile
    private String assistencia = "OK"; //OK - RE - FA
    private boolean collapsed = true;  //ui-mobile collapsed
    
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
     * @return the expediente
     */
    public int getExpediente() {
        return expediente;
    }

    /**
     * @param expediente the expediente to set
     */
    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the idGrupAsigTutoria
     */
    public int getIdGrupAsigTutoria() {
        return idGrupAsigTutoria;
    }

    /**
     * @param idGrupAsigTutoria the idGrupAsigTutoria to set
     */
    public void setIdGrupAsigTutoria(int idGrupAsigTutoria) {
        this.idGrupAsigTutoria = idGrupAsigTutoria;
    }

    /**
     * @return the idTutor
     */
    public int getIdTutor() {
        return idTutor;
    }

    /**
     * @param idTutor the idTutor to set
     */
    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    /**
     * @return the idHoraTutoria
     */
    public int getIdHoraTutoria() {
        return idHoraTutoria;
    }

    /**
     * @param idHoraTutoria the idHoraTutoria to set
     */
    public void setIdHoraTutoria(int idHoraTutoria) {
        this.idHoraTutoria = idHoraTutoria;
    }

    /**
     * @return the idDiaTutoria
     */
    public int getIdDiaTutoria() {
        return idDiaTutoria;
    }

    /**
     * @param idDiaTutoria the idDiaTutoria to set
     */
    public void setIdDiaTutoria(int idDiaTutoria) {
        this.idDiaTutoria = idDiaTutoria;
    }

    /**
     * @return the nomTutor
     */
    public String getNomTutor() {
        return nomTutor;
    }

    /**
     * @param nomTutor the nomTutor to set
     */
    public void setNomTutor(String nomTutor) {
        this.nomTutor = nomTutor;
    }

    /**
     * @return the idGrupo
     */
    public int getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
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
     * @return the idGrupoAsig
     */
    public int getIdGrupoAsig() {
        return idGrupoAsig;
    }

    /**
     * @param idGrupoAsig the idGrupoAsig to set
     */
    public void setIdGrupoAsig(int idGrupoAsig) {
        this.idGrupoAsig = idGrupoAsig;
    }

    /**
     * @return the incidencies
     */
    public String getIncidencies() {
        return incidencies;
    }

    /**
     * @param incidencies the incidencies to set
     */
    public void setIncidencies(String incidencies) {
        this.incidencies = incidencies;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public String getAssistencia() {
        return assistencia;
    }

    public void setAssistencia(String assistencia) {
        this.assistencia = assistencia;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }
}
