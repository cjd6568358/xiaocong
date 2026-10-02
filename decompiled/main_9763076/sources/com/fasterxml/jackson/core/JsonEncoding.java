package com.fasterxml.jackson.core;

import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum JsonEncoding {
    UTF8(HTTP.UTF_8, false),
    UTF16_BE("UTF-16BE", true),
    UTF16_LE("UTF-16LE", false),
    UTF32_BE("UTF-32BE", true),
    UTF32_LE("UTF-32LE", false);

    protected final boolean _bigEndian;
    protected final String _javaName;

    JsonEncoding(String javaName, boolean bigEndian) {
        this._javaName = javaName;
        this._bigEndian = bigEndian;
    }

    public String getJavaName() {
        return this._javaName;
    }

    public boolean isBigEndian() {
        return this._bigEndian;
    }
}
