package com.taggy.controller;

import com.taggy.model.Session;
import com.taggy.model.User;
import com.taggy.util.AlertUtil;
import com.taggy.util.AnimationUtil;
import com.taggy.util.DatabaseConnection;
import com.taggy.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Controller for Login scene
 */
public class LoginController implements Initializable {
    
    @FXML private VBox loginCard;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Animate login card
        AnimationUtil.fadeInUp(loginCard);
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields!");
            AnimationUtil.shake(loginCard);
            return;
        }
        
        // Check credentials in database
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Login successful
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
                );
                Session.setCurrentUser(user);
                
                AlertUtil.showSuccess("Login Successful", "Welcome back, " + username + "!");
                SceneManager.switchScene("dashboard.fxml", "TAGGY - Dashboard");
            } else {
                // Login failed
                showError("Invalid username or password!");
                AnimationUtil.shake(loginCard);
            }
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Database connection error!");
            AnimationUtil.shake(loginCard);
        }
    }
    
    @FXML
    private void handleGoToSignup() {
        AnimationUtil.slideInRight(loginCard);
        SceneManager.switchScene("signup.fxml", "TAGGY - Sign Up");
    }
    
    @FXML
    private void handleBackToHome() {
        SceneManager.switchScene("homepage.fxml", "TAGGY - Smart Shopping");
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        AnimationUtil.flash(errorLabel);
    }
}
