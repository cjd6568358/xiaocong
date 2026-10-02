package com.tencent.android.tpush.b;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m implements Runnable {
    final /* synthetic */ i a;
    private final String b = m.class.getSimpleName();
    private Context c;
    private Intent d;
    private XGIOperateCallback e;

    public m(i iVar, Context context, Intent intent, XGIOperateCallback xGIOperateCallback) {
        this.a = iVar;
        this.c = context;
        this.d = intent;
        this.e = xGIOperateCallback;
    }

    private void a() {
        Intent intent = new Intent(Constants.ACTION_PUSH_MESSAGE);
        intent.setPackage(this.c.getPackageName());
        intent.putExtras(this.d);
        this.c.sendBroadcast(intent);
        String stringExtra = this.d.getStringExtra(MessageKey.MSG_SERVICE_PACKAGE_NAME);
        if (!com.tencent.android.tpush.service.e.m.b(stringExtra)) {
            Intent intent2 = new Intent("com.tencent.android.tpush.action.ack.sdk2srv.V3");
            intent2.setPackage(stringExtra);
            intent2.putExtras(this.d);
            this.c.sendBroadcast(intent2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0291 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:61:0x0293 A[Catch: all -> 0x0095, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x0009, B:8:0x0013, B:12:0x004d, B:13:0x0080, B:15:0x0082, B:17:0x008c, B:18:0x0093, B:24:0x0099, B:26:0x00e8, B:28:0x00ee, B:30:0x00f8, B:31:0x0138, B:33:0x013b, B:35:0x0149, B:37:0x01a0, B:42:0x01c3, B:44:0x01c7, B:45:0x01e5, B:47:0x0210, B:48:0x0235, B:50:0x023b, B:52:0x0245, B:54:0x0258, B:56:0x027c, B:58:0x028b, B:61:0x0293, B:79:0x02f6, B:62:0x02a1, B:64:0x02a4, B:67:0x02b5, B:39:0x01a6, B:40:0x01c0, B:73:0x02db, B:70:0x02d0, B:76:0x02e2, B:78:0x02eb), top: B:82:0x0005, inners: #0, #2, #4 }] */
    /* JADX WARN: Code duplicated, block: B:79:0x02f6 A[Catch: all -> 0x0095, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x0009, B:8:0x0013, B:12:0x004d, B:13:0x0080, B:15:0x0082, B:17:0x008c, B:18:0x0093, B:24:0x0099, B:26:0x00e8, B:28:0x00ee, B:30:0x00f8, B:31:0x0138, B:33:0x013b, B:35:0x0149, B:37:0x01a0, B:42:0x01c3, B:44:0x01c7, B:45:0x01e5, B:47:0x0210, B:48:0x0235, B:50:0x023b, B:52:0x0245, B:54:0x0258, B:56:0x027c, B:58:0x028b, B:61:0x0293, B:79:0x02f6, B:62:0x02a1, B:64:0x02a4, B:67:0x02b5, B:39:0x01a6, B:40:0x01c0, B:73:0x02db, B:70:0x02d0, B:76:0x02e2, B:78:0x02eb), top: B:82:0x0005, inners: #0, #2, #4 }] */
    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.a) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.c(this.b, "Action -> handlerPushMessage");
            }
            try {
                try {
                    try {
                        long longExtra = this.d.getLongExtra(MessageKey.MSG_EXPIRE_TIME, 0L);
                        String str = this.d.getPackage();
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        long longExtra2 = this.d.getLongExtra(MessageKey.MSG_ID, -1L);
                        n nVarA = n.a(this.c, this.d);
                        if (longExtra > 0 && jCurrentTimeMillis > longExtra) {
                            com.tencent.android.tpush.a.a.i("PushMessageHandler", "msg is expired, currentTimeMillis=" + jCurrentTimeMillis + ", expire_time=" + longExtra + ". msgid = " + longExtra2);
                            XGPushManager.msgAck(this.c, nVarA);
                            return;
                        }
                        if (!i.a(Long.valueOf(longExtra2))) {
                            XGPushManager.msgAck(this.c, nVarA);
                            return;
                        }
                        com.tencent.android.tpush.a.a.a(2, longExtra2);
                        long longExtra3 = this.d.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L);
                        long longExtra4 = this.d.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
                        String str2 = "@" + longExtra2 + str + "@";
                        long longExtra5 = this.d.getLongExtra("accId", -1L);
                        List accessidList = XGPushConfig.getAccessidList(this.c);
                        if (accessidList != null && accessidList.size() > 0 && !accessidList.contains(Long.valueOf(longExtra5))) {
                            com.tencent.android.tpush.a.a.j(this.b, "PushMessageRunnable match accessId failed, message droped cause accessId:" + longExtra5 + " not in " + accessidList + " msgId = " + str2);
                            d.a().b(this.c, longExtra2);
                            XGPushManager.msgAck(this.c, nVarA);
                            return;
                        }
                        String strG = d.g(this.c, longExtra5);
                        if (!strG.contains(str2)) {
                            com.tencent.android.tpush.common.m.a(this.c, "tpush_msgId_" + longExtra5, str2 + strG, true);
                            String strA = com.tencent.android.tpush.common.m.a(this.c, "tpush_msgId_" + longExtra5, true);
                            if (strA == null || !strA.contains(str2)) {
                                com.tencent.android.tpush.a.a.i(this.b, str2 + " flag write failed");
                                return;
                            }
                            if (XGPushConfig.enableDebug) {
                                com.tencent.android.tpush.a.a.f(this.b, "Receiver msg from server :" + nVarA.toString());
                            }
                            XGPushManager.msgAck(this.c, nVarA);
                            com.tencent.android.tpush.service.d.a.b(this.c, this.d);
                            String stringExtra = this.d.getStringExtra(MessageKey.MSG_SERVICE_PACKAGE_NAME);
                            if (!this.c.getPackageName().equals(stringExtra)) {
                                com.tencent.android.tpush.a.a.f(this.b, "Receiver msg from other app :" + stringExtra);
                                com.tencent.android.tpush.service.d.a.a(this.c, this.d);
                            }
                            a aVarG = nVarA.g();
                            if (aVarG != null && !com.tencent.android.tpush.service.e.m.b(nVarA.f())) {
                                try {
                                    if (new e(this.c, this.d).a(nVarA, longExtra4, longExtra3, longExtra2)) {
                                        a();
                                        com.tencent.android.tpush.service.d.a.c(this.c, this.d);
                                        d.a().c(this.c, nVarA.b());
                                        if (aVarG.c() == 1) {
                                            nVarA.a();
                                            com.tencent.android.tpush.service.d.a.d(this.c, this.d);
                                        }
                                    } else {
                                        d.a().d(this.c, nVarA.b());
                                    }
                                    th = null;
                                } catch (Throwable th) {
                                    th = th;
                                    com.tencent.android.tpush.a.a.c(this.b, "unknown error", th);
                                    d.a().d(this.c, nVarA.b());
                                }
                            }
                            if (this.e != null) {
                                if (th != null) {
                                    this.e.onFail(Constants.MAIN_VERSION_TAG, -1, th.toString());
                                } else {
                                    this.e.onSuccess(Constants.MAIN_VERSION_TAG, 0);
                                }
                            }
                        }
                        this.e = null;
                        th = null;
                        if (this.e != null) {
                            if (th != null) {
                                this.e.onFail(Constants.MAIN_VERSION_TAG, -1, th.toString());
                            } else {
                                this.e.onSuccess(Constants.MAIN_VERSION_TAG, 0);
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        com.tencent.android.tpush.a.a.c(this.b, "unknown error", th);
                    }
                } catch (IllegalArgumentException e) {
                    th = e;
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "push msg type error", th);
                }
            } catch (JSONException e2) {
                th = e2;
                com.tencent.android.tpush.a.a.c(this.b, "push parse error", th);
            }
        }
    }
}
