package com.alibaba.sdk.android.utils.crashdefend;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.alibaba.sdk.android.beacon.Beacon;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: compiled from: BeaconConfigManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static List<Beacon> f88a = new ArrayList();
    private Context mContext;

    /* JADX INFO: renamed from: com.alibaba.sdk.android.utils.crashdefend.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: BeaconConfigManager.java */
    public interface InterfaceC0004a {
        void update();
    }

    private a(Context context) {
        this.mContext = null;
        this.mContext = context;
    }

    /* JADX INFO: compiled from: BeaconConfigManager.java */
    public static class b implements Beacon.OnUpdateListener {
        private Beacon a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private InterfaceC0004a f89a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private d f90a;

        public b(d dVar, InterfaceC0004a interfaceC0004a) {
            this.f90a = dVar;
            this.f89a = interfaceC0004a;
        }

        protected void a(int i) {
            if (this.f90a != null) {
                int i2 = this.f90a.c;
                this.f90a.c = i;
                if (this.f89a != null && i2 != i) {
                    this.f89a.update();
                }
            }
        }

        public void a(Beacon beacon) {
            this.a = beacon;
        }

        @Override // com.alibaba.sdk.android.beacon.Beacon.OnUpdateListener
        public void onUpdate(List<Beacon.Config> list) {
            Log.i("BeaconConfigManager", "beacon onUpdate");
            try {
                if (this.a != null) {
                    this.a.stop();
                }
                synchronized (a.f88a) {
                    a.f88a.remove(this.a);
                }
                if (this.f90a != null && list != null && list.size() > 0) {
                    for (Beacon.Config config : list) {
                        if (("___" + this.f90a.f99a + "_service___").equalsIgnoreCase(config.key)) {
                            JSONObject jSONObject = new JSONObject(config.value);
                            if (jSONObject.has("status")) {
                                String string = jSONObject.getString("status");
                                if ("disabled".equalsIgnoreCase(string)) {
                                    a(2);
                                    Log.i("BeaconConfigManager", "beacon onUpdate:disable");
                                } else if ("enable".equalsIgnoreCase(string)) {
                                    a(1);
                                    Log.i("BeaconConfigManager", "beacon onUpdate:enable");
                                } else {
                                    a(0);
                                    Log.i("BeaconConfigManager", "beacon onUpdate:normal");
                                }
                            } else {
                                a(0);
                                Log.i("BeaconConfigManager", "beacon onUpdate:unknown");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("BeaconConfigManager", "onUpdate Exception " + e.getMessage());
            }
        }
    }

    public static a a(Context context) {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a(context);
                }
            }
        }
        return a;
    }

    public void a(d dVar, InterfaceC0004a interfaceC0004a) {
        if (this.mContext != null) {
            HashMap map = new HashMap();
            map.put("sdkId", dVar.f99a);
            map.put("sdkVer", dVar.f101b);
            map.put("osType", "os.android");
            map.put("osVer", Constants.MAIN_VERSION_TAG + Build.VERSION.RELEASE);
            map.put("beaconVer", "1.0.1");
            map.put("devBrand", Build.BRAND);
            map.put("devModel", Build.MODEL);
            Beacon beaconBuild = new Beacon.Builder().appKey("24527540").appSecret("56fc10fbe8c6ae7d0d895f49c4fb6838").extras(map).build();
            b bVar = new b(dVar, interfaceC0004a);
            bVar.a(beaconBuild);
            beaconBuild.addUpdateListener(bVar);
            beaconBuild.start(this.mContext);
            f88a.add(beaconBuild);
        }
    }
}
