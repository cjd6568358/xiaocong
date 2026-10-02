package com.youzan.spiderman.c.b;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.youzan.spiderman.utils.NetWorkUtil;
import com.youzan.spiderman.utils.StringUtils;

/* JADX INFO: compiled from: SyncConfig.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {

    @SerializedName("sync_interval")
    private long a;

    @SerializedName("download_condition")
    private String b;

    public long a() {
        return this.a;
    }

    public void a(long syncInterval) {
        this.a = syncInterval;
    }

    public void a(String downloadCondition) {
        this.b = downloadCondition;
    }

    public boolean a(Context context) {
        if (StringUtils.isEmpty(this.b)) {
            return false;
        }
        if (this.b.equals("all")) {
            return true;
        }
        if (this.b.equals("no")) {
            return false;
        }
        String status = NetWorkUtil.getConnectionStatus(context);
        return this.b.equals("wifi") && status.equals(NetWorkUtil.STATE_WIFI);
    }

    public boolean b() {
        return this.b.equals("no");
    }
}
