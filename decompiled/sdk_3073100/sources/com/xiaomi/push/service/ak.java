package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ak {
    private static ak a;
    private ConcurrentHashMap<String, HashMap<String, b>> b = new ConcurrentHashMap<>();
    private List<a> c = new ArrayList();

    public interface a {
        void a();
    }

    public static class b {
        public String a;
        public String b;
        public String c;
        public String d;
        public boolean e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public com.xiaomi.push.service.b k;
        public Context l;
        private XMPushService p;
        c m = c.unbind;
        private int n = 0;
        private List<a> o = new ArrayList();
        private XMPushService.b q = new XMPushService.b(this);

        public interface a {
            void a(c cVar, c cVar2, int i);
        }

        public b() {
        }

        public b(XMPushService xMPushService) {
            this.p = xMPushService;
            a(new al(this));
        }

        public long a() {
            return 1000 * (((long) ((Math.random() * 20.0d) - 10.0d)) + ((long) ((this.n + 1) * 15)));
        }

        public String a(int i) {
            switch (i) {
                case 1:
                    return "OPEN";
                case 2:
                    return "CLOSE";
                case 3:
                    return "KICK";
                default:
                    return NetworkUtils.NETWORKTYPE_INVALID;
            }
        }

        public void a(a aVar) {
            synchronized (this.o) {
                this.o.add(aVar);
            }
        }

        public void a(c cVar, int i, int i2, String str, String str2) {
            synchronized (this.o) {
                Iterator<a> it = this.o.iterator();
                while (it.hasNext()) {
                    it.next().a(this.m, cVar, i2);
                }
            }
            if (this.m != cVar) {
                com.xiaomi.channel.commonutils.logger.b.a(String.format("update the client %7$s status. %1$s->%2$s %3$s %4$s %5$s %6$s", this.m, cVar, a(i), am.a(i2), str, str2, this.h));
                this.m = cVar;
            }
            if (this.k == null) {
                com.xiaomi.channel.commonutils.logger.b.d("status changed while the client dispatcher is missing");
                return;
            }
            if (i == 2) {
                this.k.a(this.l, this, i2);
                return;
            }
            if (i == 3) {
                this.k.a(this.l, this, str2, str);
                return;
            }
            if (i == 1) {
                boolean z = cVar == c.binded;
                if (!z && "wait".equals(str2)) {
                    this.n++;
                } else if (z) {
                    this.n = 0;
                }
                this.k.a(this.l, this, z, i2, str);
            }
        }

        public void b(a aVar) {
            synchronized (this.o) {
                this.o.remove(aVar);
            }
        }
    }

    public enum c {
        unbind,
        binding,
        binded
    }

    private ak() {
    }

    public static synchronized ak a() {
        if (a == null) {
            a = new ak();
        }
        return a;
    }

    private String d(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int iIndexOf = str.indexOf("@");
        return iIndexOf > 0 ? str.substring(0, iIndexOf) : str;
    }

    public synchronized void a(Context context) {
        Iterator<HashMap<String, b>> it = this.b.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().a(c.unbind, 1, 3, null, null);
            }
        }
    }

    public synchronized void a(Context context, int i) {
        Iterator<HashMap<String, b>> it = this.b.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().a(c.unbind, 2, i, null, null);
            }
        }
    }

    public synchronized void a(a aVar) {
        this.c.add(aVar);
    }

    public synchronized void a(b bVar) {
        HashMap<String, b> map = this.b.get(bVar.h);
        if (map == null) {
            map = new HashMap<>();
            this.b.put(bVar.h, map);
        }
        map.put(d(bVar.b), bVar);
        Iterator<a> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public synchronized void a(String str) {
        HashMap<String, b> map = this.b.get(str);
        if (map != null) {
            map.clear();
            this.b.remove(str);
        }
        Iterator<a> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public synchronized void a(String str, String str2) {
        HashMap<String, b> map = this.b.get(str);
        if (map != null) {
            map.remove(d(str2));
            if (map.isEmpty()) {
                this.b.remove(str);
            }
        }
        Iterator<a> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public synchronized b b(String str, String str2) {
        HashMap<String, b> map;
        map = this.b.get(str);
        return map == null ? null : map.get(d(str2));
    }

    public synchronized ArrayList<b> b() {
        ArrayList<b> arrayList;
        arrayList = new ArrayList<>();
        Iterator<HashMap<String, b>> it = this.b.values().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().values());
        }
        return arrayList;
    }

    public synchronized List<String> b(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<HashMap<String, b>> it = this.b.values().iterator();
        while (it.hasNext()) {
            for (b bVar : it.next().values()) {
                if (str.equals(bVar.a)) {
                    arrayList.add(bVar.h);
                }
            }
        }
        return arrayList;
    }

    public synchronized int c() {
        return this.b.size();
    }

    public synchronized Collection<b> c(String str) {
        return !this.b.containsKey(str) ? new ArrayList<>() : ((HashMap) this.b.get(str).clone()).values();
    }

    public synchronized void d() {
        this.b.clear();
    }

    public synchronized void e() {
        this.c.clear();
    }
}
