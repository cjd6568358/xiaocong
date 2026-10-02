package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class ReactMarker {
    private static MarkerListener sMarkerListener = null;

    public interface MarkerListener {
        void logMarker(String str);
    }

    @DoNotStrip
    public static void logMarker(String name) {
        if (sMarkerListener != null) {
            sMarkerListener.logMarker(name);
        }
    }
}
