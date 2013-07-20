/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.alumnos;

import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.actividades.BeanActividadesAlumno;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.clients.sgd7.profesores.BeanProfesor;
import org.iesapp.util.StringUtils;


/**
 *
 * @author Josep
 */
public class AlumnosCollection implements IClientController{
    private final IClient client;
    
    public AlumnosCollection()
    {
        //Default
        this.client = null;
    }
    
    public AlumnosCollection(IClient client)
    {
        this.client = client;
    }
    
    /**
     * 
     * @param idProfesor
     * @param idClase
     * @return ArrayList<BeanAlumno>
     */
    public  ArrayList<BeanAlumno> getAlumnosProfeClase(final String idProfesor, final int idClase)
    {
       ArrayList<BeanAlumno> listalumnos = new ArrayList<BeanAlumno>();
      
       StringBuilder tmp = new StringBuilder();
       
        tmp.append(" SELECT DISTINCT alumn.expediente, alumn.id, alumn.nombre, ");
        tmp.append("    g.grupo, g.id AS grupid, a.descripcion, a.codigo, cd.idClases,   ");
        tmp.append("    p.codigo AS profesor, p.nombre  AS NombreProfe, ga.id as guaid  ");
        tmp.append("    FROM asignaturas a    ");
        tmp.append("    INNER JOIN clasesdetalle cd ON 1=1    ");
        tmp.append("    INNER JOIN horascentro hc ON 1=1    ");
        tmp.append("    INNER JOIN horarios h ON 1=1    ");
        tmp.append("    INNER JOIN grupasig ga ON 1=1    ");
        tmp.append("    INNER JOIN grupos g ON 1=1    ");
        tmp.append("    INNER JOIN asignaturasalumno aa ON 1=1    ");
        tmp.append("    INNER JOIN alumnos alumn ON alumn.id=aa.idAlumnos    ");
        tmp.append("    LEFT OUTER JOIN aulas au ON h.idAulas=au.id    ");
        tmp.append("    LEFT OUTER JOIN profesores p ON h.idProfesores=p.id    ");
        tmp.append("    WHERE p.codigo=").append(idProfesor).append(" AND cd.idClases=").append(idClase).append(" AND    ");
        tmp.append("     aa.idGrupAsig=ga.id    ");
        tmp.append("    AND  (aa.opcion<>'0' AND (cd.opcion='X' OR cd.opcion=aa.opcion))    ");
        tmp.append("    AND  h.idClases=cd.idClases    ");
        tmp.append("    AND cd.idGrupAsig=ga.id    ");
        tmp.append("    AND  h.idHorasCentro=hc.id    ");
        tmp.append("    AND ga.idGrupos=g.id    ");
        tmp.append("    AND  ga.idAsignaturas=a.id    ");
        tmp.append("    ORDER BY nombre   ");
       
        String SQL1 = tmp.toString();
        tmp = null;
         
        int ordre = 1;
        
        try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanAlumno bh = new BeanAlumno();
               bh.setExpediente(rs1.getInt("expediente"));
               bh.setId(rs1.getInt("id"));
               bh.setNombre(StringUtils.noNull(rs1.getString("nombre")));
               bh.setNomTutor(StringUtils.noNull(rs1.getString("NombreProfe")));          
               bh.setGrupo(StringUtils.noNull(rs1.getString("grupo")));
               bh.setIdGrupo(rs1.getInt("grupid"));
               bh.setIdGrupoAsig(rs1.getInt("guaid"));
               bh.setOrdre(ordre);
               ordre += 1;
               //falta incloure descripcion, codigo, idclases si fos necessari 
               
               listalumnos.add(bh);
            }
             if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listalumnos;
      }

    //This query fails if idProfesor is tutor and has no idClasetutoria associated for tutory
    
    public  ArrayList<BeanAlumnoTutoria> getAlumnosProfeTutoria(final MyDatabase mysql, final BeanProfesor selectedUser) {
       
       
        ArrayList<BeanAlumnoTutoria> listalumnos = new ArrayList<BeanAlumnoTutoria>();
       
        StringBuilder tmp = new StringBuilder();    
        
  
        tmp.append("SELECT alumn.expediente, alumn.id, alumn.nombre, g.grupo, g.id AS grupid,  p.codigo AS profesor, p.nombre ");
        tmp.append(" AS NombreProfe, ga.id AS guaid FROM grupos AS g INNER JOIN gruposalumno AS ga ON ga.grupoGestion=g.grupoGestion INNER JOIN ");
        tmp.append(" alumnos AS alumn ON alumn.id=ga.idAlumnos INNER JOIN profesores AS p ON p.id=g.idProfesores WHERE g.idProfesores='");
        tmp.append(selectedUser.getIdProfesor()).append("' ORDER BY alumn.nombre");
        
//        tmp.append(" SELECT DISTINCT alumn.expediente, alumn.id, alumn.nombre, ");
//        tmp.append("    g.grupo, g.id AS grupid, a.descripcion, a.codigo, cd.idClases,   ");
//        tmp.append("    p.codigo AS profesor, p.nombre  AS NombreProfe, ga.id as guaid  ");
//        tmp.append("    FROM asignaturas a    ");
//        tmp.append("    INNER JOIN clasesdetalle cd ON 1=1    ");
//        tmp.append("    INNER JOIN horascentro hc ON 1=1    ");
//        tmp.append("    INNER JOIN horarios h ON 1=1    ");
//        tmp.append("    INNER JOIN grupasig ga ON 1=1    ");
//        tmp.append( "    INNER JOIN grupos g ON 1=1    ");
//        tmp.append("    INNER JOIN asignaturasalumno aa ON 1=1    ");
//        tmp.append("    INNER JOIN alumnos alumn ON alumn.id=aa.idAlumnos    ");
//        tmp.append("    LEFT OUTER JOIN aulas au ON h.idAulas=au.id    ");
//        tmp.append("    LEFT OUTER JOIN profesores p ON h.idProfesores=p.id    ");
//        tmp.append("    WHERE p.codigo=").append(selectedUser.getIdProfesor()).append(" AND cd.idClases=");
//        tmp.append(selectedUser.getIdClaseTutoria()).append(" AND    ");
//        tmp.append("     aa.idGrupAsig=ga.id    ");
//        tmp.append("    AND  (aa.opcion<>'0' AND (cd.opcion='X' OR cd.opcion=aa.opcion))    ");
//        tmp.append("    AND  h.idClases=cd.idClases    ");
//        tmp.append("    AND cd.idGrupAsig=ga.id    ");
//        tmp.append("    AND  h.idHorasCentro=hc.id    ");
//        tmp.append("    AND ga.idGrupos=g.id    ");
//        tmp.append("    AND  ga.idAsignaturas=a.id    ");
//        tmp.append("    ORDER BY nombre   ");

        String SQL1 = tmp.toString();
        tmp = null;
         
        int ordre = 1;
        
        try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanAlumnoTutoria bh = new BeanAlumnoTutoria();
               int expd = rs1.getInt("expediente");
               bh.setExpediente(expd);
               bh.setId(rs1.getInt("id"));
               bh.setNombre(StringUtils.noNull(rs1.getString("nombre")));
               bh.setNomTutor(StringUtils.noNull(rs1.getString("NombreProfe")));
               bh.setGrupo(StringUtils.noNull(rs1.getString("grupo")));
               bh.setIdGrupo(rs1.getInt("grupid"));
              // bh.setIdGrupoAsig(rs1.getInt("guaid"));  //can't be retrieved from general case???
               bh.setOrdre(ordre);
               ordre += 1;
              
    
//This must be implemented within pdaweb project 
//               
//               TasquesPendents tp = new TasquesPendents(mysql, StringUtils.anyAcademic_primer_int());
//               tp.checkTasquesPendents(expd);
//               
//               if(tp.jobs.containsKey(expd))
//               {
//                      bh.msgPendents=tp.jobs.get(expd).detallTasks.toString();
//                      bh.accionsStatus = BeanAlumnoTutoria.RED_FLAG;
//                }
//                else
//                {
//                    //no te tasques pendents
//                    if(tp.oberts.contains(expd))
//                    {
//                        bh.msgPendents="Té actuacions sense tancar";   
//                        bh.accionsStatus = BeanAlumnoTutoria.ORANGE_FLAG;
//                    }
//                    else
//                    {
//                        bh.accionsStatus = BeanAlumnoTutoria.GREEN_FLAG;
//                    }
//                }
//               
//               

               listalumnos.add(bh);
            }
             if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listalumnos;
    }

    
    public  HashMap<Integer, BeanActividadesAlumno> getAlumnosIdGrupAsig(final int idGrupAsig)
    {
       HashMap<Integer, BeanActividadesAlumno> map = new HashMap<Integer, BeanActividadesAlumno>();
       StringBuilder tmp = new StringBuilder();
        tmp.append(" SELECT DISTINCT  alumn.expediente, alumn.id, alumn.nombre, ");
        tmp.append(" g.grupo, g.id AS grupid, a.descripcion, a.codigo, cd.idClases,   ");
        tmp.append("  p.codigo AS profesor, p.nombre  AS NombreProfe, ga.id as guaid  ");
        tmp.append("   FROM asignaturas a    ");
        tmp.append("    INNER JOIN clasesdetalle cd ON 1=1    ");
        tmp.append("    INNER JOIN horascentro hc ON 1=1    ");
        tmp.append("    INNER JOIN horarios h ON 1=1    ");
        tmp.append("    INNER JOIN grupasig ga ON 1=1    ");
        tmp.append("    INNER JOIN grupos g ON 1=1    ");
        tmp.append("    INNER JOIN asignaturasalumno aa ON 1=1    ");
        tmp.append("    INNER JOIN alumnos alumn ON alumn.id=aa.idAlumnos    ");
        tmp.append("    LEFT OUTER JOIN aulas au ON h.idAulas=au.id    ");
        tmp.append("    LEFT OUTER JOIN profesores p ON h.idProfesores=p.id    ");
        tmp.append("    WHERE aa.idGrupAsig=ga.id    ");
        tmp.append("    AND aa.idGrupAsig='").append(idGrupAsig).append("' ");
        tmp.append("    AND  (aa.opcion<>'0' AND (cd.opcion='X' OR cd.opcion=aa.opcion))    ");
        tmp.append("    AND  h.idClases=cd.idClases    ");
        tmp.append("    AND cd.idGrupAsig=ga.id    ");
        tmp.append("    AND  h.idHorasCentro=hc.id   ");
        tmp.append("    AND ga.idGrupos=g.id    ");
        tmp.append("    AND  ga.idAsignaturas=a.id    ");
        tmp.append("    ORDER BY nombre   ");

        //System.out.println(SQL1);
        String SQL1 = tmp.toString();
        tmp = null;
        
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanActividadesAlumno bh = new BeanActividadesAlumno();
               int idalumno = rs1.getInt("id");
               bh.setIdAlumnos(idalumno);
               bh.setNombre(rs1.getString("nombre"));             
               bh.setSelected(false);
               
               map.put(idalumno, bh);
            }
              if(rs1!=null) {
                  rs1.close();
                  st.close();
              }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
        return map;
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
