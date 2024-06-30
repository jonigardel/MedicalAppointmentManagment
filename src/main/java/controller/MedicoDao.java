package controller;

import entidades.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class MedicoDao {
    
    private static final String INSERT = "INSERT INTO medico (id ,Nombre, Apellido, fecha_nacimiento, direccion, email, nacionalidad, dni, especialidad, matricula, horarios) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_BY_DNI = "SELECT * FROM medico WHERE dni = ?";
    private static final String UPDATE = "UPDATE medico SET Nombre = ?, Apellido = ?, fecha_nacimiento = ?, direccion  = ?,email = ?, nacionalidad  = ?,dni  = ?,especialidad  = ?,matricula  = ?, horarios = ? where id = ?";
    private static final String DELETE = "DELETE FROM medico WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM medico";
    private static final String SELECT_ESPECIALIDAD = "SELECT DISTINCT especialidad from medico";
    private static final String SELECT_MEDICO_POR_ESPECIALIDAD = "SELECT * FROM medico WHERE especialidad = ?";

     public static void insert(Medico m){
        Connection conn=null;
        PreparedStatement stmt = null;
        try{
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(INSERT);
            stmt.setString(1, m.getId().toString());
            stmt.setString(2, m.getNombre());
            stmt.setString(3, m.getApellido());
            stmt.setDate(4, new java.sql.Date(m.getFechaDeNacimiento().getTime()));
            stmt.setString(5, m.getDireccion());
            stmt.setString(6, m.getEmail());
            stmt.setString(7, m.getNacionalidad());
            stmt.setInt(8, m.getDni());
            stmt.setString(9, m.getEspecialidad());
            stmt.setString(10, m.getMatricula());
            stmt.setString(11, m.getHorarios().toString());
            stmt.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("Error al insertar nuevo Medico");
        }finally{
            Connector.close(conn);
            Connector.close(stmt);
        }
    }
    
     public static void update(Medico m) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE);
                stmt.setString(1, m.getNombre());
                stmt.setString(2, m.getApellido());
                stmt.setDate(3, new java.sql.Date(m.getFechaDeNacimiento().getTime()));
                stmt.setString(4, m.getDireccion());
                stmt.setString(5, m.getEmail());
                stmt.setString(6, m.getNacionalidad());
                stmt.setInt(7, m.getDni());
                stmt.setString(8, m.getEspecialidad());
                stmt.setString(9, m.getMatricula());
                stmt.setString(10, m.getHorarios().toString());
                stmt.setString(11, m.getId().toString());
                stmt.executeUpdate();
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el Medico: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
    
    public static Medico getMedicoByDni(int dni) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Medico medico = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(SELECT_BY_DNI);
                stmt.setInt(1, dni);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    medico = new Medico(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("email"),
                        rs.getString("direccion"),
                        new java.util.Date(rs.getDate("fecha_nacimiento").getTime()),
                        rs.getString("nacionalidad"),
                        rs.getString("especialidad"),
                        rs.getString("matricula"),
                        stringToMap(rs.getString("horarios"))
                    );
                } else {
                    System.out.println("No se encontró un Medico con el DNI proporcionado.");
                }
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar Medico por DNI:" +  dni);
            ex.printStackTrace();
        }
        
        return medico;
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
                    System.out.println("No se encontró el medico con el ID proporcionado.");
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
    
     public static ArrayList<Medico> listMedicos(boolean especialidad, String nombreEspecialidad){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Medico> medicos = new ArrayList<Medico>();
        try{
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(especialidad? SELECT_MEDICO_POR_ESPECIALIDAD :SELECT_ALL);
            if(especialidad){
                stmt.setString(1, nombreEspecialidad);
            }
            rs = stmt.executeQuery();
            while(rs.next()){
                Medico m = new Medico(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("email"),
                        rs.getString("direccion"),
                        new java.util.Date(rs.getDate("fecha_nacimiento").getTime()),
                        rs.getString("nacionalidad"),
                        rs.getString("especialidad"),
                        rs.getString("matricula"),
                        stringToMap(rs.getString("horarios"))
                    );
                medicos.add(m);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("Error al listar medicos: " + ex.getMessage());
        }finally{
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return medicos;
    }
     
    public static ArrayList<String> listarEspecialidades(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> especialidades = new ArrayList<>();
        try{
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(SELECT_ESPECIALIDAD);
            rs = stmt.executeQuery();
            while(rs.next()){
                String especialidad = rs.getString("especialidad");
                especialidades.add(especialidad);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return especialidades;
    }
     
    public static Map<String, String> stringToMap(String mapString) {
        if (mapString == null || mapString.isEmpty() || !mapString.startsWith("{") || !mapString.endsWith("}")) {
            throw new IllegalArgumentException("La cadena de entrada no es válida");
        }
        
        mapString = mapString.substring(1, mapString.length() - 1);
        
        Map<String, String> map = new HashMap<>();
        String[] entries = mapString.split(", ");

        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                map.put(key, value);
            } else {
                throw new IllegalArgumentException("Formato de entrada incorrecto para la entrada: " + entry);
            }
        }

        return map;
    }
}
