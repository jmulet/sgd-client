/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

/**
 *
 * @author Josep
 */
  public class ColumnModel implements java.io.Serializable {  
  
        private Header header;  
        private String property;  
  
        public ColumnModel(Header header, String property) {  
            this.header = header;  
            this.property = property;  
        }  
  
        public Header getHeader() {  
            return header;  
        }  
  
        public String getProperty() {  
            return property;  
        }
    }
 
