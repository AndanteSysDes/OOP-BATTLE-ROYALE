package com.andantesysdes.oopbattleroyale;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner, System.out, new Random(), new MatchFactory());
        game.start();
    }
}
