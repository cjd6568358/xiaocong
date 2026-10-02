package com.xiaomi.channel.commonutils.stats;

import java.util.LinkedList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private LinkedList<C0006a> a = new LinkedList<>();

    /* JADX INFO: renamed from: com.xiaomi.channel.commonutils.stats.a$a, reason: collision with other inner class name */
    public static class C0006a {
        private static final a d = new a();
        public int a;
        public String b;
        public Object c;

        C0006a(int i, Object obj) {
            this.a = i;
            this.c = obj;
        }
    }

    public static a a() {
        return C0006a.d;
    }

    private void d() {
        if (this.a.size() > 100) {
            this.a.removeFirst();
        }
    }

    public synchronized void a(Object obj) {
        this.a.add(new C0006a(0, obj));
        d();
    }

    public synchronized int b() {
        return this.a.size();
    }

    public synchronized LinkedList<C0006a> c() {
        LinkedList<C0006a> linkedList;
        linkedList = this.a;
        this.a = new LinkedList<>();
        return linkedList;
    }
}
