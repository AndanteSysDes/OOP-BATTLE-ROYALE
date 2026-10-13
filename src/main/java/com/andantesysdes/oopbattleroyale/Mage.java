package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Character {
    public Mage(String name) {
        super(name, 95, 18, 14);
    }

    @Override
    protected AttackReport performSpecialAttack(Character target, Random random) {
        List<HitResult> hits = new ArrayList<HitResult>();
        hits.add(performHit(target, random, getAttackDamage() + 12));
        return new AttackReport("Fireball", hits);
    }

    @Override
    public String getClassName() {
        return "Mage";
    }

    @Override
    public String getSpecialAbilityName() {
        return "Fireball";
    }
}
