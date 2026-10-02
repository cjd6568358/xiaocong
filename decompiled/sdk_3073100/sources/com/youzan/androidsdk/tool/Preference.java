package com.youzan.androidsdk.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Preference {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static Preference f575;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private final Bundle f576 = new Bundle();

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private SharedPreferences f577;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private SharedPreferences.Editor f578;

    private Preference() {
    }

    public static Preference instance() {
        if (f575 == null) {
            synchronized (Preference.class) {
                if (f575 == null) {
                    f575 = new Preference();
                }
            }
        }
        return f575;
    }

    public static void renew(Context context) {
        if (!instance().m83() && context != null) {
            instance().init(context);
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean m83() {
        return (this.f577 == null || this.f578 == null) ? false : true;
    }

    public void init(Context context) {
        Context appCtx = context.getApplicationContext();
        if (appCtx != null) {
            context = appCtx;
        }
        this.f577 = context.getSharedPreferences("com.youzan.open.sdk.preferences", 0);
        this.f578 = this.f577.edit();
    }

    public void remove(String key) {
        this.f576.remove(key);
        if (m83()) {
            this.f578.remove(key).commit();
        }
    }

    public String getString(String key, String defaultValue) {
        String value;
        String value2 = this.f576.getString(key, "!@INVALID!@");
        if (!"!@INVALID!@".equals(value2)) {
            return value2;
        }
        if (m83()) {
            value = this.f577.getString(key, defaultValue);
            this.f576.putString(key, value);
        } else {
            value = null;
        }
        return value;
    }

    public void setString(String key, String value) {
        if (value == null) {
            remove(key);
            return;
        }
        if (m83()) {
            this.f578.putString(key, value);
            this.f578.commit();
        }
        this.f576.putString(key, value);
    }

    public int getInt(String key, int defaultValue) {
        int value;
        int value2 = this.f576.getInt(key, Integer.MIN_VALUE);
        if (value2 != Integer.MIN_VALUE) {
            return value2;
        }
        if (m83()) {
            value = this.f577.getInt(key, defaultValue);
            this.f576.putInt(key, value);
        } else {
            value = 0;
        }
        return value;
    }

    public void setInt(String key, int value) {
        if (value == Integer.MIN_VALUE) {
            remove(key);
            return;
        }
        if (m83()) {
            this.f578.putInt(key, value);
            this.f578.commit();
        }
        this.f576.putInt(key, value);
    }

    public long getLong(String key, long defaultValue) {
        long value;
        long value2 = this.f576.getLong(key, Long.MIN_VALUE);
        if (value2 != Long.MIN_VALUE) {
            return value2;
        }
        if (m83()) {
            value = this.f577.getLong(key, defaultValue);
            this.f576.putLong(key, value);
        } else {
            value = 0;
        }
        return value;
    }

    public void setLong(String key, long value) {
        if (value == Long.MIN_VALUE) {
            remove(key);
            return;
        }
        if (m83()) {
            this.f578.putLong(key, value);
            this.f578.commit();
        }
        this.f576.putLong(key, value);
    }
}
