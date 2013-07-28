/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.actividades.Actividad;
import org.iesapp.clients.sgd7.actividades.ActividadAlumno;
import org.iesapp.clients.sgd7.actividades.ActividadClase;
import org.iesapp.clients.sgd7.actividades.ActividadesCollection;
import org.iesapp.clients.sgd7.actividades.BeanActividad;
import org.iesapp.clients.sgd7.actividades.BeanActividadClase;
import org.iesapp.clients.sgd7.actividades.BeanActividadesAlumno;
import org.iesapp.clients.sgd7.actividades.BeanConceptos;
import org.iesapp.clients.sgd7.actividades.Conceptos;
import org.iesapp.clients.sgd7.alumnos.Alumnos;
import org.iesapp.clients.sgd7.alumnos.AlumnosCollection;
import org.iesapp.clients.sgd7.alumnos.BeanAlumno;
import org.iesapp.clients.sgd7.base.TipoIncidencias;
import org.iesapp.clients.sgd7.base.TipoObservaciones;
import org.iesapp.clients.sgd7.clases.AnotacionesClase;
import org.iesapp.clients.sgd7.clases.BeanClase;
import org.iesapp.clients.sgd7.clases.BeanClaseGuardia;
import org.iesapp.clients.sgd7.clases.Clases;
import org.iesapp.clients.sgd7.clases.ClasesCollection;
import org.iesapp.clients.sgd7.clases.ClasesGuardia;
import org.iesapp.clients.sgd7.clases.HoraCentro;
import org.iesapp.clients.sgd7.clasesanotadas.BeanClasesAnotadas;
import org.iesapp.clients.sgd7.clasesanotadas.ClasesAnotadas;
import org.iesapp.clients.sgd7.clasesanotadas.ClasesAnotadasCollection;
import org.iesapp.clients.sgd7.evaluaciones.EvaluacionesCollection;
import org.iesapp.clients.sgd7.incidencias.BeanIncidencias;
import org.iesapp.clients.sgd7.incidencias.Incidencias;
import org.iesapp.clients.sgd7.incidencias.IncidenciasCollection;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.clients.sgd7.mensajes.Mensajes;
import org.iesapp.clients.sgd7.mensajes.MensajesCollection;
import org.iesapp.clients.sgd7.mensajes.MensajesListas;
import org.iesapp.clients.sgd7.mensajes.MensajesListasProfesores;
import org.iesapp.clients.sgd7.mensajes.MensajesProfesores;
import org.iesapp.clients.sgd7.profesores.FaltasProfesores;
import org.iesapp.clients.sgd7.profesores.Profesores;
import org.iesapp.clients.sgd7.profesores.ProfesoresCollection;
import org.iesapp.clients.sgd7.reports.InformesSGD;
import org.iesapp.clients.sgd7.tareas.TareasCollection;
import org.iesapp.database.Compare;
import org.iesapp.database.CompareIncidence;
import org.iesapp.database.MyDatabase;
import org.iesapp.database.ScriptRunner;
import org.iesapp.util.Version;

/**
 *
 * @author Josep
 */
@Version(version="7.01.0065")
public class SgdClient implements IClient{
    private final MyDatabase mysql;
    private final MyDatabase sgd;
    private final int anyAcademic;
    private Profesores user = null;
    private Log logger;
    private final String host;
    private final String hostIp;
    
    //Utility-singleton per session classes
    private AlumnosCollection alumnosCollection;
    private ActividadesCollection actividadesCollection;
    private TipoIncidencias tipoIncidencias;
    private TipoObservaciones tipoObservaciones;
    private AnotacionesClase anotacionesClase;
    private ClasesCollection clasesCollection;
    private HoraCentro horaCentro;
    private ClasesAnotadasCollection clasesAnotadasCollection;
    private IncidenciasCollection incidenciasCollection;
    private MensajesCollection mensajesCollection;
    private ProfesoresCollection profesoresCollection;
    private TareasCollection tareasCollection;
    private EvaluacionesCollection evaluacionesCollection;
    private InformesSGD informesSGD;
    private final String configDB;
    protected final String currentDBPrefix;
    private int checkedYear;
    private ArrayList<CompareIncidence> compareStructure2;
    private ArrayList<CompareIncidence> compareStructure1;
   
