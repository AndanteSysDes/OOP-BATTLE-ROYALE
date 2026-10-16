# OOP Battle Royale (Java 25)

A beginner-friendly terminal battle game implemented with object-oriented design.

## Requirements

- JDK 25
- Maven 3.9+

## Build and Test

```bash
mvn clean test
```

## Run

```bash
mvn exec:java
```

## Design Overview

- `Game` handles menus, input validation, mode selection, and replay flow.
- `Battle` handles turn-based combat rounds, speed order, targeting, and combat logs.
- `Character` is the base class with shared combat state (`name`, `health`, `attackDamage`, `speed`) and core methods (`attack`, `takeDamage`, `isAlive`).
- `Warrior`, `Mage`, `Archer`, and `Tank` override special behavior to demonstrate inheritance, method overriding, and polymorphism.
- `MatchFactory` creates mode rosters for `1 vs 1` and `Battle Royale`.

## Rules Implemented

- Main menu: `1 vs 1`, `Battle Royale`, `Exit`.
- After each match: `Replay` or `Exit`.
- In `1 vs 1`, player picks a class and CPU must pick a different class.
- In `Battle Royale`, exactly one fighter of each class is used.
- Every round, each living fighter acts once in descending speed order (random tie break).
- Defeated fighters cannot act or be targeted.
- Targets are selected automatically from random living opponents.
- Player action each turn: `Normal Attack` or unused `Special Ability`.
- Damage mechanics:
  - Random variation: `-3` to `+3`
  - Critical hit chance: `10%`, damage `x1.5` (rounded down)
  - Dodge chance: `10%` (Archer targets: `20%`)
- Special abilities (once per match):
  - Warrior: `Heavy Strike` (+12 on one hit)
  - Mage: `Fireball` (+12 on one hit)
  - Archer: `Double Shot` (two consecutive hits on same target if still alive)
  - Tank: `Shield` (next incoming hit damage halved, rounded down; dodged incoming hit also consumes shield)
- HP is clamped at zero.

## Test Coverage

The included automated tests focus on core behavior:

- damage bounds and HP clamping
- one-time special ability usage
- subclass/polymorphic behavior
- turn eligibility and target eligibility for alive fighters
- mode setup constraints and roster composition
