package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public enum YogaWrap {
    NO_WRAP(0),
    WRAP(1);

    private int mIntValue;

    YogaWrap(int intValue) {
        this.mIntValue = intValue;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaWrap fromInt(int value) {
        switch (value) {
            case 0:
                return NO_WRAP;
            case 1:
                return WRAP;
            default:
                throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
