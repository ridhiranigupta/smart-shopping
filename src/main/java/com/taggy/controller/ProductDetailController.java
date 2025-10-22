package com.taggy.controller;

import com.taggy.model.Product;
import com.taggy.model.Session;
import com.taggy.util.AlertUtil;
import com.taggy.util.DatabaseConnection;
import com.taggy.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

/**
 * Controller for Product Detail view with price comparison
 */
public class ProductDetailController implements Initializable {
    
    @FXML private Button backButton;
    @FXML private Label productNameLabel;
    @FXML private Label categoryLabel;
    
    @FXML private Label amazonPriceLabel;
    @FXML private Label amazonSavingsLabel;
    @FXML private Button addAmazonButton;
    
    @FXML private Label flipkartPriceLabel;
    @FXML private Label flipkartSavingsLabel;
    @FXML private Button addFlipkartButton;
    
    @FXML private Label myntraPriceLabel;
    @FXML private Label myntraSavingsLabel;
    @FXML private Button addMyntraButton;
    
    @FXML private Label bestStoreLabel;
    @FXML private Label bestPriceLabel;
    @FXML private Label savingsAmountLabel;
    
    private static Product currentProduct;
    
    public static void setProduct(Product product) {
        currentProduct = product;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (currentProduct == null) {
            SceneManager.switchScene("dashboard.fxml", "TAGGY - Dashboard");
            return;
        }
        
        loadProductDetails();
    }
    
    private void loadProductDetails() {
        productNameLabel.setText(currentProduct.getName());
        categoryLabel.setText(currentProduct.getCategory());
        
        double amazonPrice = currentProduct.getAmazonPrice();
        double flipkartPrice = currentProduct.getFlipkartPrice();
        double myntraPrice = currentProduct.getMyntraPrice();
        double bestPrice = currentProduct.getBestPrice();
        
        // Amazon
        if (amazonPrice > 0) {
            amazonPriceLabel.setText("₹ " + String.format("%.2f", amazonPrice));
            double savings = amazonPrice - bestPrice;
            if (savings > 0) {
                amazonSavingsLabel.setText("₹ " + String.format("%.2f", savings) + " more");
                amazonSavingsLabel.setStyle("-fx-text-fill: #F44336; -fx-font-size: 13px;");
            } else if (savings == 0) {
                amazonSavingsLabel.setText("Best Price!");
                amazonSavingsLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: 700; -fx-font-size: 13px;");
            }
            addAmazonButton.setDisable(false);
        } else {
            amazonPriceLabel.setText("Not Available");
            amazonSavingsLabel.setText("");
            addAmazonButton.setDisable(true);
        }
        
        // Flipkart
        if (flipkartPrice > 0) {
            flipkartPriceLabel.setText("₹ " + String.format("%.2f", flipkartPrice));
            double savings = flipkartPrice - bestPrice;
            if (savings > 0) {
                flipkartSavingsLabel.setText("₹ " + String.format("%.2f", savings) + " more");
                flipkartSavingsLabel.setStyle("-fx-text-fill: #F44336; -fx-font-size: 13px;");
            } else if (savings == 0) {
                flipkartSavingsLabel.setText("Best Price!");
                flipkartSavingsLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: 700; -fx-font-size: 13px;");
            }
            addFlipkartButton.setDisable(false);
        } else {
            flipkartPriceLabel.setText("Not Available");
            flipkartSavingsLabel.setText("");
            addFlipkartButton.setDisable(true);
        }
        
        // Myntra
        if (myntraPrice > 0) {
            myntraPriceLabel.setText("₹ " + String.format("%.2f", myntraPrice));
            double savings = myntraPrice - bestPrice;
            if (savings > 0) {
                myntraSavingsLabel.setText("₹ " + String.format("%.2f", savings) + " more");
                myntraSavingsLabel.setStyle("-fx-text-fill: #F44336; -fx-font-size: 13px;");
            } else if (savings == 0) {
                myntraSavingsLabel.setText("Best Price!");
                myntraSavingsLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: 700; -fx-font-size: 13px;");
            }
            addMyntraButton.setDisable(false);
        } else {
            myntraPriceLabel.setText("Not Available");
            myntraSavingsLabel.setText("");
            addMyntraButton.setDisable(true);
        }
        
        // Best Deal
        bestStoreLabel.setText(currentProduct.getBestStore());
        bestPriceLabel.setText("₹ " + String.format("%.2f", bestPrice));
        
        double maxPrice = Math.max(amazonPrice, Math.max(flipkartPrice, myntraPrice));
        double totalSavings = maxPrice - bestPrice;
        if (totalSavings > 0) {
            savingsAmountLabel.setText("Save ₹ " + String.format("%.2f", totalSavings));
        } else {
            savingsAmountLabel.setText("");
        }
    }
    
    @FXML
    private void handleBack() {
        SceneManager.switchScene("dashboard.fxml", "TAGGY - Dashboard");
    }
    
    @FXML
    private void handleAddAmazon() {
        addToCart("Amazon", currentProduct.getAmazonPrice());
    }
    
    @FXML
    private void handleAddFlipkart() {
        addToCart("Flipkart", currentProduct.getFlipkartPrice());
    }
    
    @FXML
    private void handleAddMyntra() {
        addToCart("Myntra", currentProduct.getMyntraPrice());
    }
    
    private void addToCart(String store, double price) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            // Check if product already exists in cart for this store
            String checkQuery = "SELECT id, quantity FROM cart WHERE user_id = ? AND product_id = ? AND store = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, Session.getCurrentUser().getId());
            checkStmt.setInt(2, currentProduct.getId());
            checkStmt.setString(3, store);
            
            var rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Update quantity
                int cartId = rs.getInt("id");
                int currentQty = rs.getInt("quantity");
                
                String updateQuery = "UPDATE cart SET quantity = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, currentQty + 1);
                updateStmt.setInt(2, cartId);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                // Insert new
                String insertQuery = "INSERT INTO cart (user_id, product_id, store, quantity) VALUES (?, ?, ?, 1)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, Session.getCurrentUser().getId());
                insertStmt.setInt(2, currentProduct.getId());
                insertStmt.setString(3, store);
                insertStmt.executeUpdate();
                insertStmt.close();
            }
            
            rs.close();
            checkStmt.close();
            
            AlertUtil.showSuccess("Added to Cart", 
                currentProduct.getName() + " from " + store + " has been added to your cart!");
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error", "Failed to add product to cart!");
        }
    }
}
