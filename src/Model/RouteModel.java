/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Aqil Anhein
 */
public class RouteModel {
    public boolean AddNewRoute(String name,String from,String destination,Integer duration) {
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO route (RouteName,RouteFrom,Destination,Duration) VALUES (?,?,?,?)");
            pst.setString(1, name);
            pst.setString(2, from);
            pst.setString(3, destination);
            pst.setInt(4, duration);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
