/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.tareas;

import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public class TareasCollection implements IClientController{
   private final IClient client;
    
   public TareasCollection()
   {
       this.client = null;
   } 
   
   public TareasCollection(IClient client)
   {
       this.client = client;
   }
            
            
    public  ArrayList<BeanTareas> getListTareas()
    {
        ArrayList<BeanTareas> list = new ArrayList<BeanTareas>();
        String SQL1 = "SELECT * FROM tareas";
        Statement st;
        try {
            st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL1,st);
            while(rs!=null && rs.next())
            {
                BeanTareas bt = new BeanTareas();
                bt.codigo = rs.getString("codigo");
                bt.guardia = rs.getString("guardia").equalsIgnoreCase("S");
                bt.nombre = rs.getString("nombre");
                bt.id = rs.getInt("id");
                list.add(bt);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TareasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return list;
    }
    
    //Obté una llista de id de professorat que tenen assignada un codig de tarea
    public  ArrayList<Integer> getListProfesores(String codigoTarea)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String SQL1= "SELECT DISTINCT p.id FROM horarios AS h INNER JOIN tareas AS t"
                + " ON t.id = h.idTareas INNER JOIN profesores AS p ON p.id=h.idProfesores WHERE "
                + " t.codigo='"+codigoTarea+"'";
        Statement st;
        try {
            st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL1,st);
            while(rs!=null && rs.next())
            {
                list.add(rs.getInt(1));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TareasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return list;
    }
    
        @Override
    public MyDatabase getMysql() {
        if(client==null)
        {
            return SgdBase.getMysql();
        }
        else
        {
            return client.getMysql();
        }
    }

    @Override
    public MyDatabase getSgd() {
        if(client==null)
        {
            return SgdBase.getSgd();
        }
        else
        {
            return client.getSgd();
        }
    }

    @Override
    public Log getLogger() {
        if(client==null)
        {
            return new Log();
        }
        else
        {
            return client.getLogger();
        }
    }
}
