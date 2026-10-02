package com.facebook.react.uimanager;

import bsh.ParserConstants;
import com.facebook.yoga.YogaConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Spacing {
    private static final int[] sFlagsMap = {1, 2, 4, 8, 16, 32, 64, ParserConstants.LSHIFTASSIGN, 256};
    private float mDefaultValue;
    private boolean mHasAliasesSet;
    private final float[] mSpacing;
    private int mValueFlags;

    public Spacing() {
        this(0.0f);
    }

    public Spacing(float defaultValue) {
        this.mSpacing = newFullSpacingArray();
        this.mValueFlags = 0;
        this.mDefaultValue = defaultValue;
    }

    public boolean set(int spacingType, float value) {
        if (FloatUtil.floatsEqual(this.mSpacing[spacingType], value)) {
            return false;
        }
        this.mSpacing[spacingType] = value;
        if (YogaConstants.isUndefined(value)) {
            this.mValueFlags &= sFlagsMap[spacingType] ^ (-1);
        } else {
            this.mValueFlags |= sFlagsMap[spacingType];
        }
        this.mHasAliasesSet = ((this.mValueFlags & sFlagsMap[8]) == 0 && (this.mValueFlags & sFlagsMap[7]) == 0 && (this.mValueFlags & sFlagsMap[6]) == 0) ? false : true;
        return true;
    }

    public float get(int spacingType) {
        float defaultValue = (spacingType == 4 || spacingType == 5) ? Float.NaN : this.mDefaultValue;
        if (this.mValueFlags != 0) {
            if ((this.mValueFlags & sFlagsMap[spacingType]) != 0) {
                return this.mSpacing[spacingType];
            }
            if (this.mHasAliasesSet) {
                int secondType = (spacingType == 1 || spacingType == 3) ? 7 : 6;
                if ((this.mValueFlags & sFlagsMap[secondType]) != 0) {
                    return this.mSpacing[secondType];
                }
                if ((this.mValueFlags & sFlagsMap[8]) != 0) {
                    return this.mSpacing[8];
                }
                return defaultValue;
            }
            return defaultValue;
        }
        return defaultValue;
    }

    public float getRaw(int spacingType) {
        return this.mSpacing[spacingType];
    }

    private static float[] newFullSpacingArray() {
        return new float[]{Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN};
    }
}
