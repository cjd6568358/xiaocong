package com.scwang.smartrefresh.layout.util;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ViscousFluidInterpolator implements Interpolator {
    private static final float VISCOUS_FLUID_NORMALIZE = 1.0f / viscousFluid(1.0f);
    private static final float VISCOUS_FLUID_OFFSET = 1.0f - (VISCOUS_FLUID_NORMALIZE * viscousFluid(1.0f));

    private static float viscousFluid(float x) {
        float x2 = x * 8.0f;
        if (x2 < 1.0f) {
            return x2 - (1.0f - ((float) Math.exp(-x2)));
        }
        return 0.36787945f + ((1.0f - 0.36787945f) * (1.0f - ((float) Math.exp(1.0f - x2))));
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        float interpolated = VISCOUS_FLUID_NORMALIZE * viscousFluid(input);
        if (interpolated > 0.0f) {
            return interpolated + VISCOUS_FLUID_OFFSET;
        }
        return interpolated;
    }
}
