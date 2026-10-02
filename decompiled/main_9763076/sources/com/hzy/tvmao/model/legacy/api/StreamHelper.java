package com.hzy.tvmao.model.legacy.api;

import android.content.Context;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StreamHelper {
    public static native synchronized byte[] dec(byte[] bArr);

    public static native synchronized byte[] dec1(byte[] bArr);

    public static native synchronized byte[] dec2(byte[] bArr);

    public static native synchronized byte[] enc(byte[] bArr);

    public static native synchronized byte[] enc1(byte[] bArr);

    public static native synchronized byte[] enc2(byte[] bArr);

    public static native synchronized boolean init(Context context, String str);

    static {
        System.loadLibrary("kksdk");
    }

    public static String a(String str) {
        try {
            return new String(Base64.encode(enc2(str.getBytes(HTTP.UTF_8)), 0), HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
