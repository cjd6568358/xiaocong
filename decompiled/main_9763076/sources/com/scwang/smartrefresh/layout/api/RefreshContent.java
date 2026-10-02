package com.scwang.smartrefresh.layout.api;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface RefreshContent {
    boolean canLoadMore();

    boolean canRefresh();

    void fling(int i);

    View getScrollableView();

    View getView();

    void moveSpinner(int i);

    void onActionDown(MotionEvent motionEvent);

    void onActionUpOrCancel();

    void onInitialHeaderAndFooter(int i, int i2);

    ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished(int i);

    void setEnableLoadMoreWhenContentNotFull(boolean z);

    void setScrollBoundaryDecider(ScrollBoundaryDecider scrollBoundaryDecider);

    void setUpComponent(RefreshKernel refreshKernel, View view, View view2);
}
