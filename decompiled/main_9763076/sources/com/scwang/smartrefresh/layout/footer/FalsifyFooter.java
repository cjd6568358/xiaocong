package com.scwang.smartrefresh.layout.footer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FalsifyFooter extends InternalAbstract implements RefreshFooter {
    private RefreshKernel mRefreshKernel;

    public FalsifyFooter(Context context) {
        this(context, null);
    }

    public FalsifyFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FalsifyFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int d = DensityUtil.dp2px(5.0f);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(-858993460);
            paint.setStrokeWidth(DensityUtil.dp2px(1.0f));
            paint.setPathEffect(new DashPathEffect(new float[]{d, d, d, d}, 1.0f));
            canvas.drawRect(d, d, getWidth() - d, getBottom() - d, paint);
            TextView textView = new TextView(getContext());
            textView.setText(getResources().getString(R.string.srl_component_falsify, getClass().getSimpleName(), Float.valueOf(DensityUtil.px2dp(getHeight()))));
            textView.setTextColor(-858993460);
            textView.setGravity(17);
            textView.measure(View.MeasureSpec.makeMeasureSpec(getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getHeight(), 1073741824));
            textView.layout(0, 0, getWidth(), getHeight());
            textView.draw(canvas);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        this.mRefreshKernel = kernel;
        kernel.getRefreshLayout().setEnableAutoLoadMore(false);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleased(RefreshLayout layout, int height, int extendHeight) {
        if (this.mRefreshKernel != null) {
            this.mRefreshKernel.setState(RefreshState.None);
            this.mRefreshKernel.setState(RefreshState.LoadFinish);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }
}
