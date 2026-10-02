package com.scwang.smartrefresh.layout.internal;

import android.R;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class InternalClassics<T extends InternalClassics> extends InternalAbstract implements RefreshInternal {
    protected Integer mAccentColor;
    protected ArrowDrawable mArrowDrawable;
    protected ImageView mArrowView;
    protected int mBackgroundColor;
    protected LinearLayout mCenterLayout;
    protected int mFinishDuration;
    protected int mPaddingBottom;
    protected int mPaddingTop;
    protected Integer mPrimaryColor;
    protected ProgressDrawable mProgressDrawable;
    protected ImageView mProgressView;
    protected RefreshKernel mRefreshKernel;
    protected SpinnerStyle mSpinnerStyle;
    protected TextView mTitleText;

    protected abstract T self();

    public InternalClassics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.mFinishDuration = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.mPaddingTop = 20;
        this.mPaddingBottom = 20;
        DensityUtil density = new DensityUtil();
        this.mCenterLayout = new LinearLayout(context);
        this.mCenterLayout.setId(R.id.widget_frame);
        this.mCenterLayout.setGravity(1);
        this.mCenterLayout.setOrientation(1);
        this.mTitleText = new TextView(context);
        LinearLayout.LayoutParams lpHeaderText = new LinearLayout.LayoutParams(-2, -2);
        this.mCenterLayout.addView(this.mTitleText, lpHeaderText);
        RelativeLayout.LayoutParams lpHeaderLayout = new RelativeLayout.LayoutParams(-2, -2);
        lpHeaderLayout.addRule(13);
        addView(this.mCenterLayout, lpHeaderLayout);
        RelativeLayout.LayoutParams lpArrow = new RelativeLayout.LayoutParams(density.dip2px(20.0f), density.dip2px(20.0f));
        lpArrow.addRule(15);
        lpArrow.addRule(0, R.id.widget_frame);
        this.mArrowView = new ImageView(context);
        addView(this.mArrowView, lpArrow);
        RelativeLayout.LayoutParams lpProgress = new RelativeLayout.LayoutParams((ViewGroup.LayoutParams) lpArrow);
        lpProgress.addRule(15);
        lpProgress.addRule(0, R.id.widget_frame);
        this.mProgressView = new ImageView(context);
        this.mProgressView.animate().setInterpolator(new LinearInterpolator());
        addView(this.mProgressView, lpProgress);
        if (getPaddingTop() == 0) {
            if (getPaddingBottom() == 0) {
                int paddingLeft = getPaddingLeft();
                int iDip2px = density.dip2px(20.0f);
                this.mPaddingTop = iDip2px;
                int paddingRight = getPaddingRight();
                int iDip2px2 = density.dip2px(20.0f);
                this.mPaddingBottom = iDip2px2;
                setPadding(paddingLeft, iDip2px, paddingRight, iDip2px2);
            } else {
                int paddingLeft2 = getPaddingLeft();
                int iDip2px3 = density.dip2px(20.0f);
                this.mPaddingTop = iDip2px3;
                int paddingRight2 = getPaddingRight();
                int paddingBottom = getPaddingBottom();
                this.mPaddingBottom = paddingBottom;
                setPadding(paddingLeft2, iDip2px3, paddingRight2, paddingBottom);
            }
        } else if (getPaddingBottom() == 0) {
            int paddingLeft3 = getPaddingLeft();
            int paddingTop = getPaddingTop();
            this.mPaddingTop = paddingTop;
            int paddingRight3 = getPaddingRight();
            int iDip2px4 = density.dip2px(20.0f);
            this.mPaddingBottom = iDip2px4;
            setPadding(paddingLeft3, paddingTop, paddingRight3, iDip2px4);
        } else {
            this.mPaddingTop = getPaddingTop();
            this.mPaddingBottom = getPaddingBottom();
        }
        if (isInEditMode()) {
            this.mArrowView.setVisibility(8);
        } else {
            this.mProgressView.setVisibility(8);
        }
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) == 1073741824) {
            setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        } else {
            setPadding(getPaddingLeft(), this.mPaddingTop, getPaddingRight(), this.mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= 14) {
            this.mArrowView.animate().cancel();
            this.mProgressView.animate().cancel();
        }
        Object drawable = this.mProgressView.getDrawable();
        if ((drawable instanceof Animatable) && ((Animatable) drawable).isRunning()) {
            ((Animatable) drawable).stop();
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        this.mRefreshKernel = kernel;
        if (this instanceof RefreshHeader) {
            this.mRefreshKernel.requestDrawBackgroundForHeader(this.mBackgroundColor);
        } else if (this instanceof RefreshFooter) {
            this.mRefreshKernel.requestDrawBackgroundForFooter(this.mBackgroundColor);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(RefreshLayout refreshLayout, int height, int extendHeight) {
        if (this.mProgressView.getVisibility() != 0) {
            this.mProgressView.setVisibility(0);
            Object drawable = this.mProgressView.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                this.mProgressView.animate().rotation(36000.0f).setDuration(100000L);
            }
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
        onStartAnimator(refreshLayout, height, extendHeight);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(RefreshLayout refreshLayout, boolean success) {
        Object drawable = this.mProgressView.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        } else {
            this.mProgressView.animate().rotation(0.0f).setDuration(0L);
        }
        this.mProgressView.setVisibility(8);
        return this.mFinishDuration;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... colors) {
        if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable) && this.mPrimaryColor == null) {
                setPrimaryColor(colors[0]);
                this.mPrimaryColor = null;
            }
            if (this.mAccentColor == null) {
                if (colors.length > 1) {
                    setAccentColor(colors[1]);
                } else {
                    setAccentColor(colors[0] == -1 ? -10066330 : -1);
                }
                this.mAccentColor = null;
            }
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public SpinnerStyle getSpinnerStyle() {
        return this.mSpinnerStyle;
    }

    public T setPrimaryColor(int i) {
        Integer numValueOf = Integer.valueOf(i);
        this.mPrimaryColor = numValueOf;
        this.mBackgroundColor = numValueOf.intValue();
        if (this.mRefreshKernel != null) {
            if (this instanceof RefreshHeader) {
                this.mRefreshKernel.requestDrawBackgroundForHeader(this.mPrimaryColor.intValue());
            } else if (this instanceof RefreshFooter) {
                this.mRefreshKernel.requestDrawBackgroundForFooter(this.mPrimaryColor.intValue());
            }
        }
        return (T) self();
    }

    public T setAccentColor(int i) {
        this.mAccentColor = Integer.valueOf(i);
        this.mTitleText.setTextColor(i);
        if (this.mArrowDrawable != null) {
            this.mArrowDrawable.setColor(i);
        }
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setColor(i);
        }
        return (T) self();
    }

    public ImageView getArrowView() {
        return this.mArrowView;
    }

    public ImageView getProgressView() {
        return this.mProgressView;
    }

    public TextView getTitleText() {
        return this.mTitleText;
    }
}
