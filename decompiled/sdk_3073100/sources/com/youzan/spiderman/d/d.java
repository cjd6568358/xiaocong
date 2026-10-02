package com.youzan.spiderman.d;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import okhttp3.internal.Util;

/* JADX INFO: compiled from: StreamResult.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    private Charset a;
    private InputStream b;
    private Reader c;

    public d(Charset charset, InputStream inputStream, Reader charStreamReader) {
        this.a = charset;
        this.b = inputStream;
        this.c = charStreamReader;
    }

    public InputStream a() {
        return this.b;
    }

    public Reader b() {
        return this.c;
    }

    public boolean c() {
        return this.a == null || this.a.equals(Util.UTF_8);
    }
}
