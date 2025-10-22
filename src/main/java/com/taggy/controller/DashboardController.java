package com.taggy.controller;

import com.taggy.model.Product;
import com.taggy.model.Session;
import com.taggy.util.AlertUtil;
import com.taggy.util.AnimationUtil;
import com.taggy.util.DatabaseConnection;
import com.taggy.util.SceneManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for Dashboard scene
 */
public class DashboardController implements Initializable {
    
    @FXML private VBox sidebar;
    @FXML private VBox userInfoBox;
    @FXML private Label usernameLabel;
    @FXML private Label walletLabel;
    @FXML private Label pageTitle;
    @FXML private TextField searchField;
    @FXML private VBox mainContent;
    @FXML private Label cartBadge;
    
    @FXML private Button homeButton;
    @FXML private Button compareButton;
    @FXML private Button cartButton;
    @FXML private Button walletButton;
    @FXML private Button logoutButton;
    
    private String currentView = "home";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Check if user is logged in
        if (!Session.isLoggedIn()) {
            AlertUtil.showError("Error", "Please login first!");
            SceneManager.switchScene("homepage.fxml", "TAGGY");
            return;
        }
        
        // Set user info
        usernameLabel.setText(Session.getCurrentUser().getUsername());
        loadWalletBalance();
        updateCartBadge();
        
        // Add click handler for profile
        userInfoBox.setOnMouseClicked(e -> handleProfile());
        
        // Animate sidebar
        AnimationUtil.slideInLeft(sidebar);
        