    public SgdClient(MyDatabase mysql, MyDatabase sgd, int anyAcademic, String currentDBPrefix, String configDB, String host, String hostIp)
    {
        //Generates a new client facade instance
        this.mysql = mysql;
        this.host = host;
        this.hostIp = hostIp;
        this.sgd = sgd;
        this.anyAcademic = anyAcademic;         
        this.configDB = configDB;
        this.currentDBPrefix = currentDBPrefix;
    }
    
    
    //INTERFACE METHODS------
    
    @Override
    public MyDatabase getMysql() {
        return mysql;
    }

    @Override
    public MyDatabase getSgd() {
        return sgd;
    }

    @Override
    public int getAnyAcademic() {
        return anyAcademic;
    }

    @Override
    public Log getLogger() {
        return logger;
    }
    
    @Override
    public Profesores getUser() {
        return user;
    }
    
     public void setUser(Profesores user, boolean enableLogger) {
        this.user = user;
        //Create logger when user has been set
        logger = new Log(host, hostIp, (IClient) this);
        logger.setEnable(enableLogger);
    }
    
    
    
    //FACADE METHODS -----------
     //Clase Actividad
    public Actividad getActividad(int id) {
        return new Actividad(id, (IClient) this);
    }

    public Actividad getActividad(BeanActividad bean) {
        return new Actividad(bean, (IClient) this);
    }

    public Actividad getActividad(BeanActividadClase bean) {
        return new Actividad(bean, (IClient) this);
    }

    public Actividad getActividad() {
      return new Actividad((IClient) this);
    }
   
    //Classe ActividadesAlumno
    
    public ActividadAlumno getActividadAlumno(int id)
    {
       return new ActividadAlumno(id, (IClient) this);
    }
    
    @Override
    public ActividadAlumno getActividadAlumno(BeanActividadesAlumno bean)
    {
        return new ActividadAlumno(bean, (IClient) this);
    }
  
    //Classe ActividadesClase
    
      public ActividadClase getActividadClase(String idProfesores, int idClase, ArrayList<Integer> listGrupAsig)
      {
         return new ActividadClase(idProfesores, idClase, listGrupAsig, (IClient) this);
      }

      public ActividadClase getActividadClase(String idProfesores, BeanClase bean)
      {
         return new ActividadClase(idProfesores, bean, (IClient) this);
      }
    
   
    public ActividadClase getActividadClase(String idProfesores, int idClase)
    { 
        return new ActividadClase(idProfesores, idClase, (IClient) this);
    }
    
    public ActividadClase getActividadClase(HashMap<Integer,Integer> mapa)
    {    
        return new ActividadClase(mapa, (IClient) this);
    }
   
    
    public ActividadClase getActividadClase(BeanActividadClase bean)
    {    
       return new ActividadClase(bean, (IClient) this);          
    }
    
    //Clase Alumnos
   
    public Alumnos getAlumnos(int id)
    {
        return new Alumnos(id, (IClient) this);
    }
    
    public Alumnos getAlumnos(BeanAlumno bean)
    {
       return new Alumnos(bean, (IClient) this);
    }
    
    //Clase Clases
    public Clases getClases(String idProfesor, int idDiaSetmana)
    {
       return new Clases(idProfesor, idDiaSetmana, (IClient) this);
    }
    
    public Clases getClases(String idProfesor)
    {
       return new Clases(idProfesor, (IClient) this);
    }
    
    //Clase ClasesGuardia
    public ClasesGuardia getClasesGuardia(int idDiaSetmana, int idHoraCentro)
    {
       return new ClasesGuardia(idDiaSetmana, idHoraCentro, (IClient) this);
    }
    
    //Clase ClasesAnotadas
    public ClasesAnotadas getClasesAnotadas()
    {
        return new ClasesAnotadas((IClient) this);
    }
    
    public ClasesAnotadas getClasesAnotadas(int id)
    {
        return new ClasesAnotadas(id, (IClient) this);
    }
    
    public ClasesAnotadas getClasesAnotadas(String idProfesores, String idProfesoresReal, int idHorasCentro, int idGrupAsig)
    {
       return new ClasesAnotadas(idProfesores, idProfesoresReal, idHorasCentro, idGrupAsig, (IClient) this);
    }
    
