package com.tencent.android.tpush.horse;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Handler;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.NullReturnException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static int m;
    private final Object d;
    private volatile int e;
    private volatile boolean f;
    private long g;
    private k h;
    private l i;
    private Timer l;
    private Handler n;
    private b o;
    private b p;
    static long a = 1;
    static long b = 0;
    private static long j = 0;
    private static long k = 0;
    public static int c = -1;

    /* synthetic */ g(h hVar) {
        this();
    }

    public void a(l lVar) {
        this.i = lVar;
    }

    public static g a() {
        return m.a;
    }

    private g() {
        this.d = new Object();
        this.e = 0;
        this.f = false;
        this.l = new Timer();
        this.n = null;
        this.o = new i(this);
        this.p = new j(this);
        this.n = com.tencent.android.tpush.common.g.a().b();
    }

    public synchronized void a(k kVar) {
        this.e = 0;
        this.h = kVar;
        this.n.post(new h(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        ArrayList arrayListA;
        Exception exc;
        List list;
        NullReturnException nullReturnException;
        List listB;
        if (str != null) {
            com.tencent.android.tpush.stat.a.e.b().d("startHorseTask key:" + str);
        }
        if (!com.tencent.android.tpush.service.e.a.d(com.tencent.android.tpush.service.n.f())) {
            com.tencent.android.tpush.a.a.i("OptimalLinkSelector", "Network can't reachable");
            a(Constants.CODE_NETWORK_UNREACHABLE, "network can't reachable!");
        }
        if (q.i().b() || f.i().b()) {
            com.tencent.android.tpush.a.a.a("OptimalLinkSelector", "Horse task running");
            return;
        }
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "Action -> startHorseTask with key = " + str);
        }
        CacheManager.removeOptStrategyList(com.tencent.android.tpush.service.n.f(), str);
        try {
            if (str.equals("3") || str.equals("1") || str.equals("2")) {
                arrayListA = CacheManager.getServerItems(com.tencent.android.tpush.service.n.f(), str);
            } else {
                arrayListA = DefaultServer.a(str);
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> Can not get local serverItems : " + e.getMessage());
            try {
                arrayListA = DefaultServer.a(str);
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> Can not get default serverItems : " + e2.toString());
                arrayListA = null;
            }
        }
        if (arrayListA == null) {
            arrayListA = new ArrayList();
        }
        arrayListA.addAll(DefaultServer.b());
        int channelType = Tools.getChannelType(com.tencent.android.tpush.service.n.f());
        com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "Tools.getChannelType = " + channelType);
        switch (channelType) {
            case 2:
                try {
                    com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "XINGE Use HTTP TASK");
                    List listB2 = p.b(arrayListA, str);
                    f.i().a(this.o);
                    f.i().a(listB2);
                    f.i().g();
                } catch (NullReturnException e3) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> Can not get strategyItems(create http channel fail!)>>" + e3.getMessage());
                    a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create http channel fail!");
                    return;
                } catch (Exception e4) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> (create http channel fail!) >> " + e4.getMessage());
                    a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create http channel fail!");
                    return;
                }
                break;
            case 3:
                try {
                    com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "XINGE Use HTTP_WAP");
                    List<StrategyItem> listB3 = p.b(arrayListA, str);
                    ArrayList arrayList = new ArrayList();
                    for (StrategyItem strategyItem : listB3) {
                        if (strategyItem.h()) {
                            arrayList.add(strategyItem);
                        }
                    }
                    f.i().a(this.o);
                    f.i().a(arrayList);
                    f.i().g();
                } catch (NullReturnException e5) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> Can not get strategyItems(create wap channel fail!)>>" + e5.getMessage());
                    a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create wap channel fail!");
                    return;
                } catch (Exception e6) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> (create wap channel fail!) >> " + e6.getMessage());
                    a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create wap channel fail!");
                    return;
                }
                break;
            case 4:
                try {
                    com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "XINGE Use TCP_OR_HTTP");
                    List listA = p.a(arrayListA, str);
                    try {
                        listB = p.b(arrayListA, str);
                        list = listA;
                    } catch (NullReturnException e7) {
                        list = listA;
                        nullReturnException = e7;
                        com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> Can not get strategyItems(create default channel fail!)>>" + nullReturnException.getMessage());
                        a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create default channel fail!");
                        listB = null;
                    } catch (Exception e8) {
                        list = listA;
                        exc = e8;
                        com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> (create default channel fail!) >> " + exc.getMessage());
                        a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create default channel fail!");
                        listB = null;
                    }
                } catch (NullReturnException e9) {
                    nullReturnException = e9;
                    list = null;
                } catch (Exception e10) {
                    exc = e10;
                    list = null;
                }
                q.i().a(this.p);
                q.i().a(list);
                q.i().g();
                f.i().a(this.o);
                f.i().a(listB);
                f.i().g();
                break;
            default:
                try {
                    com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "XINGE Use TcpTask");
                    List listA2 = p.a(arrayListA, str);
                    q.i().a(this.p);
                    q.i().a(listA2);
                    q.i().g();
                } catch (NullReturnException e11) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> Can not get strategyItems(create tcp channel fail!) >> " + e11.getMessage());
                    a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create tcp channel fail!");
                    return;
                } catch (Exception e12) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, ">> (create tcp channel fail!) >> " + e12.getMessage());
                    a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create tcp channel fail!");
                    return;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str) {
        if (this.h != null) {
            this.h.a(i, str);
        }
    }

    public synchronized void a(Intent intent) {
        try {
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (networkInfo != null) {
                if (XGPushConfig.enableDebug) {
                    com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "Connection state changed to - " + networkInfo.toString());
                }
                boolean booleanExtra = intent.getBooleanExtra("noConnectivity", false);
                int type = networkInfo.getType();
                if (booleanExtra) {
                    if (XGPushConfig.enableDebug) {
                        com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "DisConnected with network type " + networkInfo.getTypeName());
                    }
                    com.tencent.android.tpush.service.n.c(com.tencent.android.tpush.service.n.f());
                } else if (NetworkInfo.State.CONNECTED == networkInfo.getState()) {
                    if (XGPushConfig.enableDebug) {
                        com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "Connected with network type " + networkInfo.getTypeName());
                    }
                    c = type;
                    com.tencent.android.tpush.service.n.a(com.tencent.android.tpush.service.n.f(), 2000L);
                } else if (NetworkInfo.State.DISCONNECTED == networkInfo.getState()) {
                    if (XGPushConfig.enableDebug) {
                        com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "NetworkInfo.State.DISCONNECTED with network type = " + networkInfo.getTypeName());
                    }
                    if (c == -1 || c == type) {
                        com.tencent.android.tpush.service.n.c(com.tencent.android.tpush.service.n.f());
                    }
                } else if (XGPushConfig.enableDebug) {
                    com.tencent.android.tpush.a.a.c("OptimalLinkSelector", "other network state - " + networkInfo.getState() + ". Do nothing.");
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.a("OptimalLinkSelector", "onNetworkChanged", th);
        }
    }

    public void b() {
        m++;
        if (m < com.tencent.android.tpush.service.a.a.a(com.tencent.android.tpush.service.n.f()).t) {
            a().a(com.tencent.android.tpush.service.e.m.m(com.tencent.android.tpush.service.n.f()));
        } else {
            a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create socket err");
        }
    }
}
