package com.jeffsherwood.hustleempire;

import java.util.Scanner;
import java.util.Random;

public abstract class Scene {
    // Fields for scene name and description
    protected String name;
    protected String description;

    // Constructor to set up a scene
    public Scene(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Abstract method to play the scene
    public abstract void play(Player player, Scanner scanner, Random random);
}