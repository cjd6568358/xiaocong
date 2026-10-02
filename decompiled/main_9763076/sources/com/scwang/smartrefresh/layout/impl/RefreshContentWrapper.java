package com.scwang.smartrefresh.layout.impl;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import com.scwang.smartrefresh.layout.api.RefreshContent;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.util.CoordinatorLayoutListener;
import com.scwang.smartrefresh.layout.util.DesignUtil;
import com.scwang.smartrefresh.layout.util.ScrollBoundaryUtil;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RefreshContentWrapper implements RefreshContent {
    protected View mContentView;
    protected View mFixedFooter;
    protected View mFixedHeader;
    protected MotionEvent mMotionEvent;
    protected View mRealContentView;
    protected View mScrollableView;
    protected int mHeaderHeight = Integer.MAX_VALUE;
    protected int mFooterHeight = this.mHeaderHeight - 1;
    protected boolean mEnableRefresh = true;
    protected boolean mEnableLoadMore = true;
    protected ScrollBoundaryDeciderAdapter mBoundaryAdapter = new ScrollBoundaryDeciderAdapter();

    public RefreshContentWrapper(View view) {
        this.mRealContentView = view;
        this.mContentView = view;
    }

    protected void findScrollableView(View content, RefreshKernel kernel) {
        this.mScrollableView = null;
        CoordinatorLayoutListener listener = null;
        boolean isInEditMode = this.mContentView.isInEditMode();
        while (true) {
            if (this.mScrollableView == null || ((this.mScrollableView instanceof NestedScrollingParent) && !(this.mScrollableView instanceof NestedScrollingChild))) {
                content = findScrollableViewInternal(content, this.mScrollableView == null);
                if (content != this.mScrollableView) {
                    if (!isInEditMode) {
                        if (listener == null) {
                            listener = new CoordinatorLayoutListener() { // from class: com.scwang.smartrefresh.layout.impl.RefreshContentWrapper.1
                                @Override // com.scwang.smartrefresh.layout.util.CoordinatorLayoutListener
                                public void update(boolean enableRefresh, boolean enableLoadMore) {
                                    RefreshContentWrapper.this.mEnableRefresh = enableRefresh;
                                    RefreshContentWrapper.this.mEnableLoadMore = enableLoadMore;
                                }
                            };
                        }
                        DesignUtil.checkCoordinatorLayout(content, kernel, listener);
                    }
                    this.mScrollableView = content;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    protected View findScrollableViewInternal(View content, boolean selfable) {
        View scrollableView = null;
        Queue<View> views = new LinkedBlockingQueue<>(Collections.singletonList(content));
        while (!views.isEmpty() && scrollableView == null) {
            View view = views.poll();
            if (view != null) {
                if ((selfable || view != content) && isScrollableView(view)) {
                    scrollableView = view;
                } else if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    for (int j = 0; j < group.getChildCount(); j++) {
                        views.add(group.getChildAt(j));
                    }
                }
            }
        }
        return scrollableView == null ? content : scrollableView;
    }

    protected View findScrollableViewByEvent(View content, MotionEvent event, View orgScrollableView) {
        if ((content instanceof ViewGroup) && event != null) {
            ViewGroup viewGroup = (ViewGroup) content;
            int childCount = viewGroup.getChildCount();
            PointF point = new PointF();
            for (int i = childCount; i > 0; i--) {
                View child = viewGroup.getChildAt(i - 1);
                if (ScrollBoundaryUtil.isTransformedTouchPointInView(viewGroup, child, event.getX(), event.getY(), point)) {
                    if ((child instanceof ViewPager) || !isScrollableView(child)) {
                        MotionEvent event2 = MotionEvent.obtain(event);
                        event2.offsetLocation(point.x, point.y);
                        return findScrollableViewByEvent(child, event2, orgScrollableView);
                    }
                    return child;
                }
            }
        }
        return orgScrollableView;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public View getView() {
        return this.mContentView;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void moveSpinner(int spinner) {
        this.mRealContentView.setTranslationY(spinner);
        if (this.mFixedHeader != null) {
            this.mFixedHeader.setTranslationY(Math.max(0, spinner));
        }
        if (this.mFixedFooter != null) {
            this.mFixedFooter.setTranslationY(Math.min(0, spinner));
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public boolean canRefresh() {
        return this.mEnableRefresh && this.mBoundaryAdapter.canRefresh(this.mContentView);
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public boolean canLoadMore() {
        return this.mEnableLoadMore && this.mBoundaryAdapter.canLoadMore(this.mContentView);
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public View getScrollableView() {
        return this.mScrollableView;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void onActionDown(MotionEvent e) {
        this.mMotionEvent = MotionEvent.obtain(e);
        this.mMotionEvent.offsetLocation(-this.mContentView.getLeft(), -this.mContentView.getTop());
        if (this.mScrollableView != this.mContentView) {
            this.mScrollableView = findScrollableViewByEvent(this.mContentView, this.mMotionEvent, this.mScrollableView);
        }
        if (this.mScrollableView == this.mContentView) {
            this.mBoundaryAdapter.setActionEvent(null);
        } else {
            this.mBoundaryAdapter.setActionEvent(this.mMotionEvent);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void onActionUpOrCancel() {
        this.mMotionEvent = null;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void fling(int velocity) {
        if (this.mScrollableView instanceof ScrollView) {
            ((ScrollView) this.mScrollableView).fling(velocity);
            return;
        }
        if (this.mScrollableView instanceof AbsListView) {
            if (Build.VERSION.SDK_INT >= 21) {
                ((AbsListView) this.mScrollableView).fling(velocity);
            }
        } else if (this.mScrollableView instanceof WebView) {
            ((WebView) this.mScrollableView).flingScroll(0, velocity);
        } else if (this.mScrollableView instanceof NestedScrollView) {
            ((NestedScrollView) this.mScrollableView).fling(velocity);
        } else if (this.mScrollableView instanceof RecyclerView) {
            ((RecyclerView) this.mScrollableView).fling(0, velocity);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void setUpComponent(RefreshKernel kernel, View fixedHeader, View fixedFooter) {
        findScrollableView(this.mContentView, kernel);
        if (fixedHeader != null || fixedFooter != null) {
            this.mFixedHeader = fixedHeader;
            this.mFixedFooter = fixedFooter;
            FrameLayout frameLayout = new FrameLayout(this.mContentView.getContext());
            kernel.getRefreshLayout().getLayout().removeView(this.mContentView);
            ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
            frameLayout.addView(this.mContentView, -1, -1);
            kernel.getRefreshLayout().getLayout().addView(frameLayout, layoutParams);
            this.mContentView = frameLayout;
            if (fixedHeader != null) {
                fixedHeader.setClickable(true);
                ViewGroup.LayoutParams lp = fixedHeader.getLayoutParams();
                ViewGroup parent = (ViewGroup) fixedHeader.getParent();
                int index = parent.indexOfChild(fixedHeader);
                parent.removeView(fixedHeader);
                lp.height = measureViewHeight(fixedHeader);
                parent.addView(new Space(this.mContentView.getContext()), index, lp);
                frameLayout.addView(fixedHeader);
            }
            if (fixedFooter != null) {
                fixedFooter.setClickable(true);
                ViewGroup.LayoutParams lp2 = fixedFooter.getLayoutParams();
                ViewGroup parent2 = (ViewGroup) fixedFooter.getParent();
                int index2 = parent2.indexOfChild(fixedFooter);
                parent2.removeView(fixedFooter);
                FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(lp2);
                lp2.height = measureViewHeight(fixedFooter);
                parent2.addView(new Space(this.mContentView.getContext()), index2, lp2);
                flp.gravity = 80;
                frameLayout.addView(fixedFooter, flp);
            }
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void onInitialHeaderAndFooter(int headerHeight, int footerHeight) {
        this.mHeaderHeight = headerHeight;
        this.mFooterHeight = footerHeight;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void setScrollBoundaryDecider(ScrollBoundaryDecider boundary) {
        if (boundary instanceof ScrollBoundaryDeciderAdapter) {
            this.mBoundaryAdapter = (ScrollBoundaryDeciderAdapter) boundary;
        } else {
            this.mBoundaryAdapter.setScrollBoundaryDecider(boundary);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public void setEnableLoadMoreWhenContentNotFull(boolean enable) {
        this.mBoundaryAdapter.setEnableLoadMoreWhenContentNotFull(enable);
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshContent
    public ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished(final int spinner) {
        if (this.mScrollableView == null || spinner == 0 || ((spinner >= 0 || !ScrollBoundaryUtil.canScrollDown(this.mScrollableView)) && (spinner <= 0 || !ScrollBoundaryUtil.canScrollUp(this.mScrollableView)))) {
            return null;
        }
        return new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.impl.RefreshContentWrapper.2
            int lastValue;

            {
                this.lastValue = spinner;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                try {
                    if (RefreshContentWrapper.this.mScrollableView instanceof AbsListView) {
                        RefreshContentWrapper.scrollListBy((AbsListView) RefreshContentWrapper.this.mScrollableView, value - this.lastValue);
                    } else {
                        RefreshContentWrapper.this.mScrollableView.scrollBy(0, value - this.lastValue);
                    }
                } catch (Throwable th) {
                }
                this.lastValue = value;
            }
        };
    }

    protected static int measureViewHeight(View view) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        if (p.height > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(p.height, 1073741824);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(childWidthSpec, childHeightSpec);
        return view.getMeasuredHeight();
    }

    protected static void scrollListBy(AbsListView listView, int y) {
        View firstView;
        if (Build.VERSION.SDK_INT >= 19) {
            listView.scrollListBy(y);
            return;
        }
        if (listView instanceof ListView) {
            int firstPosition = listView.getFirstVisiblePosition();
            if (firstPosition != -1 && (firstView = listView.getChildAt(0)) != null) {
                int newTop = firstView.getTop() - y;
                ((ListView) listView).setSelectionFromTop(firstPosition, newTop);
                return;
            }
            return;
        }
        listView.smoothScrollBy(y, 0);
    }

    public static boolean isScrollableView(View view) {
        return (view instanceof AbsListView) || (view instanceof ScrollView) || (view instanceof ScrollingView) || (view instanceof WebView) || (view instanceof ViewPager) || (view instanceof NestedScrollingChild) || (view instanceof NestedScrollingParent);
    }
}
