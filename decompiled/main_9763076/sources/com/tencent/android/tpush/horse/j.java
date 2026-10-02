package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import java.nio.channels.SocketChannel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j implements b {
    final /* synthetic */ g a;

    j(g gVar) {
        this.a = gVar;
    }

    @Override // com.tencent.android.tpush.horse.b
    public void a(SocketChannel socketChannel, StrategyItem strategyItem) {
        int unused = g.m = 0;
        if (socketChannel == null || strategyItem == null) {
            this.a.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create channel fail!");
            return;
        }
        if (socketChannel.isConnected()) {
            if (this.a.h != null) {
                if (!strategyItem.j() || this.a.f) {
                    this.a.h.a(socketChannel, strategyItem);
                } else {
                    try {
                        socketChannel.close();
                    } catch (Exception e) {
                        com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "socketChannel.close()", e);
                    }
                }
            }
        } else {
            this.a.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create channel fail!");
        }
        if (this.a.f) {
            this.a.f = false;
        }
        synchronized (this.a.d) {
            this.a.e = 2;
            this.a.d.notify();
        }
    }

    @Override // com.tencent.android.tpush.horse.b
    public void a(StrategyItem strategyItem) {
        if (this.a.f) {
            this.a.f = false;
            this.a.b();
            return;
        }
        if (!q.i().b()) {
            if (this.a.e == 0 && !f.i().b()) {
                this.a.e = 2;
                if (this.a.h != null) {
                    this.a.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create channel fail!");
                }
            }
            if (this.a.e == 1) {
                synchronized (this.a.d) {
                    this.a.e = 2;
                    this.a.d.notify();
                }
                return;
            }
            return;
        }
        com.tencent.android.tpush.a.a.e(Constants.HorseLogTag, ">> tcp has remain");
    }
}
