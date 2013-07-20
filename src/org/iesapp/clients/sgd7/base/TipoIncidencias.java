/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.base;


import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public class TipoIncidencias implements IClientController {
    private final IClient client;
    
    //Use Lazy initialization of arrays
    protected LinkedHashMap<String, Integer> mapaInc2Id;
    protected LinkedHashMap<Integer, BeanTipoIncidencias> mapIncidencias;

    public TipoIncidencias()
    {
       this.client = null;
    }
   
    public TipoIncidencias(IClient client)
    {
       this.client = client;
    }
     
    
    
    public LinkedHashMap<String, Integer> getMapaInc2Id() {
       
       if(mapaInc2Id!=null)
       {
           return mapaInc2Id;
       }
       
       mapaInc2Id = new LinkedHashMap<String, Integer>();
       String SQL1 ="SELECT * FROM tipoincidencias where descripcion not like "
               + " '%Convivència%' ORDER BY descripcion"; 
 
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
                String codigo = rs1.getString("simbolo");
                int idinc = rs1.getInt("id");
                //System.out.println("entrada:: "+codigo+", "+idinc);
                mapaInc2Id.put(codigo, idinc);
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(TipoIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mapaInc2Id;

    }
    
    
    public int getID(String codigo, String tipo)
    {
         int id=0;
         String cond = "";
         if(!tipo.equals("")) {
            cond = "AND tipo LIKE '%"+tipo+"%'";
        }
         
         String SQL1 = "SELECT * FROM tipoincidencias where simbolo='"+codigo+"' " + cond;
         
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
            Logger.getLogger(TipoIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    

    public LinkedHashMap<Integer, BeanTipoIncidencias> getMapIncidencias() {
       
         if(mapIncidencias!=null)
         {
             return mapIncidencias;
         }
         
         mapIncidencias = new LinkedHashMap<Integer, BeanTipoIncidencias>();
                
         String SQL1 = "SELECT * FROM tipoincidencias ORDER BY simbolo";
        
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                BeanTipoIncidencias bean = new BeanTipoIncidencias();
                int id = rs1.getInt("id");
                bean.setId(id);
                bean.setSimbolo(rs1.getString("simbolo"));
                bean.setTipo(rs1.getString("tipo"));
                bean.setVisibleUp(rs1.getString("visibleUP").equalsIgnoreCase("S"));
                bean.setDescripcion(rs1.getString("descripcion"));
                mapIncidencias.put(id, bean);
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(TipoIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
       return mapIncidencias;
    }
      
    public HashMap<Integer, BeanTipoIncidencias> getMapIncidencias(String tipo) {
       
         HashMap<Integer, BeanTipoIncidencias> map = new HashMap<Integer, BeanTipoIncidencias>();
                
         String SQL1 = "SELECT * FROM tipoincidencias where tipo like '%"+tipo+"%'";
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                BeanTipoIncidencias bean = new BeanTipoIncidencias();
                int id = rs1.getInt("id");
                bean.setId(id);
                bean.setSimbolo(rs1.getString("simbolo"));
                bean.setTipo(rs1.getString("tipo"));
                bean.setVisibleUp(rs1.getString("visibleUP").equalsIgnoreCase("S"));
                bean.setDescripcion(rs1.getString("descripcion"));
                map.put(id, bean);
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(TipoIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
       return map;
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
