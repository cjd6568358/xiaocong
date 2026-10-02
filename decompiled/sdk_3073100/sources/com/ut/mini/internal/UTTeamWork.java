package com.ut.mini.internal;

import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.AppMonitor;
import com.alibaba.mtl.log.a;
import com.alibaba.mtl.log.b;
import com.alibaba.mtl.log.c.c;
import com.ut.device.UTDevice;
import com.ut.mini.base.UTMIVariables;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTTeamWork {
    private static UTTeamWork a = null;

    public static synchronized UTTeamWork getInstance() {
        if (a == null) {
            a = new UTTeamWork();
        }
        return a;
    }

    public void initialized() {
    }

    public void turnOnRealTimeDebug(Map<String, String> aMap) {
        AppMonitor.turnOnRealTimeDebug(aMap);
    }

    public void turnOffRealTimeDebug() {
        AppMonitor.turnOffRealTimeDebug();
    }

    public void dispatchLocalHits() {
    }

    public void saveCacheDataToLocal() {
        c.a().G();
    }

    public void setToAliyunOsPlatform() {
        UTMIVariables.getInstance().setToAliyunOSPlatform();
    }

    public String getUtsid() {
        try {
            String appkey = a.a() != null ? a.a().getAppkey() : null;
            String utdid = UTDevice.getUtdid(b.a().getContext());
            long jLongValue = Long.valueOf(a.B).longValue();
            if (TextUtils.isEmpty(appkey) || TextUtils.isEmpty(utdid)) {
                return null;
            }
            return utdid + "_" + appkey + "_" + jLongValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeAuto1010Track() {
        com.alibaba.mtl.log.c.a().p();
    }

    public void disableNetworkStatusChecker() {
    }
}
