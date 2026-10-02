package com.ut.mini.core.sign;

import android.content.Context;
import com.alibaba.mtl.log.b;
import com.alibaba.mtl.log.e.i;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTSecuritySDKRequestAuthentication implements IUTRequestAuthentication {
    private String Z;
    private String g;
    private Object b = null;
    private Object c = null;
    private Class a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Field f4a = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Field f6b = null;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private Field f7c = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Method f5a = null;
    private int z = 1;
    private boolean E = false;

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getAppkey() {
        return this.g;
    }

    public UTSecuritySDKRequestAuthentication(String aAppkey, String authCode) {
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
                        this.b = cls.getMethod("getInstance", Context.class).invoke(null, b.a().getContext());
                        this.c = cls.getMethod("getSecureSignatureComp", new Class[0]).invoke(this.b, new Object[0]);
                    } catch (Throwable th) {
                        th = th;
                        i.a("initSecurityCheck", th.getMessage());
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cls = null;
                }
                if (cls != null) {
                    try {
                        this.a = Class.forName("com.alibaba.wireless.security.open.SecurityGuardParamContext");
                        this.f4a = this.a.getDeclaredField("appKey");
                        this.f6b = this.a.getDeclaredField("paramMap");
                        this.f7c = this.a.getDeclaredField("requestType");
                        try {
                            method = cls.getMethod("isOpen", new Class[0]);
                        } catch (Throwable th3) {
                            i.a("initSecurityCheck", th3.getMessage());
                            method = null;
                        }
                        if (method != null) {
                            zBooleanValue = ((Boolean) method.invoke(this.b, new Object[0])).booleanValue();
                        } else {
                            try {
                                cls2 = Class.forName("com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent");
                            } catch (Throwable th4) {
                                i.a("initSecurityCheck", th4.getMessage());
                            }
                            zBooleanValue = cls2 == null;
                        }
                        this.z = zBooleanValue ? 1 : 12;
                        this.f5a = Class.forName("com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent").getMethod("signRequest", this.a, String.class);
                    } catch (Throwable th5) {
                        i.a("initSecurityCheck", th5.getMessage());
                    }
                }
                this.E = true;
            }
        }
    }

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getSign(String toBeSignedStr) {
        String str;
        if (!this.E) {
            F();
        }
        if (this.g == null) {
            i.a("UTSecuritySDKRequestAuthentication:getSign", "There is no appkey,please check it!");
            return null;
        }
        if (toBeSignedStr == null) {
            return null;
        }
        if (this.b == null || this.a == null || this.f4a == null || this.f6b == null || this.f7c == null || this.f5a == null || this.c == null) {
            str = null;
        } else {
            try {
                Object objNewInstance = this.a.newInstance();
                this.f4a.set(objNewInstance, this.g);
                ((Map) this.f6b.get(objNewInstance)).put("INPUT", toBeSignedStr);
                this.f7c.set(objNewInstance, Integer.valueOf(this.z));
                str = (String) this.f5a.invoke(this.c, objNewInstance, this.Z);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                str = null;
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                str = null;
            } catch (InstantiationException e3) {
                e3.printStackTrace();
                str = null;
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                str = null;
            }
        }
        return str;
    }

    public String getAuthCode() {
        return this.Z;
    }
}
