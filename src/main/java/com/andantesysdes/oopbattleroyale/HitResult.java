package com.andantesysdes.oopbattleroyale;

public class HitResult {
    private final boolean dodged;
    private final boolean critical;
    private final int damage;

    public HitResult(boolean dodged, boolean critical, int damage) {
        this.dodged = dodged;
        this.critical = critical;
        this.damage = damage;
    }

    public boolean isDodged() {
        return dodged;
    }

    public boolean isCritical() {
        return critical;
    }

    public int getDamage() {
        return damage;
    }
}
