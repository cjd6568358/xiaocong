package com.tencent.android.tpush.service.channel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.data.CachedMessageIntent;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.XGPushServiceV3;
import com.tencent.android.tpush.service.XGWatchdog;
import com.tencent.android.tpush.service.aa;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsReconnectReq;
import com.tencent.android.tpush.service.x;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b implements com.tencent.android.tpush.horse.l, com.tencent.android.tpush.service.channel.a.b {
    private PendingIntent A;
    private s B;
    private volatile boolean C;
    private com.tencent.android.tpush.horse.k H;
    private Handler I;
    private t J;
    private long K;
    private m L;
    private Handler t;
    private ArrayList u;
    private Map v;
    private Map w;
    private com.tencent.android.tpush.service.channel.a.a x;
    private volatile boolean y;
    private PendingIntent z;
    public static int a = 0;
    public static int b = 0;
    public static int c = 0;
    public static long d = 0;
    public static int e = 0;
    public static int f = 0;
    public static JSONArray g = null;
    public static JSONArray h = null;
    public static int i = 0;
    public static int j = 0;
    public static int k = 290000;
    public static int l = 180000;
    public static int m = 300000;
    public static int n = k;
    public static int o = 3600000;
    public static int p = 1800000;
    public static int q = l;
    private static volatile long D = 0;
    private static volatile long E = 0;
    private static volatile long F = 0;
    private static String G = Constants.MAIN_VERSION_TAG;
    protected static int r = 0;
    protected static Boolean s = null;

    /* synthetic */ b(c cVar) {
        this();
    }

    public static b a() {
        return r.a;
    }

    private b() {
        this.t = null;
        this.u = new ArrayList();
        this.v = new ConcurrentHashMap();
        this.w = new ConcurrentHashMap();
        this.x = null;
        this.y = false;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = true;
        this.H = new c(this);
        this.I = new d(this);
        this.J = new e(this);
        this.K = 0L;
        this.L = new m(this, null);
        com.tencent.android.tpush.horse.g.a().a(this);
        this.t = com.tencent.android.tpush.common.g.a().b();
    }

    public void b() {
        e();
    }

    public void c() {
        this.y = false;
        if (this.x != null) {
            this.x.c();
            this.x = null;
        }
    }

    public void d() {
        c();
        e();
    }

    public void e() {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.a("TpnsChannel", "Action -> checkAndSetupClient( tpnsClient = " + this.x + ", isClientCreating = " + this.y + ")");
        }
        synchronized (this) {
            if (this.x == null && !this.y) {
                this.y = true;
                try {
                    com.tencent.android.tpush.horse.g.a().a(this.H);
                } catch (Exception e2) {
                    com.tencent.android.tpush.a.a.c("TpnsChannel", "createOptimalSocketChannel error", e2);
                }
            } else if (!this.y && this.x != null && !this.x.d()) {
                com.tencent.android.tpush.a.a.i("TpnsChannel", "The socket Channel is unconnected");
                try {
                    this.x.c();
                    com.tencent.android.tpush.horse.g.a().a(this.H);
                } catch (Exception e3) {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "createOptimalSocketChannel error", e3);
                }
            }
            throw th;
        }
    }

    protected synchronized boolean f() {
        boolean z = false;
        synchronized (this) {
            if (com.tencent.android.tpush.service.e.a.d(com.tencent.android.tpush.service.n.f())) {
                int iJ = com.tencent.android.tpush.common.t.j(com.tencent.android.tpush.service.n.f());
                if (com.tencent.android.tpush.common.t.i(com.tencent.android.tpush.service.n.f()) || iJ > 0) {
                    int i2 = (r + 1) * 2 * 1000;
                    r++;
                    if (r <= 3) {
                        if (i2 > l) {
                            i2 = l;
                        }
                        if (r <= 3 || iJ == 1) {
                            if (!this.I.hasMessages(1000)) {
                                if (XGPushConfig.enableDebug) {
                                    com.tencent.android.tpush.a.a.c("TpnsChannel", "onDisconnected and retry HANDLER_CHECKANDSETUP " + i2 + " retry times = " + r);
                                }
                                this.I.sendEmptyMessageDelayed(1000, i2);
                            }
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    private void a(int i2, s sVar) {
        if (sVar.a()) {
            com.tencent.android.tpush.common.t.k(com.tencent.android.tpush.service.n.f());
        }
        try {
            try {
                synchronized (this) {
                    if (this.u.size() < 128) {
                        sVar.b = System.currentTimeMillis();
                        if (i2 == -1) {
                            this.u.add(sVar);
                        } else {
                            this.u.add(i2, sVar);
                        }
                    } else {
                        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">>FG messageInQueue is full,size:" + this.u.size());
                    }
                    if (this.x != null) {
                        this.x.h();
                    }
                    e();
                }
                q qVar = new q(this, null);
                this.w.put(sVar, qVar);
                this.t.postDelayed(qVar, com.tencent.android.tpush.service.a.a.a(com.tencent.android.tpush.service.n.f()).f);
                if (sVar.a()) {
                    com.tencent.android.tpush.common.t.a();
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "messageInQueue", th);
                if (sVar.a()) {
                    com.tencent.android.tpush.common.t.a();
                }
            }
        } catch (Throwable th2) {
            if (sVar.a()) {
                com.tencent.android.tpush.common.t.a();
            }
            throw th2;
        }
    }

    public void a(JceStruct jceStruct, t tVar) {
        if (jceStruct != null) {
            try {
                a(-1, new s(jceStruct, tVar));
                return;
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "sendMessage error ", e2);
                return;
            }
        }
        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "sendMessage null jceMessage");
    }

    public void a(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - E > 120000 || z) {
            E = jCurrentTimeMillis;
            s sVarB = com.tencent.android.tpush.service.s.a().b();
            if (sVarB != null) {
                if (XGPushConfig.enableDebug) {
                    com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> sendReconnMessage with token - " + CacheManager.getToken(com.tencent.android.tpush.service.n.f()));
                }
                if (com.tencent.android.tpush.service.n.f() != null && !PushConstants.PUSH_TYPE_NOTIFY.equals(CacheManager.getToken(com.tencent.android.tpush.service.n.f()))) {
                    a(0, sVarB);
                    XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).sendAllLocalXGAppList();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void k() {
        if (this.u.isEmpty()) {
            if (this.B == null) {
                this.B = new s((short) 7, null, this.J);
            }
            if (this.B.f == null) {
                this.B = new s((short) 7, null, this.J);
            }
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> send heartbeat ");
            }
            a(-1, this.B);
            if ((a > 0 && a % 3 == 0) || a == 2) {
                com.tencent.android.tpush.common.g.a().a(new f(this));
            }
            if (a % 4 != 0) {
                if (j >= 5) {
                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "heartbeat to watchdog failed too many time , start watchdog again");
                    j = 0;
                    XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).startWatchdog();
                } else {
                    XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).sendHeartbeat2Watchdog("heartbeat:", new g(this));
                }
            } else {
                XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).sendAllLocalXGAppList();
            }
        }
        m();
        com.tencent.android.tpush.a.b(com.tencent.android.tpush.service.n.f());
        aa.a(com.tencent.android.tpush.service.n.f()).a();
        i();
        if (XGPushConfig.isLocationEnable(com.tencent.android.tpush.service.n.f())) {
            if (F == 0) {
                F = com.tencent.android.tpush.service.e.h.a(com.tencent.android.tpush.service.n.f(), Constants.LOC_REPORT_TIME, 0L);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (F == 0 || Math.abs(jCurrentTimeMillis - F) > p) {
                com.tencent.android.tpush.service.e.i.g(com.tencent.android.tpush.service.n.f());
                F = jCurrentTimeMillis;
                com.tencent.android.tpush.service.e.h.b(com.tencent.android.tpush.service.n.f(), Constants.LOC_REPORT_TIME, jCurrentTimeMillis);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void l() {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> send heartbeatSlave ");
        }
        i++;
        if (!com.tencent.android.tpush.common.j.a()) {
            if (i % 4 != 0) {
                if (j >= 5) {
                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "heartbeat to watchdog failed too many time , start watchdog again");
                    j = 0;
                    XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).startWatchdog();
                } else {
                    XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).sendHeartbeat2Watchdog("heartbeat:", new h(this));
                }
            } else {
                XGWatchdog.getInstance(com.tencent.android.tpush.service.n.f()).sendAllLocalXGAppList();
            }
        }
        h();
        if (com.tencent.android.tpush.service.n.a().h()) {
            if (!com.tencent.android.tpush.service.e.a.d(com.tencent.android.tpush.service.n.f())) {
                com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "network is unreachable ,give up and go on slave service");
            } else if (com.tencent.android.tpush.service.n.f() != null) {
                com.tencent.android.tpush.common.g.a().a(new i(this));
            } else {
                com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "PushServiceManager.getInstance().getContext() is null");
            }
        }
    }

    public int b(boolean z) {
        a(true, (String) null);
        return 0;
    }

    public int a(boolean z, String str) {
        ArrayList arrayListC;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - D > 120000 || z) {
            D = jCurrentTimeMillis;
            Context contextF = com.tencent.android.tpush.service.n.f();
            if (contextF != null && !com.tencent.android.tpush.service.e.m.b(contextF.getPackageName())) {
                if (str == null) {
                    arrayListC = com.tencent.android.tpush.b.d.a().d(contextF);
                } else {
                    arrayListC = com.tencent.android.tpush.b.d.a().c(contextF, str);
                }
                if (arrayListC != null && arrayListC.size() > 0) {
                    if (XGPushConfig.enableDebug) {
                        com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "Action -> trySendCachedMsgIntent with CachedMsgList size = " + arrayListC.size());
                    }
                    ArrayList arrayList = new ArrayList();
                    int i2 = 0;
                    while (true) {
                        int i3 = i2;
                        if (i3 >= arrayListC.size()) {
                            break;
                        }
                        CachedMessageIntent cachedMessageIntent = (CachedMessageIntent) arrayListC.get(i3);
                        try {
                            String strDecrypt = Rijndael.decrypt(cachedMessageIntent.intent);
                            if (com.tencent.android.tpush.service.e.m.b(strDecrypt)) {
                                arrayList.add(cachedMessageIntent);
                            } else {
                                Intent uri = Intent.parseUri(strDecrypt, 1);
                                String str2 = uri.getPackage();
                                uri.getLongExtra(MessageKey.MSG_CREATE_MULTIPKG, 0L);
                                if (!com.tencent.android.tpush.service.e.m.b(com.tencent.android.tpush.service.n.f(), str2, uri.getLongExtra("accId", 0L))) {
                                    arrayList.add(cachedMessageIntent);
                                    com.tencent.android.tpush.a.a.i("TpnsChannel", str2 + " is uninstalled , discard the msg and report to the server");
                                    com.tencent.android.tpush.service.s.a().a(str2);
                                    com.tencent.android.tpush.b.d.a().b(com.tencent.android.tpush.service.n.f(), str2, new ArrayList());
                                } else {
                                    RegisterEntity registerInfoByPkgName = CacheManager.getRegisterInfoByPkgName(str2);
                                    if (registerInfoByPkgName == null || registerInfoByPkgName.state <= 0) {
                                        long longExtra = uri.getLongExtra(MessageKey.MSG_ID, 0L);
                                        uri.getLongExtra(MessageKey.MSG_SERVER_TIME, 0L);
                                        long longExtra2 = uri.getLongExtra(MessageKey.MSG_EXPIRE_TIME, 0L);
                                        com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "Action -> trySendCachedMsgIntent msgId = " + longExtra + "  appPkgName = " + str2);
                                        if (longExtra2 <= 0) {
                                            int intExtra = uri.getIntExtra(MessageKey.MSG_TTL, 0);
                                            if (intExtra < 0 || intExtra > 259200000) {
                                                intExtra = 259200000;
                                            }
                                            uri.putExtra(MessageKey.MSG_EXPIRE_TIME, ((long) intExtra) + jCurrentTimeMillis);
                                        } else if (jCurrentTimeMillis > longExtra2) {
                                            arrayList.add(cachedMessageIntent);
                                            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "currentTime:" + jCurrentTimeMillis + " > expire_time:" + longExtra2 + ", remove msg:" + cachedMessageIntent);
                                        }
                                        uri.getStringExtra(MessageKey.MSG_DATE);
                                        if (!com.tencent.android.tpush.a.a(com.tencent.android.tpush.service.n.f(), uri.getPackage(), uri)) {
                                            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "ProviderUtils.sendMsgByPkgName error msgId = " + longExtra + " appPkgName = " + str2);
                                        } else {
                                            arrayList.add(cachedMessageIntent);
                                            com.tencent.android.tpush.service.c.a.a().b(com.tencent.android.tpush.service.n.f(), uri);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            com.tencent.android.tpush.a.a.c("TpnsChannel", Constants.MAIN_VERSION_TAG, e2);
                        }
                        i2 = i3 + 1;
                    }
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "sendedList.size() = " + arrayList.size());
                    if (arrayList.size() > 0) {
                        com.tencent.android.tpush.b.d.a().a(contextF, arrayList, arrayListC);
                    }
                    return arrayListC.size();
                }
            }
        }
        return 0;
    }

    private void m() {
        try {
            if (this.z == null) {
                com.tencent.android.tpush.service.n.f().registerReceiver(new j(this), new IntentFilter("com.tencent.android.tpush.service.channel.heartbeatIntent"));
                this.z = PendingIntent.getBroadcast(com.tencent.android.tpush.service.n.f(), 0, new Intent("com.tencent.android.tpush.service.channel.heartbeatIntent"), 134217728);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (n > m) {
                n = m;
            }
            if (XGPushConfig.isForeignWeakAlarmMode(com.tencent.android.tpush.service.n.f())) {
                com.tencent.android.tpush.a.a.f("TpnsChannel", "scheduleHeartbeat WaekAlarmMode heartbeatinterval: " + o + " ms");
                n = o;
            }
            n = com.tencent.android.tpush.service.e.h.a(com.tencent.android.tpush.service.n.f(), "com.tencent.android.xg.wx.HeartbeatIntervalMs", n);
            x.a().a(0, jCurrentTimeMillis + ((long) n), this.z);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "scheduleHeartbeat error", th);
        }
    }

    public void g() {
        try {
            if (this.A == null && this.L != null) {
                com.tencent.android.tpush.service.n.f().unregisterReceiver(this.L);
            }
        } catch (Throwable th) {
        }
    }

    public void h() {
        try {
            if (this.A == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                intentFilter.addAction("android.intent.action.USER_PRESENT");
                intentFilter.addAction("com.tencent.android.tpush.service.channel.heartbeatIntent.pullup");
                com.tencent.android.tpush.service.n.f().registerReceiver(this.L, intentFilter);
                this.A = PendingIntent.getBroadcast(com.tencent.android.tpush.service.n.f(), 0, new Intent("com.tencent.android.tpush.service.channel.heartbeatIntent.pullup"), 134217728);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (q > m) {
                q = m;
            }
            if (XGPushConfig.isForeignWeakAlarmMode(com.tencent.android.tpush.service.n.f())) {
                com.tencent.android.tpush.a.a.f("TpnsChannel", "schedulePullUpHeartbeat WaekAlarmMode heartbeatinterval: " + o + " ms");
                q = o;
            }
            q = com.tencent.android.tpush.service.e.h.a(com.tencent.android.tpush.service.n.f(), "com.tencent.android.xg.wx.HeartbeatIntervalMs", q);
            long j2 = jCurrentTimeMillis + ((long) q);
            b(true);
            x.a().a(0, j2, this.A);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "scheduleHeartbeat error", th);
        }
    }

    @Override // com.tencent.android.tpush.service.channel.a.b
    public synchronized ArrayList a(com.tencent.android.tpush.service.channel.a.a aVar, int i2) {
        ArrayList arrayList;
        if (i2 < 1) {
            i2 = 1;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.v.get(aVar);
        arrayList = new ArrayList(i2);
        if (!this.u.isEmpty()) {
            Iterator it = this.u.iterator();
            s sVar = (s) it.next();
            com.tencent.android.tpush.service.channel.b.h hVar = new com.tencent.android.tpush.service.channel.b.h(sVar.c());
            sVar.a(hVar);
            arrayList.add(hVar);
            sVar.c = jCurrentTimeMillis;
            if (!sVar.a()) {
                concurrentHashMap.put(Integer.valueOf(sVar.d()), sVar);
            }
            it.remove();
            int i3 = i2 - 1;
            boolean z = sVar.e instanceof TpnsReconnectReq;
            while (it.hasNext()) {
                s sVar2 = (s) it.next();
                if (z && ((sVar2.e instanceof TpnsReconnectReq) || (sVar2.e instanceof TpnsPushVerifyReq))) {
                    if (sVar2.f != null) {
                        this.t.post(new k(this, sVar2));
                    }
                    it.remove();
                } else {
                    int i4 = i3 - 1;
                    if (i3 > 0) {
                        com.tencent.android.tpush.service.channel.b.h hVar2 = new com.tencent.android.tpush.service.channel.b.h(sVar2.c());
                        sVar2.a(hVar2);
                        arrayList.add(hVar2);
                        sVar2.c = jCurrentTimeMillis;
                        if (!sVar2.a()) {
                            concurrentHashMap.put(Integer.valueOf(sVar2.d()), sVar2);
                        }
                        it.remove();
                    }
                    i3 = i4;
                }
            }
        }
        return arrayList;
    }

    @Override // com.tencent.android.tpush.service.channel.a.b
    public void a(com.tencent.android.tpush.service.channel.a.a aVar, ChannelException channelException) {
        com.tencent.android.tpush.a.a.i("TpnsChannel", "clientExceptionOccurs(isHttpClient : " + (aVar instanceof com.tencent.android.tpush.service.channel.a.c) + "," + channelException + ")");
        f++;
        this.t.post(new o(this, aVar, channelException, true));
        try {
            if (g == null) {
                g = new JSONArray();
            }
            if (g != null && g.length() < 10) {
                JSONObject jSONObject = new JSONObject();
                if (channelException != null) {
                    jSONObject.put("errorCode", channelException.errorCode);
                }
                if (com.tencent.android.tpush.service.n.f() != null) {
                    jSONObject.put("np", (int) com.tencent.android.tpush.service.e.m.k(com.tencent.android.tpush.service.n.f()));
                }
                g.put(jSONObject);
            }
        } catch (Throwable th) {
        }
        i();
    }

    @Override // com.tencent.android.tpush.service.channel.a.b
    public void a(com.tencent.android.tpush.service.channel.a.a aVar) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.i("TpnsChannel", "Action -> clientDidCancelled " + aVar);
        }
        this.t.post(new o(this, aVar, new ChannelException(Constants.CODE_NETWORK_CHANNEL_CANCELLED, "TpnsClient is cancelled!"), false));
    }

    @Override // com.tencent.android.tpush.service.channel.a.b
    public void b(com.tencent.android.tpush.service.channel.a.a aVar) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.i("TpnsChannel", "Action -> clientDidRetired " + aVar);
        }
        this.t.post(new o(this, aVar, new ChannelException(Constants.CODE_NETWORK_TIMEOUT_EXCEPTION_OCCUR, "TpnsMessage timeout!"), false));
    }

    @Override // com.tencent.android.tpush.service.channel.a.b
    public void a(com.tencent.android.tpush.service.channel.a.a aVar, com.tencent.android.tpush.service.channel.b.i iVar) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> clientDidSendPacket packet : " + iVar.m());
        }
        s sVar = (s) ((ConcurrentHashMap) this.v.get(aVar)).get(Integer.valueOf(iVar.i()));
        if (sVar != null) {
            sVar.c = System.currentTimeMillis();
        } else {
            com.tencent.android.tpush.a.a.i("TpnsChannel", ">> message(" + iVar.i() + ") not in the sentQueue!");
        }
    }

    @Override // com.tencent.android.tpush.service.channel.a.b
    public synchronized void b(com.tencent.android.tpush.service.channel.a.a aVar, com.tencent.android.tpush.service.channel.b.i iVar) {
        a().b(false);
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> clientDidReceivePacket packet : " + iVar.m());
        }
        switch (iVar.j()) {
            case 1:
            case 10:
                if (iVar.e()) {
                    com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> clientDidReceivePacket RequestSuccRunnable NEV1 : " + iVar.m());
                    this.t.post(new p(this, aVar, iVar));
                } else {
                    com.tencent.android.tpush.a.a.c("TpnsChannel", "Action -> clientDidReceivePacket PushMessageRunnable NEV1 : " + iVar.m());
                    this.t.post(new n(this, aVar, iVar));
                }
                m();
                break;
            case 20:
                this.t.post(new l(this, aVar, iVar));
                break;
            default:
                com.tencent.android.tpush.a.a.i("TpnsChannel", "Action -> clientDidReceivePacket unkonwn protocol : " + iVar.m());
                m();
                break;
        }
    }

    public static void i() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (d == 0) {
                d = jCurrentTimeMillis;
            } else if (jCurrentTimeMillis - d < 30000) {
                return;
            }
            if (com.tencent.android.tpush.service.n.f() != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("srv_stime", XGPushServiceV3.a);
                jSONObject.put("srv_etime", System.currentTimeMillis());
                jSONObject.put("srv_startTime", XGPushServiceV3.b);
                if (XGPushServiceV3.c != null) {
                    jSONObject.put("srv_freason", XGPushServiceV3.c);
                }
                jSONObject.put("hb_suc", b);
                jSONObject.put("hb_failed", c);
                if (h != null) {
                    jSONObject.put("hb_freason", h);
                }
                jSONObject.put("con_suc", e);
                jSONObject.put("con_failed", f);
                if (g != null) {
                    jSONObject.put("con_freason", g);
                }
                com.tencent.android.tpush.service.e.h.b(com.tencent.android.tpush.service.n.f(), "service_state", jSONObject.toString());
                d = jCurrentTimeMillis;
                com.tencent.android.tpush.a.a.e("TpnsChannel", "Service bi state " + jSONObject.toString());
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "saveBIReportJson ", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(s sVar) {
        boolean z = false;
        try {
            if (sVar.d == 4) {
                Iterator it = this.u.iterator();
                while (it.hasNext()) {
                    z = ((s) it.next()).d == 4 ? true : z;
                }
                if (!z) {
                    this.u.add(sVar);
                    return;
                }
                return;
            }
            if (sVar.d == 15) {
                Iterator it2 = this.u.iterator();
                while (it2.hasNext()) {
                    z = ((s) it2.next()).d() == sVar.d() ? true : z;
                }
                if (!z) {
                    this.u.add(sVar);
                    return;
                }
                return;
            }
            if (sVar.d == 5) {
                Iterator it3 = this.u.iterator();
                while (it3.hasNext()) {
                    z = ((s) it3.next()).d == 5 ? true : z;
                }
                if (!z) {
                    this.u.add(sVar);
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "addTpnsMessages", th);
        }
    }
}
