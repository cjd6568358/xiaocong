package com.tencent.android.tpush.service.channel.a;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.b.e;
import com.tencent.android.tpush.service.channel.b.h;
import com.tencent.android.tpush.service.channel.b.i;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends a {
    protected String l;
    protected String m;
    private boolean n;

    public c(SocketChannel socketChannel, b bVar) {
        super(socketChannel, bVar);
        this.l = null;
        this.m = null;
        this.n = false;
        this.m = this.g + (this.h == 80 ? Constants.MAIN_VERSION_TAG : ":" + this.h);
        this.l = "/";
        this.i = 1;
    }

    protected c(SocketChannel socketChannel, b bVar, String str, int i, String str2) {
        super(socketChannel, bVar);
        this.l = null;
        this.m = null;
        this.n = false;
        this.g = str;
        this.h = i;
        this.m = str + (i == 80 ? Constants.MAIN_VERSION_TAG : ":" + i);
        this.l = str2;
    }

    @Override // com.tencent.android.tpush.service.channel.a.a
    public void a(a aVar, com.tencent.android.tpush.service.channel.b.d dVar) throws InnerException {
        if (dVar instanceof com.tencent.android.tpush.service.channel.b.a) {
            for (i iVar : ((com.tencent.android.tpush.service.channel.b.a) dVar).i) {
                com.tencent.android.tpush.a.a.c("TpnsHttpClient-Type", "clientDidReceivePacket  httppacket " + iVar);
                this.a.b(aVar, iVar);
            }
            c();
            return;
        }
        throw new InnerException("packet is not instance of Http****Packet!");
    }

    @Override // com.tencent.android.tpush.service.channel.a.a
    public void a(a aVar, e eVar) throws InnerException {
        this.n = true;
        if (eVar instanceof com.tencent.android.tpush.service.channel.b.b) {
            com.tencent.android.tpush.service.channel.b.b bVar = (com.tencent.android.tpush.service.channel.b.b) eVar;
            com.tencent.android.tpush.a.a.c("TpnsHttpClient-Type", "clientDidSendPacket send httppacket " + bVar);
            for (Object obj : bVar.d) {
                if ((((h) obj).h() & 127) != 7) {
                    this.a.a(aVar, (i) obj);
                }
            }
            return;
        }
        throw new InnerException("packet is not instance of Http****Packet!");
    }

    @Override // com.tencent.android.tpush.service.channel.a.a
    protected boolean b() {
        if (this.f == null && !this.n) {
            ArrayList arrayListA = this.a.a(this, 16);
            if (arrayListA.size() > 0) {
                com.tencent.android.tpush.service.channel.b.b bVar = new com.tencent.android.tpush.service.channel.b.b(this.m, this.l);
                bVar.a(this.d);
                bVar.a(HTTP.TARGET_HOST, this.m);
                bVar.a(HTTP.USER_AGENT, "TPNS_CLIENT/0.1");
                bVar.a(HTTP.CONTENT_TYPE, "application/binary");
                Iterator it = arrayListA.iterator();
                while (it.hasNext()) {
                    bVar.a((h) it.next());
                }
                this.f = bVar;
            }
        }
        return this.f != null;
    }

    @Override // com.tencent.android.tpush.service.channel.a.a
    protected boolean a() {
        if (!this.n) {
            return false;
        }
        if (this.e == null) {
            this.e = new com.tencent.android.tpush.service.channel.b.a();
            this.e.a(this.d);
        }
        return this.e != null;
    }
}
