package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    private static String db="campamento_db";
    private static String user="root";
    private static String pass="";
    private static String host="localhost:3306";
    private static String server="jdbc:mysql://"+host+"/"+db;
    
    
    public static  Connection getConexion() {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection(server,user,pass);    
        }
        catch(Exception e){
         System.out.println(String.valueOf(e));}
        return cn;
    }
    public static ResultSet getTabla(String Consulta){
        Connection cn=getConexion();
        Statement st;
        ResultSet datos=null;
        try{
            st=cn.createStatement();
            datos=st.executeQuery(Consulta);            
        }
        catch(Exception e){ System.out.print(e.toString());}
        return datos;
    } 
    
    public static void ejecutarQuery(String Consulta){
        Connection cn=getConexion();
        Statement st;
        try{
            st = cn.createStatement();
            st.executeUpdate(Consulta);
        }
        catch(Exception e){ System.out.print(e.toString());}
    }
   
}