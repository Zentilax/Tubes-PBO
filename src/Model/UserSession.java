/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Aqil Anhein
 */
public class UserSession {
    private static UserSession instance;
    private String loggedInUser; // Store the email of the logged-in user

    private UserSession() {
        // Private constructor to prevent external instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void clearSession() {
        loggedInUser = null;
        // Clear any other session-related information if needed
    }
}
