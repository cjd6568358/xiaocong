package com.tencent.android.tpush.b;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    protected String b;
    protected JSONObject a = null;
    protected String c = null;
    private String d = null;
    private String e = null;
    private String f = null;
    private String g = null;

    public abstract int c();

    protected abstract void d();

    protected a(String str) {
        this.b = null;
        this.b = str;
    }

    public String a() {
        return this.c;
    }

    public void b() {
        String strOptString;
        try {
            this.a = new JSONObject(this.b);
        } catch (Exception e) {
            try {
                this.a = new JSONObject(this.b.substring(this.b.indexOf("{"), this.b.lastIndexOf("}") + 1));
            } catch (Exception e2) {
                try {
                    this.a = new JSONObject(this.b.substring(1));
                } catch (Exception e3) {
                    try {
                        this.a = new JSONObject(this.b.substring(2));
                    } catch (Exception e4) {
                        try {
                            this.a = new JSONObject(this.b.substring(3));
                        } catch (Exception e5) {
                        }
                    }
                }
            }
        }
        try {
            if (!this.a.isNull("title")) {
                this.d = this.a.getString("title");
            }
            if (!this.a.isNull("content")) {
                this.e = this.a.getString("content");
            }
            if (!this.a.isNull("custom_content") && (strOptString = this.a.optString("custom_content", Constants.MAIN_VERSION_TAG)) != null && !strOptString.trim().equals("{}")) {
                this.f = strOptString;
            }
            if (!this.a.isNull(MessageKey.MSG_ACCEPT_TIME)) {
                this.g = this.a.optString(MessageKey.MSG_ACCEPT_TIME, Constants.MAIN_VERSION_TAG);
            }
        } catch (Throwable th) {
        }
        d();
        this.c = t.a(this.b).toUpperCase();
    }

    public String e() {
        return this.d;
    }

    public String f() {
        return this.e;
    }

    public String g() {
        return this.f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BaseMessageHolder [msgJson=").append(this.a).append(", msgJsonStr=").append(this.b).append(", title=").append(this.d).append(", content=").append(this.e).append(", customContent=").append(this.f).append(", acceptTime=").append(this.g).append("]");
        return sb.toString();
    }
}
