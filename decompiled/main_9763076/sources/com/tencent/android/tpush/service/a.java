package com.tencent.android.tpush.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterReq;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.mid.api.MidEntity;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a b = null;
    private static volatile k c = null;
    private static volatile m d = null;
    public static final String a = a.class.getSimpleName();

    private a() {
    }

    public static synchronized a a() {
        if (b == null) {
            b = new a();
        }
        return b;
    }

    public synchronized void a(Context context) {
        if (context != null) {
            try {
                if (c == null) {
                    c = new k(this, null);
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addDataScheme("package");
                    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                    context.registerReceiver(c, intentFilter);
                }
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(a, "registerReceiver", e);
            }
            try {
                if (d == null) {
                    d = new m(this, null);
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("com.tencent.android.tpush.action.REGISTER.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.UNREGISTER.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.ENABLE_DEBUG.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.MSG_ACK.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.TAG.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.PUSH_CLICK.RESULT.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.PUSH_CANCELLED.RESULT.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.ack.sdk2srv.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.reserved.act.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.UPDATE_OTHER_PUSH_TOKEN.V3");
                    intentFilter2.addAction("com.tencent.android.tpush.action.COMM_REPORT.V3");
                    context.registerReceiver(d, intentFilter2);
                }
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(a, "registerReceiver", e2);
            }
            try {
                Intent intent = new Intent("com.tencent.android.tpush.action.SERVICE_START.V3");
                intent.putExtra("pkg", n.f().getPackageName());
                intent.putExtra(MidEntity.TAG_VER, 3.24f);
                n.f().sendBroadcast(intent);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(a, "sendBroadcast", th);
            }
        }
    }

    public static void b(Context context) {
        if (context != null) {
            if (c != null) {
                com.tencent.android.tpush.common.t.a(context, c);
                c = null;
            }
            if (d != null) {
                com.tencent.android.tpush.common.t.a(context, d);
                d = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, Intent intent) {
        String dataString = intent.getDataString();
        if (dataString != null && context != null && com.tencent.android.tpush.service.e.m.g(context, dataString.substring(8))) {
            n.a().d();
            com.tencent.android.tpush.common.g.a().a(new b(this, context), 2000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context, Intent intent) {
        String dataString = intent.getDataString();
        if (dataString != null && context != null) {
            com.tencent.android.tpush.common.g.a().a(new c(this, context, dataString.substring(8)), 30000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context) {
        boolean z = true;
        if (com.tencent.android.tpush.service.e.a.a(context) == 3) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jB = com.tencent.android.tpush.service.e.f.b(context, "com.tencent.android.tpush.action.next.applist.ts.V3", 0L);
            if (jB != 0 && jCurrentTimeMillis <= jB && Math.abs(jB - jCurrentTimeMillis) <= 172800000) {
                z = false;
            }
            if (z) {
                com.tencent.android.tpush.common.g.a().a(new d(this, context, jCurrentTimeMillis), 5000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context, Intent intent) {
        if (context != null && intent != null) {
            String strDecrypt = Rijndael.decrypt(intent.getStringExtra("accId"));
            String strDecrypt2 = Rijndael.decrypt(intent.getStringExtra("accKey"));
            String strDecrypt3 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_PACK_NAME));
            String strDecrypt4 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_ACCOUNT));
            String strDecrypt5 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_TICKET));
            int intExtra = intent.getIntExtra(Constants.FLAG_TICKET_TYPE, -1);
            String strDecrypt6 = Rijndael.decrypt(intent.getStringExtra("qua"));
            String stringExtra = intent.getStringExtra("appVer");
            String strDecrypt7 = Rijndael.decrypt(intent.getStringExtra("reserved"));
            boolean booleanExtra = intent.getBooleanExtra("aidl", false);
            try {
                if (!com.tencent.android.tpush.service.e.m.b(strDecrypt6)) {
                    CacheManager.setQua(context, Long.parseLong(strDecrypt), strDecrypt6);
                }
                String encryptAPKSignature = Constants.MAIN_VERSION_TAG;
                try {
                    encryptAPKSignature = TpnsSecurity.getEncryptAPKSignature(context.createPackageContext(strDecrypt3, 0));
                } catch (Throwable th) {
                }
                s.a().a(Long.parseLong(strDecrypt), strDecrypt2, com.tencent.android.tpush.service.e.c.a(), strDecrypt4, strDecrypt5, intExtra, encryptAPKSignature, stringExtra, strDecrypt7, new f(this, strDecrypt3, strDecrypt, booleanExtra, context));
                XGWatchdog.getInstance(context).sendAllLocalXGAppList();
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.i(a, ">> register error " + e);
                com.tencent.android.tpush.a.a.i(a, ">> register error-> " + Log.getStackTraceString(e));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Context context, Intent intent) {
        if (context != null && intent != null) {
            long longExtra = intent.getLongExtra("accId", -1L);
            String strDecrypt = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_PACK_NAME));
            int intExtra = intent.getIntExtra(Constants.FLAG_TAG_TYPE, -1);
            String strDecrypt2 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_TAG_NAME));
            s.a().a(longExtra, strDecrypt, intExtra, strDecrypt2, new g(this, longExtra, intExtra, strDecrypt2, strDecrypt));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(Context context, Intent intent) {
        if (context != null && intent != null) {
            String strDecrypt = Rijndael.decrypt(intent.getStringExtra("accId"));
            String strDecrypt2 = Rijndael.decrypt(intent.getStringExtra("accKey"));
            String strDecrypt3 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_PACK_NAME));
            String strDecrypt4 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_TOKEN));
            CacheManager.UnregisterInfoByPkgName(strDecrypt3);
            try {
                s.a().a(strDecrypt4, com.tencent.android.tpush.service.e.c.a(), Long.parseLong(strDecrypt), strDecrypt2, strDecrypt3, new h(this, strDecrypt, strDecrypt3));
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.i(a, ">>> unregister error " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(Context context, Intent intent) throws Throwable {
        if (context != null && intent != null) {
            String strDecrypt = Rijndael.decrypt(intent.getStringExtra("accId"));
            String strDecrypt2 = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_TOKEN));
            String strDecrypt3 = Rijndael.decrypt(intent.getStringExtra("other_push_type"));
            String strDecrypt4 = Rijndael.decrypt(intent.getStringExtra(Constants.OTHER_PUSH_TOKEN));
            com.tencent.android.tpush.a.a.e(a, "binder other push token with accid = " + strDecrypt + "  token = " + strDecrypt2 + " otherPushType = " + strDecrypt3 + " otherPushToken = " + strDecrypt4);
            com.tencent.android.tpush.common.t.a("binder other push token with accid = " + strDecrypt + "  token = " + strDecrypt2 + " otherPushType = " + strDecrypt3 + " otherPushToken = " + strDecrypt4, context);
            String strA = com.tencent.android.tpush.service.e.h.a(context, strDecrypt + "otherpush", Constants.MAIN_VERSION_TAG);
            if (!com.tencent.android.tpush.service.e.m.b(strA)) {
                long jA = com.tencent.android.tpush.service.e.h.a(context, strDecrypt + "otherpushts", -1L);
                if (strA.equals(strDecrypt2 + ":" + strDecrypt4) && Math.abs(System.currentTimeMillis() - jA) > 86400000) {
                    com.tencent.android.tpush.a.a.f(a, "Already binder other push succeed token with accid = " + strDecrypt + "  token = " + strDecrypt2 + " otherPushType = " + strDecrypt3 + " otherPushToken = " + strDecrypt4);
                    return;
                }
                com.tencent.android.tpush.a.a.f(a, "OtherToken or Mid changed , go on binder");
            }
            s.a().a(Long.parseLong(strDecrypt), strDecrypt2, strDecrypt3, strDecrypt4, new i(this, strDecrypt, strDecrypt2, strDecrypt3, strDecrypt4, context));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(Context context, Intent intent) {
        if (context != null && intent != null) {
            s.a().a(intent, new j(this, Rijndael.decrypt(intent.getStringExtra("accessId")), context, intent.getStringExtra("pkgName"), intent));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h(Context context, Intent intent) {
        if (intent != null && context != null) {
            boolean booleanExtra = intent.getBooleanExtra("debugMode", false);
            XGPushConfig.enableDebug = booleanExtra;
            if (booleanExtra) {
                com.tencent.android.tpush.a.a.a(2);
            } else {
                com.tencent.android.tpush.a.a.a(3);
            }
        }
    }

    private void a(int i, TpnsRegisterReq tpnsRegisterReq, String str, String str2) {
        Intent intentA = com.tencent.android.tpush.service.e.m.a(i, str2, 1);
        intentA.putExtra("accId", tpnsRegisterReq.accessId);
        if (tpnsRegisterReq.account != null && tpnsRegisterReq.account.length() != 0) {
            intentA.putExtra(Constants.FLAG_ACCOUNT, tpnsRegisterReq.account);
        }
        if (str != null && str.length() != 0) {
            intentA.putExtra(Constants.FLAG_TOKEN, str);
        }
        if (tpnsRegisterReq.ticket != null && tpnsRegisterReq.ticket.length() != 0) {
            intentA.putExtra(Constants.FLAG_TICKET, tpnsRegisterReq.ticket);
            intentA.putExtra(Constants.FLAG_TICKET_TYPE, tpnsRegisterReq.ticketType);
        }
        if (tpnsRegisterReq.deviceId != null && tpnsRegisterReq.deviceId.length() != 0) {
            intentA.putExtra(Constants.FLAG_DEVICE_ID, tpnsRegisterReq.deviceId);
        }
        n.f().sendBroadcast(intentA);
    }

    private void a(int i, String str) {
        n.f().sendBroadcast(com.tencent.android.tpush.service.e.m.a(i, str, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str, int i2, String str2) {
        Intent intentA = com.tencent.android.tpush.service.e.m.a(i, str2, 3);
        intentA.putExtra(Constants.FLAG_TAG_NAME, Rijndael.encrypt(str));
        intentA.putExtra(Constants.FLAG_TAG_TYPE, i2);
        n.f().sendBroadcast(intentA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, TpnsRegisterRsp tpnsRegisterRsp, TpnsRegisterReq tpnsRegisterReq, com.tencent.android.tpush.service.channel.a aVar, String str, boolean z) {
        com.tencent.android.tpush.stat.b.c.b();
        Intent intent = new Intent("com.tencent.android.tpush.action.REGISTER.RESULT.V3");
        intent.putExtra("accId", tpnsRegisterReq.accessId);
        intent.putExtra("data", tpnsRegisterRsp.token);
        intent.putExtra("flag", 0);
        intent.putExtra("code", i);
        intent.putExtra("operation", 0);
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.accessId = tpnsRegisterReq.accessId;
        registerEntity.accessKey = tpnsRegisterReq.accessKey;
        registerEntity.token = tpnsRegisterRsp.token;
        registerEntity.packageName = str;
        registerEntity.timestamp = System.currentTimeMillis() / 1000;
        CacheManager.addRegisterInfo(registerEntity);
        registerEntity.guid = tpnsRegisterRsp.guid;
        CacheManager.setTokenAndGuid(n.f(), tpnsRegisterRsp.token, tpnsRegisterRsp.guid);
        if (!com.tencent.android.tpush.service.e.m.b(str)) {
            intent.setPackage(str);
        }
        n.f().sendBroadcast(intent);
        a(i, tpnsRegisterReq, tpnsRegisterRsp.token, str);
        com.tencent.android.tpush.service.channel.b.a().a(false);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("aidl", z);
            jSONObject.toString();
        } catch (JSONException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str, TpnsRegisterReq tpnsRegisterReq, com.tencent.android.tpush.service.channel.a aVar, String str2) {
        com.tencent.android.tpush.stat.b.c.b();
        Intent intent = new Intent("com.tencent.android.tpush.action.REGISTER.RESULT.V3");
        intent.putExtra("data", Constants.MAIN_VERSION_TAG);
        intent.putExtra("code", i);
        intent.putExtra("msg", str);
        intent.putExtra("flag", 0);
        intent.putExtra("operation", 1);
        if (!com.tencent.android.tpush.service.e.m.b(str2)) {
            intent.setPackage(str2);
        }
        n.f().sendBroadcast(intent);
        a(i, tpnsRegisterReq, tpnsRegisterReq.token, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, TpnsUnregisterReq tpnsUnregisterReq, com.tencent.android.tpush.service.channel.a aVar, String str) {
        Intent intent = new Intent("com.tencent.android.tpush.action.UNREGISTER.RESULT.V3");
        intent.putExtra("flag", 0);
        intent.putExtra("operation", 0);
        CacheManager.UnregisterInfoSuccessByPkgName(str);
        CacheManager.removeRegisterInfos(str);
        if (!com.tencent.android.tpush.common.t.c(str)) {
            intent.setPackage(str);
        }
        n.f().sendBroadcast(intent);
        a(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str, TpnsUnregisterReq tpnsUnregisterReq, com.tencent.android.tpush.service.channel.a aVar, String str2) {
        com.tencent.android.tpush.a.a.i(a, "unregisterFailHandler failed with (" + i + "," + str + "," + tpnsUnregisterReq + "," + aVar + "," + str2 + ")");
        Intent intent = new Intent("com.tencent.android.tpush.action.UNREGISTER.RESULT.V3");
        intent.putExtra("flag", 0);
        intent.putExtra("code", i);
        intent.putExtra("msg", str);
        intent.putExtra("operation", 1);
        if (!com.tencent.android.tpush.common.t.c(str2)) {
            intent.setPackage(str2);
        }
        n.f().sendBroadcast(intent);
        a(i, str2);
    }
}
