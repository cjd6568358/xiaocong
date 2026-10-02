package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class aw {
    private static aw a = new aw();
    private static ExecutorService b = Executors.newSingleThreadExecutor();
    private Context c;
    private String e;
    private Map<String, b> d = new HashMap();
    private final ArrayList<av.b> f = new ArrayList<>();

    public class a implements Runnable {
        b a;
        String b;

        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            aw.this.d.put(this.b, this.a);
            aw.this.c("Add uploader, provider is " + this.b);
        }
    }

    public interface b {
        void a(ArrayList<av.b> arrayList);

        boolean a(av.b bVar);
    }

    public class c implements Runnable {
        private Context b;

        public c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (aw.this.c != null) {
                com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: please do not init TinyDataManager repeatly.");
                return;
            }
            aw.this.c = this.b;
            aw.this.a(new av.a(this.b), "SHORT_UPLOADER_FROM_SELF");
            aw.this.c("Init");
        }
    }

    private class d implements Runnable {
        String a;

        public d(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            aw.this.c(this.a);
        }
    }

    private class f implements Runnable {
        av.b a;

        private f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            boolean z = true;
            if (this.a.c.f) {
                this.a.c.a = "push_sdk_channel";
            } else {
                this.a.c.a = aw.this.e;
            }
            this.a.a = av.a();
            b bVarC = aw.this.c();
            String str2 = null;
            boolean z2 = false;
            if (bVarC == null) {
                str2 = "uploader is null";
                z2 = true;
            }
            if (!z2 && aw.this.b()) {
                str2 = "TinyDataManager need init";
                z2 = true;
            }
            if (!z2 && this.a.c.a == null) {
                str2 = "request channel is null";
                z2 = true;
            }
            if (z2 || bVarC.a(this.a)) {
                z = z2;
                str = str2;
            } else {
                str = "uploader refuse upload";
            }
            if (z) {
                com.xiaomi.channel.commonutils.logger.b.c(this.a.toString() + " is added to pending list. Pending Reason is " + str);
                aw.this.f.add(this.a);
            } else {
                com.xiaomi.channel.commonutils.logger.b.c(this.a.toString() + " is uploaded immediately.");
                ArrayList<av.b> arrayList = new ArrayList<>();
                arrayList.add(this.a);
                bVarC.a(arrayList);
            }
        }
    }

    private aw() {
    }

    public static aw a() {
        return a;
    }

    private void a(av.b bVar) {
        f fVar = new f();
        fVar.a = bVar;
        b.execute(fVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b c() {
        b bVar = this.d.get("UPLOADER_FROM_MIPUSHCLIENT");
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = this.d.get("UPLOADER_FROM_XMPUSHSERVICE");
        if (bVar2 == null) {
            return null;
        }
        return bVar2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(String str) {
        b bVarC;
        com.xiaomi.channel.commonutils.logger.b.c("TinyDataManager is checking and uploading tiny data, reason is " + str + ", the size of pending list is " + this.f.size());
        if (b() || (bVarC = c()) == null) {
            return false;
        }
        ArrayList<av.b> arrayList = new ArrayList<>();
        for (av.b bVar : this.f) {
            if (bVar.c.a != null && bVarC.a(bVar)) {
                arrayList.add(bVar);
            }
        }
        if (arrayList.size() != 0) {
            bVarC.a(arrayList);
            for (av.b bVar2 : arrayList) {
                com.xiaomi.channel.commonutils.logger.b.c("Pending Data " + bVar2.toString() + " uploaded by TinyDataManager, reason is " + str);
                this.f.remove(bVar2);
            }
        }
        return true;
    }

    public void a(Context context) {
        if (context == null) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]:context is null, TinyDataManager.init(Context, TinyDataUploader) failed.");
            return;
        }
        c cVar = new c();
        cVar.b = context;
        b.execute(cVar);
    }

    public void a(b bVar, String str) {
        if (bVar == null) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: please do not add null uploader to TinyDataManager.");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: can not add a provider from unkown resource.");
            return;
        }
        a aVar = new a();
        aVar.b = str;
        aVar.a = bVar;
        b.execute(aVar);
    }

    public boolean a(int i, String str, String str2, long j, String str3) {
        return a(i, str, str2, j, str3, true);
    }

    public boolean a(int i, String str, String str2, long j, String str3, boolean z) {
        if (av.a(str, str2, j, str3)) {
            return false;
        }
        av.b bVar = new av.b();
        bVar.b = i;
        bVar.c.g = str;
        bVar.c.c = str2;
        bVar.c.d = j;
        bVar.c.b = str3;
        bVar.c.f = z;
        bVar.c.e = System.currentTimeMillis();
        a(bVar);
        return true;
    }

    public boolean a(String str, String str2, long j, String str3) {
        return a(0, str, str2, j, str3);
    }

    public void b(String str) {
        b.execute(new d(str));
    }

    public boolean b() {
        return this.c == null;
    }
}
