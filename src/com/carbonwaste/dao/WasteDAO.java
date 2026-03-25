package com.carbonwaste.dao;

import com.carbonwaste.database.DBConnection;
import com.carbonwaste.model.WasteEntry;
import java.sql.*;

public class WasteDAO {

    // CREATE
    public void saveLog(WasteEntry entry) {
        String sql = "INSERT INTO waste_logs (fuel_type, distance_km, efficiency, total_co2_kg) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getFuelType());
            pstmt.setDouble(2, entry.getDistance());
            pstmt.setDouble(3, entry.getEfficiency());
            pstmt.setDouble(4, entry.getTotalCo2());
            pstmt.executeUpdate();
            System.out.println(">> Success: Data saved to MySQL.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void viewLogs(int id) {
        String sql = (id == 0) ? "SELECT * FROM waste_logs" : "SELECT * FROM waste_logs WHERE id = " + id;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- CARBON WASTE HISTORY ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Fuel: %s | Dist: %.1f km | CO2: %.2f kg | Date: %s%n",
                        rs.getInt("id"), rs.getString("fuel_type"),
                        rs.getDouble("distance_km"), rs.getDouble("total_co2_kg"),
                        rs.getTimestamp("entry_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateLog(int id, double newDist, double newCo2) {
        String sql = "UPDATE waste_logs SET distance_km = ?, total_co2_kg = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newDist);
            pstmt.setDouble(2, newCo2);
            pstmt.setInt(3, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? ">> Success: Log updated." : ">> Error: ID not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteLog(int id) {
        String sql = "DELETE FROM waste_logs WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? ">> Success: Log deleted." : ">> Error: ID not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double getTotalWaste() {
        String sql = "SELECT SUM(total_co2_kg) as grand_total FROM waste_logs";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("grand_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}