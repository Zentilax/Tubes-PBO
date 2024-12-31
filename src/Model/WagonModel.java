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

public class WagonModel {
    public boolean AddWagon(String name,String type,Integer seats) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO wagon (WagonName,WagonType,Seats) VALUES (?,?,?)");
            pst.setString(1, name);
            pst.setString(2, type);
            pst.setInt(3, seats);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
