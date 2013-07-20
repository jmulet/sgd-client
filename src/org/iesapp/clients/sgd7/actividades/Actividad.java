/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;


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
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;

/**
 *
 * @author Josep
 */
public final class Actividad extends BeanActividad implements BeanController, IClientController {
    private final IClient client;
   
    /**
     * Load the activity with given id in database
     *
     * @param id
     *
     */
//    public Actividad(int id) {
//        this.client = null;
//        this.id = id;
//        load();
//    }

//    public Actividad(BeanActividad bean) {
//        this.client = null;
//        setBean(bean);
//    }
//
//    public Actividad(BeanActividadClase bean) {
//        this.client = null;
//        setBeanClase(bean);
//    }
//
//    public Actividad() {
//        this.client = null;
//        blank();
//    }
    
    //ICLIENT Constructors
    public Actividad(int id, IClient client) {
        this.client = client;
        this.id = id;
        load();
    }

    public Actividad(BeanActividad bean, IClient client) {
        this.client = client;
        setBean(bean);
    }

    public Actividad(BeanActividadClase bean, IClient client) {
        this.client = client;
        setBeanClase(bean);
    }

    public Actividad(IClient client) {
        this.client = client;
        blank();
    }

    public void setBean(BeanActividad bean) {
        this.descripcion = bean.descripcion;
        this.evaluable = bean.evaluable;
        this.fecha = bean.fecha;
        this.idEvaluacionesDetalle = bean.idEvaluacionesDetalle;
        this.setIdProfesores(bean.getIdProfesores());
        this.ordre = bean.ordre;
        this.idPeso = bean.idPeso;
        this.peso = bean.peso;
        this.publicarWEB = bean.publicarWEB;
        this.seguimiento = bean.seguimiento;
        this.id = bean.id;
        this.idGrupAsig = bean.idGrupAsig;
    }

    private void setBeanClase(BeanActividadClase bean) {
        this.descripcion = bean.descripcion;
        this.evaluable = bean.evaluable;
        this.fecha = bean.fecha;
        this.idEvaluacionesDetalle = bean.idEvaluacionesDetalle;
        this.setIdProfesores(bean.getIdProfesores());
        this.ordre = bean.ordre;
        this.peso = bean.peso;
        this.publicarWEB = bean.publicarWEB;
        this.seguimiento = bean.seguimiento;
        this.concepto = bean.concepto;
        this.idPeso = bean.idPeso;
    }

    public BeanActividad getBean() {
        BeanActividad bean = new BeanActividad();
        bean.descripcion = this.descripcion;
        bean.evaluable = this.evaluable;
        bean.fecha = this.fecha;
        bean.idEvaluacionesDetalle = this.idEvaluacionesDetalle;
        bean.setIdProfesores(this.getIdProfesores());
        bean.ordre = this.ordre;
        bean.peso = this.peso;
        bean.publicarWEB = this.publicarWEB;
        bean.seguimiento = this.seguimiento;
        bean.id = this.id;
        bean.idGrupAsig = this.idGrupAsig;
        bean.concepto = this.concepto;
        return bean;
    }

    public void blank() {
        this.descripcion = "";
        this.evaluable = true;
        this.fecha = new java.sql.Date(new java.util.Date().getTime());
        this.id = -1;
        this.publicarWEB = true;
        this.seguimiento = false;
        this.idConceptosEvaluables = 0;
        this.idEvaluacionesDetalle = 0;
        this.idGrupAsig = 0;
        this.ordre = 0;
        this.peso = 0;
        this.idPeso = 0;

    }

