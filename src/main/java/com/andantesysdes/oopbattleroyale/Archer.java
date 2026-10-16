package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Archer extends Character {
    public Archer(String name) {
        super(name, 100, 15, 16);
    }

    @Override
    protected AttackReport performSpecialAttack(Character target, Random random) {
        List<HitResult> hits = new ArrayList<HitResult>();
        hits.add(performHit(target, random, getAttackDamage()));
        if (target.isAlive()) {
            hits.add(performHit(target, random, getAttackDamage()));
        }
        return new AttackReport("Double Shot", hits);
    }

    @Override
    protected double getDodgeChance() {
        return 0.20;
    }

    @Override
    public String getClassName() {
        return "Archer";
    }

    @Override
    public String getSpecialAbilityName() {
        return "Double Shot";
    }
}
