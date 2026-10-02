package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleImageView;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedUserDetailActivity extends XcBaseActivity implements View.OnClickListener {
    private Button mBtnShared;
    private CircleImageView mCivImg;
    private String mDeviceId;
    private String mHeadImg;
    private ImageView mIvBack;
    private String mNickname;
    private TextView mTvNickname;
    private String mUid;

    protected int getLayoutId() {
        return R.layout.activity_shared_user_detail;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mCivImg = (CircleImageView) $(R.id.civ_shared_user_head_img);
        this.mTvNickname = (TextView) $(R.id.tv_tab_shared_nickname);
        this.mBtnShared = (Button) $(R.id.btn_shared_device_user);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mUid = getIntent().getStringExtra("userId");
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mNickname = getIntent().getStringExtra("userName");
        this.mHeadImg = getIntent().getStringExtra("userImg");
        this.mTvNickname.setText(this.mNickname);
        if (!TextUtils.isEmpty(this.mHeadImg)) {
            Glide.with(this.mActivity).load(this.mHeadImg).asBitmap().into(new SimpleTarget<Bitmap>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedUserDetailActivity.1
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object obj, GlideAnimation glideAnimation) {
                    onResourceReady((Bitmap) obj, (GlideAnimation<? super Bitmap>) glideAnimation);
                }

                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    SharedUserDetailActivity.this.mCivImg.setImageBitmap(resource);
                }
            });
        } else {
            this.mCivImg.setBackgroundResource(R.mipmap.head_portrait_icon);
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnShared.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shared_device_user /* 2131296335 */:
                sharedDevice();
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }

    private void sharedDevice() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("uid", this.mUid);
        params.put("deviceIds", this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("share/bind");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedUserDetailActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedUserDetailActivity.this.mActivity, "设备分享成功");
                ActivityManagerUtil.getScreenManager().popAllActivity();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedUserDetailActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }
}
