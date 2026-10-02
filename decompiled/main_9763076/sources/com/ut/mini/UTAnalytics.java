package com.ut.mini;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.AppMonitor;
import com.alibaba.mtl.log.b;
import com.alibaba.mtl.log.c;
import com.alibaba.mtl.log.e.i;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.ut.mini.base.UTMIVariables;
import com.ut.mini.core.appstatus.UTMCAppStatusRegHelper;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.core.sign.UTBaseRequestAuthentication;
import com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import com.ut.mini.internal.UTTeamWork;
import com.ut.mini.plugin.UTPluginMgr;
import com.ut.mini.sdkevents.UTMI1010_2001Event;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UTAnalytics {
    private static UTAnalytics a = null;
    private boolean L;
    private boolean M;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UTTracker f126a;
    private Map<String, UTTracker> x = new HashMap();
    private Map<String, UTTracker> y = new HashMap();

    private UTAnalytics() {
        if (Build.VERSION.SDK_INT < 14) {
            UTMI1010_2001Event uTMI1010_2001Event = new UTMI1010_2001Event();
            UTPluginMgr.getInstance().registerPlugin(uTMI1010_2001Event, false);
            UTMIVariables.getInstance().setUTMI1010_2001EventInstance(uTMI1010_2001Event);
        } else {
            UTMI1010_2001Event uTMI1010_2001Event2 = new UTMI1010_2001Event();
            UTMCAppStatusRegHelper.registerAppStatusCallbacks(uTMI1010_2001Event2);
            UTMIVariables.getInstance().setUTMI1010_2001EventInstance(uTMI1010_2001Event2);
        }
    }

    @Deprecated
    public void setContext(Context aContext) {
        b.a().setContext(aContext);
        if (aContext != null) {
            UTTeamWork.getInstance().initialized();
        }
    }

    @Deprecated
    public void setAppApplicationInstance(Application aApplicationInstance) {
        b.a().setAppApplicationInstance(aApplicationInstance);
        AppMonitor.init(aApplicationInstance);
        if (aApplicationInstance != null) {
        }
    }

    public void setAppApplicationInstance(Application application, IUTApplication utCallback) {
        try {
            if (!this.L) {
                if (application != null && utCallback != null && application.getApplicationContext() != null) {
                    getInstance().setContext(application.getApplicationContext());
                    getInstance().setAppApplicationInstance(application);
                    if (utCallback.isUTLogEnable()) {
                        getInstance().turnOnDebug();
                    }
                    getInstance().setChannel(utCallback.getUTChannel());
                    getInstance().setAppVersion(utCallback.getUTAppVersion());
                    getInstance().setRequestAuthentication(utCallback.getUTRequestAuthInstance());
                    this.M = true;
                    this.L = true;
                    return;
                }
                throw new IllegalArgumentException("application and callback must not be null");
            }
        } catch (Throwable th) {
            try {
                i.a((String) null, th);
            } catch (Throwable th2) {
            }
        }
    }

    public void setAppApplicationInstance4sdk(Application application, IUTApplication utCallback) {
        try {
            if (!this.M) {
                if (application != null && utCallback != null && application.getApplicationContext() != null) {
                    getInstance().setContext(application.getApplicationContext());
                    getInstance().setAppApplicationInstance(application);
                    if (utCallback.isUTLogEnable()) {
                        getInstance().turnOnDebug();
                    }
                    getInstance().setChannel(utCallback.getUTChannel());
                    getInstance().setAppVersion(utCallback.getUTAppVersion());
                    getInstance().setRequestAuthentication(utCallback.getUTRequestAuthInstance());
                    this.M = true;
                    return;
                }
                throw new IllegalArgumentException("application and callback must not be null");
            }
        } catch (Throwable th) {
            try {
                i.a((String) null, th);
            } catch (Throwable th2) {
            }
        }
    }

    public static synchronized UTAnalytics getInstance() {
        if (a == null) {
            a = new UTAnalytics();
        }
        return a;
    }

    public synchronized UTTracker getDefaultTracker() {
        if (this.f126a == null) {
            this.f126a = new UTTracker();
        }
        if (this.f126a == null) {
            i.a("getDefaultTracker error", "Fatal Error,must call setRequestAuthentication method first.");
        }
        return this.f126a;
    }

    @Deprecated
    public void setRequestAuthentication(IUTRequestAuthentication aRequestAuthenticationInstance) {
        if (aRequestAuthenticationInstance == null) {
            i.a("setRequestAuthentication", "Fatal Error,pRequestAuth must not be null.");
        }
        if (aRequestAuthenticationInstance instanceof UTBaseRequestAuthentication) {
            AppMonitor.setRequestAuthInfo(false, aRequestAuthenticationInstance.getAppkey(), ((UTBaseRequestAuthentication) aRequestAuthenticationInstance).getAppSecret(), ((UTBaseRequestAuthentication) aRequestAuthenticationInstance).isEncode() ? "1" : PushConstants.PUSH_TYPE_NOTIFY);
        } else {
            AppMonitor.setRequestAuthInfo(true, aRequestAuthenticationInstance.getAppkey(), null, ((UTSecuritySDKRequestAuthentication) aRequestAuthenticationInstance).getAuthCode());
        }
    }

    @Deprecated
    public void setAppVersion(String aAppVersion) {
        b.a().setAppVersion(aAppVersion);
    }

    public synchronized UTTracker getTracker(String aTrackId) {
        UTTracker uTTracker;
        if (!TextUtils.isEmpty(aTrackId)) {
            if (this.x.containsKey(aTrackId)) {
                uTTracker = this.x.get(aTrackId);
            } else {
                uTTracker = new UTTracker();
                uTTracker.p(aTrackId);
                this.x.put(aTrackId, uTTracker);
            }
        } else {
            i.a("getTracker", "TrackId is null.");
            uTTracker = null;
        }
        return uTTracker;
    }

    public synchronized UTTracker getTrackerByAppkey(String appkey) {
        UTTracker uTTracker;
        if (!TextUtils.isEmpty(appkey)) {
            if (this.y.containsKey(appkey)) {
                uTTracker = this.y.get(appkey);
            } else {
                uTTracker = new UTTracker();
                uTTracker.q(appkey);
                this.y.put(appkey, uTTracker);
            }
        } else {
            i.a("getTracker", "TrackId is null.");
            uTTracker = null;
        }
        return uTTracker;
    }

    @Deprecated
    public void setChannel(String aChannel) {
        AppMonitor.setChannel(aChannel);
    }

    @Deprecated
    public void turnOnDebug() {
        b.a().turnOnDebug();
    }

    public void updateUserAccount(String aUsernick, String aUserid) {
        b.a().updateUserAccount(aUsernick, aUserid);
    }

    public void userRegister(String aUsernick) {
        if (!TextUtils.isEmpty(aUsernick)) {
            UTTracker defaultTracker = getDefaultTracker();
            if (defaultTracker != null) {
                defaultTracker.send(new UTOriginalCustomHitBuilder("UT", ACConstants.TAG_LR_WIND_MODE1, aUsernick, (String) null, (String) null, (Map) null).build());
                return;
            } else {
                i.a("Record userRegister event error", "Fatal Error,must call setRequestAuthentication method first.");
                return;
            }
        }
        i.a("userRegister", "Fatal Error,usernick can not be null or empty!");
    }

    public void updateSessionProperties(Map<String, String> aMap) {
        Map<String, String> mapM21a = c.a().m21a();
        HashMap map = new HashMap();
        if (mapM21a != null) {
            map.putAll(mapM21a);
        }
        map.putAll(aMap);
        c.a().c(map);
    }

    public void turnOffAutoPageTrack() {
        UTPageHitHelper.getInstance().turnOffAutoPageTrack();
    }
}
