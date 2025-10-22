package com.taggy.controller;

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
 * Controller for Signup scene
 */
public class SignupController implements Initializable {
    
    @FXML private VBox signupCard;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Animate signup card
        AnimationUtil.fadeInUp(signupCard);
    }
    
    @FXML
    private void handleSignup() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        
        // Validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Please fill in all fields!");
            AnimationUtil.shake(signupCard);
            return;
        }
        
        if (!email.contains("@")) {
            showError("Please enter a valid email!");
            AnimationUtil.shake(signupCard);
            return;
        }
        
        if (password.length() < 6) {
            showError("Password must be at least 6 characters!");
            AnimationUtil.shake(signupCard);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match!");
            AnimationUtil.shake(signupCard);
            return;
        }
        
        // Check if username already exists
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            // Check existing username
            String checkQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                showError("Username or email already exists!");
                AnimationUtil.shake(signupCard);
                rs.close();
                checkStmt.close();
                return;
            }
            
            rs.close();
            checkStmt.close();
            
            // Insert new user
            String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, username);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);
            
            int rowsAffected = insertStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated user ID
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    
                    // Create wallet for new user
                    String walletQuery = "INSERT INTO wallet (user_id, balance) VALUES (?, 0.00)";
                    PreparedStatement walletStmt = conn.prepareStatement(walletQuery);
                    walletStmt.setInt(1, userId);
                    walletStmt.executeUpdate();
                    walletStmt.close();
                }
                generatedKeys.close();
                
                // Sign up successful
                AlertUtil.showSuccess("Account Created", "Welcome to TAGGY, " + username + "! Please login to continue.");
                SceneManager.switchScene("login.fxml", "TAGGY - Login");
            }
            
            insertStmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Database error occurred!");
            AnimationUtil.shake(signupCard);
        }
    }
    
    @FXML
    private void handleGoToLogin() {
        AnimationUtil.slideInLeft(signupCard);
        SceneManager.switchScene("login.fxml", "TAGGY - Login");
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
