package com.jeffsherwood.hustleempire;

import java.util.Random;

public class Player {
    private String name;
    private double bankroll;
    private int level;
    private int experience;
    private int bargaining;
    private int marketSense;

    private static final Random random = new Random();

    // Constructor
    public Player(String name, double bankroll, int bargaining, int marketSense) {
        setName(name);
        setBankroll(bankroll);
        setLevel(1);
        setExperience(0);
        setBargaining(bargaining);
        setMarketSense(marketSense);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Hustler" + (random.nextInt(1000) + 1);
        } else {
            this.name = name.trim().toUpperCase();
        }
    }

    public double getBankroll() {
        return bankroll;
    }

    public void setBankroll(double bankroll) {
        this.bankroll = Math.max(0, bankroll);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    public void addExperience(int exp) {
        this.experience += Math.max(0, exp);
    }

    public void levelUp() {
        level++;
        bargaining = Math.min(10, bargaining + 1);
        marketSense = Math.min(10, marketSense + 1);
        System.out.println("Level up! Now level " + level + "! Bargaining: " + bargaining + ", Market Sense: " + marketSense);
    }

    public int getBargaining() {
        return bargaining;
    }

    public void setBargaining(int bargaining) {
        this.bargaining = Math.min(Math.max(0, bargaining), 10);
    }

    public int getMarketSense() {
        return marketSense;
    }

    public void setMarketSense(int marketSense) {
        this.marketSense = Math.min(Math.max(0, marketSense), 10);
    }

    @Override
    public String toString() {
        return "Player [Name=" + name +
               ", Bankroll=$" + String.format("%.2f", bankroll) +
               ", Level=" + level +
               ", XP=" + experience + "/" + (level * 100) +
               ", Bargaining=" + bargaining +
               ", Market Sense=" + marketSense + "]";
    }
}