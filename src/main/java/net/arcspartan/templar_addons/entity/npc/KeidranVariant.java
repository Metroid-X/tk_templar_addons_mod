package net.arcspartan.templar_addons.entity.npc;

import java.util.Arrays;
import java.util.Comparator;

public enum KeidranVariant {
    TYPE1(0),
    TYPE2(1),
    TYPE3(2),
    TYPE4(3);

    private static final KeidranVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(KeidranVariant::getId)).toArray(KeidranVariant[]::new);
    private final int id;

    KeidranVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static KeidranVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
