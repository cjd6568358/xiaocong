package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.MainActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.NoDoubleClickUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.login.RelevancePhoneActivity;
import com.tencent.android.tpush.XGPushManager;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.openapi.XCManager;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HttpReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra("httpReceiverCode", 0);
        String msg = intent.getStringExtra("httpReceiverMsg");
        ToastUtils.showShort(context, msg);
        if (code == 100) {
            if (!NoDoubleClickUtils.isDoubleClick()) {
                XCDeviceController.getInstance().XCDeviceControllerStop(context);
                XCManager.getInstance().logout(context);
                SpUtils.clearSp("NLC_ahe_9l", context);
                SpUtils.clearSp("xiao_cong_uid", context);
                AppSpConstans.getInstance().clearParams();
                XGPushManager.unregisterPush(context);
                Intent startIntent = new Intent(context, (Class<?>) LoginActivity.class);
                startIntent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                ActivityManagerUtil.getScreenManager().popAllActivity();
                MainActivityManagerUtil.getScreenManager().popAllActivity();
                context.startActivity(startIntent);
                return;
            }
            return;
        }
        if (code == 102) {
            Intent startIntent2 = new Intent(context, (Class<?>) RelevancePhoneActivity.class);
            startIntent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            context.startActivity(startIntent2);
            return;
        }
        if (code == 110) {
        }
    }
}
