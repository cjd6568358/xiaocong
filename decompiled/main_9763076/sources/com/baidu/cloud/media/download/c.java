package com.baidu.cloud.media.download;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    String a;
    String b;
    a c;

    public static abstract class a {
        public abstract void a(int i);
    }

    public c(String str, String str2, a aVar) {
        this.a = str;
        this.b = str2;
        this.c = aVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        this.c.a(com.baidu.cloud.media.download.a.a(this.a, this.b));
    }
}
