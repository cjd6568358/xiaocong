package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterReq;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class v implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ String a;
    final /* synthetic */ s b;

    v(s sVar, String str) {
        this.b = sVar;
        this.a = str;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "Report uninstall with pkgName = " + this.a + ", reponseCode = " + i);
        }
        if (i == 0) {
            CacheManager.UninstallInfoSuccessByPkgName(this.a);
        } else {
            com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", " uninstall report fail responseCode=" + i);
            this.b.a(i, "服务器处理失败，返回错误", this.a, (TpnsUnregisterReq) jceStruct, aVar);
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        this.b.a(channelException.errorCode, channelException.getMessage(), this.a, (TpnsUnregisterReq) jceStruct, aVar);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
