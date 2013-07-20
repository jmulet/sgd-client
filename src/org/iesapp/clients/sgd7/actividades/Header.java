/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

/**
 *
 * @author Josep
 */
public class Header {
    
    private String line1="";
    private String line2="";
    private String line3="";

    public Header()
    {
        
    }
          
    
    public Header(String label1, String label2, String label3) {
        this.line1 = label1;
        this.line2 = label2;
        this.line3 = label3;
    }

    /**
     * @return the line1
     */
    public String getLine1() {
        return line1;
    }

    /**
     * @return the line2
     */
    public String getLine2() {
        return line2;
    }

    /**
     * @return the line3
     */
    public String getLine3() {
        return line3;
    }
    
}
