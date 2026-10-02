package com.youzan.spiderman.cache;

import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.JsonParseException;
import com.youzan.spiderman.utils.JsonUtil;
import com.youzan.spiderman.utils.Timing;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CacheStatistic {
    private int a;
    private int b;
    private int c;
    private int d;
    private Timing e;
    private boolean f;
    private String g;
    private Timer h = null;
    private TimerTask i = null;
    private boolean j = false;
    private long k;
    private StatisticCallback l;
    private boolean m;

    public interface InjectJsCallback {
        void onInject(String str);
    }

    public interface StatisticCallback {
        void onStatistic(String str, Map<String, String> map);
    }

    public CacheStatistic(StatisticCallback statCallback) {
        this.l = statCallback;
        reset();
    }

    public void reset() {
        if (this.h != null) {
            try {
                this.h.cancel();
            } catch (Exception e) {
            }
            this.h = null;
        }
        if (this.i != null) {
            this.j = true;
            try {
                this.i.run();
            } catch (Exception e2) {
            }
            this.i = null;
        }
        this.j = false;
        this.a = 0;
        this.b = 0;
        this.d = 0;
        this.c = 0;
        this.e = null;
        this.g = null;
        this.f = false;
        this.k = 0L;
        this.m = false;
    }

    public void resetStatisticTimer() {
        if (this.f && !this.m) {
            if (this.h == null || this.i == null) {
                this.h = new Timer();
                this.i = new TimerTask() { // from class: com.youzan.spiderman.cache.CacheStatistic.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        if (CacheStatistic.this.l != null) {
                            long now = System.currentTimeMillis();
                            if (now - CacheStatistic.this.k >= 2000 || CacheStatistic.this.j) {
                                if (!CacheStatistic.this.m) {
                                    CacheStatistic.this.m = true;
                                    CacheStatistic.this.l.onStatistic(CacheStatistic.this.g, CacheStatistic.this.getStatisticData());
                                }
                                if (CacheStatistic.this.h != null) {
                                    CacheStatistic.this.h.cancel();
                                    CacheStatistic.this.h = null;
                                    CacheStatistic.this.i = null;
                                }
                            }
                        }
                    }
                };
                try {
                    this.h.schedule(this.i, 2000L, 2000L);
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            this.k = System.currentTimeMillis();
        }
    }

    public static boolean isStatisticUrl(Uri uri) {
        return uri.getScheme().equals("spiderman");
    }

    public static boolean isStatisticUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.trim().startsWith("spiderman")) {
            return false;
        }
        Uri uri = Uri.parse(url);
        return isStatisticUrl(uri);
    }

    public void tryInjectJs(String curUrl, InjectJsCallback injectCB) {
        if (injectCB != null) {
            this.g = curUrl;
            injectCB.onInject("javascript:var loadIsListened = false;if(typeof window.addEventListener!='undefined'){window.addEventListener('load', function(){loadIsListened = true;var timing = JSON.stringify(window.performance.timing);var result = prompt('spiderman://callback?timing='+timing);})}if (!loadIsListened && document.readyState === 'complete') {var result = prompt('spiderman://callback?timing='+JSON.stringify(window.performance.timing));}");
        }
    }

    public void addStatisticCount(int count, boolean isSuccess) {
        if (isSuccess) {
            this.c += count;
            if (!this.f) {
                this.a += count;
                return;
            }
            return;
        }
        this.d += count;
        if (!this.f) {
            this.b += count;
        }
    }

    public void parseStatisticTiming(Uri uri) {
        this.f = true;
        String timing = uri.getQueryParameter("timing");
        try {
            this.e = (Timing) JsonUtil.fromJson(timing, Timing.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
    }

    public void parseStatisticTiming(String url) {
        parseStatisticTiming(Uri.parse(url));
    }

    public Map<String, String> getStatisticData() {
        Map<String, String> kvMap = new HashMap<>();
        kvMap.put("url", this.g);
        int loadCount = this.a + this.b;
        if (loadCount != 0) {
            double loadHitRate = ((double) this.a) / ((double) loadCount);
            kvMap.put("load_hit_rate", String.format(Locale.getDefault(), "%.2f", Double.valueOf(loadHitRate)));
            kvMap.put("load_hit_count", String.valueOf(this.a));
            kvMap.put("load_miss_count", String.valueOf(this.b));
        }
        int allCount = this.c + this.d;
        if (allCount != 0) {
            double hitRate = ((double) this.c) / ((double) allCount);
            kvMap.put("hit_rate", String.format(Locale.getDefault(), "%.2f", Double.valueOf(hitRate)));
            kvMap.put("hit_count", String.valueOf(this.c));
            kvMap.put("miss_count", String.valueOf(this.d));
        }
        if (this.e != null) {
            long whiteTime = this.e.responseStart - this.e.navigationStart;
            long loadTime = this.e.loadEventStart - this.e.navigationStart;
            kvMap.put("white_time", String.valueOf(whiteTime));
            kvMap.put("load_time", String.valueOf(loadTime));
        }
        return kvMap;
    }
}
