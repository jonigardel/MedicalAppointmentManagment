/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.*;

public class Connector {
    private final static String URL = "jdbc:mysql://localhost:3306/med_appointment";
    private final static String USER = "root";
    private final static String PASS = "Eunbit27";
    
    public static Connection getConnection(){
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("Error al obtener conexión");
        }
        return conn;
    }
    
    public static void close(Connection conn){
        try{
            conn.close();
        }catch(SQLException ex){
            System.out.println("Error al cerrar conexión");
        }
    }
    
    public static void close(PreparedStatement stmt){
        try{
            stmt.close();
        }catch(SQLException ex){
            System.out.println("Error al cerrar PreparedStatement");
        }
    }
    
    public static void close(ResultSet rs){
        try{
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error al cerrar ResultSet");
        }
    }
}
