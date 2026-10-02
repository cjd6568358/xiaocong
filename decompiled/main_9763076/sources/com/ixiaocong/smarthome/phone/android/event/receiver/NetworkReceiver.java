package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.NetworkUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NetworkReceiver extends BroadcastReceiver {
    private boolean isConnect = true;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && !SoftApStage.getInstance().isStartStage()) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                if (!this.isConnect && !SoftApStage.getInstance().isStartStage()) {
                    ToastUtils.showShort(context, "网络已连接");
                }
            } else if (this.isConnect && !SoftApStage.getInstance().isStartStage()) {
                ToastUtils.showShort(context, "当前网络已断开");
                XcLogger.e("NetworkReceiver", "当前网络已断开");
            }
            this.isConnect = NetworkUtils.isNetworkAvailable(context);
        }
    }
}
