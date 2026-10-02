package com.youzan.spiderman.cache;

import android.net.Uri;
import com.youzan.spiderman.utils.MD5Utils;
import com.youzan.spiderman.utils.UriUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CacheUrl {
    private Uri a;
    private String b;
    private String c;

    public CacheUrl(Uri uri) {
        this.a = uri;
        this.b = UriUtil.getUriExtend(uri);
        this.c = a(uri);
    }

    public Uri getUri() {
        return this.a;
    }

    public String getExtend() {
        return this.b;
    }

    public String getMd5() {
        return this.c;
    }

    private String a(Uri uri) {
        Uri.Builder builder = new Uri.Builder();
        builder.path(uri.getPath());
        if (!b(uri)) {
            builder.query(uri.getQuery()).fragment(uri.getFragment());
        }
        return MD5Utils.getStringMd5(builder.toString());
    }

    public boolean isScript() {
        return UriUtil.isScript(this.b);
    }

    public boolean isImg() {
        return UriUtil.isImg(this.b);
    }

    private boolean a(String lastSegment, int bit) {
        String[] segmentSplit = lastSegment.split("_");
        String tag = segmentSplit[segmentSplit.length - 1];
        return tag.length() == bit;
    }

    private boolean b(Uri uri) {
        if (this.b.equals("css") && a(uri.getLastPathSegment(), 32)) {
            return true;
        }
        return this.b.equals("js") && a(uri.getLastPathSegment(), 10);
    }
}
