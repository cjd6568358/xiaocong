package com.baidu.mobstat;

import android.content.Context;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class aw implements l {
    private ba a = ba.a;
    private Object b;
    private Class<?> c;

    public aw(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("proxy is null.");
        }
        if (!"com.baidu.bottom.remote.BPStretegyController2".equals(obj.getClass().getName())) {
            throw new IllegalArgumentException("class isn't com.baidu.bottom.remote.BPStretegyController2");
        }
        this.b = obj;
        this.c = obj.getClass();
    }

    @Override // com.baidu.mobstat.l
    public void a(Context context, JSONObject jSONObject) throws Throwable {
        try {
            a(new Object[]{context, jSONObject}, "startDataAnynalyze", new Class[]{Context.class, JSONObject.class});
        } catch (Exception e) {
            bd.b(e);
            this.a.a(context, jSONObject);
        }
    }

    @Override // com.baidu.mobstat.l
    public void a(Context context, String str) throws Throwable {
        try {
            a(new Object[]{context, str}, "saveRemoteConfig2", new Class[]{Context.class, String.class});
        } catch (Exception e) {
            bd.b(e);
            this.a.a(context, str);
        }
    }

    @Override // com.baidu.mobstat.l
    public void b(Context context, String str) throws Throwable {
        try {
            a(new Object[]{context, str}, "saveRemoteSign", new Class[]{Context.class, String.class});
        } catch (Exception e) {
            bd.b(e);
            this.a.b(context, str);
        }
    }

    @Override // com.baidu.mobstat.l
    public void a(Context context, long j) throws Throwable {
        try {
            a(new Object[]{context, Long.valueOf(j)}, "setLastUpdateTime", new Class[]{Context.class, Long.TYPE});
        } catch (Exception e) {
            bd.b(e);
            this.a.a(context, j);
        }
    }

    @Override // com.baidu.mobstat.l
    public boolean a(Context context) {
        try {
            return ((Boolean) a(new Object[]{context}, "needUpdate", new Class[]{Context.class})).booleanValue();
        } catch (Exception e) {
            bd.b(e);
            return this.a.a(context);
        }
    }

    @Override // com.baidu.mobstat.l
    public boolean b(Context context) {
        try {
            return ((Boolean) a(new Object[]{context}, "canStartService", new Class[]{Context.class})).booleanValue();
        } catch (Exception e) {
            bd.b(e);
            return this.a.b(context);
        }
    }

    private <T> T a(Object[] objArr, String str, Class<?>[] clsArr) {
        return (T) this.c.getMethod(str, clsArr).invoke(this.b, objArr);
    }
}
