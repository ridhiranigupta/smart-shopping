package com.taggy.model;

/**
 * Session manager to track logged-in user
 */
public class Session {
    private static User currentUser;
    
    /**
     * Set the current logged-in user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    /**
     * Get the current logged-in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Check if a user is logged in
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Clear the current session (logout)
     */
    public static void logout() {
        currentUser = null;
    }
}