    public ClasesAnotadas getClasesAnotadas(BeanClasesAnotadas bean)
    {
       return new ClasesAnotadas(bean,(IClient) this);
    }
    
    //Clase conceptos
    public Conceptos getConceptos(int id, byte whichID)
    {
       return new Conceptos(id,whichID,(IClient) this);
    }
    
    public Conceptos getConceptos(BeanConceptos bean)
    {
       return new Conceptos(bean,(IClient) this); 
    }
    
    //Clase Incidencias
     public Incidencias getIncidencias()
    {
       return new Incidencias((IClient) this);
    }
    public Incidencias getIncidencias(int id)
    {
        return new Incidencias(id, (IClient) this);
    }
    public Incidencias getIncidencias(BeanIncidencias bean)
    {
        return new Incidencias(bean, (IClient) this);
    }
    
      //Clase Mensajes
    public Mensajes getMensajes() {
        return new Mensajes((IClient) this);
    }

    public Mensajes getMensajes(int id) {
        return new Mensajes(id, (IClient) this);
    }

    public Mensajes getMensajes(int idRemite, String text, int idProfe) {
        return new Mensajes(idRemite, text, idProfe, (IClient) this);
    }

    public Mensajes getMensajes(int idRemite, String text, List<Integer> idProfesores) {
        return new Mensajes(idRemite, text, idProfesores, (IClient) this);
    }
    
    //Clase MensajesListas
    public MensajesListas getMensajesListas() {
        return new MensajesListas((IClient) this);
    }
    
     //Clase MensajesListasProfesores
    public MensajesListasProfesores getMensajesListasProfesores() {
        return new MensajesListasProfesores((IClient) this);
    }
    
     //Clase MensajesProfesores
    public MensajesProfesores getMensajesProfesores(int id) {
        return new MensajesProfesores(id, (IClient) this);
    }
    
    //Clase FaltasProfesores
    public FaltasProfesores getFaltasProfesores()
    {      
        return new FaltasProfesores((IClient) this);
    }
 
    public FaltasProfesores getFaltasProfesores(final BeanClaseGuardia bcg, final String idProfesor2, 
                            final String comentario, java.util.Date dia,
                            int idTipoIncidencias, String simbolo, String descripcion)
    {
        return new FaltasProfesores(bcg, idProfesor2, comentario, dia,
                           idTipoIncidencias, simbolo, descripcion, (IClient) this);
    }
    
    //Clase Profesores
    public Profesores getProfesores(String idProfesor)
    {
        return new Profesores(idProfesor, (IClient) this);   
    }
    
    public Profesores getProfesores()
    {
        return new Profesores((IClient) this);
    }
    
    
    //// Single Instance utility classes-----------------------------------------
    @Override
    public ActividadesCollection getActividadesCollection()
    {
       if(actividadesCollection==null)
       {
           actividadesCollection = new ActividadesCollection((IClient) this);
       }
       return actividadesCollection;
    } 
    
    @Override
    public AlumnosCollection getAlumnosCollection()
    {
       if(alumnosCollection==null)
       {
           alumnosCollection = new AlumnosCollection((IClient) this);
       }
       return alumnosCollection;
    }
    
    @Override
    public TipoIncidencias getTipoIncidencias()
    {
       if(tipoIncidencias==null)
       {
           tipoIncidencias = new TipoIncidencias((IClient) this);
       }
       return tipoIncidencias;
    }
    
    @Override
    public TipoObservaciones getTipoObservaciones()
    {
       if(tipoObservaciones==null)
       {
           tipoObservaciones = new TipoObservaciones((IClient) this);
       }
       return tipoObservaciones;
    }
    
    
    public AnotacionesClase getAnotacionesClase()
    {
       if(anotacionesClase==null)
       {
           anotacionesClase = new AnotacionesClase((IClient) this);
       }
       return anotacionesClase;
    }
    
    public ClasesCollection getClasesCollection()
    {
       if(clasesCollection==null)
       {
           clasesCollection = new ClasesCollection((IClient) this);
       }
       return clasesCollection;
    }
  
      
    public EvaluacionesCollection getEvaluacionesCollection()
    {
       if(evaluacionesCollection==null)
       {
           evaluacionesCollection = new EvaluacionesCollection((IClient) this);
       }
       return evaluacionesCollection;
    }
    
