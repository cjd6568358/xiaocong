package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.MainActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.service.XgTokenService;
import com.ixiaocong.smarthome.phone.android.helper.db.RNVersionDBHelper;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.utils.NetworkUtils;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.openapi.XCManager;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.youzan.androidsdk.YouzanSDK;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FeedbackActivity extends XcBaseActivity implements View.OnClickListener, EditDialogCallback {
    private Button mBtnConfirm;
    private CharSequence mCharSequence;
    private EditText mEtFeedbackText;
    private ImageView mIvBack;
    private TextView mTvFeedbackPhone;

    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mEtFeedbackText = (EditText) $(R.id.et_feedback_text);
        this.mBtnConfirm = (Button) $(R.id.btn_feedback_confirm);
        this.mTvFeedbackPhone = (TextView) $(R.id.tv_feedback_phone_num);
    }

    protected void initData() {
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnConfirm.setOnClickListener(this);
        this.mTvFeedbackPhone.setOnClickListener(this);
        this.mEtFeedbackText.addTextChangedListener(new TextWatcher() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.FeedbackActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                FeedbackActivity.this.mCharSequence = s;
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 200) {
                    ToastUtils.showShort(FeedbackActivity.this.mActivity, "反馈内容达到200上限了");
                }
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (FeedbackActivity.this.mCharSequence.length() >= 200) {
                    ToastUtils.showShort(FeedbackActivity.this.mActivity, "反馈内容达到200上限了");
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_feedback_confirm /* 2131296325 */:
                String feedbackContent = this.mEtFeedbackText.getText().toString();
                if ("xc://setdebug".equals(feedbackContent)) {
                    String url = XCHelp.getString("config_http_url", Constants.MAIN_VERSION_TAG);
                    OperationHintDialog operationHintDialog = OperationHintDialog.getInstance();
                    Activity activity = this.mActivity;
                    if (url.length() < 1) {
                        url = "https://gw.ixiaocong.com/";
                    }
                    operationHintDialog.showEditDialog(activity, this, "设置后台host", url);
                } else if (!TextUtils.isEmpty(feedbackContent)) {
                    commitFeedbackContent(feedbackContent);
                } else if (feedbackContent.length() > 200) {
                    ToastUtils.showShort(this.mActivity, "反馈内容超过200了");
                } else {
                    ToastUtils.showShort(this.mActivity, "反馈内容不能为空");
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.tv_feedback_phone_num /* 2131296983 */:
                if (ActivityCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                    ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.CALL_PHONE"}, 100);
                } else {
                    callPhone();
                }
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void callPhone() {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + getString(R.string.company_phone).replaceAll("-", Constants.MAIN_VERSION_TAG)));
        if (ActivityCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            ToastUtils.showShort(this.mActivity, "您当前未授权App访问通话功能");
        } else {
            startActivity(intent);
        }
    }

    private void commitFeedbackContent(String feedbackContent) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("content", feedbackContent);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/feedback");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.FeedbackActivity.2
            public void onComplete(XCResponseBean var1) {
                ToastUtils.showShort(FeedbackActivity.this.mActivity, "您的反馈已收到,我们会尽快处理该事宜");
                FeedbackActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(FeedbackActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == 0) {
                callPhone();
            } else {
                ToastUtils.showShort(this.mActivity, "您当前未授权App访问通话功能");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback
    public void editMsgCallback(String editMsg) {
        if ("https://gw.ixiaocong.com/".equals(editMsg) || "http://gw.ixiaocong.net/".equals(editMsg)) {
            if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
                XCHelp.putString("config_http_url", editMsg);
                ToastUtils.showShort(this.mActivity, "设置成功");
                XCDeviceController.getInstance().XCDeviceControllerStop(this.mActivity);
                XCManager.getInstance().logout(this.mActivity);
                XCRequest.getInstance().removeRequstClient(this.mActivity);
                RNVersionDBHelper.deleteAll();
                SpUtils.clearSp("NLC_ahe_9l", this.mActivity);
                SpUtils.clearSp("xiao_cong_uid", this.mActivity);
                YouzanSDK.userLogout(this.mActivity);
                XGPushManager.unregisterPush(this.mActivity);
                AppSpConstans.getInstance().clearParams();
                XgTokenService.actionStart(this.mActivity);
                XCManager.getInstance().initialWithAppId(getApplicationContext(), "27e33ff8a0634cc4b6f696c9872a2d1b", "3wruosO36ZzBXVM8", new XCDataCallback<String>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.FeedbackActivity.3
                    public void onComplete(String var1) {
                    }

                    public void onError(XCErrorMessage var1) {
                    }
                });
                Intent intent = new Intent(this.mActivity, (Class<?>) LoginActivity.class);
                ActivityManagerUtil.getScreenManager().popAllActivity();
                MainActivityManagerUtil.getScreenManager().popAllActivity();
                startActivity(intent);
                finish();
                return;
            }
            ToastUtils.showShort(this.mActivity, "请检查手机网络连接是否正常");
            return;
        }
        ToastUtils.showShort(this.mActivity, "地址错误请重新设置");
    }
}
