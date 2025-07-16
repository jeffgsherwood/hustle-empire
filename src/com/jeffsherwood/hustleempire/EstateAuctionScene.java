package com.jeffsherwood.hustleempire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EstateAuctionScene extends Scene {

    // List of items available at the auction
    private static final List<SaleItem> ALL_POSSIBLE_ITEMS = Arrays.asList(
        new SaleItem("Vintage Comic Book (fair condition)", 5.0, 35.0),
        new SaleItem("Old Porcelain Doll (chipped)", 8.0, 40.0),
        new SaleItem("Box of Antique Buttons", 1.0, 15.0),
        new SaleItem("Used Set of Golf Clubs", 10.0, 50.0),
        new SaleItem("Retro Cassette Player (untested)", 3.0, 25.0),
        new SaleItem("Collection of Old Postcards", 2.0, 20.0),
        new SaleItem("Silver-Plated Teapot", 7.0, 45.0),
        new SaleItem("Small Wooden Carving", 4.0, 30.0)
    );

    // Experience points for participating and winning
    private static final int XP_GAIN_PER_AUCTION_PARTICIPATION = 15;
    private static final int XP_GAIN_PER_SALE = 30;

    // Constructor to set up the scene
    public EstateAuctionScene() {
        super("Estate Auction", "Step into the grand halls of an estate auction, where hidden treasures await the keen eye and swift bid!");
    }

    @Override
    public void play(Player player, Scanner scanner, Random random) {
        // Welcome message and player stats
        System.out.println("\nWelcome to the " + name + "! " + description);
        System.out.println("The air is thick with anticipation. What gems will you unearth today?");
        System.out.println("\n--- Your Current Status ---");
        System.out.println("Current Bankroll: $" + String.format("%.2f", player.getBankroll()));
        System.out.println("Bargaining Skill: " + player.getBargaining() + " | Market Sense Skill: " + player.getMarketSense());
        System.out.println("Current XP: " + player.getExperience() + " / " + player.getExperienceRequiredForNextLevel());

        // Pick 3 random items for the auction
        List<SaleItem> availableItems = new ArrayList<>(ALL_POSSIBLE_ITEMS);
        Collections.shuffle(availableItems, random);
        List<SaleItem> itemsForAuction = availableItems.subList(0, Math.min(3, availableItems.size()));

        if (itemsForAuction.isEmpty()) {
            System.out.println("Looks like there are no items up for auction today. Better luck next time!");
            return;
        }

        // Show available items
        System.out.println("\nItems up for bid:");
        for (int i = 0; i < itemsForAuction.size(); i++) {
            SaleItem item = itemsForAuction.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (Est. Resell: $" + String.format("%.2f", item.getMaxResell()) + ", Starting Price around: $" + String.format("%.2f", item.getBasePrice()) + ")");
        }

        // Get player's item choice
        SaleItem itemToAuction;
        int choice;
        while (true) {
            System.out.println("\nEnter the number of the item you wish to bid on (or 0 to leave):");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice == 0) {
                    System.out.println("You quietly slip out of the auction house.");
                    return;
                }
                if (choice > 0 && choice <= itemsForAuction.size()) {
                    itemToAuction = itemsForAuction.get(choice - 1);
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + itemsForAuction.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Start bidding for chosen item
        System.out.println("\n--- Bidding for: " + itemToAuction.getName() + " ---");

        // Adjust starting bid based on player's bargaining skill
        double currentBid = itemToAuction.getBasePrice() * (1.0 - (player.getBargaining() / 20.0));
        currentBid = Math.max(itemToAuction.getBasePrice() * 0.5, currentBid);
        currentBid = Math.ceil(currentBid / 1.0) * 1.0;
        System.out.println("Starting Bid: $" + String.format("%.2f", currentBid));

        // Set bid increment
        double bidIncrement = Math.ceil(itemToAuction.getBasePrice() * (0.1 + random.nextDouble() * 0.05));
        bidIncrement = Math.max(1.0, bidIncrement);

        // Set NPC max bids
        double npc1MaxBid = itemToAuction.getMaxResell() * (0.4 + random.nextDouble() * 0.3) * (1.0 - (player.getMarketSense() * 0.03));
        double npc2MaxBid = itemToAuction.getMaxResell() * (0.4 + random.nextDouble() * 0.3) * (1.0 - (player.getMarketSense() * 0.03));
        npc1MaxBid = Math.max(itemToAuction.getBasePrice() * 0.8, npc1MaxBid);
        npc2MaxBid = Math.max(itemToAuction.getBasePrice() * 0.8, npc2MaxBid);

        // Create random NPC names
        String npc1Name = "Competitor #" + random.nextInt(1001);
        String npc2Name = "Competitor #" + random.nextInt(1001);

        // Estimate item value based on player's market sense
        double playerMarketEstimate = itemToAuction.getMaxResell() * (0.7 + (player.getMarketSense() / 20.0));
        playerMarketEstimate = Math.ceil(playerMarketEstimate / 1.0) * 1.0;

        System.out.println("Your current bankroll: $" + String.format("%.2f", player.getBankroll()));
        System.out.println("Your market sense tells you this could resell for around $" + String.format("%.2f", playerMarketEstimate) + ".");

        String lastBidder = "Auctioneer";
        boolean playerWonBid = false;

        // Bidding loop
        while (true) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Current highest bid: $" + String.format("%.2f", currentBid) + " (by " + lastBidder + ")");
            System.out.println("Minimum next bid: $" + String.format("%.2f", currentBid + bidIncrement));
            System.out.println("-------------------------------------------");
            System.out.println("Enter your bid (or 0 to pass):");

            // Get player's bid
            double playerBidInput = 0;
            while (true) {
                String bidInput = scanner.nextLine();
                try {
                    playerBidInput = Double.parseDouble(bidInput);
                    if (playerBidInput == 0) {
                        break;
                    }
                    if (playerBidInput < currentBid + bidIncrement) {
                        System.out.println("Your bid must be at least $" + String.format("%.2f", currentBid + bidIncrement) + ". Please bid again.");
                    } else if (playerBidInput > player.getBankroll()) {
                        System.out.println("You don't have enough money for that bid! Current bankroll: $" + String.format("%.2f", player.getBankroll()) + ".");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            if (playerBidInput == 0) {
                System.out.println("You pass on this item.");
                System.out.println("The auctioneer looks around... \"Going once... Going twice... Sold to " + lastBidder + "!\"");
                playerWonBid = false;
                break;
            }

            // Player bids
            currentBid = playerBidInput;
            lastBidder = player.getName();
            System.out.println(player.getName() + " bids $" + String.format("%.2f", currentBid) + "!");
            playerWonBid = true;

            // NPC bidding
            List<String> npcNames = new ArrayList<>(Arrays.asList(npc1Name, npc2Name));
            Collections.shuffle(npcNames, random);
            boolean anyNpcBiddedThisRound = false;

            for (String npcDisplayName : npcNames) {
                if (lastBidder.equals(npcDisplayName)) {
                    continue;
                }

                double npcMax = (npcDisplayName.equals(npc1Name)) ? npc1MaxBid : npc2MaxBid;
                if (currentBid < npcMax && random.nextDouble() < 0.7) {
                    double potentialNpcBid = currentBid + bidIncrement;
                    potentialNpcBid = Math.min(npcMax, potentialNpcBid);
                    potentialNpcBid = Math.ceil(potentialNpcBid / 1.0) * 1.0;

                    if (potentialNpcBid > currentBid) {
                        currentBid = potentialNpcBid;
                        lastBidder = npcDisplayName;
                        System.out.println(npcDisplayName + " bids $" + String.format("%.2f", currentBid) + "!");
                        playerWonBid = false;
                        anyNpcBiddedThisRound = true;
                    }
                }
            }

            // Check if auction should end
            boolean allNpcsReachedMax = (currentBid >= npc1MaxBid && currentBid >= npc2MaxBid);
            boolean bidTooHighForProfitThreshold = currentBid >= (itemToAuction.getMaxResell() * (0.8 - (player.getMarketSense() * 0.01)));

            if (lastBidder.equals(player.getName())) {
                if (!anyNpcBiddedThisRound || allNpcsReachedMax || bidTooHighForProfitThreshold) {
                    System.out.println("The auctioneer looks around... \"Going once... Going twice... SOLD to " + player.getName() + "!\"");
                    playerWonBid = true;
                    break;
                }
            } else {
                if (!anyNpcBiddedThisRound) {
                    System.out.println("The auctioneer looks around... \"Going once... Going twice... Sold to " + lastBidder + "!\"");
                    playerWonBid = false;
                    break;
                }
            }
        }

        // Show auction results
        System.out.println("\n--- Auction Concludes ---");
        
        if (playerWonBid) {
            System.out.println("CONGRATULATIONS! You won the " + itemToAuction.getName() + " for $" + String.format("%.2f", currentBid) + "!");
            player.setBankroll(player.getBankroll() - currentBid);
            System.out.println("Your bankroll is now: $" + String.format("%.2f", player.getBankroll()));
            player.addExperience(XP_GAIN_PER_AUCTION_PARTICIPATION);
            System.out.println("You've gained " + XP_GAIN_PER_AUCTION_PARTICIPATION + " XP for winning an auction!");

            // Sell the item
            System.out.println("\n--- Time to Flip! ---");
            System.out.println("You now own the " + itemToAuction.getName() + ". Let's see if you can make a profit!");
            
            double sellPrice = itemToAuction.getMaxResell() * (0.7 + random.nextDouble() * 0.3);
            if (random.nextInt(10) < player.getMarketSense()) {
                sellPrice *= 1.3;
                System.out.println("Your keen Market Sense paid off! You found the perfect buyer for a premium price!");
            }
            if (random.nextInt(10) < player.getBargaining()) {
                sellPrice *= 1.2;
                System.out.println("Your excellent Bargaining skills sealed the deal for an even better price!");
            }

            System.out.println("You managed to sell the " + itemToAuction.getName() + " for $" + String.format("%.2f", sellPrice) + ".");
            player.setBankroll(player.getBankroll() + sellPrice);
            player.addExperience(XP_GAIN_PER_SALE);
            System.out.println("Net Profit: $" + String.format("%.2f", (sellPrice - currentBid)) + " | +" + XP_GAIN_PER_SALE + " XP.");
            System.out.println("New Bankroll: $" + String.format("%.2f", player.getBankroll()));
            System.out.println("Current XP: " + player.getExperience() + " / " + player.getExperienceRequiredForNextLevel());

        } else {
            System.out.println("You were outbid for the " + itemToAuction.getName() + ". Better luck at the next item!");
            player.addExperience(XP_GAIN_PER_AUCTION_PARTICIPATION / 2);
            System.out.println("You gain " + (XP_GAIN_PER_AUCTION_PARTICIPATION / 2) + " XP for the experience.");
            System.out.println("Current XP: " + player.getExperience() + " / " + player.getExperienceRequiredForNextLevel());
        }
        
        System.out.println("\n--- Auction finished. What's next for your hustle? ---");
    }
}