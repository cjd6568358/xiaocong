package com.tencent.android.tpush.service.channel;

import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.XGPushConfig;
import java.util.Random;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class s {
    private static int g = new Random().nextInt();
    public short d;
    public JceStruct e;
    public t f;
    public int a = 0;
    private int h = 0;
    public long b = Long.MAX_VALUE;
    public long c = Long.MAX_VALUE;

    public s(JceStruct jceStruct, t tVar) {
        this.e = null;
        this.d = com.tencent.android.tpush.service.channel.c.d.a(jceStruct.getClass());
        this.e = jceStruct;
        this.f = tVar;
    }

    public s(short s, JceStruct jceStruct, t tVar) {
        this.e = null;
        this.d = s;
        this.e = jceStruct;
        this.f = tVar;
    }

    public void a(com.tencent.android.tpush.service.channel.b.h hVar) {
        hVar.a(this.d);
        switch (this.d & 127) {
            case 7:
                hVar.b((short) 20);
                break;
            default:
                try {
                    hVar.b((short) 1);
                    JceOutputStream jceOutputStream = new JceOutputStream();
                    jceOutputStream.setServerEncoding(HTTP.UTF_8);
                    this.e.writeTo(jceOutputStream);
                    hVar.a(jceOutputStream.toByteArray());
                } catch (Throwable th) {
                    com.tencent.android.tpush.a.a.i("XINGE", "jceMessage.write Error:" + th.getLocalizedMessage());
                    if (XGPushConfig.enableDebug) {
                        th.printStackTrace();
                        return;
                    }
                    return;
                }
                break;
        }
    }

    public boolean a() {
        return (this.d & 127) == 7;
    }

    public boolean b() {
        return (this.d & 127) == 4 || (this.d & 127) == 15 || (this.d & 127) == 5;
    }

    public int c() {
        int i = g + 1;
        g = i;
        this.h = i;
        return this.h;
    }

    public int d() {
        return this.h;
    }

    public String toString() {
        return this.e == null ? "null" : this.e.getClass().getSimpleName() + ":" + this.e + ", " + this.f + " retryTimes " + this.a;
    }
}
