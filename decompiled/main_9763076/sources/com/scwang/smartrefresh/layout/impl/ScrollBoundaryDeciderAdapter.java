package com.scwang.smartrefresh.layout.impl;

import android.view.MotionEvent;
import android.view.View;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.util.ScrollBoundaryUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScrollBoundaryDeciderAdapter implements ScrollBoundaryDecider {
    protected ScrollBoundaryDecider boundary;
    protected MotionEvent mActionEvent;
    protected boolean mEnableLoadMoreWhenContentNotFull;

    void setScrollBoundaryDecider(ScrollBoundaryDecider boundary) {
        this.boundary = boundary;
    }

    void setActionEvent(MotionEvent event) {
        this.mActionEvent = event;
    }

    @Override // com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider
    public boolean canRefresh(View content) {
        return this.boundary != null ? this.boundary.canRefresh(content) : ScrollBoundaryUtil.canRefresh(content, this.mActionEvent);
    }

    @Override // com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider
    public boolean canLoadMore(View content) {
        if (this.boundary != null) {
            return this.boundary.canLoadMore(content);
        }
        if (this.mEnableLoadMoreWhenContentNotFull) {
            return !ScrollBoundaryUtil.canScrollDown(content, this.mActionEvent);
        }
        return ScrollBoundaryUtil.canLoadMore(content, this.mActionEvent);
    }

    public void setEnableLoadMoreWhenContentNotFull(boolean enable) {
        this.mEnableLoadMoreWhenContentNotFull = enable;
    }
}
