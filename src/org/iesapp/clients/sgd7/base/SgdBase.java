/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.base;
import org.iesapp.database.MyDatabase;


/**
 *
 * @author Josep
 */
public class SgdBase {
    
    public static boolean initialized = false;
//    public static boolean isLogEnabled = false;
//    public static String user="";
//    public static String host = "";
//    public static String hostIp = "";
//    public static BeanProfesor currentUser;
  
    //public static HashMap<Integer,BeanTipoIncidencias> mapIncidencias;
    //public static HashMap<Integer,BeanTipoObservaciones> mapObservaciones;
    
    protected static MyDatabase sgd;
    protected static MyDatabase mysql;
//    protected static String configDB="config";
//    protected static int currentYear = 2012;
//    protected static String currentDBPrefix="curso";
//    protected static String currentDB="curso2012";
   
  
    /**
     * @return the sgd
     */
    public static MyDatabase getSgd() {
        return sgd;
    }

    /**
     * @param aSgd the sgd to set
     */
    public static void setSgd(MyDatabase aSgd) {
        sgd = aSgd;
    }

    /**
     * @return the mysql
     */
    public static MyDatabase getMysql() {
        return mysql;
    }

    /**
     * @param aMysql the mysql to set
     */
    public static void setMysql(MyDatabase aMysql) {
        mysql = aMysql;
    }
  
    
    public static void close()
    {
        if(mysql!=null) {
            mysql.close();
        }
        if(sgd!=null) {
            sgd.close();
        }
    }
    
  
}