        // Load home view
        loadHomeView();
    }
    
    private void loadWalletBalance() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT balance FROM wallet WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getCurrentUser().getId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                walletLabel.setText("₹ " + String.format("%.2f", balance));
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateCartBadge() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT COUNT(*) as count FROM cart WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getCurrentUser().getId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    cartBadge.setText(String.valueOf(count));
                    cartBadge.setVisible(true);
                } else {
                    cartBadge.setVisible(false);
                }
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleHome() {
        setActiveButton(homeButton);
        pageTitle.setText("Home");
        currentView = "home";
        loadHomeView();
    }
    
    @FXML
    private void handleCompare() {
        setActiveButton(compareButton);
        pageTitle.setText("Compare Prices");
        currentView = "compare";
        loadCompareView();
    }
    
    @FXML
    private void handleCart() {
        setActiveButton(cartButton);
        pageTitle.setText("My Cart");
        currentView = "cart";
        loadCartView();
    }
    
    @FXML
    private void handleWallet() {
        setActiveButton(walletButton);
        pageTitle.setText("Wallet");
        currentView = "wallet";
        loadWalletView();
    }
    
    @FXML
    private void handleProfile() {
        SceneManager.switchScene("profile.fxml", "TAGGY - My Profile");
    }
    
    @FXML
    private void handleLogout() {
        boolean confirm = AlertUtil.showConfirmation("Logout", "Are you sure you want to logout?");
        if (confirm) {
            Session.logout();
            SceneManager.switchScene("homepage.fxml", "TAGGY - Smart Shopping");
        }
    }
    
    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            loadSearchResults(searchTerm);
        }
    }
    
    private void setActiveButton(Button activeBtn) {
        // Remove active class from all buttons
        homeButton.getStyleClass().remove("sidebar-button-active");
        compareButton.getStyleClass().remove("sidebar-button-active");
        cartButton.getStyleClass().remove("sidebar-button-active");
        walletButton.getStyleClass().remove("sidebar-button-active");
        
        // Add active class to clicked button
        activeBtn.getStyleClass().add("sidebar-button-active");
    }
    
    private void loadHomeView() {
        mainContent.getChildren().clear();
        
        // Welcome Section
        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER_LEFT);
        Label welcomeLabel = new Label("Welcome back, " + Session.getCurrentUser().getUsername() + "! 👋");
        welcomeLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: 700; -fx-text-fill: #2d2d2d;");
        Label subtitleLabel = new Label("Discover the best deals on your favorite products");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        welcomeBox.getChildren().addAll(welcomeLabel, subtitleLabel);
        mainContent.getChildren().add(welcomeBox);
        
        AnimationUtil.fadeInUp(welcomeBox);
        
        // Categories Section
        Label categoriesLabel = new Label("Browse by Category");
        categoriesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #2d2d2d; -fx-padding: 20 0 10 0;");
        mainContent.getChildren().add(categoriesLabel);
        
        // First row of categories
        HBox categoriesBox1 = new HBox(20);
        categoriesBox1.setAlignment(Pos.CENTER_LEFT);
        
        VBox electronicsCard = createCategoryCard("Electronics", "📱", "#f78fb3");
        VBox fashionCard = createCategoryCard("Fashion", "�", "#f8a5c2");
        VBox homeCard = createCategoryCard("Home", "🏠", "#e77f67");
        
        categoriesBox1.getChildren().addAll(electronicsCard, fashionCard, homeCard);
        mainContent.getChildren().add(categoriesBox1);
        
        // Second row of categories
        HBox categoriesBox2 = new HBox(20);
        categoriesBox2.setAlignment(Pos.CENTER_LEFT);
        categoriesBox2.setPadding(new Insets(0, 0, 0, 0));
        
        VBox sportsCard = createCategoryCard("Sports", "⚽", "#ffc0cb");
        VBox booksCard = createCategoryCard("Books", "📚", "#c9487d");
        
        categoriesBox2.getChildren().addAll(sportsCard, booksCard);
        mainContent.getChildren().add(categoriesBox2);
        
        // Featured Products
        Label featuredLabel = new Label("Featured Products");
        featuredLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #2d2d2d; -fx-padding: 20 0 10 0;");
        mainContent.getChildren().add(featuredLabel);
        
        loadProducts(null);
    }
    
    private VBox createCategoryCard(String name, String emoji, String color) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15px; " +
                     "-fx-padding: 30px 50px; -fx-cursor: hand; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 3);");
        
        Label emojiLabel = new Label(emoji);
        emojiLabel.setStyle("-fx-font-size: 48px;");
        
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600; -fx-text-fill: " + color + ";");
        
        card.getChildren().addAll(emojiLabel, nameLabel);
        
        card.setOnMouseClicked(e -> loadCategoryView(name));
        AnimationUtil.addHoverScale(card);
        AnimationUtil.fadeInUp(card);
        
        return card;
    }
    
    private void loadCategoryView(String category) {
        mainContent.getChildren().clear();
        
        // Add category header
        HBox headerBox = new HBox(15);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        Button backButton = new Button("← Back to Home");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #f78fb3; " +
                           "-fx-font-weight: 600; -fx-font-size: 14px; -fx-cursor: hand; " +
                           "-fx-underline: true;");
        backButton.setOnAction(e -> loadHomeView());
        
        Label categoryLabel = new Label(category + " Products");
        categoryLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-text-fill: #2d2d2d;");
        
        headerBox.getChildren().addAll(backButton, categoryLabel);
        mainContent.getChildren().add(headerBox);
        
        // Load products for this category
        loadProducts(category);
    }
    
    private void loadProducts(String category) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query;
            PreparedStatement stmt;
            
            if (category == null) {
                query = "SELECT * FROM products LIMIT 6";
                stmt = conn.prepareStatement(query);
            } else {
                query = "SELECT * FROM products WHERE category = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, category);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            GridPane productGrid = new GridPane();
            productGrid.setHgap(20);
            productGrid.setVgap(20);
            productGrid.setPadding(new Insets(10, 0, 0, 0));
            
            int col = 0;
            int row = 0;
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("amazon_price"),
                    rs.getDouble("flipkart_price"),
                    rs.getDouble("myntra_price"),
                    rs.getString("image_url")
                );
                
                VBox productCard = createProductCard(product);
                productGrid.add(productCard, col, row);
                
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
            
            mainContent.getChildren().add(productGrid);
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private VBox createProductCard(Product product) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(280);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15px; " +
                     "-fx-padding: 20px; -fx-cursor: hand; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 3);");
        
        // Make card clickable to view product details
        card.setOnMouseClicked(e -> viewProductDetails(product));
        
        // Category badge
        Label categoryBadge = new Label(product.getCategory());
        categoryBadge.setStyle("-fx-background-color: rgba(247, 143, 179, 0.2); " +
                              "-fx-background-radius: 10px; -fx-padding: 5px 12px; " +
                              "-fx-font-size: 11px; -fx-font-weight: 600; -fx-text-fill: #c9487d;");
        
        // Product name
        Label nameLabel = new Label(product.getName());
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(250);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: #2d2d2d;");
        
        // Best price
        Label priceLabel = new Label("₹ " + String.format("%.2f", product.getBestPrice()));
        priceLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: 700; -fx-text-fill: #4CAF50;");
        
        Label storeLabel = new Label("Best: " + product.getBestStore());
        storeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        
        // Add to cart button
        Button addButton = new Button("Add to Cart");
        addButton.setStyle("-fx-background-color: linear-gradient(to right, #f78fb3, #e77f67); " +
                          "-fx-text-fill: white; -fx-font-weight: 600; -fx-background-radius: 20px; " +
                          "-fx-padding: 8px 20px; -fx-cursor: hand;");
        addButton.setOnAction(e -> {
            e.consume(); // Prevent card click event
            addToCart(product);
        });
        
        // View details button
        Button viewButton = new Button("Compare Prices");
        viewButton.setStyle("-fx-background-color: transparent; " +
                           "-fx-text-fill: #f78fb3; -fx-font-weight: 600; -fx-background-radius: 20px; " +
                           "-fx-border-color: #f78fb3; -fx-border-width: 2px; -fx-border-radius: 20px; " +
                           "-fx-padding: 8px 20px; -fx-cursor: hand;");
        viewButton.setOnAction(e -> {
            e.consume(); // Prevent card click event
            viewProductDetails(product);
        });
        
        card.getChildren().addAll(categoryBadge, nameLabel, priceLabel, storeLabel, viewButton, addButton);
        
        AnimationUtil.addHoverScale(card);
        AnimationUtil.fadeInUp(card);
        
        return card;
    }
    
    private void viewProductDetails(Product product) {
        ProductDetailController.setProduct(product);
        SceneManager.switchScene("productdetail.fxml", "TAGGY - " + product.getName());
    }
    
    private void addToCart(Product product) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO cart (user_id, product_id, store, quantity) VALUES (?, ?, ?, 1)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getCurrentUser().getId());
            stmt.setInt(2, product.getId());
            stmt.setString(3, product.getBestStore());
            
            stmt.executeUpdate();
            stmt.close();
            
            AlertUtil.showSuccess("Added to Cart", product.getName() + " has been added to your cart!");
            updateCartBadge();
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error", "Failed to add product to cart!");
        }
    }
    
    private void loadSearchResults(String searchTerm) {
        mainContent.getChildren().clear();
        
        Label resultsLabel = new Label("Search results for: \"" + searchTerm + "\"");
        resultsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #2d2d2d;");
        mainContent.getChildren().add(resultsLabel);
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            GridPane productGrid = new GridPane();
            productGrid.setHgap(20);
            productGrid.setVgap(20);
            productGrid.setPadding(new Insets(10, 0, 0, 0));
            
            int col = 0;
            int row = 0;
            boolean hasResults = false;
            
            while (rs.next()) {
                hasResults = true;
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("amazon_price"),
                    rs.getDouble("flipkart_price"),
                    rs.getDouble("myntra_price"),
                    rs.getString("image_url")
                );
                
                VBox productCard = createProductCard(product);
                productGrid.add(productCard, col, row);
                
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
            
            if (!hasResults) {
                Label noResultsLabel = new Label("No products found matching your search.");
                noResultsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #999999; -fx-padding: 20 0 0 0;");
                mainContent.getChildren().add(noResultsLabel);
            } else {
                mainContent.getChildren().add(productGrid);
            }
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadCompareView() {
        mainContent.getChildren().clear();
        
        Label infoLabel = new Label("Compare prices across different platforms and find the best deals!");
        infoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        mainContent.getChildren().add(infoLabel);
        
        // Load comparison table
        loadComparisonTable();
    }
    
    private void loadComparisonTable() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM products ORDER BY name";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            TableView<Product> table = new TableView<>();
            table.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
            
            TableColumn<Product, String> nameCol = new TableColumn<>("Product");
            nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
            nameCol.setPrefWidth(250);
            
            TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
            categoryCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
            categoryCol.setPrefWidth(100);
            
            TableColumn<Product, String> amazonCol = new TableColumn<>("Amazon");
            amazonCol.setCellValueFactory(data -> {
                double price = data.getValue().getAmazonPrice();
                return new javafx.beans.property.SimpleStringProperty(price > 0 ? "₹ " + String.format("%.2f", price) : "N/A");
            });
            amazonCol.setPrefWidth(120);
            
            TableColumn<Product, String> flipkartCol = new TableColumn<>("Flipkart");
            flipkartCol.setCellValueFactory(data -> {
                double price = data.getValue().getFlipkartPrice();
                return new javafx.beans.property.SimpleStringProperty(price > 0 ? "₹ " + String.format("%.2f", price) : "N/A");
            });
            flipkartCol.setPrefWidth(120);
            
            TableColumn<Product, String> myntraCol = new TableColumn<>("Myntra");
            myntraCol.setCellValueFactory(data -> {
                double price = data.getValue().getMyntraPrice();
                return new javafx.beans.property.SimpleStringProperty(price > 0 ? "₹ " + String.format("%.2f", price) : "N/A");
            });
            myntraCol.setPrefWidth(120);
            
            TableColumn<Product, String> bestCol = new TableColumn<>("Best Deal");
            bestCol.setCellValueFactory(data -> {
                Product p = data.getValue();
                return new javafx.beans.property.SimpleStringProperty(p.getBestStore() + " - ₹ " + String.format("%.2f", p.getBestPrice()));
            });
            bestCol.setPrefWidth(150);
            bestCol.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: 700;");
            
            table.getColumns().addAll(nameCol, categoryCol, amazonCol, flipkartCol, myntraCol, bestCol);
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("amazon_price"),
                    rs.getDouble("flipkart_price"),
                    rs.getDouble("myntra_price"),
                    rs.getString("image_url")
                );
                table.getItems().add(product);
            }
            
            mainContent.getChildren().add(table);
            AnimationUtil.fadeInUp(table);
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadCartView() {
        // This will be implemented in a separate method - showing cart items
        mainContent.getChildren().clear();
        
        Label infoLabel = new Label("Your shopping cart");
        infoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        mainContent.getChildren().add(infoLabel);
        
        loadCartItems();
    }
    
    private void loadCartItems() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT c.id, c.product_id, c.store, c.quantity, p.name, p.amazon_price, p.flipkart_price, p.myntra_price " +
                          "FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getCurrentUser().getId());
            
            ResultSet rs = stmt.executeQuery();
            
            VBox cartBox = new VBox(15);
            double totalAmount = 0;
            boolean hasItems = false;
            
            while (rs.next()) {
                hasItems = true;
                int cartId = rs.getInt("id");
                String productName = rs.getString("name");
                String store = rs.getString("store");
                int quantity = rs.getInt("quantity");
                
                double price = 0;
                if (store.equals("Amazon")) price = rs.getDouble("amazon_price");
                else if (store.equals("Flipkart")) price = rs.getDouble("flipkart_price");
                else if (store.equals("Myntra")) price = rs.getDouble("myntra_price");
                
                double itemTotal = price * quantity;
                totalAmount += itemTotal;
                
                HBox cartItem = createCartItemCard(cartId, productName, store, quantity, price, itemTotal);
                cartBox.getChildren().add(cartItem);
            }
            
            if (!hasItems) {
                Label emptyLabel = new Label("Your cart is empty. Start shopping!");
                emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #999999; -fx-padding: 30 0;");
                mainContent.getChildren().add(emptyLabel);
            } else {
                mainContent.getChildren().add(cartBox);
                
                // Total section
                HBox totalBox = new HBox(20);
                totalBox.setAlignment(Pos.CENTER_RIGHT);
                totalBox.setPadding(new Insets(20, 0, 0, 0));
                
                Label totalLabel = new Label("Total: ₹ " + String.format("%.2f", totalAmount));
                totalLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: 700; -fx-text-fill: #f78fb3;");
                
                Button checkoutButton = new Button("Checkout");
                checkoutButton.setStyle("-fx-background-color: linear-gradient(to right, #f78fb3, #e77f67); " +
                                       "-fx-text-fill: white; -fx-font-weight: 700; -fx-font-size: 16px; " +
                                       "-fx-background-radius: 25px; -fx-padding: 12px 40px; -fx-cursor: hand;");
                final double finalTotal = totalAmount;
                checkoutButton.setOnAction(e -> handleCheckout(finalTotal));
                
                totalBox.getChildren().addAll(totalLabel, checkoutButton);
                mainContent.getChildren().add(totalBox);
            }
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private HBox createCartItemCard(int cartId, String productName, String store, int quantity, double price, double total) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15px; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 3);");
        
        VBox infoBox = new VBox(8);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);
        
        Label nameLabel = new Label(productName);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");
        
        Label storeLabel = new Label("Store: " + store + " | Qty: " + quantity);
        storeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #666666;");
        
        Label priceLabel = new Label("₹ " + String.format("%.2f", price) + " each");
        priceLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #999999;");
        
        infoBox.getChildren().addAll(nameLabel, storeLabel, priceLabel);
        
        Label totalLabel = new Label("₹ " + String.format("%.2f", total));
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #4CAF50;");
        
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; " +
                             "-fx-background-radius: 20px; -fx-padding: 8px 20px; -fx-cursor: hand;");
        removeButton.setOnAction(e -> removeFromCart(cartId));
        
        card.getChildren().addAll(infoBox, totalLabel, removeButton);
        AnimationUtil.fadeInUp(card);
        
        return card;
    }
    
    private void removeFromCart(int cartId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM cart WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cartId);
            
            stmt.executeUpdate();
            stmt.close();
            
            updateCartBadge();
            loadCartView(); // Refresh cart view
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleCheckout(double totalAmount) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            // Check wallet balance
            String balanceQuery = "SELECT balance FROM wallet WHERE user_id = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery);
            balanceStmt.setInt(1, Session.getCurrentUser().getId());
            ResultSet rs = balanceStmt.executeQuery();
            
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                
                if (balance < totalAmount) {
                    AlertUtil.showError("Insufficient Balance", "Your wallet balance is ₹ " + String.format("%.2f", balance) + 
                                       ". Please add funds to complete this purchase.");
                    rs.close();
                    balanceStmt.close();
                    return;
                }
                
                // Deduct from wallet
                String updateQuery = "UPDATE wallet SET balance = balance - ? WHERE user_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setDouble(1, totalAmount);
                updateStmt.setInt(2, Session.getCurrentUser().getId());
                updateStmt.executeUpdate();
                updateStmt.close();
                
                // Clear cart
                String clearQuery = "DELETE FROM cart WHERE user_id = ?";
                PreparedStatement clearStmt = conn.prepareStatement(clearQuery);
                clearStmt.setInt(1, Session.getCurrentUser().getId());
                clearStmt.executeUpdate();
                clearStmt.close();
                
                // Show success
                AlertUtil.showSuccess("Order Placed!", "Your order has been placed successfully! ₹ " + 
                                     String.format("%.2f", totalAmount) + " deducted from wallet.");
                
                loadWalletBalance();
                updateCartBadge();
                loadCartView();
            }
            
            rs.close();
            balanceStmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error", "Failed to complete checkout!");
        }
    }
    
    private void loadWalletView() {
        mainContent.getChildren().clear();
        
        VBox walletBox = new VBox(30);
        walletBox.setAlignment(Pos.TOP_CENTER);
        walletBox.setPadding(new Insets(20));
        
        // Balance Card
        VBox balanceCard = new VBox(15);
        balanceCard.setAlignment(Pos.CENTER);
        balanceCard.setMaxWidth(500);
        balanceCard.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4CAF50, #8BC34A); " +
                            "-fx-background-radius: 20px; -fx-padding: 40px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.4), 20, 0.6, 0, 5);");
        
        Label balanceTitle = new Label("Wallet Balance");
        balanceTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 600; -fx-text-fill: white;");
        
        Label balanceAmount = new Label();
        balanceAmount.setStyle("-fx-font-size: 48px; -fx-font-weight: 700; -fx-text-fill: white;");
        
        // Get current balance
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT balance FROM wallet WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getCurrentUser().getId());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceAmount.setText("₹ " + String.format("%.2f", balance));
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        balanceCard.getChildren().addAll(balanceTitle, balanceAmount);
        AnimationUtil.fadeInUp(balanceCard);
        AnimationUtil.continuousPulse(balanceAmount, Integer.MAX_VALUE);
        
        // Deposit Section
        VBox depositCard = new VBox(20);
        depositCard.setAlignment(Pos.CENTER);
        depositCard.setMaxWidth(500);
        depositCard.setStyle("-fx-background-color: white; -fx-background-radius: 20px; " +
                            "-fx-padding: 30px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0.4, 0, 5);");
        
        Label depositTitle = new Label("Add Funds");
        depositTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #2d2d2d;");
        
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to add");
        amountField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 15px; " +
                            "-fx-padding: 12px 20px; -fx-font-size: 16px; -fx-border-width: 0;");
        amountField.setPrefWidth(300);
        
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button add500 = new Button("+ ₹500");
        Button add1000 = new Button("+ ₹1000");
        Button add5000 = new Button("+ ₹5000");
        
        String quickButtonStyle = "-fx-background-color: rgba(247, 143, 179, 0.2); " +
                                 "-fx-text-fill: #f78fb3; -fx-font-weight: 600; " +
                                 "-fx-background-radius: 15px; -fx-padding: 8px 16px; -fx-cursor: hand;";
        
        add500.setStyle(quickButtonStyle);
        add1000.setStyle(quickButtonStyle);
        add5000.setStyle(quickButtonStyle);
        
        add500.setOnAction(e -> amountField.setText("500"));
        add1000.setOnAction(e -> amountField.setText("1000"));
        add5000.setOnAction(e -> amountField.setText("5000"));
        
        buttonBox.getChildren().addAll(add500, add1000, add5000);
        
        Button depositButton = new Button("Deposit Funds");
        depositButton.setStyle("-fx-background-color: linear-gradient(to right, #f78fb3, #e77f67); " +
                              "-fx-text-fill: white; -fx-font-weight: 700; -fx-font-size: 16px; " +
                              "-fx-background-radius: 25px; -fx-padding: 12px 40px; -fx-cursor: hand;");
        depositButton.setOnAction(e -> handleDeposit(amountField.getText(), balanceAmount));
        
        depositCard.getChildren().addAll(depositTitle, amountField, buttonBox, depositButton);
        AnimationUtil.fadeInUp(depositCard);
        
        walletBox.getChildren().addAll(balanceCard, depositCard);
        mainContent.getChildren().add(walletBox);
    }
    
    private void handleDeposit(String amountText, Label balanceLabel) {
        try {
            double amount = Double.parseDouble(amountText);
            
            if (amount <= 0) {
                AlertUtil.showError("Invalid Amount", "Please enter a valid amount!");
                return;
            }
            
            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE wallet SET balance = balance + ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, Session.getCurrentUser().getId());
            
            stmt.executeUpdate();
            stmt.close();
            
            // Get updated balance
            String balanceQuery = "SELECT balance FROM wallet WHERE user_id = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery);
            balanceStmt.setInt(1, Session.getCurrentUser().getId());
            ResultSet rs = balanceStmt.executeQuery();
            
            if (rs.next()) {
                double newBalance = rs.getDouble("balance");
                balanceLabel.setText("₹ " + String.format("%.2f", newBalance));
                AnimationUtil.flash(balanceLabel);
            }
            
            rs.close();
            balanceStmt.close();
            
            AlertUtil.showSuccess("Success", "₹ " + String.format("%.2f", amount) + " added to your wallet!");
            loadWalletBalance();
            
        } catch (NumberFormatException e) {
            AlertUtil.showError("Invalid Amount", "Please enter a valid number!");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error", "Failed to add funds!");
        }
    }
}
