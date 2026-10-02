package com.scwang.smartrefresh.layout.api;

import com.scwang.smartrefresh.layout.constant.RefreshState;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface RefreshKernel {
    RefreshKernel finishTwoLevel();

    RefreshLayout getRefreshLayout();

    RefreshKernel requestDrawBackgroundForFooter(int i);

    RefreshKernel requestDrawBackgroundForHeader(int i);

    RefreshKernel requestNeedTouchEventWhenLoading(boolean z);

    RefreshKernel requestNeedTouchEventWhenRefreshing(boolean z);

    RefreshKernel requestRemeasureHeightForFooter();

    RefreshKernel requestRemeasureHeightForHeader();

    RefreshKernel setState(RefreshState refreshState);
}