    @Override
    public void load() {

        String selects = " act.id, "
                + "  act.idProfesores, "
                + "  act.idGrupAsig, "
                + "  act.idEvaluacionesDetalle, "
                + "  act.descripcion, "
                + "  act.fecha, "
                + "  act.publicarWeb, "
                + "  act.evaluable,"
                + "  act.seguimiento,"
                + "  IF(pes.peso IS NULL, 0, pes.peso) AS peso, "
                + "  pes.id as idpeso,"
                + "  pes.idConceptosEvaluables ";

        String SQL1 = "SELECT DISTINCT " + selects + " FROM actividades as act LEFT JOIN pesoactividades as pes ON "
                + "act.id=pes.idActividades WHERE act.id=" + id;
        //System.out.println(SQL1);
       
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if (rs1 != null && rs1.next()) {

                String desc = rs1.getString("descripcion");
                this.fecha = rs1.getDate("fecha");
                this.idEvaluacionesDetalle = rs1.getInt("idevaluacionesdetalle");
                this.id = rs1.getInt("id");
                this.publicarWEB = rs1.getString("publicarWEB").equalsIgnoreCase("S");
                this.seguimiento = rs1.getString("seguimiento").equalsIgnoreCase("S");
                this.evaluable = rs1.getString("evaluable").equalsIgnoreCase("S");
                this.peso = rs1.getInt("peso");
                this.idPeso = rs1.getInt("idpeso");
                this.idGrupAsig = rs1.getInt("idGrupasig");
                this.idConceptosEvaluables = rs1.getInt("idConceptosEvaluables");
                this.setIdProfesores(rs1.getString("idProfesores"));
                this.descripcion = desc;

                this.concepto = new Conceptos(this.id, Conceptos.IDACTIVIDAD, client).getBean();
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean exists() {
        boolean exists = false;
        if (this.id <= 0) {
            return exists;
        }

        String SQL1 = "Select * from actividades where id=" + this.id;
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if (rs1 != null && rs1.next()) {
                exists = true;
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }

        return exists;
    }

    @Override
    public int save() {
        int nup = 0;
        if (this.id <= 0 || !this.exists()) {
            nup = insert();
        } else {
            nup = update();
        }

        return nup;
    }

    @Override
    public int insert() {
        String txtpw = this.publicarWEB ? "S" : "N";
        String txtseg = this.seguimiento ? "S" : "N";
        String txteval = this.evaluable ? "S" : "N";
        if (this.fecha == null) {
            this.fecha = new java.sql.Date(new java.util.Date().getTime());
        }

//NOVA ESTRUCTURA
//idint(11) NOT NULL
//idProfesoresint(11) NOT NULL
//idGrupAsigint(11) NOT NULL
//idEvaluacionesDetalleint(11) NOT NULL
//idSistemasNotasint(11) NOT NULL                <---------------nou camp (set to 1)
//descripcionvarchar(255) NOT NULL
//fechadate NOT NULL
//publicarWebchar(1) NOT NULL
//seguimientochar(1) NOT NULL
//evaluablechar(1) NOT NULL

        String SQL1 = "INSERT INTO actividades (idProfesores,idGrupAsig,idEvaluacionesDetalle,idSistemasNotas,descripcion,"
                + "fecha,publicarWeb,seguimiento,evaluable) VALUES('" + this.getIdProfesores()
                + "','" + this.idGrupAsig + "','" + this.idEvaluacionesDetalle + "'," + this.idSistemasNotas + ",?,?,'" + txtpw + "','" + txtseg + "','" + txteval + "')";


        Object[] obj = new Object[]{this.descripcion, fecha};

        int nup = getSgd().preparedUpdateID(SQL1, obj);
        if (nup > 0) {
            this.id = nup;

            //Logger
             {
                Log log = getLogger();
                log.setTabla("Actividades");
                log.setDatos("id=" + nup + ";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(getSgd().getLastPstm());
                log.postLog();
                log = null;
            }
        }

        //Ara inserta el pes de l'activitat
        savePeso();

        //Ara inserta el concepte de l'activitat associada
        saveConcepto();

        return nup;
    }

    @Override
    public int update() {
        String txtpw = this.publicarWEB ? "S" : "N";
        String txtseg = this.seguimiento ? "S" : "N";
        String txteval = this.evaluable ? "S" : "N";
        String txtfecha = this.fecha == null ? "NULL" : "'" + new DataCtrl(fecha).getDataSQL() + "'";


        String SQL1 = "UPDATE actividades SET idProfesores='" + this.getIdProfesores() + "', idGrupAsig='" + this.idGrupAsig + "',"
                + "idEvaluacionesDetalle='" + this.idEvaluacionesDetalle + "',descripcion=?,"
                + "fecha=" + txtfecha + ",publicarWeb='" + txtpw + "',seguimiento='" + txtseg + "',evaluable='" + txteval + "' "
                + " WHERE id=" + this.id;

        Object[] obj = new Object[]{this.descripcion};

        int nup = getSgd().preparedUpdate(SQL1, obj);

        //Logger
        if (nup > 0) {
            Log log = getLogger();
            log.setTabla("Actividades");
            log.setDatos("id=" + this.id + ";");
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
            log.setSentenciaSQL(getSgd().getLastPstm());
            log.postLog();
            log = null;
        }

        //Ara inserta el pes de l'activitat
        if(this.isEvaluable())
        {
            savePeso();
        }
        else
        {
            //Si l'activitat ha deixat d'esser avaluable, esborra els pesos associats
            //Per compatibilitat no ho faig
            //deletePesos();
        }

        //Ara inserta el concepte de l'activitat associada
        saveConcepto();

        return nup;
    }

    public int savePeso() {
        //No es pot desar el pes si no he creat abans l'activitat amb id
        if (this.id <= 0) {
            return -1;
        }

        int nup = 0;
        if (getCountPesos()>0) {
            String SQL1 = "UPDATE pesoactividades SET idConceptosEvaluables='" + this.idConceptosEvaluables
                    + "', peso='" + this.peso
                    + "' WHERE idActividades=" + this.id;
            nup = getSgd().executeUpdateID(SQL1);
             //Logger
            if ( nup > 0) {
                Log log = getLogger();
                log.setTabla("PesoActividades");
                log.setDatos("id=" + nup + ";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
           
        } else {
            String SQL1 = "INSERT INTO pesoactividades (idActividades, idConceptosEvaluables, peso)"
                    + " VALUES('" + this.id + "','" + this.idConceptosEvaluables + "','" + this.peso + "')";
            this.idPeso = getSgd().executeUpdateID(SQL1);
            nup = this.idPeso;
            
            //Logger
            if ( this.idPeso > 0) {
                Log log = getLogger();
                log.setTabla("PesoActividades");
                log.setDatos("id=" + this.idPeso + ";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }

    return nup ;
}
    public int saveConcepto() {
        //System.out.println("estic en saveConcepto");
        if(this.id<=0){
            return -1;
        }
        int nup = 0;
        if (this.concepto.id <= 0) {
            String SQL1 = "DELETE FROM "+client.getPlusDbName()+"sgd_conceptosactividades WHERE idActividad=" + this.id;
            nup = client.getPlusDb().executeUpdate(SQL1);
        } else {

            String SQL1 = "UPDATE "+client.getPlusDbName()+"sgd_conceptosactividades SET idConcepto=" + this.concepto.id
                    + " WHERE idActividad=" + this.id;
            nup = client.getPlusDb().executeUpdate(SQL1);

            if (nup <= 0) {
                SQL1 = "INSERT INTO "+client.getPlusDbName()+"sgd_conceptosactividades (idActividad, idConcepto)"
                        + " VALUES('" + this.id + "','" + this.concepto.id + "')";

                client.getPlusDb().executeUpdate(SQL1);
            }
        }
        return nup;
    }
    
    /**
     * Esborra nomes l'entrada de la taula actividades
     * Veure deleteAll()
     * @return 
     */
    @Override
        public int delete()
    {
        String SQL1 = "DELETE FROM actividades where id="+this.id+" LIMIT 1;";
        int nup = getSgd().executeUpdate(SQL1);
        
        //Logger
         
         {
                Log log = getLogger();
                log.setTabla("Actividades");
                log.setDatos("id="+this.id+";");
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
        }
        
        return nup;
    }
    
    
    public int deleteAssignacions()
    {
        int nup = 0;
        //Ha d'esborrar les associacions de l'activitat amb els alumnes
        String SQL0 = "SELECT id FROM actividadesalumno WHERE idActividades='"+this.id+"'";
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL0,st);
            while(rs!=null && rs.next())
            {
                int idaa = rs.getInt(1);
                String SQL1 = "DELETE FROM actividadesalumno WHERE id='"+idaa +"' LIMIT 1;";
                int success= getSgd().executeUpdate(SQL1);
                nup += success;
                if(success>0)
                {
                    Log log = getLogger();
                    log.setTabla("ActividadesAlumno");
                    log.setDatos("id=" + idaa + ";");
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
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nup;
    }
    
    /**
     * Normalment cada activitat hauria de tenir associat un unic idpeso
     * Sino hi ha una errada en la base de dades.
     * @return 
     */
    public int getCountPesos()
    {
        int nup = 0;
        //Ha d'esborrar el pes de l'activitat o tots els que hi pogues haver-hi assignats=========================================================
        String SQL0 = "SELECT count(id) as total FROM pesoactividades WHERE idActividades='" + this.id + "'";
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL0,st);
            if (rs != null && rs.next()) {
                nup = rs.getInt("total");
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nup;
    }
    
    private int deletePesos()
    {
        int nup = 0;
        //Ha d'esborrar el pes de l'activitat o tots els que hi pogues haver-hi assignats=========================================================
        String SQL0 = "SELECT id FROM pesoactividades WHERE idActividades='" + this.id + "'";
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs = getSgd().getResultSet(SQL0,st);
            while (rs != null && rs.next()) {
                int idpeso = rs.getInt(1);

                String SQL1 = "DELETE FROM pesoactividades WHERE id='" + idpeso + "' LIMIT 1;";
                int success = getSgd().executeUpdate(SQL1);
                nup += success;
                this.idPeso = 0;
                if (success > 0) {
                    Log log = getLogger();
                    log.setTabla("PesoActividades");
                    log.setDatos("id=" + idpeso + ";");
                    log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                    log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                    log.postLog();
                    log = null;
                }
            }
            if (rs != null) {
                rs.close();
                st.close();
            

}
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nup;
    }
    /**
     * Esborra totes les referencies a l'activitat
     * incloent l'activitat mateixa, les activitats d'alumne, els pesos i els conceptes
     * @return 
     */
    public int deleteAll()
    {
        int nup = this.delete();
        
        nup +=  this.deleteAssignacions();
        
        nup += this.deletePesos();
        
        String SQL1 = "DELETE FROM "+client.getPlusDbName()+"sgd_conceptosactividades where idActividad="+this.id;
        nup += client.getPlusDb().executeUpdate(SQL1);
        this.concepto = new BeanConceptos(); //El passa a blanc
        
        return nup;
    }
    
    @Override
    public String toString()
    {
        return "Actividad:: id=" + this.id+ "; "
                +"descripcion="+this.descripcion+ "; "
                + "evaluable=" + this.evaluable+ "; "
                + "fecha=" + this.fecha+ "; "
                + "idConceptosEvaluables=" + this.idConceptosEvaluables+ "; "
                + "idEvaluacionesDetalle=" + this.idEvaluacionesDetalle+ "; "
                + "idGrupAsig=" + this.idGrupAsig+ "; "
                + "idProfesores=" + this.getIdProfesores()+ "; "
                + "ordre=" + this.ordre+ "; "
                + "peso=" + this.peso+ "; "
                + "publicarWeb=" + this.publicarWEB+ "; "
                + "seguimiento=" + this.seguimiento;

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
