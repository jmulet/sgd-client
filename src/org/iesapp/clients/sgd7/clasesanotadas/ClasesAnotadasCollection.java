/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clasesanotadas;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.actividades.ActividadesCollection;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;

/**
 *
 * @author Josep
 */
public class ClasesAnotadasCollection implements IClientController {
    private final IClient client;

  
    public ClasesAnotadasCollection()
    {
        this.client = null;
    }
    
    public ClasesAnotadasCollection(IClient client)
    {
        this.client = client;
    }
    
    /*
     * Anota tots els grups asig de la clase
     */
    public int anotaClase(String idProf, String idProfReal, int idHoras, int idClase)
    {
        ArrayList<Integer> grupAsigInClass;
        if(client!=null)
        {
            grupAsigInClass = client.getActividadesCollection().getGrupAsigInClass(idProf, idClase);
        }
        else
        {
             throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
       
        return anotaClase(idProf, idProfReal, idHoras, grupAsigInClass);
    }
  
    public  int anotaClase(String idProf, String idProfReal, int idHoras, ArrayList<Integer> grupAsigInClass) {
        int nup = 0;
        Iterator it = grupAsigInClass.iterator();
        while(it.hasNext())
        {
            int idgrupasig = ((Number) it.next()).intValue();
            nup += new ClasesAnotadas(idProf, idProfReal, idHoras, idgrupasig, client).save();
        }
        return nup;
    }
      
    /**
     * obte les id de totes les anotacions que te la clase
     * si no se n'obte cap es que no ha estat anotada
     * 
     * @param idProf
     * @param fecha
     * @param idHoras
     * @param idClase
     * @return 
     */
    public  ArrayList<Integer> isAnotada(String idProf, java.sql.Date fecha, int idHoras, int idClase)
    {    
        ArrayList<Integer> grupAsigInClass;
        if(client!=null)
        {
            grupAsigInClass = client.getActividadesCollection().getGrupAsigInClass(idProf, idClase);
        }
        else
        {
             throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        
        return isAnotada(idProf, fecha, idHoras, grupAsigInClass);
    }

    /**
     * 
     * @param idProf
     * @param fecha
     * @param idHoras
     * @param grupAsigInClass
     * @return 
     */
    public  ArrayList<Integer> isAnotada(String idProf, Date fecha, int idHoras, ArrayList<Integer> grupAsigInClass) {
                
        ArrayList<Integer> list = new ArrayList<Integer>();
        Iterator it = grupAsigInClass.iterator();
        while(it.hasNext())
        {
            String strfecha = fecha == null ? "NULL" : "'" + new DataCtrl(fecha).getDataSQL() + "' ";
            int idgrupasig = ((Number) it.next()).intValue();
            
            String SQL1 = "SELECT * FROM clasesanotadas WHERE idProfesores='" + idProf
                    + "' AND fecha=" + strfecha + " AND idHorasCentro='" + idHoras + "' AND idGrupAsig='" + idgrupasig + "'";
             try {
                 Statement st = getSgd().createStatement();
                ResultSet rs1 = getSgd().getResultSet(SQL1,st);
                if (rs1 != null && rs1.next()) {
                   list.add(rs1.getInt("id"));
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClasesAnotadasCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
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
