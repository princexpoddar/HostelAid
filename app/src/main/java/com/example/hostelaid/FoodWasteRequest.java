package com.example.hostelaid;

import java.util.List;

public class FoodWasteRequest {
    private String hostel;
    private List<FoodItem> items;
    private String timestamp;
    
    public FoodWasteRequest(String hostel, List<FoodItem> items, String timestamp) {
        this.hostel = hostel;
        this.items = items;
        this.timestamp = timestamp;
    }
    
    public String getHostel() { return hostel; }
    public List<FoodItem> getItems() { return items; }
    public String getTimestamp() { return timestamp; }
}

