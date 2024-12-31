/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.DBConnection;
import Controller.MainMenuController;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Aqil Anhein
 */
public class BookingModel {
    public Object[] getRouteFromTrainID(int TrainID) {
        String from = null;
        String dest = null;
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT r.RouteFrom, r.Destination " +
                                                            "FROM train t " +
                                                            "INNER JOIN route r ON r.TrainID = t.TrainID " +
                                                            "WHERE t.TrainID = ?");
        ){

            pst.setInt(1, TrainID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    from = rs.getString("RouteFrom");
                    dest = rs.getString("Destination");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        if (from != null && dest != null) {
            return new Object[]{from, dest};
        } else {
            return null; 
        }
    }
    
    public Object[] getScheduleFromTrainID(int TrainID) {
        String day = null;
        String time = null;
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT Day, Time "+
                                                            "FROM train " +
                                                            "WHERE TrainID = ? ");
        ){

            pst.setInt(1, TrainID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    day = rs.getString("Day");
                    time = rs.getString("Time");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        if (day != null && time != null) {
            return new Object[]{day, time};
        } else {
            return null; 
        }
    }
    
    
    public DefaultTableModel searchTrains(String from, String to, String selectedClass) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT t.TrainID, t.TrainName, r.RouteFrom, r.Destination, r.Duration FROM train t " +
                                                       "INNER JOIN route r ON t.TrainRoute = r.RouteID " +
                                                       "INNER JOIN trainwagon tw ON t.TrainID = tw.TrainID " +
                                                       "INNER JOIN wagon w ON tw.WagonID = w.WagonID WHERE 1=1 ");

        // Add search criteria based on user input
        if (from != null && !from.isEmpty()) {
            queryBuilder.append("AND r.RouteFrom = ? ");
        }
        if (to != null && !to.isEmpty()) {
            queryBuilder.append("AND r.Destination = ? ");
        }
        if (!"Any".equals(selectedClass)) {
            queryBuilder.append("AND w.WagonType = ? ");
        }

        DefaultTableModel model = new DefaultTableModel(new String[]{"TrainID","TrainName", "From", "Destination", "Duration"}, 0);

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;
            if (from != null && !from.isEmpty()) {
                stmt.setString(paramIndex++, from);
            }
            if (to != null && !to.isEmpty()) {
                stmt.setString(paramIndex++, to);
            }
            if (!"Any".equals(selectedClass)) {
                stmt.setString(paramIndex, selectedClass);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int trainid = rs.getInt("TrainID");
                String trainName = rs.getString("TrainName");
                String routeFrom = rs.getString("RouteFrom");
                String destination = rs.getString("Destination");
                int duration = rs.getInt("Duration");

                model.addRow(new Object[]{trainid,trainName, routeFrom, destination, duration});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
     public DefaultTableModel getWagonsForTrain(int trainID) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"WagonID", "Name", "Class", "Capacity","Booked Seats","Available Seats"}, 0);

        try {
            Connection conn = DBConnection.connect();
            String query = "SELECT w.WagonID, w.WagonName, w.WagonType, w.Seats " +
                           "FROM wagon w " +
                           "INNER JOIN trainwagon tw ON w.WagonID = tw.WagonID " +
                           "WHERE tw.TrainID = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, trainID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
               
                int wagonID = rs.getInt("WagonID");
                String name = rs.getString("WagonName");
                String wagonClass = rs.getString("WagonType");
                int capacity = rs.getInt("Seats");
                int booked = getBookedSeatsForWagon(trainID, wagonID);
                int remcap = getRemainingCapacity(trainID, wagonID, capacity);

                model.addRow(new Object[]{wagonID, name, wagonClass, capacity,booked,remcap});
            }

            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
     
    private int getBookedSeatsForWagon(int trainID, int wagonID) {
        int bookedSeats = 0;

        try {
            Connection conn = DBConnection.connect();
            String query = "SELECT SUM(Seats) AS BookedSeats " +
                           "FROM booking " +
                           "WHERE TrainID = ? AND WagonID = ? AND Status = 0";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, trainID);
            pst.setInt(2, wagonID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                bookedSeats = rs.getInt("BookedSeats");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookedSeats;
    }
    
    public int getRemainingCapacity(int trainID, int wagonID,int wagoncap) {
        int bookedSeats = getBookedSeatsForWagon(trainID, wagonID); 

        // Calculate remaining capacity
        int remainingCapacity = wagoncap - bookedSeats;
        return remainingCapacity >= 0 ? remainingCapacity : 0; // Ensure remaining capacity is non-negative
    }
    
    public boolean bookTrain(int customerID, int trainID, int wagonID, int seats) {
        try {
            Connection conn = DBConnection.connect();
            String routeQuery = "SELECT TrainRoute FROM Train WHERE TrainID = ?";
            PreparedStatement routePst = conn.prepareStatement(routeQuery);
            routePst.setInt(1, trainID);
            ResultSet routeRs = routePst.executeQuery();

            int routeID = -1;
            if (routeRs.next()) {
                routeID = routeRs.getInt("TrainRoute");
            }

            String query = "INSERT INTO booking (CustomerID, TrainID, WagonID, RouteID, Seats) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, customerID);
            pst.setInt(2, trainID);
            pst.setInt(3, wagonID);
            pst.setInt(4, routeID);
            pst.setInt(5, seats);

            int rowsAffected = pst.executeUpdate();
            conn.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public DefaultTableModel getBookingTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"BookingID", "Day", "Time", "TrainName", "RouteFrom", "Destination", "Status", "Payment", "Seats"}, 0);

        // Retrieving user ID from mmcontroller.getUserInfo()
        MainMenuController mmcontroller = new MainMenuController();
        Object[] custinfo = mmcontroller.getUserInfo();
        int custid = (int) custinfo[0]; // Assuming index 0 contains the user ID

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT DISTINCT b.BookingID, t.day, t.time, t.TrainName, r.RouteFrom, r.Destination, b.Status, b.Paymentinfo, b.Seats " +
                                                            "FROM booking b " +
                                                            "INNER JOIN train t ON b.TrainID = t.TrainID " +
                                                            "INNER JOIN route r ON b.RouteID = r.RouteID " +
                                                            "INNER JOIN trainwagon tw ON b.TrainID = tw.TrainID " +
                                                            "WHERE b.CustomerID = ? AND b.Status = 0");
             ) {

            pst.setInt(1, custid);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int bookingID = rs.getInt("BookingID");
                    String day = rs.getString("day");
                    String time = rs.getString("time");
                    String trainName = rs.getString("TrainName");
                    String routeFrom = rs.getString("RouteFrom");
                    String destination = rs.getString("Destination");
                    int status = rs.getInt("Status");
                    int payment = rs.getInt("Paymentinfo");
                    int seats = rs.getInt("Seats");

                    model.addRow(new Object[]{bookingID, day, time, trainName, routeFrom, destination, status, payment, seats});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    
    public boolean cancelBooking(int bookingID) {
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT Status, Paymentinfo FROM booking WHERE BookingID = ?");
             PreparedStatement updatePst = conn.prepareStatement("UPDATE booking SET Status = ?, Paymentinfo = ? WHERE BookingID = ?")) {

            pst.setInt(1, bookingID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int status = rs.getInt("Status");
                    int paymentInfo = rs.getInt("Paymentinfo");

                    if (paymentInfo == 0) {
                        updatePst.setInt(1, 1); // Set Status to 1
                        updatePst.setInt(2, 0); // Set Paymentinfo to 0
                        updatePst.setInt(3, bookingID);
                        updatePst.executeUpdate();
                        return true;
                    } else if (paymentInfo == 1 && status != 1) {
                        int choice = JOptionPane.showConfirmDialog(null, "You already paid, there will be no refund. Do you want to cancel?", "Confirmation", JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {
                            updatePst.setInt(1, 1); // Set Status to 1
                            updatePst.setInt(2, 0); // Set Paymentinfo to 0
                            updatePst.setInt(3, bookingID);
                            updatePst.executeUpdate();
                            return true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This booking has already been canceled or processed.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error canceling booking.");
        }
        return false;
    }
    
    
    public boolean makePayment(int bookingID) {
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT Paymentinfo FROM booking WHERE BookingID = ?");
             PreparedStatement updatePst = conn.prepareStatement("UPDATE booking SET Paymentinfo = ? WHERE BookingID = ?")) {

            pst.setInt(1, bookingID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int paymentInfo = rs.getInt("Paymentinfo");

                    if (paymentInfo == 0) {
                        int choice = JOptionPane.showConfirmDialog(null, "Confirm Payment?", "Payment Confirmation", JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {
                            updatePst.setInt(1, 1); // Set Paymentinfo to 1
                            updatePst.setInt(2, bookingID);
                            updatePst.executeUpdate();
                            return true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have already paid.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error processing payment.");
        }
        return false;
    }
    
    
    
    public boolean isPaymentMade(int bookingID) {
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT Paymentinfo FROM booking WHERE BookingID = ?")) {

            pst.setInt(1, bookingID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int paymentInfo = rs.getInt("Paymentinfo");
                    return paymentInfo == 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error checking payment status.");
        }
        return false;
    }

    public void finishBooking(int bookingID) {
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("UPDATE booking SET Status = 1 WHERE BookingID = ?")) {

            pst.setInt(1, bookingID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error finishing booking.");
        }
    }
    
    
     public DefaultTableModel getBookingHistoryForUser(int userID) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"BookingID", "Day", "Time", "TrainName", "RouteFrom", "Destination", "Status"}, 0);

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT b.BookingID, t.day, t.time, t.TrainName, r.RouteFrom, r.Destination, b.Status, b.Paymentinfo " +
                                                            "FROM booking b " +
                                                            "INNER JOIN train t ON b.TrainID = t.TrainID " +
                                                            "INNER JOIN route r ON b.RouteID = r.RouteID " +
                                                            "WHERE b.CustomerID = ? AND b.Status = 1")) {

            pst.setInt(1, userID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int bookingID = rs.getInt("BookingID");
                    String day = rs.getString("day");
                    String time = rs.getString("time");
                    String trainName = rs.getString("TrainName");
                    String routeFrom = rs.getString("RouteFrom");
                    String destination = rs.getString("Destination");
                    int status = rs.getInt("Status");
                    int paymentInfo = rs.getInt("Paymentinfo");
                    String statusString = (status == 1 && paymentInfo == 0) ? "CANCELLED" : "FINISHED";

                    model.addRow(new Object[]{bookingID, day, time, trainName, routeFrom, destination, statusString});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}
