package com.meizu.cloud.pushsdk.a.f;

import com.meizu.cloud.pushsdk.a.d.k;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class e {
    public static <T> com.meizu.cloud.pushsdk.a.a.c<T> a(com.meizu.cloud.pushsdk.a.a.b bVar) {
        switch (bVar.h()) {
            case 0:
                return b(bVar);
            case 1:
                return c(bVar);
            case 2:
                return d(bVar);
            default:
                return new com.meizu.cloud.pushsdk.a.a.c<>(new com.meizu.cloud.pushsdk.a.c.a());
        }
    }

    private static <T> com.meizu.cloud.pushsdk.a.a.c<T> b(com.meizu.cloud.pushsdk.a.a.b bVar) throws Throwable {
        k kVar;
        Exception exc;
        com.meizu.cloud.pushsdk.a.c.a aVar;
        com.meizu.cloud.pushsdk.a.a.c<T> cVar;
        k kVar2 = null;
        try {
            try {
                try {
                    k kVarA = b.a(bVar);
                    try {
                        if (kVarA == null) {
                            cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a()));
                            com.meizu.cloud.pushsdk.a.i.a.a(kVarA, bVar);
                        } else if (bVar.g() == com.meizu.cloud.pushsdk.a.a.e.OK_HTTP_RESPONSE) {
                            cVar = new com.meizu.cloud.pushsdk.a.a.c<>(kVarA);
                            cVar.a(kVarA);
                            com.meizu.cloud.pushsdk.a.i.a.a(kVarA, bVar);
                        } else if (kVarA.a() >= 400) {
                            cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(kVarA), bVar, kVarA.a()));
                            cVar.a(kVarA);
                            com.meizu.cloud.pushsdk.a.i.a.a(kVarA, bVar);
                        } else {
                            cVar = bVar.a(kVarA);
                            cVar.a(kVarA);
                            com.meizu.cloud.pushsdk.a.i.a.a(kVarA, bVar);
                        }
                    } catch (com.meizu.cloud.pushsdk.a.c.a e) {
                        kVar = kVarA;
                        aVar = e;
                        cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(aVar)));
                        com.meizu.cloud.pushsdk.a.i.a.a(kVar, bVar);
                    } catch (Exception e2) {
                        kVar = kVarA;
                        exc = e2;
                        cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(exc));
                        com.meizu.cloud.pushsdk.a.i.a.a(kVar, bVar);
                    }
                } catch (Throwable th) {
                    th = th;
                    kVar2 = kVar;
                    com.meizu.cloud.pushsdk.a.i.a.a(kVar2, bVar);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                com.meizu.cloud.pushsdk.a.i.a.a(kVar2, bVar);
                throw th;
            }
        } catch (com.meizu.cloud.pushsdk.a.c.a e3) {
            kVar = null;
            aVar = e3;
        } catch (Exception e4) {
            kVar = null;
            exc = e4;
        }
        return cVar;
    }

    private static <T> com.meizu.cloud.pushsdk.a.a.c<T> c(com.meizu.cloud.pushsdk.a.a.b bVar) {
        com.meizu.cloud.pushsdk.a.a.c<T> cVar;
        try {
            k kVarB = b.b(bVar);
            if (kVarB == null) {
                cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a()));
            } else if (kVarB.a() >= 400) {
                cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(kVarB), bVar, kVarB.a()));
                cVar.a(kVarB);
            } else {
                cVar = new com.meizu.cloud.pushsdk.a.a.c<>("success");
                cVar.a(kVarB);
            }
            return cVar;
        } catch (com.meizu.cloud.pushsdk.a.c.a e) {
            return new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a(e)));
        } catch (Exception e2) {
            return new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(e2));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.meizu.cloud.pushsdk.a.c.a] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.meizu.cloud.pushsdk.a.d.k, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.meizu.cloud.pushsdk.a.d.k] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.meizu.cloud.pushsdk.a.c.a] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.meizu.cloud.pushsdk.a.d.k] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.meizu.cloud.pushsdk.a.d.k] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.meizu.cloud.pushsdk.a.a.b] */
    private static <T> com.meizu.cloud.pushsdk.a.a.c<T> d(com.meizu.cloud.pushsdk.a.a.b bVar) throws Throwable {
        ?? r2;
        Exception exc;
        ?? r3;
        com.meizu.cloud.pushsdk.a.c.a aVar;
        com.meizu.cloud.pushsdk.a.a.c<T> cVar;
        ?? A;
        ?? r1 = 0;
        try {
            try {
                try {
                    A = b.c(bVar);
                    try {
                        if (A == 0) {
                            cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a()));
                            com.meizu.cloud.pushsdk.a.i.a.a(A, bVar);
                        } else if (bVar.g() == com.meizu.cloud.pushsdk.a.a.e.OK_HTTP_RESPONSE) {
                            cVar = new com.meizu.cloud.pushsdk.a.a.c<>(A);
                            cVar.a((k) A);
                            com.meizu.cloud.pushsdk.a.i.a.a(A, bVar);
                        } else if (A.a() >= 400) {
                            cVar = new com.meizu.cloud.pushsdk.a.a.c<>(com.meizu.cloud.pushsdk.a.i.b.a(new com.meizu.cloud.pushsdk.a.c.a((k) A), (com.meizu.cloud.pushsdk.a.a.b) bVar, A.a()));
                            cVar.a((k) A);
                            com.meizu.cloud.pushsdk.a.i.a.a(A, bVar);
                        } else {
                            cVar = bVar.a(A);
                            cVar.a((k) A);
                            com.meizu.cloud.pushsdk.a.i.a.a(A, bVar);
                        }
                    } catch (com.meizu.cloud.pushsdk.a.c.a e) {
                        r3 = A;
                        aVar = e;
                        A = com.meizu.cloud.pushsdk.a.i.b.a(aVar);
                        cVar = new com.meizu.cloud.pushsdk.a.a.c<>((com.meizu.cloud.pushsdk.a.c.a) A);
                        com.meizu.cloud.pushsdk.a.i.a.a(r3, bVar);
                    } catch (Exception e2) {
                        r2 = A;
                        exc = e2;
                        A = com.meizu.cloud.pushsdk.a.i.b.a(exc);
                        cVar = new com.meizu.cloud.pushsdk.a.a.c<>((com.meizu.cloud.pushsdk.a.c.a) A);
                        com.meizu.cloud.pushsdk.a.i.a.a(r2, bVar);
                    }
                } catch (Throwable th) {
                    th = th;
                    com.meizu.cloud.pushsdk.a.i.a.a(r1, bVar);
                    throw th;
                }
            } catch (com.meizu.cloud.pushsdk.a.c.a e3) {
                r3 = 0;
                aVar = e3;
                A = com.meizu.cloud.pushsdk.a.i.b.a(aVar);
                cVar = new com.meizu.cloud.pushsdk.a.a.c<>((com.meizu.cloud.pushsdk.a.c.a) A);
                com.meizu.cloud.pushsdk.a.i.a.a(r3, bVar);
            } catch (Exception e4) {
                r2 = 0;
                exc = e4;
                A = com.meizu.cloud.pushsdk.a.i.b.a(exc);
                cVar = new com.meizu.cloud.pushsdk.a.a.c<>((com.meizu.cloud.pushsdk.a.c.a) A);
                com.meizu.cloud.pushsdk.a.i.a.a(r2, bVar);
            }
            return cVar;
        } catch (Throwable th2) {
            th = th2;
            r1 = r3;
        }
    }
}
