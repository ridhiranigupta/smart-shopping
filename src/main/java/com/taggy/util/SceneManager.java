package com.taggy.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Scene manager for handling scene transitions in TAGGY
 */
public class SceneManager {
    
    private static Stage primaryStage;
    
    /**
     * Set the primary stage
     */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    
    /**
     * Load and switch to a new scene with fade transition
     */
    public static void switchScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();
            
            Scene currentScene = primaryStage.getScene();
            
            if (currentScene != null) {
                // Fade out current scene
                AnimationUtil.fadeOut(currentScene.getRoot());
                
                // Wait a bit, then switch
                javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(300));
                pause.setOnFinished(e -> {
                    Scene newScene = new Scene(root);
                    newScene.getStylesheets().add(SceneManager.class.getResource("/css/styles.css").toExternalForm());
                    primaryStage.setScene(newScene);
                    primaryStage.setTitle(title);
                    AnimationUtil.fadeIn(root, 500);
                });
                pause.play();
            } else {
                // First scene, no transition
                Scene newScene = new Scene(root);
                newScene.getStylesheets().add(SceneManager.class.getResource("/css/styles.css").toExternalForm());
                primaryStage.setScene(newScene);
                primaryStage.setTitle(title);
                AnimationUtil.fadeIn(root, 500);
            }
            
        } catch (IOException e) {
            System.err.println("Error loading scene: " + fxmlFile);
            e.printStackTrace();
        }
    }
    
    /**
     * Get the primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
