package com.xiaocong.smarthome.loading;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.xiaocong.smarthome.dialog.TipDialog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HttpLoadingHelper {
    private Activity mActivity;
    private TipDialog mLoading;
    private TipDialog mMqttLoading;

    private static class HttpLoadingHelperHolder {
        public static final HttpLoadingHelper helperHolder = new HttpLoadingHelper();
    }

    public static HttpLoadingHelper getInstance() {
        return HttpLoadingHelperHolder.helperHolder;
    }

    public void showProcessLoading(Context context) {
        try {
            Activity activity = (Activity) context;
            if (activity != null) {
                if (activity != this.mActivity) {
                    dismissProcessLoading();
                    this.mActivity = activity;
                    this.mLoading = new TipDialog.Builder(this.mActivity).setIconType(1).setTipWord("正在加载").create();
                }
                if (this.mLoading != null && !this.mLoading.isShowing()) {
                    Log.e("XcProgressLoading", "XcProgressLoading--------");
                    this.mLoading.show();
                }
            }
        } catch (Exception e) {
            Log.e("XcProgressLoading", "Exception" + e.toString());
        }
    }

    public void showMqttProcessLoading(Context context) {
        try {
            Activity activity = (Activity) context;
            if (activity != null) {
                if (activity != this.mActivity) {
                    dismissProcessLoading();
                    this.mActivity = activity;
                    this.mMqttLoading = new TipDialog.Builder(this.mActivity).setIconType(1).setTipWord("正在加载").create();
                }
                if (this.mMqttLoading != null && !this.mMqttLoading.isShowing()) {
                    Log.e("XcProgressLoading", "XcProgressLoading--------");
                    this.mMqttLoading.show();
                }
            }
        } catch (Exception e) {
            Log.e("XcProgressLoading", "Exception" + e.toString());
        }
    }

    public void dismissProcessLoading() {
        try {
            if (this.mLoading != null && this.mLoading.isShowing()) {
                Log.e("dismissProcessLoading", "dismissProcessLoading--------");
                this.mLoading.dismiss();
                this.mActivity = null;
                this.mLoading = null;
            }
        } catch (Exception e) {
        }
    }

    public void dismissMqttProcessLoading() {
        try {
            if (this.mMqttLoading != null && this.mMqttLoading.isShowing()) {
                Log.e("dismissProcessLoading", "dismissProcessLoading--------");
                this.mMqttLoading.dismiss();
                this.mActivity = null;
                this.mMqttLoading = null;
            }
        } catch (Exception e) {
        }
    }
}
