package com.jeffsherwood.hustleempire;

import java.util.Random;
import java.util.Scanner;

public class Main {

    // Game constants for win/loss and starting money
    public static final double WIN_BANKROLL_THRESHOLD = 500.0;
    public static final double LOSE_BANKROLL_THRESHOLD = 0.0;
    public static final double STARTING_BANKROLL = 100.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Random random = new Random();
        boolean playAgain = true;

        // Main game loop for restarting
        while (playAgain) {
            // Game introduction
            System.out.println("\n--- Starting a New Hustle Empire! ---");
            System.out.println("Welcome to Hustle Empire!");
            System.out.println("In this game you will thrift, shop, and bargain your way to the top!");
            System.out.println("Your goal is to buy low, sell high, and retire early!");
            System.out.println("Let's get started by setting up your Player!");
            System.out.println("Enter your player name below or press ENTER to have one chosen for you.");
            System.out.println("(Press ENTER when you're done):");
            String playerNameInput = scanner.nextLine();

            // Create player with starting bankroll
            Player player = new Player(playerNameInput, STARTING_BANKROLL, 0, 0);
            System.out.println("Awesome! Nice to meet you, " + player.getName() + "!");

            // Skill points allocation
            System.out.println("\nIf you wanna be a true hustler then you gotta have some skills.");
            System.out.println("\nWe're going to start you off with 10 points that can be divided between Bargaining and Market Sense.");
            System.out.println("\nBargaining helps you when you are trying to get the lowest price on items you want to buy.");
            System.out.println("\nMarket Sense helps you get the highest selling price for an item you are selling.");

            int totalPointsToAllocate = 10;
            int bargaining = 0;
            int marketSense = 0;

            // Loop to allocate skill points
            while (bargaining + marketSense != totalPointsToAllocate) {
                System.out.println("\nDecide how you would like to split your points! You have " + totalPointsToAllocate + " points to distribute.");
                System.out.println("Current allocation: Bargaining: " + bargaining + ", Market Sense: " + marketSense);
                System.out.println("Remaining points: " + (totalPointsToAllocate - (bargaining + marketSense)));

                System.out.println("\nEnter the number of points you want to use for Bargaining (0-" + totalPointsToAllocate + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number for Bargaining points:");
                    scanner.next();
                }
                int bargainingInput = scanner.nextInt();
                scanner.nextLine();

                System.out.println("\nEnter the number of points you want to use for Market Sense (0-" + totalPointsToAllocate + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number for Market Sense points:");
                    scanner.next();
                }
                int marketSenseInput = scanner.nextInt();
                scanner.nextLine();

                if (bargainingInput + marketSenseInput == totalPointsToAllocate) {
                    if (bargainingInput < 0 || bargainingInput > totalPointsToAllocate ||
                        marketSenseInput < 0 || marketSenseInput > totalPointsToAllocate) {
                        System.out.println("Skills points must be between 0 and " + totalPointsToAllocate + ". Please try again.");
                        continue;
                    }
                    bargaining = bargainingInput;
                    marketSense = marketSenseInput;
                    System.out.println("\nGreat! Your skills are set!");
                } else if (bargainingInput + marketSenseInput > totalPointsToAllocate) {
                    System.out.println("\nOops! You entered " + (bargainingInput + marketSenseInput) + " points total. You only have " + totalPointsToAllocate + " points to distribute.");
                    System.out.println("Please try again.");
                } else {
                    System.out.println("\nOops! You entered " + (bargainingInput + marketSenseInput) + " points total. You still have " + (totalPointsToAllocate - (bargainingInput + marketSenseInput)) + " points remaining.");
                    System.out.println("Please distribute all " + totalPointsToAllocate + " points. Let's try again.");
                }
            }

            // Set player skills
            player.setBargaining(bargaining);
            player.setMarketSense(marketSense);
            System.out.println("\nPlayer creation complete! Let the hustling begin!");
            System.out.println(player);

            // Inner game loop for playing scenes
            while (true) {
                System.out.println("\n--- Current Stats ---");
                System.out.println(player);

                System.out.println("\nWhere you hustlin’ today, " + player.getName() + "?");
                System.out.println("(1) Yard Sale");
                System.out.println("(2) Flea Market");
                System.out.println("(3) Estate Auction");
                System.out.println("(4) Call it quits");
                System.out.print("Pick a spot (1-4): ");

                // Get scene choice
                int choice;
                while (true) {
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        if (choice >= 1 && choice <= 4) {
                            break;
                        } else {
                            System.out.println("Please enter a number between 1 and 4!");
                        }
                    } else {
                        System.out.println("Please enter a number between 1 and 4!");
                        scanner.next();
                        scanner.nextLine();
                    }
                }

                // Quit game if chosen
                if (choice == 4) {
                    System.out.println("Alright, you’re out! Final bankroll: $" + String.format("%.2f", player.getBankroll()));
                    playAgain = false;
                    break;
                }

                // Select scene based on choice
                Scene scene;
                boolean scenePlayedSuccessfully = true;
                switch (choice) {
                    case 1:
                        scene = new YardSaleScene();
                        break;
                    case 2:
                        scene = new FleaMarketScene();
                        break;
                    case 3:
                        scene = new EstateAuctionScene();
                        break;
                    default:
                        System.out.println("Invalid spot, homie! Pick 1-4.");
                        scenePlayedSuccessfully = false;
                        continue;
                }

                // Play the selected scene
                if (scenePlayedSuccessfully) {
                    scene.play(player, scanner, random);
                }

                // Check if player wins or loses
                if (player.getBankroll() <= LOSE_BANKROLL_THRESHOLD) {
                    LoseGameScene loseScene = new LoseGameScene();
                    loseScene.play(player, scanner, random);
                    playAgain = loseScene.askToRestart(scanner);
                    break;
                } else if (player.getBankroll() >= WIN_BANKROLL_THRESHOLD) {
                    WinGameScene winScene = new WinGameScene();
                    winScene.play(player, scanner, random);
                    playAgain = winScene.askToRestart(scanner);
                    break;
                }

                // Check for level up
                if (player.getExperience() >= player.getExperienceRequiredForNextLevel()) {
                    player.levelUp();
                }
            }
        }

        // Close scanner and end game
        scanner.close();
        System.out.println("\nThanks for playing Hustle Empire!");
    }
}