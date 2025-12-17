package com.example.hostelaid;

public class FoodItem {
    private String itemName;
    private double quantity;

    public FoodItem(String itemName, double quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getQuantity() {
        return quantity;
    }
} 