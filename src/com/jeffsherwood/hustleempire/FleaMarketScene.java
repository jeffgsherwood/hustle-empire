package com.jeffsherwood.hustleempire;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FleaMarketScene extends Scene {

    // Inner class to represent an item (re-used from YardSaleScene, could be moved to Scene abstract class)
    private static class SaleItem {
        String name;
        double basePrice;
        double maxResell;

        public SaleItem(String name, double basePrice, double maxResell) {
            this.name = name;
            this.basePrice = basePrice;
            this.maxResell = maxResell;
        }

        @Override
        public String toString() {
            return name + " - Price: $" + String.format("%.2f", basePrice) + " - Resell Value: up to $" + String.format("%.2f", maxResell);
        }
    }

    // List of all possible items specific to a Flea Market
    private static final List<SaleItem> ALL_POSSIBLE_ITEMS = Arrays.asList(
        new SaleItem("Rare Vinyl Record (Limited Edition)", 80.0, 200.0), // High value
        new SaleItem("Vintage Collectible Toy (MIB)", 120.0, 350.0), // Higher value
        new SaleItem("Hand-crafted Artisan Jewelry", 60.0, 180.0),
        new SaleItem("Box of Assorted Electronics (Untested)", 40.0, 250.0), // High risk, high reward
        new SaleItem("Antique Silverware Set", 90.0, 280.0),
        new SaleItem("First Edition Comic Book (Slightly Damaged)", 150.0, 500.0), // Very high value, good profit potential
        new SaleItem("Old Photography Camera", 70.0, 190.0),
        new SaleItem("Designer Clothing (Used)", 100.0, 220.0)
    );

    // Chance for item loss (e.g., something got broke/didn't sell)
    private static final int LOSS_CHANCE_PERCENT = 15; // Slightly lower risk than Yard Sale
    private static final int XP_GAIN_PER_SALE = 75; // More XP for bigger deals

    public FleaMarketScene() {
        super("Busy Flea Market", "A sprawling market filled with vendors, unique finds, and shrewd dealers.");
    }

    @Override
    public void play(Player player, Scanner scanner, Random random) {
        System.out.println("\nWelcome to the " + name + "! " + description);
        System.out.println("The air is thick with anticipation. What gems will you unearth today?");

        // Main loop for item selection
        while (true) {
            System.out.println("\nHere are some intriguing items you've spotted among the stalls...");

            List<SaleItem> currentAvailableItems = new ArrayList<>(ALL_POSSIBLE_ITEMS);
            Collections.shuffle(currentAvailableItems, random); // Shuffle to randomize order

            // Select 3 unique items for the current visit
            SaleItem[] currentItems = new SaleItem[Math.min(3, currentAvailableItems.size())];
            for (int i = 0; i < currentItems.length; i++) {
                currentItems[i] = currentAvailableItems.get(i); // Take first 3 after shuffle
            }

            // Display current items
            for (int i = 0; i < currentItems.length; i++) {
                System.out.println((i + 1) + ". " + currentItems[i]);
            }
            System.out.println((currentItems.length + 1) + ". No luck today. Time to hit another stall.");

            // Get user choice
            int choice;
            while (true) {
                System.out.print("What catches your eye? (1-" + (currentItems.length + 1) + "): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Clear newline
                    if (choice >= 1 && choice <= (currentItems.length + 1)) {
                        break; // Valid input
                    } else {
                        System.out.println("Invalid choice! Pick a number between 1 and " + (currentItems.length + 1) + "!");
                    }
                } else {
                    System.out.println("Enter a number!");
                    scanner.next(); // Clear invalid input
                    scanner.nextLine(); // Consume leftover newline after scanner.next()
                }
            }

            // Exit if leaving
            if (choice == (currentItems.length + 1)) {
                System.out.println("You wander away from the bustling market, empty-handed this time.");
                return; // Exit the scene
            }

            SaleItem selectedItem = currentItems[choice - 1]; // Adjust for 0-based array index
            String itemName = selectedItem.name;
            double cost = selectedItem.basePrice;
            double maxResell = selectedItem.maxResell;

            // Bargaining
            System.out.println("\nYou're looking at the " + itemName + ". The vendor is asking $" + String.format("%.2f", cost) + ".");
            System.out.println("Do you want to haggle for a better deal? (y/n)");
            String betterPrice = scanner.nextLine().toLowerCase().trim();

            if (betterPrice.startsWith("y")) {
                // Player's bargaining skill vs. a random roll (higher bargaining means better chance)
                if (random.nextInt(10) < player.getBargaining()) {
                    double discount = 1.0 - (player.getBargaining() * 0.04); // Slightly less aggressive discount (e.g., 4% vs 5%)
                    cost *= discount;
                    System.out.println("Smooth move! You talked them down to $" + String.format("%.2f", cost) + "!");
                } else {
                    System.out.println("The vendor holds firm. Price stays $" + String.format("%.2f", cost) + ".");
                }
            }

            // Confirm purchase
            System.out.println("Commit to buying the " + itemName + " for $" + String.format("%.2f", cost) + "? (y/n)");
            if (!scanner.nextLine().toLowerCase().startsWith("y")) {
                System.out.println("You decide against it. Plenty more treasures to find...");
                continue; // Loop back to item selection
            }

            // Check funds
            if (player.getBankroll() < cost) {
                System.out.println("Your wallet feels a bit light for that one, hustler! Find something cheaper.");
                continue;
            }

            // Buy item
            player.setBankroll(player.getBankroll() - cost);
            System.out.println("Purchased the " + itemName + " for $" + String.format("%.2f", cost) + ".");

            // --- Random Loss Event ---
            if (random.nextInt(100) < LOSS_CHANCE_PERCENT) {
                int misfortuneType = random.nextInt(2); // 0 for broke, 1 for didn't sell
                if (misfortuneType == 0) {
                    System.out.println("\nUH OH! On the way out, your " + itemName + " got damaged in the crowd! It's ruined.");
                    System.out.println("You've lost the $" + String.format("%.2f", cost) + " you paid for it.");
                    player.addExperience(10); // Still get a little more XP for bigger lessons
                    System.out.println("Tough break! +10 XP. Better vigilance next time!");
                } else {
                    System.out.println("\nWell, that's a bust. You couldn't find a buyer for your " + itemName + ".");
                    System.out.println("You've lost the $" + String.format("%.2f", cost) + " you paid.");
                    player.addExperience(10); // Still get a little more XP for bigger lessons
                    System.out.println("Market's fickle! +10 XP. Gotta spot those trends!");
                }
                return; // Exit scene after a loss event
            }

            // Sell item (only if no loss event occurred)
            double sellPrice = maxResell * (0.6 + random.nextDouble() * 0.4); // 60-100% of max resell (slightly better odds)
            if (random.nextInt(10) < player.getMarketSense()) {
                sellPrice *= 1.4; // 40% bonus for high Market Sense (higher stakes, higher rewards)
                System.out.println("BOOM! You found the perfect buyer! Sold the " + itemName + " for a whopping $" + String.format("%.2f", sellPrice) + "!");
            } else {
                System.out.println("You listed the " + itemName + " and it sold for $" + String.format("%.2f", sellPrice) + ".");
            }

            // Update player bankroll and experience
            player.setBankroll(player.getBankroll() + sellPrice);
            player.addExperience(XP_GAIN_PER_SALE); // Use constant for XP gain
            System.out.println("Net Profit: $" + String.format("%.2f", (sellPrice - cost)) + " | +" + XP_GAIN_PER_SALE + " XP");
            return; // Exit scene after a successful transaction
        }
    }
}