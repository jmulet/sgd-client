/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public final class Mensajes extends BeanMensajes implements IClientController{
    private final IClient client;
    private final String dbName;

//    public Mensajes()
//    {
//        this.client = null;
//    }
      
//    public Mensajes(int id)
//    {
//        this.client = null;
//        this.id=id;
//        load();
//    }
    
//    public Mensajes(Integer idRemite, String text, Integer idProfe) {
//        this.client = null; 
//        this.idUsuarios = idRemite;
//        this.idProfesores = idRemite;
//        this.texto = text;
//        this.destinatarios.put(idProfe,-1);
//    }

//    public Mensajes(Integer idRemite, String text, List<Integer> idProfesores) {
//        this.client = null;
//        
//        this.idUsuarios = idRemite;
//        this.idProfesores = idRemite;
//        this.texto = text;
//        for(int i=0; i<idProfesores.size(); i++)
//        {
//            destinatarios.put(idProfesores.get(i), -1);
//        }
//    }
//   
    public Mensajes(IClient client)
    {
        this.client = client;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
    }
      
    public Mensajes(int id, IClient client)
    {
        this.client = client;
        this.id=id;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
        load();
    }
    
    public Mensajes(Integer idRemite, String text, Integer idProfe, IClient client) {
        this.client = client; 
        this.idUsuarios = idRemite;
        this.idProfesores = idRemite;
        this.texto = text;
        this.destinatarios.put(idProfe,-1);
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
    }

    public Mensajes(Integer idRemite, String text, List<Integer> idProfesores, IClient client) {
        this.client = client;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
        this.idUsuarios = idRemite;
        this.idProfesores = idRemite;
        this.texto = text;
        for(int i=0; i<idProfesores.size(); i++)
        {
            destinatarios.put(idProfesores.get(i), -1);
        }
    }
    
    public int save() {
        int nup = 0;
        if(id<0 || !this.exists())
        {
            nup=insert();
        }
        else
        {
            nup=update();
        }
        return nup;
    }
    
    private int update()
    {
        String strborrado = borradoUp?"S":"N";
        String strcontestado = contestado?"S":"N";
        
        if(this.fecha==null)
        {
            this.fecha = new java.sql.Date(new java.util.Date().getTime());    
        }
        
        String SQL1 ="UPDATE mensajes SET nombre='',fecha=?,texto=?,idUsuarios='"+this.idUsuarios+"',"
                + " idProfesores='"+this.idUsuarios+"',borradoUp='"+strborrado+"',idWeb='"+this.idWeb+"',"
                + " contestado='"+strcontestado+"' "
                + " WHERE id="+this.id;
        
        Object[] obj = new Object[]{this.fecha, this.texto};
        
        int nup = getSgd().preparedUpdate(SQL1, obj);
        if(nup>0)
        {
            Log log = getLogger();
            log.setTabla("Mensajes");
            log.setDatos("id="+this.id+";nombre=;texto="+this.texto+";fecha="+new DataCtrl(this.fecha).getDataSQL());
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
            log.setSentenciaSQL(getSgd().getLastPstm());
            log.postLog();
            log = null;
        }
        
        //Quan actualitzam el missatge, podem afegir o eliminar destinataris, aleshores,
        //Actualitza els mensajes a profesores
        //Necesita comparar l'actual amb la nova especificada en
        HashMap<Integer, Integer> loadDestinatarios = loadDestinatarios(this.id);
        
        //missatges a profesors que s'han d'eliminar
        for(int ky: loadDestinatarios.keySet())
        {
            if(!this.destinatarios.containsKey(ky))
            {
                int idMensajeProfesor = loadDestinatarios.get(ky);
                SQL1 = "DELETE FROM mensajesprofesores WHERE id="+idMensajeProfesor+" LIMIT 1;";
                int sucess = getSgd().executeUpdate(SQL1);
                if (sucess > 0) {
                    Log log = getLogger();
                    log.setTabla("MensajesProfesores");
                    log.setDatos("id=" + idMensajeProfesor);
                    log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                    log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                    log.postLog();
                    log = null;
                }
            }                
        }
        //Nous missatges que s'han de crear (aqui guarda les ids que es genereran)
        ArrayList<Integer> keys = new ArrayList<Integer>();
        
        for(int ky: this.destinatarios.keySet())
        {
            if(!loadDestinatarios.containsKey(ky))
            {
                keys.add(ky);
            }                
        }
        
        for(int i =0 ; i<keys.size(); i++)
        {
                int idProfe = keys.get(i);    
                
                //Inserta 
                //S'ha de posar la fechaEnviado a NULL tambe.
                SQL1 ="INSERT INTO mensajesprofesores (idMensajes,idProfesores,fechaEnviado,fechaLeido,borradoUp)"
                +"VALUES('"+this.id+"', '"+idProfe +"', NULL, NULL, 'N')";
                
                int newid2 = getSgd().executeUpdateID(SQL1);
                if(newid2>0)
                {
                    this.destinatarios.put(idProfe, newid2);
                     {
                        Log log = getLogger();
                        log.setTabla("MensajesProfesores");
                        log.setDatos("id=" + newid2+";id="+newid2);
                        log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                        log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                        log.postLog();
                        log = null;
                    }
                }
        }
        keys.clear();
        loadDestinatarios.clear();
        
        return nup;
    }
    
    public int delete()
    {
        String SQL1 = "DELETE FROM mensajes WHERE id="+this.id+" LIMIT 1;";
        int nup = getSgd().executeUpdate(SQL1);
        
        if ( nup>0) {
            Log log = getLogger();
            log.setTabla("Mensajes");
            log.setDatos("id=" + this.id);
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        
        
        String SQL0 = "SELECT id FROM mensajesprofesores WHERE idMensajes="+this.id;
        try {
             Statement st = getSgd().createStatement();
             ResultSet rs = getSgd().getResultSet(SQL0,st);
            while(rs!=null && rs.next())
            {
                int idmp = rs.getInt(1);
                SQL1 = "DELETE FROM mensajesprofesores WHERE id="+idmp+" LIMIT 1;";
                int success= getSgd().executeUpdate(SQL1);
                nup += success;
           
                if ( success > 0) {
                    Log log = getLogger();
                    log.setTabla("MensajesProfesores");
                    log.setDatos("id=" + idmp);
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
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Delete possibly associated richText
        String SQL = "DELETE FROM "+client.getPlusDbName()+"sgd_mensajes_richtext WHERE idMensajes="+this.id+" LIMIT 1";
        client.getPlusDb().executeUpdate(SQL);
        
        for(BeanMensajesAttachment atc: attachments)
        {
            atc.delete();
        }
        
        return nup;
    }
    
    private int insert()
    {
        String strborrado = borradoUp?"S":"N";
        String strcontestado = contestado?"S":"N";
        
        if(this.fecha==null) {
           this.fecha = new java.sql.Date(new java.util.Date().getTime());
        }
        
        String SQL1 ="INSERT INTO mensajes (nombre,fecha,texto,idUsuarios,"
                + "idProfesores,borradoUp,idWeb,contestado) VALUES('',?,"
                + "?,'"+this.idUsuarios+"', '"+this.idProfesores+"',"
                + "'"+strborrado+"','"+this.idWeb+"','"+strcontestado+"')";
        
        Object[] obj = new Object[]{this.fecha, this.texto};
        
        int newid = getSgd().preparedUpdateID(SQL1, obj);
        if(newid>0)
        {
            this.id = newid;
            
               {
                    Log log = getLogger();
                    log.setTabla("Mensajes");
                    log.setDatos("id="+this.id+";nombre=;texto="+this.texto+";fecha="+new DataCtrl(this.fecha).getDataSQL());
                    log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                    log.setSentenciaSQL(getSgd().getLastPstm());
                    log.postLog();
                    log = null;
                }

            if(this.richText!=null)
            {
                 //RichText has been set and is about to be stored
                 SQL1 ="INSERT INTO "+client.getPlusDbName()+"sgd_mensajes_richtext (idMensajes,richText) VALUES('"+this.id+"', ?)";
                 client.getPlusDb().preparedUpdate(SQL1, new Object[]{this.richText});
            }
        
            for(int idDesti: getDestinatarios().keySet())
            {
                //Inserta ara els diferents mensajes a profesores
                //fechaEnviado has been set to NULL
                SQL1 ="INSERT INTO mensajesprofesores (idMensajes,idProfesores,fechaEnviado,fechaLeido,borradoUp)"
                +"VALUES('"+this.id+"', '"+idDesti+"', NULL, NULL, 'N')";
                
                int newid2 = client.getPlusDb().executeUpdateID(SQL1);
                if(newid2>0)
                {
                    getDestinatarios().put(idDesti, newid2);
                    
                     {
                        Log log = getLogger();
                        log.setTabla("MensajesProfesores");
                        log.setDatos("id=" + newid2+";id="+newid2 );
                        log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                        log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                        log.postLog();
                        log = null;
                    }
       
                }
            }
        }
        
        return newid;
    }
    
    public boolean exists()
    {
        boolean exists = false;
        
        String SQL1 = "SELECT * FROM mensajes WHERE id="+this.id;
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

    public void load() {
        //Load in a single query text and possibly associated rich text
        
        String SQL1 = "SELECT * FROM mensajes WHERE id="+this.id;
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                String strborrado = StringUtils.noNull(rs1.getString("borradoUp"));
                String strcontestado = StringUtils.noNull(rs1.getString("contestado"));
                this.borradoUp = strborrado.equalsIgnoreCase("S");
                this.contestado = strcontestado.equalsIgnoreCase("S");
                this.fecha = rs1.getDate("fecha");
                this.id = rs1.getInt("id");
                this.idProfesores = rs1.getInt("idProfesores");
                this.idUsuarios = rs1.getInt("idUsuarios");
                this.idWeb = rs1.getInt("idUsuarios");
                this.texto = rs1.getString("texto");
               //this.richText = rs1.getString("richText");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_mensajes_richText WHERE idMensajes="+this.id;
         try {
             Statement st = client.getPlusDb().createStatement();
             ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
               this.richText = rs1.getString("richText");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.destinatarios = loadDestinatarios(this.id);
        this.attachments = client.getMensajesCollection().loadAttachments(this.id);  
       
    }
    
    /**
     * 
     * @param id id del missatge
     * @return un mapa idProfesor - idMensajeProfesor
     */
    private HashMap<Integer,Integer> loadDestinatarios(int id)
    {
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        
        String SQL1 = "SELECT id, idProfesores FROM mensajesprofesores WHERE idMensajes="+id;
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                map.put(rs1.getInt("idProfesores"),rs1.getInt("id"));
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }

    public void print() {
        System.out.println("Mensaje: id="+this.id+", idProfesores="+this.idProfesores+
                ", idUsuarios="+this.idUsuarios+", texto="+this.texto+", idWeb="+this.idWeb+
                ", fecha="+this.fecha+", borradoUp="+this.borradoUp+", contestado="+this.contestado+
                ", destinatarios="+this.getDestinatarios().toString());
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
