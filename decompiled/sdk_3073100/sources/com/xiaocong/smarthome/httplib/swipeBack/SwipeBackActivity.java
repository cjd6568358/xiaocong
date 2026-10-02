package com.xiaocong.smarthome.httplib.swipeBack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwipeBackActivity extends AppCompatActivity {
    private SwipeBackActivityHelper mHelper;

    /* JADX WARN: Multi-variable type inference failed */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHelper = new SwipeBackActivityHelper(this);
        this.mHelper.onActivityCreate();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate();
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && this.mHelper != null) {
            return this.mHelper.findViewById(id);
        }
        return v;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }
}
