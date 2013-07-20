/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.base;



import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public class TipoObservaciones implements IClientController {
    private final IClient client;
    private HashMap<Integer, BeanTipoObservaciones> mapObservaciones;
    private ArrayList<BeanTipoObservaciones> beanTipoObservaciones;
    
    public TipoObservaciones()
    {
       this.client = null;
    }
    
  
    public TipoObservaciones(IClient client)
    {
       this.client = client;
    }
  
    public  int getID(String nombre, String tipo)
    {
         int id=0;
         String cond = "";
         if(!tipo.equals("")) {
            cond = "AND tipo LIKE '%"+tipo+"%'";
        }
         
         String SQL1 = "SELECT * FROM tipoobservaciones where nombre LIKE '%"+nombre+"%' "+cond;
          
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                id= rs1.getInt("id");                          
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(TipoObservaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public  HashMap<Integer, BeanTipoObservaciones> getMapObservaciones() {
        if(mapObservaciones!=null)
        {
            return mapObservaciones;
        }
        mapObservaciones = new HashMap<Integer, BeanTipoObservaciones>();
        String SQL1 = "SELECT * FROM tipoobservaciones";
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                BeanTipoObservaciones bean = new BeanTipoObservaciones();
                int id = rs1.getInt("id");
                bean.setId(id);
                bean.setDescripcion(rs1.getString("descripcion"));
                bean.setNombre(rs1.getString("nombre"));
                bean.setTipo(rs1.getString("tipo"));
                mapObservaciones.put(id, bean);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TipoObservaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapObservaciones;
    }
    
  
    public ArrayList<BeanTipoObservaciones> getTipoObservaciones( )
    {
        if(beanTipoObservaciones!=null)
        {
            return beanTipoObservaciones;
        }
        beanTipoObservaciones = new ArrayList<BeanTipoObservaciones>();
        String SQL1 ="SELECT * FROM tipoobservaciones WHERE tipo='faltas'"; 
     
        
        BeanTipoObservaciones bh = null;
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               bh = new BeanTipoObservaciones();
               bh.setId(rs1.getInt("id"));
               bh.setNombre(rs1.getString("nombre"));
               bh.setDescripcion(rs1.getString("descripcion"));
               //falta incloure descripcion, codigo, idclases si fos necessari
               beanTipoObservaciones.add(bh);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TipoObservaciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        return beanTipoObservaciones;
        
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
