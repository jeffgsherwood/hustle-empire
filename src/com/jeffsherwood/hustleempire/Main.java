package com.jeffsherwood.hustleempire;

import java.util.Random;
import java.util.Scanner;

public class Main {

    // Game Constants
    public static final double WIN_BANKROLL_THRESHOLD = 500.0; // Need $500 to win
    public static final double LOSE_BANKROLL_THRESHOLD = 0.0; 
    public static final double STARTING_BANKROLL = 100.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Random random = new Random(); // Keeping this for potential future Main class game logic

        boolean playAgain = true;

        // Outer loop for restarting the entire game
        while (playAgain) {
            // Game Intro and Name assignment
            System.out.println("\n--- Starting a New Hustle Empire! ---");
            System.out.println("Welcome to Hustle Empire!");
            System.out.println("In this game you will thrift, shop, and bargain your way to the top!");
            System.out.println("Your goal is to buy low, sell high, and retire early!");
            System.out.println("Let's get started by setting up your Player!");
            System.out.println("Enter your player name below or press ENTER to have one chosen for you.");
            System.out.println("(Press ENTER when you're done):");
            String playerNameInput = scanner.nextLine();

            System.out.println("Awesome! Nice to meet you, " + (playerNameInput.isEmpty() ? "Hustler" : playerNameInput.toUpperCase()) + "!");
            System.out.println("\nIf you wanna be a true hustler then you gotta have some skills.");
            System.out.println("\nWe're going to start you off with 10 points that can be divided between Bargaining and Market Sense.");
            System.out.println("\nBargaining helps you when you are trying to get the lowest price on items you want to buy.");
            System.out.println("\nMarket Sense helps you get the highest selling price for an item you are selling.");

            int totalPointsToAllocate = 10;
            int bargaining = 0;
            int marketSense = 0;

            // While Loop for points allocation
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
                    // Ensure inputs are within the valid 0-10 range for skills.
                    // Player setters will also clamp, but this provides immediate feedback.
                    if (bargainingInput < 0 || bargainingInput > totalPointsToAllocate ||
                        marketSenseInput < 0 || marketSenseInput > totalPointsToAllocate) {
                        System.out.println("Skills points must be between 0 and " + totalPointsToAllocate + ". Please try again.");
                        continue; // Skip setting and re-loop
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
            // Player Creation 
            Player player = new Player(playerNameInput, STARTING_BANKROLL, bargaining, marketSense);
            System.out.println("\nPlayer creation complete! Let the hustling begin!");
            System.out.println(player);

            // Game loop
            while (true) {
                System.out.println("\n--- Current Stats ---");
                System.out.println(player); // Display current player stats at start of each round

                System.out.println("\nWhere you hustlin’ today, " + player.getName() + "?");
                System.out.println("(1) Yard Sale");
                System.out.println("(2) Flea Market (coming soon)");
                System.out.println("(3) Thrift Store (coming soon)");
                System.out.println("(4) Estate Sale (coming soon)");
                System.out.println("(5) Retail Arbitrage (coming soon)");
                System.out.println("(6) Call it quits");
                System.out.print("Pick a spot (1-6): ");

                // Validate input for scene choice
                int choice;
                while (true) {
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Clear newline
                        if (choice >= 1 && choice <= 6) {
                            break; // Valid input
                        } else {
                            System.out.println("Yo, enter a number between 1 and 6!");
                        }
                    } else {
                        System.out.println("Yo, enter a number between 1 and 6!");
                        scanner.next(); // Clear invalid input
                        scanner.nextLine(); // Consume leftover newline after scanner.next()
                    }
                }

                // Quit game early from main menu
                if (choice == 6) {
                    System.out.println("Aight, you’re out! Final bankroll: $" + String.format("%.2f", player.getBankroll()));
                    playAgain = false; // Set flag to exit outer loop
                    break; // Exit inner game loop
                }

                // Select and play scene
                Scene scene;
                boolean scenePlayedSuccessfully = true; // Flag to indicate if a valid scene was played
                switch (choice) {
                    case 1:
                        scene = new YardSaleScene();
                        break;
                    case 2:
                        System.out.println("Flea Market ain’t open yet, homie! Try Yard Sale.");
                        scenePlayedSuccessfully = false;
                        continue; // Skip to next loop iteration
                    case 3:
                        System.out.println("Thrift Store ain’t ready, homie! Try Yard Sale.");
                        scenePlayedSuccessfully = false;
                        continue; // Skip to next loop iteration
                    case 4:
                        System.out.println("Estate Sale not set up yet, homie! Try Yard Sale.");
                        scenePlayedSuccessfully = false;
                        continue; // Skip to next loop iteration
                    case 5:
                        System.out.println("Retail Arbitrage comin’ soon, homie! Try Yard Sale.");
                        scenePlayedSuccessfully = false;
                        continue; // Skip to next loop iteration
                    default: // Should not be reached due to input validation
                        System.out.println("Invalid spot, homie! Pick 1-6.");
                        scenePlayedSuccessfully = false;
                        continue; // Skip to next loop iteration
                }

                // Play the selected scene 
                if (scenePlayedSuccessfully) {
                    scene.play(player, scanner, random);
                }
                
                // Check end scenarios AFTER playing a scene
                if (player.getBankroll() <= LOSE_BANKROLL_THRESHOLD) { // Use constant
                    LoseGameScene loseScene = new LoseGameScene();
                    loseScene.play(player, scanner, random); // Display loss message
                    playAgain = loseScene.askToRestart(scanner); // Ask to restart
                    break; // Exit inner game loop
                } else if (player.getBankroll() >= WIN_BANKROLL_THRESHOLD) { // Use constant
                    WinGameScene winScene = new WinGameScene();
                    winScene.play(player, scanner, random); // Display win message
                    playAgain = winScene.askToRestart(scanner); // Ask to restart
                    break; // Exit inner game loop
                }

                // Level up check AFTER playing a scene and checking game end
                if (player.getExperience() >= player.getExperienceRequiredForNextLevel()) {
                    player.levelUp(); // This method now includes its own print messages
                }
            } // End of inner game loop (one game session)
        } // End of outer playAgain loop

        // Close scanner when the entire application loop ends
        scanner.close();
        System.out.println("\nThanks for playing Hustle Empire!");
    }
}
