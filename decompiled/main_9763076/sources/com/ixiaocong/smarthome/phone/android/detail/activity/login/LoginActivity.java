package com.ixiaocong.smarthome.phone.android.detail.activity.login;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
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
import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.ixiaocong.smarthome.phone.android.common.manager.AppDetailSettingManager;
import com.ixiaocong.smarthome.phone.android.common.manager.SoftInputManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.CountDownSmsUtils;
import com.ixiaocong.smarthome.phone.android.common.utils.MainActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.system.UserClauseActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.CountDownSmsFinishCallback;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LoginEvent;
import com.ixiaocong.smarthome.phone.android.event.service.XgTokenService;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
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
import com.xiaocong.smarthome.util.ACache;
import com.xiaocong.smarthome.util.log.XCLog;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LoginActivity extends XcBaseActivity implements View.OnClickListener, CountDownSmsFinishCallback {
    private Button mBtnLogin;
    private CheckBox mCbUser;
    private CountDownSmsUtils mCountDown;
    private EditText mEtPhone;
    private EditText mEtVerifyCode;
    private TextView mTvPrivacy;
    private TextView mTvSend;
    private TextView mTvUserClause;
    private ImageView mWxLigon;
    private String mCode = Constants.MAIN_VERSION_TAG;
    private boolean backKeyPressed = false;
    private boolean isSendCode = false;

    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        setSwipeBackEnable(false);
        this.mEtPhone = (EditText) $(R.id.et_login_input_phone);
        this.mEtVerifyCode = (EditText) $(R.id.et_login_input_verify_code);
        this.mTvSend = (TextView) $(R.id.tv_login_get_verify_code);
        this.mBtnLogin = (Button) $(R.id.btn_login_app);
        this.mWxLigon = (ImageView) $(R.id.iv_weixin_login_app);
        this.mCbUser = (CheckBox) $(R.id.cb_login_user_read);
        this.mTvUserClause = (TextView) $(R.id.tv_login_user_clause);
        this.mTvPrivacy = (TextView) $(R.id.tv_login_privacy_policy);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        requestPhoneState();
    }

    protected void onResume() {
        super.onResume();
        setStatusBarLight();
    }

    private void requestPhoneState() {
        if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.READ_PHONE_STATE") != 0 || ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_COARSE_LOCATION") != 0 || (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.READ_EXTERNAL_STORAGE") != 0 && ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)) {
            ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
        }
    }

    public void addListener() {
        super.addListener();
        this.mTvSend.setOnClickListener(this);
        this.mBtnLogin.setOnClickListener(this);
        this.mWxLigon.setOnClickListener(this);
        this.mTvPrivacy.setOnClickListener(this);
        this.mTvUserClause.setOnClickListener(this);
        this.mCbUser.setOnCheckedChangeListener(LoginActivity$$Lambda$1.lambdaFactory$(this));
        this.mEtVerifyCode.addTextChangedListener(new TextWatcher() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity.1
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
                if (!LoginActivity.this.isSendCode) {
                    ToastUtils.showShort(LoginActivity.this.mActivity, "请先获取验证码");
                    LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                    return;
                }
                if (this.charSequence.length() == 4) {
                    String phoneNum = LoginActivity.this.mEtPhone.getText().toString();
                    String verifyCode = LoginActivity.this.mEtVerifyCode.getText().toString();
                    if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(verifyCode) || !LoginActivity.this.mCbUser.isChecked()) {
                        LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                        return;
                    } else {
                        LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_login_shape));
                        return;
                    }
                }
                if (this.charSequence.length() != 4) {
                    LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                }
            }
        });
        this.mEtPhone.addTextChangedListener(new TextWatcher() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity.2
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
                if (LoginActivity.this.isSendCode) {
                    String phoneNum = LoginActivity.this.mEtPhone.getText().toString();
                    String verifyCode = LoginActivity.this.mEtVerifyCode.getText().toString();
                    if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(verifyCode) || !LoginActivity.this.mCbUser.isChecked()) {
                        LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                        return;
                    } else if (phoneNum.length() != 11 || verifyCode.length() != 4) {
                        LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
                        return;
                    } else {
                        LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_login_shape));
                        return;
                    }
                }
                LoginActivity.this.mBtnLogin.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.btn_unlogin_shape));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            String phoneNum = this.mEtPhone.getText().toString();
            String verifyCode = this.mEtVerifyCode.getText().toString();
            if (this.isSendCode && !TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(verifyCode) && verifyCode.length() == 4) {
                this.mBtnLogin.setBackground(getResources().getDrawable(R.drawable.btn_login_shape));
                return;
            } else {
                this.mBtnLogin.setBackground(getResources().getDrawable(R.drawable.btn_unlogin_shape));
                return;
            }
        }
        this.mBtnLogin.setBackground(getResources().getDrawable(R.drawable.btn_unlogin_shape));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_app /* 2131296326 */:
                String phoneNum = this.mEtPhone.getText().toString().trim();
                String verifyCode = this.mEtVerifyCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
                    ToastUtils.showShort(this.mActivity, "请输入手机号码,重新获取验证码");
                } else if (!this.isSendCode) {
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
                        userLogin(phoneNum, verifyCode, this.mCode);
                    } else {
                        ToastUtils.showShort(this.mActivity, "请检查手机网络是否已连接");
                    }
                }
                break;
            case R.id.iv_weixin_login_app /* 2131296548 */:
                wxInit();
                break;
            case R.id.tv_login_get_verify_code /* 2131297006 */:
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
            case R.id.tv_login_privacy_policy /* 2131297007 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) UserClauseActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.tv_login_user_clause /* 2131297009 */:
                Intent intent2 = new Intent(this.mActivity, (Class<?>) UserClauseActivity.class);
                intent2.putExtra("type", 1);
                startActivity(intent2);
                break;
        }
    }

    private void wxInit() {
        if (XcApplication.getInstance().registerWX() != null && XcApplication.getInstance().registerWX().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "xc_smart_phone_wx_login";
            XcApplication.getInstance().registerWX().sendReq(req);
            XcLogger.i("WXEntryActivity--", "sendAuth--");
            return;
        }
        ToastUtils.showShort(this.mActivity, "您手机当前未安装微信应用");
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
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity.3
            public void onComplete(XCResponseBean var1) {
                try {
                    SendCodeModel sendCodeModel = (SendCodeModel) JSON.parseObject(var1.getData(), SendCodeModel.class);
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                    LoginActivity.this.mEtVerifyCode.setVisibility(0);
                    LoginActivity.this.mEtPhone.clearFocus();
                    LoginActivity.this.mEtVerifyCode.setFocusableInTouchMode(true);
                    LoginActivity.this.mEtVerifyCode.setFocusable(true);
                    LoginActivity.this.mEtVerifyCode.requestFocus();
                    ToastUtils.showShort(LoginActivity.this.mActivity, "验证码已发送");
                    LoginActivity.this.mCountDown = new CountDownSmsUtils(LoginActivity.this, LoginActivity.this.mTvSend, 60000L, 1000L);
                    LoginActivity.this.mCountDown.start();
                    LoginActivity.this.mTvSend.setEnabled(false);
                    LoginActivity.this.mTvSend.setVisibility(0);
                    LoginActivity.this.mCode = sendCodeModel.getCode();
                    LoginActivity.this.isSendCode = true;
                } catch (Exception e) {
                    XCLog.e(e);
                }
            }

            public void onError(XCErrorMessage var1) {
                XcLogger.i("sendCode--erro", var1.getErrorMessage());
                ToastUtils.showShort(LoginActivity.this.mActivity, var1.getErrorMessage());
                LoginActivity.this.mTvSend.setText("获取验证码");
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    protected void onPause() {
        super.onPause();
        setStatusBarDark();
    }

    private void userLogin(String phoneNum, String verifyCode, String code) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", phoneNum);
        params.put("code", code);
        params.put("verifycode", verifyCode);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/login");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity.4
            public void onComplete(XCResponseBean var1) {
                try {
                    UserLoginModel userLoginModel = (UserLoginModel) JSON.parseObject(var1.getData(), UserLoginModel.class);
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                    XCManager.getInstance().loginWithToken(userLoginModel.getToken());
                    SpUtils.saveToLocal(LoginActivity.this.mActivity, "xiao_cong_jfnda", "xiao_cong_jfnda", userLoginModel.getPhone());
                    SpUtils.saveToLocal(LoginActivity.this.mActivity, "NLC_ahe_9l", "NLC_ahe_9l", userLoginModel.getToken());
                    SpUtils.saveToLocal(LoginActivity.this.mActivity, "xiao_cong_uid", "xiao_cong_uid", userLoginModel.getUid());
                    if (!TextUtils.isEmpty(userLoginModel.getUid())) {
                        XgTokenService.actionUid(LoginActivity.this.mActivity, userLoginModel.getUid());
                    }
                    EventBus.getDefault().post(new LoginEvent(true));
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.mActivity, (Class<?>) MainFragmentActivity.class));
                    ACache.get(LoginActivity.this.mActivity).clear();
                    LoginActivity.this.finish();
                } catch (Exception e) {
                    XCLog.e(e);
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(LoginActivity.this.mActivity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CountDownSmsFinishCallback
    public void restartCountDownSmsListener() {
        this.mTvSend.setEnabled(true);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100 && grantResults[3] != 0 && grantResults[4] != 0) {
            ToastUtils.showShort(this.mActivity, "请授权app获取手机存储权限");
            AppDetailSettingManager.getAppDetailSettingIntent(this.mActivity);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity$5] */
    public void onBackPressed() {
        if (!this.backKeyPressed) {
            this.backKeyPressed = true;
            ToastUtils.showShort(this.mActivity, "再按一次退出程序 ");
            new Thread() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.login.LoginActivity.5
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(1500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        LoginActivity.this.backKeyPressed = false;
                    }
                }
            }.start();
        } else {
            finish();
            MainActivityManagerUtil.getScreenManager().popAllActivity();
            ActivityManagerUtil.getScreenManager().AppExit(this.mActivity);
            super.onBackPressed();
        }
    }
}
