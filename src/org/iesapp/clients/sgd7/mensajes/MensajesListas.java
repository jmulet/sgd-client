/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import org.iesapp.database.MyDatabase;
import java.util.ArrayList;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public class MensajesListas implements IClientController{
    protected int id;
    protected String nombre="";
    protected ArrayList<MensajesListasProfesores> listMensajesListasProfesores = new ArrayList<MensajesListasProfesores>();
    private final IClient client;
    
    public MensajesListas()
    {
        this.client = null;
    }
    
    public MensajesListas(IClient client)
    {
        this.client = client;
    }
    
    private void insert()
    {
        //Crea una llista en blanc (sense cap professor assignat)
        String SQL1 = "INSERT INTO mensajeslistas (nombre) VALUES('" + this.getNombre() + "')";
        int nup = getSgd().executeUpdateID(SQL1);
        if (nup > 0) {
            this.setId(nup);

             {
                Log log = getLogger();
                log.setTabla("MensajesListas");
                log.setDatos("id=" + this.getId());
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
        
        
    }
    
    private void update()
    {
        //Crea una llista en blanc (sense cap professor assignat)
        String SQL1 = "UPDATE mensajeslistas SET nombre='"+this.getNombre() + "' WHERE id="+getId();
        int nup = getSgd().executeUpdate(SQL1);
        if (nup > 0) {
             {
                Log log = getLogger();
                log.setTabla("MensajesListas");
                log.setDatos("id=" + this.getId());
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
//        for(MensajesListasProfesores b: getListMensajesListasProfesores())
//        {
//            b.save();
//        }  
    }
    
    public void save()
    {
        if(this.getId()<=0)
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
        if(this.getId()<=0)
        {
            return;
        }
        
        for(MensajesListasProfesores b: getListMensajesListasProfesores())
        {
            b.delete();
        }
        
         String SQL1 = "DELETE FROM mensajeslistas WHERE id="+getId()+" LIMIT 1";
         int nup = getSgd().executeUpdate(SQL1);
         if(nup>0) {
            
             {
                Log log = getLogger();
                log.setTabla("MensajesListas");
                log.setDatos("id=" + this.getId());
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
          }
          this.setId(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<MensajesListasProfesores> getListMensajesListasProfesores() {
        return listMensajesListasProfesores;
    }

    public void setListMensajesListasProfesores(ArrayList<MensajesListasProfesores> listMensajesListasProfesores) {
        this.listMensajesListasProfesores = listMensajesListasProfesores;
    }
            
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(this.nombre).append(" : ");
        for(MensajesListasProfesores b: listMensajesListasProfesores)
        {
            builder.append(b.getCodigo()).append("; ");
        }
        return builder.toString();
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
