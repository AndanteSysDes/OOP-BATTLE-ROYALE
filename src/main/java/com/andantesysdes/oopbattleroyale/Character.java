package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Character {
    private final String name;
    private int health;
    private final int attackDamage;
    private final int speed;
    private boolean specialUsed;

    protected Character(String name, int health, int attackDamage, int speed) {
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
        this.speed = speed;
        this.specialUsed = false;
    }

    public AttackReport attack(Character target, Random random, ActionType actionType) {
        if (actionType == ActionType.SPECIAL_ABILITY) {
            if (!canUseSpecial()) {
                throw new IllegalStateException("Special ability is already used.");
            }
            specialUsed = true;
            return performSpecialAttack(target, random);
        }

        List<HitResult> hits = new ArrayList<HitResult>();
        hits.add(performHit(target, random, attackDamage));
        return new AttackReport("Normal Attack", hits);
    }

    protected abstract AttackReport performSpecialAttack(Character target, Random random);

    protected final HitResult performHit(Character target, Random random, int baseDamage) {
        if (random.nextDouble() < target.getDodgeChance()) {
            target.onIncomingDodged();
            return new HitResult(true, false, 0);
        }

        int variedDamage = baseDamage + random.nextInt(7) - 3;
        if (variedDamage < 0) {
            variedDamage = 0;
        }

        boolean critical = random.nextDouble() < 0.10;
        if (critical) {
            variedDamage = (int) Math.floor(variedDamage * 1.5);
        }

        int finalDamage = target.applyIncomingDamageModifiers(variedDamage);
        target.takeDamage(finalDamage);
        return new HitResult(false, critical, finalDamage);
    }

    protected int applyIncomingDamageModifiers(int damage) {
        return damage;
    }

    protected void onIncomingDodged() {
    }

    protected double getDodgeChance() {
        return 0.10;
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            damage = 0;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean canUseSpecial() {
        return !specialUsed;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public abstract String getClassName();

    public abstract String getSpecialAbilityName();
}
