/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import java.util.ArrayList;
import java.util.HashMap;
import org.iesapp.clients.sgd7.IClient;
import org.iesapp.clients.sgd7.IClientController;
import org.iesapp.clients.sgd7.base.BeanController;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.clases.BeanClase;
import org.iesapp.clients.sgd7.logger.Log;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.StringUtils;


/**
 * Extensio de Actividad
 * te un objecte que es un mapa, 
 * idGrupAsig amb l'activitat associada
 * 
 * @author Josep
 */
public final class ActividadClase extends BeanActividadClase implements BeanController, IClientController {
    private final IClient client;

    
    /**
     * Nova actividadClase sabent els idGrupAsigs que s'assigna l'activitat
     * @param listIds 
     */        
    public ActividadClase(String idProfesores, int idClase, ArrayList<Integer> listGrupAsig)
    {
        this.client = null;
        this.idClase = idClase;
        this.idProfesores = idProfesores;
        
        for(int i=0; i<listGrupAsig.size(); i++)
        {
            mapGrupAct.put(listGrupAsig.get(i), -1);
        }
    }

    
    public ActividadClase(String idProfesores, BeanClase bean)
    {
        this.client = null;
        this.idClase = bean.getIdClase();
        this.idProfesores = idProfesores;
              
        for(int i=0; i<bean.getIdGrupAsigs().size(); i++)
        {
            mapGrupAct.put(bean.getIdGrupAsigs().get(i), -1);
        }
    }
    
