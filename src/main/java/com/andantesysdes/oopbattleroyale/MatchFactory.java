package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.List;

public class MatchFactory {
    public Character createCharacter(CharacterClassType type, String name) {
        if (type == CharacterClassType.WARRIOR) {
            return new Warrior(name);
        }
        if (type == CharacterClassType.MAGE) {
            return new Mage(name);
        }
        if (type == CharacterClassType.ARCHER) {
            return new Archer(name);
        }
        return new Tank(name);
    }

    public List<Character> createBattleRoyaleRoster() {
        List<Character> roster = new ArrayList<Character>();
        roster.add(new Warrior("Warrior CPU"));
        roster.add(new Mage("Mage CPU"));
        roster.add(new Archer("Archer CPU"));
        roster.add(new Tank("Tank CPU"));
        return roster;
    }

    public List<Character> createOneVsOne(CharacterClassType playerClass, CharacterClassType cpuClass) {
        if (playerClass == cpuClass) {
            throw new IllegalArgumentException("Player and CPU classes must be different.");
        }
        List<Character> roster = new ArrayList<Character>();
        roster.add(createCharacter(playerClass, "Player"));
        roster.add(createCharacter(cpuClass, "CPU"));
        return roster;
    }
}
