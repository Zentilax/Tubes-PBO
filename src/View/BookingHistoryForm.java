/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Controller.MainMenuController;
import java.awt.Color;
import javax.swing.JOptionPane;
import sun.reflect.generics.visitor.Reifier;
/**
 *
 * @author Aqil Anhein
 */
public class BookingHistoryForm extends javax.swing.JFrame {
private final MainMenuController mmcontroller;
    /**
     * Creates new form BookingHistoryForm
     */
    public BookingHistoryForm() {
        initComponents();
        Color col = new Color(248,247,243);
        getContentPane().setBackground(col);
        this.mmcontroller = new MainMenuController();
        mmcontroller.showBookingHistoryForUser(BookingHistoryTable);
        Object[] custinfo = mmcontroller.getUserInfo();
        int custid = (int)custinfo[0];
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ReviewDialog = new javax.swing.JDialog();
        TextReviewField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        SaveRatingButton = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BookingHistoryTable = new javax.swing.JTable();
        BackButton = new javax.swing.JButton();
        ReviewButton = new javax.swing.JButton();

        TextReviewField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextReviewFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Review : ");

        jLabel3.setText("Rating : ");

        SaveRatingButton.setText("Submit");
        SaveRatingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveRatingButtonActionPerformed(evt);
            }
        });

        jSlider1.setMaximum(5);
        jSlider1.setMinimum(1);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel5.setText("Give Feedback");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Keterangan");

        javax.swing.GroupLayout ReviewDialogLayout = new javax.swing.GroupLayout(ReviewDialog.getContentPane());
        ReviewDialog.getContentPane().setLayout(ReviewDialogLayout);
        ReviewDialogLayout.setHorizontalGroup(
            ReviewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReviewDialogLayout.createSequentialGroup()
                .addGroup(ReviewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReviewDialogLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(ReviewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(TextReviewField, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ReviewDialogLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReviewDialogLayout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(SaveRatingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReviewDialogLayout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jLabel5)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReviewDialogLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(184, 184, 184))
        );
        ReviewDialogLayout.setVerticalGroup(
            ReviewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReviewDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(50, 50, 50)
                .addGroup(ReviewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(TextReviewField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(SaveRatingButton)
                .addGap(57, 57, 57))
        );

        jLabel4.setText("Puas");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Booking History");

        BookingHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(BookingHistoryTable);

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        ReviewButton.setText("Review");
        ReviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReviewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ReviewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackButton)
                    .addComponent(ReviewButton))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        UserMainMenu mainmenu = new UserMainMenu();
        mainmenu.setVisible(true);
        
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ReviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReviewButtonActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = BookingHistoryTable.getSelectedRow();
        String status = (String)BookingHistoryTable.getValueAt(selectedRowIndex, 6);
        if (status == "FINISHED" && selectedRowIndex != -1){
            int BookingID = (int)BookingHistoryTable.getValueAt(selectedRowIndex,0);
            this.setVisible(false);
            ReviewDialog.setSize(450,350);
            ReviewDialog.setVisible(true);
        }else if (selectedRowIndex != -1){
            JOptionPane.showMessageDialog(this, "Tidak bisa mengulas pesanan yang dibatalkan");
        }else{
            JOptionPane.showMessageDialog(this, "Tolong pilih booking");
        }
        
    }//GEN-LAST:event_ReviewButtonActionPerformed

    private void SaveRatingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveRatingButtonActionPerformed
        // TODO add your handling code here:
        String txt = TextReviewField.getText();
        int rating = (int) jSlider1.getValue();
        int selectedRowIndex = BookingHistoryTable.getSelectedRow();
        int BookingID = (int)BookingHistoryTable.getValueAt(selectedRowIndex, 0);
        if (mmcontroller.LeaveBookingReview(BookingID, txt, rating)){
            JOptionPane.showMessageDialog(this, "Review Successful");             
            this.dispose();
            ReviewDialog.dispose();
            UserMainMenu mainMenu = new UserMainMenu();
            mainMenu.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "Insertion Unsuccessful");
            }
    }//GEN-LAST:event_SaveRatingButtonActionPerformed

    private void TextReviewFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextReviewFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextReviewFieldActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        if (jSlider1.getValueIsAdjusting()) {
            if (jSlider1.getValue() == 1) {
                jLabel6.setText("Tidak Puas");
            } else if (jSlider1.getValue() == 2) {
                jLabel6.setText("Kurang Puas");
            } else if (jSlider1.getValue() == 3) {
                jLabel6.setText("Netral");
            } else if (jSlider1.getValue() == 4) {
                jLabel6.setText("Puas");
            } else if (jSlider1.getValue() == 5) {
                jLabel6.setText("Sangat Puas");
            }
        }
    }//GEN-LAST:event_jSlider1StateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookingHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookingHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookingHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookingHistoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookingHistoryForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JTable BookingHistoryTable;
    private javax.swing.JButton ReviewButton;
    private javax.swing.JDialog ReviewDialog;
    private javax.swing.JButton SaveRatingButton;
    private javax.swing.JTextField TextReviewField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables
}
