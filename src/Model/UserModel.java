/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.sql.*;

/**
 *
 * @author Aqil Anhein
 */
import Connection.DBConnection;
import java.awt.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class UserModel{
    public boolean validateUser(String email, String password) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM user WHERE Email=? AND Password=?");
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            boolean isValid = rs.next(); // Check if user exists with provided credentials

            if (isValid) {
                String Useremail = rs.getString("Email"); // Assuming Username is a column in your 'user' table
                UserSession.getInstance().setLoggedInUser(Useremail); // Set the logged-in user in the session
            }

        return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean registerUser(String username,String Email,String Phone, String password) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO user (Nama,Email,Phone,Password) VALUES (?,?,?, ?)");
            pst.setString(1, username);
            pst.setString(2, Email);
            pst.setString(3, Phone);
            pst.setString(4, password);
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
    
    public Object[] getUserInfo(){
        try {
        Connection conn = DBConnection.connect();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM user WHERE Email=?");
        pst.setString(1, UserSession.getInstance().getLoggedInUser());
        ResultSet rs = pst.executeQuery();
        boolean isValid = rs.next(); 

        if (isValid) {
            int id = rs.getInt("ID");
            String nama = rs.getString("Nama"); 
            String email = rs.getString("Email"); 
            String phone = rs.getString("Phone"); 
            String pass = rs.getString("Password"); 
            
            Object[] userInfo = new Object[] { id,nama, email, phone, pass };
            return userInfo;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
    return null;
    }
    
    public boolean editUserProfile(String newusername, String email, String phone,String password) {
        // Code to update the user's profile in the database
        // Implement SQL queries or ORM operations to update user details
        // Example: UPDATE users SET password = ?, email = ? WHERE username = ?
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("Update  user SET Nama = ?,Email = ?,Phone= ?,Password = ? WHERE Email = ? ");
            pst.setString(1, newusername);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, password);
            pst.setString(5, UserSession.getInstance().getLoggedInUser());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                UserSession.getInstance().setLoggedInUser(email); 
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean leaveReview(int BookingID,String txt, int rating){
        try{
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO review (BookingID,ReviewText,Rating) VALUES (?,?,?)");
            pst.setInt(1, BookingID);
            pst.setString(2, txt);
            pst.setInt(3, rating);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public DefaultTableModel getReviewTableModelFromUserID(int userID) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("RouteFrom");
        model.addColumn("Destination");
        model.addColumn("ReviewText");
        model.addColumn("Rating");

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT r.RouteFrom, r.Destination, rv.ReviewText, rv.Rating " +
                                                            "FROM review rv " +
                                                            "INNER JOIN booking b ON b.BookingID = rv.BookingID " +
                                                            "INNER JOIN route r ON r.RouteID = b.RouteID " +
                                                            "WHERE b.customerID = ?");
        ) {
            pst.setInt(1, userID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String from = rs.getString("RouteFrom");
                    String dest = rs.getString("Destination");
                    String txt = rs.getString("ReviewText");
                    int rating = rs.getInt("Rating");
                    model.addRow(new Object[]{from, dest, txt, rating});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

  

}
