package com.jeffsherwood.hustleempire;

import java.util.Random;

public class Player {
    private String name;
    private double bankroll;
    private int level;
    private int experience;
    private int bargaining;
    private int marketSense;

    // Random number generator for name generation
    private static final Random random = new Random();

    // Constructor to create a new player
    public Player(String name, double bankroll, int bargaining, int marketSense) {
        setName(name);
        setBankroll(bankroll);
        setLevel(1);
        setExperience(0);
        setBargaining(bargaining);
        setMarketSense(marketSense);
    }

    // Get player's name
    public String getName() {
        return name;
    }

    // Set player's name, with default if empty
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Hustler" + (random.nextInt(1000) + 1);
        } else {
            this.name = name.trim().toUpperCase();
        }
    }

    // Get player's bankroll
    public double getBankroll() {
        return bankroll;
    }

    // Set player's bankroll, ensure it's not negative
    public void setBankroll(double bankroll) {
        this.bankroll = Math.max(0, bankroll);
    }

    // Get player's level
    public int getLevel() {
        return level;
    }

    // Set player's level, minimum is 1
    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    // Get player's experience points
    public int getExperience() {
        return experience;
    }

    // Set player's experience, ensure it's not negative
    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    // Add experience points to player
    public void addExperience(int exp) {
        this.experience += Math.max(0, exp);
    }

    // Calculate XP needed for next level
    public int getExperienceRequiredForNextLevel() {
        return level * 100;
    }

    // Level up player and increase skills
    public void levelUp() {
        level++;
        bargaining = Math.min(10, bargaining + 1);
        marketSense = Math.min(10, marketSense + 1);
        System.out.println("\n*** LEVEL UP! ***");
        System.out.println("You've reached Level " + level + "!");
        System.out.println("Bargaining skill increased to: " + bargaining);
        System.out.println("Market Sense skill increased to: " + marketSense);
        System.out.println("Keep hustling, " + name + "!\n");
    }

    // Get player's bargaining skill
    public int getBargaining() {
        return bargaining;
    }

    // Set bargaining skill, keep it between 0 and 10
    public void setBargaining(int bargaining) {
        this.bargaining = Math.min(Math.max(0, bargaining), 10);
    }

    // Get player's market sense skill
    public int getMarketSense() {
        return marketSense;
    }

    // Set market sense skill, keep it between 0 and 10
    public void setMarketSense(int marketSense) {
        this.marketSense = Math.min(Math.max(0, marketSense), 10);
    }

    // Display player's stats
    @Override
    public String toString() {
        return "Player [Name=" + name +
               ", Bankroll=$" + String.format("%.2f", bankroll) +
               ", Level=" + level +
               ", XP=" + experience + "/" + getExperienceRequiredForNextLevel() +
               ", Bargaining=" + bargaining +
               ", Market Sense=" + marketSense + "]";
    }
}