/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.mensajes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;

 
/**
 * 
 * @author Josep
 */
public class MensajesCollection implements IClientController{
    private final IClient client;
    private final String dbName;
    
//    public MensajesCollection()
//    {
//        this.client = null;
//    }
    
    public MensajesCollection(IClient client)
    {
        this.client = client;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
    }
            
    public  int getSmsNoLeidos(String idProfe)
    {
        String SQL1="SELECT COUNT(id) AS noleido FROM mensajesprofesores WHERE "
                + " fechaLeido IS NULL AND idProfesores='"+idProfe+"' "
                + " AND (borradoUP='N' OR borradoUP='')" ; // GROUP BY id AND fechaEnviado IS NOT NULL
         
        int num =0;
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                num = rs1.getInt("noleido");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensajesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }
    
    public  int setLeido(int idSms)
    {
        String SQL1 = "UPDATE mensajesprofesores SET fechaLeido=NOW(), borradoUP='N' WHERE id='"+idSms+"'";
        int nup = getSgd().executeUpdate(SQL1);
        if ( nup>0) {
            Log log = getLogger();
            log.setTabla("MensajesProfesores");
            log.setDatos("id=" + idSms + ";");
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        return nup;
    }
    
    public  int setBorradoUpProfe(int idSms)
    {
        String SQL1 = "UPDATE mensajesprofesores SET borradoUp='S' WHERE id='"+idSms+"'";
        int nup = getSgd().executeUpdate(SQL1);
        if ( nup>0) {
            Log log = getLogger();
            log.setTabla("MensajesProfesores");
            log.setDatos("id=" + idSms + ";");
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        return nup;
    }
 
     public  int removeBorradoUpProfe(int idSms)
    {
        String SQL1 = "UPDATE mensajesprofesores SET borradoUp='N' WHERE id='"+idSms+"'";
        int nup = getSgd().executeUpdate(SQL1);
        if ( nup>0) {
            Log log = getLogger();
            log.setTabla("MensajesProfesores");
            log.setDatos("id=" + idSms + ";");
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        return nup;
    }
    
    
    public  ArrayList<BeanInbox> getInbox(String idProfe, boolean trash)
    {
        
        //Quan es creen els missatges es deixa que el sistema SGD envii
        //els missatges amb la "fechaEnviado" que toqui
        //El problema ve quan es vol llegir automaticament i aquesta data
        //es nula.
        //Solucio: Cada pic que es llegeix l'inbox, es marcarà com fechaEnviado
        //(CURRENT_DATE) per a tots aquells missatges del idProfe pels quals
        // el sistema encara no hagi enviat
        String SQL0 = "UPDATE mensajesprofesores SET fechaEnviado=CURRENT_DATE()"
                + " WHERE idProfesores='"+idProfe+"' AND fechaEnviado IS NULL";
        getSgd().executeUpdate(SQL0);
        
        ArrayList<BeanInbox> list = new ArrayList<BeanInbox>();
        
        String simbol="";
        if(trash) {
            simbol="='S'";
        }
        else {
            simbol="<>'S'";
        }
        
        String SQL1 = "  SELECT "
                + "   men.id AS idmensaje, "
                + "   menp.id AS idmensajeprof, "
                + "   texto, "
                + "   idUsuarios, "
                + "   men.idProfesores AS idProfRemite, "
                + "   prof.nombre, "
                + "   men.borradoUp AS menBorradoUp, "
                + "   idWeb, "
                + "   contestado, "
                + "   menp.idProfesores AS idProfDestinatario, "
                + "   IF(fechaEnviado IS NULL, CURRENT_DATE(), fechaEnviado) as fechaEnviado, "
                + "   fechaLeido, "
                + "   menp.borradoUp AS mepnBorradoUp  "
                + "   FROM "
                + "   mensajes AS men   "
                + "   INNER JOIN "
                + "   mensajesprofesores AS menp  "
                + "   ON men.id = menp.idMensajes  "
                + "   INNER JOIN "
                + "   profesores AS prof"
                + "   ON prof.id = men.idProfesores"
                + "   WHERE menp.idProfesores = '" + idProfe + "' "
                + "   AND  menp.borradoUp "+simbol+ " "
                + "   ORDER BY fechaEnviado DESC, men.id DESC ";
        
         
        try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                BeanInbox bean = new BeanInbox();
                int idMensaje = rs1.getInt("idmensaje");
                bean.setIdMensaje(idMensaje);
                bean.setId(rs1.getInt("idmensajeprof"));
                java.sql.Date sqldate = rs1.getDate("fechaEnviado");
                java.util.Date utildate = null;
                if(sqldate != null) {
                    utildate =new java.util.Date(sqldate.getTime());
                }
                else
                {
                    utildate = new java.util.Date(); //encara no enviat, posa la data d'avui
                }
                bean.setFechaEnviado( utildate );
                bean.setIdremitente(rs1.getInt("idProfRemite"));
                bean.setRemitente(rs1.getString("nombre"));
                String txt = rs1.getString("texto");
                bean.setTexto(txt);
                bean.setTextocorto(txt);
                
                //String rich = rs1.getString("richText");
                //bean.setRichText(rich==null? txt:rich);
                bean.setLeido(rs1.getDate("fechaLeido")!=null);
                
                bean.setAttachments(loadAttachments(idMensaje));
                
                list.add(bean);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensajesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

  public void sendSms(final String remite, final List<BeanProfSms> destinatarios, final String nouSmsText,
            final String richText,
            final List<BeanMensajesAttachment> attachments) {
        
        String SQL1="INSERT INTO mensajes (nombre, fecha, texto, idUsuarios, idProfesores, borradoUp, idWeb, contestado) "
                + " VALUES('',?,?,'"+remite+"','"+remite+"','N','0','N')";
        
        Date fecha = getSgd().getServerDate();
        Object[] obj = new Object[]{fecha, nouSmsText};
    
        int idmensaje = getSgd().preparedUpdateID(SQL1, obj);
        
        if(idmensaje>0)
        {
        
             {
                Log log = getLogger();
                log.setTabla("Mensajes");
                log.setDatos("id=" + idmensaje + ";nombre=;texto="+nouSmsText+";fecha="+fecha);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(getSgd().getLastPstm());
                log.postLog();
                log = null;
            }

            for(int i=0; i<destinatarios.size() ;i++)
            {
                if (destinatarios.get(i).isSelected()) {
                    //fechaLeido la posam a NULL
                    String SQL2 = "INSERT INTO mensajesprofesores (idMensajes,idProfesores,fechaEnviado,fechaLeido, borradoUp)"
                            + " VALUES('" + idmensaje + "', '" + destinatarios.get(i).getCodigo() + "', CURRENT_DATE(), NULL, 'N') ";
                    int nup = getSgd().executeUpdateID(SQL2);
                    
                    if ( nup > 0) {
                        Log log = getLogger();
                        log.setTabla("MensajesProfesores");
                        log.setDatos("id=" + nup + ";id=" + nup);
                        log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                        log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL2));
                        log.postLog();
                        log = null;
                    }
                }
            }
            
            if(richText!=null)
            {
                  //RichText has been set and is about to be stored
                 String SQL5 ="INSERT INTO "+client.getPlusDbName()+"sgd_mensajes_richtext (idMensajes,richText) VALUES('"+idmensaje+"', ?)";
                 client.getPlusDb().preparedUpdate(SQL5, new Object[]{richText});
            }
                
            
            //Save its attachments (els passa la id del missatge generada)
            if(attachments!=null)
            {
                for(BeanMensajesAttachment atc: attachments)
                {
                    atc.setId(-1);
                    atc.setIdMensajes(idmensaje);
                    atc.save();
                }
            }
        
        }
        
       
        
    }

    public  ArrayList<BeanOutbox> getOutbox(String codigo) {
    
        ArrayList<BeanOutbox> list = new ArrayList<BeanOutbox>();
    
        String SQL1 = "SELECT * from mensajes where idProfesores='"+codigo+"' order by fecha desc";
        
      
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                int idmensaje = rs1.getInt("id");
                BeanOutbox bean = new BeanOutbox();
                bean.setId(idmensaje);
               
                String destina="";
                String SQL2="Select prof.nombre from mensajesprofesores as menp inner"
                        + " join profesores as prof on prof.id=menp.idProfesores where "
                        + "menp.idMensajes='"+idmensaje+"'";
                
                Statement st2 = getSgd().createStatement();
                ResultSet rs2 = getSgd().getResultSet(SQL2,st2);
                int count = 0;
                while(rs2!=null && rs2.next())
                {
                    destina += rs2.getString("nombre")+"; ";
                    count +=1;
                }
                if(rs2!=null) {
                    rs2.close();
                    st2.close();
                }
                bean.setDestinatarios(destina);
                bean.setDestinatariosCount(count);
                bean.setFechaEnviado(rs1.getDate("fecha"));
                String txt = rs1.getString("texto");
                bean.setTexto(txt);
                
                bean.setAttachments(loadAttachments(idmensaje));
                list.add(bean);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensajesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;        
    }

    //Load lazy
    public String loadRichText(String txt, int idMensaje) {
        String texto = txt;
         try {
            String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_mensajes_richtext WHERE idMensajes="+idMensaje;
            Statement st = client.getPlusDb().createStatement();
            ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
               texto = rs1.getString("richText");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensajesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return texto;
    }
    
    
    public  ArrayList<BeanMensajesAttachment> loadAttachments(int idMensaje) {
        
        ArrayList<BeanMensajesAttachment> list = new ArrayList<BeanMensajesAttachment>();
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_mensajes_attachments WHERE idMensajes="+idMensaje;
        try {
            Statement st = client.getPlusDb().createStatement();
            ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                BeanMensajesAttachment bean = new BeanMensajesAttachment(client);
                bean.setId(rs1.getInt("id"));
                bean.setIdMensajes(idMensaje);
                bean.setAttachment(rs1.getString("attachment"));
                bean.setSize(rs1.getString("size"));
                list.add(bean);
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
       
       
    public ArrayList<MensajesListas> getMensajesListas()
    {
        
        ArrayList<MensajesListas> list = new ArrayList<MensajesListas>();
           
        try {
            String SQL1 = "SELECT * FROM mensajeslistas";
            Statement st1 = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st1);
            while(rs1!=null && rs1.next())
            {
                MensajesListas bml = new MensajesListas(client);  
                int idlista = rs1.getInt("id");
                bml.setId(idlista);
                bml.setNombre(rs1.getString("nombre"));
                
                String SQL2 = "SELECT mlp.*, p.nombre FROM mensajeslistasprofesores as mlp INNER JOIN"
                        + " profesores as p ON p.id=mlp.idProfesores WHERE idMensajesListas="+idlista;
                Statement st2 = getSgd().createStatement();
                ResultSet rs2 = getSgd().getResultSet(SQL2,st2);
                
                
                while(rs2!=null && rs2.next())
                {
                    MensajesListasProfesores listProfes = new MensajesListasProfesores(client);
                    listProfes.id = rs2.getInt("id");
                    listProfes.idMensajesListas = rs2.getInt("idMensajesListas");
                    listProfes.setCodigo(rs2.getString("idProfesores"));
                    listProfes.setNombre(rs2.getString("nombre"));
                    listProfes.setSelected(true);
                    bml.getListMensajesListasProfesores().add(listProfes);
                }
                rs2.close();
                st2.close();
                
                
                list.add(bml);
            }
            rs1.close();
            st1.close();
        } catch (SQLException ex) {
            Logger.getLogger(MensajesListas.class.getName()).log(Level.SEVERE, null, ex);
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

 
