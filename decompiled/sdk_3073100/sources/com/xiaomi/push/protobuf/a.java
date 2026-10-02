package com.xiaomi.push.protobuf;

import com.google.protobuf.micro.c;
import com.google.protobuf.micro.e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class a {

    /* JADX INFO: renamed from: com.xiaomi.push.protobuf.a$a, reason: collision with other inner class name */
    public static final class C0011a extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private int b = 0;
        private boolean d = false;
        private int f = 0;
        private boolean h = false;
        private List<String> i = Collections.emptyList();
        private int j = -1;

        public static C0011a b(byte[] bArr) {
            return (C0011a) new C0011a().a(bArr);
        }

        public static C0011a c(com.google.protobuf.micro.b bVar) {
            return new C0011a().a(bVar);
        }

        public int a() {
            if (this.j < 0) {
                b();
            }
            return this.j;
        }

        public C0011a a(int i) {
            this.a = true;
            this.b = i;
            return this;
        }

        public C0011a a(String str) {
            if (str == null) {
                throw new NullPointerException();
            }
            if (this.i.isEmpty()) {
                this.i = new ArrayList();
            }
            this.i.add(str);
            return this;
        }

        public C0011a a(boolean z) {
            this.c = true;
            this.d = z;
            return this;
        }

        public void a(c cVar) {
            if (e()) {
                cVar.b(1, d());
            }
            if (g()) {
                cVar.a(2, f());
            }
            if (i()) {
                cVar.a(3, h());
            }
            if (k()) {
                cVar.a(4, j());
            }
            Iterator<String> it = l().iterator();
            while (it.hasNext()) {
                cVar.a(5, it.next());
            }
        }

        public int b() {
            int iB = 0;
            int iD = e() ? c.d(1, d()) + 0 : 0;
            if (g()) {
                iD += c.b(2, f());
            }
            if (i()) {
                iD += c.c(3, h());
            }
            int iB2 = k() ? iD + c.b(4, j()) : iD;
            Iterator<String> it = l().iterator();
            while (it.hasNext()) {
                iB += c.b(it.next());
            }
            int size = iB2 + iB + (l().size() * 1);
            this.j = size;
            return size;
        }

        public C0011a b(int i) {
            this.e = true;
            this.f = i;
            return this;
        }

        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public C0011a a(com.google.protobuf.micro.b bVar) {
            while (true) {
                int iA = bVar.a();
                switch (iA) {
                    case 0:
                        break;
                    case 8:
                        a(bVar.i());
                        break;
                    case 16:
                        a(bVar.f());
                        break;
                    case 24:
                        b(bVar.e());
                        break;
                    case 32:
                        b(bVar.f());
                        break;
                    case 42:
                        a(bVar.g());
                        break;
                    default:
                        if (!a(bVar, iA)) {
                        }
                        break;
                }
            }
            return this;
        }

        public C0011a b(boolean z) {
            this.g = true;
            this.h = z;
            return this;
        }

        public int d() {
            return this.b;
        }

        public boolean e() {
            return this.a;
        }

        public boolean f() {
            return this.d;
        }

        public boolean g() {
            return this.c;
        }

        public int h() {
            return this.f;
        }

        public boolean i() {
            return this.e;
        }

        public boolean j() {
            return this.h;
        }

        public boolean k() {
            return this.g;
        }

        public List<String> l() {
            return this.i;
        }

        public int m() {
            return this.i.size();
        }
    }
}
