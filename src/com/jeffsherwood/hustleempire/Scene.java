package com.jeffsherwood.hustleempire;

import java.util.Scanner;
import java.util.Random;

public abstract class Scene {
    protected String name;
    protected String description;

    public Scene(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void play(Player player, Scanner scanner, Random random);
}