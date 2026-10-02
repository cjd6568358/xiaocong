package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements com.tencent.android.tpush.horse.k {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // com.tencent.android.tpush.horse.k
    public void a(int i, String str) {
        com.tencent.android.tpush.a.a.i("TpnsChannel", "ICreateSocketChannelCallback onFailure(" + i + "," + str + ")");
        synchronized (this.a) {
            this.a.y = false;
            if (!this.a.f()) {
                com.tencent.android.tpush.a.a.f("TpnsChannel", "Connect to Xinge Server failed!");
                ChannelException channelException = new ChannelException(i, str);
                for (s sVar : this.a.u) {
                    if (sVar.f != null) {
                        sVar.f.a(sVar.e, channelException, a.a());
                    } else {
                        com.tencent.android.tpush.a.a.i("TpnsChannel", sVar.toString());
                    }
                }
                this.a.u.clear();
            }
            b.a = 0;
        }
        b.f++;
        try {
            if (b.g == null) {
                b.g = new JSONArray();
            }
            if (b.g != null && b.g.length() < 10) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("errorCode", i);
                if (com.tencent.android.tpush.service.n.f() != null) {
                    jSONObject.put("np", (int) com.tencent.android.tpush.service.e.m.k(com.tencent.android.tpush.service.n.f()));
                }
                b.g.put(jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    @Override // com.tencent.android.tpush.horse.k
    public void a(SocketChannel socketChannel, StrategyItem strategyItem) {
        com.tencent.android.tpush.service.channel.a.a aVar;
        com.tencent.android.tpush.a.a.a("TpnsChannel", "ICreateSocketChannelCallback onSuccess(" + socketChannel + "," + socketChannel + ")");
        b.e++;
        synchronized (this.a) {
            this.a.y = false;
            b.r = 0;
            try {
                if (!b.G.equals(strategyItem.a())) {
                    switch (com.tencent.android.tpush.service.e.m.k(com.tencent.android.tpush.service.n.f())) {
                        case 1:
                            b.n = b.l;
                            break;
                        case 2:
                            b.n = b.k;
                            break;
                        case 3:
                            b.n = b.k;
                            break;
                        case 4:
                            b.n = b.k;
                            break;
                    }
                    String unused = b.G = strategyItem.a();
                }
                b.a = 0;
                b bVar = this.a;
                if (strategyItem.i()) {
                    aVar = strategyItem.h() ? new com.tencent.android.tpush.service.channel.a.d(socketChannel, b.a(), strategyItem.a(), strategyItem.b()) : new com.tencent.android.tpush.service.channel.a.c(socketChannel, b.a());
                } else {
                    aVar = new com.tencent.android.tpush.service.channel.a.a(socketChannel, b.a());
                }
                bVar.x = aVar;
                this.a.a(true);
                this.a.v.clear();
                this.a.v.put(this.a.x, new ConcurrentHashMap());
                this.a.C = true;
                this.a.x.start();
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e);
            }
        }
    }
}
