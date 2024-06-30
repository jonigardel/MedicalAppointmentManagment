package controller;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class PacienteDao {
    private static final String INSERT = "INSERT INTO paciente (id ,Nombre, Apellido, fecha_nacimiento, direccion, email, nacionalidad, dni, obra_social,numero_credencial, id_historia_clinica) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_BY_DNI = "SELECT * FROM paciente WHERE dni = ?";
    private static final String UPDATE = "UPDATE paciente SET Nombre = ?, Apellido = ?, fecha_nacimiento = ?, direccion  = ?,email = ?, nacionalidad  = ?,dni  = ?,obra_social  = ?,numero_credencial  = ?";
    private static final String DELETE = "DELETE FROM paciente WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM paciente";
    
     public static void insert(Paciente p){
        Connection conn=null;
        PreparedStatement stmt = null;
        try{
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(INSERT);
            stmt.setString(1, p.getId().toString());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getApellido());
            stmt.setDate(4, new java.sql.Date(p.getFechaDeNacimiento().getTime()));
            stmt.setString(5, p.getDireccion());
            stmt.setString(6, p.getEmail());
            stmt.setString(7, p.getNacionalidad());
            stmt.setInt(8, p.getDni());
            stmt.setString(9, p.getObraSocial());
            stmt.setInt(10, p.getNumeroCredencial());
            stmt.setString(11, p.getIdHistoriaCLinica().toString());
            stmt.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("Error al insertar nuevo paciente");
        }finally{
            Connector.close(conn);
            Connector.close(stmt);
        }
    }
     
       public static Paciente getPacienteByDni(int dni) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(SELECT_BY_DNI);
                stmt.setInt(1, dni);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    paciente = new Paciente(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getInt("dni"),
                        rs.getString("email"),
                        rs.getString("direccion"),
                        new java.util.Date(rs.getDate("fecha_nacimiento").getTime()),
                        rs.getString("nacionalidad"),
                        rs.getString("obra_social"),
                        rs.getInt("numero_credencial"),
                        UUID.fromString(rs.getString("id_historia_clinica"))
                    );
                } else {
                    System.out.println("No se encontró un paciente con el DNI proporcionado.");
                }
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar paciente por DNI:" +  dni);
            ex.printStackTrace();
        }
        
        return paciente;
    }
       
    public static void update(Paciente p) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE);
                stmt.setString(1, p.getNombre());
                stmt.setString(2, p.getApellido());
                stmt.setDate(3, new java.sql.Date(p.getFechaDeNacimiento().getTime()));
                stmt.setString(4, p.getDireccion());
                stmt.setString(5, p.getEmail());
                stmt.setString(6, p.getNacionalidad());
                stmt.setInt(7, p.getDni());
                stmt.setString(8, p.getObraSocial());
                stmt.setInt(9, p.getNumeroCredencial());
                stmt.executeUpdate();
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el Paciente: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
    
    public static void delete(String id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(DELETE);
                stmt.setString(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted <= 0) {
                    System.out.println("No se encontró el paciente con el ID proporcionado.");
                }
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar historia clínica: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
    
    public static ArrayList<Paciente> listPacientes(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
        try{
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            while(rs.next()){
                Paciente p = new Paciente(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getInt("dni"),
                    rs.getString("email"),
                    rs.getString("direccion"),
                    new java.util.Date(rs.getDate("fecha_nacimiento").getTime()),
                    rs.getString("nacionalidad"),
                    rs.getString("obra_social"),
                    rs.getInt("numero_credencial"),
                    UUID.fromString(rs.getString("id_historia_clinica"))
                    );
                pacientes.add(p);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return pacientes;
    }
}
