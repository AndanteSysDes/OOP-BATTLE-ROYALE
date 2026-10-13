package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Character {
    public Warrior(String name) {
        super(name, 115, 16, 12);
    }

    @Override
    protected AttackReport performSpecialAttack(Character target, Random random) {
        List<HitResult> hits = new ArrayList<HitResult>();
        hits.add(performHit(target, random, getAttackDamage() + 12));
        return new AttackReport("Heavy Strike", hits);
    }

    @Override
    public String getClassName() {
        return "Warrior";
    }

    @Override
    public String getSpecialAbilityName() {
        return "Heavy Strike";
    }
}
