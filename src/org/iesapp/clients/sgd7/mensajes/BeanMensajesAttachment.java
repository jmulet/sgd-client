/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import java.io.File;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class BeanMensajesAttachment implements IClientController {
    protected int id;
    protected int idMensajes;
    protected String attachment;
    protected String name;
    protected String size;
    protected IClient client;
    private final String dbName;

    public BeanMensajesAttachment(IClient client)
    {
        this.client = client;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMensajes() {
        return idMensajes;
    }

    public void setIdMensajes(int idMensajes) {
        this.idMensajes = idMensajes;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void save() {
        if(this.id>0)
        {
            update();
        }
        else
        {
            insert();
        }
    }

    private int update() {
         String SQL1 = "UPDATE "+client.getPlusDbName()+"sgd_mensajes_attachments SET idmensajes="+idMensajes+", attachment=?, size=? WHERE id="+id;
         return client.getPlusDb().preparedUpdate(SQL1, new Object[]{attachment, size});
    }

    private void insert() {
         String SQL1 = "INSERT INTO "+client.getPlusDbName()+"sgd_mensajes_attachments (idmensajes,attachment,size) VALUES('"+idMensajes+"',?,?)";
         this.id = client.getPlusDb().preparedUpdateID(SQL1, new Object[]{attachment,size});
    }
        
    public int delete() {
        int success = 0;
        if(id>0)
        {
            String SQL1 = "DELETE FROM "+client.getPlusDbName()+"sgd_mensajes_attachments WHERE id="+id+" LIMIT 1";
            success = client.getPlusDb().executeUpdate(SQL1);
        }
        return success;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        name = StringUtils.AfterLast(attachment, File.separator);
        return name;
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
