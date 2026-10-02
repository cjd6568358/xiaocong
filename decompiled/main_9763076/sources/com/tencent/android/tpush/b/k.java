package com.tencent.android.tpush.b;

import android.content.Intent;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.data.MessageId;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.service.cache.CacheManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k implements Runnable {
    final /* synthetic */ Intent a;
    final /* synthetic */ i b;

    k(i iVar, Intent intent) {
        this.b = iVar;
        this.a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        long j;
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c(i.b, "Action -> handleRemotePushMessage");
        }
        long longExtra = this.a.getLongExtra(MessageKey.MSG_ID, 0L);
        long longExtra2 = this.a.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
        long longExtra3 = this.a.getLongExtra(MessageKey.MSG_SERVER_TIME, 0L);
        int intExtra = this.a.getIntExtra(MessageKey.MSG_TTL, 0);
        long longExtra4 = this.a.getLongExtra("type", 1L);
        if (longExtra2 > 0) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            long j2 = (jCurrentTimeMillis - (jCurrentTimeMillis - (longExtra3 / 1000))) - longExtra2;
            if (longExtra >= 0 && intExtra > 0 && intExtra < j2) {
                com.tencent.android.tpush.a.a.i(i.b, "messageDistribute check server time failed, msg discarded cause msg is timeout, msg.ttl:" + intExtra + "<reviseMaxTimeoutSec:" + j2);
                return;
            }
        }
        long longExtra5 = this.a.getLongExtra("accId", 0L);
        String str = this.a.getPackage();
        try {
            RegisterEntity currentAppRegisterEntity = CacheManager.getCurrentAppRegisterEntity(this.b.d);
            if (currentAppRegisterEntity != null && !com.tencent.android.tpush.service.e.m.b(currentAppRegisterEntity.packageName) && str.equals(currentAppRegisterEntity.packageName) && longExtra5 == currentAppRegisterEntity.accessId && currentAppRegisterEntity.state == 1) {
                return;
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i(i.b, th.toString());
        }
        String stringExtra = this.a.getStringExtra(MessageKey.MSG_DATE);
        long longExtra6 = this.a.getLongExtra(MessageKey.MSG_EXTRA_PUSHTIME, 0L);
        long longExtra7 = this.a.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L);
        long longExtra8 = this.a.getLongExtra(MessageKey.MSG_CREATE_MULTIPKG, 0L);
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        MessageId messageId = new MessageId();
        messageId.id = longExtra;
        messageId.isAck = (short) 0;
        messageId.accessId = longExtra5;
        messageId.host = this.a.getLongExtra(MessageKey.MSG_EXTRA_HOST, 0L);
        messageId.port = this.a.getIntExtra(MessageKey.MSG_EXTRA_PORT, 0);
        messageId.pact = this.a.getByteExtra(MessageKey.MSG_EXTRA_PACT, (byte) 0);
        messageId.apn = com.tencent.android.tpush.service.e.m.k(this.b.d);
        messageId.isp = com.tencent.android.tpush.service.e.m.l(this.b.d);
        messageId.pushTime = longExtra6;
        messageId.serviceHost = this.a.getStringExtra(MessageKey.MSG_SERVICE_PACKAGE_NAME);
        messageId.receivedTime = jCurrentTimeMillis2;
        messageId.pkgName = str;
        messageId.busiMsgId = longExtra7;
        messageId.timestamp = longExtra2;
        messageId.msgType = longExtra4;
        messageId.multiPkg = longExtra8;
        messageId.date = stringExtra;
        long j3 = 259200000;
        if (intExtra > 0) {
            j3 = ((long) intExtra) * 1000;
        } else if (longExtra > 0 && intExtra == 0) {
            j3 = 30000;
        }
        if (longExtra3 > 0 && longExtra2 > 0) {
            j = j3 + ((longExtra3 - longExtra2) * 1000) + jCurrentTimeMillis2;
        } else {
            j = j3 + jCurrentTimeMillis2;
        }
        this.a.putExtra(MessageKey.MSG_TIME_GAP, jCurrentTimeMillis2 - (1000 * longExtra3));
        this.a.putExtra(MessageKey.MSG_EXPIRE_TIME, j);
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(i.b, ">> msg from service,  @msgId=" + messageId.id + " @accId=" + messageId.accessId + " @timeUs=" + longExtra6 + " @recTime=" + messageId.receivedTime + " @msg.date=" + stringExtra + " @msg.busiMsgId=" + longExtra7 + " @msg.timestamp=" + longExtra2 + " @msg.type=" + longExtra4 + " @msg.multiPkg=" + longExtra8 + " @msg.serverTime=" + longExtra3 + " @msg.ttl=" + intExtra + " @expire_time=" + j + " @currentTimeMillis=" + jCurrentTimeMillis2);
        }
        if (d.g(this.b.d, longExtra5).contains("@" + messageId.id + str + "@")) {
            com.tencent.android.tpush.a.a.j(i.b, "getNotifiedMsgIds contain the msgId id, return");
            return;
        }
        if (d.a().b(this.b.d, str, messageId.id)) {
            com.tencent.android.tpush.a.a.j(i.b, ">> msgId:" + messageId.id + " has been acked, return");
            return;
        }
        messageId.pkgName = str;
        if (messageId.id > 0) {
            d.a().a(this.b.d, str, messageId);
        }
        d.a().a(this.b.d, this.a);
        this.b.c(this.a);
    }
}
