package com.andantesysdes.oopbattleroyale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterCombatTest {
    @Test
    void damageVariationStaysWithinBoundsAndHpClampsToZero() {
        Warrior attacker = new Warrior("Attacker");
        Mage target = new Mage("Target");

        DeterministicRandom random = new DeterministicRandom()
                .withDouble(0.9)
                .withInt(0)
                .withDouble(0.9)
                .withDouble(0.9)
                .withInt(6)
                .withDouble(0.9);

        AttackReport lowHit = attacker.attack(target, random, ActionType.NORMAL_ATTACK);
        assertEquals(13, lowHit.getHits().get(0).getDamage());

        AttackReport highHit = attacker.attack(target, random, ActionType.NORMAL_ATTACK);
        assertEquals(19, highHit.getHits().get(0).getDamage());

        target.takeDamage(1_000);
        assertEquals(0, target.getHealth());
    }

    @Test
    void specialCanOnlyBeUsedOnce() {
        Warrior attacker = new Warrior("Attacker");
        Tank target = new Tank("Target");

        DeterministicRandom random = new DeterministicRandom()
                .withDouble(0.9)
                .withInt(3)
                .withDouble(0.9);

        attacker.attack(target, random, ActionType.SPECIAL_ABILITY);
        assertFalse(attacker.canUseSpecial());

        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() {
                attacker.attack(target, new DeterministicRandom(), ActionType.SPECIAL_ABILITY);
            }
        });
    }

    @Test
    void tankShieldConsumesOnDodgedHit() {
        Tank tank = new Tank("Tank");
        Archer archer = new Archer("Archer");

        tank.attack(archer, new DeterministicRandom(), ActionType.SPECIAL_ABILITY);
        assertTrue(tank.isShieldActive());

        DeterministicRandom dodgeRandom = new DeterministicRandom().withDouble(0.15);
        archer.attack(tank, dodgeRandom, ActionType.NORMAL_ATTACK);
        assertFalse(tank.isShieldActive());
    }
}
