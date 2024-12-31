/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AdminModel;
import Model.UserModel;

/**
 *
 * @author Aqil Anhein
 */
public class LoginController {
    private final UserModel userModel;
    private final AdminModel adminModel;

    public LoginController() {
        this.userModel = new UserModel();
        this.adminModel = new AdminModel();
    }

    public boolean validateLogin(String username, String password) {
        return userModel.validateUser(username, password);
    }

    public boolean registerUser(String username,String Email,String Phone, String password) {
        return userModel.registerUser(username, Email, Phone, password);
    }
    
    public boolean validateAdmin(String email,String password){
        return adminModel.validate(email, password);
    }
    
    public boolean registerAdmin(String email,String password){
        return adminModel.registerAdmin(email, password);
    }
}
