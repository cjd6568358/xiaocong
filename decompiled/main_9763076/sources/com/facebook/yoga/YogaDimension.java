package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public enum YogaDimension {
    WIDTH(0),
    HEIGHT(1);

    private int mIntValue;

    YogaDimension(int intValue) {
        this.mIntValue = intValue;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaDimension fromInt(int value) {
        switch (value) {
            case 0:
                return WIDTH;
            case 1:
                return HEIGHT;
            default:
                throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
