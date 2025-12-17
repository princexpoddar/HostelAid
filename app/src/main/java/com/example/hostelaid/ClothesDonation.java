package com.example.hostelaid;

import java.util.List;

public class ClothesDonation {
    private String hostel;
    private List<ClothItem> items;
    private String timestamp;
    
    public ClothesDonation(String hostel, List<ClothItem> items, String timestamp) {
        this.hostel = hostel;
        this.items = items;
        this.timestamp = timestamp;
    }
    
    public String getHostel() { return hostel; }
    public List<ClothItem> getItems() { return items; }
    public String getTimestamp() { return timestamp; }
}

