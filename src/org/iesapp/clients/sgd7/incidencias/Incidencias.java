/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.incidencias;
import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.alumnos.Alumnos;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.base.TipoIncidencias;
import org.iesapp.clients.sgd7.base.TipoObservaciones;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep Mulet
 */
public final class Incidencias extends BeanIncidencias implements IClientController {
    private final IClient client;
 
     /**
     * @return Default constructor with blank incidencia
     */
    public Incidencias()
    {
        this.client = null;
    }
    
    /**
     * @return Constructor loading incidencia with given id
     *  Remains blank if given id does not exist. Use getId()
     *  method to check whether it has been loaded or not
     */
    public Incidencias(int id)
    {
        this.client = null;
        load(id);
    }
    
    
    /**
     * @return Constructor importing incidencia given in bean
     */
    public Incidencias(BeanIncidencias bean)
    {
        this.client = null;
        setBean(bean);
    }
    
     public Incidencias(IClient client)
    {
        this.client = client;
    }
    
    /**
     * @return Constructor loading incidencia with given id
     *  Remains blank if given id does not exist. Use getId()
     *  method to check whether it has been loaded or not
     */
    public Incidencias(int id, IClient client)
    {
        this.client = client;
        load(id);
    }
    
    
    /**
     * @return Constructor importing incidencia given in bean
     */
    public Incidencias(BeanIncidencias bean, IClient client)
    {
        this.client = client;
        setBean(bean);
    }

    public void findCodigoIncidencia(String codigo, String tipo)
    {
        if(client==null)
        {
            this.idTipoIncidencias = new TipoIncidencias().getID(codigo, tipo);
        }
        else
        {
            this.idTipoIncidencias = client.getTipoIncidencias().getID(codigo, tipo);
        }
    }
    
    public void findNombreObservaciones(String nombre, String tipo)
    {
        if(client==null)
        {
            this.idTipoObservaciones = new TipoObservaciones().getID(nombre, tipo);
        }
        else
        {
            this.idTipoObservaciones = client.getTipoObservaciones().getID(nombre, tipo);
        }
    }
    
    public void findAlumnoExpediente(int expd)
    {
        this.id = Alumnos.getID(expd, getSgd());
    }
    
    public void setBean(BeanIncidencias bean)
    {
        this.comentarios = bean.comentarios;
        this.dia = bean.dia;
        this.fechaModificado = bean.fechaModificado;
        this.hora = bean.hora;
        this.id = bean.id;
        this.idAlumnos = bean.idAlumnos;
        this.idGrupAsig = bean.idGrupAsig;
        this.idHorasCentro = bean.idHorasCentro;
        this.setIdProfesores(bean.getIdProfesores());
        this.idTipoIncidencias = bean.idTipoIncidencias;
        this.idTipoObservaciones = bean.idTipoObservaciones;
        this.simbolo = bean.simbolo;
    }
    
    public BeanIncidencias getBean()
    {
        BeanIncidencias bean = new BeanIncidencias();
        
        bean.comentarios = this.comentarios;
        bean.dia = this.dia;
        bean.fechaModificado = this.fechaModificado;
        bean.hora = this.hora;
        bean.id = this.id;
        bean.idAlumnos = this.idAlumnos;
        bean.idGrupAsig = this.idGrupAsig;
        bean.idHorasCentro = this.idHorasCentro;
        bean.setIdProfesores(this.getIdProfesores());
        bean.idTipoIncidencias = this.idTipoIncidencias;
        bean.idTipoObservaciones = this.idTipoObservaciones;
        bean.simbolo = this.simbolo;
        
        return bean;
    }
    
    public boolean load(int id)
    {
        boolean exists=false;
        StringBuilder SQL1 = new StringBuilder("SELECT fa.*, ti.simbolo, CONCAT(hc.inicio,'-', hc.fin) AS horaCentro, asig.descripcion AS asignatura FROM faltasalumnos AS fa LEFT JOIN ");
        SQL1.append("tipoincidencias AS ti ON ti.id=fa.idTipoIncidencias ");
        SQL1.append("LEFT JOIN horascentro AS hc ON hc.id = fa.idHorasCentro ");
	SQL1.append("LEFT JOIN grupasig AS ga ON ga.id=fa.idGrupAsig ");
	SQL1.append("LEFT JOIN asignaturas AS asig ON asig.id=ga.idAsignaturas ");
        SQL1.append("WHERE fa.id=").append(id);
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1.toString(),st);
            if(rs1!=null && rs1.next())
            {
                this.setId(id);
                this.setIdAlumnos(rs1.getInt("idAlumnos"));
                this.setIdGrupAsig(rs1.getInt("idGrupAsig"));
                this.setIdHorasCentro(rs1.getInt("idHorasCentro"));
                this.setIdProfesores(rs1.getString("idProfesores"));
                this.setIdTipoIncidencias(rs1.getInt("idTipoIncidencias"));
                this.setIdTipoObservaciones(rs1.getInt("idTipoObservaciones"));        
                this.setComentarios(rs1.getString("comentarios"));
                this.setDia(rs1.getDate("dia"));
                this.setFechaModificado(rs1.getTimestamp("fechaModificado"));
                this.setHora(rs1.getString("hora"));
                this.setSimbolo(rs1.getString("simbolo"));
                
                //També ha de carregar informacio de la materia i l'hora del centre
                this.setAsignatura(rs1.getString("asignatura"));
                this.setHoraCentro(rs1.getString("horaCentro"));
                        
                exists = true;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }
    
