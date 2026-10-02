package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class Inspector {

    @DoNotStrip
    public static class LocalConnection {
        public native void disconnect();

        public native void sendMessage(String str);
    }

    @DoNotStrip
    public interface RemoteConnection {
    }

    private native LocalConnection connectNative(int i, RemoteConnection remoteConnection);

    private native Page[] getPagesNative();

    private static native Inspector instance();

    static {
        ReactBridge.staticInit();
    }

    @DoNotStrip
    public static class Page {
        private final int mId;
        private final String mTitle;

        public String toString() {
            return "Page{mId=" + this.mId + ", mTitle='" + this.mTitle + "'}";
        }
    }
}
