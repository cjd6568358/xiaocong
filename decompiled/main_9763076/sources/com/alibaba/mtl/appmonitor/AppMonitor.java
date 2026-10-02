package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.log.e.i;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AppMonitor {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static ServiceConnection f0a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected static IMonitor f4a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static volatile boolean f7a;
    private static Map<String, Object> b;
    private static boolean c;
    private static String f;
    private static String g;
    private static String h;
    private static String i;
    private static Context mContext;
    private static Application a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected static c f3a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static HandlerThread f1a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Object f5a = new Object();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static List<a> f6a = Collections.synchronizedList(new ArrayList());

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static boolean f8b = false;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static b f2a = b.Local;

    enum b {
        Local,
        Service
    }

    static {
        try {
            System.loadLibrary("ut_c_api");
            Log.i("AppMonitor", "load ut_c_api.so success");
        } catch (Throwable th) {
            Log.w("AppMonitor", "load ut_c_api.so failed");
        }
        f0a = new ServiceConnection() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.5
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (b.Service == AppMonitor.f2a) {
                    AppMonitor.f4a = IMonitor.Stub.asInterface(service);
                    if (AppMonitor.f8b && AppMonitor.f3a != null) {
                        AppMonitor.f3a.postAtFrontOfQueue(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AppMonitor.m6a();
                            }
                        });
                    }
                }
                synchronized (AppMonitor.f5a) {
                    AppMonitor.f5a.notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                i.a("AppMonitor", "[onServiceDisconnected]");
                synchronized (AppMonitor.f5a) {
                    AppMonitor.f5a.notifyAll();
                }
                boolean unused = AppMonitor.f8b = true;
            }
        };
        b = Collections.synchronizedMap(new HashMap());
    }

    public static synchronized void init(Application application) {
        i.a("AppMonitor", "[init]");
        try {
            if (!f7a) {
                a = application;
                if (a != null) {
                    mContext = a.getApplicationContext();
                }
                f1a = new HandlerThread("AppMonitor_Client");
                f1a.start();
                f3a = new c(f1a.getLooper());
                if (f2a == b.Local) {
                    b();
                } else if (m7a()) {
                    f3a.a(true);
                }
                m5a().run();
                f7a = true;
            }
        } catch (Throwable th) {
        }
    }

    public static void setRequestAuthInfo(boolean isSecurity, String appkey, String secret, String authcode) {
        if (checkInit()) {
            f3a.a(a(isSecurity, appkey, secret, authcode));
            c = isSecurity;
            g = appkey;
            h = secret;
            i = authcode;
        }
    }

    public static void setChannel(String channel) {
        if (checkInit()) {
            f3a.a(a(channel));
            f = channel;
        }
    }

    public static void turnOnRealTimeDebug(final Map<String, String> params) {
        if (checkInit()) {
            f3a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f4a.turnOnRealTimeDebug(params);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    public static void turnOffRealTimeDebug() {
        if (checkInit()) {
            f3a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f4a.turnOffRealTimeDebug();
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static boolean m7a() {
        if (a == null) {
            return false;
        }
        boolean zBindService = a.getApplicationContext().bindService(new Intent(a.getApplicationContext(), (Class<?>) AppMonitorService.class), f0a, 1);
        if (!zBindService) {
            b();
        }
        i.a("AppMonitor", "bindsuccess:", Boolean.valueOf(zBindService));
        return zBindService;
    }

    static class c extends Handler {
        private boolean h;

        public c(Looper looper) {
            super(looper);
            this.h = false;
        }

        public void a(Runnable runnable) {
            if (runnable != null) {
                try {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 1;
                    messageObtain.obj = runnable;
                    sendMessage(messageObtain);
                } catch (Throwable th) {
                }
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            try {
                if (this.h) {
                    this.h = false;
                    synchronized (AppMonitor.f5a) {
                        try {
                            AppMonitor.f5a.wait(5000L);
                        } catch (InterruptedException e) {
                            AppMonitor.b();
                        }
                    }
                }
                if (msg.obj != null && (msg.obj instanceof Runnable)) {
                    try {
                        ((Runnable) msg.obj).run();
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
            }
            super.handleMessage(msg);
        }

        public void a(boolean z) {
            this.h = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Exception exc) {
        i.a("AppMonitor", Constants.MAIN_VERSION_TAG, exc);
        if (exc instanceof DeadObjectException) {
            m6a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static synchronized void m6a() {
        int i2 = 0;
        synchronized (AppMonitor.class) {
            i.a("AppMonitor", "[restart]");
            try {
                if (f8b) {
                    f8b = false;
                    b();
                    m5a().run();
                    a(c, g, h, i).run();
                    a(f).run();
                    synchronized (f6a) {
                        while (true) {
                            int i3 = i2;
                            if (i3 >= f6a.size()) {
                                break;
                            }
                            a aVar = f6a.get(i3);
                            if (aVar != null) {
                                try {
                                    a(aVar.o, aVar.p, aVar.f11b, aVar.b, aVar.g).run();
                                } catch (Throwable th) {
                                }
                            }
                            i2 = i3 + 1;
                        }
                    }
                }
            } catch (Throwable th2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b() {
        f4a = new Monitor(a);
        f2a = b.Local;
        i.a("AppMonitor", "Start AppMonitor Service failed,AppMonitor run in local Mode...");
    }

    static class a {
        public DimensionSet b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        public MeasureSet f11b;
        public boolean g;
        public String o;
        public String p;

        a() {
        }
    }

    public static boolean checkInit() {
        if (!f7a) {
            i.a("AppMonitor", "Please call UTAnalytics.getInstance().setAppApplicationInstance()||.setAppApplicationInstance4sdk() before call other method");
        }
        return f7a;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static Runnable m5a() {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppMonitor.f4a.init();
                } catch (RemoteException e) {
                    AppMonitor.b();
                    try {
                        AppMonitor.f4a.init();
                    } catch (Throwable th) {
                    }
                }
            }
        };
    }

    private static Runnable a(final boolean z, final String str, final String str2, final String str3) {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppMonitor.f4a.setRequestAuthInfo(z, str, str2, str3);
                } catch (Throwable th) {
                }
            }
        };
    }

    private static Runnable a(final String str) {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppMonitor.f4a.setChannel(str);
                } catch (Throwable th) {
                }
            }
        };
    }

    private static Runnable a(final String str, final String str2, final MeasureSet measureSet, final DimensionSet dimensionSet, final boolean z) {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    i.a("AppMonitor", "register stat event. module: ", str, " monitorPoint: ", str2);
                    AppMonitor.f4a.register4(str, str2, measureSet, dimensionSet, z);
                } catch (RemoteException e) {
                    AppMonitor.a(e);
                }
            }
        };
    }
}
