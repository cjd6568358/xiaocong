package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public enum YogaPrintOptions {
    LAYOUT(1),
    STYLE(2),
    CHILDREN(4);

    private int mIntValue;

    YogaPrintOptions(int intValue) {
        this.mIntValue = intValue;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaPrintOptions fromInt(int value) {
        switch (value) {
            case 1:
                return LAYOUT;
            case 2:
                return STYLE;
            case 3:
            default:
                throw new IllegalArgumentException("Unknown enum value: " + value);
            case 4:
                return CHILDREN;
        }
    }
}
