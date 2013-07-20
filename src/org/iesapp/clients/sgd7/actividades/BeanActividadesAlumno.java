/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;


/**
 *
 * @author Josep
 */
public class BeanActividadesAlumno implements java.io.Serializable{
    
    protected int ordre;
    protected int id;
    protected int idAlumnos;
    protected String nombre;
    protected int idGrupAsig;
    protected int idActividades;
    protected java.util.Date fechaEntrega;
    protected float nota=-1;
    protected boolean selected;
    int idTipoNotasSistemas = 0;

    
    public BeanActividadesAlumno()
    {
        
    }
    
    //Clonar l'objecte
    public BeanActividadesAlumno(BeanActividadesAlumno bean)
    {
        this.ordre = bean.ordre;
        this.id = bean.id;
        this.idAlumnos = bean.idAlumnos;
        this.nombre = bean.nombre;
        this.idGrupAsig = bean.idGrupAsig;
        this.idActividades = bean.idActividades;
        this.fechaEntrega = bean.fechaEntrega;
        this.nota = bean.nota;
        this.selected = bean.selected;
        this.idTipoNotasSistemas = bean.idTipoNotasSistemas;
    }
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
     * @return the fechaEntrega
     */
    public java.util.Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(java.util.Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the nota
     */
    public float getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(float nota) {
        this.nota = nota;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
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
     * @return the idActividades
     */
    public int getIdActividades() {
        return idActividades;
    }

    /**
     * @param idActividades the idActividades to set
     */
    public void setIdActividades(int idActividades) {
        this.idActividades = idActividades;
    }
    
     /**
     * 
     * @param bean
     * @return true if both beans are equal in all fields
     */
    public boolean equals(BeanActividadesAlumno bean)
    {
        boolean equal = true;
        
        equal &= (this.fechaEntrega == bean.fechaEntrega);
        equal &= (this.nota == bean.nota);
        equal &= (this.selected == bean.selected);
                
        return equal;        
    }
    
}
