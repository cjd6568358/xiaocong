package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public enum YogaExperimentalFeature {
    ROUNDING(0),
    WEB_FLEX_BASIS(1);

    private int mIntValue;

    YogaExperimentalFeature(int intValue) {
        this.mIntValue = intValue;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaExperimentalFeature fromInt(int value) {
        switch (value) {
            case 0:
                return ROUNDING;
            case 1:
                return WEB_FLEX_BASIS;
            default:
                throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
