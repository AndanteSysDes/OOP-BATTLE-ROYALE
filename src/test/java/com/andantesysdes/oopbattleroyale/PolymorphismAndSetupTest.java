package com.andantesysdes.oopbattleroyale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolymorphismAndSetupTest {
    @Test
    void subclassesExposeDifferentSpecialNamesViaBaseType() {
        Character[] characters = new Character[] {
                new Warrior("W"),
                new Mage("M"),
                new Archer("A"),
                new Tank("T")
        };

        assertEquals("Heavy Strike", characters[0].getSpecialAbilityName());
        assertEquals("Fireball", characters[1].getSpecialAbilityName());
        assertEquals("Double Shot", characters[2].getSpecialAbilityName());
        assertEquals("Shield", characters[3].getSpecialAbilityName());
    }

    @Test
    void battleRoyaleSetupCreatesExactlyOnePerClass() {
        MatchFactory factory = new MatchFactory();
        List<Character> roster = factory.createBattleRoyaleRoster();

        int warriors = 0;
        int mages = 0;
        int archers = 0;
        int tanks = 0;

        for (int i = 0; i < roster.size(); i++) {
            Character fighter = roster.get(i);
            if (fighter instanceof Warrior) {
                warriors++;
            } else if (fighter instanceof Mage) {
                mages++;
            } else if (fighter instanceof Archer) {
                archers++;
            } else if (fighter instanceof Tank) {
                tanks++;
            }
        }

        assertEquals(4, roster.size());
        assertEquals(1, warriors);
        assertEquals(1, mages);
        assertEquals(1, archers);
        assertEquals(1, tanks);
    }

    @Test
    void oneVsOneRequiresDistinctClasses() {
        MatchFactory factory = new MatchFactory();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                factory.createOneVsOne(CharacterClassType.MAGE, CharacterClassType.MAGE);
            }
        });

        List<Character> roster = factory.createOneVsOne(CharacterClassType.MAGE, CharacterClassType.TANK);
        assertEquals(2, roster.size());
        assertNotEquals(roster.get(0).getClass(), roster.get(1).getClass());
    }

    @Test
    void defeatedCharactersCannotActOrBeTargeted() {
        Character dead = new Warrior("Dead");
        dead.takeDamage(10_000);
        Character aliveOne = new Mage("Alive1");
        Character aliveTwo = new Archer("Alive2");

        Battle battle = new Battle("test", List.of(dead, aliveOne, aliveTwo), null,
                new DeterministicRandom(), new java.util.Scanner(""), System.out);

        List<Character> order = battle.buildTurnOrder();
        assertEquals(2, order.size());
        assertTrue(order.contains(aliveOne));
        assertTrue(order.contains(aliveTwo));

        List<Character> targets = battle.getLivingTargets(aliveOne);
        assertEquals(1, targets.size());
        assertEquals(aliveTwo, targets.get(0));
    }
}
