package com.ixiaocong.smarthome.phone.android.base;

import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.squareup.leakcanary.RefWatcher;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BaseFragment extends XcBaseFragment {
    protected int getLayoutId() {
        return 0;
    }

    protected void initView() {
        RefWatcher refWatcher = XcApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected void initData() {
    }

    public void onPause() {
        super.onPause();
    }
}
