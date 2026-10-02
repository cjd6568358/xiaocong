package com.huawei.hms.a;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: HwBuildEx.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static final String a = a("ro.build.version.emui", Constants.MAIN_VERSION_TAG);

    /* JADX INFO: renamed from: com.huawei.hms.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: HwBuildEx.java */
    public static class C0014a {
        public static final int a = a.a("ro.build.hw_emui_api_level", 0);
    }

    public static String a(String str, String str2) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) cls.getDeclaredMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("HwBuildEx", "An exception occurred while reading: EMUI_VERSION");
            return str2;
        }
    }

    public static int a(String str, int i) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return ((Integer) cls.getDeclaredMethod("getInt", String.class, Integer.TYPE).invoke(cls, str, Integer.valueOf(i))).intValue();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("HwBuildEx", "An exception occurred while reading: EMUI_SDK_INT");
            return i;
        }
    }
}
