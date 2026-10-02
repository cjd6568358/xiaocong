package android.support.v4.widget;

import android.os.Build;
import android.widget.EdgeEffect;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class EdgeEffectCompat {
    private static final EdgeEffectBaseImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new EdgeEffectApi21Impl();
        } else {
            IMPL = new EdgeEffectBaseImpl();
        }
    }

    static class EdgeEffectBaseImpl {
        EdgeEffectBaseImpl() {
        }

        public void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
            edgeEffect.onPull(deltaDistance);
        }
    }

    static class EdgeEffectApi21Impl extends EdgeEffectBaseImpl {
        EdgeEffectApi21Impl() {
        }

        @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectBaseImpl
        public void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
            edgeEffect.onPull(deltaDistance, displacement);
        }
    }

    public static void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
        IMPL.onPull(edgeEffect, deltaDistance, displacement);
    }
}