    //Quan s'esborra una incidencia
    //Deixam constancia a la taula faltasalumnos_deleted
    public int delete()
    {
        if(this.id<0)
        {
            //nothing to delete
            return -1;
        }
        
        String SQL1 = "DELETE FROM faltasalumnos WHERE id="+this.id+" LIMIT 1;";
        int nup = getSgd().executeUpdate(SQL1);
        
         //Logger
         if( nup>0)
         {
                Log log = getLogger();
                log.setTabla("FaltasAlumnos");
                log.setDatos("id="+this.id+";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
         }
        
        if(nup>0)
        {
        SQL1 = "INSERT INTO faltasalumnos_deleted (idFaltasAlumnos, fechaModificado) "
                + " VALUES('"+getId()+"',NOW())";
        nup += getSgd().executeUpdate(SQL1);
        }
        
        this.id = -1;  //when deleted, we reset the id from bean
        
        return nup;
    }
    
    private boolean exists(int id)
    {
        boolean exists=false;
        String SQL1 = "SELECT * FROM faltasalumnos where id="+id;
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                exists = true;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }
    
    //save does double job update if exits or insert if it doesn't
    public int save()
    {
        if(id<0 || !exists(id))
        {
            //do insert and assign new id
            return insert();
        }
        else
        {
            return update(); 
        }
        
    }
    
    private int insert()
    {
       String strhora = "'"+this.hora+"'";
       if(strhora==null || strhora.equals("")) {
            strhora="NULL";
        }
       if(this.dia==null) {
            this.dia= new java.sql.Date(new java.util.Date().getTime());
        }

       //added field exportado 'N'
       
       String SQL1 = "INSERT INTO faltasalumnos (idAlumnos,idProfesores,"+
               " idTipoIncidencias,idHorasCentro,idGrupAsig,idTipoObservaciones,"+
               " dia,hora,comentarios,fechaModificado,exportado) "+
               " VALUES(?,?,?,?,?,?,?,?,?,?,'N')";
       Time serverTime = getSgd().getServerTime();
       Timestamp serverNow = getSgd().getServerNow();
       Object[] obj = new Object[]{this.idAlumnos, this.getIdProfesores(),
                                   this.idTipoIncidencias, this.idHorasCentro,
                                   this.idGrupAsig, this.idTipoObservaciones,
                                   this.dia, serverTime, this.comentarios, serverNow};
       
       int nup = getSgd().preparedUpdateID(SQL1, obj);
       
       if(nup>0) {
            this.id = nup;
             //Logger
            
            {
                Log log = getLogger();
                log.setTabla("FaltasAlumnos");
                log.setDatos("id="+this.id+";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(getSgd().getLastPstm());
                log.postLog();
                log = null;
            }
        }
       
       return this.id;
    }
    
    private int update()
    {
      String strhora = "'"+this.hora+"'";
      if(this.hora==null || strhora.equals("'null'")) {
            strhora="'(NULL)'";
        }
      if(this.dia==null) {
            this.dia= new java.sql.Date(new java.util.Date().getTime());
        }
       
      String SQL1 = "UPDATE faltasalumnos SET idAlumnos=?, idProfesores=?,"+
               " idTipoIncidencias=?, idHorasCentro=?, idGrupAsig=?, idTipoObservaciones=?,"+
               " dia=?, hora="+strhora+", comentarios=?, fechaModificado=NOW() "+
               " WHERE id="+this.id;
                  
       Object[] obj = new Object[]{this.idAlumnos, this.getIdProfesores(),
                                   this.idTipoIncidencias, this.idHorasCentro,
                                   this.idGrupAsig, this.idTipoObservaciones,
                                   this.dia, this.comentarios};
       
       int nup = getSgd().preparedUpdate(SQL1, obj);
       
        if( nup>0)
            {
                Log log = getLogger();
                log.setTabla("FaltasAlumnos");
                log.setDatos("id="+this.id+";idAlumnos="+this.idAlumnos);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                log.setSentenciaSQL(getSgd().getLastPstm());
                log.postLog();
                log = null;
            }
       
       
       return nup;
    }


    public String print()
    {
        return "id="+this.id+"; "+
                "idProfesores="+this.idProfesores+"; "+
                "simbolo="+this.simbolo+"; "+
                "hora="+this.hora+"; "+
                "observaciones="+this.observaciones+"; "+
                "comentarios="+this.comentarios+"; "+
                "descripcion="+this.descripcion+"; "+
                "dia="+this.dia+"; "+
                "editable="+this.editable+"; "+
                "fechaModificado="+this.fechaModificado;
                
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
