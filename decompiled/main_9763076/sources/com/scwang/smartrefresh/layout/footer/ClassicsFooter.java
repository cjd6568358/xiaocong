package com.scwang.smartrefresh.layout.footer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClassicsFooter extends InternalClassics<ClassicsFooter> implements RefreshFooter {
    protected boolean mNoMoreData;
    public static String REFRESH_FOOTER_PULLING = null;
    public static String REFRESH_FOOTER_RELEASE = null;
    public static String REFRESH_FOOTER_LOADING = null;
    public static String REFRESH_FOOTER_REFRESHING = null;
    public static String REFRESH_FOOTER_FINISH = null;
    public static String REFRESH_FOOTER_FAILED = null;
    public static String REFRESH_FOOTER_NOTHING = null;

    public ClassicsFooter(Context context) {
        this(context, null);
    }

    public ClassicsFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicsFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mNoMoreData = false;
        if (REFRESH_FOOTER_PULLING == null) {
            REFRESH_FOOTER_PULLING = context.getString(R.string.srl_footer_pulling);
        }
        if (REFRESH_FOOTER_RELEASE == null) {
            REFRESH_FOOTER_RELEASE = context.getString(R.string.srl_footer_release);
        }
        if (REFRESH_FOOTER_LOADING == null) {
            REFRESH_FOOTER_LOADING = context.getString(R.string.srl_footer_loading);
        }
        if (REFRESH_FOOTER_REFRESHING == null) {
            REFRESH_FOOTER_REFRESHING = context.getString(R.string.srl_footer_refreshing);
        }
        if (REFRESH_FOOTER_FINISH == null) {
            REFRESH_FOOTER_FINISH = context.getString(R.string.srl_footer_finish);
        }
        if (REFRESH_FOOTER_FAILED == null) {
            REFRESH_FOOTER_FAILED = context.getString(R.string.srl_footer_failed);
        }
        if (REFRESH_FOOTER_NOTHING == null) {
            REFRESH_FOOTER_NOTHING = context.getString(R.string.srl_footer_nothing);
        }
        DensityUtil density = new DensityUtil();
        this.mTitleText.setTextColor(-10066330);
        this.mTitleText.setText(isInEditMode() ? REFRESH_FOOTER_LOADING : REFRESH_FOOTER_PULLING);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsFooter);
        RelativeLayout.LayoutParams lpArrow = (RelativeLayout.LayoutParams) this.mArrowView.getLayoutParams();
        RelativeLayout.LayoutParams lpProgress = (RelativeLayout.LayoutParams) this.mProgressView.getLayoutParams();
        lpProgress.rightMargin = ta.getDimensionPixelSize(R.styleable.ClassicsFooter_srlDrawableMarginRight, density.dip2px(20.0f));
        lpArrow.rightMargin = lpProgress.rightMargin;
        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, lpProgress.height);
        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.height);
        this.mFinishDuration = ta.getInt(R.styleable.ClassicsFooter_srlFinishDuration, this.mFinishDuration);
        this.mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ClassicsFooter_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal())];
        if (ta.hasValue(R.styleable.ClassicsFooter_srlDrawableArrow)) {
            this.mArrowView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsFooter_srlDrawableArrow));
        } else {
            this.mArrowDrawable = new ArrowDrawable();
            this.mArrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_srlDrawableProgress)) {
            this.mProgressView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsFooter_srlDrawableProgress));
        } else {
            this.mProgressDrawable = new ProgressDrawable();
            this.mProgressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_srlTextSizeTitle)) {
            this.mTitleText.setTextSize(0, ta.getDimensionPixelSize(R.styleable.ClassicsFooter_srlTextSizeTitle, DensityUtil.dp2px(16.0f)));
        } else {
            this.mTitleText.setTextSize(16.0f);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.ClassicsFooter_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsFooter_srlAccentColor, 0));
        }
        ta.recycle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics
    public ClassicsFooter self() {
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(RefreshLayout refreshLayout, int height, int extendHeight) {
        if (!this.mNoMoreData) {
            super.onStartAnimator(refreshLayout, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(RefreshLayout layout, boolean success) {
        if (!this.mNoMoreData) {
            this.mTitleText.setText(success ? REFRESH_FOOTER_FINISH : REFRESH_FOOTER_FAILED);
            return super.onFinish(layout, success);
        }
        return 0;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... colors) {
        if (this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(colors);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean noMoreData) {
        if (this.mNoMoreData != noMoreData) {
            this.mNoMoreData = noMoreData;
            if (noMoreData) {
                this.mTitleText.setText(REFRESH_FOOTER_NOTHING);
                this.mArrowView.setVisibility(8);
                return true;
            }
            this.mTitleText.setText(REFRESH_FOOTER_PULLING);
            this.mArrowView.setVisibility(0);
            return true;
        }
        return true;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!this.mNoMoreData) {
            switch (newState) {
                case None:
                    this.mArrowView.setVisibility(0);
                    break;
                case PullUpToLoad:
                    break;
                case Loading:
                case LoadReleased:
                    this.mArrowView.setVisibility(8);
                    this.mTitleText.setText(REFRESH_FOOTER_LOADING);
                    return;
                case ReleaseToLoad:
                    this.mTitleText.setText(REFRESH_FOOTER_RELEASE);
                    this.mArrowView.animate().rotation(0.0f);
                    return;
                case Refreshing:
                    this.mTitleText.setText(REFRESH_FOOTER_REFRESHING);
                    this.mArrowView.setVisibility(8);
                    return;
                default:
                    return;
            }
            this.mTitleText.setText(REFRESH_FOOTER_PULLING);
            this.mArrowView.animate().rotation(180.0f);
        }
    }
}
