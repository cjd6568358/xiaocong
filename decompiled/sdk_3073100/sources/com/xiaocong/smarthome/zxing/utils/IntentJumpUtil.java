package com.xiaocong.smarthome.zxing.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.xiaocong.smarthome.zxing.ScanCodeActivity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class IntentJumpUtil {
    Handler handler;
    Activity mActivity;
    String zxingResult;

    public IntentJumpUtil(Activity mActivity, Handler handler) {
        this.handler = handler;
        this.mActivity = mActivity;
        if (this.handler == null) {
            this.handler = new Handler();
        }
    }

    public void handlerQcodeString(String zxingResult) {
        this.zxingResult = zxingResult;
        if (!TextUtils.isEmpty(zxingResult)) {
            Intent intent = new Intent("device.android.ScanContent");
            intent.putExtra("scanContent", zxingResult);
            LocalBroadcastManager.getInstance(this.mActivity).sendBroadcast(intent);
            finishActivity();
        }
    }

    private void finishActivity() {
        if (this.mActivity instanceof ScanCodeActivity) {
            this.mActivity.finish();
        }
    }
}
