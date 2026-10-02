package com.tencent.android.tpush.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.protocol.AppInfo;
import com.tencent.android.tpush.service.channel.protocol.DeviceInfo;
import com.tencent.android.tpush.service.channel.protocol.MutableInfo;
import com.tencent.android.tpush.service.channel.protocol.NetworkInfo;
import com.tencent.android.tpush.service.channel.protocol.TpnsConfigReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsGetApListReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClickReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClientReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushCommReportReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsReconnectReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsTokenTagReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsUpdateTokenReq;
import com.tencent.android.tpush.service.channel.protocol.UnregInfo;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.mid.api.MidService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class s {
    private static s a = new s();
    private static JSONArray b = new JSONArray();
    private static final String c = com.tencent.android.tpush.encrypt.a.a("com.tencent.tpush.last_wifi_ts");
    private com.tencent.android.tpush.service.channel.t d = new t(this);

    public static s a() {
        return a;
    }

    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
        if (jceStruct != null) {
            if (jceStruct instanceof TpnsPushClientReq) {
                TpnsPushClientReq tpnsPushClientReq = (TpnsPushClientReq) jceStruct;
                com.tencent.android.tpush.service.c.a.a().a(tpnsPushClientReq.msgList, tpnsPushClientReq.timeUs, aVar);
            } else {
                com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", "onReceivedServicePush unhandle message type" + jceStruct.getClass().getName());
            }
        }
    }

    public com.tencent.android.tpush.service.channel.s b() {
        if (!com.tencent.android.tpush.service.e.m.j(n.f())) {
            com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", ">> no app registered!");
            return null;
        }
        TpnsReconnectReq tpnsReconnectReq = new TpnsReconnectReq();
        tpnsReconnectReq.deviceId = com.tencent.android.tpush.service.e.c.a();
        tpnsReconnectReq.networkType = com.tencent.android.tpush.service.e.m.k(n.f());
        tpnsReconnectReq.token = CacheManager.getToken(n.f());
        tpnsReconnectReq.unregInfoList = CacheManager.getUninstallAndUnregisterInfo(n.f());
        tpnsReconnectReq.recvMsgList = com.tencent.android.tpush.service.c.a.a().c(n.f(), (List) com.tencent.android.tpush.service.c.a.a().b(n.f()));
        tpnsReconnectReq.msgClickList = com.tencent.android.tpush.service.c.a.a().a(n.f());
        tpnsReconnectReq.sdkVersion = String.valueOf(3.24f);
        tpnsReconnectReq.connVersion = 3L;
        tpnsReconnectReq.guid = CacheManager.getGuid(n.f());
        Context contextF = n.f();
        MutableInfo mutableInfo = new MutableInfo();
        if (com.tencent.android.tpush.stat.a.h.j(contextF) && com.tencent.android.tpush.stat.a.h.k(contextF)) {
            mutableInfo.bssid = com.tencent.android.tpush.stat.a.h.h(contextF);
            mutableInfo.ssid = com.tencent.android.tpush.stat.a.h.i(contextF);
        }
        mutableInfo.mac = com.tencent.android.tpush.stat.a.h.f(contextF);
        try {
            mutableInfo.wflist = b(contextF);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", ">> getWifiList(" + contextF + ")" + e);
        }
        JSONObject jSONObject = new JSONObject();
        String newMid = MidService.getNewMid(contextF);
        if (newMid != null && newMid.trim().length() == 40) {
            try {
                jSONObject.put("new_mid", newMid);
                jSONObject.put("new_mid_v", String.valueOf(4.06f));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (jSONObject != null && jSONObject.length() > 0) {
            tpnsReconnectReq.reserved = jSONObject.toString();
        }
        tpnsReconnectReq.mutableInfo = mutableInfo;
        return new com.tencent.android.tpush.service.channel.s(tpnsReconnectReq, this.d);
    }

    private String b(Context context) {
        JSONArray jSONArrayA;
        int iAbs;
        if (context != null && (jSONArrayA = com.tencent.android.tpush.common.e.a(context, 10)) != null && jSONArrayA.length() > 0) {
            long jB = com.tencent.android.tpush.service.e.f.b(context, c, 0L);
            if (b == null || b.length() <= 0) {
                iAbs = 0;
            } else {
                if (b.toString().equalsIgnoreCase(jSONArrayA.toString())) {
                    return Constants.MAIN_VERSION_TAG;
                }
                iAbs = Math.abs(b.length() - jSONArrayA.length());
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((iAbs >= 3) || Math.abs(jCurrentTimeMillis - jB) > 1800000) {
                com.tencent.android.tpush.service.e.f.a(context, c, jCurrentTimeMillis);
                String string = jSONArrayA.toString();
                b = jSONArrayA;
                return string;
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static DeviceInfo a(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.apiLevel = Constants.MAIN_VERSION_TAG + com.tencent.android.tpush.service.e.m.d();
        deviceInfo.imei = com.tencent.android.tpush.service.e.m.i(context);
        deviceInfo.model = com.tencent.android.tpush.service.e.m.e();
        deviceInfo.manu = Build.MANUFACTURER;
        deviceInfo.model = Build.MODEL;
        deviceInfo.network = com.tencent.android.tpush.stat.a.h.d(context);
        deviceInfo.os = "android";
        DisplayMetrics displayMetricsC = com.tencent.android.tpush.stat.a.h.c(context);
        deviceInfo.resolution = displayMetricsC.widthPixels + "*" + displayMetricsC.heightPixels;
        deviceInfo.apiLevel = Constants.MAIN_VERSION_TAG + Build.VERSION.SDK_INT;
        deviceInfo.sdCard = com.tencent.android.tpush.stat.a.h.b(context);
        deviceInfo.sdDouble = com.tencent.android.tpush.stat.a.h.a(context);
        deviceInfo.sdkVersion = String.valueOf(3.24f);
        deviceInfo.sdkVersionName = Build.VERSION.RELEASE;
        deviceInfo.isRooted = com.tencent.android.tpush.service.e.m.q(context);
        deviceInfo.language = Locale.getDefault().getLanguage();
        deviceInfo.timezone = TimeZone.getDefault().getID();
        deviceInfo.launcherName = com.tencent.android.tpush.service.e.m.p(context);
        return deviceInfo;
    }

    public void a(long j, String str, String str2, String str3, String str4, int i, String str5, String str6, String str7, com.tencent.android.tpush.service.channel.t tVar) {
        TpnsRegisterReq tpnsRegisterReq = new TpnsRegisterReq();
        tpnsRegisterReq.accessId = j;
        tpnsRegisterReq.accessKey = str;
        tpnsRegisterReq.deviceId = str2;
        tpnsRegisterReq.appCert = str5;
        tpnsRegisterReq.account = str3;
        tpnsRegisterReq.ticket = str4;
        tpnsRegisterReq.ticketType = (short) i;
        tpnsRegisterReq.deviceInfo = a(n.f());
        tpnsRegisterReq.token = CacheManager.getToken(n.f());
        tpnsRegisterReq.version = (short) 2;
        tpnsRegisterReq.appVersion = str6;
        tpnsRegisterReq.reserved = str7;
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.b("PushServiceNetworkHandler", "Register(" + j + "," + str2 + "," + str3 + "," + str4 + "," + i + "),token: " + tpnsRegisterReq.token);
        }
        int i2 = 0;
        tpnsRegisterReq.guid = CacheManager.getGuid(n.f());
        while (PushConstants.PUSH_TYPE_NOTIFY.equals(CacheManager.getToken(n.f())) && !com.tencent.android.tpush.stat.b.c.a()) {
            i2++;
            if (i2 >= 8) {
                break;
            } else {
                try {
                    Thread.sleep(500L);
                } catch (Exception e) {
                }
            }
        }
        tpnsRegisterReq.token = CacheManager.getToken(n.f());
        tpnsRegisterReq.guid = CacheManager.getGuid(n.f());
        com.tencent.android.tpush.service.channel.b.a().a(tpnsRegisterReq, tVar);
        if (!PushConstants.PUSH_TYPE_NOTIFY.equals(CacheManager.getToken(n.f()))) {
            com.tencent.android.tpush.stat.b.c.b();
        }
    }

    public void a(String str, String str2, long j, String str3, String str4, com.tencent.android.tpush.service.channel.t tVar) {
        TpnsUnregisterReq tpnsUnregisterReq = new TpnsUnregisterReq();
        String encryptAPKSignature = Constants.MAIN_VERSION_TAG;
        try {
            encryptAPKSignature = TpnsSecurity.getEncryptAPKSignature(n.f().createPackageContext(str4, 0));
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", ">> create context [for: " + str4 + "] fail.", e);
        }
        tpnsUnregisterReq.unregInfo = new UnregInfo(new AppInfo(j, str3, encryptAPKSignature, (byte) 0), (byte) 0, 0L);
        com.tencent.android.tpush.service.channel.b.a().a(tpnsUnregisterReq, tVar);
    }

    public void a(long j) {
        com.tencent.android.tpush.service.channel.b.a().a(new TpnsConfigReq(j), new u(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", "@@ loadConfiguraionFailHandler(" + i + "," + str + ")");
    }

    public void a(String str) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "Action uninstallReport : pkgName = " + str);
        }
        RegisterEntity registerInfoByPkgName = CacheManager.getRegisterInfoByPkgName(str);
        if (registerInfoByPkgName != null) {
            TpnsUnregisterReq tpnsUnregisterReq = new TpnsUnregisterReq();
            tpnsUnregisterReq.unregInfo = new UnregInfo(new AppInfo(registerInfoByPkgName.accessId, registerInfoByPkgName.accessKey, Constants.MAIN_VERSION_TAG, (byte) 0), (byte) 1, System.currentTimeMillis());
            CacheManager.UninstallInfoByPkgName(str);
            com.tencent.android.tpush.service.channel.b.a().a(tpnsUnregisterReq, new v(this, str));
            return;
        }
        com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "The RegisterEntity entity is null, PkgName = " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str, String str2, TpnsUnregisterReq tpnsUnregisterReq, com.tencent.android.tpush.service.channel.a aVar) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.a(Constants.ServiceLogTag, "@@ uninstallReportFailedHandler(" + i + "," + str + "," + str2 + "," + tpnsUnregisterReq + ")");
        }
    }

    public void a(ArrayList arrayList, com.tencent.android.tpush.service.channel.t tVar) {
        if (arrayList != null && arrayList.size() > 0) {
            com.tencent.android.tpush.service.channel.b.a().a(new TpnsPushVerifyReq(arrayList), tVar);
        }
    }

    public void a(long j, String str, int i, String str2, com.tencent.android.tpush.service.channel.t tVar) {
        TpnsTokenTagReq tpnsTokenTagReq = new TpnsTokenTagReq();
        tpnsTokenTagReq.accessId = j;
        tpnsTokenTagReq.flag = i;
        tpnsTokenTagReq.tag = str2;
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "Action -> sendTag to server (" + j + "," + str + ")");
        }
        com.tencent.android.tpush.service.channel.b.a().a(tpnsTokenTagReq, tVar);
    }

    public void b(ArrayList arrayList, com.tencent.android.tpush.service.channel.t tVar) {
        if (arrayList != null && arrayList.size() != 0) {
            TpnsPushClickReq tpnsPushClickReq = new TpnsPushClickReq();
            tpnsPushClickReq.msgClickList = arrayList;
            com.tencent.android.tpush.service.channel.b.a().a(tpnsPushClickReq, tVar);
        }
    }

    public void a(boolean z, long j) {
        com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "loadIPList :" + j);
        long lastLoadIpTime = CacheManager.getLastLoadIpTime(n.f());
        if (z) {
            if (System.currentTimeMillis() - lastLoadIpTime > com.tencent.android.tpush.service.a.a.a(n.f()).n && com.tencent.android.tpush.service.a.a.a(n.f()).b() != j) {
                a().a(j);
            }
        } else if (com.tencent.android.tpush.service.a.a.a(n.f()).b() != j) {
            a().a(j);
        }
        if (System.currentTimeMillis() - lastLoadIpTime >= com.tencent.android.tpush.service.a.a.a(n.f()).n) {
            TpnsGetApListReq tpnsGetApListReq = new TpnsGetApListReq();
            NetworkInfo networkInfo = new NetworkInfo();
            networkInfo.network = com.tencent.android.tpush.service.e.m.k(n.f());
            networkInfo.op = com.tencent.android.tpush.service.e.m.l(n.f());
            tpnsGetApListReq.netInfo = networkInfo;
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "sendMessage TpnsGetApListReq:" + tpnsGetApListReq);
            com.tencent.android.tpush.service.channel.b.a().a(tpnsGetApListReq, new w(this));
        }
    }

    public static byte a(boolean z) {
        return z ? (byte) 1 : (byte) 0;
    }

    public void a(long j, String str, String str2, String str3, com.tencent.android.tpush.service.channel.t tVar) {
        com.tencent.android.tpush.service.channel.b.a().a(new TpnsUpdateTokenReq(j, str, str2, str3), tVar);
    }

    public void a(Intent intent, com.tencent.android.tpush.service.channel.t tVar) {
        TpnsPushCommReportReq tpnsPushCommReportReq = new TpnsPushCommReportReq();
        tpnsPushCommReportReq.type = intent.getLongExtra("type", 0L);
        try {
            tpnsPushCommReportReq.accessId = Long.parseLong(Rijndael.decrypt(intent.getStringExtra("accessId")));
        } catch (NumberFormatException e) {
            com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", "sendCommReportMessage NumberFormatException");
        }
        tpnsPushCommReportReq.msgId = intent.getLongExtra(MessageKey.MSG_ID, 0L);
        tpnsPushCommReportReq.broadcastId = intent.getLongExtra("broadcastId", 0L);
        tpnsPushCommReportReq.msgTimestamp = intent.getLongExtra("msgTimestamp", 0L);
        tpnsPushCommReportReq.clientTimestamp = intent.getLongExtra("clientTimestamp", 0L);
        tpnsPushCommReportReq.pkgName = intent.getStringExtra("pkgName");
        String strDecrypt = Rijndael.decrypt(intent.getStringExtra("msg"));
        if (strDecrypt != null) {
            tpnsPushCommReportReq.msg = strDecrypt;
        }
        String strDecrypt2 = Rijndael.decrypt(intent.getStringExtra("ext"));
        if (strDecrypt2 != null) {
            tpnsPushCommReportReq.ext = strDecrypt2;
        }
        com.tencent.android.tpush.service.channel.b.a().a(tpnsPushCommReportReq, tVar);
    }
}
