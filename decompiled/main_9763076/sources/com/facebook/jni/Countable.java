package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class Countable {

    @DoNotStrip
    private long mInstance = 0;

    public native void dispose();

    static {
        SoLoader.loadLibrary("fb");
    }

    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }
}
