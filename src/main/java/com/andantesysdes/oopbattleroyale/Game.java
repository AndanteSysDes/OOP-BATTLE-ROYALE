package com.andantesysdes.oopbattleroyale;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Scanner scanner;
    private final PrintStream out;
    private final Random random;
    private final MatchFactory matchFactory;

    public Game(Scanner scanner, PrintStream out, Random random, MatchFactory matchFactory) {
        this.scanner = scanner;
        this.out = out;
        this.random = random;
        this.matchFactory = matchFactory;
    }

    public void start() {
        boolean running = true;

        while (running) {
            out.println();
            out.println("=== OOP Battle Royale ===");
            out.println("1. 1 vs 1");
            out.println("2. Battle Royale");
            out.println("3. Exit");
            out.print("Choose mode: ");

            int mode = readMenuChoice(1, 3);
            switch (mode) {
                case 1:
                    runOneVsOne();
                    break;
                case 2:
                    runBattleRoyale();
                    break;
                case 3:
                    out.println("Goodbye!");
                    return;
                default:
                    break;
            }

            out.println();
            out.println("1. Replay");
            out.println("2. Exit");
            out.print("Choose: ");
            int afterMatch = readMenuChoice(1, 2);
            if (afterMatch == 2) {
                out.println("Goodbye!");
                running = false;
            }
        }
    }

    private void runOneVsOne() {
        out.println();
        out.println("Select your class:");
        CharacterClassType playerClass = selectClass();

        out.println();
        out.println("Select CPU class (must be different):");
        CharacterClassType cpuClass = selectClass();
        while (cpuClass == playerClass) {
            out.println("CPU class must be different from your class.");
            cpuClass = selectClass();
        }

        List<Character> fighters = matchFactory.createOneVsOne(playerClass, cpuClass);
        Character playerCharacter = fighters.get(0);

        Battle battle = new Battle("1 vs 1", fighters, playerCharacter, random, scanner, out);
        battle.run();
    }

    private void runBattleRoyale() {
        List<Character> fighters = matchFactory.createBattleRoyaleRoster();

        out.println();
        out.println("Select your fighter class:");
        CharacterClassType playerClass = selectClass();

        Character playerCharacter = null;
        List<Character> prepared = new ArrayList<Character>();
        for (int i = 0; i < fighters.size(); i++) {
            Character fighter = fighters.get(i);
            CharacterClassType classType = CharacterClassType.valueOf(fighter.getClassName().toUpperCase());
            if (classType == playerClass) {
                Character playerFighter = matchFactory.createCharacter(playerClass, "Player");
                prepared.add(playerFighter);
                playerCharacter = playerFighter;
            } else {
                prepared.add(fighter);
            }
        }

        Battle battle = new Battle("Battle Royale", prepared, playerCharacter, random, scanner, out);
        battle.run();
    }

    private CharacterClassType selectClass() {
        while (true) {
            out.println("1. Warrior");
            out.println("2. Mage");
            out.println("3. Archer");
            out.println("4. Tank");
            out.print("Choose class: ");
            int choice = readMenuChoice(1, 4);

            if (choice == 1) {
                return CharacterClassType.WARRIOR;
            }
            if (choice == 2) {
                return CharacterClassType.MAGE;
            }
            if (choice == 3) {
                return CharacterClassType.ARCHER;
            }
            return CharacterClassType.TANK;
        }
    }

    private int readMenuChoice(int min, int max) {
        while (true) {
            String raw = scanner.nextLine();
            try {
                int choice = Integer.parseInt(raw.trim());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {
            }
            out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            out.print("Enter again: ");
        }
    }
}
