package com.jeffsherwood.hustleempire;

public class SaleItem {
    // Fields for item details
    private String name;
    private double basePrice;
    private double maxResell;

    // Constructor to create a new sale item
    public SaleItem(String name, double basePrice, double maxResell) {
        this.name = name;
        this.basePrice = basePrice;
        this.maxResell = maxResell;
    }

    // Get item name
    public String getName() {
        return name;
    }

    // Get item base price
    public double getBasePrice() {
        return basePrice;
    }

    // Get item maximum resell value
    public double getMaxResell() {
        return maxResell;
    }

    // Display item details
    @Override
    public String toString() {
        return name + " - Price: $" + String.format("%.2f", basePrice) + " - Resell Value: up to $" + String.format("%.2f", maxResell);
    }
}