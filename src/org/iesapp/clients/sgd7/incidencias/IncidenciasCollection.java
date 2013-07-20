/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.incidencias;


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
public class IncidenciasCollection implements IClientController {
    private final IClient client;

    public IncidenciasCollection()
    {
       this.client = null;
    }
    
    public IncidenciasCollection(IClient client)
    {
        this.client = client;
    }
    
    private  String normalizeSimbol(String simbol) {
        if(simbol == null) return "";
        if(simbol.equalsIgnoreCase("F") || simbol.equalsIgnoreCase("FA"))
            return "FA";
        else if(simbol.equalsIgnoreCase("R") || simbol.equalsIgnoreCase("RE"))
            return "RE";
        else
            return simbol;
    }
     
    
    /**
     * 
     * @param simbolos
     * @param commentRequired
     * @param threshold
     * @return List of ids of those students who have more than threshold number
     * of incidencias of type specified in simbolos.
     */
    public  List<Integer> getIdIncidenciasThreshold(String[] simbolos,
            boolean commentRequired, int threshold) 
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
         
        String condsimbolos = "";
        if (simbolos.length != 0) {
            String txt = "(";
            for (int i = 0; i < simbolos.length; i++) {
                txt += "'" + simbolos[i] + "',";
            }
            txt = StringUtils.BeforeLast(txt, ",") + ") ";
            condsimbolos = "AND ti.simbolo IN " + txt;
        }

        String condCommentRequired = "";
        if (commentRequired) {
            condCommentRequired = " AND NOT ((fal.idTipoObservaciones IS NULL OR fal.idTipoObservaciones=0) AND fal.Comentarios='') ";
        }

