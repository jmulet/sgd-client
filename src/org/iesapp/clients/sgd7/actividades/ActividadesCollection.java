/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.clases.BeanClase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;


/**
 *
 * @author Josep
 */
public class ActividadesCollection implements IClientController {
    
      private static final String summaryStyle = "color:grey;font-style:italic;font-size:10px";
      private static final String aprovaStyle ="font-size:10px;";    
      private static final String suspenStyle ="font-size:10px;color:red;";
      private static final String npStyle ="color:grey;font-size:10px;";
      private static final String NPSIMBOL = "NP";
      private final IClient client;
    private final String dbName;
      
//      public ActividadesCollection()
//      {
//          this.client = null;
//      }
      
      public ActividadesCollection(IClient client)
      {
          this.client = client;
          this.dbName = client.getCurrentDBPrefix()+client.getAnyAcademic()+"plus";
      }
      
     /**
     * No filtra per avaluacio si idEval=0
     * @param idProfesores
     * @param idClase
     * @param idEval
     * @param idConcepte = -1 global; 0=tots (no filtra); >0 definits per l'usuari
     * @return ArrayList<BeanActividadClase> 
     */
     public ArrayList<BeanActividadClase> loadActividades(final String idProfesores, 
             final BeanClase beanclase, final int idEval, final int idConcepte, String order, final int flagEvaluable) {
         
         if(!order.equalsIgnoreCase("ASC") && !order.equalsIgnoreCase("DESC")) {
                     order = "DESC";
                 }
         
         //Una classe pot tenir associada més d'un idGrupAsig
         String condition = beanclase.getIdGrupAsigs().toString();
         condition = condition.replace("[", "(").replace("]", ")");
         //System.out.println("CONDITION GRUPOS "+condition);
         
         //Aixo soluciona un error intern, si no s'han detectat els grupasigs de la classe
         ArrayList<BeanActividadClase> beanAct = new ArrayList<BeanActividadClase>();
         if(beanclase.getIdGrupAsigs().isEmpty()) {
                     return beanAct;
                 }
         
         StringBuilder tmp = new StringBuilder();
         String selects = new StringBuilder("  act.id, act.idProfesores, act.idGrupAsig, ")
                 .append("  act.idEvaluacionesDetalle, act.descripcion, act.fecha, ")
                 .append("  act.publicarWeb, act.evaluable, act.seguimiento,")
                 .append("  IF(pes.peso IS NULL, 0, pes.peso) AS peso ").toString();
         

         if(idEval<=0) //No filtra per avaluacio
         {  
             tmp.append("SELECT ").append(selects)
                .append(" FROM actividades as act LEFT JOIN pesoactividades as pes ON ")
                .append(" act.id=pes.idActividades WHERE idProfesores='").append(idProfesores)
                .append("' AND idGrupAsig IN ").append(condition)
                .append(" ORDER BY fecha ").append(order).append(", descripcion"); 
         }
         else
         {
            tmp.append("SELECT ").append(selects)
               .append(" FROM actividades as act LEFT JOIN pesoactividades as pes ON ")
               .append(" act.id=pes.idActividades WHERE idProfesores='").append(idProfesores)
               .append("' AND idEvaluacionesDetalle='").append(idEval).append("'")
               .append(" AND idGrupAsig IN ").append(condition)
               .append(" ORDER BY fecha ").append(order).append(", descripcion");                            
         }
         
         String SQL1 = tmp.toString();
         
          
          try{
          Statement st = getSgd().createStatement();
          ResultSet rs1 = getSgd().getResultSet(SQL1,st);        

          int i=0;
          int numact = 0;
          String descripcion0="";
          java.sql.Date fecha0=null;
          
          BeanActividadClase act = new BeanActividadClase();
          HashMap<Integer,Integer> mapIds = new HashMap<Integer,Integer>();
          while( rs1!=null && rs1.next() )
          { 
             
            String descripcion = rs1.getString("descripcion");
            java.sql.Date fecha = rs1.getDate("fecha");
            int idevaluacionesdetalle = rs1.getInt("idevaluacionesdetalle");
            int id = rs1.getInt("id");
            boolean web = rs1.getString("publicarWEB").equalsIgnoreCase("S");
            boolean seg =  rs1.getString("seguimiento").equalsIgnoreCase("S");
            boolean aval =rs1.getString("evaluable").equalsIgnoreCase("S");
            int peso = rs1.getInt("peso");
            int idgrupasig = rs1.getInt("idGrupasig");
            
            if(i==0)
            {
                act.setDescripcion(descripcion);
                act.setConcepto( new Conceptos(id, Conceptos.IDACTIVIDAD, client).getBean() );
                act.setFecha(fecha);
                act.setIdEvaluacionesDetalle(idevaluacionesdetalle);
                act.setPublicarWEB(web);
                act.setSeguimiento(seg);
                act.setEvaluable(aval);
                act.setOrdre(numact);
                act.setPeso(peso);
                act.setIdProfesores(idProfesores);   
                act.setIdClase(beanclase.getIdClase());
            }
            
            if(i>0 && !descripcion0.equals(descripcion) && fecha0!=fecha) //nova actividad
            {
                 
                 act.setMapGrupAct(mapIds);
                 act.setTotalasig( getAsignadaTotal(mapIds.values()) );
                 
                 if( (idConcepte==0 || act.concepto.id == idConcepte) )
                 {
                     if(flagEvaluable==0 || (flagEvaluable>0 && act.evaluable))
                     {
                         beanAct.add(act);
                         numact +=1;
                     }    
                    
                 }
               
                                                  
                
                 act = new BeanActividadClase();
                 mapIds = new HashMap<Integer,Integer>();
                 mapIds.put(idgrupasig, id);
                                
                 act.setDescripcion(descripcion);
                 act.setConcepto( new Conceptos(id, Conceptos.IDACTIVIDAD, client).getBean() );
                 
                 act.setFecha(fecha);
                 act.setIdEvaluacionesDetalle(idevaluacionesdetalle);
                 act.setPublicarWEB(web);
                 act.setSeguimiento(seg);
                 act.setEvaluable(aval);
                 act.setOrdre(numact);   
                 act.setPeso(peso);
                 act.setIdProfesores(idProfesores);  
                 act.setIdClase(beanclase.getIdClase());
                 mapIds.put(idgrupasig, id);
            }
            else
            {
                 mapIds.put(idgrupasig, id);
            }
            
            descripcion0 = descripcion;
            fecha0 =fecha;
                    
            i += 1;
         }
         //Al final cal guardar la darrera sempre que mapIds no sigui empty
          if(!mapIds.isEmpty()) 
          {
              act.setMapGrupAct(mapIds);
              act.setTotalasig( getAsignadaTotal(mapIds.values()) );
                                        
              if( (idConcepte==0 || act.concepto.id == idConcepte) )
                 {
                     if(flagEvaluable==0 || (flagEvaluable>0 && act.evaluable))
                     {
                         beanAct.add(act);
                         numact +=1;
                     }    
                    
                 }
          }
                   
         if(rs1!=null) {
                  rs1.close();
                  st.close();
              }
      }
      catch(java.sql.SQLException ex)
      {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
      }
          
                
         //Comprova si falta crear alguna activitat associada al grupClase
         //Això passa en activitats antigues creades per la PDA i que no
         //varen crear tots els grups
         for(int i=0; i<beanAct.size(); i++)
         {
          
          //System.out.println("la comparacio de mides es "+beanAct.get(i).mapGrupAct.keySet().size()+"; "+beanclase.getIdGrupAsigs().size());
          if(beanAct.get(i).mapGrupAct.keySet().size() != beanclase.getIdGrupAsigs().size())
          {
                     for(int k=0; k<beanclase.getIdGrupAsigs().size(); k++)
                     {
                         //System.out.println("son diferent!!! actividad ::"+beanAct.get(i).descripcion);
                         
                         int idgrupo = beanclase.getIdGrupAsigs().get(k);
                         if(!beanAct.get(i).mapGrupAct.containsKey(idgrupo))
                         {
                             //intenta arreglar problema creant una nova activitat
                             //System.out.println("trying to fix problem add new grup to activity "+  beanAct.get(i).mapGrupAct);
                             beanAct.get(i).mapGrupAct.put(idgrupo, -1);
                             ActividadClase acc = new ActividadClase(beanAct.get(i), client);
                            // acc.save();
                            // int idActividad = acc.mapGrupAct.get(idgrupo);
                            // beanAct.get(i).mapGrupAct.put(idgrupo, idActividad);
                             //System.out.println(".. new activity "+  beanAct.get(i).mapGrupAct);
                         }
                     }
           }
         }       
        
        return beanAct;
    }

     
     /**
     * 
     * @return List of idGrupAsig in a given this.idClase
     * Aquesta select es podria simplificar enormament
     * TODO
     */
    public ArrayList<Integer> getGrupAsigInClass(final String idProfesores, final int idClase)
    {            
        ArrayList<Integer> list = new ArrayList<Integer>();
            
//        String SQL1 = " SELECT DISTINCT "
//                + " ga.id as idGrupAsig "
//                + " FROM "
//                + "  horarios AS h  "
//                + "  INNER JOIN "
//                + "  aulas AS au "
//                + "  ON (h.idAulas = au.id)  "
//                + "  INNER JOIN "
//                + "  clases AS class  "
//                + " ON (h.idClases = class.id)  "
//                + " INNER JOIN "
//                + " asignaturas AS a ON 1=1 "
//                + "  INNER JOIN "
//                + " clasesdetalle AS cd  "
//                + "  ON 1 = 1  "
//                + "  INNER JOIN "
//                + "  grupasig AS ga  "
//                + "  ON 1 = 1  "
//                + "  INNER JOIN "
//                + "  grupos AS g  "
//                + "  ON 1 = 1  "
//                + "  INNER JOIN "
//                + "  asignaturasalumno AS aa  "
//                + "  ON 1 = 1  "
//                + " WHERE h.idProfesores='" + idProfesores + "' "
//                + " AND class.id='" + idClase + "'"
//                + "  AND aa.idGrupAsig = ga.id "
//                + "   AND ( "
//                + "     aa.opcion <> '0'  "
//                + "    AND ( "
//                + "      cd.opcion = 'X'  "
//                + "      OR cd.opcion = aa.opcion "
//                + "    ) "
//                + "  )  "
//                + "  AND h.idClases = cd.idClases  "
//                + "  AND cd.idGrupAsig = ga.id  "
//                + "  AND ga.idGrupos = g.id  "
//                + "  AND ga.idAsignaturas = a.id  ";
            
        
      StringBuilder tmp= new StringBuilder("SELECT DISTINCT ga.id AS idGrupAsig FROM ");
      tmp.append(" horarios AS h INNER JOIN clases AS class  ON h.idClases=class.id ");
      tmp.append(" INNER JOIN clasesdetalle AS cd ON h.idClases=cd.idClases ");
      tmp.append(" INNER JOIN grupasig AS ga ON cd.idGrupAsig=ga.id ");
      tmp.append(" INNER JOIN asignaturasalumno AS aa ON aa.idGrupAsig = ga.id ");
      tmp.append(" WHERE h.idProfesores = '").append(idProfesores).append("' "); 
      tmp.append(" AND class.id = '").append(idClase).append("' AND ( "); 
      tmp.append(" aa.opcion <> '0' AND (cd.opcion = 'X' OR cd.opcion = aa.opcion))"); 

       //System.out.println("Grupasiginclass::"+SQL1);
        String SQL1 = tmp.toString();
     
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);        

