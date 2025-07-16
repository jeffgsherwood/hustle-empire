package com.jeffsherwood.hustleempire;

import java.util.Scanner;
import java.util.Random;

public class WinGameScene extends Scene {

    // Constructor to set up the scene
    public WinGameScene() {
        super("Victory!", "You've built your Hustle Empire!");
    }

    // Display victory message
    @Override
    public void play(Player player, Scanner scanner, Random random) {
        System.out.println("\n*******************************************");
        System.out.println("            C O N G R A T S ! ! !         ");
        System.out.println("*******************************************");
        System.out.println(description);
        System.out.println("With a bankroll of $" + String.format("%.2f", player.getBankroll()) + ", you're officially a LEGEND!");
        System.out.println("You've mastered the art of buying low and selling high!");
        System.out.println("It's time to retire early and enjoy your Hustle Empire!");
        System.out.println("*******************************************");
    }

    // Ask if player wants to restart
    public boolean askToRestart(Scanner scanner) {
        System.out.println("\nWould you like to start a new Hustle Empire? (y/n)");
        String choice = scanner.nextLine().toLowerCase();
        return choice.startsWith("y");
    }
}