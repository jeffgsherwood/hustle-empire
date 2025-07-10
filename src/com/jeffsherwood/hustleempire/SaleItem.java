package com.jeffsherwood.hustleempire;

public class SaleItem {
    // Declare fields as private for encapsulation
    private String name;
    private double basePrice;
    private double maxResell;

    // This is the constructor that YardSaleScene is looking for
    public SaleItem(String name, double basePrice, double maxResell) {
        this.name = name;
        this.basePrice = basePrice;
        this.maxResell = maxResell;
    }

    // Public getter methods to access the private fields
    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getMaxResell() {
        return maxResell;
    }

    @Override
    public String toString() {
        return name + " - Price: $" + String.format("%.2f", basePrice) + " - Resell Value: up to $" + String.format("%.2f", maxResell);
    }
}