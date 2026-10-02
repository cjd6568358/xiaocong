package com.tencent.android.tpush.b;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends a {
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private String j;
    private int k;
    private String l;
    private String m;
    private int n;
    private int o;
    private g p;

    public f(String str) {
        super(str);
        this.d = 0;
        this.e = 1;
        this.f = 1;
        this.g = 1;
        this.h = 0;
        this.i = 0;
        this.j = Constants.MAIN_VERSION_TAG;
        this.k = 1;
        this.l = Constants.MAIN_VERSION_TAG;
        this.m = Constants.MAIN_VERSION_TAG;
        this.n = 0;
        this.o = 0;
        this.p = new g();
    }

    @Override // com.tencent.android.tpush.b.a
    public int c() {
        return 1;
    }

    public int h() {
        return this.d;
    }

    public int i() {
        return this.e;
    }

    public int j() {
        return this.f;
    }

    public int k() {
        return this.g;
    }

    public int l() {
        return this.h;
    }

    public g m() {
        return this.p;
    }

    public int n() {
        return this.i;
    }

    public int o() {
        return this.k;
    }

    public String p() {
        return this.l;
    }

    public String q() {
        return this.j;
    }

    public String r() {
        return this.m;
    }

    public int s() {
        return this.n;
    }

    public int t() {
        return this.o;
    }

    @Override // com.tencent.android.tpush.b.a
    protected void d() {
        this.d = this.a.optInt(MessageKey.MSG_BUILDER_ID);
        this.e = this.a.optInt(MessageKey.MSG_RING, 1);
        this.l = this.a.optString(MessageKey.MSG_RING_RAW);
        this.j = this.a.optString(MessageKey.MSG_ICON_RES);
        this.m = this.a.optString(MessageKey.MSG_SMALL_ICON);
        this.k = this.a.optInt(MessageKey.MSG_LIGHTS, 1);
        this.f = this.a.optInt(MessageKey.MSG_VIBRATE, 1);
        this.i = this.a.optInt(MessageKey.MSG_ICON);
        this.n = this.a.optInt(MessageKey.MSG_ICON_TYPE, 0);
        this.h = this.a.optInt(MessageKey.MSG_NOTIFY_ID);
        this.o = this.a.optInt(MessageKey.MSG_STYLE_ID, 0);
        if (!this.a.isNull(MessageKey.MSG_CLEARABLE)) {
            this.g = this.a.optInt(MessageKey.MSG_CLEARABLE);
        } else {
            this.g = 1;
        }
        if (this.a.isNull("action")) {
            return;
        }
        this.p.a(this.a.getString("action"));
    }
}
