/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.tareas;

/**
 *
 * @author Josep
 */
public class BeanTareas {
    protected int id;
    protected String codigo;
    protected String nombre;
    protected boolean guardia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isGuardia() {
        return guardia;
    }

    public void setGuardia(boolean guardia) {
        this.guardia = guardia;
    }
}
