package net.arcspartan.templar_addons.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum KeidranFelineVariant {
//    LYNX(0),
    TIGER(1),
//    SNOW_LEOPARD(2),
    WHITE_TIGER(3)
//    , SERVAL(4)
//    , LEOPARD(5)
//    , COUGAR(6)
//    , JAGUAR(7)
//    , CAT(8)
    ;

    private static final KeidranFelineVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(KeidranFelineVariant::getId)).toArray(KeidranFelineVariant[]::new);
    private final int id;

    KeidranFelineVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static KeidranFelineVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
