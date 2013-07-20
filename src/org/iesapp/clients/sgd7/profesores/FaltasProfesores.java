/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.profesores;

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
import org.iesapp.clients.sgd7.clases.BeanClaseGuardia;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;

/**
 * org.iesapp.clients.sgd7.base.TipoIncidencias.getID(String codigo, String tipo)
 * codigo="F" and tipo="guardia"
 * 
 * @author Josep
 */
public class FaltasProfesores extends BeanFaltasProfesores implements IClientController {
    private final IClient client;
    
    //DEFAULT CONSTRUCTOR
    public FaltasProfesores()
    {      
        this.client = null;
    }
    
//    //CONSTRUCTOR FROM BEANCLASEGUARDIA
//    public FaltasProfesores(final BeanClaseGuardia bcg, final String idProfesor2, 
//                            final String comentario, java.util.Date dia,
//                            int idTipoIncidencias, String simbolo, String descripcion, IClient client)
//    {
//        this.client = client;
//        this.idProfesores = bcg.getIdProfesor(); //id profesor que falta
//        int idClase = bcg.getIdClase();
//        ArrayList<Integer> grupAsigInClass = client.getActividadesCollection().getGrupAsigInClass(idProfesores, idClase);
//        
//        for(int i=0; i<grupAsigInClass.size(); i++)
//        {
//            this.mapIdGrupAsigs.put(grupAsigInClass.get(i),-1);
//        }
//        System.out.println(mapIdGrupAsigs.toString());
//        
//        this.fecha = new java.sql.Date(dia.getTime());
//        this.hora = null;
//        this.idHorasCentro = bcg.getIdHorasCentro();
//        this.idProfesores2 = idProfesor2; //id del profesor de guardia
//        this.idTipoIncidencias = idTipoIncidencias;
//        this.simbolo = simbolo;
//        this.observaciones = comentario;
//        this.descripcion = descripcion;
//    }
    
    //DEFAULT CONSTRUCTOR
    public FaltasProfesores(IClient client)
    {      
        this.client = null;
    }
    
