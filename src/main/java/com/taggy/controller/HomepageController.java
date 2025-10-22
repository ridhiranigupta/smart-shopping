package com.taggy.controller;

import com.taggy.util.AnimationUtil;
import com.taggy.util.SceneManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Homepage scene
 */
public class HomepageController implements Initializable {
    
    @FXML private Label logoLabel;
    @FXML private VBox logoSection;
    @FXML private VBox welcomeSection;
    @FXML private HBox buttonsSection;
    @FXML private HBox featuresSection;
    @FXML private Button loginButton;
    @FXML private Button signupButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Animate logo section with fade in
        AnimationUtil.fadeIn(logoSection, 800);
        
        // Continuous pulse on logo
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(e -> AnimationUtil.continuousPulse(logoLabel, Integer.MAX_VALUE));
        pause.play();
        
        // Animate welcome section with delay
        PauseTransition welcomePause = new PauseTransition(Duration.millis(500));
        welcomePause.setOnFinished(e -> AnimationUtil.fadeInUp(welcomeSection));
        welcomePause.play();
        
        // Animate buttons section with delay
        PauseTransition buttonsPause = new PauseTransition(Duration.millis(800));
        buttonsPause.setOnFinished(e -> AnimationUtil.fadeInUp(buttonsSection));
        buttonsPause.play();
        
        // Animate features section with delay
        PauseTransition featuresPause = new PauseTransition(Duration.millis(1100));
        featuresPause.setOnFinished(e -> AnimationUtil.fadeInUp(featuresSection));
        featuresPause.play();
        
        // Add hover effects to buttons
        AnimationUtil.addHoverScale(loginButton);
        AnimationUtil.addHoverScale(signupButton);
    }
    
    @FXML
    private void handleLogin() {
        AnimationUtil.zoomIn(loginButton);
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(e -> SceneManager.switchScene("login.fxml", "TAGGY - Login"));
        pause.play();
    }
    
    @FXML
    private void handleSignup() {
        AnimationUtil.zoomIn(signupButton);
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(e -> SceneManager.switchScene("signup.fxml", "TAGGY - Sign Up"));
        pause.play();
    }
}
