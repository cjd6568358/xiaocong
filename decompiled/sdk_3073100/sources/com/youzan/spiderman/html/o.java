package com.youzan.spiderman.html;

import android.net.Uri;
import com.youzan.spiderman.utils.MD5Utils;

/* JADX INFO: compiled from: HtmlUrl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class o {
    private String a;
    private Uri b;
    private String c;

    public o(String url) {
        this.a = url;
        this.b = Uri.parse(url);
        this.c = MD5Utils.getStringMd5(url);
    }

    public o(Uri uri) {
        this.a = uri.toString();
        this.b = uri;
        this.c = MD5Utils.getStringMd5(this.a);
    }

    public String a() {
        return this.a;
    }

    public Uri b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }
}
