package com.meizu.cloud.pushsdk.b.e;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import com.meizu.cloud.pushsdk.b.f.e;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static String a = b.class.getSimpleName();
    private HashMap<String, String> b;
    private HashMap<String, Object> c;
    private HashMap<String, String> d;

    private b(a aVar) {
        this.b = new HashMap<>();
        this.c = new HashMap<>();
        this.d = new HashMap<>();
        d();
        e();
        f();
        g();
        if (aVar.a != null) {
            a(aVar.a);
        }
        com.meizu.cloud.pushsdk.b.f.c.c(a, "Subject created successfully.", new Object[0]);
    }

    public static class a {
        private Context a = null;

        public a a(Context context) {
            this.a = context;
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public void a(Context context) {
        b(context);
        c(context);
    }

    private void a(String str, String str2) {
        if (str != null && str2 != null && !str.isEmpty() && !str2.isEmpty()) {
            this.d.put(str, str2);
        }
    }

    private void a(String str, Object obj) {
        if ((str != null && obj != null && !str.isEmpty()) || ((obj instanceof String) && !((String) obj).isEmpty())) {
            this.c.put(str, obj);
        }
    }

    private void d() {
        a("osType", "android-" + Build.VERSION.RELEASE);
    }

    private void e() {
        a("osVersion", Build.DISPLAY);
    }

    private void f() {
        a("deviceModel", Build.MODEL);
    }

    private void g() {
        a("deviceManufacturer", Build.MANUFACTURER);
    }

    public void b(Context context) {
        Location locationC = e.c(context);
        if (locationC == null) {
            com.meizu.cloud.pushsdk.b.f.c.a(a, "Location information not available.", new Object[0]);
            return;
        }
        a("latitude", Double.valueOf(locationC.getLatitude()));
        a("longitude", Double.valueOf(locationC.getLongitude()));
        a("altitude", Double.valueOf(locationC.getAltitude()));
        a("latitudeLongitudeAccuracy", Float.valueOf(locationC.getAccuracy()));
        a("speed", Float.valueOf(locationC.getSpeed()));
        a("bearing", Float.valueOf(locationC.getBearing()));
    }

    public void c(Context context) {
        String strB = e.b(context);
        if (strB != null) {
            a("carrier", strB);
        }
    }

    public Map<String, Object> a() {
        return this.c;
    }

    public Map<String, String> b() {
        return this.d;
    }

    public Map<String, String> c() {
        return this.b;
    }
}
