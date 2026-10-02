package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.util.PackageInfoUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AboutXiaocongActivity extends XcBaseActivity {
    private ImageView mIvBack;
    private TextView mTvVersion;

    protected int getLayoutId() {
        return R.layout.activity_about_xiaocong;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvVersion = (TextView) $(R.id.tv_now_app_version);
    }

    protected void initData() {
        try {
            this.mTvVersion.setText("For Android v" + PackageInfoUtil.getVersionName(this.mActivity) + " build 89701");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            this.mTvVersion.setText("For Android v2.3.0 build 89701");
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AboutXiaocongActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }
}
