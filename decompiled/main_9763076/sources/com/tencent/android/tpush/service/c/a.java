package com.tencent.android.tpush.service.c;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.k;
import com.tencent.android.tpush.data.MessageId;
import com.tencent.android.tpush.data.PushClickEntity;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.protocol.TpnsClickClientReport;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClientReport;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushMsg;
import com.tencent.android.tpush.service.e.m;
import com.tencent.android.tpush.service.n;
import com.tencent.android.tpush.service.s;
import com.tencent.android.tpush.service.x;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private PendingIntent i = null;
    private static a b = new a();
    private static final byte[] c = new byte[0];
    private static long d = 0;
    private static volatile boolean e = false;
    private static volatile boolean f = false;
    private static volatile boolean g = false;
    private static volatile boolean h = false;
    public static long a = 306000;

    private a() {
    }

    public static a a() {
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, Long l) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayListB = b(context);
        if (e) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", ">> msg ack is uploading , this time will give up! MessageId = " + l);
            return;
        }
        ArrayList arrayListC = c(context, (List) arrayListB);
        if (arrayListC != null && arrayListC.size() > 0) {
            e = true;
            d = jCurrentTimeMillis;
        } else {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "Null report list with msgId " + l);
        }
        com.tencent.android.tpush.a.a.a(5, arrayListC);
        s.a().a(arrayListC, new b(this, arrayListC, context));
    }

    public synchronized void a(Context context, Intent intent) {
        if (context != null && intent != null) {
            try {
                long longExtra = intent.getLongExtra(MessageKey.MSG_ID, -1L);
                String stringExtra = intent.getStringExtra(Constants.FLAG_PACK_NAME);
                MessageId messageId = (MessageId) intent.getSerializableExtra("MessageId");
                com.tencent.android.tpush.a.a.a(4, longExtra);
                if (messageId != null) {
                    com.tencent.android.tpush.a.a.a(Constants.ServiceLogTag, "verify " + stringExtra + longExtra);
                    com.tencent.android.tpush.b.d.a().c(context, stringExtra, longExtra);
                    c(context, stringExtra, messageId);
                    a(context, stringExtra, longExtra, (short) 1);
                    a(context, Long.valueOf(longExtra));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized void b(Context context, Intent intent) {
        if (context != null && intent != null) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                MessageId messageId = new MessageId();
                messageId.id = intent.getLongExtra(MessageKey.MSG_ID, -1L);
                if (messageId.id < 0) {
                    com.tencent.android.tpush.a.a.a(Constants.ServiceLogTag, "@@ msgSendSDKAck: Not add LocalMsg");
                } else {
                    messageId.accessId = intent.getLongExtra("accId", -1L);
                    messageId.host = intent.getLongExtra(MessageKey.MSG_EXTRA_HOST, -1L);
                    messageId.port = intent.getIntExtra(MessageKey.MSG_EXTRA_PORT, -1);
                    messageId.pact = intent.getByteExtra(MessageKey.MSG_EXTRA_PACT, (byte) -1);
                    messageId.apn = m.k(n.f());
                    messageId.isp = m.l(n.f());
                    messageId.pushTime = intent.getLongExtra(MessageKey.MSG_EXTRA_PUSHTIME, -1L);
                    messageId.serviceHost = n.f().getPackageName();
                    messageId.receivedTime = jCurrentTimeMillis;
                    messageId.pkgName = intent.getPackage();
                    messageId.busiMsgId = intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, -1L);
                    messageId.timestamp = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, -1L);
                    messageId.msgType = intent.getLongExtra("type", -1L);
                    messageId.multiPkg = intent.getLongExtra(MessageKey.MSG_CREATE_MULTIPKG, -1L);
                    messageId.date = intent.getStringExtra(MessageKey.MSG_DATE);
                    b(context, "all", messageId);
                    b(context, messageId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(Context context, MessageId messageId) {
        ArrayList arrayListC = c(context, "all");
        if (g) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "requestSendSDKAck ack is uploading , this time will give up!  msgId =  " + (messageId == null ? null : Long.valueOf(messageId.id)));
        } else {
            ArrayList arrayListB = b(context, (List) arrayListC);
            if (arrayListB == null || arrayListB.size() == 0) {
                com.tencent.android.tpush.a.a.c("SrvMessageManager", "requestSendSDKAck with null list , give up this time");
            } else {
                com.tencent.android.tpush.a.a.c("SrvMessageManager", "requestSendSDKAck with list size = " + arrayListB.size());
                g = true;
                s.a().a(arrayListB, new c(this, messageId, context));
            }
        }
    }

    public synchronized void c(Context context, Intent intent) {
        if (context != null && intent != null) {
            String stringExtra = intent.getStringExtra(Constants.FLAG_PACK_NAME);
            long longExtra = intent.getLongExtra(MessageKey.MSG_ID, -1L);
            if (longExtra <= 0) {
                com.tencent.android.tpush.a.a.a(Constants.ServiceLogTag, "@@ msgClick: Not add LocalMsg");
            } else {
                a(context, stringExtra, new PushClickEntity(longExtra, intent.getLongExtra("accId", -1L), intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, -1L), intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, -1L), stringExtra, intent.getIntExtra(Constants.PUSH_CHANNEL, 1), intent.getLongExtra(Constants.FLAG_CLICK_TIME, System.currentTimeMillis() / 1000), intent.getIntExtra("action", 0)));
                d(context, intent);
            }
        }
    }

    public void a(Context context, TpnsPushMsg tpnsPushMsg, long j, com.tencent.android.tpush.service.channel.a aVar) {
        if (tpnsPushMsg.msgId <= 0) {
            com.tencent.android.tpush.a.a.a(Constants.ServiceLogTag, "@@ msgServiceAck: Not add LocalMsg");
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        MessageId messageId = new MessageId();
        messageId.id = tpnsPushMsg.msgId;
        messageId.accessId = tpnsPushMsg.accessId;
        messageId.host = m.c(aVar.d());
        messageId.port = aVar.e();
        messageId.pact = s.a(aVar.b());
        messageId.apn = m.k(n.f());
        messageId.isp = m.l(n.f());
        messageId.pushTime = j;
        messageId.serviceHost = n.f().getPackageName();
        messageId.receivedTime = jCurrentTimeMillis;
        messageId.pkgName = tpnsPushMsg.appPkgName;
        messageId.busiMsgId = tpnsPushMsg.busiMsgId;
        messageId.timestamp = tpnsPushMsg.timestamp;
        messageId.msgType = tpnsPushMsg.type;
        messageId.multiPkg = tpnsPushMsg.multiPkg;
        messageId.date = tpnsPushMsg.date;
        a(context, tpnsPushMsg.appPkgName, messageId);
        c(context, messageId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void c(Context context, MessageId messageId) {
        ArrayList arrayListA = a(context, messageId);
        if (f) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "requestServiceAck ack is uploading , this time will give up!  msgId =  " + (messageId == null ? null : Long.valueOf(messageId.id)));
        } else {
            ArrayList arrayListA2 = a(context, (List) arrayListA);
            if (arrayListA2 == null || arrayListA2.size() == 0) {
                com.tencent.android.tpush.a.a.c("SrvMessageManager", "requestServiceAck with null list , give up this time");
            } else {
                f = true;
                s.a().a(arrayListA2, new d(this, messageId, context));
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x009a A[Catch: all -> 0x0097, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:6:0x0007, B:8:0x000d, B:10:0x0015, B:12:0x001b, B:13:0x0024, B:15:0x002a, B:17:0x003a, B:19:0x0048, B:21:0x004e, B:23:0x005c, B:25:0x0062, B:32:0x0079, B:33:0x0081, B:35:0x0087, B:29:0x0073, B:28:0x006c, B:40:0x009a), top: B:46:0x0007, inners: #0 }] */
    public void a(Context context, ArrayList arrayList) {
        ArrayList arrayList2;
        boolean z;
        synchronized (c) {
            if (context == null || arrayList == null) {
                com.tencent.android.tpush.a.a.i("SrvMessageManager", "deleteServiceMsgIdBatch with null context or null list");
            } else if (arrayList.size() > 0) {
                try {
                    ArrayList<MessageId> arrayListB = b(context, "all");
                    if (arrayListB != null && arrayListB.size() > 0) {
                        HashMap map = new HashMap();
                        for (MessageId messageId : arrayListB) {
                            ArrayList arrayList3 = (ArrayList) map.get(messageId.pkgName);
                            if (arrayList3 == null) {
                                ArrayList arrayList4 = new ArrayList();
                                map.put(messageId.pkgName, arrayList4);
                                arrayList2 = arrayList4;
                            } else {
                                arrayList2 = arrayList3;
                            }
                            int i = 0;
                            while (true) {
                                int i2 = i;
                                if (i2 >= arrayList.size()) {
                                    z = true;
                                    break;
                                }
                                if (messageId.id != ((TpnsPushClientReport) arrayList.get(i2)).msgId) {
                                    i = i2 + 1;
                                } else {
                                    arrayList.remove(i2);
                                    z = false;
                                    break;
                                }
                            }
                            if (z) {
                                arrayList2.add(messageId);
                                map.put(messageId.pkgName, arrayList2);
                            }
                        }
                        for (String str : map.keySet()) {
                            b(context, str, (ArrayList) map.get(str));
                        }
                    }
                } catch (Exception e2) {
                    com.tencent.android.tpush.a.a.c("SrvMessageManager", "+++ clear msg id exception", e2);
                }
            } else {
                com.tencent.android.tpush.a.a.i("SrvMessageManager", "deleteServiceMsgIdBatch with null context or null list");
            }
            throw th;
        }
    }

    public ArrayList a(Context context, List list) {
        ArrayList arrayList = null;
        if (list != null && list.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MessageId messageId = (MessageId) it.next();
                TpnsPushClientReport tpnsPushClientReport = new TpnsPushClientReport();
                tpnsPushClientReport.accessId = messageId.accessId;
                tpnsPushClientReport.msgId = messageId.id;
                tpnsPushClientReport.apn = messageId.apn;
                tpnsPushClientReport.isp = messageId.isp;
                tpnsPushClientReport.locip = messageId.host;
                tpnsPushClientReport.locport = messageId.port;
                tpnsPushClientReport.pack = messageId.pact;
                tpnsPushClientReport.timeUs = messageId.pushTime;
                tpnsPushClientReport.qua = CacheManager.getQua(context, tpnsPushClientReport.accessId);
                tpnsPushClientReport.serviceHost = messageId.serviceHost;
                tpnsPushClientReport.confirmMs = System.currentTimeMillis() - messageId.receivedTime;
                tpnsPushClientReport.broadcastId = messageId.busiMsgId;
                tpnsPushClientReport.timestamp = messageId.timestamp;
                tpnsPushClientReport.type = messageId.msgType;
                tpnsPushClientReport.ackType = (byte) 1;
                tpnsPushClientReport.receiveTime = messageId.receivedTime / 1000;
                if (XGPushConfig.enableDebug) {
                    com.tencent.android.tpush.a.a.c("SrvMessageManager", "Ack to server : @msgId=" + tpnsPushClientReport.msgId + " @accId=" + tpnsPushClientReport.accessId + " @timeUs=" + tpnsPushClientReport.timeUs + " @confirmMs=" + tpnsPushClientReport.confirmMs + " @recTime=" + messageId.receivedTime + " @msgType=" + messageId.msgType + " @broadcastId=" + tpnsPushClientReport.broadcastId);
                }
                arrayList2.add(tpnsPushClientReport);
                if (arrayList2.size() > 30) {
                    return arrayList2;
                }
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public ArrayList b(Context context, List list) {
        ArrayList arrayList = null;
        if (list != null && list.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MessageId messageId = (MessageId) it.next();
                TpnsPushClientReport tpnsPushClientReport = new TpnsPushClientReport();
                tpnsPushClientReport.accessId = messageId.accessId;
                tpnsPushClientReport.msgId = messageId.id;
                tpnsPushClientReport.apn = messageId.apn;
                tpnsPushClientReport.isp = messageId.isp;
                tpnsPushClientReport.locip = messageId.host;
                tpnsPushClientReport.locport = messageId.port;
                tpnsPushClientReport.pack = messageId.pact;
                tpnsPushClientReport.timeUs = messageId.pushTime;
                tpnsPushClientReport.qua = CacheManager.getQua(context, tpnsPushClientReport.accessId);
                tpnsPushClientReport.serviceHost = messageId.serviceHost;
                tpnsPushClientReport.confirmMs = System.currentTimeMillis() - messageId.receivedTime;
                tpnsPushClientReport.broadcastId = messageId.busiMsgId;
                tpnsPushClientReport.timestamp = messageId.timestamp;
                tpnsPushClientReport.type = messageId.msgType;
                tpnsPushClientReport.ackType = (byte) 4;
                tpnsPushClientReport.receiveTime = messageId.receivedTime / 1000;
                if (XGPushConfig.enableDebug) {
                    com.tencent.android.tpush.a.a.c("SrvMessageManager", "Send sdk Ack to server : @msgId=" + tpnsPushClientReport.msgId + " @accId=" + tpnsPushClientReport.accessId + " @timeUs=" + tpnsPushClientReport.timeUs + " @confirmMs=" + tpnsPushClientReport.confirmMs + " @recTime=" + messageId.receivedTime + " @msgType=" + messageId.msgType + " @broadcastId=" + tpnsPushClientReport.broadcastId);
                }
                arrayList2.add(tpnsPushClientReport);
                if (arrayList2.size() > 30) {
                    return arrayList2;
                }
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public ArrayList a(Context context, MessageId messageId) {
        ArrayList arrayList;
        synchronized (c) {
            arrayList = null;
            arrayList = null;
            ArrayList arrayList2 = null;
            if (context != null) {
                boolean z = false;
                List<String> registerInfos = CacheManager.getRegisterInfos(context);
                if (registerInfos != null && registerInfos.size() > 0) {
                    ArrayList arrayList3 = new ArrayList();
                    for (String str : registerInfos) {
                        ArrayList arrayListB = b(context, str);
                        if (messageId == null || str.equals(messageId.pkgName)) {
                            z = true;
                        }
                        if (arrayListB != null && arrayListB.size() > 0) {
                            arrayList3.addAll(arrayListB);
                        }
                    }
                    arrayList2 = arrayList3;
                }
                if (!z) {
                    try {
                        ArrayList arrayListB2 = b(context, messageId.pkgName);
                        if (arrayListB2 != null && arrayListB2.size() > 0) {
                            arrayList2.retainAll(arrayListB2);
                            if (arrayList2.size() > 0) {
                                arrayList2.removeAll(arrayList2);
                                arrayList2.addAll(arrayListB2);
                            } else {
                                arrayList2.addAll(arrayListB2);
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
                b(context, "all", arrayList2);
                arrayList = arrayList2;
            }
        }
        return arrayList;
    }

    public void a(Context context, String str, MessageId messageId) {
        synchronized (c) {
            if (context != null) {
                if (!m.b(str) && messageId != null) {
                    ArrayList arrayListB = b(context, str);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < arrayListB.size(); i++) {
                        MessageId messageId2 = (MessageId) arrayListB.get(i);
                        if (messageId2.id == messageId.id) {
                            arrayList.add(messageId2);
                        }
                    }
                    arrayListB.removeAll(arrayList);
                    arrayListB.add(messageId);
                    b(context, str, arrayListB);
                }
            }
        }
    }

    public void b(Context context, String str, MessageId messageId) {
        synchronized (c) {
            if (context != null) {
                if (!m.b(str) && messageId != null) {
                    ArrayList arrayListC = c(context, str);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < arrayListC.size(); i++) {
                        MessageId messageId2 = (MessageId) arrayListC.get(i);
                        if (messageId2.id == messageId.id) {
                            arrayList.add(messageId2);
                        }
                    }
                    arrayListC.removeAll(arrayList);
                    arrayListC.add(messageId);
                    a(context, str, arrayListC);
                }
            }
        }
    }

    public void a(Context context, String str, ArrayList arrayList) {
        synchronized (c) {
            if (context != null && arrayList != null) {
                a(context, str, ".tpns.msg.id.send.sdk", arrayList);
            }
        }
    }

    public void b(Context context, String str, ArrayList arrayList) {
        synchronized (c) {
            if (context != null && arrayList != null) {
                a(context, str, ".tpns.msg.id.service", arrayList);
            }
        }
    }

    private ArrayList b(Context context, String str) {
        ArrayList arrayList;
        Object objA;
        if (context == null || m.b(str) || (objA = a(context, str, ".tpns.msg.id.service")) == null) {
            arrayList = null;
        } else {
            arrayList = (ArrayList) objA;
        }
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }

    private ArrayList c(Context context, String str) {
        ArrayList arrayList;
        Object objA;
        if (context == null || m.b(str) || (objA = a(context, str, ".tpns.msg.id.send.sdk")) == null) {
            arrayList = null;
        } else {
            arrayList = (ArrayList) objA;
        }
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }

    public void b(Context context, ArrayList arrayList) {
        ArrayList arrayList2;
        boolean z;
        synchronized (c) {
            if (context != null && arrayList != null) {
                if (arrayList.size() > 0) {
                    try {
                        ArrayList<PushClickEntity> arrayListC = c(context);
                        if (arrayListC != null && arrayListC.size() > 0) {
                            HashMap map = new HashMap();
                            for (PushClickEntity pushClickEntity : arrayListC) {
                                ArrayList arrayList3 = (ArrayList) map.get(pushClickEntity.pkgName);
                                if (arrayList3 == null) {
                                    ArrayList arrayList4 = new ArrayList();
                                    map.put(pushClickEntity.pkgName, arrayList4);
                                    arrayList2 = arrayList4;
                                } else {
                                    arrayList2 = arrayList3;
                                }
                                int i = 0;
                                while (true) {
                                    int i2 = i;
                                    if (i2 >= arrayList.size()) {
                                        z = true;
                                        break;
                                    }
                                    if (pushClickEntity.msgId != ((TpnsClickClientReport) arrayList.get(i2)).msgId) {
                                        i = i2 + 1;
                                    } else {
                                        arrayList.remove(i2);
                                        z = false;
                                        break;
                                    }
                                }
                                if (z) {
                                    arrayList2.add(pushClickEntity);
                                    map.put(pushClickEntity.pkgName, arrayList2);
                                }
                            }
                            for (String str : map.keySet()) {
                                c(context, str, (ArrayList) map.get(str));
                            }
                        }
                    } catch (Exception e2) {
                        com.tencent.android.tpush.a.a.c("SrvMessageManager", "+++ clear msg id exception", e2);
                    }
                }
            }
        }
    }

    public ArrayList a(Context context) {
        ArrayList arrayList = null;
        ArrayList<PushClickEntity> arrayListC = c(context);
        if (arrayListC != null && arrayListC.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            for (PushClickEntity pushClickEntity : arrayListC) {
                TpnsClickClientReport tpnsClickClientReport = new TpnsClickClientReport();
                tpnsClickClientReport.accessId = pushClickEntity.accessId;
                tpnsClickClientReport.msgId = pushClickEntity.msgId;
                tpnsClickClientReport.broadcastId = pushClickEntity.broadcastId;
                tpnsClickClientReport.timestamp = pushClickEntity.timestamp;
                tpnsClickClientReport.type = pushClickEntity.type;
                tpnsClickClientReport.clickTime = pushClickEntity.clickTime;
                tpnsClickClientReport.action = pushClickEntity.action;
                arrayList2.add(tpnsClickClientReport);
                if (arrayList2.size() > 30) {
                    return arrayList2;
                }
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public void d(Context context, Intent intent) {
        if (!h) {
            ArrayList arrayListA = a(context);
            if (arrayListA != null && arrayListA.size() > 0) {
                h = true;
                s.a().b(arrayListA, new e(this, arrayListA, context, intent));
            } else {
                h = false;
            }
        }
    }

    public ArrayList b(Context context) {
        List registerInfos;
        if (context == null || (registerInfos = CacheManager.getRegisterInfos(context)) == null || registerInfos.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = registerInfos.iterator();
        while (it.hasNext()) {
            ArrayList arrayListE = e(context, (String) it.next());
            if (arrayListE != null && arrayListE.size() > 0) {
                arrayList.addAll(arrayListE);
            }
        }
        return arrayList;
    }

    public ArrayList c(Context context) {
        List registerInfos;
        if (context == null || (registerInfos = CacheManager.getRegisterInfos(context)) == null || registerInfos.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = registerInfos.iterator();
        while (it.hasNext()) {
            ArrayList arrayListD = d(context, (String) it.next());
            if (arrayListD != null && arrayListD.size() > 0) {
                arrayList.addAll(arrayListD);
            }
        }
        return arrayList;
    }

    private ArrayList d(Context context, String str) {
        ArrayList arrayList;
        Object objA;
        if (context == null || m.b(str) || (objA = a(context, str, ".tpns.msg.id.clicked")) == null) {
            arrayList = null;
        } else {
            arrayList = (ArrayList) objA;
        }
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }

    public void a(Context context, String str, PushClickEntity pushClickEntity) {
        synchronized (c) {
            if (context != null) {
                if (!m.b(str) && pushClickEntity != null) {
                    ArrayList arrayListD = d(context, str);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < arrayListD.size(); i++) {
                        PushClickEntity pushClickEntity2 = (PushClickEntity) arrayListD.get(i);
                        if (pushClickEntity2.msgId == pushClickEntity.msgId) {
                            arrayList.add(pushClickEntity2);
                        }
                    }
                    arrayListD.removeAll(arrayList);
                    arrayListD.add(pushClickEntity);
                    c(context, str, arrayListD);
                }
            }
        }
    }

    public void c(Context context, String str, ArrayList arrayList) {
        synchronized (c) {
            if (context != null && arrayList != null) {
                a(context, str, ".tpns.msg.id.clicked", arrayList);
            }
        }
    }

    public ArrayList c(Context context, List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MessageId messageId = (MessageId) it.next();
                TpnsPushClientReport tpnsPushClientReport = new TpnsPushClientReport();
                tpnsPushClientReport.accessId = messageId.accessId;
                tpnsPushClientReport.msgId = messageId.id;
                tpnsPushClientReport.apn = messageId.apn;
                tpnsPushClientReport.isp = messageId.isp;
                tpnsPushClientReport.locip = messageId.host;
                tpnsPushClientReport.locport = messageId.port;
                tpnsPushClientReport.pack = messageId.pact;
                tpnsPushClientReport.timeUs = messageId.pushTime;
                tpnsPushClientReport.qua = CacheManager.getQua(context, tpnsPushClientReport.accessId);
                tpnsPushClientReport.serviceHost = messageId.serviceHost;
                tpnsPushClientReport.confirmMs = System.currentTimeMillis() - messageId.receivedTime;
                tpnsPushClientReport.broadcastId = messageId.busiMsgId;
                tpnsPushClientReport.timestamp = messageId.timestamp;
                tpnsPushClientReport.type = messageId.msgType;
                tpnsPushClientReport.receiveTime = messageId.receivedTime / 1000;
                arrayList2.add(tpnsPushClientReport);
                if (arrayList2.size() > 30) {
                    return arrayList2;
                }
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    private ArrayList e(Context context, String str) {
        ArrayList arrayList;
        ArrayList<MessageId> arrayListA;
        synchronized (c) {
            arrayList = null;
            if (context != null) {
                if (!m.b(str) && (arrayListA = a(context, str)) != null && arrayListA.size() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    for (MessageId messageId : arrayListA) {
                        if (messageId.a()) {
                            arrayList2.add(messageId);
                        }
                    }
                    arrayList = arrayList2;
                }
            }
        }
        return arrayList;
    }

    public ArrayList a(Context context, String str) {
        ArrayList arrayList;
        Object objA;
        if (context == null || m.b(str) || (objA = a(context, str, ".tpns.msg.id")) == null) {
            arrayList = null;
        } else {
            arrayList = (ArrayList) objA;
        }
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }

    public void d(Context context, String str, ArrayList arrayList) {
        synchronized (c) {
            if (context != null && arrayList != null) {
                a(context, str, ".tpns.msg.id", arrayList);
            }
        }
    }

    public void a(Context context, String str, long j, short s) {
        boolean z;
        synchronized (c) {
            boolean z2 = false;
            if (context != null && j > 0) {
                ArrayList<MessageId> arrayListA = a(context, str);
                if (arrayListA != null && arrayListA.size() > 0) {
                    for (MessageId messageId : arrayListA) {
                        if (messageId.id == j) {
                            messageId.isAck = s;
                            z = true;
                        } else {
                            z = z2;
                        }
                        z2 = z;
                    }
                    if (z2) {
                        d(context, str, arrayListA);
                    } else {
                        com.tencent.android.tpush.a.a.i("SrvMessageManager", "updateMsgIdFlag Failed with no equal MessageId = " + j + " pkgName = " + str);
                        com.tencent.android.tpush.a.a.a(11, j);
                    }
                } else {
                    com.tencent.android.tpush.a.a.a(12, j);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context, ArrayList arrayList) {
        synchronized (c) {
            if (context != null && arrayList != null) {
                if (arrayList.size() > 0) {
                    try {
                        ArrayList<MessageId> arrayListC = c(context, "all");
                        ArrayList arrayList2 = new ArrayList();
                        if (arrayListC != null && arrayListC.size() > 0) {
                            for (MessageId messageId : arrayListC) {
                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (messageId.id == ((TpnsPushClientReport) arrayList.get(i)).msgId) {
                                        arrayList2.add(messageId);
                                    }
                                }
                            }
                        }
                        arrayListC.removeAll(arrayList2);
                        a(context, "all", arrayListC);
                    } catch (Exception e2) {
                        com.tencent.android.tpush.a.a.c("SrvMessageManager", "deleteMsgIdBatch", e2);
                    }
                }
            }
        }
    }

    public void d(Context context, List list) {
        ArrayList arrayList;
        boolean z;
        synchronized (c) {
            if (context != null && list != null) {
                if (list.size() > 0) {
                    try {
                        ArrayList<MessageId> arrayListB = b(context);
                        if (arrayListB != null && arrayListB.size() > 0) {
                            HashMap map = new HashMap();
                            for (MessageId messageId : arrayListB) {
                                ArrayList arrayList2 = (ArrayList) map.get(messageId.pkgName);
                                if (arrayList2 == null) {
                                    ArrayList arrayList3 = new ArrayList();
                                    map.put(messageId.pkgName, arrayList3);
                                    arrayList = arrayList3;
                                } else {
                                    arrayList = arrayList2;
                                }
                                int i = 0;
                                while (true) {
                                    int i2 = i;
                                    if (i2 >= list.size()) {
                                        z = true;
                                        break;
                                    }
                                    if (messageId.id != ((TpnsPushClientReport) list.get(i2)).msgId) {
                                        i = i2 + 1;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                }
                                if (z) {
                                    arrayList.add(messageId);
                                    map.put(messageId.pkgName, arrayList);
                                }
                            }
                            for (String str : map.keySet()) {
                                d(context, str, (ArrayList) map.get(str));
                            }
                        }
                    } catch (Exception e2) {
                        com.tencent.android.tpush.a.a.c("SrvMessageManager", "deleteMsgIdBatch", e2);
                    }
                }
            }
        }
    }

    public void c(Context context, String str, MessageId messageId) {
        ArrayList arrayList;
        synchronized (c) {
            if (context != null) {
                if (!m.b(str) && messageId != null) {
                    ArrayList arrayListA = a(context, str);
                    if (arrayListA == null) {
                        arrayList = new ArrayList();
                    } else {
                        int i = 0;
                        while (true) {
                            if (i >= arrayListA.size()) {
                                arrayList = arrayListA;
                                break;
                            } else if (((MessageId) arrayListA.get(i)).id != messageId.id) {
                                i++;
                            } else {
                                arrayListA.remove(i);
                                arrayList = arrayListA;
                                break;
                            }
                        }
                    }
                    arrayList.add(messageId);
                    d(context, str, arrayList);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v14, types: [java.io.Serializable, java.lang.String[]] */
    private void a(TpnsPushMsg tpnsPushMsg, long j, com.tencent.android.tpush.service.channel.a aVar) {
        long j2;
        ArrayList arrayList;
        int i = 0;
        Intent intent = new Intent(Constants.ACTION_INTERNAL_PUSH_MESSAGE);
        intent.setPackage(tpnsPushMsg.appPkgName);
        intent.putExtra(MessageKey.MSG_ID, tpnsPushMsg.msgId);
        intent.putExtra("title", Rijndael.encrypt(tpnsPushMsg.title));
        intent.putExtra("content", Rijndael.encrypt(tpnsPushMsg.content));
        intent.putExtra(MessageKey.MSG_DATE, tpnsPushMsg.date);
        intent.putExtra("type", tpnsPushMsg.type);
        intent.putExtra("accId", tpnsPushMsg.accessId);
        intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, tpnsPushMsg.busiMsgId);
        intent.putExtra(MessageKey.MSG_CREATE_TIMESTAMPS, tpnsPushMsg.timestamp);
        intent.putExtra(MessageKey.MSG_CREATE_MULTIPKG, tpnsPushMsg.multiPkg);
        intent.putExtra(MessageKey.MSG_SERVER_TIME, tpnsPushMsg.serverTime * 1000);
        intent.putExtra(MessageKey.MSG_TTL, tpnsPushMsg.ttl);
        intent.putExtra(MessageKey.MSG_SERVICE_ACK, true);
        intent.putExtra(MessageKey.MSG_SERVICE_PACKAGE_NAME, n.g());
        try {
            intent.putExtra("enKeySet", k.a((Serializable) new String[]{"title", "content"}));
            while (true) {
                int i2 = i;
                if (i2 < arrayList.size()) {
                    try {
                        com.tencent.android.tpush.a.a.e("SrvMessageManager", "distribute2SDK pkgs msgid " + tpnsPushMsg.msgId + "  pkg " + tpnsPushMsg.appPkgName);
                        String str = (String) arrayList.get(i2);
                        if (m.b(str)) {
                            com.tencent.android.tpush.a.a.c("SrvMessageManager", ">> msg.appPkgName is null!");
                        } else if (!m.a(n.f(), str, j2)) {
                            com.tencent.android.tpush.a.a.e("SrvMessageManager", "dispatchMessageOnTime appPkgName " + str + " is not installed.");
                            s.a().a(str);
                            com.tencent.android.tpush.b.d.a().d(n.f(), str);
                        } else {
                            RegisterEntity registerInfoByPkgName = CacheManager.getRegisterInfoByPkgName(str);
                            if (registerInfoByPkgName == null) {
                                com.tencent.android.tpush.a.a.a("SrvMessageManager", "RegInfo is null " + str);
                                a(tpnsPushMsg.date, intent, str);
                            } else if (registerInfoByPkgName.state > 0) {
                                com.tencent.android.tpush.b.d.a().d(n.f(), str);
                            } else {
                                intent.setPackage(str);
                                c();
                                if (!com.tencent.android.tpush.a.a(n.f(), intent.getPackage(), intent)) {
                                    a(tpnsPushMsg.date, intent, str);
                                } else {
                                    b(n.f(), intent);
                                }
                            }
                        }
                    } catch (Exception e2) {
                        com.tencent.android.tpush.a.a.c("SrvMessageManager", "dispatchMessageOnTime", e2);
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } catch (Exception e3) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "distribute2SDK", e3);
        }
        intent.putExtra(MessageKey.MSG_EXTRA_HOST, m.c(aVar.d()));
        intent.putExtra(MessageKey.MSG_EXTRA_PORT, aVar.e());
        intent.putExtra(MessageKey.MSG_EXTRA_PACT, s.a(aVar.b()));
        intent.putExtra(MessageKey.MSG_EXTRA_PUSHTIME, j);
        long j3 = tpnsPushMsg.multiPkg;
        j2 = tpnsPushMsg.accessId;
        arrayList = new ArrayList();
        if (j3 == 0) {
            arrayList.add(intent.getPackage());
        } else {
            String strFindValidPackageByAccessid = CacheManager.findValidPackageByAccessid(j2);
            if (!m.b(strFindValidPackageByAccessid)) {
                arrayList.add(strFindValidPackageByAccessid);
            }
        }
        com.tencent.android.tpush.a.a.e("SrvMessageManager", "distribute2SDK pkgs " + arrayList.size());
    }

    public void a(String str, Intent intent, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        if (m.b(str) || (!m.b(str) && simpleDateFormat.parse(str).compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date()))) == 0)) {
            if (m.a(intent)) {
                List listC = m.c(n.f(), str2 + Constants.RPC_SUFFIX);
                if (listC == null || listC.size() < 1) {
                    if (XGPushConfig.enableDebug) {
                        com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> send message intent:" + intent);
                    }
                    com.tencent.android.tpush.b.d.a().a(n.f(), str2, intent);
                    return;
                } else {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> send rpc message intent:" + intent);
                    a(intent);
                    return;
                }
            }
            return;
        }
        if (!m.b(str) && simpleDateFormat.parse(str).compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date()))) < 0) {
            List listC2 = m.c(n.f(), str2 + Constants.RPC_SUFFIX);
            if (listC2 == null || listC2.size() < 1) {
                n.f().sendBroadcast(intent);
            } else {
                a(intent);
            }
        }
    }

    public void a(Intent intent) {
        com.tencent.android.tpush.common.g.a().a(new g(this, intent));
    }

    public void a(ArrayList arrayList, long j, com.tencent.android.tpush.service.channel.a aVar) {
        b(arrayList, j, aVar);
    }

    public void b(ArrayList arrayList, long j, com.tencent.android.tpush.service.channel.a aVar) {
        int i = 0;
        if (n.f() != null && arrayList != null && arrayList.size() > 0) {
            com.tencent.android.tpush.a.a.b(0, arrayList);
            while (true) {
                int i2 = i;
                if (i2 >= arrayList.size()) {
                    break;
                }
                com.tencent.android.tpush.a.a.a("SrvMessageManager", "receive msg from service msgId = " + ((TpnsPushMsg) arrayList.get(i2)).msgId + " pkg = " + ((TpnsPushMsg) arrayList.get(i2)).appPkgName + " size = " + arrayList.size());
                i = i2 + 1;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                TpnsPushMsg tpnsPushMsg = (TpnsPushMsg) it.next();
                com.tencent.android.tpush.a.a.c("SrvMessageManager", "distributeFromServer : accid=" + tpnsPushMsg.accessId + ",busiId=" + tpnsPushMsg.busiMsgId + ",pkg=" + tpnsPushMsg.appPkgName + ",msgId=" + tpnsPushMsg.msgId + ",type=" + tpnsPushMsg.type + ",ts=" + tpnsPushMsg.timestamp + ",multi=" + tpnsPushMsg.multiPkg + ",date=" + tpnsPushMsg.date + ",serverTime=" + tpnsPushMsg.serverTime + ",ttl=" + tpnsPushMsg.ttl + ", size = " + arrayList.size());
                a(n.f(), tpnsPushMsg, j, aVar);
                if (m.b(tpnsPushMsg.appPkgName) && tpnsPushMsg.multiPkg == 0) {
                    com.tencent.android.tpush.a.a.c("SrvMessageManager", ">> messageDistribute, msg.appPkgName is null!");
                } else {
                    a(tpnsPushMsg, j, aVar);
                }
            }
        }
        com.tencent.android.tpush.service.d.a.a(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b() {
        if (com.tencent.android.tpush.service.channel.b.a().b(true) > 0) {
            c();
        }
    }

    private void c() {
        if (this.i == null) {
            n.f().registerReceiver(new i(this), new IntentFilter("com.tencent.android.tpush.service.channel.cacheMsgBeatIntent"));
            this.i = PendingIntent.getBroadcast(n.f(), 0, new Intent("com.tencent.android.tpush.service.channel.cacheMsgBeatIntent"), 134217728);
        }
        if (XGPushConfig.isForeignWeakAlarmMode(n.f())) {
            com.tencent.android.tpush.a.a.f("SrvMessageManager", "scheduleCacheMsgBeat WaekAlarmMode heartbeatinterval: " + com.tencent.android.tpush.service.channel.b.o + " ms");
            a = com.tencent.android.tpush.service.channel.b.o;
        }
        x.a().a(0, System.currentTimeMillis() + a, this.i);
    }

    private void a(Context context, String str, String str2, ArrayList arrayList) {
        try {
            if (arrayList.size() > 50) {
                arrayList.subList(0, 10).clear();
            }
            com.tencent.android.tpush.service.e.f.a(context, str + str2, Rijndael.encrypt(k.a(arrayList)));
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "putSettings", e2);
        }
    }

    private Object a(Context context, String str, String str2) {
        try {
            return k.a(Rijndael.decrypt(com.tencent.android.tpush.service.e.f.a(context, str + str2)));
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "getSettings", e2);
            return null;
        }
    }
}
