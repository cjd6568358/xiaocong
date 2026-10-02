package com.facebook.soloader;

import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class SoSource {
    public abstract int loadLibrary(String str, int i) throws IOException;

    public abstract File unpackLibrary(String str) throws IOException;

    protected void prepare(int flags) throws IOException {
    }
}
