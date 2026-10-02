package com.tencent.android.tpush.common;

import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class j {
    public static String a(String str) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            Method declaredMethod = cls.getDeclaredMethod("get", String.class);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke(cls, str);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean a() {
        try {
            return (t.c(a("ro.miui.ui.version.code")) && t.c(a(a("ro.miui.ui.version.name"))) && t.c(a(a("ro.miui.internal.storage")))) ? false : true;
        } catch (Throwable th) {
            return false;
        }
    }
}
