package com.jeffsherwood.hustleempire;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Random random = new Random();
        
        // Game Intro and Name assignment
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
        //Player Creation
        Player player = new Player(playerNameInput, 100.0, bargaining, marketSense);
        System.out.println("\nPlayer creation complete! Let the hustling begin!");
        System.out.println(player);

        // TODO: Add game loop here 

        // Keep scanner open for now
        // scanner.close();
    }
}