    /**
     * Nova actividadClase proporcionant la id de la clase
     * @param listIds 
     */        
    public ActividadClase(String idProfesores, int idClase)
    {
        this.client = null;
        this.idClase = idClase;
        this.idProfesores = idProfesores;
        ArrayList<Integer> ga;
        if(client!=null)
        {
            ga = client.getActividadesCollection().getGrupAsigInClass(idProfesores, idClase);
        }
        else
        {
            throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        for(int i=0; i<ga.size() ; i++)
        {
            mapGrupAct.put(ga.get(i), -1);
        }
    }
    
    public ActividadClase(HashMap<Integer,Integer> mapa)
    {    
        this.client = null;
        this.mapGrupAct = mapa;
               
    }
   
    
    public ActividadClase(BeanActividadClase bean)
    {    
       this.client = null;
       setBean(bean);               
    }
    
    
    /**
     * Nova actividadClase sabent els idGrupAsigs que s'assigna l'activitat
     * @param listIds 
     */        
    public ActividadClase(String idProfesores, int idClase, ArrayList<Integer> listGrupAsig, IClient client)
    {
        this.client = client;
        this.idClase = idClase;
        this.idProfesores = idProfesores;
        
        for(int i=0; i<listGrupAsig.size(); i++)
        {
            mapGrupAct.put(listGrupAsig.get(i), -1);
        }
    }

    
    public ActividadClase(String idProfesores, BeanClase bean, IClient client)
    {
        this.client = client;
        this.idClase = bean.getIdClase();
        this.idProfesores = idProfesores;
              
        for(int i=0; i<bean.getIdGrupAsigs().size(); i++)
        {
            mapGrupAct.put(bean.getIdGrupAsigs().get(i), -1);
        }
    }
    
    /**
     * Nova actividadClase proporcionant la id de la clase
     * @param listIds 
     */        
    public ActividadClase(String idProfesores, int idClase, IClient client)
    {
        this.client = client;
        this.idClase = idClase;
        this.idProfesores = idProfesores;
         ArrayList<Integer> ga;
         if(client!=null)
        {
            ga = client.getActividadesCollection().getGrupAsigInClass(idProfesores, idClase);
        }
        else
        {
             throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        for(int i=0; i<ga.size() ; i++)
        {
            mapGrupAct.put(ga.get(i), -1);
        }
    }
    
    public ActividadClase(HashMap<Integer,Integer> mapa, IClient client)
    {    
        this.client = client;
        this.mapGrupAct = mapa;
               
    }
   
    
    public ActividadClase(BeanActividadClase bean, IClient client)
    {    
       this.client = client;
       setBean(bean);               
    }
    
    
    
    
    public void setBean(BeanActividadClase bean)
    {
        this.mapGrupAct = bean.mapGrupAct;
        
        this.descripcion = bean.descripcion;
        this.evaluable = bean.evaluable;
        this.fecha = bean.fecha;
        this.idConceptosEvaluables = bean.idConceptosEvaluables;
        this.idEvaluacionesDetalle = bean.idEvaluacionesDetalle;
        this.idProfesores = bean.idProfesores;
        this.ordre = bean.ordre;
        this.peso = bean.peso;
        this.publicarWEB = bean.publicarWEB;
        this.seguimiento = bean.seguimiento;
        this.idClase = bean.idClase;
        //Cal clonar
        this.assignacions = new ArrayList<BeanActividadesAlumno>();
        for(BeanActividadesAlumno baa : bean.assignacions)
        {
            this.assignacions.add(new BeanActividadesAlumno(baa));    
        }
        
        this.concepto = bean.concepto;
        this.totalasig = bean.totalasig;
    }
    
     public BeanActividadClase getBean() {
         
        BeanActividadClase bean = new BeanActividadClase();
        bean.mapGrupAct = this.mapGrupAct;
        
        bean.descripcion = this.descripcion;
        bean.evaluable = this.evaluable;
        bean.fecha = this.fecha;
        bean.idConceptosEvaluables = this.idConceptosEvaluables;
        bean.idEvaluacionesDetalle = this.idEvaluacionesDetalle;
        bean.idProfesores = this.idProfesores;
        bean.ordre = this.ordre;
        bean.peso = this.peso;
        bean.publicarWEB = this.publicarWEB;
        bean.seguimiento = this.seguimiento;       
        bean.idClase = this.idClase;    
        //cal clonar
        bean.assignacions = new ArrayList<BeanActividadesAlumno>();
        for(BeanActividadesAlumno baa : this.assignacions)
        {
            bean.assignacions.add(new BeanActividadesAlumno(baa));    
        }
        
        bean.concepto = this.concepto;
        bean.totalasig = this.totalasig;
        
        return bean;
     }

   
    
    @Override
    public int save()
    {
        int nup = 1;
        Actividad act = new Actividad((BeanActividadClase) this, client);
        
        for(int idgrupasig: this.mapGrupAct.keySet())
        {
            int oldid = this.mapGrupAct.get(idgrupasig);
            act.id = oldid;
            act.idGrupAsig = idgrupasig;
            int saved = act.save();
            nup *= saved;
            if(oldid<=0)
            {
                mapGrupAct.put(idgrupasig, act.getId());
            }
        }
        
        return nup;
    }
    
    public int saveConceptesOnly()
    {
        int nup = 1;
        Actividad act = new Actividad((BeanActividadClase) this, client);
        
        for(int idgrupasig: this.mapGrupAct.keySet())
        {
            int oldid = this.mapGrupAct.get(idgrupasig);
            act.id = oldid;
            act.idGrupAsig = idgrupasig;
            int saved = act.saveConcepto();
            nup *= saved;
            if(oldid<=0)
            {
                mapGrupAct.put(idgrupasig, act.getId());
            }
        }
        
        return nup;
    }
    
    public int savePesosOnly()
    {
        int nup = 1;
        Actividad act = new Actividad((BeanActividadClase) this, client);
        
        for(int idgrupasig: this.mapGrupAct.keySet())
        {
            int oldid = this.mapGrupAct.get(idgrupasig);
            act.id = oldid;
            act.idGrupAsig = idgrupasig;
            int saved = act.savePeso();
            nup *= saved;
            //Actualiza la id en cas d'haver-se creada de nou
            if(oldid<=0)
            {
                mapGrupAct.put(idgrupasig, act.getId());
            }
        }
        
        return nup;
    }
    
     /*
     * Desa nomes les assignacions que difereixen de l'actual respecte orig
     * per tant, assignacions que no hagin estat editades o canviades no seran
     * updated
     */
     public void saveAssignacions(ActividadClase orig)
     {
          for(int i=0; i<this.assignacions.size(); i++)
          {
            BeanActividadesAlumno asgn = assignacions.get(i);
            BeanActividadesAlumno asgn_orig = orig.getAssignacions().get(i);
            
            //System.out.println("assignacions "+asgn.getNombre()+" "+asgn.selected+" "+asgn.getId()+" "+asgn.getIdActividades()+" "+asgn.getIdGrupAsig());
            
            //Comprova si té id assignada
            if(asgn.getId()<=0)
            {
                if(mapGrupAct.containsKey( asgn.getIdGrupAsig() ))
                {   
                    int retrivedId = mapGrupAct.get( asgn.getIdGrupAsig() );
                    asgn.setIdActividades(retrivedId);
                }
                else
                {
                    System.out.println("sgd7 error: impossible trobar la idActivitat associada pel grupasig d'aquest alumne");
                }
                        
                
            }
            
           
            //Comprova si hi ha algun canvi
            if(!asgn.equals(asgn_orig))
            {
            
                ActividadAlumno aa = new ActividadAlumno(asgn, client);
             
                if(asgn.selected)
                {
                    aa.save();
                }
                else
                {
                    if(asgn.id>0) {
                        aa.delete();
                    }
                }

            }
        }
     }
     
    /*
     * Desa totes les assignacions de l'activitat
     */
    public void saveAssignacions()
    {
        for(int i=0; i<this.assignacions.size(); i++)
        {
            BeanActividadesAlumno asgn = assignacions.get(i);
            //System.out.println("assignacions "+asgn.getNombre()+" "+asgn.selected+" "+asgn.getId()+" "+asgn.getIdActividades()+" "+asgn.getIdGrupAsig());
            
            //Comprova si té id assignada
            if(asgn.getId()<=0)
            {
                if(mapGrupAct.containsKey( asgn.getIdGrupAsig() ))
                {   
                    int retrivedId = mapGrupAct.get( asgn.getIdGrupAsig() );
                    asgn.setIdActividades(retrivedId);
                }
                else
                {
                    System.out.println("sgd7 error: impossible trobar la idActivitat associada pel grupasig d'aquest alumne");
                }
                        
                
            }
            
            ActividadAlumno aa = new ActividadAlumno(asgn, client);
            

            if(asgn.selected)
            {
                aa.save();
            }
            else
            {
                if(asgn.id!=0) {
                    aa.delete();
                }
            }
                 
        }
    }
    
    @Override
    public String toString()
    { 
        String txt = "ActividadClase:: mapGrupAct: "+this.mapGrupAct+"\n";
        Actividad act;
        if(client==null)
        {
          throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        else
        {
            act = client.getActividad((BeanActividadClase) this);
        }
        for(int idgrupasig:  this.mapGrupAct.keySet())
        {
            act.id = this.mapGrupAct.get(idgrupasig);
            act.idGrupAsig = idgrupasig;
            txt += "Real id="+act.id+"; "+ StringUtils.AfterFirst(act.toString() +"\n", ";");
        }
        
        return txt;
    }
    
    /**
     * Per a l'activitat present carrega des de la db, els almnes que la tenen assignada
     */
    public void loadAssignacions(boolean perDefecte)
    {        
          if(!this.mapGrupAct.isEmpty())
          {
            if (client != null) {
                this.assignacions = client.getActividadesCollection().getActividadesAlumno(this.idProfesores, this.idClase, this.mapGrupAct, perDefecte);
            } else {
                 throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
            }
        }
    }
    

    @Override
    public int delete()
    {
        Actividad act;
        if(client==null)
        {
               throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        else
        {
             act = client.getActividad((BeanActividadClase) this);
        }
       
        int nup = 0;
        for(int idgrupasig: this.mapGrupAct.keySet())
        {
            act.id = this.mapGrupAct.get(idgrupasig);
            act.idGrupAsig = idgrupasig;
            nup += act.delete();
        }
        return nup;
    }
    
    public int deleteAll()
    {
        Actividad act;
        if(client==null)
        {
             throw(new java.lang.UnsupportedOperationException("SGD Client can't be null"));
        }
        else
        {
             act = client.getActividad((BeanActividadClase) this);
        }
        int nup = 0;
        for(int idgrupasig: this.mapGrupAct.keySet())
        {
            act.id = this.mapGrupAct.get(idgrupasig);
            act.idGrupAsig = idgrupasig;
            nup += act.deleteAll();
        }
        
        return nup;
    }

    @Override
    public void load() {
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public int insert() {
        return 0;
    }

    @Override
    public boolean exists() {
        return false;
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
