package com.youzan.spiderman.c.a;

import com.google.gson.annotations.SerializedName;
import com.youzan.spiderman.c.b.a;
import com.youzan.spiderman.c.b.c;
import com.youzan.spiderman.c.b.d;
import com.youzan.spiderman.c.b.f;
import com.youzan.spiderman.c.b.g;
import com.youzan.spiderman.c.b.h;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: ConfigPref.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {

    @SerializedName("config_entity")
    private c a;

    public c a() {
        if (this.a == null) {
            this.a = new c();
            this.a.a(c());
            this.a.a(b());
        } else {
            a(this.a);
        }
        return this.a;
    }

    public static void a(c configEntity) {
        if (configEntity.a() == null) {
            configEntity.a(b());
        }
        com.youzan.spiderman.c.b.b content = configEntity.b();
        if (content == null) {
            configEntity.a(c());
            return;
        }
        if (content.a() == null) {
            content.a(d());
        }
        if (content.b() == null) {
            content.a(e());
        }
        if (content.c() == null) {
            content.a(f());
        }
        if (content.d() == null) {
            content.a(g());
        }
    }

    private static a b() {
        a certificate = new a();
        certificate.a(0L);
        certificate.b(0L);
        return certificate;
    }

    private static com.youzan.spiderman.c.b.b c() {
        com.youzan.spiderman.c.b.b configContent = new com.youzan.spiderman.c.b.b();
        configContent.a(d());
        configContent.a(e());
        configContent.a(f());
        configContent.a(g());
        return configContent;
    }

    private static f d() {
        f resourceConfig = new f();
        resourceConfig.a(true);
        resourceConfig.b(null);
        resourceConfig.a((List<String>) null);
        return resourceConfig;
    }

    private static g e() {
        g syncConfig = new g();
        syncConfig.a(7200000L);
        syncConfig.a("wifi");
        return syncConfig;
    }

    private static h f() {
        h uploadConfig = new h();
        uploadConfig.a(false);
        List<String> urlPattern = new ArrayList<>();
        uploadConfig.a(urlPattern);
        return uploadConfig;
    }

    private static d g() {
        d htmlConfig = new d();
        htmlConfig.a(true);
        htmlConfig.a("wifi");
        htmlConfig.a(7200000L);
        htmlConfig.b(43200000L);
        htmlConfig.a(new ArrayList());
        return htmlConfig;
    }

    public void b(c configEntity) {
        this.a = configEntity;
    }
}
