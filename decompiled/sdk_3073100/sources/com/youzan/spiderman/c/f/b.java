package com.youzan.spiderman.c.f;

import com.youzan.spiderman.cache.SpiderCacheCallback;
import com.youzan.spiderman.cache.SpiderMan;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.StringUtils;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: compiled from: TokenHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    private static b a = null;
    private List<a> b = new LinkedList();

    public static b a() {
        if (a == null) {
            a = new b();
        }
        return a;
    }

    private b() {
    }

    public void a(String token) {
        if (!StringUtils.isEmpty(token) && !token.trim().isEmpty()) {
            for (a callBack : this.b) {
                try {
                    callBack.a(token);
                } catch (Exception e) {
                    Logger.e("TokenHelper", "sync token, exception", e);
                }
            }
            this.b.clear();
        }
    }

    public void a(a callBack) {
        SpiderCacheCallback spiderCacheCallback = SpiderMan.getInstance().getSpiderCacheCallback();
        if (spiderCacheCallback != null) {
            String token = spiderCacheCallback.onTokenNeeded();
            if (callBack != null) {
                if (!StringUtils.isEmpty(token) && !token.trim().isEmpty()) {
                    callBack.a(token);
                    return;
                } else {
                    this.b.add(callBack);
                    return;
                }
            }
            return;
        }
        Logger.e("TokenHelper", "SpiderCacheCallback should be offered to return token", new Object[0]);
    }

    public void a(String preToken, a callBack) {
        SpiderCacheCallback spiderCacheCallback = SpiderMan.getInstance().getSpiderCacheCallback();
        if (spiderCacheCallback != null) {
            String token = spiderCacheCallback.onTokenInactive(preToken);
            if (callBack != null) {
                if (!StringUtils.isEmpty(token) && !token.trim().isEmpty() && !token.equals(preToken)) {
                    callBack.a(token);
                    return;
                } else {
                    this.b.add(callBack);
                    return;
                }
            }
            return;
        }
        Logger.e("TokenHelper", "SpiderCacheCallback should be offered to return token", new Object[0]);
    }

    public boolean a(int code) {
        return code == 40009 || code == 40010 || code == 42000;
    }
}
