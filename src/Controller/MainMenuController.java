/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.UserModel;
import Model.BookingModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aqil Anhein
 */
public class MainMenuController implements Model.NotificationService{
    private final UserModel userModel;
    private final BookingModel bookingModel;
    
    public MainMenuController() {
        this.userModel = new UserModel();
        this.bookingModel = new BookingModel();
    }
    public boolean editUserProfile(String newUsername, String email, String phone, String password) {
        return userModel.editUserProfile(newUsername, email, phone, password);  
    }
    
    public Object[] getUserInfo(){
        return userModel.getUserInfo();
    }
    
    public DefaultTableModel searchTrains(String from, String to, String selectedClass) {
        return bookingModel.searchTrains(from, to, selectedClass);
    }
    
    public DefaultTableModel getWagonsForTrain(int trainID) {
        return bookingModel.getWagonsForTrain(trainID);
    }
    
    public boolean bookTrain(int customerID, int trainID, int wagonID, int seats) {
        return bookingModel.bookTrain(customerID, trainID, wagonID,  seats);
    }
    
    public void updateViewBookingTable(JTable bookingTable) {
        DefaultTableModel model = bookingModel.getBookingTableModel();
        bookingTable.setModel(model);
    }
    
    public void cancelBooking(int bookingID) {
        boolean canceled = bookingModel.cancelBooking(bookingID);

        if (canceled) {
            // Optionally, update the table view here if needed
            JOptionPane.showMessageDialog(null, "Booking canceled successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Error canceling booking.");
        }
    }
    
    public void Payment(int bookingID) {
        boolean paymentProcessed = bookingModel.makePayment(bookingID);

        if (paymentProcessed) {
            JOptionPane.showMessageDialog(null, "Payment confirmed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Error processing payment.");
        }
    }
    
    public void finishBooking(int bookingID) {
        boolean isPaymentMade = bookingModel.isPaymentMade(bookingID);

        if (isPaymentMade) {
            bookingModel.finishBooking(bookingID);
            JOptionPane.showMessageDialog(null, "Booking finished successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Bayar Dulu mas Bro");
        }
    }
    
    public void showBookingHistoryForUser(JTable bookingHistoryTable) {
        Object[] custinfo = getUserInfo();
        int userID = (int) custinfo[0];

        DefaultTableModel model = bookingModel.getBookingHistoryForUser(userID);
        bookingHistoryTable.setModel(model);
    }
    
    public Object[] getRoutefromTrainID(int trainid){
        return bookingModel.getRouteFromTrainID(trainid);
    }
    
    public Object[] getScedulefromTrainID(int trainid){
        return bookingModel.getScheduleFromTrainID(trainid);
    }
    
    public boolean LeaveBookingReview(int bookingid, String txt, int rating){
        return userModel.leaveReview(bookingid, txt, rating);
    }

    @Override
    public void SendNotificationSMS() {
        JOptionPane.showMessageDialog(null,"Notifikasi pesanan telah dikirim ke nomor anda");
    }

    @Override
    public void SendConfirmationEmail() {
        JOptionPane.showMessageDialog(null,"Notifikasi pesanan telah dikirim ke Email anda");
    }

    @Override
    public void SendAds() {
        //asddas
    }
    
    public void showReviewHistoryForUser(JTable bookingHistoryTable) {
        Object[] custinfo = getUserInfo();
        int userID = (int) custinfo[0];

        DefaultTableModel model = userModel.getReviewTableModelFromUserID(userID);
        bookingHistoryTable.setModel(model);
    }
    
}
