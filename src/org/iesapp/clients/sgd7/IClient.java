/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7;

import org.iesapp.clients.UClient;
import org.iesapp.clients.sgd7.actividades.Actividad;
import org.iesapp.clients.sgd7.actividades.ActividadAlumno;
import org.iesapp.clients.sgd7.actividades.ActividadesCollection;
import org.iesapp.clients.sgd7.actividades.BeanActividadClase;
import org.iesapp.clients.sgd7.actividades.BeanActividadesAlumno;
import org.iesapp.clients.sgd7.alumnos.AlumnosCollection;
import org.iesapp.clients.sgd7.base.TipoIncidencias;
import org.iesapp.clients.sgd7.base.TipoObservaciones;
import org.iesapp.clients.sgd7.clases.HoraCentro;
import org.iesapp.clients.sgd7.mensajes.MensajesCollection;
import org.iesapp.clients.sgd7.profesores.Profesores;
import org.iesapp.clients.sgd7.profesores.ProfesoresCollection;
import org.iesapp.database.MyDatabase;

/**
 *
 * @author Josep
 */
public interface IClient extends IClientController, UClient {
     public int getAnyAcademic();
     public Profesores getUser();
     
     //Must allow access to utility classes
     public ActividadesCollection getActividadesCollection();
     public AlumnosCollection getAlumnosCollection();
     public TipoIncidencias getTipoIncidencias();
     public TipoObservaciones getTipoObservaciones();
     public HoraCentro getHoraCentro();
     public MensajesCollection getMensajesCollection();
     public ProfesoresCollection getProfesoresCollection();
     public ActividadAlumno getActividadAlumno(BeanActividadesAlumno bean);
     public Actividad getActividad(BeanActividadClase bean);

     public String getConfigDB();
     public String getCurrentDBPrefix();
     public String getPlusDbName();
     public MyDatabase getPlusDb();
     
}
