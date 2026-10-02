package com.ixiaocong.smarthome.phone.android.detail.fragment.user;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.NoDoubleClickUtils;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleImageView;
import com.ixiaocong.smarthome.phone.android.detail.activity.accredit.MyAccreditListActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.list.DeviceHomeListActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeMainListActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedHomeActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.system.FeedbackActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.system.SystemSettingActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.system.UseAppHelpActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.theme.ThemeStyleActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.user.UserDataDetailActivity;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.model.UserInfoModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabUserFragment extends XcBaseFragment implements View.OnClickListener {
    private RelativeLayout mAccreditItem;
    private CircleImageView mCivHeadImg;
    private RelativeLayout mConnectItem;
    private RelativeLayout mDeviceItem;
    private RelativeLayout mHelpItem;
    private RelativeLayout mHomeItem;
    private RelativeLayout mSharedItem;
    private RelativeLayout mSystemItem;
    private RelativeLayout mThemeStyleItem;
    private TextView mTitleBarName;
    private TextView mTvNickname;
    private LinearLayout mUserData;
    private String mUserIcon;
    private String mUserId;

    protected int getLayoutId() {
        return R.layout.fragment_user_tab;
    }

    protected void initView() {
        $(R.id.main_statusbar_view).setLayoutParams(new RelativeLayout.LayoutParams(-1, ScreenUtils.getStatusHeight(this.mActivity)));
        this.mUserData = (LinearLayout) $(R.id.ll_tab_user_data);
        this.mTitleBarName = (TextView) $(R.id.tv_centertxt_main_title);
        this.mDeviceItem = (RelativeLayout) $(R.id.rl_tab_user_device);
        this.mHelpItem = (RelativeLayout) $(R.id.rl_tab_user_help);
        this.mConnectItem = (RelativeLayout) $(R.id.rl_tab_user_feedback);
        this.mSystemItem = (RelativeLayout) $(R.id.rl_tab_user_system_setting);
        this.mSharedItem = (RelativeLayout) $(R.id.rl_tab_user_shared_device);
        this.mCivHeadImg = (CircleImageView) $(R.id.civ_tab_user_head_img);
        this.mTvNickname = (TextView) $(R.id.tv_tab_user_nick_name);
        this.mAccreditItem = (RelativeLayout) $(R.id.rl_tab_user_accredit);
        this.mThemeStyleItem = (RelativeLayout) $(R.id.rl_tab_user_theme_style);
        this.mHomeItem = (RelativeLayout) $(R.id.rl_tab_user_home);
        this.mTitleBarName.setText("我的");
    }

    protected void initData() {
    }

    private void loadData() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("user/info");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.user.TabUserFragment.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                UserInfoModel infoModel = (UserInfoModel) JSON.parseObject(var1.getData(), UserInfoModel.class);
                TabUserFragment.this.mTvNickname.setText(infoModel.getUser().getNickname());
                TabUserFragment.this.mUserId = infoModel.getUser().getPhone() + Constants.MAIN_VERSION_TAG;
                if (TextUtils.isEmpty(TabUserFragment.this.mUserIcon) || !TabUserFragment.this.mUserIcon.equals(infoModel.getUser().getPortrait())) {
                    Glide.with((Fragment) TabUserFragment.this).load(infoModel.getUser().getPortrait()).error(R.mipmap.head_portrait_icon).fallback(R.mipmap.head_portrait_icon).priority(Priority.NORMAL).crossFade(BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN).dontAnimate().into(TabUserFragment.this.mCivHeadImg);
                    TabUserFragment.this.mUserIcon = infoModel.getUser().getPortrait();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (TabUserFragment.this.getUserVisibleHint()) {
                    ToastUtils.showShort(TabUserFragment.this.mActivity, var1.getErrorMessage());
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        loadData();
        StatService.onPageStart(getActivity(), "我的");
    }

    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "我的");
    }

    public void addListener() {
        super.addListener();
        this.mUserData.setOnClickListener(this);
        this.mDeviceItem.setOnClickListener(this);
        this.mHelpItem.setOnClickListener(this);
        this.mConnectItem.setOnClickListener(this);
        this.mSystemItem.setOnClickListener(this);
        this.mSharedItem.setOnClickListener(this);
        this.mAccreditItem.setOnClickListener(this);
        this.mThemeStyleItem.setOnClickListener(this);
        this.mHomeItem.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_user_data /* 2131296613 */:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    Intent intent = new Intent(this.mActivity, (Class<?>) UserDataDetailActivity.class);
                    intent.putExtra("userImg", this.mUserIcon);
                    intent.putExtra("userName", this.mTvNickname.getText().toString());
                    intent.putExtra("userId", this.mUserId);
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.rl_tab_user_accredit /* 2131296712 */:
                startActivity(new Intent(this.mActivity, (Class<?>) MyAccreditListActivity.class));
                break;
            case R.id.rl_tab_user_device /* 2131296713 */:
                startActivity(new Intent(this.mActivity, (Class<?>) DeviceHomeListActivity.class));
                break;
            case R.id.rl_tab_user_feedback /* 2131296714 */:
                startActivity(new Intent(this.mActivity, (Class<?>) FeedbackActivity.class));
                break;
            case R.id.rl_tab_user_help /* 2131296716 */:
                startActivity(new Intent(this.mActivity, (Class<?>) UseAppHelpActivity.class));
                break;
            case R.id.rl_tab_user_home /* 2131296717 */:
                startActivity(new Intent(this.mActivity, (Class<?>) HomeMainListActivity.class));
                break;
            case R.id.rl_tab_user_shared_device /* 2131296718 */:
                startActivity(new Intent(this.mActivity, (Class<?>) SharedHomeActivity.class));
                break;
            case R.id.rl_tab_user_system_setting /* 2131296719 */:
                startActivity(new Intent(this.mActivity, (Class<?>) SystemSettingActivity.class));
                break;
            case R.id.rl_tab_user_theme_style /* 2131296720 */:
                startActivity(new Intent(this.mActivity, (Class<?>) ThemeStyleActivity.class));
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            String nickName = data.getStringExtra("userName");
            this.mTvNickname.setText(nickName);
        }
    }
}
