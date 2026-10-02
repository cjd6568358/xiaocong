package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.au;
import com.tencent.wxop.stat.common.l;
import com.tencent.wxop.stat.common.r;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class e {
    protected static String j = null;
    private StatSpecifyReportedInfo a;
    protected String b;
    protected long c = System.currentTimeMillis() / 1000;
    protected int d;
    protected com.tencent.wxop.stat.common.a e;
    protected int f;
    protected String g;
    protected String h;
    protected String i;
    protected boolean k;
    protected Context l;

    e(Context context, int i, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.b = null;
        this.e = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.k = false;
        this.a = null;
        this.l = context;
        this.d = i;
        this.h = StatConfig.getInstallChannel(context);
        this.i = l.h(context);
        this.b = StatConfig.getAppKey(context);
        if (statSpecifyReportedInfo != null) {
            this.a = statSpecifyReportedInfo;
            if (l.c(statSpecifyReportedInfo.getAppKey())) {
                this.b = statSpecifyReportedInfo.getAppKey();
            }
            if (l.c(statSpecifyReportedInfo.getInstallChannel())) {
                this.h = statSpecifyReportedInfo.getInstallChannel();
            }
            if (l.c(statSpecifyReportedInfo.getVersion())) {
                this.i = statSpecifyReportedInfo.getVersion();
            }
            this.k = statSpecifyReportedInfo.isImportant();
        }
        this.g = StatConfig.getCustomUserId(context);
        this.e = au.a(context).b(context);
        if (a() != EventType.NETWORK_DETECTOR) {
            this.f = l.q(context).intValue();
        } else {
            this.f = -EventType.NETWORK_DETECTOR.a();
        }
        if (com.tencent.a.a.a.a.h.c(j)) {
            return;
        }
        String localMidOnly = StatConfig.getLocalMidOnly(context);
        j = localMidOnly;
        if (l.c(localMidOnly)) {
            return;
        }
        j = "0";
    }

    public abstract EventType a();

    public abstract boolean a(JSONObject jSONObject);

    public boolean b(JSONObject jSONObject) {
        try {
            r.a(jSONObject, "ky", this.b);
            jSONObject.put("et", a().a());
            if (this.e != null) {
                jSONObject.put("ui", this.e.b());
                r.a(jSONObject, "mc", this.e.c());
                int iD = this.e.d();
                jSONObject.put("ut", iD);
                if (iD == 0 && l.u(this.l) == 1) {
                    jSONObject.put("ia", 1);
                }
            }
            r.a(jSONObject, "cui", this.g);
            if (a() != EventType.SESSION_ENV) {
                r.a(jSONObject, "av", this.i);
                r.a(jSONObject, "ch", this.h);
            }
            if (this.k) {
                jSONObject.put("impt", 1);
            }
            r.a(jSONObject, "mid", j);
            jSONObject.put("cch", "wxop");
            jSONObject.put("idx", this.f);
            jSONObject.put("si", this.d);
            jSONObject.put("ts", this.c);
            jSONObject.put("dts", l.a(this.l, false));
            return a(jSONObject);
        } catch (Throwable th) {
            return false;
        }
    }

    public long c() {
        return this.c;
    }

    public StatSpecifyReportedInfo d() {
        return this.a;
    }

    public Context e() {
        return this.l;
    }

    public boolean f() {
        return this.k;
    }

    public String g() {
        try {
            JSONObject jSONObject = new JSONObject();
            b(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
