package com.meizu.cloud.pushsdk.a.f;

import com.meizu.cloud.pushsdk.a.d.k;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c implements Runnable {
    public final int a;
    public final com.meizu.cloud.pushsdk.a.a.b b;
    private final com.meizu.cloud.pushsdk.a.a.d c;

    public c(com.meizu.cloud.pushsdk.a.a.b bVar) {
        this.b = bVar;
        this.a = bVar.f();
        this.c = bVar.d();
    }

    @Override // java.lang.Runnable
    public void run() {
        com.meizu.cloud.pushsdk.a.a.a.a("execution started : " + this.b.toString());
        switch (this.b.h()) {
            case 0:
                b();
                break;
            case 1:
                c();
                break;
            case 2:
                d();
                break;
        }
        com.meizu.cloud.pushsdk.a.a.a.a("execution done : " + this.b.toString());
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x008c -> B:28:0x001c). Please report as a decompilation issue!!! */
    private void b() {
        k kVarA = null;
        try {
            try {
                kVarA = b.a(this.b);
                if (kVarA == null) {
                    a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a()));
                    com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
                } else if (this.b.g() == com.meizu.cloud.pushsdk.a.a.e.OK_HTTP_RESPONSE) {
                    this.b.b(kVarA);
                    com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
                } else if (kVarA.a() >= 400) {
                    a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(kVarA), this.b, kVarA.a()));
                    com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
                } else {
                    com.meizu.cloud.pushsdk.a.a.c cVarA = this.b.a(kVarA);
                    if (cVarA.b()) {
                        cVarA.a(kVarA);
                        this.b.a(cVarA);
                        com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
                    } else {
                        a(this.b, cVarA.c());
                        com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
                    }
                }
            } catch (Exception e) {
                a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(e)));
                com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
            }
        } catch (Throwable th) {
            com.meizu.cloud.pushsdk.a.i.a.a(kVarA, this.b);
            throw th;
        }
    }

    private void c() {
        try {
            k kVarB = b.b(this.b);
            if (kVarB == null) {
                a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a()));
            } else if (kVarB.a() >= 400) {
                a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(kVarB), this.b, kVarB.a()));
            } else {
                this.b.j();
            }
        } catch (Exception e) {
            a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(e)));
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x008c -> B:28:0x001c). Please report as a decompilation issue!!! */
    private void d() {
        k kVarC = null;
        try {
            try {
                kVarC = b.c(this.b);
                if (kVarC == null) {
                    a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a()));
                    com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
                } else if (this.b.g() == com.meizu.cloud.pushsdk.a.a.e.OK_HTTP_RESPONSE) {
                    this.b.b(kVarC);
                    com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
                } else if (kVarC.a() >= 400) {
                    a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(kVarC), this.b, kVarC.a()));
                    com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
                } else {
                    com.meizu.cloud.pushsdk.a.a.c cVarA = this.b.a(kVarC);
                    if (cVarA.b()) {
                        cVarA.a(kVarC);
                        this.b.a(cVarA);
                        com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
                    } else {
                        a(this.b, cVarA.c());
                        com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
                    }
                }
            } catch (Exception e) {
                a(this.b, com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(e)));
                com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
            }
        } catch (Throwable th) {
            com.meizu.cloud.pushsdk.a.i.a.a(kVarC, this.b);
            throw th;
        }
    }

    public com.meizu.cloud.pushsdk.a.a.d a() {
        return this.c;
    }

    private void a(final com.meizu.cloud.pushsdk.a.a.b bVar, final com.meizu.cloud.pushsdk.a.c.a aVar) {
        com.meizu.cloud.pushsdk.a.b.b.a().b().c().execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.f.c.1
            @Override // java.lang.Runnable
            public void run() {
                bVar.b(aVar);
                bVar.p();
            }
        });
    }
}
