/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.clasesanotadas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;

/**
 *
 * @author Josep
 */
public final class ClasesAnotadas extends BeanClasesAnotadas implements IClientController {
    private final IClient client;
    
    public ClasesAnotadas()
    {
        this.client = null;
    }
    
    public ClasesAnotadas(int id)
    {
        this.client = null;
        this.id = id;
        load();
    }
    
    public ClasesAnotadas(String idProfesores, String idProfesoresReal, int idHorasCentro, int idGrupAsig)
    {
        this.client = null;
        this.idProfesores = idProfesores;
        this.idProfesoresReal = idProfesoresReal;
        this.idHorasCentro = idHorasCentro;
        this.idGrupAsig = idGrupAsig;
    }
    
    public ClasesAnotadas(BeanClasesAnotadas bean)
    {
        this.client = null;
        this.setBean(bean);
    }
    
    public ClasesAnotadas(IClient client)
    {
        this.client = client;
    }
    
    public ClasesAnotadas(int id, IClient client)
    {
        this.client = client;
        this.id = id;
        load();
    }
    
    public ClasesAnotadas(String idProfesores, String idProfesoresReal, int idHorasCentro, int idGrupAsig, IClient client)
    {
        this.client = client;
        this.idProfesores = idProfesores;
        this.idProfesoresReal = idProfesoresReal;
        this.idHorasCentro = idHorasCentro;
        this.idGrupAsig = idGrupAsig;
    }
    
    public ClasesAnotadas(BeanClasesAnotadas bean, IClient client)
    {
        this.client = client;
        this.setBean(bean);
    }
    
    public void setBean(BeanClasesAnotadas bean)
    {
       this.id = bean.id;
       this.idProfesores = bean.idProfesores;
       this.idProfesoresReal = bean.idProfesoresReal;
       this.fecha = bean.fecha;
       this.idHorasCentro = bean.idHorasCentro;
       this.idGrupAsig = bean.idGrupAsig;
    }
    
    public BeanClasesAnotadas getBean()
    {
       BeanClasesAnotadas bean = new BeanClasesAnotadas();
       bean.id = this.id;
       bean.idProfesores = this.idProfesores;
       bean.idProfesoresReal = this.idProfesoresReal;
       bean.fecha = this.fecha;
       bean.idHorasCentro = this.idHorasCentro;
       bean.idGrupAsig = this.idGrupAsig;
       return bean;
    }
    
    private void load()
    {
        if(this.id<=0) return;
        
        String SQL1 = "SELECT * FROM clasesanotadas WHERE id="+this.id;
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                this.fecha = rs1.getDate("fecha");
                this.idGrupAsig = rs1.getInt("idGrupAsig");
                this.idHorasCentro = rs1.getInt("idHorasCentro");
                this.idProfesores = rs1.getString("idProfesores");
                this.idProfesoresReal = rs1.getString("idProfesoresReal");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasesAnotadas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int delete()
    {
        if(this.id<=0) {
            return 0;
        }
        String SQL1 = "DELETE FROM clasesanotadas WHERE id="+this.id+" LIMIT 1;";
        int nup = getSgd().executeUpdate(SQL1);
        if ( nup > 0) {
            Log log = getLogger();
            log.setTabla("ClasesAnotadas");
            log.setDatos("id=" + this.id + ";");
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.DELETE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        return nup;
    }
    
    
    public boolean exists()
    {
        boolean exists=false;
        if(this.id<=0) return false;
        
          String SQL1 = "SELECT * FROM clasesanotadas WHERE id="+this.id;
         try {
             Statement st = getSgd().createStatement();
             ResultSet rs1 = getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
               exists = true;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasesAnotadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;
    }
    
     public int save()
    {
        int nup = 0;
        if(this.id<=0 || !this.exists()) 
        {
            nup = insert();
        }
        else
        {
            nup = update();
        }
        
        return nup;
    }

    private int insert() {
        
        String strfecha = fecha==null? "'"+new DataCtrl().getDataSQL()+"' ":"'"+new DataCtrl(fecha).getDataSQL()+"' ";
        
        String SQL1 = "INSERT INTO clasesanotadas (idProfesores,idProfesoresReal,"
                + " fecha,idHorasCentro,idGrupAsig) VALUES('"+this.idProfesores+"',"
                + "'"+this.idProfesoresReal+"',"+strfecha+",'"+this.idHorasCentro+"',"
                + " '"+this.idGrupAsig+"')";
        int nup = getSgd().executeUpdateID(SQL1);
        if(nup>0) 
        {
            this.id =nup;
            
            {
                Log log = getLogger();
                log.setTabla("ClasesAnotadas");
                log.setDatos("id="+this.id+";id="+this.id);
                log.setTipo(org.iesapp.clients.sgd7.logger.Log.INSERT);
                log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
                log.postLog();
                log = null;
            }
        }
        return nup;
    }

    private int update() {
        String strfecha = fecha==null? "NULL":"'"+new DataCtrl(fecha).getDataSQL()+"' ";
        
        String SQL1 = "UPDATE clasesanotadas SET idProfesores='"+this.idProfesores+
                "', idProfesoresReal='"+this.idProfesoresReal+"',fecha="+strfecha+
                " ,idHorasCentro='"+this.idHorasCentro+"' ,idGrupAsig='"+this.idGrupAsig+
                "' WHERE id="+this.id;
        int nup = getSgd().executeUpdate(SQL1);
        
        if ( nup>0) {
            Log log = getLogger();
            log.setTabla("ClasesAnotadas");
            log.setDatos("id=" + this.id + ";id=" + this.id);
            log.setTipo(org.iesapp.clients.sgd7.logger.Log.UPDATE);
            log.setSentenciaSQL(org.iesapp.clients.sgd7.logger.Log.normalizeSQL(SQL1));
            log.postLog();
            log = null;
        }
        return nup;
    }
     
    public String print()
    {
        return "clasesanotadas:: id="+this.id+"; "+
             "idProfesores="+this.idProfesores + "; "+
             "idProfesoresReal="+this.idProfesoresReal + "; "+
             "fecha="+this.fecha+ "; "+
             "idHorasCentro="+this.idHorasCentro + "; "+
             "idGrupAsig="+this.idGrupAsig;
    }
    
    @Override
    public String toString()
    {
        return print();
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
