package com.ixiaocong.smarthome.phone.android.detail.activity.loading;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity;
import com.ixiaocong.smarthome.phone.android.event.service.XgTokenService;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.utils.NetworkUtils;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.openapi.XCManager;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.util.log.XCLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LoadingActivity extends XcBaseActivity {
    private long inTime;
    private Runnable runnable;
    private long needShowTime = 1000;
    private Handler handler = new Handler();

    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    protected void initView() {
    }

    protected void initData() {
        requestPhoneState();
    }

    private void requestClient() {
        this.inTime = System.currentTimeMillis();
        jumpToOtherActivity(LoadingActivity$$Lambda$1.lambdaFactory$(this));
    }

    private void requestPhoneState() {
        if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.READ_PHONE_STATE") != 0 || ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_COARSE_LOCATION") != 0 || (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.READ_EXTERNAL_STORAGE") != 0 && ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)) {
            ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
            return;
        }
        initialWithAppId();
        if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
            XgTokenService.actionStart(this.mActivity);
            requestClient();
        } else {
            ToastUtils.showShort(this.mActivity, "网络未连接,请联网后重试");
            lambda$requestClient$0();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: resultManage, reason: merged with bridge method [inline-methods] */
    public void lambda$requestClient$0() {
        if (XcApplication.getInstance().isLogin(this.mActivity)) {
            startActivity(new Intent(this.mActivity, (Class<?>) MainFragmentActivity.class));
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
        } else {
            startActivity(new Intent(this.mActivity, (Class<?>) LoginActivity.class));
        }
        finish();
    }

    private void jumpToOtherActivity(Runnable runnable) {
        long hasShowTime = System.currentTimeMillis() - this.inTime;
        long remainTime = this.needShowTime - hasShowTime;
        Handler handler = this.handler;
        this.runnable = runnable;
        if (remainTime <= 0) {
            remainTime = 0;
        }
        handler.postDelayed(runnable, remainTime);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] != 0 || grantResults[1] != 0 || grantResults[2] != 0 || grantResults[3] != 0 || grantResults[4] != 0) {
                ToastUtils.showShort(this.mActivity, "请授权app获取手机相关权限");
            }
            initialWithAppId();
            if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
                XgTokenService.actionStart(this.mActivity);
                requestClient();
            } else {
                ToastUtils.showShort(this.mActivity, "网络未连接,请联网后重试");
                lambda$requestClient$0();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initialWithAppId() {
        XCManager.getInstance().initialWithAppId(this.mActivity, "27e33ff8a0634cc4b6f696c9872a2d1b", "3wruosO36ZzBXVM8", new XCDataCallback<String>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.loading.LoadingActivity.1
            public void onComplete(String var1) {
                XCLog.i("initialWithAppId", "初始化结果:" + var1);
                if (!TextUtils.isEmpty(AppSpConstans.getInstance().getToken(LoadingActivity.this.mActivity))) {
                    XCManager.getInstance().loginWithToken(AppSpConstans.getInstance().getToken(LoadingActivity.this.mActivity));
                }
                if (!TextUtils.isEmpty(var1)) {
                    SpUtils.saveToLocal(LoadingActivity.this.getApplicationContext(), "xiao_cong_uid", "xiao_cong_uid", var1);
                }
            }

            public void onError(XCErrorMessage var1) {
                XCLog.i("initialWithAppId", var1.getErrorMessage());
                if (!TextUtils.isEmpty(AppSpConstans.getInstance().getToken(LoadingActivity.this.mActivity))) {
                    XCManager.getInstance().loginWithToken(AppSpConstans.getInstance().getToken(LoadingActivity.this.mActivity));
                }
            }
        });
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
