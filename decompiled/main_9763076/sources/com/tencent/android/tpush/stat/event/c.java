package com.tencent.android.tpush.stat.event;

import android.content.Context;
import com.tencent.android.tpush.stat.a.h;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends d {
    private String a;
    private int b;
    private int m;
    private Thread n;

    public c(Context context, int i, int i2, Throwable th, Thread thread, long j) {
        super(context, i, j);
        this.m = 100;
        this.n = null;
        a(i2, th);
        this.n = thread;
    }

    private void a(int i, Throwable th) {
        if (th != null) {
            try {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                th.printStackTrace(printWriter);
                this.a = stringWriter.toString();
                this.b = i;
                printWriter.close();
            } catch (OutOfMemoryError e) {
            }
        }
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public EventType b() {
        return EventType.ERROR;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public boolean a(JSONObject jSONObject) throws JSONException {
        h.a(jSONObject, "er", this.a);
        jSONObject.put("ea", this.b);
        if (this.b == 2 || this.b == 3) {
            new com.tencent.android.tpush.stat.a.a(this.l, this.d).a(jSONObject, this.n);
            return true;
        }
        return true;
    }
}
