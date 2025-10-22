package com.taggy.model;

/**
 * Product model class
 */
public class Product {
    private int id;
    private String name;
    private String category;
    private double amazonPrice;
    private double flipkartPrice;
    private double myntraPrice;
    private String imageUrl;
    
    // Constructors
    public Product() {}
    
    public Product(int id, String name, String category, double amazonPrice, 
                   double flipkartPrice, double myntraPrice, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amazonPrice = amazonPrice;
        this.flipkartPrice = flipkartPrice;
        this.myntraPrice = myntraPrice;
        this.imageUrl = imageUrl;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public double getAmazonPrice() {
        return amazonPrice;
    }
    
    public void setAmazonPrice(double amazonPrice) {
        this.amazonPrice = amazonPrice;
    }
    
    public double getFlipkartPrice() {
        return flipkartPrice;
    }
    
    public void setFlipkartPrice(double flipkartPrice) {
        this.flipkartPrice = flipkartPrice;
    }
    
    public double getMyntraPrice() {
        return myntraPrice;
    }
    
    public void setMyntraPrice(double myntraPrice) {
        this.myntraPrice = myntraPrice;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * Get the lowest price among all stores
     */
    public double getBestPrice() {
        double min = Double.MAX_VALUE;
        
        if (amazonPrice > 0 && amazonPrice < min) min = amazonPrice;
        if (flipkartPrice > 0 && flipkartPrice < min) min = flipkartPrice;
        if (myntraPrice > 0 && myntraPrice < min) min = myntraPrice;
        
        return min == Double.MAX_VALUE ? 0 : min;
    }
    
    /**
     * Get the store with best price
     */
    public String getBestStore() {
        double bestPrice = getBestPrice();
        
        if (amazonPrice == bestPrice && amazonPrice > 0) return "Amazon";
        if (flipkartPrice == bestPrice && flipkartPrice > 0) return "Flipkart";
        if (myntraPrice == bestPrice && myntraPrice > 0) return "Myntra";
        
        return "N/A";
    }
    
    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', category='" + category + "'}";
    }
}
