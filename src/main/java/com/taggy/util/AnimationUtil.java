package com.taggy.util;

import animatefx.animation.*;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Utility class for animations in TAGGY application
 */
public class AnimationUtil {
    
    /**
     * Fade in animation for any node
     */
    public static void fadeIn(Node node) {
        new FadeIn(node).play();
    }
    
    /**
     * Fade in animation with custom duration
     */
    public static void fadeIn(Node node, double durationMillis) {
        FadeTransition ft = new FadeTransition(Duration.millis(durationMillis), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
    
    /**
     * Fade out animation
     */
    public static void fadeOut(Node node) {
        new FadeOut(node).play();
    }
    
    /**
     * Slide in from right
     */
    public static void slideInRight(Node node) {
        new SlideInRight(node).play();
    }
    
    /**
     * Slide in from left
     */
    public static void slideInLeft(Node node) {
        new SlideInLeft(node).play();
    }
    
    /**
     * Slide in from up
     */
    public static void slideInUp(Node node) {
        new SlideInUp(node).play();
    }
    
    /**
     * Bounce in animation for cart items
     */
    public static void bounceIn(Node node) {
        new BounceIn(node).play();
    }
    
    /**
     * Shake animation for invalid input
     */
    public static void shake(Node node) {
        new Shake(node).play();
    }
    
    /**
     * Pulse animation for logo or important elements
     */
    public static void pulse(Node node) {
        new Pulse(node).play();
    }
    
    /**
     * Continuous pulse animation (repeating)
     */
    public static void continuousPulse(Node node, int repeatCount) {
        ScaleTransition st = new ScaleTransition(Duration.millis(1000), node);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.1);
        st.setToY(1.1);
        st.setAutoReverse(true);
        st.setCycleCount(repeatCount);
        st.play();
    }
    
    /**
     * Glow effect animation
     */
    public static void glow(Node node) {
        Glow glow = new Glow(0.8);
        node.setEffect(glow);
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
            new KeyFrame(Duration.millis(500), new KeyValue(glow.levelProperty(), 0.8)),
            new KeyFrame(Duration.millis(1000), new KeyValue(glow.levelProperty(), 0.0))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Add hover scale effect to a node
     */
    public static void addHoverScale(Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
            
            // Add glow shadow
            DropShadow shadow = new DropShadow();
            shadow.setColor(Color.web("#f78fb3"));
            shadow.setRadius(20);
            shadow.setSpread(0.5);
            node.setEffect(shadow);
        });
        
        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
            
            node.setEffect(null);
        });
    }
    
    /**
     * Gradient movement animation for background
     */
    public static void animateGradient(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), node);
        tt.setFromX(0);
        tt.setToX(50);
        tt.setAutoReverse(true);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.play();
    }
    
    /**
     * Fade in and slide up for cards
     */
    public static void fadeInUp(Node node) {
        new FadeInUp(node).play();
    }
    
    /**
     * Zoom in animation
     */
    public static void zoomIn(Node node) {
        new ZoomIn(node).play();
    }
    
    /**
     * Flash animation for highlighting
     */
    public static void flash(Node node) {
        new Flash(node).play();
    }
}
