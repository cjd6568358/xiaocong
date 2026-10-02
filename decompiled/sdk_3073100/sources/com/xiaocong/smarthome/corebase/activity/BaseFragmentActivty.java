package com.xiaocong.smarthome.corebase.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.xiaocong.smarthome.corebase.R;
import com.xiaocong.smarthome.corebase.util.ActivityManagerUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class BaseFragmentActivty extends FragmentActivity implements View.OnClickListener {
    protected final String TAG = getClass().getName();
    protected Activity mActivity;
    protected Handler mHandler;

    /* JADX WARN: Multi-variable type inference failed */
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    public void setContentView(View view) {
        super.setContentView(view);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        this.mHandler = new Handler();
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    private void animationForNew() {
        overridePendingTransition(R.anim.xc_corebase_translatex100to0, R.anim.xc_corebase_translatex0tof100);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        animationForNew();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getScreenManager().popActivity(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService("input_method");
        if (event.getAction() == 0 && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
        }
        return super.onTouchEvent(event);
    }
}
