package com.youzan.spiderman.cache;

import android.net.Uri;
import android.text.TextUtils;
import com.youzan.spiderman.utils.Stone;
import com.youzan.spiderman.utils.StringUtils;

/* JADX INFO: compiled from: CacheUrlSplicer.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class f {
    public static CacheUrl a(String path) {
        String url;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (path.startsWith("/public_files/")) {
            url = "https://b.yzcdn.cn" + path;
        } else if (path.startsWith("/upload_files/")) {
            url = "https://img.yzcdn.cn" + path;
        } else if (StringUtils.isStartWith(path, Stone.SUPPORTED_SCHEME)) {
            url = path;
        } else {
            url = "https://b.yzcdn.cn" + path;
        }
        return new CacheUrl(Uri.parse(url));
    }

    public static CacheUrl a(CacheUrl oldUrl, String rawPath) {
        if (!oldUrl.getUri().getHost().equalsIgnoreCase("b.yzcdn.cn") || StringUtils.isStartWith(rawPath, Stone.SUPPORTED_SCHEME)) {
            return null;
        }
        if (rawPath.startsWith("/public_files/")) {
            String url = "https://img.yzcdn.cn" + rawPath;
            return new CacheUrl(Uri.parse(url));
        }
        if (rawPath.startsWith("/upload_files/")) {
            return null;
        }
        String url2 = "https://su.yzcdn.cn" + rawPath;
        return new CacheUrl(Uri.parse(url2));
    }
}