            while (rs1 != null && rs1.next()) {
                list.add(rs1.getInt("idGrupAsig"));
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    private ArrayList<BeanActividadesAlumno> getAlumnosGrupo(final String codigoProf, final int idClase, final boolean select)
    {
     
     ArrayList<BeanActividadesAlumno> list =  new ArrayList<BeanActividadesAlumno>();
     
     String SQL1 = " SELECT DISTINCT alumn.expediente, alumn.id, alumn.nombre, "
             + "    g.grupo, g.id AS grupid, a.descripcion, a.codigo, cd.idClases,   "
             + "    p.codigo AS profesor, p.nombre  AS NombreProfe, ga.id as guaid  "
             + "    FROM asignaturas a    "
             + "    INNER JOIN clasesdetalle cd ON 1=1    "
             + "    INNER JOIN horascentro hc ON 1=1    "
             + "    INNER JOIN horarios h ON 1=1    "
             + "    INNER JOIN grupasig ga ON 1=1    "
             + "    INNER JOIN grupos g ON 1=1    "
             + "    INNER JOIN asignaturasalumno aa ON 1=1    "
             + "    INNER JOIN alumnos alumn ON alumn.id=aa.idAlumnos    "
             + "    LEFT OUTER JOIN aulas au ON h.idAulas=au.id    "
             + "    LEFT OUTER JOIN profesores p ON h.idProfesores=p.id    "
             + "    WHERE p.codigo=" + codigoProf + " AND cd.idClases=" + idClase + " AND    "
             + "     aa.idGrupAsig=ga.id    "
             + "    AND  (aa.opcion<>'0' AND (cd.opcion='X' OR cd.opcion=aa.opcion))    "
             + "    AND  h.idClases=cd.idClases    "
             + "    AND cd.idGrupAsig=ga.id    "
             + "    AND  h.idHorasCentro=hc.id    "
             + "    AND ga.idGrupos=g.id    "
             + "    AND  ga.idAsignaturas=a.id    "
             + "    ORDER BY nombre   ";
     
             
              
       int i=0;    
     
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);        

            while( rs1!=null && rs1.next() )
            { 
               BeanActividadesAlumno bh = new BeanActividadesAlumno();
               bh.setOrdre(i);
               bh.setIdAlumnos(rs1.getInt("id"));
               bh.setNombre(rs1.getString("nombre"));
               bh.setSelected(select);
               bh.setIdGrupAsig(rs1.getInt("guaid"));
               list.add(bh);
               i += 1;
            }
             if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
   
     
     return list;
    }

    
    public ArrayList<BeanActividadesAlumno> getActividadesAlumno(final String codigProf, final int idClase, 
                                                                        final HashMap<Integer,Integer> mapIds, final boolean perDefecte)
    {
        //Obté una llista en blanc, per assegurar-se que obtenim tots els alumnes
        //per defecte assigna l'activitat a tothom
        ArrayList<BeanActividadesAlumno> list = getAlumnosGrupo(codigProf, idClase, perDefecte); 
        //aquesta llista ha de contenir la idactivitat associada a cada alumne
        for(int i=0; i<list.size(); i++)
        {
            BeanActividadesAlumno baa = list.get(i);
           
            if(mapIds.containsKey(baa.getIdGrupAsig()))
            {
                int idActivitat = mapIds.get(baa.getIdGrupAsig());
                baa.setIdActividades(idActivitat);
            }
        }
        
        //Ara marcarem els que tenim seleccionats en la base de dades        
        for(int idact: mapIds.values())
        {
         
        String SQL1 ="SELECT id, idAlumnos, idActividades, "
                + " IF(fechaEntrega='0000-00-00',NULL,fechaEntrega) AS fechaEntrega, "
                + " nota FROM actividadesalumno  WHERE idActividades='"+idact+"'"; 
        
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);        

            while( rs1!=null && rs1.next() )
            { 
                    int idalumno = rs1.getInt("idAlumnos");
                    
                    //Ara intenta cercar l'alumne en la llista
                    int pos =-1;
                    for(int i=0; i<list.size();i++)
                    {
                        if(list.get(i).getIdAlumnos()==idalumno)
                        {
                            pos = i;
                            break;
                        }
                    }
                    
                    if(pos>=0)
                    {
                        list.get(pos).setFechaEntrega(rs1.getDate("fechaEntrega")); //aqui hi ha un problema  Value '0000-00-00' can not be represented as java.sql.Date
                        list.get(pos).setNota(rs1.getFloat("nota"));
                        list.get(pos).setId(rs1.getInt("id"));
                        list.get(pos).setSelected(true);
                        list.get(pos).setIdActividades(idact);
                    }
                 
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        return list;
    }

    private int getAsignadaTotal(final Collection<Integer> idactividades) {        
       int total = 0;
       if(idactividades.isEmpty()) {
           return 0;
       }
       
       String txt = idactividades.toString();
       txt = StringUtils.AfterFirst(txt, "[");
       txt = StringUtils.BeforeLast(txt, "]");
       
       String SQL1 ="SELECT COUNT(idActividades) AS total FROM actividadesalumno "
               + " WHERE idActividades IN ("+txt+") GROUP BY matriculado";
              
         try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);        

            while( rs1!=null && rs1.next() )
            { 
                 
                 total=rs1.getInt("total");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }

    /**
     * @param mysql
     * @param idProfesor
     * @param idClase
     * @return 
     */
    public ArrayList<BeanConceptos> getConceptosClase(final String idProfesor, final int idClase)
    {
        ArrayList<BeanConceptos> list = new ArrayList<BeanConceptos>();
        
        //Si no hi ha cap sempre cal afegir el primer de tots com a global
        //Aquest no es pot modificar, automaticament se li va restant percentage.
        BeanConceptos bean = new BeanConceptos();
        bean.id = -1;
        bean.htmlColor="000000";
        bean.nombre="Global";
        list.add(bean);
        
        int global = 100;
        
        String SQL1 = "SELECT * FROM "+client.getPlusDbName()+"sgd_conceptos where idProfesores='"+idProfesor+"' AND idClase='"+idClase+"'"
                + " ORDER BY nombreConcepto ASC";
         try {
            Statement st = client.getPlusDb().createStatement();
            ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);        

            while( rs1!=null && rs1.next() )
            { 
                  BeanConceptos bc = new BeanConceptos();
                  bc.setEvaluable(rs1.getString("evaluableActividad").equalsIgnoreCase("S"));
                  bc.setWeb(rs1.getString("webActividad").equalsIgnoreCase("S"));
                  bc.id = rs1.getInt("id");
                  bc.idClase = idClase;
                  bc.htmlColor = rs1.getString("colorConcepto");
                  bc.nombre = rs1.getString("nombreConcepto");
                  bc.porcentaje = rs1.getInt("porcentajeConcepto");
                  global -= bc.porcentaje;
                  bc.textoActividad = rs1.getString("textoActividad");
                  bc.idProfesores = rs1.getString("idProfesores");
                  list.add(bc);
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //assigna automaticament el percentage que queda lliure
        list.get(0).setPorcentaje(global>=0?global:0);
        
        
        return list;
    }

/**
     * Obté una taula dinamica, es a dir, amb un nombre variable de columnes segons el nombre 
     * d'activitats que hi ha en una clase, avaluacio i per concepte
     * @param idClase
     * @param idProfesor
     * @param idEvaluacio
     * @param idConcepte
     * @return 
     */
    public DynamicTable getInformeDetallatActivitats(final BeanClase beanclase, final String idProfesor, final int idEvaluacio, final int idConcepte)
    {
       
        
         DynamicTable dt = new DynamicTable();
         dt.addRowLabel("","","Alumne/a");
         //Nomes activitats que siguin avaluables
         ArrayList<BeanActividadClase> suitableAct = loadActividades(idProfesor, beanclase, idEvaluacio, idConcepte, "ASC", 1);
         
         int sumaPesos = 0;
         for(int i=0; i<suitableAct.size(); i++)
         {
             dt.addRowLabel(suitableAct.get(i).descripcion,"("+suitableAct.get(i).peso+"%)", 
                     new DataCtrl(suitableAct.get(i).fecha).getDiaMesComplet());
             sumaPesos += suitableAct.get(i).getPeso();
         }
         
         
        if(suitableAct.size()>0)
            dt.addRowLabel("","","Mitjana");
        
        //Determina els alumnes
        ArrayList<BeanActividadesAlumno> alumnosGrupo = getAlumnosGrupo(idProfesor, beanclase.getIdClase(), false);

        ArrayList<CellModel> newCol = new ArrayList<CellModel>();
        for(int i=0; i<alumnosGrupo.size(); i++)
        {
            String txt = (alumnosGrupo.get(i).getOrdre()+1)+". "+alumnosGrupo.get(i).getNombre();
            newCol.add( new CellModel(txt,"font-size:11px;"));
        }
        //Afegeix al footer els camps %, mitjana, desv.
        newCol.add( new CellModel("Aproven (%)", summaryStyle));
        newCol.add( new CellModel("Mitjana", summaryStyle));
        newCol.add( new CellModel("Desv. típica", summaryStyle));
        
        dt.addColumn(newCol);

        //Va activitat a activitat posant notes
       for(int i=0; i<suitableAct.size(); i++)
        {           
            newCol = new ArrayList<CellModel>();
            ArrayList<BeanActividadesAlumno> aa = getActividadesAlumno(idProfesor, beanclase.getIdClase(), suitableAct.get(i).getMapGrupAct(),false);
            
            float aproven = 0f;
            float mitjana = 0f;
            float sigma = 0f;
            int sumen = 0;
            
            for(int j=0; j<aa.size(); j++)
            {
                boolean entregat = aa.get(j).nota>=0;
                float nota = aa.get(j).nota>=0?aa.get(j).nota:0f;
                String style = aprovaStyle;
                if(nota<5) style = suspenStyle;
                if(!entregat) style = npStyle;
                newCol.add( new CellModel(entregat? nota : NPSIMBOL, style) );
                if(entregat)
                {
                    sumen +=1;
                    mitjana += nota;
                    sigma += nota*nota;
                    if(nota>=5) aproven += 1;
                }
            }
            
            if(sumen>0)
            {
                aproven = aproven*100/(1f*sumen);
                mitjana = mitjana/(1f*sumen);
                sigma = (float) Math.sqrt( sigma/(1.0*sumen) - mitjana*mitjana );
            }
    
            newCol.add(new CellModel(round(aproven,1), summaryStyle));
            newCol.add(new CellModel(round(mitjana,1), summaryStyle));
            newCol.add(new CellModel(round(sigma,1), summaryStyle));
            
            dt.addColumn(newCol);
        }
        
        //Afegeix la columna de les mitjanes
        newCol = new ArrayList<CellModel>();
        
        float aproven = 0f;
        float mitjana = 0f;
        float sigma = 0f;
        int sumen = 0;
            
        for(int i=0; i<alumnosGrupo.size(); i++)
        {
             String style=aprovaStyle;
             
             //Calcula la mitjana línia a línia, descartant els NP
             float mitjanaAlumne = 0f;
             int overallPesos = 0;
             for(int k=0; k<suitableAct.size(); k++)
             {
                 if(!NPSIMBOL.equals(dt.getCellAt(i, k+1).getValue().toString()))
                 {
                     int pes = suitableAct.get(k).getPeso();
                     overallPesos += pes;
                     mitjanaAlumne += pes*( (Number) dt.getCellAt(i, k+1).getValue()).floatValue();
                 }
             }
             
             float nota = 0f;
             if(overallPesos>0) nota = mitjanaAlumne/(1f*overallPesos);
             float mitja = round(nota, 2);
                     
             if(mitja<5) style = suspenStyle;
             newCol.add( new CellModel ((Object) mitja, style) );
             
             sumen +=1;
             mitjana += nota;
             sigma += nota*nota;
             if(nota>=5) aproven += 1;
        }
       
        if(sumen>0)
        {
             aproven = aproven*100/(1f*sumen);
             mitjana = mitjana/(1f*sumen);
             sigma = (float) Math.sqrt( sigma/(1.0*sumen) - mitjana*mitjana );
        }
        
         newCol.add(new CellModel(round(aproven,1), summaryStyle));
         newCol.add(new CellModel(round(mitjana,1), summaryStyle));
         newCol.add(new CellModel(round(sigma,1), summaryStyle));
         dt.addColumn(newCol);
        
       return dt;    
    }
    
    /**
     * Informe Resumit
     * @param beanclase
     * @param idProfesor
     * @param idEvaluacio
     * @return 
     */
    public DynamicTable getInformeResumitActivitats(final BeanClase beanclase, final String idProfesor, final int idEvaluacio)
    {
        DynamicTable dt = new DynamicTable();
        //Primera passa determina tots els conceptes que té l'assignatura
        //Per força sempre en tindrà un anomenat global
        ArrayList<BeanConceptos> conceptosClase = getConceptosClase(idProfesor, beanclase.getIdClase());
               
        dt.addRowLabel("","Alumne/a","");
        int sumatpc=0;
        for(int i=0; i<conceptosClase.size(); i++)
        {
            dt.addRowLabel(conceptosClase.get(i).nombre , " ("+ conceptosClase.get(i).getPorcentaje()+"%)","");
            sumatpc += conceptosClase.get(i).porcentaje;
        }
        dt.addRowLabel("","Nota","");
        
        String condition = beanclase.getIdGrupAsigs().toString();
        condition = condition.replace("[", "(").replace("]", ")");
        if(beanclase.getIdGrupAsigs().isEmpty()) condition="(-1)";
        
        //Afegim una columna amb els noms del alumnes
        //Determina els alumnes
        ArrayList<BeanActividadesAlumno> alumnosGrupo = getAlumnosGrupo(idProfesor, beanclase.getIdClase(), false);
        ArrayList<CellModel> newCol = new ArrayList<CellModel>();
        for(int i=0; i<alumnosGrupo.size(); i++)
        {
            String txt = (alumnosGrupo.get(i).getOrdre()+1)+". "+alumnosGrupo.get(i).getNombre();
            newCol.add( new CellModel(txt,"font-size:11px;"));
            
        }
        //Afegeix al footer els camps %, mitjana, desv.
        newCol.add( new CellModel("Aproven (%)", summaryStyle));
        newCol.add( new CellModel("Mitjana", summaryStyle));
        newCol.add( new CellModel("Desv. típica", summaryStyle));
        dt.addColumn(newCol);
        
        //Aquesta select obte directament la mitjana de les activitats
         for(int i=0; i<conceptosClase.size(); i++)
         {
            float aproven = 0f;
            float mitjana = 0f;
            float sigma = 0f;
            int sumen = 0;
            
            String whichActivities = getIdActivities(conceptosClase, conceptosClase.get(i).id);
            HashMap<Integer, Float> mapaAlumnesNotes = new HashMap<Integer, Float>();

// Aquesta query es molt lenta en mysql3 - s'ha reemplaçat per la de sota 21/5/12
//          String sQuery = " SELECT DISTINCT  "
//                + " alumn.expediente, "
//                + " alumn.id as idAlumno, "
//                + " alumn.nombre, "
//                + " IF(SUM(peso)=0,0,ROUND(SUM(nota*peso)/SUM(peso),2)) AS nota "
//                + " FROM "
//                + " Asignaturas a  "
//                + " INNER JOIN "
//                + " ClasesDetalle cd  "
//                + " ON 1 = 1  "
//                + " INNER JOIN "
//                + " HorasCentro hc "
//                + " ON 1 = 1  "
//                + "  INNER JOIN "
//                + " Horarios h  "
//                + " ON 1 = 1  "
//                + "  INNER JOIN "
//                + " GrupAsig ga  "
//                + " ON 1 = 1  "
//                + " INNER JOIN "
//                + " Grupos g  "
//                + " ON 1 = 1  "
//                + "  INNER JOIN "
//                + "  AsignaturasAlumno aa  "
//                + " ON 1 = 1  "
//                + "  INNER JOIN "
//                + " alumnos alumn  "
//                + " ON alumn.id = aa.idAlumnos  "
//                + " LEFT OUTER JOIN "
//                + "  Aulas au  "
//                + "  ON h.idAulas = au.id  "
//                + "  LEFT OUTER JOIN "
//                + " Profesores p  "
//                + "  ON h.idProfesores = p.id  "
//                + " LEFT JOIN actividadesalumno AS aca ON aca.idAlumnos=aa.idAlumnos  "
//                + " LEFT JOIN actividades AS ac ON aca.idActividades=ac.id  "
//                + " LEFT JOIN pesoactividades AS pa ON pa.idActividades = ac.id  "
//                + " WHERE p.codigo = "+idProfesor
//                + "   AND cd.idClases = '"+beanclase.getIdClase()+"'  "
//                + "   AND aa.idGrupAsig = ga.id  "
//                + "   AND ( "
//                + "     aa.opcion <> '0'  "
//                + "     AND ( "
//                + "       cd.opcion = 'X'  "
//                + "       OR cd.opcion = aa.opcion "
//                + "     ) "
//                + "   )  "
//                + "   AND h.idClases = cd.idClases  "
//                + "   AND cd.idGrupAsig = ga.id  "
//                + "   AND h.idHorasCentro = hc.id  "
//                + "   AND ga.idGrupos = g.id  "
//                + " AND ga.idAsignaturas = a.id AND "
//                + " ac.evaluable='S' AND aca.fechaEntrega  IS NOT NULL AND ac.idEvaluacionesDetalle="+idEvaluacio+ " "
//                + " AND ac.idProfesores="+idProfesor
//                + " AND ac.idGrupAsig IN "+condition
//                + "  "+whichActivities+" GROUP BY aca.idAlumnos "
//                + " ORDER BY nombre ";
//            
            
          StringBuilder tmp = new StringBuilder("SELECT DISTINCT alumn.expediente, alumn.id AS idAlumno, alumn.nombre,");
          tmp.append(" IF(SUM(peso) = 0,0,ROUND(SUM(if(nota>=0,nota,0) * peso) / SUM(peso), 2)) AS nota ");
          tmp.append(" FROM Asignaturas a INNER JOIN GrupAsig ga ON ga.idAsignaturas = a.id ");
          tmp.append(" INNER JOIN Grupos g ON ga.idGrupos = g.id INNER JOIN ClasesDetalle cd ");
          tmp.append(" ON cd.idGrupAsig = ga.id INNER JOIN AsignaturasAlumno aa ");
          tmp.append(" ON aa.idGrupAsig = ga.id INNER JOIN alumnos alumn ");
          tmp.append(" ON alumn.id = aa.idAlumnos LEFT JOIN actividadesalumno AS aca ");
          tmp.append(" ON aca.idAlumnos = aa.idAlumnos LEFT JOIN actividades AS ac ");
          tmp.append(" ON aca.idActividades = ac.id LEFT JOIN (SELECT DISTINCT idActividades,idConceptosEvaluables,peso FROM pesoactividades) AS pa ");
          tmp.append(" ON pa.idActividades = ac.id WHERE cd.idClases = '");
          tmp.append(beanclase.getIdClase());
          tmp.append("'  AND (aa.opcion <> '0' AND (cd.opcion = 'X' OR cd.opcion = aa.opcion)) ");
          tmp.append(" AND ac.evaluable = 'S'  AND ac.idEvaluacionesDetalle = ");
          tmp.append(idEvaluacio);
          tmp.append(" AND ac.idProfesores = ");
          tmp.append(idProfesor);
          tmp.append(" AND ac.idGrupAsig IN ");
          tmp.append(condition);
          tmp.append(" ");
          tmp.append(whichActivities);
          tmp.append(" GROUP BY aca.idAlumnos ORDER BY nombre ");
        
          String sQuery = tmp.toString();
          //System.out.println("Squery="+sQuery);
        
             try {
                Statement st = getSgd().createStatement();
                ResultSet rs1 = getSgd().getResultSet(sQuery,st);        

                while(rs1!=null && rs1.next())
                {
                   mapaAlumnesNotes.put(rs1.getInt("idAlumno"),rs1.getFloat("nota"));                   
                }
                if(rs1!=null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
            ArrayList<CellModel> valorsCol = new ArrayList<CellModel>();
            for(int k=0; k<alumnosGrupo.size(); k++)
            {
                int idAlumno = alumnosGrupo.get(k).idAlumnos;
                float notaReal = 0f;
                if(mapaAlumnesNotes.containsKey(idAlumno))
                {
                    notaReal = mapaAlumnesNotes.get(idAlumno);
                    String style = aprovaStyle;
                    if(notaReal<5) style= suspenStyle;
                    valorsCol.add( new CellModel((Object) notaReal,style));   
                }
                else
                {
                    valorsCol.add( new CellModel((Object) 0f,npStyle));
                }
                
                if(notaReal>5) aproven += 1;
                mitjana += notaReal;
                sigma += notaReal*notaReal;
                sumen += 1;    
            }
            if(sumen>0)
            {
                aproven = 100*aproven/(1f*sumen);
                mitjana /= (1f*sumen);
                sigma = (float) Math.sqrt( sigma/(1f*sumen) - mitjana*mitjana);    
            }
             valorsCol.add(new CellModel( (Object) round(aproven,2), summaryStyle));
             valorsCol.add(new CellModel( (Object) round(mitjana,2), summaryStyle));
             valorsCol.add(new CellModel( (Object) round(sigma,2), summaryStyle));
           
            dt.addColumn(valorsCol);
        
        }
         
         //Calcula la nota de l'avaluacio amb els percentatges dels conceptes
        newCol = new ArrayList<CellModel>();
        
        float aproven = 0f;
        float mitjana = 0f;
        float sigma = 0f;
        int sumen = 0;
            
        for(int i=0; i<alumnosGrupo.size(); i++)
        {
            float nota = 0f;
         
            for(int j=0; j<conceptosClase.size(); j++)
            {
                int tpc = conceptosClase.get(j).porcentaje;
                nota += tpc*((Number) dt.getCellAt(i,j+1).getValue() ).floatValue();
            }
            
            if(sumatpc>0) nota = nota/(1f*sumatpc);
            
            String style=aprovaStyle;
             
             float mitja = round(nota, 2);
                     
             if(mitja<5) style = suspenStyle;
             newCol.add( new CellModel ((Object) mitja, style) );
             
             sumen +=1;
             mitjana += nota;
             sigma += nota*nota;
             if(nota>=5) aproven += 1;
        }
        
         if(sumen>0)
            {
                aproven = 100*aproven/(1f*sumen);
                mitjana /= (1f*sumen);
                sigma = (float) Math.sqrt( sigma/(1f*sumen) - mitjana*mitjana);    
            }
        
         newCol.add(new CellModel( (Object) round(aproven,2), summaryStyle));
         newCol.add(new CellModel( (Object) round(mitjana,2), summaryStyle));
         newCol.add(new CellModel( (Object) round(sigma,2), summaryStyle));
        
         dt.addColumn(newCol);
        
        return dt;
    }
    
     /**
     * Arrodoneix un nombre al numero de posicions decimals indicades
     * @param value
     * @param decimals
     * @return 
     */
 
    public float round(float value, int decimals)
    {
        double pow = Math.pow(10.0, decimals*1.0);
        return (float) (Math.round( value* pow ) / pow);
    }

    /**
     * Troba la condicio sobre les id's que pertanyen a un concepte determinat
     * Si es el global idConcepto = -1; aleshores agafa totes les activititats
     * i fa un not in
     * @param id
     * @return 
     */
    private String getIdActivities(final ArrayList<BeanConceptos> listConceptos, final int idConcepto) {
        String condition = " AND ac.id IN  (";
        String allConceptos = "";
        if(idConcepto ==-1)
        {
             for(int i=1; i<listConceptos.size(); i++)
             {
                allConceptos += listConceptos.get(i).id+",";
             }
             if(!allConceptos.equals(""))
             {
                 allConceptos = " ("+StringUtils.BeforeLast(allConceptos, ",") + ")";
                 condition = " AND ac.id NOT IN  (";
             }
             else
             {
                 allConceptos = " (-1) ";
                 condition = "";
             }
             
        }
        
        String SQL1 = "SELECT idActividad FROM "+client.getPlusDbName()+"sgd_conceptosactividades where idConcepto="+idConcepto;
        if(idConcepto ==-1) {
            SQL1 = "SELECT idActividad FROM "+client.getPlusDbName()+"sgd_conceptosactividades where idConcepto IN "+allConceptos;
        }
        
        //System.out.println("Select per trobar les activitats:: "+SQL1);
        
        String extracond="";
         try {
            Statement st = client.getPlusDb().createStatement();
            ResultSet rs1 = client.getPlusDb().getResultSet(SQL1,st);        

            while(rs1!=null && rs1.next())
            {
                extracond += rs1.getInt("idActividad")+",";
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!extracond.equals(""))
        {
            condition += StringUtils.BeforeLast(extracond, ",")+") ";
        }        
        else
        {
            condition = "";
            if(idConcepto>0) condition +="-1) ";
        }
        
        return condition;
    }


     /**
     * Cal evitar que un professor posi el mateix dia al mateix grup
     * dues activitats amb la mateixa descripcio, aixo dona un problema
     * a l'hora de carregar-les.
     * @param ac
     * @return 
     */
    public boolean checkValidityActivity(final BeanActividadClase ac) {
        boolean valid = true;
        
        //No es permeten activitats amb descripcio en blanc
        if(ac.getDescripcion().trim().equals("")) return false;
        
        //Aqui hi ha hagut un error intern; no ha trobat els grupasig que componen la clase
        if(ac.getMapGrupAct().isEmpty()) return false;
        
        String condition = "";
        String selfExclusion = "";
        
        for(int idgrup: ac.getMapGrupAct().keySet())
        {
            condition += idgrup+",";
            selfExclusion += ac.getMapGrupAct().get(idgrup)+", ";
        }
        condition = StringUtils.BeforeLast(condition, ",");
        selfExclusion = StringUtils.BeforeLast(selfExclusion, ",");
        
        String SQL1 = "SELECT * FROM actividades WHERE idProfesores="+ac.getIdProfesores()+
                " AND idGrupAsig IN ("+condition+") AND descripcion=? AND fecha=? AND "
                + " id NOT IN ("+selfExclusion+")";
    
         try {
            PreparedStatement st = getSgd().createPreparedStatement(SQL1);
            ResultSet rs1 = getSgd().getPreparedResultSet(new Object[]{ac.descripcion, ac.fecha},st);        

            if(rs1!=null && rs1.next())
            {
                valid = false;
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadesCollection.class.getName()).log(Level.SEVERE, null, ex);
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