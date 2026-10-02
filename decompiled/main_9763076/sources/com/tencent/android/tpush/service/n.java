package com.tencent.android.tpush.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.baidu.cloud.media.player.IMediaPlayer;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.service.cache.CacheManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {
    private static Context a = null;
    private static String b = Constants.MAIN_VERSION_TAG;
    private static LocalServerSocket c = null;
    private static LocalServerSocket d = null;
    private static volatile boolean f = false;
    private static volatile boolean g = false;
    private static volatile boolean h = false;
    private static volatile boolean i = false;
    private Handler e;

    /* synthetic */ n(o oVar) {
        this();
    }

    private n() {
        this.e = null;
        b = com.tencent.android.tpush.service.e.m.a(f());
    }

    public static n a() {
        return r.a;
    }

    public void b() {
    }

    public void a(Intent intent) {
        String action;
        long jRandom = 0;
        if (this.e == null) {
            p();
        }
        synchronized (this) {
            if (f && c != null) {
                if (intent != null && (action = intent.getAction()) != null) {
                    if (Constants.ACTION_KEEPALIVE.equals(action)) {
                        Message messageObtainMessage = this.e.obtainMessage(2);
                        long longExtra = intent.getLongExtra(Constants.NETWORK_RESTAT_DELAY_TIME, 0L);
                        if (longExtra == 0) {
                            this.e.removeMessages(2);
                            this.e.sendMessageDelayed(messageObtainMessage, 100L);
                        } else {
                            this.e.removeMessages(2);
                            this.e.sendMessageDelayed(messageObtainMessage, longExtra);
                        }
                    } else if (Constants.ACTION_STOP_CONNECT.equals(action)) {
                        Message messageObtainMessage2 = this.e.obtainMessage(3);
                        this.e.removeMessages(3);
                        this.e.sendMessageDelayed(messageObtainMessage2, 100L);
                    }
                }
                return;
            }
            if (g && d != null) {
                if (com.tencent.android.tpush.service.e.m.w(f()) || (intent != null && Constants.ACTION_SLVAE_2_MAIN.equals(intent.getAction()))) {
                    this.e.sendMessageDelayed(this.e.obtainMessage(4), 0L);
                }
                return;
            }
            List registerInfos = CacheManager.getRegisterInfos(f());
            if (registerInfos != null && registerInfos.size() > 1) {
                jRandom = ((int) (Math.random() * 1000.0d)) + IMediaPlayer.MEDIA_INFO_TIMED_TEXT_ERROR;
                if (jRandom < 1000) {
                    jRandom = 1000;
                }
            }
            this.e.sendMessageDelayed(this.e.obtainMessage(1), jRandom);
        }
    }

    public static void a(Context context) {
        a(context, Constants.ACTION_KEEPALIVE, 0L);
    }

    public static void b(Context context) {
        a(context, Constants.ACTION_START_SLVAE, 0L);
    }

    public static void a(Context context, long j) {
        a(context, Constants.ACTION_KEEPALIVE, j);
    }

    public static void a(Context context, String str, long j) {
        Intent intent;
        if (context != null) {
            try {
                intent = new Intent();
                try {
                    intent.setClass(context, XGPushServiceV3.class);
                    intent.setAction(str);
                    if (j != 0) {
                        intent.putExtra(Constants.NETWORK_RESTAT_DELAY_TIME, j);
                    }
                    if (com.tencent.android.tpush.common.t.a(context) <= 0) {
                        context.startService(intent);
                    } else {
                        com.tencent.android.tpush.a.a.i("PushServiceManager", "startService failed, libtpnsSecurity.so not found.");
                        context.stopService(intent);
                    }
                } catch (Throwable th) {
                    th = th;
                    com.tencent.android.tpush.a.a.i("PushServiceManager", "startService failed, intent:" + intent + ", ex:" + th);
                    try {
                        Intent intent2 = new Intent();
                        try {
                            intent2.setClass(context, XGPushServiceV3.class);
                            if (com.tencent.android.tpush.common.t.a(context) <= 0) {
                                context.startService(intent2);
                            } else {
                                com.tencent.android.tpush.a.a.i("PushServiceManager", "startService failed, libtpnsSecurity.so not found.");
                                context.stopService(intent2);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            intent = intent2;
                            com.tencent.android.tpush.a.a.i("PushServiceManager", "222 startService failed, intent:" + intent + ", ex:" + th);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                intent = null;
            }
        }
    }

    public static void c(Context context) {
        com.tencent.android.tpush.a.a.f("PushServiceManager", "Action -> stop Current Connect");
        a(context, Constants.ACTION_STOP_CONNECT, 0L);
    }

    public void c() {
        com.tencent.android.tpush.a.a.c("PushServiceManager", "@@ serviceExit()");
        com.tencent.android.tpush.common.t.a();
        if (this.e != null) {
            this.e.removeCallbacksAndMessages(null);
            this.e = null;
        }
        if (com.tencent.android.tpush.common.g.a().b() != null) {
            com.tencent.android.tpush.common.g.a().b().removeCallbacksAndMessages(null);
        }
        a.a();
        a.b(a);
        d();
        com.tencent.android.tpush.service.e.m.y(f());
    }

    public void d() {
        synchronized (this) {
            if (c != null) {
                try {
                    c.close();
                    c = null;
                } catch (Exception e) {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> Destroy local socket exception", e);
                }
                Boolean bool = false;
                f = bool.booleanValue();
            } else {
                Boolean bool2 = false;
                f = bool2.booleanValue();
            }
            throw th;
        }
    }

    public void e() {
        synchronized (this) {
            if (d != null) {
                try {
                    d.close();
                    d = null;
                } catch (Exception e) {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> Destroy local socket exception", e);
                }
                Boolean bool = false;
                g = bool.booleanValue();
            } else {
                Boolean bool2 = false;
                g = bool2.booleanValue();
            }
            throw th;
        }
    }

    public static void d(Context context) {
        if (context != null) {
            a = context;
            b = context.getPackageName();
        }
    }

    public static Context f() {
        return a;
    }

    public static String g() {
        return b;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00ff  */
    private boolean l() {
        float fFloatValue;
        float fFloatValue2;
        try {
            if (com.tencent.android.tpush.service.e.m.a(a.getPackageName())) {
                return true;
            }
            Map registerEntityMap = CacheManager.getRegisterEntityMap();
            if (registerEntityMap != null && registerEntityMap.size() >= 2) {
                HashMap map = new HashMap();
                Iterator it = registerEntityMap.entrySet().iterator();
                while (it.hasNext()) {
                    RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                    if (registerEntity != null && !com.tencent.android.tpush.service.e.m.b(registerEntity.packageName) && registerEntity.a()) {
                        map.put(registerEntity.packageName, Float.valueOf(registerEntity.xgSDKVersion));
                    }
                }
                float f2 = 0.0f;
                if (map.get(b) != null) {
                    fFloatValue = ((Float) map.get(b)).floatValue();
                } else {
                    fFloatValue = 3.24f;
                }
                List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) a.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
                if (runningServices != null && runningServices.size() > 0) {
                    String name = XGPushServiceV3.class.getName();
                    for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                        if (name.equals(runningServiceInfo.service.getClassName())) {
                            String packageName = runningServiceInfo.service.getPackageName();
                            com.tencent.android.tpush.a.a.c("PushServiceManager", "isSurvive srvPkg :" + packageName);
                            if (!com.tencent.android.tpush.stat.a.e.b(packageName) || map.get(runningServiceInfo.service.getPackageName()) == null) {
                                fFloatValue2 = f2;
                            } else {
                                fFloatValue2 = ((Float) map.get(packageName)).floatValue();
                                if (fFloatValue2 <= f2) {
                                    fFloatValue2 = f2;
                                }
                            }
                        } else {
                            fFloatValue2 = f2;
                        }
                        f2 = fFloatValue2;
                    }
                }
                if (f2 > fFloatValue) {
                    return false;
                }
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("PushServiceManager", "isSurvive", e);
        }
        return true;
    }

    public static void a(Service service) {
        com.tencent.android.tpush.a.a.i("showMockNotification", service.getClass().getSimpleName() + " showMockNotification");
        Notification notification = new Notification();
        notification.sound = null;
        notification.vibrate = null;
        service.startForeground(19981111, notification);
    }

    public static int e(Context context) {
        String packageName;
        int i2 = 0;
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() > 0) {
                String name = XGPushServiceV3.class.getName();
                for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                    i2 = (!name.equals(runningServiceInfo.service.getClassName()) || (packageName = runningServiceInfo.service.getPackageName()) == null || packageName.equals(context.getPackageName())) ? i2 : i2 + 1;
                }
            }
        } catch (Throwable th) {
        }
        return i2;
    }

    private void m() {
        if (Build.VERSION.SDK_INT < 18) {
            XGPushServiceV3.b().startForeground(-1998, new Notification());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean n() {
        boolean z;
        boolean zL = l();
        synchronized (this) {
            if (zL) {
                try {
                    String strA = com.tencent.android.tpush.service.e.m.a();
                    String strA2 = com.tencent.android.tpush.encrypt.a.a(strA + "V3");
                    String token = CacheManager.getToken(a);
                    com.tencent.android.tpush.a.a.a("PushServiceManager", "(ignore) running v3 service count " + e(a));
                    if (!com.tencent.android.tpush.common.t.c(token) && !PushConstants.PUSH_TYPE_NOTIFY.equals(token)) {
                        c = new LocalServerSocket(strA2 + token);
                        LocalServerSocket localServerSocket = new LocalServerSocket(strA2);
                        if (localServerSocket != null) {
                            try {
                                localServerSocket.close();
                            } catch (Throwable th) {
                            }
                        }
                    } else {
                        c = new LocalServerSocket(strA2);
                    }
                    com.tencent.android.tpush.a.a.a("PushServiceManager", "tmpSocketName:" + strA + ", socketName: " + strA2);
                    Boolean bool = true;
                    f = bool.booleanValue();
                    m();
                    XGWatchdog.getInstance(a).startWatchdog();
                    aa.a(a).a();
                    z = zL;
                } catch (Throwable th2) {
                    com.tencent.android.tpush.a.a.f("PushServiceManager", "isSurvive " + zL + th2.getLocalizedMessage());
                    z = f;
                }
            } else {
                z = zL;
            }
        }
        return z;
    }

    public boolean h() throws Throwable {
        LocalServerSocket localServerSocket;
        Throwable th;
        boolean z;
        try {
            String strA = com.tencent.android.tpush.service.e.m.a();
            String strA2 = com.tencent.android.tpush.encrypt.a.a(strA + "V3");
            com.tencent.android.tpush.a.a.a("PushServiceManager", "tmpSocketName:" + strA + ", socketName: " + strA2);
            localServerSocket = new LocalServerSocket(strA2);
            try {
                com.tencent.android.tpush.a.a.a("PushServiceManager", "tmpSocketName is success" + strA + ", socketName: " + strA2);
                z = true;
                if (localServerSocket != null) {
                    try {
                        localServerSocket.close();
                    } catch (Throwable th2) {
                        com.tencent.android.tpush.a.a.c("PushServiceManager", "localSocket.close()", th2);
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (localServerSocket != null) {
                    try {
                        localServerSocket.close();
                    } catch (Throwable th4) {
                        com.tencent.android.tpush.a.a.c("PushServiceManager", "localSocket.close()", th4);
                    }
                }
                throw th;
            }
        } catch (Throwable th5) {
            localServerSocket = null;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean o() {
        com.tencent.android.tpush.a.a.e("PushServiceManager", "tryToKeepSlaveServiceAlive");
        boolean zL = l();
        synchronized (this) {
            if (zL) {
                try {
                    d = new LocalServerSocket(com.tencent.android.tpush.encrypt.a.a(com.tencent.android.tpush.service.e.m.a() + "V3.Slave"));
                    Boolean bool = true;
                    g = bool.booleanValue();
                    XGWatchdog.getInstance(a).startWatchdog();
                    aa.a(a).a();
                } catch (Throwable th) {
                    zL = g;
                }
            }
        }
        return zL;
    }

    private void p() {
        this.e = new o(this, Looper.getMainLooper());
    }
}
