/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public final class MensajesProfesores extends BeanMensajesProfesores implements IClientController{
    private final IClient client;
    
    public MensajesProfesores(int id)
    {
        this.client = null;
        load(id);
    }

       
    public MensajesProfesores(int id, IClient client)
    {
        this.client = client;
        load(id);
    }
    
    public void load(int id)
    {
        String SQL1 = "Select * from mensajesprofesores where id="+id;
        try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
               this.id=id;
               String tmp = StringUtils.noNull(rs1.getString("borradoUp"));
               this.borradoUp = tmp.equalsIgnoreCase("S");
               this.fechaEnviado = rs1.getDate("fechaEnviado");
               this.fechaLeido = rs1.getDate("fechaLeido");
               this.idMensajes = rs1.getInt("idMensajes");
               this.idProfesores = rs1.getInt("idProfesores");
            }
            if(rs1!=null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensajesProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    
     public boolean exists()
    {
        boolean exists = false;
        
        String SQL1 = "SELECT * FROM mensajesprofesores WHERE id="+this.id;
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
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }
    
    public int save()
    {
        int nup=0;
        if(this.id<0 || !this.exists())
        {
            nup=insert();
        }
        else
        {
            nup=update();
        }
       return nup;       
    }
    
    private int insert()
    {
       String strfechaEnviado = this.fechaEnviado==null? "NULL": "'"+new DataCtrl(fechaEnviado).getDataSQL()+"'";
       this.fechaLeido = null;
       String strborradoUp = borradoUp? "S":"N";    
       
       String SQL1 = "INSERT INTO mensajesprofesores ( idMensajes, idProfesores, fechaEnviado borradoUp) "+
               " VALUES('"+this.idMensajes+"','"+this.idProfesores+"',"+strfechaEnviado+", "+strborradoUp+"')";
       
       int nup = getSgd().executeUpdateID(SQL1);
       
       if(nup>0) {
            this.id = nup;
             {
                Log log = getLogger();
                log.setTabla("MensajesProfesores");
                log.setDatos("id=" + this.id + ";id="+this.id);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
       
       return this.id;
    }
    
    private int update()
    {
       String strfechaEnviado = this.fechaEnviado==null? "NULL": "'"+new DataCtrl(fechaEnviado).getDataSQL()+"'";
       String strfechaLeido = this.fechaLeido==null? "NULL": "'"+new DataCtrl(fechaLeido).getDataSQL()+"'";
       String strborradoUp = borradoUp? "S":"N";    
       
       String SQL1 = "UPDATE mensajesprofesores SET idMensajes='"+this.idMensajes+"', idProfesores='"+this.idProfesores+"', "
               + " fechaEnviado="+strfechaEnviado+", fechaLeido="+strfechaLeido+", borradoUp='"+strborradoUp+"' " 
               + " WHERE id="+this.id;
       
      
       int nup = getSgd().executeUpdate(SQL1);
       if ( nup>0) {
                Log log = getLogger();
                log.setTabla("MensajesProfesores");
                log.setDatos("id=" + this.id + ";id="+this.id);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
       
       return nup;
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
