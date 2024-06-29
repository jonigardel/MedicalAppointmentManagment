package controller;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;

public class PacienteDao {
    private static final String INSERT = "INSERT INTO paciente (Nombre, Apellido, fecha_nacimiento, direccion, email, nacionalidad, dni, obra_social,numero_credencial, id_historia_clinica) VALUES (?,?,?,?,?,?,?,?,?,?)";
    
     public static void insert(Paciente p){
        Connection conn=null;
        PreparedStatement stmt = null;
        try{
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(INSERT);
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getApellido());
            stmt.setDate(3, new java.sql.Date(p.getFechaDeNacimiento().getTime()));
            stmt.setString(4, p.getDireccion());
            stmt.setString(5, p.getEmail());
            stmt.setString(6, p.getNacionalidad());
            stmt.setInt(7, p.getDni());
            stmt.setString(8, p.getObraSocial());
            stmt.setInt(9, p.getNumeroCredencial());
            stmt.setString(10, p.getIdHistoriaCLinica().toString());
            stmt.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error al insertar nuevo paciente");
        }finally{
            Connector.close(conn);
            Connector.close(stmt);
        }
    }
    
}
