package com.taggy.model;

/**
 * CartItem model class
 */
public class CartItem {
    private int id;
    private int userId;
    private int productId;
    private String productName;
    private String store;
    private int quantity;
    private double price;
    
    // Constructors
    public CartItem() {}
    
    public CartItem(int id, int userId, int productId, String productName, 
                    String store, int quantity, double price) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getStore() {
        return store;
    }
    
    public void setStore(String store) {
        this.store = store;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Calculate total price for this cart item
     */
    public double getTotal() {
        return price * quantity;
    }
    
    @Override
    public String toString() {
        return "CartItem{productName='" + productName + "', store='" + store + 
               "', quantity=" + quantity + ", price=" + price + "}";
    }
}
