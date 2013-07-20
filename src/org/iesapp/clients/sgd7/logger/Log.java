/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.logger;

import java.sql.Timestamp;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.database.MyDatabase;

/**
 *
 * @author Josep
 */
public class Log  {
    public static final String DELETE="DELETE";
    public static final String UPDATE="UPDATE";
    public static final String INSERT="INSERT";

    public static String normalizeSQL(String SQL1) {
        return SQL1.replaceAll("'", "''").trim();
    }
    
    
    protected int id = -1;
    protected String host = "";
    protected String hostIp = "";
    protected String user = "";
    protected String tipo = "";
    protected String datos = "";
    protected Timestamp fechadatetime = null;
    protected String tabla = "";
    protected String sentenciaSQL = "";
    private final IClient client;
    protected boolean enable = false;

    
    public Log()
    {
        this.client = null;
        this.host = "";
        this.hostIp = "";
        this.user = null;
    }
    
    public Log(String host, String hostIp, IClient client)
    {
        this.client = client;
        this.host = host;
        this.hostIp = hostIp;
        this.user = client.getUser()!=null? client.getUser().getNombre() : "";
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the hostIp
     */
    public String getHostIp() {
        return hostIp;
    }

    /**
     * @param hostIp the hostIp to set
     */
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the datos
     */
    public String getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(String datos) {
        this.datos = datos;
    }

    /**
     * @return the fechadatetime
     */
    public Timestamp getFechadatetime() {
        return fechadatetime;
    }

    /**
     * @param fechadatetime the fechadatetime to set
     */
    public void setFechadatetime(Timestamp fechadatetime) {
        this.fechadatetime = fechadatetime;
    }

    /**
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /**
     * @return the sentenciaSQL
     */
    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    /**
     * @param sentenciaSQL the sentenciaSQL to set
     */
    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }
    
    public int postLog(String tipo, String datos, String tabla, String sql)
    {
        this.tipo = tipo;
        this.datos = datos;
        this.tabla = tabla;
        this.sentenciaSQL = sql;
        return postLog();
    }
    
    public int postLog()
    {
        if(!enable || user==null || user.isEmpty() || client==null)
        {
            return 0;
        }
        int nup;

        String txt = this.sentenciaSQL;
        if(!txt.endsWith(";")) {
            txt += ";";
        }
        txt += "/*sgd7*/";
        
        String SQL = "INSERT INTO `" + client.getConfigDB() + "`.log" + client.getAnyAcademic() + " (host,hostIp,user,tipo,datos,fecha,tabla,sentenciaSQL) "
                + " VALUES(?,?,?,?,?,NOW(),?,?)";
        Object[] obj = new Object[]{this.host, this.hostIp, this.user, this.tipo, this.datos,
            this.tabla, txt};
        nup = getSgd().preparedUpdate(SQL, obj);

        return nup;
    }
    
     
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
