/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import org.iesapp.database.MyDatabase;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public class MensajesListasProfesores extends BeanProfSms implements IClientController{
    protected int id;
    protected int idMensajesListas;
    private final IClient client;
   
    public MensajesListasProfesores()
    {
        this.client = null;
    }
    
    public MensajesListasProfesores(IClient client)
    {
        this.client = client;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMensajesListas() {
        return idMensajesListas;
    }

    public void setIdMensajesListas(int idMensajesListas) {
        this.idMensajesListas = idMensajesListas;
    }

    public void save() {
        if(this.id<=0)
        {
            insert();
        }
        else
        {
            update();
        }
    }
    
    public void delete()
    {
        if(this.id<=0)
        {
            return;
        }
        
           String SQL1 = "DELETE FROM mensajeslistasprofesores WHERE id="+this.id+" LIMIT 1";
            int nup = getSgd().executeUpdate(SQL1);
            if(nup>0) {
            
             {
                Log log = getLogger();
                log.setTabla("MensajesListasProfesores");
                log.setDatos("id=" + this.id );
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
            }
    }

    private void insert() {
        String SQL1 = "INSERT INTO mensajeslistasprofesores "
                + "(idMensajesListas,idProfesores) VALUES('" 
                + this.idMensajesListas+"','"+this.getCodigo()+ "')";
        int nup = getSgd().executeUpdateID(SQL1);
        if (nup > 0) {
            this.id = nup;

             {
                Log log = getLogger();
                log.setTabla("MensajesListasProfesores");
                log.setDatos("id=" + this.id);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
    }

    private void update() {
             
        String SQL1 = "UPDATE mensajeslistasprofesores SET idMensajesListas='"+
                idMensajesListas+"', idProfesores='"+getCodigo()+"' WHERE id="+id;
        int nup = getSgd().executeUpdateID(SQL1);
        if (nup > 0) {
            this.id = nup;

             {
                Log log = getLogger();
                log.setTabla("MensajesListasProfesores");
                log.setDatos("id=" + this.id);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
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
