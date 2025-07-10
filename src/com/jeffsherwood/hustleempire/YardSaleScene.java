package com.jeffsherwood.hustleempire;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class YardSaleScene extends Scene {

    // The inner SaleItem class definition has been COMPLETELY REMOVED from this file.
    // SaleItem is now a separate class in SaleItem.java.

    // List of all possible items
    private static final List<SaleItem> ALL_POSSIBLE_ITEMS = Arrays.asList(
        new SaleItem("Antique oil lamp", 30.0, 75.0),
        new SaleItem("Framed art work", 50.0, 125.0),
        new SaleItem("Vintage video game system", 75.0, 150.0),
        new SaleItem("Dusty old comic book", 15.0, 60.0),
        new SaleItem("Retro action figure collection", 40.0, 110.0),
        new SaleItem("Mystery box (could be anything!)", 20.0, 200.0), // Higher risk/reward
        new SaleItem("Old vinyl records", 25.0, 85.0),
        new SaleItem("Antique teacup set", 35.0, 90.0)
    );

    // Chance for item loss (e.g., something got broke/didn't sell)
    private static final int LOSS_CHANCE_PERCENT = 20; // 20% chance of an issue
    private static final int XP_GAIN_PER_SALE = 50; // Set XP gain to 50

    public YardSaleScene() {
        super("Neighborhood Yard Sale", "Multiple houses having a yard sale in close proximity.");
    }

    @Override
    public void play(Player player, Scanner scanner, Random random) {
        System.out.println("\nWelcome to the " + name + ": " + description);
        System.out.println("You just got here and you're already finding great deals!");

        // Main loop for item selection
        while (true) {
            System.out.println("\nHere are some items you found. Choose wisely...");

            // Temporary list of items for this round to ensure uniqueness
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
            System.out.println((currentItems.length + 1) + ". Leave Yard Sale without buying anything");

            // Get user choice
            int choice;
            while (true) {
                System.out.print("Input your selection below (1-" + (currentItems.length + 1) + "): ");
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
                System.out.println("Aight, you’re leaving the Yard Sale empty-handed.");
                return; // Exit the scene
            }

            SaleItem selectedItem = currentItems[choice - 1]; // Adjust for 0-based array index
            // Using getter methods for encapsulation
            String itemName = selectedItem.getName();
            double cost = selectedItem.getBasePrice();
            double maxResell = selectedItem.getMaxResell();

            // Bargaining
            System.out.println("\nNice choice! The " + itemName + " is $" + String.format("%.2f", cost) + ".");
            System.out.println("Would you like to bargain for a lower price? (y/n)");
            String betterPrice = scanner.nextLine().toLowerCase().trim();

            if (betterPrice.startsWith("y")) {
                // Player's bargaining skill vs. a random roll (higher bargaining means better chance)
                if (random.nextInt(10) < player.getBargaining()) {
                    double discount = 1.0 - (player.getBargaining() * 0.05); // 5% off per Bargaining point (max 50% at 10 skill)
                    cost *= discount;
                    System.out.println("Dope haggle! You got it for $" + String.format("%.2f", cost) + "!");
                } else {
                    System.out.println("No luck, hustler. Price stays $" + String.format("%.2f", cost) + ".");
                }
            }

            // Confirm purchase
            System.out.println("Buy the " + itemName + " for $" + String.format("%.2f", cost) + "? (y/n)");
            if (!scanner.nextLine().toLowerCase().toLowerCase().startsWith("y")) {
                System.out.println("You pass on the deal. Back to Browse...");
                continue; // Loop back to item selection
            }

            // Check funds
            if (player.getBankroll() < cost) {
                System.out.println("Not enough cash, hustler! Pick something cheaper.");
                continue;
            }

            // Buy item
            player.setBankroll(player.getBankroll() - cost);
            System.out.println("Snagged the " + itemName + " for $" + String.format("%.2f", cost) + ".");

            // --- Random Loss Event ---
            if (random.nextInt(100) < LOSS_CHANCE_PERCENT) {
                int misfortuneType = random.nextInt(2); // 0 for broke, 1 for didn't sell
                if (misfortuneType == 0) {
                    System.out.println("\nOH NO! While transporting, your " + itemName + " accidentally broke! It's worthless now.");
                    System.out.println("You lose the $" + String.format("%.2f", cost) + " you paid.");
                    player.addExperience(5); // Still get a little XP for the lesson learned
                    System.out.println("Lesson learned! +5 XP. Better luck next time!");
                } else {
                    System.out.println("\nBummer! You listed your " + itemName + " online, but nobody wanted it. It just wouldn't sell!");
                    System.out.println("You lose the $" + String.format("%.2f", cost) + " you paid.");
                    player.addExperience(5); // Still get a little XP for the lesson learned
                    System.out.println("Tough market! +5 XP. Gotta know your audience!");
                }
                return; // Exit scene after a loss event, regardless of profit
            }

            // Sell item (only if no loss event occurred)
            double sellPrice = maxResell * (0.5 + random.nextDouble() * 0.5); // 50-100% of max resell
            if (random.nextInt(10) < player.getMarketSense()) {
                sellPrice *= 1.3; // 30% bonus for high Market Sense
                System.out.println("Killer eBay flip! Sold the " + itemName + " for $" + String.format("%.2f", sellPrice) + "!");
            } else {
                System.out.println("Sold the " + itemName + " on eBay for $" + String.format("%.2f", sellPrice) + ".");
            }

            // Update player bankroll and experience
            player.setBankroll(player.getBankroll() + sellPrice);
            player.addExperience(XP_GAIN_PER_SALE); // Use constant for XP gain
            System.out.println("Profit: $" + String.format("%.2f", (sellPrice - cost)) + " | +" + XP_GAIN_PER_SALE + " XP");
            return; // Exit scene after a successful transaction
        }
    }
}