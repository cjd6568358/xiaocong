package com.ixiaocong.smarthome.phone.android.complete.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ToastUtils {
    private static Handler mHandler;
    private static Toast sToast = null;

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public static void showShort(Context context, String msg) {
        if (context != null) {
            showToast(context, msg, 0);
        }
    }

    public static void showToast(final Context context, final String msg, final int duration) {
        getHandler().post(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils.1
            @Override // java.lang.Runnable
            public void run() {
                if (context != null && !TextUtils.isEmpty(msg)) {
                    if (ToastUtils.sToast == null) {
                        Toast unused = ToastUtils.sToast = Toast.makeText(context.getApplicationContext(), msg, duration);
                    }
                    ToastUtils.sToast.setText(msg);
                    ToastUtils.sToast.setDuration(duration);
                    ToastUtils.sToast.show();
                }
            }
        });
    }
}
