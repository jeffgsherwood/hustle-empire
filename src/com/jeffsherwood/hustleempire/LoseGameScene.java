package com.jeffsherwood.hustleempire;

import java.util.Scanner;
import java.util.Random; // Though not used directly, good to keep consistent signature with other scenes

public class LoseGameScene extends Scene {

    public LoseGameScene() {
        super("Game Over!", "Your hustle wasn't enough this time...");
    }

    // Returns true if player wants to restart, false otherwise
    @Override
    public void play(Player player, Scanner scanner, Random random) {
        System.out.println("\n*******************************************");
        System.out.println("             G A M E   O V E R            ");
        System.out.println("*******************************************");
        System.out.println(description);
        System.out.println("Your bankroll is down to $" + String.format("%.2f", player.getBankroll()) + ".");
        System.out.println("The streets are tough, and sometimes the hustle just doesn't pay off.");
        System.out.println("Don't worry, every hustler faces setbacks. Time to learn and try again!");
        System.out.println("*******************************************");
    }

    // Add a specific method to ask about restarting
    public boolean askToRestart(Scanner scanner) {
        System.out.println("\nWould you like to try building a new Hustle Empire? (y/n)");
        String choice = scanner.nextLine().toLowerCase();
        return choice.startsWith("y");
    }
}
