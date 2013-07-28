package org.iesapp.clients.sgd7;


import org.iesapp.database.MyDatabase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.iesapp.clients.sgd7.actividades.ActividadClase;
import org.iesapp.clients.sgd7.actividades.BeanActividadClase;
import org.iesapp.clients.sgd7.base.SgdBase;
import org.iesapp.clients.sgd7.clases.BeanClase;
import org.iesapp.clients.sgd7.mensajes.MensajesListas;
import org.iesapp.clients.sgd7.profesores.Profesores;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Josep
 */
public class TestClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyDatabase mysql = new MyDatabase("localhost","iesDigital2012","root","","zeroDateTimeBehavior=convertToNull");
        System.out.println(mysql.connect());
        MyDatabase sgd = new MyDatabase("localhost","curso2012","root","","zeroDateTimeBehavior=convertToNull");
        System.out.println(sgd.connect());
        
        //Inicia la instància de la base de dades sgd
     
       
        SgdClient client = new SgdClient(mysql,sgd,2012,"curso","config","","");
                
        System.out.println("====================================");
        System.out.println("Exemple d'utilització del client sgd");
        System.out.println("        --Esquema de client--       ");
        System.out.println("per Josep Mulet (c) 2011            ");
        System.out.println("====================================");
        
        //Check client
        System.out.println("Comparison:"+client.checkDatabases(2013));
        System.out.println("Fix:"+client.fixDatabases());
        
        System.exit(0);
   
        ArrayList<MensajesListas> mensajesListas = client.getMensajesCollection().getMensajesListas();
        for(MensajesListas ml: mensajesListas)
        {
            System.out.println("Lista mensajes: "+ml.toString());
        }
        
        System.out.println(client.getIncidenciasCollection().getJSONDiasIncidencias(63,sgd).toString());
          
        System.out.println("Test mapes ");
        System.out.println("iesd2sgdhcmap="+client.getHoraCentro().getIesd2sgdhcmap().toString());
        System.out.println("sgd2iesdhcmap="+client.getHoraCentro().getSgd2iesdhcmap().toString());
                  
      //  prompt the user to enter their name
      System.out.print("Entrau idProfesor: ");

      //  open up standard input
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      String idProfe = null;

      //  read the username from the command-line; need to use try/catch with the
      //  readLine() method
      try {
         idProfe = br.readLine();
      } catch (IOException ioe) {
         System.out.println("IO error trying to read your name!");
         System.exit(1);
      }
        Profesores p = client.getProfesores(idProfe);
        System.out.println(p.print());
      
        client.setUser(p, true);
        System.out.println("Enabled logger for user with idUP="+p.getBean().getIdUnidadesPersonales()); 
        
        System.out.println(".... bloquejam el compte.....");
        p.lock();
        System.out.println(p.print());
        System.out.println(".... desbloquejam el compte.....");
        p.unlock();
        System.out.println(p.print());
      
      
        
        //Esborra Missatges
        
//        Mensajes sms0 = new Mensajes();
//        sms0.setId(365);
//        sms0.delete();
//        sms0.setId(366);
//        sms0.delete();
//        sms0.setId(367);
//        sms0.delete();
        //crea un missatge de prova
        //        Mensajes sms = new Mensajes(52, "prova helllooooo", Arrays.asList(new Integer[]{1,2,3}));
        //        sms.save();
        //        int id = sms.getId();
        //        
        //el carrega i el mostra
        //        Mensajes sms2 = new Mensajes(3);
        //        sms2.print();
        //
        //        //li modifica ....
        //        sms2.setTexto("m'havia equicat---");
        //        sms2.getDestinatarios().remove(20);
        //        sms2.getDestinatarios().put(56,-1);
        //        sms2.save();
        //
        //        sms2.load();
        //        sms2.print();
        //Alumnos
              
        //Mostra l'horari d'un professor
        for(int k=1; k<6; k++)
        {
            System.out.println( client.getClases(idProfe, k) );
            
        }
        
 
        //Mostra les clases d'un professor
        System.out.println("Classes del professor: ");
        ArrayList<BeanClase> clasesProfe = client.getClasesCollection().getClasesProfe(idProfe);
        for(int i=0; i<clasesProfe.size(); i++)
        {
          System.out.println(clasesProfe.get(i).getGrupo());
        }
        
       
       String idClase_str = "";
       System.out.println("Tria una idClase:");
       try {
         idClase_str = br.readLine();
      } catch (IOException ioe) {
         System.out.println("IO error trying to read your name!");
         System.exit(1);
      }
      
        int ordre = -1;
        for(int i=0; i<clasesProfe.size(); i++)
        {
            if(idClase_str.equals(""+clasesProfe.get(i).getIdClase()))
            {
                ordre = i;
                break;
            }
        }
        
        if(ordre<0) {
            return;
        }
       
       ArrayList<BeanActividadClase> loadActividades = client.getActividadesCollection().loadActividades(idProfe, clasesProfe.get(ordre), -1, 0,"",0);
       
       for(int i=0; i<loadActividades.size(); i++)
       {
           ActividadClase actclass = client.getActividadClase(loadActividades.get(i));
           System.out.println(" alumnes; " +actclass.toString());
            actclass.loadAssignacions(true);
            for(int k=0; k<actclass.getAssignacions().size(); k++) {
               System.out.println("\t\t"+client.getActividadAlumno(actclass.getAssignacions().get(k)).print());
           }
       }
    }
}
