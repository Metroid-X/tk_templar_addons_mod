package net.arcspartan.templar_addons.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum KeidranGender {

    MALE(0),
    FEMALE(1);

    private static final KeidranGender[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(KeidranGender::getId)).toArray(KeidranGender[]::new);
    private final int id;

    KeidranGender(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static KeidranGender byId(int id) {
        return BY_ID[id % BY_ID.length];
    }



    //    MALE, FEMALE;
    //
    //    public static KeidranGender valueOf(boolean value)
    //    {
    //        return value ? MALE : FEMALE;
    //    }
    //
    //    public boolean toBool()
    //    {
    //        return this == MALE;
    //    }
    //
    //    public boolean isMale() { return this == MALE; }
    //    public boolean isFemale() { return this == FEMALE; }

}
