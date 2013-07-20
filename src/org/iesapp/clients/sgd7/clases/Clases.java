/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

import org.iesapp.database.MyDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.actividades.ActividadesCollection;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public final class Clases implements IClientController{
    
    private String idProfesor = "";
    private int idDiaSetmana = 0;
    private ArrayList<BeanClase> horario;
    private final IClient client;
    
    public Clases(String idProfesor, int idDiaSetmana)
    {
       this.client = null;
       this.idProfesor = idProfesor;
       this.idDiaSetmana = idDiaSetmana;
    }
    
    public Clases(String idProfesor)
    {
       this.client = null;
       this.idProfesor = idProfesor;
       this.idDiaSetmana = 0;
    }
    
    public Clases(String idProfesor, int idDiaSetmana, IClient client)
    {
       this.client = client;
       this.idProfesor = idProfesor;
       this.idDiaSetmana = idDiaSetmana;
    }
    
    public Clases(String idProfesor, IClient client)
    {
       this.client = client;
       this.idProfesor = idProfesor;
       this.idDiaSetmana = 0;
       
    }
    
    private ArrayList<BeanClase> load(boolean now)
    {       
        ArrayList<BeanClase> listbh = new ArrayList<BeanClase>();
        
        String conditionDia="";
        String groupCondition="";
        if(this.idDiaSetmana>0) //mostra les clases del dia ordenades
        {
            conditionDia= " AND horarios.idDias = "+this.idDiaSetmana+" ";
            if(now)
            {
                conditionDia += " AND horascentro.inicio<CURRENT_TIME() AND horascentro.fin>CURRENT_TIME() "; 
            }
 
            groupCondition = " GROUP BY idHorasCentro, clases.id  ";
        }
        else if(this.idDiaSetmana<0) //mostra les clases de tota la setmana (sense repetir) llevant la tutoria
        {
            conditionDia = " AND asig.descripcion NOT LIKE 'TUT%' ";
            groupCondition = " GROUP BY clases.id  ";
        }
//        String SQL1 = "SELECT  "+
//                "   horascentro.inicio  "+
//                "   , horascentro.fin  "+
//                "   , clases.nombre  "+
//                "   , aulas.descripcionLarga  "+
//                "   , clases.id as idClase" + 
//                "   , horarios.idHorasCentro "+
//                "   FROM  "+
//                "       horarios  "+
//                "   LEFT JOIN aulas   "+
//                "       ON (horarios.idAulas = aulas.id)  "+
//                "   LEFT JOIN horascentro   "+
//                "       ON (horarios.idHorascentro = horascentro.id)  "+
//                "   INNER JOIN clases   "+
//                "       ON (horarios.idClases = clases.id)  "+
//                "   WHERE (horarios.idProfesores ="+ this.idProfesor +
//                "    AND horarios.idDias ="+this.idDiaSetmana+")  "+
//                "   ORDER BY inicio"; 

        String SQL1 = "SELECT DISTINCT "
                + " horascentro.inicio, "
                + " horascentro.fin, "
                + " clases.nombre AS grupo, "
                + " aulas.descripcionLarga AS aula, "
                + " clases.id AS idClase, "
                + "  horarios.idHorasCentro, "
                + "  asig.descripcion  "
                + " FROM "
                + "  horarios  "
                + "  LEFT JOIN "
                + " aulas  "
                + "  ON (horarios.idAulas = aulas.id)  "
                + "  LEFT JOIN "
                + "  horascentro  "
                + "  ON ( "
                + "    horarios.idHorascentro = horascentro.id "
                + "  )  "
                + "   INNER JOIN "
                + "  clases  "
                + "  ON (horarios.idClases = clases.id)  "
                + "  LEFT JOIN "
                + "  clasesdetalle AS cd  "
                + "  ON cd.idClases = clases.id  "
                + "  LEFT JOIN "
                + "  grupasig AS ga  "
                + "  ON ga.id = cd.idGrupasig  "
                + "  LEFT JOIN "
                + "  asignaturas AS asig  "
                + "  ON asig.id = ga.idAsignaturas  "
                + " WHERE ( "
                + "    horarios.idProfesores = "+this.idProfesor+"  "
                + conditionDia
                + "   )  "
                + groupCondition
                + " ORDER BY inicio ";
        
      //  System.out.println(SQL1);
          
        int id= 1;
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);        

            while( rs1!=null && rs1.next() )
            { 
               BeanClase bh = new BeanClase();
               bh.setAula(rs1.getString("aula"));
               bh.setInicio(rs1.getTime("inicio"));
               bh.setFin(rs1.getTime("fin"));
               bh.setGrupo(rs1.getString("grupo"));
               int idClase = rs1.getInt("idClase");
               bh.setIdClase(idClase);
               bh.setIdHorasCentro(rs1.getInt("idHorasCentro"));
               bh.setMateria(rs1.getString("descripcion"));
               //Cada clase té aquest array de idGrupAsigs
               if(client!=null)
               {
                   bh.idGrupAsigs = client.getActividadesCollection().getGrupAsigInClass(idProfesor, idClase);
               }
               else
               {
                   throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
               }
               
               listbh.add(bh);
               id +=1;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Clases.class.getName()).log(Level.SEVERE, null, ex);
        }
         
  
        return listbh;
    }

    /**
     * @return the idProfesor
     */
    public String getIdProfesor() {
        return idProfesor;
    }

    /**
     * @param idProfesor the idProfesor to set
     */
    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    /**
     * @return the idDiaSetmana
     */
    public int getIdDiaSetmana() {
        return idDiaSetmana;
    }

    /**
     * @param idDiaSetmana the idDiaSetmana to set
     */
    public void setIdDiaSetmana(int idDiaSetmana) {
        this.idDiaSetmana = idDiaSetmana;
    }
    
    
    public String print()
    {
        String txt = "";
        
            switch(this.idDiaSetmana)
            {
                case 1:  txt += "**** Dilluns *****\n";  break;
                case 2:  txt += "**** Dimarts *****\n";  break;
                case 3:  txt += "**** Dimecres ****\n";  break;
                case 4:  txt += "**** Dijous ******\n";  break;
                case 5:  txt += "**** Divendres ***\n";  break;
            }
                    

        for(int i=0; i<getHorario().size(); i++)
        {
            BeanClase h = getHorario().get(i);
            txt += "Grupo="+h.getGrupo()+"; "+ 
                   "Materia="+h.getMateria()+"; "+
                   "idClase="+h.getIdClase()+"; "+
                   "idHorasCentro="+h.getIdHorasCentro()+"; "+
                    "inicio-fin="+h.getInicio()+"-"+h.getFin()+"; "+
                    "aula="+h.getAula()+"\n";
        }
        
        return txt;
    }

    @Override
    public String toString()
    {
        return print();
    }
    /**
     * @return the horario
     */
    public ArrayList<BeanClase> getHorario() {
        if(horario==null)
        {
            horario = this.load(false);
        }
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(ArrayList<BeanClase> horario) {
        this.horario = horario;
    }
    
   
    public BeanClase getCurrentClase()
    {
        BeanClase bean = null;
        ArrayList<BeanClase> load = this.load(true);
        if(!load.isEmpty())
        {
            bean = load.get(0);
        }
        return bean;
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
