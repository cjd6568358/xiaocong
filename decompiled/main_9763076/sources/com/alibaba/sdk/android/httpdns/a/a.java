package com.alibaba.sdk.android.httpdns.a;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.beacon.Beacon;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a a = null;
    private Context mContext = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.c.a f52a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Beacon f51a = null;
    private boolean g = true;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final Beacon.OnUpdateListener f50a = new Beacon.OnUpdateListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.1
        @Override // com.alibaba.sdk.android.beacon.Beacon.OnUpdateListener
        public void onUpdate(List<Beacon.Config> list) {
            a.this.b(list);
        }
    };

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final Beacon.OnServiceErrListener f49a = new Beacon.OnServiceErrListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.2
        @Override // com.alibaba.sdk.android.beacon.Beacon.OnServiceErrListener
        public void onErr(Beacon.Error error) {
            Log.e("HTTPDNS:BeaconManager", "beacon error. errorCode:" + error.errCode + ", errorMsg:" + error.errMsg);
        }
    };

    private a() {
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    private boolean a(Beacon.Config config) {
        if (config == null || !config.key.equalsIgnoreCase("___httpdns_service___")) {
            return false;
        }
        String str = config.value;
        if (str == null) {
            return true;
        }
        Log.d("HTTPDNS:BeaconManager", "httpdns configs:" + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("ut")) {
                d(jSONObject.getString("ut"));
            }
            if (!jSONObject.has("ip-ranking")) {
                return true;
            }
            i(jSONObject.getString("ip-ranking"));
            return true;
        } catch (JSONException e) {
            Log.e("HTTPDNS:BeaconManager", "parse push configs failed.", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(List<Beacon.Config> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (Beacon.Config config : list) {
            if (config.key.equalsIgnoreCase("___httpdns_service___")) {
                a(config);
            }
        }
    }

    private boolean d(String str) {
        if (str == null || this.f52a == null) {
            return false;
        }
        Log.d("HTTPDNS:BeaconManager", "is report enabled:" + str);
        if (str.equalsIgnoreCase("disabled")) {
            this.f52a.d(false);
            return true;
        }
        this.f52a.d(true);
        return true;
    }

    private void i(String str) {
        if (str != null) {
            Log.d("HTTPDNS:BeaconManager", "is IP probe enabled:" + str);
            if (str.equalsIgnoreCase("disabled")) {
                this.g = false;
            } else {
                this.g = true;
            }
        }
    }

    public void a(Context context, String str) {
        this.mContext = context;
        if (this.mContext != null) {
            HashMap map = new HashMap();
            map.put("sdkId", "httpdns");
            map.put("accountId", str);
            this.f51a = new Beacon.Builder().appKey("24657847").appSecret("f30fc0937f2b1e9e50a1b7134f1ddb10").extras(map).build();
            this.f51a.addUpdateListener(this.f50a);
            this.f51a.addServiceErrListener(this.f49a);
            this.f51a.start(this.mContext.getApplicationContext());
        }
    }

    public void a(com.alibaba.sdk.android.httpdns.c.a aVar) {
        this.f52a = aVar;
    }

    public boolean e() {
        return this.g;
    }
}
