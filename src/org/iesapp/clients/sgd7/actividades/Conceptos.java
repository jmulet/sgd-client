/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;
import org.iesapp.database.MyDatabase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.BeanController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public final class Conceptos extends BeanConceptos implements BeanController, IClientController{
    
    public final static byte IDCONCEPTO = 1;
    public final static byte IDACTIVIDAD = 2;
    private byte loadBy = IDCONCEPTO;
    protected int idActividad = 0;
    private final IClient client;
    private final String dbName;
    
//    public Conceptos(int id, byte whichID)
//    {
//        this.client = null;
//        if(whichID==IDCONCEPTO) {
//            this.id = id;
//        }
//        else if(whichID==IDACTIVIDAD) {
//            this.idActividad = id;
//        }
//        this.loadBy = whichID;
//        load();
//    }
    
//    public Conceptos(BeanConceptos bean)
//    {
//        this.client = null;
//       
//        setBean(bean);
//    }
    
    public Conceptos(int id, byte whichID, IClient client)
    {
        if(whichID==IDCONCEPTO) {
            this.id = id;
        }
        else if(whichID==IDACTIVIDAD) {
            this.idActividad = id;
        }
        this.loadBy = whichID;
        this.client = client;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
        load();
    }
    
    public Conceptos(BeanConceptos bean, IClient client)
    {
        this.client = client;
        this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
        setBean(bean);
    }
    
    
    public BeanConceptos getBean()
    {
        BeanConceptos bean = new BeanConceptos();
        bean.evaluable = this.evaluable;
        bean.htmlColor = this.htmlColor;
        bean.id = this.id;
        bean.idClase = this.idClase;
        bean.nombre = this.nombre;
        bean.porcentaje = this.porcentaje;
        bean.textoActividad = this.textoActividad;
        bean.web = this.web;    
        bean.setIdProfesores(this.getIdProfesores());
        return bean;
    }
    
    public void setBean(BeanConceptos bean)
    {
        this.evaluable = bean.evaluable;
        this.htmlColor = bean.htmlColor;
        this.id = bean.id;
        this.idClase = bean.idClase;
        this.nombre = bean.nombre;
        this.porcentaje = bean.porcentaje;
        this.textoActividad = bean.textoActividad;
        this.web = bean.web;
        this.setIdProfesores(bean.getIdProfesores());
    }
    

    @Override
    public void load()
    {
        String SQL1 = "";
        if(loadBy==IDCONCEPTO) {
            SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_conceptos where id="+this.id;
        }
        else if(loadBy==IDACTIVIDAD) {
            SQL1 = "SELECT con.* FROM "+client.getPlusDbName()+"sgd_conceptosactividades AS ca INNER JOIN "+client.getPlusDbName()+"sgd_conceptos AS con ON con.id=ca.idConcepto "
                    + " where ca.idActividad="+this.idActividad;
        }
        
      
        try {
            Statement st = client.getPlusDb().createStatement();
            ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
                  this.evaluable=rs1.getString("evaluableActividad").equalsIgnoreCase("S");
                  this.web=rs1.getString("webActividad").equalsIgnoreCase("S");
                  this.id = rs1.getInt("id");
                  this.idClase = rs1.getInt("idClase");
                  this.htmlColor = rs1.getString("colorConcepto");
                  this.nombre = rs1.getString("nombreConcepto");
                  this.porcentaje = rs1.getInt("porcentajeConcepto");
                  this.textoActividad = rs1.getString("textoActividad");
                  this.idProfesores = rs1.getString("idProfesores");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conceptos.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public int save() {
       int nup = 0;
       
       if(this.id<=0 || !exists())
       {
           nup = insert();
       }
       else
       {
           nup = update();
       }
       return nup;
    }

    @Override
    public int update() {
        String SQL1 = "UPDATE "+client.getPlusDbName()+"sgd_conceptos SET idProfesores='" + this.idProfesores
                + "', idClase='" + this.idClase + "',nombreConcepto=?,"
                + "colorConcepto='" + this.htmlColor + "',porcentajeConcepto='" + 
                this.porcentaje + "',textoActividad=?,evaluableActividad='" + (this.evaluable ? "S" : "N") + "',"
                + "webActividad='" + (this.web ? "S" : "N") + "' WHERE id=" + this.id;

       Object[] obj = new Object[]{this.nombre, this.textoActividad};
       int nup =  client.getPlusDb().preparedUpdate(SQL1, obj);
       return nup;
    }

    @Override
    public int insert() {
        String SQL1 = "INSERT INTO "+client.getPlusDbName()+"sgd_conceptos (idProfesores, idClase,nombreConcepto,"
                + "colorConcepto,porcentajeConcepto,textoActividad,evaluableActividad,"
                + "webActividad) VALUES('"+this.idProfesores+"','"+this.idClase+"',?,'"
                +this.htmlColor+"','"+this.porcentaje+"',?,'"+(this.evaluable?"S":"N")
                +"','"+(this.web?"S":"N")+"')";
        
       Object[] obj = new Object[]{this.nombre, this.textoActividad};
       int nup =  client.getPlusDb().preparedUpdateID(SQL1, obj);
       if(nup>0){ 
           this.id= nup;
       }
       return nup;
    }

    @Override
    public boolean exists() {
        boolean exists = false;
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_conceptos where id="+this.id;
        
        
        try {
            Statement st = client.getPlusDb().createStatement();
            ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);
            if( rs1!=null && rs1.next() )
            { 
                exists = true;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conceptos.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return exists;
    }

    @Override
    public int delete() {
        if(this.id<=0) {
            return 0;
        }
        
        String SQL1 = "DELETE FROM "+client.getPlusDbName()+"sgd_conceptos where id="+this.id;        
        int nup = client.getPlusDb().executeUpdate(SQL1);
        
        SQL1 = "DELETE FROM "+client.getPlusDbName()+"sgd_conceptosactividades where idConcepto="+this.id;        
        nup += client.getPlusDb().executeUpdate(SQL1);
        
        return nup;
    }
    
    @Override
    public String toString()
    {
        String txt = "Concepto:: "+this.evaluable + "; "+
        this.htmlColor  + "; "+
        this.id  + "; "+
        this.idClase  + "; "+
        this.nombre  + "; "+
        this.porcentaje  + "; "+
        this.textoActividad  + "; "+
        this.web  + "; ";       
        
        return txt;
    }
    
    /**
     * Això és per evitar valors repetits dels conceptes
     * d'un mateix professor dins d'una mateixa idClase
     * @param bean
     * @return 
     */
    public boolean checkValidity(BeanConceptos bean)
    {
        boolean valid = true;
        
        if(bean.nombre.trim().equals("")) {
            return false;
        }
        
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_conceptos WHERE idProfesores="+bean.idProfesores+
                " AND idClase="+bean.idClase+" AND nombreConcepto=? AND id<>"+bean.id;
    
       
        try {
            PreparedStatement st = client.getPlusDb().createPreparedStatement(SQL1);
            ResultSet rs1 = client.getPlusDb().getPreparedResultSet(new Object[]{bean.nombre},st);  
            if(rs1!=null && rs1.next())
            {
                valid = false;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conceptos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valid;
    }
    
    
    //Same method for static
    public static boolean checkValidity(BeanConceptos bean, IClient client)
    {
        boolean valid = true;
        
        if(bean.nombre.trim().equals("")){
            return false;
        }
        
        String dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
        
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_conceptos WHERE idProfesores="+bean.idProfesores+
                " AND idClase="+bean.idClase+" AND nombreConcepto=? AND id<>"+bean.id;
    
       
        try {
            PreparedStatement st = client.getPlusDb().createPreparedStatement(SQL1);
            ResultSet rs1 = client.getPlusDb().getPreparedResultSet(new Object[]{bean.nombre},st);  
            if(rs1!=null && rs1.next())
            {
                valid = false;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conceptos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valid;
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
