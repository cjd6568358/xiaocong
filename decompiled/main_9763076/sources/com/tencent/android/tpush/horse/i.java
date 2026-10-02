package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import java.nio.channels.SocketChannel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class i implements b {
    final /* synthetic */ g a;

    i(g gVar) {
        this.a = gVar;
    }

    @Override // com.tencent.android.tpush.horse.b
    public void a(SocketChannel socketChannel, StrategyItem strategyItem) {
        int unused = g.m = 0;
        if (q.i().b()) {
            this.a.e = 1;
        }
        synchronized (this.a.d) {
            if (this.a.e == 1) {
                try {
                    this.a.d.wait();
                } catch (Exception e) {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "lock.wait", e);
                }
            }
        }
        if (socketChannel.isConnected() && !q.i().c()) {
            if (this.a.h != null) {
                if (!strategyItem.j()) {
                    this.a.h.a(socketChannel, strategyItem);
                    return;
                }
                try {
                    socketChannel.close();
                    return;
                } catch (Exception e2) {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "socketChannel.close()", e2);
                    return;
                }
            }
            com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> mcreateSocket channelCallback is null ");
            return;
        }
        if (!socketChannel.isConnected() && !q.i().c()) {
            this.a.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create channel fail httpChannelCallback !");
        }
    }

    @Override // com.tencent.android.tpush.horse.b
    public void a(StrategyItem strategyItem) {
        if (!q.i().b() && !f.i().b() && this.a.e == 0) {
            this.a.e = 2;
            if (this.a.h != null) {
                this.a.h.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create http channel fail!");
            }
        }
    }
}
