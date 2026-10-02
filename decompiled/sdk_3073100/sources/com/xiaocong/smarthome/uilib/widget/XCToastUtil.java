package com.xiaocong.smarthome.uilib.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCToastUtil {
    private static Handler mHandler;
    private static Toast sToast = null;

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public static void showToast(final Context context, final String msg, final int duration) {
        getHandler().post(new Runnable() { // from class: com.xiaocong.smarthome.uilib.widget.XCToastUtil.1
            @Override // java.lang.Runnable
            public void run() {
                if (context != null && !TextUtils.isEmpty(msg)) {
                    if (XCToastUtil.sToast == null) {
                        Toast unused = XCToastUtil.sToast = XCToast.makeText(context.getApplicationContext(), msg, duration);
                    }
                    XCToastUtil.sToast.setText(msg);
                    XCToastUtil.sToast.setDuration(duration);
                    XCToastUtil.sToast.show();
                }
            }
        });
    }
}
