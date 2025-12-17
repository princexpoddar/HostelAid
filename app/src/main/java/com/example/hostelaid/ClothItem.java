package com.example.hostelaid;

public class ClothItem {
    private String clothType;
    private String size;
    private int quantity;

    public ClothItem(String clothType, String size, int quantity) {
        this.clothType = clothType;
        this.size = size;
        this.quantity = quantity;
    }

    public String getClothType() {
        return clothType;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }
} 