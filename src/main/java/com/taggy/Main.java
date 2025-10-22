package com.taggy;

import com.taggy.util.DatabaseConnection;
import com.taggy.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class for TAGGY
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Test database connection
            if (!DatabaseConnection.testConnection()) {
                System.err.println("⚠️  Warning: Database connection failed!");
                System.err.println("Please ensure MySQL is running and database 'taggy_db' exists.");
                System.err.println("Run the SQL scripts in database/schema.sql and database/sample_data.sql");
            }
            
            // Set up primary stage
            primaryStage.setTitle("TAGGY - Smart Shopping");
            primaryStage.setWidth(1200);
            primaryStage.setHeight(750);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(600);
            primaryStage.setResizable(true);
            
            // Initialize Scene Manager
            SceneManager.setPrimaryStage(primaryStage);
            
            // Load homepage
            SceneManager.switchScene("homepage.fxml", "TAGGY - Smart Shopping");
            
            primaryStage.show();
            
            System.out.println("✨ TAGGY Application started successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error starting application:");
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
        // Close database connection on exit
        DatabaseConnection.closeConnection();
        System.out.println("👋 TAGGY Application closed.");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
