package com.huawei.hms.update.f.a;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: MultiCardMTKImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class c extends a {
    c() {
    }

    @Override // com.huawei.hms.update.f.a.a
    public int b() {
        try {
            return e();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.c("MultiCardMTKImpl", "Failed to invoke [TelephonyManager].getDefaultSubscription()");
            return -1;
        }
    }

    private int e() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = Class.forName("android.telephony.TelephonyManager");
        Object objInvoke = cls.getDeclaredMethod("getDefault", (Class[]) null).invoke(null, (Object[]) null);
        Method declaredMethod = cls.getDeclaredMethod("getDefaultSim", (Class[]) null);
        declaredMethod.setAccessible(true);
        return ((Integer) declaredMethod.invoke(objInvoke, (Object[]) null)).intValue();
    }

    @Override // com.huawei.hms.update.f.a.a
    Object c() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
        return cls.getDeclaredMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
    }

    @Override // com.huawei.hms.update.f.a.a
    public boolean d() {
        try {
            return f();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            com.huawei.hms.support.log.a.c("MultiCardMTKImpl", "Failed to invoke FeatureOption.MTK_GEMINI_SUPPORT");
            return false;
        }
    }

    private boolean f() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException {
        Field declaredField = Class.forName("com.mediatek.common.featureoption.FeatureOption").getDeclaredField("MTK_GEMINI_SUPPORT");
        declaredField.setAccessible(true);
        return declaredField.getBoolean(null);
    }
}
