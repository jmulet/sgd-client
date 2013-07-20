/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.util.DataCtrl;


/**
 *
 * @author Josep
 */
public class ActividadAlumno extends BeanActividadesAlumno implements IClientController{
    private final IClient client;
    
      
    public ActividadAlumno(int id)
    {
        this.client=null;
        this.id=id;
    }
    
    public ActividadAlumno(BeanActividadesAlumno bean)
    {
        this.client=null;
        this.id = bean.id;
        this.fechaEntrega = bean.fechaEntrega;
        this.idActividades = bean.idActividades;
        this.idAlumnos = bean.idAlumnos;
        this.idGrupAsig = bean.idGrupAsig;
        this.nombre = bean.nombre;
        this.nota = bean.nota;
        this.ordre = bean.ordre;
        this.selected = bean.selected;
    }
    
    public ActividadAlumno(int id, IClient client)
    {
        this.client = client;
        this.id=id;
    }
    
    public ActividadAlumno(BeanActividadesAlumno bean, IClient client)
    {
        this.client = client;
        this.id = bean.id;
        this.fechaEntrega = bean.fechaEntrega;
        this.idActividades = bean.idActividades;
        this.idAlumnos = bean.idAlumnos;
        this.idGrupAsig = bean.idGrupAsig;
        this.nombre = bean.nombre;
        this.nota = bean.nota;
        this.ordre = bean.ordre;
        this.selected = bean.selected;
    }
    
     public boolean exists()
    {
        boolean exists = false;
        if(this.id<=0) {
            return exists;
        }
        
        String SQL1 = "Select * from actividadesalumno where id="+this.id;
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if (rs1 != null && rs1.next()) {
                exists = true;
            }
            if(rs1!=null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }            
       
        return exists;
    }
    
    public int save()
    {
        int nup =0;
        if(this.id<=0 || !this.exists())
        {
            nup = insert();
        }
        else
        {
            nup = update();
        }
        
        return nup;
    }
    
    private int insert()
    {   
        String strfecha = this.fechaEntrega==null? "NULL" : "'"+new DataCtrl(this.fechaEntrega).getDataSQL()+"'";
        
//idint(11) NOT NULL
//idAlumnosint(11) NOT NULL
//idActividadesint(11) NOT NULL
//fechaEntregadate NULL
//notafloat NULL
//idTipoNotasSistemasint(11) NULL     <-----nou camp (set to 1)
//matriculadochar(1) NOT NULL
        
        String SQL1 = "INSERT INTO actividadesalumno (idAlumnos,idActividades,fechaEntrega,nota,idTipoNotasSistemas,matriculado) "
                + " VALUES('"+this.idAlumnos+"','"+this.idActividades+"',"+strfecha+",'"+this.nota+"',"+this.idTipoNotasSistemas+",'S')";
       // System.out.println("insert in activiades alumno "+SQL1);       
        int nup = getSgd().executeUpdateID(SQL1);
        if(nup>0) {
            this.id = nup;
              //Logger
            
            {
                Log log = getLogger();
                log.setTabla("ActividadesAlumno");
                log.setDatos("id="+this.id+";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
        
        return nup;
    }
    
    private int update()
    {
        String strfecha = this.fechaEntrega==null? "NULL" : "'"+new DataCtrl(this.fechaEntrega).getDataSQL()+"'";
        
        String SQL1 = "UPDATE actividadesalumno SET idAlumnos='"+this.idAlumnos+"',idActividades='"+
                this.idActividades+"',fechaEntrega="+strfecha+",nota='"+this.nota+"',matriculado='S' WHERE id="+this.id;
          
               
        int nup = getSgd().executeUpdate(SQL1);
        if(nup>0)
        {
            //this.id = nup; (redundant)
            
            {
                Log log = getLogger();
                log.setTabla("ActividadesAlumno");
                log.setDatos("id="+this.id+";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
        return nup;
    }
    
    /**
     * Esborra l'activitat alumno amb id donada
     * Veure deleteAll()
     * @return num d'entrades esborrades
     */
    public int delete()
    {
        String SQL1 = "DELETE FROM actividadesalumno where id="+this.id;
        int nup = getSgd().executeUpdate(SQL1);
        
        if ( nup>0) {
            Log log = getLogger();
            log.setTabla("ActividadesAlumno");
            log.setDatos("id=" + this.id + ";");
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        return nup;
    }
    
    /**
     * Esborra totes les activitats alumno associades amb idActividad
     * @return num d'entrades esborrades
     */
   public int deleteAll()
    {
//        String SQL1 = "DELETE FROM actividadesalumno where idActividades="+this.idActividades;
//        int nup = getSgd().executeUpdate(SQL1);
//        return nup;
        int nup = 0;
        String SQL0 = "SELECT id FROM actividadesalumno WHERE idActividades='"+this.idActividades+"'";
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL0,st);
            while(rs!=null && rs.next())
            {
                int idaa = rs.getInt(1);
                String SQL1 = "DELETE FROM actividadesalumno WHERE id='"+idaa +"' LIMIT 1;";
                int success= getSgd().executeUpdate(SQL1);
                nup += success;
                if(success>0)
                {
                    Log log = getLogger();
                    log.setTabla("ActividadesAlumno");
                    log.setDatos("id=" + idaa + ";");
                    log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                    log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                    log.postLog();
                    log = null;
                }
                
            }
            if(rs!=null) {
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nup;
    }
    
    
    public String print()
    {
        return "ActividadAlumno:: id=" + this.id+ "; "+
               "idAlumnos="+this.idAlumnos+"; "+
                "nota="+this.nota+"; "+
                "ordre="+this.ordre+"; "+
                "selected="+this.selected;             
    }
    
    @Override
    public String toString()
    {
        return print();
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
