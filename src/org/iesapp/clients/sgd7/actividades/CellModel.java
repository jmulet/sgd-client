/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

/**
 *
 * @author Josep
 */
public class CellModel {
    private Object value;
    private String style;

    public CellModel()
    {
        this.value = new Object();
        this.style = "";
    }
             
    public CellModel(Object value)
    {
        this.value = value;
        this.style = "";
    }
    
    public CellModel(Object value, String style)
    {
        this.value = value;
        this.style = style;
    }
    
    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }
}
