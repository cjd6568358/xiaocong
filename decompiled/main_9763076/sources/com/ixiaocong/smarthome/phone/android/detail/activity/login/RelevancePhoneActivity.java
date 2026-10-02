package com.ixiaocong.smarthome.phone.android.detail.activity.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.SoftInputManager;
import com.ixiaocong.smarthome.phone.android.common.utils.CountDownSmsUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.system.UserClauseActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.CountDownSmsFinishCallback;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LoginEvent;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.SendCodeModel;
import com.xiaocong.smarthome.httplib.model.UserLoginModel;
import com.xiaocong.smarthome.httplib.utils.NetworkUtils;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.XCManager;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.util.log.XCLog;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RelevancePhoneActivity extends XcBaseActivity implements View.OnClickListener, CountDownSmsFinishCallback {
    private Button mBtnConfirm;
    private CheckBox mCbUser;
    private EditText mEtPhone;
    private EditText mEtVerifyCode;
    private ImageView mIvBack;
    private String mOpenId;
    private TextView mTvPrivacy;
    private TextView mTvSend;
    private TextView mTvUserClause;
    private String mCode = Constants.MAIN_VERSION_TAG;
    private Boolean isSendCode = false;

    protected int getLayoutId() {
        return R.layout.activity_relevance_phone;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mEtPhone = (EditText) $(R.id.et_releavnce_input_phone);
        this.mEtVerifyCode = (EditText) $(R.id.et_releavnce_input_verify_code);
        this.mTvSend = (TextView) $(R.id.tv_releavnce_get_verify_code);
        this.mBtnConfirm = (Button) $(R.id.btn_releavnce_confirm);
        this.mCbUser = (CheckBox) $(R.id.cb_releavnce_user_read);
        this.mTvUserClause = (TextView) $(R.id.tv_releavnce_user_clause);
        this.mTvPrivacy = (TextView) $(R.id.tv_releavnce_user_privacy_policy);
    }

    protected void initData() {
        this.mOpenId = getIntent().getStringExtra("ofjdkldkdl");
    }

    public void addListener() {
        super.addListener();
        this.mTvSend.setOnClickListener(this);
        this.mBtnConfirm.setOnClickListener(this);
        this.mTvPrivacy.setOnClickListener(this);
        this.mTvUserClause.setOnClickListener(this);
        this.mIvBack.setOnClickListener(this);
        this.mCbUser.setOnCheckedChangeListener(RelevancePhoneActivity$$Lambda$1.lambdaFactory$(this));
        this.mEtVerifyCode.addTextChangedListener(new TextWatcher() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.RelevancePhoneActivity.1
            CharSequence charSequence;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                this.charSequence = s;
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (!RelevancePhoneActivity.this.isSendCode.booleanValue()) {
                    ToastUtils.showShort(RelevancePhoneActivity.this.mActivity, "请先获取验证码");
                    RelevancePhoneActivity.this.mBtnConfirm.setBackground(RelevancePhoneActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                    return;
                }
                if (this.charSequence.length() == 4) {
                    String phoneNum = RelevancePhoneActivity.this.mEtPhone.getText().toString();
                    String verifyCode = RelevancePhoneActivity.this.mEtVerifyCode.getText().toString();
                    if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(verifyCode) || !RelevancePhoneActivity.this.mCbUser.isChecked()) {
                        RelevancePhoneActivity.this.mBtnConfirm.setBackground(RelevancePhoneActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                        return;
                    } else {
                        RelevancePhoneActivity.this.mBtnConfirm.setBackground(RelevancePhoneActivity.this.getResources().getDrawable(R.drawable.btn_login_shape));
                        return;
                    }
                }
                if (this.charSequence.length() != 4) {
                    RelevancePhoneActivity.this.mBtnConfirm.setBackground(RelevancePhoneActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            String phoneNum = this.mEtPhone.getText().toString();
            String verifyCode = this.mEtVerifyCode.getText().toString();
            if (this.isSendCode.booleanValue() && !TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(verifyCode) && verifyCode.length() == 4) {
                this.mBtnConfirm.setBackground(getResources().getDrawable(R.drawable.btn_login_shape));
                return;
            } else {
                this.mBtnConfirm.setBackground(getResources().getDrawable(R.drawable.btn_unlogin_shape));
                return;
            }
        }
        this.mBtnConfirm.setBackground(getResources().getDrawable(R.drawable.btn_unlogin_shape));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_releavnce_confirm /* 2131296329 */:
                String phoneNum = this.mEtPhone.getText().toString().trim();
                String verifyCode = this.mEtVerifyCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
                    ToastUtils.showShort(this.mActivity, "请输入手机号码,重新获取验证码");
                } else if (!this.isSendCode.booleanValue()) {
                    ToastUtils.showShort(this.mActivity, "请先获取验证码");
                } else if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtils.showShort(this.mActivity, "请输入验证码");
                } else if (verifyCode.length() != 4) {
                    ToastUtils.showShort(this.mActivity, "请输入4位有效验证码");
                } else if (!this.mCbUser.isChecked()) {
                    ToastUtils.showShort(this.mActivity, "请同意用户条款和隐私政策");
                } else {
                    SoftInputManager.hidenSoftInputFromWindow(this.mEtVerifyCode);
                    if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
                        userConfirm(phoneNum, verifyCode, this.mCode, this.mOpenId);
                    } else {
                        ToastUtils.showShort(this.mActivity, "请检查手机网络是否已连接");
                    }
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.tv_releavnce_get_verify_code /* 2131297026 */:
                String phoneNum2 = this.mEtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum2)) {
                    ToastUtils.showShort(this.mActivity, "请输入手机号码");
                } else if (phoneNum2.length() != 11) {
                    ToastUtils.showShort(this.mActivity, "请输入正确的手机号码");
                } else if (!this.mCbUser.isChecked()) {
                    ToastUtils.showShort(this.mActivity, "请同意用户条款和隐私政策");
                } else if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
                    sendCode(phoneNum2);
                    this.mEtVerifyCode.setVisibility(0);
                    this.mEtPhone.clearFocus();
                    this.mEtVerifyCode.setFocusableInTouchMode(true);
                    this.mEtVerifyCode.setFocusable(true);
                    this.mEtVerifyCode.requestFocus();
                } else {
                    ToastUtils.showShort(this.mActivity, "请检查手机网络是否已连接");
                }
                break;
            case R.id.tv_releavnce_user_clause /* 2131297028 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) UserClauseActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_releavnce_user_privacy_policy /* 2131297029 */:
                Intent intent2 = new Intent(this.mActivity, (Class<?>) UserClauseActivity.class);
                intent2.putExtra("type", 2);
                startActivity(intent2);
                break;
        }
    }

    private void sendCode(String phone) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        paramsNoSign.put("code", this.mCode);
        params.put("phone", phone);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("sms/send");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.RelevancePhoneActivity.2
            public void onComplete(XCResponseBean var1) {
                try {
                    SendCodeModel sendCodeModel = (SendCodeModel) JSON.parseObject(var1.getData(), SendCodeModel.class);
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                    RelevancePhoneActivity.this.mEtVerifyCode.setVisibility(0);
                    RelevancePhoneActivity.this.mEtPhone.clearFocus();
                    RelevancePhoneActivity.this.mEtVerifyCode.setFocusableInTouchMode(true);
                    RelevancePhoneActivity.this.mEtVerifyCode.setFocusable(true);
                    RelevancePhoneActivity.this.mEtVerifyCode.requestFocus();
                    ToastUtils.showShort(RelevancePhoneActivity.this.mActivity, "验证码已发送");
                    CountDownSmsUtils countDown = new CountDownSmsUtils(RelevancePhoneActivity.this, RelevancePhoneActivity.this.mTvSend, 60000L, 1000L);
                    countDown.start();
                    RelevancePhoneActivity.this.mTvSend.setEnabled(false);
                    RelevancePhoneActivity.this.mTvSend.setVisibility(0);
                    RelevancePhoneActivity.this.mCode = sendCodeModel.getCode();
                    RelevancePhoneActivity.this.isSendCode = true;
                } catch (Exception e) {
                    XCLog.e(e);
                }
            }

            public void onError(XCErrorMessage var1) {
                XcLogger.i("sendCode--erro", var1.getErrorMessage());
                ToastUtils.showShort(RelevancePhoneActivity.this.mActivity, "获取失败,请稍后重试");
                RelevancePhoneActivity.this.mTvSend.setText("获取验证码");
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    private void userConfirm(String phoneNum, String verifyCode, String code, String openId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", phoneNum);
        params.put("code", code);
        params.put("verifycode", verifyCode);
        params.put("openId", openId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/wx/bindPhone");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.RelevancePhoneActivity.3
            public void onComplete(XCResponseBean var1) {
                try {
                    UserLoginModel userLoginModel = (UserLoginModel) JSON.parseObject(var1.getData(), UserLoginModel.class);
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                    XCManager.getInstance().loginWithToken(userLoginModel.getToken());
                    SpUtils.saveToLocal(RelevancePhoneActivity.this.mActivity, "xiao_cong_jfnda", "xiao_cong_jfnda", userLoginModel.getPhone());
                    SpUtils.saveToLocal(RelevancePhoneActivity.this.mActivity, "NLC_ahe_9l", "NLC_ahe_9l", userLoginModel.getToken());
                    SpUtils.saveToLocal(RelevancePhoneActivity.this.mActivity, "xiao_cong_uid", "xiao_cong_uid", userLoginModel.getUid());
                    EventBus.getDefault().post(new LoginEvent(true));
                    RelevancePhoneActivity.this.startActivity(new Intent(RelevancePhoneActivity.this.mActivity, (Class<?>) MainFragmentActivity.class));
                    RelevancePhoneActivity.this.finish();
                } catch (Exception e) {
                    XCLog.e(e);
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RelevancePhoneActivity.this.mActivity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CountDownSmsFinishCallback
    public void restartCountDownSmsListener() {
        this.mTvSend.setEnabled(true);
    }
}
