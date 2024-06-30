package controller;

import entidades.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TurnoDao {
    
    private static final String INSERT = "INSERT INTO turnos (id, fecha, estado,hora, id_medico, id_paciente, id_administrativo) VALUES (?,?,?,?,?,?,?)";
    private static final String INSERT_NO_ADMIN = "INSERT INTO turnos (id, fecha, estado,hora, id_medico, id_paciente) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM turnos";
    private static final String SELECT_MEDICOS = "SELECT * FROM turnos where id_medico = ?";
    private static final String SELECT_PACIENTE = "SELECT t.id, t.fecha, t.estado, t.hora, t.id_medico, m.nombre AS nombre_medico, m.apellido AS apellido_medico, t.id_paciente, t.id_administrativo FROM turnos t JOIN medico m ON t.id_medico = m.id WHERE t.id_paciente = ?;";
    private static final String SELECT_BY_ID = "SELECT t.id, t.fecha, t.estado, t.hora, t.id_medico, m.nombre AS nombre_medico, m.apellido AS apellido_medico, t.id_paciente, t.id_administrativo FROM turnos t JOIN medico m ON t.id_medico = m.id WHERE t.id = ?;";
    private static final String UPDATE = "UPDATE turnos SET fecha = ?, hora = ?, id_medico = ? WHERE id = ?";
    private static final String UPDATE_ESTADO = "UPDATE turnos SET estado = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM turnos WHERE id = ?";
    private static final String SELECT_ESTADO = "SELECT * FROM turnos WHERE estado = ?";
    
    public static void insert(Turno t) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                conn.setAutoCommit(true);
                stmt = conn.prepareStatement(t.getIdAdministrativo() != null ? INSERT : INSERT_NO_ADMIN);
                stmt.setString(1, t.getId().toString());
                stmt.setDate(2, new java.sql.Date(t.getFecha().getTime()));
                stmt.setString(3, t.getEstado());
                stmt.setString(4, t.getHora());
                stmt.setString(5, t.getIdMedico().toString());
                stmt.setString(6, t.getIdPaciente().toString());
                if(t.getIdAdministrativo() != null) {
                    stmt.setString(7,t.getIdAdministrativo().toString());
                }
                stmt.executeUpdate();
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar nueva historia clinica: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
    
    public static void update(Turno t) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE);
                stmt.setDate(1, new java.sql.Date(t.getFecha().getTime()));
                stmt.setString(2, t.getHora());
                stmt.setString(3, t.getIdMedico().toString());
                stmt.setString(4, t.getId().toString());
                stmt.executeUpdate();
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar historia clínica: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
    
    public static ArrayList<Turno> listTurnos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Turno> turnos = new ArrayList<Turno>();
        try {
            conn = Connector.getConnection();
            stmt = conn.prepareStatement( SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Turno t = new Turno(
                        UUID.fromString(rs.getString("id")), 
                        UUID.fromString(rs.getString("id_medico")),
                        UUID.fromString(rs.getString("id_paciente")),
                        rs.getDate("fecha"), 
                        rs.getString("hora"),
                        rs.getString("estado")
                );
                turnos.add(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return turnos;
    }
    
    public static ArrayList<Turno> listarTurnosEstado(String estado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Turno> turnos = new ArrayList<Turno>();
        try {
            conn = Connector.getConnection();
            stmt = conn.prepareStatement( SELECT_ESTADO);
            stmt.setString(1, estado);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Turno t = new Turno(
                        UUID.fromString(rs.getString("id")), 
                        UUID.fromString(rs.getString("id_medico")),
                        UUID.fromString(rs.getString("id_paciente")),
                        rs.getDate("fecha"), 
                        rs.getString("hora"),
                        rs.getString("estado")
                );
                turnos.add(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return turnos;
    }
    
        public static ArrayList<Turno> listTurnosPorMedico(String medicoId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Turno> turnos = new ArrayList<Turno>();
        try {
            conn = Connector.getConnection();
            stmt = conn.prepareStatement( SELECT_MEDICOS);
            stmt.setString(1, medicoId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Turno t = new Turno(
                        UUID.fromString(rs.getString("id")), 
                        UUID.fromString(rs.getString("id_medico")),
                        UUID.fromString(rs.getString("id_paciente")),
                        rs.getDate("fecha"), 
                        rs.getString("hora"),
                        rs.getString("estado")
                );
                turnos.add(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return turnos;
    }
    
    
    public static List<Map<String, Object>> listTurnosPorPaciente(String pacienteId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> turnos = new ArrayList<>();        
        try {
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(SELECT_PACIENTE);
            stmt.setString(1, pacienteId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<>();
                rowData.put("id", rs.getString("id"));
                rowData.put("fecha", rs.getString("fecha"));
                rowData.put("estado", rs.getString("estado"));
                rowData.put("hora", rs.getString("hora"));
                rowData.put("id_medico", rs.getString("id_medico"));
                rowData.put("nombre_medico", rs.getString("nombre_medico"));
                rowData.put("apellido_medico", rs.getString("apellido_medico"));
                rowData.put("id_paciente", rs.getString("id_paciente"));
                rowData.put("id_administrativo", rs.getString("id_administrativo"));

                turnos.add(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return turnos;
    }
    
    public static void cambiarEstado(String id, String estado){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE_ESTADO);
                stmt.setString(1, estado);
                 stmt.setString(2, id);
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
    
    public static Turno buscarPorId(String id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Turno turno = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(SELECT_BY_ID);
                stmt.setString(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    turno = new Turno(
                        UUID.fromString(rs.getString("id")), 
                        UUID.fromString(rs.getString("id_paciente")),
                        UUID.fromString(rs.getString("id_medico")), 
                        rs.getDate("fecha"), 
                        rs.getString("hora"),
                        rs.getString("estado")
                    );
                } else {
                    System.out.println("No se encontró un turno con el id proporcionado.");
                }
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar turno");
            ex.printStackTrace();
        }
        return turno;
    }
    
    public static void mostrarTurno(Turno turno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(SELECT_BY_ID);
                stmt.setString(1, turno.getId().toString());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    String fecha = rs.getString("fecha");
                    String hora = rs.getString("hora");
                    String estado = rs.getString("estado");
                    String nombreMedico = rs.getString("nombre_medico");
                    String apellidoMedico = rs.getString("apellido_medico");

                    System.out.println("-------------------------");
                    System.out.println("Fecha: " + fecha);
                    System.out.println("Hora: " + hora);
                    System.out.println("Estado: " + estado);
                    System.out.println(" Médico: " + nombreMedico + " " + apellidoMedico);
                    System.out.println("-------------------------");
                } else {
                    System.out.println("No se encontró un turno con el id proporcionado.");
                }
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar turno");
            ex.printStackTrace();
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
                stmt.executeUpdate();
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar turno: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
}

