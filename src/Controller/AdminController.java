/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.WagonModel;
import Model.RouteModel;
import Model.TrainModel;
import Model.AdminModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
/**
 *
 * @author Aqil Anhein
 */
import javax.swing.table.DefaultTableModel;
public class AdminController {
    private final WagonModel wagonModel;
    private final RouteModel routeModel;
    private final TrainModel trainModel;
    private final AdminModel adminModel;
    
    public AdminController(){
        this.wagonModel = new WagonModel();
        this.routeModel = new RouteModel();
        this.adminModel = new AdminModel();
        this.trainModel = new TrainModel();
    }
    
    public boolean addNewWagon(String name,String type,Integer seats){
        return wagonModel.AddWagon(name, type, seats);
    }
    public boolean addTrainWagon(int TrainID,int WagonID){
        return adminModel.addTrainWagon(TrainID, WagonID);
    }
    public boolean UpdateCertainTrainCapacity(int TrainID){
        return adminModel.updateCertainTrainCapacity(TrainID);
    }
    
    public boolean UpdateAllTrainCapacity(){
        return adminModel.updateAllTrainCapacities();
    }
    
    public boolean addNewRoute(String name,String from,String dest,Integer dur){
        return routeModel.AddNewRoute(name, from, dest, dur);
    }
    
    public DefaultTableModel getRoutesTableModel() {
        return trainModel.getRoutesTableModel();
    }
    public DefaultTableModel getTrainsTableModel() {
        return adminModel.getTrainsTableModel();
    }
    public DefaultTableModel getWagonsTableModel() {
        return adminModel.getWagonsTableModel();
    }
    
    public boolean addTrain(JTable table,String name,String day,String time) {
        int routeID = trainModel.getRouteIDFromTable(table);

        if (routeID != -1) { 
            return trainModel.addTrain(routeID,name,day,time);
        } else {
            return false; 
        }
        
    }
}
