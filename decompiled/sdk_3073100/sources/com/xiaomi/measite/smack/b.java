package com.xiaomi.measite.smack;

import com.xiaomi.smack.d;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class b implements d {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    @Override // com.xiaomi.smack.d
    public void a(com.xiaomi.smack.a aVar) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.a.b.format(new Date()) + " Connection reconnected (" + this.a.c.hashCode() + ")");
    }

    @Override // com.xiaomi.smack.d
    public void a(com.xiaomi.smack.a aVar, int i, Exception exc) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.a.b.format(new Date()) + " Connection closed (" + this.a.c.hashCode() + ")");
    }

    @Override // com.xiaomi.smack.d
    public void a(com.xiaomi.smack.a aVar, Exception exc) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.a.b.format(new Date()) + " Reconnection failed due to an exception (" + this.a.c.hashCode() + ")");
        exc.printStackTrace();
    }

    @Override // com.xiaomi.smack.d
    public void b(com.xiaomi.smack.a aVar) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.a.b.format(new Date()) + " Connection started (" + this.a.c.hashCode() + ")");
    }
}
