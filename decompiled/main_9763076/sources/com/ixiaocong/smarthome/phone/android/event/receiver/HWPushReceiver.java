package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import com.huawei.hms.support.api.push.PushReceiver;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import java.io.FileWriter;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HWPushReceiver extends PushReceiver {
    private String HW_TAG = "HWPushReceiver";

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onEvent(Context context, PushReceiver.Event arg1, Bundle arg2) {
        super.onEvent(context, arg1, arg2);
        XcLogger.w(this.HW_TAG, "onEvent = " + arg1 + ", Bundle = " + arg2);
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public boolean onPushMsg(Context context, byte[] arg1, Bundle arg2) {
        XcLogger.w(this.HW_TAG, "onPushMsg = " + new String(arg1) + ", Bundle = " + arg2);
        return super.onPushMsg(context, arg1, arg2);
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onPushMsg(Context context, byte[] arg1, String arg2) {
        XcLogger.w(this.HW_TAG, "onPushMsg = " + new String(arg1) + " ,arg2 = " + arg2);
        super.onPushMsg(context, arg1, arg2);
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onPushState(Context context, boolean arg1) {
        XcLogger.w(this.HW_TAG, "onPushState = " + arg1);
        super.onPushState(context, arg1);
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onToken(Context context, String arg1, Bundle arg2) {
        super.onToken(context, arg1, arg2);
        XcLogger.w(this.HW_TAG, " onToken = " + arg1 + ",bundke = " + arg2);
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onToken(Context context, String arg1) {
        super.onToken(context, arg1);
        XcLogger.w(this.HW_TAG, " onToken = " + arg1);
    }

    private void writeToFile(String conrent) {
        String SDPATH = Environment.getExternalStorageDirectory() + "/huawei.txt";
        try {
            FileWriter fileWriter = new FileWriter(SDPATH, true);
            fileWriter.write(conrent + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
