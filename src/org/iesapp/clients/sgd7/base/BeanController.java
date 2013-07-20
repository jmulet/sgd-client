/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.base;

/**
 *
 * @author Josep
 */
public interface BeanController {
    
    public void load();
    
    public int save();
    
    public int update();
    
    public int insert();
    
    public boolean exists();
    
    public int delete();
   
            
}
