package com.alibaba.mtl.log.sign;

import android.content.Context;
import com.alibaba.mtl.log.a;
import com.alibaba.mtl.log.e.i;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SecurityRequestAuth implements IRequestAuth {
    private String Z;
    private String g;
    private Object b = null;
    private Object c = null;
    private Class a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Field f40a = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Field f42b = null;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private Field f43c = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Method f41a = null;
    private int z = 1;
    private boolean E = false;

    @Override // com.alibaba.mtl.log.sign.IRequestAuth
    public String getAppkey() {
        return this.g;
    }

    public SecurityRequestAuth(String aAppkey, String authCode) {
        this.g = null;
        this.g = aAppkey;
        this.Z = authCode;
    }

    private synchronized void F() {
        Class<?> cls;
        Method method;
        boolean zBooleanValue;
        Class<?> cls2 = null;
        synchronized (this) {
            if (!this.E) {
                try {
                    cls = Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
                    try {
                        this.b = cls.getMethod("getInstance", Context.class).invoke(null, a.getContext());
                        this.c = cls.getMethod("getSecureSignatureComp", new Class[0]).invoke(this.b, new Object[0]);
                    } catch (Throwable th) {
                        th = th;
                        i.a("SecurityRequestAuth", "initSecurityCheck", th);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cls = null;
                }
                if (cls != null) {
                    try {
                        this.a = Class.forName("com.alibaba.wireless.security.open.SecurityGuardParamContext");
                        this.f40a = this.a.getDeclaredField("appKey");
                        this.f42b = this.a.getDeclaredField("paramMap");
                        this.f43c = this.a.getDeclaredField("requestType");
                        try {
                            method = cls.getMethod("isOpen", new Class[0]);
                        } catch (Throwable th3) {
                            i.a("SecurityRequestAuth", "initSecurityCheck", th3);
                            method = null;
                        }
                        if (method != null) {
                            zBooleanValue = ((Boolean) method.invoke(this.b, new Object[0])).booleanValue();
                        } else {
                            try {
                                cls2 = Class.forName("com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent");
                            } catch (Throwable th4) {
                                i.a("SecurityRequestAuth", "initSecurityCheck", th4);
                            }
                            zBooleanValue = cls2 == null;
                        }
                        this.z = zBooleanValue ? 1 : 12;
                        this.f41a = Class.forName("com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent").getMethod("signRequest", this.a, String.class);
                    } catch (Throwable th5) {
                        i.a("SecurityRequestAuth", "initSecurityCheck", th5);
                    }
                }
                this.E = true;
            }
        }
    }

    @Override // com.alibaba.mtl.log.sign.IRequestAuth
    public String getSign(String toBeSignedStr) {
        String str;
        if (!this.E) {
            F();
        }
        if (this.g == null) {
            i.a("SecurityRequestAuth", "There is no appkey,please check it!");
            return null;
        }
        if (toBeSignedStr == null) {
            return null;
        }
        if (this.b == null || this.a == null || this.f40a == null || this.f42b == null || this.f43c == null || this.f41a == null || this.c == null) {
            str = null;
        } else {
            try {
                Object objNewInstance = this.a.newInstance();
                this.f40a.set(objNewInstance, this.g);
                ((Map) this.f42b.get(objNewInstance)).put("INPUT", toBeSignedStr);
                this.f43c.set(objNewInstance, Integer.valueOf(this.z));
                str = (String) this.f41a.invoke(this.c, objNewInstance, this.Z);
            } catch (Exception e) {
                e.printStackTrace();
                str = null;
            }
        }
        return str;
    }
}
