package com.youzan.androidsdk.tool;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Config {
    public static String getImageLoadAdapter() {
        return Preference.instance().getString("config.image_loader", null);
    }

    public static void setImageLoadAdapter(String value) {
        Preference.instance().setString("config.image_loader", value);
    }

    public static void clear(Context context) {
        Preference.renew(context);
        setImageLoadAdapter(null);
    }
}
