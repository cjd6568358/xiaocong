package com.hzy.tvmao.utils;

import android.text.TextUtils;
import java.io.IOException;

/* JADX INFO: compiled from: JSONUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static boolean a = true;

    public static String a(Object obj) {
        try {
            return com.hzy.tvmao.model.legacy.api.c.a().b().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T a(Class<T> cls, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return (T) com.hzy.tvmao.model.legacy.api.c.a().b().readValue(str, cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
