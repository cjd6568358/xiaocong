package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public enum YogaPositionType {
    RELATIVE(0),
    ABSOLUTE(1);

    private int mIntValue;

    YogaPositionType(int intValue) {
        this.mIntValue = intValue;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaPositionType fromInt(int value) {
        switch (value) {
            case 0:
                return RELATIVE;
            case 1:
                return ABSOLUTE;
            default:
                throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
