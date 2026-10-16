package com.andantesysdes.oopbattleroyale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttackReport {
    private final String actionName;
    private final List<HitResult> hits;

    public AttackReport(String actionName, List<HitResult> hits) {
        this.actionName = actionName;
        this.hits = new ArrayList<HitResult>(hits);
    }

    public String getActionName() {
        return actionName;
    }

    public List<HitResult> getHits() {
        return Collections.unmodifiableList(hits);
    }
}
