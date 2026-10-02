package com.scwang.smartrefresh.layout.listener;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface OnMultiPurposeListener extends OnRefreshLoadMoreListener, OnStateChangedListener {
    void onFooterFinish(RefreshFooter refreshFooter, boolean z);

    void onFooterPulling(RefreshFooter refreshFooter, float f, int i, int i2, int i3);

    void onFooterReleased(RefreshFooter refreshFooter, int i, int i2);

    void onFooterReleasing(RefreshFooter refreshFooter, float f, int i, int i2, int i3);

    void onFooterStartAnimator(RefreshFooter refreshFooter, int i, int i2);

    void onHeaderFinish(RefreshHeader refreshHeader, boolean z);

    void onHeaderPulling(RefreshHeader refreshHeader, float f, int i, int i2, int i3);

    void onHeaderReleased(RefreshHeader refreshHeader, int i, int i2);

    void onHeaderReleasing(RefreshHeader refreshHeader, float f, int i, int i2, int i3);

    void onHeaderStartAnimator(RefreshHeader refreshHeader, int i, int i2);
}
