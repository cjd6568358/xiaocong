package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.ixiaocong.smarthome.phone.android.common.utils.MainActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.openapi.XCManager;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.xiaocong.smarthome.switchbutton.SwitchButton;
import com.xiaocong.smarthome.util.ACache;
import com.youzan.androidsdk.YouzanSDK;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemSettingActivity extends XcBaseActivity implements View.OnClickListener, HintDialogCallback {
    private static Vibrator mVibrator;
    private RelativeLayout mAboutWeLayout;
    private String mAppUpdateUrl;
    private Button mBtnExitApp;
    private Button mBtnUpdate;
    private ImageView mIvBack;
    private SwitchButton mSbtnMsg;
    private SwitchButton mSbtnShake;
    private SwitchButton mSbtnVoice;
    private TextView mTvUpdate;
    private XGCustomPushNotificationBuilder mXgBuild;

    protected int getLayoutId() {
        return R.layout.activity_system_setting;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvUpdate = (TextView) $(R.id.tv_system_setting_update);
        this.mBtnExitApp = (Button) $(R.id.btn_system_setting_exit_app);
        this.mAboutWeLayout = (RelativeLayout) $(R.id.rl_system_setting_about_we);
        this.mSbtnMsg = $(R.id.btn_system_setting_msg);
        this.mSbtnVoice = $(R.id.btn_system_setting_voice);
        this.mSbtnShake = $(R.id.btn_system_setting_shake);
        this.mBtnUpdate = (Button) $(R.id.btn_setting_firm_update);
    }

    protected void initData() {
        loadUserSetting();
        String appUpdate = XCHelp.getString("app_upgrade", Constants.MAIN_VERSION_TAG);
        this.mAppUpdateUrl = XCHelp.getString("app_downloadUrl", Constants.MAIN_VERSION_TAG);
        if ("1".equals(appUpdate) && !TextUtils.isEmpty(this.mAppUpdateUrl)) {
            this.mBtnUpdate.setVisibility(0);
            this.mTvUpdate.setVisibility(8);
        } else {
            this.mBtnUpdate.setVisibility(8);
            this.mTvUpdate.setVisibility(0);
        }
        mVibrator = (Vibrator) this.mActivity.getSystemService("vibrator");
        this.mXgBuild = new XGCustomPushNotificationBuilder();
    }

    protected void loadUserSetting() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("user/config");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                try {
                    JSONObject jsonObject = JSON.parseObject(var1.getData());
                    String status = jsonObject.getString("notification");
                    if ("1".equals(status)) {
                        SystemSettingActivity.this.mSbtnMsg.setCheckedNoEvent(true);
                    } else {
                        SystemSettingActivity.this.mSbtnMsg.setCheckedNoEvent(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SystemSettingActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void SetUserMsg(String notification) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("notification", notification);
        httpSetting.setParamsMap(params);
        httpSetting.setNeedSign(false);
        httpSetting.setPath("user/update/config");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SystemSettingActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvUpdate.setOnClickListener(this);
        this.mAboutWeLayout.setOnClickListener(this);
        this.mBtnExitApp.setOnClickListener(this);
        this.mBtnUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri url = Uri.parse(SystemSettingActivity.this.mAppUpdateUrl);
                    intent.setData(url);
                    SystemSettingActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(SystemSettingActivity.this.mActivity, "升级失败,请稍后重试!");
                }
            }
        });
        this.mSbtnMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                XcLogger.i("SystemSettingActivity", "mBtnMsg" + isChecked);
                if (isChecked) {
                    SystemSettingActivity.this.SetUserMsg("1");
                } else {
                    SystemSettingActivity.this.SetUserMsg(PushConstants.PUSH_TYPE_NOTIFY);
                }
            }
        });
        this.mSbtnShake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SystemSettingActivity.mVibrator.vibrate(500L);
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_system_setting_exit_app /* 2131296337 */:
                OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "退出登录", "确定要退出登录?");
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.rl_system_setting_about_we /* 2131296707 */:
                startActivity(new Intent(this.mActivity, (Class<?>) AboutXiaocongActivity.class));
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            userLogout();
        }
    }

    private void userLogout() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("user/logout");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity.6
            public void onComplete(XCResponseBean var1) {
                SystemSettingActivity.this.logout();
            }

            public void onError(XCErrorMessage var1) {
                SystemSettingActivity.this.logout();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void logout() {
        XCDeviceController.getInstance().XCDeviceControllerStop(this.mActivity);
        XGPushManager.unregisterPush(getApplicationContext());
        XcApplication.getDaoSession().getUserInfoDBDao().deleteAll();
        SpUtils.clearSp("NLC_ahe_9l", this.mActivity);
        SpUtils.clearSp("xiao_cong_uid", this.mActivity);
        XCManager.getInstance().logout(this.mActivity);
        AppSpConstans.getInstance().clearParams();
        YouzanSDK.userLogout(this.mActivity);
        Intent intent = new Intent(this.mActivity, (Class<?>) LoginActivity.class);
        startActivity(intent);
        ACache.get(this).clear();
        MainActivityManagerUtil.getScreenManager().popAllActivity();
        finish();
    }
}
