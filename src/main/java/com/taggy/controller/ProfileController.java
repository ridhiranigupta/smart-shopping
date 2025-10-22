package com.taggy.controller;

import com.taggy.model.Session;
import com.taggy.util.AlertUtil;
import com.taggy.util.DatabaseConnection;
import com.taggy.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controller for Profile editing view
 */
public class ProfileController implements Initializable {
    
    @FXML private Button backButton;
    @FXML private Label usernameDisplay;
    @FXML private Label emailDisplay;
    
    @FXML private TextField fullNameField;
    @FXML private TextField phoneField;
    @FXML private DatePicker dobPicker;
    
    @FXML private TextField address1Field;
    @FXML private TextField address2Field;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField pinCodeField;
    @FXML private TextField countryField;
    @FXML private TextField alternatePhoneField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Session.isLoggedIn()) {
            AlertUtil.showError("Error", "Please login first!");
            SceneManager.switchScene("homepage.fxml", "TAGGY");
            return;
        }
        
        // Display current user info
        usernameDisplay.setText(Session.getCurrentUser().getUsername());
        emailDisplay.setText(Session.getCurrentUser().getEmail());
        
        // Load profile data
        loadProfileData();
    }
    
    private void loadProfileData() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getCurrentUser().getId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Personal Information
                String fullName = rs.getString("full_name");
                if (fullName != null) fullNameField.setText(fullName);
                
                String phone = rs.getString("phone");
                if (phone != null) phoneField.setText(phone);
                
                java.sql.Date dob = rs.getDate("dob");
                if (dob != null) dobPicker.setValue(dob.toLocalDate());
                
                // Shipping Address
                String address1 = rs.getString("address1");
                if (address1 != null) address1Field.setText(address1);
                
                String address2 = rs.getString("address2");
                if (address2 != null) address2Field.setText(address2);
                
                String city = rs.getString("city");
                if (city != null) cityField.setText(city);
                
                String state = rs.getString("state");
                if (state != null) stateField.setText(state);
                
                String pinCode = rs.getString("pin_code");
                if (pinCode != null) pinCodeField.setText(pinCode);
                
                String country = rs.getString("country");
                if (country != null) countryField.setText(country);
                else countryField.setText("India");
                
                String alternatePhone = rs.getString("alternate_phone");
                if (alternatePhone != null) alternatePhoneField.setText(alternatePhone);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleSave() {
        // Validate required fields
        if (phoneField.getText().trim().isEmpty()) {
            AlertUtil.showError("Validation Error", "Phone number is required!");
            return;
        }
        
        String phone = phoneField.getText().trim();
        if (!phone.matches("\\d{10}")) {
            AlertUtil.showError("Validation Error", "Please enter a valid 10-digit phone number!");
            return;
        }
        
        String pinCode = pinCodeField.getText().trim();
        if (!pinCode.isEmpty() && !pinCode.matches("\\d{6}")) {
            AlertUtil.showError("Validation Error", "Please enter a valid 6-digit PIN code!");
            return;
        }
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            String query = "UPDATE users SET " +
                          "full_name = ?, phone = ?, dob = ?, " +
                          "address1 = ?, address2 = ?, city = ?, state = ?, " +
                          "pin_code = ?, country = ?, alternate_phone = ? " +
                          "WHERE id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, fullNameField.getText().trim().isEmpty() ? null : fullNameField.getText().trim());
            stmt.setString(2, phone);
            
            LocalDate dob = dobPicker.getValue();
            if (dob != null) {
                stmt.setDate(3, java.sql.Date.valueOf(dob));
            } else {
                stmt.setDate(3, null);
            }
            
            stmt.setString(4, address1Field.getText().trim().isEmpty() ? null : address1Field.getText().trim());
            stmt.setString(5, address2Field.getText().trim().isEmpty() ? null : address2Field.getText().trim());
            stmt.setString(6, cityField.getText().trim().isEmpty() ? null : cityField.getText().trim());
            stmt.setString(7, stateField.getText().trim().isEmpty() ? null : stateField.getText().trim());
            stmt.setString(8, pinCode.isEmpty() ? null : pinCode);
            stmt.setString(9, countryField.getText().trim().isEmpty() ? "India" : countryField.getText().trim());
            stmt.setString(10, alternatePhoneField.getText().trim().isEmpty() ? null : alternatePhoneField.getText().trim());
            stmt.setInt(11, Session.getCurrentUser().getId());
            
            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            
            if (rowsAffected > 0) {
                AlertUtil.showSuccess("Success", "Profile updated successfully!");
                // Go back to dashboard
                SceneManager.switchScene("dashboard.fxml", "TAGGY - Dashboard");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error", "Failed to update profile: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleBack() {
        SceneManager.switchScene("dashboard.fxml", "TAGGY - Dashboard");
    }
}
