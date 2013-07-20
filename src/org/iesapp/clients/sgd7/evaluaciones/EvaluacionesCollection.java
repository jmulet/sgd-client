/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.evaluaciones;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;

/**
 * si es passa la database, ho fa desde la database que s'indica
 * sino agafa per defecte la del sgd
 * @author Josep
 */
public class EvaluacionesCollection implements IClientController {
    private static Date iniciCurs;
  
    private ArrayList<BeanEvaluaciones> evaluaciones;
    private final IClient client;
    private ArrayList<BeanEvaluaciones> evaluacionesies;
  
    public EvaluacionesCollection()
    {
        client = null;
    }
    
    public EvaluacionesCollection(IClient client)
    {
        this.client = client;
    }
    
    
    
//      public static int getEvaluacion(java.util.Date fecha) {
//
//          return getEvaluacion(SgdBase.getSgd(), fecha);
//    }
    
    //Try to get evaluacion from evaluaciones
    
     public static int getEvaluacion(MyDatabase mysql, java.util.Date fecha) {
 
          int idEval = 0;
          String str = new DataCtrl(fecha).getDataSQL();
          StringBuilder tmp = new StringBuilder("SELECT id FROM evaluacionesdetalle where ");
          tmp.append(" fechaInicio<='");
          tmp.append(str);
          tmp.append("' AND fechaFin>='");
          tmp.append(str);
          tmp.append("'");          
          String SQL1 = tmp.toString();
       
          try {
             Statement st = mysql.createStatement();
             ResultSet rs1 = mysql.getResultSet(SQL1,st);
              if (rs1 != null && rs1.next()) {
                  idEval = rs1.getInt("id");
              }
              if (rs1 != null) {
                  rs1.close();
                  st.close();
              }

          } catch (SQLException ex) {
              Logger.getLogger(EvaluacionesCollection.class.getName()).log(Level.SEVERE, null, ex);
          }

          return idEval;
    }
        
   
//    
//    public static ArrayList<BeanEvaluaciones> getEvaluaciones()
//    {
//        return getEvaluaciones(SgdBase.getSgd());
//    }
//  Afaga la base sgd
    public ArrayList<BeanEvaluaciones> getEvaluaciones()
    {
         if(evaluaciones!=null)
         {
             return evaluaciones;
         }
         
         evaluaciones = new ArrayList<BeanEvaluaciones>();
         String SQL1 ="SELECT * FROM evaluacionesdetalle order by fechaInicio ASC";
         
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanEvaluaciones act = new BeanEvaluaciones();
               act.setId(rs1.getInt("id"));
               act.setFechaInicio(rs1.getDate("fechaInicio"));
               act.setFechaFin(rs1.getDate("fechaFin"));
               act.setFechaInicioReal(rs1.getDate("fechaInicioReal"));
               act.setFechaFinReal(rs1.getDate("fechaFinReal"));
               act.setValorExportable(rs1.getString("valorExportable"));
               evaluaciones.add(act);
            }
            if(rs1!=null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
      
         
         return evaluaciones;
    }
    
    
    public ArrayList<BeanEvaluaciones> getEvaluacionesIes(String ensenyament, String estudis)
    {
         if(evaluacionesies!=null)
         {
             return evaluacionesies;
         }
         
         evaluacionesies = new ArrayList<BeanEvaluaciones>();
         String SQL1 ="SELECT * FROM avaluacionsdetall AS ad INNER JOIN avaluacions AS a "+
            " ON ad.idAvaluacions=a.id WHERE ensenyament LIKE '%"+ensenyament+"%' AND estudis LIKE '"+estudis+"' "+
            " ORDER BY fechaInicio";
         
         try {
             Statement st = getMysql().createStatement();
             ResultSet rs1 = getMysql().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanEvaluaciones act = new BeanEvaluaciones();
               act.setId(rs1.getInt("id"));
               act.setFechaInicio(rs1.getDate("fechaInicio"));
               act.setFechaFin(rs1.getDate("fechaFin"));
               act.setFechaInicioReal(rs1.getDate("fechaInicio"));
               act.setFechaFinReal(rs1.getDate("fechaFin"));
               act.setValorExportable(rs1.getString("valorExportable"));
               evaluacionesies.add(act);
            }
            if(rs1!=null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
      
         
         return evaluacionesies;
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
    
    public static java.util.Date getInicioCurso(IClient client){
        if(iniciCurs==null)
        {
           
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, client.getAnyAcademic());
                cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
                cal.set(Calendar.DAY_OF_MONTH,1);
                iniciCurs = cal.getTime();
                //Try to refine with query to evaluaciones 
           try {
                String SQL1 ="SELECT MIN(fechaInicio) FROM evaluacionesdetalle ORDER BY fechaInicio ASC";
                Statement st = client.getSgd().createStatement();
                ResultSet rs = client.getSgd().getResultSet(SQL1,st);
                if(rs!=null && rs.next())
                {
                    iniciCurs = rs.getDate(1);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(EvaluacionesCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return iniciCurs;
    }
}
