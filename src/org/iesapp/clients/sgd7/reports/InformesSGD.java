/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.sgd7.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class InformesSGD {
    
    private final IClient client;
    private Date iniciCurs;
    private final int any;
    private final String dbName;

    public InformesSGD(IClient client) //s'ha de passar la base de dades
    {
       this.client = client;
       this.any = client.getAnyAcademic();
       this.dbName = client.getCurrentDBPrefix()+any;
    }

    public InformesSGD(int any, IClient client) {
       this.client = client;
       this.any = any;
       this.dbName = client.getCurrentDBPrefix()+any;
    }
  
    public BeanSGDResumInc getResumIncidenciesByGrupAsig(int expd, java.util.Date ini, java.util.Date fin, int idGrupAsig)
    {
        StringBuilder SQL1 = new StringBuilder(" SELECT DISTINCT COUNT(a.id) AS total, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, ");
        SQL1.append(" CASE WHEN(fa.idGrupAsig=0) ");
        SQL1.append(" THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,");
        SQL1.append(" ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,");
        SQL1.append(" asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, ");
        SQL1.append(" tObs.descripcion AS observacio, fa.comentarios AS comentari, ");
        SQL1.append("p.nombre AS tutorProfesor FROM ").append(dbName).append(".GruposAlumno ga INNER JOIN ").append(dbName).append(".Grupos g ");
        SQL1.append("ON g.grupoGestion=ga.grupoGestion INNER JOIN ").append(dbName).append(".Alumnos a ON a.id=ga.idAlumnos ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".FaltasAlumnos fa ON fa.idAlumnos=a.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".HorasCentro hc ON fa.idHorasCentro=hc.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".Asignaturas asig ON gas.idAsignaturas=asig.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".Profesores p ON p.id=g.idProfesores WHERE 1=1 AND ");
        SQL1.append("((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR ");
        SQL1.append("(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) ");
        SQL1.append("AND ti.id IS NOT NULL");
        SQL1.append(" AND ( expediente =").append(expd).append(" AND gas.id=").append(idGrupAsig).append(" ) ");
        SQL1.append(" AND (fa.dia>='").append( new DataCtrl(ini).getDataSQL()).append( "' AND fa.dia<='").append( new DataCtrl(fin).getDataSQL() ).append("') ");
        SQL1.append(" GROUP BY simbolo, expediente ");
        SQL1.append(" ORDER BY fecha, hora");


      //System.out.println(SQL1);

      BeanSGDResumInc bean = new BeanSGDResumInc();
    
       try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 =  client.getSgd().getResultSet(SQL1.toString(),st);
            while (rs1 != null && rs1.next()) {


                  String simbolo = rs1.getString("simbolo");
                  int total = rs1.getInt("total");
                  String alumno = rs1.getString("alumno");               
                  //String expediente = rs1.getString("expediente");
                  String grupo = rs1.getString("grupo");
                  String tutor =rs1.getString("tutorProfesor");
                                 
                  bean.setAlumno(alumno);
                  bean.setGrupo(grupo);
                  bean.setTutor(tutor);
                  bean.setAlumno(alumno);
                  bean.setGrupo(grupo);
                  bean.setTutor(tutor);

                  if (simbolo.equals("FA")) 
                  {
                      bean.setFa(total);
                  }
                  else if(simbolo.equals("FJ"))
                  {
                      bean.setFj(total);
                  }
                  else if(simbolo.equals("AG"))
                  {
                      bean.setAg(total);
                  }
                  else if(simbolo.equals("AL"))
                  {
                      bean.setAl(total);
                  }
                  else if(simbolo.equals("ALH"))
                  {
                      bean.setAlh(total);
                  }
                  else if(simbolo.equals("RE"))
                  {
                      bean.setRe(total);
                  }
                  else if(simbolo.equals("RJ"))
                  {
                      bean.setRj(total);
                  }
                  else if(simbolo.equals("DI"))
                  {
                      bean.setDi(total);
                  }
                  else if(simbolo.equals("PA"))
                  {
                      bean.setPa(total);
                  }
                  else if(simbolo.equals("CP"))
                  {
                      bean.setCp(total);
                  }
                  else if(simbolo.equals("CN"))
                  {
                      bean.setCn(total);
                  }

            }
       
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bean;
    }


    
    public String getLastIncidenciesByGrupAsig(int expd, int num, int idGrupAsig, List<String> codigs)
    {
    String lastinc = "";
    String txtcodigs = "";
    for(int i=0; i<codigs.size(); i++)
    {
        txtcodigs += "'"+codigs.get(i) +"', ";
    }
    txtcodigs = StringUtils.BeforeLast(txtcodigs, ",") +" ";
    


        StringBuilder SQL1 = new StringBuilder(" SELECT DISTINCT a.id, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, ");
        SQL1.append(" CASE WHEN(fa.idGrupAsig=0) ");
        SQL1.append(" THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,");
        SQL1.append(" ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,");
        SQL1.append(" asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, ");
        SQL1.append(" tObs.descripcion AS observacio, fa.comentarios AS comentari, ");
        SQL1.append("p.nombre AS tutorProfesor FROM ").append(dbName).append(".GruposAlumno ga INNER JOIN ").append(dbName).append(".Grupos g ");
        SQL1.append("ON g.grupoGestion=ga.grupoGestion INNER JOIN ").append(dbName).append(".Alumnos a ON a.id=ga.idAlumnos ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".FaltasAlumnos fa ON fa.idAlumnos=a.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".HorasCentro hc ON fa.idHorasCentro=hc.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".Asignaturas asig ON gas.idAsignaturas=asig.id ");
        SQL1.append("LEFT OUTER JOIN ").append(dbName).append(".Profesores p ON p.id=g.idProfesores WHERE 1=1 AND ");
        SQL1.append("((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR ");
        SQL1.append("(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) ");
        SQL1.append("AND ti.id IS NOT NULL");
        SQL1.append(" AND ( expediente =").append(expd).append(" AND gas.id=").append(idGrupAsig).append(" ) ");
        SQL1.append(" AND ti.simbolo IN (").append(txtcodigs).append(") ");
        SQL1.append(" ORDER BY fecha DESC, hora");

       
      // System.out.println(SQL1);

      int i =0;
       try {
           Statement st = client.getSgd().createStatement();
            ResultSet rs1 =  client.getSgd().getResultSet(SQL1.toString(),st);
            while (rs1 != null && rs1.next()) {
                String desc = StringUtils.noNull( rs1.getString("observacio") );
                String comt = StringUtils.noNull( rs1.getString("comentari") );
               
                if((!desc.equals("") || !comt.equals("")) && !comt.contains("Acumulació de 5"))
                {
                   if(!comt.equals(""))
                   {
                       lastinc += comt+"; ";
                   }
                   else
                   {
                       lastinc += desc+"; ";
                   }
                }
                i +=1;
                if(i>num) break;
            }
       
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lastinc;
    }


        
    
    //retorna una llista de beans, directament per enchufar al jasper
    //inputs: listexps (llista amb n. d'expedients que s'han de generar,
    //        listinc  (llista amb els símbols de les incidencies que s'han de  mostrar)

    public List getListIncidencies(List<Integer> listexpds, List<Integer> listinc, java.util.Date ini, java.util.Date fin)
    {
        ArrayList<BeanSGDllistatInc> listbean = new ArrayList<BeanSGDllistatInc>();


        if(listexpds.isEmpty()) {
            return listbean;
        }

        //condicio sobre el n. d'expedients que s'han de mostrar

        String condIndex = "( expediente="+listexpds.get(0);
        for(int i=1; i<listexpds.size(); i++)
        {
                condIndex += " OR expediente="+listexpds.get(i);
        }
        condIndex += " ) ";


        //condicio sobre tipus d'incidencia
        String condInc=" (";
        String or="";
        for(int i=0; i<listinc.size(); i++)
        {
            condInc += or +" fa.idTipoIncidencias='"+listinc.get(i)+"' ";
            or = "OR";

        }
        condInc += " ) ";


            String SQL1= " SELECT DISTINCT a.id AS idAlu, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
                + " CASE WHEN(fa.idGrupAsig=0) "
                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
                + "p.nombre AS tutorProfesor FROM "+dbName+".GruposAlumno ga INNER JOIN "+dbName+".Grupos g "
                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN "+dbName+".Alumnos a ON a.id=ga.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".FaltasAlumnos fa ON fa.idAlumnos=a.id "
                + "LEFT OUTER JOIN "+dbName+".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
                + "LEFT OUTER JOIN "+dbName+".HorasCentro hc ON fa.idHorasCentro=hc.id "
                + "LEFT OUTER JOIN "+dbName+".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
                + "LEFT OUTER JOIN "+dbName+".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
                + "LEFT OUTER JOIN "+dbName+".Asignaturas asig ON gas.idAsignaturas=asig.id "
                + "LEFT OUTER JOIN "+dbName+".Profesores p ON p.id=g.idProfesores WHERE "
                + " ti.id IS NOT NULL";
            
      SQL1 += " AND ( "+ condIndex +" AND "+ condInc +" ) "
              + " AND (fa.dia>='"+new DataCtrl(ini).getDataSQL()+"' AND fa.dia<='"+new DataCtrl(fin).getDataSQL()+"') "
              + "ORDER BY grupo, alumno, fecha, hora";



       try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 =  client.getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {

                  BeanSGDllistatInc bean = new BeanSGDllistatInc();

                  String simbolo = rs1.getString("simbolo");

                  bean.setAlumno(rs1.getString("alumno"));
                  bean.setAsignatura(rs1.getString("asignatura"));
                  bean.setExpediente(rs1.getString("expediente"));
                  java.util.Date dia = rs1.getDate("fecha");
                  bean.setFecha(dia);
                  bean.setGrupo(rs1.getString("grupo"));
                  bean.setHora(rs1.getString("hora"));
                  bean.setIncidencia(rs1.getString("incidencia"));
                  bean.setObservacion(rs1.getString("observacio"));
                  bean.setComentario(rs1.getString("comentari"));
                  String tutor =rs1.getString("tutorProfesor");
                  bean.setTutor(tutor);
                  bean.setTutorProfesor(tutor);
                  listbean.add(bean);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listbean;
    }

    
    public List getResumIncidencies(List listexpds, java.util.Date ini, java.util.Date fin)
    {
        ArrayList<BeanSGDResumInc> listbean = new ArrayList<BeanSGDResumInc>();


        if(listexpds.isEmpty()) {
            return listbean;
        }

        //condicio sobre el n. d'expedients que s'han de mostrar

        String condIndex = "( expediente="+listexpds.get(0);
        for(int i=1; i<listexpds.size(); i++)
        {
                condIndex += " OR expediente="+listexpds.get(i);
        }
        condIndex += " ) ";

 


//AQUESTA SELECT JA ET DONA EL GRUP I EL TUTOR (EN SERGIO DIU QUE L'HA TRETA DEL ...)
// 25-9-11 : ALERTA: en cas de repetecio el COUNT(a.id) duplica         

    String SQL1= " SELECT DISTINCT COUNT(a.id) AS total, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
                + " CASE WHEN(fa.idGrupAsig=0) "
                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
//                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
//                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
//                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
//                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
                + "p.nombre AS tutorProfesor FROM "+dbName+".GruposAlumno ga INNER JOIN "+dbName+".Grupos g "
                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN "+dbName+".Alumnos a ON a.id=ga.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".FaltasAlumnos fa ON fa.idAlumnos=a.id "
                + "LEFT OUTER JOIN "+dbName+".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
                + "LEFT OUTER JOIN "+dbName+".HorasCentro hc ON fa.idHorasCentro=hc.id "
                + "LEFT OUTER JOIN "+dbName+".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
                + "LEFT OUTER JOIN "+dbName+".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
                + "LEFT OUTER JOIN "+dbName+".Asignaturas asig ON gas.idAsignaturas=asig.id "
                + "LEFT OUTER JOIN "+dbName+".Profesores p ON p.id=g.idProfesores WHERE 1=1 AND "
                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) "
                + "AND ti.id IS NOT NULL";

      SQL1 += " AND ( "+ condIndex +" ) "
              + " AND (fa.dia>='"+new DataCtrl(ini).getDataSQL()+"' AND fa.dia<='"+new DataCtrl(fin).getDataSQL()+"') "
              +" GROUP BY simbolo, expediente "
              +" ORDER BY grupo, alumno, fecha, hora";


      //System.out.println(SQL1);

      int nfila =0;
      boolean seguent = false;
      String oldexpediente = "";
      BeanSGDResumInc bean = new BeanSGDResumInc();


      try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 =  client.getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {

                 
// Amb la cridada antiga
//                  bean.setAlumno(rs1.getString("nombre"));
//                  bean.setAsignatura(rs1.getString("asignatura"));
//                  bean.setExpediente(rs1.getString("expediente"));
//                  java.util.Date dia = rs1.getDate("dia");
//                  bean.setFecha(dia);
//                  bean.setGrupo("");
//                  bean.setHora(rs1.getString("inicio")+" - "+rs1.getString("fin"));
//                  bean.setIncidencia(rs1.getString("incidencia"));
//                  bean.setObservacion(rs1.getString("observacion"));
//                  bean.setComentario(rs1.getString("comentarios"));
//                  bean.setTutor("");
//                  bean.setTutorProfesor("");


                  String simbolo = rs1.getString("simbolo");

                  int total = rs1.getInt("total");
                  String alumno = rs1.getString("alumno");
               
                  String expediente = rs1.getString("expediente");
                  String grupo = rs1.getString("grupo");
                 
                
                  String tutor =rs1.getString("tutorProfesor");
                 
                  if(nfila==0)
                  {
                         bean.setAlumno(alumno);
                         bean.setGrupo(grupo);
                         bean.setTutor(tutor);
                  }

                  if(nfila>=1)
                  {
                      if(!oldexpediente.equals(expediente))
                      {
                        listbean.add(bean);
                        bean = new BeanSGDResumInc();
                        bean.setAlumno(alumno);
                        bean.setGrupo(grupo);
                        bean.setTutor(tutor);
                      }
                  }

                  if(simbolo.equals("FA"))
                  {
                      bean.setFa(total);
                  }
                  else if(simbolo.equals("FJ"))
                  {
                      bean.setFj(total);
                  }
                  else if(simbolo.equals("AG"))
                  {
                      bean.setAg(total);
                  }
                  else if(simbolo.equals("AL"))
                  {
                      bean.setAl(total);
                  }
                  else if(simbolo.equals("ALH"))
                  {
                      bean.setAlh(total);
                  }
                  else if(simbolo.equals("RE"))
                  {
                      bean.setRe(total);
                  }
                  else if(simbolo.equals("RJ"))
                  {
                      bean.setRj(total);
                  }
                  else if(simbolo.equals("DI") || simbolo.equals("CD")) 
                  {
                      bean.setDi(total);
                  }
                  else if(simbolo.equals("PA"))
                  {
                      bean.setPa(total);
                  }
                  else if(simbolo.equals("CP"))
                  {
                      bean.setCp(total);
                  }
                  else if(simbolo.equals("CN"))
                  {
                      bean.setCn(total);
                  }

                  oldexpediente = expediente;
                  nfila +=1;
            }
            listbean.add(bean);
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listbean;
    }
    
    
    /////////// in the another place
    
    
    //retorna una llista de beans, directament per enchufar al jasper
    //inputs: listexps (llista amb n. d'expedients que s'han de generar,
    //        listinc  (llista amb els símbols de les incidencies que s'han de  mostrar)

    public List getListIncidencies(List listexpds, List listinc, java.util.Date ini, java.util.Date fin, int maxFaltes)
    {
        ArrayList<BeanSGDllistatInc> listbean = new ArrayList<BeanSGDllistatInc>();


        if(listexpds.isEmpty()) {
            return listbean;
        }

        //condicio sobre el n. d'expedients que s'han de mostrar

        String condIndex = "( expediente="+listexpds.get(0);
        for(int i=1; i<listexpds.size(); i++)
        {
                condIndex += " OR expediente="+listexpds.get(i);
        }
        condIndex += " ) ";


        //condicio sobre tipus d'incidencia
        String condInc=" (";
        String or="";
        for(int i=0; i<listinc.size(); i++)
        {
            String abrev = (String) listinc.get(i);
            condInc += or +" ti.simbolo='"+abrev+"' ";
            or = "OR";

        }
        condInc += " ) ";


// AQUESTA SELECT FUNCIONA PERO NO ET DONA EL GRUP I TUTOR
//        String SQL1 ="SELECT "+
//             "  alumnos.expediente "+
//             "   , alumnos.nombre "+
//             "    , faltasalumnos.dia "+
//             "    , tipoincidencias.descripcion as incidencia" +
//             "    , tipoobservaciones.descripcion as observacion "+
//             "    , faltasalumnos.comentarios "+
//             "    , asignaturas.descripcion as asignatura "+
//             "    , horascentro.inicio "+
//             "    , horascentro.fin "+
//             " FROM "+
//             "    "+m_sgdDB+".alumnos "+
//             "    INNER JOIN "+m_sgdDB+".faltasalumnos "+
//             "        ON (alumnos.id = faltasalumnos.idAlumnos) "+
//             "    INNER JOIN "+m_sgdDB+".tipoincidencias "+
//             "        ON (tipoincidencias.id = faltasalumnos.idTipoIncidencias) "+
//             "    LEFT JOIN "+m_sgdDB+".tipoobservaciones "+
//             "        ON (faltasalumnos.idTipoObservaciones = tipoobservaciones.id) "+
//             "    INNER JOIN "+m_sgdDB+".grupasig "+
//             "        ON (faltasalumnos.idGrupAsig = grupasig.id) "+
//             "    INNER JOIN "+m_sgdDB+".horascentro "+
//             "        ON (faltasalumnos.idHorasCentro = horascentro.id) "+
//             "    INNER JOIN "+m_sgdDB+".asignaturas "+
//             "        ON (grupasig.idAsignaturas = asignaturas.id) "+
//             " WHERE ( "+ condIndex +" AND "+ condInc +" ) ORDER BY alumnos.nombre, dia, inicio";


//AQUESTA SELECT JA ET DONA EL GRUP I EL TUTOR (EN SERGIO DIU QUE L'HA TRETA DEL ...)
// 29-1-2012 : Té un problema quan es canvia un alumne de grup, no apareixen totes les incidencies.
// El mateix succeix si es fa des del SGD    

//    String SQL1= " SELECT DISTINCT a.id AS idAlu, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
//                + " CASE WHEN(fa.idGrupAsig=0) "
//                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
//                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
//                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
//                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
////                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
////                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
////                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
////                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
//                + "p.nombre AS tutorProfesor FROM GruposAlumno ga INNER JOIN Grupos g "
//                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN Alumnos a ON a.id=ga.idAlumnos "
//                + "LEFT OUTER JOIN FaltasAlumnos fa ON fa.idAlumnos=a.id "
//                + "LEFT OUTER JOIN TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
//                + "LEFT OUTER JOIN HorasCentro hc ON fa.idHorasCentro=hc.id "
//                + "LEFT OUTER JOIN TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
//                + "LEFT OUTER JOIN AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
//                + "LEFT OUTER JOIN GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
//                + "LEFT OUTER JOIN Asignaturas asig ON gas.idAsignaturas=asig.id "
//                + "LEFT OUTER JOIN Profesores p ON p.id=g.idProfesores WHERE 1=1 AND "
//                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
//                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) "
//                + "AND ti.id IS NOT NULL";

            String SQL1= " SELECT DISTINCT a.id AS idAlu, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
                + " CASE WHEN(fa.idGrupAsig=0) "
                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
//                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
//                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
//                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
//                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
                + "p.nombre AS tutorProfesor FROM "+dbName+".GruposAlumno ga INNER JOIN "+dbName+".Grupos g "
                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN "+dbName+".Alumnos a ON a.id=ga.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".FaltasAlumnos fa ON fa.idAlumnos=a.id "
                + "LEFT OUTER JOIN "+dbName+".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
                + "LEFT OUTER JOIN "+dbName+".HorasCentro hc ON fa.idHorasCentro=hc.id "
                + "LEFT OUTER JOIN "+dbName+".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
                + "LEFT OUTER JOIN "+dbName+".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
                + "LEFT OUTER JOIN "+dbName+".Asignaturas asig ON gas.idAsignaturas=asig.id "
                + "LEFT OUTER JOIN "+dbName+".Profesores p ON p.id=g.idProfesores WHERE "
//                + " 1=1 AND "
//                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
//                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) AND "
                + " ti.id IS NOT NULL";
            
      SQL1 += " AND ( "+ condIndex +" AND "+ condInc +" ) "
              + " AND (fa.dia>='"+new DataCtrl(ini).getDataSQL()+"' AND fa.dia<='"+new DataCtrl(fin).getDataSQL()+"') "
              + "ORDER BY grupo, alumno, fecha, hora";


      //System.out.println(SQL1);

      int nFA =0;

       try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 =  client.getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {

                  BeanSGDllistatInc bean = new BeanSGDllistatInc();
// Amb la cridada antiga
//                  bean.setAlumno(rs1.getString("nombre"));
//                  bean.setAsignatura(rs1.getString("asignatura"));
//                  bean.setExpediente(rs1.getString("expediente"));
//                  java.util.Date dia = rs1.getDate("dia");
//                  bean.setFecha(dia);
//                  bean.setGrupo("");
//                  bean.setHora(rs1.getString("inicio")+" - "+rs1.getString("fin"));
//                  bean.setIncidencia(rs1.getString("incidencia"));
//                  bean.setObservacion(rs1.getString("observacion"));
//                  bean.setComentario(rs1.getString("comentarios"));
//                  bean.setTutor("");
//                  bean.setTutorProfesor("");

                  String simbolo = rs1.getString("simbolo");
                  if(simbolo.equals("F") || simbolo.equals("FA")) {
                    nFA +=1;
                }

                  if(maxFaltes>0 && nFA>maxFaltes) {
                    break;
                }

                  bean.setAlumno(rs1.getString("alumno"));
                  bean.setAsignatura(rs1.getString("asignatura"));
                  bean.setExpediente(rs1.getString("expediente"));
                  java.util.Date dia = rs1.getDate("fecha");
                  bean.setFecha(dia);
                  bean.setGrupo(rs1.getString("grupo"));
                  bean.setHora(rs1.getString("hora"));
                  bean.setIncidencia(rs1.getString("incidencia"));
                  bean.setObservacion(rs1.getString("observacio"));
                  bean.setComentario(rs1.getString("comentari"));
                  String tutor =rs1.getString("tutorProfesor");
                  bean.setTutor(tutor);
                  bean.setTutorProfesor(tutor);
                  listbean.add(bean);
            }
            if(rs1!=null) {
               rs1.close();
               st.close();
           }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listbean;
    }


//  Igual que l'anterior, pero aquest metode no te limit de dates, nomes
//  limitacio en el nombre d'incidencies
//  si la llista listinc contains FA, limita el nombre de FALTES
//  si la llista listinc contains AG, limita el nombre d'amon greus
//  si limit = 0, no limita res, treu totes les incidències existents

    public List getListIncidencies(List listexpds, List listinc, int limit)
    {
        ArrayList<BeanSGDllistatInc> listbean = new ArrayList<BeanSGDllistatInc>();


        if(listexpds.isEmpty()) return listbean;

        //condicio sobre el n. d'expedients que s'han de mostrar

        String condIndex = "( expediente="+listexpds.get(0);
        for(int i=1; i<listexpds.size(); i++)
        {
                condIndex += " OR expediente="+listexpds.get(i);
        }
        condIndex += " ) ";


        //condicio sobre tipus d'incidencia
        String condInc=" (";
        String or="";
        for(int i=0; i<listinc.size(); i++)
        {
            String abrev = (String) listinc.get(i);
            condInc += or +" ti.simbolo='"+abrev+"' ";
            or = "OR";

        }
        condInc += " ) ";


//AQUESTA SELECT JA ET DONA EL GRUP I EL TUTOR (EN SERGIO DIU QUE L'HA TRETA DEL ...)

// 29-1-2012 : Té un problema quan es canvia un alumne de grup, no apareixen totes les incidencies.
// El mateix succeix si es fa des del SGD        
        
//    String SQL1= " SELECT DISTINCT a.id AS idAlu, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
//                + " CASE WHEN(fa.idGrupAsig=0) "
//                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
//                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
//                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
//                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
////                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
////                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
////                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
////                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
//                + "p.nombre AS tutorProfesor FROM GruposAlumno ga INNER JOIN Grupos g "
//                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN Alumnos a ON a.id=ga.idAlumnos "
//                + "LEFT OUTER JOIN FaltasAlumnos fa ON fa.idAlumnos=a.id "
//                + "LEFT OUTER JOIN TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
//                + "LEFT OUTER JOIN HorasCentro hc ON fa.idHorasCentro=hc.id "
//                + "LEFT OUTER JOIN TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
//                + "LEFT OUTER JOIN AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
//                + "LEFT OUTER JOIN GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
//                + "LEFT OUTER JOIN Asignaturas asig ON gas.idAsignaturas=asig.id "
//                + "LEFT OUTER JOIN Profesores p ON p.id=g.idProfesores WHERE 1=1 AND "
//                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
//                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) "
//                + "AND ti.id IS NOT NULL";

            String SQL1= " SELECT DISTINCT a.id AS idAlu, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
                + " CASE WHEN(fa.idGrupAsig=0) "
                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
//                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
//                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
//                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
//                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
                + "p.nombre AS tutorProfesor FROM "+dbName+".GruposAlumno ga INNER JOIN "+dbName+".Grupos g "
                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN "+dbName+".Alumnos a ON a.id=ga.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".FaltasAlumnos fa ON fa.idAlumnos=a.id "
                + "LEFT OUTER JOIN "+dbName+".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
                + "LEFT OUTER JOIN "+dbName+".HorasCentro hc ON fa.idHorasCentro=hc.id "
                + "LEFT OUTER JOIN "+dbName+".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
                + "LEFT OUTER JOIN "+dbName+".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
                + "LEFT OUTER JOIN "+dbName+".Asignaturas asig ON gas.idAsignaturas=asig.id "
                + "LEFT OUTER JOIN "+dbName+".Profesores p ON p.id=g.idProfesores WHERE "
//                  + "1=1 AND "
//                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
//                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) AND "
                + " ti.id IS NOT NULL";
        
      SQL1 += " AND ( "+ condIndex +" AND "+ condInc +" ) "
              + "ORDER BY grupo, alumno, fecha, hora";


    //  System.out.println(SQL1);

      int nFA =0;
      int nAG =0;

      
       try {
           Statement st = client.getSgd().createStatement();
           ResultSet rs1 =  client.getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {

                  BeanSGDllistatInc bean = new BeanSGDllistatInc();
// Amb la cridada antiga
//                  bean.setAlumno(rs1.getString("nombre"));
//                  bean.setAsignatura(rs1.getString("asignatura"));
//                  bean.setExpediente(rs1.getString("expediente"));
//                  java.util.Date dia = rs1.getDate("dia");
//                  bean.setFecha(dia);
//                  bean.setGrupo("");
//                  bean.setHora(rs1.getString("inicio")+" - "+rs1.getString("fin"));
//                  bean.setIncidencia(rs1.getString("incidencia"));
//                  bean.setObservacion(rs1.getString("observacion"));
//                  bean.setComentario(rs1.getString("comentarios"));
//                  bean.setTutor("");
//                  bean.setTutorProfesor("");

                  String simbolo = rs1.getString("simbolo");
                  if(simbolo.equals("F") || simbolo.equals("FA")) {
                    nFA +=1;
                }

                  if(simbolo.equals("AG")) {
                    nAG +=1;
                }
                  
                  if(limit>0 && listinc.contains("FA") && nFA>limit) {
                    break;
                }
                  if(limit>0 && listinc.contains("AG") && nAG>limit) {
                    break;
                }

                  bean.setAlumno(rs1.getString("alumno"));
                  bean.setAsignatura(rs1.getString("asignatura"));
                  bean.setExpediente(rs1.getString("expediente"));
                  java.util.Date dia = rs1.getDate("fecha");
                  bean.setFecha(dia);
                  bean.setGrupo(rs1.getString("grupo"));
                  bean.setHora(rs1.getString("hora"));
                  bean.setIncidencia(rs1.getString("incidencia"));
                  bean.setObservacion(rs1.getString("observacio"));
                  bean.setComentario(rs1.getString("comentari"));
                  String tutor =rs1.getString("tutorProfesor");
                  bean.setTutor(tutor);
                  bean.setTutorProfesor(tutor);
                  listbean.add(bean);
            }
            if(rs1!=null) {
               rs1.close();
               st.close();
           }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listbean;
    }

    
    public BeanSGDResumInc getResumIncidenciesByAsig(int expd, java.util.Date ini, java.util.Date fin, int asigId)
    {
     
     
    String SQL1= " SELECT DISTINCT COUNT(a.id) AS total, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
                + " CASE WHEN(fa.idGrupAsig=0) "
                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
//                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
//                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
//                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
//                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
                + "p.nombre AS tutorProfesor FROM "+dbName+".GruposAlumno ga INNER JOIN "+dbName+".Grupos g "
                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN "+dbName+".Alumnos a ON a.id=ga.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".FaltasAlumnos fa ON fa.idAlumnos=a.id "
                + "LEFT OUTER JOIN "+dbName+".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
                + "LEFT OUTER JOIN "+dbName+".HorasCentro hc ON fa.idHorasCentro=hc.id "
                + "LEFT OUTER JOIN "+dbName+".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
                + "LEFT OUTER JOIN "+dbName+".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
                + "LEFT OUTER JOIN "+dbName+".Asignaturas asig ON gas.idAsignaturas=asig.id "
                + "LEFT OUTER JOIN "+dbName+".Profesores p ON p.id=g.idProfesores WHERE 1=1 AND "
                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) "
                + "AND ti.id IS NOT NULL";

      SQL1 += " AND ( expediente ="+expd+" AND asig.id="+asigId+" ) "
              + " AND (fa.dia>='"+new DataCtrl(ini).getDataSQL()+"' AND fa.dia<='"+new DataCtrl(fin).getDataSQL()+"') "
              +" GROUP BY simbolo, expediente "
              +" ORDER BY fecha, hora";


      //System.out.println(SQL1);

      BeanSGDResumInc bean = new BeanSGDResumInc();
    
       try {
           Statement st = client.getSgd().createStatement();
           ResultSet rs1 =  client.getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {


                  String simbolo = rs1.getString("simbolo");
                  int total = rs1.getInt("total");
                  String alumno = rs1.getString("alumno");               
                  //String expediente = rs1.getString("expediente");
                  String grupo = rs1.getString("grupo");
                  String tutor =rs1.getString("tutorProfesor");
                                 
                  bean.setAlumno(alumno);
                  bean.setGrupo(grupo);
                  bean.setTutor(tutor);
                  bean.setAlumno(alumno);
                  bean.setGrupo(grupo);
                  bean.setTutor(tutor);

                  if (simbolo.equals("FA")) 
                  {
                      bean.setFa(total);
                  }
                  else if(simbolo.equals("FJ"))
                  {
                      bean.setFj(total);
                  }
                  else if(simbolo.equals("AG"))
                  {
                      bean.setAg(total);
                  }
                  else if(simbolo.equals("AL"))
                  {
                      bean.setAl(total);
                  }
                  else if(simbolo.equals("ALH"))
                  {
                      bean.setAlh(total);
                  }
                  else if(simbolo.equals("RE"))
                  {
                      bean.setRe(total);
                  }
                  else if(simbolo.equals("RJ"))
                  {
                      bean.setRj(total);
                  }
                  else if(simbolo.equals("DI"))
                  {
                      bean.setDi(total);
                  }
                  else if(simbolo.equals("PA"))
                  {
                      bean.setPa(total);
                  }
                  else if(simbolo.equals("CP"))
                  {
                      bean.setCp(total);
                  }
                  else if(simbolo.equals("CN"))
                  {
                      bean.setCn(total);
                  }

            }
       
            if(rs1!=null) {
               rs1.close();
               st.close();
           }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bean;
    }


    
    public String getLastIncidenciesByAsig(int expd, int num, int asigId, List<String> codigs)
    {
    String lastinc = "";
    String txtcodigs = "";
    for(int i=0; i<codigs.size(); i++)
    {
        txtcodigs += "'"+codigs.get(i) +"', ";
    }
    txtcodigs = StringUtils.BeforeLast(txtcodigs, ",") +" ";
    
            
    
    String SQL1= " SELECT DISTINCT a.id, g.id AS idGrupo, ti.simbolo AS simbolo, fa.dia AS fecha, "
                + " CASE WHEN(fa.idGrupAsig=0) "
                + " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS hora,"
                + " ti.descripcion AS incidencia, a.nombre AS alumno, a.expediente AS expediente,"
                + " asig.descripcion AS asignatura,asig.id AS idAsig, g.grupo AS grupo, "
                + " tObs.descripcion AS observacio, fa.comentarios AS comentari, "
//                + "CONCAT(CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN ''"
//                + " ELSE tObs.descripcion END, CASE WHEN(fa.comentarios='' OR ISNULL(fa.comentarios))"
//                + " THEN '' ELSE CASE WHEN(tObs.descripcion='' OR ISNULL(tObs.descripcion)) THEN "
//                + "fa.comentarios ELSE CONCAT(' - ',fa.comentarios) END END ) AS obscomentario, "
                + "p.nombre AS tutorProfesor FROM "+dbName+".GruposAlumno ga INNER JOIN "+dbName+".Grupos g "
                + "ON g.grupoGestion=ga.grupoGestion INNER JOIN "+dbName+".Alumnos a ON a.id=ga.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".FaltasAlumnos fa ON fa.idAlumnos=a.id "
                + "LEFT OUTER JOIN "+dbName+".TipoIncidencias ti ON ti.id=fa.idTipoIncidencias "
                + "LEFT OUTER JOIN "+dbName+".HorasCentro hc ON fa.idHorasCentro=hc.id "
                + "LEFT OUTER JOIN "+dbName+".TipoObservaciones tObs ON tObs.id=fa.idTipoObservaciones "
                + "LEFT OUTER JOIN "+dbName+".AsignaturasAlumno aa ON aa.idGrupAsig=fa.idGrupAsig AND aa.idAlumnos=fa.idAlumnos "
                + "LEFT OUTER JOIN "+dbName+".GrupAsig gas ON fa.idGrupAsig=gas.id AND gas.idGrupos=g.id "
                + "LEFT OUTER JOIN "+dbName+".Asignaturas asig ON gas.idAsignaturas=asig.id "
                + "LEFT OUTER JOIN "+dbName+".Profesores p ON p.id=g.idProfesores WHERE 1=1 AND "
                + "((asig.asignatura IS NOT NULL AND fa.dia IS NOT NULL) OR "
                + "(asig.asignatura IS NULL AND fa.dia IS NOT NULL AND fa.idGrupAsig=0)) "
                + "AND ti.id IS NOT NULL";

       SQL1 +=   " AND ( expediente ="+expd+" AND asig.id="+asigId+" ) "
               + " AND ti.simbolo IN ("+txtcodigs+") "
               + " ORDER BY fecha DESC, hora";
       
       
      // System.out.println(SQL1);

      int i =0;
       try {
            Statement st = client.getSgd().createStatement();
           ResultSet rs1 =  client.getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                String desc = StringUtils.noNull( rs1.getString("observacio") );
                String comt = StringUtils.noNull( rs1.getString("comentari") );
               
                if((!desc.equals("") || !comt.equals("")) && !comt.contains("Acumulació de 5"))
                {
                   if(!comt.equals(""))
                   {
                       lastinc += comt+"; ";
                   }
                   else
                   {
                       lastinc += desc+"; ";
                   }
                }
                i +=1;
                if(i>num) {
                    break;
                }
            }
       
            if(rs1!=null) {
               rs1.close();
               st.close();
           }
        } catch (SQLException ex) {
            Logger.getLogger(InformesSGD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lastinc;
    }

}
