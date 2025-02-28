package net.arcspartan.templar_addons.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum KeidranBustSizes {
    FLAT(0),
    LAURA(1),
    FLORA(2),
    MIKE(3),
    KATHRIN(4),
    NATANI(5);


    private static final KeidranBustSizes[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(KeidranBustSizes::getId)).toArray(KeidranBustSizes[]::new);
    private final int id;

    KeidranBustSizes(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static KeidranBustSizes byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
