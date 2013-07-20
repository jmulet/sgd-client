/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.profesores;

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
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class Profesores extends BeanProfesor implements IClientController{
    private final IClient client;
    
    public Profesores(String idProfesor)
    {
        this.client = null;
        this.idProfesor = idProfesor;
        load();        
    }
    
    public Profesores()
    {
        this.client = null;
    }
    
    public Profesores(String idProfesor, IClient client)
    {
        this.client = client;
        this.idProfesor = idProfesor;
        load();        
    }
    
    public Profesores(IClient client)
    {
        this.client = client;
    }
    
    
    public Profesores(final MyDatabase mysql, final String abrev)
    {
         this.client = null;
         String SQL1 = "SELECT idSGD FROM sig_professorat where abrev='"+abrev+"' ";
          
         try {
            Statement st = mysql.createStatement();
            ResultSet rs1 = mysql.getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                this.idProfesor= rs1.getString("idSGD");                          
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        load();
    }
    
    public static Profesores getProfesorAdmin()
    {
        Profesores admin = new Profesores();
        admin.setAbrev("ADMIN");
        admin.setEnviarSMS(true);
        admin.setIdProfesor("-1");
        admin.setNombre("Administrador");
        admin.setSystemUser("admin");
        return admin;
    }
    
    public void loadAbrev(final MyDatabase mysql)
    {
         String SQL1 = "SELECT abrev FROM sig_professorat where idSGD='"+this.idProfesor+"' ";
          
         try {
            Statement st = mysql.createStatement();
            ResultSet rs1 = mysql.getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                this.abrev= rs1.getString("abrev");                          
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    public BeanProfesor getBean()
    {
        BeanProfesor bean = new BeanProfesor();
        
        bean.bloqueoMyClass = this.bloqueoMyClass;
        bean.claveUP = this.claveUP;
        bean.enviarSMS = this.enviarSMS;
        bean.idClaseTutoria = this.idClaseTutoria;
        bean.idProfesor = this.idProfesor;
        bean.idUnidadesPersonales = this.idUnidadesPersonales;
        bean.nombre = this.nombre;
        bean.systemUser = this.systemUser;
        bean.tutor = this.tutor;
        
        
        return bean;
    }
    
    private void load()
    {
         String SQL1 = "SELECT * FROM profesores where id='"+this.idProfesor+"' ";
          
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                this.bloqueoMyClass = !(rs1.getDate("fechaBloqueoMyClass")==null);
                this.enviarSMS = StringUtils.noNull(rs1.getString("enviarMsgUp")).equalsIgnoreCase("S");
                this.claveUP = StringUtils.noNull(rs1.getString("claveUp"));
                this.nombre = StringUtils.noNull(rs1.getString("nombre"));
                this.idUnidadesPersonales = rs1.getInt("idUnidadesPersonales");
                this.idClaseTutoria = this.esTutor();
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        findSystemUser();
    }
    
    //Si idUP es alfanumeric, sql enten que esta cercant idUP=0; per aixo troba
    //professors amb aquesta id inventada
    public void loadByUp(String idUP)
    {
         String SQL1 = "SELECT * FROM profesores where idUnidadesPersonales='"+idUP+"' ";
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                this.idProfesor = rs1.getString("id");
                this.bloqueoMyClass = !(rs1.getDate("fechaBloqueoMyClass")==null);
                this.enviarSMS = StringUtils.noNull(rs1.getString("enviarMsgUp")).equalsIgnoreCase("S");
                this.claveUP = StringUtils.noNull(rs1.getString("claveUp"));
                this.nombre = StringUtils.noNull(rs1.getString("nombre"));
                this.idUnidadesPersonales = rs1.getInt("idUnidadesPersonales");
                this.idClaseTutoria = this.esTutor();
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         findSystemUser();
    }
    
    
    //Sinonim de load
     public void loadById(String id)
     {
         this.idProfesor = id;
         load();
     }
     
    public void loadByAbrev(String abrev)
    {
        String SQL1 = "SELECT idSGD FROM sig_professorat WHERE abrev='"+abrev+"' LIMIT 1;";
        try {
            Statement st = getMysql().createStatement();
            ResultSet rs1 = getMysql().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                this.idProfesor = rs1.getString(1);
                this.abrev=abrev;
                this.load();
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadByWinUser(String winUser)
    {
                  
        String SQL1 = " SELECT DISTINCT "
                + "prof.nombre,   "
                + "conf.usuario, "
                + "prof.id, "
                + "prof.claveUp,  "
                + "prof.fechaBloqueoMyClass,  "
                + "prof.enviarMsgUp,  "              
                + "prof.idUnidadesPersonales  "            
                + "FROM "
                + client.getConfigDB()+".usuarios AS conf  "
                + " INNER JOIN "
                + " profesores AS prof "
                + "ON prof.id=conf.idProfesores AND prof.claveUp=conf.clave "
                + " WHERE conf.usuario='"+winUser+"' ";
        
      
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                this.idProfesor = rs1.getString("id");
                this.bloqueoMyClass = !(rs1.getDate("fechaBloqueoMyClass")==null);
                this.enviarSMS = StringUtils.noNull(rs1.getString("enviarMsgUp")).equalsIgnoreCase("S");
                this.claveUP = StringUtils.noNull(rs1.getString("claveUp"));
                this.nombre = StringUtils.noNull(rs1.getString("nombre"));
                this.idUnidadesPersonales = rs1.getInt("idUnidadesPersonales");
                this.idClaseTutoria = this.esTutor();
                this.systemUser = rs1.getString("usuario");
                String abreviatura;
                if(client==null)
                {
                   abreviatura = new ProfesoresCollection().getAbrev(this.idProfesor);
                }
                else
                {
                   abreviatura = client.getProfesoresCollection().getAbrev(this.idProfesor);
                }
                this.abrev = abreviatura;
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    public void unlock() {
        
        String SQL1 = "UPDATE profesores SET fechaBloqueoMyClass=NULL where id='"+this.idProfesor+"'";
        int nup = getSgd().executeUpdate(SQL1);
        if(nup>0) {
            this.setBloqueoMyClass(false);
        }
    }

   public void lock() {
        String SQL1 = "UPDATE profesores SET fechaBloqueoMyClass=NOW() where id='"+this.idProfesor+"'";
        int nup = getSgd().executeUpdate(SQL1);
        if(nup>0) {
           this.setBloqueoMyClass(true);
       }
    }

    
    public String getAbrev(MyDatabase mysql)
    {
         String abrev2="";
         String SQL1 = "SELECT abrev FROM sig_professorat where idSGD='"+this.idProfesor+"' ";
          
         try {
            Statement st = mysql.createStatement();
            ResultSet rs1 = mysql.getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                abrev2= rs1.getString("abrev");                          
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.abrev = abrev2;
        return abrev2;
    }
  
    
   
    
    
    
    //Aquesta query no funciona si el grup no te una assignatura de tutoria
    //retorna idClase tutoria
    //Carrega la variable boolean tutor per saber si ets o no tutor.
    protected int esTutor()
    {
        int estutor = -1;
        
        //Detecta ja sigui si ets tutor d'aquest grup o cotutor
        String SQL1 = "SELECT g.idProfesores, cg.idProfesores FROM grupos AS g "
                   + " LEFT JOIN cotutoresgrupo AS cg ON cg.grupoGestion=g.grupoGestion "
                   + " WHERE g.idProfesores='"+this.idProfesor+"' OR cg.idProfesores='"+this.idProfesor+"'";
      
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL1,st);
            if(rs!=null && rs.next())
            {
                tutor = true;
            }
            if(rs!=null)
            {
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SQL1 = " SELECT DISTINCT "
                + " class.nombre, "
                + "  au.descripcionLarga, "
                + " class.id AS idClase "
                + " FROM "
                + "  horarios AS h  "
                + "  INNER JOIN "
                + "  aulas AS au "
                + "  ON (h.idAulas = au.id)  "
                + "  INNER JOIN "
                + "  clases AS class  "
                + " ON (h.idClases = class.id)  "
                + " INNER JOIN "
                + " asignaturas AS a ON 1=1 "
                + "  INNER JOIN "
                + " clasesdetalle AS cd  "
                + "  ON 1 = 1  "
                + "  INNER JOIN "
                + "  grupasig AS ga  "
                + "  ON 1 = 1  "
                + "  INNER JOIN "
                + "  grupos AS g  "
                + "  ON 1 = 1  "
                + "  INNER JOIN "
                + "  asignaturasalumno AS aa  "
                + "  ON 1 = 1  "
                + " WHERE h.idProfesores='" + this.idProfesor + "' "
                + "  AND aa.idGrupAsig = ga.id "
                + "   AND ( "
                + "     aa.opcion <> '0'  "
                + "    AND ( "
                + "      cd.opcion = 'X'  "
                + "      OR cd.opcion = aa.opcion "
                + "    ) "
                + "  )  "
                + "  AND h.idClases = cd.idClases  "
                + "  AND cd.idGrupAsig = ga.id  "
                + "  AND ga.idGrupos = g.id  "
                + "  AND ga.idAsignaturas = a.id  "
                + "  AND a.descripcion LIKE 'TUT%' ";

        
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if( rs1!=null && rs1.next() )
            { 
              estutor = rs1.getInt("idClase");
            } 
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return estutor;
    }
    
    public String print()
    {
        
        String txt = "idProfesor="+this.idProfesor+"; "+
               "idUnidadesPersonales="+this.idUnidadesPersonales+";"+
               "nombre="+this.nombre+"; "+
               "claveUP="+ this.claveUP+"; "+
               "enviarSMS="+this.enviarSMS+"; "+
               "bloqueo="+this.bloqueoMyClass+"; "+
               "tutor="+(this.idClaseTutoria>0)+"; "+      
               "idClaseTutoria="+this.idClaseTutoria;      
               
                       
        return txt;
    }

    private void findSystemUser() {
        //based on clave UP and idPersonal and idUP
        String SQL1 = "SELECT usuario FROM `"+client.getConfigDB()+"`.usuarios as u "
                + "INNER JOIN `"+client.getConfigDB()+"`.permisoscurso AS pc ON pc.idUsuarios=u.id WHERE pc.curso='"+client.getAnyAcademic()+"' AND pc.idProfesores='"+this.idProfesor+"' LIMIT 1";
         
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                this.systemUser = rs1.getString(1);                    
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
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
