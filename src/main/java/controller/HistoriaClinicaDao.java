package controller;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;

public class HistoriaClinicaDao {
     private static final String INSERT = "INSERT INTO historia_clinica (diagnostico, observaciones, tratamiento) VALUES (?,?,?)";
    
    public static void insert(HistoriaClinica c) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connector.getConnection();
            // Activar auto-commit
            conn.setAutoCommit(true);
            stmt = conn.prepareStatement(INSERT);
            stmt.setString(1, c.getDiagnostico());
            stmt.setString(2, c.getObservaciones());
            stmt.setString(3, c.getTratamiento());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al insertar nueva historia clinica: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Connector.close(stmt);
            Connector.close(conn);
        }
    }
    
}