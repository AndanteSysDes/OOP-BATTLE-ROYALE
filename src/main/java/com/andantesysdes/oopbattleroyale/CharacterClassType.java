package com.andantesysdes.oopbattleroyale;

public enum CharacterClassType {
    WARRIOR("Warrior"),
    MAGE("Mage"),
    ARCHER("Archer"),
    TANK("Tank");

    private final String displayName;

    CharacterClassType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
