
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

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
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public class HoraCentro implements IClientController{
  
      
    //Crea mapes s que relacionen les hores de clase en els dos
    //sistemes sgd i iesdigital (Lazy creation)
    
    protected  HashMap<Integer, Integer> sgd2iesdhcmap;
    protected  HashMap<Integer, Integer> iesd2sgdhcmap;
    private final IClient client;
    private ArrayList<BeanHoraCentro> horasCentro;
  
    public HoraCentro()
    {
        this.client = null;
        
        //Lazy initialization
        //sgd2iesdhcmap = getSgd2iesdhcmap();
        //iesd2sgdhcmap = getIesd2sgdhcmap();
    }
    public HoraCentro(IClient client)
    {
        this.client = client;
         //Lazy initialization
    }
 
    
    public ArrayList<BeanHoraCentro> getHorasClase()
    {
        if(horasCentro!=null)
        {
            return horasCentro;
        }
        horasCentro = new ArrayList<BeanHoraCentro>();
        
        String SQL1 = "SELECT * FROM horasCentro order by inicio";
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
              
                horasCentro.add(new BeanHoraCentro(rs1.getInt("id"), rs1.getTime("inicio"), rs1.getTime("fin")));
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoraCentro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horasCentro;
    }
    
    //igual que l'anterior pero permet filtar les hores
    //per exemple eliminar els esplais mitjançant una condicio com ara
    // TIMEDIFF(fin,inicio)>'00:20:00'

    public  ArrayList<BeanHoraCentro> getHorasClase(String condition)
    {
        if(condition==null || condition.isEmpty()) {
            return getHorasClase();
        }
        
        ArrayList<BeanHoraCentro> list = new ArrayList<BeanHoraCentro>();
        
        String SQL1 = "SELECT * FROM horasCentro where "+condition+" order by inicio";
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
              
                list.add(new BeanHoraCentro(rs1.getInt("id"), rs1.getTime("inicio"), rs1.getTime("fin")));
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoraCentro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
   
   
    public  int getCurrentIdHoraCentro()
    {
        int id=-1;
        String SQL1="SELECT * FROM horascentro WHERE inicio<=CURRENT_TIME() AND CURRENT_TIME()<=fin";
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                id = rs1.getInt("id");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoraCentro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public HashMap<Integer, Integer> getSgd2iesdhcmap() {
        
        if(sgd2iesdhcmap!=null)
        {
            return sgd2iesdhcmap;
        }
        
        sgd2iesdhcmap = new HashMap<Integer, Integer>();
        String SQL1 = "SELECT id, TIME_FORMAT(inicio,'%H:%i') AS inicio, TIME_FORMAT(fin,'%H:%i') AS fin FROM horascentro";
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                int id = rs1.getInt("id");
                String inicio = rs1.getString("inicio");
                String fin = rs1.getString("fin");
                String SQL2 = "SELECT codigo from sig_hores_classe WHERE inicio='"+inicio+"' AND fin='"+fin+"'";
              
                Statement st2 = getMysql().createStatement();
                ResultSet rs2 = getMysql().getResultSet(SQL2,st2);
                if(rs2!=null && rs2.next())
                {
                    sgd2iesdhcmap.put(id, rs2.getInt(1));
                }
                if(rs2!=null) {
                    rs2.close();
                    st2.close();
                }
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoraCentro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sgd2iesdhcmap;
    }

    
    public HashMap<Integer, Integer> getIesd2sgdhcmap() {
        if(iesd2sgdhcmap!=null)
        {
            return iesd2sgdhcmap;
        }
        
        iesd2sgdhcmap = new HashMap<Integer, Integer>();
        String SQL1 = "SELECT codigo, TIME_FORMAT(inicio,'%H:%i') AS inicio, TIME_FORMAT(fin,'%H:%i') AS fin FROM sig_hores_classe";
        try {
            Statement st = getMysql().createStatement();
            ResultSet rs1 = getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                int id = rs1.getInt("codigo");
                String inicio = rs1.getString("inicio");
                String fin = rs1.getString("fin");
                String SQL2 = "SELECT id from horascentro WHERE inicio='"+inicio+"' AND fin='"+fin+"'";
            
                Statement st2 = getSgd().createStatement();
                ResultSet rs2 = getSgd().getResultSet(SQL2,st2);
            
                if(rs2!=null && rs2.next())
                {
                    iesd2sgdhcmap.put(id, rs2.getInt(1));
                }
                if(rs2!=null) {
                    rs2.close();
                    st2.close();
                }
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoraCentro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return iesd2sgdhcmap;
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
