package com.scwang.smartrefresh.layout.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class InternalAbstract extends RelativeLayout implements RefreshInternal {
    public InternalAbstract(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public View getView() {
        return this;
    }

    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    public void setPrimaryColors(int... colors) {
    }

    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }

    public void onPulling(float percent, int offset, int height, int extendHeight) {
    }

    public void onReleasing(float percent, int offset, int height, int extendHeight) {
    }

    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
    }

    public void onStartAnimator(RefreshLayout refreshLayout, int height, int extendHeight) {
    }

    public int onFinish(RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