        String SQL1 = " SELECT "
                + " alm.id, "
                + " COUNT(ti.simbolo) AS total  "
                + " FROM "
                + " faltasalumnos AS fal  "
                + " INNER JOIN "
                + " alumnos AS alm  "
                + " ON alm.id = fal.idAlumnos  "
                + " INNER JOIN "
                + " gruposalumno AS gal  "
                + " ON gal.idAlumnos = alm.id  "
                + " INNER JOIN "
                + " grupos AS ga  "
                + " ON ga.grupoGestion = gal.grupoGestion  "
                + " INNER JOIN "
                + " tipoincidencias ti  "
                + " ON ti.id = fal.idTipoIncidencias  "
                + condsimbolos
                + condCommentRequired
                + " GROUP BY expediente "
                + " ORDER BY grupo, nombre";

       
        

        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                int total = rs1.getInt("total");
                int idAlumnos = rs1.getInt("id");
                if (total >= threshold) {
                    list.add(idAlumnos);
                }
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    /**
     * 
     * @param idalumno
     * @param simbolos
     * @param commentRequired
     * @param threshold
     * @param order ASC OR DESC
     * @return 
     */
    public  ArrayList<BeanIncidencias> getIncidenciasOf(int idAlumno, 
            String[] simbolos, int tipoObservaciones, boolean commentRequired, int limit, String order) {
        
        ArrayList<BeanIncidencias> list = new ArrayList<BeanIncidencias>();

        String condsimbolos = "";
        if (simbolos.length != 0) {
            String txt = "(";
            for (int i = 0; i < simbolos.length; i++) {
                txt += "'" + simbolos[i] + "',";
            }
            txt = StringUtils.BeforeLast(txt, ",") + ") ";
            condsimbolos = "AND ti.simbolo IN " + txt;
        }

         String condObservaciones  ="";
        if(tipoObservaciones>0)
            condObservaciones = " AND idTipoObservaciones="+tipoObservaciones+" ";
 
        String condCommentRequired = "";
        if (commentRequired) {
            condCommentRequired = " AND NOT ((fal.idTipoObservaciones IS NULL OR fal.idTipoObservaciones=0) AND fal.Comentarios='') ";
        }
  
        String SQL1 = "SELECT fal.*, ti.simbolo, ti.descripcion, asig.asignatura,"
                 + " CASE WHEN(fal.idGrupAsig=0) "
                + " THEN fal.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS horaCentro, tob.descripcion as observaciones "
                + " FROM faltasalumnos AS fal INNER JOIN "
                + " tipoincidencias AS ti ON ti.id=fal.idTipoIncidencias "
                + " LEFT JOIN tipoobservaciones as tob ON tob.id = fal.idTipoObservaciones "
                + " LEFT OUTER JOIN grupasig gas ON fal.idGrupAsig=gas.id "
                + " LEFT OUTER JOIN asignaturas asig ON gas.idAsignaturas=asig.id "
                + " LEFT OUTER JOIN horascentro hc ON fal.idHorasCentro=hc.id "
                + " WHERE idAlumnos=" + idAlumno + " "
                + condsimbolos
                + condObservaciones
                + condCommentRequired
                + " ORDER BY dia " + order + ", "
                + " hc.inicio " + order;
           
        
        int num =0;
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {

                BeanIncidencias bean = new BeanIncidencias();
                
                bean.setId(rs1.getInt("id"));
                bean.setIdAlumnos(rs1.getInt("idAlumnos"));
                bean.setIdGrupAsig(rs1.getInt("idGrupAsig"));
                bean.setIdHorasCentro(rs1.getInt("idHorasCentro"));
                bean.setHoraCentro(rs1.getString("horaCentro"));
                bean.setIdProfesores(rs1.getString("idProfesores"));
                bean.setIdTipoIncidencias(rs1.getInt("idTipoIncidencias"));
                bean.setIdTipoObservaciones(rs1.getInt("idTipoObservaciones"));        
                bean.setComentarios(rs1.getString("comentarios"));
                bean.setDia(rs1.getDate("dia"));
                bean.setFechaModificado(rs1.getTimestamp("fechaModificado"));
                bean.setHora(rs1.getString("hora"));
                bean.setAsignatura(rs1.getString("asignatura"));
                bean.setDescripcion(rs1.getString("descripcion"));
                bean.setSimbolo(normalizeSimbol(rs1.getString("simbolo")));
                bean.setObservaciones(rs1.getString("observaciones"));
                
                list.add(bean);
                
                num += 1;
                if(limit>0 && num>limit) break;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
         
      
         
         return list;
    }
    

    /**
     * Aquesta permet veure si el professor va passar llista
     * @param idAlumno
     * @param simbolos
     * @param dia
     * @return 
     */
      public  ArrayList<BeanIncidenciasDia> getIncidenciasInDay(int idAlumno, String[] simbolos, java.util.Date dia) {
        
        ArrayList<BeanIncidenciasDia> list = new ArrayList<BeanIncidenciasDia>();

        String condsimbolos = "";
        if (simbolos.length != 0) {
            String txt = "(";
            for (int i = 0; i < simbolos.length; i++) {
                txt += "'" + simbolos[i] + "',";
            }
            txt = StringUtils.BeforeLast(txt, ",") + ") ";
            condsimbolos = "AND ti.simbolo IN " + txt;
        }
        DataCtrl dc = new DataCtrl(dia);
        String sqlDate = dc.getDataSQL();
         
        //10-10-2012 ADDED DISTINCT
        StringBuilder SQL1 =  new StringBuilder("SELECT DISTINCT a.asignatura, CASE WHEN(fa.idGrupAsig=0) ");
        SQL1.append( " THEN fa.hora ELSE CONCAT(CONCAT(hc.inicio, '-'), hc.fin) END AS horaCentro, ");
        SQL1.append(" p.nombre AS nombreProfesor, IF(ca.id IS NULL,'N','S') AS pasoLista, ");
        SQL1.append(" IF(fa.id IS NULL,0, fa.id) AS id, alumn.id AS idAlumnos, p.id AS idProfesores, IF(fa.idTipoIncidencias IS NULL, 0, fa.idTipoIncidencias) AS idTipoIncidencias,");
        SQL1.append(" hc.id AS idHorasCentro, ga.id AS idGrupAsig, IF(fa.idTipoObservaciones IS NULL, 0, fa.idTipoObservaciones) AS idTipoObservaciones, IF(fa.dia IS NULL, '").append(sqlDate).append("', fa.dia) AS dia,");
	SQL1.append(" fa.hora, IF(fa.comentarios IS NULL,'',fa.comentarios) AS comentarios, fa.fechaModificado, IF(ti.simbolo IS NULL, '', ti.simbolo) AS simbolo, IF(ti.descripcion IS NULL,'',ti.descripcion) AS descripcion");

        SQL1.append(" FROM asignaturas a INNER JOIN ClasesDetalle cd ON 1=1 INNER JOIN HorasCentro hc ON 1=1 INNER JOIN Horarios h ON 1=1  INNER JOIN GrupAsig ga ON 1=1  INNER JOIN Grupos g ON 1=1  INNER JOIN AsignaturasAlumno aa ON 1=1  INNER JOIN alumnos alumn ON alumn.id=aa.idAlumnos  LEFT OUTER JOIN Aulas au ON h.idAulas=au.id  LEFT OUTER JOIN Profesores p ON h.idProfesores=p.id  LEFT JOIN clasesanotadas AS ca ON ");
        SQL1.append(" (ca.idGrupAsig=ga.id AND ca.idHorasCentro=hc.id AND ca.fecha='").append(sqlDate).append("') ");
	SQL1.append(" LEFT JOIN faltasalumnos AS fa ON (fa.idAlumnos=alumn.id AND fa.idHorasCentro=hc.id AND fa.idGrupAsig=ga.id AND fa.dia='").append(sqlDate).append("') ");
        SQL1.append(" LEFT JOIN tipoincidencias AS ti ON ti.id=fa.idTipoIncidencias" );
        SQL1.append(" WHERE alumn.id=").append(idAlumno).append(" AND aa.idGrupAsig=ga.id  AND  (aa.opcion<>'0' AND (cd.opcion='X' OR cd.opcion=aa.opcion)) AND  h.idClases=cd.idClases  AND cd.idGrupAsig=ga.id  AND  h.idHorasCentro=hc.id  AND ga.idGrupos=g.id  AND  ga.idAsignaturas=a.id ");
        SQL1.append(" AND h.idDias=").append(dc.getIntDia()).append(" ORDER BY hc.inicio");
	
       //System.out.println("incidenciasCollection.getIncidenciasInDay->"+SQL1.toString());
        
        int ordre = 0;
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1.toString(),st);
            while(rs1!=null && rs1.next())
            {
                BeanIncidenciasDia bean = new BeanIncidenciasDia();
                
                Integer id = rs1.getInt("id");
                bean.setId(id);
                bean.setOrdre(ordre);
                bean.setSimbolo(normalizeSimbol(rs1.getString("simbolo")));
                bean.setDescripcion(rs1.getString("descripcion"));
                bean.setFechaModificado(rs1.getTimestamp("fechaModificado"));
          
                bean.setIdAlumnos(rs1.getInt("idAlumnos"));
                bean.setIdGrupAsig(rs1.getInt("idGrupAsig"));
                bean.setIdHorasCentro(rs1.getInt("idHorasCentro"));
                bean.setHoraCentro(rs1.getString("horaCentro"));
                bean.setIdProfesores(rs1.getString("idProfesores"));
                bean.setIdTipoIncidencias(rs1.getInt("idTipoIncidencias"));
                bean.setIdTipoObservaciones(rs1.getInt("idTipoObservaciones"));        
                bean.setComentarios(rs1.getString("comentarios"));
                bean.setDia(rs1.getDate("dia"));               
                bean.setHora(rs1.getString("hora"));
                bean.setAsignatura(rs1.getString("asignatura"));
              
                
                bean.setNombreProfesor(rs1.getString("nombreProfesor"));
                bean.setPasoLista(rs1.getString("pasoLista").equalsIgnoreCase("S"));
                
                list.add(bean);
                ordre += 1;
            }
            if(rs1!=null) {
                rs1.close();
                rs1.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
               
         
         return list;
    }

    
    public  int getTotalIncidenciasOf(final int idAlumno, 
            final String[] simbolos, final int tipoObservaciones, final boolean commentRequired) {
        
        int total = 0;
        
        String condsimbolos = "";
        if (simbolos.length != 0) {
            String txt = "(";
            for (int i = 0; i < simbolos.length; i++) {
                txt += "'" + simbolos[i] + "',";
            }
            txt = StringUtils.BeforeLast(txt, ",") + ") ";
            condsimbolos = "AND ti.simbolo IN " + txt;
        }
        
        String condObservaciones  ="";
        if(tipoObservaciones>0) {
                    condObservaciones = " AND idTipoObservaciones="+tipoObservaciones+" ";
                }
 
        String condCommentRequired = "";
        if (commentRequired) {
            condCommentRequired = " AND NOT ((fal.idTipoObservaciones IS NULL OR fal.idTipoObservaciones=0) AND fal.Comentarios='') ";
        }
  
        StringBuilder tmp = new StringBuilder("SELECT count(ti.simbolo) as total FROM faltasalumnos AS fal INNER JOIN ");
        tmp.append(" tipoincidencias AS ti ON ti.id=fal.idTipoIncidencias ");
        tmp.append(" WHERE idAlumnos=").append(idAlumno).append(" ");  
        tmp.append(condsimbolos).append(condObservaciones).append(condCommentRequired);
        tmp.append(" GROUP BY idAlumnos");
          
        String SQL1 = tmp.toString();  
          
        
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                total = rs1.getInt("total");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
  
         return total;
    }


/**
 * Atenció aquesta query mostra nomes incidencies associades a la assignatura
 * Per exemple no mostraria les de convivència.
 * 
 * Si idProfesor="-1" (no filtrarà per professor)
 **/
    public  ArrayList<BeanIncidencias> getIncidencias(final int idAlumno, 
            final int horaCentro, final int idGrupAsig, final String idProfesor, final String dia)
    {
        ArrayList<BeanIncidencias> listInc = new  ArrayList<BeanIncidencias>();
        StringBuilder tmp = new StringBuilder("SELECT fa.*, ti.simbolo, ti.descripcion FROM faltasalumnos AS fa INNER JOIN tipoincidencias AS ti ON ");
        tmp.append(" fa.idTipoIncidencias=ti.id WHERE idAlumnos=").append(idAlumno).append(" AND idHorasCentro=").append(horaCentro).append(" AND ");
        if(!idProfesor.equals("-1"))
        {
            tmp.append(" idProfesores='").append(idProfesor).append("' AND ");
        }
        tmp.append(" idGrupAsig=").append(idGrupAsig).append(" AND dia='").append(dia).append("'"); 
        //S'ha afegit idGrupAsig;
        String SQL1 = tmp.toString();        
          
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanIncidencias bh = new BeanIncidencias();
               bh.setId(rs1.getInt("id"));
               bh.setIdProfesores(rs1.getString("idProfesores"));
               bh.setIdAlumnos(rs1.getInt("idAlumnos"));
               bh.setIdTipoIncidencias(rs1.getInt("idTipoIncidencias"));
               bh.setIdHorasCentro(rs1.getInt("idHorasCentro"));
               bh.setIdGrupAsig(rs1.getInt("idGrupAsig"));
               bh.setIdTipoObservaciones(rs1.getInt("idTipoObservaciones"));
               bh.setDia(rs1.getDate("dia"));
               bh.setHora(rs1.getString("hora"));
               bh.setObservaciones(rs1.getString("comentarios"));
               bh.setFechaModificado(rs1.getTimestamp("fechaModificado"));
               
               bh.setDescripcion(rs1.getString("descripcion"));              
               String simbolo = normalizeSimbol(rs1.getString("simbolo"));
               bh.setSimbolo(simbolo);
               
               //falta incloure descripcion, codigo, idclases si fos necessari
               if(simbolo.equalsIgnoreCase("AG") || simbolo.equalsIgnoreCase("AL") ||
                  simbolo.equalsIgnoreCase("CP") || simbolo.equalsIgnoreCase("CN") ||
                  simbolo.equalsIgnoreCase("DI")) bh.setEditable(true);
               listInc.add(bh);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        return listInc;
   }
    
    //El mateix que l'anterior però aplica a una única claseid i dins d'un període donat DESDE - HASTA
    public  ArrayList<BeanIncidencias> getIncidencias(final int idAlumno, 
             final int idGrupAsig, final String idProfesor, final String desde, final String hasta)
    {
        ArrayList<BeanIncidencias> listInc = new  ArrayList<BeanIncidencias>();
        StringBuilder tmp = new StringBuilder("SELECT fa.*, ti.simbolo, ti.descripcion FROM faltasalumnos AS fa INNER JOIN tipoincidencias AS ti ON ");
        tmp.append(" fa.idTipoIncidencias=ti.id WHERE idAlumnos=").append(idAlumno).append(" AND ");
        tmp.append(" idProfesores='").append(idProfesor).append("' AND idGrupAsig=").append(idGrupAsig).append(" AND dia>='").append(desde).append("' ");
        tmp.append(" AND dia<='").append(hasta).append("'");
    
        String SQL1 = tmp.toString();        
        
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanIncidencias bh = new BeanIncidencias();
               bh.setId(rs1.getInt("id"));
               bh.setIdProfesores(rs1.getString("idProfesores"));
               bh.setIdAlumnos(rs1.getInt("idAlumnos"));
               bh.setIdTipoIncidencias(rs1.getInt("idTipoIncidencias"));
               bh.setIdHorasCentro(rs1.getInt("idHorasCentro"));
               bh.setIdGrupAsig(rs1.getInt("idGrupAsig"));
               bh.setIdTipoObservaciones(rs1.getInt("idTipoObservaciones"));
               bh.setDia(rs1.getDate("dia"));
               bh.setHora(rs1.getString("hora"));
               bh.setObservaciones(rs1.getString("comentarios"));
               bh.setFechaModificado(rs1.getTimestamp("fechaModificado"));
               
               bh.setDescripcion(rs1.getString("descripcion"));              
               String simbolo = normalizeSimbol(rs1.getString("simbolo"));
               bh.setSimbolo(simbolo);
               bh.setEditable(true);
               
               //falta incloure descripcion, codigo, idclases si fos necessari
               if(simbolo.equalsIgnoreCase("ALH") || simbolo.equalsIgnoreCase("EX")) {
                   bh.setEditable(false);
               }
               listInc.add(bh);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        return listInc;
   }
    
/**
 * Obté un diccionari de JSON en els dies que hi ha incidencies per que es pugui passar a un calendari
 * {'2011':{
  '1':{
    '1':'a'
  },
  '4':{
    '1':'a',
    '10':'c',
    '15':'a'
  },
  '5':{
    '17':'b'
  }
}}
 * 'a', 'b', 'c', ... stand for diferent types of markups
 * @param idAlumno
 * @return 
 */    
    public String getJSONDiasIncidencias(final int idAlumno, final MyDatabase sgd)
    {
        HashMap<String,HashMap> json = new HashMap<String,HashMap>();
                
        String SQL1 = "SELECT GROUP_CONCAT(simbolo SEPARATOR ',') AS simbolos, YEAR(fa.dia) AS anyo, MONTH(fa.dia) AS mes, DAY(dia) AS dia FROM faltasalumnos AS fa INNER JOIN tipoincidencias AS ti ON ti.id=fa.idTipoIncidencias "+
                       "WHERE idAlumnos="+idAlumno+" AND simbolo IN ('FA','F','FJ','R','RE','RJ') GROUP BY fa.dia ORDER BY anyo, mes, dia";

       
     
     
        try {
            Statement st = sgd.createStatement();
            ResultSet rs1 = sgd.getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {             
                String year2 = "'"+rs1.getInt("anyo")+"'";
                String month2 = "'"+rs1.getInt("mes")+"'";
                String day2 = "'"+rs1.getInt("dia")+"'";
                String simbolos2 = rs1.getString("simbolos");
                //System.out.println(year2+"-"+month2+"-"+day2);
                
                HashMap<String, HashMap> currentYear = null;
                if(!json.containsKey(year2))
                {
                    currentYear = new HashMap<String, HashMap>();
                    json.put(year2, currentYear);
                }
                else
                {
                    currentYear = json.get(year2);
                }
                
                HashMap<String, String> currentMonth = null;
                if(!currentYear.containsKey(month2))
                {
                    currentMonth = new HashMap<String, String>();
                    currentYear.put(month2, currentMonth);
                }
                else
                {
                    currentMonth = currentYear.get(month2);
                }
                ArrayList<String> parsed = StringUtils.parseStringToArray(simbolos2, ",", StringUtils.CASE_UPPER);
                String realsimbol = "a";
                boolean containsJ = parsed.contains("FJ") || parsed.contains("RJ");
                boolean containsNJ =  parsed.contains("FA") || parsed.contains("F") || parsed.contains("RE") || parsed.contains("R");
                if(containsJ && !containsNJ)
                    realsimbol = "b";
                else if(containsJ && containsNJ)
                    realsimbol = "c";
                currentMonth.put(day2, "'"+realsimbol+"'");
              
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncidenciasCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String result = json.toString().replaceAll("=",":");
        json = null;
        return  result;
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
