package com.meizu.cloud.pushsdk.b.c;

import com.meizu.cloud.pushsdk.b.a.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends com.meizu.cloud.pushsdk.b.c.a {
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;

    public static abstract class a<T extends a<T>> extends com.meizu.cloud.pushsdk.b.c.a.AbstractC0031a<T> {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;

        public T a(String str) {
            this.a = str;
            return a();
        }

        public T b(String str) {
            this.b = str;
            return a();
        }

        public T c(String str) {
            this.c = str;
            return a();
        }

        public T d(String str) {
            this.d = str;
            return a();
        }

        public T e(String str) {
            this.e = str;
            return a();
        }

        public T f(String str) {
            this.f = str;
            return a();
        }

        public T g(String str) {
            this.g = str;
            return a();
        }

        public b b() {
            return new b(this);
        }
    }

    /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.b.c.b$b, reason: collision with other inner class name */
    private static class C0032b extends a<C0032b> {
        private C0032b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.meizu.cloud.pushsdk.b.c.a.AbstractC0031a
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public C0032b a() {
            return this;
        }
    }

    public static a<?> d() {
        return new C0032b();
    }

    protected b(a<?> aVar) {
        super(aVar);
        this.e = ((a) aVar).b;
        this.f = ((a) aVar).c;
        this.d = ((a) aVar).a;
        this.g = ((a) aVar).d;
        this.h = ((a) aVar).e;
        this.i = ((a) aVar).f;
        this.j = ((a) aVar).g;
    }

    public c e() {
        c cVar = new c();
        cVar.a("event_name", this.d);
        cVar.a(PushConstants.TASK_ID, this.e);
        cVar.a("device_id", this.f);
        cVar.a("pushsdk_version", this.g);
        cVar.a("package_name", this.h);
        cVar.a("seq_id", this.i);
        cVar.a("message_seq", this.j);
        return a(cVar);
    }
}
