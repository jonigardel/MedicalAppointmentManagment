package controller;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class HistoriaClinicaDao {

    private static final String INSERT = "INSERT INTO historia_clinica (id, diagnostico, observaciones, tratamiento) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM historia_clinica";
    private static final String UPDATE = "UPDATE historia_clinica SET diagnostico = ?, observaciones = ?, tratamiento = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM historia_clinica WHERE id = ?";

    public static void insert(HistoriaClinica c) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                conn.setAutoCommit(true);
                stmt = conn.prepareStatement(INSERT);
                stmt.setString(1, c.getId().toString());
                stmt.setString(2, c.getDiagnostico());
                stmt.setString(3, c.getObservaciones());
                stmt.setString(4, c.getTratamiento());
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

    public static ArrayList<HistoriaClinica> listHistoriasClinicas() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<HistoriaClinica> historiasClinicas = new ArrayList<HistoriaClinica>();
        try {
            conn = Connector.getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String diagnostico = rs.getString(2);
                String observaciones = rs.getString(3);
                String tratamiento = rs.getString(4);
                HistoriaClinica h = new HistoriaClinica(UUID.fromString(id), diagnostico, observaciones, tratamiento);
                historiasClinicas.add(h);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Connector.close(conn);
            Connector.close(stmt);
            Connector.close(rs);
        }
        return historiasClinicas;
    }

    public static void update(HistoriaClinica c) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE);
                stmt.setString(1, c.getDiagnostico());
                stmt.setString(2, c.getObservaciones());
                stmt.setString(3, c.getTratamiento());
                stmt.setString(4, c.getId().toString());
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated <= 0) {
                    System.out.println("No se encontró la historia clínica con el ID proporcionado.");
                }
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
            System.out.println("Error al eliminar historia clínica: Para poder eliminar no debe pertenecer a ningun paciente activo");
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
}
