package com.xiaocong.smarthome.httplib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.baidu.mobstat.StatService;
import com.xiaocong.smarthome.httplib.manager.SystemBarTintManager;
import com.xiaocong.smarthome.zxing.utils.statusbar.StatusBarHelper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XcBaseFragmentActivity extends FragmentActivity {
    protected Activity mActivity;
    public SystemBarTintManager tintManager = null;

    protected abstract int getLayoutID();

    protected abstract void initControl(Bundle bundle);

    /* JADX WARN: Multi-variable type inference failed */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = this;
        requestWindowFeature(1);
        getWindow().setSoftInputMode(32);
        setContentView(getLayoutID());
        getWindow().setBackgroundDrawable(null);
        StatusBarHelper.translucent(this);
        if (bundle != null) {
        }
        initControl(bundle);
    }

    protected void onRestart() {
        super.onRestart();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    protected void onStop() {
        super.onStop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
    }
}
