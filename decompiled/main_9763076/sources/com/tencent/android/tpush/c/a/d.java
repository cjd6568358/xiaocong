package com.tencent.android.tpush.c.a;

import com.tencent.android.tpush.common.t;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d implements InvocationHandler {
    final /* synthetic */ b a;

    public d(b bVar) {
        this.a = bVar;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        if (method == null) {
            return null;
        }
        if (method.getName().equals("onConnected")) {
            t.a("reveiver onConnected", this.a.b);
            this.a.b();
            return method;
        }
        if (method.getName().equals("onResult")) {
            t.a("reveiver onResult", this.a.b);
            if (objArr != null) {
                try {
                    if (objArr.length > 0) {
                        Object obj2 = objArr[0];
                        t.a("TokenResult =  " + Class.forName("com.huawei.hms.support.api.entity.push.TokenResp").getDeclaredMethod("getToken", new Class[0]).invoke(Class.forName("com.huawei.hms.support.api.push.TokenResult").getDeclaredMethod("getTokenRes", new Class[0]).invoke(Class.forName("com.huawei.hms.support.api.push.TokenResult").cast(obj2), new Object[0]), new Object[0]), this.a.b);
                        return method;
                    }
                    return method;
                } catch (Exception e) {
                    t.a("TokenResult =  " + e.getMessage(), this.a.b);
                    return method;
                }
            }
            return method;
        }
        if ("onConnectionFailed".equals(method.getName()) && objArr != null) {
            try {
                if (objArr.length > 0) {
                    t.a("reveiver " + method.getName() + " errorcode " + ((Integer) Class.forName("com.huawei.hms.api.ConnectionResult").getDeclaredMethod("getErrorCode", new Class[0]).invoke(Class.forName("com.huawei.hms.api.ConnectionResult").cast(objArr[0]), new Object[0])).intValue(), this.a.b);
                    return method;
                }
                return method;
            } catch (Throwable th) {
                t.a("onConnectionFailed + " + th.getMessage(), this.a.b);
                return method;
            }
        }
        return method;
    }
}
