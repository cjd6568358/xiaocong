package com.scwang.smartrefresh.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.Scroller;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshContent;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.constant.DimensionStatus;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.impl.RefreshContentWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.util.DelayedRunnable;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.scwang.smartrefresh.layout.util.ViscousFluidInterpolator;
import java.util.ArrayList;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@SuppressLint({"RestrictedApi"})
public class SmartRefreshLayout extends ViewGroup implements NestedScrollingParent, RefreshLayout {
    protected Runnable animationRunnable;
    protected boolean mDisableContentWhenLoading;
    protected boolean mDisableContentWhenRefresh;
    protected char mDragDirection;
    protected float mDragRate;
    protected boolean mEnableAutoLoadMore;
    protected boolean mEnableClipFooterWhenFixedBehind;
    protected boolean mEnableClipHeaderWhenFixedBehind;
    protected boolean mEnableFooterFollowWhenLoadFinished;
    protected boolean mEnableFooterTranslationContent;
    protected boolean mEnableHeaderTranslationContent;
    protected boolean mEnableLoadMore;
    protected boolean mEnableLoadMoreWhenContentNotFull;
    protected boolean mEnableOverScrollBounce;
    protected boolean mEnableOverScrollDrag;
    protected boolean mEnablePreviewInEditMode;
    protected boolean mEnablePureScrollMode;
    protected boolean mEnableRefresh;
    protected boolean mEnableScrollContentWhenLoaded;
    protected boolean mEnableScrollContentWhenRefreshed;
    protected MotionEvent mFalsifyEvent;
    protected int mFixedFooterViewId;
    protected int mFixedHeaderViewId;
    protected int mFloorDuration;
    protected int mFooterBackgroundColor;
    protected int mFooterExtendHeight;
    protected int mFooterHeight;
    protected DimensionStatus mFooterHeightStatus;
    protected int mFooterInsetStart;
    protected boolean mFooterLocked;
    protected float mFooterMaxDragRate;
    protected boolean mFooterNeedTouchEventWhenLoading;
    protected boolean mFooterNoMoreData;
    protected float mFooterTriggerRate;
    protected Handler mHandler;
    protected int mHeaderBackgroundColor;
    protected int mHeaderExtendHeight;
    protected int mHeaderHeight;
    protected DimensionStatus mHeaderHeightStatus;
    protected int mHeaderInsetStart;
    protected float mHeaderMaxDragRate;
    protected boolean mHeaderNeedTouchEventWhenRefreshing;
    protected float mHeaderTriggerRate;
    protected boolean mIsBeingDragged;
    protected RefreshKernel mKernel;
    protected long mLastOpenTime;
    protected int mLastSpinner;
    protected float mLastTouchX;
    protected float mLastTouchY;
    protected List<DelayedRunnable> mListDelayedRunnable;
    protected OnLoadMoreListener mLoadMoreListener;
    protected boolean mManualHeaderTranslationContent;
    protected boolean mManualLoadMore;
    protected boolean mManualNestedScrolling;
    protected int mMaximumVelocity;
    protected int mMinimumVelocity;
    protected NestedScrollingChildHelper mNestedChild;
    protected boolean mNestedInProgress;
    protected NestedScrollingParentHelper mNestedParent;
    protected OnMultiPurposeListener mOnMultiPurposeListener;
    protected Paint mPaint;
    protected int[] mParentOffsetInWindow;
    protected int[] mPrimaryColors;
    protected int mReboundDuration;
    protected Interpolator mReboundInterpolator;
    protected RefreshContent mRefreshContent;
    protected RefreshFooter mRefreshFooter;
    protected RefreshHeader mRefreshHeader;
    protected OnRefreshListener mRefreshListener;
    protected int mScreenHeightPixels;
    protected ScrollBoundaryDecider mScrollBoundaryDecider;
    protected Scroller mScroller;
    protected int mSpinner;
    protected RefreshState mState;
    protected boolean mSuperDispatchTouchEvent;
    protected int mTotalUnconsumed;
    protected int mTouchSlop;
    protected int mTouchSpinner;
    protected float mTouchX;
    protected float mTouchY;
    protected VelocityTracker mVelocityTracker;
    protected boolean mVerticalPermit;
    protected RefreshState mViceState;
    protected ValueAnimator reboundAnimator;
    protected static boolean sManualFooterCreator = false;
    protected static DefaultRefreshFooterCreator sFooterCreator = new DefaultRefreshFooterCreator() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.1
        @Override // com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
        public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
            return new BallPulseFooter(context);
        }
    };
    protected static DefaultRefreshHeaderCreator sHeaderCreator = new DefaultRefreshHeaderCreator() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.2
        @Override // com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
        public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
            return new BezierRadarHeader(context);
        }
    };

    public SmartRefreshLayout(Context context) {
        this(context, null);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mFloorDuration = SQLiteDatabase.MAX_SQL_CACHE_SIZE;
        this.mReboundDuration = SQLiteDatabase.MAX_SQL_CACHE_SIZE;
        this.mDragRate = 0.5f;
        this.mDragDirection = 'n';
        this.mEnableRefresh = true;
        this.mEnableLoadMore = false;
        this.mEnableClipHeaderWhenFixedBehind = true;
        this.mEnableClipFooterWhenFixedBehind = true;
        this.mEnableHeaderTranslationContent = true;
        this.mEnableFooterTranslationContent = true;
        this.mEnableFooterFollowWhenLoadFinished = false;
        this.mEnablePreviewInEditMode = true;
        this.mEnableOverScrollBounce = true;
        this.mEnableOverScrollDrag = true;
        this.mEnableAutoLoadMore = true;
        this.mEnablePureScrollMode = false;
        this.mEnableScrollContentWhenLoaded = true;
        this.mEnableScrollContentWhenRefreshed = true;
        this.mEnableLoadMoreWhenContentNotFull = true;
        this.mDisableContentWhenRefresh = false;
        this.mDisableContentWhenLoading = false;
        this.mFooterNoMoreData = false;
        this.mManualLoadMore = false;
        this.mManualNestedScrolling = false;
        this.mManualHeaderTranslationContent = false;
        this.mParentOffsetInWindow = new int[2];
        this.mNestedChild = new NestedScrollingChildHelper(this);
        this.mNestedParent = new NestedScrollingParentHelper(this);
        this.mHeaderHeightStatus = DimensionStatus.DefaultUnNotify;
        this.mFooterHeightStatus = DimensionStatus.DefaultUnNotify;
        this.mHeaderMaxDragRate = 2.5f;
        this.mFooterMaxDragRate = 2.5f;
        this.mHeaderTriggerRate = 1.0f;
        this.mFooterTriggerRate = 1.0f;
        this.mState = RefreshState.None;
        this.mViceState = RefreshState.None;
        this.mLastOpenTime = 0L;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mFooterLocked = false;
        this.mVerticalPermit = false;
        this.mFalsifyEvent = null;
        setClipToPadding(false);
        DensityUtil density = new DensityUtil();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mScroller = new Scroller(context);
        this.mKernel = new RefreshKernelImpl();
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mScreenHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        this.mReboundInterpolator = new ViscousFluidInterpolator();
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SmartRefreshLayout);
        setNestedScrollingEnabled(ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableNestedScrolling, false));
        this.mDragRate = ta.getFloat(R.styleable.SmartRefreshLayout_srlDragRate, this.mDragRate);
        this.mHeaderMaxDragRate = ta.getFloat(R.styleable.SmartRefreshLayout_srlHeaderMaxDragRate, this.mHeaderMaxDragRate);
        this.mFooterMaxDragRate = ta.getFloat(R.styleable.SmartRefreshLayout_srlFooterMaxDragRate, this.mFooterMaxDragRate);
        this.mHeaderTriggerRate = ta.getFloat(R.styleable.SmartRefreshLayout_srlHeaderTriggerRate, this.mHeaderTriggerRate);
        this.mFooterTriggerRate = ta.getFloat(R.styleable.SmartRefreshLayout_srlFooterTriggerRate, this.mFooterTriggerRate);
        this.mEnableRefresh = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableRefresh, this.mEnableRefresh);
        this.mReboundDuration = ta.getInt(R.styleable.SmartRefreshLayout_srlReboundDuration, this.mReboundDuration);
        this.mEnableLoadMore = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableLoadMore, this.mEnableLoadMore);
        this.mHeaderHeight = ta.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlHeaderHeight, density.dip2px(100.0f));
        this.mFooterHeight = ta.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlFooterHeight, density.dip2px(60.0f));
        this.mHeaderInsetStart = ta.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlHeaderInsetStart, 0);
        this.mFooterInsetStart = ta.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlFooterInsetStart, 0);
        this.mDisableContentWhenRefresh = ta.getBoolean(R.styleable.SmartRefreshLayout_srlDisableContentWhenRefresh, this.mDisableContentWhenRefresh);
        this.mDisableContentWhenLoading = ta.getBoolean(R.styleable.SmartRefreshLayout_srlDisableContentWhenLoading, this.mDisableContentWhenLoading);
        this.mEnableHeaderTranslationContent = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent, this.mEnableHeaderTranslationContent);
        this.mEnableFooterTranslationContent = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterTranslationContent, this.mEnableFooterTranslationContent);
        this.mEnablePreviewInEditMode = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnablePreviewInEditMode, this.mEnablePreviewInEditMode);
        this.mEnableAutoLoadMore = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableAutoLoadMore, this.mEnableAutoLoadMore);
        this.mEnableOverScrollBounce = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableOverScrollBounce, this.mEnableOverScrollBounce);
        this.mEnablePureScrollMode = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnablePureScrollMode, this.mEnablePureScrollMode);
        this.mEnableScrollContentWhenLoaded = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableScrollContentWhenLoaded, this.mEnableScrollContentWhenLoaded);
        this.mEnableScrollContentWhenRefreshed = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableScrollContentWhenRefreshed, this.mEnableScrollContentWhenRefreshed);
        this.mEnableLoadMoreWhenContentNotFull = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull, this.mEnableLoadMoreWhenContentNotFull);
        this.mEnableFooterFollowWhenLoadFinished = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterFollowWhenLoadFinished, this.mEnableFooterFollowWhenLoadFinished);
        this.mEnableClipHeaderWhenFixedBehind = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableClipHeaderWhenFixedBehind, this.mEnableClipHeaderWhenFixedBehind);
        this.mEnableClipFooterWhenFixedBehind = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableClipFooterWhenFixedBehind, this.mEnableClipFooterWhenFixedBehind);
        this.mEnableOverScrollDrag = ta.getBoolean(R.styleable.SmartRefreshLayout_srlEnableOverScrollDrag, this.mEnableOverScrollDrag);
        this.mFixedHeaderViewId = ta.getResourceId(R.styleable.SmartRefreshLayout_srlFixedHeaderViewId, -1);
        this.mFixedFooterViewId = ta.getResourceId(R.styleable.SmartRefreshLayout_srlFixedFooterViewId, -1);
        this.mManualLoadMore = ta.hasValue(R.styleable.SmartRefreshLayout_srlEnableLoadMore);
        this.mManualNestedScrolling = ta.hasValue(R.styleable.SmartRefreshLayout_srlEnableNestedScrolling);
        this.mManualHeaderTranslationContent = ta.hasValue(R.styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent);
        this.mHeaderHeightStatus = ta.hasValue(R.styleable.SmartRefreshLayout_srlHeaderHeight) ? DimensionStatus.XmlLayoutUnNotify : this.mHeaderHeightStatus;
        this.mFooterHeightStatus = ta.hasValue(R.styleable.SmartRefreshLayout_srlFooterHeight) ? DimensionStatus.XmlLayoutUnNotify : this.mFooterHeightStatus;
        this.mHeaderExtendHeight = (int) Math.max(this.mHeaderHeight * (this.mHeaderMaxDragRate - 1.0f), 0.0f);
        this.mFooterExtendHeight = (int) Math.max(this.mFooterHeight * (this.mFooterMaxDragRate - 1.0f), 0.0f);
        int accentColor = ta.getColor(R.styleable.SmartRefreshLayout_srlAccentColor, 0);
        int primaryColor = ta.getColor(R.styleable.SmartRefreshLayout_srlPrimaryColor, 0);
        if (primaryColor != 0) {
            if (accentColor != 0) {
                this.mPrimaryColors = new int[]{primaryColor, accentColor};
            } else {
                this.mPrimaryColors = new int[]{primaryColor};
            }
        } else if (accentColor != 0) {
            this.mPrimaryColors = new int[]{0, accentColor};
        }
        ta.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 3) {
            throw new RuntimeException("最多只支持3个子View，Most only support three sub view");
        }
        int indexContent = -1;
        int[] indexArray = {1, 0, 2};
        for (int index : indexArray) {
            if (index < count) {
                View view = getChildAt(index);
                if (!(view instanceof RefreshInternal)) {
                    indexContent = index;
                }
                if (RefreshContentWrapper.isScrollableView(view)) {
                    indexContent = index;
                    break;
                }
            }
        }
        int indexHeader = -1;
        int indexFooter = -1;
        if (indexContent >= 0) {
            this.mRefreshContent = new RefreshContentWrapper(getChildAt(indexContent));
            if (indexContent == 1) {
                indexHeader = 0;
                if (count == 3) {
                    indexFooter = 2;
                }
            } else if (count == 2) {
                indexFooter = 1;
            }
        }
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            if (i == indexHeader || (i != indexFooter && indexHeader == -1 && this.mRefreshHeader == null && (childAt instanceof RefreshHeader))) {
                this.mRefreshHeader = childAt instanceof RefreshHeader ? (RefreshHeader) childAt : new RefreshHeaderWrapper(childAt);
            } else if (i == indexFooter || (indexFooter == -1 && (childAt instanceof RefreshFooter))) {
                this.mRefreshFooter = childAt instanceof RefreshFooter ? (RefreshFooter) childAt : new RefreshFooterWrapper(childAt);
            } else if (this.mRefreshContent == null) {
                this.mRefreshContent = new RefreshContentWrapper(childAt);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            if (this.mHandler == null) {
                this.mHandler = new Handler();
            }
            if (this.mListDelayedRunnable != null) {
                for (DelayedRunnable runnable : this.mListDelayedRunnable) {
                    this.mHandler.postDelayed(runnable, runnable.delayMillis);
                }
                this.mListDelayedRunnable.clear();
                this.mListDelayedRunnable = null;
            }
            if (this.mRefreshHeader == null) {
                setRefreshHeader(sHeaderCreator.createRefreshHeader(getContext(), this));
            }
            if (this.mRefreshFooter == null) {
                setRefreshFooter(sFooterCreator.createRefreshFooter(getContext(), this));
            } else {
                this.mEnableLoadMore = this.mEnableLoadMore || !this.mManualLoadMore;
            }
            if (this.mRefreshContent == null) {
                int padding = DensityUtil.dp2px(20.0f);
                TextView errorView = new TextView(getContext());
                errorView.setTextColor(-39424);
                errorView.setGravity(17);
                errorView.setTextSize(20.0f);
                errorView.setPadding(padding, padding, padding, padding);
                errorView.setText(R.string.srl_content_empty);
                addView(errorView, -1, -1);
                this.mRefreshContent = new RefreshContentWrapper(errorView);
            }
            View fixedHeaderView = this.mFixedHeaderViewId > 0 ? findViewById(this.mFixedHeaderViewId) : null;
            View fixedFooterView = this.mFixedFooterViewId > 0 ? findViewById(this.mFixedFooterViewId) : null;
            this.mRefreshContent.setScrollBoundaryDecider(this.mScrollBoundaryDecider);
            this.mRefreshContent.setEnableLoadMoreWhenContentNotFull(this.mEnableLoadMoreWhenContentNotFull);
            this.mRefreshContent.setUpComponent(this.mKernel, fixedHeaderView, fixedFooterView);
            if (this.mSpinner != 0) {
                notifyStateChanged(RefreshState.None);
                RefreshContent refreshContent = this.mRefreshContent;
                this.mSpinner = 0;
                refreshContent.moveSpinner(0);
            }
            if (!this.mManualNestedScrolling && !isNestedScrollingEnabled()) {
                post(new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.3
                    @Override // java.lang.Runnable
                    public void run() {
                        for (ViewParent parent = SmartRefreshLayout.this.getParent(); parent != null; parent = parent.getParent()) {
                            if (parent instanceof NestedScrollingParent) {
                                View target = SmartRefreshLayout.this;
                                if (((NestedScrollingParent) parent).onStartNestedScroll(target, target, 2)) {
                                    SmartRefreshLayout.this.setNestedScrollingEnabled(true);
                                    SmartRefreshLayout.this.mManualNestedScrolling = false;
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        }
        if (this.mPrimaryColors != null) {
            if (this.mRefreshHeader != null) {
                this.mRefreshHeader.setPrimaryColors(this.mPrimaryColors);
            }
            if (this.mRefreshFooter != null) {
                this.mRefreshFooter.setPrimaryColors(this.mPrimaryColors);
            }
        }
        if (this.mRefreshContent != null) {
            bringChildToFront(this.mRefreshContent.getView());
        }
        if (this.mRefreshHeader != null && this.mRefreshHeader.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
            bringChildToFront(this.mRefreshHeader.getView());
        }
        if (this.mRefreshFooter != null && this.mRefreshFooter.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
            bringChildToFront(this.mRefreshFooter.getView());
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minimumHeight = 0;
        boolean isInEditMode = isInEditMode() && this.mEnablePreviewInEditMode;
        int len = getChildCount();
        for (int i = 0; i < len; i++) {
            View child = getChildAt(i);
            if (this.mRefreshHeader != null && this.mRefreshHeader.getView() == child) {
                View headerView = this.mRefreshHeader.getView();
                LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                int widthSpec = getChildMeasureSpec(widthMeasureSpec, lp.leftMargin + lp.rightMargin, lp.width);
                if (this.mHeaderHeightStatus.gteReplaceWith(DimensionStatus.XmlLayoutUnNotify)) {
                    int heightSpec = View.MeasureSpec.makeMeasureSpec(Math.max((this.mHeaderHeight - lp.bottomMargin) - lp.topMargin, 0), 1073741824);
                    headerView.measure(widthSpec, heightSpec);
                } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                    int headerHeight = 0;
                    if (!this.mHeaderHeightStatus.notified) {
                        measureChild(headerView, widthSpec, View.MeasureSpec.makeMeasureSpec(Math.max((View.MeasureSpec.getSize(heightMeasureSpec) - lp.bottomMargin) - lp.topMargin, 0), Integer.MIN_VALUE));
                        headerHeight = headerView.getMeasuredHeight();
                    }
                    headerView.measure(widthSpec, View.MeasureSpec.makeMeasureSpec(Math.max((View.MeasureSpec.getSize(heightMeasureSpec) - lp.bottomMargin) - lp.topMargin, 0), 1073741824));
                    if (headerHeight > 0 && headerHeight != headerView.getMeasuredHeight()) {
                        this.mHeaderHeight = lp.bottomMargin + headerHeight + lp.topMargin;
                    }
                } else if (lp.height > 0) {
                    if (this.mHeaderHeightStatus.canReplaceWith(DimensionStatus.XmlExactUnNotify)) {
                        this.mHeaderHeight = lp.height + lp.bottomMargin + lp.topMargin;
                        this.mHeaderHeightStatus = DimensionStatus.XmlExactUnNotify;
                    }
                    int heightSpec2 = View.MeasureSpec.makeMeasureSpec(lp.height, 1073741824);
                    headerView.measure(widthSpec, heightSpec2);
                } else if (lp.height == -2) {
                    int heightSpec3 = View.MeasureSpec.makeMeasureSpec(Math.max((View.MeasureSpec.getSize(heightMeasureSpec) - lp.bottomMargin) - lp.topMargin, 0), Integer.MIN_VALUE);
                    headerView.measure(widthSpec, heightSpec3);
                    int measuredHeight = headerView.getMeasuredHeight();
                    if (measuredHeight > 0 && this.mHeaderHeightStatus.canReplaceWith(DimensionStatus.XmlWrapUnNotify)) {
                        this.mHeaderHeightStatus = DimensionStatus.XmlWrapUnNotify;
                        this.mHeaderHeight = headerView.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;
                    } else if (measuredHeight <= 0) {
                        int heightSpec4 = View.MeasureSpec.makeMeasureSpec(Math.max((this.mHeaderHeight - lp.bottomMargin) - lp.topMargin, 0), 1073741824);
                        headerView.measure(widthSpec, heightSpec4);
                    }
                } else if (lp.height != -1) {
                    headerView.measure(widthSpec, heightMeasureSpec);
                } else {
                    int heightSpec5 = View.MeasureSpec.makeMeasureSpec(Math.max((this.mHeaderHeight - lp.bottomMargin) - lp.topMargin, 0), 1073741824);
                    headerView.measure(widthSpec, heightSpec5);
                }
                if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale && !isInEditMode) {
                    int height = Math.max(0, isEnableRefresh() ? this.mSpinner : 0);
                    int heightSpec6 = View.MeasureSpec.makeMeasureSpec(Math.max((height - lp.bottomMargin) - lp.topMargin, 0), 1073741824);
                    headerView.measure(widthSpec, heightSpec6);
                }
                if (!this.mHeaderHeightStatus.notified) {
                    this.mHeaderHeightStatus = this.mHeaderHeightStatus.notified();
                    this.mHeaderExtendHeight = (int) Math.max(this.mHeaderHeight * (this.mHeaderMaxDragRate - 1.0f), 0.0f);
                    this.mRefreshHeader.onInitialized(this.mKernel, this.mHeaderHeight, this.mHeaderExtendHeight);
                }
                if (isInEditMode && isEnableRefresh()) {
                    minimumHeight += headerView.getMeasuredHeight();
                }
            }
            if (this.mRefreshFooter != null && this.mRefreshFooter.getView() == child) {
                View footerView = this.mRefreshFooter.getView();
                LayoutParams lp2 = (LayoutParams) footerView.getLayoutParams();
                int widthSpec2 = getChildMeasureSpec(widthMeasureSpec, lp2.leftMargin + lp2.rightMargin, lp2.width);
                if (this.mFooterHeightStatus.gteReplaceWith(DimensionStatus.XmlLayoutUnNotify)) {
                    int heightSpec7 = View.MeasureSpec.makeMeasureSpec(Math.max((this.mFooterHeight - lp2.topMargin) - lp2.bottomMargin, 0), 1073741824);
                    footerView.measure(widthSpec2, heightSpec7);
                } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                    int footerHeight = 0;
                    if (!this.mFooterHeightStatus.notified) {
                        measureChild(footerView, widthSpec2, View.MeasureSpec.makeMeasureSpec((View.MeasureSpec.getSize(heightMeasureSpec) - lp2.topMargin) - lp2.bottomMargin, Integer.MIN_VALUE));
                        footerHeight = footerView.getMeasuredHeight();
                    }
                    footerView.measure(widthSpec2, View.MeasureSpec.makeMeasureSpec((View.MeasureSpec.getSize(heightMeasureSpec) - lp2.topMargin) - lp2.bottomMargin, 1073741824));
                    if (footerHeight > 0 && footerHeight != footerView.getMeasuredHeight()) {
                        this.mHeaderHeight = lp2.topMargin + footerHeight + lp2.bottomMargin;
                    }
                } else if (lp2.height > 0) {
                    if (this.mFooterHeightStatus.canReplaceWith(DimensionStatus.XmlExactUnNotify)) {
                        this.mFooterHeight = lp2.height + lp2.topMargin + lp2.bottomMargin;
                        this.mFooterHeightStatus = DimensionStatus.XmlExactUnNotify;
                    }
                    int heightSpec8 = View.MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                    footerView.measure(widthSpec2, heightSpec8);
                } else if (lp2.height == -2) {
                    int heightSpec9 = View.MeasureSpec.makeMeasureSpec(Math.max((View.MeasureSpec.getSize(heightMeasureSpec) - lp2.topMargin) - lp2.bottomMargin, 0), Integer.MIN_VALUE);
                    footerView.measure(widthSpec2, heightSpec9);
                    int measuredHeight2 = footerView.getMeasuredHeight();
                    if (measuredHeight2 > 0 && this.mFooterHeightStatus.canReplaceWith(DimensionStatus.XmlWrapUnNotify)) {
                        this.mFooterHeightStatus = DimensionStatus.XmlWrapUnNotify;
                        this.mFooterHeight = footerView.getMeasuredHeight() + lp2.topMargin + lp2.bottomMargin;
                    } else if (measuredHeight2 <= 0) {
                        int heightSpec10 = View.MeasureSpec.makeMeasureSpec(Math.max((this.mFooterHeight - lp2.topMargin) - lp2.bottomMargin, 0), 1073741824);
                        footerView.measure(widthSpec2, heightSpec10);
                    }
                } else if (lp2.height != -1) {
                    footerView.measure(widthSpec2, heightMeasureSpec);
                } else {
                    int heightSpec11 = View.MeasureSpec.makeMeasureSpec(Math.max((this.mFooterHeight - lp2.topMargin) - lp2.bottomMargin, 0), 1073741824);
                    footerView.measure(widthSpec2, heightSpec11);
                }
                if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale && !isInEditMode) {
                    int height2 = Math.max(0, this.mEnableLoadMore ? -this.mSpinner : 0);
                    int heightSpec12 = View.MeasureSpec.makeMeasureSpec(Math.max((height2 - lp2.topMargin) - lp2.bottomMargin, 0), 1073741824);
                    footerView.measure(widthSpec2, heightSpec12);
                }
                if (!this.mFooterHeightStatus.notified) {
                    this.mFooterHeightStatus = this.mFooterHeightStatus.notified();
                    this.mFooterExtendHeight = (int) Math.max(this.mFooterHeight * (this.mFooterMaxDragRate - 1.0f), 0.0f);
                    this.mRefreshFooter.onInitialized(this.mKernel, this.mFooterHeight, this.mFooterExtendHeight);
                }
                if (isInEditMode && isEnableLoadMore()) {
                    minimumHeight += footerView.getMeasuredHeight();
                }
            }
            if (this.mRefreshContent != null && this.mRefreshContent.getView() == child) {
                View contentView = this.mRefreshContent.getView();
                LayoutParams lp3 = (LayoutParams) contentView.getLayoutParams();
                int widthSpec3 = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp3.leftMargin + lp3.rightMargin, lp3.width);
                int heightSpec13 = getChildMeasureSpec(heightMeasureSpec, ((isInEditMode && isEnableLoadMore() && this.mRefreshFooter != null && (this.mEnableFooterTranslationContent || this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind)) ? this.mFooterHeight : 0) + lp3.bottomMargin + getPaddingTop() + getPaddingBottom() + lp3.topMargin + ((isInEditMode && isEnableRefresh() && this.mRefreshHeader != null && (this.mEnableHeaderTranslationContent || this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind)) ? this.mHeaderHeight : 0), lp3.height);
                contentView.measure(widthSpec3, heightSpec13);
                this.mRefreshContent.onInitialHeaderAndFooter(this.mHeaderHeight, this.mFooterHeight);
                minimumHeight += contentView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec), resolveSize(minimumHeight, heightMeasureSpec));
        this.mLastTouchX = getMeasuredWidth() / 2;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        int len = getChildCount();
        for (int i = 0; i < len; i++) {
            View child = getChildAt(i);
            if (this.mRefreshContent != null && this.mRefreshContent.getView() == child) {
                boolean isPreviewMode = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefresh() && this.mRefreshHeader != null;
                View contentView = this.mRefreshContent.getView();
                LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
                int left = paddingLeft + lp.leftMargin;
                int top = paddingTop + lp.topMargin;
                int right = left + contentView.getMeasuredWidth();
                int bottom = top + contentView.getMeasuredHeight();
                if (isPreviewMode && (this.mEnableHeaderTranslationContent || this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind)) {
                    top += this.mHeaderHeight;
                    bottom += this.mHeaderHeight;
                }
                contentView.layout(left, top, right, bottom);
            }
            if (this.mRefreshHeader != null && this.mRefreshHeader.getView() == child) {
                boolean isPreviewMode2 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefresh();
                View headerView = this.mRefreshHeader.getView();
                LayoutParams lp2 = (LayoutParams) headerView.getLayoutParams();
                int left2 = lp2.leftMargin;
                int top2 = lp2.topMargin + this.mHeaderInsetStart;
                int right2 = left2 + headerView.getMeasuredWidth();
                int bottom2 = top2 + headerView.getMeasuredHeight();
                if (!isPreviewMode2 && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                    top2 -= this.mHeaderHeight;
                    bottom2 -= this.mHeaderHeight;
                }
                headerView.layout(left2, top2, right2, bottom2);
            }
            if (this.mRefreshFooter != null && this.mRefreshFooter.getView() == child) {
                boolean isPreviewMode3 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableLoadMore();
                View footerView = this.mRefreshFooter.getView();
                LayoutParams lp3 = (LayoutParams) footerView.getLayoutParams();
                SpinnerStyle style = this.mRefreshFooter.getSpinnerStyle();
                int left3 = lp3.leftMargin;
                int top3 = (lp3.topMargin + getMeasuredHeight()) - this.mFooterInsetStart;
                if (isPreviewMode3 || style == SpinnerStyle.FixedFront || style == SpinnerStyle.FixedBehind) {
                    top3 -= this.mFooterHeight;
                } else if (style == SpinnerStyle.Scale && this.mSpinner < 0) {
                    top3 -= Math.max(isEnableLoadMore() ? -this.mSpinner : 0, 0);
                }
                int right3 = left3 + footerView.getMeasuredWidth();
                footerView.layout(left3, top3, right3, top3 + footerView.getMeasuredHeight());
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        moveSpinner(0, false);
        notifyStateChanged(RefreshState.None);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler = null;
        this.mManualLoadMore = true;
        this.mManualNestedScrolling = true;
        this.animationRunnable = null;
        if (this.reboundAnimator != null) {
            this.reboundAnimator.removeAllListeners();
            this.reboundAnimator.removeAllUpdateListeners();
            this.reboundAnimator.cancel();
            this.reboundAnimator = null;
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        View contentView = this.mRefreshContent != null ? this.mRefreshContent.getView() : null;
        if (this.mRefreshHeader != null && this.mRefreshHeader.getView() == child) {
            if (!isEnableRefresh()) {
                return true;
            }
            if (!this.mEnablePreviewInEditMode && isInEditMode()) {
                return true;
            }
            if (contentView != null) {
                int bottom = Math.max(contentView.getTop() + contentView.getPaddingTop() + this.mSpinner, child.getTop());
                if (this.mHeaderBackgroundColor != 0 && this.mPaint != null) {
                    this.mPaint.setColor(this.mHeaderBackgroundColor);
                    if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale) {
                        bottom = child.getBottom();
                    } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        bottom = child.getBottom() + this.mSpinner;
                    }
                    canvas.drawRect(child.getLeft(), child.getTop(), child.getRight(), bottom, this.mPaint);
                }
                if (this.mEnableClipHeaderWhenFixedBehind && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), child.getTop(), child.getRight(), bottom);
                    boolean zDrawChild = super.drawChild(canvas, child, drawingTime);
                    canvas.restore();
                    return zDrawChild;
                }
            }
        }
        if (this.mRefreshFooter != null && this.mRefreshFooter.getView() == child) {
            if (!isEnableLoadMore()) {
                return true;
            }
            if (!this.mEnablePreviewInEditMode && isInEditMode()) {
                return true;
            }
            if (contentView != null) {
                int top = Math.min((contentView.getBottom() - contentView.getPaddingBottom()) + this.mSpinner, child.getBottom());
                if (this.mFooterBackgroundColor != 0 && this.mPaint != null) {
                    this.mPaint.setColor(this.mFooterBackgroundColor);
                    if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale) {
                        top = child.getTop();
                    } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                        top = child.getTop() + this.mSpinner;
                    }
                    canvas.drawRect(child.getLeft(), top, child.getRight(), child.getBottom(), this.mPaint);
                }
                if (this.mEnableClipFooterWhenFixedBehind && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), top, child.getRight(), child.getBottom());
                    boolean zDrawChild2 = super.drawChild(canvas, child, drawingTime);
                    canvas.restore();
                    return zDrawChild2;
                }
            }
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override // android.view.View
    public void computeScroll() {
        float velocity;
        this.mScroller.getCurrY();
        if (this.mScroller.computeScrollOffset()) {
            int finalY = this.mScroller.getFinalY();
            if ((finalY < 0 && ((this.mEnableOverScrollDrag || isEnableRefresh()) && this.mRefreshContent.canRefresh())) || (finalY > 0 && ((this.mEnableOverScrollDrag || isEnableLoadMore()) && this.mRefreshContent.canLoadMore()))) {
                if (this.mVerticalPermit) {
                    if (Build.VERSION.SDK_INT >= 14) {
                        velocity = finalY > 0 ? -this.mScroller.getCurrVelocity() : this.mScroller.getCurrVelocity();
                    } else {
                        velocity = (1.0f * (this.mScroller.getCurrY() - finalY)) / Math.max(this.mScroller.getDuration() - this.mScroller.timePassed(), 1);
                    }
                    animSpinnerBounce(velocity);
                }
                this.mScroller.forceFinished(true);
                return;
            }
            this.mVerticalPermit = true;
            invalidate();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:234:0x04ed  */
    /* JADX WARN: Code duplicated, block: B:237:0x051c  */
    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent e) {
        int action = e.getActionMasked();
        boolean pointerUp = action == 6;
        int skipIndex = pointerUp ? e.getActionIndex() : -1;
        float sumX = 0.0f;
        float sumY = 0.0f;
        int count = e.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skipIndex != i) {
                sumX += e.getX(i);
                sumY += e.getY(i);
            }
        }
        int div = pointerUp ? count - 1 : count;
        float touchX = sumX / div;
        float touchY = sumY / div;
        if ((action == 6 || action == 5) && this.mIsBeingDragged) {
            this.mTouchY += touchY - this.mLastTouchY;
        }
        this.mLastTouchX = touchX;
        this.mLastTouchY = touchY;
        if (this.mNestedInProgress) {
            int totalUnconsumed = this.mTotalUnconsumed;
            boolean ret = super.dispatchTouchEvent(e);
            if (action == 2 && totalUnconsumed == this.mTotalUnconsumed) {
                int offsetX = (int) this.mLastTouchX;
                int offsetMax = getWidth();
                float percentX = this.mLastTouchX / (offsetMax == 0 ? 1 : offsetMax);
                if (isEnableRefresh() && this.mSpinner > 0 && this.mRefreshHeader != null && this.mRefreshHeader.isSupportHorizontalDrag()) {
                    this.mRefreshHeader.onHorizontalDrag(percentX, offsetX, offsetMax);
                    return ret;
                }
                if (isEnableLoadMore() && this.mSpinner < 0 && this.mRefreshFooter != null && this.mRefreshFooter.isSupportHorizontalDrag()) {
                    this.mRefreshFooter.onHorizontalDrag(percentX, offsetX, offsetMax);
                    return ret;
                }
                return ret;
            }
            return ret;
        }
        if (!isEnabled() || ((!isEnableRefresh() && !isEnableLoadMore() && !this.mEnableOverScrollDrag) || ((this.mHeaderNeedTouchEventWhenRefreshing && ((this.mState.opening || this.mState.finishing) && this.mState.isHeader())) || (this.mFooterNeedTouchEventWhenLoading && ((this.mState.opening || this.mState.finishing) && this.mState.isFooter()))))) {
            boolean ret2 = super.dispatchTouchEvent(e);
            return ret2;
        }
        if (interceptByAnimator(action) || this.mState.finishing || ((this.mState == RefreshState.Loading && this.mDisableContentWhenLoading) || (this.mState == RefreshState.Refreshing && this.mDisableContentWhenRefresh))) {
            return false;
        }
        if (this.mRefreshContent != null) {
            switch (action) {
                case 0:
                    this.mVelocityTracker.clear();
                    this.mVelocityTracker.addMovement(e);
                    this.mRefreshContent.onActionDown(e);
                    this.mScroller.forceFinished(true);
                    break;
                case 1:
                    if (!this.mNestedInProgress) {
                        this.mVelocityTracker.addMovement(e);
                        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    }
                    this.mRefreshContent.onActionUpOrCancel();
                    break;
                case 2:
                    if (!this.mNestedInProgress) {
                        this.mVelocityTracker.addMovement(e);
                    }
                    break;
                case 3:
                    this.mRefreshContent.onActionUpOrCancel();
                    break;
            }
        }
        switch (action) {
            case 0:
                this.mTouchX = touchX;
                this.mTouchY = touchY;
                this.mLastSpinner = 0;
                this.mTouchSpinner = this.mSpinner;
                this.mIsBeingDragged = false;
                this.mSuperDispatchTouchEvent = super.dispatchTouchEvent(e);
                if (this.mState == RefreshState.TwoLevel && this.mTouchY < (getMeasuredHeight() * 5) / 6) {
                    this.mDragDirection = 'h';
                    boolean ret3 = this.mSuperDispatchTouchEvent;
                    return ret3;
                }
                return true;
            case 1:
                startFlingIfNeed(null);
                this.mDragDirection = 'n';
                if (this.mFalsifyEvent != null) {
                    this.mFalsifyEvent.recycle();
                    this.mFalsifyEvent = null;
                    long time = e.getEventTime();
                    MotionEvent ec = MotionEvent.obtain(time, time, action, this.mTouchX, touchY, 0);
                    super.dispatchTouchEvent(ec);
                    ec.recycle();
                }
                overSpinner();
                if (this.mIsBeingDragged) {
                    this.mIsBeingDragged = false;
                    return true;
                }
                boolean ret4 = super.dispatchTouchEvent(e);
                return ret4;
            case 2:
                float dx = touchX - this.mTouchX;
                float dy = touchY - this.mTouchY;
                if (!this.mIsBeingDragged && this.mDragDirection != 'h' && this.mRefreshContent != null) {
                    if (this.mDragDirection == 'v' || (Math.abs(dy) >= this.mTouchSlop && Math.abs(dx) < Math.abs(dy))) {
                        this.mDragDirection = 'v';
                        if (dy > 0.0f && (this.mSpinner < 0 || ((this.mEnableOverScrollDrag || isEnableRefresh()) && this.mRefreshContent.canRefresh()))) {
                            this.mIsBeingDragged = true;
                            this.mTouchY = touchY - this.mTouchSlop;
                        } else if (dy < 0.0f && (this.mSpinner > 0 || ((this.mEnableOverScrollDrag || isEnableLoadMore()) && ((this.mState == RefreshState.Loading && this.mFooterLocked) || this.mRefreshContent.canLoadMore())))) {
                            this.mIsBeingDragged = true;
                            this.mTouchY = this.mTouchSlop + touchY;
                        }
                        if (this.mIsBeingDragged) {
                            dy = touchY - this.mTouchY;
                            if (this.mSuperDispatchTouchEvent) {
                                e.setAction(3);
                                super.dispatchTouchEvent(e);
                            }
                            if (this.mSpinner > 0 || (this.mSpinner == 0 && dy > 0.0f)) {
                                this.mKernel.setState(RefreshState.PullDownToRefresh);
                            } else {
                                this.mKernel.setState(RefreshState.PullUpToLoad);
                            }
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    } else if (Math.abs(dx) >= this.mTouchSlop && Math.abs(dx) > Math.abs(dy) && this.mDragDirection != 'v') {
                        this.mDragDirection = 'h';
                    }
                }
                if (this.mIsBeingDragged) {
                    int spinner = ((int) dy) + this.mTouchSpinner;
                    if ((this.mViceState.isHeader() && (spinner < 0 || this.mLastSpinner < 0)) || (this.mViceState.isFooter() && (spinner > 0 || this.mLastSpinner > 0))) {
                        this.mLastSpinner = spinner;
                        long time2 = e.getEventTime();
                        if (this.mFalsifyEvent == null) {
                            this.mFalsifyEvent = MotionEvent.obtain(time2, time2, 0, this.mTouchX + dx, this.mTouchY, 0);
                            super.dispatchTouchEvent(this.mFalsifyEvent);
                        }
                        MotionEvent em = MotionEvent.obtain(time2, time2, 2, this.mTouchX + dx, this.mTouchY + spinner, 0);
                        super.dispatchTouchEvent(em);
                        if (this.mFooterLocked && dy > this.mTouchSlop && this.mSpinner < 0) {
                            this.mFooterLocked = false;
                        }
                        if (spinner > 0 && ((this.mEnableOverScrollDrag || isEnableRefresh()) && this.mRefreshContent.canRefresh())) {
                            this.mLastTouchY = touchY;
                            this.mTouchY = touchY;
                            spinner = 0;
                            this.mTouchSpinner = 0;
                            this.mKernel.setState(RefreshState.PullDownToRefresh);
                        } else if (spinner < 0 && ((this.mEnableOverScrollDrag || isEnableLoadMore()) && this.mRefreshContent.canLoadMore())) {
                            this.mLastTouchY = touchY;
                            this.mTouchY = touchY;
                            spinner = 0;
                            this.mTouchSpinner = 0;
                            this.mKernel.setState(RefreshState.PullUpToLoad);
                        }
                        if ((this.mViceState.isHeader() && spinner < 0) || (this.mViceState.isFooter() && spinner > 0)) {
                            if (this.mSpinner != 0) {
                                moveSpinnerInfinitely(0.0f);
                            }
                            return true;
                        }
                        if (this.mFalsifyEvent != null) {
                            this.mFalsifyEvent = null;
                            em.setAction(3);
                            super.dispatchTouchEvent(em);
                        }
                        em.recycle();
                    }
                    moveSpinnerInfinitely(spinner);
                    return true;
                }
                if (this.mFooterLocked && dy > this.mTouchSlop && this.mSpinner < 0) {
                    this.mFooterLocked = false;
                }
                boolean ret5 = super.dispatchTouchEvent(e);
                return ret5;
            case 3:
                this.mDragDirection = 'n';
                if (this.mFalsifyEvent != null) {
                    this.mFalsifyEvent.recycle();
                    this.mFalsifyEvent = null;
                    long time3 = e.getEventTime();
                    MotionEvent ec2 = MotionEvent.obtain(time3, time3, action, this.mTouchX, touchY, 0);
                    super.dispatchTouchEvent(ec2);
                    ec2.recycle();
                }
                overSpinner();
                if (this.mIsBeingDragged) {
                    this.mIsBeingDragged = false;
                    return true;
                }
                boolean ret6 = super.dispatchTouchEvent(e);
                return ret6;
            default:
                boolean ret7 = super.dispatchTouchEvent(e);
                return ret7;
        }
    }

    protected boolean startFlingIfNeed(Float flingVelocity) {
        float velocity = flingVelocity == null ? this.mVelocityTracker.getYVelocity() : flingVelocity.floatValue();
        if (Math.abs(velocity) <= this.mMinimumVelocity) {
            return false;
        }
        if ((velocity < 0.0f && ((this.mEnableOverScrollBounce && (this.mEnableOverScrollDrag || isEnableLoadMore())) || ((this.mState == RefreshState.Loading && this.mSpinner >= 0) || (this.mEnableAutoLoadMore && isEnableLoadMore())))) || (velocity > 0.0f && ((this.mEnableOverScrollBounce && (this.mEnableOverScrollDrag || isEnableRefresh())) || (this.mState == RefreshState.Refreshing && this.mSpinner <= 0)))) {
            this.mVerticalPermit = false;
            this.mScroller.fling(0, 0, 0, (int) (-velocity), 0, 0, -2147483647, Integer.MAX_VALUE);
            this.mScroller.computeScrollOffset();
            invalidate();
        }
        if (this.mSpinner * velocity >= 0.0f || this.mState == RefreshState.TwoLevel || this.mState == this.mViceState) {
            return false;
        }
        this.animationRunnable = new FlingRunnable(velocity).start();
        return true;
    }

    protected boolean interceptByAnimator(int action) {
        if (action == 0) {
            this.animationRunnable = null;
            if (this.reboundAnimator != null) {
                if (this.mState.finishing) {
                    return true;
                }
                if (this.mState == RefreshState.PullDownCanceled) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                } else if (this.mState == RefreshState.PullUpCanceled) {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                }
                this.reboundAnimator.cancel();
                this.reboundAnimator = null;
            }
        }
        return this.reboundAnimator != null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        View target = this.mRefreshContent.getScrollableView();
        if (Build.VERSION.SDK_INT >= 21 || !(target instanceof AbsListView)) {
            if (target == null || ViewCompat.isNestedScrollingEnabled(target)) {
                super.requestDisallowInterceptTouchEvent(disallowIntercept);
            }
        }
    }

    protected void notifyStateChanged(RefreshState state) {
        RefreshState oldState = this.mState;
        if (oldState != state) {
            this.mState = state;
            this.mViceState = state;
            if (this.mRefreshFooter != null) {
                this.mRefreshFooter.onStateChanged(this, oldState, state);
            }
            if (this.mRefreshHeader != null) {
                this.mRefreshHeader.onStateChanged(this, oldState, state);
            }
            if (this.mOnMultiPurposeListener != null) {
                this.mOnMultiPurposeListener.onStateChanged(this, oldState, state);
            }
        }
    }

    protected void setStateDirectLoading() {
        if (this.mState != RefreshState.Loading) {
            this.mLastOpenTime = System.currentTimeMillis();
            this.mFooterLocked = true;
            notifyStateChanged(RefreshState.Loading);
            if (this.mLoadMoreListener != null) {
                this.mLoadMoreListener.onLoadMore(this);
            } else if (this.mOnMultiPurposeListener == null) {
                finishLoadMore(BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN);
            }
            if (this.mRefreshFooter != null) {
                this.mRefreshFooter.onStartAnimator(this, this.mFooterHeight, this.mFooterExtendHeight);
            }
            if (this.mOnMultiPurposeListener != null) {
                this.mOnMultiPurposeListener.onLoadMore(this);
                this.mOnMultiPurposeListener.onFooterStartAnimator(this.mRefreshFooter, this.mFooterHeight, this.mFooterExtendHeight);
            }
        }
    }

    protected void setStateLoading() {
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SmartRefreshLayout.this.setStateDirectLoading();
            }
        };
        notifyStateChanged(RefreshState.LoadReleased);
        ValueAnimator animator = animSpinner(-this.mFooterHeight);
        if (animator != null) {
            animator.addListener(listener);
        }
        if (this.mRefreshFooter != null) {
            this.mRefreshFooter.onReleased(this, this.mFooterHeight, this.mFooterExtendHeight);
        }
        if (this.mOnMultiPurposeListener != null) {
            this.mOnMultiPurposeListener.onFooterReleased(this.mRefreshFooter, this.mFooterHeight, this.mFooterExtendHeight);
        }
        if (animator == null) {
            listener.onAnimationEnd(null);
        }
    }

    protected void setStateRefreshing() {
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SmartRefreshLayout.this.mLastOpenTime = System.currentTimeMillis();
                SmartRefreshLayout.this.notifyStateChanged(RefreshState.Refreshing);
                if (SmartRefreshLayout.this.mRefreshListener != null) {
                    SmartRefreshLayout.this.mRefreshListener.onRefresh(SmartRefreshLayout.this);
                } else if (SmartRefreshLayout.this.mOnMultiPurposeListener == null) {
                    SmartRefreshLayout.this.finishRefresh(PushConstants.WORK_RECEIVER_EVENTCORE_ERROR);
                }
                if (SmartRefreshLayout.this.mRefreshHeader != null) {
                    SmartRefreshLayout.this.mRefreshHeader.onStartAnimator(SmartRefreshLayout.this, SmartRefreshLayout.this.mHeaderHeight, SmartRefreshLayout.this.mHeaderExtendHeight);
                }
                if (SmartRefreshLayout.this.mOnMultiPurposeListener != null) {
                    SmartRefreshLayout.this.mOnMultiPurposeListener.onRefresh(SmartRefreshLayout.this);
                    SmartRefreshLayout.this.mOnMultiPurposeListener.onHeaderStartAnimator(SmartRefreshLayout.this.mRefreshHeader, SmartRefreshLayout.this.mHeaderHeight, SmartRefreshLayout.this.mHeaderExtendHeight);
                }
            }
        };
        notifyStateChanged(RefreshState.RefreshReleased);
        ValueAnimator animator = animSpinner(this.mHeaderHeight);
        if (animator != null) {
            animator.addListener(listener);
        }
        if (this.mRefreshHeader != null) {
            this.mRefreshHeader.onReleased(this, this.mHeaderHeight, this.mHeaderExtendHeight);
        }
        if (this.mOnMultiPurposeListener != null) {
            this.mOnMultiPurposeListener.onHeaderReleased(this.mRefreshHeader, this.mHeaderHeight, this.mHeaderExtendHeight);
        }
        if (animator == null) {
            listener.onAnimationEnd(null);
        }
    }

    protected void resetStatus() {
        if (this.mState != RefreshState.None && this.mSpinner == 0) {
            notifyStateChanged(RefreshState.None);
        }
        if (this.mSpinner != 0) {
            animSpinner(0);
        }
    }

    protected void setViceState(RefreshState state) {
        if (this.mState.dragging && this.mState.isHeader() != state.isHeader()) {
            notifyStateChanged(RefreshState.None);
        }
        if (this.mViceState != state) {
            this.mViceState = state;
        }
    }

    protected class FlingRunnable implements Runnable {
        int mOffset;
        float mVelocity;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mDamping = 0.95f;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        FlingRunnable(float velocity) {
            this.mVelocity = velocity;
            this.mOffset = SmartRefreshLayout.this.mSpinner;
        }

        public Runnable start() {
            if (SmartRefreshLayout.this.mState.finishing) {
                return null;
            }
            if (SmartRefreshLayout.this.mSpinner != 0 && ((!SmartRefreshLayout.this.mState.opening && (!SmartRefreshLayout.this.mFooterNoMoreData || !SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished || !SmartRefreshLayout.this.isEnableLoadMore())) || (((SmartRefreshLayout.this.mState == RefreshState.Loading || (SmartRefreshLayout.this.mFooterNoMoreData && SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished && SmartRefreshLayout.this.isEnableLoadMore())) && SmartRefreshLayout.this.mSpinner < (-SmartRefreshLayout.this.mFooterHeight)) || (SmartRefreshLayout.this.mState == RefreshState.Refreshing && SmartRefreshLayout.this.mSpinner > SmartRefreshLayout.this.mHeaderHeight)))) {
                int frame = 0;
                int offset = SmartRefreshLayout.this.mSpinner;
                int spinner = SmartRefreshLayout.this.mSpinner;
                float velocity = this.mVelocity;
                while (spinner * offset > 0) {
                    frame++;
                    velocity = (float) (((double) velocity) * Math.pow(this.mDamping, frame));
                    float velocityFrame = velocity * ((this.mFrameDelay * 1.0f) / 1000.0f);
                    if (Math.abs(velocityFrame) < 1.0f) {
                        if (SmartRefreshLayout.this.mState.opening && ((SmartRefreshLayout.this.mState != RefreshState.Refreshing || offset <= SmartRefreshLayout.this.mHeaderHeight) && (SmartRefreshLayout.this.mState == RefreshState.Refreshing || offset >= (-SmartRefreshLayout.this.mFooterHeight)))) {
                            break;
                            break;
                        }
                        return null;
                    }
                    offset = (int) (offset + velocityFrame);
                }
            }
            SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SmartRefreshLayout.this.animationRunnable == this && !SmartRefreshLayout.this.mState.finishing) {
                double d = this.mVelocity;
                double d2 = this.mDamping;
                int i = this.mFrame + 1;
                this.mFrame = i;
                this.mVelocity = (float) (d * Math.pow(d2, i));
                long now = AnimationUtils.currentAnimationTimeMillis();
                long span = now - this.mLastTime;
                float velocity = this.mVelocity * ((span * 1.0f) / 1000.0f);
                if (Math.abs(velocity) > 1.0f) {
                    this.mLastTime = now;
                    this.mOffset = (int) (this.mOffset + velocity);
                    if (SmartRefreshLayout.this.mSpinner * this.mOffset > 0) {
                        SmartRefreshLayout.this.moveSpinner(this.mOffset, false);
                        SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
                        return;
                    }
                    SmartRefreshLayout.this.animationRunnable = null;
                    SmartRefreshLayout.this.moveSpinner(0, false);
                    SmartRefreshLayout.this.mRefreshContent.fling((int) (-this.mVelocity));
                    if (SmartRefreshLayout.this.mFooterLocked && velocity > 0.0f) {
                        SmartRefreshLayout.this.mFooterLocked = false;
                        return;
                    }
                    return;
                }
                SmartRefreshLayout.this.animationRunnable = null;
            }
        }
    }

    protected class BounceRunnable implements Runnable {
        int mSmoothDistance;
        float mVelocity;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mOffset = 0.0f;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        BounceRunnable(float velocity, int smoothDistance) {
            this.mVelocity = velocity;
            this.mSmoothDistance = smoothDistance;
            SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SmartRefreshLayout.this.animationRunnable == this && !SmartRefreshLayout.this.mState.finishing) {
                if (Math.abs(SmartRefreshLayout.this.mSpinner) >= Math.abs(this.mSmoothDistance)) {
                    if (this.mSmoothDistance != 0) {
                        double d = this.mVelocity;
                        int i = this.mFrame + 1;
                        this.mFrame = i;
                        this.mVelocity = (float) (d * Math.pow(0.44999998807907104d, i));
                    } else {
                        double d2 = this.mVelocity;
                        int i2 = this.mFrame + 1;
                        this.mFrame = i2;
                        this.mVelocity = (float) (d2 * Math.pow(0.8500000238418579d, i2));
                    }
                } else {
                    double d3 = this.mVelocity;
                    int i3 = this.mFrame + 1;
                    this.mFrame = i3;
                    this.mVelocity = (float) (d3 * Math.pow(0.949999988079071d, i3));
                }
                long now = AnimationUtils.currentAnimationTimeMillis();
                float t = ((now - this.mLastTime) * 1.0f) / 1000.0f;
                float velocity = this.mVelocity * t;
                if (Math.abs(velocity) >= 1.0f) {
                    this.mLastTime = now;
                    this.mOffset += velocity;
                    SmartRefreshLayout.this.moveSpinnerInfinitely(this.mOffset);
                    SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
                    return;
                }
                SmartRefreshLayout.this.animationRunnable = null;
                if (Math.abs(SmartRefreshLayout.this.mSpinner) >= Math.abs(this.mSmoothDistance)) {
                    int duration = Math.min(Math.max((int) DensityUtil.px2dp(Math.abs(SmartRefreshLayout.this.mSpinner - this.mSmoothDistance)), 30), 100) * 10;
                    SmartRefreshLayout.this.animSpinner(this.mSmoothDistance, 0, SmartRefreshLayout.this.mReboundInterpolator, duration);
                }
            }
        }
    }

    protected ValueAnimator animSpinner(int endSpinner) {
        return animSpinner(endSpinner, 0, this.mReboundInterpolator, this.mReboundDuration);
    }

    protected ValueAnimator animSpinner(int endSpinner, int startDelay, Interpolator interpolator, int duration) {
        if (this.mSpinner == endSpinner) {
            return null;
        }
        if (this.reboundAnimator != null) {
            this.reboundAnimator.cancel();
        }
        this.animationRunnable = null;
        this.reboundAnimator = ValueAnimator.ofInt(this.mSpinner, endSpinner);
        this.reboundAnimator.setDuration(duration);
        this.reboundAnimator.setInterpolator(interpolator);
        this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SmartRefreshLayout.this.reboundAnimator = null;
                if (SmartRefreshLayout.this.mSpinner == 0) {
                    if (SmartRefreshLayout.this.mState != RefreshState.None && !SmartRefreshLayout.this.mState.opening) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                        return;
                    }
                    return;
                }
                if (SmartRefreshLayout.this.mState != SmartRefreshLayout.this.mViceState) {
                    SmartRefreshLayout.this.setViceState(SmartRefreshLayout.this.mState);
                }
            }
        });
        this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                SmartRefreshLayout.this.moveSpinner(((Integer) animation.getAnimatedValue()).intValue(), true);
            }
        });
        this.reboundAnimator.setStartDelay(startDelay);
        this.reboundAnimator.start();
        return this.reboundAnimator;
    }

    protected void animSpinnerBounce(float velocity) {
        if (this.reboundAnimator == null) {
            if (velocity > 0.0f && (this.mState == RefreshState.Refreshing || this.mState == RefreshState.TwoLevel)) {
                this.animationRunnable = new BounceRunnable(velocity, this.mHeaderHeight);
                return;
            }
            if (velocity < 0.0f && (this.mState == RefreshState.Loading || ((this.mEnableFooterFollowWhenLoadFinished && this.mFooterNoMoreData && isEnableLoadMore()) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableLoadMore() && this.mState != RefreshState.Refreshing)))) {
                this.animationRunnable = new BounceRunnable(velocity, -this.mFooterHeight);
            } else if (this.mSpinner == 0 && this.mEnableOverScrollBounce) {
                this.animationRunnable = new BounceRunnable(velocity, 0);
            }
        }
    }

    protected void overSpinner() {
        if (this.mState == RefreshState.TwoLevel) {
            if (this.mVelocityTracker.getYVelocity() > -1000.0f && this.mSpinner > getMeasuredHeight() / 2) {
                ValueAnimator animator = animSpinner(getMeasuredHeight());
                if (animator != null) {
                    animator.setDuration(this.mFloorDuration);
                    return;
                }
                return;
            }
            if (this.mIsBeingDragged) {
                this.mKernel.finishTwoLevel();
                return;
            }
            return;
        }
        if (this.mState == RefreshState.Loading || (this.mEnableFooterFollowWhenLoadFinished && this.mFooterNoMoreData && this.mSpinner < 0 && isEnableLoadMore())) {
            if (this.mSpinner < (-this.mFooterHeight)) {
                animSpinner(-this.mFooterHeight);
                return;
            } else {
                if (this.mSpinner > 0) {
                    animSpinner(0);
                    return;
                }
                return;
            }
        }
        if (this.mState == RefreshState.Refreshing) {
            if (this.mSpinner > this.mHeaderHeight) {
                animSpinner(this.mHeaderHeight);
                return;
            } else {
                if (this.mSpinner < 0) {
                    animSpinner(0);
                    return;
                }
                return;
            }
        }
        if (this.mState == RefreshState.PullDownToRefresh) {
            this.mKernel.setState(RefreshState.PullDownCanceled);
            return;
        }
        if (this.mState == RefreshState.PullUpToLoad) {
            this.mKernel.setState(RefreshState.PullUpCanceled);
            return;
        }
        if (this.mState == RefreshState.ReleaseToRefresh) {
            setStateRefreshing();
            return;
        }
        if (this.mState == RefreshState.ReleaseToLoad) {
            setStateLoading();
        } else if (this.mState == RefreshState.ReleaseToTwoLevel) {
            this.mKernel.setState(RefreshState.TwoLevelReleased);
        } else if (this.mSpinner != 0) {
            animSpinner(0);
        }
    }

    protected void moveSpinnerInfinitely(float spinner) {
        if (this.mState == RefreshState.TwoLevel && spinner > 0.0f) {
            moveSpinner(Math.min((int) spinner, getMeasuredHeight()), false);
        } else if (this.mState != RefreshState.Refreshing || spinner < 0.0f) {
            if (spinner >= 0.0f || !(this.mState == RefreshState.Loading || ((this.mEnableFooterFollowWhenLoadFinished && this.mFooterNoMoreData && isEnableLoadMore()) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableLoadMore())))) {
                if (spinner >= 0.0f) {
                    double M = this.mHeaderExtendHeight + this.mHeaderHeight;
                    double H = Math.max(this.mScreenHeightPixels / 2, getHeight());
                    double x = Math.max(0.0f, this.mDragRate * spinner);
                    double d = -x;
                    if (H == 0.0d) {
                        H = 1.0d;
                    }
                    double y = Math.min((1.0d - Math.pow(100.0d, d / H)) * M, x);
                    moveSpinner((int) y, false);
                } else {
                    double M2 = this.mFooterExtendHeight + this.mFooterHeight;
                    double H2 = Math.max(this.mScreenHeightPixels / 2, getHeight());
                    double x2 = -Math.min(0.0f, this.mDragRate * spinner);
                    double d2 = -x2;
                    if (H2 == 0.0d) {
                        H2 = 1.0d;
                    }
                    double y2 = -Math.min((1.0d - Math.pow(100.0d, d2 / H2)) * M2, x2);
                    moveSpinner((int) y2, false);
                }
            } else if (spinner > (-this.mFooterHeight)) {
                moveSpinner((int) spinner, false);
            } else {
                double M3 = this.mFooterExtendHeight;
                double H3 = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight()) - this.mFooterHeight;
                double x3 = -Math.min(0.0f, (this.mFooterHeight + spinner) * this.mDragRate);
                double d3 = -x3;
                if (H3 == 0.0d) {
                    H3 = 1.0d;
                }
                double y3 = -Math.min((1.0d - Math.pow(100.0d, d3 / H3)) * M3, x3);
                moveSpinner(((int) y3) - this.mFooterHeight, false);
            }
        } else if (spinner < this.mHeaderHeight) {
            moveSpinner((int) spinner, false);
        } else {
            double M4 = this.mHeaderExtendHeight;
            double H4 = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight()) - this.mHeaderHeight;
            double x4 = Math.max(0.0f, (spinner - this.mHeaderHeight) * this.mDragRate);
            double d4 = -x4;
            if (H4 == 0.0d) {
                H4 = 1.0d;
            }
            double y4 = Math.min((1.0d - Math.pow(100.0d, d4 / H4)) * M4, x4);
            moveSpinner(((int) y4) + this.mHeaderHeight, false);
        }
        if (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableLoadMore() && spinner < 0.0f && this.mState != RefreshState.Refreshing && this.mState != RefreshState.Loading && this.mState != RefreshState.LoadFinish) {
            setStateDirectLoading();
            if (this.mDisableContentWhenLoading) {
                this.animationRunnable = null;
                animSpinner(-this.mFooterHeight);
            }
        }
    }

    protected void moveSpinner(int spinner, boolean isAnimator) {
        if (this.mSpinner != spinner || ((this.mRefreshHeader != null && this.mRefreshHeader.isSupportHorizontalDrag()) || (this.mRefreshFooter != null && this.mRefreshFooter.isSupportHorizontalDrag()))) {
            int oldSpinner = this.mSpinner;
            this.mSpinner = spinner;
            if (!isAnimator && this.mViceState.dragging) {
                if (this.mSpinner > this.mHeaderHeight * this.mHeaderTriggerRate) {
                    if (this.mState != RefreshState.ReleaseToTwoLevel) {
                        this.mKernel.setState(RefreshState.ReleaseToRefresh);
                    }
                } else if ((-this.mSpinner) > this.mFooterHeight * this.mFooterTriggerRate && !this.mFooterNoMoreData) {
                    this.mKernel.setState(RefreshState.ReleaseToLoad);
                } else if (this.mSpinner < 0 && !this.mFooterNoMoreData) {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                } else if (this.mSpinner > 0) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                }
            }
            if (this.mRefreshContent != null) {
                Integer tSpinner = null;
                if (spinner >= 0 && this.mRefreshHeader != null) {
                    if (this.mEnableHeaderTranslationContent || this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                        tSpinner = Integer.valueOf(spinner);
                    } else if (oldSpinner < 0) {
                        tSpinner = 0;
                    }
                }
                if (spinner <= 0 && this.mRefreshFooter != null) {
                    if (this.mEnableFooterTranslationContent || this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                        tSpinner = Integer.valueOf(spinner);
                    } else if (oldSpinner > 0) {
                        tSpinner = 0;
                    }
                }
                if (tSpinner != null) {
                    this.mRefreshContent.moveSpinner(tSpinner.intValue());
                    boolean header = this.mEnableClipHeaderWhenFixedBehind && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind;
                    boolean header2 = header || this.mHeaderBackgroundColor != 0;
                    boolean footer = this.mEnableClipFooterWhenFixedBehind && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind;
                    boolean footer2 = footer || this.mFooterBackgroundColor != 0;
                    if ((header2 && (tSpinner.intValue() >= 0 || oldSpinner > 0)) || (footer2 && (tSpinner.intValue() <= 0 || oldSpinner < 0))) {
                        invalidate();
                    }
                }
            }
            if ((spinner >= 0 || oldSpinner > 0) && this.mRefreshHeader != null) {
                int offset = Math.max(spinner, 0);
                int headerHeight = this.mHeaderHeight;
                int extendHeight = this.mHeaderExtendHeight;
                float percent = (offset * 1.0f) / (this.mHeaderHeight == 0 ? 1 : this.mHeaderHeight);
                if (isEnableRefresh() || (this.mState == RefreshState.RefreshFinish && isAnimator)) {
                    if (oldSpinner != this.mSpinner) {
                        if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                            this.mRefreshHeader.getView().setTranslationY(this.mSpinner);
                        } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale) {
                            this.mRefreshHeader.getView().requestLayout();
                        }
                        if (isAnimator) {
                            this.mRefreshHeader.onReleasing(percent, offset, headerHeight, extendHeight);
                        }
                    }
                    if (!isAnimator) {
                        if (this.mRefreshHeader.isSupportHorizontalDrag()) {
                            int offsetX = (int) this.mLastTouchX;
                            int offsetMax = getWidth();
                            float percentX = this.mLastTouchX / (offsetMax == 0 ? 1 : offsetMax);
                            this.mRefreshHeader.onHorizontalDrag(percentX, offsetX, offsetMax);
                            this.mRefreshHeader.onPulling(percent, offset, headerHeight, extendHeight);
                        } else if (oldSpinner != this.mSpinner) {
                            this.mRefreshHeader.onPulling(percent, offset, headerHeight, extendHeight);
                        }
                    }
                }
                if (oldSpinner != this.mSpinner && this.mOnMultiPurposeListener != null) {
                    if (isAnimator) {
                        this.mOnMultiPurposeListener.onHeaderReleasing(this.mRefreshHeader, percent, offset, headerHeight, extendHeight);
                    } else {
                        this.mOnMultiPurposeListener.onHeaderPulling(this.mRefreshHeader, percent, offset, headerHeight, extendHeight);
                    }
                }
            }
            if ((spinner <= 0 || oldSpinner < 0) && this.mRefreshFooter != null) {
                int offset2 = -Math.min(spinner, 0);
                int footerHeight = this.mFooterHeight;
                int extendHeight2 = this.mFooterExtendHeight;
                float percent2 = (1.0f * offset2) / (this.mFooterHeight == 0 ? 1 : this.mFooterHeight);
                if (isEnableLoadMore() || (this.mState == RefreshState.LoadFinish && isAnimator)) {
                    if (oldSpinner != this.mSpinner) {
                        if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                            this.mRefreshFooter.getView().setTranslationY(this.mSpinner);
                        } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale) {
                            this.mRefreshFooter.getView().requestLayout();
                        }
                        if (isAnimator) {
                            this.mRefreshFooter.onReleasing(percent2, offset2, footerHeight, extendHeight2);
                        }
                    }
                    if (!isAnimator) {
                        if (this.mRefreshFooter.isSupportHorizontalDrag()) {
                            int offsetX2 = (int) this.mLastTouchX;
                            int offsetMax2 = getWidth();
                            float percentX2 = this.mLastTouchX / (offsetMax2 == 0 ? 1 : offsetMax2);
                            this.mRefreshFooter.onHorizontalDrag(percentX2, offsetX2, offsetMax2);
                            this.mRefreshFooter.onPulling(percent2, offset2, footerHeight, extendHeight2);
                        } else if (oldSpinner != this.mSpinner) {
                            this.mRefreshFooter.onPulling(percent2, offset2, footerHeight, extendHeight2);
                        }
                    }
                }
                if (oldSpinner != this.mSpinner && this.mOnMultiPurposeListener != null) {
                    if (isAnimator) {
                        this.mOnMultiPurposeListener.onFooterReleasing(this.mRefreshFooter, percent2, offset2, footerHeight, extendHeight2);
                    } else {
                        this.mOnMultiPurposeListener.onFooterPulling(this.mRefreshFooter, percent2, offset2, footerHeight, extendHeight2);
                    }
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int backgroundColor;
        public SpinnerStyle spinnerStyle;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SmartRefreshLayout_Layout);
            this.backgroundColor = ta.getColor(R.styleable.SmartRefreshLayout_Layout_layout_srlBackgroundColor, this.backgroundColor);
            if (ta.hasValue(R.styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle)) {
                this.spinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle, SpinnerStyle.Translate.ordinal())];
            }
            ta.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
        }
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.mNestedParent.getNestedScrollAxes();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        boolean accepted = isEnabled() && isNestedScrollingEnabled() && (nestedScrollAxes & 2) != 0;
        return accepted && (this.mEnableOverScrollDrag || isEnableRefresh() || isEnableLoadMore());
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onNestedScrollAccepted(View child, View target, int axes) {
        this.mNestedParent.onNestedScrollAccepted(child, target, axes);
        this.mNestedChild.startNestedScroll(axes & 2);
        this.mTotalUnconsumed = this.mSpinner;
        this.mNestedInProgress = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int consumedY = 0;
        if (this.mTotalUnconsumed * dy > 0) {
            if (Math.abs(dy) > Math.abs(this.mTotalUnconsumed)) {
                consumedY = this.mTotalUnconsumed;
                this.mTotalUnconsumed = 0;
            } else {
                consumedY = dy;
                this.mTotalUnconsumed -= dy;
            }
            moveSpinnerInfinitely(this.mTotalUnconsumed);
            if (this.mViceState.opening || this.mViceState == RefreshState.None) {
                if (this.mSpinner > 0) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                } else {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                }
            }
        } else if (dy > 0 && this.mFooterLocked) {
            consumedY = dy;
            this.mTotalUnconsumed -= dy;
            moveSpinnerInfinitely(this.mTotalUnconsumed);
        }
        this.mNestedChild.dispatchNestedPreScroll(dx, dy - consumedY, consumed, null);
        consumed[1] = consumed[1] + consumedY;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        this.mNestedChild.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, this.mParentOffsetInWindow);
        int dy = dyUnconsumed + this.mParentOffsetInWindow[1];
        if (dy != 0) {
            if (this.mEnableOverScrollDrag || ((dy < 0 && isEnableRefresh()) || (dy > 0 && isEnableLoadMore()))) {
                if (this.mViceState == RefreshState.None) {
                    this.mKernel.setState(dy > 0 ? RefreshState.PullUpToLoad : RefreshState.PullDownToRefresh);
                }
                int i = this.mTotalUnconsumed - dy;
                this.mTotalUnconsumed = i;
                moveSpinnerInfinitely(i);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return (this.mFooterLocked && velocityY > 0.0f) || startFlingIfNeed(Float.valueOf(-velocityY)) || this.mNestedChild.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return this.mNestedChild.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onStopNestedScroll(View target) {
        this.mNestedParent.onStopNestedScroll(target);
        this.mNestedInProgress = false;
        this.mTotalUnconsumed = 0;
        overSpinner();
        this.mNestedChild.stopNestedScroll();
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean enabled) {
        this.mManualNestedScrolling = true;
        this.mNestedChild.setNestedScrollingEnabled(enabled);
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.mNestedChild.isNestedScrollingEnabled();
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public SmartRefreshLayout setEnableRefresh(boolean enabled) {
        this.mEnableRefresh = enabled;
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public SmartRefreshLayout setEnableAutoLoadMore(boolean enabled) {
        this.mEnableAutoLoadMore = enabled;
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public SmartRefreshLayout setEnableOverScrollDrag(boolean enabled) {
        this.mEnableOverScrollDrag = enabled;
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public RefreshLayout setEnableNestedScroll(boolean enabled) {
        setNestedScrollingEnabled(enabled);
        return this;
    }

    public SmartRefreshLayout setRefreshHeader(RefreshHeader header) {
        return setRefreshHeader(header, -1, -2);
    }

    public SmartRefreshLayout setRefreshHeader(RefreshHeader header, int width, int height) {
        if (this.mRefreshHeader != null) {
            removeView(this.mRefreshHeader.getView());
        }
        this.mRefreshHeader = header;
        this.mHeaderBackgroundColor = 0;
        this.mHeaderNeedTouchEventWhenRefreshing = false;
        this.mHeaderHeightStatus = this.mHeaderHeightStatus.unNotify();
        if (header.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            addView(this.mRefreshHeader.getView(), 0, new LayoutParams(width, height));
        } else {
            addView(this.mRefreshHeader.getView(), width, height);
        }
        return this;
    }

    public SmartRefreshLayout setRefreshFooter(RefreshFooter footer) {
        return setRefreshFooter(footer, -1, -2);
    }

    public SmartRefreshLayout setRefreshFooter(RefreshFooter footer, int width, int height) {
        if (this.mRefreshFooter != null) {
            removeView(this.mRefreshFooter.getView());
        }
        this.mRefreshFooter = footer;
        this.mFooterBackgroundColor = 0;
        this.mFooterNeedTouchEventWhenLoading = false;
        this.mFooterHeightStatus = this.mFooterHeightStatus.unNotify();
        this.mEnableLoadMore = !this.mManualLoadMore || this.mEnableLoadMore;
        if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            addView(this.mRefreshFooter.getView(), 0, new LayoutParams(width, height));
        } else {
            addView(this.mRefreshFooter.getView(), width, height);
        }
        return this;
    }

    public RefreshFooter getRefreshFooter() {
        return this.mRefreshFooter;
    }

    public RefreshHeader getRefreshHeader() {
        return this.mRefreshHeader;
    }

    public RefreshState getState() {
        return this.mState;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public SmartRefreshLayout getLayout() {
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public SmartRefreshLayout setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
        return this;
    }

    public SmartRefreshLayout setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
        this.mEnableLoadMore = this.mEnableLoadMore || !(this.mManualLoadMore || listener == null);
        return this;
    }

    public SmartRefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        this.mRefreshListener = listener;
        this.mLoadMoreListener = listener;
        this.mEnableLoadMore = this.mEnableLoadMore || !(this.mManualLoadMore || listener == null);
        return this;
    }

    public SmartRefreshLayout setNoMoreData(boolean noMoreData) {
        this.mFooterNoMoreData = noMoreData;
        if (this.mRefreshFooter != null && !this.mRefreshFooter.setNoMoreData(noMoreData)) {
            System.out.println("Footer:" + this.mRefreshFooter + " Prompt completion is not supported.(不支持提示完成)");
        }
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public SmartRefreshLayout finishRefresh() {
        long passTime = System.currentTimeMillis() - this.mLastOpenTime;
        return finishRefresh(Math.max(0, 1000 - ((int) passTime)));
    }

    public SmartRefreshLayout finishLoadMore() {
        long passTime = System.currentTimeMillis() - this.mLastOpenTime;
        return finishLoadMore(Math.max(0, 1000 - ((int) passTime)));
    }

    public SmartRefreshLayout finishRefresh(int delayed) {
        return finishRefresh(delayed, true);
    }

    public SmartRefreshLayout finishRefresh(boolean success) {
        long passTime = System.currentTimeMillis() - this.mLastOpenTime;
        return finishRefresh(success ? Math.max(0, 1000 - ((int) passTime)) : 0, success);
    }

    public SmartRefreshLayout finishRefresh(int delayed, final boolean success) {
        postDelayed(new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.8
            @Override // java.lang.Runnable
            public void run() {
                if (SmartRefreshLayout.this.mState == RefreshState.Refreshing && SmartRefreshLayout.this.mRefreshHeader != null && SmartRefreshLayout.this.mRefreshContent != null) {
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshFinish);
                    int startDelay = SmartRefreshLayout.this.mRefreshHeader.onFinish(SmartRefreshLayout.this, success);
                    if (SmartRefreshLayout.this.mOnMultiPurposeListener != null) {
                        SmartRefreshLayout.this.mOnMultiPurposeListener.onHeaderFinish(SmartRefreshLayout.this.mRefreshHeader, success);
                    }
                    if (startDelay < Integer.MAX_VALUE) {
                        if (SmartRefreshLayout.this.mIsBeingDragged) {
                            SmartRefreshLayout.this.mTouchSpinner = 0;
                            SmartRefreshLayout.this.mTouchY = SmartRefreshLayout.this.mLastTouchY;
                            SmartRefreshLayout.this.mIsBeingDragged = false;
                            long time = System.currentTimeMillis();
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, time, 0, SmartRefreshLayout.this.mLastTouchX, (SmartRefreshLayout.this.mTouchY + SmartRefreshLayout.this.mSpinner) - (SmartRefreshLayout.this.mTouchSlop * 2), 0));
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, time, 2, SmartRefreshLayout.this.mLastTouchX, SmartRefreshLayout.this.mTouchY + SmartRefreshLayout.this.mSpinner, 0));
                        }
                        if (SmartRefreshLayout.this.mSpinner > 0) {
                            ValueAnimator.AnimatorUpdateListener updateListener = null;
                            ValueAnimator valueAnimator = SmartRefreshLayout.this.animSpinner(0, startDelay, SmartRefreshLayout.this.mReboundInterpolator, SmartRefreshLayout.this.mReboundDuration);
                            if (SmartRefreshLayout.this.mEnableScrollContentWhenRefreshed) {
                                updateListener = SmartRefreshLayout.this.mRefreshContent.scrollContentWhenFinished(SmartRefreshLayout.this.mSpinner);
                            }
                            if (valueAnimator != null && updateListener != null) {
                                valueAnimator.addUpdateListener(updateListener);
                                return;
                            }
                            return;
                        }
                        if (SmartRefreshLayout.this.mSpinner < 0) {
                            SmartRefreshLayout.this.animSpinner(0, startDelay, SmartRefreshLayout.this.mReboundInterpolator, SmartRefreshLayout.this.mReboundDuration);
                        } else {
                            SmartRefreshLayout.this.moveSpinner(0, true);
                            SmartRefreshLayout.this.resetStatus();
                        }
                    }
                }
            }
        }, delayed <= 0 ? 1L : delayed);
        return this;
    }

    public SmartRefreshLayout finishLoadMore(int delayed) {
        return finishLoadMore(delayed, true, false);
    }

    /* JADX INFO: renamed from: com.scwang.smartrefresh.layout.SmartRefreshLayout$9, reason: invalid class name */
    class AnonymousClass9 implements Runnable {
        final /* synthetic */ boolean val$noMoreData;
        final /* synthetic */ boolean val$success;

        AnonymousClass9(boolean z, boolean z2) {
            this.val$success = z;
            this.val$noMoreData = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SmartRefreshLayout.this.mState == RefreshState.Loading && SmartRefreshLayout.this.mRefreshFooter != null && SmartRefreshLayout.this.mRefreshContent != null) {
                SmartRefreshLayout.this.notifyStateChanged(RefreshState.LoadFinish);
                int startDelay = SmartRefreshLayout.this.mRefreshFooter.onFinish(SmartRefreshLayout.this, this.val$success);
                if (SmartRefreshLayout.this.mOnMultiPurposeListener != null) {
                    SmartRefreshLayout.this.mOnMultiPurposeListener.onFooterFinish(SmartRefreshLayout.this.mRefreshFooter, this.val$success);
                }
                if (startDelay < Integer.MAX_VALUE) {
                    boolean needHoldFooter = this.val$noMoreData && SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished && SmartRefreshLayout.this.mSpinner < 0 && SmartRefreshLayout.this.mRefreshContent.canLoadMore();
                    final int offset = SmartRefreshLayout.this.mSpinner - (needHoldFooter ? Math.max(SmartRefreshLayout.this.mSpinner, -SmartRefreshLayout.this.mFooterHeight) : 0);
                    if (SmartRefreshLayout.this.mIsBeingDragged) {
                        SmartRefreshLayout.this.mTouchSpinner = SmartRefreshLayout.this.mSpinner - offset;
                        SmartRefreshLayout.this.mTouchY = SmartRefreshLayout.this.mLastTouchY;
                        SmartRefreshLayout.this.mIsBeingDragged = false;
                        long time = System.currentTimeMillis();
                        SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, time, 0, SmartRefreshLayout.this.mLastTouchX, SmartRefreshLayout.this.mTouchY + offset + (SmartRefreshLayout.this.mTouchSlop * 2), 0));
                        SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, time, 2, SmartRefreshLayout.this.mLastTouchX, SmartRefreshLayout.this.mTouchY + offset, 0));
                    }
                    SmartRefreshLayout.this.postDelayed(new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ValueAnimator.AnimatorUpdateListener updateListener = null;
                            if (SmartRefreshLayout.this.mEnableScrollContentWhenLoaded && offset < 0) {
                                updateListener = SmartRefreshLayout.this.mRefreshContent.scrollContentWhenFinished(SmartRefreshLayout.this.mSpinner);
                            }
                            if (updateListener != null) {
                                updateListener.onAnimationUpdate(ValueAnimator.ofInt(0, 0));
                            }
                            ValueAnimator animator = null;
                            AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.9.1.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationCancel(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animation) {
                                    SmartRefreshLayout.this.mFooterLocked = false;
                                    if (AnonymousClass9.this.val$noMoreData) {
                                        SmartRefreshLayout.this.setNoMoreData(true);
                                    }
                                    if (SmartRefreshLayout.this.mState == RefreshState.LoadFinish) {
                                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                                    }
                                }
                            };
                            if (SmartRefreshLayout.this.mSpinner > 0) {
                                animator = SmartRefreshLayout.this.animSpinner(0);
                            } else if (updateListener != null || SmartRefreshLayout.this.mSpinner == 0) {
                                if (SmartRefreshLayout.this.reboundAnimator != null) {
                                    SmartRefreshLayout.this.reboundAnimator.cancel();
                                    SmartRefreshLayout.this.reboundAnimator = null;
                                }
                                SmartRefreshLayout.this.moveSpinner(0, true);
                                SmartRefreshLayout.this.resetStatus();
                            } else if (AnonymousClass9.this.val$noMoreData && SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished) {
                                if (SmartRefreshLayout.this.mSpinner >= (-SmartRefreshLayout.this.mFooterHeight)) {
                                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                                } else {
                                    animator = SmartRefreshLayout.this.animSpinner(-SmartRefreshLayout.this.mFooterHeight);
                                }
                            } else {
                                animator = SmartRefreshLayout.this.animSpinner(0);
                            }
                            if (animator != null) {
                                animator.addListener(listenerAdapter);
                            } else {
                                listenerAdapter.onAnimationEnd(null);
                            }
                        }
                    }, SmartRefreshLayout.this.mSpinner < 0 ? startDelay : 0L);
                    return;
                }
                return;
            }
            if (this.val$noMoreData) {
                SmartRefreshLayout.this.setNoMoreData(true);
            }
        }
    }

    public SmartRefreshLayout finishLoadMore(int delayed, boolean success, boolean noMoreData) {
        postDelayed(new AnonymousClass9(success, noMoreData), delayed <= 0 ? 1L : delayed);
        return this;
    }

    public SmartRefreshLayout finishLoadMoreWithNoMoreData() {
        long passTime = System.currentTimeMillis() - this.mLastOpenTime;
        return finishLoadMore(Math.max(0, 1000 - ((int) passTime)), true, true);
    }

    public boolean autoLoadMore() {
        return autoLoadMore(0);
    }

    public boolean autoLoadMore(int delayed) {
        return autoLoadMore(delayed, this.mReboundDuration, ((this.mFooterHeight + (this.mFooterExtendHeight / 2)) * 1.0f) / (this.mFooterHeight == 0 ? 1 : this.mFooterHeight));
    }

    public boolean autoLoadMore(int delayed, final int duration, final float dragRate) {
        if (this.mState == RefreshState.None && isEnableLoadMore() && !this.mFooterNoMoreData) {
            if (this.reboundAnimator != null) {
                this.reboundAnimator.cancel();
            }
            Runnable runnable = new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.11
                @Override // java.lang.Runnable
                public void run() {
                    SmartRefreshLayout.this.reboundAnimator = ValueAnimator.ofInt(SmartRefreshLayout.this.mSpinner, -((int) (SmartRefreshLayout.this.mFooterHeight * dragRate)));
                    SmartRefreshLayout.this.reboundAnimator.setDuration(duration);
                    SmartRefreshLayout.this.reboundAnimator.setInterpolator(new DecelerateInterpolator());
                    SmartRefreshLayout.this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.11.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            SmartRefreshLayout.this.moveSpinner(((Integer) animation.getAnimatedValue()).intValue(), false);
                        }
                    });
                    SmartRefreshLayout.this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.11.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            SmartRefreshLayout.this.mLastTouchX = SmartRefreshLayout.this.getMeasuredWidth() / 2;
                            SmartRefreshLayout.this.mKernel.setState(RefreshState.PullUpToLoad);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            SmartRefreshLayout.this.reboundAnimator = null;
                            if (SmartRefreshLayout.this.mState != RefreshState.ReleaseToLoad) {
                                SmartRefreshLayout.this.mKernel.setState(RefreshState.ReleaseToLoad);
                            }
                            if (SmartRefreshLayout.this.mEnableAutoLoadMore) {
                                SmartRefreshLayout.this.mEnableAutoLoadMore = false;
                                SmartRefreshLayout.this.overSpinner();
                                SmartRefreshLayout.this.mEnableAutoLoadMore = true;
                                return;
                            }
                            SmartRefreshLayout.this.overSpinner();
                        }
                    });
                    SmartRefreshLayout.this.reboundAnimator.start();
                }
            };
            if (delayed > 0) {
                this.reboundAnimator = new ValueAnimator();
                postDelayed(runnable, delayed);
            } else {
                runnable.run();
            }
            return true;
        }
        return false;
    }

    public boolean isEnableRefresh() {
        return this.mEnableRefresh && !this.mEnablePureScrollMode;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public boolean isEnableLoadMore() {
        return this.mEnableLoadMore && !this.mEnablePureScrollMode;
    }

    public static void setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator creator) {
        sHeaderCreator = creator;
    }

    public static void setDefaultRefreshFooterCreator(DefaultRefreshFooterCreator creator) {
        sFooterCreator = creator;
        sManualFooterCreator = true;
    }

    public class RefreshKernelImpl implements RefreshKernel {
        public RefreshKernelImpl() {
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshLayout getRefreshLayout() {
            return SmartRefreshLayout.this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel setState(RefreshState state) {
            switch (state) {
                case None:
                    SmartRefreshLayout.this.resetStatus();
                    break;
                case PullDownToRefresh:
                    if (!SmartRefreshLayout.this.mState.opening && SmartRefreshLayout.this.isEnableRefresh()) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownToRefresh);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullDownToRefresh);
                    }
                    break;
                case PullUpToLoad:
                    if (SmartRefreshLayout.this.isEnableLoadMore() && !SmartRefreshLayout.this.mState.opening && !SmartRefreshLayout.this.mState.finishing && (!SmartRefreshLayout.this.mFooterNoMoreData || !SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullUpToLoad);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullUpToLoad);
                    }
                    break;
                case PullDownCanceled:
                    if (!SmartRefreshLayout.this.mState.opening && SmartRefreshLayout.this.isEnableRefresh()) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownCanceled);
                        SmartRefreshLayout.this.resetStatus();
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullDownCanceled);
                    }
                    break;
                case PullUpCanceled:
                    if (SmartRefreshLayout.this.isEnableLoadMore() && !SmartRefreshLayout.this.mState.opening && (!SmartRefreshLayout.this.mFooterNoMoreData || !SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullUpCanceled);
                        SmartRefreshLayout.this.resetStatus();
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullUpCanceled);
                    }
                    break;
                case ReleaseToRefresh:
                    if (!SmartRefreshLayout.this.mState.opening && SmartRefreshLayout.this.isEnableRefresh()) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToRefresh);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToRefresh);
                    }
                    break;
                case ReleaseToLoad:
                    if (SmartRefreshLayout.this.isEnableLoadMore() && !SmartRefreshLayout.this.mState.opening && !SmartRefreshLayout.this.mState.finishing && (!SmartRefreshLayout.this.mFooterNoMoreData || !SmartRefreshLayout.this.mEnableFooterFollowWhenLoadFinished)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToLoad);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToLoad);
                    }
                    break;
                case ReleaseToTwoLevel:
                    if (!SmartRefreshLayout.this.mState.opening && SmartRefreshLayout.this.isEnableRefresh()) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToTwoLevel);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToTwoLevel);
                    }
                    break;
                case RefreshReleased:
                    if (!SmartRefreshLayout.this.mState.opening && SmartRefreshLayout.this.isEnableRefresh()) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshReleased);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.RefreshReleased);
                    }
                    break;
                case LoadReleased:
                    if (!SmartRefreshLayout.this.mState.opening && SmartRefreshLayout.this.isEnableLoadMore()) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.LoadReleased);
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.LoadReleased);
                    }
                    break;
                case Refreshing:
                    SmartRefreshLayout.this.setStateRefreshing();
                    break;
                case Loading:
                    SmartRefreshLayout.this.setStateLoading();
                    break;
                case RefreshFinish:
                    if (SmartRefreshLayout.this.mState == RefreshState.Refreshing) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshFinish);
                    }
                    break;
                case LoadFinish:
                    if (SmartRefreshLayout.this.mState == RefreshState.Loading) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.LoadFinish);
                    }
                    break;
                case TwoLevelReleased:
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.TwoLevelReleased);
                    break;
                case TwoLevelFinish:
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.TwoLevelFinish);
                    break;
                case TwoLevel:
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.TwoLevel);
                    break;
            }
            return null;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel finishTwoLevel() {
            if (SmartRefreshLayout.this.mState == RefreshState.TwoLevel) {
                SmartRefreshLayout.this.mKernel.setState(RefreshState.TwoLevelFinish);
                if (SmartRefreshLayout.this.mSpinner == 0) {
                    moveSpinner(0, true);
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                } else {
                    SmartRefreshLayout.this.animSpinner(0).setDuration(SmartRefreshLayout.this.mFloorDuration);
                }
            }
            return this;
        }

        public RefreshKernel moveSpinner(int spinner, boolean isAnimator) {
            SmartRefreshLayout.this.moveSpinner(spinner, isAnimator);
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestDrawBackgroundForHeader(int backgroundColor) {
            if (SmartRefreshLayout.this.mPaint == null && backgroundColor != 0) {
                SmartRefreshLayout.this.mPaint = new Paint();
            }
            SmartRefreshLayout.this.mHeaderBackgroundColor = backgroundColor;
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestDrawBackgroundForFooter(int backgroundColor) {
            if (SmartRefreshLayout.this.mPaint == null && backgroundColor != 0) {
                SmartRefreshLayout.this.mPaint = new Paint();
            }
            SmartRefreshLayout.this.mFooterBackgroundColor = backgroundColor;
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestNeedTouchEventWhenRefreshing(boolean request) {
            SmartRefreshLayout.this.mHeaderNeedTouchEventWhenRefreshing = request;
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestNeedTouchEventWhenLoading(boolean request) {
            SmartRefreshLayout.this.mFooterNeedTouchEventWhenLoading = request;
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestRemeasureHeightForHeader() {
            if (SmartRefreshLayout.this.mHeaderHeightStatus.notified) {
                SmartRefreshLayout.this.mHeaderHeightStatus = SmartRefreshLayout.this.mHeaderHeightStatus.unNotify();
            }
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestRemeasureHeightForFooter() {
            if (SmartRefreshLayout.this.mFooterHeightStatus.notified) {
                SmartRefreshLayout.this.mFooterHeightStatus = SmartRefreshLayout.this.mFooterHeightStatus.unNotify();
            }
            return this;
        }
    }

    @Override // android.view.View
    public boolean post(Runnable action) {
        if (this.mHandler == null) {
            this.mListDelayedRunnable = this.mListDelayedRunnable == null ? new ArrayList<>() : this.mListDelayedRunnable;
            this.mListDelayedRunnable.add(new DelayedRunnable(action));
            return false;
        }
        return this.mHandler.post(new DelayedRunnable(action));
    }

    @Override // android.view.View
    public boolean postDelayed(Runnable action, long delayMillis) {
        if (delayMillis == 0) {
            new DelayedRunnable(action).run();
            return true;
        }
        if (this.mHandler == null) {
            this.mListDelayedRunnable = this.mListDelayedRunnable == null ? new ArrayList<>() : this.mListDelayedRunnable;
            this.mListDelayedRunnable.add(new DelayedRunnable(action, delayMillis));
            return false;
        }
        return this.mHandler.postDelayed(new DelayedRunnable(action), delayMillis);
    }
}
