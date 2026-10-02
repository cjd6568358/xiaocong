package com.ut.mini.core.appstatus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.alibaba.mtl.log.e.r;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@TargetApi(14)
public class UTMCAppStatusMonitor implements Application.ActivityLifecycleCallbacks {
    private static UTMCAppStatusMonitor a = null;
    private int J = 0;
    private boolean S = false;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ScheduledFuture<?> f3a = null;
    private Object d = new Object();
    private List<UTMCAppStatusCallbacks> m = new LinkedList();
    private Object e = new Object();

    private UTMCAppStatusMonitor() {
    }

    public static synchronized UTMCAppStatusMonitor getInstance() {
        if (a == null) {
            a = new UTMCAppStatusMonitor();
        }
        return a;
    }

    public void registerAppStatusCallbacks(UTMCAppStatusCallbacks aCallbacks) {
        if (aCallbacks != null) {
            synchronized (this.e) {
                this.m.add(aCallbacks);
            }
        }
    }

    public void unregisterAppStatusCallbacks(UTMCAppStatusCallbacks aCallbacks) {
        if (aCallbacks != null) {
            synchronized (this.e) {
                this.m.remove(aCallbacks);
            }
        }
    }

    private void N() {
        synchronized (this.d) {
            r.a().f(11);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        synchronized (this.e) {
            Iterator<UTMCAppStatusCallbacks> it = this.m.iterator();
            while (it.hasNext()) {
                it.next().onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        synchronized (this.e) {
            Iterator<UTMCAppStatusCallbacks> it = this.m.iterator();
            while (it.hasNext()) {
                it.next().onActivityDestroyed(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        synchronized (this.e) {
            Iterator<UTMCAppStatusCallbacks> it = this.m.iterator();
            while (it.hasNext()) {
                it.next().onActivityPaused(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        synchronized (this.e) {
            Iterator<UTMCAppStatusCallbacks> it = this.m.iterator();
            while (it.hasNext()) {
                it.next().onActivityResumed(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        synchronized (this.e) {
            Iterator<UTMCAppStatusCallbacks> it = this.m.iterator();
            while (it.hasNext()) {
                it.next().onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        N();
        this.J++;
        if (!this.S) {
            synchronized (this.e) {
                Iterator<UTMCAppStatusCallbacks> it = this.m.iterator();
                while (it.hasNext()) {
                    it.next().onSwitchForeground();
                }
            }
        }
        this.S = true;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        this.J--;
        if (this.J == 0) {
            N();
            r.a().a(11, new a(), 1000L);
        }
    }

    private class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            UTMCAppStatusMonitor.this.S = false;
            synchronized (UTMCAppStatusMonitor.this.e) {
                Iterator it = UTMCAppStatusMonitor.this.m.iterator();
                while (it.hasNext()) {
                    ((UTMCAppStatusCallbacks) it.next()).onSwitchBackground();
                }
            }
        }
    }
}
