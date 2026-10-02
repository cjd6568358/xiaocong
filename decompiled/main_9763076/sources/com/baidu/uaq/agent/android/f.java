package com.baidu.uaq.agent.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

/* JADX INFO: compiled from: SavedState.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private long B;
    private long C;
    private long dataReportPeriod;
    private final SharedPreferences x;
    private SharedPreferences.Editor y;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();
    private final Lock z = new ReentrantLock();
    private com.baidu.uaq.agent.android.harvest.bean.b A = new com.baidu.uaq.agent.android.harvest.bean.b();

    @SuppressLint({"CommitPrefEdits"})
    public f(Context context) {
        this.x = context.getSharedPreferences("com.baidu.uaq.android.agent.v2_" + context.getPackageName(), 0);
        j();
        k();
        l();
        m();
    }

    private void j() {
        com.baidu.uaq.agent.android.harvest.bean.b tmp = n();
        if (tmp != null) {
            this.A.g(tmp.af());
            this.A.h(tmp.ag());
        }
    }

    public void k() {
        if (has("dataReportPeriod")) {
            this.dataReportPeriod = o();
        }
    }

    public void l() {
        if (!has("dataReportLimit")) {
            d(AGENT.getConfig().getDataReportLimit());
        }
        this.B = p();
    }

    private void m() {
        if (!has("lastUpdateTimestamp")) {
            e(System.currentTimeMillis());
        }
        this.C = q();
    }

    private com.baidu.uaq.agent.android.harvest.bean.b n() {
        int[] dataToken = new int[2];
        String dataTokenString = getString("dataToken");
        if (dataTokenString == null) {
            return null;
        }
        try {
            JSONTokener tokener = new JSONTokener(dataTokenString);
            JSONArray array = (JSONArray) tokener.nextValue();
            if (array == null) {
                return null;
            }
            dataToken[0] = array.getInt(0);
            dataToken[1] = array.getInt(1);
        } catch (JSONException e) {
            LOG.a("Caught error while getDataToken: ", e);
        }
        return new com.baidu.uaq.agent.android.harvest.bean.b(dataToken[0], dataToken[1]);
    }

    private long o() {
        return getLong("dataReportPeriod");
    }

    private long p() {
        return getLong("dataReportLimit");
    }

    private long q() {
        return getLong("lastUpdateTimestamp");
    }

    public long getDataReportPeriod() {
        return this.dataReportPeriod;
    }

    public void c(long dataReportPeriod) {
        LOG.E("!! saving dataReportPeriod: " + dataReportPeriod);
        a("dataReportPeriod", dataReportPeriod);
    }

    public void d(long dataReportLimit) {
        LOG.E("!! saving dataReportLimit: " + dataReportLimit);
        a("dataReportLimit", dataReportLimit);
    }

    public void e(long timestamp) {
        LOG.E("!! saving lastUpdateTimestamp: " + timestamp);
        a("lastUpdateTimestamp", timestamp);
    }

    public String v() {
        return getString("appToken");
    }

    public void b(String appToken) {
        a("appToken", appToken);
    }

    private boolean has(String key) {
        return this.x.contains(key);
    }

    private void a(String key, String value) {
        this.z.lock();
        try {
            if (this.y == null) {
                this.y = this.x.edit();
            }
            this.y.putString(key, value);
            this.y.apply();
        } catch (Exception e) {
            LOG.a("Caught error while SavedState save: ", e);
        } finally {
            this.z.unlock();
        }
    }

    private void a(String key, long value) {
        this.z.lock();
        try {
            if (this.y == null) {
                this.y = this.x.edit();
            }
            this.y.putLong(key, value);
            this.y.apply();
        } catch (Exception e) {
            LOG.a("Caught error while SavedState save: ", e);
        } finally {
            this.z.unlock();
        }
    }

    private String getString(String key) {
        return this.x.getString(key, null);
    }

    private long getLong(String key) {
        return this.x.getLong(key, 0L);
    }

    public void clear() {
        this.z.lock();
        try {
            if (this.y == null) {
                this.y = this.x.edit();
            }
            this.y.clear();
            this.y.apply();
        } catch (Exception e) {
            LOG.a("Caught error while clear SavedState: ", e);
        } finally {
            this.z.unlock();
        }
    }
}
