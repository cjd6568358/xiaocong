package com.facebook.react.uimanager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SizeMonitoringFrameLayout extends FrameLayout {
    private OnSizeChangedListener mOnSizeChangedListener;

    public interface OnSizeChangedListener {
        void onSizeChanged(int i, int i2, int i3, int i4);
    }

    public SizeMonitoringFrameLayout(Context context) {
        super(context);
    }

    public SizeMonitoringFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeMonitoringFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener) {
        this.mOnSizeChangedListener = onSizeChangedListener;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mOnSizeChangedListener != null) {
            this.mOnSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }
}
