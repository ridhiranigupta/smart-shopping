module com.taggy {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    // JFoenix
    requires com.jfoenix;
    
    // FontAwesome
    requires de.jensd.fx.glyphs.fontawesome;
    
    // MySQL
    requires java.sql;
    
    // AnimateFX
    requires AnimateFX;
    
    // Open packages to JavaFX for reflection
    opens com.taggy to javafx.fxml;
    opens com.taggy.controller to javafx.fxml;
    opens com.taggy.model to javafx.base;
    
    // Export packages
    exports com.taggy;
    exports com.taggy.controller;
    exports com.taggy.model;
    exports com.taggy.util;
}
