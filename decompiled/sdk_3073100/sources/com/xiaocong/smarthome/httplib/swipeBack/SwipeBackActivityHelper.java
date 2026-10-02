package com.xiaocong.smarthome.httplib.swipeBack;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaocong.smarthome.httplib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwipeBackActivityHelper {
    private Activity mActivity;
    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackActivityHelper(Activity activity) {
        this.mActivity = activity;
    }

    public void onActivityCreate() {
        this.mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        this.mSwipeBackLayout = LayoutInflater.from(this.mActivity).inflate(R.layout.layout_swipeback, (ViewGroup) null);
        this.mSwipeBackLayout.addSwipeListener(new SwipeBackLayout$SwipeListener() { // from class: com.xiaocong.smarthome.httplib.swipeBack.SwipeBackActivityHelper.1
            @Override // com.xiaocong.smarthome.httplib.swipeBack.SwipeBackLayout$SwipeListener
            public void onScrollStateChange(int state, float scrollPercent) {
            }

            @Override // com.xiaocong.smarthome.httplib.swipeBack.SwipeBackLayout$SwipeListener
            public void onEdgeTouch(int edgeFlag) {
                SwipeBackUtils.convertActivityToTranslucent(SwipeBackActivityHelper.this.mActivity);
            }

            @Override // com.xiaocong.smarthome.httplib.swipeBack.SwipeBackLayout$SwipeListener
            public void onScrollOverThreshold() {
            }
        });
    }

    public void onPostCreate() {
        this.mSwipeBackLayout.attachToActivity(this.mActivity);
    }

    public View findViewById(int id) {
        if (this.mSwipeBackLayout != null) {
            return this.mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mSwipeBackLayout;
    }
}
