/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aqil Anhein
 */
public class AdminModel {
    public boolean validate(String email, String password) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM admin WHERE email=? AND Password=?");
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            boolean isValid = rs.next(); // Check if user exists with provided credentials

            if (isValid) {
                String Useremail = rs.getString("email"); // Assuming Username is a column in your 'user' table
                UserSession.getInstance().setLoggedInUser(Useremail); // Set the logged-in user in the session
            }

        return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registerAdmin(String Email,String password) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO admin (email,password) VALUES (?,?)");
            pst.setString(1, Email);
            pst.setString(2, password);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Registration successful if rows affected > 0
        } catch (SQLIntegrityConstraintViolationException e) {
            // Catch the specific exception for duplicate entry (email)
            if (e.getErrorCode() == 1062) { // MySQL error code for duplicate entry
                JOptionPane.showMessageDialog(null, "Email already exists!", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                // Handle the error as needed (show asn error message, log, etc.)
            }
            return false;
           
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public DefaultTableModel getTrainsTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("TrainID");
        tableModel.addColumn("Train Name");
        tableModel.addColumn("From");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Capacity");
        tableModel.addColumn("Day");
        tableModel.addColumn("Time");

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM train INNER JOIN route ON train.TrainRoute = route.RouteID");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("TrainID");
                String routeName = rs.getString("TrainName");
                String from = rs.getString("RouteFrom");
                String to = rs.getString("Destination");
                int cap = rs.getInt("TrainCapacity");
                String day = rs.getString("Day");
                String time = rs.getString("Time");

                tableModel.addRow(new Object[]{id,routeName, from, to, cap, day, time});
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }

    return tableModel;
    }
    
    public DefaultTableModel getWagonsTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Wagon ID");
        tableModel.addColumn("Wagon Name");
        tableModel.addColumn("Wagon Class");
        tableModel.addColumn("Seats");
        

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM wagon");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("WagonID");
                String name = rs.getString("WagonName");
                String type = rs.getString("WagonType");
                int seat = rs.getInt("Seats");
                

                tableModel.addRow(new Object[]{id, name, type, seat});
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
      
    return tableModel;
    }
    
    
    public boolean addTrainWagon(int TrainID,int WagonID) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO trainwagon (TrainID, WagonID) VALUES (?, ?)");
            
            pst.setInt(1, TrainID);
            pst.setInt(2, WagonID); // Capacity set to 0 for new train creations
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
        String errorMessage = "That wagon is already associated with that train.";
        JOptionPane.showMessageDialog(null, errorMessage, "Constraint Violation", JOptionPane.ERROR_MESSAGE);
        return false;
        } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    }
    
    
    public boolean updateCertainTrainCapacity(int trainID) {
        boolean updated = false;
        try (Connection conn = DBConnection.connect()) {
            // Fetch total capacity of associated wagons for the given train
            String query = "SELECT SUM(w.Seats) AS TotalCapacity FROM wagon w " +
                           "INNER JOIN TrainWagon tw ON w.WagonID = tw.WagonID " +
                           "WHERE tw.TrainID = ?";
            
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, trainID);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    int totalCapacity = rs.getInt("TotalCapacity");

                    // Update train's capacity in the train table
                    String updateQuery = "UPDATE train SET TrainCapacity = ? WHERE TrainID = ?";
                    try (PreparedStatement updatePst = conn.prepareStatement(updateQuery)) {
                        updatePst.setInt(1, totalCapacity);
                        updatePst.setInt(2, trainID);
                        int rowsAffected = updatePst.executeUpdate();
                        updated = rowsAffected > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }
    
    
    public boolean updateAllTrainCapacities() {
    try (Connection conn = DBConnection.connect()) {
        String trainQuery = "SELECT DISTINCT TrainID FROM trainwagon";
        PreparedStatement trainPst = conn.prepareStatement(trainQuery);
        ResultSet trainRs = trainPst.executeQuery();

        while (trainRs.next()) {
            int trainID = trainRs.getInt("TrainID");

            // Calculate total capacity of associated wagons for the given train
            String query = "SELECT SUM(w.Seats) AS TotalCapacity FROM wagon w " +
                           "INNER JOIN TrainWagon tw ON w.WagonID = tw.WagonID " +
                           "WHERE tw.TrainID = ?";
            
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, trainID);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    int totalCapacity = rs.getInt("TotalCapacity");

                    // Update train's capacity in the train table
                    String updateQuery = "UPDATE train SET TrainCapacity = ? WHERE TrainID = ?";
                    try (PreparedStatement updatePst = conn.prepareStatement(updateQuery)) {
                        updatePst.setInt(1, totalCapacity);
                        updatePst.setInt(2, trainID);
                        updatePst.executeUpdate();
                    }
                }
            }
        }
        return true; // All trains' capacities updated successfully
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
