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
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;

/**
 *
 * @author Josep
 */
public final class ClasesGuardia implements IClientController{
 
    private int idDiaSetmana = 0;
    private ArrayList<BeanClaseGuardia> horario;
    private final int idHoraCentro;
    private final IClient client;
    
    public ClasesGuardia(int idDiaSetmana, int idHoraCentro)
    {
       this.client = null;
       this.idDiaSetmana = idDiaSetmana;
       this.idHoraCentro = idHoraCentro;
       horario = this.load();
    }
    
    public ClasesGuardia(int idDiaSetmana, int idHoraCentro, IClient client)
    {
       this.client = client;
       this.idDiaSetmana = idDiaSetmana;
       this.idHoraCentro = idHoraCentro;
       horario = this.load();
    }
    
    private ArrayList<BeanClaseGuardia> load()
    {       
        ArrayList<BeanClaseGuardia> listbh = new ArrayList<BeanClaseGuardia>();
        
        String conditionDia="";
        String groupCondition="";
        if(this.idDiaSetmana>0) //mostra les clases del dia ordenades
        {
            conditionDia= "horarios.idDias="+this.idDiaSetmana+" AND horarios.idHorasCentro="+this.idHoraCentro+" ";
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
                + "  asig.descripcion,  "
                + " horarios.idProfesores,"
                + " profesores.nombre "
                + " FROM "
                + "  horarios  "
                + " INNER JOIN profesores ON profesores.id = horarios.idProfesores "
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
                + " WHERE (  "
                + conditionDia
                + "   )  "
                + groupCondition
                + " ORDER BY profesores.nombre ";
        
      //  System.out.println(SQL1);
        
          
        int id= 1;
        try {
            Statement st = getSgd().createStatement();
            ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while( rs1!=null && rs1.next() )
            { 
               BeanClaseGuardia bh = new BeanClaseGuardia();
               bh.setAula(rs1.getString("aula"));
               bh.setInicio(rs1.getTime("inicio"));
               bh.setFin(rs1.getTime("fin"));
               bh.setGrupo(rs1.getString("grupo"));
               int idClase = rs1.getInt("idClase");
               bh.setIdClase(idClase);
               bh.setIdHorasCentro(rs1.getInt("idHorasCentro"));
               bh.setMateria(rs1.getString("descripcion"));
               
               bh.setIdProfesor(rs1.getString("idProfesores"));
               bh.setNombreProfesor(rs1.getString("nombre"));
               //Cada clase té aquest array de idGrupAsigs
               //Ho farà a posteriori (nomes quan es necessiti)
               //bh.idGrupAsigs = ActividadesCollection.getGrupAsigInClass(idProfesor, idClase);
               
               listbh.add(bh);
               id +=1;
            }
            if(rs1!=null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasesGuardia.class.getName()).log(Level.SEVERE, null, ex);
        }
         
  
        return listbh;
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
    public ArrayList<BeanClaseGuardia> getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(ArrayList<BeanClaseGuardia> horario) {
        this.horario = horario;
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
