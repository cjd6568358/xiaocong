package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public enum YogaAlign {
    AUTO(0),
    FLEX_START(1),
    CENTER(2),
    FLEX_END(3),
    STRETCH(4),
    BASELINE(5);

    private int mIntValue;

    YogaAlign(int intValue) {
        this.mIntValue = intValue;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaAlign fromInt(int value) {
        switch (value) {
            case 0:
                return AUTO;
            case 1:
                return FLEX_START;
            case 2:
                return CENTER;
            case 3:
                return FLEX_END;
            case 4:
                return STRETCH;
            case 5:
                return BASELINE;
            default:
                throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
