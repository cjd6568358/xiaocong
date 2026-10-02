package com.xiaocong.smarthome.corebase.activity;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XcCoreBaseActivity extends Activity {
    protected Activity mActivity;

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(32);
        getWindow().setBackgroundDrawable(null);
        this.mActivity = this;
        setContentView(getLayoutId());
        initView();
        initAdapter();
        initData();
        addListener();
    }

    public void addListener() {
    }

    public void initAdapter() {
    }
}
