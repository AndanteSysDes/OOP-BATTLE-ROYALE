package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tank extends Character {
    private boolean shieldActive;

    public Tank(String name) {
        super(name, 140, 13, 8);
        shieldActive = false;
    }

    @Override
    protected AttackReport performSpecialAttack(Character target, Random random) {
        shieldActive = true;
        return new AttackReport("Shield", new ArrayList<HitResult>());
    }

    @Override
    protected int applyIncomingDamageModifiers(int damage) {
        if (shieldActive) {
            shieldActive = false;
            return (int) Math.floor(damage * 0.5);
        }
        return damage;
    }

    @Override
    protected void onIncomingDodged() {
        if (shieldActive) {
            shieldActive = false;
        }
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    @Override
    public String getClassName() {
        return "Tank";
    }

    @Override
    public String getSpecialAbilityName() {
        return "Shield";
    }
}
