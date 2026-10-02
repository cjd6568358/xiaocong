package com.huawei.hms.update.f.a;

import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: MultiCard.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    public abstract int b();

    abstract Object c() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException;

    public abstract boolean d();

    public static a a() {
        b bVar = new b();
        if (bVar.d()) {
            com.huawei.hms.support.log.a.b("MultiCard", "Return HW instance.");
            return bVar;
        }
        c cVar = new c();
        if (cVar.d()) {
            com.huawei.hms.support.log.a.b("MultiCard", "Return MTK instance.");
            return cVar;
        }
        return null;
    }

    public String a(int i) {
        return Constants.MAIN_VERSION_TAG;
    }

    public int b(int i) {
        try {
            Object objC = c();
            return ((Integer) objC.getClass().getDeclaredMethod("getSimState", Integer.TYPE).invoke(objC, Integer.valueOf(i))).intValue();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.c("MultiCard", "Failed to call [TelephonyManager].getSimState()");
            return 0;
        }
    }

    public String c(int i) {
        try {
            Object objC = c();
            return (String) objC.getClass().getMethod("getSubscriberId", Integer.TYPE).invoke(objC, Integer.valueOf(i));
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.c("MultiCard", "Failed to call [TelephonyManager].getSubscriberId()");
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
