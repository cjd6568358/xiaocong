package com.ixiaocong.smarthome.phone.android.detail.activity.accredit;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleImageView;
import com.ixiaocong.smarthome.phone.android.detail.adater.AccreditPermissionListAdapter;
import com.ixiaocong.smarthome.phone.android.event.receiver.ScanResultReceiver;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.AuthInfoModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AccreditManagerActivity extends XcBaseActivity {
    private AccreditPermissionListAdapter mAdapter;
    private CircleImageView mCivAuthImg;
    private String mCode;
    private ImageView mIvBack;
    private RecyclerView mRvAuthList;
    private String mScanResult;
    private TextView mTvAccredit;
    private TextView mTvAuthName;
    private String mType;

    protected int getLayoutId() {
        return R.layout.activity_login_accredit;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvAccredit = (TextView) $(R.id.tv_accredit_login);
        this.mTvAuthName = (TextView) $(R.id.tv_auth_user_name);
        this.mCivAuthImg = (CircleImageView) $(R.id.civ_auth_user_img);
        this.mRvAuthList = (RecyclerView) $(R.id.rv_auth_perm_list);
        this.mRvAuthList.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    protected void initData() {
        this.mScanResult = getIntent().getStringExtra("scanContent");
        if (!TextUtils.isEmpty(this.mScanResult)) {
            checkeQrCode();
        }
        this.mAdapter = new AccreditPermissionListAdapter();
        this.mRvAuthList.setAdapter(this.mAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AccreditManagerActivity$$Lambda$1.lambdaFactory$(this));
        this.mTvAccredit.setOnClickListener(AccreditManagerActivity$$Lambda$2.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        if (!TextUtils.isEmpty(this.mCode) && !TextUtils.isEmpty(this.mType)) {
            if (this.mType.equals("login")) {
                accreditLogin();
                return;
            } else if (this.mType.equals("oauth")) {
                queryOauth();
                return;
            } else {
                ToastUtils.showShort(this.mActivity, "请重新扫描需要授权登录的二维码");
                return;
            }
        }
        ToastUtils.showShort(this.mActivity, "请扫描需要授权登录的二维码");
    }

    private void accreditLogin() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", this.mCode);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/qrcode/login");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditManagerActivity.1
            public void onComplete(XCResponseBean var1) {
                try {
                    JSONObject jsonObject = new JSONObject(var1.getData().toString());
                    int status = jsonObject.optInt("status");
                    if (status == 100) {
                        ToastUtils.showShort(AccreditManagerActivity.this.mActivity, "登录成功");
                        AccreditManagerActivity.this.finish();
                    } else if (status == 0) {
                        ToastUtils.showShort(AccreditManagerActivity.this.mActivity, "二维码已过期");
                    } else {
                        ToastUtils.showShort(AccreditManagerActivity.this.mActivity, "登录失败,请稍后重试");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(AccreditManagerActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private void checkeQrCode() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("qrcode", this.mScanResult);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("qr/check");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditManagerActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                try {
                    JSONObject jsonObj = new JSONObject(var1.getData());
                    AccreditManagerActivity.this.mType = jsonObj.optString("type");
                    if (!TextUtils.isEmpty(AccreditManagerActivity.this.mType) && !AccreditManagerActivity.this.mType.equals("redirect")) {
                        if (AccreditManagerActivity.this.mType.equals("login")) {
                            AccreditManagerActivity.this.mTvAccredit.setText("登录");
                            AccreditManagerActivity.this.mCode = jsonObj.optString("code");
                        } else if (AccreditManagerActivity.this.mType.equals("oauth")) {
                            AccreditManagerActivity.this.mTvAccredit.setText("授权");
                            AccreditManagerActivity.this.mCode = jsonObj.optString("code");
                            AccreditManagerActivity.this.requestAuthInfo(AccreditManagerActivity.this.mCode);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AccreditManagerActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestAuthInfo(String code) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("authorize/qrcode/info");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditManagerActivity.3
            public void onComplete(XCResponseBean var1) {
                AuthInfoModel authInfoModel = (AuthInfoModel) JSON.parseObject(var1.getData(), AuthInfoModel.class);
                if (authInfoModel.getStatus() == 0) {
                    ToastUtils.showShort(AccreditManagerActivity.this.mActivity, "二维码已过期");
                    return;
                }
                if (authInfoModel.getStatus() == 1) {
                    AccreditManagerActivity.this.mTvAuthName.setText(authInfoModel.getAppName());
                    Glide.with((FragmentActivity) AccreditManagerActivity.this).load(authInfoModel.getLogo()).error(R.mipmap.head_portrait_icon).fallback(R.mipmap.head_portrait_icon).priority(Priority.NORMAL).crossFade(BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN).dontAnimate().into(AccreditManagerActivity.this.mCivAuthImg);
                    if (authInfoModel.getPermissionList() != null && authInfoModel.getPermissionList().length > 0) {
                        List<String> authData = new ArrayList<>();
                        for (int i = 0; i < authInfoModel.getPermissionList().length; i++) {
                            authData.add(authInfoModel.getPermissionList()[i]);
                        }
                        AccreditManagerActivity.this.mAdapter.setNewData(authData);
                        AccreditManagerActivity.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                ToastUtils.showShort(AccreditManagerActivity.this.mActivity, "获取授权信息失败");
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(AccreditManagerActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private void queryOauth() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", this.mCode);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("authorize/qrcode/oauth");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditManagerActivity.4
            public void onComplete(XCResponseBean var1) {
                ToastUtils.showShort(AccreditManagerActivity.this.mActivity, "授权成功");
                AccreditManagerActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(AccreditManagerActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        ScanResultReceiver.getInstance().unregisterReceiver(this.mActivity);
    }
}
