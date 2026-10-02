package com.xiaocong.smarthome.httplib.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.baidu.mobstat.StatService;
import com.xiaocong.smarthome.httplib.manager.SystemBarTintManager;
import com.xiaocong.smarthome.httplib.swipeBack.SwipeBackActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.zxing.utils.statusbar.StatusBarHelper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XcBaseActivity extends SwipeBackActivity {
    protected Activity mActivity;
    public SystemBarTintManager tintManager = null;

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaocong.smarthome.httplib.swipeBack.SwipeBackActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(32);
        getWindow().setBackgroundDrawable(null);
        StatusBarHelper.translucent(this);
        this.mActivity = this;
        setContentView(getLayoutId());
        initView();
        initAdapter();
        initData();
        addListener();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setStatusBarLight() {
        StatusBarHelper.setStatusBarLightMode(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setStatusBarDark() {
        StatusBarHelper.setStatusBarDarkMode(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    public <T extends View> T $(int i) {
        return (T) findViewById(i);
    }

    public void addListener() {
    }

    public void initAdapter() {
    }

    public void onDestroy() {
        super.onDestroy();
        HttpLoadingHelper.getInstance().dismissProcessLoading();
    }
}
