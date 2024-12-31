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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aqil Anhein
 */
public class TrainModel {
    public boolean addTrain(int routeID,String name,String day,String time) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO train (TrainName, TrainRoute, TrainCapacity, Day, Time) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, name);
            pst.setInt(2, routeID);
            pst.setInt(3, 0); // Capacity set to 0 for new train creations
            pst.setString(4, day); // Add day (if needed)
            pst.setString(5, time); // Add time (if needed)

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public DefaultTableModel getRoutesTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Route Name");
        tableModel.addColumn("From");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Duration");

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM Route");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String routeName = rs.getString("RouteName");
                String from = rs.getString("RouteFrom");
                String to = rs.getString("Destination");
                int duration = rs.getInt("Duration");

                tableModel.addRow(new Object[]{routeName, from, to, duration});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableModel;
    }
    
    
    public int getRouteIDFromTable(JTable table) {
        int selectedRowIndex = table.getSelectedRow();
        int routeID = -1; // Set default routeID as -1 (or any other suitable default value)

        if (selectedRowIndex != -1) { // Check if a row is selected
            String routeName = table.getValueAt(selectedRowIndex, 0).toString(); // Assuming RouteName is in column 1
            String from = table.getValueAt(selectedRowIndex, 1).toString(); // Assuming From is in column 2
            String destination = table.getValueAt(selectedRowIndex, 2).toString(); // Assuming Destination is in column 3

            // Establish the database connection
            try (Connection conn = DBConnection.connect()) {
                // Query the database to fetch the routeID based on RouteName, From, and Destination
                String query = "SELECT RouteID FROM Route WHERE RouteName = ? AND RouteFrom = ? AND Destination = ?";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, routeName);
                    pst.setString(2, from);
                    pst.setString(3, destination);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        routeID = rs.getInt("RouteID");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return routeID;
    }
    
}
