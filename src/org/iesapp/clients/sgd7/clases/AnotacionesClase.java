/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.clients.sgd7.profesores.ProfesoresCollection;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;


/**
 *
 * @author Josep
 * Utility class
 */
public class AnotacionesClase implements IClientController{
    private final IClient client;
    private final String dbName;

   
    public AnotacionesClase(IClient client)
    {
        this.client = client;
        dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
    }
    
    public String getAnotacionesClase(final int idClase, final String idProfesor, 
                                            final int horaClase, final java.util.Date fecha)
    {
        String txt = ""; 
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_anotaciones WHERE idClase='"+idClase+
                "' AND idProfesor='"+idProfesor+"' AND fecha='"+new DataCtrl(fecha).getDataSQL()+
                "' AND horaClase='"+horaClase+"'";
         try {
             Statement st = client.getPlusDb().createStatement();
             ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                txt = rs1.getString("anotacion");
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnotacionesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         //Intenta comprovar si existeix informacio de guardia en sig_diaris_guardies
        //Per aixo s'ha de convertir horaClase (sistema sgd) a --> (sistema iesDigital)
        //Veure: HoraCentro.class,  members  
        //HashMap<Integer, Integer> sgd2iesdhcmap;
        //HashMap<Integer, Integer> iesd2sgdhcmap;
         
        int hora;
        if(client!=null)
        {
            hora = client.getHoraCentro().getSgd2iesdhcmap().get(horaClase);
        }
        else
        {
            hora = new HoraCentro().getSgd2iesdhcmap().get(horaClase);
        }
         
        if (txt.isEmpty()) {
              String abrev;
              if(client==null)
              {
                  abrev = new ProfesoresCollection().getAbrev(idProfesor);
              }
              else
              {
                   abrev = client.getProfesoresCollection().getAbrev(idProfesor);
              }
              SQL1 = "SELECT * FROM sig_diari_guardies WHERE "
                    + " profe_falta='" + abrev + "' AND data='" + new DataCtrl(fecha).getDataSQL()
                    + "' AND hora='" + hora + "'";
            try {
                Statement st = getMysql().createStatement();
                ResultSet rs1 = getMysql().getResultSet(SQL1, st);
                if (rs1 != null && rs1.next()) {
                    txt = rs1.getString("comentaris");
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AnotacionesClase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return txt;
    }
    
    public  int setAnotacionesClase(final int idClase, final String idProfesor, final int horaClase, final java.util.Date fecha, final String txt)
    {
        int id = exists(idClase, idProfesor, horaClase, fecha);
        int nup = 0;
        if(id>0)
        {
              String SQL1 = "UPDATE "+client.getPlusDbName()+"sgd_anotaciones SET anotacion=? WHERE id="+id;
              nup = client.getPlusDb().preparedUpdate(SQL1, new Object[]{txt});
        }
        else
        {
              String SQL1 = "INSERT INTO "+client.getPlusDbName()+"sgd_anotaciones (idClase, idProfesor, horaClase, fecha, anotacion) "
                      + " VALUES('"+idClase+"','"+idProfesor+"', '"+horaClase+"',?,?)";
              nup = client.getPlusDb().preparedUpdate(SQL1, new Object[]{fecha,txt});
        }
        return nup;
    }
    
    
    private int exists(final int idClase, final String idProfesor, final int horaClase, final java.util.Date fecha)
    {
        int exist = -1;
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_anotaciones WHERE idClase='"+idClase+"' AND idProfesor='"+idProfesor+"' AND horaClase='"+horaClase+"' AND fecha='"+new DataCtrl(fecha).getDataSQL()+"'";
         try {
             Statement st = client.getPlusDb().createStatement();
             ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
               exist = rs1.getInt("id");
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnotacionesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;
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
