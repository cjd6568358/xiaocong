package com.huawei.hms.update.f.a;

import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: MultiCardHwImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b extends a {
    b() {
    }

    @Override // com.huawei.hms.update.f.a.a
    public int b() {
        try {
            Object objC = c();
            return ((Integer) objC.getClass().getMethod("getDefaultSubscription", new Class[0]).invoke(objC, new Object[0])).intValue();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.c("MultiCardHwImpl", "Failed to invoke [TelephonyManager].getDefaultSubscription()");
            return -1;
        }
    }

    @Override // com.huawei.hms.update.f.a.a
    public String a(int i) {
        try {
            Object objC = c();
            return (String) objC.getClass().getMethod("getSimOperator", Integer.TYPE).invoke(objC, Integer.valueOf(i));
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.c("MultiCardHwImpl", "Failed to invoke [TelephonyManager].getSimOperator()");
            return Constants.MAIN_VERSION_TAG;
        }
    }

    @Override // com.huawei.hms.update.f.a.a
    Object c() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = Class.forName("android.telephony.MSimTelephonyManager");
        return cls.getDeclaredMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
    }

    @Override // com.huawei.hms.update.f.a.a
    public boolean d() {
        try {
            Object objC = c();
            return ((Boolean) objC.getClass().getMethod("isMultiSimEnabled", new Class[0]).invoke(objC, new Object[0])).booleanValue();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.c("MultiCardHwImpl", "Failed to invoke [TelephonyManager].isMultiSimEnabled()");
            return false;
        }
    }
}
