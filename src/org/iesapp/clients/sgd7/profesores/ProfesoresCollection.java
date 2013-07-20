/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.profesores;


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
import org.iesapp.clients.sgd7.mensajes.BeanProfSms;

/**
 *
 * @author Josep
 */
public class ProfesoresCollection implements IClientController {
    private final IClient client;
    private ArrayList<BeanProfSms> listProf;
    
    public ProfesoresCollection()
    {
        this.client = null;
    }

    public ProfesoresCollection(IClient client)
    {
        this.client = client;
    }

    
    //use singleton patter for this list
    public  ArrayList<BeanProfSms> listProf()
    {
        if(listProf!=null)
        {
            return listProf;
        }
        listProf = new ArrayList<BeanProfSms>();
       

        String SQL1 = "SELECT * FROM  profesores ORDER BY NOMBRE ";
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            {
                String nombre =rs1.getString("nombre");
                String codig = rs1.getString("codigo");
              
                BeanProfSms p = new BeanProfSms();
                p.setCodigo(codig);
                p.setNombre(nombre);
                p.setSelected(false);
                listProf.add(p);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }  
        } catch (SQLException ex) {
            Logger.getLogger(ProfesoresCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
 
       return listProf;
    }
    
  
  public  String getAbrev(String idProfesor)
    {
         String abrev2="";
         String SQL1 = "SELECT abrev FROM sig_professorat where idSGD='"+idProfesor+"' ";
         
         try {
            Statement st = getMysql().createStatement();
            ResultSet rs1 = getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                abrev2= rs1.getString("abrev");                          
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return abrev2;
    }
  
  
   public static String getAbrev(String idProfesor, MyDatabase mysql)
    {
         String abrev2="";
         String SQL1 = "SELECT abrev FROM sig_professorat where idSGD='"+idProfesor+"' ";
         
         try {
            Statement st = mysql.createStatement();
            ResultSet rs1 = mysql.getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                abrev2= rs1.getString("abrev");                          
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return abrev2;
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