    @Override
    public HoraCentro getHoraCentro()
    {
       if(horaCentro==null)
       {
           horaCentro = new HoraCentro((IClient) this);
       }
       return horaCentro;
    }
    
    public ClasesAnotadasCollection getClasesAnotadasCollection()
    {
       if(clasesAnotadasCollection==null)
       {
           clasesAnotadasCollection = new ClasesAnotadasCollection((IClient) this);
       }
       return clasesAnotadasCollection;
    }
    
    public IncidenciasCollection getIncidenciasCollection()
    {
       if(incidenciasCollection==null)
       {
           incidenciasCollection = new IncidenciasCollection((IClient) this);
       }
       return incidenciasCollection;
    }
    
    @Override
    public MensajesCollection getMensajesCollection()
    {
       if(mensajesCollection==null)
       {
           mensajesCollection = new MensajesCollection((IClient) this);
       }
       return mensajesCollection;
    }
    
    @Override
    public ProfesoresCollection getProfesoresCollection()
    {
       if(profesoresCollection==null)
       {
           profesoresCollection = new ProfesoresCollection((IClient) this);
       }
       return profesoresCollection;
    }
    
   
    public TareasCollection getTareasCollection()
    {
       if(tareasCollection==null)
       {
           tareasCollection = new TareasCollection((IClient) this);
       }
       return tareasCollection;
    }
    
    public InformesSGD getInformesSGD()
    {
        if(informesSGD==null)
        {
            informesSGD = new InformesSGD((IClient) this);
       }
       return informesSGD;
    }
    
    public void dispose()
    {
    	alumnosCollection = null;
        actividadesCollection = null;
        tipoIncidencias = null;
        tipoObservaciones = null;
        anotacionesClase = null;
        clasesCollection = null;
        horaCentro = null;
        clasesAnotadasCollection = null;
        incidenciasCollection = null;
        mensajesCollection = null;
        profesoresCollection = null;
        tareasCollection = null;
        evaluacionesCollection = null;
        informesSGD = null;    
    }

    @Override
    public String getConfigDB() {
        return configDB;
    }

    @Override
    public String getCurrentDBPrefix() {
        return currentDBPrefix;
    }

    public String getClientVersion() {
                
        Version annotation = this.getClass().getAnnotation(Version.class);
        if(annotation!=null)
        {
            return annotation.version();
        }
        
        return "[7.01.0065]";
    }
    