    //CONSTRUCTOR FROM BEANCLASEGUARDIA
    public FaltasProfesores(final BeanClaseGuardia bcg, final String idProfesor2, 
                            final String comentario, java.util.Date dia,
                            int idTipoIncidencias, String simbolo, String descripcion, IClient client)
    {
        this.client = client;
        this.idProfesores = bcg.getIdProfesor(); //id profesor que falta
        int idClase = bcg.getIdClase();
        ArrayList<Integer> grupAsigInClass;
        if(client!=null)
        {
            grupAsigInClass = client.getActividadesCollection().getGrupAsigInClass(idProfesores, idClase);
        }
        else
        {
             throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        
        for(int i=0; i<grupAsigInClass.size(); i++)
        {
            this.mapIdGrupAsigs.put(grupAsigInClass.get(i),-1);
        }
        System.out.println(mapIdGrupAsigs.toString());
        
        this.fecha = new java.sql.Date(dia.getTime());
        this.hora = null;
        this.idHorasCentro = bcg.getIdHorasCentro();
        this.idProfesores2 = idProfesor2; //id del profesor de guardia
        this.idTipoIncidencias = idTipoIncidencias;
        this.simbolo = simbolo;
        this.observaciones = comentario;
        this.descripcion = descripcion;
    }
    
    public BeanFaltasProfesores getBean()
    {
        return (BeanFaltasProfesores) this;
    }
    
    public void setBean(BeanFaltasProfesores bean)
    {
        this.idProfesores = bean.idProfesores; //id profesor que falta
        this.mapIdGrupAsigs = bean.mapIdGrupAsigs;
        this.fecha = bean.fecha;
        this.hora = bean.hora;
        this.idHorasCentro = bean.idHorasCentro;
        this.idProfesores2 = bean.idProfesores2; //id del profesor de guardia
        this.idTipoIncidencias = bean.idTipoIncidencias;
        this.simbolo = bean.simbolo;
        this.observaciones = bean.observaciones;
        this.descripcion = bean.descripcion;

    }
    
    
    
    public void loadByProfeFalta(String idProfesor, java.util.Date fecha, int idHorasCentro)
    {
        this.idProfesores = idProfesor;
        this.fecha = new java.sql.Date(fecha.getTime());
        this.idHorasCentro = idHorasCentro;
        this.mapIdGrupAsigs.clear();
        String SQL1 = "SELECT fp.*, ti.simbolo, ti.descripcion FROM faltasprofesores as fp left join tipoincidencias as ti on fp.idTipoIncidencias=ti.id"
                + " WHERE idProfesores='"+idProfesores+"'"+
                " AND fecha='"+new DataCtrl(fecha).getDataSQL()+"' AND idHorasCentro="+idHorasCentro;
        
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                mapIdGrupAsigs.put(rs1.getInt("idGrupAsig"),rs1.getInt("id"));
                hora = rs1.getTime("hora");
                idProfesores2 = rs1.getString("idProfesores2");
                idTipoIncidencias = rs1.getInt("idTipoIncidencias");
                observaciones = rs1.getString("observaciones");
                simbolo = rs1.getString("simbolo");
                descripcion = rs1.getString("descripcion");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltasProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadByProfeGuardia(String idProfesor2, java.util.Date fecha, int idHorasCentro)
    {
        this.idProfesores2 = idProfesor2;
        this.fecha = new java.sql.Date(fecha.getTime());
        this.idHorasCentro = idHorasCentro;
        this.mapIdGrupAsigs.clear();
        
        //Problema que passa si a una mateixa hora // fa més d'una substitucio
        //Puc mesclar idGrupAsigs de diverses clases...
        //He d'associar el idGrupAsigs a idClase
        String SQL1 = "SELECT fp.*, ti.simbolo, ti.descripcion FROM faltasprofesores as fp left join tipoincidencias as ti on fp.idTipoIncidencias=ti.id"
                + " WHERE idProfesores2='"+idProfesores2+"'"+
                " AND fecha='"+new DataCtrl(fecha).getDataSQL()+"' AND idHorasCentro="+idHorasCentro;
        
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                mapIdGrupAsigs.put(rs1.getInt("idGrupAsig"),rs1.getInt("id"));
                hora = rs1.getTime("hora");
                idProfesores = rs1.getString("idProfesores");
                idTipoIncidencias = rs1.getInt("idTipoIncidencias");
                observaciones = rs1.getString("observaciones");
                simbolo = rs1.getString("simbolo");
                descripcion = rs1.getString("descripcion");
            }
            if(rs1!=null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltasProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public int save()
    {
        int nup = 0;
        
        for(int idGrupAsig: mapIdGrupAsigs.keySet())
        {
            int id = mapIdGrupAsigs.get(idGrupAsig);
            if(id<=0)
            {
                //insert
                String SQL1 = "";
                Object[] obj = null;
                if(this.hora==null)
                {
                    SQL1 = "INSERT INTO faltasprofesores (idProfesores,idGrupAsig,fecha,hora,idHorasCentro,idProfesores2,idTipoIncidencias,observaciones) "
                        + " VALUES(?,?,?,CURRENT_TIME(),?,?,?,?)";
                   obj= new Object[]{idProfesores,idGrupAsig,fecha,idHorasCentro,idProfesores2,idTipoIncidencias,observaciones};
              
                }
                else
                {
                    SQL1 = "INSERT INTO faltasprofesores (idProfesores,idGrupAsig,fecha,hora,idHorasCentro,idProfesores2,idTipoIncidencias,observaciones) "
                        + " VALUES(?,?,?,?,?,?,?,?)";
                  obj= new Object[]{idProfesores,idGrupAsig,fecha,hora,idHorasCentro,idProfesores2,idTipoIncidencias,observaciones};
              
                }
                int newid = getSgd().preparedUpdateID(SQL1, obj);
                
                //Elimina l'entrada
                if(newid>0)
                {
                    nup +=1;
                    mapIdGrupAsigs.put(idGrupAsig, newid);
                    
                    
                     {
                        Log log = getLogger();
                        log.setTabla("FaltasProfesores");
                        log.setDatos("id=" + newid + ";");
                        log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                        log.setSentenciaSQL(getSgd().getLastPstm());
                        log.postLog();
                        log = null;
                    }
                }
            }
            else
            {
                //update
                String SQL1 = "";
                Object[] obj = null;
                if(this.hora==null)
                {
                    SQL1 = "UPDATE faltasprofesores SET idProfesores=?, idGrupAsig=?, fecha=?, hora=CURRENT_TIME(), idHorasCentro=?, idProfesores2=?, "
                        + " idTipoIncidencias=?, observaciones)=? WHERE id="+id;
                        
                    obj = new Object[]{idProfesores,idGrupAsig,fecha,idHorasCentro,idProfesores2,idTipoIncidencias,observaciones};
                }
                else
                {
                    SQL1 = "UPDATE faltasprofesores SET idProfesores=?, idGrupAsig=?, fecha=?, hora=?, idHorasCentro=?, idProfesores2=?, "
                        + " idTipoIncidencias=?, observaciones=? WHERE id="+id;
                        
                    obj = new Object[]{idProfesores,idGrupAsig,fecha,hora,idHorasCentro,idProfesores2,idTipoIncidencias,observaciones}; 
                }
                
                int newid = getSgd().preparedUpdate(SQL1, obj);
                
           
                if(newid>0)
                {
                    nup +=1;
                     {
                        Log log = getLogger();
                        log.setTabla("FaltasProfesores");
                        log.setDatos("id=" + id + ";");
                        log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
                        log.setSentenciaSQL(getSgd().getLastPstm());
                        log.postLog();
                        log = null;
                    }
                }
            }
        }
        
        return nup;
    }
    
    public int delete()
    {
        int nup =0;
        for(int idGrupAsig: mapIdGrupAsigs.keySet())
        {
            int id = mapIdGrupAsigs.get(idGrupAsig);
            if(id>0)
            {
                String SQL1 = "DELETE FROM faltasprofesores WHERE id="+id+" LIMIT 1;";
                int success = getSgd().executeUpdate(SQL1);
                nup += success;
                if ( success>0) {
                    Log log = getLogger();
                    log.setTabla("FaltasProfesores");
                    log.setDatos("id=" + id + ";");
                    log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
                    log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                    log.postLog();
                    log = null;
                }
            }
        }
        return nup;
    }
    
    
    public void loadInfoClaseGuardia()
    {
        //TODO
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
