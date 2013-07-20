/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clases;

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
import org.iesapp.clients.sgd7.profesores.BeanFaltasProfesores;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;

/**
 *
 * @author Josep
 */
public class ClasesCollection implements IClientController {
    private final IClient client;
    
    public ClasesCollection()
    {
        this.client = null;
    }
    
    public ClasesCollection(IClient client)
    {
        this.client = client;
    }
    
    /**
     * 
     * @param idProfesor
     * @return  
     */
    public ArrayList<BeanClase> getClasesProfe(String idProfesor)
    {      
        return new Clases(idProfesor,-1, client).getHorario();
    }

    public  int findIdConceptoEvaluable(String grupo) {
        int id = 0;
        String nivel="";
        String estudis="";
 
        if(grupo.contains("1")) {
            nivel="1r";
        }
        else if(grupo.contains("2")) {
            nivel="2n";
        }
        else if(grupo.contains("3")) {
            nivel="3r";
        }
        else if(grupo.contains("4")) {
            nivel="4t";
        }
        
        if(grupo.toUpperCase().contains("ESO")) {
            estudis="ESO";
        }
        else if(grupo.toUpperCase().contains("BAT")) {
            estudis="BAT";
        }
       
        String SQL1 = "Select id from tiponota where descripcion like '%"+nivel+"%"+estudis+"%'";
        try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            if(rs1!=null && rs1.next())
            {
                id = rs1.getInt("id");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
}
    
    //Obté una llista de clases de guardia
    //Omple el bean clase guardia i a mes crea un beanFaltaProfe
    public  ArrayList<BeanClaseGuardia> getSubstitucionesByProfeGuardia(final MyDatabase sgd,
            final int idDias, final java.util.Date fecha, final String idProfesores2)
    {
        ArrayList<BeanClaseGuardia> list = new ArrayList<BeanClaseGuardia>();
        
        String SQL1 = "SELECT DISTINCT "
                + " horascentro.inicio, "
                + " horascentro.fin, "
                + " clases.nombre AS grupo, "
                + " aulas.descripcionLarga AS aula, "
                + " clases.id AS idClase, "
                + " horarios.idHorasCentro, "
                + " horarios.idProfesores, "
                + " profesores.nombre, "
                + " fp.idProfesores2, "
                + " fp.idTipoIncidencias, "
                + " fp.observaciones,  "
                + " fp.fecha,  "
                + " fp.hora,  "
                + " ti.descripcion,  "
                + " ti.simbolo  "
                + " FROM "
                + " horarios  "
                + " INNER JOIN "
                + " profesores  "
                + " ON profesores.id = horarios.idProfesores  "
                + " LEFT JOIN "
                + " aulas  "
                + " ON (horarios.idAulas = aulas.id)  "
                + " LEFT JOIN "
                + " horascentro  "
                + " ON ( "
                + "   horarios.idHorascentro = horascentro.id "
                + " )  "
                + " INNER JOIN "
                + " clases  "
                + " ON (horarios.idClases = clases.id)  "
                + " LEFT JOIN "
                + " clasesdetalle AS cd  "
                + " ON cd.idClases = clases.id  "
                + " LEFT JOIN "
                + " grupasig AS ga  "
                + " ON ga.id = cd.idGrupasig  "
                + " LEFT JOIN "
                + " asignaturas AS asig  "
                + " ON asig.id = ga.idAsignaturas  "
                + " INNER JOIN "
                + " faltasprofesores AS fp "
                + " ON (fp.idProfesores=horarios.idProfesores "
                + " AND fp.idHorasCentro=horarios.idHorascentro AND cd.idGrupasig=fp.idGrupAsig) "
                + " LEFT JOIN "
                + " tipoincidencias as ti"
                + " ON ti.id=fp.idTipoIncidencias "
                + " WHERE (horarios.idDias="+idDias+" AND fp.fecha='"+new DataCtrl(fecha).getDataSQL()+"'  "
                + " AND idProfesores2="+idProfesores2+"   )"
                + " ORDER BY inicio, profesores.nombre ";

         
        try {
             Statement st = sgd.createStatement();
             ResultSet rs1 = sgd.getResultSet(SQL1,st);
         while(rs1!=null && rs1.next())
         {
             //Construeix el bean base
             BeanClaseGuardia bh = new BeanClaseGuardia();
             bh.setAula(rs1.getString("aula"));
             bh.setInicio(rs1.getTime("inicio"));
             bh.setFin(rs1.getTime("fin"));
             bh.setGrupo(rs1.getString("grupo"));
             int idClase = rs1.getInt("idClase");
             bh.setIdClase(idClase);
             bh.setIdHorasCentro(rs1.getInt("idHorasCentro"));
             //bh.setMateria(""); //no aplica
             bh.setIdProfesor(rs1.getString("idProfesores"));
             bh.setNombreProfesor(rs1.getString("nombre"));
             
             //Aqui carrega la informacio del BeanFaltasProfesores
             BeanFaltasProfesores beanfp = new BeanFaltasProfesores();
             
             beanfp.setIdTipoIncidencias(rs1.getInt("idTipoIncidencias"));
             beanfp.setObservaciones(rs1.getString("observaciones"));
             beanfp.setDescripcion(rs1.getString("descripcion"));
             beanfp.setSimbolo(rs1.getString("simbolo"));
             beanfp.setIdProfesores2(rs1.getString("idProfesores2"));
             beanfp.setIdProfesores(rs1.getString("idProfesores"));
             beanfp.setFecha(rs1.getDate("fecha"));
             beanfp.setHora(rs1.getTime("hora"));
             beanfp.setIdHorasCentro(rs1.getInt("idHorasCentro"));
             
             bh.setBeanFProf(beanfp);
             list.add(bh);
         }
          if(rs1!=null){
              rs1.close();
              st.close();
          }
        } catch (SQLException ex) {
            Logger.getLogger(ClasesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return list;
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
