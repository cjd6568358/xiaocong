package com.youzan.spiderman.html;

import android.content.Context;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.youzan.spiderman.utils.NetWorkUtil;
import com.youzan.spiderman.utils.StringUtils;
import java.util.List;

/* JADX INFO: compiled from: HtmlConfigJudge.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class h {
    private Context a;
    private HtmlCacheStrategy b;
    private com.youzan.spiderman.c.b.d c;

    public h(Context context, HtmlCacheStrategy htmlCacheStrategy, com.youzan.spiderman.c.b.d htmlConfig) {
        this.a = context.getApplicationContext();
        this.b = htmlCacheStrategy;
        this.c = htmlConfig;
    }

    public boolean a() {
        Boolean htmlCacheEnable;
        return (this.b == null || (htmlCacheEnable = this.b.a()) == null) ? this.c.a() : htmlCacheEnable.booleanValue();
    }

    public long b() {
        Long htmlCacheValid;
        return (this.b == null || (htmlCacheValid = this.b.b()) == null) ? this.c.d() : htmlCacheValid.longValue();
    }

    public List<String> c() {
        return this.c.e();
    }

    public long d() {
        return this.c.b();
    }

    private String f() {
        return this.c.c();
    }

    public boolean e() {
        String condition = f();
        if (StringUtils.isEmpty(condition)) {
            return false;
        }
        if (condition.equals("all")) {
            return true;
        }
        if (condition.equals("no")) {
            return false;
        }
        String status = NetWorkUtil.getConnectionStatus(this.a);
        return condition.equals(NetworkUtils.NETWORKTYPE_WIFI) && status.equals(NetWorkUtil.STATE_WIFI);
    }

    public boolean a(i htmlData) {
        long fetchTime = htmlData.a();
        long current = System.currentTimeMillis();
        return current - fetchTime <= b();
    }

    public boolean a(long lastFetchTime) {
        long current = System.currentTimeMillis();
        return current - lastFetchTime > d();
    }

    public boolean b(long lastFetchTime) {
        return a() && e() && a(lastFetchTime);
    }
}
