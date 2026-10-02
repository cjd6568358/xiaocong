package com.scwang.smartrefresh.layout.header;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClassicsHeader extends InternalClassics<ClassicsHeader> implements RefreshHeader {
    protected String KEY_LAST_UPDATE_TIME;
    protected boolean mEnableLastTime;
    protected Date mLastTime;
    protected DateFormat mLastUpdateFormat;
    protected TextView mLastUpdateText;
    protected SharedPreferences mShared;
    public static String REFRESH_HEADER_PULLING = null;
    public static String REFRESH_HEADER_REFRESHING = null;
    public static String REFRESH_HEADER_LOADING = null;
    public static String REFRESH_HEADER_RELEASE = null;
    public static String REFRESH_HEADER_FINISH = null;
    public static String REFRESH_HEADER_FAILED = null;
    public static String REFRESH_HEADER_UPDATE = null;
    public static String REFRESH_HEADER_SECONDARY = null;

    public ClassicsHeader(Context context) {
        this(context, null);
    }

    public ClassicsHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        FragmentManager manager;
        List<Fragment> fragments;
        super(context, attrs, defStyleAttr);
        this.KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
        this.mEnableLastTime = true;
        if (REFRESH_HEADER_PULLING == null) {
            REFRESH_HEADER_PULLING = context.getString(R.string.srl_header_pulling);
        }
        if (REFRESH_HEADER_REFRESHING == null) {
            REFRESH_HEADER_REFRESHING = context.getString(R.string.srl_header_refreshing);
        }
        if (REFRESH_HEADER_LOADING == null) {
            REFRESH_HEADER_LOADING = context.getString(R.string.srl_header_loading);
        }
        if (REFRESH_HEADER_RELEASE == null) {
            REFRESH_HEADER_RELEASE = context.getString(R.string.srl_header_release);
        }
        if (REFRESH_HEADER_FINISH == null) {
            REFRESH_HEADER_FINISH = context.getString(R.string.srl_header_finish);
        }
        if (REFRESH_HEADER_FAILED == null) {
            REFRESH_HEADER_FAILED = context.getString(R.string.srl_header_failed);
        }
        if (REFRESH_HEADER_UPDATE == null) {
            REFRESH_HEADER_UPDATE = context.getString(R.string.srl_header_update);
        }
        if (REFRESH_HEADER_SECONDARY == null) {
            REFRESH_HEADER_SECONDARY = context.getString(R.string.srl_header_secondary);
        }
        DensityUtil density = new DensityUtil();
        this.mTitleText.setTextColor(-10066330);
        this.mTitleText.setText(isInEditMode() ? REFRESH_HEADER_REFRESHING : REFRESH_HEADER_PULLING);
        this.mLastUpdateText = new TextView(context);
        this.mLastUpdateText.setTextColor(-8618884);
        LinearLayout.LayoutParams lpUpdateText = new LinearLayout.LayoutParams(-2, -2);
        this.mCenterLayout.addView(this.mLastUpdateText, lpUpdateText);
        this.mLastUpdateFormat = new SimpleDateFormat(REFRESH_HEADER_UPDATE, Locale.getDefault());
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader);
        RelativeLayout.LayoutParams lpArrow = (RelativeLayout.LayoutParams) this.mArrowView.getLayoutParams();
        RelativeLayout.LayoutParams lpProgress = (RelativeLayout.LayoutParams) this.mProgressView.getLayoutParams();
        lpUpdateText.topMargin = ta.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextTimeMarginTop, density.dip2px(0.0f));
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
        this.mFinishDuration = ta.getInt(R.styleable.ClassicsHeader_srlFinishDuration, this.mFinishDuration);
        this.mEnableLastTime = ta.getBoolean(R.styleable.ClassicsHeader_srlEnableLastTime, this.mEnableLastTime);
        this.mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal())];
        this.mLastUpdateText.setVisibility(this.mEnableLastTime ? 0 : 8);
        if (ta.hasValue(R.styleable.ClassicsHeader_srlDrawableArrow)) {
            this.mArrowView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsHeader_srlDrawableArrow));
        } else {
            this.mArrowDrawable = new ArrowDrawable();
            this.mArrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlDrawableProgress)) {
            this.mProgressView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsHeader_srlDrawableProgress));
        } else {
            this.mProgressDrawable = new ProgressDrawable();
            this.mProgressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlTextSizeTitle)) {
            this.mTitleText.setTextSize(0, ta.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTitle, DensityUtil.dp2px(16.0f)));
        } else {
            this.mTitleText.setTextSize(16.0f);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlTextSizeTime)) {
            this.mLastUpdateText.setTextSize(0, ta.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTime, DensityUtil.dp2px(12.0f)));
        } else {
            this.mLastUpdateText.setTextSize(12.0f);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.ClassicsHeader_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsHeader_srlAccentColor, 0));
        }
        ta.recycle();
        try {
            if ((context instanceof FragmentActivity) && (manager = ((FragmentActivity) context).getSupportFragmentManager()) != null && (fragments = manager.getFragments()) != null && fragments.size() > 0) {
                setLastUpdateTime(new Date());
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        this.KEY_LAST_UPDATE_TIME += context.getClass().getName();
        this.mShared = context.getSharedPreferences("ClassicsHeader", 0);
        setLastUpdateTime(new Date(this.mShared.getLong(this.KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics
    public ClassicsHeader self() {
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(RefreshLayout layout, boolean success) {
        if (success) {
            this.mTitleText.setText(REFRESH_HEADER_FINISH);
            if (this.mLastTime != null) {
                setLastUpdateTime(new Date());
            }
        } else {
            this.mTitleText.setText(REFRESH_HEADER_FAILED);
        }
        return super.onFinish(layout, success);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
                this.mLastUpdateText.setVisibility(this.mEnableLastTime ? 0 : 8);
                break;
            case PullDownToRefresh:
                break;
            case Refreshing:
            case RefreshReleased:
                this.mTitleText.setText(REFRESH_HEADER_REFRESHING);
                this.mArrowView.setVisibility(8);
                return;
            case ReleaseToRefresh:
                this.mTitleText.setText(REFRESH_HEADER_RELEASE);
                this.mArrowView.animate().rotation(180.0f);
                return;
            case ReleaseToTwoLevel:
                this.mTitleText.setText(REFRESH_HEADER_SECONDARY);
                this.mArrowView.animate().rotation(0.0f);
                return;
            case Loading:
                this.mArrowView.setVisibility(8);
                this.mLastUpdateText.setVisibility(this.mEnableLastTime ? 4 : 8);
                this.mTitleText.setText(REFRESH_HEADER_LOADING);
                return;
            default:
                return;
        }
        this.mTitleText.setText(REFRESH_HEADER_PULLING);
        this.mArrowView.setVisibility(0);
        this.mArrowView.animate().rotation(0.0f);
    }

    public ClassicsHeader setLastUpdateTime(Date time) {
        this.mLastTime = time;
        this.mLastUpdateText.setText(this.mLastUpdateFormat.format(time));
        if (this.mShared != null && !isInEditMode()) {
            this.mShared.edit().putLong(this.KEY_LAST_UPDATE_TIME, time.getTime()).apply();
        }
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics
    public ClassicsHeader setAccentColor(int accentColor) {
        this.mLastUpdateText.setTextColor((16777215 & accentColor) | (-872415232));
        return (ClassicsHeader) super.setAccentColor(accentColor);
    }

    public TextView getLastUpdateText() {
        return this.mLastUpdateText;
    }
}
