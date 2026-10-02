package com.youzan.spiderman.cache;

import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ResourceResponse {
    private String a;
    private String b;
    private InputStream c;

    public ResourceResponse(String mimeType, String encoding, InputStream data) {
        this.a = mimeType;
        this.b = encoding;
        this.c = data;
    }

    public String getMimeType() {
        return this.a;
    }

    public String getEncoding() {
        return this.b;
    }

    public InputStream getInputStream() {
        return this.c;
    }
}
