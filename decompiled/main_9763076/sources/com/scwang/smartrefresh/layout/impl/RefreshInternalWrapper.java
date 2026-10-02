package com.scwang.smartrefresh.layout.impl;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@SuppressLint({"RestrictedApi"})
public class RefreshInternalWrapper implements RefreshInternal {
    private SpinnerStyle mSpinnerStyle;
    View mWrapperView;

    RefreshInternalWrapper(View wrapper) {
        this.mWrapperView = wrapper;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public View getView() {
        return this.mWrapperView;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(RefreshLayout refreshLayout, boolean success) {
        if (this.mWrapperView instanceof RefreshInternal) {
            return ((RefreshInternal) this.mWrapperView).onFinish(refreshLayout, success);
        }
        return 0;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... colors) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).setPrimaryColors(colors);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public SpinnerStyle getSpinnerStyle() {
        if (this.mWrapperView instanceof RefreshInternal) {
            return ((RefreshInternal) this.mWrapperView).getSpinnerStyle();
        }
        if (this.mSpinnerStyle != null) {
            return this.mSpinnerStyle;
        }
        ViewGroup.LayoutParams params = this.mWrapperView.getLayoutParams();
        if (params instanceof SmartRefreshLayout.LayoutParams) {
            this.mSpinnerStyle = ((SmartRefreshLayout.LayoutParams) params).spinnerStyle;
            if (this.mSpinnerStyle != null) {
                return this.mSpinnerStyle;
            }
        }
        if (params != null && (params.height == 0 || params.height == -1)) {
            SpinnerStyle spinnerStyle = SpinnerStyle.Scale;
            this.mSpinnerStyle = spinnerStyle;
            return spinnerStyle;
        }
        SpinnerStyle spinnerStyle2 = SpinnerStyle.Translate;
        this.mSpinnerStyle = spinnerStyle2;
        return spinnerStyle2;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onInitialized(kernel, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public boolean isSupportHorizontalDrag() {
        return (this.mWrapperView instanceof RefreshInternal) && ((RefreshInternal) this.mWrapperView).isSupportHorizontalDrag();
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onHorizontalDrag(percentX, offsetX, offsetMax);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onPulling(percent, offset, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleasing(float percent, int offset, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onReleasing(percent, offset, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onReleased(refreshLayout, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(RefreshLayout refreshLayout, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onStartAnimator(refreshLayout, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (this.mWrapperView instanceof RefreshInternal) {
            ((RefreshInternal) this.mWrapperView).onStateChanged(refreshLayout, oldState, newState);
        }
    }
}
