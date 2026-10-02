package com.meizu.cloud.pushsdk.a.a;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.meizu.cloud.pushsdk.a.a.b;
import com.meizu.cloud.pushsdk.a.d.g;
import com.meizu.cloud.pushsdk.a.e.f;
import com.meizu.cloud.pushsdk.a.e.h;
import com.meizu.cloud.pushsdk.a.e.i;
import com.meizu.cloud.pushsdk.a.e.j;
import com.meizu.cloud.pushsdk.a.e.k;
import com.meizu.cloud.pushsdk.a.e.l;
import com.meizu.cloud.pushsdk.a.e.m;
import com.meizu.cloud.pushsdk.a.e.n;
import com.meizu.cloud.pushsdk.a.e.o;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b<T extends b> {
    private static final String a = b.class.getSimpleName();
    private static final g w = g.a("application/json; charset=utf-8");
    private static final g x = g.a("text/x-markdown; charset=utf-8");
    private static final Object z = new Object();
    private Future A;
    private com.meizu.cloud.pushsdk.a.d.a B;
    private int C;
    private boolean D;
    private boolean E;
    private int F;
    private com.meizu.cloud.pushsdk.a.e.e G;
    private f H;
    private n I;
    private l J;
    private com.meizu.cloud.pushsdk.a.e.b K;
    private m L;
    private i M;
    private h N;
    private k O;
    private com.meizu.cloud.pushsdk.a.e.g P;
    private j Q;
    private com.meizu.cloud.pushsdk.a.e.d R;
    private o S;
    private com.meizu.cloud.pushsdk.a.e.c T;
    private com.meizu.cloud.pushsdk.a.e.a U;
    private Bitmap.Config V;
    private int W;
    private int X;
    private ImageView.ScaleType Y;
    private Executor Z;
    private String aa;
    private Type ab;
    private int b;
    private d c;
    private int d;
    private String e;
    private int f;
    private Object g;
    private e h;
    private HashMap<String, String> i;
    private HashMap<String, String> j;
    private HashMap<String, String> k;
    private HashMap<String, String> l;
    private HashMap<String, String> m;
    private HashMap<String, String> n;
    private HashMap<String, File> o;
    private String p;
    private String q;
    private JSONObject r;
    private JSONArray s;
    private String t;
    private byte[] u;
    private File v;
    private g y;

    public b(C0028b c0028b) {
        this.i = new HashMap<>();
        this.j = new HashMap<>();
        this.k = new HashMap<>();
        this.l = new HashMap<>();
        this.m = new HashMap<>();
        this.n = new HashMap<>();
        this.o = new HashMap<>();
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.y = null;
        this.F = 0;
        this.Z = null;
        this.aa = null;
        this.ab = null;
        this.d = 0;
        this.b = c0028b.b;
        this.c = c0028b.a;
        this.e = c0028b.c;
        this.g = c0028b.d;
        this.i = c0028b.i;
        this.V = c0028b.e;
        this.X = c0028b.g;
        this.W = c0028b.f;
        this.Y = c0028b.h;
        this.m = c0028b.j;
        this.n = c0028b.k;
        this.Z = c0028b.l;
        this.aa = c0028b.m;
    }

    public b(c cVar) {
        this.i = new HashMap<>();
        this.j = new HashMap<>();
        this.k = new HashMap<>();
        this.l = new HashMap<>();
        this.m = new HashMap<>();
        this.n = new HashMap<>();
        this.o = new HashMap<>();
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.y = null;
        this.F = 0;
        this.Z = null;
        this.aa = null;
        this.ab = null;
        this.d = 0;
        this.b = cVar.b;
        this.c = cVar.a;
        this.e = cVar.c;
        this.g = cVar.d;
        this.i = cVar.j;
        this.j = cVar.k;
        this.k = cVar.l;
        this.m = cVar.m;
        this.n = cVar.n;
        this.r = cVar.e;
        this.s = cVar.f;
        this.t = cVar.g;
        this.v = cVar.i;
        this.u = cVar.h;
        this.Z = cVar.o;
        this.aa = cVar.p;
        if (cVar.q == null) {
            return;
        }
        this.y = g.a(cVar.q);
    }

    public b(a aVar) {
        this.i = new HashMap<>();
        this.j = new HashMap<>();
        this.k = new HashMap<>();
        this.l = new HashMap<>();
        this.m = new HashMap<>();
        this.n = new HashMap<>();
        this.o = new HashMap<>();
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.y = null;
        this.F = 0;
        this.Z = null;
        this.aa = null;
        this.ab = null;
        this.d = 1;
        this.b = 0;
        this.c = aVar.a;
        this.e = aVar.b;
        this.g = aVar.c;
        this.p = aVar.g;
        this.q = aVar.h;
        this.i = aVar.d;
        this.m = aVar.e;
        this.n = aVar.f;
        this.F = aVar.i;
        this.Z = aVar.j;
        this.aa = aVar.k;
    }

    public void a(k kVar) {
        this.h = e.STRING;
        this.O = kVar;
        com.meizu.cloud.pushsdk.a.f.a.a().a(this);
    }

    public com.meizu.cloud.pushsdk.a.a.c a() {
        this.h = e.BITMAP;
        return com.meizu.cloud.pushsdk.a.f.e.a(this);
    }

    public com.meizu.cloud.pushsdk.a.a.c b() {
        return com.meizu.cloud.pushsdk.a.f.e.a(this);
    }

    public int c() {
        return this.b;
    }

    public d d() {
        return this.c;
    }

    public String e() {
        String str;
        String strReplace = this.e;
        Iterator<Map.Entry<String, String>> it = this.n.entrySet().iterator();
        while (true) {
            str = strReplace;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<String, String> next = it.next();
            strReplace = str.replace("{" + next.getKey() + "}", String.valueOf(next.getValue()));
        }
        com.meizu.cloud.pushsdk.a.d.f.a aVarG = com.meizu.cloud.pushsdk.a.d.f.c(str).g();
        for (Map.Entry<String, String> entry : this.m.entrySet()) {
            aVarG.a(entry.getKey(), entry.getValue());
        }
        return aVarG.b().toString();
    }

    public int f() {
        return this.f;
    }

    public void a(int i) {
        this.f = i;
    }

    public e g() {
        return this.h;
    }

    public int h() {
        return this.d;
    }

    public void a(String str) {
        this.aa = str;
    }

    public String i() {
        return this.aa;
    }

    public void j() {
        this.E = true;
        if (this.T != null) {
            if (!this.D) {
                if (this.Z != null) {
                    this.Z.execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (b.this.T != null) {
                                b.this.T.a();
                            }
                            com.meizu.cloud.pushsdk.a.a.a.a("Delivering success : " + toString());
                            b.this.p();
                        }
                    });
                    return;
                } else {
                    com.meizu.cloud.pushsdk.a.b.b.a().b().c().execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.b.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (b.this.T != null) {
                                b.this.T.a();
                            }
                            com.meizu.cloud.pushsdk.a.a.a.a("Delivering success : " + toString());
                            b.this.p();
                        }
                    });
                    return;
                }
            }
            b(new com.meizu.cloud.pushsdk.a.c.a());
            p();
            return;
        }
        com.meizu.cloud.pushsdk.a.a.a.a("Prefetch done : " + toString());
        p();
    }

    public o k() {
        return new o() { // from class: com.meizu.cloud.pushsdk.a.a.b.3
            @Override // com.meizu.cloud.pushsdk.a.e.o
            public void a(long j, long j2) {
                b.this.C = (int) ((100 * j) / j2);
                if (b.this.S != null && !b.this.D) {
                    b.this.S.a(j, j2);
                }
            }
        };
    }

    public String l() {
        return this.p;
    }

    public String m() {
        return this.q;
    }

    public com.meizu.cloud.pushsdk.a.d.a n() {
        return this.B;
    }

    public void a(com.meizu.cloud.pushsdk.a.d.a aVar) {
        this.B = aVar;
    }

    public void a(Future future) {
        this.A = future;
    }

    public void o() {
        this.G = null;
        this.G = null;
        this.I = null;
        this.K = null;
        this.L = null;
        this.R = null;
        this.S = null;
        this.T = null;
        this.U = null;
    }

    public void p() {
        o();
        com.meizu.cloud.pushsdk.a.f.a.a().b(this);
    }

    public com.meizu.cloud.pushsdk.a.a.c a(com.meizu.cloud.pushsdk.a.d.k kVar) {
        com.meizu.cloud.pushsdk.a.a.c<Bitmap> cVarA;
        switch (this.h) {
            case JSON_ARRAY:
                try {
                    return com.meizu.cloud.pushsdk.a.a.c.a(new JSONArray(com.meizu.cloud.pushsdk.a.h.f.a(kVar.b().a()).h()));
                } catch (Exception e) {
                    return com.meizu.cloud.pushsdk.a.a.c.a(com.meizu.cloud.pushsdk.a.i.b.b(new com.meizu.cloud.pushsdk.a.c.a(e)));
                }
            case JSON_OBJECT:
                try {
                    return com.meizu.cloud.pushsdk.a.a.c.a(new JSONObject(com.meizu.cloud.pushsdk.a.h.f.a(kVar.b().a()).h()));
                } catch (Exception e2) {
                    return com.meizu.cloud.pushsdk.a.a.c.a(com.meizu.cloud.pushsdk.a.i.b.b(new com.meizu.cloud.pushsdk.a.c.a(e2)));
                }
            case STRING:
                try {
                    return com.meizu.cloud.pushsdk.a.a.c.a(com.meizu.cloud.pushsdk.a.h.f.a(kVar.b().a()).h());
                } catch (Exception e3) {
                    return com.meizu.cloud.pushsdk.a.a.c.a(com.meizu.cloud.pushsdk.a.i.b.b(new com.meizu.cloud.pushsdk.a.c.a(e3)));
                }
            case BITMAP:
                synchronized (z) {
                    try {
                        cVarA = com.meizu.cloud.pushsdk.a.i.b.a(kVar, this.W, this.X, this.V, this.Y);
                    } catch (Exception e4) {
                        cVarA = com.meizu.cloud.pushsdk.a.a.c.a(com.meizu.cloud.pushsdk.a.i.b.b(new com.meizu.cloud.pushsdk.a.c.a(e4)));
                    }
                }
                return cVarA;
            case PREFETCH:
                return com.meizu.cloud.pushsdk.a.a.c.a("prefetch");
            default:
                return null;
        }
    }

    public com.meizu.cloud.pushsdk.a.c.a a(com.meizu.cloud.pushsdk.a.c.a aVar) {
        try {
            if (aVar.a() != null && aVar.a().b() != null && aVar.a().b().a() != null) {
                aVar.b(com.meizu.cloud.pushsdk.a.h.f.a(aVar.a().b().a()).h());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aVar;
    }

    public synchronized void b(com.meizu.cloud.pushsdk.a.c.a aVar) {
        try {
            if (!this.E) {
                if (this.D) {
                    aVar.d();
                    aVar.a(0);
                }
                c(aVar);
                com.meizu.cloud.pushsdk.a.a.a.a("Delivering anError : " + toString());
            }
            this.E = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(final com.meizu.cloud.pushsdk.a.a.c cVar) {
        try {
            this.E = true;
            if (!this.D) {
                if (this.Z != null) {
                    this.Z.execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.b.4
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.b(cVar);
                        }
                    });
                } else {
                    com.meizu.cloud.pushsdk.a.b.b.a().b().c().execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.b.5
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.b(cVar);
                        }
                    });
                }
                com.meizu.cloud.pushsdk.a.a.a.a("Delivering success : " + toString());
                return;
            }
            com.meizu.cloud.pushsdk.a.c.a aVar = new com.meizu.cloud.pushsdk.a.c.a();
            aVar.d();
            aVar.a(0);
            c(aVar);
            p();
            com.meizu.cloud.pushsdk.a.a.a.a("Delivering cancelled : " + toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.meizu.cloud.pushsdk.a.a.c cVar) {
        if (this.H != null) {
            this.H.a((JSONObject) cVar.a());
        } else if (this.G != null) {
            this.G.a((JSONArray) cVar.a());
        } else if (this.I != null) {
            this.I.a((String) cVar.a());
        } else if (this.K != null) {
            this.K.a((Bitmap) cVar.a());
        } else if (this.L != null) {
            this.L.a(cVar.a());
        } else if (this.M != null) {
            this.M.a(cVar.d(), (JSONObject) cVar.a());
        } else if (this.N != null) {
            this.N.a(cVar.d(), (JSONArray) cVar.a());
        } else if (this.O != null) {
            this.O.a(cVar.d(), (String) cVar.a());
        } else if (this.P != null) {
            this.P.a(cVar.d(), (Bitmap) cVar.a());
        } else if (this.Q != null) {
            this.Q.a(cVar.d(), cVar.a());
        }
        p();
    }

    private void c(com.meizu.cloud.pushsdk.a.c.a aVar) {
        if (this.H != null) {
            this.H.a(aVar);
            return;
        }
        if (this.G != null) {
            this.G.a(aVar);
            return;
        }
        if (this.I != null) {
            this.I.a(aVar);
            return;
        }
        if (this.K != null) {
            this.K.a(aVar);
            return;
        }
        if (this.L != null) {
            this.L.a(aVar);
            return;
        }
        if (this.M != null) {
            this.M.a(aVar);
            return;
        }
        if (this.N != null) {
            this.N.a(aVar);
            return;
        }
        if (this.O != null) {
            this.O.a(aVar);
            return;
        }
        if (this.P != null) {
            this.P.a(aVar);
        } else if (this.Q != null) {
            this.Q.a(aVar);
        } else if (this.T != null) {
            this.T.a(aVar);
        }
    }

    public void b(final com.meizu.cloud.pushsdk.a.d.k kVar) {
        try {
            this.E = true;
            if (!this.D) {
                if (this.Z != null) {
                    this.Z.execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.b.6
                        @Override // java.lang.Runnable
                        public void run() {
                            if (b.this.J != null) {
                                b.this.J.a(kVar);
                            }
                            b.this.p();
                        }
                    });
                } else {
                    com.meizu.cloud.pushsdk.a.b.b.a().b().c().execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.b.7
                        @Override // java.lang.Runnable
                        public void run() {
                            if (b.this.J != null) {
                                b.this.J.a(kVar);
                            }
                            b.this.p();
                        }
                    });
                }
                com.meizu.cloud.pushsdk.a.a.a.a("Delivering success : " + toString());
                return;
            }
            com.meizu.cloud.pushsdk.a.c.a aVar = new com.meizu.cloud.pushsdk.a.c.a();
            aVar.d();
            aVar.a(0);
            if (this.J != null) {
                this.J.a(aVar);
            }
            p();
            com.meizu.cloud.pushsdk.a.a.a.a("Delivering cancelled : " + toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public com.meizu.cloud.pushsdk.a.d.j q() {
        if (this.r != null) {
            if (this.y != null) {
                return com.meizu.cloud.pushsdk.a.d.j.a(this.y, this.r.toString());
            }
            return com.meizu.cloud.pushsdk.a.d.j.a(w, this.r.toString());
        }
        if (this.s != null) {
            if (this.y != null) {
                return com.meizu.cloud.pushsdk.a.d.j.a(this.y, this.s.toString());
            }
            return com.meizu.cloud.pushsdk.a.d.j.a(w, this.s.toString());
        }
        if (this.t != null) {
            if (this.y != null) {
                return com.meizu.cloud.pushsdk.a.d.j.a(this.y, this.t);
            }
            return com.meizu.cloud.pushsdk.a.d.j.a(x, this.t);
        }
        if (this.v != null) {
            if (this.y != null) {
                return com.meizu.cloud.pushsdk.a.d.j.a(this.y, this.v);
            }
            return com.meizu.cloud.pushsdk.a.d.j.a(x, this.v);
        }
        if (this.u != null) {
            if (this.y != null) {
                return com.meizu.cloud.pushsdk.a.d.j.a(this.y, this.u);
            }
            return com.meizu.cloud.pushsdk.a.d.j.a(x, this.u);
        }
        com.meizu.cloud.pushsdk.a.d.b.a aVar = new com.meizu.cloud.pushsdk.a.d.b.a();
        try {
            for (Map.Entry<String, String> entry : this.j.entrySet()) {
                aVar.a(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry2 : this.k.entrySet()) {
                aVar.b(entry2.getKey(), entry2.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aVar.a();
    }

    public com.meizu.cloud.pushsdk.a.d.j r() {
        com.meizu.cloud.pushsdk.a.d.h.a aVarA = new com.meizu.cloud.pushsdk.a.d.h.a().a(com.meizu.cloud.pushsdk.a.d.h.e);
        try {
            for (Map.Entry<String, String> entry : this.l.entrySet()) {
                aVarA.a(com.meizu.cloud.pushsdk.a.d.c.a("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), com.meizu.cloud.pushsdk.a.d.j.a((g) null, entry.getValue()));
            }
            for (Map.Entry<String, File> entry2 : this.o.entrySet()) {
                String name = entry2.getValue().getName();
                aVarA.a(com.meizu.cloud.pushsdk.a.d.c.a("Content-Disposition", "form-data; name=\"" + entry2.getKey() + "\"; filename=\"" + name + "\""), com.meizu.cloud.pushsdk.a.d.j.a(g.a(com.meizu.cloud.pushsdk.a.i.b.a(name)), entry2.getValue()));
                if (this.y != null) {
                    aVarA.a(this.y);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aVarA.a();
    }

    public com.meizu.cloud.pushsdk.a.d.c s() {
        com.meizu.cloud.pushsdk.a.d.c.a aVar = new com.meizu.cloud.pushsdk.a.d.c.a();
        try {
            for (Map.Entry<String, String> entry : this.i.entrySet()) {
                aVar.a(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aVar.a();
    }

    /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.a.a.b$b, reason: collision with other inner class name */
    public static class C0028b<T extends C0028b> {
        private int b;
        private String c;
        private Object d;
        private Bitmap.Config e;
        private int f;
        private int g;
        private ImageView.ScaleType h;
        private Executor l;
        private String m;
        private d a = d.MEDIUM;
        private HashMap<String, String> i = new HashMap<>();
        private HashMap<String, String> j = new HashMap<>();
        private HashMap<String, String> k = new HashMap<>();

        public C0028b(String str) {
            this.b = 0;
            this.c = str;
            this.b = 0;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.j.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class c<T extends c> {
        private int b;
        private String c;
        private Object d;
        private Executor o;
        private String p;
        private String q;
        private d a = d.MEDIUM;
        private JSONObject e = null;
        private JSONArray f = null;
        private String g = null;
        private byte[] h = null;
        private File i = null;
        private HashMap<String, String> j = new HashMap<>();
        private HashMap<String, String> k = new HashMap<>();
        private HashMap<String, String> l = new HashMap<>();
        private HashMap<String, String> m = new HashMap<>();
        private HashMap<String, String> n = new HashMap<>();

        public c(String str) {
            this.b = 1;
            this.c = str;
            this.b = 1;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.k.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class a<T extends a> {
        private String b;
        private Object c;
        private String g;
        private String h;
        private Executor j;
        private String k;
        private d a = d.MEDIUM;
        private HashMap<String, String> d = new HashMap<>();
        private HashMap<String, String> e = new HashMap<>();
        private HashMap<String, String> f = new HashMap<>();
        private int i = 0;

        public a(String str, String str2, String str3) {
            this.b = str;
            this.g = str2;
            this.h = str3;
        }

        public b a() {
            return new b(this);
        }
    }

    public String toString() {
        return "ANRequest{sequenceNumber='" + this.f + ", mMethod=" + this.b + ", mPriority=" + this.c + ", mRequestType=" + this.d + ", mUrl=" + this.e + '}';
    }
}
