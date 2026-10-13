package com.andantesysdes.oopbattleroyale;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Battle {
    private final String modeName;
    private final List<Character> fighters;
    private final Character playerCharacter;
    private final Random random;
    private final Scanner scanner;
    private final PrintStream out;

    public Battle(String modeName, List<Character> fighters, Character playerCharacter, Random random, Scanner scanner, PrintStream out) {
        this.modeName = modeName;
        this.fighters = fighters;
        this.playerCharacter = playerCharacter;
        this.random = random;
        this.scanner = scanner;
        this.out = out;
    }

    public Character run() {
        int round = 1;
        out.println("=== Battle Start ===");
        out.println("Mode: " + modeName);
        printCombatants();

        while (countAliveFighters() > 1) {
            out.println();
            out.println("--- Round " + round + " ---");
            List<Character> turnOrder = buildTurnOrder();

            for (int i = 0; i < turnOrder.size(); i++) {
                Character attacker = turnOrder.get(i);
                if (!attacker.isAlive()) {
                    continue;
                }
                if (countAliveFighters() <= 1) {
                    break;
                }

                List<Character> targets = getLivingTargets(attacker);
                if (targets.isEmpty()) {
                    continue;
                }

                Character target = targets.get(random.nextInt(targets.size()));
                ActionType actionType = chooseAction(attacker);
                AttackReport report = attacker.attack(target, random, actionType);

                out.println(attacker.getName() + " (" + attacker.getClassName() + ") uses " + report.getActionName() +
                        " on " + target.getName() + " (" + target.getClassName() + ").");

                if (report.getHits().isEmpty()) {
                    out.println("No direct damage this turn.");
                } else {
                    for (int hitIndex = 0; hitIndex < report.getHits().size(); hitIndex++) {
                        HitResult hit = report.getHits().get(hitIndex);
                        if (hit.isDodged()) {
                            out.println("Hit " + (hitIndex + 1) + ": Dodged!");
                        } else {
                            String criticalText = hit.isCritical() ? " (Critical)" : "";
                            out.println("Hit " + (hitIndex + 1) + ": " + hit.getDamage() + " damage" + criticalText +
                                    ". " + target.getName() + " HP: " + target.getHealth());
                        }
                    }
                }

                if (!target.isAlive()) {
                    out.println(target.getName() + " has been eliminated!");
                }
            }

            round++;
        }

        Character winner = getWinner();
        if (winner != null) {
            out.println();
            out.println("Winner: " + winner.getName() + " (" + winner.getClassName() + ")");
        }
        out.println("=== Battle End ===");
        return winner;
    }

    List<Character> buildTurnOrder() {
        List<Character> alive = getAliveFighters();
        Collections.sort(alive, new Comparator<Character>() {
            @Override
            public int compare(Character first, Character second) {
                return Integer.compare(second.getSpeed(), first.getSpeed());
            }
        });

        List<Character> ordered = new ArrayList<Character>();
        int index = 0;
        while (index < alive.size()) {
            int speed = alive.get(index).getSpeed();
            List<Character> sameSpeedGroup = new ArrayList<Character>();

            while (index < alive.size() && alive.get(index).getSpeed() == speed) {
                sameSpeedGroup.add(alive.get(index));
                index++;
            }

            Collections.shuffle(sameSpeedGroup, random);
            ordered.addAll(sameSpeedGroup);
        }

        return ordered;
    }

    List<Character> getLivingTargets(Character attacker) {
        List<Character> targets = new ArrayList<Character>();
        for (int i = 0; i < fighters.size(); i++) {
            Character candidate = fighters.get(i);
            if (candidate != attacker && candidate.isAlive()) {
                targets.add(candidate);
            }
        }
        return targets;
    }

    private ActionType chooseAction(Character attacker) {
        if (attacker == playerCharacter && attacker.isAlive()) {
            while (true) {
                out.println("Choose action for " + attacker.getName() + ":");
                out.println("1. Normal Attack");
                out.println("2. Special Ability (" + attacker.getSpecialAbilityName() + ")");
                out.print("Enter choice: ");

                String input = scanner.nextLine();
                if ("1".equals(input)) {
                    return ActionType.NORMAL_ATTACK;
                }
                if ("2".equals(input)) {
                    if (attacker.canUseSpecial()) {
                        return ActionType.SPECIAL_ABILITY;
                    }
                    out.println("Special ability is already used. Choose another action.");
                    continue;
                }
                out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        if (attacker.canUseSpecial() && random.nextDouble() < 0.30) {
            return ActionType.SPECIAL_ABILITY;
        }
        return ActionType.NORMAL_ATTACK;
    }

    private void printCombatants() {
        out.println("Combatants:");
        for (int i = 0; i < fighters.size(); i++) {
            Character fighter = fighters.get(i);
            out.println("- " + fighter.getName() + " (" + fighter.getClassName() + ") HP=" + fighter.getHealth()
                    + " ATK=" + fighter.getAttackDamage() + " SPD=" + fighter.getSpeed());
        }
    }

    private List<Character> getAliveFighters() {
        List<Character> alive = new ArrayList<Character>();
        for (int i = 0; i < fighters.size(); i++) {
            Character fighter = fighters.get(i);
            if (fighter.isAlive()) {
                alive.add(fighter);
            }
        }
        return alive;
    }

    private int countAliveFighters() {
        int count = 0;
        for (int i = 0; i < fighters.size(); i++) {
            if (fighters.get(i).isAlive()) {
                count++;
            }
        }
        return count;
    }

    private Character getWinner() {
        for (int i = 0; i < fighters.size(); i++) {
            Character fighter = fighters.get(i);
            if (fighter.isAlive()) {
                return fighter;
            }
        }
        return null;
    }
}
