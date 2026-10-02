package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.view.SearchIcon;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApScanWifiUtils;
import com.xiaocong.smarthome.httplib.helper.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AddDeviceScanDialog extends Dialog {
    private static LoadingCountDown mLoadingCountDown;
    private CommonTypeCallback mCallback;
    private Context mContext;
    private ImageView mIvScan;
    private View mRootView;
    private SearchIcon mSearchIcon;
    private long mTimerNum;

    public AddDeviceScanDialog(Context context, int themeResId, View view, long time, CommonTypeCallback callback) {
        super(context, themeResId);
        this.mContext = context;
        this.mCallback = callback;
        this.mTimerNum = time;
        this.mRootView = view;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(8, 8);
        setContentView(R.layout.layout_add_device_scan_dialog);
        this.mSearchIcon = (SearchIcon) findViewById(R.id.si_add_dev_scan_dialog);
        this.mSearchIcon.start();
        this.mIvScan = (ImageView) findViewById(R.id.iv_add_dev_scan_dialog);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().addFlags(2);
        lp.dimAmount = 0.0f;
        lp.width = -1;
        lp.height = -1;
        getWindow().setAttributes(lp);
        setCancelable(false);
    }

    public void dismissScan() {
        if (mLoadingCountDown != null) {
            mLoadingCountDown.cancel();
            mLoadingCountDown = null;
            SoftApScanWifiUtils.stopScanDeviceAp();
        }
        dismiss();
    }

    public void startScan() {
        if (mLoadingCountDown == null) {
            mLoadingCountDown = new LoadingCountDown(this.mTimerNum, 1000L);
        }
        mLoadingCountDown.start();
    }

    private class LoadingCountDown extends CountDownTimer {
        public LoadingCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            XcLogger.w("LoadingCountDown", "onReceivePassThroughMessage--getContent = " + millisUntilFinished);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            AddDeviceScanDialog.this.mCallback.resultTypeCalllback(2);
            AddDeviceScanDialog.this.dismiss();
        }
    }
}
