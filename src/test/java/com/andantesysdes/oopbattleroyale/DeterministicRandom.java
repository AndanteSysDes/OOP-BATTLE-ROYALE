package com.andantesysdes.oopbattleroyale;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class DeterministicRandom extends Random {
    private final Queue<Integer> ints = new LinkedList<Integer>();
    private final Queue<Double> doubles = new LinkedList<Double>();

    public DeterministicRandom withInt(int value) {
        ints.add(value);
        return this;
    }

    public DeterministicRandom withDouble(double value) {
        doubles.add(value);
        return this;
    }

    @Override
    public int nextInt(int bound) {
        if (ints.isEmpty()) {
            return 0;
        }
        int value = ints.remove();
        if (value < 0) {
            value = 0;
        }
        return value % bound;
    }

    @Override
    public double nextDouble() {
        if (doubles.isEmpty()) {
            return 0.99;
        }
        return doubles.remove();
    }
}