    /**
     * Returns the number of cursoxxxx cursos existing in host
     * curso is the mysqldbprefix specified in the system
     * @return 
     */
    public ArrayList<Integer> getAllYears()
    {
        ArrayList<Integer> list = new  ArrayList<Integer>();
        String SQL1  = "SELECT RIGHT(SCHEMA_NAME,4)+0 FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME LIKE '"
                +getCurrentDBPrefix()+"____'";
        try {
            Statement st = getMysql().createStatement();
            ResultSet rs = getMysql().getResultSet(SQL1,st);
            while(rs!=null && rs.next())
            {
               list.add(rs.getInt(1));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return list;
    }
    
    
    //Defines location for the sgd-addons tables
    @Override
    public String getPlusDbName() {
        //return getCurrentDBPrefix()+getAnyAcademic()+"plus.";
        return "";
    }
    
    //Defines database for sgd-addons tables
    @Override
    public MyDatabase getPlusDb() {
        //return sgd;
        return mysql;
    }

    @Override
    public String checkDatabases(int year) {
        
        //Create a clone of database connection
        MyDatabase sgdClone = new MyDatabase(sgd.getConBean());
        boolean connect = sgdClone.connect();
        if(!connect)
        {
            return "ERROR: no database connection";
        }
        
        String result="";
        this.checkedYear = year;
        this.compareStructure1 = null;
        this.compareStructure2 = null;
        
        if(configDB!=null && !configDB.isEmpty())
        {
        boolean doesSchemaExists = sgdClone.doesSchemaExists(configDB);
        if(!doesSchemaExists)
        {
            System.out.println("Creating configDB");
            sgdClone.executeUpdate("CREATE DATABASE "+configDB);
            sgdClone.setCatalog(configDB);
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/sgd7/sql/config.sql");
            ScriptRunner srun = new ScriptRunner(sgdClone.getConnection(), true, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //Check it against script
            String tmpDB = "tmp"+Math.round(Math.random()*1e8);
            sgdClone.executeUpdate("CREATE DATABASE "+tmpDB);
            System.out.println("Creating "+ tmpDB);
            sgdClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/sgd7/sql/config.sql");
            ScriptRunner srun = new ScriptRunner(sgdClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Compare compare = new Compare(sgdClone, tmpDB, sgd, this.configDB);
            compareStructure1 = compare.compareStructure();
            for (CompareIncidence ci: compareStructure1)
            {
                result += ci.toString();
            }
            sgdClone.executeUpdate("DROP DATABASE "+tmpDB);
           
        }
        }
        
        boolean doesSchemaExists = sgdClone.doesSchemaExists(currentDBPrefix+year);
        if(!doesSchemaExists)
        {
            System.out.println("Creating "+ currentDBPrefix+year);

            sgdClone.executeUpdate("CREATE DATABASE "+currentDBPrefix+year);
            sgdClone.setCatalog(currentDBPrefix+year);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/sgd7/sql/cursoxxxx.sql");
            ScriptRunner srun = new ScriptRunner(sgdClone.getConnection(), true, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //Check it with script tmp backup
            String tmpDB = "tmp"+Math.round(Math.random()*1e8);
            sgdClone.executeUpdate("CREATE DATABASE "+tmpDB);
            System.out.println("Creating "+ tmpDB);
            sgdClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/sgd7/sql/cursoxxxx.sql");
            ScriptRunner srun = new ScriptRunner(sgdClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Compare compare = new Compare(sgdClone, tmpDB, sgd, this.currentDBPrefix+year);
            compareStructure2 = compare.compareStructure();
            for (CompareIncidence ci: compareStructure2)
            {
                result += ci.toString();
            }
            sgdClone.executeUpdate("DROP DATABASE "+tmpDB);
            
        }
        sgdClone.close();
        return result;
    }

    @Override
    public String fixDatabases() {
        if (this.compareStructure2 == null && this.compareStructure1 == null) {
            return "ERROR: CheckDatabases must be invoked before fixDatabases";
        }
        //Create a clone of database connection
        MyDatabase sgdClone = new MyDatabase(sgd.getConBean());
        boolean connect = sgdClone.connect();
        if (!connect) {
            return "ERROR: no database connection";
        }
        
        String result = "";
        
        if (this.compareStructure1 != null && !this.compareStructure1.isEmpty()) {
            //Set up temporal database
            //Check it with script tmp backup
            String tmpDB = "tmp" + Math.round(Math.random() * 1e8);
            sgdClone.executeUpdate("CREATE DATABASE " + tmpDB);
            sgdClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/sgd7/sql/config.sql");
            ScriptRunner srun = new ScriptRunner(sgdClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            Compare compare = new Compare(sgdClone, tmpDB, sgd, this.configDB);
            ArrayList<CompareIncidence> notSolved = compare.fixStructure(compareStructure1);

            for (CompareIncidence ci : notSolved) {
                result += ci.toString();
            }
            sgdClone.executeUpdate("DROP DATABASE " + tmpDB);
        }
        
        if (this.compareStructure2 != null && !this.compareStructure2.isEmpty()) {
            //Set up temporal database
            //Check it with script tmp backup
            String tmpDB = "tmp" + Math.round(Math.random() * 1e8);
            sgdClone.executeUpdate("CREATE DATABASE " + tmpDB);
            sgdClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/sgd7/sql/cursoxxxx.sql");
            ScriptRunner srun = new ScriptRunner(sgdClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SgdClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            Compare compare = new Compare(sgdClone, tmpDB, sgd, this.currentDBPrefix + this.checkedYear);
            ArrayList<CompareIncidence> notSolved = compare.fixStructure(compareStructure2);

            for (CompareIncidence ci : notSolved) {
                result += ci.toString();
            }
            sgdClone.executeUpdate("DROP DATABASE " + tmpDB);
        }
        
        
        sgdClone.close();
        this.compareStructure1 = null;
        this.compareStructure2 = null;

        return result;
    }

}
