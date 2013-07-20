/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.alumnos;

import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public final class Alumnos extends BeanAlumno implements IClientController{
    private final IClient client;

    public Alumnos(int id)
    {
        client = null;
        this.id = id;
        load();
    }
    
    public Alumnos(BeanAlumno bean)
    {
       client = null;
       setBean(bean);
    }
    
    
    public Alumnos(int id, IClient client)
    {
        this.client = client;
        this.id = id;
        load();
    }
    
    public Alumnos(BeanAlumno bean, IClient client)
    {
       this.client = client;
       setBean(bean);
    }
    
    private void setBean(BeanAlumno bean) {
        this.id = bean.id;
        this.expediente = bean.expediente;
        this.grupo = bean.grupo;
        this.nombre = bean.nombre;
        this.ordre = bean.ordre;
        this.idGrupoAsig = bean.idGrupoAsig;
        this.idGrupAsigTutoria = bean.idGrupAsigTutoria;
        this.idTutor = bean.idTutor;
        this.idHoraTutoria = bean.idHoraTutoria;
        this.idDiaTutoria = bean.idDiaTutoria;
        this.nomTutor = bean.nomTutor;
        this.idGrupo = bean.idGrupo;
        this.incidencies = bean.incidencies;
    }
    
    public BeanAlumno getBean()
    {
        BeanAlumno bean = new BeanAlumno();
       
        bean.id = this.id;
        bean.expediente = this.expediente;
        bean.grupo = this.grupo;
        bean.nombre = this.nombre;
        bean.ordre = this.ordre;
        bean.idGrupoAsig = this.idGrupoAsig;
        bean.idGrupAsigTutoria = this.idGrupAsigTutoria;
        bean.idTutor = this.idTutor;
        bean.idHoraTutoria = this.idHoraTutoria;
        bean.idDiaTutoria = this.idDiaTutoria;
        bean.nomTutor = this.nomTutor;
        bean.idGrupo = this.idGrupo;
        bean.incidencies = this.incidencies;
        
        return bean;
    }
    
    public void load()
    {
              
        String SQL1 = "SELECT al.id, al.expediente, al.nombre, "
                + " IF(g.id IS NULL, -1, g.id) AS idGrupo, "
                + " IF(g.grupo IS NULL, '', g.grupo) AS grupo "
                + " FROM alumnos AS al LEFT JOIN gruposalumno AS "
                + " ga ON ga.idAlumnos=al.id LEFT JOIN grupos AS "
                + " g ON g.grupoGestion=ga.grupoGestion "
                + " WHERE al.id="+this.id;
        
        
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                this.setId(id);
                this.setExpediente(rs1.getInt("expediente"));
                this.setGrupo(rs1.getString("grupo"));
                this.setNombre(rs1.getString("nombre"));
                this.setIdGrupo(rs1.getInt("idGrupo"));
                
                //queda definir la tutoria que s'obte a traves de getTutoriaInfo
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void getTutoriaInfo()
    {
     
        String SQL1 = "SELECT DISTINCT aa.idAlumnos, alumn.expediente, alumn.nombre, aa.idGrupAsig, "
                + " g.grupo, a.asignatura, p.codigo AS profesor,p.nombre AS nombreProfe  "
                + " , CONCAT(CONCAT(hc.inicio, '-'), hc.fin) AS hora, hc.id as codighora, h.idDias AS dia,  "
                + " hc.inicio, au.codigo AS aula  "
                + " FROM asignaturas a  "
                + " INNER JOIN clasesdetalle cd ON 1=1  "
                + " INNER JOIN horascentro hc ON 1=1  "
                + " INNER JOIN horarios h ON 1=1  "
                + " INNER JOIN grupasig ga ON 1=1  "
                + " INNER JOIN grupos g ON 1=1  "
                + " INNER JOIN asignaturasalumno aa ON 1=1  "
                + " LEFT OUTER JOIN alumnos alumn ON alumn.id=aa.idAlumnos  "
                + " LEFT OUTER JOIN aulas au ON h.idAulas=au.id  "
                + " LEFT OUTER JOIN profesores p ON h.idProfesores=p.id  "
                + "  WHERE a.asignatura LIKE 'Tut%'  "
                + " AND  aa.idGrupAsig=ga.id  "
                + " AND  (aa.opcion<>'0' AND (cd.opcion='X' OR cd.opcion=aa.opcion))  "
                + " AND  h.idClases=cd.idClases  "
                + " AND cd.idGrupAsig=ga.id  "
                + " AND  h.idHorasCentro=hc.id  "
                + " AND ga.idGrupos=g.id  "
                + " AND  ga.idAsignaturas=a.id "
                + " AND aa.idAlumnos=" + this.id;
        
        
        
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
              this.setIdTutor(rs1.getInt("profesor"));
              this.setIdGrupAsigTutoria(rs1.getInt("idGrupAsig"));
              this.setIdDiaTutoria(rs1.getInt("dia"));
              this.setIdHoraTutoria(rs1.getInt("codighora"));
              this.setNomTutor(rs1.getString("nombreProfe"));
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
    
    public static int getID(int expd, MyDatabase mdb) {
         int id=-1;
         
         String SQL1 = "SELECT id FROM alumnos WHERE expediente='"+expd+"'";
         try {
             Statement st = mdb.createStatement();
             ResultSet rs1 = mdb.getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                id= rs1.getInt("id");                          
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
     public static int getExpediente(int id, IClient client) {
         int expd=-1;
         
         String SQL1 = "SELECT expediente FROM alumnos WHERE id='"+id+"'";
          try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 = client.getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                expd= rs1.getInt("expediente");                          
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expd;
    }

    @Override
   public String toString()
   {
       return "Alumno: id="+this.id+"; expediente="+this.expediente+"; nombre="+
                this.nombre+"; grupo="+this.grupo+"; idGrupo="+this.idGrupo+"; tutor="+this.nomTutor+"; idTutor="+
                this.idTutor+"; idGrupAsigTutoria="+this.idGrupAsigTutoria+"; idDiaTutoria="+this.idDiaTutoria+
                "; idHoraTutoria="+this.idHoraTutoria;
   }
  

    public void print() {
        System.out.println(toString());
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
