/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7;

import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;

/**
 *
 * @author Josep
 */
public interface IClientController {
    
    public MyDatabase getMysql();
    public MyDatabase getSgd();
    public Log getLogger();
    
 }
